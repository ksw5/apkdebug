package com.good.gd.ui.subcontainer_activationui;

import android.app.Fragment;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.good.gd.client.GDClient;
import com.good.gd.resources.R;
import com.good.gd.support.impl.GDConnectedApplicationControl;
import com.good.gd.utils.GDApplicationUtils;
import com.good.gd.utils.GDLocalizer;
import com.good.gt.ndkproxy.util.GTLog;
import java.util.Locale;

/* loaded from: classes.dex */
public class GTWearActivationInProgressFragment extends Fragment implements View.OnClickListener {
    private void applyUICustomization(View view) {
        GDClient gDClient = GDClient.getInstance();
        if (!gDClient.isUICustomized()) {
            return;
        }
        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.gd_gtwear_activation_progressBar);
        if (progressBar != null && progressBar.getIndeterminateDrawable() != null) {
            ((LayerDrawable) progressBar.getIndeterminateDrawable()).getDrawable(1).setColorFilter(gDClient.getCustomUIColor().intValue(), PorterDuff.Mode.SRC_IN);
        }
        GradientDrawable gradientDrawable = (GradientDrawable) ((ImageView) view.findViewById(R.id.gdwear_setup_icon)).getBackground();
        if (gradientDrawable == null) {
            return;
        }
        gradientDrawable.setColor(gDClient.getCustomUIColor().intValue());
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.gd_gtwear_activation_button1) {
            GDConnectedApplicationControl.getInstance().ConnectedActivationComplete(GDConnectedApplicationControl.AppActivationCompletedState.CONNECTED_APP_ACTIVATION_USER_CANCELLED);
            getActivity().finish();
        }
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        GTLog.DBGPRINTF(16, "GTWearActivationInProgressFragment: onCreateView\n");
        View inflate = layoutInflater.inflate(R.layout.gd_gtwear_activation_inprogress, viewGroup, false);
        ((ProgressBar) inflate.findViewById(R.id.gd_gtwear_activation_progressBar)).setIndeterminate(true);
        ((TextView) inflate.findViewById(R.id.gd_gtwear_activation_title_text)).setText(GDLocalizer.getLocalizedString("WF Connecting watch"));
        ((TextView) inflate.findViewById(R.id.gd_gtwear_activation_in_progress_body)).setText(GDLocalizer.getLocalizedString("WF Connecting Watch body"));
        ((TextView) inflate.findViewById(R.id.gdwear_setup_requested_appname)).setText(GDApplicationUtils.getApplicationName(getActivity()));
        ((TextView) inflate.findViewById(R.id.gdwear_setup_requested)).setText(GDLocalizer.getLocalizedString("WF Setup Requested "));
        ((ImageView) inflate.findViewById(R.id.gdwear_setup_icon)).setBackgroundResource(R.drawable.gd_activation_start_background);
        applyUICustomization(inflate);
        Button button = (Button) inflate.findViewById(R.id.gd_gtwear_activation_button1);
        button.setText(GDLocalizer.getLocalizedString("WF Button Later").toUpperCase(Locale.getDefault()));
        button.setVisibility(0);
        button.setOnClickListener(this);
        return inflate;
    }
}
