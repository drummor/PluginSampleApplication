package com.sample.pluginsampleapplication.classloadersample

import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.res.AssetManager
import android.content.res.Resources

class ResourceLoader {
    fun loadPluginResource(
        base: Application,
        apkPath: String,
    ): Resources {
        val packageManager = base.packageManager
        val hostAppInfo = base.applicationInfo
        val pluginAppInfo = ApplicationInfo()
        pluginAppInfo.uid = hostAppInfo.uid
        pluginAppInfo.packageName = hostAppInfo.packageName
        pluginAppInfo.publicSourceDir = apkPath
        pluginAppInfo.sourceDir = apkPath
        val hostSharedLibraryFiles = hostAppInfo.sharedLibraryFiles
        pluginAppInfo.sharedLibraryFiles =
            listOf(*hostSharedLibraryFiles, apkPath).toTypedArray<String>()
        return packageManager.getResourcesForApplication(pluginAppInfo)
    }

//    fun loadResource(
//        base: Application,
//        apkPath: String,
//    ): Resources {
//        val manager = (AssetManager::class.java).getDeclaredConstructor().newInstance()
//
//    }
}