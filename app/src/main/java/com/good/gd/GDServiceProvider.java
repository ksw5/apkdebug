package com.good.gd;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.util.Vector;

/* loaded from: classes.dex */
public final class GDServiceProvider {
    private String address;
    private Bitmap icon;
    private boolean iconPending;
    private String identifier;
    private String name;
    private Vector<GDAppServer> serverCluster;
    private Vector<GDServiceDetail> services;
    private String version;

    public GDServiceProvider(String str, String str2, String str3, String str4, byte[] bArr, boolean z, Object[] objArr, Object[] objArr2) {
        this.identifier = str;
        this.version = str2;
        this.name = str3;
        this.address = str4;
        this.iconPending = z;
        if (bArr != null && bArr.length > 0) {
            this.icon = BitmapFactory.decodeByteArray(bArr, 0, bArr.length);
        }
        this.serverCluster = new Vector<>();
        if (objArr != null) {
            for (Object obj : objArr) {
                GDAppServer gDAppServer = (GDAppServer) obj;
                this.serverCluster.add(new GDAppServer(gDAppServer.server, gDAppServer.port, gDAppServer.priority));
            }
        }
        this.services = new Vector<>();
        if (objArr2 != null) {
            for (Object obj2 : objArr2) {
                GDServiceDetail gDServiceDetail = (GDServiceDetail) obj2;
                this.services.add(new GDServiceDetail(gDServiceDetail.getIdentifier(), gDServiceDetail.getVersion(), gDServiceDetail.getServiceType()));
            }
        }
    }

    public String getAddress() {
        return this.address;
    }

    public Bitmap getIcon() {
        return this.icon;
    }

    public boolean getIconPending() {
        return this.iconPending;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public String getName() {
        return this.name;
    }

    public Vector<GDAppServer> getServerCluster() {
        return this.serverCluster;
    }

    public Vector<GDServiceDetail> getServices() {
        return this.services;
    }

    public String getVersion() {
        return this.version;
    }

    public GDServiceProvider(String str, String str2, String str3, String str4, byte[] bArr, boolean z, Object[] objArr) {
        this.identifier = str;
        this.version = str2;
        this.name = str3;
        this.address = str4;
        this.iconPending = z;
        if (bArr != null && bArr.length > 0) {
            this.icon = BitmapFactory.decodeByteArray(bArr, 0, bArr.length);
        }
        this.services = new Vector<>();
        if (objArr != null) {
            for (Object obj : objArr) {
                GDServiceDetail gDServiceDetail = (GDServiceDetail) obj;
                this.services.add(new GDServiceDetail(gDServiceDetail.getIdentifier(), gDServiceDetail.getVersion(), gDServiceDetail.getServiceType()));
            }
        }
    }
}
