package com.good.gd.ndkproxy.ui.data;

import android.content.Context;
import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.ndkproxy.ui.data.base.BaseUI;
import com.good.gd.ui.GDMTDBlockedView;
import com.good.gd.ui.ViewCustomizer;
import com.good.gd.ui.ViewInteractor;
import com.good.gd.ui.base_ui.GDView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public class MTDBlockedUI extends BaseUI {
    private List<String> packageInfoItems;

    public MTDBlockedUI(long j, String[] strArr) {
        super(BBUIType.UI_BLOCK, j);
        ArrayList arrayList = new ArrayList(strArr.length);
        this.packageInfoItems = arrayList;
        Collections.addAll(arrayList, strArr);
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BaseUI
    /* renamed from: createView */
    public GDView mo294createView(Context context, ViewInteractor viewInteractor, ViewCustomizer viewCustomizer) {
        return new GDMTDBlockedView(context, viewInteractor, this, viewCustomizer);
    }

    public List<String> getPackageInfoItems() {
        return this.packageInfoItems;
    }
}
