package com.good.gd.ndkproxy.ui.data;

import android.content.Context;
import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.ndkproxy.ui.BISStatus;
import com.good.gd.ndkproxy.ui.data.base.BaseUI;
import com.good.gd.ndkproxy.ui.event.BBDUIMessageType;
import com.good.gd.ui.SISPrimerView;
import com.good.gd.ui.ViewCustomizer;
import com.good.gd.ui.ViewInteractor;
import com.good.gd.ui.base_ui.GDView;

/* loaded from: classes.dex */
public class SISPrimerUI extends BaseUI {
    private int behaviorType;
    private BISStatus bisStatusNative = new BISStatus();

    public SISPrimerUI(long j, int i) {
        super(BBUIType.UI_SIS_PRIMER, j);
        this.behaviorType = i;
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BaseUI
    /* renamed from: createView */
    public GDView mo294createView(Context context, ViewInteractor viewInteractor, ViewCustomizer viewCustomizer) {
        return new SISPrimerView(context, viewInteractor, viewCustomizer, this, this.behaviorType);
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BBDUIObject
    public void onMessage(BBDUIMessageType bBDUIMessageType, Object obj) {
        switch (bBDUIMessageType.ordinal()) {
            case 27:
                this.bisStatusNative.complete(getCoreHandle());
                return;
            case 28:
                this.bisStatusNative.savePermissionRequired(getCoreHandle());
                return;
            case 29:
                this.bisStatusNative.maybeLaterClicked(getCoreHandle());
                return;
            case 30:
                this.bisStatusNative.resetSaved();
                return;
            default:
                super.onMessage(bBDUIMessageType, obj);
                return;
        }
    }
}
