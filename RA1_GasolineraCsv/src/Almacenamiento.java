import java.util.Collection;

public interface Almacenamiento<T> {
    boolean existeFicheroAlmacenamiento();

    void guardar(T entidad);

    void guardarTodos(Collection<T> entidades);

    Collection<T> obtenerTodos();
}
