import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    implementation(project(":domain"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.projectlombok:lombok:1.18.22")
    implementation("io.springfox:springfox-boot-starter:3.0.0")
}

// bootJar 허용, 실행 jar 만들기
tasks.withType<BootJar> {
    enabled = true
}