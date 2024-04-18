println("==> 1.builds gradle ===>")

buildscript {
    // 为当前项目 gradle 脚本配置 classpath
    println("==> 2. buildscript")
}


// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    println("==> 3. buildscript")
    // apply false [to tell Gradle not to apply the plugin to the current project]
    id("com.android.application") version "8.2.0" apply false

    // apply false [to tell Gradle not to apply the plugin to the current project]
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
}