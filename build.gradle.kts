plugins {
    java
    application
    id("org.javamodularity.moduleplugin") version "1.8.15"
    id("org.openjfx.javafxplugin") version "0.0.13"
    id("org.beryx.jlink") version "2.25.0"
}

group = "com.homsim"
version = "0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

// configuration for the XML parsing
val xjc: Configuration = configurations.register("xjc") {
    isCanBeConsumed = false
    isCanBeResolved = true
}.get()

val junitVersion = "5.12.1"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

application {
    mainModule.set("com.homsim.jeld")
    mainClass.set("com.homsim.jeld.Jeld")
}

javafx {
    version = "21.0.6"
    modules = listOf("javafx.controls", "javafx.fxml", "javafx.web", "javafx.swing")
}

dependencies {
    implementation("org.kordamp.ikonli:ikonli-javafx:12.3.1") // Intellij default
    implementation("eu.hansolo:tilesfx:21.0.9") {
        exclude(group = "org.openjfx")
    } // Intellij default
    implementation("org.projectlombok:lombok:1.18.38") // https://mvnrepository.com/artifact/org.projectlombok/lombok
    implementation("com.google.inject:guice:7.0.0") // https://mvnrepository.com/artifact/com.google.inject/guice

    implementation("com.h2database:h2:2.4.240") // https://mvnrepository.com/artifact/com.h2database/h2

    implementation(platform("org.hibernate.orm:hibernate-platform:7.1.10.Final")) //https://mvnrepository.com/artifact/org.hibernate.orm/hibernate-platform
    implementation("org.hibernate.orm:hibernate-core") // https://mvnrepository.com/artifact/org.hibernate.orm/hibernate-core
    implementation("jakarta.transaction:jakarta.transaction-api") //https://mvnrepository.com/artifact/jakarta.transaction/jakarta.transaction-api
    // uncomment below as soon as it is needed
    // implementation("org.hibernate.validator:hibernate-validator") // https://mvnrepository.com/artifact/org.hibernate.validator/hibernate-validator
    // implementation("jakarta.el:jakarta.el-api") // https://mvnrepository.com/artifact/jakarta.el/jakarta.el-api

    add("xjc", "org.glassfish.jaxb:jaxb-xjc:4.0.2")
    add("xjc", "org.glassfish.jaxb:jaxb-runtime:4.0.2")
    implementation("jakarta.xml.bind:jakarta.xml.bind-api:4.0.2") // https://mvnrepository.com/artifact/jakarta.xml.bind/jakarta.xml.bind-api/4.0.2
    implementation("org.glassfish.jaxb:jaxb-runtime:4.0.2") // https://mvnrepository.com/artifact/org.glassfish.jaxb/jaxb-runtime

    testImplementation("org.junit.jupiter:junit-jupiter-api:${junitVersion}") // Intellij default
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junitVersion}") // Intellij default
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.named("compileJava") {
    dependsOn("generateCamt")
}

jlink {
    imageZip.set(layout.buildDirectory.file("/distributions/app-${javafx.platform.classifier}.zip"))
    options.set(listOf("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages"))
    launcher {
        name = "app"
    }
}

// For the usage of the XML parser. Generates java objects from XSD files.
val generateCamt = tasks.register("generateCamt") {
    group = "build"
    description = "Generate JAXB classes for CAMT.052, CAMT.053, CAMT.054 and CAMT.060."
}

val xsdDir = file("src/main/resources/com/homsim/jeld/data/parse/xsd")

xsdDir.walk()
    .filter { it.isFile && it.extension == "xsd" }
    .forEach { file ->

        val parts = file.name
            .removePrefix("camt.")
            .removeSuffix(".xsd")
            .split(".")

        val messageType = parts[0]
        val businessArea = parts[1]
        val version = parts[2]

        val pkg = "com.homsim.jeld.data.parse.iso20022.camt" +
                "${messageType}_${businessArea}_${version}"

        val outputDir = layout.buildDirectory.dir(
            "generated/camt${messageType}.${businessArea}.${version}"
        )

        val camtTask = tasks.register<JavaExec>("generateCamt${messageType}${businessArea}${version}") {
            classpath = xjc
            mainClass.set("com.sun.tools.xjc.XJCFacade")

            inputs.file(file)
            outputs.dir(outputDir)

            args(
                "-d", outputDir.get().asFile.absolutePath,
                "-p", pkg,
                "-extension",
                file.absolutePath
            )
        }

        generateCamt.configure {
            dependsOn(camtTask)
        }
    }

sourceSets["main"].java.srcDir(
    layout.buildDirectory.dir("generated") /// this is probably wrong
)

