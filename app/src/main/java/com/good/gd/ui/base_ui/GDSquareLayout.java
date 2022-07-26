package com.good.gd.ui.base_ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/* loaded from: classes.dex */
public class GDSquareLayout extends LinearLayout {
    public GDSquareLayout(Context context) {
        super(context);
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i);
    }

    public GDSquareLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public GDSquareLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
