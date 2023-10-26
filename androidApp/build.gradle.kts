plugins {
    id("com.android.application")
    kotlin("android")
    id("io.gitlab.arturbosch.detekt")
}

android {
    namespace = "com.bumble.appyx.livemosaic.android"
    compileSdk = libs.versions.androidCompileSdk.get().toInt()
    defaultConfig {
        applicationId = "com.bumble.appyx.livemosaic.android"
        minSdk = 30
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("debug")
        }
        create("benchmark") {
            initWith(buildTypes.getByName("release"))
            matchingFallbacks += listOf("release")
            isDebuggable = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(platform(libs.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.compose.ui.tooling)
    implementation(libs.appyx.navigation)
}

detekt {
    source.setFrom("src/main/kotlin")
    config.setFrom("../Detekt.yml")
    buildUponDefaultConfig = true
}
dependencies {
    detektPlugins(libs.detekt.compose.rules)
}
