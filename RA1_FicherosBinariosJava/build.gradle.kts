plugins { java }

allprojects { repositories { mavenCentral() } }

subprojects {
    apply(plugin = "java")
    extensions.configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    extensions.configure<SourceSetContainer> {
        named("main") { java.setSrcDirs(listOf("src")) }
        named("test") { java.setSrcDirs(listOf("test")) }
    }
    dependencies {
        "testImplementation"("org.junit.jupiter:junit-jupiter-api:6.1.3")
        "testImplementation"("org.junit.jupiter:junit-jupiter-params:6.1.3")
        "testRuntimeOnly"("org.junit.jupiter:junit-jupiter-engine:6.1.3")
        "testRuntimeOnly"("org.junit.platform:junit-platform-launcher:6.1.3")
    }
    tasks.withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
        options.release.set(21)
    }
    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
        workingDir = projectDir
        outputs.upToDateWhen { false }
        // Protocolo de feedback de la plantilla oficial: lo consume el botón Check.
        addTestListener(object : TestListener {
            override fun beforeSuite(suite: TestDescriptor) {}
            override fun beforeTest(testDescriptor: TestDescriptor) {}
            override fun afterSuite(suite: TestDescriptor, result: TestResult) {}
            override fun afterTest(testDescriptor: TestDescriptor, result: TestResult) {
                if (result.resultType == TestResult.ResultType.FAILURE) {
                    val lines = (result.exception?.message ?: "Revisa tu solución").split("\n")
                    println("#educational_plugin FAILED + ${lines.first()}")
                    lines.drop(1).forEach { println("#educational_plugin$it") }
                    println()
                }
            }
        })
    }
    tasks.register<JavaExec>("run") {
        dependsOn("classes")
        classpath = project.extensions.getByType<SourceSetContainer>()["main"].runtimeClasspath
        mainClass.set("Main")
        workingDir = projectDir
    }
}
