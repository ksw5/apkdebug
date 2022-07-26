package com.good.gd.mam;

/* loaded from: classes.dex */
public class GDCatalogApplicationInstallerDetails {
    private String nativeApplicationDownloadUrl;
    private GDApplicationInstallerType type;
    private String webApplicationUrl;

    /* loaded from: classes.dex */
    enum GDApplicationInstallerType {
        WebApp,
        NativeApp
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public String getNativeApplicationDownloadUrl() {
        return this.nativeApplicationDownloadUrl;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public GDApplicationInstallerType getType() {
        return this.type;
    }

    public String getWebApplicationUrl() {
        return this.webApplicationUrl;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setNativeApplicationDownloadUrl(String str) {
        this.nativeApplicationDownloadUrl = str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setType(GDApplicationInstallerType gDApplicationInstallerType) {
        this.type = gDApplicationInstallerType;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setWebApplicationUrl(String str) {
        this.webApplicationUrl = str;
    }
}
