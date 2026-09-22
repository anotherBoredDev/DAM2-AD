import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MenuPrincipal {
    private final ClienteGestor clienteGestor;
    private final PagoGestor pagoGestor;
    private final LectorConsola lectorConsola;

    public MenuPrincipal(ClienteGestor clienteGestor, PagoGestor pagoGestor, LectorConsola lectorConsola) {
        this.clienteGestor = clienteGestor;
        this.pagoGestor = pagoGestor;
        this.lectorConsola = lectorConsola;
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarMenu();
            opcion = lectorConsola.leerEnteroEnRango("Opción", 0, 5);
            seleccionarOpcion(opcion);
        } while (opcion != 0);
    }

    private void mostrarMenu() {
        System.out.println("=== GESTIÓN DE GASOLINERA ===");
        System.out.println("1. Dar de alta un cliente");
        System.out.println("2. Listar clientes");
        System.out.println("3. Buscar clientes");
        System.out.println("4. Procesar un pago de repostaje");
        System.out.println("5. Consultar pagos");
        System.out.println("0. Salir");
    }

    private void seleccionarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> darAltaCliente();
            case 2 -> listarClientes();
            case 3 -> System.out.println("Buscando clientes...");
            case 4 -> System.out.println("Procesando un pago de repostaje...");
            case 5 -> System.out.println("Consultando pagos...");
            case 0 -> { }
            default -> System.out.println("Opción inválida");
        }
    }

    private void darAltaCliente() {
        String nombre = lectorConsola.leerCadena("Nombre");
        String telefono = lectorConsola.leerCadena("Teléfono");
        String matricula = lectorConsola.leerCadena("Matricula");

        Cliente clienteRegistrado = clienteGestor.registrarCliente(nombre, telefono, matricula);

        if (clienteRegistrado == null) {
            System.out.println("Error: Cliente no se ha podido registrar.");
        } else {
            String mensaje = String.format("Cliente con id %d ha sido registrado correctamente.", clienteRegistrado.getID());
            System.out.println(mensaje);
        }
    }

    private void listarClientes() {
        List<Cliente> listadoClientes = clienteGestor.conseguirTodosClientes();
        Collections.sort(listadoClientes);

        int longitudId = 6;
        int longitudNombre = 15;
        int longitudTelefono = 15;

        if (listadoClientes.isEmpty()) {
            System.out.println("No hay ningún cliente registrado.");
        } else {
            System.out.println("ID     NOMBRE          TELÉFONO        MATRICULA");
            for (Cliente c : listadoClientes) {
                String id = c.getID() + " ".repeat(Math.max(0, longitudId - String.valueOf(c.getID()).length()));
                String nombre = c.getNombre() + " ".repeat(Math.max(0, longitudNombre - c.getNombre().length()));
                String telefono = c.getTelefono() + " ".repeat(Math.max(0, longitudTelefono - c.getTelefono().length()));
                String matricula = c.getMatricula();

                System.out.printf("%s %s %s %s%n", id, nombre, telefono, matricula);

            }
        }

    }
}
