
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.amit_kundu_io.nextcart"

    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.amit_kundu_io.nextcart"
        minSdk = 29
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
    }

    // Product flavors
    flavorDimensions += "brand"

    productFlavors {
        create("flavor1") {
            dimension = "brand"
            applicationId = "com.amit_kundu_io.nextcart.flavor1"
            resValue("string", "app_name", "NextCart Blue")
        }

        create("flavor2") {
            dimension = "brand"
            applicationId = "com.amit_kundu_io.nextcart.flavor2"
            resValue("string", "app_name", "NextCart Green")
        }

        create("flavor3") {
            dimension = "brand"
            applicationId = "com.amit_kundu_io.nextcart.flavor3"
            resValue("string", "app_name", "NextCart Red")
        }
    }

    buildTypes {
        release {
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

    buildFeatures {
        compose = true
        resValues = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(libs.androidx.appcompat)

    // Project modules
    implementation(project(":core:theme"))
    implementation(project(":feature:auth"))

    testImplementation(libs.junit)

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)

    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}
