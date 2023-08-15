import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.2"
    id("io.spring.dependency-management") version "1.1.2"
    id("org.jlleitschuh.gradle.ktlint") version "11.5.1"
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
}

group = "co.mintcho"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

extra["springCloudVersion"] = "2022.0.4"

subprojects {
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(plugin = "kotlin")
    apply(plugin = "kotlin-spring")

    group = "co.mintcho"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-resilience4j")
        implementation("io.github.oshai:kotlin-logging-jvm:5.0.1")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
        annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
        testImplementation("org.springframework.boot:spring-boot-starter-test") {
            exclude(module = "mockito-core")
        }
        testImplementation("io.kotest:kotest-runner-junit5:5.6.2")
        testImplementation("io.kotest:kotest-assertions-core:5.6.2")
        testImplementation("io.kotest:kotest-property:5.6.2")
        testImplementation("com.ninja-squad:springmockk:4.0.2")
        testImplementation("com.appmattus.fixture:fixture:1.2.0")
    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = "17"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        debug.set(true)
    }
}

val appModules = listOf(project(":scheduler"), project(":consumer"))

configure(appModules) {
    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-actuator")
    }

    tasks.jar {
        enabled = false
    }

    tasks.bootJar {
        enabled = true
    }
}

val coreModules = listOf(project(":domain"), project(":data"), project(":client"))

configure(coreModules) {
    tasks.jar {
        enabled = true
    }

    tasks.bootJar {
        enabled = false
    }
}

tasks.jar {
    enabled = false
}

tasks.bootJar {
    enabled = false
}
