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

     public Pago registrarPago(int idCliente, LocalDate fecha, float importe, float litros, String combustible) {
         int id = conseguirSiguienteId();

         Pago pago = new Pago(id, idCliente, fecha, importe, litros, combustible);

         pagoAlmacenamiento.guardar(pago);
         pagosEnMemoria.add(pago);

         return pago;
     }

    private int conseguirSiguienteId() {
        int id = 0;
        for (Pago p : pagosEnMemoria) {
            if (p.getId() >= id) {
                id = p.getId();
            }
        }
        return id + 1;
    }

    public List<Pago> conseguirTodosPagos() {
        return pagosEnMemoria;
    }
}
