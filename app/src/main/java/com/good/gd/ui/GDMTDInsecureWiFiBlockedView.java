package com.good.gd.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.good.gd.ndkproxy.ui.data.MTDInsecureWiFiBlockedUI;
import com.good.gd.resources.R;
import com.good.gd.ui.base_ui.GDView;

/* loaded from: classes.dex */
public class GDMTDInsecureWiFiBlockedView extends GDView {
    private static int PROGRESS_BAR_FULL = 10;
    private Activity mActivity;
    private MTDInsecureWiFiBlockedUI uiData;

    /* loaded from: classes.dex */
    class hbfhc implements OnClickListener {
        hbfhc() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            GDMTDInsecureWiFiBlockedView.this.openNetworkSettings();
        }
    }

    public GDMTDInsecureWiFiBlockedView(Context context, ViewInteractor viewInteractor, MTDInsecureWiFiBlockedUI mTDInsecureWiFiBlockedUI, ViewCustomizer viewCustomizer) {
        super(context, viewInteractor, viewCustomizer);
        inflateLayout(R.layout.bbd_mtd_insecure_wifi_blocked_view, this);
        this.uiData = mTDInsecureWiFiBlockedUI;
        this.mActivity = (Activity) context;
        String localizedTitle = mTDInsecureWiFiBlockedUI.getLocalizedTitle();
        String localizedMessage = this.uiData.getLocalizedMessage();
        ((TextView) findViewById(R.id.insecure_wifi_blocked_title)).setText(localizedTitle);
        ((TextView) findViewById(R.id.insecure_wifi_blocked_message)).setText(localizedMessage);
        Button button = (Button) findViewById(R.id.insecure_wifi_blocked_settings_button);
        button.setText(this.uiData.getLocalizedNetworkSettingsButtonText());
        button.setOnClickListener(new hbfhc());
        rearrangeButton(button);
        setProgressLine(PROGRESS_BAR_FULL);
        applyUICustomization();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void openNetworkSettings() {
        if (Build.VERSION.SDK_INT >= 29) {
            this.mActivity.startActivity(new Intent("android.settings.panel.action.WIFI"));
        } else {
            this.mActivity.startActivity(new Intent("android.settings.WIFI_SETTINGS"));
        }
    }
}
