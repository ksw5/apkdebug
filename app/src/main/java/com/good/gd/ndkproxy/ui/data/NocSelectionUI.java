package com.good.gd.ndkproxy.ui.data;

import android.content.Context;
import com.good.gd.enterprise.NocServerListProvider;
import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.ndkproxy.ui.data.base.BaseUI;
import com.good.gd.ndkproxy.ui.event.BBDUIMessageType;
import com.good.gd.ui.GDENocSelectionView;
import com.good.gd.ui.ViewCustomizer;
import com.good.gd.ui.ViewInteractor;
import com.good.gd.ui.base_ui.GDView;

/* loaded from: classes.dex */
public class NocSelectionUI extends BaseUI {
    private final NocServerListProvider nocServerListProvider;

    public NocSelectionUI(long j, NocServerListProvider nocServerListProvider) {
        super(BBUIType.UI_NOC_SELECTION, j);
        this.nocServerListProvider = nocServerListProvider;
    }

    private static native void _selectNoc(long j, String str);

    private void selectNoc(String str) {
        _selectNoc(getCoreHandle(), str);
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BaseUI
    /* renamed from: createView */
    public GDView mo294createView(Context context, ViewInteractor viewInteractor, ViewCustomizer viewCustomizer) {
        return new GDENocSelectionView(context, viewInteractor, this, viewCustomizer, this.nocServerListProvider);
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BBDUIObject
    public void onMessage(BBDUIMessageType bBDUIMessageType, Object obj) {
        if (bBDUIMessageType.ordinal() != 10) {
            super.onMessage(bBDUIMessageType, obj);
        } else {
            selectNoc((String) obj);
        }
    }
}
