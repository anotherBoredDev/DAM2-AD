import java.util.Scanner;

public class LectorConsola {
    private final Scanner scanner;

    public LectorConsola() {
        this.scanner = new Scanner(System.in);
    }

    public int leerEntero(String mensaje) {
        int entero = -1;
        do {
            System.out.printf(mensaje);
            try {
                entero = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Formato de numero equivocado");
            }
        } while (entero == -1);
        return entero;
    }

    public String leerCadena(String mensaje) {
        String cadena;
        do {
            System.out.print(mensaje);
            cadena = scanner.nextLine();
        } while (cadena.isEmpty());
        return cadena;
    }


}
