package Vistas;

import Controladores.GestorPeluqueria;
import Modelos.Agenda;
import Modelos.Turno;
import javax.swing.JOptionPane;

public class VistaPrograma {

    private GestorPeluqueria gestor;

    public VistaPrograma(GestorPeluqueria gestor) {
        this.gestor = gestor;
    }

    public void iniciarMenu() {
        int opcion = 0;
        do {
            String menu = "=== MENÚ PELUQUERÍA ===\n"
                    + "1. Registrar nuevo turno\n"
                    + "2. Ver turnos registrados\n"
                    + "3. Salir\n"
                    + "Elija una opción:";

            String entrada = JOptionPane.showInputDialog(null, menu, "Peluquería", JOptionPane.QUESTION_MESSAGE);

            if (entrada == null) {
                break; // Si el usuario presiona "Cancelar" o cierra la ventana
            }

            try {
                opcion = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                opcion = 0;
            }

            switch (opcion) {
                case 1:
                    registrarNuevoTurno();
                    break;
                case 2:
                    mostrarTurnosRegistrados();
                    break;
                case 3:
                    JOptionPane.showMessageDialog(null, "Saliendo del sistema...");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción inválida.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } while (opcion != 3);
    }

    private void registrarNuevoTurno() {
        String diaStr = JOptionPane.showInputDialog(null, "Ingrese día del mes (1-31):", "Registrar Turno", JOptionPane.QUESTION_MESSAGE);
        if (diaStr == null) return;

        String horaStr = JOptionPane.showInputDialog(null, "Ingrese hora:", "Registrar Turno", JOptionPane.QUESTION_MESSAGE);
        if (horaStr == null) return;

        try {
            int dia = Integer.parseInt(diaStr);
            int hora = Integer.parseInt(horaStr);
            JOptionPane.showMessageDialog(null, "Día ingresado: " + dia + ", Hora: " + hora + " hs.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Debe ingresar números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarTurnosRegistrados() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== TURNOS REGISTRADOS ===\n\n");
        boolean hayTurnos = false;

        for (Agenda agenda : gestor.getAgendas()) {
            for (Turno t : agenda.getTurnos()) {
                sb.append("Día: ").append(t.getDiaDelMes())
                        .append(" - ").append(t.getHora()).append(" hs | ")
                        .append("Cliente: ").append(t.getCliente().getNombre()).append(" ").append(t.getCliente().getApellido())
                        .append(" | Profesional: ").append(t.getProfesional().getNombre()).append(" ").append(t.getProfesional().getApellido())
                        .append("\n");
                hayTurnos = true;
            }
        }

        if (!hayTurnos) {
            sb.append("No hay turnos registrados.");
        }

        JOptionPane.showMessageDialog(null, sb.toString(), "Listado de Turnos", JOptionPane.INFORMATION_MESSAGE);
    }

    // =========================================================================
    // MÉTODOS DE MENSAJES EN VENTANA (MIGRADOS DESDE AGENDA Y GESTOR)
    // =========================================================================

    public void mostrarMensajeTurnoRegistrado(int diaDelMes, int hora) {
        JOptionPane.showMessageDialog(null, "Turno registrado exitosamente el día " + diaDelMes + " a las " + hora + " hs.");
    }

    public void mostrarMensajeTurnoCancelado(Turno turno) {
        JOptionPane.showMessageDialog(null, "El turno del día " + turno.getDiaDelMes() + " a las " + turno.getHora() + " ha sido cancelado con éxito.");
    }

    public void mostrarMensajeTurnoReprogramado(int nuevoDia, int nuevaHora) {
        JOptionPane.showMessageDialog(null, "Turno reprogramado exitosamente para el día " + nuevoDia + " a las " + nuevaHora + " hs.");
    }

    public void mostrarErrorHorarioOcupado() {
        JOptionPane.showMessageDialog(null, "No se puede registrar el turno, horario ocupado.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarErrorTurnoInexistente() {
        JOptionPane.showMessageDialog(null, "Error: El turno especificado no existe.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarErrorEstadoTurno(Turno turno) {
        JOptionPane.showMessageDialog(null, "Error: No se puede operar un turno en estado " + turno.getEstadoTurno() + ".", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarErrorProfesionalNoDisponible() {
        JOptionPane.showMessageDialog(null, "Error: El profesional no está disponible en el nuevo día/horario seleccionado.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}