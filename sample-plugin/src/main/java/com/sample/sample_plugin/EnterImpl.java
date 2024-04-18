package com.sample.sample_plugin;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Keep;

import com.sample.plugin.sample.common.Enter;
import com.sample.plugin.sample.common.EnterCallback;


@Keep
public class EnterImpl implements Enter {
    public void enter(Context context, EnterCallback callback) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_loading, null);
        Log.d("drummor", "此时的packageName" + context.getPackageName());
        int id = context.getResources().getIdentifier("host_layout", "layout", context.getPackageName());
        Log.d("drummor", "找到了吗" + id);

        Log.d("drummor", "==>$" + com.sample.plugin.sample.common.R.string.a_base_name_string);
        Log.d("drummor", "==>$" +R.string.a_sample_plugin_name);
        callback.callback(view);
    }

    @Override
    public View fetchPluginCardView(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.layout_plugin_card_view, null);
    }
}
