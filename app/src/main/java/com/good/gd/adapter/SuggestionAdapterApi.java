package com.good.gd.adapter;

import android.app.SearchableInfo;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.widget.CursorAdapter;
import android.widget.SearchView;
import java.util.WeakHashMap;

/* loaded from: classes.dex */
public interface SuggestionAdapterApi {
    CursorAdapter createSuggestionsAdapter(SearchableInfo searchableInfo, WeakHashMap<String, Drawable.ConstantState> weakHashMap, Context context, SearchView searchView);

    Object findActionKey(SearchableInfo searchableInfo, int i);

    String getColumnString(Cursor cursor, Object obj);

    String getQueryActionMsg(Object obj);

    int getQueryRefirementRefineAll(CursorAdapter cursorAdapter);

    int getQueryRefirementRefineByEntry(CursorAdapter cursorAdapter);

    String getSuggestActionMsg(Object obj);

    String getSuggestActionMsgColumn(Object obj);

    void releaseCursor(CursorAdapter cursorAdapter);

    void setQueryRefinement(CursorAdapter cursorAdapter, int i);
}
