package es.iesluisbraille.accesodatos;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * RA1 - Acceso a Datos: examen práctico de gestión de ficheros.
 *
 * SOLO se completan los métodos marcados como TODO.
 * No se deben modificar Alumno, ParseadorAlumnos ni las excepciones entregadas.
 * No se evalúa el parseo de CSV ni la lectura de datos por consola.
 */
public class ExamenRA1 {
    private static final Path DIRECTORIO_ENTRADA = Path.of("datos", "entrada");
    private static final Path FICHERO_1 = DIRECTORIO_ENTRADA.resolve("alumnos1.csv");
    private static final Path FICHERO_2 = DIRECTORIO_ENTRADA.resolve("alumnos2.csv");
    private static final Path FICHERO_3 = DIRECTORIO_ENTRADA.resolve("alumnos3.csv");

    public static void main(String[] args) {
        System.out.println("=== EXAMEN RA1: GESTIÓN DE FICHEROS ===");
        System.out.println("Directorio de entrada: " + DIRECTORIO_ENTRADA.toAbsolutePath());

        // Entrada por consola ya implementada; no hay que modificarla.
        Path directorioSalida;
        try (Scanner teclado = new Scanner(System.in)) {
            System.out.print("Introduce la ruta del directorio de salida: ");
            String ruta = teclado.nextLine().trim();
            if (ruta.isEmpty()) {
                System.err.println("La ruta de salida no puede estar vacía.");
                return;
            }

            directorioSalida = Path.of(ruta);
        }
        if (!prepararDirectorioSalida(directorioSalida)) {
            System.err.println("No se puede continuar sin un directorio de salida válido.");
            return;
        }
        System.out.println("[Flujo] Ejercicio 1 finalizado; comienza el procesamiento.");
        System.out.println("[Flujo] Ejercicio 2");
        Alumno alumno1 = ejercicio2LeerYEscribirAlumno(FICHERO_1, directorioSalida);
        System.out.println("[Flujo] Ejercicio 3");
        List<Alumno> alumnos2 = ejercicio3LeerAlumnos(FICHERO_2);
        System.out.println("[Flujo] Ejercicio 4");
        ejercicio4EscribirNumeroBinario(directorioSalida, alumnos2);
        System.out.println("[Flujo] Ejercicio 5");
        List<Alumno> alumnos3 = ejercicio5LeerAlumnosConErrores(FICHERO_3);
        System.out.println("[Flujo] Ejercicio 6");
        ejercicio6FusionarYGenerar(alumnos2, alumnos3, alumno1, directorioSalida);
        System.out.println("[Flujo] Fin de la ejecución.");
    }

    /** Ejercicio 1 (1 p.): comprobar/crear directorio, informar y detectar ruta no válida. */
    private static boolean prepararDirectorioSalida(Path directorio) {
        // TODO: implementar exclusivamente la gestión del directorio de salida.
        return false;
    }

    /** Ejercicio 2 (1 p.): leer el CSV de un alumno y escribir solo su nombre en alumno1.txt. */
    private static Alumno ejercicio2LeerYEscribirAlumno(Path entrada, Path salida) {
        // TODO: leer una línea, utilizar ParseadorAlumnos.parsear(linea),
        // obtener alumno.getNombre() y crear alumno1.txt con un salto de línea.
        // Devuelve el Alumno leído para poder utilizarlo en el ejercicio 6.
        return null;
    }

    /** Ejercicio 3 (2 p.): procesar 200 líneas y construir una lista. */
    private static List<Alumno> ejercicio3LeerAlumnos(Path entrada) {
        List<Alumno> alumnos = new ArrayList<>();
        // TODO: leer alumnos2.csv, gestionar errores y cerrar los recursos.
        // Añadir los alumnos a la lista y mostrar cuántos se han incorporado.
        return alumnos;
    }

    /** Ejercicio 4 (1 p.): guardar un int de 32 bits, no texto, en numero_alumnos.bin. */
    private static void ejercicio4EscribirNumeroBinario(Path salida, List<Alumno> alumnos2) {
        // TODO: escribir alumnos2.size() en un fichero binario de 32 bits.
    }

    /** Ejercicio 5 (2 p.): continuar tras errores y descartar IDs repetidos dentro del CSV 3. */
    private static List<Alumno> ejercicio5LeerAlumnosConErrores(Path entrada) {
        List<Alumno> alumnos = new ArrayList<>();
        // TODO: gestionar POR SEPARADO NumeroCamposIncorrectoException,
        // TipoDatoIncorrectoException y el caso parsear(...) == null.
        // Informar de cada línea descartada y de su causa. Detectar duplicados
        // por Alumno.getId() SOLO dentro del fichero 3.
        return alumnos;
    }

    /** Ejercicio 6 (3 p.): fusionar, ordenar, CREATE_NEW y segunda escritura. */
    private static void ejercicio6FusionarYGenerar(
            List<Alumno> alumnos2, List<Alumno> alumnos3, Alumno alumno1, Path salida) {
        // TODO: combinar 2 y 3 (sin duplicados por ID) y ordenar con el
        // orden natural de Alumno (Comparable).
        // Primera escritura: alumnos_ordenados.csv con CREATE_NEW.
        // Si el fichero YA existía, NO tocarlo ni hacer la Parte B.
        // SOLO si se creó: incorporar alumno1 si no está repetido, volver
        // a ordenar y SOBREESCRIBIR/TRUNCAR el fichero con la colección completa.
        // Cada línea debe obtenerse con alumno.toCsv().
    }
}
