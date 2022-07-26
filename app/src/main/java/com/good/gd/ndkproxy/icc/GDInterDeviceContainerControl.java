package com.good.gd.ndkproxy.icc;

import android.content.Context;
import android.os.AsyncTask;
import com.good.gd.containerstate.ndkproxy.GDActivitySupport;
import com.good.gd.ndkproxy.GDDeviceInfo;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.sub.GDSubContainerEventHandler;
import com.good.gd.ndkproxy.sub.GDSubContainerManager;
import com.good.gd.support.GDConnectedApplication;
import com.good.gd.support.GDConnectedApplicationState;
import com.good.gd.support.GDConnectedApplicationType;
import com.good.gd.support.impl.GDConnectedApplicationControl;
import com.good.gd.support.impl.GDConnectedApplicationControlListener;
import com.good.gd.ui.subcontainer_activationui.GTWearActivationUIControl;
import com.good.gd.ui_control.UniversalActivityController;
import com.good.gd.utils.ErrorUtils;
import com.good.gt.icc.AppControl;
import com.good.gt.icc.FrontParams;
import com.good.gt.icc.GTContainerInfo;
import com.good.gt.icc.ICCControllerFactory;
import com.good.gt.icc.InterDeviceConnectedListener;
import com.good.gt.icc.InterDeviceContainerControl;
import com.good.gt.icc.InterDeviceContainerControlListener;
import com.good.gt.icc.ListenerAlreadySetException;
import com.good.gt.interdevice_icc.InterDeviceActivationControlInterface;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class GDInterDeviceContainerControl implements InterDeviceContainerControlListener, AppControl, InterDeviceConnectedListener, GDConnectedApplicationControlListener {
    private static GDInterDeviceContainerControl _instance;
    private final InterDeviceContainerControl _interDeviceContainerControlClient;
    private final GTWearActivationUIControl mActivationUIControl;
    private final GDConnectedApplicationControl mConnectedApplicationControl;

    /* loaded from: classes.dex */
    private static class hbfhc extends AsyncTask<Void, Void, Void> {
        private int dbjc;

        public hbfhc(int i) {
            this.dbjc = i;
        }

        @Override // android.os.AsyncTask
        protected Void doInBackground(Void[] voidArr) {
            GDLog.DBGPRINTF(12, "GDInterDeviceContainerControl.InterDeviceErrorHandler Error = " + this.dbjc);
            int i = this.dbjc;
            if (i != 114) {
                GDLog.DBGPRINTF(12, "GDInterDeviceContainerControl.onInterDeviceError = " + i);
                return null;
            }
            ErrorUtils.throwGDInterDeviceIncompatibleVersionError();
            return null;
        }
    }

    public GDInterDeviceContainerControl() {
        InterDeviceContainerControl interDeviceContainerControl = ICCControllerFactory.getICCController(this).getInterDeviceContainerControl();
        this._interDeviceContainerControlClient = interDeviceContainerControl;
        try {
            interDeviceContainerControl.setContainerControlListener(this);
            interDeviceContainerControl.setDeviceConnectedListener(this);
        } catch (ListenerAlreadySetException e) {
            GDLog.DBGPRINTF(12, "Auth Delegation Consumer - listener error: " + e.toString() + "\n");
        }
        this.mConnectedApplicationControl = GDConnectedApplicationControl.createInstance(this, GDConnectedApplicationType.GD_HANDHELD);
        this.mActivationUIControl = GTWearActivationUIControl.createInstance();
        try {
            GDSubContainerEventHandler.getInstance().initialize();
        } catch (Exception e2) {
            GDLog.DBGPRINTF(12, "GDSubContainerEventHandler - error: " + e2.toString() + "\n");
        }
    }

    public static synchronized GDInterDeviceContainerControl getInstance() {
        GDInterDeviceContainerControl gDInterDeviceContainerControl;
        synchronized (GDInterDeviceContainerControl.class) {
            if (_instance == null) {
                _instance = new GDInterDeviceContainerControl();
            }
            gDInterDeviceContainerControl = _instance;
        }
        return gDInterDeviceContainerControl;
    }

    @Override // com.good.gt.icc.AppControl
    public void becomeForeground(FrontParams frontParams) {
    }

    @Override // com.good.gt.icc.InterDeviceConnectedListener
    public void containerCameIntoRange(String str) {
    }

    @Override // com.good.gt.icc.InterDeviceConnectedListener
    public void containerOutOfRange(String str) {
    }

    @Override // com.good.gt.icc.InterDeviceActivationControlListener
    public void displayAuthenticationUI(String str) {
        GDLog.DBGPRINTF(16, "GDInterDeviceContainerControl.displayAuthenticationUI");
        this.mActivationUIControl.displayUserValidationCode(str, UniversalActivityController.getInstance().getCurrentActivityContext());
    }

    @Override // com.good.gd.support.impl.GDConnectedApplicationControlListener
    public GDConnectedApplicationState getConnectedApplicationState(String str) {
        if (GDSubContainerManager.isSubContainerActivated(str)) {
            return GDConnectedApplicationState.StateActivated;
        }
        return GDConnectedApplicationState.StateNotActivated;
    }

    @Override // com.good.gd.support.impl.GDConnectedApplicationControlListener
    public GDConnectedApplicationType getConnectedApplicationType(String str) {
        if (GDSubContainerManager.isSubContainerActivated(str)) {
            return GDConnectedApplicationType.GD_WEARABLE;
        }
        return GDConnectedApplicationType.GD_UNKNOWN;
    }

    @Override // com.good.gd.support.impl.GDConnectedApplicationControlListener
    public ArrayList<GDConnectedApplication> getConnectedApplications() {
        return GDSubContainerManager.getConnectedApplications();
    }

    @Override // com.good.gt.icc.InterDeviceContainerControlListener
    public String getSubContainerPolicy(String str) {
        String subContainerPolicy = GDSubContainerManager.getSubContainerPolicy(str);
        GDLog.DBGPRINTF(16, "GDInterDeviceContainerControl.getSubContainerPolicy = " + subContainerPolicy);
        return subContainerPolicy;
    }

    @Override // com.good.gd.support.impl.GDConnectedApplicationControlListener
    public boolean isConnectedApplicationActivationAllowed() {
        return isSubContainerAllowed();
    }

    @Override // com.good.gt.icc.InterDeviceActivationControlListener
    public boolean isContainerLocked() {
        return !GDActivitySupport.isAuthorised();
    }

    @Override // com.good.gt.icc.InterDeviceContainerControlListener
    public boolean isSubContainerAllowed() {
        boolean isSubcontainerAllowed = GDSubContainerManager.isSubcontainerAllowed();
        GDLog.DBGPRINTF(16, "GDInterDeviceContainerControl.isSubContainerAllowed =" + isSubcontainerAllowed);
        return isSubcontainerAllowed;
    }

    @Override // com.good.gt.icc.InterDeviceActivationControlListener
    public void onActivationResult(boolean z, String str, int i, GTContainerInfo gTContainerInfo) {
        GDLog.DBGPRINTF(16, "GDInterDeviceContainerControl.onActivationResult success =" + z);
        if (z) {
            GDSubContainerManager.onActivationCompletedSuccess(gTContainerInfo, str);
        }
        this.mActivationUIControl.activationCompleted(z, i);
    }

    public void onContainerAuthorized() {
        InterDeviceContainerControl interDeviceContainerControl = this._interDeviceContainerControlClient;
        if (interDeviceContainerControl != null) {
            interDeviceContainerControl.onContainerAuthorized();
        }
    }

    @Override // com.good.gt.icc.InterDeviceContainerControlListener
    public void onInterDeviceError(int i) {
        new hbfhc(i).execute(new Void[0]);
    }

    @Override // com.good.gt.icc.InterDeviceContainerControlListener
    public void onSubContainerPolicyAcknowledgement(String str, String str2) {
        GDLog.DBGPRINTF(16, "GDInterDeviceContainerControl.onSubContainerPolicyAcknowledgement Policy = " + str + "RemoteDeviceID = " + str2);
        GDSubContainerManager.subContainerPolicyAcknowledgement(str, str2);
    }

    @Override // com.good.gt.icc.InterDeviceActivationControlListener
    public void persistAuthorisationSequenceID(String str, int i) {
        GDSubContainerManager.persistAuthorisationSequenceID(str, i);
    }

    public void promptUserStartSubContainerActivation(String str, String str2) {
        this._interDeviceContainerControlClient.promptUserStartSubContainerActivation(str, str2);
    }

    @Override // com.good.gd.support.impl.GDConnectedApplicationControlListener
    public void removeConnectedApplication(String str) {
        GDSubContainerManager.removeConnectedApplication(str);
    }

    @Override // com.good.gt.icc.InterDeviceActivationControlListener
    public String retrieveActivationLinkKey(String str) {
        GDLog.DBGPRINTF(16, "GDInterDeviceContainerControl.retrieveActivationLinkKey");
        return GDSubContainerManager.getLinkKey(str);
    }

    @Override // com.good.gt.icc.InterDeviceActivationControlListener
    public int retrieveAuthorisationSequenceID(String str) {
        return GDSubContainerManager.retrieveAuthorisationSequenceID(str);
    }

    @Override // com.good.gt.icc.InterDeviceActivationControlListener
    public String retrieveGDClientLibraryVersion() {
        GDLog.DBGPRINTF(16, "GDInterDeviceContainerControl.retrieveGDClientLibraryVersion");
        return GDDeviceInfo.getInstance().getClientVersion();
    }

    public void sendUpdatePolicy(String str) {
        InterDeviceContainerControl interDeviceContainerControl = this._interDeviceContainerControlClient;
        if (interDeviceContainerControl != null) {
            interDeviceContainerControl.sendUpdatePolicy(str);
        }
    }

    @Override // com.good.gd.support.impl.GDConnectedApplicationControlListener
    public boolean startConnectedApplicationActivation(String str, Context context) {
        this.mActivationUIControl.startActivationUI(str, context);
        return true;
    }

    public void userValidationCodeResult(String str, InterDeviceActivationControlInterface.ValidationCodeResponse validationCodeResponse) {
        this._interDeviceContainerControlClient.userActivationCodeResponse(str, validationCodeResponse);
    }

    public void sendUpdatePolicy(String str, String str2) {
        InterDeviceContainerControl interDeviceContainerControl = this._interDeviceContainerControlClient;
        if (interDeviceContainerControl != null) {
            interDeviceContainerControl.sendUpdatePolicy(str, str2);
        }
    }
}
