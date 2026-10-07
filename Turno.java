
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
    
        public int getCodigoTurno(){
        return this.codigoTurno=codigoTurno;
    }
    public Cliente getCliente(){
        return this.cliente=cliente;
    }
    public Profesional getProfesional(){
        return this.profesional=profesional;
    }
    public Servicio getServicio(){
        return this.servicio=servicio;
    }
    public int getHora(){
        return this.hora=hora;
    }
    public EstadoTurno getEstadoTurno(){
        return this.estadoTurno=estadoTurno;
    }
    public void setCodigoTurno(int codigoTurno){
        this.codigoTurno=codigoTurno;
    }
    public void setCliente(Cliente cliente){
        this.cliente=cliente;
    }
    public void setProfesional(Profesional profesional){
        this.profesional=profesional;
    }
    public void setServicio(Servicio servicio){
        this.servicio=servicio;
    }
    public void setHora(int hora){
        this.hora=hora;
    }
    public void setEstadoTurno(EstadoTurno estadoTurno){
        this.estadoTurno=estadoTurno;
    }
    }
    
    
