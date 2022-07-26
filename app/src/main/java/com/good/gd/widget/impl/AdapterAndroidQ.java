package com.good.gd.widget.impl;

import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.CursorAdapter;
import android.widget.SearchView;
import com.good.gd.adapter.GDSuggestionsAdapter;
import com.good.gd.adapter.SuggestionAdapterApi;
import com.good.gd.richtext.ParcelableUtil;
import java.util.HashMap;
import java.util.WeakHashMap;

/* loaded from: classes.dex */
public class AdapterAndroidQ implements SuggestionAdapterApi {
    private HashMap<Integer, GDActionKeyInfo> mActionKeys;

    /* loaded from: classes.dex */
    public static class GDActionKeyInfo implements Parcelable {
        public static final Creator<GDActionKeyInfo> CREATOR = new hbfhc();
        private int mKeyCode;
        private String mQueryActionMsg;
        private String mSuggestActionMsg;
        private String mSuggestActionMsgColumn;

        /* loaded from: classes.dex */
        static class hbfhc implements Creator<GDActionKeyInfo> {
            hbfhc() {
            }

            @Override // android.os.Parcelable.Creator
            public GDActionKeyInfo createFromParcel(Parcel parcel) {
                return new GDActionKeyInfo(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public GDActionKeyInfo[] newArray(int i) {
                return new GDActionKeyInfo[i];
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public int getKeyCode() {
            return this.mKeyCode;
        }

        public String getQueryActionMsg() {
            return this.mQueryActionMsg;
        }

        public String getSuggestActionMsg() {
            return this.mSuggestActionMsg;
        }

        public String getSuggestActionMsgColumn() {
            return this.mSuggestActionMsgColumn;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mKeyCode);
            parcel.writeString(this.mQueryActionMsg);
            parcel.writeString(this.mSuggestActionMsg);
            parcel.writeString(this.mSuggestActionMsgColumn);
        }

        private GDActionKeyInfo(Parcel parcel) {
            this.mKeyCode = parcel.readInt();
            this.mQueryActionMsg = parcel.readString();
            this.mSuggestActionMsg = parcel.readString();
            this.mSuggestActionMsgColumn = parcel.readString();
        }
    }

    private HashMap<Integer, GDActionKeyInfo> parseSearchableInfo(SearchableInfo searchableInfo) {
        Parcel unmarshall = ParcelableUtil.unmarshall(ParcelableUtil.marshall(searchableInfo));
        unmarshall.readInt();
        ComponentName.readFromParcel(unmarshall);
        unmarshall.readInt();
        unmarshall.readInt();
        unmarshall.readInt();
        unmarshall.readInt();
        unmarshall.readInt();
        unmarshall.readInt();
        unmarshall.readInt();
        unmarshall.readInt();
        unmarshall.readInt();
        unmarshall.readInt();
        unmarshall.readString();
        unmarshall.readString();
        unmarshall.readString();
        unmarshall.readString();
        unmarshall.readString();
        unmarshall.readInt();
        for (int readInt = unmarshall.readInt(); readInt > 0; readInt--) {
            GDActionKeyInfo gDActionKeyInfo = new GDActionKeyInfo(unmarshall);
            this.mActionKeys.put(Integer.valueOf(gDActionKeyInfo.getKeyCode()), gDActionKeyInfo);
        }
        return this.mActionKeys;
    }

    @Override // com.good.gd.adapter.SuggestionAdapterApi
    public CursorAdapter createSuggestionsAdapter(SearchableInfo searchableInfo, WeakHashMap<String, Drawable.ConstantState> weakHashMap, Context context, SearchView searchView) {
        if (searchableInfo == null || weakHashMap == null) {
            return null;
        }
        return new GDSuggestionsAdapter(context, searchView, searchableInfo, weakHashMap);
    }

    @Override // com.good.gd.adapter.SuggestionAdapterApi
    public Object findActionKey(SearchableInfo searchableInfo, int i) {
        if (this.mActionKeys == null) {
            this.mActionKeys = new HashMap<>();
            this.mActionKeys = parseSearchableInfo(searchableInfo);
        }
        return this.mActionKeys.get(Integer.valueOf(i));
    }

    @Override // com.good.gd.adapter.SuggestionAdapterApi
    public String getColumnString(Cursor cursor, Object obj) {
        return GDSuggestionsAdapter.getColumnString(cursor, (String) obj);
    }

    @Override // com.good.gd.adapter.SuggestionAdapterApi
    public String getQueryActionMsg(Object obj) {
        return ((GDActionKeyInfo) obj).getQueryActionMsg();
    }

    @Override // com.good.gd.adapter.SuggestionAdapterApi
    public int getQueryRefirementRefineAll(CursorAdapter cursorAdapter) {
        return 2;
    }

    @Override // com.good.gd.adapter.SuggestionAdapterApi
    public int getQueryRefirementRefineByEntry(CursorAdapter cursorAdapter) {
        return 1;
    }

    @Override // com.good.gd.adapter.SuggestionAdapterApi
    public String getSuggestActionMsg(Object obj) {
        return ((GDActionKeyInfo) obj).getSuggestActionMsg();
    }

    @Override // com.good.gd.adapter.SuggestionAdapterApi
    public String getSuggestActionMsgColumn(Object obj) {
        return ((GDActionKeyInfo) obj).getSuggestActionMsgColumn();
    }

    @Override // com.good.gd.adapter.SuggestionAdapterApi
    public void releaseCursor(CursorAdapter cursorAdapter) {
        if (cursorAdapter != null) {
            cursorAdapter.changeCursor(null);
        }
    }

    @Override // com.good.gd.adapter.SuggestionAdapterApi
    public void setQueryRefinement(CursorAdapter cursorAdapter, int i) {
        ((GDSuggestionsAdapter) cursorAdapter).setQueryRefinement(i);
    }
}
