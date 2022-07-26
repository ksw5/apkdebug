package com.good.gd.qvfxc;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/* loaded from: classes.dex */
public class yfdke implements View.OnTouchListener {
    private GestureDetector dbjc;

    /* renamed from: com.good.gd.qvfxc.yfdke$yfdke  reason: collision with other inner class name */
    /* loaded from: classes.dex */
    private final class C0025yfdke extends GestureDetector.SimpleOnGestureListener {
        private C0025yfdke() {
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public boolean onDoubleTap(MotionEvent motionEvent) {
            if (yfdke.this != null) {
                return super.onDoubleTap(motionEvent);
            }
            throw null;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onDown(MotionEvent motionEvent) {
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            try {
                float y = motionEvent2.getY() - motionEvent.getY();
                float x = motionEvent2.getX() - motionEvent.getX();
                if (Math.abs(x) > Math.abs(y)) {
                    if (Math.abs(x) <= 100.0f || Math.abs(f) <= 100.0f) {
                        return false;
                    }
                    if (x > 0.0f) {
                        yfdke.this.dbjc();
                        return false;
                    } else if (yfdke.this == null) {
                        throw null;
                    } else {
                        return false;
                    }
                } else if (Math.abs(y) <= 100.0f || Math.abs(f2) <= 100.0f) {
                    return false;
                } else {
                    if (y > 0.0f) {
                        if (yfdke.this == null) {
                            throw null;
                        }
                        return false;
                    } else if (yfdke.this != null) {
                        return false;
                    } else {
                        throw null;
                    }
                }
            } catch (Exception e) {
                return false;
            }
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public void onLongPress(MotionEvent motionEvent) {
            if (yfdke.this != null) {
                super.onLongPress(motionEvent);
                return;
            }
            throw null;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            if (yfdke.this != null) {
                return super.onSingleTapUp(motionEvent);
            }
            throw null;
        }
    }

    public yfdke(Context context) {
        this.dbjc = new GestureDetector(context, new C0025yfdke());
    }

    public void dbjc() {
        throw null;
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return this.dbjc.onTouchEvent(motionEvent);
    }
}
