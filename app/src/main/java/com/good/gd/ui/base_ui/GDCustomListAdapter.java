package com.good.gd.ui.base_ui;

import android.content.Context;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.good.gd.client.GDCustomizedUI;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.enterprise.GDEActivationManager;
import com.good.gd.resources.R;
import com.good.gd.utils.GDLocalizer;
import java.util.List;
import java.util.Locale;

/* loaded from: classes.dex */
public class GDCustomListAdapter extends BaseAdapter {
    private static final String receivingActivity = ".iccreceivingactivity";
    private List<GDEActivationManager.Application> _actInfoArrayist;
    private Context _context;
    private final GDCustomizedUI _customizedUI;
    private LayoutInflater _layoutInflator;

    /* loaded from: classes.dex */
    class hbfhc {
        TextView dbjc;
        ImageView jwxax;
        TextView qkduk;

        hbfhc(GDCustomListAdapter gDCustomListAdapter) {
        }
    }

    public GDCustomListAdapter(List<GDEActivationManager.Application> list, Context context, GDCustomizedUI gDCustomizedUI) {
        this._actInfoArrayist = list;
        this._context = context;
        this._customizedUI = gDCustomizedUI;
    }

    private void applyUICustomization(TextView textView, TextView textView2) {
        if (this._customizedUI.isUICustomized()) {
            textView.setTextColor(this._customizedUI.getCustomUIColor().intValue());
            textView2.setTextColor(this._customizedUI.getCustomUIColor().intValue());
        }
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this._actInfoArrayist.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return this._actInfoArrayist.get(i);
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view2;
        hbfhc hbfhcVar;
        if (view == null) {
            this._layoutInflator = (LayoutInflater) this._context.getSystemService("layout_inflater");
            hbfhcVar = new hbfhc(this);
            view2 = this._layoutInflator.inflate(R.layout.bbd_activation_delegate_list, viewGroup, false);
            hbfhcVar.dbjc = (TextView) view2.findViewById(R.id.app_title);
            hbfhcVar.qkduk = (TextView) view2.findViewById(R.id.app_name);
            hbfhcVar.jwxax = (ImageView) view2.findViewById(R.id.appLogo);
            view2.setTag(hbfhcVar);
        } else {
            view2 = view;
            hbfhcVar = (hbfhc) view.getTag();
        }
        hbfhcVar.dbjc.setText(GDLocalizer.getLocalizedString("Set up using"));
        hbfhcVar.qkduk.setText(this._actInfoArrayist.get(i).getName());
        PackageManager packageManager = this._context.getPackageManager();
        try {
            String str = (String) this._actInfoArrayist.get(i).getAddress()[0];
            int lastIndexOf = str.lastIndexOf(".");
            if (lastIndexOf > 0) {
                if (receivingActivity.equals(str.substring(lastIndexOf, str.length()).toLowerCase(Locale.ENGLISH))) {
                    str = str.substring(0, lastIndexOf);
                }
            }
            hbfhcVar.jwxax.setImageDrawable(packageManager.getApplicationIcon(str));
        } catch (PackageManager.NameNotFoundException e) {
            GDLog.DBGPRINTF(12, "GDCustomListAdapter.getView exception", e);
        }
        applyUICustomization(hbfhcVar.dbjc, hbfhcVar.qkduk);
        return view2;
    }
}
