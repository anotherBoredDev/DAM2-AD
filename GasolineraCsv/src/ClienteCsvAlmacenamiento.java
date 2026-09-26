import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class ClienteCsvAlmacenamiento implements Almacenamiento<Cliente> {
    private final Path directorio;
    private final Path fichero;

    public ClienteCsvAlmacenamiento() {
        this.directorio = Path.of("datos");
        this.fichero = directorio.resolve("clientes.csv");
        prepararAlmacenamiento();
    }

    private void prepararAlmacenamiento() {
        try {
            if (Files.notExists(directorio)) {
                Files.createDirectory(directorio);
            }

            if (Files.notExists(fichero)) {
                String cabecera = "id,nombre,telefono,matricula;" + System.lineSeparator();
                Files.writeString(fichero, cabecera, StandardCharsets.UTF_8, CREATE);
            }

        } catch (IOException e) {
            System.out.println("Se ha producido un error al preparar la ruta " + directorio);
        }
    }

    @Override
    public void guardar(Cliente entidad) {
        try {
            String registro = String.format("%d,%s,%s,%s;",
                    entidad.getId(),
                    entidad.getNombre(),
                    entidad.getTelefono(),
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
    public Collection<Cliente> obtenerTodos() {
        Collection<Cliente> clientes = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(fichero)) {
            String linea = reader.readLine(); // lee el header del archivo csv y evita que lo consuma el bucle

            while ((linea = reader.readLine()) != null) {
                linea = linea.substring(0, linea.indexOf(";")); // Busca el último carácter de la línea (;) y lo elimina, incluyendo lo posterior
                String[] atributosCliente = linea.split(",");

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
