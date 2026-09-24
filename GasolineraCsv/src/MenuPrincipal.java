import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    private void imprimirSeparador() {
        System.out.println("-".repeat(20));
    }

    private void imprimirListadoClientes(List<Cliente> listadoClientes) {
        Collections.sort(listadoClientes);

        int longitudId = 6;
        int longitudNombre = 15;
        int longitudTelefono = 15;

        System.out.println("ID     NOMBRE          TELÉFONO        MATRICULA");
        for (Cliente c : listadoClientes) {
            String id = c.getId() + " ".repeat(Math.max(0, longitudId - String.valueOf(c.getId()).length()));
            String nombre = c.getNombre() + " ".repeat(Math.max(0, longitudNombre - c.getNombre().length()));
            String telefono = c.getTelefono() + " ".repeat(Math.max(0, longitudTelefono - c.getTelefono().length()));
            String matricula = c.getMatricula();

            System.out.printf("%s %s %s %s%n", id, nombre, telefono, matricula);
        }
    }

    private void seleccionarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> darAltaCliente();
            case 2 -> listarTodosClientes();
            case 3 -> buscarCliente();
            case 4 -> procesarPago();
            case 5 -> consultarPagos();
            case 0 -> { }
            default -> System.out.println("Opción inválida");
        }
    }

    private void darAltaCliente() {
        String nombre = lectorConsola.leerCadena("Nombre");
        String telefono = lectorConsola.leerCadena("Teléfono");
        String matricula = lectorConsola.leerCadena("Matricula");

        Cliente clienteRegistrado = clienteGestor.registrarCliente(nombre, telefono, matricula);

        imprimirSeparador();
        if (clienteRegistrado == null) {
            System.out.println("Error: Cliente no se ha podido registrar.");
        } else {
            String mensaje = String.format("Cliente con id %d ha sido registrado correctamente.", clienteRegistrado.getId());
            System.out.println(mensaje);
        }
        imprimirSeparador();
    }

    private void listarTodosClientes() {
        List<Cliente> listadoClientes = clienteGestor.conseguirTodosClientes();

        imprimirSeparador();
        if (listadoClientes.isEmpty()) {
            System.out.println("No hay ningún cliente registrado.");
        } else {
            imprimirListadoClientes(listadoClientes);
        }
        imprimirSeparador();

    }

    private void buscarCliente() {
        String busqueda = lectorConsola.leerCadena("Texto que buscar");
        List<Cliente> coincidencias = clienteGestor.buscarClienteCualquierCoincidencia(busqueda);

        imprimirSeparador();
        if (coincidencias.isEmpty()) {
            System.out.println("No hay ningún cliente que coincida con la búsqueda '" + busqueda + "'.");
        } else {
            imprimirListadoClientes(coincidencias);
        }
        imprimirSeparador();
    }

    private void procesarPago() {
        if (clienteGestor.conseguirTodosClientes().isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }

        listarTodosClientes();
        int idCliente = lectorConsola.leerEntero("Introduzca el id del cliente");

        if (!clienteGestor.existeClientePorId(idCliente)) {
            System.out.println("El cliente con el id introducido no existe.");
            return;
        }

        LocalDate fecha = lectorConsola.leerFecha("Introduzca la fecha del pago");
        float importe = lectorConsola.leerDecimal("Introduzca el importe", 2);
        int litros = lectorConsola.leerEntero("Introduzca los litros");
        String combustible;

        System.out.println("Tipos de combustible:");
        System.out.println("1. Gasolina 95");
        System.out.println("2. Diésel");
        int indiceCombustible = lectorConsola.leerEnteroEnRango("Introduzca el tipo de combustible", 1, 2);

        if (indiceCombustible == 1) {
            combustible = "Gasolina 95";
        } else if (indiceCombustible == 2 ){
            combustible = "Diésel";
        } else {
            combustible = "Desconocido";
        }

        Pago pagoRegistrado = pagoGestor.registrarPago(idCliente, fecha, importe, litros, combustible);

        imprimirSeparador();
        if (pagoRegistrado == null) {
            System.out.println("Error: El pago no se ha podido procesar");
        } else {
            String mensaje = String.format("Pago con id %d, a nombre del cliente %s y con un importe de %.2f€ ha sido registrado correctamente.",
                    pagoRegistrado.getId(),
                    clienteGestor.buscarClientePorId(idCliente).getNombre(),
                    pagoRegistrado.getImporte());
            System.out.println(mensaje);
        }
        imprimirSeparador();
    }

    private void consultarPagos() {
        List<Pago> listadoPagos = new ArrayList<>(pagoGestor.conseguirTodosPagos());
        Collections.sort(listadoPagos);

        int longitudId = 6;
        int longitudIdCliente = 12;
        int longitudFecha = 12;
        int longitudImporte = 8;
        int longitudLitros = 10;

        imprimirSeparador();
        System.out.println("ID     ID CLIENTE   FECHA        IMPORTE    LITROS     COMBUSTIBLE");
        for (Pago p : listadoPagos) {
            String id = p.getId() + " ".repeat(Math.max(0, longitudId - String.valueOf(p.getId()).length()));
            String idCliente = p.getIdCliente() + " ".repeat(Math.max(0, longitudIdCliente - String.valueOf(p.getIdCliente()).length()));
            String fecha = p.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " ".repeat(Math.max(0, longitudFecha - p.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).length()));
            String importe = String.format("%.2f", p.getImporte()) + " ".repeat(Math.max(0, longitudImporte - String.valueOf(p.getImporte()).length()));
            String litros = p.getLitros() + " ".repeat(Math.max(0, longitudLitros - String.valueOf(p.getLitros()).length()));
            String combustible = p.getCombustible();

            System.out.printf("%s %s %s %s %s %s%n", id, idCliente, fecha, importe, litros, combustible);
        }
        imprimirSeparador();
    }
}
