package src.Dados;

import src.Exceptions.ClienteNaoEncontradoException;
import src.Negocio.Veiculos.Cliente;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ClienteDAO {
    private static ClienteDAO singleton = null;

    private ClienteDAO() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS Cliente (" +
                    "NIF INT UNSIGNED NOT NULL," +
                    "nome VARCHAR(45) NOT NULL," +
                    "morada VARCHAR(100) NOT NULL," +
                    "telefone INT NOT NULL,"+
                    "email VARCHAR(75) NOT NULL," +
                    "PRIMARY KEY (NIF)," +
                    "UNIQUE INDEX NIF_UNIQUE (NIF ASC) VISIBLE);";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static ClienteDAO getInstance() {
        if (ClienteDAO.singleton == null) {
            ClienteDAO.singleton = new ClienteDAO();
        }
        return ClienteDAO.singleton;
    }

    public boolean containsKey(Object key) {
        boolean r;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs =
                     stm.executeQuery("SELECT NIF FROM Cliente WHERE NIF='"+key.toString()+"'")) {
            r = rs.next();
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    public Cliente get(Object key) throws ClienteNaoEncontradoException {
        Cliente cliente = null;

        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD)) {

            // Consulta principal para obter dados do cliente
            String queryCliente = "SELECT * FROM Cliente WHERE NIF = ?";
            try (PreparedStatement pstmtCliente = conn.prepareStatement(queryCliente)) {
                pstmtCliente.setInt(1, (Integer) key);
                try (ResultSet rs = pstmtCliente.executeQuery()) {
                    if (rs.next()) {
                        // Recuperar os dados do cliente
                        int nif = rs.getInt("NIF");
                        String nome = rs.getString("nome");
                        String morada = rs.getString("morada");
                        int telefone = rs.getInt("telefone");
                        String email = rs.getString("email");

                        // Consulta para obter as matrículas do cliente
                        String queryMatriculas = "SELECT matricula FROM FichaVeiculo WHERE clienteNIF = ?";
                        Set<String> matriculas = new HashSet<>();

                        try (PreparedStatement pstmtMatriculas = conn.prepareStatement(queryMatriculas)) {
                            pstmtMatriculas.setInt(1, nif);
                            try (ResultSet rs2 = pstmtMatriculas.executeQuery()) {
                                while (rs2.next()) {
                                    matriculas.add(rs2.getString("matricula"));
                                }
                            }
                        }

                        // Criar o objeto Cliente
                        cliente = new Cliente(nif, nome, morada, telefone, email, matriculas);
                    }
                }
            }

        } catch (SQLException e) {
            // Tratar erros de base de dados
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }

        if (cliente == null) {
            throw new ClienteNaoEncontradoException("O cliente " + key + " não existe!");
        }

        return cliente;
    }



    public Cliente put(Integer key, Cliente c) {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             PreparedStatement pstmtCliente = conn.prepareStatement("INSERT INTO Cliente (NIF, nome, morada, telefone, email) VALUES (?, ?, ?, ?, ?) " +
                     "ON DUPLICATE KEY UPDATE nome=VALUES(nome), morada=VALUES(morada), telefone=VALUES(telefone), email=VALUES(email)");

             PreparedStatement pstmtFichaVeiculo = conn.prepareStatement("UPDATE FichaVeiculo SET clienteNIF = ? WHERE matricula = ?")) {

            pstmtCliente.setInt(1, c.getNif());
            pstmtCliente.setString(2, c.getNome());
            pstmtCliente.setString(3, c.getMorada());
            pstmtCliente.setInt(4, c.getTelefone());
            pstmtCliente.setString(5, c.getEmail());

            pstmtCliente.executeUpdate();

            // Adicionar as matriculas na FichaVeiculo
            for (String matricula : c.getMatriculas()) {
                pstmtFichaVeiculo.setInt(1, key);
                pstmtFichaVeiculo.setString(2, matricula);
                pstmtFichaVeiculo.executeUpdate();
            }

        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return c;
    }
}
