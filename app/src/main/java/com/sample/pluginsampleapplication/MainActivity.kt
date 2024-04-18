package com.sample.pluginsampleapplication

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sample.plugin.sample.common.Enter
import com.sample.plugin.sample.common.IPluginArithmetic
import com.sample.pluginsampleapplication.classloadersample.ApkClassLoader
import com.sample.pluginsampleapplication.classloadersample.PluginContext
import com.sample.pluginsampleapplication.utils.AssetsCopier
import com.sample.pluginsampleapplication.utils.getInterface
import java.io.File

class MainActivity : AppCompatActivity() {

    private val pluginApkFile: File by lazy { ensureApkFile() }
    private val apkClassLoader: ApkClassLoader by lazy { createApkClassLoader() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.bt_call_plugin_increase).setOnClickListener {
            invokeIncreaseByPlugin()
        }
        findViewById<Button>(R.id.bt_load_plugin).setOnClickListener {
            loadPluginView()
        }

    }

    private fun loadPluginView() {
        val pluginManagerContext: Context =
            PluginContext(
                applicationContext,
                this.pluginApkFile.absolutePath,
                apkClassLoader
            )
        val clzName = "com.sample.sample_plugin.EnterImpl"
        val interfaceImplementClass: Class<*> = apkClassLoader.loadClass(clzName)
        val enter: Enter = getInterface(Enter::class.java, interfaceImplementClass)
        val container: ViewGroup = findViewById(R.id.container)
        enter.enter(
            pluginManagerContext
        ) { view ->
           // container.addView(view)
        }
        val cardView = enter.fetchPluginCardView(pluginManagerContext)
        container.addView(cardView)

    }

    private fun ensureApkFile(): File {
        val dir = this.application.getDir("plugins", 0)
        val pluginFile = File(dir, "sample-plugin-debug.apk")
        AssetsCopier.copyFile(this.assets, "sample-plugin-debug.apk", pluginFile, true)
        return pluginFile
    }

    private fun createApkClassLoader(): ApkClassLoader {

        return ApkClassLoader(
            pluginApkFile.absolutePath,
            null,
            null,
            this.classLoader,
        )
    }

    private fun invokeIncreaseByPlugin() {
        val arithmeticClzName = "com.sample.sample_plugin.PluginArithmeticImpl"
        val arithmeticClzNameImpl: Class<*> = apkClassLoader.loadClass(arithmeticClzName)
        val arithmetic: IPluginArithmetic =
            getInterface(IPluginArithmetic::class.java, arithmeticClzNameImpl)
        val result = arithmetic.plus(1, 1)
        Toast.makeText(this.applicationContext, "result:${result}", Toast.LENGTH_SHORT).show()
    }

}