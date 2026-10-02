# 8. Proyecto final: modificar un BMP

Vas a cambiar un píxel real. Abre `imagen.bmp`: es una imagen pequeña de **5 × 4**, 24 bits y sin compresión. Cada fila ocupa 16 bytes: 15 de color y uno de relleno.

Completa **A → B → C → D → E**. Puedes pulsar Check tras cada paso; el conjunto quedará aprobado cuando estén resueltos todos. La validación de entradas ya está proporcionada para centrarte en los bytes.

## A. Reconocer la firma

`tieneFirmaBmp` debe comprobar los bytes `B` y `M`. Conserva la comprobación de longitud anterior al acceso al array.
Una firma correcta es necesaria, pero no garantiza que todo el archivo sea válido.

## B. Interpretar la cabecera

BMP guarda estos números en **little-endian**: el byte menos significativo aparece primero.
Configura `ByteBuffer` con ese orden antes de leer. Las posiciones son desplazamientos absolutos desde el comienzo del fichero.

| Posición | Tipo y significado |
| --- | --- |
| 10 | `int`: comienzo de píxeles |
| 18 | `int`: ancho |
| 22 | `int`: alto con signo |
| 28 | `short` sin signo: bits por píxel |
| 30 | `int`: compresión |

Usa `getInt(posicion)` y `Short.toUnsignedInt(buffer.getShort(posicion))`.
El validador admite la variante con cabecera DIB de 40 bytes, un plano, 24 bits y compresión 0. No estamos implementando un lector universal de BMP.

## C. Localizar un píxel

Cada píxel ocupa tres bytes. Las filas se completan hasta un múltiplo de cuatro:

```java
((ancho * 3L + 3) / 4) * 4
```

Nuestra coordenada `(0, 0)` está arriba a la izquierda. Con alto positivo, la primera fila almacenada es la inferior: usa `altoReal - 1 - y`. Con alto negativo, usa `y`.

La posición es **comienzo de píxeles + fila almacenada × bytes por fila + x × 3**.
Usamos `long` en los cálculos y `Math.toIntExact` al volver al índice del array para evitar desbordamientos silenciosos.

## D. Pintar en BGR

En disco el orden es azul, verde y rojo: `datos[p]`, `datos[p + 1]`, `datos[p + 2]`.
Convierte cada componente a `byte`; 255 se representa como `(byte) 255`.
No cambies cabecera, relleno ni ningún otro píxel.

## E. Guardar la copia

Completa la escritura de `datos` en `destino`. Ejecuta el ejemplo y abre `salida/imagen_modificada.bmp`: el píxel `(2, 3)` será rojo. Amplía la imagen para verlo.
La original debe conservarse. Los tests comprueban también otros colores, anchos y ambas orientaciones.

## Para pensar y ampliar

¿Por qué no serviría esta fórmula para PNG o JPEG comprimidos? ¿Qué ventaja tendría un canal posicionable si la imagen fuera enorme?
Un `Files.newByteChannel` permite situarse con `position`, pero hay que cerrarlo con `try-with-resources`; además, sus lecturas y escrituras pueden requerir bucles para completar todos los bytes.

Después de superar la tarea puedes dibujar una línea o invertir colores con `255 - componente`. Estos retos no forman parte de Check.


Completa los huecos de `src/Main.java`, ejecuta el ejemplo y pulsa **Check**. Si falla, lee el mensaje y vuelve a intentarlo. Al superarla habrás completado el curso.
