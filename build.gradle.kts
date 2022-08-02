buildscript {

    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
    dependencies {
        classpath(BuildPlugins.kotlin)
        classpath(BuildPlugins.android)
        classpath(BuildPlugins.safeArgs)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

tasks {
    val clean by registering(Delete::class){
        delete(buildDir)
    }
}