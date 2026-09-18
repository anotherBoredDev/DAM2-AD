import java.util.Scanner;

public class LectorConsola {
    private final Scanner scanner;

    public LectorConsola() {
        this.scanner = new Scanner(System.in);
    }

    public int leerEntero(String mensaje) {
        String mensajeFormateado = String.format("%s: ", mensaje);
        while (true) {
            System.out.print(mensajeFormateado);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número válido.");
            }
        }
    }

    public int leerEnteroEnRango(String mensaje, int valorMin, int valorMax) {
        String mensajeFormateado = String.format("%s (%d-%d)", mensaje, valorMin, valorMax);
        while (true) {
            int entero = leerEntero(mensajeFormateado);
            if (entero >= valorMin && entero <= valorMax) {
                return entero;
            }
            System.out.printf("Error: Debe introducir un número dentro del rango (%d-%d).%n", valorMin, valorMax);
        }
    }

    public String leerCadena(String mensaje) {
        String mensajeFormateado = String.format("%s: ", mensaje);
        while (true) {
            System.out.print(mensajeFormateado);
            String cadena = scanner.nextLine().trim();
            if (!cadena.isEmpty()) {
                return cadena;
            }
            System.out.println("Error: Debe ingresar una cadena no vacía.");

        }
    }


}
