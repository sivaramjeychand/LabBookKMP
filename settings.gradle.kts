rootProject.name = "LabBookKMP"

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            version("kotlin", "1.9.21") // Stable version
            version("agp", "8.2.0")
            version("compose", "1.5.11")
            
            version("androidx-activityCompose", "1.8.0")
            
            plugin("kotlinMultiplatform", "org.jetbrains.kotlin.multiplatform").versionRef("kotlin")
            plugin("androidApplication", "com.android.application").versionRef("agp")
            plugin("androidLibrary", "com.android.library").versionRef("agp")
            plugin("jetbrainsCompose", "org.jetbrains.compose").versionRef("compose")
            
            library("androidx-activity-compose", "androidx.activity", "activity-compose").versionRef("androidx-activityCompose")
        }
    }
}

include(":composeApp")
