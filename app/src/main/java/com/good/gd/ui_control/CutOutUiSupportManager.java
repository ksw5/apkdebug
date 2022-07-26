package com.good.gd.ui_control;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.DisplayCutout;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import androidx.fragment.app.FragmentTransaction;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.resources.R;
import java.lang.ref.WeakReference;

/* loaded from: classes.dex */
public class CutOutUiSupportManager {
    private static final CutOutUiSupportManager instance = new CutOutUiSupportManager();
    private WeakReference<Activity> activityWeakReference;
    private final boolean androidPOrHigher;
    private boolean mHasCutOut = false;
    private boolean mChecked = false;

    /* loaded from: classes.dex */
    class hbfhc implements View.OnApplyWindowInsetsListener {
        hbfhc() {
        }

        @Override // android.view.View.OnApplyWindowInsetsListener
        public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
            CutOutUiSupportManager.this.onApplyWindowInsets(windowInsets);
            return windowInsets;
        }
    }

    private CutOutUiSupportManager() {
        boolean z = false;
        this.androidPOrHigher = Build.VERSION.SDK_INT >= 28 ? true : z;
    }

    private void applyCutOutUIChangesLandscapeMode(Activity activity) {
        activity.getWindow().getDecorView().setSystemUiVisibility(FragmentTransaction.TRANSIT_FRAGMENT_MATCH_ACTIVITY_OPEN);
    }

    private void applyCutOutUIChangesPortraitMode(Activity activity) {
        Window window = activity.getWindow();
        window.addFlags(Integer.MIN_VALUE);
        window.setStatusBarColor(activity.getResources().getColor(R.color.bbd_common_background_color));
        if (!isDarkModeEnabled(activity)) {
            window.getDecorView().setSystemUiVisibility(8192);
        }
    }

    public static CutOutUiSupportManager getInstance() {
        return instance;
    }

    private void hideActionBar(Activity activity) {
        try {
            ActionBar actionBar = activity.getActionBar();
            actionBar.setElevation(0.0f);
            actionBar.hide();
        } catch (NullPointerException e) {
            GDLog.DBGPRINTF(14, "CutOutUiSupportManager: hideActionBar( ) could not get Action Bar\n");
        }
    }

    private boolean isDarkModeEnabled(Context context) {
        return (context.getResources().getConfiguration().uiMode & 48) == 32;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onApplyWindowInsets(WindowInsets windowInsets) {
        Activity activity;
        WeakReference<Activity> weakReference = this.activityWeakReference;
        if (weakReference == null || (activity = weakReference.get()) == null) {
            return;
        }
        DisplayCutout displayCutout = windowInsets.getDisplayCutout();
        GDLog.DBGPRINTF(14, "CutOutUiSupportManager: onApplyWindowInsets\n");
        activity.getWindow().getDecorView().setOnApplyWindowInsetsListener(null);
        boolean z = displayCutout != null;
        this.mHasCutOut = z;
        if (z) {
            GDLog.DBGPRINTF(14, "CutOutUiSupportManager: Device has a display cut out\n");
            applyCutOutUiChanges(activity);
        }
        this.mChecked = true;
    }

    public void applyCutOutUiChanges(Activity activity) {
        hideActionBar(activity);
        if (activity.getResources().getConfiguration().orientation == 1) {
            applyCutOutUIChangesPortraitMode(activity);
        } else {
            applyCutOutUIChangesLandscapeMode(activity);
        }
    }

    public boolean checkForCutOut(Activity activity) {
        if (this.androidPOrHigher && !this.mChecked) {
            hideActionBar(activity);
            this.activityWeakReference = new WeakReference<>(activity);
            activity.getWindow().getAttributes().layoutInDisplayCutoutMode = 1;
            activity.getWindow().getDecorView().setOnApplyWindowInsetsListener(new hbfhc());
        }
        return this.mHasCutOut;
    }

    public void unsubscribe() {
        WeakReference<Activity> weakReference = this.activityWeakReference;
        if (weakReference != null) {
            weakReference.clear();
        }
        this.activityWeakReference = null;
    }
}
