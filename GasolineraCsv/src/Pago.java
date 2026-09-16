import java.time.LocalDate;

public class Pago {
    private final int id;
    private int idCliente;
    private LocalDate fecha;
    private float importe;
    private float litros;
    private String combustible;

    public Pago(int id, int idCliente, LocalDate fecha, float importe, float litros, String combustible) {
        this.id = id;
        this.idCliente = idCliente;
        this.fecha = fecha;
        this.importe = importe;
        this.litros = litros;
        this.combustible = combustible;
    }
}
