package com.good.gd.ui.utils;

import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

/* loaded from: classes.dex */
public class ScrollEventDispatcher implements ViewTreeObserver.OnScrollChangedListener {
    private ScrollEventListener listener;
    private ScrollView scrollView;

    public ScrollEventDispatcher(ScrollView scrollView) {
        this.scrollView = scrollView;
    }

    private int getScrollableRange() {
        View childAt = this.scrollView.getChildAt(0);
        int paddingBottom = this.scrollView.getPaddingBottom();
        return childAt.getHeight() - ((this.scrollView.getHeight() - paddingBottom) - this.scrollView.getPaddingTop());
    }

    @Override // android.view.ViewTreeObserver.OnScrollChangedListener
    public void onScrollChanged() {
        if (getScrollableRange() == this.scrollView.getScrollY()) {
            this.listener.onBottomReached();
        }
    }

    public void setScrollEventListener(ScrollEventListener scrollEventListener) {
        this.listener = scrollEventListener;
    }
}
