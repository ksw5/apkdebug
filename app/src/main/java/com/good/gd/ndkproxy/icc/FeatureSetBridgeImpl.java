package com.good.gd.ndkproxy.icc;

import com.good.gt.utils.FeatureSetBridge;

/* loaded from: classes.dex */
public class FeatureSetBridgeImpl implements FeatureSetBridge {
    private native String getFeatureSetNative();

    private native void onFeatureSetReceivedNative(String str);

    @Override // com.good.gt.utils.FeatureSetBridge
    public String getFeatureSet() {
        return getFeatureSetNative();
    }

    @Override // com.good.gt.utils.FeatureSetBridge
    public void onFeatureSetReceived(String str) {
        onFeatureSetReceivedNative(str);
    }
}
