package es.iesluisbraille.accesodatos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Solo se completan los seis métodos TODO. Consola y coordinación resueltas.
 */
public final class ExamenRA1B {
    private static final Path DIRECTORIO_ENTRADA = Path.of("datos", "entrada");
    private static final Path FICHERO_1 = DIRECTORIO_ENTRADA.resolve("arboles1.jsonl");
    private static final Path FICHERO_2 = DIRECTORIO_ENTRADA.resolve("arboles2.jsonl");
    private static final Path FICHERO_3 = DIRECTORIO_ENTRADA.resolve("arboles3.jsonl");
    private static final Path FICHERO_ID = DIRECTORIO_ENTRADA.resolve("id_arbol.bin");
    public static final String SALIDA_NOMBRE = "arbol1.txt";
    public static final String SALIDA_FINAL = "arboles_ordenados.jsonl";

    private ExamenRA1B() {
    }

    public static void main(String[] args) {
        System.out.println("=== EXAMEN B: ÁRBOLES Y FICHEROS JSON ===");
        System.out.println("Directorio de entrada: " + DIRECTORIO_ENTRADA.toAbsolutePath());

        try (Scanner teclado = new Scanner(System.in)) {
            System.out.print("Introduce la ruta del directorio de salida: ");
            String ruta = teclado.nextLine().trim();
            if (ruta.isEmpty()) {
                System.err.println("La ruta de salida no puede estar vacía.");
                return;
            }
            Path salida;
            try {
                salida = Path.of(ruta);
            } catch (InvalidPathException e) {
                System.err.println("Ruta de salida incorrecta: " + e.getMessage());
                return;
            }
            if (!prepararDirectorioSalida(salida)) {
                System.err.println("No se puede continuar sin un directorio válido.");
                return;
            }

            Arbol arbol1 = ejercicio2LeerYEscribirArbol(FICHERO_1, salida);
            HashMap<Integer, Arbol> arboles2 = ejercicio3LeerArboles(FICHERO_2);
            ejercicio4LeerIdYMostrarArbol(FICHERO_ID, arboles2);
            HashMap<Integer, Arbol> arboles3 = ejercicio5LeerArbolesConErrores(FICHERO_3);
            ejercicio6FusionarYGenerar(arboles2, arboles3, arbol1, salida);
        }
    }

    /**
     * Ejercicio 1 (1 punto): comprobar/crear directorio e informar de errores.
     */
    private static boolean prepararDirectorioSalida(Path salida) {
        try {
            if (Files.exists(salida)) {
                if (Files.isDirectory(salida)) {
                    System.out.println("El directorio de salida '" + salida + "' ya existía.");
                    return true;
                }
                System.out.println("ERROR: Ya existe un fichero con el nombre '" + salida + "'.");
                return false;
            }

            // Files.createDirectories también crea los directorios intermedios necesarios.
            Files.createDirectories(salida);
            System.out.println("La ruta '" + salida + "' se ha creado exitosamente.");
            return true;
        } catch (IOException | SecurityException e) {
            System.out.println("ERROR: No se ha podido crear el directorio '" + salida + "'. " + e.getMessage());
            return false;
        }
    }

    /**
     * Ejercicio 2 (1 punto): leer el árbol y escribir su nombre en arbol1.txt.
     */
    private static Arbol ejercicio2LeerYEscribirArbol(Path entrada, Path salida) {
        // TODO: leer en UTF-8 y usar ParseadorArboles.parsear(linea).
        // Escribir getNombre() y un salto de línea. Devolver el árbol válido.
        // Si falla la escritura, conservar el árbol leído para el ejercicio 6.
        try (BufferedReader bf = Files.newBufferedReader(entrada, StandardCharsets.UTF_8)) {
            Arbol a = ParseadorArboles.parsear(bf.readLine());

            try {
                if (a != null && prepararDirectorioSalida(salida)) {
                    Files.writeString(salida.resolve(SALIDA_NOMBRE), a.getNombre() + System.lineSeparator(), StandardCharsets.UTF_8);
                }
            } catch (IOException e) {
                System.out.println("ERROR: No se ha podido escribir en el fichero '" + salida + "'. " + e.getMessage());
            }

            return a;

        } catch (TipoDatoIncorrectoException e) {
            System.out.println("ERROR: Se ha encontrado un tipo de dato incorrecto al pasear el fichero JSONL '" + entrada + "'.");
        } catch (FormatoJsonIncorrectoException e) {
            System.out.println("ERROR: El fichero '" + entrada + "' no contiene un formato JSONL válido.");
        } catch (IOException e) {
            System.out.println("ERROR: No se ha podido leer el fichero '" + entrada + "'. " + e.getMessage());
        }
        return null;
    }

    /**
     * Ejercicio 3 (2 puntos): leer 200 objetos JSON en un HashMap por ID.
     */
    private static HashMap<Integer, Arbol> ejercicio3LeerArboles(Path entrada) {
        // TODO: leer línea a línea en UTF-8, parsear e incorporar al mapa.
        // La clave es getId(). Gestionar recursos y errores, e informar del total.
        HashMap<Integer, Arbol> arboles = new HashMap<>();
        String linea;
        try (BufferedReader bf = Files.newBufferedReader(entrada)) {
            while ((linea = bf.readLine()) != null) {
                Arbol a = ParseadorArboles.parsear(linea);
                if (a == null) {
                    System.out.println("ERROR: Se ha producido un error al parsear el registro JSONL '" + linea + "'.");
                    continue;
                }
                arboles.put(a.getId(), a);
            }
        } catch (TipoDatoIncorrectoException e) {
            System.out.println("ERROR: Se ha encontrado un tipo de dato incorrecto al pasear el fichero JSONL '" + entrada + "'. " + e.getMessage());
        } catch (FormatoJsonIncorrectoException e) {
            System.out.println("ERROR: El fichero '" + entrada + "' no contiene un formato JSONL válido. " + e.getMessage());
        } catch (IOException e) {
            System.out.println("ERROR: No se ha podido leer el fichero '" + entrada + "'. " + e.getMessage());
        }

        System.out.println("Se han incorporado " + arboles.size() + " árboles al mapa.");
        return arboles;
    }

    /**
     * Ejercicio 4 (1 punto): leer un entero binario y mostrar el árbol con ese ID.
     */
    private static void ejercicio4LeerIdYMostrarArbol(
            Path ficheroId, HashMap<Integer, Arbol> arboles) {
        // TODO: leer un entero binario de 32 bits (formato de DataInputStream.readInt).
        // Buscar por clave y mostrar el árbol con su toString(), o indicar que no existe.
        // Gestionar recursos y errores, incluido un fichero incompleto.
        try (DataInputStream dos = new DataInputStream(Files.newInputStream(ficheroId))) {
            int id = dos.readInt();
            if (arboles.containsKey(id)) {
                Arbol a = arboles.get(id);
                System.out.println(a);
            } else {
                System.out.println("ERROR: No existe un árbol con el id " + id + ".");
            }
        } catch (IOException e) {
            System.out.println("ERROR: No se ha podido leer el fichero '" + ficheroId + "'. " + e.getMessage());
        }
    }

    /**
     * Ejercicio 5 (2 puntos): errores por línea y duplicados dentro del fichero 3.
     */
    private static HashMap<Integer, Arbol> ejercicio5LeerArbolesConErrores(Path entrada) {
        HashMap<Integer, Arbol> arboles = new HashMap<>();
        // TODO: leer en UTF-8 y tratar separadamente las dos excepciones y null.
        // Conservar la primera aparición de cada ID. Informar de línea y causa
        // para cada descarte; continuar con la siguiente línea. Mostrar el total.
        String linea;
        int numeroLinea = 0;
        try (BufferedReader bf = Files.newBufferedReader(entrada)) {
            while ((linea = bf.readLine()) != null) {
                numeroLinea++;
                try {
                    Arbol a = ParseadorArboles.parsear(linea);
                    if (a == null) {
                        System.out.println("ERROR: Se ha producido un error al parsear el registro JSONL '" + linea + "'.");
                        System.out.println("Se ha descartado el registro de la linea " + numeroLinea + ".");
                        continue;
                    }

                    if (arboles.containsKey(a.getId())) {
                        System.out.println("ERROR: Ya existe un registro en el mapa con el id " + a.getId() + ".");
                        System.out.println("Se ha descartado el registro de la linea " + numeroLinea + ".");
                        continue;
                    }

                    arboles.put(a.getId(), a);
                } catch (TipoDatoIncorrectoException e) {
                    System.out.println("ERROR: Se ha encontrado un tipo de dato incorrecto al pasear el fichero JSONL '" + entrada + "'. " + e.getMessage());
                    System.out.println("Se ha descartado el registro de la linea " + numeroLinea + ".");
                } catch (FormatoJsonIncorrectoException e) {
                    System.out.println("ERROR: El fichero '" + entrada + "' no contiene un formato JSONL válido. " + e.getMessage());
                    System.out.println("Se ha descartado el registro de la linea " + numeroLinea + ".");
                }
            }
        } catch (IOException e) {
            System.out.println("ERROR: No se ha podido leer el fichero '" + entrada + "'. " + e.getMessage());
        }

        System.out.println("Se han incorporado " + arboles.size() + " árboles al mapa.");
        return arboles;
    }

    /**
     * Ejercicio 6 (3 puntos): CREATE_NEW y actualización completa del fichero.
     */
    private static void ejercicio6FusionarYGenerar(
            HashMap<Integer, Arbol> arboles2, HashMap<Integer, Arbol> arboles3,
            Arbol arbol1, Path salida) {
        // TODO: usar OperacionesArboles.combinar(arboles2, arboles3).
        // Recorrer OperacionesArboles.ordenar(mapa) y escribir toJson() por línea.
        // Primera escritura: SALIDA_FINAL, UTF-8 y CREATE_NEW.
        // Si ya existe o la primera escritura falla, no realizar la Parte B.
        // Solo tras crear Y terminar la primera escritura: llamar a
        // incorporarSiAusente(mapa, arbol1), ordenar y reescribir todo truncando.
        HashMap<Integer, Arbol> mapa = OperacionesArboles.combinar(arboles2, arboles3);
        Path ficheroSalida = salida.resolve(SALIDA_FINAL);
        try (BufferedWriter bw = Files.newBufferedWriter(ficheroSalida, StandardCharsets.UTF_8, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE)) {
            for (Arbol a : OperacionesArboles.ordenar(mapa)) {
                bw.write(a.toJson());
                bw.newLine();
            }
        } catch (FileAlreadyExistsException e) {
            System.out.println("ERROR: El fichero '" + ficheroSalida + "' ya existe; se conserva intacto.");
            return;
        } catch (IOException e) {
            System.out.println("ERROR: No se ha podido escribir en el fichero '" + salida + "'. " + e.getMessage());
            return;
        }

        System.out.println("Primera operación completada con " + mapa.size() + " árboles.");

        OperacionesArboles.incorporarSiAusente(mapa, arbol1);
        try (BufferedWriter bw = Files.newBufferedWriter(ficheroSalida, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)) {
            for (Arbol a : OperacionesArboles.ordenar(mapa)) {
                bw.write(a.toJson());
                bw.newLine();
            }
            System.out.println("Segunda operación completada con " + mapa.size() + " árboles.");
        } catch (IOException e) {
            System.out.println("ERROR: No se ha podido escribir en el fichero '" + salida + "'. " + e.getMessage());
        }

    }
}
