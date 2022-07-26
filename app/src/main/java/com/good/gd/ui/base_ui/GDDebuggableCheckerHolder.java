package com.good.gd.ui.base_ui;

import com.good.gd.utils.DebuggableChecker;

/* loaded from: classes.dex */
public class GDDebuggableCheckerHolder {
    private static DebuggableChecker debuggableChecker;

    public static DebuggableChecker getDebuggableChecker() {
        return debuggableChecker;
    }

    public static void setDebuggableChecker(DebuggableChecker debuggableChecker2) {
        debuggableChecker = debuggableChecker2;
    }
}
