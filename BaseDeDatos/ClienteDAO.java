package BaseDeDatos;

import Modelos.Cliente;

import java.sql.*;

public class ClienteDAO {
    public void registrarCliente(Cliente cliente) throws SQLException {

        String sql = """
            INSERT INTO cliente (dni, nombre, apellido, telefono)
            VALUES (?, ?, ?, ?)
            """;

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, cliente.getDni());
            sentencia.setString(2, cliente.getNombre());
            sentencia.setString(3, cliente.getApellido());
            sentencia.setString(4, cliente.getTelefono());

            sentencia.executeUpdate();
        }
    }

    public void listarClientes() throws SQLException {

        String sql = "SELECT dni, nombre, apellido, telefono FROM cliente ORDER BY apellido";

        try (Connection conexion = ConexionBD.conectar();
             Statement sentencia = conexion.createStatement();
             ResultSet resultado = sentencia.executeQuery(sql)) {

            System.out.println("\n--- LISTADO DE CLIENTES ---");

            while (resultado.next()) {

                String dni = resultado.getString("Dni");
                String nombre = resultado.getString("Nombre");
                String apellido = resultado.getString("Apellido");
                String telefono = resultado.getString("Telefono");
                System.out.println(
                       dni + " - " + nombre + " - " + apellido + " - " + telefono);
            }
        }
    }
}
