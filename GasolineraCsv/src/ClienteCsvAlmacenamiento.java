import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import static java.nio.file.StandardOpenOption.*;

public class ClienteCsvAlmacenamiento implements Almacenamiento<Cliente> {
    private final Path directorio;
    private final Path fichero;
    private final String cabecera;

    public ClienteCsvAlmacenamiento() {
        this.directorio = Path.of("datos");
        this.fichero = directorio.resolve("clientes.csv");
        cabecera = "id,nombre,telefono,matricula;" + System.lineSeparator();
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
    public void guardar(Cliente entidad) {
        try {
            String registro = String.format(Locale.ROOT, "%d,%s,%s,%s;",
                    entidad.getId(),
                    entidad.getNombre(),
                    entidad.getTelefono(),
                    entidad.getMatricula()
            ) + System.lineSeparator();

            Files.writeString(fichero, registro,
                    StandardCharsets.UTF_8,
                    APPEND
            );

        } catch (IOException e) {
            System.out.println("Se ha producido un error al guardar un cliente en " + fichero + ". Id cliente " + entidad.getId());
        }
    }

    @Override
    public void guardarTodos(Collection<Cliente> entidades) {
        try {
            Files.writeString(fichero, cabecera, StandardCharsets.UTF_8, TRUNCATE_EXISTING);

            for (Cliente entidad : entidades) {
                guardar(entidad);
            }
        } catch (IOException e) {
            System.out.println("Se ha producido un error al guardar clientes en " + fichero + ".");
        }
    }

    @Override
    public Collection<Cliente> obtenerTodos() {
        Collection<Cliente> clientes = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(fichero)) {
            String linea = reader.readLine(); // lee el header del archivo csv
            int cantidadCampos = linea.split(",").length; // guarda la cantidad de campos de un registro correcto

            int lineaActual = 1;

            while ((linea = reader.readLine()) != null) {
                lineaActual++;

                linea = linea.substring(0, linea.indexOf(";")); // Busca el último carácter de la línea (;) y lo elimina, incluyendo lo posterior
                String[] atributosCliente = linea.split(",");

                if (atributosCliente.length != cantidadCampos) {
                    System.err.println("Registro corrupto en " + fichero + " en linea " + lineaActual);
                    continue;
                }

                Cliente c = new Cliente(
                        Integer.parseInt(atributosCliente[0]),
                        atributosCliente[1],
                        atributosCliente[2],
                        atributosCliente[3]
                );

                clientes.add(c);
            }

        } catch (IOException e) {
            System.out.println("Se ha producido un error al leer los datos de " + fichero);
        }

        return clientes;
    }
}
