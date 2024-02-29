package src.Dados;
import src.Exceptions.*;
import src.Negocio.Veiculos.*;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class FichaVeiculoDAO{
    private static FichaVeiculoDAO singleton = null;

    private FichaVeiculoDAO() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
                String sql = "CREATE TABLE IF NOT EXISTS FichaVeiculo (" +
                        "  `matricula` VARCHAR(15) NOT NULL," +
                        "  `clienteNIF` INT UNSIGNED NOT NULL," +
                        "  `Motor_idMotor` INT NOT NULL," +
                        "  PRIMARY KEY (`matricula`)," +
                        "  UNIQUE INDEX `matricula_UNIQUE` (`matricula` ASC) VISIBLE," +
                        "  INDEX `fk_FichaVeiculo_Cliente_idx` (`clienteNIF` ASC) VISIBLE," +
                        "  INDEX `fk_FichaVeiculo_Motor1_idx` (`Motor_idMotor` ASC) VISIBLE," +
                        "  CONSTRAINT `fk_FichaVeiculo_Cliente`" +
                        "    FOREIGN KEY (`clienteNIF`)" +
                        "    REFERENCES `Cliente` (`NIF`)" +
                        "    ON DELETE NO ACTION" +
                        "    ON UPDATE NO ACTION," +
                        "  CONSTRAINT `fk_FichaVeiculo_Motor1`" +
                        "    FOREIGN KEY (`Motor_idMotor`)" +
                        "    REFERENCES `Motor` (`idMotor`)" +
                        "    ON DELETE NO ACTION" +
                        "    ON UPDATE NO ACTION);";
                stm.executeUpdate(sql);
                sql = "CREATE TABLE IF NOT EXISTS Motor (" +
                    "  `idMotor` INT UNSIGNED NOT NULL AUTO_INCREMENT," +
                    "  `marca` VARCHAR(45) NOT NULL," +
                    "  `tipoMotor` TINYINT NOT NULL," +
                    "  PRIMARY KEY (`idMotor`)," +
                    "  UNIQUE INDEX `codMotor_UNIQUE` (`idMotor` ASC) VISIBLE);";
                stm.executeUpdate(sql);
                sql = "CREATE TABLE IF NOT EXISTS `MotorCombustao` (" +
                        "  `idMotorFK` INT UNSIGNED NOT NULL," +
                        "  `cilindrada` INT UNSIGNED NOT NULL," +
                        "  INDEX `fk_MotorCombustao_Motor1_idx` (`idMotorFK` ASC) VISIBLE," +
                        "  PRIMARY KEY (`idMotorFK`)," +
                        "  UNIQUE INDEX `idMotorFK_UNIQUE` (`idMotorFK` ASC) VISIBLE," +
                        "  CONSTRAINT `fk_MotorCombustao_Motor1`" +
                        "    FOREIGN KEY (`idMotorFK`)" +
                        "    REFERENCES `Motor` (`idMotor`)" +
                        "    ON DELETE NO ACTION" +
                        "    ON UPDATE NO ACTION)" +
                        "ENGINE = InnoDB;";
                stm.executeUpdate(sql);
                sql = "CREATE TABLE IF NOT EXISTS MotorEletrico (" +
                        "  `idMotorFK` INT UNSIGNED NOT NULL," +
                        "  `potenciaEltrica` INT UNSIGNED NOT NULL," +
                        "  PRIMARY KEY (`idMotorFK`)," +
                        "  INDEX `fk_MotorEletrico_Motor1_idx` (`idMotorFK` ASC) VISIBLE," +
                        "  UNIQUE INDEX `idMotorFK_UNIQUE` (`idMotorFK` ASC) VISIBLE," +
                        "  CONSTRAINT `fk_MotorEletrico_Motor1`" +
                        "    FOREIGN KEY (`idMotorFK`)" +
                        "    REFERENCES `Motor` (`idMotor`)" +
                        "    ON DELETE NO ACTION" +
                        "    ON UPDATE NO ACTION);";
                stm.executeUpdate(sql);
                sql = "CREATE TABLE IF NOT EXISTS MotorHibrido (" +
                        "  `idMotorFK` INT UNSIGNED NOT NULL," +
                        "  `motorCombustaoFK` INT UNSIGNED NOT NULL," +
                        "  `motorEletricoFK` INT UNSIGNED NOT NULL," +
                        "  INDEX `fk_MotorHibrido_Motor1_idx` (`idMotorFK` ASC) VISIBLE," +
                        "  PRIMARY KEY (`idMotorFK`)," +
                        "  INDEX `fk_MotorHibrido_MotorCombustao1_idx` (`motorCombustaoFK` ASC) VISIBLE," +
                        "  INDEX `fk_MotorHibrido_MotorEletrico1_idx` (`motorEletricoFK` ASC) VISIBLE," +
                        "  UNIQUE INDEX `idMotorFK_UNIQUE` (`idMotorFK` ASC) VISIBLE," +
                        "  CONSTRAINT `fk_MotorHibrido_Motor1`" +
                        "    FOREIGN KEY (`idMotorFK`)" +
                        "    REFERENCES `Motor` (`idMotor`)" +
                        "    ON DELETE NO ACTION" +
                        "    ON UPDATE NO ACTION," +
                        "  CONSTRAINT `fk_MotorHibrido_MotorCombustao1`" +
                        "    FOREIGN KEY (`motorCombustaoFK`)" +
                        "    REFERENCES `MotorCombustao` (`idMotorFK`)" +
                        "    ON DELETE NO ACTION" +
                        "    ON UPDATE NO ACTION," +
                        "  CONSTRAINT `fk_MotorHibrido_MotorEletrico1`" +
                        "    FOREIGN KEY (`motorEletricoFK`)" +
                        "    REFERENCES `MotorEletrico` (`idMotorFK`)" +
                        "    ON DELETE NO ACTION" +
                        "    ON UPDATE NO ACTION);";
                stm.executeUpdate(sql);
                sql = "CREATE TABLE IF NOT EXISTS MotorGasolina (" +
                        "  `idMotorFK` INT UNSIGNED NOT NULL," +
                        "  INDEX `fk_MotorGasolina_MotorCombustao1_idx` (`idMotorFK` ASC) VISIBLE," +
                        "  PRIMARY KEY (`idMotorFK`)," +
                        "  UNIQUE INDEX `idMotorFK_UNIQUE` (`idMotorFK` ASC) VISIBLE," +
                        "  CONSTRAINT `fk_MotorGasolina_MotorCombustao1`" +
                        "    FOREIGN KEY (`idMotorFK`)" +
                        "    REFERENCES `MotorCombustao` (`idMotorFK`)" +
                        "    ON DELETE NO ACTION" +
                        "    ON UPDATE NO ACTION);";
                stm.executeUpdate(sql);
                sql = "CREATE TABLE IF NOT EXISTS MotorGasoleo (" +
                        "  `idMotorFK` INT UNSIGNED NOT NULL," +
                        "  INDEX `fk_MotorGasoleo_MotorCombustao1_idx` (`idMotorFK` ASC) VISIBLE," +
                        "  PRIMARY KEY (`idMotorFK`)," +
                        "  UNIQUE INDEX `MotorCombustao_idMotorFK_UNIQUE` (`idMotorFK` ASC) VISIBLE," +
                        "  CONSTRAINT `fk_MotorGasoleo_MotorCombustao1`" +
                        "    FOREIGN KEY (`idMotorFK`)" +
                        "    REFERENCES `MotorCombustao` (`idMotorFK`)" +
                        "    ON DELETE NO ACTION" +
                        "    ON UPDATE NO ACTION);";
                stm.executeUpdate(sql);
                sql = "CREATE TABLE IF NOT EXISTS FichaVeiculo_Servico (" +
                        "  `FichaVeiculo_matricula` VARCHAR(15) NOT NULL," +
                        "  `Servico_codServico` INT UNSIGNED NOT NULL," +
                        "  PRIMARY KEY (`FichaVeiculo_matricula`, `Servico_codServico`)," +
                        "  INDEX `fk_FichaVeiculo_has_Servico_Servico1_idx` (`Servico_codServico` ASC) VISIBLE," +
                        "  INDEX `fk_FichaVeiculo_has_Servico_FichaVeiculo1_idx` (`FichaVeiculo_matricula` ASC) VISIBLE," +
                        "  CONSTRAINT `fk_FichaVeiculo_has_Servico_FichaVeiculo1`" +
                        "    FOREIGN KEY (`FichaVeiculo_matricula`)" +
                        "    REFERENCES `FichaVeiculo` (`matricula`)" +
                        "    ON DELETE NO ACTION" +
                        "    ON UPDATE NO ACTION," +
                        "  CONSTRAINT `fk_FichaVeiculo_has_Servico_Servico1`" +
                        "    FOREIGN KEY (`Servico_codServico`)" +
                        "    REFERENCES `Servico` (`codServico`)" +
                        "    ON DELETE NO ACTION" +
                        "    ON UPDATE NO ACTION);";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }


    public static FichaVeiculoDAO getInstance() {
        if (FichaVeiculoDAO.singleton == null) {
            FichaVeiculoDAO.singleton = new FichaVeiculoDAO();
        }
        return FichaVeiculoDAO.singleton;
    }
    
    public FichaVeiculo get(Object key) throws FichaVeiculoNaoEncontradoException {
        FichaVeiculo r = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM FichaVeiculo WHERE matricula='" + key + "'")) {
            if (rs.next()) {  // A chave existe na tabela
                int idMotor = rs.getInt("Motor_idMotor");
                Motor motorResultado = null;
                try (PreparedStatement pstmt = conn.prepareStatement( "SELECT * FROM Motor WHERE idMotor=?")) {
                    pstmt.setInt(1, idMotor);
                    try (ResultSet motor = pstmt.executeQuery()) {

                        if (motor.next()) {  // Encontrou a sala
                            int tipo = motor.getInt("tipoMotor");
                            if (tipo == 4) {
                                String sqlMC = "SELECT * FROM MotorCombustao WHERE idMotorFK='" + idMotor + "'";

                                try (ResultSet motorComb = stm.executeQuery(sqlMC)) {
                                    if (motorComb.next()) {
                                        motorResultado = new MotorGasolina(motor.getString("marca"), idMotor, motorComb.getInt("cilindrada"));
                                    }
                                }

                            } else if (tipo == 5) {
                                String sqlMC = "SELECT * FROM MotorCombustao WHERE idMotorFK='" + motor.getInt("idMotor") + "'";

                                try (ResultSet motorComb = stm.executeQuery(sqlMC)) {
                                    if (motorComb.next()) {
                                        motorResultado = new MotorGasoleo(motor.getString("marca"), motor.getInt("idMotor"), motorComb.getInt("cilindrada"));

                                    }
                                }
                            } else if (tipo == 3) {
                                String sql2 = "SELECT * FROM MotorEletrico WHERE idMotorFK='" + motor.getInt("idMotor") + "'";
                                try (ResultSet motorEle = stm.executeQuery(sql2)) {
                                    if (motorEle.next()) {
                                        motorResultado = new MotorEletrico(motor.getString("marca"), motor.getInt("idMotor"), motorEle.getInt("potenciaEltrica"));
                                    }
                                }
                            } else if (tipo == 6) {

                                String sql2 = "SELECT * FROM MotorHibrido WHERE idMotorFK='" + motor.getInt("idMotor") + "'";
                                MotorCombustao motorC = null;
                                MotorEletrico motorE = null;
                                try (ResultSet motorHibrido = stm.executeQuery(sql2)) {
                                    if (motorHibrido.next()) {
                                        String sql3 = "SELECT * FROM MotorEletrico WHERE idMotorFK='" + motorHibrido.getInt("motorEletricoFK") + "'";
                                        try (ResultSet motorEle = stm.executeQuery(sql3)) {
                                            if (motorEle.next()) {
                                                motorE = new MotorEletrico(motor.getString("marca"), motor.getInt("idMotor"), motorEle.getInt("potenciaEltrica"));
                                            }
                                        }
                                        int tipomotor = motorHibrido.getInt("motorCombustaoFK");
                                        String sql4 = "SELECT * FROM MotorCombustao WHERE idMotorFK='" + tipomotor + "'";
                                        try (ResultSet motorComb = stm.executeQuery(sql4)) {
                                            motorC = new MotorGasolina(motor.getString("marca"), motor.getInt("idMotor"), motorComb.getInt("cilindrada"));
                                        }
                                    }
                                    motorResultado = new MotorHibrido(motorHibrido.getString("marca"), motor.getInt("idMotor"), motorC, motorE);
                                }
                            } else if (tipo == 7) {
                                MotorCombustao motorC = null;
                                MotorEletrico motorE = null;
                                String sql2 = "SELECT * FROM MotorHibrido WHERE idMotorFK='" + motor.getInt("idMotor") + "'";
                                try (ResultSet motorHibrido = stm.executeQuery(sql2)) {
                                    if (motorHibrido.next()) {
                                        String sql3 = "SELECT * FROM MotorEletrico WHERE idMotorFK='" + motorHibrido.getInt("motorEletricoFK") + "'";
                                        try (ResultSet motorEle = stm.executeQuery(sql3)) {
                                            if (motorEle.next()) {
                                                motorE = new MotorEletrico(motor.getString("marca"), motor.getInt("idMotor"), motorEle.getInt("potenciaEltrica"));
                                            }
                                        }
                                        int tipomotor = motorHibrido.getInt("motorCombustaoFK");
                                        String sql4 = "SELECT * FROM MotorCombustao WHERE idMotorFK='" + tipomotor + "'";
                                        try (ResultSet motorComb = stm.executeQuery(sql4)) {
                                            motorC = new MotorGasoleo(motor.getString("marca"), motor.getInt("idMotor"), motorComb.getInt("cilindrada"));
                                        }
                                    }
                                    motorResultado = new MotorHibrido(motorHibrido.getString("marca"), motor.getInt("idMotor"), motorC, motorE);
                                }

                            } else {
                                System.out.println("Erro no motor, a fazer o get");
                            }
                        }
                    }
                }
                Set<Integer>  codigosPedidos = new HashSet<>();
                ResultSet cpTabela = stm.executeQuery("SELECT * FROM Pedidos WHERE matriculaFK='" + key+ "'");
                while (cpTabela.next()) {
                    codigosPedidos.add(cpTabela.getInt("codPedidos"));
                }


                Set<Integer>  codigosServico = new HashSet<>();
                ResultSet cs = stm.executeQuery("SELECT * FROM FichaVeiculo_Servico WHERE FichaVeiculo_matricula='" +key + "'");
                while (cs.next()) {
                    codigosServico.add(cs.getInt("Servico_codServico"));
                }
                r = new FichaVeiculo((String) key,codigosPedidos,codigosServico,motorResultado);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(r == null){
                throw new FichaVeiculoNaoEncontradoException("O ficha veiculo "+key+" não existe!");
        }

        return r;
    }

    public FichaVeiculo put(String key, FichaVeiculo fv) {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             PreparedStatement insertFichaVeiculo = conn.prepareStatement("INSERT INTO FichaVeiculo (matricula, Motor_idMotor) fvS (?, ?)");
             PreparedStatement updatePedidos = conn.prepareStatement("UPDATE Pedidos SET matriculaFK = ? WHERE codPedidos = ?");
             PreparedStatement insertOrUpdateFichaVeiculoServico = conn.prepareStatement("INSERT INTO FichaVeiculo_Servico (FichaVeiculo_matricula, Servico_codServico) fvS (?, ?) ON DUPLICATE KEY UPDATE FichaVeiculo_matricula = fvS(FichaVeiculo_matricula), Servico_codServico = fvS(Servico_codServico)");
             PreparedStatement insertMotor = conn.prepareStatement("INSERT INTO Motor (idMotor, marca, tipoMotor) fvS (?, ?, ?)");
             PreparedStatement insertMotorCombustao = conn.prepareStatement("INSERT INTO MotorCombustao (idMotor, cilindrada) fvS (?, ?)");
             PreparedStatement insertMotorGasolina = conn.prepareStatement("INSERT INTO MotorGasolina (idMotor) fvS (?)");
             PreparedStatement insertMotorGasoleo = conn.prepareStatement("INSERT INTO MotorGasoleo (idMotor) fvS (?)");
             PreparedStatement insertMotorEletrico = conn.prepareStatement("INSERT INTO MotorEletrico (idMotor, potenciaEltrica) fvS (?, ?)");
             PreparedStatement insertMotorHibrido = conn.prepareStatement("INSERT INTO MotorHibrido (idMotor) fvS (?)");
             PreparedStatement insertOrUpdateMotorEletrico = conn.prepareStatement("INSERT INTO MotorEletrico (idMotor, potenciaEltrica) fvS (?, ?) ON DUPLICATE KEY UPDATE potenciaEltrica = fvS(potenciaEltrica)");
             PreparedStatement insertOrUpdateMotorCombustao = conn.prepareStatement("INSERT INTO MotorCombustao (idMotor, cilindrada) fvS (?, ?) ON DUPLICATE KEY UPDATE cilindrada = fvS(cilindrada)");
             PreparedStatement insertMotorGasolinaHibrido = conn.prepareStatement("INSERT INTO MotorGasolina (idMotor) fvS (?)");
             PreparedStatement insertMotorGasoleoHibrido = conn.prepareStatement("INSERT INTO MotorGasoleo (idMotor) fvS (?)")) {

            int idMotor = fv.getMotor().getIdMotor();

            // Insert FichaVeiculo
            insertFichaVeiculo.setString(1, key);
            insertFichaVeiculo.setInt(2, idMotor);
            insertFichaVeiculo.executeUpdate();

            // Update Pedidos
            for (Integer codPedido : fv.getCodigosPedidos()) {
                updatePedidos.setString(1, key);
                updatePedidos.setInt(2, codPedido);
                updatePedidos.executeUpdate();
            }

            // Insert or update FichaVeiculo_Servico
            for (Integer codServico : fv.getCodigosServicos()) {
                insertOrUpdateFichaVeiculoServico.setString(1, fv.getMatricula());
                insertOrUpdateFichaVeiculoServico.setInt(2, codServico);
                insertOrUpdateFichaVeiculoServico.executeUpdate();
            }

            // Insert Motor
            insertMotor.setInt(1, idMotor);
            insertMotor.setString(2, fv.getMotor().getMarca());
            insertMotor.setInt(3, fv.getMotor().getCodMotor());
            insertMotor.executeUpdate();

            // Handle specific motor types
            if (fv.getMotor().getCodMotor() == 4 || fv.getMotor().getCodMotor() == 5) {
                // MotorCombustao
                insertMotorCombustao.setInt(1, idMotor);
                insertMotorCombustao.setInt(2, ((MotorCombustao) fv.getMotor()).getCilindrada());
                insertMotorCombustao.executeUpdate();

                // MotorGasolina or MotorGasoleo
                if (fv.getMotor().getCodMotor() == 4) {
                    insertMotorGasolina.setInt(1, idMotor);
                    insertMotorGasolina.executeUpdate();
                } else {
                    insertMotorGasoleo.setInt(1, idMotor);
                    insertMotorGasoleo.executeUpdate();
                }
            } else if (fv.getMotor().getCodMotor() == 3) {
                // MotorEletrico
                MotorEletrico motorEletrico = (MotorEletrico) fv.getMotor();
                insertMotorEletrico.setInt(1, idMotor);
                insertMotorEletrico.setInt(2, motorEletrico.getPotenciaEletrica());
                insertMotorEletrico.executeUpdate();
            } else if (fv.getMotor().getCodMotor() == 6 || fv.getMotor().getCodMotor() == 7) {
                // MotorHibrido
                MotorHibrido motorH = (MotorHibrido) fv.getMotor();

                // MotorEletrico
                MotorEletrico motorEletrico = motorH.getEletrico();
                insertOrUpdateMotorEletrico.setInt(1, idMotor);
                insertOrUpdateMotorEletrico.setInt(2, motorEletrico.getPotenciaEletrica());
                insertOrUpdateMotorEletrico.executeUpdate();

                // MotorCombustao
                MotorCombustao motorC = motorH.getCombustao();
                insertOrUpdateMotorCombustao.setInt(1, idMotor);
                insertOrUpdateMotorCombustao.setInt(2, motorC.getCilindrada());
                insertOrUpdateMotorCombustao.executeUpdate();

                // MotorGasolina or MotorGasoleo
                if (fv.getMotor().getCodMotor() == 6) {
                    insertMotorGasolinaHibrido.setInt(1, idMotor);
                    insertMotorGasolinaHibrido.executeUpdate();
                } else {
                    insertMotorGasoleoHibrido.setInt(1, idMotor);
                    insertMotorGasoleoHibrido.executeUpdate();
                }
            }

        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return fv;
    }
}
