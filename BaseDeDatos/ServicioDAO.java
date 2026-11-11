package BaseDeDatos;

import Modelos.Servicio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ServicioDAO {

    public void registrarServicio(Servicio servicio) throws SQLException {

        String sql = """
            INSERT INTO servicio (nombre, duracion)
            VALUES (?, ?)
            """;

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, servicio.getServicio());
            sentencia.setInt(2, servicio.getDuracion());

            sentencia.executeUpdate();
        }
    }

    public void listarServicios() throws SQLException {

        String sql = "SELECT codigoServicio, servicio, duracion FROM servicio ORDER BY codigoServicio";

        try (Connection conexion = ConexionBD.conectar();
             Statement sentencia = conexion.createStatement();
             ResultSet resultado = sentencia.executeQuery(sql)) {

            System.out.println("\n--- LISTADO DE SERVICIOS ---");

            while (resultado.next()) {

                int codigoServicio = resultado.getInt("codigoServicio");
                String servicio = resultado.getString("servicio");
                int duracion = resultado.getInt("duracion");

                System.out.println(
                        codigoServicio + " - " + servicio + " " + duracion
                );
            }
        }
    }

}
