package Controladores;
import Modelos.Cliente;
import Modelos.Profesional;
import Modelos.Servicio;
import Modelos.Agenda;
import Modelos.Turno;
import java.util.ArrayList;

import Validaciones.Validador;

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
    
    public void setHorarioApertura (int horarioApertura){
        this.horarioApertura = horarioApertura;
    }
    
    public void setHorarioCierre (int horarioCierre){
        this.horarioCierre = horarioCierre;
    }

    public Cliente crearCliente(String dni,String nombre,String apellido,String telefono){
        return new Cliente (dni, nombre, apellido, telefono);
    }

    public boolean isDiaValido(int dia){
        return Validador.isDiaValido(dia);
    }

    public boolean isHoraValida(int hora){
        return Validador.isHoraValida(hora, this.horarioApertura, this.horarioCierre);
    }

    public Agenda buscarAgenda(Profesional profesional, int diaDelMes){
        for (Agenda a : agendas){
            if (a.coincidenciaProfesional(profesional) && a.coincidenciaDia(diaDelMes)){
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

    public boolean registrarTurnoEnAgenda (Cliente cliente, Profesional profesional, Servicio servicio, int diaDelMes, int hora){
        if (!this.isAgendaProfesionalDisponible(profesional, diaDelMes, hora)){
            return false;
        }
        
        Agenda agenda = this.buscarAgenda(profesional, diaDelMes);
        
        if (agenda == null){
            agenda = new Agenda(profesional, diaDelMes);
            this.agregarAgenda(agenda);
        }
        
        agenda.registrarTurno (cliente, profesional, servicio, diaDelMes, hora);
                           
        return true;
    }

    public boolean reprogramarTurno(Turno turno, int nuevoDia, int nuevaHora){
        if (turno == null){
            return false;
            }
    
        if (!turno.isTurnoReservado()){
            return false;
        }
    
        if (!this.isAgendaProfesionalDisponible(turno.getProfesional(), nuevoDia, nuevaHora)) {
            return false;
        }
    
        int diaOrigen = turno.getDiaDelMes();
    
        if (diaOrigen != nuevoDia) {
            Agenda agendaVieja = buscarAgenda(turno.getProfesional(), diaOrigen);
            if (agendaVieja != null) {
                agendaVieja.quitarTurno(turno);
            }
    
            Agenda agendaNueva = buscarAgenda(turno.getProfesional(), nuevoDia);
            if (agendaNueva == null) {
                agendaNueva = new Agenda(turno.getProfesional(), nuevoDia);
                agendas.add(agendaNueva);
            }
    
            agendaNueva.agregarTurno(turno);
        }
    
        turno.setDiaDelMes(nuevoDia);
        turno.setHora(nuevaHora);
        return true;
    }

    public Cliente buscarClientePorDni(String dni){
        for (Cliente c : clientes){
            if (c.getDni().equals(dni)){
                return c;
            }
        }
        return null;
    }
}