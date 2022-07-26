package com.good.gd.widget;

import android.content.Context;
import android.view.inputmethod.InputConnection;
import android.widget.TextView;
import com.good.gd.dlp_util.DLPInputConnection;

/* loaded from: classes.dex */
public class GDDLPKeyboardControl {
    private static final int FLAG_NO_PERSONALIZED_LEARNING = 16777216;
    private static final String GD_NM = "nm";
    private static GDDLPKeyboardControl _instance;
    private DLPSettings settings = new DLPSettings();

    /* loaded from: classes.dex */
    public static class DLPSettings {
        public boolean isKeyboardDLPOn = true;
        public boolean isIncognitoModeOn = false;
    }

    private GDDLPKeyboardControl() {
    }

    public static GDDLPKeyboardControl getInstance() {
        if (_instance == null) {
            _instance = new GDDLPKeyboardControl();
        }
        return _instance;
    }

    public void configureDLPKeyboardOptions(TextView textView) {
        int imeOptions = textView.getImeOptions();
        if (this.settings.isIncognitoModeOn) {
            textView.setImeOptions(imeOptions | FLAG_NO_PERSONALIZED_LEARNING);
        } else {
            int i = FLAG_NO_PERSONALIZED_LEARNING;
            if ((imeOptions & i) != 0) {
                imeOptions ^= i;
            }
            textView.setImeOptions(imeOptions);
        }
        if (this.settings.isKeyboardDLPOn) {
            textView.setPrivateImeOptions(GD_NM);
        } else {
            textView.setPrivateImeOptions(null);
        }
    }

    public InputConnection configureInputConnection(InputConnection inputConnection, Context context) {
        return inputConnection != null ? new DLPInputConnection(inputConnection, context) : inputConnection;
    }

    public boolean isDLPIncognitoModeOn() {
        return this.settings.isIncognitoModeOn;
    }

    public boolean isDLPKeyboardOn() {
        return this.settings.isKeyboardDLPOn;
    }

    public void updateDLPKeyboardControl(DLPSettings dLPSettings) {
        this.settings = dLPSettings;
    }
}
