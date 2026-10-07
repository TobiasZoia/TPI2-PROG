import java.util.ArrayList;

public class Agenda {

    private int codigoAgenda;
    private int diaDelMes;
    private Profesional profesional;
    private ArrayList<Turno> turnos = new ArrayList<>();

    public Agenda(int codigoAgenda, int diaDelMes){
        this.codigoAgenda = codigoAgenda;
        this.diaDelMes = diaDelMes;        
    }
    
    public void agregarTurno (Turno turno){
        this.turnos.add(turno);        
    }
    
    public int getCodigoAgenda (){
        return this.codigoAgenda;
    }
    
    public int getDiaDelMes(){
        return this.diaDelMes;
    }
    
    public Profesional getProfesional(){
        return this.profesional;
    }
    
    public ArrayList<Turno> getTurnos(){
        return this.turnos;
    }
    
    public void setCodigoAgenda(int codigoAgenda){
        this.codigoAgenda = codigoAgenda;
    }
    
    public void setDiaDelMes(int diaDelMes){
        this.diaDelMes = diaDelMes;
    }
    
    public void setProfesional(Profesional profesional){
        this.profesional = profesional;
    }
    
    public boolean isOcupado(int diaDelMes, int hora){
        for (Turno t : turnos){
            return (t.getDiaDelMes() == diaDelMes && t.getHora() == hora && t.getEstadoTurno() == EstadoTurno.RESERVADO);
        }
        return false;
    }
}