
public class Turno {
    private int codigoTurno;
    private Cliente cliente;
    private Profesional profesional;
    private Servicio servicio;
    private int hora;
    private EstadoTurno estadoTurno;
    
    
    public Turno(int codigoTurno,Cliente cliente,Profesional profesional,
    Servicio servicio,int hora, EstadoTurno estadoTurno){
        this.codigoTurno=codigoTurno;
        this.cliente=cliente;
        this.profesional=profesional;
        this.servicio=servicio;
        this.hora=hora;
        this.estadoTurno=estadoTurno;
        
    }
    
    }