package com.good.gd.interception;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.good.gd.GDDialog;
import com.good.gd.GDLocalBroadcastManager;
import com.good.gd.interception.IntentData;
import com.good.gd.interception.ServiceAction;
import com.good.gd.ndkproxy.GDLog;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/* loaded from: classes.dex */
public class ChooserAction implements ServiceAction<IntentData.PackageInfo> {
    private static final String LIST_ITEM_CANCELLED_BOOL_KEY = "listItemCancelldKey";
    private static final String LIST_ITEM_INDEX_INT_KEY = "listItemIndexKey";
    private static final String LIST_ITEM_SELECTED_ACTION = "listItemSelectedAction";
    private static final String LIST_ITEM_TEXT_KEY = "listItemNameKey";
    private static final String TAG = "ChooserAction";
    private final GDDialog gdDialog;
    private final List<IntentData.PackageInfo> packagesInfo;

    /* loaded from: classes.dex */
    class hbfhc implements Callback<IntentData.PackageInfo> {
        hbfhc(ChooserAction chooserAction) {
        }

        @Override // com.good.gd.interception.ServiceAction.Callback
        public void done(IntentData.PackageInfo packageInfo, Exception exc) {
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class yfdke extends BroadcastReceiver {
        final /* synthetic */ Callback dbjc;

        yfdke(Callback callback) {
            this.dbjc = callback;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent == null || !"listItemSelectedAction".equals(intent.getAction())) {
                return;
            }
            GDLog.DBGPRINTF(16, "intent received");
            GDLocalBroadcastManager.getInstance().unregisterReceiver(this);
            if (intent.getBooleanExtra("listItemCancelldKey", false)) {
                this.dbjc.done(null, new Exception("User cancelled"));
                return;
            }
            int intExtra = intent.getIntExtra("listItemIndexKey", -1);
            if (intExtra >= 0 && intExtra <= ChooserAction.this.packagesInfo.size() - 1) {
                this.dbjc.done(ChooserAction.this.packagesInfo.get(intExtra), null);
            } else {
                this.dbjc.done(null, new Exception(String.format(Locale.ENGLISH, "Selected position(%d) is invalid", Integer.valueOf(intExtra))));
            }
        }
    }

    public ChooserAction(List<? extends IntentData.PackageInfo> list, GDDialog gDDialog) {
        this.packagesInfo = Collections.unmodifiableList(list);
        this.gdDialog = gDDialog;
    }

    private List<String> getViewData(Context context) {
        ArrayList arrayList = new ArrayList(this.packagesInfo.size());
        for (IntentData.PackageInfo packageInfo : this.packagesInfo) {
            arrayList.add(packageInfo.packageName);
        }
        return Utils.getAppReadableNames(arrayList, context);
    }

    private void subscribeForListItemSelectedAction(Callback<IntentData.PackageInfo> callback, Context context) {
        GDLocalBroadcastManager.getInstance().registerReceiver(new yfdke(callback), new IntentFilter("listItemSelectedAction"));
    }

    public void execute(Context context) throws Exception {
    }

    @Override // com.good.gd.interception.ServiceAction
    public void execute(Callback<IntentData.PackageInfo> callback, Context context) {
        if (callback == null) {
            callback = new hbfhc(this);
        }
        subscribeForListItemSelectedAction(callback, context);
        this.gdDialog.show(getViewData(context), context);
    }
}
