package src.Dados;


import src.Exceptions.PedidoNaoEncontradoException;
import src.Negocio.Servicos.Pedido;

import java.sql.*;
import java.util.Objects;

public class PedidoDAO {

    private static PedidoDAO singleton = null;

    private PedidoDAO() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            String sql = "CREATE TABLE IF NOT EXISTS Estado (" +
                    "nome VARCHAR(45) NOT NULL,"+
                    "codEstado INT UNSIGNED NOT NULL AUTO_INCREMENT," +
                    "PRIMARY KEY (codEstado)," +
                    "UNIQUE INDEX codEstado_UNIQUE (codEstado ASC) VISIBLE," +
                    "UNIQUE INDEX nome_UNIQUE (nome ASC) VISIBLE);";
            stm.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS `Pedidos` (" +
                    "  `codPedidos` INT UNSIGNED NOT NULL AUTO_INCREMENT," +
                    "  `mensagem` BIT NOT NULL," +
                    "  `motivo` VARCHAR(200) NULL," +
                    "  `atribuido` BIT NOT NULL," +
                    "  `codServicoFK` INT UNSIGNED  NULL," +
                    "  `matriculaFK` VARCHAR(15)  NULL," +
                    "  `codPostoTrabalhoFK` INT UNSIGNED  NULL," +
                    "  `mecanicoCodFuncionarioFK` INT UNSIGNED  NULL," +
                    "  `codEstadoFK` INT UNSIGNED NOT NULL," +
                    "  PRIMARY KEY (`codPedidos`)," +
                    "  INDEX `fk_Pedidos_Servico1_idx` (`codServicoFK` ASC) VISIBLE," +
                    "  INDEX `fk_Pedidos_FichaVeiculo1_idx` (`matriculaFK` ASC) VISIBLE," +
                    "  INDEX `fk_Pedidos_oTrabalho1_idx` (`codPostoTrabalhoFK` ASC) VISIBLE," +
                    "  INDEX `fk_Pedidos_Mecanico1_idx` (`mecanicoCodFuncionarioFK` ASC) VISIBLE," +
                    "  INDEX `fk_Pedidos_Estado1_idx` (`codEstadoFK` ASC) VISIBLE," +
                    "  CONSTRAINT `fk_Pedidos_Servico1`" +
                    "    FOREIGN KEY (`codServicoFK`)" +
                    "    REFERENCES `Servico` (`codServico`)" +
                    "    ON DELETE NO ACTION" +
                    "    ON UPDATE NO ACTION," +
                    "  CONSTRAINT `fk_Pedidos_FichaVeiculo1`" +
                    "    FOREIGN KEY (`matriculaFK`)" +
                    "    REFERENCES `FichaVeiculo` (`matricula`)" +
                    "    ON DELETE NO ACTION" +
                    "    ON UPDATE NO ACTION," +
                    "  CONSTRAINT `fk_Pedidos_PostoTrabalho1`" +
                    "    FOREIGN KEY (`codPostoTrabalhoFK`)" +
                    "    REFERENCES `PostoTrabalho` (`codPostoTrabalho`)" +
                    "    ON DELETE NO ACTION" +
                    "    ON UPDATE NO ACTION," +
                    "  CONSTRAINT `fk_Pedidos_Mecanico1`" +
                    "    FOREIGN KEY (`mecanicoCodFuncionarioFK`)" +
                    "    REFERENCES `Mecanico` (`codFuncionarioFK`)" +
                    "    ON DELETE NO ACTION" +
                    "    ON UPDATE NO ACTION," +
                    "  CONSTRAINT `fk_Pedidos_Estado1`" +
                    "    FOREIGN KEY (`codEstadoFK`)" +
                    "    REFERENCES `Estado` (`codEstado`)" +
                    "    ON DELETE NO ACTION" +
                    "    ON UPDATE NO ACTION)" +
                    "ENGINE = InnoDB";
            stm.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static PedidoDAO getInstance() {
        if (PedidoDAO.singleton == null) {
            PedidoDAO.singleton = new PedidoDAO();
        }
        return PedidoDAO.singleton;
    }

    public static int size() {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM Pedidos")) {
            if(rs.next()) {
                i = rs.getInt(1);
            }
        }
        catch (Exception e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return i;
    }

    public Pedido get(Object key) throws PedidoNaoEncontradoException {
        Pedido pedido = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM Pedidos WHERE codPedidos='"+key+"'")) {
            if (rs.next()) {
                pedido = new Pedido(rs.getInt("codPedidos"),
                        Objects.requireNonNullElse(rs.getString("motivo"), "N/D"),
                        rs.getBoolean("atribuido"),
                        rs.getBoolean("mensagem"),
                        rs.getInt("codEstadoFK"),
                        rs.getString("matriculaFK"),
                        rs.getInt("codServicoFK"));
            }
        } catch (SQLException e) {
            // Tratar erros de base de dados
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }

        if(pedido == null){
            throw new PedidoNaoEncontradoException("O pedido "+key+" não existe!");
        }
        return pedido;
    }



    public Pedido put(Integer key, Pedido p) {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Pedidos (codPedidos, codEstadoFK, mensagem, atribuido, motivo, codServicoFK, matriculaFK) " +
                     "VALUES (?, ?, ?, ?, ?, ?,?) " +
                     "ON DUPLICATE KEY UPDATE codEstadoFK=VALUES(codEstadoFK), mensagem=VALUES(mensagem), " +
                     "atribuido=VALUES(atribuido), motivo=VALUES(motivo), codServicoFK=VALUES(codServicoFK)")) {

            pstmt.setInt(1, p.getCodPedido());
            pstmt.setInt(2, p.getCodEstado());
            pstmt.setBoolean(3, p.getMensagem());
            pstmt.setBoolean(4, p.getAtribuido());
            pstmt.setString(5, p.getMotivo());
            pstmt.setInt(6, p.getCodServico());
            pstmt.setString(7,p.getMatricula());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }

        return p;
    }
}
