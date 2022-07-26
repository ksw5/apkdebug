package com.good.gd.interception;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.good.gd.database.sqlite.SQLiteDatabase;
import com.good.gd.interception.IntentData;
import com.good.gd.interception.ServiceAction;

/* loaded from: classes.dex */
public class StartActivtyAction implements ServiceAction<Void> {
    final IntentData.ApplicationData appData;
    final IntentData.ApplicationInfo appInfo;

    public StartActivtyAction(IntentData.ApplicationInfo applicationInfo, IntentData.ApplicationData applicationData) {
        this.appInfo = applicationInfo;
        this.appData = applicationData;
    }

    public void execute(Context context) {
        Intent intent = this.appData.intent;
        IntentData.ApplicationInfo applicationInfo = this.appInfo;
        intent.setComponent(new ComponentName(applicationInfo.packageName, applicationInfo.activityName));
        intent.addFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
        context.startActivity(intent);
    }

    @Override // com.good.gd.interception.ServiceAction
    public void execute(Callback<Void> callback, Context context) {
        throw new UnsupportedOperationException();
    }
}
