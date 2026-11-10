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

    public static String toCapitalize(String texto) {
        String capitalizado = texto.trim();
        return capitalizado.substring(0, 1).toUpperCase() + capitalizado.substring(1).toLowerCase();
    }

}
