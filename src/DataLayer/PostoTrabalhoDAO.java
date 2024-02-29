package src.Dados;

import src.Exceptions.PostoTrabalhoNaoEncontradoException;
import src.Negocio.Servicos.PostoTrabalho;

import java.sql.*;
import java.time.LocalTime;
import java.util.*;

public class PostoTrabalhoDAO{
    private static PostoTrabalhoDAO singleton = null;

    private PostoTrabalhoDAO() {

        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql =  "CREATE TABLE IF NOT EXISTS `PostoTrabalho` (" +
                    "`codPostoTrabalho` INT UNSIGNED NOT NULL AUTO_INCREMENT," +
                    "`nome` VARCHAR(45) NOT NULL," +
                    "    `disponibilidade` TIME NULL," +
                    "    `codServicoFK` INT UNSIGNED NULL," +
                    "     `ativo` BOOLEAN  NOT NULL," +
                    "    PRIMARY KEY (`codPostoTrabalho`)," +
                    "    UNIQUE INDEX `nome_UNIQUE` (`nome` ASC)," +
                    "    INDEX `fk_PostoTrabalho_Servico1_idx` (`codServicoFK` ASC)," +
                    "    CONSTRAINT `fk_PostoTrabalho_Servico1`" +
                    "    FOREIGN KEY (`codServicoFK`)" +
                    "    REFERENCES `Servico` (`codServico`)" +
                    "    ON DELETE NO ACTION" +
                    "    ON UPDATE NO ACTION" +
                    "    ) ENGINE = InnoDB;";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static PostoTrabalhoDAO getInstance() {
        if (PostoTrabalhoDAO.singleton == null) {
            PostoTrabalhoDAO.singleton = new PostoTrabalhoDAO();
        }
        return PostoTrabalhoDAO.singleton;
    }

    public PostoTrabalho get(Object key) throws PostoTrabalhoNaoEncontradoException {
        PostoTrabalho t = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM PostoTrabalho WHERE codPostoTrabalho='" + key + "'")) {
            if (rs.next()) {
                int codPosto = rs.getInt("codPostoTrabalho");
                LocalTime disponibilidade = rs.getTime("disponibilidade").toLocalTime();
                String nome = rs.getString("nome");
                boolean ativo = rs.getBoolean("ativo");
                Queue<Integer> codigosPedidos = new ArrayDeque<>();
                String sql = "SELECT * FROM Pedidos WHERE codPostoTrabalhoFK=? AND codEstadoFK=?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    // Set the values for the parameters
                    pstmt.setInt(1, rs.getInt("codPostoTrabalho"));
                    pstmt.setInt(2, 3);

                    // Execute the query
                    try (ResultSet rs2 = pstmt.executeQuery()) {
                        // Process the ResultSet rs2
                        while (rs2.next()) {
                            codigosPedidos.add(rs2.getInt("codPedidos"));
                        }
                    }
                }
                t = new PostoTrabalho(codPosto, disponibilidade, nome, ativo, codigosPedidos);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }

        if(t == null){
            throw new PostoTrabalhoNaoEncontradoException("O posto de trabalho "+key+" não existe!");

        }
        return t;
    }

public PostoTrabalho put(Integer key, PostoTrabalho p) {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             PreparedStatement pst = conn.prepareStatement(
                     "INSERT INTO PostoTrabalho (codPostoTrabalho, nome, disponibilidade, ativo) " +
                             "VALUES (?, ?, ?, ?) " +
                             "ON DUPLICATE KEY UPDATE " +
                             "nome=VALUES(nome), disponibilidade=VALUES(disponibilidade), ativo=VALUES(ativo)")) {

            pst.setInt(1, key);
            pst.setString(2, p.getNome());
            pst.setTime(3, Time.valueOf(p.getDisponibilidade()));
            pst.setBoolean(4, p.getAtivo());

            pst.executeUpdate();

            for (Integer codPedido : p.getCodigosPedidos()) {
                String sqlUpdatePedido = "UPDATE Pedidos SET codPostoTrabalhoFK = ? WHERE codPedidos = ? ";
                try (PreparedStatement pstUpdatePedido = conn.prepareStatement(sqlUpdatePedido)) {
                    pstUpdatePedido.setInt(1, key);
                    pstUpdatePedido.setInt(2, codPedido);
                    pstUpdatePedido.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while updating the database", e);
        }
        return p;
    }
}
