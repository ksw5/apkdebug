package com.good.gd.utils;

import android.content.Context;
import com.good.gt.deviceid.control.GTSystemUserChecker;

/* loaded from: classes.dex */
public class GDUserChecker implements UserChecker {
    @Override // com.good.gd.utils.UserChecker
    public boolean isUser0(Context context) {
        return GTSystemUserChecker.isUser0(context);
    }
}
