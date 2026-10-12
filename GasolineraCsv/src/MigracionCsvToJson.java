import java.util.List;

public class MigracionCsvToJson {
    public final ClienteCsvAlmacenamiento clienteCsvAlmacenamiento;
    public final ClienteJsonAlmacenamiento clienteJsonAlmacenamiento;

    public final PagoCsvAlmacenamiento pagoCsvAlmacenamiento;
    public final PagoJsonAlmacenamiento pagoJsonAlmacenamiento;

    public MigracionCsvToJson(String directorioDatos) {
        this.clienteCsvAlmacenamiento = new ClienteCsvAlmacenamiento(directorioDatos);
        this.clienteJsonAlmacenamiento = new ClienteJsonAlmacenamiento(directorioDatos);

        this.pagoCsvAlmacenamiento = new PagoCsvAlmacenamiento(directorioDatos);
        this.pagoJsonAlmacenamiento = new PagoJsonAlmacenamiento(directorioDatos);
    }

    public ResultadoMigracion migrar() {
        int clientesMigrados = migrarClientes();
        int pagosMigrados = migrarPagos();

        return new ResultadoMigracion(clientesMigrados, pagosMigrados);
    }

    private int migrarClientes() {
        List<Cliente> clientesParaMigrar = (List<Cliente>) clienteCsvAlmacenamiento.obtenerTodos();
        int clientesMigrados = 0;

        if (!clienteJsonAlmacenamiento.existeFicheroAlmacenamiento()) {
            clientesMigrados = clientesParaMigrar.size();
            clienteJsonAlmacenamiento.guardarTodos(clientesParaMigrar);
        }

        return clientesMigrados;
    }

    private int migrarPagos() {
        List<Pago> pagosParaMigrar = (List<Pago>) pagoCsvAlmacenamiento.obtenerTodos();
        int pagosMigrados = 0;

        if (!pagoJsonAlmacenamiento.existeFicheroAlmacenamiento()) {
            pagosMigrados = pagosParaMigrar.size();
            pagoJsonAlmacenamiento.guardarTodos(pagosParaMigrar);
        }

        return pagosMigrados;
    }
}
