# 2. Un byte, tres interpretaciones

Un byte son ocho bits. Esos bits no indican por sí solos si representan una letra o un número: el programa y el formato deben acordar cómo interpretarlos.

| Bits | Como ASCII | Como `byte` con signo | Como número sin signo |
| --- | --- | ---: | ---: |
| `01000001` | `A` | 65 | 65 |
| `11111111` | No es ASCII de 7 bits | −1 | 255 |

ASCII representa directamente los valores del 0 al 127. Java presenta `byte` como un número con signo entre −128 y 127; al interpretarlo sin signo, los mismos ocho bits corresponden a un valor entre 0 y 255.

## Tu reto

Completa los tres métodos de `src/Main.java`:

- `comoAscii` convierte los bytes a texto usando `StandardCharsets.US_ASCII`.
- `comoNumerosConSigno` devuelve cada valor como lo interpreta Java en un `byte`.
- `comoNumerosSinSigno` devuelve los mismos bits entre 0 y 255. Puedes usar `Byte.toUnsignedInt`.

No cambies los arrays del ejemplo. Para los bytes `65`, `-1` y `-56`, las dos interpretaciones numéricas deben producir respectivamente `[65, -1, -56]` y `[65, 255, 200]`. El ejemplo ASCII usa únicamente bytes del rango válido.

<details><summary>Pista</summary>Recorre el array para construir los dos arrays numéricos. En la versión sin signo convierte cada elemento por separado; no basta con convertir el array completo.</details>

Completa los huecos de `src/Main.java`, ejecuta el ejemplo y pulsa **Check**. Si falla, lee el mensaje y vuelve a intentarlo. Después continúa con **Next**.
