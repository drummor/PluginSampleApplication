package com.sample.pluginsampleapplication.classloadersample;

import android.util.Log;

import dalvik.system.DexClassLoader;

public class ApkClassLoader extends DexClassLoader {
    public ApkClassLoader(String dexPath,
                          String optimizedDirectory,
                          String librarySearchPath,
                          ClassLoader hostClassLoader) { //宿主classLaoder
        super(dexPath, optimizedDirectory, librarySearchPath, hostClassLoader);
    }

    /**
     * 这里处理插件依赖
     *
     * @param name    The <a href="#name">binary name</a> of the class
     * @param resolve If <tt>true</tt> then resolve the class
     * @return
     * @throws ClassNotFoundException
     */

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        return super.loadClass(name, resolve);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return super.findClass(name);
    }

}
