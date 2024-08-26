package net.infumia.gradle

import com.vanniktech.maven.publish.MavenPublishBaseExtension
import com.vanniktech.maven.publish.MavenPublishPlugin
import com.vanniktech.maven.publish.SonatypeHost
import com.vanniktech.maven.publish.tasks.JavadocJar
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.*

fun Project.applyPublish(moduleName: String? = null, javaVersion: Int = 8) {
    applyJava(javaVersion)
    apply<MavenPublishPlugin>()

    val projectName = "pack${if (moduleName == null) "" else "-$moduleName"}"
    val signRequired = project.hasProperty("sign-required")

    val sourceSets = extensions.getByType<JavaPluginExtension>().sourceSets
    tasks.register("sourcesJar", Jar::class) {
        dependsOn("classes")
        archiveClassifier.set("sources")
        from(sourceSets["main"].allSource)
    }

    tasks.withType<JavadocJar> { afterEvaluate { archiveBaseName = name } }

    extensions.configure<MavenPublishBaseExtension> {
        coordinates(project.group.toString(), projectName, project.version.toString())
        publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, true)
        if (signRequired) {
            signAllPublications()
        }

        pom {
            name.set(projectName)
            description.set("Minecraft resource pack generator.")
            url.set("https://github.com/Infumia/pack")
            licenses {
                license {
                    name.set("MIT License")
                    url.set("https://mit-license.org/license.txt")
                }
            }
            developers {
                developer {
                    id.set("portlek")
                    name.set("Hasan Demirtaş")
                    email.set("utsukushihito@outlook.com")
                }
            }
            scm {
                connection.set("scm:git:git://github.com/infumia/pack.git")
                developerConnection.set("scm:git:ssh://github.com/infumia/pack.git")
                url.set("https://github.com/infumia/pack/")
            }
        }
    }
}
