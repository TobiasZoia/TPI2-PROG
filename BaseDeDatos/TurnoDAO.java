package BaseDeDatos;

import Modelos.*;

import java.sql.*;
import java.util.ArrayList;

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
                "ORDER BY t.dia ASC, t.hora ASC";

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

    public boolean existeTurnoReservado(int codProf, int dia, int hora) throws SQLException {
        String sql = "SELECT COUNT(*) FROM turno WHERE codProf = ? AND dia = ? AND hora = ? AND estado = 'RESERVADO'";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, codProf);
            sentencia.setInt(2, dia);
            sentencia.setInt(3, hora);

            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    return resultado.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public ArrayList<Turno> obtenerTurnosPorDia(int dia) throws SQLException {
        ArrayList<Turno> turnos = new ArrayList<>();

        String sql = "SELECT t.codTurno, t.dia, t.hora, t.estado, " +
                "c.dni, c.nombre AS nombreCliente, c.apellido AS apellidoCliente, c.telefono AS telCliente, " +
                "p.codProf, p.dni AS dniProf, p.nombre AS nombreProf, p.apellido AS apellidoProf, p.telefono AS telProf, " +
                "s.codigoServicio, s.servicio AS nombreServicio, s.duracion " +
                "FROM turno t " +
                "LEFT JOIN cliente c ON t.dniCliente = c.dni " +
                "LEFT JOIN profesional p ON t.codProf = p.codProf " +
                "LEFT JOIN servicio s ON t.codigoServicio = s.codigoServicio " +
                "WHERE t.dia = ? " +
                "ORDER BY t.hora ASC";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, dia);

            try (ResultSet resultado = sentencia.executeQuery()) {
                while (resultado.next()) {
                    int codTurno = resultado.getInt("codTurno");
                    int diaTurno = resultado.getInt("dia");
                    int hora = resultado.getInt("hora");
                    EstadoTurno estado = EstadoTurno.valueOf(resultado.getString("estado"));

                    Cliente cliente = new Cliente(
                            resultado.getString("dni"),
                            resultado.getString("nombreCliente"),
                            resultado.getString("apellidoCliente"),
                            resultado.getString("telCliente")
                    );

                    Profesional profesional = new Profesional(
                            resultado.getString("dniProf"),
                            resultado.getString("nombreProf"),
                            resultado.getString("apellidoProf"),
                            resultado.getString("telProf"),
                            resultado.getInt("codProf")
                    );

                    Servicio servicio = new Servicio(
                            resultado.getInt("codigoServicio"),
                            resultado.getString("nombreServicio")
                    );
                    servicio.setDuracion(resultado.getInt("duracion"));

                    Turno turno = new Turno(codTurno, cliente, profesional, servicio, diaTurno, hora);
                    turno.setEstadoTurno(estado);
                    turnos.add(turno);
                }
            }
        }
        return turnos;
    }

    public int actualizarEstadoTurno(int codProf, int dia, int hora, String nuevoEstado) throws SQLException {
        String sqlConsulta = "SELECT estado FROM turno WHERE codProf = ? AND dia = ? AND hora = ? ORDER BY codTurno DESC LIMIT 1";
        String estadoActual = null;

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement sentenciaConsulta = conexion.prepareStatement(sqlConsulta)) {

            sentenciaConsulta.setInt(1, codProf);
            sentenciaConsulta.setInt(2, dia);
            sentenciaConsulta.setInt(3, hora);

            try (ResultSet resultado = sentenciaConsulta.executeQuery()) {
                if (!resultado.next()) {
                    return 0;
                }
                estadoActual = resultado.getString("estado");
            }
        }

        if (estadoActual.equals(nuevoEstado)) {
            return -1;
        }

        if (!estadoActual.equals("RESERVADO")) {
            return -2;
        }

        String sqlUpdate = "UPDATE turno SET estado = ? WHERE codProf = ? AND dia = ? AND hora = ? AND estado = 'RESERVADO'";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement sentenciaUpdate = conexion.prepareStatement(sqlUpdate)) {

            sentenciaUpdate.setString(1, nuevoEstado);
            sentenciaUpdate.setInt(2, codProf);
            sentenciaUpdate.setInt(3, dia);
            sentenciaUpdate.setInt(4, hora);

            int filas = sentenciaUpdate.executeUpdate();
            if (filas > 0) {
                return 1;
            } else {
                return 0;
            }
        }
    }

}
