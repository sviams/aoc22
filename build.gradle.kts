import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlin_version = "1.8.0"
val spek_version = "2.0.19"
val kluent_version = "1.72"

plugins {
    kotlin("jvm") version "1.8.0"
    application
}

group = "org.sviams"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.5")
    implementation("com.github.shiguruikai:combinatoricskt:1.6.0")
    testImplementation("org.spekframework.spek2:spek-dsl-jvm:$spek_version")
    testImplementation("org.amshove.kluent:kluent:$kluent_version")
    testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:$spek_version")

    // spek requires kotlin-reflect, can be omitted if already in the classpath
    testRuntimeOnly("org.jetbrains.kotlin:kotlin-reflect:$kotlin_version")
}

tasks.test {
    useJUnitPlatform {
        includeEngines = setOf("spek2")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

val compileKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask<*> by tasks

compileKotlin.compilerOptions.freeCompilerArgs.add("-Xdebug")

application {
    mainClass.set("MainKt")
}