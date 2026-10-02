import java.io.DataInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public record Cliente(int id, String nombre, String telefono, String matricula,
                          double saldo, boolean activo) {}
    public static Cliente leer(Path ruta) throws IOException {
        try (DataInputStream in = new DataInputStream(Files.newInputStream(ruta))) {
            int id = 0 /* TODO: leer id */;
            String nombre = "" /* TODO: leer nombre */;
            String telefono = "" /* TODO: leer teléfono */;
            String matricula = "" /* TODO: leer matrícula */;
            double saldo = 0.0 /* TODO: leer saldo */;
            boolean activo = false /* TODO: leer activo */;
            return new Cliente(id, nombre, telefono, matricula, saldo, activo);
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println(leer(Path.of("cliente.bin")));
        } catch (IOException e) {
            System.err.println("No se pudo leer el cliente: " + e.getMessage());
        }
    }
}
