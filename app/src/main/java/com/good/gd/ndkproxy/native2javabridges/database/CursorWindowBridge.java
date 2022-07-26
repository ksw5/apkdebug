package com.good.gd.ndkproxy.native2javabridges.database;

import com.good.gd.database.sqlite.CursorWindow;

/* loaded from: classes.dex */
final class CursorWindowBridge {
    CursorWindowBridge() {
    }

    private static String getCursorWindowClassString() {
        return CursorWindow.class.getName().replace(".", "/");
    }
}
