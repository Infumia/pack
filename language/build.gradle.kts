import net.infumia.gradle.applyPublish

applyPublish("language")

dependencies {
    compileOnly(project(":common"))

    compileOnly(libs.creative.api)
}
