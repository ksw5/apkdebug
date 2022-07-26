package com.good.gd.ui_control;

import android.app.Activity;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.text.TextUtils;
import android.view.LayoutInflater;
import com.good.gd.GDIccReceivingActivity;
import com.good.gd.background.detection.AppForegroundChangedListener;
import com.good.gd.background.detection.BackgroundLifecycleCallbacks;
import com.good.gd.client.GDClient;
import com.good.gd.client.GDDefaultAppEventListener;
import com.good.gd.content_Impl.GDClipboardManagerImpl;
import com.good.gd.content_Impl.GDWidgetUserActivityInterface;
import com.good.gd.context.GDContext;
import com.good.gd.database.sqlite.SQLiteDatabase;
import com.good.gd.machines.activation.GDActivationManager;
import com.good.gd.machines.icc.GDIccStateManager;
import com.good.gd.ndkproxy.GDDLPScreenCaptureControl;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.ndkproxy.ui.data.StartUI;
import com.good.gd.ndkproxy.ui.data.base.BBDUIObject;
import com.good.gd.ndkproxy.ui.data.base.BBDUIState;
import com.good.gd.ndkproxy.ui.data.base.BaseUI;
import com.good.gd.ndkproxy.ui.data.dialog.DialogUI;
import com.good.gd.ndkproxy.ui.event.BBDUIUpdateEvent;
import com.good.gd.ui.GDInternalActivity;
import com.good.gd.ui.ViewInteractor;
import com.good.gd.ui_control.WearableNotificationManager;
import com.good.gd.utils.GDActivityInfo;
import com.good.gd.utils.GDActivityUtils;
import com.good.gd.utils.GDInit;
import com.good.gd.utils.MessageFactory;
import com.good.gd.utils.ReflectionUtils;
import com.good.gt.context.GTBaseContext;
import com.good.gt.icc.FrontParams;
import com.good.gt.icc.IccCoreProtocolTag;
import com.good.gt.icc.IccVersion;
import com.good.gt.ndkproxy.icc.IccActivity;
import com.good.gt.util.AndroidVersionUtils;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public class UniversalActivityController implements GDWidgetUserActivityInterface, AppForegroundChangedListener, ViewInteractor {
    private static final long INTERNAL_ACTIVITY_TIMEOUT_WAIT = 5000;
    public static final String LOCATION_PERMISSION_STATE = "LOCATION_PERMISSION_STATE";
    private static final long MINIMUM_ACTIVITY_INTERVAL = 5000;
    private static final long MINIMUM_ACTIVITY_INTERVAL_ANDROID_S = 10000;
    private static UniversalActivityController _instance;
    private Messenger _incomingMessenger;
    private Handler showDialogHandler = new Handler(Looper.getMainLooper());
    private AtomicBoolean shouldMoveToBackgroundWhenResumed = new AtomicBoolean(false);
    private boolean _appInForeground = false;
    private boolean _isInternalActivityVisible = false;
    private int _numRestarts = 0;
    private final int MAX_RESTARTS = 2;
    private boolean _waitingForInternalActivity = false;
    private boolean __waitingForInternalActivityCalledFromService = false;
    private boolean __waitingForInternalActivityFromServiceAuthResponse = false;
    private boolean internalActivityStartedByPI = false;
    private Timer _internalActivityTimer = null;
    private BaseUI _uiBase = new StartUI();
    private DialogToDraw _uiDialog = new DialogToDraw(null);
    private boolean _stateWasUpdated = false;
    private boolean newUIRequest = false;
    private Messenger _serviceMessenger = null;
    private LayoutInflater _layoutInflater = null;
    private boolean _firstTime = true;
    private int _numAppActivities = 0;
    private GDActivityMonitor _activityMonitor = null;
    private WeakReference<GDInternalActivityImpl> _internalActivityWeakRef = null;
    private WeakReference<Activity> _contentActivityWeakRef = null;
    private WeakReference<Activity> _currentActivityWeakRef = null;
    private WeakReference<Context> _currentActivityContextWeakRef = null;
    private WeakReference<Context> _iccServiceContextRef = null;
    private WeakReference<Activity> _activityWithLaunchAffinity = null;
    private String _launchActivityAffinity = null;
    private boolean mPendingActivityResumeMesssage = false;
    private long _lastTimeUserActivityNotified = System.currentTimeMillis();

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class DialogToDraw {
        private DialogUI dbjc;
        private boolean qkduk;

        private DialogToDraw() {
            this.dbjc = null;
            this.qkduk = false;
        }

        /* synthetic */ DialogToDraw(hbfhc hbfhcVar) {
            this();
        }

        void dbjc(DialogUI dialogUI) {
            this.dbjc = dialogUI;
            this.qkduk = true;
        }

        void dbjc() {
            this.dbjc.onStateChanged(BBDUIState.STATE_ACTIVE);
            this.qkduk = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class ehnkx implements Runnable {
        final /* synthetic */ GDDLPScreenCaptureControl dbjc;

        ehnkx(GDDLPScreenCaptureControl gDDLPScreenCaptureControl) {
            this.dbjc = gDDLPScreenCaptureControl;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (UniversalActivityController.this.getGDInternalActivity() != null) {
                this.dbjc.updateScreenCaptureStatus(UniversalActivityController.this.mo295getInternalActivity());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class fdyxd implements Runnable {
        fdyxd() {
        }

        @Override // java.lang.Runnable
        public void run() {
            UniversalActivityController.this.onViewOpened();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class hbfhc extends TimerTask {
        hbfhc() {
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            UniversalActivityController.this.internalActivityWaitTimeOut();
        }
    }

    /* loaded from: classes.dex */
    private static class mjbm extends Handler {
        private final WeakReference<UniversalActivityController> dbjc;

        public mjbm(UniversalActivityController universalActivityController) {
            this.dbjc = new WeakReference<>(universalActivityController);
        }

        @Override // android.os.Handler
        public synchronized void handleMessage(Message message) {
            GDLog.DBGPRINTF(16, "UAC: handleMessage: " + MessageFactory.showMessage(message) + "\n");
            if (this.dbjc.get() != null) {
                try {
                    GDLog.DBGPRINTF(16, "UniversalActivityController.handleMessage: " + MessageFactory.showMessage(message) + " UNHANDLED\n");
                    super.handleMessage(message);
                } catch (ClassCastException e) {
                    throw new RuntimeException("UniversalActivityController: handleMessage: " + e);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class pmoiy implements Runnable {
        final /* synthetic */ GDDLPScreenCaptureControl dbjc;

        pmoiy(GDDLPScreenCaptureControl gDDLPScreenCaptureControl) {
            this.dbjc = gDDLPScreenCaptureControl;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (UniversalActivityController.this.getCurrentActivity() != null) {
                this.dbjc.updateScreenCaptureStatus(UniversalActivityController.this.getCurrentActivity());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class yfdke implements Runnable {
        yfdke() {
        }

        @Override // java.lang.Runnable
        public void run() {
            UniversalActivityController.this.shouldMoveToBackgroundWhenResumed.set(false);
        }
    }

    private UniversalActivityController() {
        this._incomingMessenger = null;
        this._incomingMessenger = new Messenger(new mjbm(this));
    }

    private boolean canSkipGDInternalActivityRestart() {
        if (GDDefaultAppEventListener.getInstance().isTrustedAuthenticator()) {
            GDLog.DBGPRINTF(14, "UAC: canSkipGDInternalActivityRestart - TA\n");
            return true;
        } else if (this.internalActivityStartedByPI) {
            GDLog.DBGPRINTF(14, "UAC: canSkipGDInternalActivityRestart - PI\n");
            return false;
        } else if (this._appInForeground || this._numRestarts <= 2) {
            return false;
        } else {
            GDLog.DBGPRINTF(14, "UAC: canSkipGDInternalActivityRestart - bg\n");
            return true;
        }
    }

    private void cancelSignalWaitingInternalActivity() {
        signalWaitingInternalActivity(false, false, false);
    }

    private synchronized boolean checkWaitingInternalActivity() {
        return this._waitingForInternalActivity;
    }

    private void clearActivityWithLaunchAffinity() {
        WeakReference<Activity> weakReference = this._activityWithLaunchAffinity;
        if (weakReference != null) {
            weakReference.clear();
            this._activityWithLaunchAffinity = null;
        }
    }

    private void clearCurrentActivity(Activity activity) {
        GDLog.DBGPRINTF(16, "clearCurrentActivity\n");
        if (this._currentActivityWeakRef == null || getCurrentActivity() != activity) {
            return;
        }
        this._currentActivityWeakRef.clear();
        this._currentActivityWeakRef = null;
    }

    private void clearCurrentActivityContext(Context context) {
        GDLog.DBGPRINTF(16, "clearCurrentActivityContext\n");
        if (this._currentActivityContextWeakRef == null || getCurrentActivityContext() != context) {
            return;
        }
        this._currentActivityContextWeakRef.clear();
        this._currentActivityContextWeakRef = null;
    }

    private void clearGDInternalActivity(GDInternalActivityImpl gDInternalActivityImpl) {
        GDLog.DBGPRINTF(16, "clearGDInternalActivity\n");
        if (this._internalActivityWeakRef == null || getGDInternalActivity() != gDInternalActivityImpl) {
            return;
        }
        this._internalActivityWeakRef.clear();
        this._internalActivityWeakRef = null;
    }

    private void closeInternalDialogUI() {
        if (this._uiDialog.dbjc != null) {
            this._uiDialog.dbjc.close();
            this._uiDialog.qkduk = true;
        }
    }

    private Context getActiveContext() {
        Context currentActivityContext = getCurrentActivityContext();
        return currentActivityContext != null ? currentActivityContext : getGDInternalActivity();
    }

    private Activity getActivityWithLaunchAffinity() {
        WeakReference<Activity> weakReference = this._activityWithLaunchAffinity;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    private Intent getAppMainActivityIntent(Context context, String str) {
        Intent launchIntent = launchIntent(context);
        GDLog.DBGPRINTF(16, "UAC:handleICCFrontRequest, start Main Activity " + launchIntent.toString() + "\n");
        if (str != null && !str.isEmpty()) {
            launchIntent.putExtra(str, launchIntent.getComponent());
        }
        return launchIntent;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public GDInternalActivityImpl getGDInternalActivity() {
        WeakReference<GDInternalActivityImpl> weakReference = this._internalActivityWeakRef;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    private Context getIccServiceContext() {
        WeakReference<Context> weakReference = this._iccServiceContextRef;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    public static synchronized UniversalActivityController getInstance() {
        UniversalActivityController universalActivityController;
        synchronized (UniversalActivityController.class) {
            if (_instance == null) {
                _instance = new UniversalActivityController();
            }
            universalActivityController = _instance;
        }
        return universalActivityController;
    }

    private boolean hasLaunchAffinity(Activity activity) {
        try {
            return TextUtils.equals(activity.getPackageManager().getActivityInfo(activity.getComponentName(), 0).taskAffinity, getLaunchActivityAffinity(activity));
        } catch (PackageManager.NameNotFoundException e) {
            GDLog.DBGPRINTF(13, "UAC: hasLaunchAffinity: " + e + "\n");
            return false;
        }
    }

    private void initializeStartUI() {
        if (this._uiBase == null) {
            this._uiBase = new StartUI();
        }
    }

    private IntentAndContextForStart intentAndContextForGDInternalActivity(Context context, boolean z) {
        boolean z2;
        GDLog.DBGPRINTF(16, "UAC: intentAndContextForGDInternalActivity IN\n");
        GDIccStateManager gDIccStateManager = GDIccStateManager.getInstance();
        Intent intent = new Intent();
        Context activeContext = getActiveContext();
        boolean z3 = false;
        boolean z4 = true;
        if (activeContext == null) {
            activeContext = context;
            z2 = true;
        } else {
            z2 = false;
        }
        boolean z5 = z2;
        if (activeContext == null) {
            return IntentAndContextForStart.from(null, null);
        }
        if (GDDefaultAppEventListener.getInstance().isTrustedAuthenticator() && getNumberAppActivities() > 0) {
            GDLog.DBGPRINTF(16, "UAC: intentAndContextForGDInternalActivity TA\n");
            intent.setClass(context, IccActivity.class).putExtra(IccCoreProtocolTag.ICC_BringToFront_Activity, true);
        } else {
            if (gDIccStateManager.isReorderWorkAroundNeeded_N() || AndroidVersionUtils.androidHasBackgroundActivityStartRestrictions()) {
                z3 = true;
            }
            if (z3) {
                intent.setClass(activeContext, IccActivity.class).putExtra(IccCoreProtocolTag.ICC_BringToFront_Internal_Activity, true);
                if (!z2) {
                    IccActivity.setContextRef(activeContext);
                }
            } else {
                intent.setClass(activeContext, GDInternalActivity.class);
            }
            z3 = true;
        }
        if (!GDActivityUtils.hasFeaturePC(activeContext)) {
            z4 = z5;
        }
        if (z4) {
            intent.addFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
        }
        GDLog.DBGPRINTF(14, "UAC: start Internal Activity, New Task = " + z4 + "\n");
        intent.addFlags(131072);
        if (z) {
            intent.addFlags(8388608);
        }
        return new IntentAndContextForStart(intent, activeContext, z3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void internalActivityWaitTimeOut() {
        GDLog.DBGPRINTF(12, "UAC: internalActivityWaitTimeOut, Timeout Waiting for InternalActivity to be launched reset flag and retry\n");
        this._waitingForInternalActivity = false;
        if (canSkipGDInternalActivityRestart()) {
            cancelSignalWaitingInternalActivity();
            return;
        }
        if (this.__waitingForInternalActivityCalledFromService) {
            Context activeContext = getActiveContext();
            Context iccServiceContext = getIccServiceContext();
            if (activeContext == null) {
                activeContext = iccServiceContext;
            }
            startInternalActivityForService(activeContext, this.__waitingForInternalActivityFromServiceAuthResponse, true);
        } else {
            startInternalActivity();
        }
    }

    private boolean isAndroidSOrHigher() {
        return Build.VERSION.SDK_INT > 30 || GTBaseContext.getInstance().getApplicationContext().getApplicationInfo().targetSdkVersion > 30;
    }

    private static boolean isLocationPermissionsRevoked(Bundle bundle, Context context) {
        if (bundle != null && context != null) {
            boolean z = bundle.getBoolean(LOCATION_PERMISSION_STATE);
            boolean z2 = context.checkSelfPermission("android.permission.ACCESS_FINE_LOCATION") == 0;
            if (z && !z2) {
                return true;
            }
        }
        return false;
    }

    private static Intent launchIntent(Context context) {
        Intent launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        launchIntentForPackage.addFlags(131072);
        return launchIntentForPackage;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onViewOpened() {
        if (mo295getInternalActivity() != null) {
            if (this._uiDialog.dbjc != null && this._uiDialog.qkduk) {
                if (this._uiDialog.dbjc.showDialog(getGDInternalActivity())) {
                    this._uiBase.onStateChanged(BBDUIState.STATE_PAUSED);
                    this._uiDialog.dbjc();
                }
            } else {
                this._uiBase.onStateChanged(BBDUIState.STATE_ACTIVE);
            }
            if (this._uiBase.getBBDUIType() == BBUIType.UI_UNLOCK || this._uiBase.getBBDUIType() == BBUIType.UI_ACTIVATION_UNLOCK || this._uiBase.getBBDUIType() == BBUIType.UI_ACTIVATION_UNLOCK_BIOMETRIC) {
                return;
            }
            getGDInternalActivity().checkDialogState();
            return;
        }
        GDLog.DBGPRINTF(13, "UAC: onViewOpened() - activity reference was lost");
    }

    private void processActivityResumedEvent(Activity activity) {
        this.mPendingActivityResumeMesssage = !sendMessageOptional(MessageFactory.newMessage(1016));
        setCurrentActivity(activity);
        setCurrentActivityContext(activity);
        if (this.shouldMoveToBackgroundWhenResumed.getAndSet(false)) {
            moveTaskToBack(false);
        }
    }

    private void registerActivityMonitor(Application application, GDActivityMonitor gDActivityMonitor) {
        boolean z = false;
        if (ReflectionUtils.canUseReflectionInAndroidPorLater()) {
            try {
                ArrayList arrayList = (ArrayList) ReflectionUtils.getFieldValue(Application.class, application, "mActivityLifecycleCallbacks");
                if (arrayList != null) {
                    synchronized (arrayList) {
                        arrayList.add(gDActivityMonitor);
                    }
                    z = true;
                }
            } catch (Exception e) {
                GDLog.DBGPRINTF(13, "UAC: registerActivityMonitor failed : " + e + "\n");
            }
            GDLog.DBGPRINTF(16, "UAC: registerActivityMonitor: " + z + "\n");
        }
        if (!z) {
            application.registerActivityLifecycleCallbacks(gDActivityMonitor);
        }
    }

    private void resetNumberAppActivities() {
        this._numAppActivities = 0;
    }

    private void setActivityWithLaunchAffinity(Activity activity) {
        this._activityWithLaunchAffinity = new WeakReference<>(activity);
    }

    private void setCurrentActivity(Activity activity) {
        GDLog.DBGPRINTF(16, "setCurrentActivity\n");
        this._currentActivityWeakRef = new WeakReference<>(activity);
    }

    private void setCurrentActivityContext(Context context) {
        GDLog.DBGPRINTF(16, "setCurrentActivityContext\n");
        this._currentActivityContextWeakRef = new WeakReference<>(context);
    }

    private void setGDInternalActivity(GDInternalActivityImpl gDInternalActivityImpl) {
        GDLog.DBGPRINTF(16, "setGDInternalActivity\n");
        this._internalActivityWeakRef = new WeakReference<>(gDInternalActivityImpl);
    }

    private boolean shouldCountActivity(Activity activity) {
        return !(activity instanceof GDInternalActivity) && !(activity instanceof GDIccReceivingActivity);
    }

    private boolean shouldShowNewDialog(DialogUI dialogUI) {
        DialogToDraw dialogToDraw = this._uiDialog;
        return (dialogToDraw == null || dialogUI == dialogToDraw.dbjc) ? false : true;
    }

    private void showUI() {
        getGDInternalActivity().openView(this._uiBase, this.newUIRequest);
        boolean z = false;
        this.newUIRequest = false;
        if (this._stateWasUpdated) {
            getGDInternalActivity().stateWasUpdated();
            this._stateWasUpdated = false;
        }
        BBUIType bBDUIType = this._uiDialog.dbjc != null ? this._uiDialog.dbjc.getBBDUIType() : null;
        if (bBDUIType == BBUIType.UI_UNLOCK_BIOMETRIC || bBDUIType == BBUIType.UI_ACTIVATION_UNLOCK_BIOMETRIC || bBDUIType == BBUIType.UI_REAUTH_UNLOCK_BIOMETRIC) {
            z = true;
        }
        if (z) {
            this.showDialogHandler.removeCallbacksAndMessages(null);
            this.showDialogHandler.postDelayed(new fdyxd(), 100L);
            return;
        }
        onViewOpened();
    }

    public static synchronized void showWearableNotification(Context context, WearableNotificationManager.NotificationType notificationType) {
        synchronized (UniversalActivityController.class) {
            getInstance().initializeStartUI();
            WearableNotificationManager.showNotification(IntentAndContextForStart.from(launchIntent(context), context), notificationType);
        }
    }

    private synchronized void signalWaitingInternalActivity(boolean z, boolean z2, boolean z3) {
        GDLog.DBGPRINTF(16, "UAC: signalWaitingInternalActivity" + z + "\n");
        if (z) {
            this._numRestarts++;
            this._waitingForInternalActivity = true;
            this.__waitingForInternalActivityCalledFromService = z2;
            this.__waitingForInternalActivityFromServiceAuthResponse = z3;
            Timer timer = this._internalActivityTimer;
            if (timer != null) {
                timer.cancel();
            }
            Timer timer2 = new Timer();
            this._internalActivityTimer = timer2;
            timer2.schedule(new hbfhc(), 5000L);
        } else {
            this._numRestarts = 0;
            this._waitingForInternalActivity = false;
            this.__waitingForInternalActivityCalledFromService = false;
            this.__waitingForInternalActivityFromServiceAuthResponse = false;
            Timer timer3 = this._internalActivityTimer;
            if (timer3 != null) {
                timer3.cancel();
                this._internalActivityTimer = null;
            }
        }
    }

    private void startGDInternalActivityForService(Context context, boolean z, boolean z2) {
        signalWaitingInternalActivity(true, true, z);
        if (!z) {
            initializeStartUI();
        }
        IntentAndContextForStart intentAndContextForGDInternalActivity = intentAndContextForGDInternalActivity(context, z2);
        if (!intentAndContextForGDInternalActivity.shouldWaitInternalActivity()) {
            cancelSignalWaitingInternalActivity();
        }
        intentAndContextForGDInternalActivity.startActivity();
        internalActivityStartRequested();
    }

    private synchronized boolean startInternalActivity() {
        if (getCurrentActivity() == null) {
            GDLog.DBGPRINTF(13, "UAC: startInternalActivity getCurrentActivity() == null\n");
            GDActivationManager.getInstance().internalUIWatingToBeDisplayed();
            return false;
        } else if (this._uiBase == null) {
            GDLog.DBGPRINTF(13, "UAC: startInternalActivity _uiBase == null\n");
            return false;
        } else {
            this._layoutInflater = (LayoutInflater) getCurrentActivity().getSystemService("layout_inflater");
            GDLog.DBGPRINTF(16, "UAC: startInternalActivity, waitingForActivity =" + checkWaitingInternalActivity() + " \n");
            if (!checkWaitingInternalActivity()) {
                signalWaitingInternalActivity(true, false, false);
                Intent intent = new Intent(getCurrentActivity(), GDInternalActivity.class);
                intent.setFlags(131072);
                getCurrentActivity().startActivity(intent);
            }
            internalActivityStartRequested();
            return true;
        }
    }

    public static synchronized void startInternalActivityForService(Context context, boolean z, boolean z2) {
        synchronized (UniversalActivityController.class) {
            GDLog.DBGPRINTF(16, "UAC: startInternalActivityForService - authResponse = " + z + " excludeFromRecents = " + z2 + "\n");
            UniversalActivityController universalActivityController = getInstance();
            if (!universalActivityController.checkWaitingInternalActivity()) {
                universalActivityController.startGDInternalActivityForService(context, z, z2);
            }
        }
    }

    public static synchronized void startInternalActivityForServiceUsingPI(PendingIntent pendingIntent, boolean z, Context context, boolean z2) {
        synchronized (UniversalActivityController.class) {
            GDLog.DBGPRINTF(14, "UAC: startInternalActivityForServiceUsingPI\n");
            UniversalActivityController universalActivityController = getInstance();
            universalActivityController.signalWaitingInternalActivity(true, true, z);
            if (!z) {
                universalActivityController.initializeStartUI();
            }
            if (pendingIntent != null) {
                IntentAndContextForStart intentAndContextForGDInternalActivity = universalActivityController.intentAndContextForGDInternalActivity(context, z2);
                if (intentAndContextForGDInternalActivity != null) {
                    if (!intentAndContextForGDInternalActivity.shouldWaitInternalActivity()) {
                        universalActivityController.cancelSignalWaitingInternalActivity();
                    }
                    new ActivityStarter(intentAndContextForGDInternalActivity.getIntent(), intentAndContextForGDInternalActivity.getContext(), pendingIntent).startActivity();
                    universalActivityController.internalActivityStartByPIRequested();
                } else {
                    GDLog.DBGPRINTF(12, "UAC: internalActivityIntentForService returned null\n");
                }
            }
        }
    }

    private void updateInternalActivityEvent(BBDUIUpdateEvent bBDUIUpdateEvent) {
        GDLog.DBGPRINTF(16, "UAC: updateInternalActivityEvent - internalActivity = " + getGDInternalActivity() + "\n");
        if (getGDInternalActivity() != null) {
            getGDInternalActivity().update(bBDUIUpdateEvent);
        }
    }

    private void updateInternalActivityUIObject() {
        GDLog.DBGPRINTF(16, "UAC: updateInternalActivityUIObject - internalActivity = " + getGDInternalActivity() + "\n");
        if (getGDInternalActivity() != null) {
            updateInternalActivity();
        } else {
            startInternalActivity();
        }
    }

    public void SetIccServiceContext(Context context) {
        GDLog.DBGPRINTF(16, "SetIccServiceContext\n");
        this._iccServiceContextRef = new WeakReference<>(context);
    }

    public void activityCreated(Activity activity) {
        Intent intent = activity.getIntent();
        GDLog.DBGPRINTF(16, "UAC: activityCreated: " + activity + "\n");
        GDContext.getInstance().setContext(activity.getApplicationContext());
        GDClient.getInstance().initForeground(new GDActivityInfo(activity));
        if (this._activityMonitor == null) {
            this._activityMonitor = new GDActivityMonitor();
            registerActivityMonitor(activity.getApplication(), this._activityMonitor);
        }
        if (shouldCountActivity(activity)) {
            this._numAppActivities++;
        }
        if (this._firstTime) {
            GDClipboardManagerImpl.getInstance().registerUserActivityInterface(this);
            if (GDActivityUtils.checkIsGDActivity(activity) && !GDInit.isActivityExported(activity.getClass().getName()) && ((intent == null || !intent.hasCategory("android.intent.category.LAUNCHER") || !"android.intent.action.MAIN".equals(intent.getAction())) && !GDDefaultAppEventListener.getInstance().isTrustedAuthenticator())) {
                GDLog.DBGPRINTF(14, "UniversalActivityController: first time launch is not the launcher activity or exported\n");
                if (!activity.isFinishing()) {
                    activity.finish();
                }
            } else if (!GDActivityUtils.checkIsGDActivity(activity) || GDInit.isActivityExported(activity.getClass().getName()) || (intent.hasCategory("android.intent.category.LAUNCHER") && "android.intent.action.MAIN".equals(intent.getAction()))) {
                this._firstTime = false;
            }
        }
        setCurrentActivityContext(activity);
        if (hasLaunchAffinity(activity)) {
            setActivityWithLaunchAffinity(activity);
        }
    }

    public void activityDestroyed(Activity activity) {
        GDLog.DBGPRINTF(16, "UAC: activityDestroyed: " + activity + "\n");
        if (shouldCountActivity(activity)) {
            this._numAppActivities--;
        }
        clearCurrentActivityContext(activity);
        if (hasLaunchAffinity(activity)) {
            clearActivityWithLaunchAffinity();
        }
    }

    public synchronized void activityPaused(Activity activity) {
        GDLog.DBGPRINTF(16, "UAC: activityPaused: " + activity + "\n");
        sendMessageOptional(MessageFactory.newMessage(1015));
        clearCurrentActivity(activity);
    }

    public synchronized void activityResumed(Activity activity) {
        GDLog.DBGPRINTF(16, "UAC: activityResumed: " + activity + "\n");
        processActivityResumedEvent(activity);
    }

    public synchronized void activityWindowFocusGained(Activity activity) {
        GDLog.DBGPRINTF(16, "UAC: activityWindowFocusGained: " + activity + "\n");
        if (activity != getCurrentActivity()) {
            processActivityResumedEvent(activity);
        }
    }

    @Override // com.good.gd.ui.ViewInteractor
    public void cancelDialog() {
        GDInternalActivityImpl mo295getInternalActivity = mo295getInternalActivity();
        if (mo295getInternalActivity != null) {
            mo295getInternalActivity.cancelDialog();
        }
    }

    @Override // com.good.gd.ui.ViewInteractor
    public void cleanInternalDialogHandler() {
        this.showDialogHandler.removeCallbacksAndMessages(null);
    }

    @Override // com.good.gd.ui.ViewInteractor
    public void cleanInternalDialogUI() {
        if (this._uiDialog.dbjc != null) {
            this._uiDialog.dbjc.close();
            this._uiDialog.dbjc.onStateChanged(BBDUIState.STATE_DESTROYED);
            this._uiDialog.dbjc = null;
        }
    }

    public void clearContentActivity(Activity activity) {
        GDLog.DBGPRINTF(16, "clearContentActivity\n");
        if (this._contentActivityWeakRef == null || getContentActivity() != activity) {
            return;
        }
        this._contentActivityWeakRef.clear();
        this._contentActivityWeakRef = null;
    }

    @Override // com.good.gd.ui.ViewInteractor
    public void closeInternalUI() {
        GDInternalActivityImpl gDInternalActivity = getGDInternalActivity();
        this._uiBase = null;
        if (gDInternalActivity != null) {
            gDInternalActivity.closeViewAndActivity();
            clearGDInternalActivity(gDInternalActivity);
        }
    }

    public void deinitializeStartUI() {
        BaseUI baseUI = this._uiBase;
        if (baseUI != null && (baseUI instanceof StartUI)) {
            this._uiBase = null;
        }
    }

    public void enteringBackground() {
        GDLog.DBGPRINTF(16, "UAC: enteringBackground\n");
        if (mo295getInternalActivity() != null && getNumberAppActivities() == 0 && !mo295getInternalActivity().isFinishing()) {
            GDLog.DBGPRINTF(14, "UAC: GDInternalActivity will be closed\n");
            mo295getInternalActivity().finish();
        }
        WeakReference<Activity> weakReference = this._currentActivityWeakRef;
        if (weakReference != null) {
            weakReference.clear();
            this._currentActivityWeakRef = null;
        }
        signalWaitingInternalActivity(false, false, false);
    }

    public Activity getContentActivity() {
        WeakReference<Activity> weakReference = this._contentActivityWeakRef;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    public Activity getCurrentActivity() {
        WeakReference<Activity> weakReference = this._currentActivityWeakRef;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    public Context getCurrentActivityContext() {
        WeakReference<Context> weakReference = this._currentActivityContextWeakRef;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    public String getLaunchActivityAffinity(Context context) {
        if (this._launchActivityAffinity == null) {
            PackageManager packageManager = context.getPackageManager();
            this._launchActivityAffinity = packageManager.getLaunchIntentForPackage(context.getPackageName()).resolveActivityInfo(packageManager, 0).taskAffinity;
        }
        return this._launchActivityAffinity;
    }

    @Override // com.good.gd.ui.ViewInteractor
    public LayoutInflater getLayoutInflater() {
        return this._layoutInflater;
    }

    public int getNumberAppActivities() {
        return this._numAppActivities;
    }

    public void handleICCFrontRequest(FrontParams frontParams) {
        IccVersion iccVersion = frontParams.version;
        GDLog.DBGPRINTF(16, "UAC:handleICCFrontRequest Icc Version = " + iccVersion + "\n");
        Context applicationContext = GDContext.getInstance().getApplicationContext();
        if (applicationContext == null) {
            GDLog.DBGPRINTF(12, "UAC:handleICCFrontRequest does not have an application context\n");
            return;
        }
        int numberAppActivities = getNumberAppActivities();
        GDLog.DBGPRINTF(16, "UAC:handleICCFrontRequest numActivities = " + numberAppActivities + "\n");
        ActivityStarter activityStarter = new ActivityStarter();
        activityStarter.pendingIntent = frontParams.pendingIntent;
        if (numberAppActivities == 0) {
            activityStarter.launchIntent = getAppMainActivityIntent(applicationContext, frontParams.activityOverrideKey);
            activityStarter.context = applicationContext;
        } else if (iccVersion != IccVersion.V1) {
            Activity activityWithLaunchAffinity = getActivityWithLaunchAffinity();
            if (activityWithLaunchAffinity != null) {
                Intent intent = new Intent(activityWithLaunchAffinity, IccActivity.class);
                intent.addFlags(131072);
                intent.putExtra(IccCoreProtocolTag.ICC_BringToFront_Activity, true);
                activityStarter.launchIntent = intent;
                activityStarter.context = activityWithLaunchAffinity;
            } else {
                GDLog.DBGPRINTF(16, "UAC:handleICCFrontRequest Icc V2, no valid Activities, launch main activity\n");
                activityStarter.launchIntent = getAppMainActivityIntent(applicationContext, frontParams.activityOverrideKey);
                activityStarter.context = applicationContext;
            }
        }
        activityStarter.startActivity();
    }

    public void initialize() {
        if (this._layoutInflater == null) {
            this._layoutInflater = (LayoutInflater) GDContext.getInstance().getApplicationContext().getSystemService("layout_inflater");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void internalActivityCreated(GDInternalActivityImpl gDInternalActivityImpl) {
        GDLog.DBGPRINTF(16, "UAC: internalActivityCreated\n");
        signalWaitingInternalActivity(false, false, false);
        setGDInternalActivity(gDInternalActivityImpl);
        DialogToDraw dialogToDraw = this._uiDialog;
        if (dialogToDraw != null && dialogToDraw.dbjc != null) {
            GDLog.DBGPRINTF(16, "UAC: internalActivityCreated set alertChanged\n");
            this._uiDialog.qkduk = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void internalActivityDestroyed(GDInternalActivityImpl gDInternalActivityImpl) {
        if (gDInternalActivityImpl == mo295getInternalActivity()) {
            clearGDInternalActivity(gDInternalActivityImpl);
            GDLog.DBGPRINTF(16, "UAC: internalActivityDestroyed\n");
        } else {
            signalWaitingInternalActivity(false, false, false);
            GDLog.DBGPRINTF(16, "UAC: internalActivity Not Destroyed a different instance is active\n");
        }
        clearCurrentActivityContext(gDInternalActivityImpl);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void internalActivityPaused() {
        GDLog.DBGPRINTF(16, "UAC: internalActivityPaused\n");
        closeInternalDialogUI();
        BaseUI baseUI = this._uiBase;
        if (baseUI != null) {
            baseUI.onStateChanged(BBDUIState.STATE_PAUSED);
        }
        sendMessageOptional(MessageFactory.newMessage(1017));
        this.showDialogHandler.removeCallbacksAndMessages(null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void internalActivityResumed(GDInternalActivityImpl gDInternalActivityImpl) {
        GDLog.DBGPRINTF(16, "UAC: internalActivityResumed\n");
        setGDInternalActivity(gDInternalActivityImpl);
        signalWaitingInternalActivity(false, false, false);
        this.mPendingActivityResumeMesssage = !sendMessageOptional(MessageFactory.newMessage(1018));
        onForegroundEntering();
        this._isInternalActivityVisible = true;
    }

    public void internalActivityStartByPIRequested() {
        this.internalActivityStartedByPI = true;
    }

    public void internalActivityStartRequested() {
        this.internalActivityStartedByPI = false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void internalActivityStarted(GDInternalActivityImpl gDInternalActivityImpl) {
        if (gDInternalActivityImpl == mo295getInternalActivity()) {
            GDLog.DBGPRINTF(16, "UAC: internalActivityStarted\n");
            this._isInternalActivityVisible = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void internalActivityStopped(GDInternalActivityImpl gDInternalActivityImpl) {
        if (gDInternalActivityImpl == mo295getInternalActivity()) {
            GDLog.DBGPRINTF(16, "UAC: internalActivityStopped\n");
            this._isInternalActivityVisible = false;
        }
    }

    public boolean isInternalActivityInForeground() {
        WeakReference<GDInternalActivityImpl> weakReference;
        return this._appInForeground && this._isInternalActivityVisible && (weakReference = this._internalActivityWeakRef) != null && weakReference.get() != null;
    }

    public synchronized boolean isViewWaitingOrOpen(Bundle bundle) {
        boolean z;
        GDLog.DBGPRINTF(16, "UAC: isViewWaitingOrOpen = " + this._uiBase + " \n");
        if (this._uiBase != null) {
            if (!isLocationPermissionsRevoked(bundle, getActiveContext())) {
                z = true;
            }
        }
        z = false;
        return z;
    }

    @Override // com.good.gd.ui.ViewInteractor
    public synchronized void moveTaskToBack() {
        moveTaskToBack(true);
    }

    @Override // com.good.gd.background.detection.AppForegroundChangedListener
    public void onBackgroundEntering() {
        this._appInForeground = false;
        deinitializeStartUI();
    }

    @Override // com.good.gd.background.detection.AppForegroundChangedListener
    public void onForegroundEntering() {
        this._appInForeground = true;
    }

    public void onUserActivity(GDInternalActivityImpl gDInternalActivityImpl) {
        setGDInternalActivity(gDInternalActivityImpl);
        onUserActivity();
    }

    @Override // com.good.gd.ui.ViewInteractor
    public void requestNewUIs(BaseUI baseUI, DialogUI dialogUI) {
        boolean z;
        BaseUI baseUI2 = this._uiBase;
        if (baseUI2 == baseUI) {
            z = false;
        } else {
            if (baseUI2 != null) {
                baseUI2.onStateChanged(BBDUIState.STATE_PAUSED);
            }
            z = true;
        }
        this._uiBase = baseUI;
        if (shouldShowNewDialog(dialogUI)) {
            closeInternalDialogUI();
            this._uiDialog.dbjc(dialogUI);
        }
        this.newUIRequest = z;
        updateInternalActivityUIObject();
    }

    @Override // com.good.gd.ui.ViewInteractor
    public void sendMessage(Message message) {
        GDLog.DBGPRINTF(16, "UAC: sendMessage(" + MessageFactory.showMessage(message) + ")\n");
        Messenger messenger = this._serviceMessenger;
        if (messenger != null) {
            try {
                message.replyTo = this._incomingMessenger;
                messenger.send(message);
                return;
            } catch (RemoteException e) {
                throw new RuntimeException("UniversalActivityController.sendMessageToServer: ", e);
            }
        }
        throw new RuntimeException("UniversalActivityController.sendMessageToServer: not bound to service yet!");
    }

    public boolean sendMessageOptional(Message message) {
        if (this._serviceMessenger != null) {
            sendMessage(message);
            return true;
        }
        return false;
    }

    public void setContentActivity(Activity activity) {
        GDLog.DBGPRINTF(16, "setContentActivity\n");
        this._contentActivityWeakRef = new WeakReference<>(activity);
    }

    @Override // com.good.gd.ui.ViewInteractor
    public synchronized void showDialog() {
        GDInternalActivityImpl mo295getInternalActivity = mo295getInternalActivity();
        if (mo295getInternalActivity != null) {
            mo295getInternalActivity.checkDialogState();
        } else {
            startInternalActivity();
        }
    }

    public synchronized boolean startInternalActivityForResult(GDMonitorFragmentImplInterface gDMonitorFragmentImplInterface, Activity activity) {
        boolean z;
        GDLog.DBGPRINTF(16, "UAC: startInternalActivityForResult, waitingForActivity = " + checkWaitingInternalActivity() + ", isInternalActivityInForeground = " + isInternalActivityInForeground() + " \n");
        z = true;
        if (checkWaitingInternalActivity()) {
            z = false;
        } else {
            if (GDIccStateManager.getInstance().isReorderWorkAroundNeeded_N()) {
                Intent intent = new Intent(activity, IccActivity.class);
                intent.addFlags(131072);
                intent.putExtra(IccCoreProtocolTag.ICC_BringToFront_Internal_Activity, true);
                activity.startActivity(intent);
            } else {
                Intent intent2 = new Intent(activity, GDInternalActivity.class);
                intent2.setFlags(131072);
                gDMonitorFragmentImplInterface.fragmentStartActivityForResult(intent2, 0);
            }
            signalWaitingInternalActivity(true, false, false);
            internalActivityStartRequested();
        }
        return z;
    }

    public boolean uiRequiresUserAction() {
        BaseUI baseUI = this._uiBase;
        if (baseUI == null || !baseUI.isUserActionRequired()) {
            DialogToDraw dialogToDraw = this._uiDialog;
            if (dialogToDraw != null && dialogToDraw.dbjc != null) {
                return this._uiDialog.dbjc.isUserActionRequired();
            }
            return false;
        }
        return true;
    }

    @Override // com.good.gd.ui.ViewInteractor
    public void updateHost(BBDUIUpdateEvent bBDUIUpdateEvent) {
        updateInternalActivityEvent(bBDUIUpdateEvent);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void updateInternalActivity() {
        boolean isInternalActivityInForeground = isInternalActivityInForeground();
        GDLog.DBGPRINTF(16, "UAC: updateInternalActivity - ui to open = " + this._uiBase + " InternalActivity inForeground= " + isInternalActivityInForeground + " InternalActivity = " + getGDInternalActivity() + "\n");
        if (this._uiBase == null) {
            getGDInternalActivity().closeViewAndActivity();
            clearGDInternalActivity(mo295getInternalActivity());
        } else if (isInternalActivityInForeground) {
            showUI();
        } else {
            startInternalActivity();
        }
    }

    public synchronized void updateScreenCaptureStatus(GDDLPScreenCaptureControl gDDLPScreenCaptureControl) {
        if (getGDInternalActivity() != null) {
            getGDInternalActivity().runOnUiThread(new ehnkx(gDDLPScreenCaptureControl));
        }
        if (getCurrentActivity() != null) {
            getCurrentActivity().runOnUiThread(new pmoiy(gDDLPScreenCaptureControl));
        }
    }

    @Override // com.good.gd.ui.ViewInteractor
    public void updateUI(BBDUIObject bBDUIObject) {
        if (this._uiBase == bBDUIObject) {
            this._stateWasUpdated = true;
            closeInternalDialogUI();
            updateInternalActivityUIObject();
            return;
        }
        GDLog.DBGPRINTF(12, "UAC: updateUI different UIs to update\n");
    }

    @Override // com.good.gd.ui.ViewInteractor
    /* renamed from: getInternalActivity  reason: collision with other method in class */
    public synchronized GDInternalActivityImpl mo295getInternalActivity() {
        return getGDInternalActivity();
    }

    public synchronized void moveTaskToBack(boolean z) {
        boolean z2 = false;
        if (getGDInternalActivity() != null) {
            getGDInternalActivity().moveTaskToBack(true);
            z2 = true;
        }
        if (getCurrentActivity() != null) {
            getCurrentActivity().moveTaskToBack(true);
            z2 = true;
        }
        if (z && !z2) {
            this.shouldMoveToBackgroundWhenResumed.set(true);
            GDLog.DBGPRINTF(16, "UAC: No active activity. Delay moving task to back.\n");
            new Handler().postDelayed(new yfdke(), BackgroundLifecycleCallbacks.TIMEOUT);
        }
    }

    public void initialize(Messenger messenger) {
        initialize();
        this._serviceMessenger = messenger;
        sendMessage(MessageFactory.newMessage(1002));
        if (this.mPendingActivityResumeMesssage) {
            GDLog.DBGPRINTF(16, "UAC: initialize sending pending resume message\n");
            this.mPendingActivityResumeMesssage = false;
            sendMessageOptional(MessageFactory.newMessage(1018));
        }
    }

    @Override // com.good.gd.content_Impl.GDWidgetUserActivityInterface
    public void onUserActivity() {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this._lastTimeUserActivityNotified > (isAndroidSOrHigher() ? MINIMUM_ACTIVITY_INTERVAL_ANDROID_S : 5000L)) {
            this._lastTimeUserActivityNotified = currentTimeMillis;
            sendMessageOptional(MessageFactory.newMessage(1014));
            return;
        }
        GDLog.DBGPRINTF(16, "UAC: ignore user activity, occured time is less then minimum interval \n");
    }
}
