import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static String idaYVuelta(Path ruta, String texto) throws IOException {
        byte[] datos = texto.getBytes(StandardCharsets.UTF_8);
        Files.write(ruta, datos);
        byte[] recuperados = Files.readAllBytes(ruta);
        return new String(recuperados, StandardCharsets.UTF_8);
    }

    public static void main(String[] args) throws IOException {
        Path carpeta = Files.createDirectories(Path.of("salida"));
        System.out.println(idaYVuelta(carpeta.resolve("mensaje.bin"), "Hola, Lucía: ¡bienvenida a DAM!"));
    }
}
