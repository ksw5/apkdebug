package com.good.gd.utils;

import android.os.Build;

/* loaded from: classes.dex */
public class GDEmulatorChecker implements EmulatorChecker {
    private static final String NEW_EMULATOR_IDENTIFIER = "ranchu";
    private static final String OLD_EMULATOR_IDENTIFIER = "goldfish";

    @Override // com.good.gd.utils.EmulatorChecker
    public boolean isEmulator() {
        return OLD_EMULATOR_IDENTIFIER.equals(Build.HARDWARE) || NEW_EMULATOR_IDENTIFIER.equals(Build.HARDWARE);
    }
}
