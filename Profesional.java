public class Profesional {
    
    private String dni;
    private String nombre;
    private String apellido;
    private String telefono;
    
    public Profesional(String dni,String nombre,String apellido,String telefono){
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;        
    }
    

    public String getDni(){
        return this.dni;
    }
    
    public String getNombre(){
        return this.nombre;
    }

    public String getApellido(){
        return this.apellido;
    }

    public String getTelefono(){
        return this.telefono;
    }

    public void setDni(String dni){
        this.dni = dni;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setApellido(String apellido){
        this.apellido = apellido;
    }

    public void setTelefono(String telefono){
        this.telefono = telefono;
    }
}