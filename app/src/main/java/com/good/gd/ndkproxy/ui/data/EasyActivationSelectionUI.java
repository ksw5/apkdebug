package com.good.gd.ndkproxy.ui.data;

import android.content.Context;
import com.good.gd.messages.ActivationMsg;
import com.good.gd.messages.CloseEasyActSelectionMsg;
import com.good.gd.messages.ProvisionMsg;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.enterprise.GDEActivationManager;
import com.good.gd.ndkproxy.ui.BBDUIDataStore;
import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.ndkproxy.ui.GDActivationDelegate;
import com.good.gd.ndkproxy.ui.data.base.BaseUI;
import com.good.gd.ndkproxy.ui.event.BBDUIEventManager;
import com.good.gd.ndkproxy.ui.event.BBDUIMessageType;
import com.good.gd.ui.GDActivationDelegateView;
import com.good.gd.ui.ViewCustomizer;
import com.good.gd.ui.ViewInteractor;
import com.good.gd.ui.base_ui.GDView;
import java.util.List;

/* loaded from: classes.dex */
public class EasyActivationSelectionUI extends BaseUI implements GDEActivationManager.DelegateListChangeListener {
    private String _nonce = null;
    private List<List<GDEActivationManager.Application>> delegates = GDEActivationManager.getInstance().getActivationInfo();
    private final ProvisionManager provisionManager;

    public EasyActivationSelectionUI(long j, ProvisionManager provisionManager) {
        super(BBUIType.UI_ACTIVATION_DELEGATION_SELECTION, j);
        this.provisionManager = provisionManager;
    }

    private native void setupManually(long j, boolean z);

    @Override // com.good.gd.ndkproxy.ui.data.base.BaseUI
    /* renamed from: createView */
    public GDView mo294createView(Context context, ViewInteractor viewInteractor, ViewCustomizer viewCustomizer) {
        return new GDActivationDelegateView(context, viewInteractor, this, viewCustomizer);
    }

    public List<List<GDEActivationManager.Application>> getDelegates() {
        return this.delegates;
    }

    public String getNonce() {
        return this._nonce;
    }

    @Override // com.good.gd.ndkproxy.enterprise.GDEActivationManager.DelegateListChangeListener
    public void onDelegateListChanged() {
        this.delegates = GDEActivationManager.getInstance().getActivationInfo();
        BBDUIEventManager.sendUpdateEvent(null, BBDUIDataStore.getInstance().getUIData(getCoreHandle()));
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BBDUIObject
    public void onMessage(BBDUIMessageType bBDUIMessageType, Object obj) {
        GDLog.DBGPRINTF(16, "EasyActivationSelectionUI.onMessage IN: " + bBDUIMessageType + "\n");
        int ordinal = bBDUIMessageType.ordinal();
        if (ordinal == 0) {
            if (!(obj instanceof ProvisionMsg)) {
                return;
            }
            this.provisionManager.startProvisioningProcedure((ProvisionMsg) obj);
        } else if (ordinal == 8) {
            if (!(obj instanceof ActivationMsg)) {
                return;
            }
            GDActivationDelegate.handleClientActivationRequest((ActivationMsg) obj);
        } else if (ordinal != 9) {
            super.onMessage(bBDUIMessageType, obj);
        } else if (!(obj instanceof CloseEasyActSelectionMsg)) {
        } else {
            setupManually(getCoreHandle(), ((CloseEasyActSelectionMsg) obj).isDelegateListEmpty());
        }
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BBDUIObject
    public void onStateActive() {
        super.onStateActive();
        GDEActivationManager.getInstance().setListChangeListener(this);
        this.delegates = GDEActivationManager.getInstance().getActivationInfo();
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BBDUIObject
    public void onStateDestroyed() {
        super.onStateDestroyed();
        GDEActivationManager.getInstance().setListChangeListener(null);
    }

    public void setNonce(String str) {
        this._nonce = str;
    }
}
