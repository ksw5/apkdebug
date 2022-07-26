package com.good.gd.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.ui.BBDUIHelper;
import com.good.gd.ndkproxy.ui.data.DisclaimerUI;
import com.good.gd.resources.R;
import com.good.gd.ui.base_ui.GDView;
import com.good.gd.ui.utils.ActionDispatcher;
import com.good.gd.ui.utils.Actions;
import com.good.gd.ui.utils.ScrollEventDispatcher;
import com.good.gd.utils.GDLocalizer;

/* loaded from: classes.dex */
public class GDDisclaimerView extends GDView {
    private DisclaimerUI bbduiData;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public final class fdyxd extends GDViewDelegateAdapter {
        private ScrollView dbjc;
        private yfdke qkduk;

        /* loaded from: classes.dex */
        class hbfhc implements Runnable {
            hbfhc() {
            }

            @Override // java.lang.Runnable
            public void run() {
                if (!fdyxd.this.dbjc.canScrollVertically(1)) {
                    fdyxd.this.qkduk.onControlEnable();
                }
            }
        }

        public fdyxd(GDDisclaimerView gDDisclaimerView, ScrollView scrollView, yfdke yfdkeVar) {
            this.dbjc = scrollView;
            this.qkduk = yfdkeVar;
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActivityCreate(Bundle bundle) {
            Runnable yfdkeVar;
            super.onActivityCreate(bundle);
            if (bundle != null) {
                if (bundle.getBoolean("accept_button_extra_key", false)) {
                    this.qkduk.onControlEnable();
                    yfdkeVar = new com.good.gd.ui.hbfhc(this);
                } else {
                    yfdkeVar = new com.good.gd.ui.yfdke(this, bundle);
                }
                this.dbjc.post(yfdkeVar);
            }
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActivityResume() {
            super.onActivityResume();
            this.dbjc.post(new hbfhc());
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onSaveInstanceState(Bundle bundle) {
            super.onSaveInstanceState(bundle);
            bundle.putBoolean("accept_button_extra_key", this.qkduk.dbjc());
            bundle.putDouble("scroll_position_extra_key", this.dbjc.getScrollY() / this.dbjc.getHeight());
        }
    }

    /* loaded from: classes.dex */
    class hbfhc implements OnClickListener {
        hbfhc() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            GDDisclaimerView.this.closeDisclaimer();
        }
    }

    /* loaded from: classes.dex */
    private class yfdke implements Actions {
        private Button dbjc;

        public yfdke(GDDisclaimerView gDDisclaimerView, Button button) {
            this.dbjc = button;
        }

        public boolean dbjc() {
            return this.dbjc.isEnabled();
        }

        @Override // com.good.gd.ui.utils.Actions
        public void onControlEnable() {
            this.dbjc.setEnabled(true);
        }
    }

    public GDDisclaimerView(Context context, ViewInteractor viewInteractor, DisclaimerUI disclaimerUI, ViewCustomizer viewCustomizer) {
        super(context, viewInteractor, viewCustomizer);
        this.bbduiData = disclaimerUI;
        String parsedDisclaimerMessage = disclaimerUI.getParsedDisclaimerMessage();
        if (TextUtils.isEmpty(parsedDisclaimerMessage)) {
            closeDisclaimer();
            return;
        }
        inflateLayout(R.layout.bbd_disclaimer_view, this);
        Button button = (Button) findViewById(R.id.gd_done_button);
        checkFieldNotNull(button, "bbd_disclaimer_view", "gd_done_button");
        button.setText(GDLocalizer.getLocalizedString("Disclaimer UI right button label text"));
        button.setEnabled(false);
        button.setOnClickListener(new hbfhc());
        rearrangeButton(button);
        ScrollView scrollView = (ScrollView) findViewById(R.id.bbd_disclaimer_scroll_view);
        checkFieldNotNull(scrollView, "bbd_disclaimer_view", "gd_disclaimer_scroll_view");
        ScrollEventDispatcher scrollEventDispatcher = new ScrollEventDispatcher(scrollView);
        scrollView.getViewTreeObserver().addOnScrollChangedListener(scrollEventDispatcher);
        ActionDispatcher actionDispatcher = new ActionDispatcher();
        scrollEventDispatcher.setScrollEventListener(actionDispatcher);
        yfdke yfdkeVar = new yfdke(this, button);
        actionDispatcher.setActionHandler(yfdkeVar);
        this._delegate = new fdyxd(this, scrollView, yfdkeVar);
        TextView textView = (TextView) findViewById(R.id.gd_disclaimer_text);
        checkFieldNotNull(textView, "bbd_disclaimer_view", "gd_disclaimer_text");
        textView.setText(parsedDisclaimerMessage);
        applyUICustomization();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeDisclaimer() {
        GDLog.DBGPRINTF(16, "GDDisclaimerView.closeDisclaimer\n");
        BBDUIHelper.ok(this.bbduiData.getCoreHandle());
    }
}
