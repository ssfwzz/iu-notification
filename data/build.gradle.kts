plugins {
    id("org.flywaydb.flyway") version "9.16.3"
}

buildscript {
    dependencies {
        classpath("org.flywaydb:flyway-mysql:9.16.3")
    }
}

dependencies {
    implementation(project(":domain"))
    implementation("org.jetbrains.exposed:exposed-json:0.43.0")
    implementation("org.jetbrains.exposed:exposed-java-time:0.43.0")
    implementation("org.jetbrains.exposed:exposed-spring-boot-starter:0.43.0")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-mysql")

    runtimeOnly("com.mysql:mysql-connector-j")
}

flyway {
    url = "jdbc:mysql://localhost:3306/db"
    user = "root"
    password = "root"
    encoding = "UTF-8"
    outOfOrder = true
    validateOnMigrate = true
    cleanDisabled = false
}
