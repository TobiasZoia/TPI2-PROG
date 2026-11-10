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
            System.out.println("4. Cancelar turno");
            System.out.println("5. Salir");
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
                        cancelarTurno();
                        break;
                    case 5:
                        System.out.println("--- Saliendo del sistema... ---");
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
            } while (opcion != 5);
    }

    public void mostrarProfesionales() {
        System.out.println("\n--- LISTA DE PROFESIONALES ---");
        ArrayList<Profesional> listaProf = gestor.getProfesionales();
        if (listaProf == null || listaProf.isEmpty()) {
            System.out.println("No hay profesionales registrados.");
            return;
        }
        int i = 1;
        for (Profesional p : listaProf) {
            if (p != null) {
                System.out.println(i + ". " + p.getNombre() + " " + p.getApellido());
                i++;
            }
        }
    }

    public void mostrarServicios() {
        System.out.println("\n--- LISTA DE SERVICIOS ---");
        ArrayList<Servicio> listaServ = gestor.getServicios();
        if (listaServ == null || listaServ.isEmpty()) {
            System.out.println("No hay servicios registrados.");
            return;
        }
        int i = 1;
        for (Servicio s : listaServ) {
            if (s != null) {
                System.out.println(i + ". " + s.getServicio());
                i++;
            }
        }
    }

    public Profesional seleccionarProfesional() {
        this.mostrarProfesionales();
        System.out.println("Ingrese el códido del Profesional");
        int codProf = Validador.leerEntero(scanner);

        Profesional profSeleccionado = gestor.obtenerProfesionalPorIndice(codProf);

        while (profSeleccionado == null) {
            System.out.println("Ingrese un código de profesional válido: ");
            codProf = Validador.leerEntero(scanner);
            profSeleccionado = gestor.obtenerProfesionalPorIndice(codProf);
        }
        return profSeleccionado;
    }

    public Servicio seleccionarServicio() {
        this.mostrarServicios();
        System.out.println("Ingrese el código del Servicio: ");
        int codigoServ = Validador.leerEntero(scanner);

        Servicio servSeleccionado = gestor.obtenerServicioPorIndice(codigoServ);
        while (servSeleccionado == null) {
            System.out.print("Ingrese un código de servicio válido: ");
            codigoServ = Validador.leerEntero(scanner);
            servSeleccionado = gestor.obtenerServicioPorIndice(codigoServ);
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
            nombre = Validador.toCapitalize(nombre);
            while (Validador.cadenaVacia(nombre) || Validador.isNotCadena(nombre)){
                System.out.println("Debe ingresar su Nombre: ");
                nombre = scanner.nextLine();
                nombre = Validador.quitarEspacios(nombre);
                nombre = Validador.toCapitalize(nombre);
            }
            System.out.println("Ingrese Apellido del Cliente: ");
            String apellido = scanner.nextLine();
            apellido = Validador.quitarEspacios(apellido);
            apellido = Validador.toCapitalize(apellido);
            while (Validador.cadenaVacia(apellido) || Validador.isNotCadena(apellido)){
                System.out.println("Debe ingresar su Apellido: ");
                apellido = scanner.nextLine();
                apellido = Validador.quitarEspacios(apellido);
                apellido = Validador.toCapitalize(apellido);
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

        Profesional profesional = this.seleccionarProfesional();

        System.out.println("Seleccione un Servicio: ");

        Servicio servicio = this.seleccionarServicio();

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
        ArrayList<Turno> turnos = gestor.obtenerTurnosPorDia(dia);
        System.out.println("\n--- TURNOS REGISTRADOS DEL DÍA " + dia + " ---");
        if (turnos.isEmpty()) {
            System.out.println("No hay turnos registrados.");
            return;
        }
        for (Turno t : turnos) {
            String cliente = t.getNombreYApellidoCliente();
            String profesional = t.getNombreYApellidoProfesional();
            System.out.println(t.getHora() + " hs. | " + "Cliente: " + cliente + " | Profesional: " + profesional + " | Estado: " + t.getEstadoTurno());
        }
    }

    public void registrarTurnoRealizado(){
        System.out.println("\n--- REGISTRAR TURNO COMO REALIZADO ---");
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
        Profesional profesional = this.seleccionarProfesional();
        int resultado = gestor.marcarTurnoComoRealizado(profesional, dia, hora);
        switch (resultado) {
            case 1:
                mostrarMensajeTurnoRealizado(dia, hora);
                break;
            case -1:
                System.out.println("El turno ya se encontraba marcado como REALIZADO anteriormente.");
                break;
            case -2:
                System.out.println("No es posible marcar como realizado un turno que fue CANCELADO.");
                break;
            default:
                System.out.println("No se encontró ningún turno reservado a las " + hora + " hs. del día " + dia + " para el profesional seleccionado.");
                break;
        }
    }

    public void cancelarTurno(){
        System.out.println("\n--- CANCELAR TURNO ---");
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
        Profesional profesional = this.seleccionarProfesional();
        int resultado = gestor.cancelarTurno(profesional, dia, hora);
        switch (resultado) {
            case 1:
                mostrarMensajeTurnoCancelado(dia, hora);
                break;
            case -1:
                System.out.println("El turno ya se encontraba CANCELADO anteriormente.");
                break;
            case -2:
                System.out.println("No es posible marcar como cancelado un turno que fue REALIZADO.");
                break;
            default:
                System.out.println("No se encontró ningún turno reservado a las " + hora + " hs. del día " + dia + " para el profesional seleccionado.");
                break;
        }
    }

    public void mostrarMensajeTurnoRegistrado(int diaDelMes, int hora) {
        System.out.println("Turno registrado exitosamente el día " + diaDelMes + " a las " + hora + " hs.");
    }

    public void mostrarMensajeTurnoCancelado( int diaDelMes, int hora) {
        System.out.println("Turno del dia " + diaDelMes + " a las " + hora + " hs. exitosamente cancelado.");
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
