package com.good.gd.ndkproxy.ui.data.base;

import android.content.Context;
import com.good.gd.messages.SetPasswordMsg;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.native2javabridges.utils.UIDataResult;
import com.good.gd.ndkproxy.ui.BBDUILocalizationHelper;
import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.ndkproxy.ui.data.PasswordChangeFacade;
import com.good.gd.ndkproxy.ui.data.PasswordChangeUIEventFactory;
import com.good.gd.ndkproxy.ui.event.BBDUIEventBuilder;
import com.good.gd.ndkproxy.ui.event.BBDUIEventManager;
import com.good.gd.ndkproxy.ui.event.BBDUIMessageType;
import com.good.gd.ndkproxy.ui.event.UIEventType;
import com.good.gd.ui.GDSetPasswordView;
import com.good.gd.ui.ViewCustomizer;
import com.good.gd.ui.ViewInteractor;
import com.good.gd.ui.base_ui.GDView;
import com.good.gd.utils.GDLocalizer;
import java.util.List;

/* loaded from: classes.dex */
public abstract class SetChangePasswordBaseUI extends BaseUI {
    private String mLocalizablePasswordEyeKey = BBDUILocalizationHelper.getLocalizablePasswordEyeKey(this);
    private final PasswordChangeFacade passwordChangeFacade;
    private final PasswordChangeUIEventFactory passwordChangeUIEventFactory;

    public SetChangePasswordBaseUI(BBUIType bBUIType, long j, PasswordChangeUIEventFactory passwordChangeUIEventFactory, PasswordChangeFacade passwordChangeFacade) {
        super(bBUIType, j);
        this.passwordChangeUIEventFactory = passwordChangeUIEventFactory;
        this.passwordChangeFacade = passwordChangeFacade;
    }

    private void setNewPassword(SetPasswordMsg setPasswordMsg) {
        UIDataResult newPassword = this.passwordChangeUIEventFactory.setNewPassword(getCoreHandle(), setPasswordMsg);
        if (!newPassword.isSuccess()) {
            String localizedString = GDLocalizer.getLocalizedString(newPassword.mErrorMessage);
            if (localizedString.contains("[time]")) {
                localizedString = localizedString.replace("[time]", this.passwordChangeFacade.getLocaleFormatDate());
            }
            BBDUIEventManager.sendUpdateEvent(new BBDUIEventBuilder(UIEventType.UI_PASSWORD_SET_RESULT).successful(false).addTitle(BBDUILocalizationHelper.getLocalizedError()).addText(localizedString).build(), this);
            return;
        }
        BBDUIEventManager.sendUpdateEvent(new BBDUIEventBuilder(UIEventType.UI_PASSWORD_SET_RESULT).successful(true).build(), this);
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BaseUI
    /* renamed from: createView */
    public GDView mo294createView(Context context, ViewInteractor viewInteractor, ViewCustomizer viewCustomizer) {
        return new GDSetPasswordView(context, viewInteractor, this, viewCustomizer);
    }

    public String getLocalizedPasswordEyeText() {
        return GDLocalizer.getLocalizedString(this.mLocalizablePasswordEyeKey);
    }

    public final List<String> getPasswordGuide() {
        return this.passwordChangeFacade.setPasswordGuideWithPolicyInformation();
    }

    public boolean isAuthTypeNoPass() {
        return this.passwordChangeFacade.isAuthTypeNoPass();
    }

    public abstract boolean isChangePassword();

    public abstract boolean isLaunchedByApp();

    @Override // com.good.gd.ndkproxy.ui.data.base.BBDUIObject
    public void onMessage(BBDUIMessageType bBDUIMessageType, Object obj) {
        int ordinal = bBDUIMessageType.ordinal();
        if (ordinal != 1) {
            if (ordinal == 2) {
                cancel();
            }
            super.onMessage(bBDUIMessageType, obj);
        } else if (!(obj instanceof SetPasswordMsg)) {
            GDLog.DBGPRINTF(13, "SetChangePasswordBaseUI.setNewPassword: message from UI has invalid payload, ignoring.\n");
        } else {
            setNewPassword((SetPasswordMsg) obj);
        }
    }
}
