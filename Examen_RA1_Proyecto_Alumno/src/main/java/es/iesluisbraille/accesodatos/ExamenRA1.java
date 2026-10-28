package es.iesluisbraille.accesodatos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * RA1 - Acceso a Datos: examen práctico de gestión de ficheros.
 * <p>
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

    /**
     * Ejercicio 1 (1 p.): comprobar/crear directorio, informar y detectar ruta no válida.
     */
    private static boolean prepararDirectorioSalida(Path directorio) {
        try {
            if (Files.exists(directorio)) {
                if (!Files.isDirectory(directorio)) {
                    System.err.println("ERROR: Ya existe un fichero con el mismo nombre ('" + directorio + "')");
                    return false;
                }
                System.out.println("Ya existe el directorio '" + directorio + "'. Se procede sin modificaciones.");
                return true;
            }

            // createDirectories crea los directorios intermedios que sean necesarios.
            Files.createDirectories(directorio);
            System.out.println("Directorio '" + directorio + "' creado correctamente.");
            return true;
        } catch (IOException | SecurityException e) {
            System.err.println("ERROR: No se ha podido crear o comprobar el directorio '" + directorio + "'. " + e.getMessage());
        }
        return false;
    }

    /**
     * Ejercicio 2 (1 p.): leer el CSV de un alumno y escribir solo su nombre en alumno1.txt.
     */
    private static Alumno ejercicio2LeerYEscribirAlumno(Path entrada, Path salida) {
        // TODO: leer una línea, utilizar ParseadorAlumnos.parsear(linea),
        // obtener alumno.getNombre() y crear alumno1.txt con un salto de línea.
        // Devuelve el Alumno leído para poder utilizarlo en el ejercicio 6.

        try (BufferedReader br = Files.newBufferedReader(entrada, StandardCharsets.UTF_8)) {
            String linea;
            linea = br.readLine();

            if (linea == null) {
                System.err.println("ERROR: El fichero '" + entrada + "' está vacío.");
                return null;
            }

            Alumno a = ParseadorAlumnos.parsear(linea); // Se asigna a null si algún campo no es válido.

            if (a == null) {
                System.err.println("ERROR: El alumno contiene uno o más datos inválidos.");
                return null;
            }

            Path ficheroSalida = salida.resolve("alumno1.txt");
            try {
                // Se utiliza Files.writeString porque para escribir una línea es menos pesado que crear un BufferedWriter.
                // Se sobreescriben datos anteriores en ficheroSalida
                Files.writeString(ficheroSalida, a.getNombre() + System.lineSeparator(), StandardCharsets.UTF_8);
            } catch (IOException e) {
                System.err.println("ERROR: No se ha podido escribir en el fichero '" + ficheroSalida + "'.");
            }

            return a;
        } catch (NumeroCamposIncorrectoException e) {
            System.err.println("ERROR: No se han podido leer los campos de un registro del fichero '" + entrada + "'. " + e.getMessage());
        } catch (TipoDatoIncorrectoException e) {
            System.err.println("ERROR: Un registro del fichero '" + entrada + "' contiene tipos de datos incorrectos. " + e.getMessage());
        } catch (IOException | SecurityException e) {
            System.err.println("ERROR: No se ha podido acceder al fichero '" + entrada + "'. " + e.getMessage());
        }

        return null;
    }

    /**
     * Ejercicio 3 (2 p.): procesar 200 líneas y construir una lista.
     */
    private static List<Alumno> ejercicio3LeerAlumnos(Path entrada) {
        List<Alumno> alumnos = new ArrayList<>();
        // TODO: leer alumnos2.csv, gestionar errores y cerrar los recursos.
        // Añadir los alumnos a la lista y mostrar cuántos se han incorporado.
        try (BufferedReader br = Files.newBufferedReader(entrada, StandardCharsets.UTF_8)) {
            String linea;
            while ((linea = br.readLine()) != null) {
                try {
                    Alumno a = ParseadorAlumnos.parsear(linea); // Se asigna a null si algún campo no es válido.

                    if (a == null) {
                        System.err.println("ERROR: El alumno contiene uno o más datos inválidos. No se añade al listado.");
                        continue;
                    }

                    alumnos.add(a);
                } catch (NumeroCamposIncorrectoException e) {
                    System.err.println("ERROR: No se han podido leer los campos del registro '" + linea + "'. " + e.getMessage());
                    System.err.println("Se procede sin añadir el registro al listado.");
                } catch (TipoDatoIncorrectoException e) {
                    System.err.println("ERROR: El registro '" + linea + "' contiene tipos de datos incorrectos. " + e.getMessage());
                    System.err.println("Se procede sin añadir el registro al listado.");
                }
            }
        } catch (IOException | SecurityException e) {
            System.err.println("ERROR: No se ha podido acceder al fichero '" + entrada + "'. " + e.getMessage());
        }

        System.out.println(alumnos.size() + " alumnos en la lista.");
        return alumnos;
    }

    /**
     * Ejercicio 4 (1 p.): guardar un int de 32 bits, no texto, en numero_alumnos.bin.
     */
    private static void ejercicio4EscribirNumeroBinario(Path salida, List<Alumno> alumnos2) {
        // TODO: escribir alumnos2.size() en un fichero binario de 32 bits.
        Path ficheroSalida = salida.resolve("numero_alumnos.bin");
        try (DataOutputStream dos = new DataOutputStream(Files.newOutputStream(ficheroSalida))) {
            dos.writeInt(alumnos2.size());
        } catch (IOException e) {
            System.err.println("ERROR: No se ha podido escribir en el fichero '" + salida + "'.");
        }
    }

    /**
     * Ejercicio 5 (2 p.): continuar tras errores y descartar IDs repetidos dentro del CSV 3.
     */
    private static List<Alumno> ejercicio5LeerAlumnosConErrores(Path entrada) {
        List<Alumno> alumnos = new ArrayList<>();
        // TODO: gestionar POR SEPARADO NumeroCamposIncorrectoException,
        // TipoDatoIncorrectoException y el caso parsear(...) == null.
        // Informar de cada línea descartada y de su causa. Detectar duplicados
        // por Alumno.getId() SOLO dentro del fichero 3.
        try (BufferedReader br = Files.newBufferedReader(entrada, StandardCharsets.UTF_8)) {
            String linea;
            int numeroLinea = 0;
            while ((linea = br.readLine()) != null) {
                try {
                    numeroLinea++;
                    Alumno a = ParseadorAlumnos.parsear(linea); // Se asigna a null si algún campo no es válido.

                    if (a == null) {
                        System.err.println("ERROR (Linea " + numeroLinea + "): El registro '" + linea + "' contiene uno o más datos inválidos. No se añade al listado.");
                        continue;
                    }

                    if (alumnos.contains(a)) {
                        System.err.println("ERROR (Linea " + numeroLinea + "): El listado ya contiene el alumno '" + a + "'. No se añade al listado.");
                        continue;
                    }

                    alumnos.add(a);
                } catch (NumeroCamposIncorrectoException e) {
                    System.err.println("ERROR (Linea " + numeroLinea + "): No se han podido leer los campos del registro '" + linea + "'. " + e.getMessage());
                    System.err.println("Se procede sin añadir el registro al listado.");
                } catch (TipoDatoIncorrectoException e) {
                    System.err.println("ERROR (Linea " + numeroLinea + "): El registro '" + linea + "' contiene tipos de datos incorrectos. " + e.getMessage());
                    System.err.println("Se procede sin añadir el registro al listado.");
                }
            }
        } catch (IOException | SecurityException e) {
            System.err.println("ERROR: No se ha podido acceder al fichero '" + entrada + "'. " + e.getMessage());
        }

        System.out.println(alumnos.size() + " alumnos en la lista.");
        return alumnos;
    }

    /**
     * Ejercicio 6 (3 p.): fusionar, ordenar, CREATE_NEW y segunda escritura.
     */
    private static void ejercicio6FusionarYGenerar(
            List<Alumno> alumnos2, List<Alumno> alumnos3, Alumno alumno1, Path salida) {
        // TODO: combinar 2 y 3 (sin duplicados por ID) y ordenar con el
        // orden natural de Alumno (Comparable).
        // Primera escritura: alumnos_ordenados.csv con CREATE_NEW.
        // Si el fichero YA existía, NO tocarlo ni hacer la Parte B.
        // SOLO si se creó: incorporar alumno1 si no está repetido, volver
        // a ordenar y SOBREESCRIBIR/TRUNCAR el fichero con la colección completa.
        // Cada línea debe obtenerse con alumno.toCsv().

        // Inicializa la lista añadiendo todos los elementos de alumnos2.
        List<Alumno> alumnos = combinarAlumnos(alumnos2, alumnos3);

        Path ficheroSalida = salida.resolve("alumnos_ordenados.csv");
        try (BufferedWriter bw = Files.newBufferedWriter(ficheroSalida, StandardCharsets.UTF_8, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE)) {
            alumnos.sort(Alumno::compareTo); // Ordena mediante comparación natural.
            for (Alumno a : alumnos) {
                bw.write(a.toCsv());
                bw.newLine();
            }
        } catch (FileAlreadyExistsException e) {
            System.err.println("ERROR: El fichero '" + ficheroSalida + "' ya existe. " + e.getMessage());
            return;
        } catch (IOException | SecurityException e) {
            System.err.println("ERROR: No se ha podido escribir en el fichero '" + ficheroSalida + "'.");
            return;
        }

        // Si no contiene. TODO lógica.
        alumnos = combinarAlumnos(alumnos, alumno1);

        try (BufferedWriter bw = Files.newBufferedWriter(ficheroSalida, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)) {
            alumnos.sort(Alumno::compareTo);
            for (Alumno a : alumnos) {
                bw.write(a.toCsv());
                bw.newLine();
            }
        } catch (FileAlreadyExistsException e) {
            System.err.println("ERROR: El fichero '" + ficheroSalida + "' ya existe. " + e.getMessage());
        } catch (IOException | SecurityException e) {
            System.err.println("ERROR: No se ha podido escribir en el fichero '" + ficheroSalida + "'.");
        }
    }

    private static List<Alumno> combinarAlumnos(List<Alumno> alumnos1, List<Alumno> alumnos2) {
        List<Alumno> alumnos = new ArrayList<>(alumnos1); // Crea una lista con todos los alumnos de la lista1
        alumnos2.forEach( alumno -> {
            if (!alumnos.stream().map(Alumno::getId).toList().contains(alumno.getId())) { // Crea una lista mediante programación funcional con todos los ids de los alumnos y comprueba uno a uno si ya existe el que se intenta insertar
                alumnos.add(alumno);
            } else {
                System.out.println("Alumno con id " + alumno.getId() + " ya existe. No se añade a la lista.");
            }
        });
        return alumnos;
    }

    private static List<Alumno> combinarAlumnos(List<Alumno> alumnos1, Alumno alumno) {
        List<Alumno> alumnos = new ArrayList<>(alumnos1); // Crea una lista con todos los alumnos de la lista1
        if (!alumnos.stream().map(Alumno::getId).toList().contains(alumno.getId())) { // Crea una lista mediante programación funcional con todos los ids de los alumnos y comprueba si ya existe el que se intenta insertar
            alumnos.add(alumno);
        } else {
            System.out.println("Alumno con id " + alumno.getId() + " ya existe. No se añade a la lista.");
        }
        return alumnos;
    }
}
