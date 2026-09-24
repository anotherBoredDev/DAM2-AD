import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

    public float leerDecimal(String mensaje, int decimales) {
        String mensajeFormateado = String.format("%s (%d %s): ", mensaje, decimales, (decimales <= 1 ? "decimal" : "decimales"));
        while (true) {
            String numero = leerCadena(mensajeFormateado);
            numero = numero.replace(',', '.');

            int posicionDecimal = numero.indexOf('.');
            if (numero.substring(posicionDecimal).length() <= decimales) {
                return Float.parseFloat(numero);
            }

            System.out.printf("Error: Debe introducir un decimal con %d %s.%n", decimales, (decimales <= 1 ? "decimal" : "decimales"));
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

    public LocalDate leerFecha(String mensaje) {
        String mensajeFormateado = String.format("%s (dd/MM/aaaa): ", mensaje);
        while (true) {
            System.out.print(mensajeFormateado);
            String fechaCadena = scanner.nextLine().trim();
            if (fechaCadena.isEmpty()) {
                return LocalDate.now();
            }
            try {
                return LocalDate.parse(fechaCadena, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (DateTimeParseException e) {
                System.out.println("Error: Debe ingresar una fecha con el formato válido (dd/MM/aaaa).");
            }
        }
    }
}
