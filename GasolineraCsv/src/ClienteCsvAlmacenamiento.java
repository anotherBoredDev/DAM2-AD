import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ClienteCsvAlmacenamiento implements Almacenamiento<Cliente> {
    private final Path directorio;
    private final Path fichero;

    public ClienteCsvAlmacenamiento() {
        this.directorio = Path.of("datos");
        this.fichero = directorio.resolve("clientes.csv");
    }

    @Override
    public void guardar(Cliente entidad) {
        // guardar
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
            System.out.println("Holi esto no va :c (leer fichero clientes)");
        }

        return clientes;
    }
}
