import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static String idaYVuelta(Path ruta, String texto) throws IOException {
        byte[] datos = new byte[0] /* TODO: codificar texto */;
        // TODO: guardar los bytes
        byte[] recuperados = new byte[0] /* TODO: recuperar los bytes */;
        return "" /* TODO: decodificar recuperados */;
    }

    public static void main(String[] args) throws IOException {
        Path carpeta = Files.createDirectories(Path.of("salida"));
        System.out.println(idaYVuelta(carpeta.resolve("mensaje.bin"), "Hola, Lucía: ¡bienvenida a DAM!"));
    }
}
