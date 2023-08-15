dependencies {
    implementation(project(":domain"))
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    api(platform("io.awspring.cloud:spring-cloud-aws-dependencies:3.0.1"))
    implementation("io.awspring.cloud:spring-cloud-aws-starter-sqs")
    implementation("org.jsoup:jsoup:1.16.1")
}
