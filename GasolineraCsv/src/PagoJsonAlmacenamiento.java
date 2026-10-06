import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import static java.nio.file.StandardOpenOption.APPEND;

public class PagoJsonAlmacenamiento implements Almacenamiento<Pago> {
    private final Path directorio;
    private final Path fichero;

    public PagoJsonAlmacenamiento() {
        this.directorio = Path.of("datos");
        this.fichero = directorio.resolve("pagos.json");
        prepararAlmacenamiento();
    }

    private void prepararAlmacenamiento() {
        try {
            if (Files.notExists(directorio)) {
                Files.createDirectory(directorio);
            }

            if (Files.notExists(fichero) || Files.readAllLines(fichero).isEmpty()) {
                String estructuraInicial ="{" + System.lineSeparator()
                        + "  [" + System.lineSeparator()
                        + "  ]" + System.lineSeparator()
                        + "}" + System.lineSeparator();

                Files.writeString(fichero, estructuraInicial, StandardCharsets.UTF_8);
            }

        } catch (IOException e) {
            System.out.println("Se ha producido un error al preparar la ruta " + directorio);
        }
    }

    @Override
    public void guardar(Pago entidad) {
        try {
            String[] nombreAtributos = {"id", "clienteId", "fecha", "importe", "litros", "combustible"};
            String registro = String.format(Locale.ROOT, "{\"%s\": %d,\"%s\": %d,\"%s\": \"%s\",\"%s\": \"%.2f\",\"%s\": \"%f\",\"%s\": \"%s\"},",
                    nombreAtributos[0],
                    entidad.getId(),
                    nombreAtributos[1],
                    entidad.getIdCliente(),
                    nombreAtributos[2],
                    entidad.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    nombreAtributos[3],
                    entidad.getImporte(),
                    nombreAtributos[4],
                    entidad.getLitros(),
                    nombreAtributos[5],
                    entidad.getCombustible()
            ) + System.lineSeparator();

            Files.writeString(fichero, registro ,
                    StandardCharsets.UTF_8,
                    APPEND
            );

        } catch (IOException e) {
            System.out.println("Se ha producido un error al guardar un cliente en " + fichero + ". Id cliente " + entidad.getId());
        }
    }

    @Override
    public void guardarTodos(Collection<Pago> entidades) {
        for (Pago entidad : entidades) {
            guardar(entidad);
        }
    }

    @Override
    public Collection<Pago> obtenerTodos() {
        return List.of();
    }
}
