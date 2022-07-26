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
import com.good.gd.ui_control.WearableNotificationManager;
import com.good.gd.utils.GDLocalizer;
import com.good.gt.ndkproxy.util.GTLog;
import java.util.Locale;

/* loaded from: classes.dex */
public class GTWearActivationCompleteFragment extends Fragment implements View.OnClickListener {
    private void applyUICustomization(View view) {
        ProgressBar progressBar;
        GDClient gDClient = GDClient.getInstance();
        if (gDClient.isUICustomized() && (progressBar = (ProgressBar) view.findViewById(R.id.gd_gtwear_activation_progressBar)) != null) {
            progressBar.setProgressDrawable(new ColorDrawable(gDClient.getCustomUIColor().intValue()));
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.gd_gtwear_activation_button2) {
            WearableNotificationManager.onActivationSuccess();
            GDConnectedApplicationControl.getInstance().ConnectedActivationComplete(GDConnectedApplicationControl.AppActivationCompletedState.CONNECTED_APP_ACTIVATION_SUCCESS);
            getActivity().finish();
        }
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        GTLog.DBGPRINTF(16, "GDWearActivationCompleteFragment: onCreateView\n");
        View inflate = layoutInflater.inflate(R.layout.gd_gtwear_wearable_complete, viewGroup, false);
        applyUICustomization(inflate);
        ((TextView) inflate.findViewById(R.id.gd_gtwear_activation_title_text)).setText(GDLocalizer.getLocalizedString("WF Done Title"));
        Button button = (Button) inflate.findViewById(R.id.gd_gtwear_activation_button2);
        button.setText(GDLocalizer.getLocalizedString("WF Button Done").toUpperCase(Locale.getDefault()));
        button.setOnClickListener(this);
        button.setVisibility(0);
        button.requestFocus();
        return inflate;
    }
}
