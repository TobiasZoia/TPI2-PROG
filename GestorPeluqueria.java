import java.util.ArrayList;

public class GestorPeluqueria {
    
    private int codigoPeluqueria;
    private String nombre;
    private int horarioApertura;
    private int horarioCierre;
    private ArrayList<Cliente> clientes = new ArrayList<>();
    private ArrayList<Profesional> profesionales = new ArrayList<>();
    private ArrayList<Servicio> servicios = new ArrayList<>();
    private ArrayList<Agenda> agendas = new ArrayList<>();

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
    
    public ArrayList<Cliente> getClientes(){
        return this.clientes;
    }
    
    public ArrayList<Profesional> getProfesionales(){
        return this.profesionales;
    }
    
    public ArrayList<Servicio> getServicios(){
        return this.servicios;
    }
    
    public ArrayList<Agenda> getAgendas(){
        return this.agendas;
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
    
    public boolean isDiaValido(int dia){
        return dia >= 1 && dia <= 31;
    }
    
    public boolean isHoraValida(int hora){
        return hora >= this.horarioApertura && hora < this.horarioCierre;
    }
    
    public Agenda buscarAgenda(Profesional profesional, int diaDelMes){
        for (Agenda a : agendas){
            if (a.getProfesional().equals(profesional) && a.getDiaDelMes() == diaDelMes){
                return a;
            }
        }
        return null;
    }
    
    public boolean isAgendaProfesionalDisponible (Profesional profesional, int diaDelMes, int hora){
        if (!this.isDiaValido(diaDelMes) || !this.isHoraValida(hora)){
            return false;
        }
        
        Agenda agenda = this.buscarAgenda(profesional, diaDelMes);
        
        if (agenda == null){
            return true;
        }
        
        return !agenda.isAgendaOcupada(diaDelMes, hora);
    }
    
    public String mensajeHorarioOcupado(){
        return ("No se puede registrar el turno, horario ocupado.");
    }
    
    public String mensajeTurnoRegistradoCorrectamente(int diaDelMes, int hora){
        return ("Turno registrado exitosamente el día " + diaDelMes + " a las " + hora + " hs.");
    }

    public boolean registrarTurno(Cliente cliente, Profesional profesional, Servicio servicio, int diaDelMes, int hora){
        if (!this.isAgendaProfesionalDisponible(profesional, diaDelMes, hora)){
            this.mensajeHorarioOcupado();
            return false;
        }
        
        Agenda agenda = this.buscarAgenda(profesional, diaDelMes);
        
        if (agenda == null){
            agenda = new Agenda(profesional, diaDelMes);
            this.agregarAgenda(agenda);
        }
        
        Turno nuevoTurno = new Turno (cliente, profesional, servicio, diaDelMes, hora);
        
        agenda.agregarTurno(nuevoTurno);
        
        this.mensajeTurnoRegistradoCorrectamente(diaDelMes, hora);
                
        return true;
    }
    
    public String mensajeTurnoInexistente(){
        return ("Error: El turno especificado no existe");
    
    }
    
    public String mensajeTurnoCancelado(Turno turno){
        return (" El turno del dia " + turno.getDiaDelMes() + " a las " + turno.getHora() + " Ha sido cancelado con exito. ");
    }
    
    public String mensajeErrorEstadoTurno(Turno turno){
        return (" Error: No se puede cancelar un turno en estado " + turno.getEstadoTurno() + ".");
    }
    
    public boolean cancelarTurno(Turno turno) {
        if (turno == null) {
            this.mensajeTurnoInexistente();
            return false;
        }

        if (turno.getEstadoTurno() == EstadoTurno.RESERVADO) {
            turno.setEstadoTurno(EstadoTurno.CANCELADO);
            this.mensajeTurnoCancelado(turno);
            return true;
        }

        this.mensajeErrorEstadoTurno(turno);
        return false;
    }
}