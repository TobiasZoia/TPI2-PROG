import Controladores.GestorPeluqueria;
import Modelos.Profesional;
import Modelos.Servicio;
import Vistas.VistaConsola;

public class Main {
    public static void main (String args[]){

        GestorPeluqueria gestor = new GestorPeluqueria(1, "Peluqueria Central", 8, 20);

        Profesional profesional1 = new Profesional("30111222", "Carlos", "Gomez", "3534112233");

        gestor.agregarProfesional(profesional1);

        Profesional profesional2 = new Profesional("30111223", "Alberto", "Perez", "3534112234");

        gestor.agregarProfesional(profesional2);

        Servicio servicio1 = new Servicio(101, "Corte de Cabello");

        gestor.agregarServicio(servicio1);

        Servicio servicio2 = new Servicio(102, "Coloración");

        gestor.agregarServicio(servicio2);

        VistaConsola vista =  new VistaConsola(gestor);

        vista.iniciarMenu();

    }
}
