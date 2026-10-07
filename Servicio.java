public class Servicio {
    
    private int codigoServicio;
    private String servicio;
    private int duracion;

    public Servicio(int codigoServicio, String servicio){        
        this.codigoServicio = codigoServicio;
        this.servicio = servicio;
        this.duracion = 1;        
    }
    
    public int getCodigoServicio(){
        return this.codigoServicio;
    }
    
    public String getServicio(){
        return this.servicio;
    }
    
    public int getDuracion(){
        return this.duracion;
    }
    
    public void setCodigoServicio(int codigoServicio){
        this.codigoServicio = codigoServicio;
    }
    
    public void setServicio(String servicio){
        this.servicio = servicio;
    }
    
    public void setDuracion(int duracion){
        this.duracion = duracion;
    }
}