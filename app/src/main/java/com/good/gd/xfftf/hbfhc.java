package com.good.gd.xfftf;

import android.content.Context;
import android.view.View;
import com.good.gd.qvfxc.yfdke;
import com.good.gd.ui.utils.sis.SwipeRightListener;
import com.good.gd.ui.utils.sis.TouchListenersProvider;

/* loaded from: classes.dex */
public class hbfhc implements TouchListenersProvider {

    /* renamed from: com.good.gd.xfftf.hbfhc$hbfhc  reason: collision with other inner class name */
    /* loaded from: classes.dex */
    class C0034hbfhc extends yfdke {
        final /* synthetic */ SwipeRightListener qkduk;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C0034hbfhc(hbfhc hbfhcVar, Context context, SwipeRightListener swipeRightListener) {
            super(context);
            this.qkduk = swipeRightListener;
        }

        @Override // com.good.gd.qvfxc.yfdke
        public void dbjc() {
            SwipeRightListener swipeRightListener = this.qkduk;
            if (swipeRightListener != null) {
                swipeRightListener.onSwipeRight();
            }
        }
    }

    @Override // com.good.gd.ui.utils.sis.TouchListenersProvider
    public View.OnTouchListener getOnTouchListenerForSwipeRight(Context context, SwipeRightListener swipeRightListener) {
        return new C0034hbfhc(this, context, swipeRightListener);
    }
}
