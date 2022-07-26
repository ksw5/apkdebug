package com.good.gd.wxh;

import com.good.gd.ui.utils.sis.ActivityStatusListener;

/* loaded from: classes.dex */
public class yfdke implements ActivityStatusListener {
    @Override // com.good.gd.ui.utils.sis.ActivityStatusListener
    public int getCurrentSISParticipationStatus() {
        return com.good.gd.idl.hbfhc.pqq().liflu();
    }

    @Override // com.good.gd.ui.utils.sis.ActivityStatusListener
    public boolean isRequireToShowCustomPermissionDialog() {
        return com.good.gd.idl.hbfhc.pqq().vfle();
    }

    @Override // com.good.gd.ui.utils.sis.ActivityStatusListener
    public boolean isRequireToShowDeviceLocationSettingsDialog() {
        return com.good.gd.idl.hbfhc.pqq().iulf();
    }

    @Override // com.good.gd.ui.utils.sis.ActivityStatusListener
    public boolean isRequireToShowSISPrimerScreen() {
        return com.good.gd.idl.hbfhc.pqq().rynix();
    }

    @Override // com.good.gd.ui.utils.sis.ActivityStatusListener
    public boolean isRequireToShowSystemPermissionDialog() {
        return com.good.gd.idl.hbfhc.pqq().ugfcv();
    }

    @Override // com.good.gd.ui.utils.sis.ActivityStatusListener
    public void setIsCustomPermissionDialogAlreadyShown(boolean z) {
        com.good.gd.idl.hbfhc.pqq().dbjc(z);
    }

    @Override // com.good.gd.ui.utils.sis.ActivityStatusListener
    public void setIsDeviceLocationDialogAlreadyShown(boolean z) {
        com.good.gd.idl.hbfhc.pqq().qkduk(z);
    }

    @Override // com.good.gd.ui.utils.sis.ActivityStatusListener
    public void setIsPrimerScreenAlreadyShown(boolean z) {
        com.good.gd.idl.hbfhc.pqq().jwxax(z);
    }

    @Override // com.good.gd.ui.utils.sis.ActivityStatusListener
    public void setIsSettingsActivityRunning(boolean z) {
        com.good.gd.idl.hbfhc.pqq().wxau(z);
    }

    @Override // com.good.gd.ui.utils.sis.ActivityStatusListener
    public void setIsSystemPermissionDialogAlreadyShown(boolean z) {
        com.good.gd.idl.hbfhc.pqq().ztwf(z);
    }

    @Override // com.good.gd.ui.utils.sis.ActivityStatusListener
    public void setSISLearnMoreActivityRunning(boolean z) {
        if (com.good.gd.idl.hbfhc.pqq() != null) {
            return;
        }
        throw null;
    }
}
