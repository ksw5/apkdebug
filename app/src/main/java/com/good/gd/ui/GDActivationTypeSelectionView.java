package com.good.gd.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.good.gd.client.GDCustomizedUI;
import com.good.gd.ndkproxy.ui.data.ActivationTypeSelectionUI;
import com.good.gd.ndkproxy.ui.event.BBDUIEventManager;
import com.good.gd.ndkproxy.ui.event.BBDUIMessageType;
import com.good.gd.resources.R;
import com.good.gd.ui.base_ui.GDView;

/* loaded from: classes.dex */
public class GDActivationTypeSelectionView extends GDView {
    private final GDCustomizedUI customizedUI;
    ActivationTypeSelectionUI uiData;
    TextView getStarted = (TextView) findViewById(R.id.gd_selection_getStarted_text);
    ViewGroup scanQR = (ViewGroup) findViewById(R.id.gd_selection_QR_button);
    TextView scanQrText = (TextView) findViewById(R.id.gd_qr_button_text);
    TextView noQRCode = (TextView) findViewById(R.id.gd_selection_no_QR_text);
    ViewGroup singInOption = (ViewGroup) findViewById(R.id.gd_selection_sign_in_option);
    TextView textSignIn = (TextView) findViewById(R.id.text_sign_in);
    ImageView signInBackground = (ImageView) findViewById(R.id.image_sign_in);
    ViewGroup credentialOption = (ViewGroup) findViewById(R.id.gd_selection_credentials_option);
    TextView textCredentials = (TextView) findViewById(R.id.text_enter_credentials);
    ImageView credentialsBackground = (ImageView) findViewById(R.id.image_enter_credentials);

    /* loaded from: classes.dex */
    class fdyxd implements OnClickListener {
        fdyxd() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            BBDUIEventManager.sendMessage(GDActivationTypeSelectionView.this.uiData, BBDUIMessageType.MSG_UI_ACTIVATION);
        }
    }

    /* loaded from: classes.dex */
    class hbfhc implements OnClickListener {
        hbfhc() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            BBDUIEventManager.sendMessage(GDActivationTypeSelectionView.this.uiData, BBDUIMessageType.MSG_UI_QR_CODE);
        }
    }

    /* loaded from: classes.dex */
    class yfdke implements OnClickListener {
        yfdke() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            BBDUIEventManager.sendMessage(GDActivationTypeSelectionView.this.uiData, BBDUIMessageType.MSG_UI_WEB_VIEW);
        }
    }

    public GDActivationTypeSelectionView(Context context, ViewInteractor viewInteractor, ActivationTypeSelectionUI activationTypeSelectionUI, ViewCustomizer viewCustomizer) {
        super(context, viewInteractor, viewCustomizer);
        this.uiData = null;
        this.customizedUI = viewCustomizer.getGDCustomizedUI();
        inflateLayout(R.layout.bbd_activation_type_selection, this);
        this.uiData = activationTypeSelectionUI;
        this.getStarted.setText(this.uiData.getLocalizedTitle());
        this.scanQrText.setText(this.uiData.getLocalizedScanQRButtonKey());
        this.scanQR.setOnClickListener(new hbfhc());
        this.noQRCode.setText(this.uiData.getLocalizedNoQRCodeTitleKey());
        this.singInOption.setOnClickListener(new yfdke());
        this.textSignIn.setText(Html.fromHtml(this.uiData.getLocalizedSignInText(), 63));
        this.credentialOption.setOnClickListener(new fdyxd());
        this.textCredentials.setText(Html.fromHtml(this.uiData.getLocalizedCredentialsText(), 63));
        applyUICustomization();
        adjustHeaderPositioning();
        setDarkModeSettings();
    }

    private boolean isDarkModeEnabled() {
        return (getContext().getResources().getConfiguration().uiMode & 48) == 32;
    }

    private void setDarkModeSettings() {
        if (isDarkModeEnabled()) {
            this.getStarted.setTextColor(ContextCompat.getColor(getContext(), R.color.bbd_almost_white));
            this.noQRCode.setTextColor(ContextCompat.getColor(getContext(), R.color.bbd_almost_white));
            this.textSignIn.setTextColor(ContextCompat.getColor(getContext(), R.color.bbd_almost_white));
            this.textCredentials.setTextColor(ContextCompat.getColor(getContext(), R.color.bbd_almost_white));
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.good.gd.ui.base_ui.GDView
    public void applyUICustomization() {
        super.applyUICustomization();
        if (!this.customizedUI.isUICustomized() || this.customizedUI.getCustomUIColor() == null) {
            return;
        }
        changeViewDrawableBackgroundColor(this.scanQR);
        changeViewDrawableBackgroundColor(this.signInBackground);
        changeViewDrawableBackgroundColor(this.credentialsBackground);
    }

    void changeViewDrawableBackgroundColor(View view) {
        Drawable background = view.getBackground();
        if (background instanceof GradientDrawable) {
            ((GradientDrawable) background).setColor(this.customizedUI.getCustomUIColor().intValue());
        }
    }

    @Override // com.good.gd.ui.base_ui.GDView
    public void onBackPressed() {
        BBDUIEventManager.sendMessage(this.uiData, BBDUIMessageType.MSG_UI_CANCEL);
    }
}
