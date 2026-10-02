# 6. Escribir datos estructurados

Ahora definiremos nuestro propio formato. El orden acordado es:

| Dato | Método | Representación |
| --- | --- | --- |
| id | `writeInt` | 4 bytes, big-endian |
| nombre | `writeUTF` | longitud de 2 bytes + UTF-8 modificado |
| teléfono | `writeUTF` | longitud de 2 bytes + UTF-8 modificado |
| matrícula | `writeUTF` | longitud de 2 bytes + UTF-8 modificado |
| saldo | `writeDouble` | 8 bytes |
| activo | `writeBoolean` | 1 byte |

`Cliente` contiene el identificador, nombre, teléfono y matrícula definidos para los clientes de la P1. Añadimos `saldo` y `activo` para practicar también la escritura de un `double` y un `boolean`.

**`writeUTF` no escribe el UTF-8 estándar de la tarea anterior**: usa UTF-8 modificado y antepone su longitud. Se recupera con `readUTF`; limita el contenido codificado a 65 535 bytes.

## Tu reto

Completa las seis escrituras dentro del `try`, respetando el orden de la tabla. Usa los datos de `cliente`, no valores fijos.
El `record Cliente` es un contenedor ya proporcionado; `cliente.id()` consulta su identificador.

`Files.newOutputStream` abre un recurso. El `try-with-resources` proporcionado cierra `out` y su flujo subyacente incluso si ocurre una excepción: **conserva esta estructura**.
No hay que añadir un `close` manual. A diferencia de este flujo, `Files.write` resolvía la operación completa en una llamada.

<details><summary>Pista</summary>Usa un write por cada campo, con el método de la tabla y el accesor del record correspondiente.</details>

Completa los huecos de `src/Main.java`, ejecuta el ejemplo y pulsa **Check**. Si falla, lee el mensaje y vuelve a intentarlo. Después continúa con **Next**.
