package com.good.gd.icc.impl;

/* loaded from: classes.dex */
public class GDServiceHelperImplProvider {
    private static GDServiceHelperImplProvider ourInstance = new GDServiceHelperImplProvider();
    private GDServiceHelperInterface implDelegate;

    private GDServiceHelperImplProvider() {
    }

    public static GDServiceHelperImplProvider getInstance() {
        return ourInstance;
    }

    public GDServiceHelperInterface getImplementation() {
        return this.implDelegate;
    }

    public void initialize(GDServiceHelperInterface gDServiceHelperInterface) {
        this.implDelegate = gDServiceHelperInterface;
    }
}
