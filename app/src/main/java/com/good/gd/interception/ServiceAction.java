package com.good.gd.interception;

import android.content.Context;

/* loaded from: classes.dex */
public interface ServiceAction<T> {

    /* loaded from: classes.dex */
    public interface Callback<T> {
        void done(T t, Exception exc);
    }

    void execute(Callback<T> callback, Context context);
}
