import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    api(project(":core"))
    api(project(":domain"))

    api("org.springframework.boot:spring-boot-starter-web")
    api("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    api("org.springframework.boot:spring-boot-starter-security")
    implementation("org.projectlombok:lombok:1.18.22")
}

// bootJar 허용, 실행 jar 만들기
tasks.withType<BootJar> {
    enabled = true
}