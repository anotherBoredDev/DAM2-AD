import java.util.List;

public class ClienteCsvAlmacenamiento implements ClienteAlmacenamiento {
    @Override
    public Cliente buscarPorMatricula(String matricula) {
        return null;
    }

    @Override
    public void guardar(Cliente entidad) {

    }

    @Override
    public List<Cliente> obtenerTodos() {
        return List.of();
    }

    @Override
    public Cliente buscarPorId(Integer integer) {
        return null;
    }
}
