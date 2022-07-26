package com.good.gd.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.security.KeyChain;
import android.security.KeyChainAliasCallback;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.good.gd.client.GDCustomizedUI;
import com.good.gd.messages.PKCSPasswordMsg;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.ui.GDPKCSPassword;
import com.good.gd.ndkproxy.ui.data.CertificateImportUI;
import com.good.gd.ndkproxy.ui.event.BBDCertImportUpdateEvent;
import com.good.gd.ndkproxy.ui.event.BBDUIEventManager;
import com.good.gd.ndkproxy.ui.event.BBDUIMessageType;
import com.good.gd.resources.R;
import com.good.gd.ui.base_ui.GDView;
import com.good.gd.utils.GDLocalizer;
import java.security.Principal;
import java.util.ArrayList;
import javax.security.auth.x500.X500Principal;

/* loaded from: classes.dex */
public class GDPKCSPasswordView extends GDView {
    private static final int DENY_ALIAS = 0;
    private static final String OTP_PASSWORD_KEY = "Certificate Enrolment Password";
    private static final String PKCS12_PASSWORD_KEY = "Certificate Import Password";
    private static final int SELECT_ALIAS = 1;
    private static String _profileAlias;
    private RelativeLayout back_dim_layout;
    private CertificateImportUI bbduiData;
    private final GDCustomizedUI customizedUI;
    private Handler mHandler;
    private boolean needSpinner;
    private final Button okayButton;
    private final EditText passwordField;
    private PasswordFieldTextWatcher passwordFieldTextWatcher = new PasswordFieldTextWatcher(this, null);
    private final TextView pwdMess;
    private final Button skipSetupButton;

    /* loaded from: classes.dex */
    private final class PasswordFieldTextWatcher implements TextWatcher {
        private PasswordFieldTextWatcher() {
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            GDPKCSPasswordView.this.updateOkayButtonState(editable);
        }

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        /* synthetic */ PasswordFieldTextWatcher(GDPKCSPasswordView gDPKCSPasswordView, fdyxd fdyxdVar) {
            this();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class aqdzk implements KeyChainAliasCallback {
        aqdzk() {
        }

        @Override // android.security.KeyChainAliasCallback
        public void alias(String str) {
            GDLog.DBGPRINTF(16, "Selected alias: " + str);
            GDPKCSPasswordView.this.onAliasSelected(str);
        }
    }

    /* loaded from: classes.dex */
    class ehnkx implements OnClickListener {
        final /* synthetic */ CertificateImportUI dbjc;

        ehnkx(GDPKCSPasswordView gDPKCSPasswordView, CertificateImportUI certificateImportUI) {
            this.dbjc = certificateImportUI;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            BBDUIEventManager.sendMessage(this.dbjc, BBDUIMessageType.MSG_CLIENT_PKCS_PASSWORD_LATER);
        }
    }

    /* loaded from: classes.dex */
    class fdyxd implements TextView.OnEditorActionListener {
        final /* synthetic */ CertificateImportUI dbjc;

        fdyxd(CertificateImportUI certificateImportUI) {
            this.dbjc = certificateImportUI;
        }

        @Override // android.widget.TextView.OnEditorActionListener
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i == 6) {
                if (this.dbjc.getStateType() != GDPKCSPassword.StateType.Pkcs12 && GDPKCSPasswordView.this.passwordField.getText().length() == 0) {
                    return true;
                }
                GDPKCSPasswordView.this.disableControls();
                GDPKCSPasswordView.this.passwordWillValidate();
                return true;
            }
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class gioey implements DialogInterface.OnClickListener {
        gioey(GDPKCSPasswordView gDPKCSPasswordView) {
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class hbfhc implements DialogInterface.OnClickListener {
        hbfhc() {
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            GDPKCSPasswordView.this.enableControls();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class mjbm extends Handler {
        mjbm(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 0) {
                GDPKCSPasswordView.this.denyAliasSelection();
            } else if (i != 1) {
            } else {
                GDPKCSPasswordView.this.handleSelectedAlias((String) message.obj);
            }
        }
    }

    /* loaded from: classes.dex */
    private final class ooowe extends GDViewDelegateAdapter {
        private ooowe() {
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActivityCreate(Bundle bundle) {
            if (bundle == null) {
                return;
            }
            String string = bundle.getString(GDPKCSPasswordView.PKCS12_PASSWORD_KEY);
            if (string != null) {
                GDPKCSPasswordView.this.passwordField.setText(string);
                GDPKCSPasswordView.this.passwordField.setSelection(string.length());
            }
            GDPKCSPasswordView gDPKCSPasswordView = GDPKCSPasswordView.this;
            gDPKCSPasswordView.updateOkayButtonState(gDPKCSPasswordView.passwordField.getText());
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActivityResume() {
            super.onActivityResume();
            GDPKCSPasswordView.this.updateInformativeMessageTextView();
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActivityStart() {
            super.onActivityStart();
            GDPKCSPasswordView.this.clearFieldsForSecurity();
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onSaveInstanceState(Bundle bundle) {
            super.onSaveInstanceState(bundle);
            String password = GDPKCSPasswordView.this.getPassword();
            if (!TextUtils.isEmpty(password)) {
                bundle.putString(GDPKCSPasswordView.PKCS12_PASSWORD_KEY, password);
            }
        }

        /* synthetic */ ooowe(GDPKCSPasswordView gDPKCSPasswordView, fdyxd fdyxdVar) {
            this();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class orlrx implements DialogInterface.OnClickListener {
        orlrx(GDPKCSPasswordView gDPKCSPasswordView) {
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
        }
    }

    /* loaded from: classes.dex */
    class pmoiy implements OnClickListener {
        final /* synthetic */ CertificateImportUI dbjc;

        pmoiy(CertificateImportUI certificateImportUI) {
            this.dbjc = certificateImportUI;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (this.dbjc.getStateType() == GDPKCSPassword.StateType.DeviceKeyStore) {
                GDPKCSPasswordView.this.chooseAlias();
                return;
            }
            GDPKCSPasswordView.this.disableControls();
            GDPKCSPasswordView.this.passwordWillValidate();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class vzw implements DialogInterface.OnClickListener {
        final /* synthetic */ String dbjc;

        vzw(String str) {
            this.dbjc = str;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            GDPKCSPasswordView.this.showSpinner();
            BBDUIEventManager.sendMessage(GDPKCSPasswordView.this.bbduiData, BBDUIMessageType.MSG_CLIENT_PKCS_PASSWORD_REQUEST, new PKCSPasswordMsg(GDPKCSPasswordView.this.bbduiData.getUuid(), this.dbjc));
        }
    }

    /* loaded from: classes.dex */
    class yfdke implements Runnable {
        final /* synthetic */ boolean dbjc;

        yfdke(boolean z) {
            this.dbjc = z;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (!this.dbjc || GDPKCSPasswordView.this.passwordField.getVisibility() != 0 || !GDPKCSPasswordView.this.passwordField.isEnabled()) {
                return;
            }
            GDPKCSPasswordView gDPKCSPasswordView = GDPKCSPasswordView.this;
            gDPKCSPasswordView.requestShowKeyboard(gDPKCSPasswordView.passwordField);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class yjh implements DialogInterface.OnClickListener {
        yjh(GDPKCSPasswordView gDPKCSPasswordView) {
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
        }
    }

    public GDPKCSPasswordView(Context context, ViewInteractor viewInteractor, CertificateImportUI certificateImportUI, ViewCustomizer viewCustomizer) {
        super(context, viewInteractor, viewCustomizer);
        this.bbduiData = certificateImportUI;
        this.customizedUI = viewCustomizer.getGDCustomizedUI();
        this._delegate = new ooowe(this, null);
        setHasTextContainers(true);
        inflateLayout(R.layout.bbd_pkcs_password_view, this);
        EditText editText = (EditText) findViewById(R.id.COM_GOOD_GD_PKCS_PASSSWORD_VIEW_PASSWORD_FIELD);
        this.passwordField = editText;
        updatePasswordFieldHint();
        editText.setOnFocusChangeListener(this.fieldFocusListener);
        editText.setOnEditorActionListener(new fdyxd(certificateImportUI));
        editText.addTextChangedListener(this.passwordFieldTextWatcher);
        requestShowKeyboard(editText);
        Button button = (Button) findViewById(R.id.COM_GOOD_GD_PKCS_PASSSWORD_LATER_BUTTON);
        this.skipSetupButton = button;
        button.setOnClickListener(new ehnkx(this, certificateImportUI));
        button.setText(GDLocalizer.getLocalizedString("Certificate Skip Setup Button"));
        rearrangeButton(button);
        Button button2 = (Button) findViewById(R.id.COM_GOOD_GD_PKCS_PASSSWORD_ACCESS_BUTTON);
        this.okayButton = button2;
        button2.setOnClickListener(new pmoiy(certificateImportUI));
        button2.setText(GDLocalizer.getLocalizedString("Certificate Okay Button"));
        rearrangeButton(button2);
        TextView textView = (TextView) findViewById(R.id.COM_GOOD_GD_PKCS_PASSSWORD_MESSAGE);
        this.pwdMess = textView;
        textView.setMovementMethod(new ScrollingMovementMethod());
        textView.setFocusable(false);
        updateInformativeMessageTextView();
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.gd_bac_dim_layout);
        this.back_dim_layout = relativeLayout;
        relativeLayout.setClickable(true);
        if (isSpinnerRequired()) {
            showSpinner();
        }
        enableBottomLine();
        setBottomLabelVisibility(4);
        applyUICustomization();
        updatePasswordField();
        updatePostPoneButton();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void chooseAlias() {
        String[] issuers;
        GDPKCSPassword.KeyStoreRequestObject keyStoreObject = GDPKCSPassword.getKeyStoreObject(this.bbduiData.getUuid());
        _profileAlias = keyStoreObject.getAlias();
        String label = this.bbduiData.getLabel();
        ArrayList arrayList = new ArrayList();
        for (String str : keyStoreObject.getIssuers()) {
            try {
                arrayList.add(new X500Principal(str));
            } catch (IllegalArgumentException e) {
                GDLog.DBGPRINTF(16, "Something wrong with the provided issuers value: " + str + "\n", e);
                invalidProfileDataAlert();
                return;
            }
        }
        Principal[] principalArr = (Principal[]) arrayList.toArray(new Principal[arrayList.size()]);
        this.mHandler = new mjbm(Looper.getMainLooper());
        KeyChain.choosePrivateKeyAlias(this.viewInteractor.mo295getInternalActivity(), new aqdzk(), keyStoreObject.getKeyTypes(), principalArr, label, keyStoreObject.getPort(), keyStoreObject.getAlias());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearFieldsForSecurity() {
        this.passwordField.setText("");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void denyAliasSelection() {
        String localizedString = GDLocalizer.getLocalizedString("Certificate Alert Error Title");
        String localizedString2 = GDLocalizer.getLocalizedString("Certificate Import: Deny Option Selection");
        enableControls();
        showPopupDialog(localizedString, localizedString2, GDLocalizer.getLocalizedString("Certificate Alert OK Button"), new yjh(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void disableControls() {
        if (this.bbduiData.getStateType() == GDPKCSPassword.StateType.DeviceKeyStore) {
            setButtonEnabled(this.okayButton, false);
        }
        this.passwordField.setEnabled(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enableControls() {
        this.passwordField.setEnabled(true);
        this.passwordField.clearFocus();
        setButtonEnabled(this.okayButton, true);
        hideSpinner();
    }

    private String getFormatBasedOnOrientation() {
        return getResources().getConfiguration().orientation == 2 ? "%s <b>%s</b><br>%s" : "%s<br><b>%s</b><br>%s";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSelectedAlias(String str) {
        showSpinner();
        String str2 = _profileAlias;
        if (str2 != null && !str2.isEmpty() && !_profileAlias.equals(str)) {
            String localizedString = GDLocalizer.getLocalizedString("Certificate Alert Error Title");
            String format = String.format(GDLocalizer.getLocalizedString("Certificate Import: Incorrect Certificate Selection Post First time setup"), this.bbduiData.getLabel());
            enableControls();
            showPopupDialog(localizedString, format, GDLocalizer.getLocalizedString("Certificate Alert OK Button"), new orlrx(this));
        } else if (!GDPKCSPassword.hasProfileWithAlias(str)) {
            CertificateImportUI certificateImportUI = this.bbduiData;
            BBDUIEventManager.sendMessage(certificateImportUI, BBDUIMessageType.MSG_CLIENT_PKCS_PASSWORD_REQUEST, new PKCSPasswordMsg(certificateImportUI.getUuid(), str));
        } else {
            String localizedString2 = GDLocalizer.getLocalizedString("Certificate Import: Title In Use");
            String format2 = String.format(GDLocalizer.getLocalizedString("Certificate Import: Profile Certificate Match"), this.bbduiData.getLabel());
            enableControls();
            showPopupDialog2(localizedString2, format2, GDLocalizer.getLocalizedString("Option No"), new gioey(this), GDLocalizer.getLocalizedString("Option Yes"), new vzw(str));
        }
    }

    private void hideSpinner() {
        this.back_dim_layout.setVisibility(8);
        spinnerIsNotNeededAnyMore();
    }

    private void invalidProfileDataAlert() {
        showPopupDialog(GDLocalizer.getLocalizedString("Certificate Alert Error Title"), GDLocalizer.getLocalizedString("Certificate Enrolment Alert Error Message"), GDLocalizer.getLocalizedString("Certificate Alert OK Button"), new hbfhc());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAliasSelected(String str) {
        if (str != null) {
            Message.obtain(this.mHandler, 1, str).sendToTarget();
        } else {
            Message.obtain(this.mHandler, 0, null).sendToTarget();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void passwordWillValidate() {
        CertificateImportUI certificateImportUI = this.bbduiData;
        BBDUIEventManager.sendMessage(certificateImportUI, BBDUIMessageType.MSG_CLIENT_PKCS_PASSWORD_REQUEST, new PKCSPasswordMsg(certificateImportUI.getUuid(), getPassword()));
        clearFieldsForSecurity();
        if (this.bbduiData.getStateType() != GDPKCSPassword.StateType.Pkcs12) {
            showSpinner();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSpinner() {
        disableControls();
        this.back_dim_layout.setVisibility(0);
        spinnerHasToBe();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateInformativeMessageTextView() {
        String format;
        if (this.bbduiData.getStateType() == GDPKCSPassword.StateType.Pkcs12) {
            format = String.format(getFormatBasedOnOrientation(), GDLocalizer.getLocalizedString("Optional Certificate Title"), this.bbduiData.getLabel(), GDLocalizer.getLocalizedString("Certificate Import Information"));
        } else if (this.bbduiData.getStateType() == GDPKCSPassword.StateType.DeviceKeyStore) {
            String alias = GDPKCSPassword.getKeyStoreObject(this.bbduiData.getUuid()).getAlias();
            _profileAlias = alias;
            if (alias != null && !alias.isEmpty()) {
                if (this.bbduiData.isRequireCert()) {
                    format = String.format(getFormatBasedOnOrientation(), GDLocalizer.getLocalizedString("Required Certificate Title"), this.bbduiData.getLabel(), GDLocalizer.getLocalizedString("Certificate Import Info from Device Credential Store Post First Time setup"));
                } else {
                    format = String.format(getFormatBasedOnOrientation(), GDLocalizer.getLocalizedString("Optional Certificate Title"), this.bbduiData.getLabel(), GDLocalizer.getLocalizedString("Certificate Import Info from Device Credential Store Post First Time setup Optional"));
                }
            } else if (this.bbduiData.isRequireCert()) {
                format = String.format(getFormatBasedOnOrientation(), GDLocalizer.getLocalizedString("Required Certificate Title"), this.bbduiData.getLabel(), GDLocalizer.getLocalizedString("Certificate Import Info from Device Credential Store"));
            } else {
                format = String.format(getFormatBasedOnOrientation(), GDLocalizer.getLocalizedString("Optional Certificate Title"), this.bbduiData.getLabel(), GDLocalizer.getLocalizedString("Certificate Import Info from Device Credential Store Optional"));
            }
        } else if (!this.bbduiData.isRequirePassword()) {
            format = String.format(getFormatBasedOnOrientation(), GDLocalizer.getLocalizedString("Optional Certificate Title"), this.bbduiData.getLabel(), GDLocalizer.getLocalizedString("Certificate Enrolment Information No Password Required"));
        } else {
            format = String.format(getFormatBasedOnOrientation(), GDLocalizer.getLocalizedString("Required Certificate Title"), this.bbduiData.getLabel(), GDLocalizer.getLocalizedString("Certificate Enrolment Information"));
        }
        this.pwdMess.setText(Html.fromHtml(format));
    }

    private void updatePasswordField() {
        if (this.bbduiData.getStateType() != GDPKCSPassword.StateType.Pkcs12 && !this.bbduiData.isRequirePassword()) {
            this.passwordField.setVisibility(8);
        } else {
            this.passwordField.setVisibility(0);
        }
    }

    private void updatePasswordFieldHint() {
        if (this.bbduiData.getStateType() != GDPKCSPassword.StateType.OtpWithoutErrors && this.bbduiData.getStateType() != GDPKCSPassword.StateType.OtpPasswordIncorrect && this.bbduiData.getStateType() != GDPKCSPassword.StateType.GcError) {
            this.passwordField.setHint(GDLocalizer.getLocalizedString(PKCS12_PASSWORD_KEY));
        } else {
            this.passwordField.setHint(GDLocalizer.getLocalizedString(OTP_PASSWORD_KEY));
        }
    }

    private void updatePostPoneButton() {
        if (this.bbduiData.isRequireCert()) {
            this.skipSetupButton.setVisibility(4);
        } else {
            this.skipSetupButton.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.good.gd.ui.base_ui.GDView
    public void applyUICustomization() {
        super.applyUICustomization();
        if (!this.customizedUI.isUICustomized() || this.customizedUI.getCustomUIColor() == null) {
            return;
        }
        this.okayButton.setTextColor(this.customizedUI.getCustomUIColor().intValue());
        this.skipSetupButton.setTextColor(this.customizedUI.getCustomUIColor().intValue());
        customizeEditTextField(this.passwordField, this.customizedUI.getCustomUIColor().intValue());
    }

    @Override // com.good.gd.ui.base_ui.GDView
    public void clearSensitiveData() {
        this.passwordField.setText("");
    }

    public String getPassword() {
        if (this.passwordField.getText() != null) {
            return this.passwordField.getText().toString().trim();
        }
        throw new Error("trying to get null text in " + GDPKCSPasswordView.class.getSimpleName());
    }

    boolean isSpinnerRequired() {
        return this.needSpinner;
    }

    @Override // com.good.gd.ui.base_ui.GDView
    public void onBackPressed() {
        moveTaskToBack();
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        post(new yfdke(z));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.good.gd.ui.base_ui.GDView
    public void showPopupDialog(String str, String str2, String str3, DialogInterface.OnClickListener onClickListener) {
        this.viewInteractor.cancelDialog();
        super.showPopupDialog(str, str2, str3, onClickListener);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.good.gd.ui.base_ui.GDView
    public void showPopupDialog2(String str, String str2, String str3, DialogInterface.OnClickListener onClickListener, String str4, DialogInterface.OnClickListener onClickListener2) {
        this.viewInteractor.cancelDialog();
        super.showPopupDialog2(str, str2, str3, onClickListener, str4, onClickListener2);
    }

    void spinnerHasToBe() {
        this.needSpinner = true;
    }

    void spinnerIsNotNeededAnyMore() {
        this.needSpinner = false;
    }

    @Override // com.good.gd.ui.base_ui.GDView
    public void stateWasUpdated() {
        BBDCertImportUpdateEvent certImportEvent = this.bbduiData.getCertImportEvent();
        if (certImportEvent == null) {
            return;
        }
        clearFieldsForSecurity();
        updatePasswordFieldHint();
        int ordinal = certImportEvent.getInstructionType().ordinal();
        if (ordinal != 3) {
            if (ordinal != 4) {
                return;
            }
            enableControls();
            return;
        }
        updateInformativeMessageTextView();
        updatePasswordField();
        updatePostPoneButton();
        enableControls();
    }

    void updateOkayButtonState(Editable editable) {
        if (this.bbduiData.getStateType() != GDPKCSPassword.StateType.Pkcs12 && this.bbduiData.isRequirePassword() && editable.length() == 0) {
            if (editable.length() != 0 || editable.length() != this.passwordField.getText().length()) {
                return;
            }
            setButtonEnabled(this.okayButton, false);
            return;
        }
        setButtonEnabled(this.okayButton, true);
    }
}
