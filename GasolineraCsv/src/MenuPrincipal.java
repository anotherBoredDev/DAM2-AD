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
            case 2 -> System.out.println("Listando clientes...");
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

        clienteGestor.registrarCliente(nombre, telefono, matricula);
    }
}
