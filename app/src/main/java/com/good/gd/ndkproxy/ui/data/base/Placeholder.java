package com.good.gd.ndkproxy.ui.data.base;

import com.good.gd.ndkproxy.ui.BBUIType;

/* loaded from: classes.dex */
public class Placeholder extends BBDUIObject {
    public Placeholder(BBUIType bBUIType, long j) {
        super(bBUIType, j);
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BBDUIObject
    public boolean isModal() {
        return false;
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BBDUIObject
    public final boolean isPlaceholder() {
        return true;
    }
}
