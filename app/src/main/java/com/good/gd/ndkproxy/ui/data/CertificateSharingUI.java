package com.good.gd.ndkproxy.ui.data;

import android.content.Context;
import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.ndkproxy.ui.GDIdentitySharedStoreHandler;
import com.good.gd.ndkproxy.ui.data.base.BaseUI;
import com.good.gd.ndkproxy.ui.event.BBDCertSharingUpdateEvent;
import com.good.gd.ndkproxy.ui.event.BBDUIUpdateEvent;
import com.good.gd.ui.GDIdentitySharingView;
import com.good.gd.ui.ViewCustomizer;
import com.good.gd.ui.ViewInteractor;
import com.good.gd.ui.base_ui.GDView;

/* loaded from: classes.dex */
public class CertificateSharingUI extends BaseUI {
    private BBDCertSharingUpdateEvent certSharingUpdateEvent;
    private boolean isCertificateUIOpening;
    private boolean needSpinner;
    private String providerName;
    private String reason;

    public CertificateSharingUI(long j, String str, String str2) {
        super(BBUIType.UI_CERTIFICATESHARING, j);
        this.isCertificateUIOpening = false;
        this.reason = str;
        this.providerName = str2;
        if (str == null || str2 == null) {
            return;
        }
        this.isCertificateUIOpening = true;
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BaseUI
    /* renamed from: createView */
    public GDView mo294createView(Context context, ViewInteractor viewInteractor, ViewCustomizer viewCustomizer) {
        return new GDIdentitySharingView(context, viewInteractor, this, viewCustomizer);
    }

    public BBDCertSharingUpdateEvent getCertSharingUpdateEvent() {
        return this.certSharingUpdateEvent;
    }

    public String getProviderName() {
        return this.providerName;
    }

    public String getReason() {
        return this.reason;
    }

    public GDIdentitySharedStoreHandler.IMPORT_SHARING_RESULT getStateType() {
        return this.certSharingUpdateEvent.getResultState();
    }

    public boolean isCertificateUIOpening() {
        return this.isCertificateUIOpening;
    }

    public boolean isResultAvailable() {
        return this.certSharingUpdateEvent != null;
    }

    public boolean isSpinnerRequired() {
        return this.needSpinner;
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BBDUIObject
    public void onStateDestroyed() {
        super.onStateDestroyed();
        GDIdentitySharedStoreHandler.getInstance().unregisterCallback();
    }

    public void resetCertificateUIOpenedState() {
        this.isCertificateUIOpening = false;
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BBDUIObject
    public void setUpdateData(BBDUIUpdateEvent bBDUIUpdateEvent) {
        if (!(bBDUIUpdateEvent instanceof BBDCertSharingUpdateEvent)) {
            super.setUpdateData(bBDUIUpdateEvent);
        } else {
            this.certSharingUpdateEvent = (BBDCertSharingUpdateEvent) bBDUIUpdateEvent;
        }
    }

    public void spinnerHasToBe() {
        this.needSpinner = true;
    }

    public void spinnerIsNotRequiredAnyMore() {
        this.needSpinner = false;
    }
}
