package com.sample.pluginsampleapplication.classloadersample


import android.content.res.Configuration
import android.content.res.Resources
import android.content.res.XmlResourceParser
import android.graphics.Movie
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue

 /**
     * 构造插件Resources时有两个方案，都要解决一个共同的问题：
     * 系统有可能从宿主Manifest中获取app icon或者logo的资源ID，
     * 然后直接向插件的Resources对象查询这些资源。
     *
     * 第一个方案是MixResources方案，但该方案依赖Resources的Deprecated构造器，
     * 未来可能会不可用。实际上Resources的构造器如果不取消的话，这个方案可以一直使用下去。
     *
     * 第二个方案是利用资源分区，这是一个和AAB设计中dynamic-feature相同的方案，
     * 将宿主和插件apk添加到同一个Resources对象中。
     * 尽管构造这种带有多资源ID分区的Resources对象所需的API在低版本系统上就已经有了，
     * 但通过不断测试发现MAX_API_FOR_MIX_RESOURCES及更低的API系统上，有个别API不能正确支持非0x7f分区的资源。
     */

private class MixResources(
    private val mainResources: Resources, // pluginResource
    private val sharedResources: Resources, // mainResource
) : Resources(mainResources.assets, mainResources.displayMetrics, mainResources.configuration) {

    private var beforeInitDone = false
    private var updateConfigurationCalledInInit = false

    /**
     * 低版本系统中Resources构造器中会调用updateConfiguration方法，
     * 此时mainResources还没有初始化。
     */
    init {
        if (updateConfigurationCalledInInit) {
            updateConfiguration(mainResources.configuration, mainResources.displayMetrics)
        }
        beforeInitDone = true
    }

    private fun <R> tryMainThenShared(function: (res: Resources) -> R) = try {
        function(mainResources)
    } catch (e: NotFoundException) {
        function(sharedResources)
    }

    override fun getText(id: Int) = tryMainThenShared { it.getText(id) }

    override fun getText(id: Int, def: CharSequence?) = tryMainThenShared { it.getText(id, def) }

    override fun getQuantityText(id: Int, quantity: Int) =
        tryMainThenShared { it.getQuantityText(id, quantity) }

    override fun getString(id: Int) =
        tryMainThenShared { it.getString(id) }

    override fun getString(id: Int, vararg formatArgs: Any?) =
        tryMainThenShared { it.getString(id, *formatArgs) }


    override fun getQuantityString(id: Int, quantity: Int, vararg formatArgs: Any?) =
        tryMainThenShared { it.getQuantityString(id, quantity, *formatArgs) }

    override fun getQuantityString(id: Int, quantity: Int) =
        tryMainThenShared {
            it.getQuantityString(id, quantity)
        }

    override fun getTextArray(id: Int): Array<CharSequence> =
        tryMainThenShared {
            it.getTextArray(id)
        }

    override fun getStringArray(id: Int): Array<String> =
        tryMainThenShared {
            it.getStringArray(id)
        }

    override fun getIntArray(id: Int) =
        tryMainThenShared {
            it.getIntArray(id)
        }

    override fun obtainTypedArray(id: Int) =
        tryMainThenShared {
            it.obtainTypedArray(id)
        }

    override fun getDimension(id: Int) =
        tryMainThenShared {
            it.getDimension(id)
        }

    override fun getDimensionPixelOffset(id: Int) =
        tryMainThenShared {
            it.getDimensionPixelOffset(id)
        }

    override fun getDimensionPixelSize(id: Int) =
        tryMainThenShared {
            it.getDimensionPixelSize(id)
        }

    override fun getFraction(id: Int, base: Int, pbase: Int) =
        tryMainThenShared {
            it.getFraction(id, base, pbase)
        }

    override fun getDrawable(id: Int): Drawable =
        tryMainThenShared {
            it.getDrawable(id)
        }

    override fun getDrawable(id: Int, theme: Theme?): Drawable =
        tryMainThenShared {
            it.getDrawable(id, theme)
        }

    override fun getDrawableForDensity(id: Int, density: Int) =
        tryMainThenShared {
            it.getDrawableForDensity(id, density)
        }

    override fun getDrawableForDensity(id: Int, density: Int, theme: Theme?) =
        tryMainThenShared {
            it.getDrawableForDensity(id, density, theme)
        }

    override fun getMovie(id: Int): Movie =
        tryMainThenShared {
            it.getMovie(id)
        }

    override fun getColor(id: Int) =
        tryMainThenShared {
            it.getColor(id)
        }

    override fun getColor(id: Int, theme: Theme?) =
        tryMainThenShared {
            it.getColor(id, theme)
        }

    override fun getColorStateList(id: Int) =
        tryMainThenShared {
            it.getColorStateList(id)
        }

    override fun getColorStateList(id: Int, theme: Theme?) =
        tryMainThenShared {
            it.getColorStateList(id, theme)
        }

    override fun getBoolean(id: Int) =
        tryMainThenShared {
            it.getBoolean(id)
        }

    override fun getInteger(id: Int) =
        tryMainThenShared {
            it.getInteger(id)
        }

    override fun getLayout(id: Int) =
        tryMainThenShared {
            it.getLayout(id)
        }

    override fun getAnimation(id: Int) =
        tryMainThenShared {
            it.getAnimation(id)
        }

    override fun getXml(id: Int) =
        tryMainThenShared {
            it.getXml(id)
        }

    override fun openRawResource(id: Int) =
        tryMainThenShared {
            it.openRawResource(id)
        }

    override fun openRawResource(id: Int, value: TypedValue?) =
        tryMainThenShared {
            it.openRawResource(id, value)
        }

    override fun openRawResourceFd(id: Int) =
        tryMainThenShared {
            it.openRawResourceFd(id)
        }

    override fun getValue(id: Int, outValue: TypedValue?, resolveRefs: Boolean) =
        tryMainThenShared {
            it.getValue(id, outValue, resolveRefs)
        }

    override fun getValue(name: String?, outValue: TypedValue?, resolveRefs: Boolean) =
        tryMainThenShared {
            it.getValue(name, outValue, resolveRefs)
        }

    override fun getValueForDensity(
        id: Int,
        density: Int,
        outValue: TypedValue?,
        resolveRefs: Boolean,
    ) =
        tryMainThenShared {
            it.getValueForDensity(id, density, outValue, resolveRefs)
        }

    override fun obtainAttributes(set: AttributeSet?, attrs: IntArray?) =
        tryMainThenShared {
            it.obtainAttributes(set, attrs)
        }

    override fun updateConfiguration(config: Configuration?, metrics: DisplayMetrics?) {
        if (beforeInitDone) {
            tryMainThenShared {
                it.updateConfiguration(config, metrics)
            }
        }
    }

    override fun getDisplayMetrics() =
        tryMainThenShared {
            it.getDisplayMetrics()
        }

    override fun getConfiguration() =
        tryMainThenShared {
            it.getConfiguration()
        }

    override fun getIdentifier(name: String?, defType: String?, defPackage: String?) =
        tryMainThenShared {
            it.getIdentifier(name, defType, defPackage)
        }

    override fun getResourceName(resid: Int) =
        tryMainThenShared {
            it.getResourceName(resid)
        }

    override fun getResourcePackageName(resid: Int) =
        tryMainThenShared {
            it.getResourcePackageName(resid)
        }

    override fun getResourceTypeName(resid: Int) =
        tryMainThenShared {
            it.getResourceTypeName(resid)
        }

    override fun getResourceEntryName(resid: Int): String =
        tryMainThenShared {
            it.getResourceEntryName(resid)
        }

    override fun parseBundleExtras(parser: XmlResourceParser?, outBundle: Bundle?) =
        tryMainThenShared {
            it.parseBundleExtras(parser, outBundle)
        }

    override fun parseBundleExtra(tagName: String?, attrs: AttributeSet?, outBundle: Bundle?) =
        tryMainThenShared {
            it.parseBundleExtra(tagName, attrs, outBundle)
        }
}