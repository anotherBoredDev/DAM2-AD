import java.util.Collection;
import java.util.List;

public interface Almacenamiento<T> {
    void guardar(T entidad);
    Collection<T> obtenerTodos();
}
