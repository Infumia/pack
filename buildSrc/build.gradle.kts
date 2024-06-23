plugins { `kotlin-dsl` }

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(libs.nexus.plugin)
    implementation(libs.spotless.plugin)
}

kotlin { jvmToolchain(11) }
