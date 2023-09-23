import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
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
                implementation(project(":shared"))
                implementation(compose.desktop.macos_arm64)
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
            packageName = "puzzyx-desktop"
            packageVersion = "1.0.0"
        }
    }
}

afterEvaluate {
    task<Copy>("packageReleaseUberStripArchitecture") {
        from("build/compose/jars")
        rename {
            it.split("-").run {
                slice(0 until size - 2) + last()
            }.joinToString("-")
        }
        into("build/distributable")
    }.dependsOn(tasks.named("packageReleaseUberJarForCurrentOS"))
}
