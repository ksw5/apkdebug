package com.good.gd.ui;

import android.content.Context;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.good.gd.ndkproxy.ui.BBDUIHelper;
import com.good.gd.ndkproxy.ui.data.base.BaseUI;
import com.good.gd.resources.R;
import com.good.gd.ui.base_ui.GDView;
import com.good.gd.utils.GDLocalizer;

/* loaded from: classes.dex */
public class GDBlockedLoadingView extends GDView {
    protected final ProgressBar spinner;
    protected final TextView titleView;
    protected final BaseUI uiData;

    public GDBlockedLoadingView(Context context, ViewInteractor viewInteractor, BaseUI baseUI, ViewCustomizer viewCustomizer) {
        super(context, viewInteractor, viewCustomizer);
        inflateLayout(R.layout.bbd_blocked_loading_view, this);
        this.uiData = baseUI;
        TextView textView = (TextView) findViewById(R.id.COM_GOOD_GD_BLOCK_VIEW_TITLE_VIEW);
        this.titleView = textView;
        checkFieldNotNull(textView, "bbd_block_view", "COM_GOOD_GD_BLOCK_VIEW_TITLE_VIEW");
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.COM_GOOD_GD_BLOCK_VIEW_PROGRESSBAR);
        this.spinner = progressBar;
        checkFieldNotNull(progressBar, "bbd_block_view", "COM_GOOD_GD_BLOCK_VIEW_PROGRESSBAR");
        enableBottomLine();
        enableHelpButton(baseUI);
        applyUICustomization();
    }

    private void updateSpinner() {
        this.spinner.setVisibility(0);
    }

    private void updateUI() {
        updateSpinner();
        updateGravity();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.titleView.setText(GDLocalizer.getLocalizedString(BBDUIHelper.getLocalizableTitle(this.uiData.getCoreHandle())));
        updateUI();
    }

    @Override // com.good.gd.ui.base_ui.GDView
    public void onBackPressed() {
        moveTaskToBack();
    }

    protected void updateGravity() {
        this.titleView.setGravity(1);
    }
}
