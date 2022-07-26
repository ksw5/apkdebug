package com.good.gd.ui;

import com.good.gd.client.GDCustomizedUI;
import com.good.gd.enterprise.NocServerListProvider;
import com.good.gd.utils.DebuggableChecker;
import com.good.gd.utils.EmulatorChecker;
import com.good.gd.utils.EnterpriseModeChecker;
import com.good.gd.utils.NoPassChecker;

/* loaded from: classes.dex */
public interface ViewCustomizer {
    DebuggableChecker getDebuggableChecker();

    EmulatorChecker getEmulatorChecker();

    EnterpriseModeChecker getEnterpriseModeChecker();

    GDCustomizedUI getGDCustomizedUI();

    NoPassChecker getNoPassChecker();

    NocServerListProvider getNocServerListProvider();
}
