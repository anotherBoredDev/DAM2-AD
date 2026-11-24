public class Main {
    public static int sinSigno(byte dato) {
        return Byte.toUnsignedInt(dato);
    }

    public static void main(String[] args) {
        byte[] datos = {0, 1, 127, (byte) 128, (byte) 255};
        for (byte dato : datos) {
            System.out.println(sinSigno(dato));
        }
    }
}
