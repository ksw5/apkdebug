package com.good.gd.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.good.gd.ndkproxy.ui.data.base.BBDUIObject;
import com.good.gd.ndkproxy.ui.event.BBDUIEventManager;
import com.good.gd.ndkproxy.ui.event.BBDUIMessageType;
import com.good.gd.resources.R;
import com.good.gd.ui.base_ui.GDView;
import com.good.gd.ui.utils.sis.ActivityStatusListener;
import com.good.gd.ui.utils.sis.AnalyticsLogger;
import com.good.gd.ui.utils.sis.SISProxy;
import com.good.gd.utils.GDLocalizer;

/* loaded from: classes.dex */
public class SISLearnMoreView extends GDView {
    private final String TAG;
    private final BBDUIObject uiData;
    private AnalyticsLogger logger = SISProxy.getLogger();
    private ActivityStatusListener activityStatusListener = SISProxy.getActivityStatusListener();

    /* loaded from: classes.dex */
    class hbfhc implements OnClickListener {
        hbfhc() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            BBDUIEventManager.sendMessage(SISLearnMoreView.this.uiData, BBDUIMessageType.MSG_UI_OK);
        }
    }

    /* loaded from: classes.dex */
    private final class yfdke extends GDViewDelegateAdapter {
        private yfdke() {
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActivityCreate(Bundle bundle) {
            super.onActivityCreate(bundle);
            SISLearnMoreView.this.activityStatusListener.setSISLearnMoreActivityRunning(true);
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActivityDestroy() {
            super.onActivityDestroy();
            SISLearnMoreView.this.activityStatusListener.setSISLearnMoreActivityRunning(false);
        }

        /* synthetic */ yfdke(SISLearnMoreView sISLearnMoreView, hbfhc hbfhcVar) {
            this();
        }
    }

    public SISLearnMoreView(Context context, ViewInteractor viewInteractor, ViewCustomizer viewCustomizer, BBDUIObject bBDUIObject) {
        super(context, viewInteractor, viewCustomizer);
        String simpleName = SISLearnMoreView.class.getSimpleName();
        this.TAG = simpleName;
        this.uiData = bBDUIObject;
        this._delegate = new yfdke(this, null);
        this.logger.i(simpleName, "Starting SISLearnMoreGDActivity.");
        inflateLayout(R.layout.activity_sis_learn_more, this);
        ((TextView) findViewById(R.id.tv_learn_more_title)).setText(GDLocalizer.getLocalizedString("BISLearnMoreTitleText"));
        ((TextView) findViewById(R.id.tv_learn_more_desc)).setText(GDLocalizer.getLocalizedString("BISLearnMoreDescriptionText"));
        Button button = (Button) findViewById(R.id.btn_done);
        button.setText(GDLocalizer.getLocalizedString("BISDone"));
        button.setOnClickListener(new hbfhc());
    }

    @Override // com.good.gd.ui.base_ui.GDView
    public void onBackPressed() {
        BBDUIEventManager.sendMessage(this.uiData, BBDUIMessageType.MSG_UI_OK);
    }
}
