plugins {
    id("java-library")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.4")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.18.2")

    runtimeOnly ("com.mysql:mysql-connector-j:8.0.31")
}