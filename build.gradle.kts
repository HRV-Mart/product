import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.2"
    id("io.spring.dependency-management") version "1.1.2"
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
    `maven-publish`
    // Detekt and Jacoco
    id("io.gitlab.arturbosch.detekt").version("1.22.0") // This is to add detekt
    id("jacoco")// This is to use Jacoco for coverage testing
}

group = "com.hrv.mart"
version = System.getenv("VERSION")
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
    maven {
        name = "GitHubPackages"
        url = uri("https://maven.pkg.github.com/hrv-mart/custom-pageable")
        credentials {
            username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
            password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
        }
    }
}
publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/hrv-mart/product")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
            }
        }
    }
    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
        }
    }
}
dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    // Detekt
    detektPlugins ("io.gitlab.arturbosch.detekt:detekt-formatting:1.23.1")
    // HRV-Mart dependency
    implementation("com.hrv.mart:api-call:0.0.3")
    implementation("com.hrv.mart:custom-pageable:0.0.2")
}
detekt {
    toolVersion = "1.22.0"
    config = files("config/detekt/detekt.yml")
}
tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    // To run Jacoco Test Coverage Verification
    finalizedBy("jacocoTestCoverageVerification")
}
tasks.jacocoTestCoverageVerification {
    violationRules {
//        rule {
//            excludes = listOf(
//                "com.hrv.mart.product.repository.ProductRepository.kt"
//            )
//            limit {
//                minimum = "0.9".toBigDecimal()
//            }
//        }
    }
}
tasks.jacocoTestReport{
    reports {
        html.required.set(true)
        generate()
    }
}
tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}