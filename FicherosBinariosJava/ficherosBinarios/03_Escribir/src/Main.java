import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void guardar(Path ruta, byte[] datos) throws IOException {
        // TODO: guardar datos en ruta
    }

    public static void main(String[] args) throws IOException {
        Path carpeta = Files.createDirectories(Path.of("salida"));
        Path ruta = carpeta.resolve("datos.bin");
        byte[] datos = {65, 66, 67, 68};
        guardar(ruta, datos);
        System.out.println(ruta.toAbsolutePath() + " (" + Files.size(ruta) + " bytes)");
    }
}
