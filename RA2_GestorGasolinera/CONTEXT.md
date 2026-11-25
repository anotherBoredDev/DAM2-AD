# Contexto del proyecto: GestorGasolinera

## Propósito y estado

Aplicación de consola para registrar y consultar clientes y pagos de repostaje. Mantiene los datos en memoria mientras se ejecuta y los persiste en ficheros CSV o JSON.

La aplicación actual no utiliza una base de datos ni implementa acceso JDBC.

## Tecnologías y organización

- Java 21 y Maven.
- Las clases de la aplicación están en el paquete
  `es.iesluisbraille.accesodatos`.
- Los ficheros de datos de la ejecución normal se guardan en `datos/`.
- No hay una separación de clases mediante subpaquetes: las responsabilidades se
  distinguen por las clases y sus colaboraciones.

## Componentes

### `Main`

Punto de entrada y lugar donde se construyen y conectan las dependencias:

1. Crea un `Scanner` y un `LectorConsola`.
2. Crea los almacenamientos JSON de clientes y pagos, configurados para `datos/`.
3. Si falta alguno de los dos ficheros JSON, ofrece realizar una migración desde
   CSV.
4. Crea `ClienteGestor` y `PagoGestor`, inyectándoles los almacenamientos JSON.
5. Crea e inicia `MenuPrincipal`, inyectándole los gestores y el lector.
6. Cierra el `Scanner` al finalizar.

También contiene el flujo de interacción que pregunta si se desea migrar y,
cuando corresponde, solicita el directorio de los ficheros CSV.

### `MenuPrincipal`

Controla el flujo de interacción por consola: muestra las opciones, recoge los
datos necesarios, llama a los gestores y presenta sus resultados.

Además de actuar como puente entre usuario y gestores, actualmente contiene
responsabilidades de presentación, como ordenar y formatear listados. También
realiza algunas comprobaciones asociadas al flujo de uso; por ejemplo, antes de
procesar un pago comprueba que haya clientes y que exista el cliente seleccionado.

No realiza la carga ni el guardado de las colecciones persistentes.

### `LectorConsola`

Helper utilizado por `MenuPrincipal` y por el flujo de migración para leer
entradas y repetir la petición cuando el formato no es válido. Ofrece lectura de
cadenas, enteros, decimales y fechas.

### `ClienteGestor` y `PagoGestor`

Mantienen colecciones de sus entidades en memoria. Al construirse, cargan esas
colecciones llamando a `obtenerTodos()` del almacenamiento recibido.

- `ClienteGestor` registra clientes, calcula el siguiente identificador,
  normaliza la matrícula a mayúsculas, evita matrículas duplicadas y permite
  buscar clientes.
- `PagoGestor` registra pagos y calcula el siguiente identificador.
- Ambos guardan la colección completa mediante `guardarTodos()` al finalizar la
ejecución.

Los gestores reciben la interfaz `Almacenamiento<T>`, no una implementación
específica, lo que permite conectarlos con las implementaciones CSV o JSON.

Las consultas de colección devuelven las colecciones mantenidas internamente por
los gestores; no se entrega una copia defensiva.

### `Almacenamiento<T>`

Interfaz genérica para leer y guardar datos. Define operaciones para comprobar si
existe el fichero de almacenamiento, guardar una entidad, guardar una colección
y obtener todas las entidades.

No define operaciones de actualización, borrado ni búsqueda por identificador.

### Implementaciones de almacenamiento

Hay implementaciones CSV y JSON para cada entidad:

- `ClienteCsvAlmacenamiento`
- `ClienteJsonAlmacenamiento`
- `PagoCsvAlmacenamiento`
- `PagoJsonAlmacenamiento`

Cada implementación determina la ruta y el formato de su fichero. Las
implementaciones JSON serializan y leen el formato manualmente; no utilizan una
biblioteca JSON.

En el flujo normal, `Main` configura las implementaciones JSON con el directorio
`datos/`. Los gestores guardan las colecciones completas al salir. Los
almacenamientos también ofrecen `guardar(T)`, aunque los gestores actuales no
lo usan para persistir cada alta inmediatamente.

Los errores de entrada/salida de los almacenamientos se comunican mediante
mensajes en consola; la interfaz no define un resultado o una excepción de
almacenamiento común.

### Entidades

- `Cliente`: contiene `id`, `nombre`, `telefono` y `matricula`.
- `Pago`: contiene `id`, `idCliente`, `fecha`, `importe`, `litros` y
  `combustible`.

Ambas clases tienen atributos finales y se construyen con todos sus datos. Los
identificadores se calculan en los gestores buscando el mayor identificador
cargado y sumándole uno.

`Pago` representa un pago asociado a un cliente mediante su identificador, no
mediante una referencia directa a un objeto `Cliente`.

### Migración CSV a JSON

`MigracionCsvToJson` coordina la lectura de clientes y pagos mediante las
implementaciones CSV y su escritura mediante las implementaciones JSON.
`ResultadoMigracion` es un `record` con el número de clientes y pagos migrados,
que `Main` muestra al usuario.

La clase de migración construye directamente las implementaciones concretas CSV
y JSON; no recibe esos almacenes mediante la interfaz `Almacenamiento<T>`.
Migra cada conjunto solo si no existe el fichero JSON correspondiente y devuelve
cero como cantidad migrada cuando ese fichero ya existe.

## Flujo de datos durante una ejecución

1. Los almacenamientos JSON se configuran en `datos/`.
2. Los gestores cargan desde ellos los clientes y pagos al construirse.
3. `MenuPrincipal` recoge las acciones y entradas del usuario y delega las
   operaciones en los gestores.
4. Los cambios se mantienen en las colecciones en memoria.
5. Al elegir salir, `MenuPrincipal` llama a `finalizarEjecucion()` en ambos
   gestores.
6. Cada gestor guarda su colección completa en el almacenamiento JSON.

## Particularidades y limitaciones del código actual

- Si falta cualquiera de los ficheros JSON, se ofrece migrar. Sin embargo,
  después de migrar, `Main` continúa utilizando los almacenamientos JSON que
  había creado al inicio, configurados en `datos/`. Si el usuario indicó otro
  directorio para la migración, los ficheros migrados pueden quedar en ese otro
  directorio y no ser los que utilizan los gestores durante esa ejecución.
- La migración conoce directamente las clases concretas de almacenamiento CSV y
  JSON, y su decisión de ejecución se coordina desde `Main`.
- Los gestores entregan sus colecciones internas a quien las consulta; por
  tanto, quien recibe una colección puede modificarla.
- La persistencia ordinaria se realiza al salir guardando la colección completa,
  no inmediatamente después de cada operación.
- Las entidades y los pagos usan `float` para importes y litros.

Estas particularidades describen el comportamiento actual; no constituyen una
propuesta de refactorización.
