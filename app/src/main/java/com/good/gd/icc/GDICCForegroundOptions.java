package com.good.gd.icc;

/* loaded from: classes.dex */
public enum GDICCForegroundOptions {
    PreferMeInForeground,
    PreferPeerInForeground,
    NoForegroundPreference;

    public static GDICCForegroundOptions createFromOrdinal(int i) {
        if (i == 0) {
            return PreferMeInForeground;
        }
        if (i == 1) {
            return PreferPeerInForeground;
        }
        return NoForegroundPreference;
    }
}
