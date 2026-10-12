import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            LectorConsola lectorConsola = new LectorConsola(scanner);

            Almacenamiento<Cliente> clienteAlmacenamiento = new ClienteJsonAlmacenamiento("datos");
            Almacenamiento<Pago> pagoAlmacenamiento = new PagoJsonAlmacenamiento("datos");

            if (!clienteAlmacenamiento.existeFicheroAlmacenamiento() || !pagoAlmacenamiento.existeFicheroAlmacenamiento()) {
                realizarMigracion(lectorConsola);
            }

            ClienteGestor clienteGestor = new ClienteGestor(clienteAlmacenamiento);
            PagoGestor pagoGestor = new PagoGestor(pagoAlmacenamiento);

            MenuPrincipal menuPrincipal = new MenuPrincipal(clienteGestor, pagoGestor, lectorConsola);
            menuPrincipal.iniciar();
        }
    }

    public static void realizarMigracion(LectorConsola lectorConsola) {
        String entrada = lectorConsola.leerCadena("¿Desea realizar una migración de Csv a Json? (S/n)");
        while (!entrada.equalsIgnoreCase("s") && !entrada.equalsIgnoreCase("n")) {
            entrada = lectorConsola.leerCadena("¿Desea realizar una migración de Csv a Json? (S/n)");
        }

        if (entrada.equalsIgnoreCase("s")) {
            String directorio = lectorConsola.leerCadena("Directorio de los datos CSV");
            MigracionCsvToJson migracionCsvToJson = new MigracionCsvToJson(directorio);

            ResultadoMigracion resultado = migracionCsvToJson.migrar();

            System.out.println("Clientes migrados: " + resultado.clientesMigrados());
            System.out.println("Pagos migrados: " + resultado.pagosMigrados());
        }
    }
}