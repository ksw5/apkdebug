package com.good.gd.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.widget.TextViewCompat;
import com.good.gd.ndkproxy.ui.BBDUIHelper;
import com.good.gd.ndkproxy.ui.data.base.BBDUIObject;
import com.good.gd.ndkproxy.ui.event.BBDUIEventManager;
import com.good.gd.ndkproxy.ui.event.BBDUIMessageType;
import com.good.gd.resources.R;
import com.good.gd.ui.base_ui.GDView;
import com.good.gd.ui.utils.sis.ActivityStatusListener;
import com.good.gd.ui.utils.sis.LocationInfoProvider;
import com.good.gd.ui.utils.sis.TelemetryManagerConstants;
import com.good.gd.ui.utils.sis.UIBehaviorType;
import com.good.gd.ui.utils.sis.UXActionListener;
import com.good.gd.utils.GDLocalizer;

/* loaded from: classes.dex */
public class SISPrimerView extends BISView {
    private TextView mAllowBtn;
    private TextView mLearnMoreTV;
    private TextView mMayBeLaterBtn;
    private TextView mNeverAskAgainBtn;
    private LinearLayout mSISPrimerTextContainer = null;
    private RelativeLayout mSISPrimerScreenTopLayout = (RelativeLayout) findViewById(R.id.rl_primer_screen_top);
    private LinearLayout mSISPrimerScreenBottomLayout = (LinearLayout) findViewById(R.id.ll_primer_screen_bottom);

    /* loaded from: classes.dex */
    private final class ehnkx extends GDViewDelegateAdapter {
        private ehnkx() {
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActivityCreate(Bundle bundle) {
            super.onActivityCreate(bundle);
            if (bundle != null) {
                if (bundle.getBoolean("isDeviceLocationCustomDialogVisible")) {
                    SISPrimerView.this.displayDeviceLocSettingsRedirectionDialog();
                    return;
                } else if (bundle.getBoolean("isAppLocationCustomDialogVisible")) {
                    SISPrimerView.this.displayAppLocPermissionRedirectionDialog();
                    return;
                }
            }
            SISPrimerView sISPrimerView = SISPrimerView.this;
            sISPrimerView.displayBISUIs(sISPrimerView.mBehaviorType);
            if (24 <= Build.VERSION.SDK_INT) {
                if (bundle == null && !SISPrimerView.this.isInMultiWindowMode()) {
                    return;
                }
                SISPrimerView.this.handleWindowModeChangesAndUpdateViewsIfRequired();
            }
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActivityDestroy() {
            super.onActivityDestroy();
            SISPrimerView.this.resetBISViewState();
            SISPrimerView.this.dismissCustomDialogs();
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActivityResume() {
            super.onActivityResume();
            SISPrimerView sISPrimerView = SISPrimerView.this;
            if (sISPrimerView.mIsRequireToFinishViewAfterDeviceSettings) {
                sISPrimerView.finish();
            }
            SISPrimerView sISPrimerView2 = SISPrimerView.this;
            if (sISPrimerView2.mIsLocationSettingsUINeededOnAppResume) {
                LocationInfoProvider locationInfoProvider = sISPrimerView2.mLocationInfoProvider;
                if (locationInfoProvider != null && locationInfoProvider.isAppLocationPermissionGranted()) {
                    if (!SISPrimerView.this.mLocationInfoProvider.checkIfLocationServicesEnabled()) {
                        SISPrimerView.this.displayDeviceLocSettingsRedirectionDialog();
                        return;
                    } else {
                        SISPrimerView.this.finish();
                        return;
                    }
                }
                SISPrimerView.this.finish();
            }
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onSaveInstanceState(Bundle bundle) {
            super.onSaveInstanceState(bundle);
            if (bundle != null) {
                Dialog dialog = SISPrimerView.this.mDeviceLocPermDialog;
                boolean z = true;
                bundle.putBoolean("isDeviceLocationCustomDialogVisible", dialog != null && dialog.isShowing());
                Dialog dialog2 = SISPrimerView.this.mAppLocPermDialog;
                if (dialog2 == null || !dialog2.isShowing()) {
                    z = false;
                }
                bundle.putBoolean("isAppLocationCustomDialogVisible", z);
            }
        }

        /* synthetic */ ehnkx(SISPrimerView sISPrimerView, hbfhc hbfhcVar) {
            this();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class fdyxd implements OnClickListener {
        fdyxd() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            BBDUIEventManager.sendMessage(SISPrimerView.this.mUIData, BBDUIMessageType.MSG_UI_CANCEL);
            ActivityStatusListener activityStatusListener = SISPrimerView.this.mActivityStatusListener;
            if (activityStatusListener != null) {
                activityStatusListener.setIsPrimerScreenAlreadyShown(true);
            }
            UXActionListener uXActionListener = SISPrimerView.this.mBisUxActionListener;
            if (uXActionListener != null) {
                uXActionListener.onPrimerUpdated(TelemetryManagerConstants.PRIMER_DISAGREED_WITH_NEVER_ASK_AGAIN);
            }
            SISPrimerView.this.finish();
            SISPrimerView.this.disableButtons();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class hbfhc implements OnClickListener {
        hbfhc() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            ActivityStatusListener activityStatusListener = SISPrimerView.this.mActivityStatusListener;
            if (activityStatusListener != null) {
                activityStatusListener.setIsPrimerScreenAlreadyShown(true);
            }
            UXActionListener uXActionListener = SISPrimerView.this.mBisUxActionListener;
            if (uXActionListener != null) {
                uXActionListener.onPrimerUpdated(TelemetryManagerConstants.PRIMER_AGREED);
            }
            BBDUIEventManager.sendMessage(SISPrimerView.this.mUIData, BBDUIMessageType.MSG_UI_OK);
            BISView.mLogger.v(BISView.TAG, "BIS Primer Allowed.");
            SISPrimerView.this.displayBISUIs(UIBehaviorType.PERMISSION_DIALOG);
            SISPrimerView.this.disableButtons();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class yfdke implements OnClickListener {
        yfdke() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            BBDUIEventManager.sendMessage(SISPrimerView.this.mUIData, BBDUIMessageType.MSG_DISCLAIMER_MAYBE_LATER);
            ActivityStatusListener activityStatusListener = SISPrimerView.this.mActivityStatusListener;
            if (activityStatusListener != null) {
                activityStatusListener.setIsPrimerScreenAlreadyShown(true);
            }
            UXActionListener uXActionListener = SISPrimerView.this.mBisUxActionListener;
            if (uXActionListener != null) {
                uXActionListener.onPrimerUpdated(TelemetryManagerConstants.PRIMER_MAY_BE_LATER);
            }
            SISPrimerView.this.finish();
            SISPrimerView.this.disableButtons();
        }
    }

    public SISPrimerView(Context context, ViewInteractor viewInteractor, ViewCustomizer viewCustomizer, BBDUIObject bBDUIObject, int i) {
        super(context, viewInteractor, viewCustomizer, bBDUIObject);
        BISView.mLogger.i(BISView.TAG, "Starting SISPrimerView.");
        this._delegate = new ehnkx(this, null);
        this.mBehaviorType = UIBehaviorType.getTypeForInt(i);
        this.mIsMessageToCoreNeededForBISUI = true;
        this.mIsRequireToFinishView = true;
        inflateLayout(R.layout.layout_analytics_primer_screen, this);
        initLocalization();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void disableButtons() {
        this.mAllowBtn.setEnabled(false);
        this.mMayBeLaterBtn.setEnabled(false);
        this.mNeverAskAgainBtn.setEnabled(false);
    }

    private void displayAndHandlePrimerUI() {
        this.mLearnMoreTV.setOnClickListener(BBDUIHelper.getClickListenerForMessageType(this.mUIData, BBDUIMessageType.MSG_UI_HELP));
        this.mAllowBtn.setOnClickListener(new hbfhc());
        this.mMayBeLaterBtn.setOnClickListener(new yfdke());
        this.mNeverAskAgainBtn.setOnClickListener(new fdyxd());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleWindowModeChangesAndUpdateViewsIfRequired() {
        RelativeLayout relativeLayout;
        if (isTablet() || 2 != getResources().getConfiguration().orientation || (relativeLayout = this.mSISPrimerScreenTopLayout) == null || relativeLayout.getVisibility() != 0) {
            return;
        }
        boolean isInMultiWindowMode = isInMultiWindowMode();
        BISView.mLogger.sensitiveI(BISView.TAG, "Applying layout modifications based on the window mode.");
        updateViewMarginAndPadding(isInMultiWindowMode);
    }

    private void initLocalization() {
        int dimensionPixelSize;
        ((TextView) findViewById(R.id.tv_sis_loc_approval_msg)).setText(GDLocalizer.getLocalizedString("BISLocDescText"));
        ((TextView) findViewById(R.id.tv_sis_loc_desc)).setText(GDLocalizer.getLocalizedString("BISLocDescMoreText"));
        ((TextView) findViewById(R.id.tv_spark_security_enabled)).setText(GDLocalizer.getLocalizedString("BISParticipationEnableText"));
        this.mSISPrimerTextContainer = (LinearLayout) findViewById(R.id.cl_primer_text);
        this.mAllowBtn = (TextView) findViewById(R.id.btn_allow);
        this.mMayBeLaterBtn = (TextView) findViewById(R.id.btn_may_be_later);
        this.mNeverAskAgainBtn = (TextView) findViewById(R.id.btn_never_ask_again);
        TextView textView = (TextView) findViewById(R.id.tv_learn_more);
        this.mLearnMoreTV = textView;
        if (this.mAllowBtn == null || this.mMayBeLaterBtn == null || this.mNeverAskAgainBtn == null || textView == null) {
            BISView.mLogger.w(BISView.TAG, "Unable to instantiate components of BIS Primer Screen.");
            finish();
        }
        this.mAllowBtn.setText(GDLocalizer.getLocalizedString("BISAllow"));
        this.mMayBeLaterBtn.setText(GDLocalizer.getLocalizedString("BISMayBeLater"));
        this.mNeverAskAgainBtn.setText(GDLocalizer.getLocalizedString("BISNeverAskAgain"));
        this.mLearnMoreTV.setText(GDLocalizer.getLocalizedString("BISLearnMore"));
        if (2 == getResources().getConfiguration().orientation) {
            dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.btn_text_size_land);
        } else {
            dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.btn_text_size);
        }
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(this.mAllowBtn, 12, dimensionPixelSize, 1, 0);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(this.mMayBeLaterBtn, 12, dimensionPixelSize, 1, 0);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(this.mNeverAskAgainBtn, 12, dimensionPixelSize, 1, 0);
    }

    private void updateViewMarginAndPadding(boolean z) {
        int dimensionPixelSize;
        int dimensionPixelSize2;
        int dimensionPixelSize3;
        int dimensionPixelSize4;
        TypedValue typedValue = new TypedValue();
        Resources resources = getResources();
        resources.getValue(z ? R.dimen.primer_center_constraint_land_split : R.dimen.primer_weight_top_land, typedValue, true);
        float f = typedValue.getFloat();
        if (z) {
            dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.primer_btn_left_right_margin_land_split);
            dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.screen_text_left_right_margin_land_split);
            dimensionPixelSize3 = resources.getDimensionPixelSize(R.dimen.screen_text_left_right_margin_land_split);
            dimensionPixelSize4 = resources.getDimensionPixelSize(R.dimen.btn_text_size_land_split);
        } else {
            dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.primer_btn_left_right_margin_land);
            dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.screen_text_left_right_margin_land);
            dimensionPixelSize3 = resources.getDimensionPixelSize(R.dimen.screen_text_left_right_margin_land);
            dimensionPixelSize4 = resources.getDimensionPixelSize(R.dimen.btn_text_size_land);
        }
        TextView textView = this.mAllowBtn;
        if (textView != null) {
            ((LinearLayout.LayoutParams) textView.getLayoutParams()).setMarginEnd(dimensionPixelSize);
            this.mAllowBtn.setTextSize(0, dimensionPixelSize4);
            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(this.mAllowBtn, 12, dimensionPixelSize4, 1, 0);
        }
        TextView textView2 = this.mLearnMoreTV;
        if (textView2 != null) {
            ((LinearLayout.LayoutParams) textView2.getLayoutParams()).setMarginStart(dimensionPixelSize3);
        }
        TextView textView3 = this.mMayBeLaterBtn;
        if (textView3 != null) {
            textView3.setTextSize(0, dimensionPixelSize4);
            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(this.mMayBeLaterBtn, 12, dimensionPixelSize4, 1, 0);
        }
        TextView textView4 = this.mNeverAskAgainBtn;
        if (textView4 != null) {
            ((LinearLayout.LayoutParams) textView4.getLayoutParams()).setMarginStart(dimensionPixelSize);
            this.mNeverAskAgainBtn.setTextSize(0, dimensionPixelSize4);
            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(this.mNeverAskAgainBtn, 12, dimensionPixelSize4, 1, 0);
        }
        LinearLayout linearLayout = this.mSISPrimerTextContainer;
        if (linearLayout != null) {
            linearLayout.setPadding(dimensionPixelSize2, 0, dimensionPixelSize2, 0);
        }
        if (this.mSISPrimerScreenTopLayout == null || this.mSISPrimerScreenBottomLayout == null) {
            return;
        }
        this.mSISPrimerScreenTopLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, 0, f));
        this.mSISPrimerScreenBottomLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, 0, 1.0f - f));
    }

    @Override // com.good.gd.ui.BISView
    public void finish() {
        BBDUIEventManager.sendMessage(this.mUIData, BBDUIMessageType.MSG_DISCLAIMER_COMPLETE);
        resetBISViewState();
    }

    @Override // com.good.gd.ui.base_ui.GDView
    public void onBackPressed() {
        super.onBackPressed();
        BBDUIEventManager.sendMessage(this.mUIData, BBDUIMessageType.MSG_DISCLAIMER_MAYBE_LATER);
        ActivityStatusListener activityStatusListener = this.mActivityStatusListener;
        if (activityStatusListener != null) {
            activityStatusListener.setIsPrimerScreenAlreadyShown(true);
        }
        UXActionListener uXActionListener = this.mBisUxActionListener;
        if (uXActionListener != null) {
            uXActionListener.onPrimerUpdated(TelemetryManagerConstants.PRIMER_MAY_BE_LATER);
        }
        finish();
    }

    @Override // com.good.gd.ui.BISView
    public void showPrimerOrSettingsBISUI() {
        displayAndHandlePrimerUI();
    }
}
