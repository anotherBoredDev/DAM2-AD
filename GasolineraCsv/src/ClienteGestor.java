public class ClienteGestor {
    private final ClienteAlmacenamiento clienteAlmacenamiento;

    public ClienteGestor() {
        this.clienteAlmacenamiento = new ClienteCsvAlmacenamiento();
    }

    public void registrarCliente(String nombre, String telefono, String matricula) {
        boolean clienteExiste = clienteAlmacenamiento.buscarPorMatricula(matricula) != null;

        if (clienteExiste) {
            System.out.println("Error: Cliente ya existe.");
            return;
        }

        int id = clienteAlmacenamiento.conseguirSiguienteId();
        Cliente cliente = new Cliente(id, nombre, telefono, matricula);

        clienteAlmacenamiento.guardar(cliente);

        System.out.println("Cliente con id " + id + " registrado.");
    }
}
