package Vistas;

import Controladores.GestorPeluqueria;
import Modelos.Agenda;
import Modelos.Turno;

import java.util.Scanner;

public class VistaConsola {

    private GestorPeluqueria gestor;
    private Scanner scanner;

    public VistaConsola() {
        this.scanner = new Scanner(System.in);
    }

    public VistaConsola(GestorPeluqueria gestor) {
        this.gestor = gestor;
        this.scanner = new Scanner(System.in);
    }

    public void iniciarMenu() {
        int opcion = 0;
        do {
            System.out.println("\n=== MENÚ PELUQUERÍA ===");
            System.out.println("1. Registrar nuevo turno");
            System.out.println("2. Ver turnos registrados");
            System.out.println("3. Salir");
            System.out.print("Elija una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    registrarNuevoTurno();
                    break;
                case 2:
                    mostrarTurnosRegistrados();
                    break;
                case 3:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 3);
    }

    private void registrarNuevoTurno() {
        System.out.println("\n--- REGISTRAR NUEVO TURNO ---");
        System.out.print("Ingrese día del mes: ");
        int dia = scanner.nextInt();

        System.out.print("Ingrese hora: ");
        int hora = scanner.nextInt();

        System.out.println("Día ingresado: " + dia + ", Hora: " + hora + " hs.");
    }

    private void mostrarTurnosRegistrados() {
        System.out.println("\n--- TURNOS REGISTRADOS ---");
        boolean hayTurnos = false;
        for (Agenda agenda : gestor.getAgendas()) {
            for (Turno t : agenda.getTurnos()) {
                System.out.println("Día: " + t.getDiaDelMes() + " - " + t.getHora() + " hs | "
                        + "Cliente: " + t.getCliente().getNombre() + " " + t.getCliente().getApellido()
                        + " | Profesional: " + t.getProfesional().getNombre() + " " + t.getProfesional().getApellido());
                hayTurnos = true;
            }
        }
        if (!hayTurnos) {
            System.out.println("No hay turnos registrados.");
        }
    }

    // MÉTODOS DE MENSAJES (MIGRADOS DESDE AGENDA Y GESTOR)

    public void mostrarMensajeTurnoRegistrado(int diaDelMes, int hora) {
        System.out.println("Turno registrado exitosamente el día " + diaDelMes + " a las " + hora + " hs.");
    }

    public void mostrarMensajeTurnoCancelado(Turno turno) {
        System.out.println("El turno del día " + turno.getDiaDelMes() + " a las " + turno.getHora() + " ha sido cancelado con éxito.");
    }

    public void mostrarMensajeTurnoReprogramado(int nuevoDia, int nuevaHora) {
        System.out.println("Turno reprogramado exitosamente para el día " + nuevoDia + " a las " + nuevaHora + " hs.");
    }

    public void mostrarErrorHorarioOcupado() {
        System.out.println("No se puede registrar el turno, horario ocupado.");
    }

    public void mostrarErrorTurnoInexistente() {
        System.out.println("Error: El turno especificado no existe.");
    }

    public void mostrarErrorEstadoTurno(Turno turno) {
        System.out.println("Error: No se puede operar un turno en estado " + turno.getEstadoTurno() + ".");
    }

    public void mostrarErrorProfesionalNoDisponible() {
        System.out.println("Error: El profesional no está disponible en el nuevo día/horario seleccionado.");
    }
}