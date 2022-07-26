package com.good.gd.widget.impl;

import android.app.SearchableInfo;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.widget.CursorAdapter;
import android.widget.SearchView;
import com.good.gd.adapter.SuggestionAdapterApi;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.utils.ReflectionUtils;
import com.good.gd.widget.GDSearchView;
import java.util.WeakHashMap;

/* loaded from: classes.dex */
public class AdapterBelowAndroidQ implements SuggestionAdapterApi {
    @Override // com.good.gd.adapter.SuggestionAdapterApi
    public CursorAdapter createSuggestionsAdapter(SearchableInfo searchableInfo, WeakHashMap<String, Drawable.ConstantState> weakHashMap, Context context, SearchView searchView) {
        if (searchableInfo != null && weakHashMap != null) {
            try {
                return (CursorAdapter) ReflectionUtils.createObject("android.widget.SuggestionsAdapter", new Class[]{Context.class, SearchView.class, SearchableInfo.class, WeakHashMap.class}, context, searchView, searchableInfo, weakHashMap);
            } catch (Exception e) {
                GDSearchView.logClassInstantiationException("android.widget.SuggestionsAdapter");
            }
        }
        return null;
    }

    @Override // com.good.gd.adapter.SuggestionAdapterApi
    public Object findActionKey(SearchableInfo searchableInfo, int i) {
        try {
            return ReflectionUtils.invokeMethod(SearchableInfo.class, searchableInfo, "findActionKey", new Class[]{Integer.class}, Integer.valueOf(i));
        } catch (Exception e) {
            GDSearchView.logMethodAccessException("findActionKey(..)");
            return null;
        }
    }

    @Override // com.good.gd.adapter.SuggestionAdapterApi
    public String getColumnString(Cursor cursor, Object obj) {
        try {
            try {
                return (String) ReflectionUtils.invokeMethod(ReflectionUtils.getClassForName("android.widget.SuggestionsAdapter"), null, "getColumnString", new Class[]{Cursor.class, obj.getClass()}, cursor, obj);
            } catch (Exception e) {
                GDSearchView.logMethodAccessException("getColumnString(..)");
                return null;
            }
        } catch (Exception e2) {
            GDSearchView.logClassAccessException("android.widget.SuggestionsAdapter");
            return null;
        }
    }

    @Override // com.good.gd.adapter.SuggestionAdapterApi
    public String getQueryActionMsg(Object obj) {
        try {
            return (String) ReflectionUtils.invokeMethod(obj.getClass(), obj, "getQueryActionMsg", null, new Object[0]);
        } catch (Exception e) {
            GDSearchView.logMethodAccessException("getQueryActionMsg()");
            return null;
        }
    }

    @Override // com.good.gd.adapter.SuggestionAdapterApi
    public int getQueryRefirementRefineAll(CursorAdapter cursorAdapter) {
        try {
            return ((Integer) ReflectionUtils.getFieldValue(cursorAdapter.getClass(), null, "REFINE_ALL")).intValue();
        } catch (Exception e) {
            GDSearchView.logFieldAccessException("REFINE_ALL");
            return 0;
        }
    }

    @Override // com.good.gd.adapter.SuggestionAdapterApi
    public int getQueryRefirementRefineByEntry(CursorAdapter cursorAdapter) {
        try {
            return ((Integer) ReflectionUtils.getFieldValue(cursorAdapter.getClass(), null, "REFINE_BY_ENTRY")).intValue();
        } catch (Exception e) {
            GDSearchView.logFieldAccessException("REFINE_BY_ENTRY");
            return 0;
        }
    }

    @Override // com.good.gd.adapter.SuggestionAdapterApi
    public String getSuggestActionMsg(Object obj) {
        try {
            return (String) ReflectionUtils.invokeMethod(obj.getClass(), obj, "getSuggestActionMsg", null, new Object[0]);
        } catch (Exception e) {
            GDSearchView.logMethodAccessException("getSuggestActionMsg()");
            return null;
        }
    }

    @Override // com.good.gd.adapter.SuggestionAdapterApi
    public String getSuggestActionMsgColumn(Object obj) {
        try {
            return (String) ReflectionUtils.invokeMethod(obj.getClass(), obj, "getSuggestActionMsgColumn", null, new Object[0]);
        } catch (Exception e) {
            GDSearchView.logMethodAccessException("getSuggestActionMsgColumn()");
            return null;
        }
    }

    @Override // com.good.gd.adapter.SuggestionAdapterApi
    public void releaseCursor(CursorAdapter cursorAdapter) {
        if (cursorAdapter != null) {
            try {
                if (!ReflectionUtils.getClassForName("android.widget.SuggestionsAdapter").isInstance(cursorAdapter)) {
                    return;
                }
                cursorAdapter.changeCursor(null);
            } catch (Exception e) {
                GDLog.DBGPRINTF(13, "GDSearchView releaseCursor", e);
            }
        }
    }

    @Override // com.good.gd.adapter.SuggestionAdapterApi
    public void setQueryRefinement(CursorAdapter cursorAdapter, int i) {
        try {
            ReflectionUtils.invokeVoidMethod(cursorAdapter.getClass(), cursorAdapter, "setQueryRefinement", new Class[]{Integer.class}, Integer.valueOf(i));
        } catch (Exception e) {
            GDSearchView.logMethodAccessException("setQueryRefinement(..)");
        }
    }
}
