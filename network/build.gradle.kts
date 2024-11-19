plugins {
    `java-library`
}

dependencies {
    implementation ("org.mockito:mockito-junit-jupiter:5.12.0")

    //test
    implementation("org.junit.jupiter:junit-jupiter-api:5.11.0")
    implementation("org.junit.jupiter:junit-jupiter-engine:5.11.0")
    testImplementation ("org.mockito:mockito-core:5.3.0")
    // @TODO add neded dependecies
    implementation("com.google.guava:guava:32.1.0-jre") 
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.0")
}
