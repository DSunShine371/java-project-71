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

val lang3Version = "3.17.0"
val collections4Version = "4.4"
val picocliVersion = "4.7.6"
val jacksonDatabindVersion = "2.18.3"
val jacksonDataformatYamlVersion = "2.18.3"
val slf4jApiVersion = "2.0.17"
val logbackClassicVersion = "1.5.18"
val junitBomVersion = "5.12.1"
val junitPlatformLauncherVersion = "1.12.1"

dependencies {
    implementation("org.apache.commons:commons-lang3:3.17.0")
    implementation("org.apache.commons:commons-collections4:4.4")
    implementation("info.picocli:picocli:4.7.6")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.18.3")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.18.3")
    implementation("org.slf4j:slf4j-api:2.0.17")
    implementation("ch.qos.logback:logback-classic:1.5.18")

    testImplementation(platform("org.junit:junit-bom:5.12.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("org.junit.platform:junit-platform-launcher:1.12.1")
    testImplementation("com.fasterxml.jackson.core:jackson-databind:2.18.3")
    testImplementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.18.3")
    testImplementation("org.slf4j:slf4j-api:2.0.17")
    testImplementation("ch.qos.logback:logback-classic:1.5.18")
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
    }
}