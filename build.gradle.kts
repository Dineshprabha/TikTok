// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    id("androidx.navigation.safeargs.kotlin") version "2.7.5" apply false // this line, not the whole block
    id("com.google.dagger.hilt.android") version "2.49" apply false
}

buildscript {

    dependencies{
        classpath("com.android.tools.build:gradle:4.1.3") //4.1.3
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.10")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.49")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.5")
    }
}