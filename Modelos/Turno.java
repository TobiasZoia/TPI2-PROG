package Modelos;

import Interfaces.EstadoGestionable;

public class Turno implements EstadoGestionable {
    
    private Cliente cliente;
    private Profesional profesional;
    private Servicio servicio;
    private int diaDelMes;
    private int hora;
    private EstadoTurno estadoTurno;
        
    public Turno(Cliente cliente,Profesional profesional, Servicio servicio,int diaDelMes, int hora){
        this.cliente = cliente;
        this.profesional = profesional;
        this.servicio = servicio;
        this.diaDelMes = diaDelMes;
        this.hora = hora;
        this.estadoTurno = EstadoTurno.RESERVADO;       
    }

    @Override
    public EstadoTurno getEstadoTurno(){
        return this.estadoTurno;
    }

    @Override
    public boolean isTurnoReservado() {
        return this.getEstadoTurno() == EstadoTurno.RESERVADO;
    }

    @Override
    public void setEstadoTurno(EstadoTurno estadoTurno){
        this.estadoTurno = estadoTurno;
    }

    @Override
    public void setEstadoCancelado () {
        this.estadoTurno = EstadoTurno.CANCELADO;
    }

    @Override
    public void setEstadoRealizado () {
        this.estadoTurno = EstadoTurno.REALIZADO;
    }

    @Override
    public boolean isDiaHoraEstadoReservado(int diaDelMes, int hora) {
        return this.getDiaDelMes() == diaDelMes && this.getHora() == hora && this.isTurnoReservado();
    }

    public Cliente getCliente(){
        return this.cliente;
    }
    
    public Profesional getProfesional(){
        return this.profesional;
    }
    
    public Servicio getServicio(){
        return this.servicio;
    }
    
    public int getDiaDelMes(){
        return this.diaDelMes;
    }
    
    public int getHora(){
        return this.hora;
    }

    public void setCliente(Cliente cliente){
        this.cliente = cliente;
    }
    
    public void setProfesional(Profesional profesional){
        this.profesional = profesional;
    }
    
    public void setServicio(Servicio servicio){
        this.servicio = servicio;
    }
    
    public void setDiaDelMes(int diaDelMes){
        this.diaDelMes = diaDelMes;
    }
    
    public void setHora(int hora){
        this.hora = hora;
    }

}