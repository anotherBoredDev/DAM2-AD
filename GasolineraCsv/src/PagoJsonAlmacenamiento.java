import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class PagoJsonAlmacenamiento implements Almacenamiento<Pago> {
    private final String aperturaJson = "{" + System.lineSeparator() + "  [" + System.lineSeparator();
    private final String cierreJson = System.lineSeparator() + "  ]" + System.lineSeparator() + "}";

    private final Path directorio;
    private final Path fichero;

    public PagoJsonAlmacenamiento(String directorio) {
        this.directorio = Path.of(directorio);
        this.fichero = this.directorio.resolve("pagos.json");
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

    private String convertirPagoToJson(Pago p) {
        return String.format(Locale.ROOT, "{\"id\": %d,\"idCliente\": %d,\"fecha\": \"%s\",\"importe\": \"%.2f\",\"litros\": \"%f\",\"combustible\": \"%s\"}",
                p.getId(),
                p.getIdCliente(),
                p.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                p.getImporte(),
                p.getLitros(),
                p.getCombustible()
        );
    }

    @Override
    public void guardar(Pago entidad) {
        List<Pago> listaClientes = (List<Pago>) obtenerTodos();
        listaClientes.add(entidad);

        String pagosEnJson = listaClientes.stream()
                .map(this::convertirPagoToJson) // transformar objetos Pago en objeto json con los datos del pago
                .map((String string) -> " ".repeat(4) + string) // indentación para formato correcto
                .reduce((String s1, String s2) -> s1 + "," + System.lineSeparator() + s2) // , y salto de línea
                .orElse("");


        try (BufferedWriter writer = Files.newBufferedWriter(fichero)) {
            writer.write(aperturaJson);
            writer.append(pagosEnJson);
            writer.append(cierreJson);
        } catch (IOException e) {
            System.out.println("Se ha producido un error al guardar un pago en " + fichero + ". Id cliente " + entidad.getId());
        }
    }

    @Override
    public void guardarTodos(Collection<Pago> entidades) {
        String pagosEnJson = entidades.stream()
                .map(this::convertirPagoToJson) // transformar objetos Pago en objeto json con los datos del pago
                .map((String string) -> " ".repeat(4) + string) // indentación para formato correcto
                .reduce((String s1, String s2) -> s1 + "," + System.lineSeparator() + s2) // , y salto de línea
                .orElse("");


        try (BufferedWriter writer = Files.newBufferedWriter(fichero)) {
            writer.write(aperturaJson);
            writer.append(pagosEnJson);
            writer.append(cierreJson);
        } catch (IOException e) {
            System.out.println("Se ha producido un error al guardar todos los pagos en " + fichero + ".");
        }
    }

    @Override
    public Collection<Pago> obtenerTodos() {
        Collection<Pago> pagos = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(fichero)) {
            // Comprueba que el fichero inicia con el formato correcto
            String linea1 = reader.readLine();
            String linea2 = reader.readLine();

            if (linea1 == null || linea2 == null) {
                throw new RuntimeException("Error: Json inválido en el fichero " + fichero + ".");
            }

            if (!linea1.strip().equals(aperturaJson.strip().split(System.lineSeparator())[0].strip())
                    || !linea2.strip().equals(aperturaJson.strip().split(System.lineSeparator())[1].strip())) {
                throw new RuntimeException("Error: Json inválido en el fichero " + fichero + ".");
            }

            // Lee los datos y crea los clientes
            String linea;
            boolean quedanRegistros = true;
            while (quedanRegistros && (linea = reader.readLine()) != null) {
                linea = linea.strip();

                if (linea.isEmpty()) {
                    return pagos;
                }

                if (!linea.endsWith(",")) {
                    quedanRegistros = false;
                }

                linea = linea.substring(linea.indexOf("{") + 1, linea.lastIndexOf("}"));

                String[] atributosPago = linea.split(",");

                for (int i = 0; i < atributosPago.length; i++) {
                    String atributo = atributosPago[i].strip();
                    atributo = atributo.substring(atributo.indexOf(":") + 1).strip();

                    if (atributo.contains("\"")) {
                        atributo = atributo.substring(atributo.indexOf("\"") + 1, atributo.length() - 1);
                    }
                    atributosPago[i] = atributo;
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

            // Comprueba que el fichero cierra con el formato correcto
            linea1 = reader.readLine();
            linea2 = reader.readLine();

            if (linea1 == null || linea2 == null) {
                throw new RuntimeException("Error: Json inválido en el fichero " + fichero + ".");
            }

            if (!linea1.strip().equals(cierreJson.strip().split(System.lineSeparator())[0].strip())
                    || !linea2.strip().equals(cierreJson.strip().split(System.lineSeparator())[1].strip())) {
                throw new RuntimeException("Error: Json inválido en el fichero " + fichero + ".");
            }

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());

        } catch (IOException e) {
            System.out.println("Se ha producido un error al leer los datos de " + fichero);
        }

        return pagos;
    }
}
