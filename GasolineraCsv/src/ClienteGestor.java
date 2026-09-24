import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ClienteGestor {
    private final Almacenamiento<Cliente> clienteAlmacenamiento;
    private final List<Cliente> clientesEnMemoria;

    public ClienteGestor(Almacenamiento<Cliente> clienteAlmacenamiento) {
        this.clienteAlmacenamiento = clienteAlmacenamiento;
        this.clientesEnMemoria = cargarClientesEnMemoria();
    }

    private List<Cliente> cargarClientesEnMemoria() {
        return new ArrayList<>(clienteAlmacenamiento.obtenerTodos());
    }

    public Cliente registrarCliente(String nombre, String telefono, String matricula) {
        matricula = matricula.toUpperCase(Locale.ROOT);
        boolean clienteExiste = existeClientePorMatricula(matricula);

        if (clienteExiste) {
            return null;
        }

        int id = conseguirSiguienteId();
        Cliente cliente = new Cliente(id, nombre, telefono, matricula);

        clienteAlmacenamiento.guardar(cliente);
        clientesEnMemoria.add(cliente);

        return cliente;
    }

    public List<Cliente> conseguirTodosClientes() {
        return clientesEnMemoria;
    }

    public List<Cliente> buscarClienteCualquierCoincidencia(String busqueda) {
        List<Cliente> coincidencias = new ArrayList<>();
        busqueda = busqueda.toUpperCase(Locale.ROOT);

        for (Cliente c : clientesEnMemoria) {
            boolean encontrado = c.getNombre().toUpperCase(Locale.ROOT).contains(busqueda) || c.getTelefono().toUpperCase(Locale.ROOT).contains(busqueda) || c.getMatricula().toUpperCase(Locale.ROOT).contains(busqueda);
            if (encontrado) {
                coincidencias.add(c);
            }
        }

        return coincidencias;
    }

    private int conseguirSiguienteId() {
        int id = 0;
        for (Cliente c : clientesEnMemoria) {
            if (c.getId() >= id) {
                id = c.getId();
            }
        }
        return id + 1;
    }

    public boolean existeClientePorId(int id) {
        for (Cliente c : clientesEnMemoria) {
            if (c.getId() == id) {
                return true;
            }
        }
        return false;
    }

    private boolean existeClientePorMatricula(String matricula) {
        for (Cliente c : clientesEnMemoria) {
            if (c.getMatricula().equals(matricula)) {
                return true;
            }
        }
        return false;
    }


}
