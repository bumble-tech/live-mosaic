plugins {
    //trick: for the same plugin versions in all sub-modules
    kotlin("android") version libs.versions.kotlin.get() apply false
    kotlin("multiplatform") version libs.versions.kotlin.get() apply false
    id("com.android.application") version libs.versions.agp.get() apply false
    id("org.jetbrains.compose") version libs.versions.composePlugin.get() apply false
    id("com.google.devtools.ksp") version libs.versions.ksp.get() apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
