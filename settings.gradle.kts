import java.io.File

pluginManagement {
    includeBuild("../build-logic")
}

plugins {
    id("multimodule")
}

fun includeSubs(base: String, path: String = base, vararg subs: String) {
    subs.forEach {
        include(":$base-$it")
        project(":$base-$it").projectDir = File("$path/$it")
    }
}

listOf(
    "kommander", "kollections", "kevlar", "cinematic", "kase", "kiota"
).forEach { includeBuild("../$it") }

rootProject.name = "sanity"

includeSubs("sanity", "../sanity", "core", "local", "remote", "flix")
