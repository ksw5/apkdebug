package com.good.gd.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.good.gd.client.GDCustomizedUI;
import com.good.gd.messages.ProvisionMsg;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.ui.BBDUIHelper;
import com.good.gd.ndkproxy.ui.BBDUILocalizationHelper;
import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.ndkproxy.ui.data.ActivationUI;
import com.good.gd.ndkproxy.ui.event.BBDUIEventManager;
import com.good.gd.ndkproxy.ui.event.BBDUIMessageType;
import com.good.gd.ndkproxy.ui.event.BBDUIUpdateEvent;
import com.good.gd.ndkproxy.ui.event.UIEventType;
import com.good.gd.resources.R;
import com.good.gd.ui.base_ui.GDView;
import com.good.gd.ui.utils.EditorState;
import com.good.gd.ui.utils.TextPasswordWatcher;
import com.good.gd.widget.EditAction;
import com.good.gd.widget.GDEditText;
import com.good.gd.widget.GDTextView;

/* loaded from: classes.dex */
public final class GDEProvisionView extends GDView {
    private static final int ACTIVATION_PROGRESS = 1;
    private static final int FIELD_BCP_URL_TAG = 1;
    private static final int FIELD_EMAIL_TAG = 0;
    private static final int FIELD_PASSWORD_TAG = 2;
    private static final int PIN_LENGTH = 15;
    private static final String STATE_BCP_URL = "bcp url";
    private static final String STATE_EMAIL = "email";
    private static final String STATE_FIELD = "focus";
    private static final String STATE_PASSWORD = "password";
    private static final String STATE_PASSWORD_TRANSFORMATION = "text password transformation";
    private final Button accessButton;
    private final GDTextView activationInfo;
    private final GDEditText bcpURLField;
    private final ViewGroup bcpUrlLayout;
    private final ViewGroup buttonsLayout;
    private final Button cancelButton;
    private final GDCustomizedUI customizedUI;
    private final GDEditText emailField;
    private final OnFocusChangeListener fieldFocusListener;
    private EditText focusField;
    private final GDEditText passwordField;
    private final TextPasswordWatcher passwordWatcher;
    private final OnKeyListener provisionKeyListener;
    private final ViewGroup qrCodeButtonLayout;
    private final Button scanQRCodeButton;
    private ActivationUI uiData;
    private final TextView unlockMessage;
    private final TextView unlockTitle;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class aqdzk implements OnClickListener {
        aqdzk() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            GDEProvisionView.this.cancelAction();
        }
    }

    /* loaded from: classes.dex */
    class ehnkx implements OnClickListener {
        ehnkx() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            GDEProvisionView.this.doProvision();
        }
    }

    /* loaded from: classes.dex */
    class fdyxd implements TextPasswordWatcher.TextEnteredListener {
        fdyxd() {
        }

        @Override // com.good.gd.ui.utils.TextPasswordWatcher.TextEnteredListener
        public void onTextEntered() {
            GDEProvisionView.this.adjustNextButton();
        }
    }

    /* loaded from: classes.dex */
    class hbfhc implements OnFocusChangeListener {
        hbfhc() {
        }

        @Override // android.view.View.OnFocusChangeListener
        public void onFocusChange(View view, boolean z) {
            GDEProvisionView.this.setEditTextBackground(view, z);
        }
    }

    /* loaded from: classes.dex */
    class mjbm implements OnClickListener {
        mjbm() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            BBDUIEventManager.sendMessage(GDEProvisionView.this.uiData, BBDUIMessageType.MSG_UI_QR_CODE);
        }
    }

    /* loaded from: classes.dex */
    class orlrx implements DialogInterface.OnClickListener {
        final /* synthetic */ Runnable dbjc;

        orlrx(Runnable runnable) {
            this.dbjc = runnable;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            GDEProvisionView.this.enableControls();
            Runnable runnable = this.dbjc;
            if (runnable != null) {
                runnable.run();
            }
        }
    }

    /* loaded from: classes.dex */
    class pmoiy implements OnClickListener {
        pmoiy() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            GDEProvisionView.this.cancelAction();
        }
    }

    /* loaded from: classes.dex */
    private class vzw implements TextWatcher {
        private final TextView dbjc;

        public vzw(TextView textView) {
            this.dbjc = textView;
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            GDEProvisionView.this.adjustNextButton();
            if (editable.toString().isEmpty()) {
                this.dbjc.setAlpha(GDEProvisionView.this.getFloatValueFromResource(R.dimen.gd_text_transparent));
            }
        }

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            this.dbjc.setAlpha(GDEProvisionView.this.getFloatValueFromResource(R.dimen.gd_text_opaque));
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
    }

    /* loaded from: classes.dex */
    class yfdke implements OnKeyListener {
        yfdke() {
        }

        @Override // android.view.View.OnKeyListener
        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            if (keyEvent.getAction() == 0) {
                if (i != 66 && i != 160) {
                    return false;
                }
                GDEProvisionView.this.doProvision();
                return true;
            }
            return false;
        }
    }

    public GDEProvisionView(Context context, ViewInteractor viewInteractor, ActivationUI activationUI, ViewCustomizer viewCustomizer) {
        super(context, viewInteractor, viewCustomizer);
        this.uiData = null;
        hbfhc hbfhcVar = new hbfhc();
        this.fieldFocusListener = hbfhcVar;
        yfdke yfdkeVar = new yfdke();
        this.provisionKeyListener = yfdkeVar;
        this.uiData = activationUI;
        GDCustomizedUI gDCustomizedUI = viewCustomizer.getGDCustomizedUI();
        this.customizedUI = gDCustomizedUI;
        this.uiData.setProvisionFields("", "");
        this._delegate = new gioey(this, null);
        setHasTextContainers(true);
        inflateLayout(R.layout.gde_provision_view, this);
        inflateHeader(context);
        applySimulatedOrDebugMode();
        GDTextView gDTextView = (GDTextView) findViewById(R.id.gd_activation_info);
        this.activationInfo = gDTextView;
        gDTextView.setText(Html.fromHtml(this.uiData.getLocalizedProvisionInfoMessageText(), 63));
        GDEditText gDEditText = (GDEditText) findViewById(R.id.COM_GOOD_GD_EPROV_EMAIL_FIELD);
        this.emailField = gDEditText;
        gDEditText.setEditActionEnabled(EditAction.COPY, false);
        gDEditText.setEditActionEnabled(EditAction.CUT, false);
        checkFieldNotNull(gDEditText, "gde_provision_view", "COM_GOOD_GD_EPROV_EMAIL_FIELD");
        gDEditText.setHint(this.uiData.getLocalizedEmailText());
        gDEditText.addTextChangedListener(new vzw(gDEditText));
        GDEditText gDEditText2 = (GDEditText) findViewById(R.id.COM_GOOD_GD_EPROV_BCP_URL_FIELD);
        this.bcpURLField = gDEditText2;
        checkFieldNotNull(gDEditText2, "gde_provision_view", "COM_GOOD_GD_EPROV_BCP_URL_FIELD");
        gDEditText2.setHint(this.uiData.getLocalizedBCPUrlText());
        gDEditText2.addTextChangedListener(new vzw(gDEditText2));
        gDEditText2.setEditActionEnabled(EditAction.COPY, false);
        gDEditText2.setEditActionEnabled(EditAction.CUT, false);
        GDEditText gDEditText3 = (GDEditText) findViewById(R.id.COM_GOOD_GD_EPROV_PASSWORD_FIELD);
        this.passwordField = gDEditText3;
        checkFieldNotNull(gDEditText3, "gde_provision_view", "COM_GOOD_GD_EPROV_PASSWORD_FIELD");
        gDEditText3.setHint(this.uiData.getLocalizedPinHintText());
        gDEditText3.setEditActionEnabled(EditAction.COPY, false);
        gDEditText3.setEditActionEnabled(EditAction.CUT, false);
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.COM_GOOD_GD_EPROV_BCP_URL_LAYOUT);
        this.bcpUrlLayout = viewGroup;
        checkFieldNotNull(viewGroup, "gde_provision_view", "COM_GOOD_GD_EPROV_BCP_URL_LAYOUT");
        ViewGroup viewGroup2 = (ViewGroup) findViewById(R.id.COM_GOOD_GD_FORGOT_FLOW_FIELDS);
        this.buttonsLayout = viewGroup2;
        checkFieldNotNull(viewGroup2, "gde_provision_view", "COM_GOOD_GD_FORGOT_FLOW_FIELDS");
        ViewGroup viewGroup3 = (ViewGroup) findViewById(R.id.COM_GOOD_GD_PROVISION_FIELDS);
        this.qrCodeButtonLayout = viewGroup3;
        checkFieldNotNull(viewGroup3, "gde_provision_view", "COM_GOOD_GD_PROVISION_FIELDS");
        ImageButton imageButton = (ImageButton) findViewById(R.id.prov_password_eye);
        checkFieldNotNull(imageButton, "gde_provision_view", "prov_password_eye");
        imageButton.setContentDescription(this.uiData.getLocalizedPasswordEyeText());
        TextPasswordWatcher textPasswordWatcher = new TextPasswordWatcher(gDEditText3, imageButton, getResources());
        this.passwordWatcher = textPasswordWatcher;
        textPasswordWatcher.setBrandingColor(gDCustomizedUI.getCustomUIColor());
        textPasswordWatcher.setTextEnteredListener(new fdyxd());
        gDEditText3.addTextChangedListener(textPasswordWatcher);
        gDEditText3.setOnKeyListener(yfdkeVar);
        gDEditText2.setOnKeyListener(yfdkeVar);
        gDEditText.setOnKeyListener(yfdkeVar);
        gDEditText.setOnFocusChangeListener(hbfhcVar);
        gDEditText2.setOnFocusChangeListener(hbfhcVar);
        gDEditText3.setOnFocusChangeListener(hbfhcVar);
        setEditTextBackground(gDEditText, false);
        setEditTextBackground(gDEditText2, false);
        setEditTextBackground(gDEditText3, false);
        Button button = (Button) findViewById(R.id.COM_GOOD_GD_EPROV_ACCESS_BUTTON);
        this.accessButton = button;
        checkFieldNotNull(button, "gde_provision_view", "COM_GOOD_GD_EPROV_ACCESS_BUTTON");
        button.setText(BBDUILocalizationHelper.getLocalizedOK());
        button.setOnClickListener(new ehnkx());
        Button button2 = (Button) findViewById(R.id.COM_GOOD_GD_GDE_PROVISION_VIEW_CANCEL_BUTTON);
        this.cancelButton = button2;
        checkFieldNotNull(button2, "gde_provision_view", "COM_GOOD_GD_EPROV_ACCESS_BUTTON");
        button2.setText(BBDUILocalizationHelper.getLocalizedCancel());
        button2.setOnClickListener(new pmoiy());
        rearrangeButton(button2);
        Button button3 = (Button) findViewById(R.id.COM_GOOD_GD_SCAN_QR_CODE);
        this.scanQRCodeButton = button3;
        checkFieldNotNull(button3, "gde_provision_view", "COM_GOOD_GD_SCAN_QR_CODE");
        button3.setText(this.uiData.getLocalizedQRCodeButtonText());
        button3.setOnClickListener(new mjbm());
        TextView textView = (TextView) findViewById(R.id.gd_application_unlock_title);
        this.unlockTitle = textView;
        checkFieldNotNull(textView, "gde_provision_view", "gd_application_unlock_title");
        String localizedTitle = this.uiData.getLocalizedTitle();
        if (localizedTitle.isEmpty()) {
            textView.setVisibility(8);
            if (this.uiData.hasCancelButton() || this.uiData.hasOkButton()) {
                viewGroup2.setVisibility(0);
                setButtonsLayoutMargins();
            }
        } else {
            textView.setText(localizedTitle);
            textView.setVisibility(0);
            viewGroup2.setVisibility(0);
            if (!this.uiData.hasCancelButton()) {
                button2.setVisibility(8);
            }
            setButtonParams(button3, 1);
        }
        TextView textView2 = (TextView) findViewById(R.id.gd_application_unlock_message);
        this.unlockMessage = textView2;
        checkFieldNotNull(textView2, "gde_provision_view", "gd_application_unlock_message");
        String localizedMessage = this.uiData.getLocalizedMessage();
        if (localizedMessage.isEmpty()) {
            textView2.setVisibility(8);
        } else {
            textView2.setText(localizedMessage);
            textView2.setVisibility(0);
        }
        enableHelpButton(this.uiData);
        enableBottomButton(this.uiData);
        setBottomLineBackground(this.uiData.hasBottomButton());
        applyPreviousData();
        applyUICustomization();
        adjustHeaderPositioning();
        adjustControls();
        setLastEditTextForAction(getLastEditTextForAction(), 6);
        setFieldsFocus();
        adjustTopMargin();
    }

    private void adjustControls() {
        if (this.unlockTitle.getVisibility() == 0) {
            this.activationInfo.setVisibility(8);
            setTopHeaderShadow();
            this.passwordField.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
        } else {
            this.activationInfo.setVisibility(0);
            if (TextUtils.isEmpty(this.emailField.getText())) {
                this.emailField.setText(this.uiData.getEnteredEmail());
            }
            this.passwordField.setText(this.uiData.getEnteredPassword());
        }
        if (this.uiData.hasQRCodeOkButton()) {
            this.scanQRCodeButton.setVisibility(0);
            this.qrCodeButtonLayout.setVisibility(0);
        } else {
            this.scanQRCodeButton.setVisibility(4);
            this.qrCodeButtonLayout.setVisibility(8);
        }
        if (this.uiData.getBBDUIType() == BBUIType.UI_ADVANCED_SETTINGS) {
            this.unlockTitle.setVisibility(8);
            this.unlockMessage.setVisibility(8);
            this.activationInfo.setVisibility(8);
            this.bcpUrlLayout.setVisibility(0);
            this.qrCodeButtonLayout.setVisibility(8);
            if (TextUtils.isEmpty(this.emailField.getText())) {
                this.emailField.setText(this.uiData.getEnteredEmail());
            }
            this.passwordField.setText(this.uiData.getEnteredPassword());
            this.bcpURLField.setText(this.uiData.getEnteredBcpUrl());
        }
        setProgressLine(1);
        BBDUIHelper.clearSensitiveData(this.uiData.getCoreHandle());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void adjustNextButton() {
        if (this.uiData.getLocalizedTitle().isEmpty() || this.uiData.getBBDUIType() == BBUIType.UI_ADVANCED_SETTINGS) {
            this.uiData.setProvisionFields(getEmail(), getPassword(), "");
        }
        setButtonEnabled(this.accessButton, canProvision());
    }

    private void adjustTopMargin() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.bbd_activation_ui_top_margin_layout);
        if (this.uiData.isLockFlow()) {
            linearLayout.setVisibility(8);
        } else {
            linearLayout.setVisibility(0);
        }
    }

    private void applyPreviousData() {
        String email = this.uiData.getEmail();
        String pin = this.uiData.getPin();
        if (!TextUtils.isEmpty(email)) {
            this.emailField.setText(email);
        }
        if (!TextUtils.isEmpty(pin)) {
            boolean z = true;
            if (isPasswordAccessKey() && pin.length() != 15) {
                z = false;
            }
            if (!z) {
                return;
            }
            this.passwordField.setText(pin);
        }
    }

    private boolean canProvision() {
        boolean z;
        boolean z2 = !TextUtils.isEmpty(getEmail());
        boolean z3 = !TextUtils.isEmpty(getPassword());
        if (!this.uiData.hasBCPURLField()) {
            z = true;
        } else {
            z = !TextUtils.isEmpty(getServer());
        }
        if (isPasswordAccessKey()) {
            z3 = getPassword().length() == 15;
        }
        return z2 && z && z3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelAction() {
        requestHideKeyboard(this.emailField);
        BBDUIEventManager.sendMessage(this.uiData, BBDUIMessageType.MSG_UI_CANCEL);
    }

    private void disableControls() {
        this.emailField.setEnabled(false);
        this.bcpURLField.setEnabled(false);
        this.passwordField.setEnabled(false);
        setButtonEnabled(this.accessButton, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doProvision() {
        GDLog.DBGPRINTF(16, "Starting provision process...\n");
        String email = getEmail();
        String server = getServer();
        String password = getPassword();
        if (!canProvision()) {
            return;
        }
        disableControls();
        clearSensitiveData();
        BBDUIEventManager.sendMessage(this.uiData, BBDUIMessageType.MSG_UI_START_PROVISIONING, new ProvisionMsg(email, password, server));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enableControls() {
        this.emailField.setEnabled(true);
        this.bcpURLField.setEnabled(true);
        this.passwordField.setEnabled(true);
        adjustNextButton();
    }

    private String getEmail() {
        return this.emailField.getText().toString().trim();
    }

    private EditText getLastEditTextForAction() {
        if (this.uiData.hasBCPURLField()) {
            return this.bcpURLField;
        }
        return this.passwordField;
    }

    private String getPassword() {
        return this.passwordField.getText().toString().trim();
    }

    private String getServer() {
        return this.bcpURLField.getText().toString().trim();
    }

    private void hideKeyboard() {
        View view = this.focusField;
        if (view == null) {
            view = this.emailField;
        }
        requestHideKeyboard(view);
    }

    private void inflateHeader(Context context) {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.bbd_provision_view_header_layout);
        if (this.uiData.isLockFlow()) {
            View.inflate(context, R.layout.bbd_common_header, linearLayout);
            return;
        }
        View.inflate(context, R.layout.bbd_activation_header, linearLayout);
        ((TextView) findViewById(R.id.bbd_activation_header_text)).setText(this.uiData.getLocalizedEnterCredentialsText());
        ((ImageView) findViewById(R.id.bbd_activation_header_back_button)).setOnClickListener(new aqdzk());
    }

    private boolean isDarkModeEnabled() {
        return (getContext().getResources().getConfiguration().uiMode & 48) == 32;
    }

    private boolean isPasswordAccessKey() {
        return this.unlockTitle.getVisibility() == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void restoreEditTextState(Bundle bundle, String str, EditText editText) {
        String string = bundle.getString(str);
        editText.setVisibility(0);
        if (!TextUtils.isEmpty(string)) {
            editText.setText(string);
            editText.setSelection(string.length());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveEditTextState(Bundle bundle, String str, EditText editText) {
        Editable text = editText.getText();
        bundle.putString(str, TextUtils.isEmpty(text) ? "" : text.toString());
    }

    private void setButtonParams(Button button, int i) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.setMargins(0, 0, 0, 0);
        layoutParams.gravity = i;
        button.setLayoutParams(layoutParams);
    }

    private void setButtonsLayoutMargins() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.COM_GOOD_GD_PROVISION_FIELDS);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
        layoutParams.setMargins(getResources().getDimensionPixelSize(R.dimen.gd_view_start_end_margin), getResources().getDimensionPixelSize(R.dimen.gd_view_margin_small), getResources().getDimensionPixelSize(R.dimen.gd_view_start_end_margin), 0);
        linearLayout.setLayoutParams(layoutParams);
    }

    private void setDarkModeSettings() {
        if (isDarkModeEnabled()) {
            TextView textView = (TextView) findViewById(R.id.bbd_activation_header_text);
            if (textView != null) {
                textView.setTextColor(ContextCompat.getColor(getContext(), R.color.bbd_almost_white));
            }
            ImageView imageView = (ImageView) findViewById(R.id.bbd_activation_header_back_button);
            if (imageView == null) {
                return;
            }
            imageView.setImageResource(R.drawable.bbd_icon_back_dark);
        }
    }

    private void setEditTextFocusNext(EditText editText, int i) {
        editText.setNextFocusDownId(i);
        editText.setNextFocusRightId(i);
        editText.setNextFocusForwardId(i);
    }

    private void setFieldsFocus() {
        if (this.uiData.hasBCPURLField()) {
            setEditTextFocusNext(this.passwordField, this.bcpURLField.getId());
            return;
        }
        this.passwordField.setImeOptions(33554438);
        if (!this.uiData.hasOkButton()) {
            return;
        }
        setEditTextFocusNext(this.passwordField, this.accessButton.getId());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showKeyboard() {
        View view = this.focusField;
        if (view == null) {
            view = this.emailField;
        }
        requestShowKeyboard(view);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.good.gd.ui.base_ui.GDView
    public void applyUICustomization() {
        super.applyUICustomization();
        if (!this.customizedUI.isUICustomized() || this.customizedUI.getCustomUIColor() == null) {
            return;
        }
        ImageView imageView = (ImageView) findViewById(R.id.prov_password_eye);
        if (imageView != null) {
            imageView.setImageDrawable(getCustomizedDrawable(R.drawable.gd_eye_show_inactive));
        }
        ImageView imageView2 = (ImageView) findViewById(R.id.password_eye);
        if (imageView2 != null) {
            imageView2.setImageDrawable(getCustomizedDrawable(R.drawable.gd_eye_show_inactive));
        }
        this.accessButton.setTextColor(this.customizedUI.getCustomUIColor().intValue());
        this.cancelButton.setTextColor(this.customizedUI.getCustomUIColor().intValue());
        this.scanQRCodeButton.setBackgroundColor(this.customizedUI.getCustomUIColor().intValue());
        customizeEditTextField(this.emailField, this.customizedUI.getCustomUIColor().intValue());
        customizeEditTextField(this.passwordField, this.customizedUI.getCustomUIColor().intValue());
        customizeEditTextField(this.bcpURLField, this.customizedUI.getCustomUIColor().intValue());
        setDarkModeSettings();
    }

    @Override // com.good.gd.ui.base_ui.GDView
    public void clearSensitiveData() {
        this.bcpURLField.setText("");
        this.passwordField.setText("");
        this.focusField = null;
        this.emailField.requestFocus();
        BBDUIHelper.clearSensitiveData(this.uiData.getCoreHandle());
    }

    @Override // com.good.gd.ui.base_ui.GDView
    public void onBackPressed() {
        cancelAction();
    }

    @Override // com.good.gd.ui.base_ui.GDView
    public void stateWasUpdated() {
        BBDUIUpdateEvent updateData = this.uiData.getUpdateData();
        if (updateData != null && updateData.getType() == UIEventType.UI_PROVISION_ENABLE_CONTROLS) {
            this.uiData.resetUpdateData();
            enableControls();
        } else if (updateData == null || updateData.isSuccessful()) {
        } else {
            showPopupDialog(updateData.getTitle(), updateData.getText(), BBDUILocalizationHelper.getLocalizedOK(), new orlrx(updateData.getAcknowledgeCb()));
        }
    }

    /* loaded from: classes.dex */
    private final class gioey extends GDViewDelegateAdapter {
        private final EditorState dbjc;

        private gioey() {
            this.dbjc = new EditorState();
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActionNext() {
            super.onActionNext();
            GDEProvisionView.this.doProvision();
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActivityCreate(Bundle bundle) {
            if (bundle == null) {
                return;
            }
            GDEProvisionView gDEProvisionView = GDEProvisionView.this;
            gDEProvisionView.restoreEditTextState(bundle, "email", gDEProvisionView.emailField);
            GDEProvisionView gDEProvisionView2 = GDEProvisionView.this;
            gDEProvisionView2.restoreEditTextState(bundle, GDEProvisionView.STATE_PASSWORD, gDEProvisionView2.passwordField);
            GDEProvisionView.this.passwordWatcher.restorePasswordEyeState(bundle.getBoolean(GDEProvisionView.STATE_PASSWORD_TRANSFORMATION, true));
            if (GDEProvisionView.this.uiData.hasBCPURLField()) {
                GDEProvisionView gDEProvisionView3 = GDEProvisionView.this;
                gDEProvisionView3.restoreEditTextState(bundle, GDEProvisionView.STATE_BCP_URL, gDEProvisionView3.bcpURLField);
            }
            int i = bundle.getInt(GDEProvisionView.STATE_FIELD, 0);
            if (i == 1) {
                GDEProvisionView gDEProvisionView4 = GDEProvisionView.this;
                gDEProvisionView4.focusField = gDEProvisionView4.bcpURLField;
            } else if (i != 2) {
                GDEProvisionView gDEProvisionView5 = GDEProvisionView.this;
                gDEProvisionView5.focusField = gDEProvisionView5.emailField;
            } else {
                GDEProvisionView gDEProvisionView6 = GDEProvisionView.this;
                gDEProvisionView6.focusField = gDEProvisionView6.passwordField;
            }
            GDEProvisionView.this.focusField.setVisibility(0);
            this.dbjc.restore(GDEProvisionView.this.focusField, bundle);
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActivityPause() {
            super.onActivityPause();
            if (!GDEProvisionView.this.uiData.hasBCPURLField() || !GDEProvisionView.this.bcpURLField.isFocused()) {
                if (GDEProvisionView.this.passwordField.isFocused()) {
                    GDEProvisionView gDEProvisionView = GDEProvisionView.this;
                    gDEProvisionView.focusField = gDEProvisionView.passwordField;
                } else {
                    GDEProvisionView gDEProvisionView2 = GDEProvisionView.this;
                    gDEProvisionView2.focusField = gDEProvisionView2.emailField;
                }
            } else {
                GDEProvisionView gDEProvisionView3 = GDEProvisionView.this;
                gDEProvisionView3.focusField = gDEProvisionView3.bcpURLField;
            }
            GDEProvisionView.this.passwordWatcher.resetRestoredPasswordEyeState();
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActivityResume() {
            super.onActivityResume();
            GDEProvisionView.this.enableControls();
            GDEProvisionView.this.showKeyboard();
            GDEProvisionView.this.passwordWatcher.restorePasswordEyeStateAfterBackgroundTransition();
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActivityStart() {
            super.onActivityStart();
            GDEProvisionView.this.enableControls();
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onSaveInstanceState(Bundle bundle) {
            int i;
            super.onSaveInstanceState(bundle);
            GDEProvisionView gDEProvisionView = GDEProvisionView.this;
            gDEProvisionView.saveEditTextState(bundle, "email", gDEProvisionView.emailField);
            GDEProvisionView gDEProvisionView2 = GDEProvisionView.this;
            gDEProvisionView2.saveEditTextState(bundle, GDEProvisionView.STATE_BCP_URL, gDEProvisionView2.bcpURLField);
            GDEProvisionView gDEProvisionView3 = GDEProvisionView.this;
            gDEProvisionView3.saveEditTextState(bundle, GDEProvisionView.STATE_PASSWORD, gDEProvisionView3.passwordField);
            bundle.putBoolean(GDEProvisionView.STATE_PASSWORD_TRANSFORMATION, GDEProvisionView.this.passwordWatcher.getPasswordState());
            if (!GDEProvisionView.this.uiData.hasBCPURLField() || !GDEProvisionView.this.bcpURLField.isFocused()) {
                if (GDEProvisionView.this.passwordField.isFocused()) {
                    i = 2;
                    this.dbjc.save(GDEProvisionView.this.passwordField, bundle);
                } else {
                    i = 0;
                    this.dbjc.save(GDEProvisionView.this.emailField, bundle);
                }
            } else {
                i = 1;
                this.dbjc.save(GDEProvisionView.this.bcpURLField, bundle);
            }
            bundle.putInt(GDEProvisionView.STATE_FIELD, i);
        }

        /* synthetic */ gioey(GDEProvisionView gDEProvisionView, hbfhc hbfhcVar) {
            this();
        }
    }
}
