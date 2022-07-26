package com.good.gd.ui_control;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/* loaded from: classes.dex */
public class IntentAndContextForStart {
    private Context context;
    private Intent intent;
    private boolean shouldWait;

    public IntentAndContextForStart(Intent intent, Context context, boolean z) {
        this.context = context;
        this.intent = intent;
        this.shouldWait = z;
    }

    public static IntentAndContextForStart from(Intent intent, Context context) {
        return new IntentAndContextForStart(intent, context, false);
    }

    public Context getContext() {
        return this.context;
    }

    public Intent getIntent() {
        return this.intent;
    }

    public PendingIntent getPendingIntent() {
        return getPendingIntent(67108864);
    }

    public boolean shouldWaitInternalActivity() {
        return this.shouldWait;
    }

    public void startActivity() {
        Intent intent;
        Context context = this.context;
        if (context == null || (intent = this.intent) == null) {
            return;
        }
        context.startActivity(intent);
    }

    public PendingIntent getPendingIntent(int i) {
        Intent intent;
        Context context = this.context;
        if (context == null || (intent = this.intent) == null) {
            return null;
        }
        return PendingIntent.getActivity(context, 0, intent, i);
    }
}
