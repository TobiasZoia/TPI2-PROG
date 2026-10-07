import java.util.List;
import java.util.ArrayList;

public class GestorPeluqueria {
    
    private int codigoPeluqueria;
    private String nombre;
    private List<Cliente> clientes = new ArrayList<>();
    private List<Profesional> profesionales = new ArrayList<>();
    private List<Servicio> servicios = new ArrayList<>();
    private List<Agenda> agendas = new ArrayList<>();

    public GestorPeluqueria(int codigoPeluqueria, String nombre){
        this.codigoPeluqueria = codigoPeluqueria;
        this.nombre = nombre;        
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
    
    public void setCodigoPeluqueria (int codigoPeluqueria){
        this.codigoPeluqueria = codigoPeluqueria;
    }
    
    public void setNombre (String nombre){
        this.nombre = nombre;
    }
}