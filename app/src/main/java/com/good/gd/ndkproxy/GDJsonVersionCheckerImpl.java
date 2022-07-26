package com.good.gd.ndkproxy;

import android.util.SparseArray;
import com.good.gd.profileoverride.GDBISProfileOverrideAsyncCallback;
import com.good.gd.profileoverride.GDBISProfileOverrideCallback;
import org.json.JSONArray;

/* loaded from: classes.dex */
public class GDJsonVersionCheckerImpl {
    private static SparseArray<GDBISProfileOverrideCallback> callbackMap = new SparseArray<>();
    private static int counter = 0;

    private static void invokeCallback(int i, int i2, String str) {
        GDBISProfileOverrideCallback gDBISProfileOverrideCallback = callbackMap.get(i);
        if (gDBISProfileOverrideCallback == null) {
            GDLog.DBGPRINTF(12, "GDJsonVersionCheckerImpl::invokeCallback - could not find corresponding callback");
            return;
        }
        gDBISProfileOverrideCallback.onProfileOverrideApplied(i2, str);
        callbackMap.remove(i);
    }

    private static native void resetValidationStateNative(String str, String str2, int i);

    public static synchronized void revertProfile(String str, JSONArray jSONArray, GDBISProfileOverrideCallback gDBISProfileOverrideCallback) {
        synchronized (GDJsonVersionCheckerImpl.class) {
            callbackMap.put(counter, new GDBISProfileOverrideAsyncCallback(gDBISProfileOverrideCallback));
            String jSONArray2 = jSONArray.toString();
            int i = counter;
            counter = i + 1;
            resetValidationStateNative(str, jSONArray2, i);
        }
    }

    public static synchronized void setProfile(String str, JSONArray jSONArray, GDBISProfileOverrideCallback gDBISProfileOverrideCallback) {
        synchronized (GDJsonVersionCheckerImpl.class) {
            callbackMap.put(counter, new GDBISProfileOverrideAsyncCallback(gDBISProfileOverrideCallback));
            String jSONArray2 = jSONArray.toString();
            int i = counter;
            counter = i + 1;
            validateVersionNative(str, jSONArray2, i);
        }
    }

    private static native void validateVersionNative(String str, String str2, int i);
}
