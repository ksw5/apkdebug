package com.good.gd.ndkproxy.ui.data;

import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.ndkproxy.ui.data.base.SetChangePasswordBaseUI;

/* loaded from: classes.dex */
public class SetPasswordUI extends SetChangePasswordBaseUI {
    public SetPasswordUI(long j, PasswordChangeUIEventFactory passwordChangeUIEventFactory, PasswordChangeFacade passwordChangeFacade) {
        super(BBUIType.UI_SET_PASSWORD, j, passwordChangeUIEventFactory, passwordChangeFacade);
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.SetChangePasswordBaseUI
    public boolean isChangePassword() {
        return false;
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.SetChangePasswordBaseUI
    public boolean isLaunchedByApp() {
        return false;
    }
}
