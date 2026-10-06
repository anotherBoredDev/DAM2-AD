public class MigrarCsvToJson<T> {
    private final Almacenamiento<T> ficheroCsv;
    private final Almacenamiento<T> ficheroJson;

    public MigrarCsvToJson(Almacenamiento<T> ficheroCsv, Almacenamiento<T> ficheroJson) {
        this.ficheroCsv = ficheroCsv;
        this.ficheroJson = ficheroJson;
    }

    public void realizarMigracion() {
        ficheroJson.guardarTodos(ficheroCsv.obtenerTodos());
    }
}
