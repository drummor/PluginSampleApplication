package com.sample.pluginsampleapplication.classloadersample

import android.content.Context
import java.io.File

class PluginLoader {

    fun load(baseContext: Context, apkFile: File) {
        val apkClassLoader = createApkClassLoader(apkFile)
        val contextWrapper = createWrapperContext(baseContext, apkClassLoader, apkFile)
    }

    private fun createApkClassLoader(apkFile: File): ApkClassLoader {
        return ApkClassLoader(apkFile.absolutePath, null, null, this::class.java.classLoader)
    }

    private fun createWrapperContext(
        baseContext: Context,
        classLoader: ApkClassLoader,
        apkFile: File
    ): PluginContext {
        return PluginContext(
            baseContext,
            apkFile.absolutePath,
            classLoader
        )
    }
}