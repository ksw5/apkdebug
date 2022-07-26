package com.good.gd.ui;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.core.app.NotificationCompat;
import com.good.gd.containerstate.ContainerState;
import com.good.gd.messages.LoginMsg;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.ui.BBDUILocalizationHelper;
import com.good.gd.ndkproxy.ui.data.ApplicationLifecycleListener;
import com.good.gd.ndkproxy.ui.data.ReauthenticationUnlockUI;
import com.good.gd.ndkproxy.ui.event.BBDUIEventManager;
import com.good.gd.ndkproxy.ui.event.BBDUIMessageType;
import com.good.gd.ndkproxy.ui.event.BBDUIUpdateEvent;
import com.good.gd.resources.R;
import com.good.gd.ui.base_ui.GDView;
import com.good.gd.utils.DebuggableChecker;
import com.good.gd.utils.GDLocalizer;
import com.good.gd.utils.NoPassChecker;
import com.good.gd.utils.SensitiveDataUtils;

/* loaded from: classes.dex */
public class GDReauthUnlockView extends GDView {
    private static final int FRAMES_PER_SECOND = 35;
    private static final int MILLS_IN_SEC = 1000;
    private static final String PASSWORD_KEY = "TIME";
    protected static boolean no_pass;
    private GDViewDelegateAdapter adapter;
    private final DebuggableChecker debuggableChecker;
    private ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener;
    private final EditText passwordField;
    private PasswordFieldTextWatcher passwordFieldTextWatcher = new PasswordFieldTextWatcher(this, null);
    private final ReauthenticationUnlockUI uiData;

    /* loaded from: classes.dex */
    private final class PasswordFieldTextWatcher implements TextWatcher {
        private PasswordFieldTextWatcher() {
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            ((Button) GDReauthUnlockView.this.findViewById(R.id.btnOk)).setEnabled(!TextUtils.isEmpty(editable));
        }

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (!GDReauthUnlockView.this.debuggableChecker.isApplicationDebuggable()) {
                int abs = Math.abs(i3 - i2);
                if (i3 <= 1 || abs <= 1) {
                    return;
                }
                GDLog.DBGPRINTF(16, "GDReauthUnlockView PasswordFieldTextWatcher.onTextChanged: multi-characters input filtered\n");
                GDReauthUnlockView.this.passwordField.getText().replace(i, i3 + i, "");
            }
        }

        /* synthetic */ PasswordFieldTextWatcher(GDReauthUnlockView gDReauthUnlockView, hbfhc hbfhcVar) {
            this();
        }
    }

    /* loaded from: classes.dex */
    private final class aqdzk extends GDViewDelegateAdapter {
        private aqdzk() {
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActivityCreate(Bundle bundle) {
            super.onActivityCreate(bundle);
            if (bundle != null) {
                String string = bundle.getString(GDReauthUnlockView.PASSWORD_KEY);
                GDReauthUnlockView.this.passwordField.removeTextChangedListener(GDReauthUnlockView.this.passwordFieldTextWatcher);
                if (string != null) {
                    GDReauthUnlockView.this.passwordField.setText(string);
                    GDReauthUnlockView.this.passwordField.setSelection(string.length());
                }
                GDReauthUnlockView.this.passwordField.addTextChangedListener(GDReauthUnlockView.this.passwordFieldTextWatcher);
            }
            GDReauthUnlockView.this.startAnimation();
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActivityResume() {
            if (!GDReauthUnlockView.no_pass) {
                GDReauthUnlockView.this.enableControls();
            }
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActivityStart() {
            super.onActivityStart();
            GDReauthUnlockView.this.clearFieldsForSecurity();
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onSaveInstanceState(Bundle bundle) {
            super.onSaveInstanceState(bundle);
            String passwordStr = GDReauthUnlockView.this.getPasswordStr();
            if (!TextUtils.isEmpty(passwordStr)) {
                bundle.putString(GDReauthUnlockView.PASSWORD_KEY, passwordStr);
            }
        }

        /* synthetic */ aqdzk(GDReauthUnlockView gDReauthUnlockView, hbfhc hbfhcVar) {
            this();
        }
    }

    /* loaded from: classes.dex */
    class ehnkx implements ViewTreeObserver.OnGlobalLayoutListener {
        final /* synthetic */ ScrollView dbjc;
        final /* synthetic */ Runnable qkduk;

        ehnkx(GDReauthUnlockView gDReauthUnlockView, ScrollView scrollView, Runnable runnable) {
            this.dbjc = scrollView;
            this.qkduk = runnable;
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            this.dbjc.fullScroll(33);
            this.dbjc.postDelayed(this.qkduk, 1000L);
        }
    }

    /* loaded from: classes.dex */
    class fdyxd implements Runnable {
        final /* synthetic */ ScrollView dbjc;

        fdyxd(ScrollView scrollView) {
            this.dbjc = scrollView;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.dbjc.getViewTreeObserver().removeOnGlobalLayoutListener(GDReauthUnlockView.this.globalLayoutListener);
        }
    }

    /* loaded from: classes.dex */
    class hbfhc implements OnClickListener {
        hbfhc() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (GDReauthUnlockView.no_pass) {
                BBDUIEventManager.sendMessage(GDReauthUnlockView.this.uiData, BBDUIMessageType.MSG_UI_LOGIN_REQUEST, new LoginMsg("".toCharArray(), false));
                return;
            }
            GDReauthUnlockView.this.disableControls();
            GDReauthUnlockView.this.passwordWillValidate();
        }
    }

    /* loaded from: classes.dex */
    class mjbm implements DialogInterface.OnClickListener {
        mjbm() {
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            GDReauthUnlockView.this.enableControls();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class pmoiy implements OnClickListener {
        pmoiy() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            GDReauthUnlockView.this.handleCancel();
        }
    }

    /* loaded from: classes.dex */
    class yfdke implements TextView.OnEditorActionListener {
        yfdke() {
        }

        @Override // android.widget.TextView.OnEditorActionListener
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i == 6) {
                GDReauthUnlockView.this.disableControls();
                GDReauthUnlockView.this.passwordWillValidate();
                return true;
            }
            return false;
        }
    }

    public GDReauthUnlockView(Context context, ViewInteractor viewInteractor, ReauthenticationUnlockUI reauthenticationUnlockUI, ViewCustomizer viewCustomizer, ContainerState containerState, ApplicationLifecycleListener applicationLifecycleListener) {
        super(context, viewInteractor, viewCustomizer);
        this.uiData = reauthenticationUnlockUI;
        this.debuggableChecker = viewCustomizer.getDebuggableChecker();
        NoPassChecker noPassChecker = viewCustomizer.getNoPassChecker();
        GDLog.DBGPRINTF(14, "GDReauthUnlockView: applicationEnteringForeground\n");
        applicationLifecycleListener.applicationEnteringForeground(containerState.isAuthorized());
        aqdzk aqdzkVar = new aqdzk(this, null);
        this.adapter = aqdzkVar;
        this._delegate = aqdzkVar;
        boolean isAuthTypeNoPass = noPassChecker.isAuthTypeNoPass();
        no_pass = isAuthTypeNoPass;
        setHasTextContainers(!isAuthTypeNoPass);
        inflateLayout(R.layout.bbd_reauth_unlock_view, this);
        Button button = (Button) findViewById(R.id.btnOk);
        button.setOnClickListener(new hbfhc());
        button.setText(BBDUILocalizationHelper.getLocalizedOK());
        EditText editText = (EditText) findViewById(R.id.passwordEditor);
        this.passwordField = editText;
        editText.setHint(GDLocalizer.getLocalizedString("Password"));
        editText.setOnFocusChangeListener(this.fieldFocusListener);
        editText.setOnEditorActionListener(new yfdke());
        TextView textView = (TextView) findViewById(R.id.userInstructionText);
        TextView textView2 = (TextView) findViewById(R.id.userText);
        TextView textView3 = (TextView) findViewById(R.id.userTitle);
        if (no_pass) {
            editText.setVisibility(8);
            textView.setVisibility(8);
            button.setEnabled(true);
        } else {
            editText.addTextChangedListener(this.passwordFieldTextWatcher);
            textView.setText(GDLocalizer.getLocalizedString(reauthenticationUnlockUI.isEnforced() ? "Enter password to authenticate" : "Enter password to authorize"));
        }
        Object[] objArr = new Object[2];
        objArr[0] = GDLocalizer.getLocalizedString(reauthenticationUnlockUI.isEnforced() ? "Authenticate" : "Authorize");
        objArr[1] = reauthenticationUnlockUI.getTitle();
        String format = String.format("%s \"%s\"", objArr);
        String text = reauthenticationUnlockUI.getText();
        textView3.setText(format);
        textView2.setText(text);
        if (reauthenticationUnlockUI.hasCancelButton()) {
            addCancelButton();
        }
        enableBottomLine();
        if (!no_pass) {
            enableBottomButton(reauthenticationUnlockUI);
        }
        enableHelpButton(reauthenticationUnlockUI);
        applyUICustomization();
        ScrollView scrollView = (ScrollView) findViewById(R.id.reauth_provision_view);
        this.globalLayoutListener = new ehnkx(this, scrollView, new fdyxd(scrollView));
        scrollView.getViewTreeObserver().addOnGlobalLayoutListener(this.globalLayoutListener);
    }

    private void addCancelButton() {
        Button button = (Button) findViewById(R.id.btnCancel);
        button.setOnClickListener(new pmoiy());
        button.setVisibility(0);
        button.setText(BBDUILocalizationHelper.getLocalizedCancel());
        rearrangeButton(button);
    }

    private char[] getPassword() {
        if (this.passwordField.getText() != null) {
            return SensitiveDataUtils.charSequenceToCharArray(this.passwordField.getText());
        }
        throw new Error("trying to get null text in " + GDReauthUnlockView.class.getSimpleName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getPasswordStr() {
        if (this.passwordField.getText() != null) {
            return this.passwordField.getText().toString().trim();
        }
        throw new Error("trying to get null text in " + GDReauthUnlockView.class.getSimpleName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleCancel() {
        requestHideKeyboard(this.passwordField);
        BBDUIEventManager.sendMessage(this.uiData, BBDUIMessageType.MSG_UI_CANCEL);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startAnimation() {
        if (!this.uiData.isEnforced()) {
            int timeout = (((int) this.uiData.getTimeout()) * 35) / 1000;
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.timeoutProgress);
            progressBar.setMax(timeout);
            ObjectAnimator ofInt = ObjectAnimator.ofInt(progressBar, NotificationCompat.CATEGORY_PROGRESS, 0, timeout);
            ofInt.setDuration(this.uiData.getTimeout());
            ofInt.setInterpolator(new LinearInterpolator());
            ofInt.setCurrentPlayTime(System.currentTimeMillis() - this.uiData.getStartTime());
            ofInt.start();
        }
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
        if (!no_pass) {
            disableBottomButton(this.uiData);
        }
    }

    protected void enableControls() {
        this.passwordField.setEnabled(true);
        this.passwordField.clearFocus();
        ((Button) findViewById(R.id.btnOk)).setEnabled(!TextUtils.isEmpty(this.passwordField.getText().toString()));
        enableBottomButton(this.uiData);
    }

    public GDViewDelegateAdapter getAdapter() {
        return this.adapter;
    }

    @Override // com.good.gd.ui.base_ui.GDView
    public void onBackPressed() {
        if (this.uiData.hasCancelButton()) {
            handleCancel();
        } else {
            moveTaskToBack();
        }
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
                showPopupDialog(updateData.getTitle(), updateData.getText(), BBDUILocalizationHelper.getLocalizedOK(), new mjbm());
                return;
            }
            enableControls();
            requestShowKeyboard(this.passwordField);
        }
    }
}
