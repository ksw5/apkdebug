package com.good.gd.mam;

import android.content.pm.ApplicationInfo;
import com.good.gd.context.GDContext;
import com.good.gt.utils.PackageManagerUtils;
import java.util.Date;

/* loaded from: classes.dex */
public class GDCatalogApplicationVersionDetails {
    private String applicationVersion;
    private boolean inMarket;
    private boolean isInstalled;
    private String nativeApplicationIdentifier;
    private Date releaseDate;
    private String releaseNotes;
    private int size;

    public String getApplicationVersion() {
        return this.applicationVersion;
    }

    public String getNativeApplicationIdentifier() {
        return this.nativeApplicationIdentifier;
    }

    public Date getReleaseDate() {
        return this.releaseDate;
    }

    public String getReleaseNotes() {
        return this.releaseNotes;
    }

    public int getSize() {
        return this.size;
    }

    public boolean inMarket() {
        return this.inMarket;
    }

    public boolean isInstalled() {
        return this.isInstalled;
    }

    public boolean isInstalledCheck() {
        boolean isAppInstalled = PackageManagerUtils.isAppInstalled(this.nativeApplicationIdentifier, this.applicationVersion).isAppInstalled();
        if (!isAppInstalled) {
            for (ApplicationInfo applicationInfo : GDContext.getInstance().getApplicationContext().getPackageManager().getInstalledApplications(128)) {
                if (applicationInfo.packageName.equalsIgnoreCase(this.nativeApplicationIdentifier)) {
                    return true;
                }
            }
            return isAppInstalled;
        }
        return isAppInstalled;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setApplicationVersion(String str) {
        this.applicationVersion = str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setIsInstalled(boolean z) {
        this.isInstalled = z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setMarket(boolean z) {
        this.inMarket = z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setNativeApplicationIdentifier(String str) {
        this.nativeApplicationIdentifier = str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setReleaseDate(Date date) {
        this.releaseDate = date;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setReleaseNotes(String str) {
        this.releaseNotes = str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setSize(int i) {
        this.size = i;
    }
}
