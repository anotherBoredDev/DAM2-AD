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

    public void migrar() {
        clienteJsonAlmacenamiento.guardarTodos(clienteCsvAlmacenamiento.obtenerTodos());
        pagoJsonAlmacenamiento.guardarTodos(pagoCsvAlmacenamiento.obtenerTodos());
    }
}
