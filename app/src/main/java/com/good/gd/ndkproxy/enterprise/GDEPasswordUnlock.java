package com.good.gd.ndkproxy.enterprise;

import com.good.gd.messages.LoginMsg;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.native2javabridges.utils.UIDataResult;
import com.good.gd.ndkproxy.ui.BBDUIDataStore;
import com.good.gd.ndkproxy.ui.data.PasswordUnlock;
import com.good.gd.ndkproxy.ui.event.BBDUIEventBuilder;
import com.good.gd.ndkproxy.ui.event.BBDUIEventManager;
import com.good.gd.ndkproxy.ui.event.UIEventType;
import com.good.gd.utils.GDLocalizer;
import com.good.gd.utils.SensitiveDataUtils;

/* loaded from: classes.dex */
public final class GDEPasswordUnlock implements PasswordUnlock {
    private static GDEPasswordUnlock _instance;

    private GDEPasswordUnlock() {
        try {
            GDLog.DBGPRINTF(16, "GDEPasswordUnlock: Attempting to initialize C++ peer\n");
            synchronized (this) {
                ndkInit();
            }
        } catch (Exception e) {
            GDLog.DBGPRINTF(12, "GDEPasswordUnlock: Cannot initialize C++ peer", e);
        }
    }

    public static synchronized GDEPasswordUnlock getInstance() {
        GDEPasswordUnlock gDEPasswordUnlock;
        synchronized (GDEPasswordUnlock.class) {
            if (_instance == null) {
                _instance = new GDEPasswordUnlock();
            }
            gDEPasswordUnlock = _instance;
        }
        return gDEPasswordUnlock;
    }

    public static native void lock();

    private native void ndkInit();

    public static native void unlockComplete(long j);

    public static native boolean validatePassword(String str);

    public native int getIncorrectPwdAttempts();

    public native int getMaxPwdRetryCount();

    @Override // com.good.gd.ndkproxy.ui.data.PasswordUnlock
    public UIDataResult handleClientUnlockRequest(long j, LoginMsg loginMsg) {
        UIDataResult submitPassword = submitPassword(j, loginMsg.password);
        SensitiveDataUtils.shredSensitiveData(loginMsg.password);
        SensitiveDataUtils.callGarbageCollection();
        if (submitPassword != null && !submitPassword.isSuccess()) {
            BBDUIEventBuilder bBDUIEventBuilder = new BBDUIEventBuilder(UIEventType.UI_UNLOCK_RESULT);
            BBDUIEventManager.sendUpdateEvent(bBDUIEventBuilder.successful(false).addTitle(GDLocalizer.getLocalizedString("Incorrect Password")).addText(GDLocalizer.getLocalizedString(submitPassword.getErrorMessage())).build(), BBDUIDataStore.getInstance().getUIData(j));
        }
        return submitPassword;
    }

    @Override // com.good.gd.ndkproxy.ui.data.PasswordUnlock
    public native void submitMatchingBiometry(long j);

    public native UIDataResult submitPassword(long j, char[] cArr);
}
