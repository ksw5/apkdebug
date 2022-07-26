package com.good.gd.service.interception;

import android.content.Intent;
import com.good.gd.GDAndroid;
import com.good.gd.context.GDContext;
import com.good.gd.icc.GDServiceException;
import com.good.gd.icc.impl.GDServiceHelperInterface;
import com.good.gd.interception.ChooserAction;
import com.good.gd.interception.ErrorAction;
import com.good.gd.interception.IntentData;
import com.good.gd.interception.IntentParser;
import com.good.gd.interception.ServiceAction;
import com.good.gd.interception.StartActivtyAction;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ui.dialogs.GDDialogActivity;
import com.good.gd.utils.GDLocalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

/* loaded from: classes.dex */
public class GDServiceHelperImpl implements GDServiceHelperInterface {
    private static final String TAG = "GDServiceHelperImpl";

    /* loaded from: classes.dex */
    class hbfhc implements ServiceAction.Callback<IntentData.PackageInfo> {
        private IntentData dbjc;

        public hbfhc(IntentData intentData) {
            this.dbjc = intentData;
        }

        @Override // com.good.gd.interception.ServiceAction.Callback
        public void done(IntentData.PackageInfo packageInfo, Exception exc) {
            IntentData.PackageInfo packageInfo2 = packageInfo;
            if (exc == null) {
                GDLog.DBGPRINTF(16, "done : " + packageInfo2.packageName);
                if (packageInfo2.isGD()) {
                    try {
                        IccAction iccAction = GDServiceHelperImpl.this.getIccAction((IntentData.GDServiceProviderInfo) packageInfo2, this.dbjc.iccData);
                        GDContext.getInstance().getApplicationContext();
                        iccAction.dbjc();
                        return;
                    } catch (GDServiceException e) {
                        GDLog.DBGPRINTF(12, "getChooserAction -> done, GDServiceException : " + e.getMessage());
                        return;
                    }
                }
                try {
                    GDServiceHelperImpl.this.getSystemBehaviourAction((IntentData.ApplicationInfo) packageInfo2, this.dbjc.applicationData).execute(GDContext.getInstance().getApplicationContext());
                    return;
                } catch (Exception e2) {
                    GDLog.DBGPRINTF(12, "getSystemBehaviourAction -> done, Exception : " + e2.getMessage());
                    return;
                }
            }
            GDLog.DBGPRINTF(12, "getChooserAction -> done, error: " + exc.getMessage());
        }
    }

    private boolean canConsume(IntentData intentData) {
        return (intentData == null || intentData.serviceInfo == null || (intentData.gdServiceProvidersInfo.isEmpty() && (intentData.applicationsInfo.isEmpty() || isDLPEnabled(intentData)))) ? false : true;
    }

    private ServiceAction<IntentData.PackageInfo> getChooserAction(List<? extends IntentData.PackageInfo> list) {
        return new ChooserAction(list, GDDialogActivity.GDDialogImpl.newInstance());
    }

    private ErrorAction getErrorAction() {
        return new ErrorAction(GDLocalizer.getLocalizedString("No secure application"), GDDialogActivity.GDDialogImpl.newInstance());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public IccAction getIccAction(IntentData.GDServiceProviderInfo gDServiceProviderInfo, IntentData.IccData iccData) {
        return new IccAction(gDServiceProviderInfo, iccData);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public StartActivtyAction getSystemBehaviourAction(IntentData.ApplicationInfo applicationInfo, IntentData.ApplicationData applicationData) {
        return new StartActivtyAction(applicationInfo, applicationData);
    }

    private List<IntentData.PackageInfo> getUniquePackagesInfo(List<IntentData.GDServiceProviderInfo> list, List<IntentData.ApplicationInfo> list2) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (IntentData.GDServiceProviderInfo gDServiceProviderInfo : list) {
            linkedHashMap.put(gDServiceProviderInfo.packageName, gDServiceProviderInfo);
        }
        for (IntentData.ApplicationInfo applicationInfo : list2) {
            if (!linkedHashMap.containsKey(applicationInfo.packageName)) {
                linkedHashMap.put(applicationInfo.packageName, applicationInfo);
            }
        }
        return Collections.unmodifiableList(new ArrayList(linkedHashMap.values()));
    }

    private boolean isDLPEnabled(IntentData intentData) {
        if (!intentData.serviceInfo.allowNonGD) {
            return true;
        }
        return ((Boolean) GDAndroid.getInstance().getApplicationConfig().get("copyPasteOn")).booleanValue();
    }

    public static GDServiceHelperInterface newInstance() {
        return new GDServiceHelperImpl();
    }

    @Override // com.good.gd.icc.impl.GDServiceHelperInterface
    public boolean GDConsume(Intent intent) throws GDServiceException {
        IntentData parse = IntentParser.create(intent, new ServiceProvidersImpl(), GDContext.getInstance().getApplicationContext()).parse();
        if (canConsume(parse)) {
            if (isDLPEnabled(parse)) {
                if (parse.gdServiceProvidersInfo.size() == 1) {
                    IccAction iccAction = getIccAction(parse.gdServiceProvidersInfo.get(0), parse.iccData);
                    GDContext.getInstance().getApplicationContext();
                    iccAction.dbjc();
                } else {
                    getChooserAction(parse.gdServiceProvidersInfo).execute(new hbfhc(parse), GDContext.getInstance().getApplicationContext());
                }
            } else {
                List<IntentData.PackageInfo> uniquePackagesInfo = getUniquePackagesInfo(parse.gdServiceProvidersInfo, parse.applicationsInfo);
                if (uniquePackagesInfo.size() == 1) {
                    IntentData.PackageInfo packageInfo = uniquePackagesInfo.get(0);
                    if (packageInfo.isGD()) {
                        IccAction iccAction2 = getIccAction((IntentData.GDServiceProviderInfo) packageInfo, parse.iccData);
                        GDContext.getInstance().getApplicationContext();
                        iccAction2.dbjc();
                    } else {
                        getSystemBehaviourAction((IntentData.ApplicationInfo) packageInfo, parse.applicationData).execute(GDContext.getInstance().getApplicationContext());
                    }
                } else {
                    getChooserAction(uniquePackagesInfo).execute(new hbfhc(parse), GDContext.getInstance().getApplicationContext());
                }
            }
            return true;
        }
        getErrorAction().execute(GDContext.getInstance().getApplicationContext());
        return false;
    }

    @Override // com.good.gd.icc.impl.GDServiceHelperInterface
    public boolean canGDConsume(Intent intent) {
        return canConsume(IntentParser.create(intent, new ServiceProvidersImpl(), GDContext.getInstance().getApplicationContext()).parse());
    }
}
