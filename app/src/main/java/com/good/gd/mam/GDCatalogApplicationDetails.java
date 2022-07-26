package com.good.gd.mam;

import com.good.gd.GDVersion;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class GDCatalogApplicationDetails {
    private String developer;
    private String entitlementIdentifier;
    private String storeDescription;
    private boolean upgradeAvailable;
    private List<GDCatalogScreenshotDetails> screenshots = new ArrayList();
    private List<GDCatalogApplicationVersion> versions = new ArrayList();

    public String getDeveloper() {
        return this.developer;
    }

    public String getEntitlementIdentifier() {
        return this.entitlementIdentifier;
    }

    public GDCatalogApplicationVersion getLatestApplicationVersion() {
        int size = this.versions.size();
        if (size == 0) {
            return null;
        }
        GDCatalogApplicationVersion gDCatalogApplicationVersion = this.versions.get(0);
        GDVersion entitlementVersion = gDCatalogApplicationVersion.getEntitlementVersion();
        for (int i = 1; i < size; i++) {
            GDCatalogApplicationVersion gDCatalogApplicationVersion2 = this.versions.get(i);
            GDVersion entitlementVersion2 = gDCatalogApplicationVersion2.getEntitlementVersion();
            if (entitlementVersion2 != null && entitlementVersion2.isGreaterThanVersion(entitlementVersion)) {
                gDCatalogApplicationVersion = gDCatalogApplicationVersion2;
                entitlementVersion = entitlementVersion2;
            }
        }
        return gDCatalogApplicationVersion;
    }

    public List<GDCatalogScreenshotDetails> getScreenshots() {
        return this.screenshots;
    }

    public String getStoreDescription() {
        return this.storeDescription;
    }

    public List<GDCatalogApplicationVersion> getVersions() {
        return this.versions;
    }

    public boolean isUpgradeAvailable() {
        return this.upgradeAvailable;
    }

    public boolean isWebApplication() {
        GDCatalogApplicationVersionDetails latestApplicationVersionDetails = getLatestApplicationVersion().getLatestApplicationVersionDetails();
        if (latestApplicationVersionDetails == null) {
            return false;
        }
        String nativeApplicationIdentifier = latestApplicationVersionDetails.getNativeApplicationIdentifier();
        return nativeApplicationIdentifier == null || nativeApplicationIdentifier.length() == 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setDeveloper(String str) {
        this.developer = str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setEntitlementIdentifier(String str) {
        this.entitlementIdentifier = str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setScreenshots(List<GDCatalogScreenshotDetails> list) {
        this.screenshots = list;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setStoreDescription(String str) {
        this.storeDescription = str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setUpgradeAvailable(boolean z) {
        this.upgradeAvailable = z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setVersions(List<GDCatalogApplicationVersion> list) {
        this.versions = list;
    }
}
