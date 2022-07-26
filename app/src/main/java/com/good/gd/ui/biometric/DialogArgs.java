package com.good.gd.ui.biometric;

import android.view.View;

/* loaded from: classes.dex */
public class DialogArgs {
    private View.OnClickListener negativeButtonCallback;
    private String negativeButtonTextKey;
    private View.OnClickListener positiveButtonCallback;
    private String positiveButtonTextKey;
    private String status;
    private String textKey;
    private String titleKey;

    public DialogArgs(String str, String str2, String str3, String str4) {
        this.titleKey = str;
        this.textKey = str2;
        this.negativeButtonTextKey = str3;
        this.positiveButtonTextKey = str4;
    }

    public View.OnClickListener getNegativeButtonCallback() {
        return this.negativeButtonCallback;
    }

    public String getNegativeButtonTextKey() {
        return this.negativeButtonTextKey;
    }

    public View.OnClickListener getPositiveButtonCallback() {
        return this.positiveButtonCallback;
    }

    public String getPositiveButtonTextKey() {
        return this.positiveButtonTextKey;
    }

    public String getStatus() {
        return this.status;
    }

    public String getTextKey() {
        return this.textKey;
    }

    public String getTitleKey() {
        return this.titleKey;
    }

    public void setNegativeButtonCallback(View.OnClickListener onClickListener) {
        this.negativeButtonCallback = onClickListener;
    }

    public void setPositiveButtonCallback(View.OnClickListener onClickListener) {
        this.positiveButtonCallback = onClickListener;
    }

    public void setPositiveButtonTextKey(String str) {
        this.positiveButtonTextKey = str;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public DialogArgs(String str, String str2, String str3, String str4, String str5) {
        this(str, str2, str3, str4);
        this.status = str5;
    }
}
