package com.sample.pluginsampleapplication.classloadersample

import android.content.res.Resources
import android.content.res.XmlResourceParser
import android.graphics.drawable.Drawable

class MixResource(private val pluginResource: Resources, val hostResources: Resources) :
    Resources(pluginResource.assets, pluginResource.displayMetrics, pluginResource.configuration) {
    override fun getAnimation(id: Int): XmlResourceParser {
        return super.getAnimation(id)
    }

    override fun getBoolean(id: Int): Boolean {
        return super.getBoolean(id)
    }

    override fun getDimension(id: Int): Float {
        return super.getDimension(id)
    }

    override fun getDrawable(id: Int, theme: Theme?): Drawable {
        return super.getDrawable(id, theme)
    }

    override fun getFloat(id: Int): Float {
        return super.getFloat(id)
    }

    override fun getLayout(id: Int): XmlResourceParser {
        return super.getLayout(id)
    }

    override fun getIdentifier(name: String?, defType: String?, defPackage: String?): Int {
        return super.getIdentifier(name, defType, defPackage)
    }

    override fun getString(id: Int): String {
        return try {
            pluginResource.getString(id)
        } catch (e: Exception) {
            hostResources.getString(id)
        }
    }


}