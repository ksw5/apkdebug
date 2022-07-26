package com.good.gd.ui;

import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.core.text.HtmlCompat;
import com.good.gd.ndkproxy.ui.CoreUI;
import com.good.gd.ndkproxy.ui.data.MTDInsecureWiFiPermissionsBlockedUI;
import com.good.gd.resources.R;

/* loaded from: classes.dex */
public class GDMTDInsecureWiFiPermissionsBlockedView extends InsecureWiFiPermissionRequestBaseView {
    private static int PROGRESS_BAR_FULL = 10;
    private MTDInsecureWiFiPermissionsBlockedUI uiData;

    /* loaded from: classes.dex */
    class hbfhc implements OnClickListener {
        hbfhc() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            GDMTDInsecureWiFiPermissionsBlockedView.this.requestInsecureWiFiPermissions();
        }
    }

    public GDMTDInsecureWiFiPermissionsBlockedView(Context context, ViewInteractor viewInteractor, MTDInsecureWiFiPermissionsBlockedUI mTDInsecureWiFiPermissionsBlockedUI, ViewCustomizer viewCustomizer) {
        super(context, viewInteractor, viewCustomizer, mTDInsecureWiFiPermissionsBlockedUI);
        inflateLayout(R.layout.bbd_mtd_insecure_wifi_permissions_blocked_view, this);
        this.uiData = mTDInsecureWiFiPermissionsBlockedUI;
        String localizedTitle = mTDInsecureWiFiPermissionsBlockedUI.getLocalizedTitle();
        String localizedMessage = this.uiData.getLocalizedMessage();
        Button button = (Button) findViewById(R.id.insecure_wifi_permissions_blocked_enable_button);
        button.setText(this.uiData.getLocalizedEnableButtonText());
        button.setOnClickListener(new hbfhc());
        rearrangeButton(button);
        ((TextView) findViewById(R.id.insecure_wifi_permissions_blocked_title)).setText(localizedTitle);
        TextView textView = (TextView) findViewById(R.id.insecure_wifi_permissions_blocked_message);
        textView.setText(HtmlCompat.fromHtml(localizedMessage, 0));
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        setProgressLine(PROGRESS_BAR_FULL);
        applyUICustomization();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.good.gd.ui.InsecureWiFiPermissionRequestBaseView
    public void displayDeviceLocSettingsRedirectionDialog() {
        super.displayDeviceLocSettingsRedirectionDialog();
        CoreUI.requestLocationServicesDisabledAlert(true, this.uiData.getCoreHandle());
    }

    @Override // com.good.gd.ui.InsecureWiFiPermissionRequestBaseView
    protected boolean isHighPriority() {
        return true;
    }
}
