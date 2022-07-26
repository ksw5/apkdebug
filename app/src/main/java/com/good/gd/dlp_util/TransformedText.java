package com.good.gd.dlp_util;

import android.text.method.TransformationMethod;
import android.widget.TextView;

/* loaded from: classes.dex */
public class TransformedText {
    private CharSequence getTransformedText(TextView textView) {
        TransformationMethod transformationMethod = textView.getTransformationMethod();
        if (transformationMethod != null) {
            return transformationMethod.getTransformation(textView.getText(), textView);
        }
        return textView.getText();
    }

    public CharSequence getTransformed(TextView textView) {
        return getTransformedText(textView);
    }
}
