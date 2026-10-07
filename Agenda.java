import java.util.ArrayList;
import java.util.List;

public class Agenda {

    private int codigoAgenda;
    private String fecha;
    private List<Turno> turnos = new ArrayList<>();

    public Agenda(int codigoAgenda, String fecha){
        this.codigoAgenda = codigoAgenda;
        this.fecha = fecha;        
    }
    
    public void agregarTurno (Turno turno){
        this.turnos.add(turno);        
    }
    
    public int getCodigoAgenda (){
        return this.codigoAgenda;
    }
    
    public String getFecha(){
        return this.fecha;
    }
    
    public void setCodigoAgenda(int codigoAgenda){
        this.codigoAgenda = codigoAgenda;
    }
    
    public void setFecha (String fecha){
        this.fecha = fecha;
    }
}