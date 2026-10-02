import java.util.Arrays;

public class Main {
    public static String comoAscii(byte[] datos) {
        return Byte.toString() /* TODO: decodificar como ASCII */;
    }

    public static int[] comoNumerosConSigno(byte[] datos) {
        int[] resultado = new int[datos.length];
        for (int i = 0; i < datos.length; i++) {
            resultado[i] = datos[i];
        }
        return resultado;
    }

    public static int[] comoNumerosSinSigno(byte[] datos) {
        int[] resultado = new int[datos.length];
        for (int i = 0; i < datos.length; i++) {
            resultado[i] = Byte.toUnsignedInt(datos[i]);
        }
        return resultado;
    }

    public static void main(String[] args) {
        byte[] letras = {72, 111, 108, 97};
        byte[] valores = {65, (byte) 255, (byte) 200};

        System.out.println(comoAscii(letras));
        System.out.println(Arrays.toString(comoNumerosConSigno(valores)));
        System.out.println(Arrays.toString(comoNumerosSinSigno(valores)));
    }
}
