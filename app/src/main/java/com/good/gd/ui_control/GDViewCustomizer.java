package com.good.gd.ui_control;

import com.good.gd.client.GDClient;
import com.good.gd.client.GDCustomizedUI;
import com.good.gd.enterprise.NocServerListProvider;
import com.good.gd.ndkproxy.SDKVersionNative;
import com.good.gd.ndkproxy.enterprise.GDAuthManager;
import com.good.gd.ndkproxy.enterprise.GDEProvisionUtil;
import com.good.gd.ui.ViewCustomizer;
import com.good.gd.utils.DebuggableChecker;
import com.good.gd.utils.EmulatorChecker;
import com.good.gd.utils.EnterpriseModeChecker;
import com.good.gd.utils.GDDebuggableChecker;
import com.good.gd.utils.GDEmulatorChecker;
import com.good.gd.utils.GDEnterpriseModeChecker;
import com.good.gd.utils.NoPassChecker;

/* loaded from: classes.dex */
public class GDViewCustomizer implements ViewCustomizer {
    @Override // com.good.gd.ui.ViewCustomizer
    public DebuggableChecker getDebuggableChecker() {
        return new GDDebuggableChecker();
    }

    @Override // com.good.gd.ui.ViewCustomizer
    public EmulatorChecker getEmulatorChecker() {
        return new GDEmulatorChecker();
    }

    @Override // com.good.gd.ui.ViewCustomizer
    public EnterpriseModeChecker getEnterpriseModeChecker() {
        return new GDEnterpriseModeChecker();
    }

    @Override // com.good.gd.ui.ViewCustomizer
    public GDCustomizedUI getGDCustomizedUI() {
        return GDClient.getInstance();
    }

    @Override // com.good.gd.ui.ViewCustomizer
    public NoPassChecker getNoPassChecker() {
        return GDAuthManager.getInstance();
    }

    @Override // com.good.gd.ui.ViewCustomizer
    public NocServerListProvider getNocServerListProvider() {
        return new GDEProvisionUtil();
    }

    public String getSDKVersion() {
        return SDKVersionNative.getSDKVersion();
    }
}
