package BaseDeDatos;

import Modelos.Turno;

import java.sql.*;

public class TurnoDAO {
    public void registrarTurno(Turno turno) throws SQLException {

        String sql = """
            INSERT INTO turno (dia, hora, estado, dniCliente, codProf, codigoServicio)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            sentencia.setInt(1, turno.getDiaDelMes());
            sentencia.setInt(2, turno.getHora());
            sentencia.setString(3, turno.getEstadoString());
            sentencia.setString(4, turno.getDniCliente());
            sentencia.setInt(5, turno.getCodProfesional());
            sentencia.setInt(6, turno.getCodigoServicio());

            sentencia.executeUpdate();

            try (ResultSet claves = sentencia.getGeneratedKeys()) {
                if (claves.next()) {
                    turno.setCodTurno(claves.getInt(1));
                }
            }
        }
    }

    public void listarTurnos() throws SQLException {

        String sql = "SELECT t.codTurno, t.dia, t.hora, t.estado, " +
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
                int diaDelMes = resultado.getInt("dia");
                int hora = resultado.getInt("hora");
                String estadoTurno = resultado.getString("estado");

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
