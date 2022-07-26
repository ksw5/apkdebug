package com.good.gd.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.good.gd.ndkproxy.ui.BBDUIHelper;
import com.good.gd.ndkproxy.ui.CoreUI;
import com.good.gd.ndkproxy.ui.data.base.BaseUI;
import com.good.gd.ui.base_ui.GDView;
import com.good.gd.ui.utils.permissions.ButtonClickListener;
import com.good.gd.ui.utils.permissions.PermissionsUtils;
import com.good.gd.ui.utils.sis.LocationInfoProvider;
import com.good.gd.ui.utils.sis.SISProxy;
import com.good.gd.utils.GDLocalizer;
import java.util.LinkedList;

/* loaded from: classes.dex */
public abstract class InsecureWiFiPermissionRequestBaseView extends PermissionRequestView {
    private static final String BACKGROUND_PERMISSION = "android.permission.ACCESS_BACKGROUND_LOCATION";
    private static final int PERMISSIONS_REQUEST_CODE = 111;
    private Context mContext;
    private BaseUI mUIData;
    private String permissionToBeGranted = null;
    private boolean shouldCheckOnResume = false;
    protected boolean mIsLocationSettingsUIShowed = false;
    protected LocationInfoProvider mLocationInfoProvider = SISProxy.getLocationInfoProvider();

    /* loaded from: classes.dex */
    private final class fdyxd extends GDViewDelegateAdapter {
        private fdyxd() {
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActivityResume() {
            super.onActivityResume();
            InsecureWiFiPermissionRequestBaseView insecureWiFiPermissionRequestBaseView = InsecureWiFiPermissionRequestBaseView.this;
            if (insecureWiFiPermissionRequestBaseView.mIsLocationSettingsUIShowed) {
                insecureWiFiPermissionRequestBaseView.mIsLocationSettingsUIShowed = false;
                insecureWiFiPermissionRequestBaseView.finishPermissionsRequesting();
            }
            if (InsecureWiFiPermissionRequestBaseView.this.permissionToBeGranted == null || !InsecureWiFiPermissionRequestBaseView.this.shouldCheckOnResume) {
                return;
            }
            InsecureWiFiPermissionRequestBaseView insecureWiFiPermissionRequestBaseView2 = InsecureWiFiPermissionRequestBaseView.this;
            if (insecureWiFiPermissionRequestBaseView2.mPermissionsRequestor.isPermissionGranted(insecureWiFiPermissionRequestBaseView2.permissionToBeGranted)) {
                InsecureWiFiPermissionRequestBaseView.this.requestNextPermissions();
            } else {
                InsecureWiFiPermissionRequestBaseView.this.mPermissionsRequestor.cancelRequesting();
            }
        }

        /* synthetic */ fdyxd(InsecureWiFiPermissionRequestBaseView insecureWiFiPermissionRequestBaseView, hbfhc hbfhcVar) {
            this();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class hbfhc implements ButtonClickListener {
        hbfhc() {
        }

        @Override // com.good.gd.ui.utils.permissions.ButtonClickListener
        public void onButtonClicked(Activity activity, long j) {
            InsecureWiFiPermissionRequestBaseView.this.shouldCheckOnResume = true;
            BBDUIHelper.ok(j);
            Intent intent = new Intent();
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", InsecureWiFiPermissionRequestBaseView.this.mContext.getPackageName(), null));
            InsecureWiFiPermissionRequestBaseView.this.mContext.startActivity(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class yfdke implements ButtonClickListener {
        yfdke() {
        }

        @Override // com.good.gd.ui.utils.permissions.ButtonClickListener
        public void onButtonClicked(Activity activity, long j) {
            BBDUIHelper.ok(j);
            InsecureWiFiPermissionRequestBaseView.this.requestNextPermissions();
        }
    }

    public InsecureWiFiPermissionRequestBaseView(Context context, ViewInteractor viewInteractor, ViewCustomizer viewCustomizer, BaseUI baseUI) {
        super(context, viewInteractor, viewCustomizer);
        this.mUIData = baseUI;
        this.mContext = context;
        this._delegate = new fdyxd(this, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishPermissionsRequesting() {
        if (!this.mLocationInfoProvider.checkIfLocationServicesEnabled()) {
            displayDeviceLocSettingsRedirectionDialog();
        } else {
            BBDUIHelper.ok(this.mUIData.getCoreHandle());
        }
    }

    private void openDialogUI(String str) {
        CoreUI.requestPermissionAlertUI(GDLocalizer.getLocalizedString("MTD Insecure WiFi: Background Permission Alert UI: Title"), GDLocalizer.getLocalizedString("MTD Insecure WiFi: Permission Denied Alert UI: Message"), isHighPriority(), new hbfhc());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void displayDeviceLocSettingsRedirectionDialog() {
        this.mIsLocationSettingsUIShowed = true;
    }

    public void displayPermissionAlert() {
        CoreUI.requestPermissionAlertUI(GDLocalizer.getLocalizedString("MTD Insecure WiFi: Background Permission Alert UI: Title"), GDLocalizer.getLocalizedString("MTD Insecure WiFi: Background Permission Alert UI: Message"), isHighPriority(), new yfdke());
    }

    public Activity getActivity() {
        Context context = getContext();
        if (context instanceof Activity) {
            return (Activity) context;
        }
        return null;
    }

    protected LinkedList<String> getPermissionsToRequestList() {
        LinkedList<String> linkedList = new LinkedList<>();
        linkedList.add("android.permission.ACCESS_FINE_LOCATION");
        if (PermissionsUtils.shouldCheckBackgroundLocationPermission(this.mContext, 29, 29) && PermissionsUtils.hasPermissionInManifest(BACKGROUND_PERMISSION, this.mContext)) {
            linkedList.add(BACKGROUND_PERMISSION);
        }
        return linkedList;
    }

    protected abstract boolean isHighPriority();

    @Override // com.good.gd.ui.PermissionRequestView, com.good.gd.ui.utils.permissions.PermissionsListener
    public void onAllPermissionsRequested() {
        super.onAllPermissionsRequested();
        this.permissionToBeGranted = null;
        this.shouldCheckOnResume = false;
        finishPermissionsRequesting();
    }

    @Override // com.good.gd.ui.PermissionRequestView, com.good.gd.ui.utils.permissions.PermissionsListener
    public void onPermissionDenied(String str) {
        super.onPermissionDenied(str);
        String str2 = this.permissionToBeGranted;
        if (str2 != null && str2.equals(str)) {
            this.permissionToBeGranted = null;
            this.shouldCheckOnResume = false;
            this.mPermissionsRequestor.cancelRequesting();
            return;
        }
        showExplanation(str);
    }

    @Override // com.good.gd.ui.PermissionRequestView, com.good.gd.ui.utils.permissions.PermissionsListener
    public void onPermissionGranted(String str) {
        super.onPermissionGranted(str);
        this.permissionToBeGranted = null;
        this.shouldCheckOnResume = false;
        this.mPermissionsRequestor.removePermission(str);
        this.mPermissionsRequestor.requestNextPermissions();
    }

    @Override // com.good.gd.ui.PermissionRequestView, com.good.gd.ui.base_ui.GDView
    public void onPermissions(int i, String[] strArr, int[] iArr) {
        this.mPermissionsRequestor.onPermissions(i, strArr, iArr);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void requestInsecureWiFiPermissions() {
        requestPermissions(getPermissionsToRequestList(), 111);
    }

    @Override // com.good.gd.ui.PermissionRequestView, com.good.gd.ui.utils.permissions.PermissionsListener
    public boolean shouldShowExplanation(String str) {
        if (this.permissionToBeGranted != null) {
            return false;
        }
        char c = 65535;
        int hashCode = str.hashCode();
        if (hashCode != -1888586689) {
            if (hashCode == 2024715147 && str.equals(BACKGROUND_PERMISSION)) {
                c = 1;
            }
        } else if (str.equals("android.permission.ACCESS_FINE_LOCATION")) {
            c = 0;
        }
        return c != 0 && c == 1;
    }

    @Override // com.good.gd.ui.PermissionRequestView, com.good.gd.ui.utils.permissions.PermissionsListener
    public void showExplanation(String str) {
        this.permissionToBeGranted = str;
        if (str.equals("android.permission.ACCESS_FINE_LOCATION")) {
            openDialogUI(str);
        } else if (!str.equals(BACKGROUND_PERMISSION)) {
        } else {
            if (!((Activity) this.mContext).shouldShowRequestPermissionRationale(str) && PermissionsUtils.shouldCheckBackgroundLocationPermission(this.mContext, 30, 30)) {
                openDialogUI(str);
            } else {
                displayPermissionAlert();
            }
        }
    }
}
