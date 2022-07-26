package com.good.gd.ui.subcontainer_activationui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import com.good.gd.GDAndroid;
import com.good.gd.GDStateListener;
import com.good.gd.resources.R;
import com.good.gd.support.impl.GDConnectedApplicationControl;
import com.good.gd.ui.subcontainer_activationui.GTWearActivationUIControl;
import com.good.gd.ui_control.CutOutUiSupportManager;
import com.good.gt.ndkproxy.util.GTLog;
import java.util.Map;

/* loaded from: classes.dex */
public class GTWearActivationUIActivity extends Activity implements GDStateListener {
    private static final String FRAGMENT_TAG = "gdwear_fragment";
    private static final String TAG = GTWearActivationUIActivity.class.getSimpleName();
    private static final String VERIFICATION_CODE_ID = "verification_code_id";
    private GTWearActivationUIControl mUIControl;
    private String mVerificationCode;

    private void loadFragment(Fragment fragment) {
        FragmentTransaction beginTransaction = getFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.gtwear_fragment_space, fragment, FRAGMENT_TAG);
        beginTransaction.commitAllowingStateLoss();
    }

    @Override // com.good.gd.GDStateListener
    public void onAuthorized() {
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        GDConnectedApplicationControl.getInstance().ConnectedActivationComplete(GDConnectedApplicationControl.AppActivationCompletedState.CONNECTED_APP_ACTIVATION_USER_CANCELLED);
        finish();
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        GTLog.DBGPRINTF(16, TAG, "onCreate");
        GDAndroid.getInstance().activityInit(this);
        CutOutUiSupportManager cutOutUiSupportManager = CutOutUiSupportManager.getInstance();
        if (cutOutUiSupportManager.checkForCutOut(this)) {
            cutOutUiSupportManager.applyCutOutUiChanges(this);
        }
        requestWindowFeature(1);
        setContentView(R.layout.gd_gtwear_activation_activity);
        GTWearActivationUIControl createInstance = GTWearActivationUIControl.createInstance();
        this.mUIControl = createInstance;
        createInstance.setActivationActivity(this);
        if (bundle != null) {
            this.mVerificationCode = bundle.getString(VERIFICATION_CODE_ID, "");
        }
        setActivationUI(this.mUIControl.getActivationUIState());
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        GTWearActivationUIControl.getInstance().clearActivationActivity(this);
    }

    @Override // com.good.gd.GDStateListener
    public void onLocked() {
    }

    @Override // android.app.Activity
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        GTLog.DBGPRINTF(16, TAG, "onNewIntent");
        setActivationUI(this.mUIControl.getActivationUIState());
    }

    @Override // android.app.Activity
    protected void onPause() {
        super.onPause();
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
    }

    @Override // android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString(VERIFICATION_CODE_ID, this.mVerificationCode);
    }

    @Override // com.good.gd.GDStateListener
    public void onUpdateConfig(Map<String, Object> map) {
    }

    @Override // com.good.gd.GDStateListener
    public void onUpdateEntitlements() {
    }

    @Override // com.good.gd.GDStateListener
    public void onUpdatePolicy(Map<String, Object> map) {
    }

    @Override // com.good.gd.GDStateListener
    public void onUpdateServices() {
    }

    @Override // com.good.gd.GDStateListener
    public void onWiped() {
    }

    public void setActivationUI(GTWearActivationUIControl.ActivationUIState activationUIState) {
        GTLog.DBGPRINTF(16, TAG, "setActivationUI state = " + activationUIState);
        int ordinal = activationUIState.ordinal();
        if (ordinal == 0) {
            loadFragment(new GTWearSetupFragment());
        } else if (ordinal == 1) {
            loadFragment(new GTWearActivationInProgressFragment());
        } else if (ordinal == 3) {
            loadFragment(new GTWearActivationInProgressPostValidationUIFragment());
        } else if (ordinal != 4) {
        } else {
            loadFragment(new GTWearActivationCompleteFragment());
        }
    }

    public void startActivationErrorUI(int i) {
        loadFragment(GTWearActivationErrorFragment.newInstance(i));
    }

    public void startUserValidationUI(String str) {
        if (this.mVerificationCode == null || str != null) {
            this.mVerificationCode = str;
        }
        loadFragment(GTWearActivationUserValidationFragment.newInstance(this.mVerificationCode));
    }
}
