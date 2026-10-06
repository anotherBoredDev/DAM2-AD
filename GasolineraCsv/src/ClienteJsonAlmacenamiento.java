import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import static java.nio.file.StandardOpenOption.APPEND;

public class ClienteJsonAlmacenamiento implements Almacenamiento<Cliente> {
    private final Path directorio;
    private final Path fichero;

    public ClienteJsonAlmacenamiento() {
        this.directorio = Path.of("datos");
        this.fichero = directorio.resolve("clientes.json");
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
    public void guardar(Cliente entidad) {
        try {
            String[] nombreAtributos = {"id", "nombre", "telefono", "matricula"};
            String registro = String.format(Locale.ROOT, "{\"%s\": %d,\"%s\": \"%s\",\"%s\": \"%s\",\"%s\": \"%s\"},",
                    nombreAtributos[0],
                    entidad.getId(),
                    nombreAtributos[1],
                    entidad.getNombre(),
                    nombreAtributos[2],
                    entidad.getTelefono(),
                    nombreAtributos[3],
                    entidad.getMatricula()
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
    public void guardarTodos(Collection<Cliente> entidades) {
        for (Cliente entidad : entidades) {
            guardar(entidad);
        }
    }

    @Override
    public Collection<Cliente> obtenerTodos() {
        Collection<Cliente> clientes = new ArrayList<>();


        return clientes;
    }
}
