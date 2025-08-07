plugins {
    java
    id("org.sonarqube") version "4.4.1.3373"
    id("org.springframework.boot") version "3.0.5"
    id("io.spring.dependency-management") version "1.1.0"
}

springBoot {
    buildInfo()
}

version = "25.5.2"
group = "com.onnitech"
java.sourceCompatibility = JavaVersion.VERSION_17

val springVersion = "3.0.5"
val lombokVersion = "1.18.28"
val openApiVersion = "2.1.0"
val postgresVersion = "42.6.0"
val hibernateValidatorVersion = "8.0.0.Final"
val springAdminVersion = "3.1.2"

tasks.bootJar {
    archiveFileName.set("pix-webhook-solution.jar")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter:$springVersion")
    implementation("org.springframework.boot:spring-boot-starter-web:$springVersion")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:$springVersion")
    implementation("org.springframework.boot:spring-boot-starter-security:$springVersion")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$openApiVersion")
    implementation("org.postgresql:postgresql:$postgresVersion")
    implementation("org.hibernate.validator:hibernate-validator:$hibernateValidatorVersion")
    implementation("org.projectlombok:lombok:$lombokVersion")
    implementation("de.codecentric:spring-boot-admin-starter-client:$springAdminVersion")

    compileOnly("org.projectlombok:lombok:$lombokVersion")

    annotationProcessor("org.projectlombok:lombok:$lombokVersion")

    testImplementation("org.springframework.boot:spring-boot-starter-test:$springVersion")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.register("printVersion") {
    description = "Print version"
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    println("version=$version")
}
