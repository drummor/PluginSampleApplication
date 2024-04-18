package com.sample.plugin.sample.common;

import android.content.Context;
import android.view.View;

import androidx.annotation.Keep;

@Keep
public interface Enter {
    void enter(Context context, EnterCallback callback);

    View fetchPluginCardView(Context context);
}

