package com.good.gd.dlp_util;

import android.content.Context;
import android.view.inputmethod.InputConnection;
import com.good.gd.content_Impl.GDClipboardUtils;
import com.good.gd.ndkproxy.GDLog;

/* loaded from: classes.dex */
public class DLPInputConnection extends EditableInputConnectionImpl {
    private final int INSERTION_TEXT_LENGTH_LIMIT = 50;
    private GDClipboardUtils gdClipboardUtils;

    public DLPInputConnection(InputConnection inputConnection, Context context) {
        super(inputConnection);
        this.gdClipboardUtils = GDClipboardUtils.getInstance(context);
    }

    @Override // com.good.gd.dlp_util.EditableInputConnectionImpl, android.view.inputmethod.InputConnection
    public boolean commitText(CharSequence charSequence, int i) {
        if (!this.gdClipboardUtils.isPasteFromNonGDAppEnabled() && charSequence.length() > 50) {
            GDLog.DBGPRINTF(14, "DLPInputConnection commitText: prevented insertion above the specified limit \n");
            return true;
        }
        return super.commitText(charSequence, i);
    }

    @Override // com.good.gd.dlp_util.EditableInputConnectionImpl, android.view.inputmethod.InputConnection
    public CharSequence getSelectedText(int i) {
        if (!this.gdClipboardUtils.isPasteFromGDAppEnabled()) {
            GDLog.DBGPRINTF(14, "DLPInputConnection getSelectedText: prevent copying data from GDApp \n");
            return "";
        }
        return super.getSelectedText(i);
    }

    @Override // com.good.gd.dlp_util.EditableInputConnectionImpl, android.view.inputmethod.InputConnection
    public boolean setComposingText(CharSequence charSequence, int i) {
        if (!this.gdClipboardUtils.isPasteFromNonGDAppEnabled() && charSequence.length() > 50) {
            GDLog.DBGPRINTF(14, "DLPInputConnection setComposingText: prevented insertion above the specified limit \n");
            return true;
        }
        return super.setComposingText(charSequence, i);
    }
}
