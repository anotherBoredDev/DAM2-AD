import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class PagoCsvAlmacenamiento implements Almacenamiento<Pago> {
    private final Path directorio;
    private final Path fichero;

    public PagoCsvAlmacenamiento() {
        this.directorio = Path.of("datos");
        this.fichero = directorio.resolve("pagos.csv");
        prepararAlmacenamiento();
    }

    private void prepararAlmacenamiento() {
        try {
            if (Files.notExists(directorio)) {
                Files.createDirectory(directorio);
            }

            if (Files.notExists(fichero)) {
                String cabecera = "id,idCliente,fecha,importe,litros,combustible;" + System.lineSeparator();
                Files.writeString(fichero, cabecera, StandardCharsets.UTF_8, CREATE);
            }

        } catch (IOException e) {
            System.out.println("Se ha producido un error al preparar la ruta " + directorio);
        }
    }

    @Override
    public void guardar(Pago entidad) {
        try {
            String registro = String.format("%d,%d,%s,%.2f,%f,%s;",
                    entidad.getId(),
                    entidad.getIdCliente(),
                    entidad.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    entidad.getImporte(),
                    entidad.getLitros(),
                    entidad.getCombustible()
            ) + System.lineSeparator();

            Files.writeString(fichero, registro ,
                    StandardCharsets.UTF_8,
                    APPEND
            );

        } catch (IOException e) {
            System.out.println("Se ha producido un error al guardar un pago en " + fichero + ". Id pago " + entidad.getId());
        }
    }

    @Override
    public Collection<Pago> obtenerTodos() {
        Collection<Pago> pagos = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(fichero)) {
            String linea = reader.readLine(); // lee el header del archivo csv y evita que lo consuma el bucle

            while ((linea = reader.readLine()) != null) {
                linea = linea.substring(0, linea.indexOf(";")); // Busca el último carácter de la línea (;) y lo elimina, incluyendo lo posterior
                String[] atributosPago = linea.split(",");

                Pago p = new Pago(
                        Integer.parseInt(atributosPago[0]),
                        Integer.parseInt(atributosPago[1]),
                        LocalDate.parse(atributosPago[2], DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        Float.parseFloat(atributosPago[3]),
                        Float.parseFloat(atributosPago[4]),
                        atributosPago[5]
                );

                pagos.add(p);
            }

        } catch (IOException e) {
            System.out.println("Se ha producido un error al leer los datos de " + fichero);
        }

        return pagos;
    }
}
