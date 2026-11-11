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

        String sql = "SELECT t.codTurno, t.diaDelMes, t.hora, t.estadoTurno, " +
                "c.nombre AS nombreCliente, c.apellido AS apellidoCliente, " +
                "p.nombre AS nombreProf, p.apellido AS apellidoProf, " +
                "s.servicio AS nombreServicio " +
                "FROM turno t " +
                "LEFT JOIN cliente c ON t.dniCliente = c.dni " +
                "LEFT JOIN profesional p ON t.codProf = p.codProf " +
                "LEFT JOIN servicio s ON t.codigoServicio = s.codigoServicio " +
                "ORDER BY t.diaDelMes ASC, t.hora ASC";

        try (Connection conexion = ConexionBD.conectar();
             Statement sentencia = conexion.createStatement();
             ResultSet resultado = sentencia.executeQuery(sql)) {

            System.out.println("\n--- LISTADO DE TURNOS ---");

            boolean hayTurnos = false;

            while (resultado.next()) {

                hayTurnos = true;

                int codTurno = resultado.getInt("codTurno");
                int diaDelMes = resultado.getInt("diaDelMes");
                int hora = resultado.getInt("hora");
                String estadoTurno = resultado.getString("estadoTurno");

                String cliente = resultado.getString("nombreCliente") + " " + resultado.getString("apellidoCliente");
                String profesional = resultado.getString("nombreProf") + " " + resultado.getString("apellidoProf");
                String servicio = resultado.getString("nombreServicio");


                System.out.println("ID Turno: " + codTurno +
                        " | Día: " + diaDelMes +
                        " | Hora: " + hora + " hs" +
                        " | Cliente: " + cliente +
                        " | Profesional: " + profesional +
                        " | Servicio: " + servicio +
                        " | Estado: " + estadoTurno);
            }
            if (!hayTurnos) {
                System.out.println("No hay turnos registrados en la base de datos.");
            }
        }
    }

}
