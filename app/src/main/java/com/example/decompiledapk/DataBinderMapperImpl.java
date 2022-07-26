package com.example.decompiledapk;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.example.decompiledapk.databinding.ActivityMainBindingImpl;
import com.example.decompiledapk.databinding.FragmentNetBindingImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes3.dex */
public class DataBinderMapperImpl extends DataBinderMapper {
    private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP;
    private static final int LAYOUT_ACTIVITYMAIN = 1;
    private static final int LAYOUT_FRAGMENTNET = 2;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray(2);
        INTERNAL_LAYOUT_ID_LOOKUP = sparseIntArray;
        sparseIntArray.put(R.layout.activity_main, 1);
        sparseIntArray.put(R.layout.fragment_net, 2);
    }

    @Override // androidx.databinding.DataBinderMapper
    public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
        int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
        if (localizedLayoutId > 0) {
            Object tag = view.getTag();
            if (tag == null) {
                throw new RuntimeException("view must have a tag");
            }
            switch (localizedLayoutId) {
                case 1:
                    if ("layout/activity_main_0".equals(tag)) {
                        return new ActivityMainBindingImpl(component, view);
                    }
                    throw new IllegalArgumentException("The tag for activity_main is invalid. Received: " + tag);
                case 2:
                    if ("layout/fragment_net_0".equals(tag)) {
                        return new FragmentNetBindingImpl(component, view);
                    }
                    throw new IllegalArgumentException("The tag for fragment_net is invalid. Received: " + tag);
                default:
                    return null;
            }
        }
        return null;
    }

    @Override // androidx.databinding.DataBinderMapper
    public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
        if (views == null || views.length == 0) {
            return null;
        }
        int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
        if (localizedLayoutId > 0) {
            Object tag = views[0].getTag();
            if (tag == null) {
                throw new RuntimeException("view must have a tag");
            }
        }
        return null;
    }

    @Override // androidx.databinding.DataBinderMapper
    public int getLayoutId(String tag) {
        Integer tmpVal;
        if (tag == null || (tmpVal = InnerLayoutIdLookup.sKeys.get(tag)) == null) {
            return 0;
        }
        return tmpVal.intValue();
    }

    @Override // androidx.databinding.DataBinderMapper
    public String convertBrIdToString(int localId) {
        String tmpVal = InnerBrLookup.sKeys.get(localId);
        return tmpVal;
    }

    @Override // androidx.databinding.DataBinderMapper
    public List<DataBinderMapper> collectDependencies() {
        ArrayList<DataBinderMapper> result = new ArrayList<>(1);
        result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
        return result;
    }

    /* loaded from: classes3.dex */
    private static class InnerBrLookup {
        static final SparseArray<String> sKeys;

        private InnerBrLookup() {
        }

        static {
            SparseArray<String> sparseArray = new SparseArray<>(1);
            sKeys = sparseArray;
            sparseArray.put(0, "_all");
        }
    }

    /* loaded from: classes3.dex */
    private static class InnerLayoutIdLookup {
        static final HashMap<String, Integer> sKeys;

        private InnerLayoutIdLookup() {
        }

        static {
            HashMap<String, Integer> hashMap = new HashMap<>(2);
            sKeys = hashMap;
            hashMap.put("layout/activity_main_0", Integer.valueOf((int) R.layout.activity_main));
            hashMap.put("layout/fragment_net_0", Integer.valueOf((int) R.layout.fragment_net));
        }
    }
}
