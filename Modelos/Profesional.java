package Modelos;

public class Profesional extends Persona {

    private int codProf;

    public Profesional(String dni,String nombre,String apellido,String telefono, int codProf){
        super (dni,  nombre, apellido, telefono);
        this.codProf = codProf;

    }

    public int getCodProf() {
        return codProf;
    }

    public void setCodProf(int codProf) {
        this.codProf = codProf;
    }

}