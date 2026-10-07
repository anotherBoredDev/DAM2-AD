import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class ClienteJsonAlmacenamiento implements Almacenamiento<Cliente> {
    private final String aperturaJson = "{" + System.lineSeparator() + "[" + System.lineSeparator();
    private final String cierreJson = System.lineSeparator() + "]" + System.lineSeparator() + "}";

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

    private String convertirClienteToJson(Cliente c) {
        return String.format(Locale.ROOT,"{\"id\": %d,\"nombre\": \"%s\",\"telefono\": \"%s\",\"matricula\": \"%s\"}",
                c.getId(),
                c.getNombre(),
                c.getTelefono(),
                c.getMatricula()
        );
    }

    @Override
    public void guardar(Cliente entidad) {
        List<Cliente> listaClientes = (List<Cliente>) obtenerTodos();
        listaClientes.add(entidad);

        String clientesEnJson = listaClientes.stream()
                .map(this::convertirClienteToJson)
                .reduce((String s1, String s2) -> s1 + "," + System.lineSeparator() + s2)
                .orElse("");

        try (BufferedWriter writer = Files.newBufferedWriter(fichero)){
            writer.write(aperturaJson);
            writer.append(clientesEnJson);
            writer.append(cierreJson);
        } catch (IOException e) {
            System.out.println("Se ha producido un error al guardar un cliente en " + fichero + ". Id cliente " + entidad.getId());
        }
    }

    @Override
    public void guardarTodos(Collection<Cliente> entidades) {
        String clientesEnJson = entidades.stream()
                .map(this::convertirClienteToJson)
                .reduce((String s1, String s2) -> s1 + "," + System.lineSeparator() + s2)
                .orElse("");

        try (BufferedWriter writer = Files.newBufferedWriter(fichero)){
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
            String linea;

            while ((linea = reader.readLine()) != null) {
                boolean ultimoResgistro = false;
                linea = linea.strip();

                if (!linea.endsWith(",")) {
                    ultimoResgistro = true;
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
