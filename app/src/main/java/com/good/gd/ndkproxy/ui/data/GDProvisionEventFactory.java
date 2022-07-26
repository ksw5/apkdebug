package com.good.gd.ndkproxy.ui.data;

import com.good.gd.machines.activation.ActivationStates;
import com.good.gd.ndkproxy.PMState;
import com.good.gd.ndkproxy.ui.event.BBDUIProvisionUpdateEvent;

/* loaded from: classes.dex */
public class GDProvisionEventFactory {
    public static BBDUIProvisionUpdateEvent makeProvisionUpdateInstruction(int i) {
        String str;
        ActivationStates.StateOutput stateLocalizedMessageKey = new ActivationStates().getStateLocalizedMessageKey(PMState.convertFromIntegerValue(i));
        if (stateLocalizedMessageKey == null) {
            str = null;
        } else {
            str = stateLocalizedMessageKey.mLocalizedMessage;
        }
        if (str != null) {
            PMState convertFromIntegerValue = PMState.convertFromIntegerValue(i);
            PMState pMState = PMState.EPM_STATUS_POLICY_SET_COMPLETE;
            return new BBDUIProvisionUpdateEvent(convertFromIntegerValue.ordinal(), (convertFromIntegerValue.ordinal() * 100) / 18, str);
        }
        return null;
    }
}
