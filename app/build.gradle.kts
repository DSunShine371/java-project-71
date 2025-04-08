plugins {
    application
    java
    checkstyle
    jacoco
    id("com.github.ben-manes.versions") version "0.52.0"
    id("se.patrikerdes.use-latest-versions") version "0.2.18"
    id("io.freefair.lombok") version "8.13"
    id("org.sonarqube") version "6.0.1.5171"
}

group = "hexlet.code"
version = "1.0"

application {
    mainClass.set("hexlet.code.App")
}

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

dependencies {
    implementation("org.apache.commons:commons-lang3:3.17.0")
    implementation("org.apache.commons:commons-collections4:4.4")
    implementation("info.picocli:picocli:4.7.6")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.1")

    testImplementation("org.assertj:assertj-core:3.27.3")
    testImplementation(platform("org.junit:junit-bom:5.11.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("org.junit.platform:junit-platform-launcher:1.11.0")
}

tasks {
    test {
        useJUnitPlatform()
        finalizedBy(jacocoTestReport)
    }

    jacocoTestReport {
        dependsOn(test)
        reports { xml.required.set(true) }
    }
}

sonar {
    properties {
        property("sonar.projectKey", "DSunShine371_java-project-71")
        property("sonar.organization", "dsunshine371pis")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.login", "6691c31b8c7c030f25e7ac32210c9c8f1ef4dfd5")
    }
}