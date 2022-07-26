package com.good.gd.utils;

import com.good.gd.client.GDClient;
import com.good.gd.ndkproxy.enterprise.GDDisclaimerHelper;
import com.good.gd.ndkproxy.ui.data.ClientDefinedStrings;

/* loaded from: classes.dex */
public class GDClientDefinedStrings implements ClientDefinedStrings {
    @Override // com.good.gd.ndkproxy.ui.data.ClientDefinedStrings
    public String getBlockMessage() {
        return GDClient.getInstance().getBlockMessage();
    }

    @Override // com.good.gd.ndkproxy.ui.data.ClientDefinedStrings
    public String getParsedDisclaimerMessage() {
        return GDDisclaimerHelper.getParsedDisclaimerMessage();
    }

    @Override // com.good.gd.ndkproxy.ui.data.ClientDefinedStrings
    public String getWipeMessage() {
        return GDClient.getInstance().getWipeMessage();
    }
}
