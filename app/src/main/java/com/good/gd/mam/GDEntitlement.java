package com.good.gd.mam;

import com.good.gd.GDVersion;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class GDEntitlement implements Comparable<GDEntitlement> {
    private String applicationType;
    private int averageRating;
    private String catalogDescription;
    private String developer;
    private Map<String, Boolean> downloadableVersions;
    private String entitlementIdentifier;
    private List<GDVersion> entitlementVersions;
    private GDCatalogIconDetails icon;
    private List<GDCatalogIconDetails> icons;
    private boolean isApplication;
    private boolean isGoodDynamicsApplication;
    private String name;
    private Map<String, Boolean> upgradableVersions;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static List<GDEntitlement> filterByApplications(List<GDEntitlement> list) {
        if (list != null) {
            ArrayList arrayList = new ArrayList();
            for (GDEntitlement gDEntitlement : list) {
                if (gDEntitlement.isApplication()) {
                    ArrayList arrayList2 = new ArrayList();
                    for (GDVersion gDVersion : gDEntitlement.getEntitlementVersions()) {
                        if (gDEntitlement.downloadableVersions().get(gDVersion.toString()).booleanValue()) {
                            arrayList2.add(gDVersion);
                        }
                    }
                    gDEntitlement.setEntitlementVersions(arrayList2);
                    arrayList.add(gDEntitlement);
                }
            }
            return arrayList;
        }
        return list;
    }

    Map<String, Boolean> downloadableVersions() {
        return this.downloadableVersions;
    }

    public String getApplicationType() {
        return this.applicationType;
    }

    int getAverageRating() {
        return this.averageRating;
    }

    public String getCatalogDescription() {
        return this.catalogDescription;
    }

    public GDCatalogIconDetails getClosestIcon(int i, int i2) {
        GDCatalogIconDetails gDCatalogIconDetails = this.icon;
        int size = this.icons.size();
        if (size > 0) {
            for (int i3 = 0; i3 < size; i3++) {
                GDCatalogIconDetails gDCatalogIconDetails2 = this.icons.get(i3);
                if (gDCatalogIconDetails == null || (gDCatalogIconDetails2.getWidth() > gDCatalogIconDetails.getWidth() && gDCatalogIconDetails2.getWidth() <= i && gDCatalogIconDetails2.getHeight() > gDCatalogIconDetails.getHeight() && gDCatalogIconDetails2.getHeight() <= i2)) {
                    gDCatalogIconDetails = gDCatalogIconDetails2;
                }
            }
        }
        return gDCatalogIconDetails;
    }

    public String getDeveloper() {
        return this.developer;
    }

    public String getEntitlementIdentifier() {
        return this.entitlementIdentifier;
    }

    public List<GDVersion> getEntitlementVersions() {
        return this.entitlementVersions;
    }

    public GDCatalogIconDetails getIcon() {
        return this.icon;
    }

    public List<GDCatalogIconDetails> getIcons() {
        return this.icons;
    }

    public GDVersion getLatestEntitlementVersion() {
        int size = this.entitlementVersions.size();
        if (size == 0) {
            return null;
        }
        GDVersion gDVersion = this.entitlementVersions.get(0);
        for (int i = 1; i < size; i++) {
            GDVersion gDVersion2 = this.entitlementVersions.get(i);
            if (gDVersion2.isGreaterThanVersion(gDVersion)) {
                gDVersion = gDVersion2;
            }
        }
        return gDVersion;
    }

    public String getName() {
        return this.name;
    }

    boolean isApplication() {
        return this.isApplication;
    }

    public boolean isEnterpriseApplication() {
        String str = this.applicationType;
        return str != null && str.equals("native");
    }

    public boolean isGoodDynamicsApplication() {
        return this.isGoodDynamicsApplication;
    }

    public boolean isPublicApplication() {
        String str = this.applicationType;
        return str != null && str.equals("linked");
    }

    public boolean isUpgradeAvailable(GDVersion gDVersion) {
        return upgradableVersions().get(gDVersion.toString()).booleanValue();
    }

    public boolean isWebApplication() {
        String str = this.applicationType;
        return str != null && str.equals("web");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setApplication(boolean z) {
        this.isApplication = z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setApplicationType(String str) {
        this.applicationType = str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setAverageRating(int i) {
        this.averageRating = i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setCatalogDescription(String str) {
        this.catalogDescription = str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setDeveloper(String str) {
        this.developer = str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setDownloadableVersions(Map<String, Boolean> map) {
        this.downloadableVersions = map;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setEntitlementIdentifier(String str) {
        this.entitlementIdentifier = str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setEntitlementVersions(List<GDVersion> list) {
        this.entitlementVersions = list;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setGoodDynamicsApplication(boolean z) {
        this.isGoodDynamicsApplication = z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setIcon(GDCatalogIconDetails gDCatalogIconDetails) {
        this.icon = gDCatalogIconDetails;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setIcons(List<GDCatalogIconDetails> list) {
        this.icons = list;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setName(String str) {
        this.name = str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setUpgradableVersions(Map<String, Boolean> map) {
        this.upgradableVersions = map;
    }

    Map<String, Boolean> upgradableVersions() {
        return this.upgradableVersions;
    }

    @Override // java.lang.Comparable
    public int compareTo(GDEntitlement gDEntitlement) {
        return this.name.compareTo(gDEntitlement.getName());
    }

    public GDCatalogIconDetails getIcon(int i, int i2) {
        for (GDCatalogIconDetails gDCatalogIconDetails : this.icons) {
            if (gDCatalogIconDetails.getWidth() == i && gDCatalogIconDetails.getHeight() == i2) {
                return gDCatalogIconDetails;
            }
        }
        GDCatalogIconDetails gDCatalogIconDetails2 = this.icon;
        if (gDCatalogIconDetails2 != null && gDCatalogIconDetails2.getWidth() == i && this.icon.getHeight() == i2) {
            return this.icon;
        }
        return null;
    }
}
