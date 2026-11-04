package Vistas;

import Controladores.GestorPeluqueria;
import Modelos.Agenda;
import Modelos.Cliente;
import Modelos.Profesional;
import Modelos.Servicio;
import Modelos.Turno;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Clase VistaConsola
 * Implementa la interfaz de usuario basada estrictamente en CONSOLA (CLI).
 *
 * Responsabilidades:
 * - Ciclo de vida y menu interactivo por terminal mediante Scanner.
 * - Captura y validacion basica de entradas desde el teclado.
 * - Presentacion de datos formateados (tablas, encabezados, estados).
 * - Emision de mensajes de exito y advertencias/errores por consola.
 */
public class VistaConsola {

    private GestorPeluqueria gestor;
    private final Scanner scanner;

    public VistaConsola() {
        this.scanner = new Scanner(System.in);
    }

    public VistaConsola(GestorPeluqueria gestor) {
        this.gestor = gestor;
        this.scanner = new Scanner(System.in);
    }

    public GestorPeluqueria getGestor() {
        return gestor;
    }

    public void setGestor(GestorPeluqueria gestor) {
        this.gestor = gestor;
    }

    // =========================================================================
    // CICLO PRINCIPAL DEL MENU EN CONSOLA
    // =========================================================================

    public void iniciarPrograma() {
        if (this.gestor == null) {
            mostrarError("No se ha asignado un GestorPeluqueria. Asignando instancia por defecto...");
            this.gestor = new GestorPeluqueria(1, "Peluqueria Central", 8, 20);
        }

        int opcion = -1;
        do {
            mostrarMenuPrincipal();
            opcion = leerEntero("Seleccione una opcion: ");
            System.out.println();

            switch (opcion) {
                case 1:
                    flujoRegistrarTurno();
                    break;
                case 2:
                    flujoCancelarTurno();
                    break;
                case 3:
                    flujoReprogramarTurno();
                    break;
                case 4:
                    flujoListarTurnos();
                    break;
                case 5:
                    flujoListarClientes();
                    break;
                case 6:
                    flujoListarProfesionales();
                    break;
                case 7:
                    flujoListarServicios();
                    break;
                case 8:
                    flujoRegistrarCliente();
                    break;
                case 9:
                    flujoRegistrarProfesional();
                    break;
                case 10:
                    flujoRegistrarServicio();
                    break;
                case 0:
                    mostrarMensaje("Finalizando aplicacion de consola. Hasta luego!");
                    break;
                default:
                    mostrarError("Opcion no valida. Seleccione un numero de la lista.");
                    break;
            }

            if (opcion != 0) {
                pausar();
            }

        } while (opcion != 0);
    }

    private void mostrarMenuPrincipal() {
        mostrarSeparador();
        System.out.println("        SISTEMA DE GESTION DE PELUQUERIA - MODO CONSOLA");
        if (gestor != null && gestor.getNombre() != null) {
            System.out.println("  Establecimiento: " + gestor.getNombre() + 
                               " | Horario: " + gestor.getHorarioApertura() + ":00 a " + gestor.getHorarioCierre() + ":00 hs");
        }
        mostrarSeparador();
        System.out.println("  1. Registrar nuevo turno");
        System.out.println("  2. Cancelar turno");
        System.out.println("  3. Reprogramar turno");
        System.out.println("  4. Consultar agendas y turnos");
        System.out.println("  5. Listar clientes");
        System.out.println("  6. Listar profesionales");
        System.out.println("  7. Listar servicios");
        System.out.println("  8. Registrar nuevo cliente");
        System.out.println("  9. Registrar nuevo profesional");
        System.out.println("  10. Registrar nuevo servicio");
        System.out.println("  0. Salir");
        mostrarSeparador();
    }

    // =========================================================================
    // FLUJOS OPERATIVOS
    // =========================================================================

    private void flujoRegistrarTurno() {
        mostrarTitulo("REGISTRAR NUEVO TURNO");

        if (gestor.getProfesionales().isEmpty()) {
            mostrarError("No existen profesionales registrados para asignar turnos.");
            return;
        }

        if (gestor.getServicios().isEmpty()) {
            mostrarError("No existen servicios registrados en el sistema.");
            return;
        }

        Cliente cliente = seleccionarOBuscarCliente();
        if (cliente == null) {
            mostrarError("Operacion cancelada: Cliente no seleccionado.");
            return;
        }

        mostrarTitulo("Seleccion de Profesional");
        mostrarListaProfesionales(gestor.getProfesionales());
        int idxProf = leerEntero("Ingrese el numero del profesional: ") - 1;
        if (idxProf < 0 || idxProf >= gestor.getProfesionales().size()) {
            mostrarError("Seleccion de profesional invalida.");
            return;
        }
        Profesional profesional = gestor.getProfesionales().get(idxProf);

        mostrarTitulo("Seleccion de Servicio");
        mostrarListaServicios(gestor.getServicios());
        int idxServ = leerEntero("Ingrese el numero del servicio: ") - 1;
        if (idxServ < 0 || idxServ >= gestor.getServicios().size()) {
            mostrarError("Seleccion de servicio invalida.");
            return;
        }
        Servicio servicio = gestor.getServicios().get(idxServ);

        int dia = leerEntero("Ingrese el dia del mes (1 - 31): ");
        int hora = leerEntero("Ingrese la hora (" + gestor.getHorarioApertura() + " a " + gestor.getHorarioCierre() + " hs): ");

        boolean registrado = gestor.registrarTurnoEnAgenda(cliente, profesional, servicio, dia, hora);
        if (registrado) {
            mostrarMensajeTurnoRegistrado(dia, hora);
        } else {
            mostrarErrorHorarioOcupado();
        }
    }

    private void flujoCancelarTurno() {
        mostrarTitulo("CANCELAR TURNO");

        ArrayList<Turno> turnosReservados = obtenerTodosLosTurnosReservados();
        if (turnosReservados.isEmpty()) {
            mostrarMensaje("No hay turnos reservados disponibles para cancelar.");
            return;
        }

        System.out.println("Turnos actualmente reservados:");
        for (int i = 0; i < turnosReservados.size(); i++) {
            System.out.println("  [" + (i + 1) + "] " + formatearTurnoResumido(turnosReservados.get(i)));
        }

        int seleccion = leerEntero("Ingrese el numero de turno a cancelar (0 para volver): ") - 1;
        if (seleccion == -1) {
            mostrarMensaje("Cancelacion abortada por el usuario.");
            return;
        }

        if (seleccion < 0 || seleccion >= turnosReservados.size()) {
            mostrarErrorTurnoInexistente();
            return;
        }

        Turno turnoACancelar = turnosReservados.get(seleccion);
        Agenda agenda = gestor.buscarAgenda(turnoACancelar.getProfesional(), turnoACancelar.getDiaDelMes());

        if (agenda != null) {
            boolean exito = agenda.cancelarTurno(turnoACancelar);
            if (exito) {
                mostrarMensajeTurnoCancelado(turnoACancelar);
            } else {
                mostrarErrorEstadoTurno(turnoACancelar, "cancelar");
            }
        } else {
            mostrarErrorTurnoInexistente();
        }
    }

    private void flujoReprogramarTurno() {
        mostrarTitulo("REPROGRAMAR TURNO");

        ArrayList<Turno> turnosReservados = obtenerTodosLosTurnosReservados();
        if (turnosReservados.isEmpty()) {
            mostrarMensaje("No hay turnos reservados para reprogramar.");
            return;
        }

        System.out.println("Turnos disponibles para reprogramar:");
        for (int i = 0; i < turnosReservados.size(); i++) {
            System.out.println("  [" + (i + 1) + "] " + formatearTurnoResumido(turnosReservados.get(i)));
        }

        int seleccion = leerEntero("Ingrese el numero del turno (0 para volver): ") - 1;
        if (seleccion == -1) {
            mostrarMensaje("Operacion cancelada.");
            return;
        }

        if (seleccion < 0 || seleccion >= turnosReservados.size()) {
            mostrarErrorTurnoInexistente();
            return;
        }

        Turno turnoAReprogramar = turnosReservados.get(seleccion);
        int nuevoDia = leerEntero("Ingrese el nuevo dia del mes (1 - 31): ");
        int nuevaHora = leerEntero("Ingrese la nueva hora (" + gestor.getHorarioApertura() + " a " + gestor.getHorarioCierre() + " hs): ");

        boolean reprogramado = gestor.reprogramarTurno(turnoAReprogramar, nuevoDia, nuevaHora);
        if (reprogramado) {
            mostrarMensajeTurnoReprogramado(nuevoDia, nuevaHora);
        } else {
            if (!gestor.isDiaValido(nuevoDia) || !gestor.isHoraValida(nuevaHora)) {
                mostrarError("El nuevo dia u horario ingresado no cumple con los parametros de la peluqueria.");
            } else if (!gestor.isAgendaProfesionalDisponible(turnoAReprogramar.getProfesional(), nuevoDia, nuevaHora)) {
                mostrarErrorProfesionalNoDisponible();
            } else {
                mostrarErrorEstadoTurno(turnoAReprogramar, "reprogramar");
            }
        }
    }

    private void flujoListarTurnos() {
        mostrarTitulo("CONSULTA GENERAL DE AGENDAS Y TURNOS");
        mostrarAgendas(gestor.getAgendas());
    }

    private void flujoListarClientes() {
        mostrarTitulo("PADRON DE CLIENTES");
        mostrarListaClientes(gestor.getClientes());
    }

    private void flujoListarProfesionales() {
        mostrarTitulo("STAFF DE PROFESIONALES");
        mostrarListaProfesionales(gestor.getProfesionales());
    }

    private void flujoListarServicios() {
        mostrarTitulo("CATALOGO DE SERVICIOS");
        mostrarListaServicios(gestor.getServicios());
    }

    private void flujoRegistrarCliente() {
        mostrarTitulo("NUEVO CLIENTE");
        String dni = leerTexto("DNI: ");
        String nombre = leerTexto("Nombre: ");
        String apellido = leerTexto("Apellido: ");
        String telefono = leerTexto("Telefono: ");

        Cliente nuevo = new Cliente(dni, nombre, apellido, telefono);
        gestor.agregarCliente(nuevo);
        mostrarMensaje("Cliente dado de alta: " + apellido + ", " + nombre + " (DNI: " + dni + ")");
    }

    private void flujoRegistrarProfesional() {
        mostrarTitulo("NUEVO PROFESIONAL");
        String dni = leerTexto("DNI: ");
        String nombre = leerTexto("Nombre: ");
        String apellido = leerTexto("Apellido: ");
        String telefono = leerTexto("Telefono: ");

        Profesional nuevo = new Profesional(dni, nombre, apellido, telefono);
        gestor.agregarProfesional(nuevo);
        mostrarMensaje("Profesional dado de alta: " + apellido + ", " + nombre);
    }

    private void flujoRegistrarServicio() {
        mostrarTitulo("NUEVO SERVICIO");
        int codigo = leerEntero("Codigo de servicio: ");
        String nombre = leerTexto("Descripcion/Nombre del servicio: ");

        Servicio nuevo = new Servicio(codigo, nombre);
        gestor.agregarServicio(nuevo);
        mostrarMensaje("Servicio registrado con exito: " + nombre + " [Cod: " + codigo + "]");
    }

    // =========================================================================
    // METODOS DE PRESENTACION Y MENSAJES (MIGRADOS DE MODELO Y CONTROLADOR)
    // =========================================================================

    public void mostrarMensajeTurnoRegistrado(int diaDelMes, int hora) {
        System.out.println(">> EXITO: Turno registrado exitosamente el dia " + diaDelMes + " a las " + hora + " hs.");
    }

    public void mostrarMensajeTurnoCancelado(Turno turno) {
        if (turno != null) {
            System.out.println(">> EXITO: El turno del dia " + turno.getDiaDelMes() + " a las " + turno.getHora() + " hs ha sido cancelado con exito.");
        } else {
            System.out.println(">> EXITO: El turno ha sido cancelado con exito.");
        }
    }

    public void mostrarMensajeTurnoReprogramado(int nuevoDia, int nuevaHora) {
        System.out.println(">> EXITO: Turno reprogramado exitosamente para el dia " + nuevoDia + " a las " + nuevaHora + " hs.");
    }

    public void mostrarErrorHorarioOcupado() {
        System.out.println(">> ERROR: No se puede registrar el turno, horario ocupado o no disponible.");
    }

    public void mostrarErrorTurnoInexistente() {
        System.out.println(">> ERROR: El turno especificado no existe.");
    }

    public void mostrarErrorEstadoTurno(Turno turno, String accion) {
        if (turno != null) {
            System.out.println(">> ERROR: No se puede " + accion + " un turno en estado " + turno.getEstadoTurno() + ".");
        } else {
            System.out.println(">> ERROR: Estado del turno incompatible con la accion solicitada.");
        }
    }

    public void mostrarErrorProfesionalNoDisponible() {
        System.out.println(">> ERROR: El profesional no esta disponible en el nuevo dia/horario seleccionado.");
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(">> " + mensaje);
    }

    public void mostrarError(String error) {
        System.err.println(">> [!] " + error);
    }

    public void mostrarTitulo(String titulo) {
        System.out.println();
        System.out.println("--- " + titulo.toUpperCase() + " ---");
    }

    public void mostrarSeparador() {
        System.out.println("======================================================================");
    }

    public void mostrarListaClientes(ArrayList<Cliente> clientes) {
        if (clientes == null || clientes.isEmpty()) {
            System.out.println("   (No hay clientes registrados en el sistema)");
            return;
        }
        System.out.printf("   %-4s | %-12s | %-25s | %-15s%n", "#", "DNI", "NOMBRE COMPLETO", "TELEFONO");
        System.out.println("   -------------------------------------------------------------------");
        for (int i = 0; i < clientes.size(); i++) {
            Cliente c = clientes.get(i);
            String nombreCompleto = c.getApellido() + ", " + c.getNombre();
            System.out.printf("   %-4d | %-12s | %-25s | %-15s%n", (i + 1), c.getDni(), nombreCompleto, c.getTelefono());
        }
    }

    public void mostrarListaProfesionales(ArrayList<Profesional> profesionales) {
        if (profesionales == null || profesionales.isEmpty()) {
            System.out.println("   (No hay profesionales registrados en el sistema)");
            return;
        }
        System.out.printf("   %-4s | %-12s | %-25s | %-15s%n", "#", "DNI", "NOMBRE COMPLETO", "TELEFONO");
        System.out.println("   -------------------------------------------------------------------");
        for (int i = 0; i < profesionales.size(); i++) {
            Profesional p = profesionales.get(i);
            String nombreCompleto = p.getApellido() + ", " + p.getNombre();
            System.out.printf("   %-4d | %-12s | %-25s | %-15s%n", (i + 1), p.getDni(), nombreCompleto, p.getTelefono());
        }
    }

    public void mostrarListaServicios(ArrayList<Servicio> servicios) {
        if (servicios == null || servicios.isEmpty()) {
            System.out.println("   (No hay servicios registrados en el sistema)");
            return;
        }
        System.out.printf("   %-4s | %-8s | %-30s | %-10s%n", "#", "CODIGO", "SERVICIO", "DURACION");
        System.out.println("   -------------------------------------------------------------------");
        for (int i = 0; i < servicios.size(); i++) {
            Servicio s = servicios.get(i);
            System.out.printf("   %-4d | %-8d | %-30s | %d hora(s)%n", (i + 1), s.getCodigoServicio(), s.getServicio(), s.getDuracion());
        }
    }

    public void mostrarAgendas(ArrayList<Agenda> agendas) {
        if (agendas == null || agendas.isEmpty()) {
            System.out.println("   (No hay agendas creadas ni turnos registrados aun)");
            return;
        }

        for (Agenda agenda : agendas) {
            Profesional prof = agenda.getProfesional();
            String nombreProf = (prof != null) ? prof.getApellido() + ", " + prof.getNombre() : "Sin profesional";
            System.out.println();
            System.out.println("   [AGENDA] Dia: " + agenda.getDiaDelMes() + " | Profesional: " + nombreProf);
            ArrayList<Turno> turnos = agenda.getTurnos();
            if (turnos.isEmpty()) {
                System.out.println("      (Sin turnos registrados para este dia)");
            } else {
                for (Turno t : turnos) {
                    mostrarDetalleTurno(t);
                }
            }
        }
    }

    public void mostrarDetalleTurno(Turno turno) {
        if (turno == null) return;
        String clienteStr = (turno.getCliente() != null) ? 
                turno.getCliente().getApellido() + ", " + turno.getCliente().getNombre() : "Sin cliente";
        String servicioStr = (turno.getServicio() != null) ? turno.getServicio().getServicio() : "Sin servicio";
        System.out.printf("      * Hora: %02d:00 hs | Estado: %-10s | Cliente: %-20s | Servicio: %s%n",
                turno.getHora(), turno.getEstadoTurno(), clienteStr, servicioStr);
    }

    // =========================================================================
    // METODOS AUXILIARES DE ENTRADA
    // =========================================================================

    private Cliente seleccionarOBuscarCliente() {
        if (gestor.getClientes().isEmpty()) {
            mostrarMensaje("No hay clientes en el sistema. Debe registrar el primero:");
            return crearYAgregarNuevoCliente();
        }

        System.out.println("  1. Seleccionar cliente existente");
        System.out.println("  2. Registrar nuevo cliente");
        int op = leerEntero("Opcion: ");

        if (op == 2) {
            return crearYAgregarNuevoCliente();
        }

        mostrarTitulo("Listado de Clientes");
        mostrarListaClientes(gestor.getClientes());
        int idx = leerEntero("Ingrese el numero de cliente: ") - 1;
        if (idx >= 0 && idx < gestor.getClientes().size()) {
            return gestor.getClientes().get(idx);
        } else {
            mostrarError("Cliente inexistente.");
            return null;
        }
    }

    private Cliente crearYAgregarNuevoCliente() {
        String dni = leerTexto("DNI del cliente: ");
        String nombre = leerTexto("Nombre: ");
        String apellido = leerTexto("Apellido: ");
        String telefono = leerTexto("Telefono: ");

        Cliente nuevo = new Cliente(dni, nombre, apellido, telefono);
        gestor.agregarCliente(nuevo);
        mostrarMensaje("Cliente registrado: " + nombre + " " + apellido);
        return nuevo;
    }

    private ArrayList<Turno> obtenerTodosLosTurnosReservados() {
        ArrayList<Turno> reservados = new ArrayList<>();
        for (Agenda a : gestor.getAgendas()) {
            for (Turno t : a.getTurnos()) {
                if (t.isTurnoReservado()) {
                    reservados.add(t);
                }
            }
        }
        return reservados;
    }

    private String formatearTurnoResumido(Turno t) {
        String cliente = (t.getCliente() != null) ? t.getCliente().getApellido() + " " + t.getCliente().getNombre() : "S/C";
        String prof = (t.getProfesional() != null) ? t.getProfesional().getApellido() : "S/P";
        String serv = (t.getServicio() != null) ? t.getServicio().getServicio() : "S/S";
        return "Dia " + t.getDiaDelMes() + " a las " + t.getHora() + ":00 hs | Prof: " + prof + " | Cliente: " + cliente + " | Servicio: " + serv;
    }

    public int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine().trim();
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                mostrarError("Debe ingresar un numero entero valido.");
            }
        }
    }

    public String leerTexto(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine().trim();
            if (!entrada.isEmpty()) {
                return entrada;
            }
            mostrarError("El campo no puede estar vacio.");
        }
    }

    private void pausar() {
        System.out.println();
        System.out.print("Presione [ENTER] para continuar...");
        scanner.nextLine();
        System.out.println();
    }
}