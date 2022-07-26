package com.good.gd.ndkproxy.auth.biometric.prompt;

/* loaded from: classes.dex */
public class BiometricPromptData {
    private BiometricClickListener cancelListener;
    private String description;
    private String negativeButtonText;
    private String title;

    public BiometricPromptData(String str, String str2, String str3, BiometricClickListener biometricClickListener) {
        this.title = str;
        this.description = str2;
        this.negativeButtonText = str3;
        this.cancelListener = biometricClickListener;
    }

    public boolean equals(BiometricPromptData biometricPromptData) {
        if (biometricPromptData == null) {
            return false;
        }
        if (this == biometricPromptData) {
            return true;
        }
        return this.title.equals(biometricPromptData.getTitle()) && this.description.equals(biometricPromptData.getDescription()) && this.negativeButtonText.equals(biometricPromptData.getNegativeButtonText()) && this.cancelListener.equals(biometricPromptData.getCancelListener());
    }

    public BiometricClickListener getCancelListener() {
        return this.cancelListener;
    }

    public String getDescription() {
        return this.description;
    }

    public String getNegativeButtonText() {
        return this.negativeButtonText;
    }

    public String getTitle() {
        return this.title;
    }

    public BiometricPromptData(BiometricClickListener biometricClickListener) {
        this.title = "Biometric Authentication";
        this.description = "Provide biometric data";
        this.negativeButtonText = "Cancel";
        this.cancelListener = biometricClickListener;
    }
}
