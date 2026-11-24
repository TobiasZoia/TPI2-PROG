import Controladores.GestorPeluqueria;
import Modelos.Profesional;
import Modelos.Servicio;
import Vistas.VistaConsola;
import BaseDeDatos.InicializadorBD;
import BaseDeDatos.ProfesionalDAO;
import BaseDeDatos.ServicioDAO;
import java.sql.SQLException;

public class Main {
    public static void main (String args[]){

        try {
            InicializadorBD.inicializar();
        } catch (SQLException e) {
            System.err.println("Error al conectar/inicializar la BD: " + e.getMessage());
            return;
        }

        GestorPeluqueria gestor = new GestorPeluqueria(1, "Peluqueria Central", 8, 20);

        Profesional profesional1 = new Profesional("30111222", "Carlos", "Gomez", "3534112233", 1);
        gestor.agregarProfesional(profesional1);

        Profesional profesional2 = new Profesional("30111223", "Alberto", "Perez", "3534112234", 2);
        gestor.agregarProfesional(profesional2);

        Servicio servicio1 = new Servicio(1, "Corte de Cabello");
        gestor.agregarServicio(servicio1);

        Servicio servicio2 = new Servicio(2, "Coloración");
        gestor.agregarServicio(servicio2);

        ProfesionalDAO profesionalDAO = new ProfesionalDAO();
        ServicioDAO servicioDAO = new ServicioDAO();

        try {
            profesionalDAO.registrarProfesional(profesional1);
            profesionalDAO.registrarProfesional(profesional2);
            servicioDAO.registrarServicio(servicio1);
            servicioDAO.registrarServicio(servicio2);
        } catch (SQLException e) {
            System.err.println("Error al registrar datos iniciales en la base de datos: " + e.getMessage());
        }

        VistaConsola vista =  new VistaConsola(gestor);

        vista.iniciarMenu();
    }
}
