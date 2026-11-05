package Modelos;

import java.util.ArrayList;

public class Agenda {

    private Profesional profesional;
    private int diaDelMes;
    private ArrayList<Turno> turnos = new ArrayList<>();

    public Agenda(Profesional profesional, int diaDelMes){
        this.profesional = profesional;
        this.diaDelMes = diaDelMes;
    }
    
    public void agregarTurno (Turno turno){
        this.turnos.add(turno);        
    }
    
    public Profesional getProfesional(){
        return this.profesional;
    }
    
    public int getDiaDelMes(){
        return this.diaDelMes;
    }
    
    public ArrayList<Turno> getTurnos(){
        return this.turnos;
    }
    
    public void setProfesional(Profesional profesional){
        this.profesional = profesional;
    }
    
    public void setDiaDelMes(int diaDelMes){
        this.diaDelMes = diaDelMes;
    }
    
    public boolean isAgendaOcupada(int diaDelMes, int hora){
        for (Turno t : turnos){
            if (t.getDiaDelMes() == diaDelMes && t.getHora() == hora && t.isTurnoReservado()){
                return true;
            }
        }
        return false;
    }
    
    public boolean coincidenciaDia(int diaDelMes){
        return this.getDiaDelMes() == diaDelMes;
    }
    
    public boolean coincidenciaProfesional(Profesional profesional){
        return this.getProfesional().equals(profesional);
    }

    public Turno registrarTurno (Cliente cliente, Profesional profesional, Servicio servicio, int diaDelMes, int hora){
        Turno nuevoTurno = new Turno(cliente, profesional, servicio, diaDelMes, hora);

        this.agregarTurno(nuevoTurno);

        return nuevoTurno;
    }

    public boolean cancelarTurno(Turno turno) {
        if (turno == null) {
            return false;
        }

        if (turno.isTurnoReservado()) {
            turno.setEstadoCancelado();
            return true;
        }

        return false;
    }
    
    public void quitarTurno(Turno turno){
        this.turnos.remove(turno);
    }    
}