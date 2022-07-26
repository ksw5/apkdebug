package com.good.gd.interception;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import com.good.gd.GDServiceDetail;
import com.good.gd.GDServiceProvider;
import com.good.gd.interception.IntentData;
import com.good.gd.utils.ServiceProvidersHelper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class IntentParser {
    public static final String URL_KEY = "url";
    private Context context;
    private Intent intent;
    private ServiceProvidersHelper serviceProvidersHelper;

    protected IntentParser() {
    }

    public static IntentParser create(Intent intent, ServiceProvidersHelper serviceProvidersHelper, Context context) {
        IntentParser intentParser = new IntentParser();
        intentParser.intent = intent;
        intentParser.context = context;
        intentParser.serviceProvidersHelper = serviceProvidersHelper;
        return intentParser;
    }

    protected List<IntentData.ApplicationInfo> findApplications() {
        List<ResolveInfo> activitiesInfo = Utils.getActivitiesInfo(this.intent, this.context);
        ArrayList arrayList = new ArrayList(activitiesInfo.size());
        for (ResolveInfo resolveInfo : activitiesInfo) {
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            arrayList.add(new IntentData.ApplicationInfo(activityInfo.packageName, activityInfo.name));
        }
        return arrayList;
    }

    protected IntentData.ServiceInfo findServiceInfo(IntentData.IccData iccData) {
        Object obj = iccData.params;
        if (obj instanceof String) {
            return hbfhc.dbjc().dbjc((String) iccData.params);
        }
        if (obj instanceof Map) {
            Object obj2 = ((Map) obj).get("url");
            if (obj2 instanceof String) {
                return hbfhc.dbjc().dbjc((String) obj2);
            }
        }
        return null;
    }

    protected List<IntentData.GDServiceProviderInfo> findServiceProviders(IntentData.ServiceInfo serviceInfo) {
        List<GDServiceProvider> serviceProvidersFor;
        GDServiceDetail gDServiceDetail = serviceInfo.gdServiceDetail;
        if (gDServiceDetail != null && (serviceProvidersFor = this.serviceProvidersHelper.getServiceProvidersFor(gDServiceDetail.getIdentifier(), gDServiceDetail.getVersion(), gDServiceDetail.getServiceType())) != null) {
            ArrayList arrayList = new ArrayList();
            String[] strArr = serviceInfo.priorityApplications;
            if (strArr != null) {
                for (String str : strArr) {
                    for (GDServiceProvider gDServiceProvider : serviceProvidersFor) {
                        if (str.equals(gDServiceProvider.getIdentifier())) {
                            arrayList.add(new IntentData.GDServiceProviderInfo(serviceInfo, gDServiceProvider.getAddress()));
                            return arrayList;
                        }
                    }
                }
            }
            for (GDServiceProvider gDServiceProvider2 : serviceProvidersFor) {
                arrayList.add(new IntentData.GDServiceProviderInfo(serviceInfo, gDServiceProvider2.getAddress()));
            }
            return arrayList;
        }
        return Collections.emptyList();
    }

    public IntentData parse() {
        IntentData.IccData parseIccData = parseIccData();
        if (parseIccData != null) {
            IntentData.IntentDataBuilder intentDataBuilder = new IntentData.IntentDataBuilder();
            intentDataBuilder.setIccData(parseIccData);
            IntentData.ServiceInfo findServiceInfo = findServiceInfo(parseIccData);
            if (findServiceInfo != null) {
                intentDataBuilder.setGdServiceProvidersInfo(findServiceProviders(findServiceInfo));
                intentDataBuilder.setServiceInfo(findServiceInfo);
            }
            intentDataBuilder.setApplicationsInfo(findApplications());
            intentDataBuilder.setApplicationData(new IntentData.ApplicationData(this.intent));
            return intentDataBuilder.createIntentData();
        }
        return null;
    }

    protected IntentData.IccData parseIccData() {
        Intent intent = this.intent;
        if (intent == null || !"android.intent.action.VIEW".equals(intent.getAction()) || this.intent.getDataString() == null) {
            return null;
        }
        HashMap hashMap = new HashMap();
        hashMap.put("url", this.intent.getDataString());
        return new IntentData.IccData(hashMap, null);
    }
}
