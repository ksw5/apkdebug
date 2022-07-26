package com.good.gd.ndkproxy.ui.data;

import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.ndkproxy.ui.data.base.ProgressBaseUI;

/* loaded from: classes.dex */
public class MigrationProgressUI extends ProgressBaseUI {
    public MigrationProgressUI(long j) {
        super(BBUIType.UI_MIGRATION_PROGRESS, j);
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.ProgressBaseUI
    public boolean isMigrationFlow() {
        return true;
    }
}
