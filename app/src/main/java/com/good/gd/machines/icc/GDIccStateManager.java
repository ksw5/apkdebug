package com.good.gd.machines.icc;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import com.good.gd.client.GDClient;
import com.good.gd.context.GDContext;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.icc.IccControllerJniEntry;
import com.good.gd.service.GDActivityStateManager;
import com.good.gd.ui_control.UniversalActivityController;
import com.good.gd.ui_control.WearableNotificationManager;
import com.good.gd.utils.GDInit;
import com.good.gd.utils.INIT_STATE;
import com.good.gd.utils.IccIntentUtils;
import com.good.gd.utils.LogUtils;
import com.good.gt.icc.AppContainerState;
import com.good.gt.icc.GTServicesException;
import com.good.gt.icc.ICCController;
import com.good.gt.icc.IccCommand;
import com.good.gt.icc.IccCoreProtocolTag;
import com.good.gt.icc.IccManagerState;
import com.good.gt.icc.IccManagerStateListener;
import com.good.gt.icc.IccProtocol;
import com.good.gt.icc.IccSideChannelEvent;
import com.good.gt.icc.IccVersion;
import com.good.gt.ndkproxy.icc.IccManager;
import com.good.gt.util.AndroidVersionUtils;
import java.util.EnumSet;

/* loaded from: classes.dex */
public class GDIccStateManager implements IccManagerStateListener {
    private static GDIccStateManager _instance;
    private boolean m_IsProcessingICC_ConReq = false;
    private Runnable sendFrontResponseTask = null;

    /* loaded from: classes.dex */
    class hbfhc implements Runnable {
        final /* synthetic */ String dbjc;

        hbfhc(GDIccStateManager gDIccStateManager, String str) {
            this.dbjc = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            GDIccStateManager.sendFrontResponse(this.dbjc);
        }
    }

    private GDIccStateManager() {
        ICCController iccController = GDInit.getIccController();
        if (iccController != null) {
            iccController.addIccManagerStateListener(this);
        }
    }

    public static GDIccStateManager getInstance() {
        if (_instance == null) {
            _instance = new GDIccStateManager();
        }
        return _instance;
    }

    private boolean interDeviceCommand(IccCommand iccCommand) {
        return EnumSet.of(IccCommand.INTER_DEVICE_CON_REQ, IccCommand.INTER_DEVICE_CON_RESP, IccCommand.INTER_DEVICE_POLICY, IccCommand.INTER_DEVICE_PING, IccCommand.INTER_DEVICE_DATA, IccCommand.INTER_DEVICE_CONTROL, IccCommand.INTER_DEVICE_ACT).contains(iccCommand);
    }

    private boolean needToShowNotification(IccCommand iccCommand) {
        return interDeviceCommand(iccCommand) && AndroidVersionUtils.androidHasBackgroundActivityStartRestrictions() && !GDActivityStateManager.getInstance().inForeground();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void sendFrontResponse(String str) {
        GDLog.DBGPRINTF(14, "GDIccStateManager sendFrontResponse\n");
        try {
            IccManager.getInstance().sendFrontResponse(str);
        } catch (GTServicesException e) {
            LogUtils.logStackTrace(e);
        }
    }

    private void sendFrontResponseIfRequired() {
        Runnable runnable = this.sendFrontResponseTask;
        if (runnable != null) {
            runnable.run();
            this.sendFrontResponseTask = null;
        }
    }

    private boolean shouldSendFrontResponseForIncomingIntent(IccSideChannelEvent iccSideChannelEvent) {
        return (iccSideChannelEvent.command == IccCommand.FRONT) && IccProtocol.shouldSendResponseToFront(iccSideChannelEvent.version);
    }

    private boolean shouldUsePendingIntent(Intent intent) {
        return AndroidVersionUtils.androidHasBackgroundActivityStartRestrictions() && IccVersion.V2_5.compareTo(IccProtocol.getIccVersion(intent)) <= 0;
    }

    private void startShowInternalUI(Intent intent, boolean z, Context context, boolean z2) {
        if (shouldUsePendingIntent(intent)) {
            UniversalActivityController.startInternalActivityForServiceUsingPI((PendingIntent) intent.getParcelableExtra(IccCoreProtocolTag.GD_ICC_SYSTEM_VERIFICATION_BUNDLE), z, context, z2);
        } else {
            UniversalActivityController.startInternalActivityForService(context.getApplicationContext(), z, z2);
        }
    }

    public boolean isReorderWorkAroundNeeded_N() {
        return Build.VERSION.SDK_INT < 26;
    }

    public void onActivityResumed() {
        sendFrontResponseIfRequired();
    }

    @Override // com.good.gt.icc.IccManagerStateListener
    public void onStateUpdate(IccManagerState iccManagerState) {
        boolean z;
        if (iccManagerState.getState() == 0) {
            if (UniversalActivityController.getInstance().mo295getInternalActivity() == null) {
                z = false;
            } else {
                z = UniversalActivityController.getInstance().mo295getInternalActivity().isInMultiWindowMode();
            }
            if (!this.m_IsProcessingICC_ConReq || z) {
                return;
            }
            this.m_IsProcessingICC_ConReq = false;
            if (!IccControllerJniEntry.checkReauthInProgress()) {
                GDLog.DBGPRINTF(14, "GDIccStateManager onStateUpdate - CON_RESP sent now backgrounding\n");
                UniversalActivityController.getInstance().moveTaskToBack();
                return;
            }
            GDLog.DBGPRINTF(14, "GDIccStateManager onStateUpdate - CON_RESP sent do not background the app\n");
        }
    }

    public void processIccMessage(Intent intent, Context context) {
        IccSideChannelEvent onReceivedIntent = IccProtocol.onReceivedIntent(intent, null);
        IccCommand iccCommand = onReceivedIntent.command;
        if (shouldSendFrontResponseForIncomingIntent(onReceivedIntent)) {
            String callingApplication = IccProtocol.callingApplication(intent);
            GDLog.DBGPRINTF(14, "GDIccStateManager processIccMessage - should send a FRONT response\n");
            if (!GDActivityStateManager.getInstance().inForeground()) {
                this.sendFrontResponseTask = new hbfhc(this, callingApplication);
            } else {
                GDLog.DBGPRINTF(14, "GDIccStateManager processIccMessage - sending FRONT_RESP right away\n");
                sendFrontResponse(callingApplication);
            }
        }
        boolean z = !(iccCommand == IccCommand.FRONT);
        AppContainerState containerStateInfo = IccIntentUtils.getContainerStateInfo();
        if (!IccManager.shouldRespondWithContainerError(intent, containerStateInfo)) {
            if (!containerStateInfo.isProvisioned() && iccCommand == IccCommand.CON_REQ) {
                GDLog.DBGPRINTF(13, "GDIccStateManager onHandleIntent - Not Provisioned. \n");
            } else if (containerStateInfo.isICCLocked()) {
                if (EnumSet.of(IccCommand.CON_RESP, IccCommand.ACT_RESP, IccCommand.CSR_RESP, IccCommand.REAUTH_RESP).contains(iccCommand)) {
                    GDLog.DBGPRINTF(16, "GDIccStateManager onHandleIntent - No need to explictly unlock Command Type = " + iccCommand + "\n");
                    startShowInternalUI(intent, false, context, z);
                } else if (needToShowNotification(iccCommand)) {
                    GDLog.DBGPRINTF(16, "GDIccStateManager Wearable ICC command received while in background, Command Type = " + iccCommand + "\n");
                    GDContext.getInstance().setContext(context.getApplicationContext());
                    GDClient.getInstance().init(INIT_STATE.STATE_UNKNOWN);
                    if (iccCommand == IccCommand.INTER_DEVICE_ACT) {
                        UniversalActivityController.showWearableNotification(context, WearableNotificationManager.NotificationType.ACTIVATION);
                    } else {
                        UniversalActivityController.showWearableNotification(context, WearableNotificationManager.NotificationType.AUTHORIZATION);
                    }
                    GDClient.getInstance().authorize(null, false);
                } else {
                    GDLog.DBGPRINTF(14, "GDIccStateManager onHandleIntent - Requires unlocked\n");
                    if (iccCommand == IccCommand.CON_REQ) {
                        this.m_IsProcessingICC_ConReq = true;
                    }
                    GDContext.getInstance().setContext(context.getApplicationContext());
                    GDClient.getInstance().init(INIT_STATE.STATE_UNKNOWN);
                    startShowInternalUI(intent, false, context, z);
                    GDClient.getInstance().authorize(null, false);
                }
            } else {
                GDLog.DBGPRINTF(14, "GDIccStateManager onHandleIntent - already unlocked\n");
                if (iccCommand != IccCommand.ACT_REQ && iccCommand != IccCommand.CSR_REQ && iccCommand != IccCommand.REAUTH_REQ) {
                    if (!IccIntentUtils.isAuthResponse(onReceivedIntent) && iccCommand != IccCommand.REAUTH_RESP) {
                        if (UniversalActivityController.getInstance().uiRequiresUserAction()) {
                            if (iccCommand == IccCommand.CON_REQ) {
                                this.m_IsProcessingICC_ConReq = true;
                            }
                            GDLog.DBGPRINTF(16, "GDIccStateManager onHandleIntent - user action is required\n");
                            startShowInternalUI(intent, false, context, z);
                        }
                    } else {
                        GDLog.DBGPRINTF(16, "GDIccStateManager onHandleIntent - unlocked but Auth CON RESP so ensure us at front\n");
                        startShowInternalUI(intent, true, context, z);
                    }
                } else {
                    startShowInternalUI(intent, false, context, z);
                }
            }
        } else {
            GDLog.DBGPRINTF(16, "GDIccStateManager onHandleIntent - app is remote locked / wiped, respond with error\n");
        }
        UniversalActivityController.getInstance().SetIccServiceContext(context);
        IccIntentUtils.processIccIntent(intent);
    }
}
