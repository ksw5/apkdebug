package com.good.gd.profileoverride;

import com.good.gd.ndkproxy.GDJsonVersionCheckerImpl;
import org.json.JSONArray;

/* loaded from: classes.dex */
public class GDBISProfileOverride {
    private String componentName;

    public GDBISProfileOverride(String str) {
        this.componentName = str;
    }

    public void revertProfile(JSONArray jSONArray, GDBISProfileOverrideCallback gDBISProfileOverrideCallback) {
        GDJsonVersionCheckerImpl.revertProfile(this.componentName, jSONArray, gDBISProfileOverrideCallback);
    }

    public void setProfile(JSONArray jSONArray, GDBISProfileOverrideCallback gDBISProfileOverrideCallback) {
        GDJsonVersionCheckerImpl.setProfile(this.componentName, jSONArray, gDBISProfileOverrideCallback);
    }
}
