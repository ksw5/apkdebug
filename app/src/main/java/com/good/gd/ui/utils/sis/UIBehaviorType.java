package com.good.gd.ui.utils.sis;

import android.util.SparseArray;

/* loaded from: classes.dex */
public enum UIBehaviorType {
    SIS_PRIMER_SCREEN(0),
    PERMISSION_DIALOG(1),
    SIS_FRAMEWORK_DEV_LOC_SETTINGS_DIALOG(2),
    SIS_GOOGLE_DEV_LOC_SETTINGS_DIALOG(3);
    
    private static final SparseArray<UIBehaviorType> values = new SparseArray<>(4);
    private int value;

    static {
        UIBehaviorType[] values2;
        for (UIBehaviorType uIBehaviorType : values()) {
            values.put(uIBehaviorType.getValue(), uIBehaviorType);
        }
    }

    UIBehaviorType(int i) {
        this.value = i;
    }

    public static UIBehaviorType getTypeForInt(int i) {
        return values.get(i, SIS_PRIMER_SCREEN);
    }

    public int getValue() {
        return this.value;
    }
}
