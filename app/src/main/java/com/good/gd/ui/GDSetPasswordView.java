package com.good.gd.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.good.gd.client.GDCustomizedUI;
import com.good.gd.messages.SetPasswordMsg;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.ui.BBDUILocalizationHelper;
import com.good.gd.ndkproxy.ui.data.base.SetChangePasswordBaseUI;
import com.good.gd.ndkproxy.ui.event.BBDUIEventManager;
import com.good.gd.ndkproxy.ui.event.BBDUIMessageType;
import com.good.gd.ndkproxy.ui.event.BBDUIUpdateEvent;
import com.good.gd.resources.R;
import com.good.gd.ui.base_ui.GDView;
import com.good.gd.ui.dialogs.GDDialogState;
import com.good.gd.ui.utils.EditorState;
import com.good.gd.ui.utils.TextPasswordWatcher;
import com.good.gd.utils.DebuggableChecker;
import com.good.gd.utils.SensitiveDataUtils;

/* loaded from: classes.dex */
public final class GDSetPasswordView extends GDView {
    private static final int FIELD_CONFIRM = 2;
    private static final int FIELD_NEW = 1;
    private static final int FIELD_OLD = 0;
    private static final int SET_PASSWORD_ACTIVATION_PROGRESS = 9;
    private static final String STATE_CONFIRM_PASSWORD = "conf";
    private static final String STATE_FOCUSED_FIELD = "focus";
    private static final String STATE_NEW_PASSWORD = "new";
    private static final String STATE_OLD_PASSWORD = "old";
    private static final String STATE_PASSWORD_TRANSFORMATION = "text password transformation";
    private final Button accessButton;
    private final EditText confirmPasswordEditText;
    private final OnFocusChangeListener customfieldFocusListener;
    private final DebuggableChecker debuggableChecker;
    private final OnKeyListener enterActionKeyListener;
    private EditText focusField;
    private boolean isEmulator;
    private boolean multiCharsInputFilterEnabled = true;
    private final EditText oldPasswordEditText;
    private final EditText passwordEditText;
    private final TextView passwordRequiredText;
    private final TextPasswordWatcher passwordWatcher;
    private SetChangePasswordBaseUI uiData;
    private final ViewCustomizer viewCustomizer;
    private GDWaitingDialog waitingDialog;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class aqdzk implements DialogInterface.OnClickListener {
        aqdzk() {
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            GDSetPasswordView.this.enableControls();
            GDSetPasswordView gDSetPasswordView = GDSetPasswordView.this;
            gDSetPasswordView.requestShowKeyboard(gDSetPasswordView.focusField != null ? GDSetPasswordView.this.focusField : GDSetPasswordView.this.oldPasswordEditText);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class ehnkx implements OnClickListener {
        ehnkx() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            GDSetPasswordView.this.cancelAction();
        }
    }

    /* loaded from: classes.dex */
    class fdyxd implements OnClickListener {
        fdyxd() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            GDSetPasswordView.this.passwordWillSet();
        }
    }

    /* loaded from: classes.dex */
    private final class gioey implements TextWatcher, TextPasswordWatcher.TextPasswordWatcherCallback {
        private final EditText dbjc;
        private boolean qkduk = false;

        gioey(EditText editText) {
            this.dbjc = editText;
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
        }

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // com.good.gd.ui.utils.TextPasswordWatcher.TextPasswordWatcherCallback
        public void disableGuard() {
            this.qkduk = true;
        }

        @Override // com.good.gd.ui.utils.TextPasswordWatcher.TextPasswordWatcherCallback
        public void enableGuard() {
            this.qkduk = false;
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            boolean z = !TextUtils.isEmpty(GDSetPasswordView.this.passwordEditText.getText()) && !TextUtils.isEmpty(GDSetPasswordView.this.confirmPasswordEditText.getText());
            if (GDSetPasswordView.this.uiData.isChangePassword()) {
                z &= !TextUtils.isEmpty(GDSetPasswordView.this.oldPasswordEditText.getText());
                if (TextUtils.isEmpty(GDSetPasswordView.this.oldPasswordEditText.getText())) {
                    GDSetPasswordView.this.oldPasswordEditText.setAlpha(GDSetPasswordView.this.getFloatValueFromResource(R.dimen.gd_text_transparent));
                } else {
                    GDSetPasswordView.this.oldPasswordEditText.setAlpha(GDSetPasswordView.this.getFloatValueFromResource(R.dimen.gd_text_opaque));
                }
            }
            if (!GDSetPasswordView.this.debuggableChecker.isApplicationDebuggable() && GDSetPasswordView.this.multiCharsInputFilterEnabled && !this.qkduk) {
                int abs = Math.abs(i3 - i2);
                if (i3 > 1 && abs > 1) {
                    GDLog.DBGPRINTF(16, "SetPasswordFieldTextWatcher.onTextChanged: multi-characters input filtered\n");
                    this.dbjc.getText().replace(i, i3 + i, "");
                    return;
                }
            }
            GDSetPasswordView gDSetPasswordView = GDSetPasswordView.this;
            gDSetPasswordView.setButtonEnabled(gDSetPasswordView.accessButton, z);
        }
    }

    /* loaded from: classes.dex */
    class hbfhc implements OnFocusChangeListener {
        hbfhc() {
        }

        @Override // android.view.View.OnFocusChangeListener
        public void onFocusChange(View view, boolean z) {
            GDSetPasswordView.this.setEditTextBackground(view, z);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class mjbm implements OnClickListener {
        final /* synthetic */ String dbjc;
        final /* synthetic */ DialogInterface.OnClickListener qkduk;

        mjbm(String str, DialogInterface.OnClickListener onClickListener) {
            this.dbjc = str;
            this.qkduk = onClickListener;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            String join = TextUtils.join("", GDSetPasswordView.this.uiData.getPasswordGuide());
            GDDialogState.getInstance().setCanceledOnTouchOutside(true);
            GDSetPasswordView gDSetPasswordView = GDSetPasswordView.this;
            gDSetPasswordView.showPopupDialog(this.dbjc, join, gDSetPasswordView.getResources().getString(17039370), this.qkduk);
        }
    }

    /* loaded from: classes.dex */
    class orlrx extends GDViewDelegateAdapter {
        private final EditorState dbjc = new EditorState();

        orlrx() {
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActionNext() {
            super.onActionNext();
            GDSetPasswordView.this.passwordWillSet();
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActivityCreate(Bundle bundle) {
            GDLog.DBGPRINTF(16, "GDSetPasswordViewDelegateAdapter.onActivityCreate: IN\n");
            if (bundle != null) {
                GDSetPasswordView.this.enableMultiCharsInputFilter(false);
                String string = bundle.getString(GDSetPasswordView.STATE_OLD_PASSWORD);
                if (string != null) {
                    GDSetPasswordView.this.oldPasswordEditText.setText(string);
                    GDSetPasswordView.this.oldPasswordEditText.setSelection(string.length());
                }
                String string2 = bundle.getString(GDSetPasswordView.STATE_NEW_PASSWORD);
                if (string2 != null) {
                    GDSetPasswordView.this.passwordEditText.setText(string2);
                    GDSetPasswordView.this.passwordEditText.setSelection(string2.length());
                    if (!TextUtils.isEmpty(GDSetPasswordView.this.passwordEditText.getText())) {
                        GDSetPasswordView.this.passwordEditText.setAlpha(GDSetPasswordView.this.getFloatValueFromResource(R.dimen.gd_text_opaque));
                    }
                }
                String string3 = bundle.getString(GDSetPasswordView.STATE_CONFIRM_PASSWORD);
                if (string3 != null) {
                    GDSetPasswordView.this.confirmPasswordEditText.setText(string3);
                    GDSetPasswordView.this.confirmPasswordEditText.setSelection(string3.length());
                    if (!TextUtils.isEmpty(GDSetPasswordView.this.confirmPasswordEditText.getText())) {
                        GDSetPasswordView.this.confirmPasswordEditText.setAlpha(GDSetPasswordView.this.getFloatValueFromResource(R.dimen.gd_text_opaque));
                    }
                }
                GDSetPasswordView.this.passwordWatcher.restorePasswordEyeState(bundle.getBoolean(GDSetPasswordView.STATE_PASSWORD_TRANSFORMATION, true));
                int i = bundle.getInt(GDSetPasswordView.STATE_FOCUSED_FIELD, 0);
                if (i == 0) {
                    GDSetPasswordView gDSetPasswordView = GDSetPasswordView.this;
                    gDSetPasswordView.focusField = gDSetPasswordView.oldPasswordEditText;
                } else if (i == 1) {
                    GDSetPasswordView gDSetPasswordView2 = GDSetPasswordView.this;
                    gDSetPasswordView2.focusField = gDSetPasswordView2.passwordEditText;
                } else if (i == 2) {
                    GDSetPasswordView gDSetPasswordView3 = GDSetPasswordView.this;
                    gDSetPasswordView3.focusField = gDSetPasswordView3.confirmPasswordEditText;
                }
                GDSetPasswordView.this.enableMultiCharsInputFilter(true);
                this.dbjc.restore(GDSetPasswordView.this.focusField, bundle);
            }
            GDLog.DBGPRINTF(16, "GDSetPasswordViewDelegateAdapter.onActivityCreate: OUT\n");
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActivityPause() {
            GDSetPasswordView.this.hideWaitingDialogIfShowing();
            GDSetPasswordView.this.passwordWatcher.resetRestoredPasswordEyeState();
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActivityResume() {
            super.onActivityResume();
            EditText editText = GDSetPasswordView.this.uiData.isChangePassword() ? GDSetPasswordView.this.oldPasswordEditText : GDSetPasswordView.this.passwordEditText;
            GDSetPasswordView gDSetPasswordView = GDSetPasswordView.this;
            if (gDSetPasswordView.focusField != null) {
                editText = GDSetPasswordView.this.focusField;
            }
            gDSetPasswordView.requestShowKeyboard(editText);
            GDSetPasswordView.this.passwordWatcher.restorePasswordEyeStateAfterBackgroundTransition();
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActivityStart() {
            super.onActivityStart();
            GDSetPasswordView.this.clearFieldsForSecurity();
            GDSetPasswordView.this.enableControls();
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onSaveInstanceState(Bundle bundle) {
            GDLog.DBGPRINTF(16, "GDSetPasswordViewDelegateAdapter.onSaveInstanceState: IN\n");
            super.onSaveInstanceState(bundle);
            String obj = GDSetPasswordView.this.oldPasswordEditText.getText().toString();
            if (!TextUtils.isEmpty(obj)) {
                bundle.putString(GDSetPasswordView.STATE_OLD_PASSWORD, obj);
            }
            String obj2 = GDSetPasswordView.this.passwordEditText.getText().toString();
            if (!TextUtils.isEmpty(obj2)) {
                bundle.putString(GDSetPasswordView.STATE_NEW_PASSWORD, obj2);
            }
            String obj3 = GDSetPasswordView.this.confirmPasswordEditText.getText().toString();
            if (!TextUtils.isEmpty(obj3)) {
                bundle.putString(GDSetPasswordView.STATE_CONFIRM_PASSWORD, obj3);
            }
            if (!TextUtils.isEmpty(obj3) || !TextUtils.isEmpty(obj2)) {
                bundle.putBoolean(GDSetPasswordView.STATE_PASSWORD_TRANSFORMATION, GDSetPasswordView.this.passwordWatcher.getPasswordState());
            }
            if (!GDSetPasswordView.this.oldPasswordEditText.isFocused()) {
                if (!GDSetPasswordView.this.passwordEditText.isFocused()) {
                    if (GDSetPasswordView.this.confirmPasswordEditText.isFocused()) {
                        bundle.putInt(GDSetPasswordView.STATE_FOCUSED_FIELD, 2);
                        this.dbjc.save(GDSetPasswordView.this.confirmPasswordEditText, bundle);
                    }
                } else {
                    bundle.putInt(GDSetPasswordView.STATE_FOCUSED_FIELD, 1);
                    this.dbjc.save(GDSetPasswordView.this.passwordEditText, bundle);
                }
            } else {
                bundle.putInt(GDSetPasswordView.STATE_FOCUSED_FIELD, 0);
                this.dbjc.save(GDSetPasswordView.this.oldPasswordEditText, bundle);
            }
            GDLog.DBGPRINTF(16, "GDSetPasswordViewDelegateAdapter.onSaveInstanceState: OUT\n");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class pmoiy implements DialogInterface.OnClickListener {
        pmoiy(GDSetPasswordView gDSetPasswordView) {
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
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
                GDSetPasswordView.this.passwordWillSet();
                return true;
            }
            return false;
        }
    }

    public GDSetPasswordView(Context context, ViewInteractor viewInteractor, SetChangePasswordBaseUI setChangePasswordBaseUI, ViewCustomizer viewCustomizer) {
        super(context, viewInteractor, viewCustomizer);
        OnFocusChangeListener hbfhcVar = new hbfhc();
        this.customfieldFocusListener = hbfhcVar;
        OnKeyListener yfdkeVar = new yfdke();
        this.enterActionKeyListener = yfdkeVar;
        this.uiData = setChangePasswordBaseUI;
        this.viewCustomizer = viewCustomizer;
        this.debuggableChecker = viewCustomizer.getDebuggableChecker();
        this.isEmulator = viewCustomizer.getEmulatorChecker().isEmulator();
        this._delegate = new orlrx();
        boolean isAuthTypeNoPass = this.uiData.isAuthTypeNoPass();
        setHasTextContainers(true);
        inflateLayout(R.layout.bbd_set_password_view, this);
        EditText editText = (EditText) findViewById(R.id.COM_GOOD_GD_EPROV_SET_PWD_DLG_OLD_PWD_EDIT);
        this.oldPasswordEditText = editText;
        checkFieldNotNull(editText, "bbd_set_password_view", "COM_GOOD_GD_EPROV_SET_PWD_DLG_OLD_PWD_EDIT");
        editText.setHint(BBDUILocalizationHelper.getLocalizedOldPasswordKey(this.uiData));
        editText.setContentDescription(BBDUILocalizationHelper.getLocalizedOldPasswordKey(this.uiData));
        editText.setOnFocusChangeListener(hbfhcVar);
        setEditTextBackground(editText, false);
        EditText editText2 = (EditText) findViewById(R.id.COM_GOOD_GD_EPROV_SET_PWD_DLG_NEW_PWD_EDIT);
        this.passwordEditText = editText2;
        checkFieldNotNull(editText2, "bbd_set_password_view", "COM_GOOD_GD_EPROV_SET_PWD_DLG_NEW_PWD_EDIT");
        editText2.setHint(BBDUILocalizationHelper.getLocalizedNewPasswordKey(this.uiData));
        editText2.setContentDescription(BBDUILocalizationHelper.getLocalizedNewPasswordKey(this.uiData));
        editText2.setOnFocusChangeListener(hbfhcVar);
        setEditTextBackground(editText2, false);
        EditText editText3 = (EditText) findViewById(R.id.COM_GOOD_GD_EPROV_SET_PWD_DLG_CONFIRM_PWD_EDIT);
        this.confirmPasswordEditText = editText3;
        checkFieldNotNull(editText3, "bbd_set_password_view", "COM_GOOD_GD_EPROV_SET_PWD_DLG_CONFIRM_PWD_EDIT");
        editText3.setHint(BBDUILocalizationHelper.getLocalizedConfirmPasswordKey(this.uiData));
        editText3.setContentDescription(BBDUILocalizationHelper.getLocalizedConfirmPasswordKey(this.uiData));
        editText3.setOnFocusChangeListener(hbfhcVar);
        editText3.setOnKeyListener(yfdkeVar);
        editText.setOnKeyListener(yfdkeVar);
        editText2.setOnKeyListener(yfdkeVar);
        setEditTextBackground(editText3, false);
        ImageButton imageButton = (ImageButton) findViewById(R.id.password_eye);
        checkFieldNotNull(imageButton, "bbd_set_password_view", "password_eye");
        imageButton.setContentDescription(this.uiData.getLocalizedPasswordEyeText());
        TextPasswordWatcher textPasswordWatcher = new TextPasswordWatcher(editText3, editText2, imageButton, getResources());
        this.passwordWatcher = textPasswordWatcher;
        textPasswordWatcher.setOldPasswordField(editText);
        textPasswordWatcher.setBrandingColor(viewCustomizer.getGDCustomizedUI().getCustomUIColor());
        gioey gioeyVar = new gioey(editText2);
        gioey gioeyVar2 = new gioey(editText3);
        gioey gioeyVar3 = new gioey(editText);
        textPasswordWatcher.setCallbackPassword(gioeyVar);
        textPasswordWatcher.setCallbackConfirmPassword(gioeyVar2);
        textPasswordWatcher.setCallbackOldPassword(gioeyVar3);
        editText2.addTextChangedListener(gioeyVar);
        editText3.addTextChangedListener(gioeyVar2);
        editText.addTextChangedListener(gioeyVar3);
        editText3.addTextChangedListener(textPasswordWatcher);
        editText2.addTextChangedListener(textPasswordWatcher);
        editText.addTextChangedListener(textPasswordWatcher);
        TextView textView = (TextView) findViewById(R.id.COM_GOOD_GD_PASSWORD_REQUIRED_TEXT);
        this.passwordRequiredText = textView;
        checkFieldNotNull(textView, "bbd_set_password_view", "COM_GOOD_GD_PASSWORD_REQUIRED_TEXT");
        textView.setText(BBDUILocalizationHelper.getLocalizedPasswordNowRequiredKey(this.uiData));
        setLastEditTextForActionNext(editText3);
        Button button = (Button) findViewById(R.id.COM_GOOD_GD_EPROV_ACCESS_BUTTON);
        this.accessButton = button;
        checkFieldNotNull(button, "bbd_set_password_view", "COM_GOOD_GD_EPROV_ACCESS_BUTTON");
        button.setText(BBDUILocalizationHelper.getLocalizedOK());
        button.setOnClickListener(new fdyxd());
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.LLayoutOkCancelButtons);
        checkFieldNotNull(linearLayout, "bbd_set_password_view", "LLayoutOkCancelButtons");
        if (this.uiData.isChangePassword()) {
            linearLayout.setVisibility(0);
            editText.setVisibility(0);
            this.focusField = editText;
            setProgressLine(10);
        } else {
            linearLayout.setVisibility(8);
            editText.setVisibility(8);
            this.focusField = editText2;
            setProgressLine(9);
        }
        if (isAuthTypeNoPass) {
            textView.setVisibility(0);
        } else {
            textView.setVisibility(8);
        }
        setBottomLineBackground(true);
        if (this.uiData.hasCancelButton()) {
            addCancelButton();
        }
        enableMultiCharsInputFilter(true);
        clearFieldsForSecurity();
        applyUICustomization();
        customizeBottomLabel();
        enableBottomLine();
        adjustHeaderPositioning();
    }

    private void addCancelButton() {
        Button button = (Button) findViewById(R.id.COM_GOOD_GD_EPROV_CANCEL_BUTTON);
        button.setOnClickListener(new ehnkx());
        button.setVisibility(0);
        button.setText(BBDUILocalizationHelper.getLocalizedCancel());
        rearrangeButton(button);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelAction() {
        hideWaitingDialogIfShowing();
        if (this.uiData.isChangePassword()) {
            cancelSetPassword();
        } else {
            GDLog.DBGPRINTF(16, "GDSetPasswordView.onBackPressed: ignoring Back button in Set Password mode\n");
        }
    }

    private void cancelSetPassword() {
        if (this.uiData.isLaunchedByApp()) {
            clearFieldsForSecurity();
            BBDUIEventManager.sendMessage(this.uiData, BBDUIMessageType.MSG_UI_CANCEL_SET_PASSWORD);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearFieldsForSecurity() {
        if (this.uiData.isChangePassword()) {
            clearEditText(this.oldPasswordEditText);
        }
        clearEditText(this.passwordEditText);
        clearEditText(this.confirmPasswordEditText);
    }

    private void customizeBottomLabel() {
        String localizedPasswordRequirementsKey = BBDUILocalizationHelper.getLocalizedPasswordRequirementsKey(this.uiData);
        setBottomLabelTextAndAction(localizedPasswordRequirementsKey, new mjbm(localizedPasswordRequirementsKey, new pmoiy(this)));
    }

    private void disableControls() {
        this.oldPasswordEditText.setEnabled(false);
        this.passwordEditText.setEnabled(false);
        this.confirmPasswordEditText.setEnabled(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enableControls() {
        this.oldPasswordEditText.setEnabled(true);
        this.passwordEditText.setEnabled(true);
        this.confirmPasswordEditText.setEnabled(true);
        this.oldPasswordEditText.clearFocus();
        this.passwordEditText.clearFocus();
        this.confirmPasswordEditText.clearFocus();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enableMultiCharsInputFilter(boolean z) {
        this.multiCharsInputFilterEnabled = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideWaitingDialogIfShowing() {
        GDWaitingDialog gDWaitingDialog = this.waitingDialog;
        if (gDWaitingDialog == null || !gDWaitingDialog.isShowing()) {
            return;
        }
        this.waitingDialog.dismiss();
        this.waitingDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void passwordWillSet() {
        disableControls();
        char[] charSequenceToCharArray = SensitiveDataUtils.charSequenceToCharArray(this.oldPasswordEditText.getText());
        char[] charSequenceToCharArray2 = SensitiveDataUtils.charSequenceToCharArray(this.passwordEditText.getText());
        char[] charSequenceToCharArray3 = SensitiveDataUtils.charSequenceToCharArray(this.confirmPasswordEditText.getText());
        clearFieldsForSecurity();
        showWaitingDialog();
        BBDUIEventManager.sendMessage(this.uiData, BBDUIMessageType.MSG_UI_SET_NEW_PASSWORD, new SetPasswordMsg(charSequenceToCharArray, charSequenceToCharArray2, charSequenceToCharArray3));
    }

    private void processPasswordSuccessfullySet() {
        hideWaitingDialogIfShowing();
    }

    private void showWaitingDialog() {
        if (this.waitingDialog == null) {
            this.waitingDialog = new GDWaitingDialog(getContext(), this.viewCustomizer);
        }
        this.waitingDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.good.gd.ui.base_ui.GDView
    public void applyUICustomization() {
        super.applyUICustomization();
        GDCustomizedUI gDCustomizedUI = this.viewCustomizer.getGDCustomizedUI();
        if (!gDCustomizedUI.isUICustomized() || gDCustomizedUI.getCustomUIColor() == null) {
            return;
        }
        this.accessButton.setTextColor(gDCustomizedUI.getCustomUIColor().intValue());
        customizeEditTextField(this.oldPasswordEditText, gDCustomizedUI.getCustomUIColor().intValue());
        customizeEditTextField(this.passwordEditText, gDCustomizedUI.getCustomUIColor().intValue());
        customizeEditTextField(this.confirmPasswordEditText, gDCustomizedUI.getCustomUIColor().intValue());
    }

    void clearEditText(EditText editText) {
        editText.getText().clear();
        editText.getText().clearSpans();
        editText.setText("");
    }

    @Override // com.good.gd.ui.base_ui.GDView
    public void clearSensitiveData() {
        clearEditText(this.oldPasswordEditText);
        clearEditText(this.passwordEditText);
        clearEditText(this.confirmPasswordEditText);
        if (this.uiData.isChangePassword()) {
            this.oldPasswordEditText.requestFocus();
        } else {
            this.passwordEditText.requestFocus();
        }
    }

    @Override // com.good.gd.ui.base_ui.GDView
    public void onBackPressed() {
        cancelAction();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        hideWaitingDialogIfShowing();
    }

    public void showPasswordErrorDialog(String str, String str2) {
        hideWaitingDialogIfShowing();
        clearFieldsForSecurity();
        aqdzk aqdzkVar = new aqdzk();
        showPopupDialog2(str, str2, null, aqdzkVar, BBDUILocalizationHelper.getLocalizedOK(), aqdzkVar);
    }

    @Override // com.good.gd.ui.base_ui.GDView
    public void stateWasUpdated() {
        BBDUIUpdateEvent updateData = this.uiData.getUpdateData();
        if (updateData != null) {
            if (updateData.isSuccessful()) {
                processPasswordSuccessfullySet();
            } else {
                showPasswordErrorDialog(updateData.getTitle(), updateData.getText());
            }
        }
    }
}
