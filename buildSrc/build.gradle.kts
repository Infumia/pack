plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(libs.nexus.plugin)
}

kotlin {
    jvmToolchain(11)
}
