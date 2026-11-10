package Interfaces;

import Modelos.EstadoTurno;
import Modelos.Turno;

public interface EstadoGestionable {

    EstadoTurno getEstadoTurno();

    boolean isTurnoReservado();

    boolean isTurnoCancelado();

    boolean isTurnoRealizado();

    void setEstadoTurno(EstadoTurno estadoTurno);

    void setEstadoCancelado();

    void setEstadoRealizado();

    boolean isDiaHoraEstadoReservado(int diaDelMes, int hora);

}
