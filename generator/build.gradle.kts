import net.infumia.gradle.publish

publish("generator")

dependencies {
    compileOnly(project(":common"))
    compileOnly(project(":blank"))
    compileOnly(project(":language"))

    compileOnly(libs.creative.serializer)
    compileOnly(libs.jackson.databind)
}
