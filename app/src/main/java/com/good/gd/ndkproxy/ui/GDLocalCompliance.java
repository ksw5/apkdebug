package com.good.gd.ndkproxy.ui;

import android.text.TextUtils;
import com.good.gd.ndkproxy.ui.localization.Handler;

/* loaded from: classes.dex */
public class GDLocalCompliance {
    public static final String BLOCK_ID = "BLOCK_ID";
    public static final String MESSAGE = "MESSAGE";
    public static final String TITLE = "TITLE";
    private static GDLocalCompliance _instance;

    private GDLocalCompliance() {
    }

    public static synchronized GDLocalCompliance getInstance() {
        GDLocalCompliance gDLocalCompliance;
        synchronized (GDLocalCompliance.class) {
            if (_instance == null) {
                _instance = new GDLocalCompliance();
            }
            gDLocalCompliance = _instance;
        }
        return gDLocalCompliance;
    }

    public synchronized void executeLocalBlock(String str, String str2, String str3) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3)) {
            Handler.applyCustomLocalization(str, str2, str3);
        } else {
            throw new RuntimeException("param values should not be empty");
        }
    }

    public synchronized void executeLocalUnblock(String str) {
        if (!TextUtils.isEmpty(str)) {
            Handler.resetLocalization(str);
        } else {
            throw new RuntimeException("ID can not be empty");
        }
    }

    public synchronized void executeLocalWipe() {
        Handler.applyDefaultLocalization();
    }
}
