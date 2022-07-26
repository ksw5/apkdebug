package com.good.gd.ndkproxy.enterprise;

/* loaded from: classes.dex */
public class GDENocServer {
    private String details;
    private String title;

    private GDENocServer(String str, String str2) {
        this.title = str;
        this.details = str2;
    }

    public boolean equals(Object obj) {
        if (obj instanceof GDENocServer) {
            GDENocServer gDENocServer = (GDENocServer) obj;
            return this.title.equals(gDENocServer.getTitle()) && this.details.equals(gDENocServer.getDetails());
        }
        return false;
    }

    public String getDetails() {
        return this.details;
    }

    public String getTitle() {
        return this.title;
    }

    public int hashCode() {
        return this.title.hashCode();
    }
}
