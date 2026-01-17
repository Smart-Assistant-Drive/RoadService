plugins {
	kotlin("jvm") version "2.0.20"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.3.4"
	id("io.spring.dependency-management") version "1.1.6"
	alias(libs.plugins.gitSemVer)
	alias(libs.plugins.kotlin.qa)
	alias(libs.plugins.dokka)
}

group = "com.smartassistantdrive"
version = "1.0-SNAPSHOT"

extra["springCloudVersion"] = "2023.0.3"

kotlin {
	jvmToolchain(21)
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-hateoas")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.3")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springframework.boot:spring-boot-starter-aop")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")

	// Source: https://mvnrepository.com/artifact/io.micrometer/micrometer-registry-prometheus
	implementation("io.micrometer:micrometer-registry-prometheus")

	testImplementation(libs.mockito.kotlin)
	testImplementation(libs.archunit)
	testImplementation(libs.bundles.cucmber.test)

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.test {
	useJUnitPlatform()
}

configurations.matching { it.name == "detekt" }.all {
	resolutionStrategy.eachDependency {
		if (requested.group == "org.jetbrains.kotlin") {
			useVersion(io.gitlab.arturbosch.detekt.getSupportedKotlinVersion())
		}
	}
}

// build.gradle.kts (for Gradle Kotlin DSL)
detekt {
	config = files("detekt.yml") // Explicitly point to the root detekt.yml
	// ... other Detekt configurations
}

tasks.jar {
	manifest {
		attributes(
			mapOf("Implementation-Title" to project.name, "Implementation-Version" to project.version)
		)
	}
	enabled = false
}
