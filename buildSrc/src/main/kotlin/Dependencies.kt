import Versions.fragment_version
import Versions.kotlin_version
import Versions.lifecycle_version

object Versions {
    val kotlin_version = "1.5.20"
    val kotlin_core = "1.6.0"
    val appCompat = "1.3.0"
    val material = "1.4.0"
    val constraintLayout = "2.0.4"
    val jUnit = "4.13.2"
    val jUnit_androidX = "1.1.3"
    val gradle = "4.2.1"
    val moshi_converter_version = "2.9.0"
    val gson_converter_version = "2.5.0"
    val lifecycle_version = "2.4.0-alpha02"
    val fragment_version = "1.3.5"
    val hilt_version = "2.35"
    val glide_version = "4.12.0"
    val retrofit_version = "2.9.0"
    val okhttp_version = "4.9.1"
    val moshi = "1.12.0"
    val gson = "2.8.7"
    val rxAndroid_version = "3.0.0"
    val rxJava_version = "3.0.0"
    val room_version = "2.3.0"
}

object AndroidX {
    val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    val material = "com.google.android.material:material:${Versions.material}"
    val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    val jUnit = "androidx.test.ext:junit:${Versions.jUnit_androidX}"
    val lifecycleViewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version"
    val lifecycleLivedata = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version"
    val lifecycleAnnotationProcessor = "androidx.lifecycle:lifecycle-compiler:$lifecycle_version"
    val fragment = "androidx.fragment:fragment-ktx:$fragment_version"
    val room = "androidx.room:room-runtime:${Versions.room_version}"
    val roomAnnotationProcessor = "androidx.room:room-compiler:${Versions.room_version}"
    val roomKtx = "androidx.room:room-ktx:${Versions.room_version}"
}

object GradleDepndency {
    val gradleTools = "com.android.tools.build:gradle:${Versions.gradle}"
}

object Kotlin {
    val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
    val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin_version}"
    val core = "androidx.core:core-ktx:${Versions.kotlin_core}"
}

object Testing {
    val jUnit = "junit:junit:${Versions.jUnit}"
}

object Dagger {
    val hiltGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt_version}"
    val hilt_core = "com.google.dagger:hilt-android:${Versions.hilt_version}"
    val hilt_annotation_compiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt_version}"
}

object ImageLoading {
    val glideCore = "com.github.bumptech.glide:glide:${Versions.glide_version}"
    val glideAnnotationProcessor = "com.github.bumptech.glide:compiler:${Versions.glide_version}"
}

object Network {
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit_version}"
    val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp_version}"
    val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp_version}"
}

object Moshi {
    val core = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
}

object Gson {
    val core = "com.google.code.gson:gson:${Versions.gson}"
}

object Converters {
    val moshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.moshi_converter_version}"
    val gsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.gson_converter_version}"
}

object Reactive {
    val rxAndroid = "io.reactivex.rxjava3:rxandroid:${Versions.rxAndroid_version}"
    val rxJava = "io.reactivex.rxjava3:rxjava:${Versions.rxJava_version}"
}