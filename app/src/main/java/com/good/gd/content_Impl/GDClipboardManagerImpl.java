package com.good.gd.content_Impl;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.view.DragEvent;
import android.view.View;
import com.good.gd.dlp_util.DragAndDropUtils;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.utils.GDSDKState;
import com.good.gt.context.GTBaseContext;
import java.nio.charset.Charset;
import java.util.LinkedHashSet;
import java.util.Set;

/* loaded from: classes.dex */
public final class GDClipboardManagerImpl implements GDClipboardUtilsInterface {
    private static final String EMPTY_CLIPBOARD_FILLER_STRING = ".";
    private static GDClipboardManagerImpl _instance;
    private ClipboardManager mAndroidClipboardManager;
    private GDWidgetUserActivityInterface mUserActivityInterface;
    private boolean secureClipboardEnabled = true;
    private boolean pasteDisabled = false;
    private boolean preventPasteFromGDApps = true;
    private final Set<ClipboardManager.OnPrimaryClipChangedListener> clipChangedListeners = new LinkedHashSet();
    private final ClipboardManager.OnPrimaryClipChangedListener internalClipchangedListener = new hbfhc();

    /* loaded from: classes.dex */
    class hbfhc implements ClipboardManager.OnPrimaryClipChangedListener {
        hbfhc() {
        }

        @Override // android.content.ClipboardManager.OnPrimaryClipChangedListener
        public void onPrimaryClipChanged() {
            GDLog.DBGPRINTF(16, "GDClipboardManagerImpl onPrimaryClipChanged");
            if (GDClipboardManagerImpl.this.clipChangedListeners.isEmpty() || !GDClipboardManagerImpl.this.hasPrimaryClip()) {
                return;
            }
            GDLog.DBGPRINTF(16, "GDClipboardManagerImpl onPrimaryClipChanged has GD");
            for (ClipboardManager.OnPrimaryClipChangedListener onPrimaryClipChangedListener : GDClipboardManagerImpl.this.clipChangedListeners) {
                onPrimaryClipChangedListener.onPrimaryClipChanged();
            }
        }
    }

    private GDClipboardManagerImpl() {
    }

    private ClipboardManager getAndroidClipboardManager() {
        if (this.mAndroidClipboardManager == null) {
            this.mAndroidClipboardManager = (ClipboardManager) GTBaseContext.getInstance().getApplicationContext().getSystemService("clipboard");
        }
        return this.mAndroidClipboardManager;
    }

    private CharSequence getClipDataText(ClipData clipData) {
        for (int i = 0; i < clipData.getItemCount(); i++) {
            ClipData.Item itemAt = clipData.getItemAt(i);
            if (itemAt.getText() != null) {
                return itemAt.getText();
            }
        }
        return "";
    }

    public static GDClipboardManagerImpl getInstance() {
        if (_instance == null) {
            _instance = new GDClipboardManagerImpl();
        }
        return _instance;
    }

    private ClipData handleIncomingClipData(ClipData clipData) {
        String str;
        if (clipData.getDescription().getLabel() == null) {
            str = null;
        } else {
            str = clipData.getDescription().getLabel().toString();
        }
        GDLog.DBGPRINTF(16, "GDClipboardManagerImpl handleIncomingClipData clipLabel: " + str);
        if (isGDSecureClipData(clipData)) {
            GDLog.DBGPRINTF(16, "GDClipboardManagerImpl handleIncomingClipData received Encrypted data");
            return GDClipData.newDecryptedTextData(clipData);
        }
        GDLog.DBGPRINTF(16, "GDClipboardManagerImpl handleIncomingClipData received Decrypted data");
        if (this.pasteDisabled) {
            GDLog.DBGPRINTF(16, "GDClipboardManagerImpl handleIncomingClipData InBound DLP enabled, return null");
            return null;
        }
        GDLog.DBGPRINTF(16, "GDClipboardManagerImpl handleIncomingClipData InBound DLP disabled, return original data");
        return clipData;
    }

    private ClipData handleOutgoingClipData(ClipData clipData) {
        if (isGDSecureClipBoardEnabled()) {
            CharSequence clipDataText = getClipDataText(clipData);
            GDLog.DBGPRINTF(16, "GDClipboardManagerImpl handleOutgoingClipData OutBound DLP enabled");
            return GDClipData.newEncryptedTextData(clipDataText);
        }
        GDLog.DBGPRINTF(16, "GDClipboardManagerImpl handleOutgoingClipData OutBound DLP disabled");
        return clipData;
    }

    private boolean isExceedsLimit(String str) {
        return str != null && str.length() * 2 > 102400 && str.getBytes(Charset.defaultCharset()).length > 102400;
    }

    private void populateEmptyClipBoard(ClipData clipData) {
        GDLog.DBGPRINTF(16, "GDClipboardManagerImpl populateEmptyClipBoard");
        if (getAndroidClipboardManager().hasPrimaryClip()) {
            String str = (String) getAndroidClipboardManager().getPrimaryClip().getDescription().getLabel();
            if (str != null && str.equals("GD encrypted clipboard data")) {
                getAndroidClipboardManager().setPrimaryClip(ClipData.newPlainText("GD", EMPTY_CLIPBOARD_FILLER_STRING));
            }
            getAndroidClipboardManager().setPrimaryClip(clipData);
            return;
        }
        getAndroidClipboardManager().setPrimaryClip(ClipData.newPlainText("GD", EMPTY_CLIPBOARD_FILLER_STRING));
        getAndroidClipboardManager().setPrimaryClip(clipData);
    }

    public void addPrimaryClipChangedListener(ClipboardManager.OnPrimaryClipChangedListener onPrimaryClipChangedListener) {
        if (onPrimaryClipChangedListener != null) {
            GDLog.DBGPRINTF(16, "GDClipboardManagerImpl addPrimaryClipChangedListener: " + onPrimaryClipChangedListener.getClass().getName());
            synchronized (this.clipChangedListeners) {
                if (this.clipChangedListeners.isEmpty()) {
                    getAndroidClipboardManager().addPrimaryClipChangedListener(this.internalClipchangedListener);
                }
                this.clipChangedListeners.add(onPrimaryClipChangedListener);
            }
        }
    }

    public void emptyClipBoard() {
        if (getAndroidClipboardManager().hasPrimaryClip()) {
            getAndroidClipboardManager().setPrimaryClip(ClipData.newPlainText("GD", ""));
        }
    }

    public ClipData getClipData(DragEvent dragEvent) {
        GDLog.DBGPRINTF(16, "GDClipboardManagerImpl getClipData\n");
        GDSDKState.getInstance().checkAuthorized();
        if (dragEvent.getAction() == 3) {
            GDLog.DBGPRINTF(16, "GDClipboardManagerImpl getClipData ACTION_DROP\n");
            return handleIncomingClipData(dragEvent.getClipData());
        }
        GDLog.DBGPRINTF(16, "GDClipboardManagerImpl getClipData no action to handle\n");
        return null;
    }

    public ClipData getPrimaryClip() {
        GDSDKState.getInstance().checkAuthorized();
        if (!getAndroidClipboardManager().hasPrimaryClip()) {
            GDLog.DBGPRINTF(16, "GDClipboardManagerImpl getPrimaryClip hasPrimaryClip == false");
            return null;
        }
        return handleIncomingClipData(getAndroidClipboardManager().getPrimaryClip());
    }

    public ClipDescription getPrimaryClipDescription() {
        return getAndroidClipboardManager().getPrimaryClipDescription();
    }

    public boolean hasPrimaryClip() {
        ClipData primaryClip = getAndroidClipboardManager().getPrimaryClip();
        return primaryClip != null && (isGDSecureClipData(primaryClip) || isPasteFromNonGDAppEnabled());
    }

    @Override // com.good.gd.content_Impl.GDClipboardUtilsInterface
    public boolean isGDSecureClipBoardEnabled() {
        return this.secureClipboardEnabled;
    }

    @Override // com.good.gd.content_Impl.GDClipboardUtilsInterface
    public boolean isGDSecureClipData(ClipData clipData) {
        CharSequence label = clipData.getDescription().getLabel();
        return label != null && "GD encrypted clipboard data".equals(label.toString());
    }

    @Override // com.good.gd.content_Impl.GDClipboardUtilsInterface
    public boolean isPasteFromGDAppEnabled() {
        return !this.preventPasteFromGDApps;
    }

    @Override // com.good.gd.content_Impl.GDClipboardUtilsInterface
    public boolean isPasteFromNonGDAppEnabled() {
        return !this.pasteDisabled;
    }

    public void registerUserActivityInterface(GDWidgetUserActivityInterface gDWidgetUserActivityInterface) {
        this.mUserActivityInterface = gDWidgetUserActivityInterface;
    }

    public void removePrimaryClipChangedListener(ClipboardManager.OnPrimaryClipChangedListener onPrimaryClipChangedListener) {
        if (onPrimaryClipChangedListener != null) {
            GDLog.DBGPRINTF(16, "GDClipboardManagerImpl removePrimaryClipChangedListener: " + onPrimaryClipChangedListener.getClass().getName());
            synchronized (this.clipChangedListeners) {
                this.clipChangedListeners.remove(onPrimaryClipChangedListener);
                if (this.clipChangedListeners.isEmpty()) {
                    getAndroidClipboardManager().removePrimaryClipChangedListener(this.internalClipchangedListener);
                }
            }
        }
    }

    public void reportUserActivity() {
        GDWidgetUserActivityInterface gDWidgetUserActivityInterface = this.mUserActivityInterface;
        if (gDWidgetUserActivityInterface != null) {
            gDWidgetUserActivityInterface.onUserActivity();
        }
    }

    public void setPrimaryClip(ClipData clipData) {
        GDSDKState.getInstance().checkAuthorized();
        CharSequence clipDataText = getClipDataText(clipData);
        GDLog.DBGPRINTF(16, "GDClipboardManagerImpl setPrimaryClip input clipDataText length: " + clipDataText.length());
        if (!isExceedsLimit(clipDataText.toString())) {
            ClipData handleOutgoingClipData = handleOutgoingClipData(clipData);
            if (handleOutgoingClipData != null) {
                populateEmptyClipBoard(handleOutgoingClipData);
                return;
            } else {
                GDLog.DBGPRINTF(12, "GDClipboardManagerImpl setPrimaryClip clipData to set is null");
                return;
            }
        }
        throw new IllegalArgumentException("clipData size exceeds allowed limit of 102400 bytes");
    }

    public void setSecureClipboardEnabled(boolean z, boolean z2, boolean z3, boolean z4) {
        GDLog.DBGPRINTF(16, String.format("GDClipboardManagerImpl setSecureClipboardEnabled secureClipboardEnabled:%s\npreventPasteFromNonGDApps:%s\npreventPasteFromGDApps:%s\nisOldDLPFormat:%s", Boolean.valueOf(z), Boolean.valueOf(z2), Boolean.valueOf(z3), Boolean.valueOf(z4)));
        if (z4) {
            this.secureClipboardEnabled = z;
        } else {
            this.secureClipboardEnabled = z3;
        }
        this.pasteDisabled = z2;
        this.preventPasteFromGDApps = z3;
    }

    public void startDragAndDrop(ClipData clipData, View view) {
        startDragAndDrop(clipData, view, null, null);
    }

    public void startDragAndDrop(ClipData clipData, View view, View.DragShadowBuilder dragShadowBuilder, Object obj) {
        startDragAndDrop(clipData, view, dragShadowBuilder, obj, 256);
    }

    public void startDragAndDrop(ClipData clipData, View view, View.DragShadowBuilder dragShadowBuilder, Object obj, int i) {
        GDLog.DBGPRINTF(16, "GDClipbordManagerImpl startDragAndDrop\n");
        GDSDKState.getInstance().checkAuthorized();
        ClipData handleOutgoingClipData = handleOutgoingClipData(clipData);
        if (!isGDSecureClipBoardEnabled()) {
            if (dragShadowBuilder == null) {
                dragShadowBuilder = DragAndDropUtils.getTextThumbnailBuilder(view, getClipDataText(handleOutgoingClipData).toString());
            }
            GDLog.DBGPRINTF(16, "GDClipboardManagerImpl startStandardDrag\n");
        } else {
            i = 256;
            dragShadowBuilder = DragAndDropUtils.getSecuredTextThumbnailBuilder(view.getContext());
            GDLog.DBGPRINTF(16, "GDClipboardManagerImpl startSecureDrag\n");
        }
        view.startDragAndDrop(handleOutgoingClipData, dragShadowBuilder, obj, i);
    }
}
