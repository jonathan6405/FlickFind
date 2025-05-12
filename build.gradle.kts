// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("com.google.devtools.ksp") version "2.1.20-2.0.1" apply false
}

val localProperties = rootProject.file("local.properties")
val properties = java.util.Properties()
properties.load(localProperties.inputStream())

val tmdbApiKey: String = properties.getProperty("TMDB_API_KEY") ?: ""

