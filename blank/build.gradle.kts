import net.infumia.gradle.publish

publish("blank")

dependencies {
    compileOnly(project(":common"))

    compileOnly(libs.creative.api)
}
