package com.good.gd.content_Impl;

import android.content.ClipData;

/* loaded from: classes.dex */
interface GDClipboardUtilsInterface {
    boolean isGDSecureClipBoardEnabled();

    boolean isGDSecureClipData(ClipData clipData);

    boolean isPasteFromGDAppEnabled();

    boolean isPasteFromNonGDAppEnabled();
}
