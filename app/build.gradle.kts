plugins {
    id("com.android.application")
    id("kotlin-parcelize")
    kotlin("android")
    kotlin("kapt")
    kotlin("plugin.serialization") version Versions.kotlin
}

android {

    compileSdk = ConfigData.compileSdkVersion

    defaultConfig {
        applicationId = "com.mira.organizer"
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdkVersion
        versionCode = ConfigData.versionCode
        versionName = ConfigData.versionName
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), ("proguard-rules.pro"))
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility(JavaVersion.VERSION_11)
        targetCompatibility(JavaVersion.VERSION_11)
    }
    packagingOptions.apply {
        resources.pickFirsts.add("**")
        setExcludes(setOf("/META-INF/{AL2.0,LGPL2.1}"))
    }

    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs = listOf("-Xjvm-default=all")
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0"
    }

    kotlin.sourceSets.all {
        languageSettings.optIn("kotlin.RequiresOptIn")
    }
}

dependencies {

    coreLibraryDesugaring(Deps.desugar)

    implementation(Deps.core)

    implementation(Deps.composeUi)
    implementation(Deps.composeMaterial)
    implementation(Deps.composeMaterial3)
    implementation(Deps.materialWindowSize)
    implementation(Deps.composeUiToolingPreview)
    androidTestImplementation(Deps.composeUiTest)
    debugImplementation(Deps.composeUiTooling)
    implementation(Deps.composeNavigation)
    implementation(Deps.navAccompanist)

    implementation(Deps.lifecycleExt)
    implementation(Deps.activityCompose)
    testImplementation(Deps.junit)
    androidTestImplementation(Deps.extJunit)
    androidTestImplementation(Deps.espresso)
    implementation(Deps.retrofit)
    implementation(Deps.okhttp)
    implementation(Deps.logInterceptor)
    implementation(Deps.jsonConverter)
    implementation(Deps.serialization)
    implementation(Deps.serializationCore)
    implementation(Deps.viewModel)
    implementation(Deps.liveData)
    implementation(Deps.lifecycleExt)
    implementation(Deps.coroutinesCore)
    implementation(Deps.coroutinesAndroid)

    implementation(Deps.composeRuntime)
    implementation(Deps.composeRuntimeLivedata)

    implementation(Deps.koin)
    implementation(Deps.koinCompose)

    implementation(Deps.coilCompose)

    implementation(Deps.materialDesign)

    implementation(Deps.composePager)
    implementation(Deps.composePagerIndicators)

    implementation(Deps.gson)
    implementation(Deps.gsonConverter)

    implementation(Deps.room)
    kapt(Deps.roomCompiler)
    implementation(Deps.roomKtx)
}