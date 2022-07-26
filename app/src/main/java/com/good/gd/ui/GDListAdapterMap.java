package com.good.gd.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import com.good.gd.resources.R;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class GDListAdapterMap extends BaseAdapter {
    public static final int TYPE_SECTION_HEADER = 0;
    public ArrayAdapter<String> headers;
    public Map<String, Adapter> sections = new LinkedHashMap();

    public GDListAdapterMap(Context context) {
        this.headers = null;
        this.headers = new ArrayAdapter<>(context, R.layout.bbd_activation_delegate_list_header);
    }

    public void addSection(String str, Adapter adapter) {
        this.headers.add(str);
        this.sections.put(str, adapter);
    }

    public boolean areAllItemsSelectable() {
        return false;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        int i = 0;
        for (Adapter adapter : this.sections.values()) {
            i += adapter.getCount() + 1;
        }
        return i;
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        for (String str : this.sections.keySet()) {
            Adapter adapter = this.sections.get(str);
            int count = adapter.getCount() + 1;
            if (i == 0) {
                return str;
            }
            if (i < count) {
                return adapter.getItem(i - 1);
            }
            i -= count;
        }
        return null;
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getItemViewType(int i) {
        int i2 = 1;
        for (String str : this.sections.keySet()) {
            Adapter adapter = this.sections.get(str);
            int count = adapter.getCount() + 1;
            if (i == 0) {
                return 0;
            }
            if (i < count) {
                return i2 + adapter.getItemViewType(i - 1);
            }
            i -= count;
            i2 += adapter.getViewTypeCount();
        }
        return -1;
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        int i2 = 0;
        for (String str : this.sections.keySet()) {
            Adapter adapter = this.sections.get(str);
            int count = adapter.getCount() + 1;
            if (i == 0) {
                return this.headers.getView(i2, view, viewGroup);
            }
            if (i < count) {
                return adapter.getView(i - 1, view, viewGroup);
            }
            i -= count;
            i2++;
        }
        return null;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getViewTypeCount() {
        int i = 1;
        for (Adapter adapter : this.sections.values()) {
            i += adapter.getViewTypeCount();
        }
        return i;
    }

    @Override // android.widget.BaseAdapter, android.widget.ListAdapter
    public boolean isEnabled(int i) {
        return getItemViewType(i) != 0;
    }
}
