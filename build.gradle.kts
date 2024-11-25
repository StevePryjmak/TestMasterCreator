plugins {
    // Apply any plugins common to all subprojects
}

allprojects {
    repositories {
        mavenCentral()
    }
}



subprojects {
    afterEvaluate {
        tasks.findByName("jar")?.let { jarTask ->
            (jarTask as Jar).apply {
                // Set the output directory
                destinationDirectory.set(file("${rootProject.buildDir}/libs/${project.name}"))

                // Optionally set common manifest attributes
                manifest {
                    attributes(
                        "Implementation-Title" to project.name,
                        "Implementation-Version" to project.version
                    )
                }
            }
        }
    }
}


dependencies {
    // Common dependencies can be added here
}

tasks.withType<Test> {
    useJUnitPlatform()

    testLogging {
        events("passed", "skipped", "failed")

        showStandardStreams = true  // Show standard output and error streams during test execution.

        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        info.events("started", "skipped", "failed", "passed")
        debug.events("started", "skipped", "failed", "passed")
    }
}
