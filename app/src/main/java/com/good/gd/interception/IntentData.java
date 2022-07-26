package com.good.gd.interception;

import android.content.Intent;
import com.good.gd.GDServiceDetail;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public class IntentData {
    public final ApplicationData applicationData;
    public final List<ApplicationInfo> applicationsInfo;
    public final List<GDServiceProviderInfo> gdServiceProvidersInfo;
    public final IccData iccData;
    public final ServiceInfo serviceInfo;

    /* loaded from: classes.dex */
    public static class ApplicationData {
        public final Intent intent;

        /* JADX INFO: Access modifiers changed from: package-private */
        public ApplicationData(Intent intent) {
            this.intent = intent;
        }
    }

    /* loaded from: classes.dex */
    public static class ApplicationInfo extends PackageInfo {
        public final String activityName;

        /* JADX INFO: Access modifiers changed from: package-private */
        public ApplicationInfo(String str, String str2) {
            super(str);
            this.activityName = str2;
        }

        @Override // com.good.gd.interception.IntentData.PackageInfo
        public boolean isGD() {
            return false;
        }
    }

    /* loaded from: classes.dex */
    public static class GDServiceProviderInfo extends PackageInfo {
        public final ServiceInfo serviceInfo;

        /* JADX INFO: Access modifiers changed from: package-private */
        public GDServiceProviderInfo(ServiceInfo serviceInfo, String str) {
            super(str);
            this.serviceInfo = serviceInfo;
        }

        @Override // com.good.gd.interception.IntentData.PackageInfo
        public boolean isGD() {
            return true;
        }
    }

    /* loaded from: classes.dex */
    public static class IccData {
        public final String[] attachments;
        public final Object params;

        public IccData(Object obj, String[] strArr) {
            this.params = obj;
            this.attachments = strArr;
        }
    }

    /* loaded from: classes.dex */
    public static class IntentDataBuilder {
        private ApplicationData applicationData;
        private List<ApplicationInfo> applicationsInfo;
        private List<GDServiceProviderInfo> gdServiceProvidersInfo;
        private IccData iccData;
        private ServiceInfo serviceInfo;

        public IntentData createIntentData() {
            if (this.gdServiceProvidersInfo == null) {
                this.gdServiceProvidersInfo = Collections.emptyList();
            }
            if (this.applicationsInfo == null) {
                this.applicationsInfo = Collections.emptyList();
            }
            return new IntentData(this.iccData, this.serviceInfo, this.gdServiceProvidersInfo, this.applicationsInfo, this.applicationData);
        }

        public IntentDataBuilder setApplicationData(ApplicationData applicationData) {
            this.applicationData = applicationData;
            return this;
        }

        public IntentDataBuilder setApplicationsInfo(List<ApplicationInfo> list) {
            this.applicationsInfo = list;
            return this;
        }

        public IntentDataBuilder setGdServiceProvidersInfo(List<GDServiceProviderInfo> list) {
            this.gdServiceProvidersInfo = list;
            return this;
        }

        public IntentDataBuilder setIccData(IccData iccData) {
            this.iccData = iccData;
            return this;
        }

        public IntentDataBuilder setServiceInfo(ServiceInfo serviceInfo) {
            this.serviceInfo = serviceInfo;
            return this;
        }
    }

    /* loaded from: classes.dex */
    public static abstract class PackageInfo {
        public final String packageName;

        public abstract boolean isGD();

        private PackageInfo(String str) {
            this.packageName = str;
        }
    }

    /* loaded from: classes.dex */
    public static class ServiceInfo {
        public final boolean allowNonGD;
        public final GDServiceDetail gdServiceDetail;
        public final String[] priorityApplications;
        public final String serviceMethod;

        /* JADX INFO: Access modifiers changed from: package-private */
        public ServiceInfo(GDServiceDetail gDServiceDetail, String str, String[] strArr, boolean z) {
            this.gdServiceDetail = gDServiceDetail;
            this.serviceMethod = str;
            this.priorityApplications = strArr;
            this.allowNonGD = z;
        }
    }

    private IntentData(IccData iccData, ServiceInfo serviceInfo, List<GDServiceProviderInfo> list, List<ApplicationInfo> list2, ApplicationData applicationData) {
        this.iccData = iccData;
        this.serviceInfo = serviceInfo;
        this.gdServiceProvidersInfo = list;
        this.applicationsInfo = list2;
        this.applicationData = applicationData;
    }

    private IntentData() {
        this(null, null, null, null, null);
    }
}
