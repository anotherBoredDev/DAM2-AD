import java.util.Collection;

public interface Almacenamiento<T> {
    void guardar(T entidad);
    Collection<T> obtenerTodos();
}
