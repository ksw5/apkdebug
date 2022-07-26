package com.good.gd.utils.migration;

import com.good.gd.containerstate.ndkproxy.GDActivitySupport;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.net.NetworkStateMonitor;
import com.good.gt.ndkproxy.icc.migration.AuthDelMigrationResponseHandler;

/* loaded from: classes.dex */
public class GDAuthDelMigrationResponseHandler implements AuthDelMigrationResponseHandler {
    @Override // com.good.gt.ndkproxy.icc.migration.AuthDelMigrationResponseHandler
    public AuthDelMigrationResponseHandler.ACTION_ON_RESPONSE onMigrationResponse(byte[] bArr) {
        boolean isNetworkAvailable = NetworkStateMonitor.getInstance().isNetworkAvailable();
        boolean z = !GDActivitySupport.isStartupSuccessful();
        boolean isAuthKeyValid = GDMigrationUtils.isAuthKeyValid(bArr);
        GDLog.DBGPRINTF(16, "GDAuthDelMigrationResponseHandler", "onMigrationResponse", " launchedFromColdStart: " + z, ",isAuthKeyValid: " + isAuthKeyValid, ",isNetworkConnected: " + isNetworkAvailable);
        if (!isAuthKeyValid) {
            return AuthDelMigrationResponseHandler.ACTION_ON_RESPONSE.LOCK;
        }
        if (z && isNetworkAvailable) {
            return AuthDelMigrationResponseHandler.ACTION_ON_RESPONSE.MIGRATE;
        }
        return AuthDelMigrationResponseHandler.ACTION_ON_RESPONSE.AUTHENTICATE;
    }
}
