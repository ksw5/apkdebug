package com.good.gd.ui_control;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import com.good.gd.GDLocalBroadcastManager;
import com.good.gd.GDStateListener;
import com.good.gd.client.GDClient;
import com.good.gd.client.GDDefaultAppEventListener;
import com.good.gd.context.GDContext;
import com.good.gd.machines.activation.GDActivationManager;
import com.good.gd.ndkproxy.GDDLPControl;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.bypass.GDBypassAbilityImpl;
import com.good.gd.ndkproxy.ui.BBDUIManager;
import com.good.gd.ndkproxy.ui.CoreUI;
import com.good.gd.resources.R;
import com.good.gd.ui.utils.GDInternalThemeHelper;
import com.good.gd.utility.GDWatermarkUtility;
import com.good.gd.utils.GDActivityInfo;
import com.good.gd.utils.GDActivityUtils;
import com.good.gd.utils.GDInit;
import com.good.gd.utils.GDLocalizer;
import com.good.gd.utils.UserAuthUtils;
import com.good.gd.widget.GDTextView;
import java.util.Map;

/* loaded from: classes.dex */
public class GDMonitorFragmentImpl implements View.OnFocusChangeListener, GDStateListener {
    private static final int GDLOCK_VIEW = 2;
    public static final String GDMONITORFRAGMENT = "!!!GD_MONITOR_FRAGMENT!!!";
    static final int GDMONITOR_FRAGMENT_LAUNCH_INTERNAL_ACTIVITY = 0;
    private static final int GDMONITOR_VIEW = 1;
    private static final int GDWARNING_VIEW = 3;
    static final String IS_ICC_INTENT_KEY = "isIccIntent";
    static final String SHOULD_CALL_AUTH_KEY = "shouldCallAuth";
    public static final String SHOW_TIMER_EXPIRED_WARNING = "showtimerexpiredwarning";
    private GDMonitorFragmentImplInterface mFragmentInterface;
    private boolean mWaitingToFinish = false;
    private boolean mIsIccIntent = false;
    protected UniversalActivityController _uac = UniversalActivityController.getInstance();
    protected boolean mActivityInForground = false;
    private View mMonitorView = null;
    private boolean mMonitorViewActive = false;
    private boolean mDestroyCalled = false;
    private boolean mShouldShowDynamicsUI = true;
    final BroadcastReceiver showIdleTimeOutWarningListener = new hbfhc();
    final Runnable r = new yfdke();
    private Handler mHandler = new Handler();

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class ehnkx implements View.OnTouchListener {
        ehnkx() {
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            GDMonitorFragmentImpl.this.userInteraction();
            GDMonitorFragmentImpl.this.addGDMonitorView(1);
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class fdyxd implements View.OnTouchListener {
        fdyxd(GDMonitorFragmentImpl gDMonitorFragmentImpl) {
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return true;
        }
    }

    /* loaded from: classes.dex */
    class hbfhc extends BroadcastReceiver {
        hbfhc() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (GDMonitorFragmentImpl.this.isInactivityWarningRequired()) {
                GDMonitorFragmentImpl.this.addGDMonitorView(3);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class pmoiy extends View {
        private final Paint dbjc = new Paint();
        final Runnable qkduk = new hbfhc();

        /* loaded from: classes.dex */
        class hbfhc implements Runnable {
            hbfhc() {
            }

            @Override // java.lang.Runnable
            public void run() {
                pmoiy pmoiyVar = pmoiy.this;
                if (pmoiyVar != null) {
                    GDLog.DBGPRINTF(12, "ERROR GDWindow detached from Window while Activity still running this isn't allowed calling FINISH on Activity\n");
                    if (GDMonitorFragmentImpl.this.getContainerActivity().isFinishing()) {
                        return;
                    }
                    GDMonitorFragmentImpl.this.getContainerActivity().finish();
                    return;
                }
                throw null;
            }
        }

        public pmoiy(Context context) {
            super(context);
        }

        @Override // android.view.View
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            GDLog.DBGPRINTF(14, "GDMonitorFragmentImpl::onAttachedToWindow --" + GDMonitorFragmentImpl.this.getActivityName() + "\n");
            GDMonitorFragmentImpl.this.mHandler.removeCallbacks(this.qkduk);
        }

        @Override // android.view.View
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            if (GDMonitorFragmentImpl.this.isGDErrorCondition()) {
                GDLog.DBGPRINTF(12, "GDMonitorFragmentImpl::onDetachedFromWindow --" + GDMonitorFragmentImpl.this.getActivityName() + "\n");
                GDMonitorFragmentImpl.this.mHandler.postDelayed(this.qkduk, 50L);
            }
        }

        @Override // android.view.View
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            GDWatermarkUtility.drawWaterMark(canvas, this.dbjc, getContext(), this);
        }

        @Override // android.view.View, android.view.KeyEvent.Callback
        public boolean onKeyDown(int i, KeyEvent keyEvent) {
            GDMonitorFragmentImpl.this.userInteraction();
            return false;
        }

        @Override // android.view.View
        public boolean onTouchEvent(MotionEvent motionEvent) {
            GDMonitorFragmentImpl.this.userInteraction();
            return false;
        }

        @Override // android.view.View
        public void onWindowFocusChanged(boolean z) {
            super.onWindowFocusChanged(z);
            GDLog.DBGPRINTF(16, "GDMonitorFragmentImpl onWindowFocusChanged =" + z + "\n");
            GDMonitorFragmentImpl.this.activityWindowFocusChanged(z);
            if (GDMonitorFragmentImpl.this.mMonitorView == null || z) {
                return;
            }
            GDMonitorFragmentImpl.this.mHandler.postDelayed(GDMonitorFragmentImpl.this.r, 0L);
        }
    }

    /* loaded from: classes.dex */
    class yfdke implements Runnable {
        yfdke() {
        }

        @Override // java.lang.Runnable
        public void run() {
            GDMonitorFragmentImpl.this.addGDMonitorView(1);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public GDMonitorFragmentImpl(GDMonitorFragmentImplInterface gDMonitorFragmentImplInterface) {
        this.mFragmentInterface = gDMonitorFragmentImplInterface;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void activityWindowFocusChanged(boolean z) {
        if (z) {
            activityWindowFocusGained();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void addGDMonitorView(int i) {
        try {
            if (i == 1) {
                View view = this.mMonitorView;
                if (view != null && (view instanceof pmoiy) && isGDMonitorVisible()) {
                    GDLog.DBGPRINTF(16, "GDMonitorFragmentImpl createGDMonitorView already created\n");
                    return;
                } else {
                    removeView();
                    this.mMonitorView = createGDMonitorView();
                }
            } else if (i == 2) {
                if (isGDLockView() && isGDMonitorVisible()) {
                    GDLog.DBGPRINTF(16, "GDMonitorFragmentImpl createLockView already created\n");
                    return;
                } else {
                    removeView();
                    this.mMonitorView = createGDLockView();
                }
            } else if (i == 3) {
                if (isGDWarningView() && isGDMonitorVisible()) {
                    GDLog.DBGPRINTF(16, "GDMonitorFragmentImpl createWarningView already created\n");
                    return;
                } else {
                    removeView();
                    this.mMonitorView = createGDWarningView();
                }
            }
            if (this.mMonitorView == null) {
                GDLog.DBGPRINTF(16, "GDMonitorFragmentImpl monitorView is null - eg due to changing language\n");
                return;
            }
            Window window = getContainerActivity().getWindow();
            while (window.getContainer() != null) {
                window = window.getContainer();
            }
            this.mMonitorView.setOnFocusChangeListener(this);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -1);
            this.mMonitorViewActive = true;
            ((ViewGroup) window.getDecorView()).addView(this.mMonitorView, layoutParams);
        } catch (Throwable th) {
            throw th;
        }
    }

    private View createGDLockView() {
        if (getContainerActivity() != null) {
            GDLog.DBGPRINTF(16, "GDMonitorFragmentImpl createGDLockView\n");
            View inflate = GDInternalThemeHelper.inflate(getContainerActivity().getApplicationContext(), R.layout.bbd_lock_view);
            ((ImageView) inflate.findViewById(R.id.gd_lock_icon)).setContentDescription(GDLocalizer.getLocalizedString("Locked"));
            inflate.setOnTouchListener(new fdyxd(this));
            return inflate;
        }
        return null;
    }

    public static GDMonitorFragmentImpl createGDMonitorFragmentImpl(GDMonitorFragmentImplInterface gDMonitorFragmentImplInterface, String str) {
        if (((GDBypassAbilityImpl) GDContext.getInstance().getDynamicsService("dynamics_bypass_service")).isBypassActivity(str)) {
            return new GDMonitorBypassFragmentImpl(gDMonitorFragmentImplInterface);
        }
        return new GDMonitorFragmentImpl(gDMonitorFragmentImplInterface);
    }

    private View createGDMonitorView() {
        if (getContainerActivity() != null) {
            GDLog.DBGPRINTF(16, "GDMonitorFragmentImpl createGDMonitorView\n");
            return new pmoiy(getContainerActivity());
        }
        return null;
    }

    private View createGDWarningView() {
        if (getContainerActivity() != null) {
            GDLog.DBGPRINTF(16, "GDMonitorFragmentImpl createWarningView\n");
            View inflate = ((LayoutInflater) getContainerActivity().getApplicationContext().getSystemService("layout_inflater")).inflate(R.layout.bbd_warning_view, (ViewGroup) null);
            ((GDTextView) inflate.findViewById(R.id.dialog_header)).setText(GDLocalizer.getLocalizedString("WARNING_IDLE_TIMER_TITLE"));
            ((GDTextView) inflate.findViewById(R.id.dialog_content)).setText(GDLocalizer.getLocalizedString("WARNING_IDLE_TIMER"));
            inflate.setOnTouchListener(new ehnkx());
            return inflate;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getActivityName() {
        return getContainerActivity().getLocalClassName();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isGDErrorCondition() {
        return getContainerActivity() != null && !getContainerActivity().isFinishing() && !this.mDestroyCalled && !this.mFragmentInterface.shouldIgnoreDetach() && this.mMonitorViewActive && GDClient.getInstance().isAuthorized();
    }

    private boolean isGDLockView() {
        View view = this.mMonitorView;
        return (view == null || view.findViewById(R.id.bbd_lock_view_UI) == null) ? false : true;
    }

    private boolean isGDMonitorVisible() {
        View view = this.mMonitorView;
        return view != null && view.getVisibility() == 0 && this.mMonitorView.getWindowVisibility() == 0;
    }

    private boolean isGDWarningView() {
        View view = this.mMonitorView;
        return (view == null || view.findViewById(R.id.bbd_warning_view_UI) == null) ? false : true;
    }

    private boolean isTrustedAuthenticator() {
        return GDDefaultAppEventListener.getInstance().isTrustedAuthenticator();
    }

    private synchronized void removeView() {
        GDLog.DBGPRINTF(16, "GDMonitorFragmentImpl removeGDMonitorView mMonitorView = " + this.mMonitorView + "\n");
        View view = this.mMonitorView;
        if (view != null) {
            this.mMonitorViewActive = false;
            ViewGroup viewGroup = (ViewGroup) view.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(this.mMonitorView);
            }
            this.mMonitorView = null;
            GDLog.DBGPRINTF(16, "GDMonitorFragmentImpl removeGDMonitorView mMonitorView - set to null \n");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void userInteraction() {
        this._uac.onUserActivity();
        GDLog.DBGPRINTF(19, "GDMonitorFragmentImpl::userInteraction() --" + getActivityName() + "\n");
    }

    protected void activityResumed() {
        this._uac.activityResumed(getContainerActivity());
    }

    protected void activityWindowFocusGained() {
        this._uac.activityWindowFocusGained(getContainerActivity());
    }

    protected boolean forceShow() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Activity getContainerActivity() {
        return this.mFragmentInterface.getContainerActivity();
    }

    protected boolean isInMultiWindowMode() {
        if (UniversalActivityController.getInstance().getCurrentActivity() == null) {
            return false;
        }
        return UniversalActivityController.getInstance().getCurrentActivity().isInMultiWindowMode();
    }

    protected boolean isInactivityWarningRequired() {
        return false;
    }

    @Override // com.good.gd.GDStateListener
    public void onAuthorized() {
        if (this.mActivityInForground) {
            addGDMonitorView(1);
        }
    }

    @Override // android.view.View.OnFocusChangeListener
    public void onFocusChange(View view, boolean z) {
        if (this.mMonitorView == null || z) {
            return;
        }
        addGDMonitorView(1);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onFragmentActivityResult(int i, int i2, Intent intent) {
        GDLog.DBGPRINTF(14, "GDMonitorFragmentImpl::onActivityResult()" + getActivityName() + "GDInternalActivity is finished()  \n");
        if (i != 0 || !this.mWaitingToFinish) {
            return;
        }
        GDLog.DBGPRINTF(14, "GDMonitorFragmentImpl::onActivityResult() --" + getActivityName() + "ICC was being Authed now finished. IccReceivingActivity is Resumed \n");
        this.mWaitingToFinish = false;
    }

    public void onFragmentCreate() {
        GDInit.checkInitializeFailed();
        GDContext.getInstance().setContext(getContainerActivity().getApplicationContext());
        GDClient.getInstance().initForeground(new GDActivityInfo(getContainerActivity()));
        boolean z = true;
        this.mFragmentInterface.setFragmentRetainInstance(true);
        GDLog.DBGPRINTF(14, "GDMonitorFragmentImpl::onCreate() --" + getActivityName() + "\n");
        Bundle fragmentArguments = this.mFragmentInterface.getFragmentArguments();
        if (fragmentArguments != null) {
            z = fragmentArguments.getBoolean(SHOULD_CALL_AUTH_KEY, true);
            this.mIsIccIntent = fragmentArguments.getBoolean(IS_ICC_INTENT_KEY, false);
        }
        GDActivationManager gDActivationManager = GDActivationManager.getInstance();
        if (gDActivationManager.isProgrammaticActivating()) {
            this._uac.activityResumed(getContainerActivity());
            z = false;
        }
        boolean isUserAuthRequired = UserAuthUtils.isUserAuthRequired();
        GDLog.DBGPRINTF(14, "GDMonitorFragmentImpl::onCreate() --" + getActivityName() + " shouldAuth =" + isUserAuthRequired + " shouldCallAuth =" + z + " issIccIntent = " + this.mIsIccIntent + "\n");
        if (isUserAuthRequired) {
            this.mShouldShowDynamicsUI = gDActivationManager.shouldShowDynamicsStartupUI();
            GDLog.DBGPRINTF(14, "GDMonitorFragmentImpl mShouldShowDynamicsUI = " + this.mShouldShowDynamicsUI + '\n');
        }
        if (isUserAuthRequired && z) {
            GDLog.DBGPRINTF(14, "GDMonitorFragmentImpl::onCreate() -- shouldAuth = true \n");
            GDClient.getInstance().authorize(null, false);
        }
        this._uac.activityCreated(getContainerActivity());
        GDDefaultAppEventListener.getInstance().addGDStateListener(this);
        this.mDestroyCalled = false;
    }

    public void onFragmentDestroy() {
        GDLog.DBGPRINTF(14, "GDMonitorFragmentImpl::onDestroy() --" + getActivityName() + "\n");
        this._uac.activityDestroyed(getContainerActivity());
        if (GDActivityUtils.checkImplementsGDStateListener(getContainerActivity())) {
            GDDefaultAppEventListener.getInstance().removeGDStateListener((GDStateListener) getContainerActivity());
        }
        GDDefaultAppEventListener.getInstance().removeGDStateListener(this);
        this.mHandler.removeCallbacksAndMessages(null);
        this.mDestroyCalled = true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onFragmentDetach() {
        if (isGDErrorCondition()) {
            GDLog.DBGPRINTF(12, "GDMonitorFragmentImpl::onDetach --" + getActivityName() + "\n");
            GDLog.DBGPRINTF(12, "ERROR Fragment Detached while Activity still running this isn't allowed calling FINISH on Activity\n");
            if (getContainerActivity().isFinishing()) {
                return;
            }
            getContainerActivity().finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onFragmentPause() {
        this.mActivityInForground = false;
        GDLog.DBGPRINTF(14, "GDMonitorFragmentImpl::onPause() --" + getActivityName() + "\n");
        this._uac.activityPaused(getContainerActivity());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onFragmentResume() {
        if (getContainerActivity().isFinishing()) {
            return;
        }
        GDLog.DBGPRINTF(14, "GDMonitorFragmentImpl::onResume() --" + getActivityName() + "\n");
        boolean isUserAuthRequired = UserAuthUtils.isUserAuthRequired();
        boolean z = false;
        if (this.mWaitingToFinish && this._uac.mo295getInternalActivity() == null) {
            this.mWaitingToFinish = false;
        }
        boolean z2 = this.mIsIccIntent;
        if (z2 && !isUserAuthRequired && !this.mWaitingToFinish) {
            GDLog.DBGPRINTF(14, "GDMonitorFragmentImpl::onResume() --" + getActivityName() + " finish() called because ICC and don't need to auth\n");
            if (!getContainerActivity().isFinishing()) {
                getContainerActivity().finish();
            }
        } else if (z2) {
            this.mWaitingToFinish = true;
        }
        boolean forceShow = forceShow();
        boolean z3 = BBDUIManager.getInstance().getCurrentUI() != null;
        boolean isMandatoryActivationCompletionUIPending = CoreUI.isMandatoryActivationCompletionUIPending();
        GDLog.DBGPRINTF(14, "GDMonitorFragmentImpl::onFragmentResume()forceDynamicsUIForActivation = " + isMandatoryActivationCompletionUIPending + '\n');
        if ((this.mShouldShowDynamicsUI || isMandatoryActivationCompletionUIPending) && ((isUserAuthRequired || z3) && ((!isTrustedAuthenticator() || (isTrustedAuthenticator() && BBDUIManager.getInstance().isUiAllowedForTAF())) && !forceShow))) {
            z = this._uac.startInternalActivityForResult(this.mFragmentInterface, getContainerActivity());
            GDLog.DBGPRINTF(14, "GDMonitorFragmentImpl::onResume( ) --" + getActivityName() + " Starting GDInternalActivity\n");
        }
        if (!z) {
            activityResumed();
        }
        if (this.mShouldShowDynamicsUI && isUserAuthRequired && !isTrustedAuthenticator() && !forceShow) {
            addGDMonitorView(2);
        } else if (!isUserAuthRequired) {
            addGDMonitorView(1);
        }
        com.good.gd.ui_control.hbfhc.dbjc();
        GDDLPControl.getInstance().getScreenCaptureControl().updateScreenCaptureStatus(getContainerActivity());
        this.mActivityInForground = true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onFragmentStart() {
        GDLog.DBGPRINTF(14, "GDMonitorFragmentImpl::onStart() --" + getActivityName() + "\n");
        boolean isUserAuthRequired = UserAuthUtils.isUserAuthRequired();
        if (this.mShouldShowDynamicsUI && isUserAuthRequired && !isTrustedAuthenticator() && !forceShow()) {
            addGDMonitorView(2);
        } else if (!isUserAuthRequired) {
            GDLocalBroadcastManager.getInstance().registerReceiver(this.showIdleTimeOutWarningListener, new IntentFilter(SHOW_TIMER_EXPIRED_WARNING));
            addGDMonitorView(1);
        }
        GDDLPControl.getInstance().getScreenCaptureControl().updateScreenCaptureStatus(getContainerActivity());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onFragmentStop() {
        GDLog.DBGPRINTF(16, "GDMonitorFragmentImpl::onStop() --" + getActivityName() + "\n");
        if (this.mMonitorView instanceof pmoiy) {
            GDLocalBroadcastManager.getInstance().unregisterReceiver(this.showIdleTimeOutWarningListener);
            removeView();
        }
    }

    public void onLocked() {
        if (isTrustedAuthenticator() || !this.mShouldShowDynamicsUI) {
            return;
        }
        addGDMonitorView(2);
    }

    @Override // com.good.gd.GDStateListener
    public void onUpdateConfig(Map<String, Object> map) {
    }

    @Override // com.good.gd.GDStateListener
    public void onUpdateEntitlements() {
    }

    @Override // com.good.gd.GDStateListener
    public void onUpdatePolicy(Map<String, Object> map) {
    }

    @Override // com.good.gd.GDStateListener
    public void onUpdateServices() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onUserInteraction() {
        GDLog.DBGPRINTF(19, "GDMonitorFragmentImpl::onUserInteraction() --" + getActivityName() + "\n");
        userInteraction();
    }

    public void onWiped() {
        if (isTrustedAuthenticator() || !this.mShouldShowDynamicsUI) {
            return;
        }
        addGDMonitorView(2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void startInternalActivityForResult() {
        this._uac.startInternalActivityForResult(this.mFragmentInterface, getContainerActivity());
    }
}
