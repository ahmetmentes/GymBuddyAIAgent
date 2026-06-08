repositories {
    mavenCentral()
}

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlinSerialization)
}

group = "com.gymbuddyaiagent"
version = "1.0.0"
application {
    mainClass = "com.gymbuddyaiagent.ApplicationKt"
}

dependencies {
    api(projects.core)
    implementation(libs.logback)
    implementation(libs.ktor.serverCore)
    implementation(libs.ktor.serverNetty)
    implementation("io.ktor:ktor-server-content-negotiation-jvm:${libs.versions.ktor.get()}")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:${libs.versions.ktor.get()}")
    testImplementation(libs.ktor.serverTestHost)
    testImplementation(libs.kotlin.testJunit)
    implementation(libs.koogAgents)
}

val copyWebDist = tasks.register<Copy>("copyWebDist") {
    from(project(":app:webApp").layout.buildDirectory.dir("dist/wasmJs/productionExecutable"))
    into(layout.buildDirectory.dir("resources/main/static"))
}