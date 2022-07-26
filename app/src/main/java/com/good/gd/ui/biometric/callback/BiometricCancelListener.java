package com.good.gd.ui.biometric.callback;

import android.content.DialogInterface;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.auth.biometric.prompt.BiometricClickListener;
import com.good.gd.ndkproxy.ui.BBDUIDataStore;
import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.ndkproxy.ui.data.base.BBDUIObject;
import com.good.gd.ndkproxy.ui.event.BBDUIEventManager;
import com.good.gd.ndkproxy.ui.event.BBDUIMessageType;

/* loaded from: classes.dex */
public class BiometricCancelListener extends BiometricClickListener {
    private BBUIType typeToSend;

    public BiometricCancelListener(BBUIType bBUIType) {
        this.typeToSend = bBUIType;
    }

    @Override // com.good.gd.ndkproxy.auth.biometric.prompt.BiometricClickListener
    public boolean equals(BiometricClickListener biometricClickListener) {
        return (biometricClickListener instanceof BiometricCancelListener) && this.typeToSend == ((BiometricCancelListener) biometricClickListener).typeToSend;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialogInterface, int i) {
        BBDUIObject uIData = BBDUIDataStore.getInstance().getUIData(this.typeToSend);
        if (uIData != null) {
            BBDUIEventManager.sendMessage(uIData, BBDUIMessageType.MSG_UI_CANCEL);
        } else {
            GDLog.DBGPRINTF(13, "Biometrics: cannot send cancel to the dead object\n");
        }
    }
}
