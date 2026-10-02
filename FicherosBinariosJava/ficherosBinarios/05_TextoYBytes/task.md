# 5. Texto y bytes UTF-8

La codificación establece la correspondencia entre texto y bytes.
En UTF-8 no todos los caracteres ocupan un byte: `í` ocupa dos.
Un archivo llamado `mensaje.bin` puede contener texto UTF-8; la extensión no transforma su contenido.

## Tu reto: un viaje de ida y vuelta

1. Convierte `texto` a bytes con `getBytes` y `StandardCharsets.UTF_8`.
2. Guarda esos bytes en `ruta`.
3. Lee el fichero y conserva el resultado en `recuperados`.
4. Construye el texto con `new String(bytes, codificacion)` y devuélvelo.

Las pruebas comprobarán tanto los **bytes del archivo** como el texto recuperado, incluyendo tildes y otros caracteres.
No basta con devolver el parámetro `texto`.

<details><summary>Pista</summary>Usa la misma codificación explícita al codificar y decodificar; evita depender de la configuración del sistema.</details>

Completa los huecos de `src/Main.java`, ejecuta el ejemplo y pulsa **Check**. Si falla, lee el mensaje y vuelve a intentarlo. Después continúa con **Next**.
