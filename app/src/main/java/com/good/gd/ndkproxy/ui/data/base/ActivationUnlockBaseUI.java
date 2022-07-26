package com.good.gd.ndkproxy.ui.data.base;

import com.good.gd.ndkproxy.ui.BBUIType;

/* loaded from: classes.dex */
public abstract class ActivationUnlockBaseUI extends BaseUI {
    private String _fromAppName;
    private String _fromAppPackageName;

    public ActivationUnlockBaseUI(long j, BBUIType bBUIType, String str, String str2) {
        super(bBUIType, j);
        this._fromAppPackageName = str;
        this._fromAppName = str2;
    }

    public String getAppName() {
        return this._fromAppName;
    }

    public String getAppPackageName() {
        return this._fromAppPackageName;
    }
}
