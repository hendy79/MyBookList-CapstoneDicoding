plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp")
    id("kotlin-kapt")
}

android {
    namespace = "com.hendyapp.mblcore"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        buildConfig = true
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            /*
            val p = `java.util`.Properties()
            p.load(project.rootProject.file("local.properties").reader())
            val urlApi: String = p.getProperty("URL_API")
            val keyRoom: String = p.getProperty("KEY_ROOM")
             */
            val urlApi = "https://www.googleapis.com/books/v1/"
            val keyRoom = "y_book_list-dbkey"
            buildConfigField("String", "URL_API", "\"$urlApi\"")
            buildConfigField("String", "KEY_ROOM", "\"$keyRoom\"")
        }
        debug {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            /*
            val p = `java.util`.Properties()
            p.load(project.rootProject.file("local.properties").reader())
            val urlApi: String = p.getProperty("URL_API")
            val keyRoom: String = p.getProperty("KEY_ROOM")
             */
            val urlApi = "https://www.googleapis.com/books/v1/"
            val keyRoom = "y_book_list-dbkey"
            buildConfigField("String", "URL_API", "\"$urlApi\"")
            buildConfigField("String", "KEY_ROOM", "\"$keyRoom\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("androidx.room:room-runtime:2.5.2")
    ksp("androidx.room:room-compiler:2.5.2")
    implementation("androidx.room:room-ktx:2.5.2")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")

    implementation("com.google.dagger:hilt-android:2.48.1")
    kapt("com.google.dagger:hilt-android-compiler:2.48.1")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")

    implementation("net.zetetic:android-database-sqlcipher:4.4.0")
    implementation("androidx.sqlite:sqlite-ktx:2.4.0")
}