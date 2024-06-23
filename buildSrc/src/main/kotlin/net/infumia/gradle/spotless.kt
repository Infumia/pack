package net.infumia.gradle

import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.gradle.spotless.SpotlessPlugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.*

fun Project.spotless() {
    repositories.mavenCentral()

    apply<SpotlessPlugin>()

    extensions.configure<SpotlessExtension> {
        isEnforceCheck = false
        lineEndings = com.diffplug.spotless.LineEnding.UNIX

        val prettierConfig =
            mapOf(
                "prettier" to "3.3.2",
                "prettier-plugin-java" to "2.6.0",
                "prettier-plugin-toml" to "2.0.1",
            )

        yaml {
            target(".github/**/*.yml")
            endWithNewline()
            trimTrailingWhitespace()
            jackson().yamlFeature("LITERAL_BLOCK_STYLE", true).yamlFeature("SPLIT_LINES", false)
        }

        format("toml") {
            target("gradle/libs.versions.toml")
            prettier(prettierConfig)
                .config(
                    mapOf(
                        "parser" to "toml",
                        "tabWidth" to 4,
                        "useTabs" to false,
                        "printWidth" to 100,
                        "plugins" to listOf("prettier-plugin-toml"),
                    ),
                )
        }

        kotlinGradle {
            target(
                "buildSrc/**/*.gradle.kts",
                "*.gradle.kts",
                *subprojects.map { it.name }.map { "$it/*.gradle.kts" }.toTypedArray(),
            )
            ktfmt().kotlinlangStyle().configure {
                it.setMaxWidth(80)
                it.setBlockIndent(4)
                it.setContinuationIndent(4)
                it.setRemoveUnusedImport(true)
            }
        }

        kotlin {
            target(
                "buildSrc/src/main/kotlin/**/*.kt",
            )
            ktfmt().kotlinlangStyle().configure {
                it.setMaxWidth(100)
                it.setBlockIndent(4)
                it.setContinuationIndent(4)
                it.setRemoveUnusedImport(true)
            }
        }

        java {
            target(
                *subprojects.map { it.name }.map { "$it/src/main/java/**/*.java" }.toTypedArray(),
            )
            importOrder()
            removeUnusedImports()
            endWithNewline()
            trimTrailingWhitespace()
            prettier(prettierConfig)
                .config(
                    mapOf(
                        "parser" to "java",
                        "tabWidth" to 4,
                        "useTabs" to false,
                        "printWidth" to 100,
                        "plugins" to listOf("prettier-plugin-java"),
                    ),
                )
        }
    }
}
