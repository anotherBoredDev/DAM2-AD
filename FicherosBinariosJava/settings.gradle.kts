rootProject.name = "Ficheros binarios con Java"

// Un módulo independiente por tarea, siguiendo la plantilla de JetBrains Academy.
file("ficherosBinarios").listFiles()!!.filter { File(it, "src").isDirectory }.sorted().forEach {
    val moduleName = "ficherosBinarios-${it.name}"
    include(moduleName)
    project(":$moduleName").projectDir = it
}
