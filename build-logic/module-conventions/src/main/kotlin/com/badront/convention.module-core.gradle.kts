@file:Suppress("UnstableApiUsage")

import com.android.build.gradle.BaseExtension
import com.badront.ext.withVersionCatalog
import gradle.kotlin.dsl.accessors._2ad85c4daf9be52380ce79d9cd25ef63.implementation
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

configure<BaseExtension> {
    project.withVersionCatalog { libs ->
        compileSdkVersion(libs.versions.compileSdk.get().toInt())
        defaultConfig {
            minSdk = libs.versions.minSdk.get().toInt()
            targetSdk = libs.versions.targetSdk.get().toInt()

            vectorDrawables.useSupportLibrary = true
        }
    }

    buildTypes {
        named("debug").configure {
            isDebuggable = true
            isMinifyEnabled = false
        }
        named("release").configure {
            isDebuggable = false
            isMinifyEnabled = true
        }
    }
    packagingOptions {
        resources.excludes.add("LICENSE.txt")
        resources.excludes.add("META-INF/DEPENDENCIES")
        resources.excludes.add("META-INF/NOTICE")
        resources.excludes.add("META-INF/LICENSE")
        resources.excludes.add("META-INF/LICENSE.txt")
        resources.excludes.add("META-INF/NOTICE.txt")
        resources.excludes.add("META-INF/ASL2.0")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()
    }
    dependencies {
        project.withVersionCatalog { libs ->
            implementation(libs.bundles.kotlin)
        }
    }
}