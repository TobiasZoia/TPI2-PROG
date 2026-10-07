public class Turno {
    
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
    
    public EstadoTurno getEstadoTurno(){
        return this.estadoTurno;
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
    
    public void setEstadoTurno(EstadoTurno estadoTurno){
        this.estadoTurno = estadoTurno;
    }
}