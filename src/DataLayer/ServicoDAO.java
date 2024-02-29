package src.Dados;

import src.Exceptions.ServicoNaoEncontradoException;
import src.Negocio.Servicos.Pack;
import src.Negocio.Servicos.Servico;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ServicoDAO {
    private static ServicoDAO singleton = null;

    private ServicoDAO() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql ="CREATE TABLE IF NOT EXISTS `Servico` (" +
                    "  `codServico` INT UNSIGNED NOT NULL AUTO_INCREMENT," +
                    "  `universal` BIT NOT NULL," +
                    "  `duracaoMedia` INT UNSIGNED NOT NULL," +
                    "  `nome` VARCHAR(75) NOT NULL," +
                    "  `preco` FLOAT UNSIGNED NOT NULL," +
                    "  `tipoServico` TINYINT NOT NULL," +
                    "  `codMotor` INT NOT NULL," +
                    "  PRIMARY KEY (`codServico`)," +
                    "  UNIQUE INDEX `codServico_UNIQUE` (`codServico` ASC) VISIBLE)";
            stm.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS Pack (" +
                    "  `codServicoPackFK` INT UNSIGNED NOT NULL," +
                    "  PRIMARY KEY (`codServicoPackFK`)," +
                    "  INDEX `fk_Pack_Servico1_idx` (`codServicoPackFK` ASC) VISIBLE," +
                    "  UNIQUE INDEX `Servico_codServico_UNIQUE` (`codServicoPackFK` ASC) VISIBLE," +
                    "  CONSTRAINT `fk_Pack_Servico1`" +
                    "    FOREIGN KEY (`codServicoPackFK`)" +
                    "    REFERENCES `Servico` (`codServico`)" +
                    "    ON DELETE NO ACTION" +
                    "    ON UPDATE NO ACTION);";
            stm.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS PackServico (" +
                    "  `codServicoFK` INT UNSIGNED NOT NULL," +
                    "  `codServicoPackFK` INT UNSIGNED NOT NULL," +
                    "  PRIMARY KEY (`codServicoFK`, `codServicoPackFK`)," +
                    "  INDEX `fk_PackServico_Pack1_idx` (`codServicoPackFK` ASC) VISIBLE," +
                    "  CONSTRAINT `fk_PackServico_Servico1`" +
                    "    FOREIGN KEY (`codServicoFK`)" +
                    "    REFERENCES `Servico` (`codServico`)" +
                    "    ON DELETE NO ACTION" +
                    "    ON UPDATE NO ACTION," +
                    "  CONSTRAINT `fk_PackServico_Pack1`" +
                    "    FOREIGN KEY (`codServicoPackFK`)" +
                    "    REFERENCES `Pack` (`codServicoPackFK`)" +
                    "    ON DELETE NO ACTION" +
                    "    ON UPDATE NO ACTION);";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static ServicoDAO getInstance() {
        if (ServicoDAO.singleton == null) {
            ServicoDAO.singleton =  new ServicoDAO();
        }
        return ServicoDAO.singleton;
    }

    public Servico get(Object key) throws ServicoNaoEncontradoException {
        Servico servico = null;

        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD)) {
            String selectServicoQuery = "SELECT * FROM Servico WHERE codServico = ?";
            try (PreparedStatement stm = conn.prepareStatement(selectServicoQuery)) {
                stm.setObject(1, key);

                try (ResultSet rs = stm.executeQuery()) {
                    if (rs.next()) {
                        int codServico = rs.getInt("codServico");
                        boolean universal = rs.getBoolean("universal");
                        int duracaoMedia = rs.getInt("duracaoMedia");
                        String nome = rs.getString("nome");
                        float preco = rs.getFloat("preco");
                        int idMotorFK = rs.getInt("codMotor");

                        Set<Integer> codigosPosto = new HashSet<>();
                        String selectPostoQuery = "SELECT codPostoTrabalho FROM PostoTrabalho WHERE codServicoFK = ?";
                        try (PreparedStatement stm2 = conn.prepareStatement(selectPostoQuery)) {
                            stm2.setObject(1, key);

                            try (ResultSet rs2 = stm2.executeQuery()) {
                                while (rs2.next()) {
                                    int codPosto = rs2.getInt("codPostoTrabalho");
                                    codigosPosto.add(codPosto);
                                }
                            }
                        }

                        String selectPackQuery = "SELECT * FROM Pack WHERE codServicoPackFK = ?";
                        try (PreparedStatement stm3 = conn.prepareStatement(selectPackQuery)) {
                            stm3.setObject(1, key);

                            try (ResultSet rs3 = stm3.executeQuery()) {
                                if (rs3.next()) {
                                    // This Servico is also a Pack
                                    Set<Integer> codServicos = new HashSet<>();
                                    String selectPackServicoQuery = "SELECT * FROM PackServico WHERE codServicoPackFK = ?";
                                    try (PreparedStatement stm4 = conn.prepareStatement(selectPackServicoQuery)) {
                                        stm4.setObject(1, key);

                                        try (ResultSet rs4 = stm4.executeQuery()) {
                                            while (rs4.next()) {
                                                int codServicoFK = rs4.getInt("codServicoFK");
                                                codServicos.add(codServicoFK);
                                            }
                                        }
                                    }

                                    servico = new Pack(universal, codServico, duracaoMedia, nome, preco, idMotorFK, codigosPosto, codServicos);
                                } else {
                                    // This Servico is not a Pack
                                    servico = new Servico(universal, codServico, duracaoMedia, nome, preco, idMotorFK, codigosPosto);
                                }
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Deu erro no get");
            // e.printStackTrace(); // Handle the exception appropriately
            throw new NullPointerException(e.getMessage());
        }

        if (servico == null) {
            throw new ServicoNaoEncontradoException("O servico " + key + " não existe!");
        }
        return servico;
    }



    public Servico put(Integer key, Servico s) {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             PreparedStatement pstm = conn.prepareStatement("INSERT INTO Pedido " +
                     "(codServico, universal, duracaoMedia, nome, preco, idMotorFK) " +
                     "VALUES (?, ?, ?, ?, ?, ?) " +
                     "ON DUPLICATE KEY UPDATE universal=VALUES(universal), duracaoMedia=VALUES(duracaoMedia), " +
                     "nome=VALUES(nome), preco=VALUES(preco), idMotorFK=VALUES(idMotorFK)")) {

            pstm.setInt(1, key);
            pstm.setBoolean(2, s.getUniversal());
            pstm.setInt(3, s.getDuracaoMedia());
            pstm.setString(4, s.getNome());
            pstm.setFloat(5, s.getPreco());
            pstm.setInt(6, s.getCodMotor());
            pstm.executeUpdate();

            // Adicionar codServico nos PostosTrabalho
            PreparedStatement pstmPostoTrabalho = conn.prepareStatement("UPDATE PostoTrabalho SET codServicoFK = ? " +
                    "WHERE codPostoTrabalho = ?");
            for (Integer codPosto: s.getCodigosPosto()){
                pstmPostoTrabalho.setInt(1,s.getCodServico());
                pstmPostoTrabalho.setInt(2,codPosto);
                pstmPostoTrabalho.executeUpdate();
            }

            if(s instanceof Pack){
                PreparedStatement pstmPack = conn.prepareStatement("INSERT INTO Pack (codServicoPackFK) VALUES (?)");
                pstmPack.setInt(1,key);
                pstmPack.executeUpdate();

                // Adicionar entradas no PackServico
                PreparedStatement pstmPackServico = conn.prepareStatement("UPDATE PackServico SET codServicoFK = ? " +
                        "WHERE codServicoPackFK = ?");
                for (Integer codServico: ((Pack) s).getCodServicos()){
                    pstmPostoTrabalho.setInt(1,codServico);
                    pstmPostoTrabalho.setInt(2,key);
                    pstmPackServico.executeUpdate();
                }
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return s;
    }
    
    public Collection<Servico> values() {
        Collection<Servico> res = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT codServico FROM Servico")) {
            while (rs.next()) {
                Integer codServico = rs.getInt("codServico");
                Servico t = this.get(codServico);
                res.add(t);
            }
            return res;
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }
}