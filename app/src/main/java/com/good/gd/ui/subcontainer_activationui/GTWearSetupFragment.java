package com.good.gd.ui.subcontainer_activationui;

import android.app.ListFragment;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.good.gd.client.GDClient;
import com.good.gd.resources.R;
import com.good.gd.ui.subcontainer_activationui.GTWearActivationUIControl;
import com.good.gd.utils.GDLocalizer;
import com.good.gt.ndkproxy.util.GTLog;
import java.util.ArrayList;
import java.util.Locale;

/* loaded from: classes.dex */
public class GTWearSetupFragment extends ListFragment implements View.OnClickListener {

    /* loaded from: classes.dex */
    class hbfhc<String> extends ArrayAdapter<String> {
        private String[] dbjc;

        public hbfhc(Context context, int i, String[] stringArr) {
            super(context, i, stringArr);
            this.dbjc = stringArr;
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            GradientDrawable gradientDrawable;
            if (view == null) {
                view = GTWearSetupFragment.this.getActivity().getLayoutInflater().inflate(R.layout.gd_gtwear_setup_stepitem, viewGroup, false);
            }
            ((TextView) view.findViewById(R.id.gdwear_setup_step_text)).setText(this.dbjc[i].toString());
            ImageView imageView = (ImageView) view.findViewById(R.id.gdwear_setup_icon);
            imageView.setBackgroundResource(R.drawable.gd_permission_number_background);
            ((TextView) view.findViewById(R.id.gdwear_setup_icon_permission_number)).setText(Integer.toString(i + 1));
            GDClient gDClient = GDClient.getInstance();
            if (gDClient.isUICustomized() && (gradientDrawable = (GradientDrawable) imageView.getBackground()) != null) {
                gradientDrawable.setColor(gDClient.getCustomUIColor().intValue());
            }
            return view;
        }
    }

    private void applyUICustomization(View view) {
        ProgressBar progressBar;
        GDClient gDClient = GDClient.getInstance();
        if (gDClient.isUICustomized() && (progressBar = (ProgressBar) view.findViewById(R.id.gd_gtwear_activation_progressBar)) != null) {
            progressBar.setProgressDrawable(new ColorDrawable(gDClient.getCustomUIColor().intValue()));
        }
    }

    private int convertDpToPixel(float f) {
        return (int) (f * (getActivity().getResources().getDisplayMetrics().densityDpi / 160.0f));
    }

    private void startAuthProcess() {
        GTWearActivationUIControl.getInstance().promptStartActivation(GTWearActivationUIControl.getInstance().getRemoteAddress());
        GTWearActivationUIControl.getInstance().setActivationUIState(GTWearActivationUIControl.ActivationUIState.StateActivationInProgressUI);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.gd_gtwear_activation_button1) {
            getActivity().finish();
        } else if (view.getId() != R.id.gd_gtwear_activation_button2) {
        } else {
            startAuthProcess();
        }
    }

    @Override // android.app.ListFragment, android.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        GTLog.DBGPRINTF(16, "GDWearSetupFragment: onCreateView\n");
        View inflate = layoutInflater.inflate(R.layout.gd_gtwear_wearable_setup, viewGroup, false);
        applyUICustomization(inflate);
        TextView textView = (TextView) inflate.findViewById(R.id.gd_gtwear_activation_title_text);
        textView.setText(GDLocalizer.getLocalizedString("WF Start setup"));
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) textView.getLayoutParams();
        layoutParams.setMargins(convertDpToPixel(72.0f), 0, convertDpToPixel(16.0f), convertDpToPixel(8.0f));
        textView.setLayoutParams(layoutParams);
        Button button = (Button) inflate.findViewById(R.id.gd_gtwear_activation_button1);
        button.setText(GDLocalizer.getLocalizedString("WF Button Later").toUpperCase(Locale.getDefault()));
        button.setOnClickListener(this);
        button.setVisibility(0);
        Button button2 = (Button) inflate.findViewById(R.id.gd_gtwear_activation_button2);
        button2.setText(GDLocalizer.getLocalizedString("WF Button Setup").toUpperCase(Locale.getDefault()) + " >");
        button2.setOnClickListener(this);
        button2.setVisibility(0);
        button2.requestFocus();
        ((TextView) inflate.findViewById(R.id.gd_gtwear_activation_setup_body)).setText(GDLocalizer.getLocalizedString("WF Start body"));
        ArrayList arrayList = new ArrayList(4);
        arrayList.add(GDLocalizer.getLocalizedString("WF Start item1"));
        arrayList.add(GDLocalizer.getLocalizedString("WF Start item2"));
        arrayList.add(GDLocalizer.getLocalizedString("WF Start item3a"));
        arrayList.add(GDLocalizer.getLocalizedString("WF Start item4"));
        setListAdapter(new hbfhc(layoutInflater.getContext(), R.layout.gd_gtwear_setup_stepitem, (String[]) arrayList.toArray(new String[arrayList.size()])));
        return inflate;
    }
}
