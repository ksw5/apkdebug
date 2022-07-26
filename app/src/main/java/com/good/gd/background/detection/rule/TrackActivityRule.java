package com.good.gd.background.detection.rule;

import android.app.Activity;

/* loaded from: classes.dex */
public class TrackActivityRule implements TrackActivityLifecycleRule {
    @Override // com.good.gd.background.detection.rule.TrackActivityLifecycleRule
    public boolean shouldTrackActivityLifecycle(Activity activity) {
        return true;
    }
}
