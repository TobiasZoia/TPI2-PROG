package Vistas;

import Controladores.GestorPeluqueria;
import Modelos.*;
import Validaciones.Validador;

import java.util.ArrayList;
import java.util.InputMismatchException;
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
            System.out.println("3. Registrar turno realizado");
            System.out.println("4. Salir");
            System.out.print("Elija una opción: ");

            try {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        registrarNuevoTurno();
                        break;
                    case 2:
                        mostrarTurnosRegistrados();
                        break;
                    case 3:
                        registrarTurnoRealizado();
                        break;
                    case 4:
                        System.out.println("Saliendo del sistema...");
                        scanner.close();
                        break;
                    default:
                        System.out.println("Opción inválida.");
                }
            }   catch(InputMismatchException e) {
                    System.out.println("Opción invalida, debe ingresar un número: ");
                    scanner.nextLine();
                    opcion = 0;
            }
            } while (opcion != 4);
    }

    public static Profesional seleccionarProfesional(ArrayList<Profesional> listaProf, Scanner sc) {
        for (Profesional p : listaProf) {
            System.out.println(p.getCodProf() + " - " + p.getApellido() + ", " + p.getNombre());
        }
        System.out.println("Ingrese el códido del Profesional");
        int codProf = Validador.leerEntero(sc);

        while (!Validador.codigoProfValido(codProf)) {
            System.out.println("Ingrese un código válido: ");
            codProf = Validador.leerEntero(sc);

        }
        Profesional profSeleccionado = null;
        for (Profesional p : listaProf) {
            if (codProf == p.getCodProf()) {
                profSeleccionado = p;
            }
        }
        return profSeleccionado;
    }

    public static Servicio seleccionarServicio(ArrayList<Servicio> listaServ, Scanner sc) {
        for (Servicio s : listaServ) {
            System.out.println(s.getCodigoServicio() + "-" + s.getServicio());
        }
        System.out.println("Ingrese el código del Servicio: ");
        int codigoServ = Validador.leerEntero(sc);

        while (!Validador.codigoServicioValido(codigoServ)){
            System.out.println("Ingrese un código válido: ");
            codigoServ = Validador.leerEntero(sc);

        }
        Servicio servSeleccionado = null;
        for (Servicio s: listaServ) {
            if (codigoServ == s.getCodigoServicio()) {
                servSeleccionado = s;
            }
        }
        return servSeleccionado;
    }

    private void registrarNuevoTurno() {
        System.out.println("\n--- REGISTRAR NUEVO TURNO ---");

        System.out.println("Ingrese DNI del Cliente: ");
        String dni = scanner.nextLine();
        dni = Validador.quitarEspacios(dni);
        while (Validador.cadenaVacia(dni) || Validador.isNotNumero(dni)){
            System.out.println("Debe ingresar su DNI: ");
            dni = scanner.nextLine();
            dni = Validador.quitarEspacios(dni);
        }

        Cliente cliente = gestor.buscarClientePorDni(dni);

        if (cliente != null) {
            System.out.println("Cliente ya registrado: " + cliente.getNombre() + " " + cliente.getApellido());
        } else {
            System.out.println("Ingrese Nombre del Cliente: ");
            String nombre = scanner.nextLine();
            nombre = Validador.quitarEspacios(nombre);
            while (Validador.cadenaVacia(nombre) || Validador.isNotCadena(nombre)){
                System.out.println("Debe ingresar su Nombre: ");
                nombre = scanner.nextLine();
                nombre = Validador.quitarEspacios(nombre);
            }
            System.out.println("Ingrese Apellido del Cliente: ");
            String apellido = scanner.nextLine();
            apellido = Validador.quitarEspacios(apellido);
            while (Validador.cadenaVacia(apellido) || Validador.isNotCadena(apellido)){
                System.out.println("Debe ingresar su Apellido: ");
                apellido = scanner.nextLine();
                apellido = Validador.quitarEspacios(apellido);
            }
            System.out.println("Ingrese Teléfono: ");
            String tel = scanner.nextLine();
            tel = Validador.quitarEspacios(tel);
            while (Validador.cadenaVacia(tel) || Validador.isNotNumero(tel)){
                System.out.println("Debe ingresar su Telefono: ");
                tel = scanner.nextLine();
                tel = Validador.quitarEspacios(tel);
            }

            cliente = gestor.crearCliente(dni, nombre, apellido, tel);
            gestor.agregarCliente(cliente);
        }

        System.out.println("Seleccione un Profesional: ");

        Profesional profesional = this.seleccionarProfesional(gestor.getProfesionales(), scanner);

        System.out.println("Seleccione un Servicio: ");

        Servicio servicio = this.seleccionarServicio(gestor.getServicios(), scanner);

        System.out.print("Ingrese día del mes en curso (1-31): ");
        int dia = Validador.leerEntero(scanner);
        while (!Validador.isDiaValido(dia)){
            System.out.print("Ingrese un día válido (1-31): ");
            dia = Validador.leerEntero(scanner);
        }
        System.out.println("Nuestro horario de atención en de " + gestor.getHorarioApertura() + " hs. a " + gestor.getHorarioCierre() + " hs.");
        System.out.print("Ingrese hora: ");
        int hora = Validador.leerEntero(scanner);
        while (!Validador.isHoraValida(hora, gestor.getHorarioApertura(), gestor.getHorarioCierre())) {
            System.out.print("Ingrese una hora válida: ");
            hora = Validador.leerEntero(scanner);
        }
        boolean exito = gestor.registrarTurnoEnAgenda(cliente, profesional, servicio, dia, hora);

        if (exito) {
            mostrarMensajeTurnoRegistrado(dia, hora);
        } else {
            mostrarErrorHorarioOcupado();
        }
    }

    private void mostrarTurnosRegistrados() {
        System.out.print("Ingrese día del mes en curso (1-31): ");
        int dia = Validador.leerEntero(scanner);
        while (!Validador.isDiaValido(dia)){
            System.out.print("Ingrese un día válido (1-31): ");
            dia = Validador.leerEntero(scanner);
        }
        System.out.println("\n--- TURNOS REGISTRADOS DEL DÍA " + dia + " ---");
        boolean hayTurnos = false;
        for (Agenda agenda : gestor.getAgendas()) {
            if (agenda != null && agenda.getDiaDelMes() == dia) {
            for (Turno t : agenda.getTurnos()) {
                System.out.println(t.getHora() + " hs | "
                        + "Cliente: " + t.getCliente().getNombre() + " " + t.getCliente().getApellido()
                        + " | Profesional: " + t.getProfesional().getNombre() + " " + t.getProfesional().getApellido());
                hayTurnos = true;
                }
            }
        }
        if (!hayTurnos) {
            System.out.println("No hay turnos registrados.");
        }
    }

    public void registrarTurnoRealizado(){
        System.out.print("Ingrese día del turno: ");
        int dia = Validador.leerEntero(scanner);
        while (!Validador.isDiaValido(dia)){
            System.out.print("Ingrese un día válido (1-31): ");
            dia = Validador.leerEntero(scanner);
        }
        System.out.print("Ingrese hora de turno: ");
        int hora = Validador.leerEntero(scanner);
        while (!Validador.isHoraValida(hora, gestor.getHorarioApertura(), gestor.getHorarioCierre())) {
            System.out.print("Ingrese una hora válida: ");
            hora = Validador.leerEntero(scanner);
        }
        System.out.println("Seleccione el Profesional a cargo del turno: ");
        Profesional profesional = this.seleccionarProfesional(gestor.getProfesionales(), scanner);
        Agenda a = gestor.buscarAgenda(profesional, dia );
        boolean exito = false;
        if (a == null) {
            System.out.println("No existe una agenda registrada para ese profesional en el día " + dia + ".");
            return;
        }
        Turno turnoEncontrado = null;
        for (Turno t : a.getTurnos()) {
            if (t != null && t.getHora() == hora) {
                turnoEncontrado = t;
                break;
            }
        }
        if (turnoEncontrado == null) {
            System.out.println("No se encontró ningún turno registrado a las " + hora + " hs.");
        } else if (turnoEncontrado.isTurnoReservado()) {
            turnoEncontrado.setEstadoRealizado();
            mostrarMensajeTurnoRealizado(dia, hora);
        } else if (turnoEncontrado.isTurnoRealizado()) {
            System.out.println("El turno de las " + hora + " hs ya se encontraba marcado como REALIZADO.");
        } else if (turnoEncontrado.isTurnoCancelado()) {
            System.out.println("El turno de las " + hora + " hs no se puede completar porque fue CANCELADO.");
        }
    }

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
    public void mostrarMensajeTurnoRealizado(int diaDelMes, int hora) {
        System.out.println("El turno del día " + diaDelMes + " a las " + hora + " hs. ha cambiado su estado a REALIZADO.");
    }
}
