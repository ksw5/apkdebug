package com.good.gd.ui;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.good.gd.client.GDCustomizedUI;
import com.good.gd.messages.ActivationMsg;
import com.good.gd.messages.CloseEasyActSelectionMsg;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.enterprise.GDEActivationManager;
import com.good.gd.ndkproxy.ui.BBDUILocalizationHelper;
import com.good.gd.ndkproxy.ui.data.EasyActivationSelectionUI;
import com.good.gd.ndkproxy.ui.event.BBDUIEventManager;
import com.good.gd.ndkproxy.ui.event.BBDUIMessageType;
import com.good.gd.ndkproxy.ui.event.BBDUIUpdateEvent;
import com.good.gd.resources.R;
import com.good.gd.ui.base_ui.GDCustomListAdapter;
import com.good.gd.ui.base_ui.GDView;
import com.good.gd.utils.GDLocalizer;
import java.util.List;

/* loaded from: classes.dex */
public class GDActivationDelegateView extends GDView {
    private static final String HEADER_TAG = "header_tag";
    private Context _context;
    private final GDCustomizedUI customizedUI;
    private List<List<GDEActivationManager.Application>> delegates;
    private TextView textViewKeyLink;
    private final EasyActivationSelectionUI uiData;
    private final AdapterView.OnItemClickListener onItemClickListener = new hbfhc();
    private boolean isResumed = false;

    /* loaded from: classes.dex */
    class fdyxd extends GDViewDelegateAdapter {
        fdyxd() {
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActivityPause() {
            GDActivationDelegateView.this.isResumed = false;
            super.onActivityPause();
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActivityResume() {
            super.onActivityResume();
            GDActivationDelegateView.this.isResumed = true;
            if (GDActivationDelegateView.this.uiData != null) {
                GDActivationDelegateView gDActivationDelegateView = GDActivationDelegateView.this;
                gDActivationDelegateView.delegates = gDActivationDelegateView.uiData.getDelegates();
            }
            GDActivationDelegateView.this.displayDelegates();
        }
    }

    /* loaded from: classes.dex */
    class hbfhc implements AdapterView.OnItemClickListener {
        hbfhc() {
        }

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            if (!(view.getTag() instanceof String) || !((String) view.getTag()).equals(GDActivationDelegateView.HEADER_TAG)) {
                try {
                    GDEActivationManager.getInstance().setActivationTypeManual(false);
                    GDActivationDelegateView.this.uiData.setNonce(GDEActivationManager.getInstance().generateNonce());
                    BBDUIEventManager.sendMessage(GDActivationDelegateView.this.uiData, BBDUIMessageType.MSG_UI_ACTIVATION_REQUEST, new ActivationMsg((String) ((GDEActivationManager.Application) adapterView.getItemAtPosition(i)).getAddress()[0], GDActivationDelegateView.this.uiData.getNonce()));
                } catch (Exception e) {
                    GDLog.DBGPRINTF(16, "GDActivationDelegateView.onItemClick: " + e + "\n");
                }
            }
        }
    }

    /* loaded from: classes.dex */
    class yfdke implements OnClickListener {
        yfdke() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            GDEActivationManager.getInstance().setActivationTypeManual(true);
            BBDUIEventManager.sendMessage(GDActivationDelegateView.this.uiData, BBDUIMessageType.MSG_UI_SETUP_MANUALLY, new CloseEasyActSelectionMsg(false));
        }
    }

    public GDActivationDelegateView(Context context, ViewInteractor viewInteractor, EasyActivationSelectionUI easyActivationSelectionUI, ViewCustomizer viewCustomizer) {
        super(context, viewInteractor, viewCustomizer);
        this.uiData = easyActivationSelectionUI;
        this.customizedUI = viewCustomizer.getGDCustomizedUI();
        this._context = context;
        this._delegate = new fdyxd();
        this.delegates = easyActivationSelectionUI.getDelegates();
        inflateLayout(R.layout.bbd_activation_delegate_view, this);
        if (isLandscape() || isInMultiWindowMode()) {
            addFooterView();
        }
        TextView textView = (TextView) findViewById(R.id.activationKeyLink);
        this.textViewKeyLink = textView;
        textView.setText(GDLocalizer.getLocalizedString("Set up using your Access Key"));
        this.textViewKeyLink.setOnClickListener(new yfdke());
        enableBottomLine();
        enableHelpButton(easyActivationSelectionUI);
        addHeaderView();
        applyUICustomization();
        adjustHeaderPositioning();
    }

    private void addFooterView() {
        ListView listView = (ListView) findViewById(R.id.List);
        LinearLayout linearLayout = (LinearLayout) ((LayoutInflater) this._context.getSystemService("layout_inflater")).inflate(R.layout.bbd_activation_delegate_view_footer, (ViewGroup) listView, false);
        linearLayout.setBackground(getResources().getDrawable(R.drawable.bbd_text_view_background_selector));
        listView.addFooterView(linearLayout, null, true);
        listView.setFooterDividersEnabled(false);
        listView.setItemsCanFocus(true);
    }

    private void addHeaderView() {
        ListView listView = (ListView) findViewById(R.id.List);
        TextView textView = (TextView) ((LayoutInflater) this._context.getSystemService("layout_inflater")).inflate(R.layout.bbd_activation_delegate_view_header, (ViewGroup) listView, false);
        textView.setTag(HEADER_TAG);
        listView.addHeaderView(textView);
        listView.setHeaderDividersEnabled(false);
        try {
            PackageManager packageManager = this._context.getPackageManager();
            textView.setText(String.format(GDLocalizer.getLocalizedString("EA app selection screen help."), (String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(this._context.getPackageName(), 0))));
        } catch (PackageManager.NameNotFoundException e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void displayDelegates() {
        List<List<GDEActivationManager.Application>> list = this.delegates;
        if (list.size() == 0) {
            BBDUIEventManager.sendMessage(this.uiData, BBDUIMessageType.MSG_UI_SETUP_MANUALLY, new CloseEasyActSelectionMsg(true));
            return;
        }
        GDListAdapterMap gDListAdapterMap = new GDListAdapterMap(this._context);
        for (int i = 0; i < list.size(); i++) {
            GDCustomListAdapter gDCustomListAdapter = new GDCustomListAdapter(list.get(i), this._context, this.customizedUI);
            String enterpriseName = list.get(i).get(0).getEnterpriseName();
            gDListAdapterMap.addSection(TextUtils.isEmpty(enterpriseName) ? "" : String.format(GDLocalizer.getLocalizedString("Application for %s:"), enterpriseName), gDCustomListAdapter);
        }
        ListView listView = (ListView) findViewById(R.id.List);
        listView.setOnItemClickListener(this.onItemClickListener);
        listView.setAdapter((ListAdapter) gDListAdapterMap);
    }

    private boolean isLandscape() {
        Resources resources = getResources();
        return (resources == null || resources.getConfiguration() == null || resources.getConfiguration().orientation != 2) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.good.gd.ui.base_ui.GDView
    public void applyUICustomization() {
        super.applyUICustomization();
        if (this.customizedUI.isUICustomized()) {
            this.textViewKeyLink.setTextColor(this.customizedUI.getCustomUIColor().intValue());
        }
    }

    @Override // com.good.gd.ui.base_ui.GDView
    public void onBackPressed() {
        moveTaskToBack();
    }

    @Override // com.good.gd.ui.base_ui.GDView
    public void stateWasUpdated() {
        BBDUIUpdateEvent updateData = this.uiData.getUpdateData();
        if (updateData == null) {
            if (!this.isResumed) {
                return;
            }
            this.delegates = this.uiData.getDelegates();
            displayDelegates();
            return;
        }
        GDLog.DBGPRINTF(16, "GDActivationDelegateView.onActivationDelegationResponse() call failed.\n");
        showPopupDialog(updateData.getTitle(), updateData.getText(), BBDUILocalizationHelper.getLocalizedOK(), null);
    }
}
