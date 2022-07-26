package com.good.gd.messages;

/* loaded from: classes.dex */
public class SetPasswordMsg {
    public char[] confirmPassword;
    public char[] newPassword;
    public char[] oldPassword;

    public SetPasswordMsg(char[] cArr, char[] cArr2, char[] cArr3) {
        this.oldPassword = cArr;
        this.newPassword = cArr2;
        this.confirmPassword = cArr3;
    }

    public String toString() {
        return "RemoteUnlockRequest";
    }
}
