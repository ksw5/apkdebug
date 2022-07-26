package com.good.gd.content;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.DragEvent;
import android.view.View;
import com.good.gd.content_Impl.GDClipboardManagerImpl;

/* loaded from: classes.dex */
public class ClipboardManager {
    public static final int CLIPDATA_TEXT_SIZE_LIMIT = 102400;
    private static ClipboardManager _instance;
    private final GDClipboardManagerImpl mImpl = GDClipboardManagerImpl.getInstance();

    private ClipboardManager(Context context) {
    }

    public static ClipboardManager getInstance(Context context) {
        if (_instance == null) {
            _instance = new ClipboardManager(context);
        }
        return _instance;
    }

    public void addPrimaryClipChangedListener(ClipboardManager.OnPrimaryClipChangedListener onPrimaryClipChangedListener) {
        this.mImpl.addPrimaryClipChangedListener(onPrimaryClipChangedListener);
    }

    public ClipData getClipData(DragEvent dragEvent) {
        return this.mImpl.getClipData(dragEvent);
    }

    public ClipData getPrimaryClip() {
        return this.mImpl.getPrimaryClip();
    }

    public ClipDescription getPrimaryClipDescription() {
        return this.mImpl.getPrimaryClipDescription();
    }

    public boolean hasPrimaryClip() {
        return this.mImpl.hasPrimaryClip();
    }

    public void removePrimaryClipChangedListener(ClipboardManager.OnPrimaryClipChangedListener onPrimaryClipChangedListener) {
        this.mImpl.removePrimaryClipChangedListener(onPrimaryClipChangedListener);
    }

    public void setPrimaryClip(ClipData clipData) {
        this.mImpl.setPrimaryClip(clipData);
    }

    public void startDragAndDrop(ClipData clipData, View view) {
        this.mImpl.startDragAndDrop(clipData, view);
    }

    public void startDragAndDrop(ClipData clipData, View view, View.DragShadowBuilder dragShadowBuilder, Object obj) {
        this.mImpl.startDragAndDrop(clipData, view, dragShadowBuilder, obj);
    }

    public void startDragAndDrop(ClipData clipData, View view, View.DragShadowBuilder dragShadowBuilder, Object obj, int i) {
        this.mImpl.startDragAndDrop(clipData, view, dragShadowBuilder, obj, i);
    }
}
