import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public record Cabecera(int comienzoPixeles, int ancho, int alto) {}

    // Paso A: la longitud se comprueba antes de acceder a los bytes.
    public static boolean tieneFirmaBmp(byte[] datos) {
        return datos.length >= 2 && false /* TODO A: comprobar BM */;
    }

    // Paso B: este ejercicio admite BITMAPINFOHEADER de 40 bytes.
    public static Cabecera leerCabecera(byte[] datos) {
        if (datos.length < 54 || !tieneFirmaBmp(datos)) {
            throw new IllegalArgumentException("Cabecera BMP incompleta o firma incorrecta");
        }
        ByteBuffer buffer = ByteBuffer.wrap(datos).order(ByteOrder.BIG_ENDIAN /* TODO B: orden BMP */);
        int comienzoPixeles = 0; // TODO B: leer comienzo de píxeles
        int ancho = 0; // TODO B
        int alto = 0; // TODO B
        int bitsPorPixel = 0; // TODO B
        int compresion = -1; // TODO B
        validarCabecera(datos, buffer, comienzoPixeles, ancho, alto, bitsPorPixel, compresion);
        return new Cabecera(comienzoPixeles, ancho, alto);
    }

    // Validación proporcionada: no forma parte de los huecos del ejercicio.
    private static void validarCabecera(byte[] datos, ByteBuffer b, int inicio,
                                        int ancho, int alto, int bits, int compresion) {
        if (b.getInt(14) != 40 || b.getShort(26) != 1 || bits != 24 || compresion != 0) {
            throw new IllegalArgumentException("Solo BMP con cabecera de 40 bytes, un plano, 24 bits y sin compresión");
        }
        if (ancho <= 0 || alto == 0 || alto == Integer.MIN_VALUE || inicio < 54 || inicio > datos.length) {
            throw new IllegalArgumentException("Dimensiones o comienzo de píxeles inválidos");
        }
        long fila = ((ancho * 3L + 3) / 4) * 4;
        if (fila > datos.length || Math.abs((long) alto) > (datos.length - inicio) / fila) {
            throw new IllegalArgumentException("Faltan bytes de píxeles");
        }
        if (Integer.toUnsignedLong(b.getInt(2)) != datos.length) {
            throw new IllegalArgumentException("El tamaño declarado no coincide con el fichero");
        }
    }

    // Paso C: (0, 0) es la esquina superior izquierda de la imagen.
    public static int posicion(Cabecera c, int x, int y) {
        int altoReal = Math.abs(c.alto());
        if (x < 0 || x >= c.ancho() || y < 0 || y >= altoReal) {
            throw new IllegalArgumentException("Píxel fuera de la imagen");
        }
        long bytesPorFila = 0L /* TODO C: alinear fila a 4 bytes */;
        int filaEnFichero = 0 /* TODO C: orientar la fila */;
        return 0 /* TODO C: posición absoluta */;
    }

    // Paso D: el método recibe RGB, pero el fichero almacena BGR.
    public static void pintarPixel(byte[] datos, Cabecera c, int x, int y,
                                   int rojo, int verde, int azul) {
        if (rojo < 0 || rojo > 255 || verde < 0 || verde > 255 || azul < 0 || azul > 255) {
            throw new IllegalArgumentException("Cada componente debe estar entre 0 y 255");
        }
        int p = posicion(c, x, y);
        // TODO D: escribir los tres componentes en orden BGR
    }

    // Paso E: guardar una copia. El origen del ejemplo siempre se conserva.
    public static void modificar(Path origen, Path destino, int x, int y,
                                 int rojo, int verde, int azul) throws IOException {
        if (origen.toAbsolutePath().normalize().equals(destino.toAbsolutePath().normalize())
                || (Files.exists(destino) && Files.isSameFile(origen, destino))) {
            throw new IllegalArgumentException("Elige un destino distinto del origen");
        }
        byte[] datos = Files.readAllBytes(origen);
        Cabecera c = leerCabecera(datos);
        pintarPixel(datos, c, x, y, rojo, verde, azul);
        // TODO E: guardar los bytes modificados
    }

    public static void main(String[] args) {
        try {
            Path carpeta = Files.createDirectories(Path.of("salida"));
            Path destino = carpeta.resolve("imagen_modificada.bmp");
            modificar(Path.of("imagen.bmp"), destino, 2, 3, 255, 0, 0);
            System.out.println("Imagen guardada en " + destino.toAbsolutePath());
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("No se pudo modificar la imagen: " + e.getMessage());
        }
    }
}
