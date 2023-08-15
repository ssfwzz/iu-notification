dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":client"))
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("io.awspring.cloud:spring-cloud-aws-starter-sqs")
}
