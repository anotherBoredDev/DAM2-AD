# Instrucciones de ejecución

* Escrito en Java 21. 
* Código ubicado en src/
* En IntelliJ IDEA ejecutar método main dentro de la clase Main.java

# Decisiones de diseño

**Main** sirve como punto de entrada al programa: Instancia los objetos necesarios para su ejecución e inyecta dependencias.

Main llama a **MenuPrincial**, clase que sirve como intermediario entre el usuario y el programa. Se encarga de saber sobre los gestores de clientes y pagos y llama sus métodos a demanda del usuario, pero no gestiona nada la lógica de negocio, no es su responsabilidad.

MenuPrincipal hace uso de **LectorConsola**, cuyo único propósito es obtener la entrada del usuario, limpiarla y formatearla, y controlar las excepciones relacionadas de forma segura.

**ClienteGestor** y un **PagoGestor** son la lógica de negocio. Estos gestores son los que se encargan de realizar las operaciones con clientes y pagos respectivamente, como registrar uno nuevo, obtener un listado de ellos o buscarlos por diferentes criterios.

**Almacenamiento** es la interfaz encargada de la permanencia de datos. Es una interfaz porque así sus implementaciones siempre tendrán, al menos, los métodos que esta defina. Eso permite que existan varias implementaciones y alternar entre ellas sea tan fácil como modificar el objeto que se crea dentro del gestor correspondiente.  
Además, es una interfaz que utiliza **tipos genéricos**. El tipo de entidad que maneja la interfaz no se define hasta que se implementa.

**ClienteCsvAlmacenamiento** y **PagoCsvAlmacenamiento** son implementaciones de Almacenamiento. Especifícan cómo se van a guardar los datos que maneja, en este caso, las entidades son Clientes y Pagos y se guardan en ficheros con formato csv.

**Cliente** y **Pago** son objetos que representan a los clientes y pagos que gestiona la gasolinera en la vida real. Contienen atributos con los datos relevantes de cada uno.