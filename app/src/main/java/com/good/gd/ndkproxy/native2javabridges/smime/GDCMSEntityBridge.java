package com.good.gd.ndkproxy.native2javabridges.smime;

import com.good.gd.smime.GDSMIME;
import java.io.InputStream;

/* loaded from: classes.dex */
final class GDCMSEntityBridge extends GDSMIME.GDCMSEntity.GDCMSEntityBridgeHelper {
    GDCMSEntityBridge() {
    }

    private static byte[] getDataArray(GDSMIME.GDCMSEntity gDCMSEntity) {
        return GDSMIME.GDCMSEntity.GDCMSEntityBridgeHelper._getDataArray(gDCMSEntity);
    }

    private static long getInfoObjInternalData(GDSMIME.GDCMSEntity gDCMSEntity) {
        return GDSMIME.GDCMSEntity.GDCMSEntityBridgeHelper._getInfoObjInternalData(gDCMSEntity);
    }

    private static void setInfoObjInternalData(GDSMIME.GDCMSEntity gDCMSEntity, long j) {
        GDSMIME.GDCMSEntity.GDCMSEntityBridgeHelper._setInfoObjInternalData(gDCMSEntity, j);
    }

    private static void setInputStream(GDSMIME.GDCMSEntity gDCMSEntity, InputStream inputStream) {
        GDSMIME.GDCMSEntity.GDCMSEntityBridgeHelper._setInputStream(gDCMSEntity, inputStream);
    }

    private static void setType(GDSMIME.GDCMSEntity gDCMSEntity, int i) {
        GDSMIME.GDCMSEntity.GDCMSEntityBridgeHelper._setType(gDCMSEntity, i);
    }
}
