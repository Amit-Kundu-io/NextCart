plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.amit_kundu_io.auth"
    compileSdk {
        version = release(37)
    }


    flavorDimensions += "brand"

    productFlavors {
        create("flavor1") {
            dimension = "brand"
        }

        create("flavor2") {
            dimension = "brand"
        }

        create("flavor3") {
            dimension = "brand"
        }
    }

    defaultConfig {
        minSdk = 29

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

}

dependencies {
    implementation(project(":core:theme"))

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}