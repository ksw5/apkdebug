package com.good.gd.ui;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.good.gd.ndkproxy.ui.data.base.LearnMoreUI;
import com.good.gd.ndkproxy.ui.event.BBDUIEventManager;
import com.good.gd.ndkproxy.ui.event.BBDUIMessageType;
import com.good.gd.resources.R;
import com.good.gd.ui.base_ui.GDView;
import com.good.gd.utils.GDLocalizer;

/* loaded from: classes.dex */
public class GDLearnMoreView extends GDView {
    private LearnMoreUI uiData;

    /* loaded from: classes.dex */
    class hbfhc implements OnClickListener {
        final /* synthetic */ LearnMoreUI dbjc;

        hbfhc(GDLearnMoreView gDLearnMoreView, LearnMoreUI learnMoreUI) {
            this.dbjc = learnMoreUI;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            BBDUIEventManager.sendMessage(this.dbjc, BBDUIMessageType.MSG_UI_OK);
        }
    }

    public GDLearnMoreView(Context context, ViewInteractor viewInteractor, LearnMoreUI learnMoreUI, ViewCustomizer viewCustomizer) {
        super(context, viewInteractor, viewCustomizer);
        this.uiData = learnMoreUI;
        inflateLayout(R.layout.bbd_learn_more_view, this);
        Button button = (Button) findViewById(R.id.gd_done_button);
        checkFieldNotNull(button, "bbd_learn_more_view", "gd_done_button");
        button.setText(GDLocalizer.getLocalizedString("Done"));
        button.setOnClickListener(new hbfhc(this, learnMoreUI));
        rearrangeButton(button);
        TextView textView = (TextView) findViewById(R.id.gd_learn_more_title);
        checkFieldNotNull(textView, "bbd_learn_more_view", "gd_learn_more_title");
        textView.setText(GDLocalizer.getLocalizedString(learnMoreUI.getLearnMoreTitleTextKey()));
        TextView textView2 = (TextView) findViewById(R.id.gd_learn_more_details);
        checkFieldNotNull(textView2, "bbd_learn_more_view", "gd_learn_more_details");
        textView2.setText(GDLocalizer.getLocalizedString(learnMoreUI.getLearnMoreDetailsTextKey()));
        applyUICustomization();
        enableBottomLine();
        adjustHeaderPositioning();
    }

    @Override // com.good.gd.ui.base_ui.GDView
    public void onBackPressed() {
        BBDUIEventManager.sendMessage(this.uiData, BBDUIMessageType.MSG_UI_OK);
    }
}
