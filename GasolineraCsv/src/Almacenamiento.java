import java.util.List;

public interface Almacenamiento<T, ID> {
    void guardar(T entidad);
    List<T> obtenerTodos();
    T buscarPorId(ID id);
}
