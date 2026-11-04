import Controladores.GestorPeluqueria;
import Modelos.Profesional;
import Modelos.Servicio;
import Vistas.VistaConsola;

public class Main {
    public static void main (String args[]){

        GestorPeluqueria gestor = new GestorPeluqueria(1, "Peluqueria Central", 8, 20);

        gestor.agregarProfesional(new Profesional("30111222", "Carlos", "Gomez", "3534112233"));
        gestor.agregarServicio(new Servicio(101, "Corte de Cabello"));

        VistaConsola vista =  new VistaConsola(gestor);
        vista.iniciarMenu();

    }
}
