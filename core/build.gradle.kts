@Suppress("DSL_SCOPE_VIOLATION") plugins {
    kotlin("multiplatform")
    id("tz.co.asoft.library")
}

description = "An event streaming kotlin multiplatform library"

val tmp = 5

kotlin {
    jvm { library() }
    if (Targeting.JS) js(IR) { library() }
    if (Targeting.WASM) wasm { library() }
    val osxTargets = if (Targeting.OSX) osxTargets() else listOf()
    val ndkTargets = if (Targeting.NDK) ndkTargets() else listOf()
    val linuxTargets = if (Targeting.LINUX) linuxTargets() else listOf()
    val mingwTargets = if (Targeting.MINGW) mingwTargets() else listOf()

    sourceSets {
        val commonMain by getting {
            dependencies {
//                api(projects.koncurrentLaterCore)
            }
        }
    }
}