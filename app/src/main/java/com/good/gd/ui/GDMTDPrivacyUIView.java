package com.good.gd.ui;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.ui.BBDUIHelper;
import com.good.gd.ndkproxy.ui.data.MTDPrivacyUI;
import com.good.gd.resources.R;
import com.good.gd.ui.base_ui.GDView;
import com.good.gd.utils.GDLocalizer;

/* loaded from: classes.dex */
public class GDMTDPrivacyUIView extends GDView {
    private MTDPrivacyUI bbduiData;

    /* loaded from: classes.dex */
    class hbfhc implements OnClickListener {
        hbfhc() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            GDMTDPrivacyUIView.this.closeDisclaimer();
        }
    }

    public GDMTDPrivacyUIView(Context context, ViewInteractor viewInteractor, MTDPrivacyUI mTDPrivacyUI, ViewCustomizer viewCustomizer) {
        super(context, viewInteractor, viewCustomizer);
        this.bbduiData = mTDPrivacyUI;
        String localizedString = GDLocalizer.getLocalizedString("MTDPrivacy : Main Body");
        String localizedString2 = GDLocalizer.getLocalizedString("MTDPrivacy : Heading");
        String localizedString3 = GDLocalizer.getLocalizedString("MTDPrivacy : Review Privacy");
        String localizedString4 = GDLocalizer.getLocalizedString("MTDPrivacy : Privacy Link");
        inflateLayout(R.layout.bbd_mtd_privacy_disclaimer_view, this);
        Button button = (Button) findViewById(R.id.gd_done_button);
        checkFieldNotNull(button, "bbd_mtd_privacy_disclaimer_view", "gd_done_button");
        button.setText(GDLocalizer.getLocalizedString("MTDPrivacy : Allow Button"));
        button.setOnClickListener(new hbfhc());
        rearrangeButton(button);
        TextView textView = (TextView) findViewById(R.id.gd_mtd_disclaimer_heading_text);
        checkFieldNotNull(textView, "bbd_mtd_privacy_disclaimer_view", "gd_mtd_disclaimer_heading_text");
        textView.setText(localizedString2);
        TextView textView2 = (TextView) findViewById(R.id.gd_mtd_disclaimer_text);
        checkFieldNotNull(textView2, "bbd_mtd_privacy_disclaimer_view", "gd_mtd_disclaimer_text");
        textView2.setText(localizedString);
        Spanned fromHtml = Html.fromHtml("<a href ='https://www.blackberry.com/privacy'>" + localizedString4 + "</a>", 63);
        TextView textView3 = (TextView) findViewById(R.id.gd_mtd_privacy_policy_text);
        checkFieldNotNull(textView2, "bbd_mtd_privacy_disclaimer_view", "gd_mtd_privacy_policy_text");
        textView3.setMovementMethod(LinkMovementMethod.getInstance());
        textView3.setText(localizedString3);
        textView3.append(fromHtml);
        applyUICustomization();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeDisclaimer() {
        GDLog.DBGPRINTF(16, "GDMTDPrivacyUIView.closeDisclaimer\n");
        BBDUIHelper.ok(this.bbduiData.getCoreHandle());
    }
}
