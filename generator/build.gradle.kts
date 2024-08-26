import net.infumia.gradle.applyPublish

applyPublish("generator")

dependencies {
    compileOnly(project(":common"))
    compileOnly(project(":blank"))
    compileOnly(project(":language"))

    compileOnly(libs.creative.serializer)
    compileOnly(libs.jackson.databind)
}
