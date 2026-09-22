import java.util.List;

public class PagoCsvAlmacenamiento implements Almacenamiento<Pago> {
    @Override
    public void guardar(Pago entidad) {

    }

    @Override
    public List<Pago> obtenerTodos() {
        return List.of();
    }
}
