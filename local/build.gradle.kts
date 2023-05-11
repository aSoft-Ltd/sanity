@Suppress("DSL_SCOPE_VIOLATION") plugins {
    kotlin("multiplatform")
    id("tz.co.asoft.library")
}

description = "An event streaming kotlin multiplatform library for local events"

val tmp = 5

kotlin {
    jvm { library() }
    if (Targeting.JS) js(IR) { library() }
    if (Targeting.WASM) wasm { library() }
    if (Targeting.OSX) osxTargets() else listOf()
    if (Targeting.NDK) ndkTargets() else listOf()
    if (Targeting.LINUX) linuxTargets() else listOf()
    if (Targeting.MINGW) mingwTargets() else listOf()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.sanityCore)
            }
        }

        val commonTest by getting {
            dependencies {
                api(projects.kommanderCore)
            }
        }
    }
}