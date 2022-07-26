package com.good.gd.ndkproxy.ui;

import com.blackberry.attestation.playServices.AttestationPlayServicesCheck;
import com.good.gd.context.GDContext;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.ui.data.dialog.AdditionalErrorMessageCreator;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
class hbfhc implements AdditionalErrorMessageCreator {
    @Override // com.good.gd.ndkproxy.ui.data.dialog.AdditionalErrorMessageCreator
    public List<String> additionalErrorMessageKeys(int i) {
        ArrayList arrayList = new ArrayList();
        if (24 == i) {
            boolean isPlayServicesAvailable = AttestationPlayServicesCheck.getInstance().isPlayServicesAvailable(GDContext.getInstance().getApplicationContext());
            if (!isPlayServicesAvailable) {
                arrayList.add("WF Application update required details");
            }
            GDLog.DBGPRINTF(12, "snvf psa: " + isPlayServicesAvailable + "\n");
        }
        return arrayList;
    }
}
