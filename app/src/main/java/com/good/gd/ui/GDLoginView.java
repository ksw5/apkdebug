package com.good.gd.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import com.good.gd.client.GDCustomizedUI;
import com.good.gd.messages.LoginMsg;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationManager;
import com.good.gd.ndkproxy.ui.BBDUILocalizationHelper;
import com.good.gd.ndkproxy.ui.data.UnlockUI;
import com.good.gd.ndkproxy.ui.event.BBDUIEventManager;
import com.good.gd.ndkproxy.ui.event.BBDUIMessageType;
import com.good.gd.ndkproxy.ui.event.BBDUIUpdateEvent;
import com.good.gd.ndkproxy.ui.event.UIEventType;
import com.good.gd.resources.R;
import com.good.gd.ui.base_ui.GDView;
import com.good.gd.ui.biometric.GDAbstractBiometricHelper;
import com.good.gd.ui.utils.EditorState;
import com.good.gd.ui.utils.TextPasswordWatcher;
import com.good.gd.utils.DebuggableChecker;
import com.good.gd.utils.EnterpriseModeChecker;
import com.good.gd.utils.GDLocalizer;
import com.good.gd.utils.SensitiveDataUtils;

/* loaded from: classes.dex */
public class GDLoginView extends GDView {
    private static final String STATE_ADDITIONAL_BUTTON = "additionalButton";
    private static final String STATE_ERROR_MESSAGE = "errorMessage";
    private static final String STATE_PASSWORD = "password";
    private static final String STATE_PASSWORD_TRANSFORMATION = "text password transformation";
    private final Button accessButton;
    private GDViewDelegateAdapter adapter;
    private final GDCustomizedUI customizedUI;
    private final DebuggableChecker debuggableChecker;
    private final EnterpriseModeChecker enterpriseModeChecker;
    private ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener;
    private final EditText passwordField;
    private TextPasswordWatcher passwordWatcher;
    private UnlockUI uiData;
    private boolean viewCreated = false;
    private boolean shouldShowKeyboard = false;
    private PasswordFieldTextWatcher passwordFieldTextWatcher = new PasswordFieldTextWatcher(this, null);

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class ehnkx implements DialogInterface.OnClickListener {
        ehnkx() {
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            GDLoginView.this.enableControls();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class fdyxd implements ViewTreeObserver.OnGlobalLayoutListener {
        fdyxd() {
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            GDLoginView.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            GDLoginView.this.viewCreated = true;
            if (GDLoginView.this.shouldShowKeyboard) {
                GDLoginView.this.requestShowKeyboard();
            }
        }
    }

    /* loaded from: classes.dex */
    class hbfhc implements OnKeyListener {
        hbfhc() {
        }

        @Override // android.view.View.OnKeyListener
        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            if (keyEvent.getAction() == 0) {
                if (i != 66 && i != 160) {
                    return false;
                }
                GDLoginView.this.nextButtonClicked();
                return true;
            }
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class pmoiy implements OnClickListener {
        pmoiy() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            GDFingerprintAuthenticationManager.getInstance().getAuthenticationHandler().startFingerprintEnrollment(GDLoginView.this.getContext());
        }
    }

    /* loaded from: classes.dex */
    class yfdke implements OnClickListener {
        yfdke() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            GDLoginView.this.proceedAction();
        }
    }

    public GDLoginView(Context context, ViewInteractor viewInteractor, UnlockUI unlockUI, ViewCustomizer viewCustomizer) {
        super(context, viewInteractor, viewCustomizer);
        this.uiData = null;
        EnterpriseModeChecker enterpriseModeChecker = viewCustomizer.getEnterpriseModeChecker();
        this.enterpriseModeChecker = enterpriseModeChecker;
        this.debuggableChecker = viewCustomizer.getDebuggableChecker();
        GDCustomizedUI gDCustomizedUI = viewCustomizer.getGDCustomizedUI();
        this.customizedUI = gDCustomizedUI;
        this.uiData = unlockUI;
        mjbm mjbmVar = new mjbm(this, null);
        this.adapter = mjbmVar;
        this._delegate = mjbmVar;
        setHasTextContainers(true);
        inflateLayout(R.layout.bbd_login_view, this);
        EditText editText = (EditText) findViewById(R.id.COM_GOOD_GD_LOGIN_VIEW_PASSWORD_FIELD);
        this.passwordField = editText;
        checkFieldNotNull(editText, "bbd_login_view", "COM_GOOD_GD_LOGIN_VIEW_PASSWORD_FIELD");
        CharSequence localizedString = GDLocalizer.getLocalizedString("Enter Password");
        editText.setHint(localizedString);
        if (Build.VERSION.SDK_INT > 25) {
            editText.setContentDescription(localizedString);
        }
        editText.setOnKeyListener(new hbfhc());
        editText.setOnFocusChangeListener(this.fieldFocusListener);
        editText.addTextChangedListener(this.passwordFieldTextWatcher);
        ImageButton imageButton = (ImageButton) findViewById(R.id.password_eye);
        checkFieldNotNull(imageButton, "bbd_login_view", "password_eye");
        imageButton.setContentDescription(unlockUI.getLocalizedPasswordEyeText());
        TextPasswordWatcher textPasswordWatcher = new TextPasswordWatcher(editText, imageButton, getResources());
        this.passwordWatcher = textPasswordWatcher;
        textPasswordWatcher.setBrandingColor(gDCustomizedUI.getCustomUIColor());
        this.passwordWatcher.setCallbackPassword(this.passwordFieldTextWatcher);
        editText.addTextChangedListener(this.passwordWatcher);
        setLastEditTextForAction(editText, 6);
        Button button = (Button) findViewById(R.id.COM_GOOD_GD_EPROV_ACCESS_BUTTON);
        this.accessButton = button;
        checkFieldNotNull(button, "gde_provision_view", "COM_GOOD_GD_EPROV_ACCESS_BUTTON");
        button.setText(GDLocalizer.getLocalizedString("Go"));
        button.setOnClickListener(new yfdke());
        button.setVisibility(4);
        applyUICustomization();
        adjustHeaderPositioning();
        enableBottomLine();
        enableBottomButton(unlockUI);
        setProgressLine(10);
        if (enterpriseModeChecker.enterpriseSimulationModeEnabled()) {
            setBottomLabelVisibility(8);
        }
        addOnViewCreatedListener();
    }

    private void addOnViewCreatedListener() {
        ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        fdyxd fdyxdVar = new fdyxd();
        this.globalLayoutListener = fdyxdVar;
        viewTreeObserver.addOnGlobalLayoutListener(fdyxdVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void adjustNextButton() {
        this.accessButton.setEnabled(!SensitiveDataUtils.isEmpty(getPassword()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearFieldsForSecurity() {
        this.passwordField.getText().clear();
        this.passwordField.getText().clearSpans();
        this.passwordField.setText("");
    }

    private void enableAdditionalButton(String str) {
        Button button = (Button) findViewById(R.id.COM_GOOD_GD_ADDITIONAL_BUTTON);
        button.setVisibility(0);
        button.setEnabled(true);
        button.setText(GDLocalizer.getLocalizedString(str));
        button.setOnClickListener(new pmoiy());
    }

    private void handleBottomButtonClicked() {
        BBDUIEventManager.sendMessage(this.uiData, BBDUIMessageType.MSG_UI_BOTTOM_BUTTON);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void nextButtonClicked() {
        disableControls();
        passwordWillValidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void proceedAction() {
        disableControls();
        passwordWillValidate();
    }

    private void restoreEditTextState(Bundle bundle, String str, EditText editText) {
        String string = bundle.getString(str);
        if (TextUtils.isEmpty(string)) {
            return;
        }
        editText.setText(string);
        editText.setSelection(string.length());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveViewState(Bundle bundle, String str, TextView textView) {
        CharSequence text = textView.getText();
        if (TextUtils.isEmpty(text)) {
            return;
        }
        bundle.putString(str, text.toString());
    }

    private void showErrorMessage(String str) {
        TextView textView = (TextView) findViewById(R.id.COM_GOOD_GD_LOGIN_VIEW_TEXT_FIELD);
        textView.setVisibility(0);
        textView.setText(GDLocalizer.getLocalizedString(str));
    }

    private boolean updateFingerprintExpiryView(String str, boolean z) {
        TextView textView = (TextView) findViewById(R.id.COM_GOOD_GD_LOGIN_VIEW_TEXT_FIELD);
        textView.setVisibility(z ? 0 : 8);
        if (z) {
            textView.setText(GDLocalizer.getLocalizedString(str).replace("[period]", String.valueOf(GDFingerprintAuthenticationManager.getInstance().getRequirePwdNotFingerprintPeriod())));
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.good.gd.ui.base_ui.GDView
    public void applyUICustomization() {
        super.applyUICustomization();
        if (!this.customizedUI.isUICustomized() || this.customizedUI.getCustomUIColor() == null) {
            return;
        }
        this.accessButton.setTextColor(this.customizedUI.getCustomUIColor().intValue());
        customizeEditTextField(this.passwordField, this.customizedUI.getCustomUIColor().intValue());
    }

    @Override // com.good.gd.ui.base_ui.GDView
    public void clearSensitiveData() {
        this.passwordField.setText("");
    }

    protected void disableControls() {
        this.passwordField.setEnabled(false);
        this.accessButton.setEnabled(false);
    }

    protected void enableControls() {
        this.passwordField.setEnabled(true);
        this.accessButton.setEnabled(true);
        this.passwordField.clearFocus();
        adjustNextButton();
    }

    public GDViewDelegateAdapter getAdapter() {
        return this.adapter;
    }

    public char[] getPassword() {
        return SensitiveDataUtils.charSequenceToCharArray(this.passwordField.getText());
    }

    @Override // com.good.gd.ui.base_ui.GDView
    public void onBackPressed() {
        moveTaskToBack();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    protected void passwordWillValidate() {
        this.uiData.resetUpdateData();
        BBDUIEventManager.sendMessage(this.uiData, BBDUIMessageType.MSG_UI_LOGIN_REQUEST, new LoginMsg(getPassword(), false));
        clearFieldsForSecurity();
    }

    @Override // com.good.gd.ui.base_ui.GDView
    public void requestHideKeyboard() {
        this.shouldShowKeyboard = false;
        requestHideKeyboard(this.passwordField);
    }

    @Override // com.good.gd.ui.base_ui.GDView
    public void requestShowKeyboard() {
        if (this.viewCreated) {
            requestShowKeyboard(this.passwordField);
        } else {
            this.shouldShowKeyboard = true;
        }
    }

    protected void restoreAdditionalButton(Bundle bundle) {
        String string = bundle.getString(STATE_ADDITIONAL_BUTTON);
        if (string != null) {
            enableAdditionalButton(string);
        }
    }

    protected void restoreErrorMessageText(Bundle bundle) {
        String string = bundle.getString(STATE_ERROR_MESSAGE);
        if (string != null) {
            showErrorMessage(string);
        }
    }

    protected void restorePasswordField(Bundle bundle) {
        this.passwordField.removeTextChangedListener(this.passwordFieldTextWatcher);
        restoreEditTextState(bundle, STATE_PASSWORD, this.passwordField);
        this.passwordField.addTextChangedListener(this.passwordFieldTextWatcher);
    }

    @Override // com.good.gd.ui.base_ui.GDView
    public void stateWasUpdated() {
        BBDUIUpdateEvent updateData = this.uiData.getUpdateData();
        if (updateData != null) {
            clearFieldsForSecurity();
            if (updateData.getType() == UIEventType.UI_UPDATE_BIOMETRY_ERROR) {
                String text = updateData.getText();
                if (text == null) {
                    text = GDAbstractBiometricHelper.DIALOG_TEXT_FINGERPRINT_CHANGED;
                }
                showErrorMessage(text);
            } else if (updateData.getType() == UIEventType.UI_UPDATE_BIOMETRY_NOT_ALLOWED) {
                String text2 = updateData.getText();
                if (text2 == null) {
                    text2 = GDAbstractBiometricHelper.DIALOG_TEXT_BIOMETRY_ERROR_UNLOCK;
                }
                showErrorMessage(text2);
                enableAdditionalButton(GDAbstractBiometricHelper.BUTTON_DEVICE_SETTINGS);
            } else if (updateData.getType() == UIEventType.UI_FINGERPRINT_EXPIRED) {
                String text3 = updateData.getText();
                if (text3 == null) {
                    text3 = GDAbstractBiometricHelper.DIALOG_TITLE_FINGERPRINT_EXPIRED;
                }
                updateFingerprintExpiryView(text3, updateData.isSuccessful());
            } else if (updateData.isSuccessful()) {
            } else {
                if (updateData.shouldShowPopup()) {
                    ehnkx ehnkxVar = new ehnkx();
                    showPopupDialog2(updateData.getTitle(), updateData.getText(), null, ehnkxVar, BBDUILocalizationHelper.getLocalizedOK(), ehnkxVar);
                    return;
                }
                enableControls();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public final class PasswordFieldTextWatcher implements TextWatcher, TextPasswordWatcher.TextPasswordWatcherCallback {
        private boolean dbjc;

        private PasswordFieldTextWatcher() {
            this.dbjc = false;
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
        }

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // com.good.gd.ui.utils.TextPasswordWatcher.TextPasswordWatcherCallback
        public void disableGuard() {
            this.dbjc = true;
        }

        @Override // com.good.gd.ui.utils.TextPasswordWatcher.TextPasswordWatcherCallback
        public void enableGuard() {
            this.dbjc = false;
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            GDLoginView.this.adjustNextButton();
            if (GDLoginView.this.debuggableChecker.isApplicationDebuggable() || this.dbjc) {
                return;
            }
            int abs = Math.abs(i3 - i2);
            if (i3 <= 1 || abs <= 1) {
                return;
            }
            GDLog.DBGPRINTF(16, "GDLoginView PasswordFieldTextWatcher.onTextChanged: multi-characters input filtered\n");
            GDLoginView.this.passwordField.getText().replace(i, i3 + i, "");
        }

        /* synthetic */ PasswordFieldTextWatcher(GDLoginView gDLoginView, hbfhc hbfhcVar) {
            this();
        }
    }

    /* loaded from: classes.dex */
    private final class mjbm extends GDViewDelegateAdapter {
        private EditorState dbjc;

        private mjbm() {
            this.dbjc = new EditorState();
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActionNext() {
            GDLoginView.this.proceedAction();
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActivityCreate(Bundle bundle) {
            super.onActivityCreate(bundle);
            GDLoginView.this.passwordField.requestFocus();
            if (bundle == null) {
                BBDUIUpdateEvent updateData = GDLoginView.this.uiData.getUpdateData();
                if (updateData == null || !updateData.isSuccessful()) {
                    return;
                }
                GDLoginView.this.stateWasUpdated();
                return;
            }
            GDLoginView.this.restorePasswordField(bundle);
            GDLoginView.this.restoreErrorMessageText(bundle);
            GDLoginView.this.restoreAdditionalButton(bundle);
            GDLoginView.this.passwordWatcher.restorePasswordEyeState(bundle.getBoolean(GDLoginView.STATE_PASSWORD_TRANSFORMATION, true));
            this.dbjc.restore(GDLoginView.this.passwordField, bundle);
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActivityDestroy() {
            super.onActivityDestroy();
            ViewTreeObserver viewTreeObserver = GDLoginView.this.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.removeOnGlobalLayoutListener(GDLoginView.this.globalLayoutListener);
            }
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActivityPause() {
            GDLoginView.this.passwordWatcher.resetRestoredPasswordEyeState();
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActivityResume() {
            GDLoginView.this.enableControls();
            GDLoginView.this.passwordWatcher.restorePasswordEyeStateAfterBackgroundTransition();
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActivityStart() {
            GDLoginView.this.clearFieldsForSecurity();
            GDLoginView.this.enableControls();
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onSaveInstanceState(Bundle bundle) {
            super.onSaveInstanceState(bundle);
            GDLoginView gDLoginView = GDLoginView.this;
            gDLoginView.saveViewState(bundle, GDLoginView.STATE_PASSWORD, gDLoginView.passwordField);
            GDLoginView gDLoginView2 = GDLoginView.this;
            gDLoginView2.saveViewState(bundle, GDLoginView.STATE_ERROR_MESSAGE, (TextView) gDLoginView2.findViewById(R.id.COM_GOOD_GD_LOGIN_VIEW_TEXT_FIELD));
            GDLoginView gDLoginView3 = GDLoginView.this;
            gDLoginView3.saveViewState(bundle, GDLoginView.STATE_ADDITIONAL_BUTTON, (TextView) gDLoginView3.findViewById(R.id.COM_GOOD_GD_ADDITIONAL_BUTTON));
            bundle.putBoolean(GDLoginView.STATE_PASSWORD_TRANSFORMATION, GDLoginView.this.passwordWatcher.getPasswordState());
            this.dbjc.save(GDLoginView.this.passwordField, bundle);
        }

        /* synthetic */ mjbm(GDLoginView gDLoginView, hbfhc hbfhcVar) {
            this();
        }
    }
}
