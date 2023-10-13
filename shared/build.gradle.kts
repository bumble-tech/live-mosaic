plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("com.android.library")
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
}

kotlin {
    android {
        compilations.all {
            kotlinOptions.jvmTarget = libs.versions.jvmTarget.get()
        }
    }

    jvm("desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = libs.versions.jvmTarget.get()
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material3)
                implementation(kotlin("test"))
                implementation(libs.appyx.navigation)
                api(libs.appyx.components.backstack)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

android {
    namespace = "com.bumble.appyx.puzzyx"
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")
    compileSdk = libs.versions.androidCompileSdk.get().toInt()
    defaultConfig {
        minSdk = 30
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    add("kspCommonMainMetadata", libs.appyx.mutable.ui.processor)
    add("kspAndroid", libs.appyx.mutable.ui.processor)
    add("kspDesktop", libs.appyx.mutable.ui.processor)
    add("kspIosArm64", libs.appyx.mutable.ui.processor)
    add("kspIosX64", libs.appyx.mutable.ui.processor)
    add("kspIosSimulatorArm64", libs.appyx.mutable.ui.processor)
}
