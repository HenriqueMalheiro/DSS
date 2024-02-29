package src.Dados;

public class DAOconfig {
    public static final String USERNAME = "oficina";
    public static final String PASSWORD = "oficina";
    private static final String DATABASE = "oficina";
    private static final String DRIVER = "jdbc:mysql";
    public static final String URL = DRIVER+"://localhost:3306/"+DATABASE +"?serverTimezone=UTC";

    public DAOconfig() {}
}