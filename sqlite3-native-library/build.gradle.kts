plugins {
    id("com.android.library") version "7.3.0"
    id("maven-publish")
}

group = "snnafi"
version = "0.0.1"
description = "Native sqlite3 library without JNI bindings"

repositories {
    mavenCentral()
    google()
}

android {
    compileSdk = 33
    buildToolsVersion = "33.0.0"
    ndkVersion = "25.1.8937393"

    namespace = "snnafi.sqlite3_native_library"

    defaultConfig {
        minSdk = 16

        ndk {
            abiFilters += setOf("armeabi-v7a", "arm64-v8a", "x86", "x86_64")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    externalNativeBuild {
        cmake {
            path = file("cpp/CMakeLists.txt")
        }
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}


publishing {
    publications {
        register<MavenPublication>("maven") {
            groupId = "snnafi"
            artifactId = "sqlite3_native_library"
            version = "0.0.1"

            afterEvaluate {
                from(components["release"])
            }

        }
    }
}



tasks.named("publish").configure {
    dependsOn("assembleRelease")
}