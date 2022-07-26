package com.good.gd.ndkproxy.ui.data;

import android.content.Context;
import androidx.core.view.GravityCompat;
import com.good.gd.ndkproxy.ui.data.base.BlockBaseUI;
import com.good.gd.ui.GDBlockView;
import com.good.gd.ui.ViewCustomizer;
import com.good.gd.ui.ViewInteractor;

/* loaded from: classes.dex */
public class AuthDelegationBlockView extends GDBlockView {
    public AuthDelegationBlockView(Context context, ViewInteractor viewInteractor, BlockBaseUI blockBaseUI, ViewCustomizer viewCustomizer) {
        super(context, viewInteractor, blockBaseUI, viewCustomizer);
    }

    @Override // com.good.gd.ui.GDBlockView
    protected void updateGravity() {
        this.titleView.setGravity(GravityCompat.START);
        this.messageView.setGravity(GravityCompat.START);
    }
}
