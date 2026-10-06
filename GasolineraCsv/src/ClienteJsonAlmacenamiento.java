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

        try (BufferedReader reader = Files.newBufferedReader(fichero)) {
            String linea;

            // Comprueba que la estructura de json es válida
            if ((linea = reader.readLine()) == null) {
                throw new IOException("El fichero Json que se intenta leer está vacío.");
            }

            if (!linea.strip().equals("{")) {
                throw new RuntimeException("Formato del fichero Json inválido.");
            }

            if ((linea = reader.readLine()) == null) {
                throw new IOException("El fichero Json que se intenta leer está incompleto.");
            }

            if (!linea.strip().equals("[")) {
                throw new RuntimeException("Formato del fichero Json inválido.");
            }

            while ((linea = reader.readLine()) != null) {
                boolean ultimoResgistro = false;
                linea = linea.strip();

                if (!linea.endsWith(",")) {
                    ultimoResgistro = true;
                }

                linea = linea.substring(linea.indexOf("{") + 1, linea.lastIndexOf("}") - 1);

                String[] atributosCliente = linea.split(",");

                for (int i = 0; i < atributosCliente.length; i++) {
                    String atributo = atributosCliente[i].strip();
                    atributo = atributo.substring(atributo.indexOf(":") + 1).strip();
                    if (atributo.contains("\"")) {
                        atributo = atributo.substring(atributo.indexOf("\"") + 1, atributo.length() - 1);
                    }
                    atributosCliente[i] = atributo;
                }

                Cliente c = new Cliente(
                        Integer.parseInt(atributosCliente[0]),
                        atributosCliente[1],
                        atributosCliente[2],
                        atributosCliente[3]
                );

                clientes.add(c);

                if (ultimoResgistro) {
                    return clientes; // se ha llegado al último registro
                }
            }

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());

        } catch (IOException e) {
            System.out.println("Se ha producido un error al leer los datos de " + fichero);
        }

        return clientes;
    }
}
