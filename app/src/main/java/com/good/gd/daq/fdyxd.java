package com.good.gd.daq;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* loaded from: classes.dex */
public class fdyxd extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String action;
        if (intent == null || (action = intent.getAction()) == null || !action.equals("android.location.MODE_CHANGED")) {
            return;
        }
        com.good.gd.idl.hbfhc.pqq().sbesx();
        hbfhc.dbjc();
        if (com.blackberry.bis.core.yfdke.muee() == null) {
            throw null;
        }
    }
}
