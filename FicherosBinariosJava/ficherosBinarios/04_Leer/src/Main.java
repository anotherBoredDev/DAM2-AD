import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Main {
    public static byte[] leer(Path ruta) throws IOException {
        return Files.readAllBytes(ruta);
    }

    public static void main(String[] args) throws IOException {
        Path ruta = Path.of("datos.bin");
        System.out.println(Arrays.toString(leer(ruta)));
    }
}
