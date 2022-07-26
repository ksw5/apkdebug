package com.good.gd.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import com.good.gd.ndkproxy.ui.data.base.BBDUIObject;
import com.good.gd.ndkproxy.ui.event.BBDUIEventManager;
import com.good.gd.ndkproxy.ui.event.BBDUIMessageType;
import com.good.gd.resources.R;
import com.good.gd.ui.base_ui.GDView;
import com.good.gd.ui.utils.sis.ActivityStatusListener;
import com.good.gd.ui.utils.sis.LocationInfoProvider;
import com.good.gd.ui.utils.sis.SISProxy;
import com.good.gd.ui.utils.sis.SwipeRightListener;
import com.good.gd.ui.utils.sis.TelemetryManagerConstants;
import com.good.gd.ui.utils.sis.UIBehaviorType;
import com.good.gd.ui.utils.sis.UXActionListener;
import com.good.gd.utils.GDLocalizer;

/* loaded from: classes.dex */
public class SISSettingsView extends BISView {
    private static final String CLOUD_SERVICES_PRODUCT_NAME = "BlackBerry Cloud Services";
    private static final String IS_APP_LOCATION_CUSTOM_DIALOG_VISIBLE = "isAppLocationCustomDialogVisible";
    private static final String IS_DEVICE_LOCATION_CUSTOM_DIALOG_VISIBLE = "isDeviceLocationCustomDialogVisible";
    private static final String PERSONA_PRODUCT_NAME = "BlackBerry Persona";
    private final String TAG;
    private RelativeLayout mRLSISSwitch;
    private CompoundButton.OnCheckedChangeListener mSISParticipationStatusChangeListener = new fdyxd();
    private Switch mSwitchSISParticipation;
    private TextView mTVSISParticipationMsg;
    private View rootView;

    /* loaded from: classes.dex */
    private final class ehnkx extends GDViewDelegateAdapter {
        private ehnkx() {
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActivityCreate(Bundle bundle) {
            super.onActivityCreate(bundle);
            if (bundle != null) {
                if (bundle.getBoolean(SISSettingsView.IS_DEVICE_LOCATION_CUSTOM_DIALOG_VISIBLE)) {
                    SISSettingsView.this.displayDeviceLocSettingsRedirectionDialog();
                    return;
                } else if (bundle.getBoolean(SISSettingsView.IS_APP_LOCATION_CUSTOM_DIALOG_VISIBLE)) {
                    SISSettingsView.this.displayAppLocPermissionRedirectionDialog();
                    return;
                }
            }
            ActivityStatusListener activityStatusListener = SISSettingsView.this.mActivityStatusListener;
            if (activityStatusListener != null) {
                activityStatusListener.setIsSettingsActivityRunning(true);
            }
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActivityDestroy() {
            super.onActivityDestroy();
            ActivityStatusListener activityStatusListener = SISSettingsView.this.mActivityStatusListener;
            if (activityStatusListener != null) {
                activityStatusListener.setIsSettingsActivityRunning(false);
            }
            SISSettingsView.this.resetBISViewState();
            SISSettingsView.this.dismissCustomDialogs();
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActivityResume() {
            LocationInfoProvider locationInfoProvider;
            super.onActivityResume();
            SISSettingsView sISSettingsView = SISSettingsView.this;
            if (!sISSettingsView.mIsLocationSettingsUINeededOnAppResume || (locationInfoProvider = sISSettingsView.mLocationInfoProvider) == null || !locationInfoProvider.isAppLocationPermissionGranted() || SISSettingsView.this.mLocationInfoProvider.checkIfLocationServicesEnabled()) {
                return;
            }
            SISSettingsView.this.displayDeviceLocSettingsRedirectionDialog();
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onSaveInstanceState(Bundle bundle) {
            super.onSaveInstanceState(bundle);
            if (bundle != null) {
                Dialog dialog = SISSettingsView.this.mDeviceLocPermDialog;
                boolean z = true;
                bundle.putBoolean(SISSettingsView.IS_DEVICE_LOCATION_CUSTOM_DIALOG_VISIBLE, dialog != null && dialog.isShowing());
                Dialog dialog2 = SISSettingsView.this.mAppLocPermDialog;
                if (dialog2 == null || !dialog2.isShowing()) {
                    z = false;
                }
                bundle.putBoolean(SISSettingsView.IS_APP_LOCATION_CUSTOM_DIALOG_VISIBLE, z);
            }
        }

        /* synthetic */ ehnkx(SISSettingsView sISSettingsView, hbfhc hbfhcVar) {
            this();
        }
    }

    /* loaded from: classes.dex */
    class fdyxd implements CompoundButton.OnCheckedChangeListener {
        fdyxd() {
        }

        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            SISSettingsView.this.persistUserSettingsAndProceed();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class hbfhc implements SwipeRightListener {
        hbfhc() {
        }

        @Override // com.good.gd.ui.utils.sis.SwipeRightListener
        public void onSwipeRight() {
            SISSettingsView.this.finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class yfdke implements OnClickListener {
        yfdke() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            SISSettingsView.this.finish();
        }
    }

    public SISSettingsView(Context context, ViewInteractor viewInteractor, ViewCustomizer viewCustomizer, BBDUIObject bBDUIObject) {
        super(context, viewInteractor, viewCustomizer, bBDUIObject);
        String simpleName = SISSettingsView.class.getSimpleName();
        this.TAG = simpleName;
        BISView.mLogger.i(simpleName, "Starting SISSettingsView.");
        this.mUIData = bBDUIObject;
        this._delegate = new ehnkx(this, null);
        inflateLayout(R.layout.layout_sis_settings, this);
        initViews();
        adjustUIBasedOnAvailableEntitlements();
        showPrimerOrSettingsBISUI();
    }

    private void adjustUIBasedOnAvailableEntitlements() {
        LocationInfoProvider locationInfoProvider = this.mLocationInfoProvider;
        if (locationInfoProvider != null && locationInfoProvider.isGeoLocationEntitlementEnabled()) {
            this.mRLSISSwitch.setVisibility(0);
            this.mTVSISParticipationMsg.setText(createDescription(), TextView.BufferType.SPANNABLE);
            return;
        }
        this.mTVSISParticipationMsg.setText(GDLocalizer.getLocalizedString("BISParticipationDisabledText"));
    }

    private CharSequence createDescription() {
        SpannableString updateStringWithBold = updateStringWithBold(GDLocalizer.getLocalizedString("BISSettingsDescText"), PERSONA_PRODUCT_NAME);
        SpannableString updateStringWithBold2 = updateStringWithBold(GDLocalizer.getLocalizedString("BISLearnMoreDescriptionText"), CLOUD_SERVICES_PRODUCT_NAME);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(updateStringWithBold);
        spannableStringBuilder.append((CharSequence) "\n\n");
        spannableStringBuilder.append((CharSequence) updateStringWithBold2);
        return spannableStringBuilder;
    }

    private void initViews() {
        findViewById(R.id.toolbar_back_icon).setOnClickListener(new yfdke());
        this.rootView = findViewById(R.id.sis_settings_root_view);
        this.mRLSISSwitch = (RelativeLayout) findViewById(R.id.rl_sis_switch_layout);
        this.mTVSISParticipationMsg = (TextView) findViewById(R.id.tv_analytics_sis_msg);
        ((TextView) findViewById(R.id.tv_sis_settings_enable_msg)).setText(GDLocalizer.getLocalizedString("BISEnableBIS"));
        this.mSwitchSISParticipation = (Switch) findViewById(R.id.switch_sis_participation);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void persistUserSettingsAndProceed() {
        int currentSISParticipationStatus = this.mActivityStatusListener.getCurrentSISParticipationStatus();
        boolean isChecked = this.mSwitchSISParticipation.isChecked();
        int i = TelemetryManagerConstants.PRIMER_DISAGREED_WITH_NEVER_ASK_AGAIN;
        if (i != currentSISParticipationStatus) {
            i = TelemetryManagerConstants.PRIMER_WAITING_FOR_USER_ACTION;
            if (i != currentSISParticipationStatus) {
                i = isChecked ? TelemetryManagerConstants.PRIMER_AGREED : TelemetryManagerConstants.PRIMER_MAY_BE_LATER;
            } else if (isChecked) {
                i = TelemetryManagerConstants.PRIMER_AGREED;
            }
        } else if (isChecked) {
            i = TelemetryManagerConstants.PRIMER_AGREED;
        }
        UXActionListener uXActionListener = this.mBisUxActionListener;
        if (uXActionListener != null) {
            uXActionListener.onPrimerUpdated(i);
        }
        if (TelemetryManagerConstants.PRIMER_AGREED == i) {
            displayBISUIs(UIBehaviorType.PERMISSION_DIALOG);
        }
        BBDUIEventManager.sendMessage(this.mUIData, BBDUIMessageType.MSG_BIS_STATUS_RESET);
    }

    private void populateDataToViews() {
        this.rootView.setOnTouchListener(SISProxy.getListenerForSwipeRightEvent(getContext(), new hbfhc()));
        this.mSwitchSISParticipation.setChecked(this.mActivityStatusListener.getCurrentSISParticipationStatus() == TelemetryManagerConstants.PRIMER_AGREED);
        this.mSwitchSISParticipation.setOnCheckedChangeListener(this.mSISParticipationStatusChangeListener);
    }

    private SpannableString updateStringWithBold(String str, String str2) {
        SpannableString spannableString = new SpannableString(str);
        int indexOf = str.indexOf(str2);
        spannableString.setSpan(new StyleSpan(1), indexOf, str2.length() + indexOf, 34);
        return spannableString;
    }

    @Override // com.good.gd.ui.BISView
    public void finish() {
        BBDUIEventManager.sendMessage(this.mUIData, BBDUIMessageType.MSG_UI_CANCEL);
        resetBISViewState();
        ActivityStatusListener activityStatusListener = this.mActivityStatusListener;
        if (activityStatusListener != null) {
            activityStatusListener.setIsSettingsActivityRunning(false);
        }
    }

    @Override // com.good.gd.ui.base_ui.GDView
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override // com.good.gd.ui.BISView
    public void showPrimerOrSettingsBISUI() {
        populateDataToViews();
    }
}
