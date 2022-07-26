package com.good.gd;

/* loaded from: classes.dex */
public final class GDServiceDetail {
    private String identifier;
    private GDServiceType serviceType;
    private GDServiceProviderType type;
    private String version;

    public GDServiceDetail(String str, String str2, int i) {
        this.identifier = str;
        this.version = str2;
        this.serviceType = i == 0 ? GDServiceType.GD_SERVICE_TYPE_APPLICATION : GDServiceType.GD_SERVICE_TYPE_SERVER;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    @Deprecated
    public GDServiceProviderType getProviderType() {
        return this.type;
    }

    public GDServiceType getServiceType() {
        return this.serviceType;
    }

    public String getVersion() {
        return this.version;
    }

    @Deprecated
    public GDServiceDetail(String str, String str2, GDServiceProviderType gDServiceProviderType) {
        this.identifier = str;
        this.version = str2;
        this.type = gDServiceProviderType;
    }

    public GDServiceDetail(String str, String str2, GDServiceType gDServiceType) {
        this.identifier = str;
        this.version = str2;
        this.serviceType = gDServiceType;
    }
}
