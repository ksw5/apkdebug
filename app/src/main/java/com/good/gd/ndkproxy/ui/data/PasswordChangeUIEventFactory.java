package com.good.gd.ndkproxy.ui.data;

import com.good.gd.messages.SetPasswordMsg;
import com.good.gd.ndkproxy.native2javabridges.utils.UIDataResult;

/* loaded from: classes.dex */
public interface PasswordChangeUIEventFactory {
    UIDataResult setNewPassword(long j, SetPasswordMsg setPasswordMsg);
}
