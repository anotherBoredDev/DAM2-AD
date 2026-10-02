# 3. Escribir un fichero binario

`Path` representa una ruta; `Files` ofrece operaciones sobre ficheros.
`Files.write(ruta, bytes)` escribe un array completo y cierra internamente el fichero.
Sin opciones adicionales, crea el archivo o reemplaza su contenido anterior.

## Tu reto

Completa `guardar` con una llamada a `Files.write`. Usa sus parámetros: no fijes el nombre del archivo ni los datos dentro del método.
Ejecuta el ejemplo dos veces: `salida/datos.bin` debe seguir ocupando **4 bytes**, no 8.
También debe reemplazar un fichero anterior más largo, sin dejar bytes sobrantes.

`IOException` comunica un fallo de entrada/salida al código que llama al método.
En este primer ejemplo dejamos que `main` la propague para ver el error completo.

<details><summary>Pista</summary>El orden de argumentos es la ruta y después el array. No uses APPEND.</details>

Completa los huecos de `src/Main.java`, ejecuta el ejemplo y pulsa **Check**. Si falla, lee el mensaje y vuelve a intentarlo. Después continúa con **Next**.
