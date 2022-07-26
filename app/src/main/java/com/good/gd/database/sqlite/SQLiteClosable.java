package com.good.gd.database.sqlite;

import com.good.gd.utils.GDSDKState;
import java.io.Closeable;

/* loaded from: classes.dex */
public abstract class SQLiteClosable implements Closeable {
    private int mReferenceCount = 1;

    public void acquireReference() {
        synchronized (this) {
            int i = this.mReferenceCount;
            if (i > 0) {
                this.mReferenceCount = i + 1;
            } else {
                throw new IllegalStateException("attempt to re-open an already-closed object: " + this);
            }
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        if (GDSDKState.getInstance().isWiped()) {
            return;
        }
        releaseReference();
    }

    protected abstract void onAllReferencesReleased();

    @Deprecated
    protected void onAllReferencesReleasedFromContainer() {
        onAllReferencesReleased();
    }

    public void releaseReference() {
        boolean z;
        synchronized (this) {
            z = true;
            int i = this.mReferenceCount - 1;
            this.mReferenceCount = i;
            if (i != 0) {
                z = false;
            }
        }
        if (z) {
            onAllReferencesReleased();
        }
    }

    @Deprecated
    public void releaseReferenceFromContainer() {
        boolean z;
        synchronized (this) {
            z = true;
            int i = this.mReferenceCount - 1;
            this.mReferenceCount = i;
            if (i != 0) {
                z = false;
            }
        }
        if (z) {
            onAllReferencesReleasedFromContainer();
        }
    }
}
