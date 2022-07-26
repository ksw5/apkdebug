package com.good.gd.database.sqlite;

import android.os.OperationCanceledException;

/* loaded from: classes.dex */
public final class CancellationSignal {
    private boolean mCancelInProgress;
    private boolean mIsCanceled;
    private OnCancelListener mOnCancelListener;

    /* loaded from: classes.dex */
    public interface OnCancelListener {
        void onCancel();
    }

    private void waitForCancelFinishedLocked() {
        while (this.mCancelInProgress) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
    }

    public void cancel() {
        synchronized (this) {
            if (this.mIsCanceled) {
                return;
            }
            this.mIsCanceled = true;
            this.mCancelInProgress = true;
            OnCancelListener onCancelListener = this.mOnCancelListener;
            if (onCancelListener != null) {
                try {
                    onCancelListener.onCancel();
                } catch (Throwable th) {
                    synchronized (this) {
                        this.mCancelInProgress = false;
                        notifyAll();
                        throw th;
                    }
                }
            }
            synchronized (this) {
                this.mCancelInProgress = false;
                notifyAll();
            }
        }
    }

    public boolean isCanceled() {
        boolean z;
        synchronized (this) {
            z = this.mIsCanceled;
        }
        return z;
    }

    public void setOnCancelListener(OnCancelListener onCancelListener) {
        synchronized (this) {
            waitForCancelFinishedLocked();
            if (this.mOnCancelListener == onCancelListener) {
                return;
            }
            this.mOnCancelListener = onCancelListener;
            if (this.mIsCanceled && onCancelListener != null) {
                onCancelListener.onCancel();
            }
        }
    }

    public void throwIfCanceled() {
        if (!isCanceled()) {
            return;
        }
        throw new OperationCanceledException();
    }
}
