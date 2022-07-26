package com.good.gd.machines.activation;

import com.good.gd.ndkproxy.PMState;
import com.good.gd.utils.GDLocalizer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class ActivationStates {
    private ArrayList<hbfhc> mStates;

    /* loaded from: classes.dex */
    public class StateOutput {
        public String mLocalizedMessage;
        int mStateIndex;

        public StateOutput() {
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class hbfhc {
        PMState dbjc;
        String qkduk;

        hbfhc(ActivationStates activationStates, PMState pMState, String str) {
            this.dbjc = pMState;
            this.qkduk = str;
        }
    }

    public ActivationStates() {
        ArrayList<hbfhc> arrayList = new ArrayList<>();
        this.mStates = arrayList;
        arrayList.add(new hbfhc(this, PMState.NOC_EPM_STATUS_WAIT_FOR_NOC_CONNECTION, "NOC Activation"));
        this.mStates.add(new hbfhc(this, PMState.NOC_EPM_STATUS_PROV_ENTERPRISE_NOC, "NOC Provisioning"));
        this.mStates.add(new hbfhc(this, PMState.NOC_EPM_STATUS_PROV_START_PUSH_CHANNEL, "Secure Data"));
        this.mStates.add(new hbfhc(this, PMState.NOC_EPM_STATUS_PROV_NEGOTIATE_REQUEST, "Negotiating Request"));
        this.mStates.add(new hbfhc(this, PMState.EPM_STATUS_PROV_DATA_REQUEST, "Data Request"));
        this.mStates.add(new hbfhc(this, PMState.EPM_STATUS_PROV_COMPLETE, "Downloading Policies"));
        this.mStates.add(new hbfhc(this, PMState.EPM_STATUS_POLICY_DOWNLOAD, "Downloading Policies"));
        this.mStates.add(new hbfhc(this, PMState.EPM_STATUS_POLICY_DOWNLOADED, "Policy Download Complete"));
        this.mStates.add(new hbfhc(this, PMState.EPM_STATUS_POLICY_SET_COMPLETE, "Policy Download Complete"));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public StateOutput getStateLocalizedMessage(PMState pMState) {
        StateOutput stateLocalizedMessageKey = getStateLocalizedMessageKey(pMState);
        if (stateLocalizedMessageKey != null) {
            stateLocalizedMessageKey.mLocalizedMessage = GDLocalizer.getLocalizedString(stateLocalizedMessageKey.mLocalizedMessage);
        }
        return stateLocalizedMessageKey;
    }

    public StateOutput getStateLocalizedMessageKey(PMState pMState) {
        Iterator<hbfhc> it = this.mStates.iterator();
        while (it.hasNext()) {
            hbfhc next = it.next();
            if (next.dbjc == pMState) {
                StateOutput stateOutput = new StateOutput();
                stateOutput.mLocalizedMessage = next.qkduk;
                stateOutput.mStateIndex = this.mStates.indexOf(next);
                return stateOutput;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public List<String> getStateLocalizedMessages() {
        ArrayList arrayList = new ArrayList();
        Iterator<hbfhc> it = this.mStates.iterator();
        while (it.hasNext()) {
            arrayList.add(GDLocalizer.getLocalizedString(it.next().qkduk));
        }
        return arrayList;
    }
}
