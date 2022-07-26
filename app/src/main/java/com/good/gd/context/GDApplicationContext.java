package com.good.gd.context;

import android.content.Context;
import android.os.Handler;
import com.good.gd.ApplicationContext;
import com.good.gd.client.GDClient;

/* loaded from: classes.dex */
public class GDApplicationContext implements ApplicationContext {
    @Override // com.good.gd.ApplicationContext
    public Context getApplicationContext() {
        return GDContext.getInstance().getApplicationContext();
    }

    @Override // com.good.gd.ApplicationContext
    public Handler getClientHandler() {
        return GDClient.getInstance().getClientHandler();
    }
}
