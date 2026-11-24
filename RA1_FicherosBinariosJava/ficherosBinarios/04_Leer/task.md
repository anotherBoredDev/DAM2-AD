# 4. Leer sin modificar

Leer convierte los bytes del fichero en un `byte[]` dentro de la memoria.
`Files.readAllBytes(ruta)` realiza esa operación y cierra internamente el fichero.

## Tu reto

Completa `leer`. El archivo `datos.bin` ya está incluido: esta tarea no depende de ejecutar la anterior.
Devuelve todos sus bytes en el mismo orden y **no lo modifiques**.
El ejemplo muestra valores con signo; para ver 0…255 puedes reutilizar `Byte.toUnsignedInt`.

Si el fichero no existe, deja que se propague `IOException`: devolver un array vacío ocultaría el fallo y lo confundiría con un fichero vacío real.

## Límite de esta técnica

`readAllBytes` carga todo el fichero en memoria. Resulta cómodo para estos ejemplos pequeños.
Para archivos grandes se pueden procesar bloques con un flujo; para saltar a una posición se puede usar un canal. Estos recursos sí deben cerrarse.

<details><summary>Pista</summary>Devuelve el resultado de una sola llamada a Files. No escribas ni crees el archivo dentro de leer.</details>

Completa los huecos de `src/Main.java`, ejecuta el ejemplo y pulsa **Check**. Si falla, lee el mensaje y vuelve a intentarlo. Después continúa con **Next**.
