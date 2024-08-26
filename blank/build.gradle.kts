import net.infumia.gradle.applyPublish

applyPublish("blank")

dependencies {
    compileOnly(project(":common"))

    compileOnly(libs.creative.api)
}
