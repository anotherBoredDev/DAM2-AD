public class Main {
    public static void main(String[] args) {
        Almacenamiento<Cliente> clienteAlmacenamiento = new ClienteCsvAlmacenamiento();
        Almacenamiento<Pago> pagoAlmacenamiento = new PagoCsvAlmacenamiento();

        ClienteGestor clienteGestor = new ClienteGestor(clienteAlmacenamiento);
        PagoGestor pagoGestor = new PagoGestor(pagoAlmacenamiento);
        LectorConsola lectorConsola = new LectorConsola();

        MenuPrincipal menuPrincipal = new MenuPrincipal(clienteGestor, pagoGestor, lectorConsola);

        menuPrincipal.iniciar();
    }
}