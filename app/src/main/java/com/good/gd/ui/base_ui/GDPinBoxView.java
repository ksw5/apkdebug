package com.good.gd.ui.base_ui;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import com.good.gd.utils.DebuggableChecker;
import com.good.gd.widget.GDEditText;

/* loaded from: classes.dex */
public class GDPinBoxView extends GDEditText {
    private static final int EMPTY_PIN_BOX = 0;
    private static final int MAXIMUM_PIN_BOX_SIZE = 6;
    public static final int PIN_PART_LENGTH = 5;
    private OnDeleteKeyEventListener onDeleteKeyEventListener;
    private boolean editedProgrammatically = false;
    private final DebuggableChecker debuggableChecker = GDDebuggableCheckerHolder.getDebuggableChecker();

    /* loaded from: classes.dex */
    private interface OnDeleteKeyEventListener {
        void dbjc();
    }

    /* loaded from: classes.dex */
    private class yfdke implements TextWatcher, OnDeleteKeyEventListener {
        private final GDPinBoxView dbjc;
        private final GDPinBoxView jwxax;
        private final GDPinBoxView qkduk;
        private final GDPinBoxEventListener wxau;
        private boolean ztwf;

        /* JADX INFO: Access modifiers changed from: package-private */
        /* loaded from: classes.dex */
        public class hbfhc implements Runnable {
            final /* synthetic */ int dbjc;

            hbfhc(int i) {
                this.dbjc = i;
            }

            @Override // java.lang.Runnable
            public void run() {
                yfdke yfdkeVar = yfdke.this;
                yfdkeVar.dbjc(yfdkeVar.jwxax, this.dbjc);
            }
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            this.wxau.afterTextChanged();
        }

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // com.good.gd.ui.base_ui.GDPinBoxView.OnDeleteKeyEventListener
        public void dbjc() {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            GDPinBoxView gDPinBoxView;
            if (this.ztwf) {
                return;
            }
            if (i3 > 1 && !GDPinBoxView.this.getEditedProgrammatically() && !GDPinBoxView.this.debuggableChecker.isApplicationDebuggable()) {
                this.qkduk.getText().replace(i, i3 + i, "");
                return;
            }
            this.wxau.onTextChanged();
            int length = charSequence.length();
            if (length == 0) {
                if (i != 0 || i2 != 1 || (gDPinBoxView = this.qkduk) == this.dbjc) {
                    return;
                }
                gDPinBoxView.setVisibility(8);
                GDPinBoxView gDPinBoxView2 = this.dbjc;
                dbjc(gDPinBoxView2, gDPinBoxView2.length());
            } else if (length == 5) {
                dbjc(0);
            } else if (length != 6) {
            } else {
                int i4 = i + 1;
                if (i4 == 6) {
                    char charAt = charSequence.charAt(5);
                    StringBuilder sb = new StringBuilder(charSequence);
                    sb.replace(i, i + 2, "");
                    String sb2 = sb.toString();
                    int length2 = sb.length();
                    this.ztwf = true;
                    this.qkduk.setText(sb2);
                    this.ztwf = false;
                    this.qkduk.setSelection(length2);
                    GDPinBoxView gDPinBoxView3 = this.qkduk;
                    GDPinBoxView gDPinBoxView4 = this.jwxax;
                    if (gDPinBoxView3 == gDPinBoxView4) {
                        return;
                    }
                    if (TextUtils.isEmpty(gDPinBoxView4.getText())) {
                        this.jwxax.getText().replace(0, 0, "" + charAt);
                    } else {
                        this.jwxax.getText().replace(0, 1, "" + charAt);
                    }
                    dbjc(1);
                    return;
                }
                this.ztwf = true;
                this.qkduk.getText().replace(i, i + 2, "" + ("" + charSequence.charAt(i)));
                this.ztwf = false;
                this.qkduk.setSelection(i4);
                if (i4 != 5) {
                    return;
                }
                dbjc(0);
            }
        }

        private yfdke(GDPinBoxView gDPinBoxView, GDPinBoxView gDPinBoxView2, GDPinBoxView gDPinBoxView3, GDPinBoxEventListener gDPinBoxEventListener) {
            this.dbjc = gDPinBoxView;
            this.qkduk = gDPinBoxView2;
            this.jwxax = gDPinBoxView3;
            this.wxau = gDPinBoxEventListener;
        }

        private void dbjc(int i) {
            GDPinBoxView gDPinBoxView = this.jwxax;
            GDPinBoxView gDPinBoxView2 = this.qkduk;
            if (gDPinBoxView != gDPinBoxView2 && gDPinBoxView2.getText().length() == 5) {
                if (this.qkduk.getSelectionEnd() != 5 && !GDPinBoxView.this.debuggableChecker.isApplicationDebuggable()) {
                    return;
                }
                this.jwxax.setVisibility(0);
                GDPinBoxView.this.post(new hbfhc(i));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dbjc(GDPinBoxView gDPinBoxView, int i) {
            if (!gDPinBoxView.isFocusableInTouchMode()) {
                gDPinBoxView.setFocusableInTouchMode(true);
                gDPinBoxView.requestFocus();
                gDPinBoxView.setFocusableInTouchMode(false);
                return;
            }
            gDPinBoxView.requestFocus();
            gDPinBoxView.setSelection(i);
        }
    }

    public GDPinBoxView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getAction() == 0 && keyEvent.getKeyCode() == 67) {
            this.onDeleteKeyEventListener.dbjc();
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    public boolean getEditedProgrammatically() {
        return this.editedProgrammatically;
    }

    public void setEditedProgrammatically(boolean z) {
        this.editedProgrammatically = z;
    }

    public void setOnPinBoxEventListener(GDPinBoxView gDPinBoxView, GDPinBoxView gDPinBoxView2, GDPinBoxView gDPinBoxView3, GDPinBoxEventListener gDPinBoxEventListener) {
        yfdke yfdkeVar = new yfdke(gDPinBoxView, gDPinBoxView2, gDPinBoxView3, gDPinBoxEventListener);
        this.onDeleteKeyEventListener = yfdkeVar;
        addTextChangedListener(yfdkeVar);
    }
}
