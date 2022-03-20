package com.badront.pokedex

object Libraries {
    object Android {
        const val appPlugin = "com.android.application"
        const val libraryPlugin = "com.android.library"
        const val kotlinPlugin = "kotlin-android"
        const val kaptPlugin = "org.jetbrains.kotlin.kapt"
    }

    object Gradle {
        private const val version = "7.0.3"
        const val classpath = "com.android.tools.build:gradle:$version"
    }

    object Kotlin {
        internal const val version = "1.5.31"
        private const val coroutinesVersion = "1.5.2"
        const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$version"
        const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion"
        const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion"
        const val coroutinesGms = "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:$coroutinesVersion"
        const val reflection = "org.jetbrains.kotlin:kotlin-reflect:$version"

        const val classpath = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val parcelizePlugin = "kotlin-parcelize"
    }

    object ViewModel {
        private const val version = "2.4.1"
        const val core = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
        const val compose = "androidx.lifecycle:lifecycle-viewmodel-compose:$version"
        const val lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
        const val common = "androidx.lifecycle:lifecycle-common-java8:$version"
    }

    object Support {
        private const val androidxFragments = "1.3.6"
        private const val androidxBrowser = "1.0.0"
        private const val androidxCore = "1.6.0"
        private const val androidxAppCompat = "1.3.1"
        private const val androidxRecycler = "1.2.0"
        private const val swipeRefreshVersion = "1.1.0"
        private const val material = "1.4.0-beta01"
        private const val androidxCardview = "1.0.0"
        private const val constraintLayoutVersion = "2.1.2"
        private const val workManagerVersion = "2.5.0"
        private const val preferenceVersion = "1.1.1"
        const val core = "androidx.core:core-ktx:$androidxCore"
        const val fragmentsKtx = "androidx.fragment:fragment-ktx:$androidxFragments"
        const val customTabs = "androidx.browser:browser:$androidxBrowser"
        const val appCompat = "androidx.appcompat:appcompat:$androidxAppCompat"
        const val recyclerView = "androidx.recyclerview:recyclerview:$androidxRecycler"
        const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:$swipeRefreshVersion"
        const val design = "com.google.android.material:material:$material"
        const val cardView = "androidx.cardview:cardview:$androidxCardview"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:$constraintLayoutVersion"
        const val workManager = "androidx.work:work-runtime-ktx:$workManagerVersion"
        const val preference = "androidx.preference:preference-ktx:$preferenceVersion"
    }

    object Network {
        object Retrofit {
            private const val version = "2.9.0"
            const val core = "com.squareup.retrofit2:retrofit:$version"
            const val converterGson = "com.squareup.retrofit2:converter-gson:$version"
        }

        object OkHttp {
            private const val version = "4.9.3"
            const val core = "com.squareup.okhttp3:okhttp:$version"
            const val logging = "com.squareup.okhttp3:logging-interceptor:$version"
            const val mockWebServer = "com.squareup.okhttp3:mockwebserver:$version"
        }
    }

    object Converter {
        object Gson {
            private const val version = "2.8.9"
            const val core = "com.google.code.gson:gson:$version"
        }
    }

    object Database {
        object Room {
            internal const val version = "2.3.0"
            const val runtime = "androidx.room:room-runtime:$version"
            const val ktx = "androidx.room:room-ktx:$version"
            const val compiler = "androidx.room:room-compiler:$version"
        }
    }

    object DI {
        const val inject = "javax.inject:javax.inject:1"
        object Dagger {
            private const val version = "2.41"
            const val core = "com.google.dagger:dagger:$version"
            const val processor = "com.google.dagger:dagger-android-processor:$version"
            const val compiler = "com.google.dagger:dagger-compiler:$version"
            const val support = "com.google.dagger:dagger-android-support:$version"
            const val android = "com.google.dagger:dagger-android:$version"
        }

        object Hilt {
            private const val version = "2.38.1"
            const val core = "com.google.dagger:hilt-android:$version"
            const val compiler = "com.google.dagger:hilt-compiler:$version"

            const val classpath = "com.google.dagger:hilt-android-gradle-plugin:$version"
            const val plugin = "dagger.hilt.android.plugin"
        }

        object Koin {
            private const val version = "2.2.3"
            const val koin = "io.insert-koin:koin-android:$version"
            const val scope = "io.insert-koin:koin-androidx-scope:$version"
            const val viewModel = "io.insert-koin:koin-androidx-viewmodel:$version"
            const val ext = "io.insert-koin:koin-androidx-ext:$version"
            const val test = "io.insert-koin:koin-test:$version"
        }
    }

    object Navigation {
        object Jetpack {
            private const val version = "2.4.1"

            const val fragment = "androidx.navigation:navigation-fragment-ktx:$version"
            const val core = "androidx.navigation:navigation-ui-ktx:$version"

            const val classpath = "androidx.navigation:navigation-safe-args-gradle-plugin:$version"
            const val safeArgPlugin = "androidx.navigation.safeargs.kotlin"
        }

        object Cicerone {
            const val core = "com.github.terrakok:cicerone:5.1.1"
        }
    }

    object Test {
        object JUnit {
            const val core = "junit:junit:4.13"
            const val kotlin = "org.jetbrains.kotlin:kotlin-test-junit:${Kotlin.version}"
            const val android = "androidx.test.ext:junit:1.1.1"
        }

        object AndroidX {
            private const val androidXTestVersion = "1.2.0"
            const val core = "androidx.test:core:$androidXTestVersion"
            const val runner = "androidx.test:runner:$androidXTestVersion"
            const val rules = "androidx.test:rules:$androidXTestVersion"
            const val room = "androidx.room:room-testing:${Database.Room.version}"
        }

        object Mockito {
            const val core = "org.mockito:mockito-core:3.3.3"
            const val kotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0"
        }

        const val assertJ = "org.assertj:assertj-core:3.16.1"
    }

    object Other {
        const val timber = "com.jakewharton.timber:timber:5.0.1"
        const val leakCanary = "com.squareup.leakcanary:leakcanary-android:2.7"
        const val viewBindingDelegate = "com.github.kirich1409:viewbindingpropertydelegate:1.5.3"
        const val palette = "androidx.palette:palette-ktx:1.0.0"
        const val flexbox = "com.google.android.flexbox:flexbox:3.0.0"
        const val time = "org.threeten:threetenbp:1.3.1"
    }

    object ImageLoading {
        object Coil {
            private const val version = "2.0.0-rc01"
            const val core = "io.coil-kt:coil:$version"
            const val compose = "io.coil-kt:coil-compose:$version"
        }

        object Glide {
            private const val version = "4.12.0"
            const val core = "com.github.bumptech.glide:glide:$version"
            const val okhttp = "com.github.bumptech.glide:okhttp3-integration:$version"
            const val compiler = "com.github.bumptech.glide:compiler:$version"
            const val annotations = "com.github.bumptech.glide:annotations:$version"
        }
    }
}