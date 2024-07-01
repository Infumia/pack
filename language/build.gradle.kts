import net.infumia.gradle.publish

publish("language")

dependencies {
    compileOnly(project(":common"))

    compileOnly(libs.creative.api)
    compileOnly(libs.adventure.api)
}
