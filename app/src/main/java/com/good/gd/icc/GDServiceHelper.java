package com.good.gd.icc;

import android.content.Intent;
import com.good.gd.icc.impl.GDServiceHelperImplProvider;
import com.good.gd.icc.impl.GDServiceHelperInterface;

/* loaded from: classes.dex */
public class GDServiceHelper {
    private static GDServiceHelper sInstance = new GDServiceHelper();

    private GDServiceHelper() {
    }

    public static GDServiceHelper getInstance() {
        return sInstance;
    }

    public boolean GDConsume(Intent intent) throws GDServiceException {
        GDServiceHelperInterface implementation = GDServiceHelperImplProvider.getInstance().getImplementation();
        if (implementation != null) {
            return implementation.GDConsume(intent);
        }
        return false;
    }

    public boolean canGDConsume(Intent intent) {
        GDServiceHelperInterface implementation = GDServiceHelperImplProvider.getInstance().getImplementation();
        if (implementation != null) {
            return implementation.canGDConsume(intent);
        }
        return false;
    }
}
