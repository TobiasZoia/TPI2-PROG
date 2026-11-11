package BaseDeDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL =
            "jdbc:mysql://localhost:3306/registro_peluqueria";

    private static final String USUARIO = "root";

    private static final String CLAVE = "admin1995";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(
                URL, USUARIO, CLAVE
        );
    }
}
