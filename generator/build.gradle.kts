import net.infumia.gradle.applyPublish

applyPublish("generator")

dependencies {
    compileOnly(project(":common"))
    compileOnly(project(":blank"))
    compileOnly(project(":language"))

    compileOnly(libs.creative.serializer) { exclude(group = "org.jetbrains") }
    compileOnly(libs.jackson.databind)

    testImplementation(project(":common"))
    testImplementation(libs.junit.api)
    testImplementation(libs.junit.engine)
    testImplementation(libs.junit.params)
    testImplementation(libs.jackson.yaml)
    testImplementation(libs.creative.serializer)
    testImplementation(
        files(layout.projectDirectory.dir("src").dir("test").dir("resources").file("test.jar"))
    )
}
