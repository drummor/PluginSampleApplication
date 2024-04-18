package com.sample.pluginsampleapplication.utils

import android.content.res.AssetManager
import java.io.File
import java.io.IOException

object AssetsCopier {
    fun copyFile(
        am: AssetManager,
        source: String,
        target: File,
        overwrite: Boolean = false
    ): Boolean {
        if (target.exists()) {
            if (!overwrite) {
                return true
            } else if (!target.delete()) {
                throw IOException("Tried to overwrite the destination, but failed to delete it.")
            }
        }
        target.parentFile?.mkdirs()
        am.open(source).use { input ->
            target.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        return true
    }
}