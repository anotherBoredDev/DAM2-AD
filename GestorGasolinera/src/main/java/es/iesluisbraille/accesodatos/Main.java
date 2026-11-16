package es.iesluisbraille.accesodatos;

import es.iesluisbraille.accesodatos.almacenamiento.Almacenamiento;
import es.iesluisbraille.accesodatos.almacenamiento.json.ClienteJsonAlmacenamiento;
import es.iesluisbraille.accesodatos.almacenamiento.mysql.ClienteMySqlAlmacenamiento;
import es.iesluisbraille.accesodatos.almacenamiento.json.PagoJsonAlmacenamiento;
import es.iesluisbraille.accesodatos.cli.LectorConsola;
import es.iesluisbraille.accesodatos.cli.MenuPrincipal;
import es.iesluisbraille.accesodatos.entidad.Cliente;
import es.iesluisbraille.accesodatos.entidad.Pago;
import es.iesluisbraille.accesodatos.gestor.ClienteGestor;
import es.iesluisbraille.accesodatos.gestor.PagoGestor;
import es.iesluisbraille.accesodatos.migracion.MigracionCsvToJson;
import es.iesluisbraille.accesodatos.migracion.ResultadoMigracion;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            LectorConsola lectorConsola = new LectorConsola(scanner);

            Almacenamiento<Cliente> clienteAlmacenamiento = new ClienteMySqlAlmacenamiento();
            Almacenamiento<Pago> pagoAlmacenamiento = new PagoJsonAlmacenamiento("datos");

            if (clienteAlmacenamiento.getClass() == ClienteJsonAlmacenamiento.class
                    && pagoAlmacenamiento.getClass() == PagoJsonAlmacenamiento.class) {
                if (!clienteAlmacenamiento.existeAlmacenamiento() || !pagoAlmacenamiento.existeAlmacenamiento()) {
                    realizarMigracion(lectorConsola);
                }
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