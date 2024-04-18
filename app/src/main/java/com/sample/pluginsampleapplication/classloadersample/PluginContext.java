package com.sample.pluginsampleapplication.classloadersample;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.view.LayoutInflater;

import com.sample.pluginsampleapplication.utils.Reflector;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PluginContext extends ContextWrapper {

    private Resources mResources;

    private LayoutInflater mLayoutInflater;

    final private ClassLoader mClassloader;

    private AssetManager mAssetManager;


    public PluginContext(Context base, String apkPath, ClassLoader mClassloader) throws Exception {
        super(base);
        this.mClassloader = mClassloader;

        // 这种插件和资源相互隔离的 ！
        Resources pluginResources = generateResourcesByAssetManager(base, new File(apkPath));
        // 合并起来
        mResources = new MixResources(pluginResources, base.getResources());
        // 这种并不是
        // mResources = createResourcesByOpenApi(apkPath, base);
    }


    private Resources createResourcesByOpenApi(String apkPath, Context base) {
        PackageManager packageManager = base.getPackageManager();
        ApplicationInfo hostAppInfo = base.getApplicationInfo();

        ApplicationInfo pluginAppInfo = new ApplicationInfo();

        pluginAppInfo.uid = hostAppInfo.uid;
        pluginAppInfo.packageName = hostAppInfo.packageName;


        pluginAppInfo.publicSourceDir = apkPath;
        pluginAppInfo.sourceDir = apkPath;

        try {
            String[] hostSharedLibraryFiles = hostAppInfo.sharedLibraryFiles;
            if (hostSharedLibraryFiles == null) {
                String[] array = {apkPath};
                pluginAppInfo.sharedLibraryFiles = array;
            } else {
                List<String> hostSharedFiles = new ArrayList<>(Arrays.asList(hostSharedLibraryFiles));
                hostSharedFiles.add(apkPath);
                String[] strArray = hostSharedFiles.toArray(new String[0]);
                pluginAppInfo.sharedLibraryFiles = strArray;
            }

            //这种是公开的API
            Resources result = packageManager.getResourcesForApplication(pluginAppInfo);

            mAssetManager = result.getAssets();

            return result;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public AssetManager getAssets() {
        return mAssetManager;
    }

    @Override
    public Resources getResources() {
        return mResources;
    }

    @Override
    public Resources.Theme getTheme() {
        return mResources.newTheme();
    }

    @Override
    public Object getSystemService(String name) {
        if (Context.LAYOUT_INFLATER_SERVICE.equals(name)) {
            if (mLayoutInflater == null) {
                LayoutInflater layoutInflater = (LayoutInflater) super.getSystemService(name);
                mLayoutInflater = layoutInflater.cloneInContext(this);
            }
            return mLayoutInflater;
        }
        return super.getSystemService(name);
    }

    @Override
    public ClassLoader getClassLoader() {
        return mClassloader;
    }

    protected AssetManager createAssetManager(File apk) throws Exception {
        AssetManager am = AssetManager.class.newInstance();
        Reflector.with(am).method("addAssetPath", String.class).call(apk.getAbsolutePath());
        return am;
    }

    private Resources generateResourcesByAssetManager(Context context, File apk) {
        AssetManager assetManager;
        try {
            Resources hostResources = context.getResources();
            assetManager = createAssetManager(apk);

            return new Resources(
                    assetManager,
                    hostResources.getDisplayMetrics(),
                    hostResources.getConfiguration());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
