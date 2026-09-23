import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class PagoGestor {
    private final Almacenamiento<Pago> pagoAlmacenamiento;
    private final List<Pago> pagosEnMemoria;

    public PagoGestor(Almacenamiento<Pago> pagoAlmacenamiento) {
        this.pagoAlmacenamiento = pagoAlmacenamiento;
        this.pagosEnMemoria = cargarPagosEnMemoria();
    }

     private LinkedList<Pago> cargarPagosEnMemoria() {
        return new LinkedList<Pago>(pagoAlmacenamiento.obtenerTodos());
     }

     public Pago registrarPago(int idCliente, LocalDate fecha, float importe, int litros, String combustible) {
        return null;
     }
}
