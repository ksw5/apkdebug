package com.good.gd.ndkproxy.ui.data;

import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.ndkproxy.ui.data.base.SetChangePasswordBaseUI;

/* loaded from: classes.dex */
public class ChangePasswordOptionalUI extends SetChangePasswordBaseUI {
    public ChangePasswordOptionalUI(long j, PasswordChangeUIEventFactory passwordChangeUIEventFactory, PasswordChangeFacade passwordChangeFacade) {
        super(BBUIType.UI_CHANGE_PASSWORD_OPTIONAL, j, passwordChangeUIEventFactory, passwordChangeFacade);
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.SetChangePasswordBaseUI
    public boolean isChangePassword() {
        return true;
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.SetChangePasswordBaseUI
    public boolean isLaunchedByApp() {
        return true;
    }
}
