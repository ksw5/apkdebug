package com.good.gd.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.autofill.AutofillValue;
import android.view.textclassifier.TextClassifier;
import android.widget.TextView;
import com.good.gd.content_Impl.GDClipboardUtils;
import com.good.gd.dlp_util.GDCustomROMDLPUtil;
import com.good.gd.widget.GDDLPWidgetUtil;

/* loaded from: classes.dex */
public class GDTextView extends TextView {
    private GDDLPWidgetUtil gdDLPWidgetUtil;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class hbfhc implements GDDLPWidgetUtil.aqdzk {
        hbfhc() {
        }

        @Override // com.good.gd.widget.GDDLPWidgetUtil.aqdzk
        public void dbjc(OnLongClickListener onLongClickListener) {
            GDTextView.this.setPrivateLongClickListenerImpl(onLongClickListener);
        }
    }

    public GDTextView(Context context) {
        super(context);
        GDViewSetup();
    }

    private void GDViewSetup() {
        this.gdDLPWidgetUtil = new GDDLPWidgetUtil(this, new hbfhc());
        GDDLPKeyboardControl.getInstance().configureDLPKeyboardOptions(this);
        this.gdDLPWidgetUtil.dbjc();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPrivateLongClickListenerImpl(OnLongClickListener onLongClickListener) {
        super.setOnLongClickListener(onLongClickListener);
    }

    @Override // android.view.View
    public final void autofill(SparseArray<AutofillValue> sparseArray) {
    }

    @Override // android.widget.TextView, android.view.View
    public final void autofill(AutofillValue autofillValue) {
    }

    @Override // android.widget.TextView, android.view.View
    public final int getAutofillType() {
        return 0;
    }

    @Override // android.widget.TextView, android.view.View
    public final AutofillValue getAutofillValue() {
        return null;
    }

    @Override // android.view.View
    public final int getImportantForAutofill() {
        return 8;
    }

    @Override // android.widget.TextView
    public final TextClassifier getTextClassifier() {
        if (GDClipboardUtils.getInstance(getContext()).isGDSecureClipBoardEnabled()) {
            setTextClassifier(TextClassifier.NO_OP);
        } else {
            setTextClassifier(null);
        }
        return super.getTextClassifier();
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onDragEvent(DragEvent dragEvent) {
        GDDLPWidgetUtil gDDLPWidgetUtil = this.gdDLPWidgetUtil;
        if (gDDLPWidgetUtil != null) {
            int ordinal = gDDLPWidgetUtil.dbjc(dragEvent).ordinal();
            if (ordinal == 0) {
                return false;
            }
            if (ordinal == 1) {
                return true;
            }
            if (ordinal == 2) {
                return super.onDragEvent(dragEvent);
            }
        }
        return super.onDragEvent(dragEvent);
    }

    @Override // android.widget.TextView
    public boolean onTextContextMenuItem(int i) {
        GDDLPWidgetUtil gDDLPWidgetUtil = this.gdDLPWidgetUtil;
        if (gDDLPWidgetUtil != null) {
            return gDDLPWidgetUtil.dbjc(i);
        }
        return false;
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int ordinal = this.gdDLPWidgetUtil.dbjc(motionEvent).ordinal();
        if (ordinal != 0) {
            if (ordinal == 1) {
                return true;
            }
            if (ordinal != 2) {
                return super.onTouchEvent(motionEvent);
            }
            return super.onTouchEvent(motionEvent);
        }
        return false;
    }

    @Override // android.widget.TextView, android.view.View
    protected void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        GDDLPWidgetUtil gDDLPWidgetUtil = this.gdDLPWidgetUtil;
        if (gDDLPWidgetUtil != null) {
            gDDLPWidgetUtil.qkduk(i);
        }
    }

    @Override // android.view.View
    public void setOnLongClickListener(OnLongClickListener onLongClickListener) {
        GDDLPWidgetUtil gDDLPWidgetUtil = this.gdDLPWidgetUtil;
        if (gDDLPWidgetUtil != null) {
            gDDLPWidgetUtil.dbjc(onLongClickListener);
        }
    }

    @Override // android.widget.TextView
    public final void setTextClassifier(TextClassifier textClassifier) {
        super.setTextClassifier(textClassifier);
    }

    @Override // android.widget.TextView
    public void setTextIsSelectable(boolean z) {
        GDDLPWidgetUtil gDDLPWidgetUtil = this.gdDLPWidgetUtil;
        if (gDDLPWidgetUtil == null) {
            super.setTextIsSelectable(z);
        } else if (gDDLPWidgetUtil != null) {
            if (!GDCustomROMDLPUtil.shouldExplicitlyBlockAllDlpActions()) {
                super.setTextIsSelectable(z);
            } else {
                super.setTextIsSelectable(false);
            }
        } else {
            throw null;
        }
    }

    public GDTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        GDViewSetup();
    }

    public GDTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        GDViewSetup();
    }

    public GDTextView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        GDViewSetup();
    }
}
