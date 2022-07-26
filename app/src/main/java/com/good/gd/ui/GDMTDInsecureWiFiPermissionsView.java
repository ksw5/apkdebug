package com.good.gd.ui;

import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.core.text.HtmlCompat;
import com.good.gd.ndkproxy.ui.CoreUI;
import com.good.gd.ndkproxy.ui.data.MTDInsecureWiFiPermissionUI;
import com.good.gd.resources.R;

/* loaded from: classes.dex */
public class GDMTDInsecureWiFiPermissionsView extends InsecureWiFiPermissionRequestBaseView {
    private static final int PROGRESS_BAR_FULL = 10;
    private MTDInsecureWiFiPermissionUI uiData;

    /* loaded from: classes.dex */
    class hbfhc implements OnClickListener {
        hbfhc() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            GDMTDInsecureWiFiPermissionsView.this.onUserAction();
            GDMTDInsecureWiFiPermissionsView.this.requestInsecureWiFiPermissions();
        }
    }

    public GDMTDInsecureWiFiPermissionsView(Context context, ViewInteractor viewInteractor, MTDInsecureWiFiPermissionUI mTDInsecureWiFiPermissionUI, ViewCustomizer viewCustomizer) {
        super(context, viewInteractor, viewCustomizer, mTDInsecureWiFiPermissionUI);
        inflateLayout(R.layout.bbd_mtd_insecure_wifi_permissions_view, this);
        this.uiData = mTDInsecureWiFiPermissionUI;
        String localizedTitle = mTDInsecureWiFiPermissionUI.getLocalizedTitle();
        String localizedMessage = this.uiData.getLocalizedMessage();
        Button button = (Button) findViewById(R.id.insecure_wifi_permissions_continue_button);
        button.setText(this.uiData.getLocalizedContinueButtonText());
        button.setOnClickListener(new hbfhc());
        rearrangeButton(button);
        ((TextView) findViewById(R.id.insecure_wifi_permissions_title)).setText(localizedTitle);
        TextView textView = (TextView) findViewById(R.id.insecure_wifi_permissions_message);
        textView.setText(HtmlCompat.fromHtml(localizedMessage, 0));
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        setProgressLine(10);
        applyUICustomization();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.good.gd.ui.InsecureWiFiPermissionRequestBaseView
    public void displayDeviceLocSettingsRedirectionDialog() {
        super.displayDeviceLocSettingsRedirectionDialog();
        CoreUI.requestLocationServicesDisabledAlert(false, this.uiData.getCoreHandle());
    }

    @Override // com.good.gd.ui.InsecureWiFiPermissionRequestBaseView
    protected boolean isHighPriority() {
        return false;
    }

    protected void onUserAction() {
        this.uiData.onUserAction();
    }
}
