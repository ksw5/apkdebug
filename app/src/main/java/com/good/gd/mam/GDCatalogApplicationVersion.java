package com.good.gd.mam;

import com.good.gd.GDVersion;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* loaded from: classes.dex */
public class GDCatalogApplicationVersion {
    private List<GDCatalogApplicationVersionDetails> applicationVersions = new ArrayList();
    private GDVersion entitlementVersion;

    public List<GDCatalogApplicationVersionDetails> getApplicationVersions() {
        return this.applicationVersions;
    }

    public GDVersion getEntitlementVersion() {
        return this.entitlementVersion;
    }

    public GDCatalogApplicationVersionDetails getLatestApplicationVersionDetails() {
        int size = this.applicationVersions.size();
        if (size == 0) {
            return null;
        }
        GDCatalogApplicationVersionDetails gDCatalogApplicationVersionDetails = this.applicationVersions.get(0);
        Date releaseDate = gDCatalogApplicationVersionDetails.getReleaseDate();
        for (int i = 1; i < size; i++) {
            GDCatalogApplicationVersionDetails gDCatalogApplicationVersionDetails2 = this.applicationVersions.get(i);
            Date releaseDate2 = gDCatalogApplicationVersionDetails2.getReleaseDate();
            if (releaseDate2 != null && releaseDate == null) {
                releaseDate = releaseDate2;
            } else if (releaseDate2 != null && releaseDate != null && releaseDate2.after(releaseDate)) {
                gDCatalogApplicationVersionDetails = gDCatalogApplicationVersionDetails2;
                releaseDate = releaseDate2;
            }
        }
        return gDCatalogApplicationVersionDetails;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setApplicationVersions(List<GDCatalogApplicationVersionDetails> list) {
        this.applicationVersions = list;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setEntitlementVersion(GDVersion gDVersion) {
        this.entitlementVersion = gDVersion;
    }
}
