package com.good.gd.messages;

/* loaded from: classes.dex */
public class LoginMsg {
    public boolean isWithPin;
    public char[] password;

    public LoginMsg(char[] cArr, boolean z) {
        this.password = cArr;
        this.isWithPin = z;
    }

    public String toString() {
        return "LoginMsg";
    }
}
