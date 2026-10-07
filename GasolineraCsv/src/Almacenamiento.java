import java.util.Collection;

public interface Almacenamiento<T> {
    void guardar(T entidad);

    void guardarTodos(Collection<T> entidades);

    Collection<T> obtenerTodos();
}
