package com.good.gd.icc.impl;

import android.content.Intent;
import com.good.gd.icc.GDServiceException;

/* loaded from: classes.dex */
public interface GDServiceHelperInterface {
    boolean GDConsume(Intent intent) throws GDServiceException;

    boolean canGDConsume(Intent intent);
}
