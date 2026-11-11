package BaseDeDatos;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class InicializadorBD {

    public static void inicializar() throws SQLException {

        String[] tablas = {
                "CREATE TABLE IF NOT EXISTS servicio (" +
                        "codigoServicio INT PRIMARY KEY AUTO_INCREMENT, " +
                        "nombre VARCHAR(50) NOT NULL, " +
                        "duracion INT NOT NULL DEFAULT 1" +
                        ");",

                "CREATE TABLE IF NOT EXISTS profesional (" +
                        "codProf INT PRIMARY KEY AUTO_INCREMENT, " +
                        "dni VARCHAR(10) NOT NULL, " +
                        "nombre VARCHAR(50) NOT NULL, " +
                        "apellido VARCHAR(50) NOT NULL, " +
                        "telefono VARCHAR(20) NOT NULL" +
                        ");",

                "CREATE TABLE IF NOT EXISTS cliente (" +
                        "dni VARCHAR(10) PRIMARY KEY, " +
                        "nombre VARCHAR(50) NOT NULL, " +
                        "apellido VARCHAR(50) NOT NULL, " +
                        "telefono VARCHAR(20) NOT NULL" +
                        ");",

                "CREATE TABLE IF NOT EXISTS turno (" +
                        "codTurno INT PRIMARY KEY AUTO_INCREMENT, " +
                        "dia INT NOT NULL, " +
                        "hora INT NOT NULL, " +
                        "estado VARCHAR(20) NOT NULL, " +
                        "dniCliente VARCHAR(50), " +
                        "codProf INT, " +
                        "codigoServicio INT, " +
                        "FOREIGN KEY (dniCliente) REFERENCES cliente(dni), " +
                        "FOREIGN KEY (codProf) REFERENCES profesional(codProf), " +
                        "FOREIGN KEY (codigoServicio) REFERENCES servicio(codigoServicio)" +
                        ");"
        };

        try (Connection conexion = ConexionBD.conectar();
             Statement sentencia = conexion.createStatement()) {

            for (String t : tablas) {
                sentencia.executeUpdate(t);
            }

            System.out.println("Esquema de base de datos verificado e inicializado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al inicializar la base de datos: " + e.getMessage());
            throw e;
        }
    }

}
