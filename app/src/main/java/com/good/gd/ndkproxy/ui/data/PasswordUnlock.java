package com.good.gd.ndkproxy.ui.data;

import com.good.gd.messages.LoginMsg;
import com.good.gd.ndkproxy.native2javabridges.utils.UIDataResult;

/* loaded from: classes.dex */
public interface PasswordUnlock {
    UIDataResult handleClientUnlockRequest(long j, LoginMsg loginMsg);

    void submitMatchingBiometry(long j);
}
