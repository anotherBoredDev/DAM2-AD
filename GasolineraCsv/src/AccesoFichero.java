import java.util.List;

public interface AccesoFichero<T> {
    void crearFicheroDatos();
    void guardar(T entidad);
    List<T> obtenerTodos();
    T buscarPorId(int id);
}
