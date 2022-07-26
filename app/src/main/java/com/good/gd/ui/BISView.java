package com.good.gd.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.widget.TextView;
import com.good.gd.ndkproxy.ui.BBDUILocalizationHelper;
import com.good.gd.ndkproxy.ui.data.base.BBDUIObject;
import com.good.gd.ndkproxy.ui.event.BBDUIEventManager;
import com.good.gd.ndkproxy.ui.event.BBDUIMessageType;
import com.good.gd.resources.R;
import com.good.gd.ui.base_ui.GDView;
import com.good.gd.ui.dialogs.GDDialogState;
import com.good.gd.ui.utils.sis.ActivityStatusListener;
import com.good.gd.ui.utils.sis.AnalyticsLogger;
import com.good.gd.ui.utils.sis.LocationDialogListener;
import com.good.gd.ui.utils.sis.LocationInfoProvider;
import com.good.gd.ui.utils.sis.LocationUIInteractor;
import com.good.gd.ui.utils.sis.SISProxy;
import com.good.gd.ui.utils.sis.UIBehaviorType;
import com.good.gd.ui.utils.sis.UXActionListener;
import com.good.gd.utils.GDLocalizer;

/* loaded from: classes.dex */
public abstract class BISView extends GDView implements LocationUIInteractor {
    protected static final String IS_APP_LOCATION_CUSTOM_DIALOG_VISIBLE = "isAppLocationCustomDialogVisible";
    protected static final String IS_DEVICE_LOCATION_CUSTOM_DIALOG_VISIBLE = "isDeviceLocationCustomDialogVisible";
    protected static final int REQUEST_LOCATION_ENABLED_OR_UPGRADE = 1111;
    protected static final int REQUEST_LOCATION_PERMISSION = 1110;
    protected static String TAG;
    protected static AnalyticsLogger mLogger = SISProxy.getLogger();
    protected String LOCATION_PROVIDER_TAG;
    protected ActivityStatusListener mActivityStatusListener;
    protected Dialog mAppLocPermDialog;
    protected UIBehaviorType mBehaviorType;
    protected UXActionListener mBisUxActionListener;
    protected Dialog mDeviceLocPermDialog;
    protected boolean mIsLocationSettingsUINeededOnAppResume;
    protected boolean mIsMessageToCoreNeededForBISUI = false;
    protected boolean mIsRequireToFinishView = false;
    protected boolean mIsRequireToFinishViewAfterDeviceSettings;
    protected LocationDialogListener mLocationDialogListener;
    protected LocationInfoProvider mLocationInfoProvider;
    protected BBDUIObject mUIData;
    protected ViewCustomizer mViewCustomizer;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class ehnkx implements OnClickListener {
        ehnkx() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            ActivityStatusListener activityStatusListener = BISView.this.mActivityStatusListener;
            if (activityStatusListener != null) {
                activityStatusListener.setIsCustomPermissionDialogAlreadyShown(true);
            }
            BISView bISView = BISView.this;
            if (bISView.mIsMessageToCoreNeededForBISUI) {
                BBDUIEventManager.sendMessage(bISView.mUIData, BBDUIMessageType.MSG_UI_OK);
            }
            LocationDialogListener locationDialogListener = BISView.this.mLocationDialogListener;
            if (locationDialogListener != null) {
                locationDialogListener.onLocationPermissionUpdated(false);
            }
            ((GDView) BISView.this).viewInteractor.cancelDialog();
            BISView bISView2 = BISView.this;
            if (bISView2.mIsRequireToFinishView) {
                bISView2.finish();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class fdyxd implements OnClickListener {
        fdyxd() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            ActivityStatusListener activityStatusListener = BISView.this.mActivityStatusListener;
            if (activityStatusListener != null) {
                activityStatusListener.setIsCustomPermissionDialogAlreadyShown(true);
            }
            Intent intent = new Intent();
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            Context applicationContext = BISView.this.getContext().getApplicationContext();
            intent.setData(Uri.fromParts("package", applicationContext.getPackageName(), null));
            if (intent.resolveActivity(applicationContext.getPackageManager()) != null) {
                BISView.this.getContext().startActivity(intent);
            } else {
                BISView.mLogger.w(BISView.TAG, "Unable to open application settings.");
            }
            BBDUIEventManager.sendMessage(BISView.this.mUIData, BBDUIMessageType.MSG_UI_OK);
            ((GDView) BISView.this).viewInteractor.cancelDialog();
            BISView bISView = BISView.this;
            if (bISView.mIsRequireToFinishView && bISView.mLocationInfoProvider.checkIfLocationServicesEnabled()) {
                BISView.this.mIsRequireToFinishViewAfterDeviceSettings = true;
                return;
            }
            BISView bISView2 = BISView.this;
            if (bISView2.mIsMessageToCoreNeededForBISUI) {
                BBDUIEventManager.sendMessage(bISView2.mUIData, BBDUIMessageType.MSG_DISCLAIMER_ADDITIONAL_SETUP);
            }
            BISView.this.mIsLocationSettingsUINeededOnAppResume = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class hbfhc implements OnClickListener {
        hbfhc() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            ActivityStatusListener activityStatusListener = BISView.this.mActivityStatusListener;
            if (activityStatusListener != null) {
                activityStatusListener.setIsDeviceLocationDialogAlreadyShown(true);
            }
            Intent intent = new Intent("android.settings.LOCATION_SOURCE_SETTINGS");
            if (intent.resolveActivity(BISView.this.getContext().getApplicationContext().getPackageManager()) != null) {
                BISView.this.getContext().startActivity(intent);
            } else {
                BISView.mLogger.w(BISView.TAG, "Unable to open Device location services settings.");
            }
            BBDUIEventManager.sendMessage(BISView.this.mUIData, BBDUIMessageType.MSG_UI_OK);
            ((GDView) BISView.this).viewInteractor.cancelDialog();
            BISView bISView = BISView.this;
            if (bISView.mIsRequireToFinishView) {
                bISView.mIsRequireToFinishViewAfterDeviceSettings = true;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class yfdke implements OnClickListener {
        yfdke() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            BISView bISView = BISView.this;
            if (bISView.mIsMessageToCoreNeededForBISUI) {
                BBDUIEventManager.sendMessage(bISView.mUIData, BBDUIMessageType.MSG_UI_OK);
            }
            ActivityStatusListener activityStatusListener = BISView.this.mActivityStatusListener;
            if (activityStatusListener != null) {
                activityStatusListener.setIsDeviceLocationDialogAlreadyShown(true);
            }
            LocationDialogListener locationDialogListener = BISView.this.mLocationDialogListener;
            if (locationDialogListener != null) {
                locationDialogListener.onLocationSettingsUpdated(false);
            }
            ((GDView) BISView.this).viewInteractor.cancelDialog();
            BISView bISView2 = BISView.this;
            if (bISView2.mIsRequireToFinishView) {
                bISView2.finish();
            }
        }
    }

    public BISView(Context context, ViewInteractor viewInteractor, ViewCustomizer viewCustomizer, BBDUIObject bBDUIObject) {
        super(context, viewInteractor, viewCustomizer);
        TAG = (this instanceof SISPrimerView ? SISPrimerView.class : SISSettingsView.class).getName();
        this.mUIData = bBDUIObject;
        this.mViewCustomizer = viewCustomizer;
        this.mActivityStatusListener = SISProxy.getActivityStatusListener();
        LocationInfoProvider locationInfoProvider = SISProxy.getLocationInfoProvider();
        this.mLocationInfoProvider = locationInfoProvider;
        this.LOCATION_PROVIDER_TAG = locationInfoProvider.getLocationProviderAPIName();
        this.mBisUxActionListener = SISProxy.getUXActionListener();
        this.mLocationDialogListener = SISProxy.getLocationDialogListener();
        this.mIsLocationSettingsUINeededOnAppResume = false;
        this.mIsRequireToFinishViewAfterDeviceSettings = false;
        boolean isAppLocationPermissionGranted = this.mLocationInfoProvider.isAppLocationPermissionGranted();
        boolean isNeverAskAgainOptionSelectedSystemCheck = this.mLocationInfoProvider.isNeverAskAgainOptionSelectedSystemCheck(getActivity());
        if (true == isAppLocationPermissionGranted || true == isNeverAskAgainOptionSelectedSystemCheck) {
            return;
        }
        this.mLocationInfoProvider.setDenyOptionSelectedExplicitly(true);
    }

    public void dismissCustomDialogs() {
        Dialog dialog = this.mDeviceLocPermDialog;
        if (dialog != null && dialog.isShowing()) {
            this.mDeviceLocPermDialog.dismiss();
            this.mDeviceLocPermDialog = null;
        }
        Dialog dialog2 = this.mAppLocPermDialog;
        if (dialog2 == null || !dialog2.isShowing()) {
            return;
        }
        this.mAppLocPermDialog.dismiss();
        this.mAppLocPermDialog = null;
    }

    public void displayAppLocPermissionRedirectionDialog() {
        Dialog dialog = this.mAppLocPermDialog;
        if (dialog != null && dialog.isShowing()) {
            mLogger.v(TAG, "App location permission custom dialog is already showing.");
            return;
        }
        Dialog dialog2 = new Dialog(getContext(), R.style.BISAlertDialogStyle);
        this.mAppLocPermDialog = dialog2;
        dialog2.setContentView(R.layout.layout_app_loc_perm_suggestion);
        this.mAppLocPermDialog.setCancelable(false);
        ((TextView) this.mAppLocPermDialog.findViewById(R.id.tv_app_loc_perm_desc)).setText(GDLocalizer.getLocalizedString("BISApplicationLocationPermissionAndroid"));
        TextView textView = (TextView) this.mAppLocPermDialog.findViewById(R.id.btn_settings);
        textView.setText(GDLocalizer.getLocalizedString("BISLocationAlertSettings"));
        textView.setOnClickListener(new fdyxd());
        TextView textView2 = (TextView) this.mAppLocPermDialog.findViewById(R.id.btn_cancel);
        textView2.setText(BBDUILocalizationHelper.getLocalizedCancel());
        textView2.setOnClickListener(new ehnkx());
        GDDialogState.getInstance().showCustomDialog(this.mAppLocPermDialog, this.viewInteractor);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0044  */
    /* JADX WARN: Removed duplicated region for block: B:32:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void displayBISUIs(UIBehaviorType r1) {
        /*
            r0 = this;
            int r1 = r1.ordinal()
            switch(r1) {
                case 0: goto L8;
                case 1: goto L16;
                case 2: goto L32;
                case 3: goto L32;
                default: goto L7;
            }
        L7:
            goto L47
        L8:
            com.good.gd.ui.utils.sis.ActivityStatusListener r1 = r0.mActivityStatusListener
            if (r1 == 0) goto L16
            boolean r1 = r1.isRequireToShowSISPrimerScreen()
            if (r1 == 0) goto L16
            r0.showPrimerOrSettingsBISUI()
            goto L47
        L16:
            com.good.gd.ui.utils.sis.ActivityStatusListener r1 = r0.mActivityStatusListener
            if (r1 == 0) goto L24
            boolean r1 = r1.isRequireToShowCustomPermissionDialog()
            if (r1 == 0) goto L24
            r0.displayAppLocPermissionRedirectionDialog()
            goto L47
        L24:
            com.good.gd.ui.utils.sis.ActivityStatusListener r1 = r0.mActivityStatusListener
            if (r1 == 0) goto L32
            boolean r1 = r1.isRequireToShowSystemPermissionDialog()
            if (r1 == 0) goto L32
            r0.requestLocationPermission()
            goto L47
        L32:
            com.good.gd.ui.utils.sis.ActivityStatusListener r1 = r0.mActivityStatusListener
            if (r1 == 0) goto L40
            boolean r1 = r1.isRequireToShowDeviceLocationSettingsDialog()
            if (r1 == 0) goto L40
            r0.displayLocationSettingsBISUI()
            goto L47
        L40:
            boolean r1 = r0.mIsRequireToFinishView
            if (r1 == 0) goto L47
            r0.finish()
        L47:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.good.gd.ui.BISView.displayBISUIs(com.good.gd.ui.utils.sis.UIBehaviorType):void");
    }

    public void displayDeviceLocSettingsRedirectionDialog() {
        this.mIsLocationSettingsUINeededOnAppResume = false;
        Dialog dialog = this.mDeviceLocPermDialog;
        if (dialog != null && dialog.isShowing()) {
            mLogger.v(TAG, "Device location settings custom dialog is already showing.");
            return;
        }
        Dialog dialog2 = new Dialog(getActivity(), R.style.BISAlertDialogStyle);
        this.mDeviceLocPermDialog = dialog2;
        dialog2.setContentView(R.layout.layout_device_loc_perm_suggestion);
        this.mDeviceLocPermDialog.setCancelable(false);
        ((TextView) this.mDeviceLocPermDialog.findViewById(R.id.tv_dev_loc_settings_desc)).setText(GDLocalizer.getLocalizedString("BISDeviceLocationServicesDisableAndroid"));
        TextView textView = (TextView) this.mDeviceLocPermDialog.findViewById(R.id.btn_settings);
        textView.setText(GDLocalizer.getLocalizedString("BISLocationAlertSettings"));
        textView.setOnClickListener(new hbfhc());
        TextView textView2 = (TextView) this.mDeviceLocPermDialog.findViewById(R.id.btn_cancel);
        textView2.setText(BBDUILocalizationHelper.getLocalizedCancel());
        textView2.setOnClickListener(new yfdke());
        GDDialogState.getInstance().showCustomDialog(this.mDeviceLocPermDialog, this.viewInteractor);
    }

    public void displayLocationSettingsBISUI() {
        if (this.mIsMessageToCoreNeededForBISUI) {
            BBDUIEventManager.sendMessage(this.mUIData, BBDUIMessageType.MSG_DISCLAIMER_ADDITIONAL_SETUP);
        }
        if (this.mLocationInfoProvider.isGoogleLocationProvider()) {
            this.mLocationInfoProvider.checkLocationSettingsWithGoogleAPI(this, REQUEST_LOCATION_ENABLED_OR_UPGRADE);
        } else {
            displayDeviceLocSettingsRedirectionDialog();
        }
    }

    public abstract void finish();

    @Override // com.good.gd.ui.utils.sis.LocationUIInteractor
    public Activity getActivity() {
        Context context = getContext();
        if (context instanceof Activity) {
            return (Activity) context;
        }
        return null;
    }

    @Override // com.good.gd.ui.utils.sis.LocationUIInteractor
    public LocationDialogListener getLocationDialogListener() {
        return this.mLocationDialogListener;
    }

    @Override // com.good.gd.ui.utils.sis.LocationUIInteractor
    public String getLocationTag() {
        return this.LOCATION_PROVIDER_TAG;
    }

    protected boolean isDeviceOSAndroid11OrHigher() {
        return Build.VERSION.SDK_INT > 29;
    }

    @Override // com.good.gd.ui.base_ui.GDView
    public void onActivityResult(int i, int i2, Intent intent) {
        if (REQUEST_LOCATION_ENABLED_OR_UPGRADE == i) {
            if (i2 == -1) {
                if (this.mIsMessageToCoreNeededForBISUI) {
                    BBDUIEventManager.sendMessage(this.mUIData, BBDUIMessageType.MSG_UI_OK);
                }
                ActivityStatusListener activityStatusListener = this.mActivityStatusListener;
                if (activityStatusListener != null) {
                    activityStatusListener.setIsDeviceLocationDialogAlreadyShown(true);
                }
                LocationDialogListener locationDialogListener = this.mLocationDialogListener;
                if (locationDialogListener != null) {
                    locationDialogListener.onLocationSettingsUpdated(true);
                }
                if (!this.mIsRequireToFinishView) {
                    return;
                }
                finish();
            } else if (i2 != 0) {
                if (!this.mIsRequireToFinishView) {
                    return;
                }
                finish();
            } else {
                if (this.mIsMessageToCoreNeededForBISUI) {
                    BBDUIEventManager.sendMessage(this.mUIData, BBDUIMessageType.MSG_UI_OK);
                }
                ActivityStatusListener activityStatusListener2 = this.mActivityStatusListener;
                if (activityStatusListener2 != null) {
                    activityStatusListener2.setIsDeviceLocationDialogAlreadyShown(true);
                }
                LocationDialogListener locationDialogListener2 = this.mLocationDialogListener;
                if (locationDialogListener2 != null) {
                    locationDialogListener2.onLocationSettingsUpdated(false);
                }
                if (!this.mIsRequireToFinishView) {
                    return;
                }
                finish();
            }
        }
    }

    @Override // com.good.gd.ui.base_ui.GDView
    public void onPermissions(int i, String[] strArr, int[] iArr) {
        ActivityStatusListener activityStatusListener;
        if (strArr == null || iArr == null || strArr.length == 0 || iArr.length == 0) {
            mLogger.v(TAG, this.LOCATION_PROVIDER_TAG + ": Vague callback for location permission.");
        } else if (i != REQUEST_LOCATION_PERMISSION) {
        } else {
            ActivityStatusListener activityStatusListener2 = this.mActivityStatusListener;
            if (activityStatusListener2 != null) {
                activityStatusListener2.setIsSystemPermissionDialogAlreadyShown(true);
            }
            if (iArr[0] == 0) {
                LocationInfoProvider locationInfoProvider = this.mLocationInfoProvider;
                if (locationInfoProvider == null || !locationInfoProvider.isAppLocationPermissionGranted()) {
                    return;
                }
                this.mLocationInfoProvider.setDenyOptionSelectedExplicitly(false);
                mLogger.i(TAG, this.LOCATION_PROVIDER_TAG + " : Location Permission Granted.");
                LocationDialogListener locationDialogListener = this.mLocationDialogListener;
                if (locationDialogListener != null) {
                    locationDialogListener.onLocationPermissionUpdated(true);
                }
                if (this.mIsMessageToCoreNeededForBISUI) {
                    BBDUIEventManager.sendMessage(this.mUIData, BBDUIMessageType.MSG_UI_OK);
                }
                displayBISUIs(this.mLocationInfoProvider.isGoogleLocationProvider() ? UIBehaviorType.SIS_GOOGLE_DEV_LOC_SETTINGS_DIALOG : UIBehaviorType.SIS_FRAMEWORK_DEV_LOC_SETTINGS_DIALOG);
                return;
            }
            mLogger.i(TAG, this.LOCATION_PROVIDER_TAG + " : Location Permission Denied.");
            if (this.mIsMessageToCoreNeededForBISUI) {
                BBDUIEventManager.sendMessage(this.mUIData, BBDUIMessageType.MSG_UI_OK);
            }
            LocationInfoProvider locationInfoProvider2 = this.mLocationInfoProvider;
            boolean z = locationInfoProvider2 != null && locationInfoProvider2.isNeverAskAgainOptionSelectedSystemCheck(getActivity());
            if (isDeviceOSAndroid11OrHigher()) {
                LocationInfoProvider locationInfoProvider3 = this.mLocationInfoProvider;
                if (locationInfoProvider3 != null && locationInfoProvider3.isDenyOptionSelectedExplicitly()) {
                    this.mLocationInfoProvider.setNeverAskAgainOptionSelected(z);
                }
                LocationInfoProvider locationInfoProvider4 = this.mLocationInfoProvider;
                if (locationInfoProvider4 != null && true != z) {
                    locationInfoProvider4.setDenyOptionSelectedExplicitly(true);
                }
            } else {
                LocationInfoProvider locationInfoProvider5 = this.mLocationInfoProvider;
                if (locationInfoProvider5 != null) {
                    locationInfoProvider5.setNeverAskAgainOptionSelected(z);
                }
            }
            LocationDialogListener locationDialogListener2 = this.mLocationDialogListener;
            if (locationDialogListener2 != null) {
                locationDialogListener2.onLocationPermissionUpdated(false);
            }
            if (z && (activityStatusListener = this.mActivityStatusListener) != null) {
                activityStatusListener.setIsCustomPermissionDialogAlreadyShown(true);
            }
            if (!this.mIsRequireToFinishView) {
                return;
            }
            finish();
        }
    }

    @Override // com.good.gd.ui.utils.sis.LocationUIInteractor
    public void requestCloseView() {
        finish();
    }

    @Override // com.good.gd.ui.utils.sis.LocationUIInteractor
    public void requestLocationPermission() {
        if (this.mIsMessageToCoreNeededForBISUI) {
            BBDUIEventManager.sendMessage(this.mUIData, BBDUIMessageType.MSG_DISCLAIMER_ADDITIONAL_SETUP);
        }
        mLogger.v(TAG, this.LOCATION_PROVIDER_TAG + " : Requesting Location Permission via Activity.");
        getActivity().requestPermissions(new String[]{"android.permission.ACCESS_FINE_LOCATION"}, REQUEST_LOCATION_PERMISSION);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void resetBISViewState() {
        mLogger.v(TAG, this.LOCATION_PROVIDER_TAG + " : Reset BIS View State.");
        this.mIsLocationSettingsUINeededOnAppResume = false;
        this.mIsMessageToCoreNeededForBISUI = false;
        this.mIsRequireToFinishView = false;
    }

    protected abstract void showPrimerOrSettingsBISUI();
}
