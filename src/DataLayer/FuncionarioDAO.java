package src.Dados;

import src.Exceptions.FuncionarioNaoEncontradoException;
import src.Exceptions.PostoTrabalhoNaoEncontradoException;
import src.Negocio.Funcionarios.Funcionario;
import src.Negocio.Funcionarios.Mecanico;
import src.Negocio.Funcionarios.Rececionista;
import src.Negocio.Funcionarios.Turno;
import src.Negocio.Servicos.PostoTrabalho;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class FuncionarioDAO{
    private static FuncionarioDAO singleton = null;

    private FuncionarioDAO() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS `Funcionario` (" +
                    "  `codFuncionario` INT UNSIGNED NOT NULL AUTO_INCREMENT," +
                    "  `nome` VARCHAR(45) NOT NULL," +
                    "  `codPostoTrabalhoFK` INT UNSIGNED NOT NULL," +
                    "  `codTurnoFK` INT UNSIGNED NOT NULL," +
                    "  PRIMARY KEY (`codFuncionario`)," +
                    "  UNIQUE INDEX `codFuncionario_UNIQUE` (`codFuncionario` ASC) VISIBLE," +
                    "  INDEX `fk_Funcionario_PostoTrabalho1_idx` (`PostoTrabalho_codPostoTrabalho` ASC) VISIBLE," +
                    "  INDEX `fk_Funcionario_Turno1_idx` (`codTurnoFK` ASC) VISIBLE," +
                    "  CONSTRAINT `fk_Funcionario_PostoTrabalho1`" +
                    "    FOREIGN KEY (`codPostoTrabalhoFK`)" +
                    "    REFERENCES `PostoTrabalho` (`codPostoTrabalho`)" +
                    "    ON DELETE NO ACTION" +
                    "    ON UPDATE NO ACTION," +
                    "  CONSTRAINT `fk_Funcionario_Turno1`" +
                    "    FOREIGN KEY (`codTurnoFK`)" +
                    "    REFERENCES `Turno` (`codTurno`)" +
                    "    ON DELETE NO ACTION" +
                    "    ON UPDATE NO ACTION)" +
                    "ENGINE = InnoDB;";
            stm.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS `Mecanico` (" +
                    "  `codFuncionarioFK` INT UNSIGNED NOT NULL," +
                    "  PRIMARY KEY (`codFuncionarioFK`)," +
                    "  UNIQUE INDEX `codFuncionarioFK_UNIQUE` (`codFuncionarioFK` ASC) VISIBLE," +
                    "  CONSTRAINT `fk_Mecanico_Funcionario1`" +
                    "    FOREIGN KEY (`codFuncionarioFK`)" +
                    "    REFERENCES `Funcionario` (`codFuncionario`)" +
                    "    ON DELETE NO ACTION" +
                    "    ON UPDATE NO ACTION)" +
                    "ENGINE = InnoDB;";
            stm.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS `Rececionista` (" +
                    "  `codFuncionarioFK` INT UNSIGNED NOT NULL," +
                    "  PRIMARY KEY (`codFuncionarioFK`)," +
                    "  UNIQUE INDEX `codFuncionario_UNIQUE` (`codFuncionarioFK` ASC) VISIBLE," +
                    "  CONSTRAINT `fk_Rececionista_Funcionario1`" +
                    "    FOREIGN KEY (`codFuncionarioFK`)" +
                    "    REFERENCES `Funcionario` (`codFuncionario`)" +
                    "    ON DELETE NO ACTION" +
                    "    ON UPDATE NO ACTION)" +
                    "ENGINE = InnoDB;";
            stm.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS `DiaTrabalho` (" +
                    "  `codDiaTrabalho` INT UNSIGNED NOT NULL AUTO_INCREMENT," +
                    "  `dia` DATE NOT NULL," +
                    "  `horaEntrada` TIME NOT NULL," +
                    "  `horaSaida` TIME NOT NULL," +
                    "  `codFuncionarioFK` INT UNSIGNED NOT NULL," +
                    "  PRIMARY KEY (`codDiaTrabalho`)," +
                    "  UNIQUE INDEX `codDiaTrabalho_UNIQUE` (`codDiaTrabalho` ASC) VISIBLE," +
                    "  INDEX `fk_DiaTrabalho_Funcionario1_idx` (`codFuncionarioFK` ASC) VISIBLE," +
                    "  CONSTRAINT `fk_DiaTrabalho_Funcionario1`" +
                    "    FOREIGN KEY (`codFuncionarioFK`)" +
                    "    REFERENCES `Funcionario` (`codFuncionario`)" +
                    "    ON DELETE NO ACTION" +
                    "    ON UPDATE NO ACTION)" +
                    "ENGINE = InnoDB;";
            stm.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS `Turno` (" +
                    "  `codTurno` INT UNSIGNED NOT NULL AUTO_INCREMENT," +
                    "  `horaInicio` TIME NOT NULL," +
                    "  `horaFim` TIME NOT NULL," +
                    "  PRIMARY KEY (`codTurno`)," +
                    "  UNIQUE INDEX `codTurno_UNIQUE` (`codTurno` ASC) VISIBLE)" +
                    "ENGINE = InnoDB;";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static FuncionarioDAO getInstance() {
        if (FuncionarioDAO.singleton == null) {
            FuncionarioDAO.singleton = new FuncionarioDAO();
        }
        return FuncionarioDAO.singleton;
    }

    public Funcionario get(Integer key) throws FuncionarioNaoEncontradoException {
        Funcionario funcionario = null;

        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery( "SELECT * FROM Funcionario WHERE Funcionario.codFuncionario ='"+key+"'")){

            if (rs.next()) {
                int codFuncionario = rs.getInt("codFuncionario");
                String nome = rs.getString("nome");
                PostoTrabalho postoTrabalho = PostoTrabalhoDAO.getInstance().get(rs.getInt("codPostoTrabalhoFK"));
                ResultSet rs2 = stm.executeQuery("SELECT * FROM Turno WHERE Turno.codTurno ='"
                        + rs.getInt("codTurnoFK") + "'");
                if (rs2.next()){
                    Turno turno = new Turno(rs2.getTime("horaInicio").toLocalTime(),
                            rs2.getTime("horaFim").toLocalTime(),rs2.getInt("codTurno"));

                ResultSet rs3 = stm.executeQuery("SELECT * FROM Mecanico WHERE Mecanico.codFuncionarioFK = '" + key + "'");
                if (rs3.next()) {
                    //this Funcionario is a Mecanico
                    Set<Integer> codigosPedidos = new HashSet<>();
                    String sqlQuery = "SELECT * FROM Pedidos WHERE mecanicoCodFuncionarioFK = ?";

                    try (PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery)) {
                        preparedStatement.setString(1, String.valueOf(key));
                        ResultSet rs4 = preparedStatement.executeQuery();
                        if (rs4.next()) {
                            int codPedido = rs4.getInt("codPedidos");
                            codigosPedidos.add(codPedido);
                        }

                    funcionario = new Mecanico(codFuncionario, nome, postoTrabalho, turno, codigosPedidos);
                }} else {
                    //this Funcionario is a Rececionista
                    funcionario = new Rececionista(codFuncionario, nome, postoTrabalho, turno);
                }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
            throw new NullPointerException(e.getMessage());
        } catch (PostoTrabalhoNaoEncontradoException e) {
            throw new RuntimeException(e);
        }

        if (funcionario==null){
            throw new FuncionarioNaoEncontradoException("O funcionario "+key+" não existe!");
        }
        return funcionario;
    }

    
    public Funcionario put(Integer key, Funcionario f) {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             PreparedStatement pstm = conn.prepareStatement("INSERT INTO Funcionario " +
                     "(codFuncionario, nome, codPostoTrabalhoFK, codTurnoFK) VALUES (?, ?, ?, ?) " +
                     "ON DUPLICATE KEY UPDATE nome=VALUES(nome), " +
                     "codPostoTrabalhoFK=VALUES(codPostoTrabalhoFK), codTurnoFK=VALUES(codTurnoFK)")) {

            pstm.setInt(1, key);
            pstm.setString(2, f.getNome());
            pstm.setInt(3, f.getPostoTrabalhoAtual().getCodPosto());
            pstm.setInt(4, f.getTurno().getCodTurno());
            pstm.executeUpdate();


            PreparedStatement pstmTurno = conn.prepareStatement("INSERT INTO Turno " +
                    "(codTurno, horaInicio, horaFim) VALUES (?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE horaInicio=VALUES(horaInicio), horaFim=VALUES(horaFim)");
            pstmTurno.setInt(1,f.getTurno().getCodTurno());
            pstmTurno.setTime(2,Time.valueOf(f.getTurno().getHoraInicio()));
            pstmTurno.setTime(3,Time.valueOf(f.getTurno().getHoraFim()));
            pstmTurno.executeUpdate();


            if(f instanceof Mecanico){
                PreparedStatement pstmMecanico = conn.prepareStatement("INSERT INTO Mecanico (codFuncionarioFK) VALUES (?) ON DUPLICATE KEY UPDATE codFuncionarioFK=VALUES(codFuncionarioFK)");
                pstmMecanico.setInt(1,key);
                pstmMecanico.executeUpdate();

                // Adicionar mecanicoCodFuncionarioFK ao Pedido
                PreparedStatement pstmPedido = conn.prepareStatement("UPDATE Pedidos SET mecanicoCodFuncionarioFK = ? " +
                        "WHERE codPedidos = ?");
                for (Integer codPedido: ((Mecanico) f).getCodigosPedidosRealizados()){
                    pstmPedido.setInt(1,key);
                    pstmPedido.setInt(2,codPedido);
                    pstmPedido.executeUpdate();
                }
                if((((Mecanico) f).getCodPedidoExecutar() != -1)){
                    pstmPedido.setInt(1,key);
                    pstmPedido.setInt(2,((Mecanico) f).getCodPedidoExecutar());
                    pstmPedido.executeUpdate();
                }
            }else if (f instanceof Rececionista){
                PreparedStatement pstmRececionista = conn.prepareStatement("INSERT INTO Rececionista (codFuncionarioFK) VALUES (?)  ON DUPLICATE KEY UPDATE codFuncionarioFK=VALUES(codFuncionarioFK)");
                pstmRececionista.setInt(1,key);
                pstmRececionista.executeUpdate();
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return f;
    }
}
