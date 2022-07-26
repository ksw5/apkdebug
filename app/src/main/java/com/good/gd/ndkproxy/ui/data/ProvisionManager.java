package com.good.gd.ndkproxy.ui.data;

import com.good.gd.messages.ProvisionMsg;
import com.good.gd.ndkproxy.native2javabridges.utils.UIDataResult;

/* loaded from: classes.dex */
public interface ProvisionManager {
    UIDataResult startProvision(long j, String str, String str2);

    UIDataResult startProvisionOverBcp(long j, String str, String str2, String str3);

    void startProvisionUsingAuthCode(String str, String str2);

    void startProvisioningProcedure(ProvisionMsg provisionMsg);
}
