package Validaciones;

public class Validador {

    // BOOLEANO DE SI EL DIA ES VALIDO
    public static boolean isDiaValido(int dia) {
        return dia >= 1 && dia <= 31;
    }

    // BOOLEANDO DE SI LA HORA ES VALIDA
    public static boolean isHoraValida(int hora, int horarioApertura, int horarioCierre) {
        return hora >= horarioApertura && hora < horarioCierre;
    }
}
