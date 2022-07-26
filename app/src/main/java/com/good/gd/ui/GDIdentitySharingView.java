package com.good.gd.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.RelativeLayout;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.ui.GDIdentitySharedStoreHandler;
import com.good.gd.ndkproxy.ui.data.CertificateSharingUI;
import com.good.gd.resources.R;
import com.good.gd.ui.base_ui.GDView;
import com.good.gd.utils.GDLocalizer;

/* loaded from: classes.dex */
public class GDIdentitySharingView extends GDView {
    private RelativeLayout back_dim_layout = (RelativeLayout) findViewById(R.id.gd_bac_dim_layout);
    private CertificateSharingUI uiData;

    /* loaded from: classes.dex */
    private class fdyxd extends GDViewDelegateAdapter {
        private fdyxd() {
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActivityResume() {
            super.onActivityResume();
            if (GDIdentitySharingView.this.uiData.isCertificateUIOpening()) {
                GDLog.DBGPRINTF(16, "Register Certificate Sharing CallBack");
                GDIdentitySharedStoreHandler.getInstance().registerCallback();
                GDIdentitySharingView.this.uiData.resetCertificateUIOpenedState();
            }
            GDIdentitySharingView.this.stateWasUpdated();
        }

        /* synthetic */ fdyxd(GDIdentitySharingView gDIdentitySharingView, hbfhc hbfhcVar) {
            this();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class hbfhc implements DialogInterface.OnClickListener {
        hbfhc(GDIdentitySharingView gDIdentitySharingView) {
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            GDIdentitySharedStoreHandler.getInstance().performNextAction();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class yfdke implements DialogInterface.OnClickListener {
        yfdke(GDIdentitySharingView gDIdentitySharingView) {
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            GDIdentitySharedStoreHandler.getInstance().cancelImport();
        }
    }

    public GDIdentitySharingView(Context context, ViewInteractor viewInteractor, CertificateSharingUI certificateSharingUI, ViewCustomizer viewCustomizer) {
        super(context, viewInteractor, viewCustomizer);
        this.uiData = certificateSharingUI;
        inflateLayout(R.layout.bbd_identity_sharing_view, this);
        this._delegate = new fdyxd(this, null);
        this.back_dim_layout.setClickable(true);
        if (certificateSharingUI.isSpinnerRequired()) {
            showSpinner();
        }
    }

    private void hideSpinner() {
        this.back_dim_layout.setVisibility(8);
        this.uiData.spinnerIsNotRequiredAnyMore();
    }

    private void showSpinner() {
        this.back_dim_layout.setVisibility(0);
        this.uiData.spinnerHasToBe();
    }

    protected void showPopupDialog(String str, String str2, String str3, DialogInterface.OnClickListener onClickListener, String str4, DialogInterface.OnClickListener onClickListener2, boolean z) {
        this.viewInteractor.cancelDialog();
        if (z) {
            super.showPopupDialog4(str, str2, z);
        } else if (onClickListener != null) {
            super.showPopupDialog2(str, str2, str3, onClickListener, str4, onClickListener2);
        } else {
            super.showPopupDialog(str, str2, str4, onClickListener2);
        }
    }

    @Override // com.good.gd.ui.base_ui.GDView
    public void stateWasUpdated() {
        String str;
        yfdke yfdkeVar;
        String localizedString;
        String localizedString2;
        String format;
        if (!this.uiData.isResultAvailable()) {
            return;
        }
        hbfhc hbfhcVar = new hbfhc(this);
        boolean allowCancellation = GDIdentitySharedStoreHandler.getInstance().allowCancellation();
        if (allowCancellation) {
            yfdkeVar = new yfdke(this);
            str = GDLocalizer.getLocalizedString("Certificate Sharing Alert Cancel Button");
        } else {
            str = null;
            yfdkeVar = null;
        }
        String reason = this.uiData.getReason();
        String providerName = this.uiData.getProviderName();
        switch (this.uiData.getStateType().ordinal()) {
            case 1:
                showSpinner();
                if (reason.equals("Expired")) {
                    localizedString = GDLocalizer.getLocalizedString("Certificate Sharing Expired Alert Title");
                } else {
                    localizedString = GDLocalizer.getLocalizedString("Certificate Sharing Alert Title");
                }
                showPopupDialog(localizedString, GDLocalizer.getLocalizedString("Certificate Sharing Alert Connecting Message"), str, null, null, null, true);
                return;
            case 2:
            case 4:
            case 9:
            case 10:
            default:
                return;
            case 3:
                showSpinner();
                if (reason.equals("Expired")) {
                    localizedString2 = GDLocalizer.getLocalizedString("Certificate Sharing Expired Alert Title");
                } else {
                    localizedString2 = GDLocalizer.getLocalizedString("Certificate Sharing Alert Title");
                }
                String localizedString3 = GDLocalizer.getLocalizedString("Certificate Sharing Alert Next Button");
                if (allowCancellation) {
                    format = String.format(GDLocalizer.getLocalizedString("Certificate Sharing Alert Cancel Message"), providerName);
                } else {
                    format = String.format(GDLocalizer.getLocalizedString("Certificate Sharing Alert Message"), providerName, providerName);
                }
                showPopupDialog(localizedString2, format, str, yfdkeVar, localizedString3, hbfhcVar, false);
                return;
            case 5:
                showSpinner();
                showPopupDialog(GDLocalizer.getLocalizedString("Certificate Sharing Alert Error Missing Certificates Title"), String.format(GDLocalizer.getLocalizedString("Certificate Sharing Alert Error Missing Certificates"), providerName), str, yfdkeVar, GDLocalizer.getLocalizedString("Certificate Sharing Alert Retry Button"), hbfhcVar, false);
                return;
            case 6:
                showSpinner();
                showPopupDialog(GDLocalizer.getLocalizedString("Certificate Sharing Alert Error Missing App Title"), String.format(GDLocalizer.getLocalizedString("Certificate Sharing Alert Error Missing App"), providerName, providerName), str, yfdkeVar, GDLocalizer.getLocalizedString("Certificate Sharing Alert Retry Button"), hbfhcVar, false);
                return;
            case 7:
                showSpinner();
                showPopupDialog(GDLocalizer.getLocalizedString("Certificate Sharing Alert Error Incompatible App Title"), String.format(GDLocalizer.getLocalizedString("Certificate Sharing Alert Error Incompatible App"), providerName, providerName), str, yfdkeVar, GDLocalizer.getLocalizedString("Certificate Sharing Alert Retry Button"), hbfhcVar, false);
                return;
            case 8:
                showSpinner();
                showPopupDialog(GDLocalizer.getLocalizedString("Certificate Sharing Alert Error App Locked Title"), String.format(GDLocalizer.getLocalizedString("Certificate Sharing Alert Error App Locked"), providerName), str, yfdkeVar, GDLocalizer.getLocalizedString("Certificate Sharing Alert Retry Button"), hbfhcVar, false);
                return;
            case 11:
                showSpinner();
                showPopupDialog(GDLocalizer.getLocalizedString("Certificate Sharing Alert Error Unknown Title"), String.format(GDLocalizer.getLocalizedString("Certificate Sharing Alert Error Unknown"), new Object[0]), str, yfdkeVar, GDLocalizer.getLocalizedString("Certificate Sharing Alert Retry Button"), hbfhcVar, false);
                return;
        }
    }
}
