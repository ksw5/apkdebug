package com.good.gd.dlp_util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/* loaded from: classes.dex */
public class DragAndDropUtils {
    public static View.DragShadowBuilder getSecuredTextThumbnailBuilder(Context context) {
        View inflate = View.inflate(context, context.getResources().getIdentifier("bbd_secure_dragdrop", "layout", context.getPackageName()), null);
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        inflate.measure(makeMeasureSpec, makeMeasureSpec);
        inflate.layout(0, 0, inflate.getMeasuredWidth(), inflate.getMeasuredHeight());
        inflate.invalidate();
        return new View.DragShadowBuilder(inflate);
    }

    public static View.DragShadowBuilder getTextThumbnailBuilder(View view, String str) {
        Context context = view.getContext();
        TextView textView = (TextView) View.inflate(context, context.getResources().getIdentifier("bbd_text_dragdrop", "layout", context.getPackageName()), null);
        textView.setText(str);
        if (view instanceof TextView) {
            textView.setTextColor(((TextView) view).getTextColors());
        }
        textView.setGravity(17);
        textView.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        textView.measure(makeMeasureSpec, makeMeasureSpec);
        textView.layout(0, 0, textView.getMeasuredWidth(), textView.getMeasuredHeight());
        textView.invalidate();
        return new View.DragShadowBuilder(textView);
    }
}
