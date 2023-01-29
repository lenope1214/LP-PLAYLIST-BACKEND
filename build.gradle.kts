import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
	val kotlinVersion: String by System.getProperties() // 1.7.20
	val springBootVersion: String by System.getProperties() // 2.7.6

	java
	id("org.springframework.boot") version springBootVersion apply false
	id("io.spring.dependency-management") version "1.0.13.RELEASE" apply false
	id("org.jlleitschuh.gradle.ktlint") version "10.0.0" apply false


	kotlin("jvm") version kotlinVersion apply false // 1.7.20
	kotlin("kapt") version kotlinVersion apply false // 1.7.20
	kotlin("plugin.spring") version kotlinVersion apply false // 1.7.20
	kotlin("plugin.jpa") version kotlinVersion apply false // 1.7.20
}

allprojects{
	group = "kr.weareboard.lp"
	version = ""

	repositories {
		mavenCentral()
	}
}
subprojects {

	apply {
		plugin("io.spring.dependency-management")
		plugin("org.springframework.boot")

		plugin("kotlin")
		plugin("org.jlleitschuh.gradle.ktlint")
		plugin("org.jetbrains.kotlin.jvm")
		plugin("org.jetbrains.kotlin.plugin.spring")
	}

	dependencies {
		// kotlin logger
		implementation("io.github.microutils:kotlin-logging-jvm:3.0.0")

		implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
		implementation("org.springframework.boot:spring-boot-starter-data-jpa")
		implementation("org.springframework.boot:spring-boot-starter-data-redis")
		implementation("org.springframework.boot:spring-boot-starter-jdbc")
		implementation("org.springframework.boot:spring-boot-starter-validation") // 파라미터 값 확인(인증, Bean Validation)을 위해
		implementation("org.springframework.boot:spring-boot-starter-security")
		implementation("org.springframework.boot:spring-boot-starter-oauth2-client") // kakao

		// websocket에
		// com.fasterxml.jackson.databind
		//javax.servlet 과 같은 의존성이 있어서 덤으로 추가했다. websocket을 빼고 따로 추가해도 된다
		implementation("org.springframework.boot:spring-boot-starter-websocket")

		implementation("org.jetbrains.kotlin:kotlin-reflect")
		implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

		// databases
		runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
		runtimeOnly("com.h2database:h2")

		// swagger v3
		implementation("io.springfox:springfox-boot-starter:3.0.0")

		// query dsl
		implementation("com.querydsl:querydsl-jpa:5.0.0")
		implementation("com.querydsl:querydsl-core:5.0.0")

		// jwt
		runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
		runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")
		implementation("io.jsonwebtoken:jjwt-gson:0.11.5")

		annotationProcessor("com.querydsl:querydsl-apt:5.0.0:jpa")
		annotationProcessor("org.hibernate.javax.persistence:hibernate-jpa-2.1-api:1.0.2.Final")
		annotationProcessor("javax.annotation:javax.annotation-api:1.3.2")

		// 파일 관리 아파치 라이브러리
		implementation("commons-io:commons-io:2.11.0")

		testImplementation("org.springframework.boot:spring-boot-starter-test")
	}

	configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
		verbose.set(true)
		disabledRules.set(
			setOf(
				"import-ordering",
				"no-wildcard-imports",
				"final-newline",
				"insert_final_newline",
				"max_line_length"
			)
		)
	}

	configure<JavaPluginExtension> {
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}

	tasks.withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
			jvmTarget = "17"
		}
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}

	// plain jar 기본 true
	tasks.withType<Jar> {
		enabled = true
		// build 중에 중복되는 파일이 생성될경우 에러가 발생한다. 그것을 방지하기 위한 설정이다.
		duplicatesStrategy = DuplicatesStrategy.EXCLUDE
	}

	// BootJar 기본 false, 프로젝트 빌드 후 실행해야 하는 모듈이면 BootJar true 해줘야 함.
	tasks.withType<BootJar> {
		enabled = false
	}

}