package com.good.gd.machines.activation;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import com.good.gd.ApplicationState;
import com.good.gd.GDAndroid;
import com.good.gd.GDAppEvent;
import com.good.gd.GDAppEventType;
import com.good.gd.GDAppResultCode;
import com.good.gd.GDLocalBroadcastManager;
import com.good.gd.GDStateAction;
import com.good.gd.client.GDClient;
import com.good.gd.client.GDDefaultAppEventListener;
import com.good.gd.error.GDInitializationError;
import com.good.gd.machines.activation.ActivationStates;
import com.good.gd.ndkproxy.GDLangInterface;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.GDStartupController;
import com.good.gd.ndkproxy.PMState;
import com.good.gd.ndkproxy.enterprise.BBDActivationManager;
import com.good.gd.ndkproxy.enterprise.EntProvErrors;
import com.good.gd.ndkproxy.enterprise.GDEProvisionUtil;
import com.good.gd.ndkproxy.net.NetworkStateMonitor;
import com.good.gd.ndkproxy.ui.CoreUI;
import com.good.gd.net.GDConnectivityManager;
import com.good.gd.ui_control.GDMonitorFragmentImpl;
import com.good.gd.utils.GDLocalizer;
import com.good.gd.utils.UserAuthUtils;
import com.good.gt.context.GTBaseContext;
import java.util.List;

/* loaded from: classes.dex */
public class GDActivationManager implements ActivationStateUpdate {
    private static GDActivationManager _instance;
    private BBDActivationManager mActivationManager;
    private ActivationStates.StateOutput mCurrentActivationState;
    private PMState mCurrentPMState;
    private Internal_Activation_State mState;
    private BroadcastReceiver mConnectionStatusReceiver = new hbfhc();
    private ActivationStates mActivationStates = new ActivationStates();
    private boolean mShouldShowUI = true;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public enum Internal_Activation_State {
        StateNOTSetup,
        StateNOTActivated,
        StateProgrammaticConfigurationProvided,
        StateActivationInProgress,
        StateActivationInProgressForegroundRequired,
        StateActivationFailed,
        StateActivated
    }

    /* loaded from: classes.dex */
    class hbfhc extends BroadcastReceiver {
        hbfhc() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            GDLog.DBGPRINTF(14, "GDActivationManager connection status received\n");
            GDAndroid.getInstance().unregisterReceiver(this);
            GDActivationManager.this.doActivation();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class yfdke implements Runnable {
        final /* synthetic */ GDAppEvent dbjc;

        yfdke(GDActivationManager gDActivationManager, GDAppEvent gDAppEvent) {
            this.dbjc = gDAppEvent;
        }

        @Override // java.lang.Runnable
        public void run() {
            GDDefaultAppEventListener.getInstance().onGDEvent(this.dbjc);
        }
    }

    private GDActivationManager() {
        setState(Internal_Activation_State.StateNOTSetup);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doActivation() {
        setState(Internal_Activation_State.StateActivationInProgress);
        GDClient.getInstance().authorize(null, false);
        PMState pMState = PMState.NOC_EPM_STATUS_WAIT_FOR_NOC_CONNECTION;
        activationStateUpdate(1);
    }

    public static GDActivationManager getInstance() {
        if (_instance == null) {
            _instance = new GDActivationManager();
        }
        return _instance;
    }

    private BBDActivationManager getNativeActivationManager() {
        if (this.mActivationManager == null) {
            this.mActivationManager = new BBDActivationManager(this);
        }
        return this.mActivationManager;
    }

    private boolean hasDataConnectivity() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) GTBaseContext.getInstance().getApplicationContext().getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private boolean isUserInputValid(String str, String str2, String str3) {
        String str4;
        boolean z;
        GDAppResultCode gDAppResultCode = GDAppResultCode.GDErrorProgrammaticActivationUnknown;
        if (str == null || str.length() == 0) {
            gDAppResultCode = GDAppResultCode.GDErrorProgrammaticActivationCredentialsFailed;
            str4 = "Invalid ProvisionId provided. ProvisionId should not be null or empty string";
            z = true;
        } else {
            z = false;
            str4 = "";
        }
        if (str3 == null || str3.length() == 0) {
            if (str2 != null && str2.length() != 0) {
                if (!GDEProvisionUtil.pinChecksumOk(str2)) {
                    gDAppResultCode = GDAppResultCode.GDErrorProgrammaticActivationCredentialsFailed;
                    str4 = "Invalid AccessKey provided.";
                    z = true;
                }
            } else {
                gDAppResultCode = GDAppResultCode.GDErrorProgrammaticActivationCredentialsFailed;
                str4 = "AccessKey and Password are not provided. At least one of AccessKey or Password should not be null or empty string";
                z = true;
            }
        }
        if (z) {
            GDAppEvent gDAppEvent = new GDAppEvent(str4, gDAppResultCode, GDAppEventType.GDAppEventNotAuthorized);
            Intent intent = new Intent(GDStateAction.GD_STATE_ACTIVATION_ACTION);
            intent.putExtra(ApplicationState.BBDActivationDescriptionKey, str4);
            intent.putExtra(ApplicationState.BBDActivationErrorKey, gDAppResultCode);
            intent.putExtra(ApplicationState.BBDActivationStateKey, getActivationState());
            GDLocalBroadcastManager.getInstance().sendBroadcast(intent);
            new Handler().postDelayed(new yfdke(this, gDAppEvent), 0L);
        }
        return !z;
    }

    private String printState(Internal_Activation_State internal_Activation_State) {
        int ordinal = internal_Activation_State.ordinal();
        return ordinal != 0 ? ordinal != 1 ? ordinal != 3 ? ordinal != 5 ? "Error Unknown State" : "StateActivationFailed, ProgrammaticActivation Failed" : "StateActivationInProgress, ProgrammaticActivation in-progress" : "StateInitialized, ProgrammaticActivation parameters provided" : "StateNOTInUse, ProgrammaticActivation not in use";
    }

    private void sendActivationStateBroadcast() {
        Intent intent = new Intent(GDStateAction.GD_STATE_ACTIVATION_ACTION);
        intent.putExtra(ApplicationState.BBDActivationStateKey, getActivationState());
        ActivationStates.StateOutput stateOutput = this.mCurrentActivationState;
        if (stateOutput != null) {
            intent.putExtra(ApplicationState.BBDActivationDescriptionKey, stateOutput.mLocalizedMessage);
            intent.putExtra(ApplicationState.BBDActivationProcessingIndexKey, this.mCurrentActivationState.mStateIndex);
        }
        GDLocalBroadcastManager.getInstance().sendBroadcast(intent);
    }

    private void setState(Internal_Activation_State internal_Activation_State) {
        GDLog.DBGPRINTF(16, "GDActivationManager setState" + printState(internal_Activation_State) + "\n");
        this.mState = internal_Activation_State;
    }

    @Override // com.good.gd.machines.activation.ActivationStateUpdate
    public synchronized void activationStateUpdate(int i) {
        PMState convertFromIntegerValue = PMState.convertFromIntegerValue(i);
        this.mCurrentActivationState = this.mActivationStates.getStateLocalizedMessage(convertFromIntegerValue);
        boolean z = false;
        if (PMState.EPM_STATUS_POLICY_SET_COMPLETE == PMState.convertFromIntegerValue(i)) {
            z = true;
        }
        if (convertFromIntegerValue != this.mCurrentPMState && this.mCurrentActivationState != null && !z) {
            this.mCurrentPMState = convertFromIntegerValue;
            Intent intent = new Intent(GDStateAction.GD_STATE_ACTIVATION_ACTION);
            intent.putExtra(ApplicationState.BBDActivationDescriptionKey, this.mCurrentActivationState.mLocalizedMessage);
            intent.putExtra(ApplicationState.BBDActivationProcessingIndexKey, this.mCurrentActivationState.mStateIndex);
            intent.putExtra(ApplicationState.BBDActivationStateKey, getActivationState());
            GDLocalBroadcastManager.getInstance().sendBroadcast(intent);
        }
    }

    public synchronized boolean configureProgrammaticActivation(Bundle bundle) {
        String str;
        GDLog.DBGPRINTF(16, "GDActivationManager configureProgrammaticActivation\n");
        if (!UserAuthUtils.isActivated()) {
            ApplicationState.ActivationParameter activationParameter = ApplicationState.ActivationParameter.UserIdentifier;
            String string = bundle.getString("UserIdentifier", "");
            ApplicationState.ActivationParameter activationParameter2 = ApplicationState.ActivationParameter.AccessKey;
            String string2 = bundle.getString("AccessKey", "");
            ApplicationState.ActivationParameter activationParameter3 = ApplicationState.ActivationParameter.Password;
            String string3 = bundle.getString("Password", "");
            if (!isUserInputValid(string, string2, string3)) {
                GDStartupController.getInstance().resetState();
                return false;
            }
            if (string2.length() != 0 || string3.length() == 0) {
                str = string2;
            } else {
                GDLog.DBGPRINTF(14, "GDActivationManager Activation password is passed \n");
                str = string3;
            }
            ApplicationState.ActivationParameter activationParameter4 = ApplicationState.ActivationParameter.NOCAddress;
            String string4 = bundle.getString("NOCAddress", "");
            ApplicationState.ActivationParameter activationParameter5 = ApplicationState.ActivationParameter.EnrollmentAddress;
            String string5 = bundle.getString("EnrollmentAddress", "");
            ApplicationState.ActivationParameter activationParameter6 = ApplicationState.ActivationParameter.ShowUserInterface;
            this.mShouldShowUI = bundle.getBoolean("ShowUserInterface", true);
            setState(Internal_Activation_State.StateProgrammaticConfigurationProvided);
            return getNativeActivationManager().setActivationInfo(string, str, string4, string5, this.mShouldShowUI);
        }
        throw new GDInitializationError("Attempted ProgrammaticActivation when already Activated");
    }

    public synchronized boolean configureProgrammaticActivationInBackground(Bundle bundle) {
        ApplicationState.ActivationParameter activationParameter = ApplicationState.ActivationParameter.ShowUserInterface;
        if (!bundle.getBoolean("ShowUserInterface", true)) {
        } else {
            throw new GDInitializationError("Attempted ProgrammaticActivation in background with ShowUserInterface parameter set to TRUE, set this parameter to FALSE");
        }
        return configureProgrammaticActivation(bundle);
    }

    public List<String> getActivationProcessingDescriptions() {
        return this.mActivationStates.getStateLocalizedMessages();
    }

    public synchronized ApplicationState.ActivationState getActivationState() {
        int ordinal = this.mState.ordinal();
        if (ordinal == 0) {
            if (UserAuthUtils.isActivated()) {
                setState(Internal_Activation_State.StateActivated);
                return ApplicationState.ActivationState.EActivated;
            }
            setState(Internal_Activation_State.StateNOTActivated);
            return ApplicationState.ActivationState.ENotActivated;
        } else if (ordinal == 6) {
            return ApplicationState.ActivationState.EActivated;
        } else if (ordinal == 3) {
            return ApplicationState.ActivationState.EActivationInProgress;
        } else if (ordinal != 4) {
            return ApplicationState.ActivationState.ENotActivated;
        } else {
            return ApplicationState.ActivationState.EActivatingForegroundRequired;
        }
    }

    public synchronized void informAuthorized() {
        switch (this.mState.ordinal()) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                setState(Internal_Activation_State.StateActivated);
                this.mCurrentActivationState = null;
                sendActivationStateBroadcast();
                break;
        }
    }

    public void internalUIWatingToBeDisplayed() {
        if (this.mState == Internal_Activation_State.StateActivationInProgress && CoreUI.isMandatoryActivationCompletionUIPending()) {
            setState(Internal_Activation_State.StateActivationInProgressForegroundRequired);
            sendActivationStateBroadcast();
        }
    }

    public synchronized boolean isProgrammaticActivating() {
        boolean z;
        Internal_Activation_State internal_Activation_State = this.mState;
        if (internal_Activation_State != Internal_Activation_State.StateActivationInProgress) {
            if (internal_Activation_State != Internal_Activation_State.StateProgrammaticConfigurationProvided) {
                z = false;
            }
        }
        z = true;
        return z;
    }

    @Override // com.good.gd.machines.activation.ActivationStateUpdate
    public synchronized void onActivationFailed(int i, int i2) {
        String str;
        GDAppResultCode gDAppResultCode;
        setState(Internal_Activation_State.StateActivationFailed);
        PMState convertFromIntegerValue = PMState.convertFromIntegerValue(i2);
        switch (i) {
            case 14:
            case EntProvErrors.errDbNotFound /* 262146 */:
                str = "Provisioning Denied";
                gDAppResultCode = GDAppResultCode.GDErrorProgrammaticActivationCredentialsFailed;
                break;
            case 24:
                str = GDLangInterface.lookup("29IXhLxu2gore/Uwnrzhi21mKW7Eu9Y77Q8Lwloa5b8=");
                if (hasDataConnectivity()) {
                    gDAppResultCode = GDAppResultCode.GDErrorProgrammaticActivationAttestationFailed;
                    break;
                } else {
                    gDAppResultCode = GDAppResultCode.GDErrorProgrammaticActivationNoNetwork;
                    break;
                }
            case 26:
                str = "OS version not compliant";
                if (hasDataConnectivity()) {
                    gDAppResultCode = GDAppResultCode.GDErrorProgrammaticActivationAttestationFailed;
                    break;
                } else {
                    gDAppResultCode = GDAppResultCode.GDErrorProgrammaticActivationNoNetwork;
                    break;
                }
            case EntProvErrors.errNocActivationFailed /* 16711681 */:
                str = "NOC Activation failed";
                if (hasDataConnectivity()) {
                    gDAppResultCode = GDAppResultCode.GDErrorProgrammaticActivationServerCommsFailed;
                    break;
                } else {
                    gDAppResultCode = GDAppResultCode.GDErrorProgrammaticActivationNoNetwork;
                    break;
                }
            case EntProvErrors.errPolicyExchangeFailed /* 16711691 */:
                str = "Policy Exchange failed";
                if (hasDataConnectivity()) {
                    gDAppResultCode = GDAppResultCode.GDErrorProgrammaticActivationServerCommsFailed;
                    break;
                } else {
                    gDAppResultCode = GDAppResultCode.GDErrorProgrammaticActivationNoNetwork;
                    break;
                }
            default:
                int ordinal = convertFromIntegerValue.ordinal();
                if (ordinal != 11) {
                    switch (ordinal) {
                        case 1:
                        case 2:
                        case 3:
                            str = "NOC Provisioning Failed";
                            if (hasDataConnectivity()) {
                                gDAppResultCode = GDAppResultCode.GDErrorProgrammaticActivationServerCommsFailed;
                                break;
                            } else {
                                gDAppResultCode = GDAppResultCode.GDErrorProgrammaticActivationNoNetwork;
                                break;
                            }
                        case 4:
                            str = "Starting Push Channel Failed";
                            if (hasDataConnectivity()) {
                                gDAppResultCode = GDAppResultCode.GDErrorProgrammaticActivationServerCommsFailed;
                                break;
                            } else {
                                gDAppResultCode = GDAppResultCode.GDErrorProgrammaticActivationNoNetwork;
                                break;
                            }
                        case 5:
                            str = "Negotiating Request Failed";
                            gDAppResultCode = GDAppResultCode.GDErrorProgrammaticActivationCredentialsFailed;
                            break;
                        default:
                            str = "Provisioning with enterprise failed";
                            gDAppResultCode = GDAppResultCode.GDErrorProgrammaticActivationUnknown;
                            break;
                    }
                } else {
                    str = "Data Request Failed";
                    if (hasDataConnectivity()) {
                        gDAppResultCode = GDAppResultCode.GDErrorProgrammaticActivationServerCommsFailed;
                        break;
                    } else {
                        gDAppResultCode = GDAppResultCode.GDErrorProgrammaticActivationNoNetwork;
                        break;
                    }
                }
        }
        String localizedString = GDLocalizer.getLocalizedString(str);
        GDClient.getInstance().sendGDAppEventToApp(new GDAppEvent(localizedString, gDAppResultCode, GDAppEventType.GDAppEventNotAuthorized));
        Intent intent = new Intent(GDStateAction.GD_STATE_ACTIVATION_ACTION);
        intent.putExtra(ApplicationState.BBDActivationDescriptionKey, localizedString);
        intent.putExtra(ApplicationState.BBDActivationStateKey, getActivationState());
        intent.putExtra(ApplicationState.BBDActivationErrorKey, gDAppResultCode);
        GDLocalBroadcastManager.getInstance().sendBroadcast(intent);
        GDStartupController.getInstance().resetState();
    }

    public synchronized void rollbackActivity(Activity activity) {
        if (this.mState != Internal_Activation_State.StateActivationFailed) {
            return;
        }
        GDLog.DBGPRINTF(16, "GDActivationManager rollbackActivity\n");
        FragmentManager fragmentManager = activity.getFragmentManager();
        Fragment findFragmentByTag = fragmentManager.findFragmentByTag(GDMonitorFragmentImpl.GDMONITORFRAGMENT);
        if (findFragmentByTag != null) {
            FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
            beginTransaction.remove(findFragmentByTag);
            beginTransaction.commit();
            fragmentManager.executePendingTransactions();
        }
    }

    public synchronized boolean shouldShowDynamicsStartupUI() {
        int ordinal = this.mState.ordinal();
        if (ordinal == 0 || ordinal == 6) {
            this.mShouldShowUI = getNativeActivationManager().shouldShowStartupUI();
        }
        return this.mShouldShowUI;
    }

    public synchronized void startProgrammaticActivation() {
        GDLog.DBGPRINTF(16, "GDActivationManager startProgrammaticActivation\n");
        if (this.mState != Internal_Activation_State.StateProgrammaticConfigurationProvided) {
            GDLog.DBGPRINTF(12, "GDActivationManager startProgrammaticActivation\n");
            return;
        }
        if (!NetworkStateMonitor.getInstance().isWifiNetworkStateReady()) {
            GDLog.DBGPRINTF(14, "GDActivationManager waiting for wifi state ready\n");
            GDAndroid.getInstance().registerReceiver(this.mConnectionStatusReceiver, new IntentFilter(GDConnectivityManager.GD_CONNECTIVITY_ACTION));
            NetworkStateMonitor.getInstance().refreshNetworkStateDelayed(0L);
        } else {
            doActivation();
        }
    }
}
