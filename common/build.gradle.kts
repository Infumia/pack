import net.infumia.gradle.publish

publish()

dependencies {
    compileOnly(libs.creative.api)
    compileOnly(libs.adventure.api)
}
