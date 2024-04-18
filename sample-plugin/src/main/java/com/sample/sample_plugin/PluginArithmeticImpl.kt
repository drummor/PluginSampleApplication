package com.sample.sample_plugin

import androidx.annotation.Keep
import com.sample.plugin.sample.common.IPluginArithmetic

@Keep
class IPluginArithmeticImpl : IPluginArithmetic {
    override fun plus(num: Int, addNum: Int): Int {
        return num + addNum
    }
}