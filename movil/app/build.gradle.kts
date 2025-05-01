plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.Tfg.juego"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.Tfg.juego"
        minSdk = 30
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

    // retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")

    // convertidor de json
    implementation("com.squareup.retrofit2:converter-jackson:2.9.0")
    implementation ("com.fasterxml.jackson.module:jackson-module-kotlin:2.17.0")

    // dataStore
    implementation ("androidx.datastore:datastore-preferences:1.0.0")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
    implementation ("androidx.lifecycle:lifecycle-runtime-compose:2.6.0")

    // shared preferences
    implementation ("androidx.preference:preference-ktx:1.1.1")

    // jwtDecode
    implementation ("com.auth0.android:jwtdecode:2.0.2")

    // Navigation Compose
    implementation ("androidx.navigation:navigation-compose:2.7.7")

    // El websocket
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    // la imagen asicrona
    implementation ("io.coil-kt:coil-compose:2.6.0")
}