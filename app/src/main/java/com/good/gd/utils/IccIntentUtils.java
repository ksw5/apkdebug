package com.good.gd.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import com.good.gd.client.GDDefaultAppEventListener;
import com.good.gd.containerstate.ndkproxy.GDActivitySupport;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.enterprise.GDEActivationManager;
import com.good.gd.ndkproxy.ui.GDBlock;
import com.good.gt.icc.AppContainerState;
import com.good.gt.icc.ICCController;
import com.good.gt.icc.ICCControllerFactory;
import com.good.gt.icc.IccCommand;
import com.good.gt.icc.IccCommandManager;
import com.good.gt.icc.IccCoreProtocolTag;
import com.good.gt.icc.IccProtocol;
import com.good.gt.icc.IccSideChannelEvent;
import com.good.gt.utils.CONRESP_Parameters;
import java.util.EnumSet;

/* loaded from: classes.dex */
public class IccIntentUtils {
    public static boolean checkForExpectedResponseIntent(Intent intent) {
        IccCommand iccIntentType = getIccIntentType(intent);
        if (!EnumSet.of(IccCommand.CON_RESP, IccCommand.ACT_RESP, IccCommand.CSR_RESP).contains(iccIntentType) || IccCommandManager.getInstance().getCachedRequestForResponse(iccIntentType)) {
            return true;
        }
        GDLog.DBGPRINTF_UNSECURE(14, "IccIntentUtils", "IccIntentUtils.checkForExpectedResponseIntent: " + iccIntentType + " is not expected\n");
        return false;
    }

    public static boolean checkForIccIntent(Intent intent) {
        return (intent == null || !IccCoreProtocolTag.actionString.equalsIgnoreCase(intent.getAction()) || intent.getData() == null || getIccIntentType(intent) == null) ? false : true;
    }

    private static void checkIfActReqCommand(Intent intent) {
        ICCController iCCController;
        if (EnumSet.of(IccCommand.ACT_REQ, IccCommand.CSR_REQ).contains(IccProtocol.getIntentICCCommandType(intent.getData()))) {
            GDLog.DBGPRINTF(14, "GDIccIntentUtils::checkIfActReqCommand () - is Act Req\n");
            if (!GDInit.isInitialized() || (iCCController = ICCControllerFactory.getICCController(null)) == null || iCCController.getIccManagerState().getState() != 0) {
                return;
            }
            GDEActivationManager.getInstance().setProcessingActDelegation(true);
        }
    }

    public static AppContainerState getContainerStateInfo() {
        AppContainerState appContainerState = new AppContainerState();
        appContainerState.setContainerLocked(isLocked());
        appContainerState.setAuthorized(GDDefaultAppEventListener.getInstance().isAuthorized());
        appContainerState.setProvisioned(GDActivitySupport.isProvisioned());
        appContainerState.setRemoteLocked(GDActivitySupport.isRemoteLocked());
        appContainerState.setWiped(GDActivitySupport.isWiped());
        return appContainerState;
    }

    public static IccCommand getIccIntentType(Intent intent) {
        return IccProtocol.getIntentICCCommandType(intent.getData());
    }

    public static boolean isAuthResponse(IccSideChannelEvent iccSideChannelEvent) {
        IccCommand iccCommand = iccSideChannelEvent.command;
        if (iccCommand == null) {
            GDLog.DBGPRINTF(12, "isAuthResponse invalid command type \n");
            return false;
        } else if (iccCommand != IccCommand.CON_RESP) {
            return false;
        } else {
            CONRESP_Parameters cONRESP_Parameters = CONRESP_Parameters.getInstance(iccSideChannelEvent.annotations);
            return (cONRESP_Parameters.clientPublic == null || cONRESP_Parameters.securePackage == null) ? false : true;
        }
    }

    public static boolean isLocked() {
        return GDActivitySupport.isCurrentlyBlockedByPolicyOrLocally() ? !new GDBlock().isMDMBlockActive() : GDActivitySupport.isLocked();
    }

    public static void processIccIntent(Intent intent, Activity activity) {
        GDLog.DBGPRINTF(14, "GDIccIntentUtils::processIccIntent()\n");
        GDInit.initializeIcc();
        ICCController iccController = GDInit.getIccController();
        checkIfActReqCommand(intent);
        iccController.onReceivedIntent(intent, getContainerStateInfo(), activity.getCallingActivity());
    }

    public static IccCommand processIccIntent(Intent intent) {
        IccCommand intentICCCommandType = IccProtocol.getIntentICCCommandType(intent.getData());
        ComponentName componentName = null;
        if (intentICCCommandType == null) {
            GDLog.DBGPRINTF(12, "processIccIntent invalid command type \n");
            return null;
        }
        GDLog.DBGPRINTF(16, "processIccIntent Type = " + intentICCCommandType + "\n");
        checkIfActReqCommand(intent);
        if (IccProtocol.shouldEnforceIccIdentity(IccProtocol.getIccVersion(intent.getData()))) {
            ComponentName IccIdentityDiscovery = IccProtocol.IccIdentityDiscovery(intent);
            if (IccIdentityDiscovery != null) {
                componentName = IccIdentityDiscovery;
            } else {
                GDLog.DBGPRINTF(12, "processIccIntent No Calling Component Error, don't process\n");
                return null;
            }
        }
        if (intentICCCommandType.isResponse()) {
            GDInit.getIccController().onReceivedIntentResponse(intent, componentName);
        } else {
            GDInit.getIccController().onReceivedIntent(intent, getContainerStateInfo(), componentName);
        }
        return intentICCCommandType;
    }
}
