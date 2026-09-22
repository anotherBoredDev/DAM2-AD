import java.util.Collection;
import java.util.List;

public class ClienteCsvAlmacenamiento implements Almacenamiento<Cliente> {

    @Override
    public void guardar(Cliente entidad) {
        // guardar
    }

    @Override
    public Collection<Cliente> obtenerTodos() {
        return List.of();
    }
}
