package com.good.gd.ui.subcontainer_activationui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.good.gd.error.GDError;
import com.good.gd.resources.R;
import com.good.gd.support.impl.GDConnectedApplicationControl;
import com.good.gd.ui.subcontainer_activationui.GTWearActivationUIControl;
import com.good.gd.utils.GDLocalizer;
import com.good.gt.ndkproxy.util.GTLog;
import java.util.Locale;

/* loaded from: classes.dex */
public class GTWearActivationErrorFragment extends Fragment implements View.OnClickListener {
    private static final String ERROR_CODE_ID = "error_code_id";
    private String mErrorBody;
    private int mErrorCode;
    private String mErrorTitle;
    private boolean mRetryButtonEnabled;

    public static final GTWearActivationErrorFragment newInstance(int i) {
        GTWearActivationErrorFragment gTWearActivationErrorFragment = new GTWearActivationErrorFragment();
        Bundle bundle = new Bundle(1);
        bundle.putInt(ERROR_CODE_ID, i);
        gTWearActivationErrorFragment.setArguments(bundle);
        return gTWearActivationErrorFragment;
    }

    private void processErrorCode() {
        int i = this.mErrorCode;
        if (i != 105) {
            if (i == 107) {
                this.mErrorTitle = GDLocalizer.getLocalizedString("WF Code not match Title");
                this.mErrorBody = GDLocalizer.getLocalizedString("WF Code not match Body");
                this.mRetryButtonEnabled = true;
                return;
            } else if (i == 109) {
                this.mErrorTitle = GDLocalizer.getLocalizedString("WF Wearable Disabled Title");
                this.mErrorBody = GDLocalizer.getLocalizedString("WF Wearable Disabled details");
                this.mRetryButtonEnabled = false;
                return;
            } else if (i != 114) {
                switch (i) {
                    case 100:
                    case 103:
                        this.mErrorTitle = GDLocalizer.getLocalizedString("WF Connection Lost Title");
                        this.mErrorBody = GDLocalizer.getLocalizedString("WF Connection Lost Text");
                        this.mRetryButtonEnabled = true;
                        return;
                    case 101:
                        this.mErrorTitle = GDLocalizer.getLocalizedString("WF Unsupported Error Title");
                        this.mErrorBody = GDLocalizer.getLocalizedString("WF Unsupported Error details");
                        this.mRetryButtonEnabled = false;
                        return;
                    case 102:
                        this.mErrorTitle = GDLocalizer.getLocalizedString("WF Application update required");
                        this.mErrorBody = GDLocalizer.getLocalizedString("WF Application update required details");
                        this.mRetryButtonEnabled = false;
                        return;
                    default:
                        this.mErrorTitle = GDLocalizer.getLocalizedString("WF General Error Title");
                        this.mErrorBody = GDLocalizer.getLocalizedString("WF General Error details");
                        this.mRetryButtonEnabled = true;
                        return;
                }
            } else {
                throw new GDError("Error GD Android Wearable Framework App & GD Android SDK App must be based on the sameversion of Good Dynamics, Ensure that both applications are built on libraries from the same SDK release");
            }
        }
        throw new GDError("Error GD Wearable Framework applications must have GD App ID & Version specified in com.good.gd.settings.json file");
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.gd_error_retry) {
            GTWearActivationUIControl.getInstance().setActivationUIState(GTWearActivationUIControl.ActivationUIState.StateInitialSetupUI);
        } else if (view.getId() != R.id.gd_gtwear_activation_button1) {
        } else {
            GDConnectedApplicationControl.getInstance().ConnectedActivationComplete(GDConnectedApplicationControl.AppActivationCompletedState.CONNECTED_APP_ACTIVATION_ERROR);
            getActivity().finish();
        }
    }

    @Override // android.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mErrorCode = getArguments().getInt(ERROR_CODE_ID);
        processErrorCode();
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        GTLog.DBGPRINTF(16, "GTWearActivationErrorFragment: onCreateView\n");
        View inflate = layoutInflater.inflate(R.layout.gd_gtwear_wearable_error, viewGroup, false);
        ((TextView) inflate.findViewById(R.id.gd_gtwear_activation_title_text)).setText(this.mErrorTitle);
        ((TextView) inflate.findViewById(R.id.gd_gtwear_error_body)).setText(this.mErrorBody);
        if (this.mRetryButtonEnabled) {
            Button button = (Button) inflate.findViewById(R.id.gd_error_retry);
            button.setOnClickListener(this);
            button.setVisibility(0);
            button.setText(GDLocalizer.getLocalizedString("Retry").toUpperCase(Locale.getDefault()));
        }
        Button button2 = (Button) inflate.findViewById(R.id.gd_gtwear_activation_button1);
        button2.setText(GDLocalizer.getLocalizedString("WF Button Later").toUpperCase(Locale.getDefault()));
        button2.setVisibility(0);
        button2.setOnClickListener(this);
        return inflate;
    }
}
