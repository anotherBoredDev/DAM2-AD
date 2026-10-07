import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import static java.nio.file.StandardOpenOption.*;

public class PagoCsvAlmacenamiento implements Almacenamiento<Pago> {
    private final Path directorio;
    private final Path fichero;
    private final String cabecera;

    public PagoCsvAlmacenamiento() {
        this.directorio = Path.of("datos");
        this.fichero = directorio.resolve("pagos.csv");
        cabecera = "id,idCliente,fecha,importe,litros,combustible;" + System.lineSeparator();
        prepararAlmacenamiento();
    }

    private void prepararAlmacenamiento() {
        try {
            if (Files.notExists(directorio)) {
                Files.createDirectory(directorio);
            }

            if (Files.notExists(fichero) || Files.readAllLines(fichero).isEmpty()) {
                Files.writeString(fichero, cabecera, StandardCharsets.UTF_8, CREATE);
            }

        } catch (IOException e) {
            System.out.println("Se ha producido un error al preparar la ruta " + directorio);
        }
    }

    @Override
    public void guardar(Pago entidad) {
        try {
            String registro = String.format(Locale.ROOT, "%d,%d,%s,%.2f,%f,%s;",
                    entidad.getId(),
                    entidad.getIdCliente(),
                    entidad.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    entidad.getImporte(),
                    entidad.getLitros(),
                    entidad.getCombustible()
            ) + System.lineSeparator();

            Files.writeString(fichero, registro,
                    StandardCharsets.UTF_8,
                    APPEND
            );

        } catch (IOException e) {
            System.out.println("Se ha producido un error al guardar un pago en " + fichero + ". Id pago " + entidad.getId());
        }
    }

    @Override
    public void guardarTodos(Collection<Pago> entidades) {
        try {
            Files.writeString(fichero, cabecera, StandardCharsets.UTF_8, TRUNCATE_EXISTING);

            for (Pago entidad : entidades) {
                guardar(entidad);
            }
        } catch (IOException e) {
            System.out.println("Se ha producido un error al guardar clientes en " + fichero + ".");
        }
    }

    @Override
    public Collection<Pago> obtenerTodos() {
        Collection<Pago> pagos = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(fichero)) {
            String linea = reader.readLine(); // lee el header del archivo csv
            int cantidadCampos = linea.split(",").length; // guarda la cantidad de campos de un registro correcto

            int lineaActual = 1;

            while ((linea = reader.readLine()) != null) {
                lineaActual++;

                linea = linea.substring(0, linea.indexOf(";")); // Busca el último carácter de la línea (;) y lo elimina, incluyendo lo posterior
                String[] atributosPago = linea.split(",");

                if (atributosPago.length != cantidadCampos) {
                    System.err.println("Registro corrupto en " + fichero + " en linea " + lineaActual);
                    continue;
                }

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
