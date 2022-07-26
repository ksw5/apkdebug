package com.good.gd.ui;

import android.content.Context;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.good.gd.client.GDCustomizedUI;
import com.good.gd.resources.R;
import com.good.gd.ui.base_ui.GDView;
import com.good.gd.utils.EmulatorChecker;
import com.good.gd.utils.GDApplicationUtils;

/* loaded from: classes.dex */
public class GDStartingView extends GDView {
    private GDCustomizedUI customizedUI;
    private EmulatorChecker emulatorChecker;

    public GDStartingView(Context context, ViewInteractor viewInteractor, ViewCustomizer viewCustomizer) {
        this(context, viewInteractor, viewCustomizer, true);
    }

    private String getApplicationName() {
        return GDApplicationUtils.getApplicationName(getContext());
    }

    @Override // com.good.gd.ui.base_ui.GDView
    protected void applyUICustomization() {
        super.applyUICustomization();
        if (!this.customizedUI.isUICustomized()) {
            return;
        }
        ImageView imageView = (ImageView) findViewById(R.id.gd_welcome_logo);
        if (this.customizedUI.getBigApplicationLogo() != null) {
            imageView.setImageDrawable(this.customizedUI.getBigApplicationLogo());
        }
        findViewById(R.id.gd_welcome_banner).setBackgroundColor(this.customizedUI.getCustomUIColor().intValue());
        ((TextView) findViewById(R.id.gd_application_name)).setText(getApplicationName());
        ((ImageView) findViewById(R.id.gd_secure_logo)).setVisibility(0);
    }

    public GDStartingView(Context context, ViewInteractor viewInteractor, ViewCustomizer viewCustomizer, boolean z) {
        super(context, viewInteractor, viewCustomizer);
        ProgressBar progressBar;
        this.customizedUI = viewCustomizer.getGDCustomizedUI();
        this.emulatorChecker = viewCustomizer.getEmulatorChecker();
        inflateLayout(R.layout.bbd_welcome_view, this);
        if (z) {
            setVisibility(0);
        } else {
            setVisibility(4);
        }
        applyUICustomization();
        if (!this.emulatorChecker.isEmulator() || (progressBar = (ProgressBar) findViewById(R.id.gd_spinner)) == null) {
            return;
        }
        progressBar.setEnabled(false);
        progressBar.setVisibility(4);
    }
}
