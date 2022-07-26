package com.good.gd.ui.subcontainer_activationui;

import android.app.Fragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.good.gd.client.GDClient;
import com.good.gd.resources.R;
import com.good.gd.support.impl.GDConnectedApplicationControl;
import com.good.gd.ui.subcontainer_activationui.GTWearActivationUIControl;
import com.good.gd.utils.GDLocalizer;
import com.good.gt.interdevice_icc.InterDeviceActivationControlInterface;
import java.util.Locale;

/* loaded from: classes.dex */
public class GTWearActivationUserValidationFragment extends Fragment implements View.OnClickListener {
    private static final String VERIFICATION_CODE_ID = "verification_code_id";
    private String mVerificationCode;

    private void applyUICustomization(View view) {
        ProgressBar progressBar;
        GDClient gDClient = GDClient.getInstance();
        if (gDClient.isUICustomized() && (progressBar = (ProgressBar) view.findViewById(R.id.gd_gtwear_activation_progressBar)) != null) {
            progressBar.setProgressDrawable(new ColorDrawable(gDClient.getCustomUIColor().intValue()));
        }
    }

    public static final GTWearActivationUserValidationFragment newInstance(String str) {
        GTWearActivationUserValidationFragment gTWearActivationUserValidationFragment = new GTWearActivationUserValidationFragment();
        Bundle bundle = new Bundle(1);
        bundle.putString(VERIFICATION_CODE_ID, str);
        gTWearActivationUserValidationFragment.setArguments(bundle);
        return gTWearActivationUserValidationFragment;
    }

    private void verificationCodeCorrect() {
        GTWearActivationUIControl.getInstance().userValidationCodeResult(this.mVerificationCode, InterDeviceActivationControlInterface.ValidationCodeResponse.Success);
        GTWearActivationUIControl.getInstance().setActivationUIState(GTWearActivationUIControl.ActivationUIState.StateActivationInProgressPostValidationUI);
    }

    private void verificationCodeWrong() {
        GTWearActivationUIControl.getInstance().userValidationCodeResult(this.mVerificationCode, InterDeviceActivationControlInterface.ValidationCodeResponse.Failure_CodeMisMatch);
        GTWearActivationUIControl.getInstance().displayActivationError(107);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.gd_gtwear_activation_button1) {
            GDConnectedApplicationControl.getInstance().ConnectedActivationComplete(GDConnectedApplicationControl.AppActivationCompletedState.CONNECTED_APP_ACTIVATION_USER_CANCELLED);
            GTWearActivationUIControl.getInstance().userValidationCodeResult(this.mVerificationCode, InterDeviceActivationControlInterface.ValidationCodeResponse.Failure_UserCancelled);
            getActivity().finish();
        } else if (view.getId() == R.id.gd_gtwear_button_correct) {
            verificationCodeCorrect();
        } else if (view.getId() != R.id.gd_gtwear_button_wrong) {
        } else {
            verificationCodeWrong();
        }
    }

    @Override // android.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mVerificationCode = getArguments().getString(VERIFICATION_CODE_ID);
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.gd_gtwear_activation_user_validation, viewGroup, false);
        applyUICustomization(inflate);
        ((TextView) inflate.findViewById(R.id.gd_gtwear_activation_title_text)).setText(GDLocalizer.getLocalizedString("WF Confirmation Title"));
        ((TextView) inflate.findViewById(R.id.gd_gtwear_validation_body1)).setText(GDLocalizer.getLocalizedString("WF Confirmation Body"));
        ((TextView) inflate.findViewById(R.id.gd_gtwear_validation_body2)).setText(GDLocalizer.getLocalizedString("WF Confirmation Code"));
        ((TextView) inflate.findViewById(R.id.gd_gtwear_verification_code_textview)).setText(this.mVerificationCode);
        Button button = (Button) inflate.findViewById(R.id.gd_gtwear_activation_button1);
        button.setText(GDLocalizer.getLocalizedString("WF Button Later").toUpperCase(Locale.getDefault()));
        button.setVisibility(0);
        button.setOnClickListener(this);
        Button button2 = (Button) inflate.findViewById(R.id.gd_gtwear_button_correct);
        button2.setText(GDLocalizer.getLocalizedString("WF Button Correct").toUpperCase(Locale.getDefault()));
        button2.setOnClickListener(this);
        Button button3 = (Button) inflate.findViewById(R.id.gd_gtwear_button_wrong);
        button3.setText(GDLocalizer.getLocalizedString("WF Button Wrong").toUpperCase(Locale.getDefault()));
        button3.setOnClickListener(this);
        return inflate;
    }
}
