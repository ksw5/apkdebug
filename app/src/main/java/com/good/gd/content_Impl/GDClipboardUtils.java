package com.good.gd.content_Impl;

import android.content.ClipData;
import android.content.Context;

/* loaded from: classes.dex */
public class GDClipboardUtils implements GDClipboardUtilsInterface {
    private static GDClipboardUtils _instance;
    private GDClipboardUtilsInterface mClipboardUtilsImpl = GDClipboardManagerImpl.getInstance();

    private GDClipboardUtils(Context context) {
    }

    public static GDClipboardUtils getInstance(Context context) {
        if (_instance == null) {
            _instance = new GDClipboardUtils(context);
        }
        return _instance;
    }

    @Override // com.good.gd.content_Impl.GDClipboardUtilsInterface
    public boolean isGDSecureClipBoardEnabled() {
        return this.mClipboardUtilsImpl.isGDSecureClipBoardEnabled();
    }

    @Override // com.good.gd.content_Impl.GDClipboardUtilsInterface
    public boolean isGDSecureClipData(ClipData clipData) {
        return this.mClipboardUtilsImpl.isGDSecureClipData(clipData);
    }

    @Override // com.good.gd.content_Impl.GDClipboardUtilsInterface
    public boolean isPasteFromGDAppEnabled() {
        return this.mClipboardUtilsImpl.isPasteFromGDAppEnabled();
    }

    @Override // com.good.gd.content_Impl.GDClipboardUtilsInterface
    public boolean isPasteFromNonGDAppEnabled() {
        return this.mClipboardUtilsImpl.isPasteFromNonGDAppEnabled();
    }
}
