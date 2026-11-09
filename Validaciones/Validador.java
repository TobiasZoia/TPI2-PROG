package Validaciones;

import java.util.Scanner;

public abstract class Validador {

    public static boolean isDiaValido(int dia) {
        return dia >= 1 && dia <= 31;
    }

    public static boolean isHoraValida(int hora, int horarioApertura, int horarioCierre) {
        return hora >= horarioApertura && hora < horarioCierre;
    }

    public static String quitarEspacios(String cadena){
        return cadena.trim();
    }

    public static boolean cadenaVacia(String cadena){
        return cadena.trim().isEmpty();
    }

    public static boolean isNotNumero(String cadena){
        return !cadena.matches("\\d+");
    }

    public static boolean isNotCadena(String cadena){
        return !cadena.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$");
    }

    public static int leerEntero(Scanner scanner) {

        while (!scanner.hasNextInt()) {
            System.out.println("Entrada inválida. Debe ingresar un número entero.");
            scanner.nextLine();
        }
        int numero = scanner.nextInt();
        scanner.nextLine();
        return numero;
    }

    public static boolean codigoProfValido(int codigo){
        return codigo == 1 || codigo == 2;
    }

    public static boolean codigoServicioValido(int codigo){
        return codigo == 101 || codigo == 102;
    }

}
