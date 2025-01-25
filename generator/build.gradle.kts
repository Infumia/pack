import net.infumia.gradle.applyPublish

applyPublish("generator")

dependencies {
    compileOnly(project(":common"))
    compileOnly(project(":blank"))
    compileOnly(project(":language"))

    compileOnly(libs.creative.serializer) { exclude(group = "org.jetbrains") }
    compileOnly(libs.jackson.databind)
}
