package com.good.gd;

import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.good.gd.client.GDDefaultAppEventListener;
import com.good.gd.icc.GDService;
import com.good.gd.icc.GDServiceException;
import com.good.gd.icc.GDServiceListener;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ui_control.GDMonitorActivityImpl;
import com.good.gd.utils.GDInit;
import com.good.gd.utils.GDLocalizer;
import com.good.gt.icc.ICCController;
import com.good.gt.icc.IccManagerState;
import com.good.gt.icc.IccManagerStateListener;

/* loaded from: classes.dex */
public class GDIccReceivingActivity extends android.app.Activity {
    private GDMonitorActivityImpl mActivityImpl;
    private ICCController mIccController;
    private IccManagerStateListener mIccManagerStateListener;

    /* loaded from: classes.dex */
    class hbfhc implements IccManagerStateListener {
        hbfhc() {
        }

        @Override // com.good.gt.icc.IccManagerStateListener
        public void onStateUpdate(IccManagerState iccManagerState) {
            GDIccReceivingActivity.this.onStateUpdateImpl(iccManagerState);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onStateUpdateImpl(IccManagerState iccManagerState) {
        GDLog.DBGPRINTF(16, "GDIccReceivingActivity::onStateUpdate() --" + iccManagerState + "\n");
        if (iccManagerState.getState() == 0 || iccManagerState.getState() == 5) {
            if (iccManagerState.getState() == 0) {
                GDLog.DBGPRINTF(16, "GDIccReceivingActivity::onStateUpdate() --" + iccManagerState + " Sending Task to Background \n");
                moveTaskToBack(true);
            }
            if (isFinishing()) {
                return;
            }
            GDLog.DBGPRINTF(16, "GDIccReceivingActivity::onStateUpdate() --" + iccManagerState + " Now finishing Activity \n");
            finish();
        }
    }

    @Override // android.app.Activity
    public final void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        super.addContentView(view, layoutParams);
    }

    @Override // android.app.Activity
    public final void finish() {
        super.finish();
    }

    @Override // android.app.Activity
    public final void finishActivity(int i) {
        super.finishActivity(i);
    }

    @Override // android.app.Activity
    public final void finishActivityFromChild(android.app.Activity activity, int i) {
        super.finishActivityFromChild(activity, i);
    }

    @Override // android.app.Activity
    public final ComponentName getCallingActivity() {
        return super.getCallingActivity();
    }

    @Override // android.app.Activity
    public final Intent getIntent() {
        return super.getIntent();
    }

    @Override // android.app.Activity
    public final boolean isDestroyed() {
        return super.isDestroyed();
    }

    @Override // android.app.Activity
    public final boolean isFinishing() {
        return super.isFinishing();
    }

    @Override // android.app.Activity
    public final boolean isTaskRoot() {
        return super.isTaskRoot();
    }

    @Override // android.app.Activity
    protected final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        this.mActivityImpl.ActivityImpl_onActivityResult(i, i2, intent);
    }

    @Override // android.app.Activity
    public final void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        GDMonitorActivityImpl gDMonitorActivityImpl = new GDMonitorActivityImpl(this);
        this.mActivityImpl = gDMonitorActivityImpl;
        if (!gDMonitorActivityImpl.ActivityImpl_onCreate(bundle, true)) {
            GDLog.DBGPRINTF(12, "GDIccReceivingActivity::onCreate() Error launched by non iCC Intent, finishing");
            finish();
            return;
        }
        setContentView(R.layout.bbd_block_view);
        ((TextView) findViewById(R.id.COM_GOOD_GD_BLOCK_VIEW_TITLE_VIEW)).setText(GDLocalizer.getLocalizedString("Authenticating"));
        ((TextView) findViewById(R.id.COM_GOOD_GD_BLOCK_VIEW_MESSAGE_VIEW)).setText("");
        ((ProgressBar) findViewById(R.id.COM_GOOD_GD_BLOCK_VIEW_PROGRESSBAR)).setVisibility(0);
        this.mIccController = GDInit.getIccController();
        hbfhc hbfhcVar = new hbfhc();
        this.mIccManagerStateListener = hbfhcVar;
        this.mIccController.addIccManagerStateListener(hbfhcVar);
        GDServiceListener onSetGDServiceListener = onSetGDServiceListener();
        if (onSetGDServiceListener == null) {
            return;
        }
        try {
            GDService.setServiceListener(onSetGDServiceListener);
        } catch (GDServiceException e) {
            GDLog.DBGPRINTF(12, "GDIccReceivingActivity::onCreate()  Error Setting GDServiceListener --" + e.getMessage() + "\n");
        }
    }

    @Override // android.app.Activity, android.view.LayoutInflater.Factory
    public final View onCreateView(String str, Context context, AttributeSet attributeSet) {
        return super.onCreateView(str, context, attributeSet);
    }

    @Override // android.app.Activity
    protected final void onDestroy() {
        GDMonitorActivityImpl gDMonitorActivityImpl = this.mActivityImpl;
        if (gDMonitorActivityImpl != null) {
            gDMonitorActivityImpl.ActivityImpl_onDestroy();
        }
        ICCController iCCController = this.mIccController;
        if (iCCController != null) {
            iCCController.removeIccManagerStateListener(this.mIccManagerStateListener);
        }
        super.onDestroy();
    }

    @Override // android.app.Activity
    protected final void onPause() {
        super.onPause();
        this.mActivityImpl.ActivityImpl_onPause();
    }

    @Override // android.app.Activity
    protected final void onRestart() {
        super.onRestart();
    }

    @Override // android.app.Activity
    protected final void onResume() {
        super.onResume();
        this.mActivityImpl.ActivityImpl_onResume();
    }

    protected GDServiceListener onSetGDServiceListener() {
        GDLog.DBGPRINTF(16, "GDIccReceivingActivity::onSetGDServiceListener() NOT SET \n");
        return null;
    }

    @Override // android.app.Activity
    protected final void onStart() {
        super.onStart();
        this.mActivityImpl.ActivityImpl_onStart();
    }

    @Override // android.app.Activity
    protected void onStop() {
        super.onStop();
        this.mActivityImpl.ActivityImpl_onStop();
        if (GDDefaultAppEventListener.getInstance().isTrustedAuthenticator()) {
            finish();
        }
    }

    @Override // android.app.Activity
    public final void onUserInteraction() {
        this.mActivityImpl.ActivityImpl_onUserInteraction();
        super.onUserInteraction();
    }

    @Override // android.app.Activity, android.view.LayoutInflater.Factory2
    public final View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        return super.onCreateView(view, str, context, attributeSet);
    }
}
