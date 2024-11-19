plugins {
    application
}

dependencies {
    implementation(project(":network"))

    // MySQL connector
    implementation("mysql:mysql-connector-java:8.0.33")
}

application {
    // @TODO write main class path
    mainClass.set("ServerApp")
}


tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    dependsOn(":network:jar")
    manifest {
        attributes(
            "Main-Class" to "ServerApp"
        )
    }

    from({
        configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }
    })
}
