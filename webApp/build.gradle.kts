import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    js(IR) {
        moduleName = "puzzyx-web"
        browser()
        binaries.executable()
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(project(":shared"))
                implementation(libs.appyx.navigation)
                implementation(libs.appyx.components.backstack)
            }
        }
    }
}

compose.experimental {
    web.application {}
}

tasks.register<Copy>("copyResources") {
    // Dirs containing files we want to copy
    from("../shared/src/commonMain/resources")

    // Output for web resources
    into("$buildDir/processedResources/js/main")

    include("**/*")
}

tasks.named("jsBrowserProductionExecutableDistributeResources") {
    dependsOn("copyResources")
}

tasks.named("compileKotlinJs") {
    dependsOn("copyResources")
}
