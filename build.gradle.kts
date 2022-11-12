import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    `maven-publish`
}

group = "org.atlasin"
version = "0.1"

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

dependencies {
    api("net.xyzsd.fluent:fluent-base:0.70")
    api("net.xyzsd.fluent:fluent-functions-cldr:0.70")
    api("com.github.Atlas-Initiative:Inspekt:0.1")
    testImplementation(kotlin("test"))
}
publishing {
    publications {
        create<MavenPublication>("jitpack") {
            groupId = "com.github.Atlas-Initiative"
            artifactId = "Dialect"
            version = rootProject.version.toString()

            from(components["java"])
        }
    }
}
tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}