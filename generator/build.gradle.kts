import net.infumia.gradle.publish

publish("generator")

dependencies {
    compileOnly(project(":common"))
    compileOnly(project(":blank"))
    compileOnly(project(":language"))

    compileOnly(libs.adventure.api)
    compileOnly(libs.creative.api)
    compileOnly(libs.jackson.databind)
}
