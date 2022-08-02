object BuildPlugins {
    val android by lazy { "com.android.tools.build:gradle:${Versions.gradlePlugin}" }
    val kotlin by lazy { "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}" }
    val gms by lazy { "com.google.gms:google-services:${Versions.gms}" }
    val safeArgs by lazy { "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}" }
}

object Deps {
    val appCompat by lazy { "androidx.appcompat:appcompat:${Versions.appCompat}" }
    val kotlin by lazy { "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}" }

    val materialDesign by lazy { "com.google.android.material:material:${Versions.material}" }

    val constraintLayout by lazy { "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}" }
    val junit by lazy { "junit:junit:${Versions.jUnit}" }
    val activity by lazy { "androidx.activity:activity-ktx:${Versions.activity}" }
    val fragment by lazy { "androidx.fragment:fragment-ktx:${Versions.fragment}" }
    val core by lazy { "androidx.core:core-ktx:${Versions.core}" }
    val recyclerView by lazy { "androidx.recyclerview:recyclerview:${Versions.recyclerView}" }
    val swipeRefresh by lazy { "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefresh}" }
    val cardView by lazy { "androidx.cardview:cardview:${Versions.cardView}" }
    val editTextMask by lazy { "com.github.santalu:maskara:${Versions.editTextMask}" }
    val legacy by lazy { "androidx.legacy:legacy-support-v4:${Versions.legacy}" }

    val extJunit by lazy { "androidx.test.ext:junit:${Versions.extJunit}" }
    val espresso by lazy { "androidx.test.espresso:espresso-core:${Versions.espresso}" }

    val firebaseBom by lazy { "com.google.firebase:firebase-bom:${Versions.firebaseBom}" }
    val firebaseMessaging by lazy { "com.google.firebase:firebase-messaging-ktx" }
    val firebaseAnalytics by lazy { "com.google.firebase:firebase-analytics-ktx" }

    val navFragment by lazy { "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}" }
    val navUi by lazy { "androidx.navigation:navigation-ui-ktx:${Versions.navigation}" }
    val navDynamic by lazy { "androidx.navigation:navigation-dynamic-features-fragment:${Versions.navigation}" }
    val navTest by lazy { "androidx.navigation:navigation-testing:${Versions.navigation}" }
    val navAccompanist by lazy { "com.google.accompanist:accompanist-navigation-animation:${Versions.accompanist}" }

    val composePager by lazy { "com.google.accompanist:accompanist-pager:${Versions.accompanist}" }
    val composePagerIndicators by lazy { "com.google.accompanist:accompanist-pager-indicators:${Versions.accompanist}" }

    val retrofit by lazy { "com.squareup.retrofit2:retrofit:${Versions.retrofit}" }
    val jsonConverter by lazy { "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.serializationConverter}" }
    val adapterRxJava by lazy { "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}" }
    val serialization by lazy { "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.serialization}" }
    val serializationCore by lazy { "org.jetbrains.kotlinx:kotlinx-serialization-core:${Versions.serialization}" }

    val glide by lazy { "com.github.bumptech.glide:glide:${Versions.glide}" }
    val coilCompose by lazy { "io.coil-kt:coil-compose:${Versions.coil}" }

    val coroutinesCore by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}" }
    val coroutinesAndroid by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}" }

    val viewModel by lazy { "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}" }
    val liveData by lazy { "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}" }
    val lifecycleExt by lazy { "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleExt}" }

    val okhttp by lazy { "com.squareup.okhttp3:okhttp:${Versions.okhttp}" }
    val logInterceptor by lazy { "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}" }

    val jsoup by lazy { "org.jsoup:jsoup:${Versions.jsoup}" }

    val dotsIndicator by lazy { "com.tbuonomo:dotsindicator:${Versions.dotsIndicator}" }

    val koin by lazy { "io.insert-koin:koin-android:${Versions.koin}" }
    val koinCompose by lazy { "io.insert-koin:koin-androidx-compose:${Versions.koin}" }

    val dagger by lazy { "com.google.dagger:dagger:${Versions.dagger}" }
    val daggerCompiler by lazy { "com.google.dagger:dagger-compiler:${Versions.dagger}" }
    val daggerSupport by lazy { "com.google.dagger:dagger-android-support:${Versions.dagger}" }

    val skeleton by lazy { "com.faltenreich:skeletonlayout:${Versions.skeleton}" }

    val room by lazy { "androidx.room:room-runtime:${Versions.room}" }
    val roomCompiler by lazy { "androidx.room:room-compiler:${Versions.room}" }
    val roomKtx by lazy { "androidx.room:room-ktx:${Versions.room}" }

    val keyboardVisibility by lazy {
        "net.yslibrary.keyboardvisibilityevent:keyboardvisibilityevent:${Versions.keyboardVisibility}"
    }

    val badge by lazy { "com.nex3z:notification-badge:${Versions.badge}" }

    val wallet by lazy { "com.google.android.gms:play-services-wallet:${Versions.wallet}" }

    val groupie by lazy { "com.github.lisawray.groupie:groupie:${Versions.groupie}" }
    val groupieViewBinding by lazy { "com.github.lisawray.groupie:groupie-viewbinding:${Versions.groupie}" }

    val paging by lazy { "androidx.paging:paging-runtime:${Versions.paging}" }

    val webSocket by lazy { "org.java-websocket:Java-WebSocket:${Versions.webSocket}" }

    val composeUi by lazy { "androidx.compose.ui:ui:${Versions.compose}" }
    val composeUiTooling by lazy { "androidx.compose.ui:ui-tooling:${Versions.compose}" }
    val composeUiToolingPreview by lazy { "androidx.compose.ui:ui-tooling-preview:${Versions.compose}" }
    val composeUiTest by lazy { "androidx.compose.ui:ui-test-junit4:${Versions.compose}" }
    val composeMaterial by lazy { "androidx.compose.material:material:${Versions.compose}" }
    val composeMaterial3 by lazy { "androidx.compose.material3:material3:${Versions.material3}" }
    val materialWindowSize by lazy { "androidx.compose.material3:material3-window-size-class:${Versions.material3}" }
    val activityCompose by lazy { "androidx.activity:activity-compose:${Versions.activityCompose}" }
    val composeRuntime by lazy { "androidx.compose.runtime:runtime:${Versions.composeRuntime}" }
    val composeRuntimeLivedata by lazy { "androidx.compose.runtime:runtime-livedata:${Versions.composeRuntime}" }
    val composeNavigation by lazy { "androidx.navigation:navigation-compose:${Versions.navigation}" }

    val desugar by lazy { "com.android.tools:desugar_jdk_libs:${Versions.desugar}" }

    val gson by lazy { "com.google.code.gson:gson:${Versions.gson}" }
    val gsonConverter by lazy { "com.squareup.retrofit2:converter-gson:${Versions.gson}" }
}