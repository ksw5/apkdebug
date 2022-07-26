package com.good.gd.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import com.good.gd.messages.FingerprintCheckedChangedMsg;
import com.good.gd.ndkproxy.ui.data.ActivateFingerprintUI;
import com.good.gd.ndkproxy.ui.event.BBDBiometricsUpdateEvent;
import com.good.gd.ndkproxy.ui.event.BBDUIEventManager;
import com.good.gd.ndkproxy.ui.event.BBDUIMessageType;
import com.good.gd.ndkproxy.ui.event.BBDUIUpdateEvent;
import com.good.gd.ndkproxy.ui.event.UIEventType;
import com.good.gd.resources.R;
import com.good.gd.ui.base_ui.GDView;
import com.good.gd.utils.GDLocalizer;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public final class GDActivateFingerprintView extends GDView implements CompoundButton.OnCheckedChangeListener {
    private static final String FINGERPRINT_POPUP_STATE = "FINGERPRINT_POPUP_STATE";
    private static final String SWITCH_STATE = "SWITCH_STATE";
    private ActivateFingerprintUI uiData;
    private boolean fingerprintPopupShown = false;
    private final Switch activateFingerprint = (Switch) findViewById(R.id.toggle_button);

    /* loaded from: classes.dex */
    class hbfhc implements OnClickListener {
        hbfhc() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            GDActivateFingerprintView.this.onBackPressed();
        }
    }

    /* loaded from: classes.dex */
    private class yfdke extends GDViewDelegateAdapter {
        private boolean dbjc;

        /* loaded from: classes.dex */
        class hbfhc implements Runnable {
            final /* synthetic */ AtomicBoolean dbjc;
            final /* synthetic */ AtomicBoolean qkduk;

            hbfhc(AtomicBoolean atomicBoolean, AtomicBoolean atomicBoolean2) {
                this.dbjc = atomicBoolean;
                this.qkduk = atomicBoolean2;
            }

            @Override // java.lang.Runnable
            public void run() {
                BBDUIEventManager.sendMessage(GDActivateFingerprintView.this.uiData, BBDUIMessageType.MSG_FINGERPRINT_ACTIVATE, new FingerprintCheckedChangedMsg(this.dbjc.get(), this.qkduk.get() || yfdke.this.dbjc || GDActivateFingerprintView.this.fingerprintPopupShown));
            }
        }

        private yfdke() {
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActivityCreate(Bundle bundle) {
            super.onActivityCreate(bundle);
            if (bundle != null) {
                this.dbjc = bundle.getBoolean(GDActivateFingerprintView.SWITCH_STATE);
                GDActivateFingerprintView.this.fingerprintPopupShown = bundle.getBoolean(GDActivateFingerprintView.FINGERPRINT_POPUP_STATE);
            }
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActivityPause() {
            GDActivateFingerprintView gDActivateFingerprintView = GDActivateFingerprintView.this;
            gDActivateFingerprintView.fingerprintPopupShown = gDActivateFingerprintView.uiData.isDialogShowing();
            BBDUIEventManager.sendMessage(GDActivateFingerprintView.this.uiData, BBDUIMessageType.MSG_HIDE_FINGERPRINT_DIALOG);
            this.dbjc = GDActivateFingerprintView.this.activateFingerprint.isChecked();
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActivityResume() {
            AtomicBoolean atomicBoolean = new AtomicBoolean();
            AtomicBoolean atomicBoolean2 = new AtomicBoolean();
            boolean hasEnrolledAndCanUseFingerprint = GDActivateFingerprintView.this.uiData.hasEnrolledAndCanUseFingerprint(atomicBoolean, atomicBoolean2);
            ((TextView) GDActivateFingerprintView.this.findViewById(R.id.COM_GOOD_GD_ACTIVATE_FINGERPRINT_VIEW_TITLE)).setText(GDLocalizer.getLocalizedString("Fingerprint Settings"));
            ((TextView) GDActivateFingerprintView.this.findViewById(R.id.COM_GOOD_GD_ACTIVATE_FINGERPRINT_VIEW_TEXT)).setText(GDLocalizer.getLocalizedString("Fingerprint settings page message"));
            GDActivateFingerprintView.this.activateFingerprint.setText(GDLocalizer.getLocalizedString("Fingerprint log in"));
            GDActivateFingerprintView.this.findViewById(R.id.container).setEnabled(hasEnrolledAndCanUseFingerprint);
            boolean z = false;
            if (hasEnrolledAndCanUseFingerprint) {
                GDActivateFingerprintView.this.activateFingerprint.setEnabled(true);
                GDActivateFingerprintView gDActivateFingerprintView = GDActivateFingerprintView.this;
                if (atomicBoolean2.get() && (atomicBoolean.get() || this.dbjc)) {
                    z = true;
                }
                gDActivateFingerprintView.setCheckedWithoutCallback(z);
                new Handler().post(new hbfhc(atomicBoolean2, atomicBoolean));
                return;
            }
            GDActivateFingerprintView.this.activateFingerprint.setEnabled(false);
            GDActivateFingerprintView.this.activateFingerprint.setChecked(false);
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onSaveInstanceState(Bundle bundle) {
            super.onSaveInstanceState(bundle);
            boolean isChecked = GDActivateFingerprintView.this.activateFingerprint.isChecked();
            this.dbjc = isChecked;
            bundle.putBoolean(GDActivateFingerprintView.SWITCH_STATE, isChecked);
            bundle.putBoolean(GDActivateFingerprintView.FINGERPRINT_POPUP_STATE, GDActivateFingerprintView.this.fingerprintPopupShown);
        }

        /* synthetic */ yfdke(GDActivateFingerprintView gDActivateFingerprintView, hbfhc hbfhcVar) {
            this();
        }
    }

    public GDActivateFingerprintView(Context context, ViewInteractor viewInteractor, ActivateFingerprintUI activateFingerprintUI, ViewCustomizer viewCustomizer) {
        super(context, viewInteractor, viewCustomizer);
        this.uiData = activateFingerprintUI;
        this._delegate = new yfdke(this, null);
        inflateLayout(R.layout.bbd_activate_fingerprint_view, this);
        findViewById(R.id.COM_GOOD_GD_ACTIVATE_FINGERPRINT_VIEW_BACK).setOnClickListener(new hbfhc());
        applyUICustomization();
        if (viewCustomizer.getEnterpriseModeChecker().enterpriseSimulationModeEnabled()) {
            setBottomLabelVisibility(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCheckedWithoutCallback(boolean z) {
        this.activateFingerprint.setOnCheckedChangeListener(null);
        this.activateFingerprint.setChecked(z);
        this.activateFingerprint.setOnCheckedChangeListener(this);
    }

    @Override // com.good.gd.ui.base_ui.GDView
    public void onBackPressed() {
        BBDUIEventManager.sendMessage(this.uiData, BBDUIMessageType.MSG_FINGERPRINT_CANCEL_ACTIVATE);
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        BBDUIEventManager.sendMessage(this.uiData, BBDUIMessageType.MSG_FINGERPRINT_ACTIVATE, new FingerprintCheckedChangedMsg(true, z));
    }

    @Override // com.good.gd.ui.base_ui.GDView
    public void stateWasUpdated() {
        BBDUIUpdateEvent updateData = this.uiData.getUpdateData();
        if (updateData == null) {
            return;
        }
        if (updateData.getType() == UIEventType.UI_UPDATE_BIOMETRY_STATE) {
            BBDBiometricsUpdateEvent bBDBiometricsUpdateEvent = (BBDBiometricsUpdateEvent) updateData;
            boolean isSwitchChecked = bBDBiometricsUpdateEvent.isSwitchChecked();
            this.activateFingerprint.setEnabled(bBDBiometricsUpdateEvent.isSwitchEnabled());
            if (bBDBiometricsUpdateEvent.notifySwitchUpdate()) {
                this.activateFingerprint.setOnCheckedChangeListener(this);
                this.activateFingerprint.setChecked(isSwitchChecked);
                return;
            }
            setCheckedWithoutCallback(isSwitchChecked);
        } else if (updateData.getType() != UIEventType.UI_UPDATE_BIOMETRY_ERROR) {
        } else {
            setCheckedWithoutCallback(updateData.isSuccessful());
        }
    }
}
