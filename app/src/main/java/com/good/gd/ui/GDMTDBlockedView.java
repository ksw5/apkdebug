package com.good.gd.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.good.gd.client.GDCustomizedUI;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.ui.data.MTDBlockedUI;
import com.good.gd.ndkproxy.ui.event.BBDUIEventManager;
import com.good.gd.ndkproxy.ui.event.BBDUIMessageType;
import com.good.gd.resources.R;
import com.good.gd.ui.base_ui.GDView;
import com.good.gd.utils.GDLocalizer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class GDMTDBlockedView extends GDView {
    private static final String MALWARE_HEADING_LOC_KEY = "MTD Malware heading";
    private static final String MALWARE_HEADING_SUB_TEXT_LOC_KEY = "MTD Malicious malware subtext";
    private static final String MALWARE_SIDELOAD_SUB_TEXT_LOC_KEY = "MTD Malicious sideloaded subtext";
    private static final String UINSTALL_MULTIPLE_APP_LOC_KEY = "MTD APK Scanning: Block UI: Uninstall Message with multiple items";
    private static final String UINSTALL_SINGLE_APP_LOC_KEY = "MTD APK Scanning: Block UI: Uninstall Message with one item";
    private static final String UNTRUSTED_SOURCE_HEADING_LOC_KEY = "MTD Untrusted source heading";
    private Context _context;
    private final GDCustomizedUI customizedUI;
    private MTDBlockedUI uiData;
    private final AdapterView.OnItemClickListener onItemClickListener = new hbfhc();
    List<String> malwareAppList = new ArrayList();
    List<String> sideloadedAppList = new ArrayList();
    private TextView m_textView = (TextView) findViewById(R.id.COM_GOOD_GD_BLOCK_VIEW_MESSAGE_VIEW);

    /* loaded from: classes.dex */
    public static class GDAppListAdapter extends BaseAdapter {
        private List<String> _appsPackagesList;
        private Context _context;
        private GDCustomizedUI _customizedUI;
        private LayoutInflater _layoutInflator;

        /* loaded from: classes.dex */
        class hbfhc {
            TextView dbjc;
            ImageView jwxax;
            TextView qkduk;

            hbfhc(GDAppListAdapter gDAppListAdapter) {
            }
        }

        public GDAppListAdapter(List<String> list, Context context, GDCustomizedUI gDCustomizedUI) {
            this._appsPackagesList = list;
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
            List<String> list = this._appsPackagesList;
            if (list != null) {
                return list.size();
            }
            return 0;
        }

        @Override // android.widget.Adapter
        public Object getItem(int i) {
            return this._appsPackagesList.get(i);
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
                view2 = this._layoutInflator.inflate(R.layout.bbd_application_list, viewGroup, false);
                hbfhcVar.dbjc = (TextView) view2.findViewById(R.id.app_title);
                hbfhcVar.qkduk = (TextView) view2.findViewById(R.id.app_name);
                hbfhcVar.jwxax = (ImageView) view2.findViewById(R.id.appLogo);
                view2.setTag(hbfhcVar);
            } else {
                view2 = view;
                hbfhcVar = (hbfhc) view.getTag();
            }
            hbfhcVar.qkduk.setText(this._appsPackagesList.get(i));
            PackageManager packageManager = this._context.getPackageManager();
            try {
                String str = this._appsPackagesList.get(i);
                String charSequence = packageManager.getApplicationLabel(packageManager.getApplicationInfo(str, 0)).toString();
                Drawable applicationIcon = packageManager.getApplicationIcon(str);
                hbfhcVar.dbjc.setText(charSequence);
                hbfhcVar.jwxax.setImageDrawable(applicationIcon);
            } catch (PackageManager.NameNotFoundException e) {
                GDLog.DBGPRINTF(12, "GDAppListAdapter.getView exception", e);
            }
            applyUICustomization(hbfhcVar.dbjc, hbfhcVar.qkduk);
            return view2;
        }
    }

    /* loaded from: classes.dex */
    private class fdyxd extends GDViewDelegateAdapter {
        private fdyxd() {
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActivityResume() {
            GDMTDBlockedView.this.displayApps();
        }

        /* synthetic */ fdyxd(GDMTDBlockedView gDMTDBlockedView, hbfhc hbfhcVar) {
            this();
        }
    }

    /* loaded from: classes.dex */
    class hbfhc implements AdapterView.OnItemClickListener {
        hbfhc() {
        }

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            try {
                Intent intent = new Intent();
                intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                intent.setData(Uri.fromParts("package", (String) adapterView.getItemAtPosition(i), null));
                GDMTDBlockedView.this._context.startActivity(intent);
            } catch (Exception e) {
                GDLog.DBGPRINTF(16, "GDMTDBlockedView.onItemClick: " + e + "\n");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class yfdke implements OnClickListener {
        final /* synthetic */ MTDBlockedUI dbjc;

        yfdke(MTDBlockedUI mTDBlockedUI) {
            this.dbjc = mTDBlockedUI;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            String str;
            String str2;
            PackageManager packageManager;
            char c;
            PackageInfo packageInfo;
            List<String> packageInfoItems = this.dbjc.getPackageInfoItems();
            char c2 = '\f';
            GDLog.DBGPRINTF(12, "User Report MTD Problem Info\n");
            PackageManager packageManager2 = GDMTDBlockedView.this._context.getPackageManager();
            String str3 = "";
            int i = 0;
            String str4 = str3;
            boolean z = false;
            for (String str5 : packageInfoItems) {
                int indexOf = str5.indexOf("\t");
                if (str5.equals("<SL>")) {
                    z = true;
                } else if (indexOf == -1) {
                    packageManager2 = packageManager2;
                    i = 0;
                } else {
                    String substring = str5.substring(i, indexOf);
                    if (!z) {
                        str = str5.substring(indexOf + 1, str5.length());
                        str2 = str3;
                    } else {
                        String substring2 = str5.substring(indexOf + 1, str5.length());
                        str = str4;
                        str2 = substring2;
                    }
                    try {
                        String charSequence = packageManager2.getApplicationLabel(packageManager2.getPackageInfo(substring, i).applicationInfo).toString();
                        String installerPackageName = packageManager2.getInstallerPackageName(substring);
                        if (!z) {
                            packageManager = packageManager2;
                            try {
                                GDLog.DBGPRINTF(12, "Malware info: PName = " + substring + " , AppName = " + charSequence + " , AppVersion = " + packageInfo.versionName + " , VersionCode= " + packageInfo.versionCode + " , Installer = " + installerPackageName + ", Hash = " + str + "\n");
                                c = '\f';
                            } catch (PackageManager.NameNotFoundException e) {
                                e = e;
                                c = '\f';
                                GDLog.DBGPRINTF(12, "User Report MTD Problem exception", e);
                                c2 = c;
                                str3 = str2;
                                str4 = str;
                                packageManager2 = packageManager;
                                i = 0;
                            }
                        } else {
                            packageManager = packageManager2;
                            GDLog.DBGPRINTF(12, "Sideloaded info: PName = " + substring + " , AppName = " + charSequence + " , AppVersion = " + packageInfo.versionName + " , VersionCode= " + packageInfo.versionCode + " , Installer = " + str2 + "\n");
                            c = '\f';
                        }
                    } catch (PackageManager.NameNotFoundException e2) {
                        e = e2;
                        packageManager = packageManager2;
                    }
                    c2 = c;
                    str3 = str2;
                    str4 = str;
                    packageManager2 = packageManager;
                    i = 0;
                }
            }
            BBDUIEventManager.sendMessage(this.dbjc, BBDUIMessageType.MSG_UI_BOTTOM_BUTTON);
        }
    }

    public GDMTDBlockedView(Context context, ViewInteractor viewInteractor, MTDBlockedUI mTDBlockedUI, ViewCustomizer viewCustomizer) {
        super(context, viewInteractor, viewCustomizer);
        this.customizedUI = viewCustomizer.getGDCustomizedUI();
        inflateLayout(R.layout.bbd_mtd_blocked_view, this);
        this._delegate = new fdyxd(this, null);
        this._context = context;
        this.uiData = mTDBlockedUI;
        ((TextView) findViewById(R.id.COM_GOOD_GD_BLOCK_VIEW_TITLE_VIEW)).setText(this.uiData.getLocalizedTitle());
        ((TextView) findViewById(R.id.COM_GOOD_GD_BLOCK_VIEW_MESSAGE_VIEW)).setText(this.uiData.getLocalizedMessage());
        ((TextView) findViewById(R.id.COM_GOOD_GD_BLOCK_VIEW_MALWARE_MESSAGE_VIEW)).setText(GDLocalizer.getLocalizedString(MALWARE_HEADING_LOC_KEY));
        ((TextView) findViewById(R.id.COM_GOOD_GD_BLOCK_VIEW_MALWARE_SUB_TEXT_VIEW)).setText(GDLocalizer.getLocalizedString(MALWARE_HEADING_SUB_TEXT_LOC_KEY));
        ((TextView) findViewById(R.id.COM_GOOD_GD_BLOCK_VIEW_SIDELOAD_MESSAGE_VIEW)).setText(GDLocalizer.getLocalizedString(UNTRUSTED_SOURCE_HEADING_LOC_KEY));
        ((TextView) findViewById(R.id.COM_GOOD_GD_BLOCK_VIEW_SIDELOAD_SUB_TEXT_VIEW)).setText(GDLocalizer.getLocalizedString(MALWARE_SIDELOAD_SUB_TEXT_LOC_KEY));
        setupUploadLogs(this.uiData);
        applyUICustomization();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void displayApps() {
        String str;
        updatedData();
        String localizedMessage = this.uiData.getLocalizedMessage();
        if (this.malwareAppList.size() + this.sideloadedAppList.size() > 1) {
            str = localizedMessage + " " + GDLocalizer.getLocalizedString(UINSTALL_MULTIPLE_APP_LOC_KEY);
        } else {
            str = localizedMessage + " " + GDLocalizer.getLocalizedString(UINSTALL_SINGLE_APP_LOC_KEY);
        }
        this.m_textView.setText(str);
        if (this.sideloadedAppList.size() == 0) {
            findViewById(R.id.bbd_mtd_sideload_layout).setVisibility(8);
        }
        if (this.malwareAppList.size() == 0) {
            findViewById(R.id.bbd_mtd_malware_layout).setVisibility(8);
        }
        if (this.malwareAppList.size() > 0) {
            GDAppListAdapter gDAppListAdapter = new GDAppListAdapter(this.malwareAppList, this._context, this.customizedUI);
            ListView listView = (ListView) findViewById(R.id.MalwareList);
            listView.setOnItemClickListener(this.onItemClickListener);
            listView.setAdapter((ListAdapter) gDAppListAdapter);
        }
        if (this.sideloadedAppList.size() > 0) {
            GDAppListAdapter gDAppListAdapter2 = new GDAppListAdapter(this.sideloadedAppList, this._context, this.customizedUI);
            ListView listView2 = (ListView) findViewById(R.id.SideLoadList);
            listView2.setOnItemClickListener(this.onItemClickListener);
            listView2.setAdapter((ListAdapter) gDAppListAdapter2);
        }
    }

    private OnClickListener getClickListener(MTDBlockedUI mTDBlockedUI) {
        return new yfdke(mTDBlockedUI);
    }

    private void setupUploadLogs(MTDBlockedUI mTDBlockedUI) {
        if (mTDBlockedUI.hasBottomButton()) {
            TextView textView = (TextView) findViewById(R.id.gd_bottom_line_action_label);
            textView.setVisibility(0);
            textView.setText(mTDBlockedUI.getLocalizedBottomButton());
            textView.setOnClickListener(getClickListener(mTDBlockedUI));
        }
    }

    private void updatedData() {
        this.malwareAppList.clear();
        this.sideloadedAppList.clear();
        List<String> packageInfoItems = this.uiData.getPackageInfoItems();
        if (packageInfoItems.size() > 0) {
            PackageManager packageManager = this._context.getPackageManager();
            boolean z = false;
            for (String str : packageInfoItems) {
                if (str.equals("<SL>")) {
                    z = true;
                } else {
                    int indexOf = str.indexOf("\t");
                    if (indexOf != -1) {
                        String substring = str.substring(0, indexOf);
                        try {
                            packageManager.getApplicationInfo(substring, 0);
                            if (!z) {
                                this.malwareAppList.add(substring);
                            } else {
                                this.sideloadedAppList.add(substring);
                            }
                        } catch (PackageManager.NameNotFoundException e) {
                            GDLog.DBGPRINTF(12, "GDAppListAdapter.getView exception", e);
                        }
                    }
                }
            }
        }
    }
}
