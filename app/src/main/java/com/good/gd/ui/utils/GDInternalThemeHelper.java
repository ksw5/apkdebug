package com.good.gd.ui.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatDelegate;
import com.good.gd.resources.R;

/* loaded from: classes.dex */
public class GDInternalThemeHelper {
    private static Context contextForGdTheme(Context context) {
        return new hbfhc(context, R.style.GDInternalActivityTheme);
    }

    public static Configuration createConfigOverride(Context context) {
        int nightModeConfiguration = getNightModeConfiguration(AppCompatDelegate.getDefaultNightMode());
        Configuration configuration = context.getResources().getConfiguration();
        int i = configuration.uiMode & (-49);
        configuration.uiMode = i;
        configuration.uiMode = nightModeConfiguration | i;
        return configuration;
    }

    private static int getNightModeConfiguration(int i) {
        if (i != 1) {
            return i != 2 ? 0 : 32;
        }
        return 16;
    }

    public static View inflate(Context context, int i) {
        LayoutInflater from;
        try {
            from = LayoutInflater.from(contextForGdTheme(context));
        } catch (NoClassDefFoundError e) {
            from = LayoutInflater.from(context.getApplicationContext());
        }
        return from.inflate(i, (ViewGroup) null);
    }
}
