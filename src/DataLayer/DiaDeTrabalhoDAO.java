package src.Dados;

import src.Negocio.Funcionarios.DiaDeTrabalho;
import src.Exceptions.DiaDeTrabalhoNaoEncontradoException;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

import java.util.Set;


public class DiaDeTrabalhoDAO {
    private static DiaDeTrabalhoDAO singleton = null;

    public static DiaDeTrabalhoDAO getInstance() {
        if (DiaDeTrabalhoDAO.singleton == null) {
            DiaDeTrabalhoDAO.singleton = new DiaDeTrabalhoDAO();
        }
        return DiaDeTrabalhoDAO.singleton;
    }

    private DiaDeTrabalhoDAO() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS `DiaTrabalho` (" +
                    "  `codDiaTrabalho` INT UNSIGNED NOT NULL AUTO_INCREMENT," +
                    "  `dia` DATE NOT NULL," +
                    "  `horaEntrada` TIME NOT NULL," +
                    "  `horaSaida` TIME NULL," +
                    "  `codFuncionarioFK` INT UNSIGNED NOT NULL," +
                    "  PRIMARY KEY (`codDiaTrabalho`)," +
                    "  UNIQUE INDEX `codDiaTrabalho_UNIQUE` (`codDiaTrabalho` ASC) VISIBLE," +
                    "  INDEX `fk_DiaTrabalho_Funcionario1_idx` (`codFuncionarioFK` ASC) VISIBLE," +
                    "  CONSTRAINT `fk_DiaTrabalho_Funcionario1`" +
                    "    FOREIGN KEY (`codFuncionarioFK`)" +
                    "    REFERENCES `Funcionario` (`codFuncionario`)" +
                    "    ON DELETE NO ACTION" +
                    "    ON UPDATE NO ACTION)";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int size() {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM DiaTrabalho")) {
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

    public DiaDeTrabalho get (Object key, Object codFunc) throws DiaDeTrabalhoNaoEncontradoException {
        DiaDeTrabalho r = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM DiaTrabalho WHERE dia=? AND codFuncionarioFK=?")) {
            ps.setObject(1, Date.valueOf((LocalDate) key));
            ps.setObject(2, codFunc);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Date diaSQL = rs.getDate("dia");
                LocalTime horaEntrada = rs.getTime("horaEntrada") != null ? rs.getTime("horaEntrada").toLocalTime() : null;
                LocalTime horaSaida = rs.getTime("horaSaida") != null ? rs.getTime("horaSaida").toLocalTime() : null;

                r = new DiaDeTrabalho(rs.getInt("codDiaTrabalho"), diaSQL.toLocalDate(), horaEntrada, horaSaida);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(r == null){
            throw new DiaDeTrabalhoNaoEncontradoException("O dia de Trabalho "+key+" do funcionário "+ codFunc +" não existe!");
        }
        return r;
    }

    public DiaDeTrabalho put(Integer codFunc,DiaDeTrabalho p) {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD)) {
            String sql = "INSERT INTO DiaTrabalho (dia, horaEntrada, horaSaida, codFuncionarioFK) " +
                    "VALUES (?, ?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE " +
                    "dia=VALUES(dia), horaEntrada=VALUES(horaEntrada), horaSaida=VALUES(horaSaida), codFuncionarioFK=VALUES(codFuncionarioFK)";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setDate(1, Date.valueOf(p.getDia()));
                ps.setTime(2, Time.valueOf(p.getHoraEntrada()));
                if(p.getHoraSaida() == null)
                {
                    ps.setTime(3, null);

                }else {
                    ps.setTime(3, Time.valueOf(p.getHoraSaida()));
                }
                ps.setInt(4, codFunc);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return p;
    }
}
