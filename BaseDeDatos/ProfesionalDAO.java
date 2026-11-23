package BaseDeDatos;

import Modelos.Profesional;

import java.sql.*;

public class ProfesionalDAO {

    public void registrarProfesional(Profesional profesional) throws SQLException {

        String sql = """
            INSERT IGNORE INTO profesional (codProf, dni, nombre, apellido, telefono)
            VALUES (?, ?, ?, ?, ?)
            """;

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, profesional.getCodProf());
            sentencia.setString(2, profesional.getDni());
            sentencia.setString(3, profesional.getNombre());
            sentencia.setString(4, profesional.getApellido());
            sentencia.setString(5, profesional.getTelefono());

            sentencia.executeUpdate();
        }
    }

    public void listarProfesionales() throws SQLException {

        String sql = "SELECT codProf, dni, nombre, apellido, telefono FROM profesional ORDER BY codProf";

        try (Connection conexion = ConexionBD.conectar();
             Statement sentencia = conexion.createStatement();
             ResultSet resultado = sentencia.executeQuery(sql)) {

            System.out.println("\n--- LISTADO DE PROFESIONALES ---");

            while (resultado.next()) {

                int codProf = resultado.getInt("codProf");
                String dni = resultado.getString("dni");
                String nombre = resultado.getString("nombre");
                String apellido = resultado.getString("apellido");
                String telefono = resultado.getString("telefono");
                System.out.println(
                        codProf + " - " + dni + " - " + nombre + " - " + apellido + " - " + telefono);
            }
        }
    }
}
