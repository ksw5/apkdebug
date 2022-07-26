package com.good.gd.ui.dialogs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

/* loaded from: classes.dex */
public class AutoWrapButtonLayout extends RelativeLayout {
    public AutoWrapButtonLayout(Context context) {
        super(context);
    }

    @Override // android.widget.RelativeLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (getWidth() > 0) {
            int childCount = getChildCount();
            float f = 0.0f;
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt = getChildAt(i3);
                if (childAt instanceof Button) {
                    Button button = (Button) childAt;
                    f += button.getPaint().measureText(button.getText().toString());
                }
            }
            if (f <= getWidth() * 0.8d) {
                return;
            }
            for (int i4 = 0; i4 < childCount; i4++) {
                View childAt2 = getChildAt(i4);
                if (childAt2 instanceof Button) {
                    Button button2 = (Button) childAt2;
                    String charSequence = button2.getText().toString();
                    if (!charSequence.contains("\n")) {
                        button2.setText(charSequence.replaceFirst(" ", "\n"));
                    }
                }
            }
            super.onMeasure(i, i2);
        }
    }

    public AutoWrapButtonLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public AutoWrapButtonLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
