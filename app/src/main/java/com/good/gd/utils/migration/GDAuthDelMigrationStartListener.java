package com.good.gd.utils.migration;

import com.good.gd.ndkproxy.GDLog;
import com.good.gt.ndkproxy.icc.migration.AuthDelegationMigrationListener;

/* loaded from: classes.dex */
public class GDAuthDelMigrationStartListener implements AuthDelegationMigrationListener {
    @Override // com.good.gt.ndkproxy.icc.migration.AuthDelegationMigrationListener
    public void shouldStartMigration(byte[] bArr, String str) {
        GDLog.DBGPRINTF(14, "AuthDelMigration", "shouldStartMigration");
        GDMigrationUtils.startMigration(bArr, str);
    }
}
