plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("org.jetbrains.compose")
}

kotlin {
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        version = "1.0.0"
        summary = "Puzzyx iOS module"
        homepage = "https://bumble-tech.github.io/appyx/"
        ios.deploymentTarget = "17.0"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "ios"
            isStatic = true
        }
        extraSpecAttributes["resources"] = "['build/processedResources/ios/main/**']"
    }

    sourceSets {
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation(project(":shared"))
                api(compose.runtime)
                api(compose.foundation)
                implementation(libs.appyx.navigation)
                api(compose.material)
            }
        }
    }
}

compose.experimental {
    uikit.application {
        projectName = "Appyx"
        bundleIdPrefix = "com.bumble.puzzyx"
    }
}

tasks.register<Copy>("copyResources") {
    // Dirs containing files we want to copy
    from("../shared/src/commonMain/resources")

    // Output for iOS resources
    into("$buildDir/processedResources/ios/main")

    include("**/*")
}

tasks.named("compileKotlinIosArm64") {
    dependsOn("copyResources")
}

tasks.named("compileKotlinIosSimulatorArm64") {
    dependsOn("copyResources")
}

tasks.named("compileKotlinIosX64") {
    dependsOn("copyResources")
}