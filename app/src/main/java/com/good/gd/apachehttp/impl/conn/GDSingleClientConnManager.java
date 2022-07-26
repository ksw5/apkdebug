package com.good.gd.apachehttp.impl.conn;

import com.good.gd.apache.http.conn.ClientConnectionOperator;
import com.good.gd.apache.http.conn.scheme.SchemeRegistry;
import com.good.gd.apache.http.impl.conn.SingleClientConnManager;
import com.good.gd.apache.http.params.HttpParams;
import com.good.gd.ndkproxy.GDLog;

/* loaded from: classes.dex */
public class GDSingleClientConnManager extends SingleClientConnManager {
    private static boolean intercept = true;
    private GDDefaultClientConnectionOperator _gdClientConnectionOperator = null;
    private GDDefaultClientConfigs _ownerConfig;

    public GDSingleClientConnManager(HttpParams httpParams, SchemeRegistry schemeRegistry, GDDefaultClientConfigs gDDefaultClientConfigs) {
        super(httpParams, schemeRegistry);
        this._ownerConfig = null;
        GDLog.DBGPRINTF(16, "GDSingleClientConnManager::GDSingleClientConnManager() IN\n");
        this._ownerConfig = gDDefaultClientConfigs;
        GDLog.DBGPRINTF(16, "GDSingleClientConnManager::GDSingleClientConnManager() OUT\n");
    }

    public static void disableIntercept() {
        intercept = false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.good.gd.apache.http.impl.conn.SingleClientConnManager
    public ClientConnectionOperator createConnectionOperator(SchemeRegistry schemeRegistry) {
        GDDefaultClientConnectionOperator gDDefaultClientConnectionOperator;
        GDLog.DBGPRINTF(16, "GDSingleClientConnManager::createConnectionOperator() IN\n");
        if (intercept) {
            GDDefaultClientConnectionOperator gDDefaultClientConnectionOperator2 = new GDDefaultClientConnectionOperator(schemeRegistry, this);
            this._gdClientConnectionOperator = gDDefaultClientConnectionOperator2;
            gDDefaultClientConnectionOperator = gDDefaultClientConnectionOperator2;
        } else {
            gDDefaultClientConnectionOperator = super.createConnectionOperator(schemeRegistry);
        }
        GDLog.DBGPRINTF(16, "GDSingleClientConnManager::createConnectionOperator() OUT:" + gDDefaultClientConnectionOperator + "\n");
        return gDDefaultClientConnectionOperator;
    }

    public boolean isCertVerificationDisabled() {
        return this._ownerConfig.isCertVerificationDisabled();
    }

    public boolean isHostVerificationDisabled() {
        return this._ownerConfig.isHostVerificationDisabled();
    }
}
