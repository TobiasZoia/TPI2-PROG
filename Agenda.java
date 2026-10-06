import java.util.ArrayList;
import java.util.List;

public class Agenda
{

    private int codigoAgenda;
    private String fecha;
    private List<Turno> turnos = new ArrayList<>();

    public Agenda(int codigoAgenda, String fecha)
    {
        this.codigoAgenda = codigoAgenda;
        this.fecha = fecha;
        
    }
    
    public void agregarTurno (Turno turno){
        this.turnos.add(turno);
        
    }

}