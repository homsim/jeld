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

    testImplementation("org.junit.jupiter:junit-jupiter-api:${junitVersion}") // Intellij default
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junitVersion}") // Intellij default
}

tasks.withType<Test> {
    useJUnitPlatform()
}

jlink {
    imageZip.set(layout.buildDirectory.file("/distributions/app-${javafx.platform.classifier}.zip"))
    options.set(listOf("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages"))
    launcher {
        name = "app"
    }
}
