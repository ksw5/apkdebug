package com.good.gd.ndkproxy.ui.data;

import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.ndkproxy.ui.data.base.ProgressBaseUI;

/* loaded from: classes.dex */
public class ActivationProgressUI extends ProgressBaseUI {
    public ActivationProgressUI(long j) {
        super(BBUIType.UI_PROVISION_PROGRESS, j);
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.ProgressBaseUI
    public boolean isMigrationFlow() {
        return false;
    }
}
