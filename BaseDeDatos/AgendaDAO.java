package BaseDeDatos;

import Modelos.Agenda;

import java.sql.*;

public class AgendaDAO {
    public void registrarAgenda(Agenda agenda) throws SQLException {

        String sql = """
            INSERT INTO agenda (diaDelMes, codProf)
            VALUES (?, ?)
            """;

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            sentencia.setInt(1, agenda.getDiaDelMes());
            sentencia.setInt(2, agenda.getProfesional().getCodProf());

            sentencia.executeUpdate();

            try (ResultSet claves = sentencia.getGeneratedKeys()) {
                if (claves.next()) {
                    agenda.setCodigoAgenda(claves.getInt(1));
                }
            }
        }
    }
}
