import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class ClienteJsonAlmacenamiento implements Almacenamiento<Cliente> {
    private final String aperturaJson = "{" + System.lineSeparator() + "  [" + System.lineSeparator();
    private final String cierreJson = System.lineSeparator() + "  ]" + System.lineSeparator() + "}";

    private final Path directorio;
    private final Path fichero;

    public ClienteJsonAlmacenamiento(String directorio) {
        this.directorio = Path.of(directorio);
        this.fichero = this.directorio.resolve("clientes.json");
        prepararAlmacenamiento();
    }

    private void prepararAlmacenamiento() {
        try {
            if (Files.notExists(directorio)) {
                Files.createDirectory(directorio);
            }

        } catch (IOException e) {
            System.out.println("Se ha producido un error al preparar la ruta " + directorio);
        }
    }

    private String convertirClienteToJson(Cliente c) {
        return String.format(Locale.ROOT, "{\"id\": %d,\"nombre\": \"%s\",\"telefono\": \"%s\",\"matricula\": \"%s\"}", c.getId(), c.getNombre(), c.getTelefono(), c.getMatricula());
    }

    @Override
    public void guardar(Cliente entidad) {
        List<Cliente> listaClientes = (List<Cliente>) obtenerTodos();
        listaClientes.add(entidad);

        String clientesEnJson = listaClientes.stream().map(this::convertirClienteToJson) // transformar objetos Cliente en objeto json con los datos del cliente
                .map((String string) -> " ".repeat(4) + string) // indentación para formato correcto
                .reduce((String s1, String s2) -> s1 + "," + System.lineSeparator() + s2) // , y salto de línea
                .orElse("");

        try (BufferedWriter writer = Files.newBufferedWriter(fichero)) {
            writer.write(aperturaJson);
            writer.append(clientesEnJson);
            writer.append(cierreJson);
        } catch (IOException e) {
            System.out.println("Se ha producido un error al guardar un cliente en " + fichero + ". Id cliente " + entidad.getId());
        }
    }

    @Override
    public void guardarTodos(Collection<Cliente> entidades) {
        String clientesEnJson = entidades.stream().map(this::convertirClienteToJson) // transformar objetos Cliente en objeto json con los datos del cliente
                .map((String string) -> " ".repeat(4) + string) // indentación para formato correcto
                .reduce((String s1, String s2) -> s1 + "," + System.lineSeparator() + s2) // , y salto de línea
                .orElse("");

        try (BufferedWriter writer = Files.newBufferedWriter(fichero)) {
            writer.write(aperturaJson);
            writer.append(clientesEnJson);
            writer.append(cierreJson);
        } catch (IOException e) {
            System.out.println("Se ha producido un error al guardar todos los clientes en " + fichero + ".");
        }
    }

    @Override
    public Collection<Cliente> obtenerTodos() {
        Collection<Cliente> clientes = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(fichero)) {
            // Comprueba que el fichero inicia con el formato correcto
            String linea1 = reader.readLine();
            String linea2 = reader.readLine();

            if (linea1 == null || linea2 == null) {
                throw new RuntimeException("Error: Json inválido en el fichero " + fichero + ".");
            }

            if (!linea1.strip().equals(aperturaJson.strip().split(System.lineSeparator())[0].strip()) || !linea2.strip().equals(aperturaJson.strip().split(System.lineSeparator())[1].strip())) {
                throw new RuntimeException("Error: Json inválido en el fichero " + fichero + ".");
            }

            // Lee los datos y crea los clientes
            String linea;
            boolean quedanRegistros = true;
            while (quedanRegistros && (linea = reader.readLine()) != null) {
                linea = linea.strip();

                if (linea.isEmpty()) {
                    return clientes;
                }

                if (!linea.endsWith(",")) {
                    quedanRegistros = false;
                }

                linea = linea.substring(linea.indexOf("{") + 1, linea.lastIndexOf("}"));

                String[] atributosCliente = linea.split(",");

                for (int i = 0; i < atributosCliente.length; i++) {
                    String atributo = atributosCliente[i].strip();
                    atributo = atributo.substring(atributo.indexOf(":") + 1).strip();
                    if (atributo.contains("\"")) {
                        atributo = atributo.substring(atributo.indexOf("\"") + 1, atributo.length() - 1);
                    }
                    atributosCliente[i] = atributo;
                }

                Cliente c = new Cliente(Integer.parseInt(atributosCliente[0]), atributosCliente[1], atributosCliente[2], atributosCliente[3]);

                clientes.add(c);
            }


            // Comprueba que el fichero cierra con el formato correcto
            linea1 = reader.readLine();
            linea2 = reader.readLine();

            if (linea1 == null || linea2 == null) {
                throw new RuntimeException("Error: Json inválido en el fichero " + fichero + ".");
            }

            if (!linea1.strip().equals(cierreJson.strip().split(System.lineSeparator())[0].strip()) || !linea2.strip().equals(cierreJson.strip().split(System.lineSeparator())[1].strip())) {
                throw new RuntimeException("Error: Json inválido en el fichero " + fichero + ".");
            }

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());

        } catch (IOException e) {
            System.out.println("Se ha producido un error al leer los datos de " + fichero);
        }

        return clientes;
    }
}
