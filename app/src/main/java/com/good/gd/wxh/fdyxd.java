package com.good.gd.wxh;

import com.good.gd.ui.utils.sis.LocationDialogListener;
import com.good.gd.ui.utils.sis.UXActionListener;

/* loaded from: classes.dex */
public class fdyxd implements UXActionListener, LocationDialogListener {
    @Override // com.good.gd.ui.utils.sis.LocationDialogListener
    public void onLocationPermissionUpdated(boolean z) {
        ((com.good.gd.gectx.hbfhc) com.blackberry.bis.core.yfdke.dbjc()).qkduk();
        com.good.gd.daq.hbfhc.qkduk().qkduk().onLocationPermissionUpdated(z);
    }

    @Override // com.good.gd.ui.utils.sis.LocationDialogListener
    public void onLocationSettingsUpdated(boolean z) {
        com.good.gd.daq.hbfhc.qkduk().qkduk().onLocationSettingsUpdated(z);
    }

    @Override // com.good.gd.ui.utils.sis.UXActionListener
    public void onPrimerUpdated(int i) {
        ((com.good.gd.gectx.hbfhc) com.blackberry.bis.core.yfdke.dbjc()).dbjc().onPrimerUpdated(i);
    }
}
