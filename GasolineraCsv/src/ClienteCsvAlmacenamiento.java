import java.util.ArrayList;
import java.util.List;

public class ClienteCsvAlmacenamiento implements ClienteAlmacenamiento {
    private final List<Cliente> listadoClientes;
    private int ultimoId;

    public ClienteCsvAlmacenamiento() {
        this.listadoClientes = cargarDesdeFichero();
        this.ultimoId = conseguirUltimoIdInicial();
    }

    private List<Cliente> cargarDesdeFichero() {
        // logica aburrida
        return new ArrayList<>();
    }

    private int conseguirUltimoIdInicial() {
        int ultimoIdCliente = 1;
        for (Cliente c : obtenerTodos()) {
            if (c.getID() > ultimoIdCliente) ultimoIdCliente = c.getID();
        }
        return ultimoIdCliente;
    }

    @Override
    public int conseguirSiguienteId() {
        return ++this.ultimoId;
    }

    @Override
    public Cliente buscarPorMatricula(String matricula) {
        // lógica búsqueda real
        for (Cliente c : listadoClientes) {
            if (c.getMatricula().equals(matricula)) {
                return c;
            }
        }
        return null;
    }

    @Override
    public void guardar(Cliente entidad) {
        // lógica guardar en fichero
        listadoClientes.add(entidad);
    }

    @Override
    public List<Cliente> obtenerTodos() {
        // lógica conseguir todos los clientes desde fichero
        return listadoClientes;
    }

    @Override
    public Cliente buscarPorId(Integer id) {
        // lógica búsqueda real
        for (Cliente c : listadoClientes) {
            if (c.getID() == id) {
                return c;
            }
        }
        return null;
    }
}
