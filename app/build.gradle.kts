plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.roblescode.quiztrack"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.roblescode.quiztrack"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Firebase Authentication (email/password, providers, etc.)
    implementation(libs.firebase.auth)

    // Google Sign-In (for logging in with a Google account)
    implementation(libs.googleid)

    // Credential Manager API (modern and secure login, part of AndroidX)
    implementation(libs.androidx.credentials)

    // Credential Manager integration with Google Play Services (supports Passkeys, Smart Lock, etc.)
    implementation(libs.androidx.credentials.play.services.auth)

    // Firebase Firestore: cloud NoSQL real-time database
    implementation(libs.firebase.firestore)

    // Retrofit: HTTP client for making REST API calls
    implementation(libs.retrofit)
    implementation(libs.retrofit.serialization.converter)

    // Serialization: JSON serialization and deserialization
    implementation(libs.kotlinx.serialization.json)

    // Coil: Image loading library
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    // Navigation Compose
    implementation(libs.androidx.navigation.compose)

    // Compose Material Icons Extended
    implementation(libs.androidx.material.icons.extended)

}