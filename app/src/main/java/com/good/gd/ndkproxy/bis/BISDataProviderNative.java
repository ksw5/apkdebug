package com.good.gd.ndkproxy.bis;

import com.blackberry.bis.core.aqdzk;
import com.blackberry.bis.core.yfdke;
import com.good.gd.idl.hbfhc;
import com.good.gd.ui.utils.sis.UIBehaviorType;

/* loaded from: classes.dex */
public class BISDataProviderNative {
    private static int getBISUIBehavior() {
        UIBehaviorType uIBehaviorType;
        hbfhc pqq = hbfhc.pqq();
        if (pqq.ugfcv()) {
            return UIBehaviorType.PERMISSION_DIALOG.getValue();
        }
        if (pqq.vfle()) {
            return UIBehaviorType.PERMISSION_DIALOG.getValue();
        }
        if (pqq.iulf()) {
            String ztwf = pqq.ztwf();
            char c = 65535;
            int hashCode = ztwf.hashCode();
            if (hashCode != -684752332) {
                if (hashCode != -418938129) {
                    if (hashCode != 489084919) {
                        if (hashCode == 1286213020 && ztwf.equals("sisPrimerScreen")) {
                            c = 1;
                        }
                    } else if (ztwf.equals("permissionDialog")) {
                        c = 0;
                    }
                } else if (ztwf.equals("googleDevLocSettingsDialog")) {
                    c = 3;
                }
            } else if (ztwf.equals("frameworkDevLocSettingsDialog")) {
                c = 2;
            }
            switch (c) {
                case 0:
                    uIBehaviorType = UIBehaviorType.PERMISSION_DIALOG;
                    break;
                case 1:
                    uIBehaviorType = UIBehaviorType.SIS_PRIMER_SCREEN;
                    break;
                case 2:
                    uIBehaviorType = UIBehaviorType.SIS_FRAMEWORK_DEV_LOC_SETTINGS_DIALOG;
                    break;
                case 3:
                    uIBehaviorType = UIBehaviorType.SIS_GOOGLE_DEV_LOC_SETTINGS_DIALOG;
                    break;
                default:
                    uIBehaviorType = UIBehaviorType.SIS_PRIMER_SCREEN;
                    break;
            }
            return uIBehaviorType.getValue();
        }
        return UIBehaviorType.SIS_PRIMER_SCREEN.getValue();
    }

    private static int getStoredBISStatus(int i, int i2, int i3, int i4, int i5) {
        hbfhc pqq = hbfhc.pqq();
        int liflu = pqq.liflu();
        boolean z = true;
        if (liflu != 0) {
            return liflu != 1 ? liflu != 2 ? i : i4 : i3;
        }
        if (!pqq.iulf() && !pqq.ugfcv() && !pqq.vfle()) {
            z = false;
        }
        return z ? i5 : i2;
    }

    private static boolean hasBISEntitlement() {
        return hbfhc.pqq().odlf();
    }

    private static boolean isBISEnabledMetadataAvailable() {
        return aqdzk.wxau();
    }

    private static boolean isBISEntitlementFetched() {
        return yfdke.sbesx().dbjc("BISEntitlementStatus");
    }
}
