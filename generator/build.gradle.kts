import net.infumia.gradle.publish

publish("generator")

dependencies {
    compileOnly(project(":common"))

    compileOnly(libs.creative.api)
    compileOnly(libs.creative.serializer)
    compileOnly(libs.adventure.api)
}
