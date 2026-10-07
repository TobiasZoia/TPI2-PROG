import java.util.List;
import java.util.ArrayList;

public class GestorPeluqueria {
    
    private int codigoPeluqueria;
    private String nombre;
    private int horarioApertura;
    private int horarioCierre;
    private List<Cliente> clientes = new ArrayList<>();
    private List<Profesional> profesionales = new ArrayList<>();
    private List<Servicio> servicios = new ArrayList<>();
    private List<Agenda> agendas = new ArrayList<>();

    public GestorPeluqueria(int codigoPeluqueria, String nombre, int horarioApertura, int horarioCierre){
        this.codigoPeluqueria = codigoPeluqueria;
        this.nombre = nombre;
        this.horarioApertura = horarioApertura;
        this.horarioCierre = horarioCierre;
    }
    
    public void agregarCliente (Cliente cliente){
        this.clientes.add(cliente);        
    }
    
    public void agregarProfesional (Profesional profesional){
        this.profesionales.add(profesional);        
    }
    
    public void agregarServicio (Servicio servicio){
        this.servicios.add(servicio);        
    }
    
    public void agregarAgenda (Agenda agenda){
        this.agendas.add(agenda);        
    }
    
    public int getCodigoPeluqueria(){
        return this.codigoPeluqueria;
    }
    
    public String getNombre(){
        return this.nombre;
    }
    
    public int getHorarioApertura(){
        return this.horarioApertura;
    }
    
    public int getHorarioCierre(){
        return this.horarioCierre;
    }
    
    public void setCodigoPeluqueria (int codigoPeluqueria){
        this.codigoPeluqueria = codigoPeluqueria;
    }
    
    public void setNombre (String nombre){
        this.nombre = nombre;
    }
    
    public void serHorarioApertura (int horarioApertura){
        this.horarioApertura = horarioApertura;
    }
    
    public void serHorarioCierre (int horarioCierre){
        this.horarioCierre = horarioCierre;
    }
    
    public boolean esDiaValido(int dia){
        return dia >= 1 && dia <= 31;
    }
    
    public boolean esHoraValida(int hora){
        return hora >= this.horarioApertura && hora < this.horarioCierre;
    }
}