package com.good.gd.service.interception;

import android.content.Context;
import com.good.gd.icc.GDICCForegroundOptions;
import com.good.gd.icc.GDServiceClient;
import com.good.gd.icc.GDServiceException;
import com.good.gd.interception.IntentData;
import com.good.gd.interception.ServiceAction;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class IccAction implements ServiceAction<Void> {
    final IntentData.GDServiceProviderInfo dbjc;
    final IntentData.IccData qkduk;

    /* JADX INFO: Access modifiers changed from: package-private */
    public IccAction(IntentData.GDServiceProviderInfo gDServiceProviderInfo, IntentData.IccData iccData) {
        this.dbjc = gDServiceProviderInfo;
        this.qkduk = iccData;
    }

    public void dbjc() throws GDServiceException {
        IntentData.GDServiceProviderInfo gDServiceProviderInfo = this.dbjc;
        if (gDServiceProviderInfo != null) {
            IntentData.ServiceInfo serviceInfo = gDServiceProviderInfo.serviceInfo;
            if (serviceInfo != null) {
                String str = gDServiceProviderInfo.packageName;
                String identifier = serviceInfo.gdServiceDetail.getIdentifier();
                String version = this.dbjc.serviceInfo.gdServiceDetail.getVersion();
                String str2 = this.dbjc.serviceInfo.serviceMethod;
                IntentData.IccData iccData = this.qkduk;
                GDServiceClient.sendTo(str, identifier, version, str2, iccData.params, iccData.attachments, GDICCForegroundOptions.PreferPeerInForeground);
                return;
            }
            throw new IllegalStateException("ServiceInfo is null");
        }
        throw new IllegalStateException("GDServiceProviderInfo is null");
    }

    @Override // com.good.gd.interception.ServiceAction
    public void execute(Callback<Void> callback, Context context) {
        throw new UnsupportedOperationException();
    }
}
