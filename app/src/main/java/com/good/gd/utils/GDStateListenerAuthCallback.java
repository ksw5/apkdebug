package com.good.gd.utils;

import android.app.Activity;
import android.os.Handler;
import com.good.gd.GDStateListener;
import java.lang.ref.WeakReference;

/* loaded from: classes.dex */
public class GDStateListenerAuthCallback {
    private WeakReference<Activity> mActivityWeakRef;

    /* loaded from: classes.dex */
    class hbfhc implements Runnable {
        hbfhc() {
        }

        @Override // java.lang.Runnable
        public void run() {
            Activity activity = (Activity) GDStateListenerAuthCallback.this.mActivityWeakRef.get();
            if (activity != null) {
                ((GDStateListener) activity).onAuthorized();
            }
        }
    }

    public GDStateListenerAuthCallback(Activity activity) {
        this.mActivityWeakRef = new WeakReference<>(activity);
        new Handler().postDelayed(new hbfhc(), 0L);
    }
}
