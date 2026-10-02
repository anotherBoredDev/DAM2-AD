import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public record Cliente(int id, String nombre, String telefono, String matricula,
                          double saldo, boolean activo) {}
    public static void guardar(Path ruta, Cliente cliente) throws IOException {
        try (DataOutputStream out = new DataOutputStream(Files.newOutputStream(ruta))) {
            // TODO: escribir los seis campos, en el orden acordado
        }
    }

    public static void main(String[] args) throws IOException {
        Path carpeta = Files.createDirectories(Path.of("salida"));
        guardar(carpeta.resolve("cliente.bin"),
            new Cliente(27, "Lucía", "600123456", "1234ABC", 1532.75, true));
        System.out.println("Cliente guardado en " + carpeta.toAbsolutePath());
    }
}
