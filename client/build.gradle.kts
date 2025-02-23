

plugins {
    java
    application
    id("org.openjfx.javafxplugin") version "0.0.13"
}

repositories {
    mavenCentral()
}

val junitVersion = "5.9.2"

application {
    mainClass = "com.LerningBara.app.Main"
}

javafx {
    version = "22.0.1"
    modules = listOf(
        "javafx.base",
        "javafx.controls",
        "javafx.fxml",
        "javafx.graphics",
        "javafx.media",
        "javafx.web",
        "javafx.swing"
    )
}

dependencies {

    implementation(project(":network"))
    // Use JUnit Jupiter for testing.
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation ("org.mockito:mockito-core:5.5.0")


    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")

    // This dependency is used by the application.
    implementation(libs.guava)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(22)
    }
}

tasks.test {
    useJUnitPlatform()
}


tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    dependsOn(":network:jar")
    manifest {
        attributes(
            "Main-Class" to "com.LerningBara.app.Main"
        )
    }

    from({
        configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }
    })
}
