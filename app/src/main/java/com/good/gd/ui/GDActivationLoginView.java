package com.good.gd.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.good.gd.client.GDCustomizedUI;
import com.good.gd.containerstate.ContainerState;
import com.good.gd.messages.LoginMsg;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.ui.BBDUILocalizationHelper;
import com.good.gd.ndkproxy.ui.data.ActivationUnlockUI;
import com.good.gd.ndkproxy.ui.data.ApplicationLifecycleListener;
import com.good.gd.ndkproxy.ui.event.BBDUIEventManager;
import com.good.gd.ndkproxy.ui.event.BBDUIMessageType;
import com.good.gd.ndkproxy.ui.event.BBDUIUpdateEvent;
import com.good.gd.resources.R;
import com.good.gd.ui.base_ui.GDView;
import com.good.gd.ui.utils.EditorState;
import com.good.gd.utils.DebuggableChecker;
import com.good.gd.utils.GDLocalizer;
import com.good.gd.utils.NoPassChecker;
import com.good.gd.utils.SensitiveDataUtils;

/* loaded from: classes.dex */
public class GDActivationLoginView extends GDView {
    private static final String PASSWORD = GDLocalizer.getLocalizedString("Password");
    protected static boolean no_pass;
    private GDViewDelegateAdapter adapter;
    private final Button cancelButton;
    private final GDCustomizedUI customizedUI;
    private final DebuggableChecker debuggableChecker;
    private final Button okButton;
    private final EditText passwordField;
    private PasswordFieldTextWatcher passwordFieldTextWatcher = new PasswordFieldTextWatcher(this, null);
    private final ActivationUnlockUI uiData;

    /* loaded from: classes.dex */
    private final class PasswordFieldTextWatcher implements TextWatcher {
        private PasswordFieldTextWatcher() {
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            GDActivationLoginView.this.setButtonEnabled((Button) GDActivationLoginView.this.findViewById(R.id.btnOk), !TextUtils.isEmpty(editable));
        }

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (!GDActivationLoginView.this.debuggableChecker.isApplicationDebuggable()) {
                int abs = Math.abs(i3 - i2);
                if (i3 <= 1 || abs <= 1) {
                    return;
                }
                GDLog.DBGPRINTF(16, "GDActivationLoginView PasswordFieldTextWatcher.onTextChanged: multi-characters input filtered\n");
                GDActivationLoginView.this.passwordField.getText().replace(i, i3 + i, "");
            }
        }

        /* synthetic */ PasswordFieldTextWatcher(GDActivationLoginView gDActivationLoginView, hbfhc hbfhcVar) {
            this();
        }
    }

    /* loaded from: classes.dex */
    class ehnkx implements DialogInterface.OnClickListener {
        ehnkx() {
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            GDActivationLoginView.this.enableControls();
        }
    }

    /* loaded from: classes.dex */
    class fdyxd implements OnClickListener {
        fdyxd() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            GDActivationLoginView gDActivationLoginView = GDActivationLoginView.this;
            gDActivationLoginView.requestHideKeyboard(gDActivationLoginView.passwordField);
            BBDUIEventManager.sendMessage(GDActivationLoginView.this.uiData, BBDUIMessageType.MSG_UI_CANCEL);
        }
    }

    /* loaded from: classes.dex */
    class hbfhc implements OnClickListener {
        hbfhc() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (GDActivationLoginView.no_pass) {
                BBDUIEventManager.sendMessage(GDActivationLoginView.this.uiData, BBDUIMessageType.MSG_UI_LOGIN_REQUEST, new LoginMsg("".toCharArray(), false));
                return;
            }
            GDActivationLoginView.this.disableControls();
            GDActivationLoginView.this.passwordWillValidate();
        }
    }

    /* loaded from: classes.dex */
    class yfdke implements TextView.OnEditorActionListener {
        yfdke() {
        }

        @Override // android.widget.TextView.OnEditorActionListener
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i == 6) {
                GDActivationLoginView.this.disableControls();
                GDActivationLoginView.this.passwordWillValidate();
                return true;
            }
            return false;
        }
    }

    public GDActivationLoginView(Context context, ViewInteractor viewInteractor, ActivationUnlockUI activationUnlockUI, ViewCustomizer viewCustomizer, ContainerState containerState, ApplicationLifecycleListener applicationLifecycleListener) {
        super(context, viewInteractor, viewCustomizer);
        this.debuggableChecker = viewCustomizer.getDebuggableChecker();
        NoPassChecker noPassChecker = viewCustomizer.getNoPassChecker();
        this.uiData = activationUnlockUI;
        this.customizedUI = viewCustomizer.getGDCustomizedUI();
        GDLog.DBGPRINTF(14, "GDActivationLoginView.: applicationEnteringForeground\n");
        applicationLifecycleListener.applicationEnteringForeground(containerState.isAuthorized());
        pmoiy pmoiyVar = new pmoiy(this, null);
        this.adapter = pmoiyVar;
        this._delegate = pmoiyVar;
        boolean isAuthTypeNoPass = noPassChecker.isAuthTypeNoPass();
        no_pass = isAuthTypeNoPass;
        setHasTextContainers(!isAuthTypeNoPass);
        inflateLayout(R.layout.bbd_activation_login_view, this);
        Button button = (Button) findViewById(R.id.btnOk);
        this.okButton = button;
        button.setOnClickListener(new hbfhc());
        button.setText(BBDUILocalizationHelper.getLocalizedOK());
        EditText editText = (EditText) findViewById(R.id.passwordEditor);
        this.passwordField = editText;
        editText.setHint(GDLocalizer.getLocalizedString("Password"));
        editText.setOnFocusChangeListener(this.fieldFocusListener);
        editText.setOnEditorActionListener(new yfdke());
        PackageManager packageManager = context.getPackageManager();
        try {
            Drawable applicationIcon = packageManager.getApplicationIcon(activationUnlockUI.getAppPackageName());
            TextView textView = (TextView) findViewById(R.id.userInstructionText);
            ((ImageView) findViewById(R.id.reqAppIcon)).setImageDrawable(applicationIcon);
            if (no_pass) {
                editText.setVisibility(8);
                textView.setVisibility(8);
                setButtonEnabled(button, true);
            } else {
                editText.addTextChangedListener(this.passwordFieldTextWatcher);
                textView.setText(String.format(GDLocalizer.getLocalizedString("Enter the password for [App Name] to continue."), (String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(context.getPackageName(), 0))));
            }
        } catch (PackageManager.NameNotFoundException e) {
            GDLog.DBGPRINTF(12, "GDActivationLoginView - " + e + "\n");
            StackTraceElement[] stackTrace = e.getStackTrace();
            int length = stackTrace.length;
            for (int i = 0; i < length; i++) {
                GDLog.DBGPRINTF(16, "GDActivationLoginView " + stackTrace[i] + "\n");
            }
        }
        ((TextView) findViewById(R.id.reqAppNameText)).setText(this.uiData.getAppName() + " " + GDLocalizer.getLocalizedString("is requesting setup."));
        Button button2 = (Button) findViewById(R.id.btnCancel);
        this.cancelButton = button2;
        button2.setOnClickListener(new fdyxd());
        button2.setText(BBDUILocalizationHelper.getLocalizedCancel());
        rearrangeButton(button2);
        enableBottomLine();
        if (!no_pass) {
            enableHelpButton(activationUnlockUI);
        }
        applyUICustomization();
    }

    private char[] getPassword() {
        if (this.passwordField.getText() != null) {
            return SensitiveDataUtils.charSequenceToCharArray(this.passwordField.getText());
        }
        throw new Error("trying to get null text in " + GDActivationLoginView.class.getSimpleName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getPasswordStr() {
        if (this.passwordField.getText() != null) {
            return this.passwordField.getText().toString().trim();
        }
        throw new Error("trying to get null text in " + GDActivationLoginView.class.getSimpleName());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.good.gd.ui.base_ui.GDView
    public void applyUICustomization() {
        super.applyUICustomization();
        if (!this.customizedUI.isUICustomized() || this.customizedUI.getCustomUIColor() == null) {
            return;
        }
        this.okButton.setTextColor(this.customizedUI.getCustomUIColor().intValue());
        this.cancelButton.setTextColor(this.customizedUI.getCustomUIColor().intValue());
        customizeEditTextField(this.passwordField, this.customizedUI.getCustomUIColor().intValue());
    }

    protected void clearFieldsForSecurity() {
        this.passwordField.setText("");
    }

    @Override // com.good.gd.ui.base_ui.GDView
    public void clearSensitiveData() {
        this.passwordField.setText("");
    }

    protected void disableControls() {
        this.passwordField.setEnabled(false);
    }

    protected void enableControls() {
        this.passwordField.setEnabled(true);
        this.passwordField.clearFocus();
        setButtonEnabled((Button) findViewById(R.id.btnOk), !TextUtils.isEmpty(this.passwordField.getText().toString()));
    }

    public GDViewDelegateAdapter getAdapter() {
        return this.adapter;
    }

    @Override // com.good.gd.ui.base_ui.GDView
    public void onBackPressed() {
        moveTaskToBack();
    }

    protected void passwordWillValidate() {
        this.uiData.resetUpdateData();
        BBDUIEventManager.sendMessage(this.uiData, BBDUIMessageType.MSG_UI_LOGIN_REQUEST, new LoginMsg(getPassword(), false));
        clearFieldsForSecurity();
    }

    @Override // com.good.gd.ui.base_ui.GDView
    public void requestHideKeyboard() {
        if (!no_pass) {
            requestHideKeyboard(this.passwordField);
        }
    }

    @Override // com.good.gd.ui.base_ui.GDView
    public void requestShowKeyboard() {
        if (!no_pass) {
            requestShowKeyboard(this.passwordField);
        }
    }

    @Override // com.good.gd.ui.base_ui.GDView
    public void stateWasUpdated() {
        BBDUIUpdateEvent updateData = this.uiData.getUpdateData();
        if (updateData != null) {
            clearFieldsForSecurity();
            if (updateData.isSuccessful()) {
                return;
            }
            if (updateData.shouldShowPopup()) {
                showPopupDialog(updateData.getTitle(), updateData.getText(), BBDUILocalizationHelper.getLocalizedOK(), new ehnkx());
                return;
            }
            enableControls();
            requestShowKeyboard(this.passwordField);
        }
    }

    /* loaded from: classes.dex */
    private final class pmoiy extends GDViewDelegateAdapter {
        private EditorState dbjc;

        private pmoiy() {
            this.dbjc = new EditorState();
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActivityCreate(Bundle bundle) {
            super.onActivityCreate(bundle);
            if (bundle == null) {
                return;
            }
            String string = bundle.getString(GDActivationLoginView.PASSWORD);
            GDActivationLoginView.this.passwordField.removeTextChangedListener(GDActivationLoginView.this.passwordFieldTextWatcher);
            if (string != null) {
                GDActivationLoginView.this.passwordField.setText(string);
                GDActivationLoginView.this.passwordField.setSelection(string.length());
            }
            GDActivationLoginView.this.passwordField.addTextChangedListener(GDActivationLoginView.this.passwordFieldTextWatcher);
            this.dbjc.restore(GDActivationLoginView.this.passwordField, bundle);
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActivityResume() {
            if (!GDActivationLoginView.no_pass) {
                GDActivationLoginView.this.enableControls();
            }
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActivityStart() {
            super.onActivityStart();
            GDActivationLoginView.this.clearFieldsForSecurity();
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onSaveInstanceState(Bundle bundle) {
            super.onSaveInstanceState(bundle);
            String passwordStr = GDActivationLoginView.this.getPasswordStr();
            if (!TextUtils.isEmpty(passwordStr)) {
                bundle.putString(GDActivationLoginView.PASSWORD, passwordStr);
            }
            this.dbjc.save(GDActivationLoginView.this.passwordField, bundle);
        }

        /* synthetic */ pmoiy(GDActivationLoginView gDActivationLoginView, hbfhc hbfhcVar) {
            this();
        }
    }
}
