plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.services)
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

android {
    namespace = "com.ort.parcial.c2.tp3.grupo10"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.ort.parcial.c2.tp3.grupo10"
        minSdk = 27
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "API_BASE_URL", "\"https://d9811bf4-5e67-4a8c-bdcf-603cbbfc0275.mock.pstmn.io/\"")
        buildConfigField("String", "API_KEY", "\"123456789\"")
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
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    // --- NUEVA IMPLEMENTACIÓN ---
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)

    //Coil --v 3.2.0 BAJO VERSION A VER SI FUNCIONA
    implementation(libs.coil.compose)

    //implementation(libs.coil.compose)

    //Hilt-Dagger --v 2.56.2
    implementation("com.google.dagger:hilt-android:2.56.2")
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.compose.foundation)

    kapt("com.google.dagger:hilt-android-compiler:2.56.2")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    //Room --v 2.7.0 (compatible con Kotlin 2.0.21)
    implementation("androidx.room:room-runtime:2.7.0")
    kapt("androidx.room:room-compiler:2.7.0")
    implementation("androidx.room:room-ktx:2.7.0")

    //Retrofit --v 2.9.0
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    //Firebase --v 33.5.1
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.kotlinx.coroutines.play.services)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //temporizador
    implementation(libs.kotlinx.coroutines.android)
}

// Compatibility shim for IDEs expecting a ':app:testClasses' task.
// Android Gradle Plugin does not define 'testClasses' like the Java plugin does.
// This task compiles unit test sources so Gradle-aware Make or Test runners don't fail.
tasks.register("testClasses") {
    dependsOn(
        "compileDebugUnitTestKotlin",
        "compileDebugUnitTestJavaWithJavac"
    )
}
