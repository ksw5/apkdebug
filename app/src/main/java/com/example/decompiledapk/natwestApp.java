package com.example.decompiledapk;

import android.app.Application;
import androidx.appcompat.app.AppCompatDelegate;
import com.good.gd.GDAndroid;
import kotlin.Metadata;

/* compiled from: NetWestApp.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016¨\u0006\u0005"}, d2 = {"Lcom/bold360/natwest/natwestApp;", "Landroid/app/Application;", "()V", "onCreate", "", "app_debug"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class natwestApp extends Application {
    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        GDAndroid.getInstance().applicationInit(this);
        GDAndroid.getInstance().setGDStateListener(new GDEventListener());
    }
}
