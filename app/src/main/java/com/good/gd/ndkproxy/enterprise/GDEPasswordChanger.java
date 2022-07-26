package com.good.gd.ndkproxy.enterprise;

import com.good.gd.messages.SetPasswordMsg;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.native2javabridges.utils.UIDataResult;
import com.good.gd.ndkproxy.ui.CoreUI;
import com.good.gd.ndkproxy.ui.data.PasswordChangeFacade;
import com.good.gd.ndkproxy.ui.data.PasswordChangeUIEventFactory;
import com.good.gd.utils.GDLocalizer;
import com.good.gd.utils.SensitiveDataUtils;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/* loaded from: classes.dex */
public final class GDEPasswordChanger implements PasswordChangeFacade, PasswordChangeUIEventFactory {
    private static GDEPasswordChanger _instance;

    public static synchronized GDEPasswordChanger getInstance() {
        GDEPasswordChanger gDEPasswordChanger;
        synchronized (GDEPasswordChanger.class) {
            if (_instance == null) {
                _instance = new GDEPasswordChanger();
            }
            gDEPasswordChanger = _instance;
        }
        return gDEPasswordChanger;
    }

    public static native int getPasswordType();

    public static native String getPwdSetTime();

    private native UIDataResult updatePasswordNDK(long j, char[] cArr, char[] cArr2, char[] cArr3);

    public native boolean getBannedPwdListEnabled();

    @Override // com.good.gd.ndkproxy.ui.data.PasswordChangeFacade
    public String getLocaleFormatDate() {
        return DateFormat.getDateTimeInstance(3, 2).format(new Date(Long.parseLong(getPwdSetTime()) * 1000));
    }

    public native int getMinPwdLen();

    public native boolean getPwdDisallowNumSeq();

    public native boolean getPwdIsAlphaNum();

    public native boolean getPwdIsMixedCase();

    public native int getPwdMaxRepeats();

    public native boolean getPwdPersonalInfo();

    public native boolean getPwdSplChar();

    @Override // com.good.gd.utils.NoPassChecker
    public boolean isAuthTypeNoPass() {
        return GDAuthManager.getInstance().isAuthTypeNoPass();
    }

    public void requestChangePasswordUIfromApplication() {
        GDLog.DBGPRINTF(16, "GDEPasswordChanger.requestChangePasswordUIfromApplication()\n");
        CoreUI.requestChangePasswordOptionalUI();
    }

    @Override // com.good.gd.ndkproxy.ui.data.PasswordChangeUIEventFactory
    public UIDataResult setNewPassword(long j, SetPasswordMsg setPasswordMsg) {
        GDLog.DBGPRINTF(16, "GDEPasswordChanger.setNewPassword()\n");
        UIDataResult updatePasswordNDK = updatePasswordNDK(j, setPasswordMsg.oldPassword, setPasswordMsg.newPassword, setPasswordMsg.confirmPassword);
        SensitiveDataUtils.shredSensitiveData(setPasswordMsg.oldPassword);
        SensitiveDataUtils.shredSensitiveData(setPasswordMsg.newPassword);
        SensitiveDataUtils.shredSensitiveData(setPasswordMsg.confirmPassword);
        SensitiveDataUtils.callGarbageCollection();
        return updatePasswordNDK;
    }

    @Override // com.good.gd.ndkproxy.ui.data.PasswordChangeFacade
    public ArrayList<String> setPasswordGuideWithPolicyInformation() {
        ArrayList<String> arrayList = new ArrayList<>(8);
        arrayList.add(GDLocalizer.getLocalizedString("Your password must include:\n"));
        arrayList.add(String.format(GDLocalizer.getLocalizedString("- Require at least %d characters\n"), Integer.valueOf(getMinPwdLen())));
        int pwdMaxRepeats = getPwdMaxRepeats();
        if (pwdMaxRepeats > 0) {
            arrayList.add(String.format(GDLocalizer.getLocalizedString("- Disallow %d or more repeated characters\n"), Integer.valueOf(pwdMaxRepeats)));
        }
        if (getPwdIsAlphaNum()) {
            arrayList.add(GDLocalizer.getLocalizedString("- Require both letters and numbers\n"));
        }
        if (getPwdIsMixedCase()) {
            arrayList.add(GDLocalizer.getLocalizedString("- Require both upper and lower case\n"));
        }
        if (getPwdSplChar()) {
            arrayList.add(GDLocalizer.getLocalizedString("- Require at least one special character\n"));
        }
        if (getPwdDisallowNumSeq()) {
            arrayList.add(GDLocalizer.getLocalizedString("- Do not allow sequential numbers\n"));
        }
        if (getPwdPersonalInfo()) {
            arrayList.add(GDLocalizer.getLocalizedString("- Do not allow personal information\n"));
        }
        if (getBannedPwdListEnabled()) {
            arrayList.add(GDLocalizer.getLocalizedString("\nDo not allow banned passwords\n"));
        }
        return arrayList;
    }
}
