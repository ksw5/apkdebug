package com.good.gd.ui.base_ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/* loaded from: classes.dex */
public class GDScrollView extends ScrollView {

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class hbfhc implements OnTouchListener {
        hbfhc(GDScrollView gDScrollView) {
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return true;
        }
    }

    public GDScrollView(Context context) {
        super(context);
        init();
    }

    private void init() {
        setVerticalFadingEdgeEnabled(false);
        setVerticalScrollBarEnabled(false);
        setOnTouchListener(new hbfhc(this));
    }

    @Override // android.widget.ScrollView, android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case 19:
            case 20:
            case 21:
            case 22:
                return false;
            default:
                return super.dispatchKeyEvent(keyEvent);
        }
    }

    @Override // android.widget.ScrollView, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() != 0) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        return false;
    }

    public GDScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }
}
