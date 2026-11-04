package Interfaces;

import Modelos.EstadoTurno;

public interface EstadoGestionable {

    EstadoTurno getEstadoTurno();

    boolean isTurnoReservado();

    void setEstadoTurno(EstadoTurno estadoTurno);

    void setEstadoCancelado();

    void setEstadoRealizado();
}
