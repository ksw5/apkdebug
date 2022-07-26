package com.good.gd.ui.subcontainer_activationui;

import android.app.Fragment;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.good.gd.client.GDClient;
import com.good.gd.resources.R;
import com.good.gd.utils.GDLocalizer;
import com.good.gt.ndkproxy.util.GTLog;

/* loaded from: classes.dex */
public class GTWearActivationInProgressPostValidationUIFragment extends Fragment {
    private void applyUICustomization(View view) {
        ProgressBar progressBar;
        GDClient gDClient = GDClient.getInstance();
        if (!gDClient.isUICustomized() || (progressBar = (ProgressBar) view.findViewById(R.id.gd_gtwear_activation_progressBar)) == null || progressBar.getIndeterminateDrawable() == null) {
            return;
        }
        ((LayerDrawable) progressBar.getIndeterminateDrawable()).getDrawable(1).setColorFilter(gDClient.getCustomUIColor().intValue(), PorterDuff.Mode.SRC_IN);
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        GTLog.DBGPRINTF(16, "GTWearActivationInProgressPostValidationUIFragment: onCreateView\n");
        View inflate = layoutInflater.inflate(R.layout.gd_gtwear_activation_inprogress_post_validation, viewGroup, false);
        ((ProgressBar) inflate.findViewById(R.id.gd_gtwear_activation_progressBar)).setIndeterminate(true);
        applyUICustomization(inflate);
        ((TextView) inflate.findViewById(R.id.gd_gtwear_activation_title_text)).setText(GDLocalizer.getLocalizedString("WF Waiting Watch"));
        ((TextView) inflate.findViewById(R.id.gd_gtwear_activation_in_progress_body)).setText(GDLocalizer.getLocalizedString("WF Waiting Watch Body2"));
        return inflate;
    }
}
