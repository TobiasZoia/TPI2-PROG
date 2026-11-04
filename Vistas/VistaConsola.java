package Vistas;

import Controladores.GestorPeluqueria;
import Modelos.Agenda;
import Modelos.Cliente;
import Modelos.Profesional;
import Modelos.Servicio;
import Modelos.Turno;

import java.util.Scanner;

public class VistaConsola {

    private GestorPeluqueria gestor;
    private Scanner scanner;

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
            System.out.println("3. Cancelar turno");
            System.out.println("4. Reprogramar turno");
            System.out.println("5. Salir");
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
                    cancelarTurno();
                    break;
                case 4:
                    reprogramarTurno();
                    break;
                case 5:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 5);
    }

    private void registrarNuevoTurno() {
        System.out.println("\n--- REGISTRAR NUEVO TURNO ---");

        if (gestor.getClientes().isEmpty() || gestor.getProfesionales().isEmpty() || gestor.getServicios().isEmpty()) {
            System.out.println("Aviso: Se necesitan clientes, profesionales y servicios cargados.");
            return;
        }

        // Selección de cliente
        System.out.println("Seleccione Cliente:");
        for (int i = 0; i < gestor.getClientes().size(); i++) {
            Cliente c = gestor.getClientes().get(i);
            System.out.println((i + 1) + ". " + c.getNombre() + " " + c.getApellido());
        }
        int idxCli = scanner.nextInt() - 1;

        // Selección de profesional
        System.out.println("Seleccione Profesional:");
        for (int i = 0; i < gestor.getProfesionales().size(); i++) {
            Profesional p = gestor.getProfesionales().get(i);
            System.out.println((i + 1) + ". " + p.getNombre() + " " + p.getApellido());
        }
        int idxProf = scanner.nextInt() - 1;

        // Selección de servicio
        System.out.println("Seleccione Servicio:");
        for (int i = 0; i < gestor.getServicios().size(); i++) {
            Servicio s = gestor.getServicios().get(i);
            System.out.println((i + 1) + ". " + s.getServicio());
        }
        int idxServ = scanner.nextInt() - 1;

        System.out.print("Ingrese día del mes: ");
        int dia = scanner.nextInt();

        System.out.print("Ingrese hora: ");
        int hora = scanner.nextInt();

        boolean exito = gestor.registrarTurnoEnAgenda(
                gestor.getClientes().get(idxCli),
                gestor.getProfesionales().get(idxProf),
                gestor.getServicios().get(idxServ),
                dia,
                hora
        );

        if (exito) {
            mostrarMensajeTurnoRegistrado(dia, hora);
        } else {
            mostrarErrorHorarioOcupado();
        }
    }

    private void mostrarTurnosRegistrados() {
        System.out.println("\n--- TURNOS REGISTRADOS ---");
        boolean hayTurnos = false;
        for (Agenda agenda : gestor.getAgendas()) {
            for (Turno t : agenda.getTurnos()) {
                System.out.println("Día: " + t.getDiaDelMes() + " | Hora: " + t.getHora() + " hs | Estado: " + t.getEstadoTurno()
                        + " | Cliente: " + t.getCliente().getNombre() + " " + t.getCliente().getApellido()
                        + " | Profesional: " + t.getProfesional().getNombre() + " " + t.getProfesional().getApellido()
                        + " | Servicio: " + t.getServicio().getServicio());
                hayTurnos = true;
            }
        }
        if (!hayTurnos) {
            System.out.println("No hay turnos registrados.");
        }
    }

    private void cancelarTurno() {
        System.out.println("\n--- CANCELAR TURNO ---");
        Turno turno = buscarTurnoInteractivo();
        if (turno == null) {
            mostrarErrorTurnoInexistente();
            return;
        }

        Agenda agenda = gestor.buscarAgenda(turno.getProfesional(), turno.getDiaDelMes());
        if (agenda != null && agenda.cancelarTurno(turno)) {
            mostrarMensajeTurnoCancelado(turno);
        } else {
            mostrarErrorEstadoTurno(turno, "cancelar");
        }
    }

    private void reprogramarTurno() {
        System.out.println("\n--- REPROGRAMAR TURNO ---");
        Turno turno = buscarTurnoInteractivo();
        if (turno == null) {
            mostrarErrorTurnoInexistente();
            return;
        }

        System.out.print("Ingrese nuevo día del mes: ");
        int nuevoDia = scanner.nextInt();

        System.out.print("Ingrese nueva hora: ");
        int nuevaHora = scanner.nextInt();

        boolean exito = gestor.reprogramarTurno(turno, nuevoDia, nuevaHora);
        if (exito) {
            mostrarMensajeTurnoReprogramado(nuevoDia, nuevaHora);
        } else {
            System.out.println("No se pudo reprogramar el turno (verifique disponibilidad o estado).");
        }
    }

    private Turno buscarTurnoInteractivo() {
        System.out.print("Ingrese el día del turno que busca: ");
        int dia = scanner.nextInt();
        System.out.print("Ingrese la hora del turno: ");
        int hora = scanner.nextInt();

        for (Agenda a : gestor.getAgendas()) {
            if (a.coincidenciaDia(dia)) {
                for (Turno t : a.getTurnos()) {
                    if (t.getHora() == hora) {
                        return t;
                    }
                }
            }
        }
        return null;
    }

    // MÉTODOS DE MENSAJES (estos vienen desde Agenda y Gestor)

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

    public void mostrarErrorEstadoTurno(Turno turno, String accion) {
        System.out.println("Error: No se puede " + accion + " un turno en estado " + turno.getEstadoTurno() + ".");
    }
}