package com.good.gd.ui.utils;

import android.os.Bundle;
import android.text.Editable;
import android.widget.EditText;
import com.good.gd.ndkproxy.GDLog;

/* loaded from: classes.dex */
public class EditorState {
    private static final String SEL_END_KEY = "selectionEndKey";
    private static final String SEL_START_KEY = "selectionStartKey";

    public void restore(EditText editText, Bundle bundle) {
        if (bundle != null && editText != null) {
            int i = bundle.getInt(SEL_START_KEY, -1);
            int i2 = bundle.getInt(SEL_END_KEY, -1);
            if (i != -1 && i2 != -1) {
                Editable text = editText.getText();
                if (text != null && text.length() >= i2) {
                    editText.setSelection(i, i2);
                    return;
                } else {
                    GDLog.DBGPRINTF(13, "restoreCursorState null text");
                    return;
                }
            }
            GDLog.DBGPRINTF(13, "restoreCursorState noSelection");
            return;
        }
        GDLog.DBGPRINTF(13, "restoreCursorState null");
    }

    public void save(EditText editText, Bundle bundle) {
        if (editText != null && bundle != null) {
            bundle.putInt(SEL_START_KEY, editText.getSelectionStart());
            bundle.putInt(SEL_END_KEY, editText.getSelectionEnd());
            return;
        }
        GDLog.DBGPRINTF(13, "saveCursorState null");
    }
}
