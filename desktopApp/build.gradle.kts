import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("io.gitlab.arturbosch.detekt")
}

kotlin {
    jvm("desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = libs.versions.jvmTarget.get()
        }
    }
    sourceSets {
        val desktopMain by getting {
            dependencies {
                // Tricky way to ensure CI builds ARM64 artifacts
                val isCIPresent = providers.environmentVariable("CI").isPresent
                val composeDependency =
                    if (isCIPresent) compose.desktop.macos_arm64 else compose.desktop.currentOs

                implementation(project(":shared"))
                implementation(composeDependency)
                implementation(libs.appyx.navigation)
                implementation(libs.kotlin.coroutines.core)
                implementation(libs.kotlin.coroutines.swing)
                api(compose.material)
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "live-mosaic-desktop"
            packageVersion = "1.0.0"
        }
    }
}

tasks.register<Copy>("packageReleaseStripArchitecture") {
    from("build/compose/jars")
    // Due to GitHub Actions' lack of support for arm64 macOS virtual machines, we've removed
    // the architecture component from our distributable filename. We've enforced the use of
    // the arm64 architecture in our dependencies, as shown in 'dependencies' section above.
    rename {
        it.split("-").run {
            slice(0 until size - 2) + last()
        }.joinToString("-")
    }
    into("build/distributable")
}.configure {
    dependsOn(tasks.named("packageReleaseUberJarForCurrentOS"))
}

detekt {
    source.setFrom("src")
    config.setFrom("../Detekt.yml")
    buildUponDefaultConfig = true
}
dependencies {
    detektPlugins(libs.detekt.compose.rules)
}
