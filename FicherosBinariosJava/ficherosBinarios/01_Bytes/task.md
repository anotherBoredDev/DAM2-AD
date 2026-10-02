# 1. Qué es un byte

Un fichero es una secuencia de bytes. Un byte tiene ocho bits: 256 combinaciones.
Java interpreta `byte` entre −128 y 127; un formato puede interpretar esos mismos bits entre 0 y 255.
El moldeado `(byte) 255` conserva los ocho bits, aunque Java muestre −1.

## Tu reto

Completa `sinSigno`: debe devolver el valor de **cualquier** byte entre 0 y 255.
La clase `Byte` tiene un método `toUnsignedInt(byte)` para hacerlo.
Ejecuta `Main`: al terminar debes ver `0`, `1`, `127`, `128`, `255`, una cifra por línea.

## Antes de continuar

Los bytes `72 111 108 97` pueden interpretarse como «Hola» en UTF-8.
La extensión del archivo no cambia sus bytes. ¿Qué información necesitamos para interpretarlos?

<details><summary>Pista</summary>Invoca el método estático sobre la variable dato, sin cambiar el array de ejemplo.</details>

Completa los huecos de `src/Main.java`, ejecuta el ejemplo y pulsa **Check**. Si falla, lee el mensaje y vuelve a intentarlo. Después continúa con **Next**.
