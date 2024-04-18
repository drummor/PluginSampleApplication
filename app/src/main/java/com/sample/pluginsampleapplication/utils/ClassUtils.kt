package com.sample.pluginsampleapplication.utils

@Throws(Exception::class)
fun <T> getInterface(interfaceClz: Class<T>, raw: Class<*>): T {
    return try {
        val interfaceImplementClass: Class<*> = raw
        val interfaceImplement = interfaceImplementClass.newInstance()
        interfaceClz.cast(interfaceImplement)!!
    } catch (e: ClassNotFoundException) {
        throw Exception(e)
    } catch (e: InstantiationException) {
        throw Exception(e)
    } catch (e: ClassCastException) {
        throw Exception(e)
    } catch (e: IllegalAccessException) {
        throw Exception(e)
    }
}