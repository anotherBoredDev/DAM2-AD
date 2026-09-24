import java.time.LocalDate;

public class Pago implements Comparable<Pago> {
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

    @Override
    public int compareTo(Pago o) {
        int comparacion = fecha.compareTo(o.getFecha());
        if (comparacion == 0) {
            comparacion = o.getId() - id;
        }
        return comparacion;
    }

    public String getCombustible() {
        return combustible;
    }

    public float getLitros() {
        return litros;
    }

    public float getImporte() {
        return importe;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public int getId() {
        return id;
    }
}
