package com.good.gd.ui.dialogs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.good.gd.Activity;
import com.good.gd.GDDialog;
import com.good.gd.GDLocalBroadcastManager;
import com.good.gd.database.sqlite.SQLiteDatabase;
import com.good.gd.resources.R;
import com.good.gd.utils.GDLocalizer;
import java.util.List;

/* loaded from: classes.dex */
public class GDDialogActivity extends Activity implements AdapterView.OnItemClickListener {
    private static final int INFO_DIALOG_MODE = 2;
    private static final String INFO_KEY = "infoKey";
    public static final String LAYOUT_ID_KEY = "layoutIdKey";
    private static final int LIST_DIALOG_MODE = 1;
    public static final String LIST_ITEM_CANCELLED_BOOL_KEY = "listItemCancelldKey";
    public static final String LIST_ITEM_INDEX_INT_KEY = "listItemIndexKey";
    public static final String LIST_ITEM_SELECTED_ACTION = "listItemSelectedAction";
    public static final String LIST_ITEM_TEXT_KEY = "listItemNameKey";
    private static final String LIST_KEY = "listKey";
    private static final String MODE_KEY = "modeKey";
    private static final String TAG = GDDialogActivity.class.getSimpleName();
    private int mode;

    /* loaded from: classes.dex */
    public static class GDDialogImpl implements GDDialog {
        public static GDDialog newInstance() {
            return new GDDialogImpl();
        }

        @Override // com.good.gd.GDDialog
        public void show(List<String> list, Context context) {
            GDDialogActivity.showListDialog(list, context);
        }

        @Override // com.good.gd.GDDialog
        public void show(String str, Context context) {
            GDDialogActivity.showInfoDialog(str, context);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class fdyxd implements Runnable {
        fdyxd() {
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                Window window = GDDialogActivity.this.getWindow();
                while (window.getContainer() != null) {
                    window = window.getContainer();
                }
                ViewGroup viewGroup = (ViewGroup) window.getDecorView();
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    View childAt = viewGroup.getChildAt(i);
                    if (!(childAt instanceof ViewGroup)) {
                        childAt.getLayoutParams().height = GDDialogActivity.this.findViewById(R.id.dialog_content).getHeight();
                        childAt.requestLayout();
                    }
                }
            } catch (Exception e) {
                Log.e(GDDialogActivity.TAG, "forceWrapContent", e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class hbfhc implements View.OnClickListener {
        hbfhc() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            GDDialogActivity.this.finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class yfdke implements View.OnClickListener {
        yfdke() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            GDDialogActivity.this.finish();
            GDDialogActivity.this.listDialogCancelled();
        }
    }

    private void forceWrapContent() {
        new Handler().post(new fdyxd());
    }

    private TextView getTitleView() {
        TextView textView = (TextView) findViewById(16908310);
        textView.setSingleLine(false);
        textView.setGravity(17);
        return textView;
    }

    private void initInfoMode(Bundle bundle) {
        ((TextView) findViewById(R.id.info_text)).setText(bundle.getString(INFO_KEY));
        findViewById(R.id.ok_button).setOnClickListener(new hbfhc());
    }

    private void initListMode(Bundle bundle) {
        String[] stringArray = bundle.getStringArray(LIST_KEY);
        ListView listView = (ListView) findViewById(R.id.items_list);
        listView.setAdapter((ListAdapter) new ArrayAdapter(this, 17367057, stringArray));
        listView.setOnItemClickListener(this);
        findViewById(R.id.cancel_button).setOnClickListener(new yfdke());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void listDialogCancelled() {
        Intent intent = new Intent(LIST_ITEM_SELECTED_ACTION);
        intent.putExtra(LIST_ITEM_CANCELLED_BOOL_KEY, true);
        GDLocalBroadcastManager.getInstance().sendBroadcast(intent);
    }

    private void setDialogTitle() {
        TextView titleView = getTitleView();
        int i = this.mode;
        if (i == 1) {
            titleView.setText(GDLocalizer.getLocalizedString("Open URL with"));
        } else if (i != 2) {
        } else {
            titleView.setText(GDLocalizer.getLocalizedString("Cannot Open URL"));
        }
    }

    public static void showInfoDialog(String str, Context context) {
        Intent intent = new Intent(context, GDDialogActivity.class);
        intent.putExtra(LAYOUT_ID_KEY, R.layout.bbd_dialog_info);
        intent.putExtra(MODE_KEY, 2);
        intent.putExtra(INFO_KEY, str);
        intent.setFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
        context.startActivity(intent);
    }

    public static void showListDialog(List<String> list, Context context) {
        Intent intent = new Intent(context, GDDialogActivity.class);
        intent.putExtra(LAYOUT_ID_KEY, R.layout.bbd_dialog_list);
        intent.putExtra(MODE_KEY, 1);
        intent.putExtra(LIST_KEY, (String[]) list.toArray(new String[list.size()]));
        intent.setFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
        context.startActivity(intent);
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        if (this.mode == 1) {
            listDialogCancelled();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.good.gd.Activity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle extras = getIntent().getExtras();
        int i = extras.getInt(LAYOUT_ID_KEY, 0);
        if (i > 0) {
            setContentView(i);
            int i2 = extras.getInt(MODE_KEY, 0);
            this.mode = i2;
            if (i2 == 1) {
                initListMode(extras);
                return;
            } else if (i2 != 2) {
                return;
            } else {
                initInfoMode(extras);
                return;
            }
        }
        finish();
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        Intent intent = new Intent(LIST_ITEM_SELECTED_ACTION);
        intent.putExtra(LIST_ITEM_INDEX_INT_KEY, i);
        intent.putExtra(LIST_ITEM_CANCELLED_BOOL_KEY, false);
        if (view instanceof TextView) {
            intent.putExtra(LIST_ITEM_TEXT_KEY, ((TextView) view).getText().toString());
        }
        finish();
        GDLocalBroadcastManager.getInstance().sendBroadcast(intent);
    }

    @Override // android.app.Activity
    protected void onPostResume() {
        super.onPostResume();
        forceWrapContent();
        setDialogTitle();
    }
}
