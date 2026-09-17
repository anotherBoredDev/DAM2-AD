public class ClienteGestor {
    private final ClienteAlmacenamiento clienteAlmacenamiento;

    public ClienteGestor() {
        this.clienteAlmacenamiento = new ClienteCsvAlmacenamiento();
    }

    public void registrarCliente(String nombre, String telefono, String matricula) {
        System.out.println("Registrando cliente " + nombre + " telefono: " + telefono + " matriucla: " + matricula);
        Cliente cliente = new Cliente(0, nombre, telefono, matricula);
        clienteAlmacenamiento.guardar(cliente);
    }
}
