# 7. Recuperar datos estructurados

El fichero no almacena nombres de variables ni etiquetas de tipos Java. Solo bytes.
**El programa debe conocer el formato y respetar los tipos y su orden.**

## Tu reto

El recurso `cliente.bin` incluido contiene el identificador `27`, el nombre `Lucía`, el teléfono `600123456`, la matrícula `1234ABC`, el saldo `1532.75` y el estado `true`.
Completa las seis lecturas dentro del `try-with-resources`, respetando el orden utilizado al escribir el registro: `readInt`, tres llamadas a `readUTF`, `readDouble` y `readBoolean`.
Cada lectura avanza por el flujo. Intercambiar dos puede causar una excepción o producir valores incoherentes; no hay detección automática de tipos.

El método propaga `IOException`; `main` la captura y muestra un mensaje. Si el fichero acaba antes de completar los campos, `DataInputStream` lanza `EOFException`, que es una subclase de `IOException`. No fabriques un cliente vacío para ocultarlo.

## Comprueba y explica

Ejecuta el ejemplo y comprueba los seis campos. Explica por qué el orden es parte del formato y por qué conservamos el cierre automático.

<details><summary>Pista</summary>Empareja cada read con el write de la actividad anterior. readUTF ya consume la longitud del texto.</details>

Completa los huecos de `src/Main.java`, ejecuta el ejemplo y pulsa **Check**. Si falla, lee el mensaje y vuelve a intentarlo. Después continúa con **Next**.
