package com.good.gd.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.good.gd.enterprise.NocServerListProvider;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.enterprise.GDENocServer;
import com.good.gd.ndkproxy.ui.BBDUIHelper;
import com.good.gd.ndkproxy.ui.BBDUILocalizationHelper;
import com.good.gd.ndkproxy.ui.data.base.BBDUIObject;
import com.good.gd.ndkproxy.ui.event.BBDUIEventManager;
import com.good.gd.ndkproxy.ui.event.BBDUIMessageType;
import com.good.gd.resources.R;
import com.good.gd.ui.base_ui.GDView;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class GDENocSelectionView extends GDView {
    private static final String DEFAULT_NOC = "prod1";
    private Context _context;
    private Button accessButton;
    private BBDUIObject bbdUI;
    private final NocServerListProvider nocServerListProvider;
    private GDENocServer selectedNoc = null;
    private GDENocServer currentNoc = null;

    /* loaded from: classes.dex */
    private class ehnkx extends GDViewDelegateAdapter {
        private ehnkx() {
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActivityResume() {
            super.onActivityResume();
            GDENocSelectionView.this.displayDelegates();
        }

        /* synthetic */ ehnkx(GDENocSelectionView gDENocSelectionView, hbfhc hbfhcVar) {
            this();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class fdyxd implements AdapterView.OnItemClickListener {
        final /* synthetic */ pmoiy dbjc;
        final /* synthetic */ ListView qkduk;

        fdyxd(pmoiy pmoiyVar, ListView listView) {
            this.dbjc = pmoiyVar;
            this.qkduk = listView;
        }

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            this.dbjc.dbjc(i);
            GDENocSelectionView.this.selectedNoc = (GDENocServer) this.qkduk.getItemAtPosition(i);
            if (GDENocSelectionView.this.selectedNoc == GDENocSelectionView.this.currentNoc) {
                GDENocSelectionView.this.accessButton.setEnabled(false);
            } else {
                GDENocSelectionView.this.accessButton.setEnabled(true);
            }
        }
    }

    /* loaded from: classes.dex */
    class hbfhc implements OnClickListener {
        final /* synthetic */ BBDUIObject dbjc;

        hbfhc(BBDUIObject bBDUIObject) {
            this.dbjc = bBDUIObject;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (GDENocSelectionView.this.selectedNoc != null) {
                BBDUIEventManager.sendMessage(this.dbjc, BBDUIMessageType.MSG_CLIENT_SET_NOC_SELECTED, GDENocSelectionView.this.selectedNoc.getTitle());
                Toast.makeText(GDENocSelectionView.this._context, "Noc is reset to " + GDENocSelectionView.this.selectedNoc.getTitle(), 0).show();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class pmoiy extends BaseAdapter {
        private ArrayList<GDENocServer> dbjc;
        private int jwxax = -1;
        private LayoutInflater qkduk;

        /* loaded from: classes.dex */
        class hbfhc {
            TextView dbjc;
            RelativeLayout jwxax;
            TextView qkduk;

            hbfhc(pmoiy pmoiyVar) {
            }
        }

        pmoiy(Context context, ArrayList<GDENocServer> arrayList) {
            this.dbjc = arrayList;
            this.qkduk = LayoutInflater.from(context);
        }

        void dbjc(int i) {
            this.jwxax = i;
            notifyDataSetChanged();
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return this.dbjc.size();
        }

        @Override // android.widget.Adapter
        public Object getItem(int i) {
            ArrayList<GDENocServer> arrayList = this.dbjc;
            if (arrayList == null || arrayList.size() <= 0) {
                return null;
            }
            return this.dbjc.get(i);
        }

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return i;
        }

        @Override // android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            hbfhc hbfhcVar;
            if (view == null) {
                view = this.qkduk.inflate(R.layout.gde_noc_selection_list_item, viewGroup, false);
                hbfhcVar = new hbfhc(this);
                hbfhcVar.dbjc = (TextView) view.findViewById(R.id.title);
                hbfhcVar.qkduk = (TextView) view.findViewById(R.id.details);
                hbfhcVar.jwxax = (RelativeLayout) view.findViewById(R.id.layout);
                view.setTag(hbfhcVar);
            } else {
                hbfhcVar = (hbfhc) view.getTag();
            }
            int i2 = this.jwxax;
            if (i2 != -1 && i == i2) {
                hbfhcVar.jwxax.setBackgroundColor(GDENocSelectionView.this.getResources().getColor(R.color.bbd_noc_selection_bg_color));
            } else {
                hbfhcVar.jwxax.setBackgroundColor(GDENocSelectionView.this.getResources().getColor(R.color.bbd_common_background_color));
            }
            hbfhcVar.dbjc.setText(this.dbjc.get(i).getTitle());
            hbfhcVar.qkduk.setText(this.dbjc.get(i).getDetails());
            return view;
        }
    }

    /* loaded from: classes.dex */
    class yfdke implements OnClickListener {
        yfdke() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            GDENocSelectionView.this.moveBackToProvisionUI();
        }
    }

    public GDENocSelectionView(Context context, ViewInteractor viewInteractor, BBDUIObject bBDUIObject, ViewCustomizer viewCustomizer, NocServerListProvider nocServerListProvider) {
        super(context, viewInteractor, viewCustomizer);
        this._context = null;
        this.nocServerListProvider = nocServerListProvider;
        this.bbdUI = bBDUIObject;
        this._context = context;
        this._delegate = new ehnkx(this, null);
        inflateLayout(R.layout.gde_noc_selection_view, this);
        Button button = (Button) findViewById(R.id.COM_GOOD_GD_GDE_NOC_SELECTION_VIEW_OK_BUTTON);
        this.accessButton = button;
        checkFieldNotNull(button, "gde_noc_selection_view", "COM_GOOD_GD_GDE_NOC_SELECTION_VIEW_OK_BUTTON");
        this.accessButton.setEnabled(false);
        this.accessButton.setText(BBDUILocalizationHelper.getLocalizedOK());
        this.accessButton.setOnClickListener(new hbfhc(bBDUIObject));
        Button button2 = (Button) findViewById(R.id.COM_GOOD_GD_GDE_NOC_SELECTION_VIEW_CANCEL_BUTTON);
        checkFieldNotNull(button2, "gde_noc_selection_view", "COM_GOOD_GD_GDE_NOC_SELECTION_VIEW_CANCEL_BUTTON");
        button2.setEnabled(true);
        button2.setText(BBDUILocalizationHelper.getLocalizedCancel());
        button2.setOnClickListener(new yfdke());
        enableBottomLine();
        setBottomLabelVisibility(0);
        applyUICustomization();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void displayDelegates() {
        GDENocServer[] listOfNocs = this.nocServerListProvider.getListOfNocs();
        ArrayList arrayList = new ArrayList();
        if (listOfNocs != null) {
            GDLog.DBGPRINTF(16, "GDEProvisionNocSelectionView getListOfNocs length = " + listOfNocs.length);
            String selectedNocServer = this.nocServerListProvider.getSelectedNocServer();
            if (selectedNocServer == null || selectedNocServer.trim().isEmpty()) {
                selectedNocServer = DEFAULT_NOC;
            }
            int i = 0;
            for (int i2 = 0; i2 < listOfNocs.length; i2++) {
                GDENocServer gDENocServer = listOfNocs[i2];
                if (gDENocServer.getTitle().equalsIgnoreCase(selectedNocServer)) {
                    i = i2;
                }
                arrayList.add(gDENocServer);
            }
            ListView listView = (ListView) findViewById(R.id.List);
            pmoiy pmoiyVar = new pmoiy(this._context, arrayList);
            listView.setAdapter((ListAdapter) pmoiyVar);
            listView.setClickable(true);
            listView.setSelection(i);
            pmoiyVar.dbjc(i);
            this.currentNoc = (GDENocServer) listView.getItemAtPosition(i);
            listView.setOnItemClickListener(new fdyxd(pmoiyVar, listView));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void moveBackToProvisionUI() {
        BBDUIHelper.cancel(this.bbdUI.getCoreHandle());
    }

    @Override // com.good.gd.ui.base_ui.GDView
    public void onBackPressed() {
        moveBackToProvisionUI();
    }
}
