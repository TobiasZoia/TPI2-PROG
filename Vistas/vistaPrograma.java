package Vistas;

import Controladores.GestorPeluqueria;
import Modelos.Agenda;
import Modelos.Cliente;
import Modelos.Profesional;
import Modelos.Servicio;
import Modelos.Turno;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Dimension;
import java.util.ArrayList;

/**
 * Clase VistaPrograma
 * Implementa la interfaz de usuario en modo de PROGRAMA VISUAL (GUI con ventanas emergentes).
 * Utiliza JOptionPane y cuadros de dialogo estandar de Java Swing.
 *
 * Responsabilidades:
 * - Menu principal visual con botones de accion.
 * - Formularios de captura de datos mediante cuadros de dialogo.
 * - Desplegables de seleccion para clientes, profesionales y servicios.
 * - Presentacion de reportes y turnos en ventanas con barras de desplazamiento.
 * - Avisos graficos de exito, confirmacion y error.
 */
public class VistaPrograma {

    private GestorPeluqueria gestor;
    private static final String TITULO_APP = "Peluqueria - Gestion de Turnos";

    public VistaPrograma() {
    }

    public VistaPrograma(GestorPeluqueria gestor) {
        this.gestor = gestor;
    }

    public GestorPeluqueria getGestor() {
        return gestor;
    }

    public void setGestor(GestorPeluqueria gestor) {
        this.gestor = gestor;
    }

    // =========================================================================
    // CICLO PRINCIPAL DEL MENU EN PROGRAMA (VENTANAS)
    // =========================================================================

    public void iniciarPrograma() {
        if (this.gestor == null) {
            mostrarErrorVentana("No se ha asignado un GestorPeluqueria. Asignando instancia inicial...");
            this.gestor = new GestorPeluqueria(1, "Peluqueria Central", 8, 20);
        }

        String[] opciones = {
                "1. Registrar turno",
                "2. Cancelar turno",
                "3. Reprogramar turno",
                "4. Consultar agendas/turnos",
                "5. Listar clientes",
                "6. Listar profesionales",
                "7. Listar servicios",
                "8. Alta de cliente",
                "9. Alta de profesional",
                "10. Alta de servicio",
                "Salir"
        };

        boolean continuar = true;
        while (continuar) {
            String seleccion = (String) JOptionPane.showInputDialog(
                    null,
                    "Bienvenido al Sistema de Gestion\nSeleccione la operacion a realizar:",
                    TITULO_APP + " - Menu Principal",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]
            );

            if (seleccion == null || seleccion.equals("Salir")) {
                continuar = false;
                mostrarMensajeVentana("Gracias por utilizar el sistema.");
                break;
            }

            switch (seleccion) {
                case "1. Registrar turno":
                    flujoRegistrarTurno();
                    break;
                case "2. Cancelar turno":
                    flujoCancelarTurno();
                    break;
                case "3. Reprogramar turno":
                    flujoReprogramarTurno();
                    break;
                case "4. Consultar agendas/turnos":
                    flujoListarTurnos();
                    break;
                case "5. Listar clientes":
                    flujoListarClientes();
                    break;
                case "6. Listar profesionales":
                    flujoListarProfesionales();
                    break;
                case "7. Listar servicios":
                    flujoListarServicios();
                    break;
                case "8. Alta de cliente":
                    flujoRegistrarCliente();
                    break;
                case "9. Alta de profesional":
                    flujoRegistrarProfesional();
                    break;
                case "10. Alta de servicio":
                    flujoRegistrarServicio();
                    break;
            }
        }
    }

    // =========================================================================
    // FLUJOS OPERATIVOS MEDIANTE DIALOGOS
    // =========================================================================

    private void flujoRegistrarTurno() {
        if (gestor.getProfesionales().isEmpty()) {
            mostrarErrorVentana("No hay profesionales disponibles. Registre uno primero.");
            return;
        }
        if (gestor.getServicios().isEmpty()) {
            mostrarErrorVentana("No hay servicios disponibles. Registre uno primero.");
            return;
        }

        Cliente cliente = seleccionarOBuscarCliente();
        if (cliente == null) return;

        // Seleccion de Profesional
        String[] profNombres = new String[gestor.getProfesionales().size()];
        for (int i = 0; i < gestor.getProfesionales().size(); i++) {
            Profesional p = gestor.getProfesionales().get(i);
            profNombres[i] = p.getApellido() + ", " + p.getNombre() + " (DNI: " + p.getDni() + ")";
        }
        String profSel = (String) JOptionPane.showInputDialog(
                null, "Seleccione el profesional:", "Registro de Turno",
                JOptionPane.QUESTION_MESSAGE, null, profNombres, profNombres[0]
        );
        if (profSel == null) return;
        Profesional profesional = gestor.getProfesionales().get(obtenerIndiceSeleccionado(profNombres, profSel));

        // Seleccion de Servicio
        String[] servNombres = new String[gestor.getServicios().size()];
        for (int i = 0; i < gestor.getServicios().size(); i++) {
            Servicio s = gestor.getServicios().get(i);
            servNombres[i] = s.getServicio() + " [Cod: " + s.getCodigoServicio() + "]";
        }
        String servSel = (String) JOptionPane.showInputDialog(
                null, "Seleccione el servicio:", "Registro de Turno",
                JOptionPane.QUESTION_MESSAGE, null, servNombres, servNombres[0]
        );
        if (servSel == null) return;
        Servicio servicio = gestor.getServicios().get(obtenerIndiceSeleccionado(servNombres, servSel));

        // Entrada de Dia y Hora
        Integer dia = pedirEnteroVentana("Ingrese dia del mes (1 - 31):");
        if (dia == null) return;

        Integer hora = pedirEnteroVentana("Ingrese hora (" + gestor.getHorarioApertura() + " a " + gestor.getHorarioCierre() + " hs):");
        if (hora == null) return;

        boolean exito = gestor.registrarTurnoEnAgenda(cliente, profesional, servicio, dia, hora);
        if (exito) {
            mostrarMensajeTurnoRegistrado(dia, hora);
        } else {
            mostrarErrorHorarioOcupado();
        }
    }

    private void flujoCancelarTurno() {
        ArrayList<Turno> reservados = obtenerTodosLosTurnosReservados();
        if (reservados.isEmpty()) {
            mostrarMensajeVentana("No hay turnos reservados registrados para cancelar.");
            return;
        }

        String[] opcionesTurnos = new String[reservados.size()];
        for (int i = 0; i < reservados.size(); i++) {
            opcionesTurnos[i] = (i + 1) + ". " + formatearTurnoResumido(reservados.get(i));
        }

        String sel = (String) JOptionPane.showInputDialog(
                null, "Seleccione el turno a cancelar:", "Cancelar Turno",
                JOptionPane.WARNING_MESSAGE, null, opcionesTurnos, opcionesTurnos[0]
        );
        if (sel == null) return;

        int idx = obtenerIndiceSeleccionado(opcionesTurnos, sel);
        Turno turno = reservados.get(idx);

        int confirm = JOptionPane.showConfirmDialog(
                null, "¿Esta seguro de cancelar el turno?\n" + formatearTurnoResumido(turno),
                "Confirmar Cancelacion", JOptionPane.YES_NO_OPTION
        );
        if (confirm != JOptionPane.YES_OPTION) return;

        Agenda agenda = gestor.buscarAgenda(turno.getProfesional(), turno.getDiaDelMes());
        if (agenda != null && agenda.cancelarTurno(turno)) {
            mostrarMensajeTurnoCancelado(turno);
        } else {
            mostrarErrorEstadoTurno(turno, "cancelar");
        }
    }

    private void flujoReprogramarTurno() {
        ArrayList<Turno> reservados = obtenerTodosLosTurnosReservados();
        if (reservados.isEmpty()) {
            mostrarMensajeVentana("No hay turnos disponibles para reprogramar.");
            return;
        }

        String[] opcionesTurnos = new String[reservados.size()];
        for (int i = 0; i < reservados.size(); i++) {
            opcionesTurnos[i] = (i + 1) + ". " + formatearTurnoResumido(reservados.get(i));
        }

        String sel = (String) JOptionPane.showInputDialog(
                null, "Seleccione el turno a reprogramar:", "Reprogramar Turno",
                JOptionPane.QUESTION_MESSAGE, null, opcionesTurnos, opcionesTurnos[0]
        );
        if (sel == null) return;

        int idx = obtenerIndiceSeleccionado(opcionesTurnos, sel);
        Turno turno = reservados.get(idx);

        Integer nuevoDia = pedirEnteroVentana("Ingrese nuevo dia del mes (1 - 31):");
        if (nuevoDia == null) return;

        Integer nuevaHora = pedirEnteroVentana("Ingrese nueva hora (" + gestor.getHorarioApertura() + " a " + gestor.getHorarioCierre() + " hs):");
        if (nuevaHora == null) return;

        boolean exito = gestor.reprogramarTurno(turno, nuevoDia, nuevaHora);
        if (exito) {
            mostrarMensajeTurnoReprogramado(nuevoDia, nuevaHora);
        } else {
            if (!gestor.isDiaValido(nuevoDia) || !gestor.isHoraValida(nuevaHora)) {
                mostrarErrorVentana("Dia u hora fuera de los rangos de atencion.");
            } else if (!gestor.isAgendaProfesionalDisponible(turno.getProfesional(), nuevoDia, nuevaHora)) {
                mostrarErrorProfesionalNoDisponible();
            } else {
                mostrarErrorEstadoTurno(turno, "reprogramar");
            }
        }
    }

    private void flujoListarTurnos() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== REPORTE GENERAL DE AGENDAS Y TURNOS ===\n\n");
        if (gestor.getAgendas().isEmpty()) {
            sb.append("No hay agendas ni turnos creados en el sistema.\n");
        } else {
            for (Agenda a : gestor.getAgendas()) {
                String prof = (a.getProfesional() != null) ? a.getProfesional().getApellido() + ", " + a.getProfesional().getNombre() : "S/P";
                sb.append("AGENDA DIA ").append(a.getDiaDelMes()).append(" - PROFESIONAL: ").append(prof).append("\n");
                if (a.getTurnos().isEmpty()) {
                    sb.append("   (Sin turnos registrados)\n");
                } else {
                    for (Turno t : a.getTurnos()) {
                        String cli = (t.getCliente() != null) ? t.getCliente().getApellido() + " " + t.getCliente().getNombre() : "S/C";
                        String srv = (t.getServicio() != null) ? t.getServicio().getServicio() : "S/S";
                        sb.append(String.format("   * %02d:00 hs | [%-9s] | Cliente: %-18s | Servicio: %s\n",
                                t.getHora(), t.getEstadoTurno(), cli, srv));
                    }
                }
                sb.append("\n");
            }
        }
        mostrarTextoEnVentanaConScroll("Agendas y Turnos", sb.toString());
    }

    private void flujoListarClientes() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-4s | %-12s | %-25s | %-15s\n", "#", "DNI", "NOMBRE COMPLETO", "TELEFONO"));
        sb.append("-------------------------------------------------------------------\n");
        for (int i = 0; i < gestor.getClientes().size(); i++) {
            Cliente c = gestor.getClientes().get(i);
            sb.append(String.format("%-4d | %-12s | %-25s | %-15s\n",
                    (i + 1), c.getDni(), c.getApellido() + ", " + c.getNombre(), c.getTelefono()));
        }
        mostrarTextoEnVentanaConScroll("Listado de Clientes", sb.toString());
    }

    private void flujoListarProfesionales() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-4s | %-12s | %-25s | %-15s\n", "#", "DNI", "NOMBRE COMPLETO", "TELEFONO"));
        sb.append("-------------------------------------------------------------------\n");
        for (int i = 0; i < gestor.getProfesionales().size(); i++) {
            Profesional p = gestor.getProfesionales().get(i);
            sb.append(String.format("%-4d | %-12s | %-25s | %-15s\n",
                    (i + 1), p.getDni(), p.getApellido() + ", " + p.getNombre(), p.getTelefono()));
        }
        mostrarTextoEnVentanaConScroll("Listado de Profesionales", sb.toString());
    }

    private void flujoListarServicios() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-4s | %-8s | %-30s | %-10s\n", "#", "CODIGO", "SERVICIO", "DURACION"));
        sb.append("-------------------------------------------------------------------\n");
        for (int i = 0; i < gestor.getServicios().size(); i++) {
            Servicio s = gestor.getServicios().get(i);
            sb.append(String.format("%-4d | %-8d | %-30s | %d hora(s)\n",
                    (i + 1), s.getCodigoServicio(), s.getServicio(), s.getDuracion()));
        }
        mostrarTextoEnVentanaConScroll("Catalogo de Servicios", sb.toString());
    }

    private void flujoRegistrarCliente() {
        String dni = pedirTextoVentana("Ingrese DNI del cliente:");
        if (dni == null) return;
        String nombre = pedirTextoVentana("Ingrese Nombre:");
        if (nombre == null) return;
        String apellido = pedirTextoVentana("Ingrese Apellido:");
        if (apellido == null) return;
        String telefono = pedirTextoVentana("Ingrese Telefono:");
        if (telefono == null) return;

        Cliente c = new Cliente(dni, nombre, apellido, telefono);
        gestor.agregarCliente(c);
        mostrarMensajeVentana("Cliente registrado con exito:\n" + apellido + ", " + nombre + " (DNI: " + dni + ")");
    }

    private void flujoRegistrarProfesional() {
        String dni = pedirTextoVentana("Ingrese DNI del profesional:");
        if (dni == null) return;
        String nombre = pedirTextoVentana("Ingrese Nombre:");
        if (nombre == null) return;
        String apellido = pedirTextoVentana("Ingrese Apellido:");
        if (apellido == null) return;
        String telefono = pedirTextoVentana("Ingrese Telefono:");
        if (telefono == null) return;

        Profesional p = new Profesional(dni, nombre, apellido, telefono);
        gestor.agregarProfesional(p);
        mostrarMensajeVentana("Profesional registrado con exito:\n" + apellido + ", " + nombre);
    }

    private void flujoRegistrarServicio() {
        Integer cod = pedirEnteroVentana("Ingrese codigo del servicio:");
        if (cod == null) return;
        String desc = pedirTextoVentana("Ingrese nombre o descripcion del servicio:");
        if (desc == null) return;

        Servicio s = new Servicio(cod, desc);
        gestor.agregarServicio(s);
        mostrarMensajeVentana("Servicio agregado exitosamente:\n" + desc + " [Cod: " + cod + "]");
    }

    // =========================================================================
    // METODOS DE PRESENTACION VISUAL (MIGRADOS DE MODELO Y CONTROLADOR)
    // =========================================================================

    public void mostrarMensajeTurnoRegistrado(int diaDelMes, int hora) {
        mostrarMensajeVentana("Turno registrado exitosamente el dia " + diaDelMes + " a las " + hora + " hs.");
    }

    public void mostrarMensajeTurnoCancelado(Turno turno) {
        if (turno != null) {
            mostrarMensajeVentana("El turno del dia " + turno.getDiaDelMes() + " a las " + turno.getHora() + " hs ha sido cancelado con exito.");
        } else {
            mostrarMensajeVentana("El turno ha sido cancelado con exito.");
        }
    }

    public void mostrarMensajeTurnoReprogramado(int nuevoDia, int nuevaHora) {
        mostrarMensajeVentana("Turno reprogramado exitosamente para el dia " + nuevoDia + " a las " + nuevaHora + " hs.");
    }

    public void mostrarErrorHorarioOcupado() {
        mostrarErrorVentana("No se puede registrar el turno: Horario ocupado o no disponible.");
    }

    public void mostrarErrorTurnoInexistente() {
        mostrarErrorVentana("Error: El turno especificado no existe.");
    }

    public void mostrarErrorEstadoTurno(Turno turno, String accion) {
        if (turno != null) {
            mostrarErrorVentana("Error: No se puede " + accion + " un turno en estado " + turno.getEstadoTurno() + ".");
        } else {
            mostrarErrorVentana("Error en el estado del turno para la accion solicitada.");
        }
    }

    public void mostrarErrorProfesionalNoDisponible() {
        mostrarErrorVentana("Error: El profesional no esta disponible en el nuevo dia/horario seleccionado.");
    }

    public void mostrarMensajeVentana(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, TITULO_APP, JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarErrorVentana(String error) {
        JOptionPane.showMessageDialog(null, error, TITULO_APP + " - Error", JOptionPane.ERROR_MESSAGE);
    }

    // =========================================================================
    // UTILIDADES AUXILIARES DE ENTRADA Y DIALOGOS
    // =========================================================================

    private Cliente seleccionarOBuscarCliente() {
        if (gestor.getClientes().isEmpty()) {
            mostrarMensajeVentana("No hay clientes en el sistema. Registremos el primero.");
            flujoRegistrarCliente();
            return gestor.getClientes().isEmpty() ? null : gestor.getClientes().get(gestor.getClientes().size() - 1);
        }

        String[] opciones = {"Seleccionar cliente existente", "Registrar nuevo cliente"};
        int eleccion = JOptionPane.showOptionDialog(
                null, "¿Como desea asignar el cliente?", "Asignacion de Cliente",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]
        );

        if (eleccion == 1) {
            flujoRegistrarCliente();
            return gestor.getClientes().isEmpty() ? null : gestor.getClientes().get(gestor.getClientes().size() - 1);
        }

        String[] cliNombres = new String[gestor.getClientes().size()];
        for (int i = 0; i < gestor.getClientes().size(); i++) {
            Cliente c = gestor.getClientes().get(i);
            cliNombres[i] = c.getApellido() + ", " + c.getNombre() + " (DNI: " + c.getDni() + ")";
        }

        String sel = (String) JOptionPane.showInputDialog(
                null, "Seleccione un cliente:", "Clientes",
                JOptionPane.QUESTION_MESSAGE, null, cliNombres, cliNombres[0]
        );
        if (sel == null) return null;

        return gestor.getClientes().get(obtenerIndiceSeleccionado(cliNombres, sel));
    }

    private String pedirTextoVentana(String mensaje) {
        while (true) {
            String val = JOptionPane.showInputDialog(null, mensaje, TITULO_APP, JOptionPane.QUESTION_MESSAGE);
            if (val == null) return null; // Cancelado
            if (!val.trim().isEmpty()) return val.trim();
            mostrarErrorVentana("El campo no puede quedar vacio.");
        }
    }

    private Integer pedirEnteroVentana(String mensaje) {
        while (true) {
            String val = JOptionPane.showInputDialog(null, mensaje, TITULO_APP, JOptionPane.QUESTION_MESSAGE);
            if (val == null) return null; // Cancelado
            try {
                return Integer.parseInt(val.trim());
            } catch (NumberFormatException e) {
                mostrarErrorVentana("Debe ingresar un valor numerico valido.");
            }
        }
    }

    private void mostrarTextoEnVentanaConScroll(String titulo, String texto) {
        JTextArea area = new JTextArea(texto);
        area.setEditable(false);
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(550, 320));
        JOptionPane.showMessageDialog(null, scroll, TITULO_APP + " - " + titulo, JOptionPane.PLAIN_MESSAGE);
    }

    private ArrayList<Turno> obtenerTodosLosTurnosReservados() {
        ArrayList<Turno> res = new ArrayList<>();
        for (Agenda a : gestor.getAgendas()) {
            for (Turno t : a.getTurnos()) {
                if (t.isTurnoReservado()) {
                    res.add(t);
                }
            }
        }
        return res;
    }

    private String formatearTurnoResumido(Turno t) {
        String cli = (t.getCliente() != null) ? t.getCliente().getApellido() + " " + t.getCliente().getNombre() : "S/C";
        String prof = (t.getProfesional() != null) ? t.getProfesional().getApellido() : "S/P";
        String srv = (t.getServicio() != null) ? t.getServicio().getServicio() : "S/S";
        return "Dia " + t.getDiaDelMes() + " a las " + t.getHora() + ":00 hs | Prof: " + prof + " | Cliente: " + cli + " | Servicio: " + srv;
    }

    private int obtenerIndiceSeleccionado(String[] arr, String valor) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(valor)) return i;
        }
        return 0;
    }
}