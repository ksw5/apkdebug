package com.good.gd.mam;

import com.good.gd.GDResult;
import java.nio.ByteBuffer;
import java.util.List;

/* loaded from: classes.dex */
public interface GDMobileApplicationManagementListener {
    void onApplicationDetailsResponse(int i, GDResult gDResult, GDCatalogApplicationDetails gDCatalogApplicationDetails);

    void onApplicationDispatchedForInstallation(int i, GDResult gDResult);

    void onApplicationInstallerDetailsResponse(int i, GDResult gDResult, GDCatalogApplicationInstallerDetails gDCatalogApplicationInstallerDetails);

    void onInstallableApplicationsResponse(int i, GDResult gDResult, List<GDEntitlement> list);

    void onReceivedApplicationResource(int i, GDResult gDResult, ByteBuffer byteBuffer);
}
