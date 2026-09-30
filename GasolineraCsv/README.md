# Instrucciones de ejecución

* Escrito en Java 21. 
* Código ubicado en src/
* En IntelliJ IDEA ejecutar método main dentro de la clase Main.java

# Decisiones de diseño

Main sirve como punto de entrada al programa: Instancia los objetos necesarios para su ejecución e inyecta dependencias.

MenuPrincipal muestra al usuario una interfaz por consola y gestiona sus interacciones con el programa.

LectorConsola es una clase ayudante. Sirve para leer y limpiar la entrada del usuario por consola y controlar posibles excepciones.