@Suppress("DSL_SCOPE_VIOLATION") // Remove when fixed https://youtrack.jetbrains.com/issue/KTIJ-19369
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt.gradle)
    alias(libs.plugins.ksp)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.project.kotlincomposeapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.project.kotlincomposeapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.iua.app.HiltTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        // Enable room auto-migrations
        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        compose = true
        aidl = false
        buildConfig = false
        renderScript = false
        shaders = false
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    kapt {
        correctErrorTypes = true
    }
}

dependencies {

    implementation(libs.work.runtime.ktx)

    implementation (libs.androidx.hilt.work.v100)

    // Para WorkManager y Hilt
    implementation (libs.androidx.hilt.work)
    kapt (libs.androidx.hilt.compiler.v120)

// Para WorkManager
    implementation (libs.work.runtime.ktx.v281)

    implementation(libs.material3)
    implementation("androidx.compose.material3:material3-window-size-class-android:1.3.1")

    kapt (libs.androidx.hilt.compiler)

    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.hilt.work)
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.material)
    implementation(libs.accompanist.swiperefresh)


    // DataStore
    implementation(libs.androidx.datastore.preferences)

    // retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // Coil for image loading
    implementation(libs.coil.compose.v222)

    // Core Android dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Hilt Dependency Injection
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)


    // Hilt and instrumented tests.
    androidTestImplementation(libs.hilt.android.testing)
    kaptAndroidTest(libs.hilt.android.compiler)

    // Hilt and Robolectric tests.
    testImplementation(libs.hilt.android.testing)
    kaptTest(libs.hilt.android.compiler)

    // Arch Components
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // Compose
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    // Tooling
    debugImplementation(libs.androidx.compose.ui.tooling)

    // Instrumented tests
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // Local tests: jUnit, coroutines, Android runner
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)

    // Instrumented tests: jUnit rules and runners
    androidTestImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.runner)
}