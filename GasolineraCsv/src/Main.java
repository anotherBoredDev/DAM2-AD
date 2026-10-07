import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Almacenamiento<Cliente> clienteAlmacenamiento = new ClienteJsonAlmacenamiento();
        Almacenamiento<Pago> pagoAlmacenamiento = new PagoJsonAlmacenamiento();

        ClienteGestor clienteGestor = new ClienteGestor(clienteAlmacenamiento);
        PagoGestor pagoGestor = new PagoGestor(pagoAlmacenamiento);

        try (Scanner scanner = new Scanner(System.in)) {
            LectorConsola lectorConsola = new LectorConsola(scanner);

            MenuPrincipal menuPrincipal = new MenuPrincipal(clienteGestor, pagoGestor, lectorConsola);
            menuPrincipal.iniciar();
        }
    }
}