package BaseDeDatos;

import Modelos.Turno;

import java.sql.*;

public class TurnoDAO {
    public void registrarTurno(Turno turno) throws SQLException {

        String sql = """
            INSERT INTO turno (cliente, profesional, servicio, diaDelMes, hora, estadoTurno)
            VALUES (?, ?, ?, ?. ?, ?)
            """;

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, turno.getDniCliente());
            sentencia.setInt(2, turno.getCodProfesional());
            sentencia.setInt(3, turno.getCodigoServicio());
            sentencia.setInt(4, turno.getDiaDelMes());
            sentencia.setInt(5, turno.getHora());
            sentencia.setString(6, turno.getEstadoString());

            sentencia.executeUpdate();
        }
    }

    public void listarTurnos() throws SQLException {

        String sql = "SELECT codTurno, cliente, profesional, servicio, diaDelMes, hora, estadoTurno FROM turno ORDER BY hora";

        try (Connection conexion = ConexionBD.conectar();
             Statement sentencia = conexion.createStatement();
             ResultSet resultado = sentencia.executeQuery(sql)) {

            System.out.println("\n--- LISTADO DE TURNOS ---");

            while (resultado.next()) {

                int codTurno = resultado.getInt("codigo del Turno");
                String dniCliente = resultado.getString("Dni del cliente");
                int codProf = resultado.getInt("Codigo del profesional");
                int codigoServicio = resultado.getInt("Codigo del servicio");
                int diaDelMes = resultado.getInt("Dia del mes");
                int hora = resultado.getInt("Hora");
                String estadoTurno = resultado.getString("Estado del turno");
                System.out.println(
                        codTurno + " - " + dniCliente + " - " + codProf + " - " + codigoServicio + " - " + diaDelMes + " - " + hora + " - " + estadoTurno);
            }
        }
    }
}
