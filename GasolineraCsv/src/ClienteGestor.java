import java.util.ArrayList;
import java.util.List;

public class ClienteGestor {
    private final Almacenamiento<Cliente> clienteAlmacenamiento;
    private final List<Cliente> clientesEnMemoria;

    public ClienteGestor() {
        this.clienteAlmacenamiento = new ClienteCsvAlmacenamiento();
        clientesEnMemoria = cargarClientesEnMemoria();
    }

    private List<Cliente> cargarClientesEnMemoria() {
        return new ArrayList<>(clienteAlmacenamiento.obtenerTodos());
    }

    public Cliente registrarCliente(String nombre, String telefono, String matricula) {
        boolean clienteExiste = buscarPorMatricula(matricula) != null;

        if (clienteExiste) {
            return null;
        }

        int id = conseguirSiguienteId();
        Cliente cliente = new Cliente(id, nombre, telefono, matricula);

        clienteAlmacenamiento.guardar(cliente);
        clientesEnMemoria.add(cliente);

        return cliente;
    }

    private int conseguirSiguienteId() {
        int id = 1;
        for (Cliente c : clientesEnMemoria) {
            if (c.getID() > id) {
                id = c.getID();
            }
        }
        return id;
    }

    private Cliente buscarPorMatricula(String matricula) {
        for (Cliente c : clientesEnMemoria) {
            if (c.getMatricula().equals(matricula)) {
                return c;
            }
        }
        return null;
    }

    public List<Cliente> conseguirTodosClientes() {
        return clientesEnMemoria;
    }
}
