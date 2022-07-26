package com.good.gd.adapter;

import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.good.gd.ndkproxy.GDLog;
import com.good.gd.widget.GDSearchView;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.WeakHashMap;

/* loaded from: classes.dex */
public class GDSuggestionsAdapter extends ResourceCursorAdapter implements View.OnClickListener {
    private static final int INVALID_INDEX = -1;
    private static final String LOG_TAG = "GDSuggestionsAdapter";
    private static final int QUERY_LIMIT = 50;
    public static final int REFINE_ALL = 2;
    public static final int REFINE_BY_ENTRY = 1;
    public static final int REFINE_NONE = 0;
    private final Context mContext;
    private final WeakHashMap<String, Drawable.ConstantState> mOutsideDrawablesCache;
    private final Context mProviderContext;
    private final SearchView mSearchView;
    private final SearchableInfo mSearchable;
    private ColorStateList mUrlColor;
    private boolean mClosed = false;
    private int mQueryRefinement = 1;
    private int mText1Col = -1;
    private int mText2Col = -1;
    private int mText2UrlCol = -1;
    private int mIconName1Col = -1;
    private int mIconName2Col = -1;
    private int mFlagsCol = -1;
    private final int mCommitIconResId = Resources.getSystem().getIdentifier("SearchView_commitIcon", "styleable", "android");

    /* loaded from: classes.dex */
    private class hbfhc {
        public final TextView dbjc;
        public final ImageView jwxax;
        public final TextView qkduk;
        public final ImageView wxau;
        public final ImageView ztwf;

        public hbfhc(GDSuggestionsAdapter gDSuggestionsAdapter, View view) {
            this.dbjc = (TextView) view.findViewById(16908308);
            this.qkduk = (TextView) view.findViewById(16908309);
            this.jwxax = (ImageView) view.findViewById(16908295);
            this.wxau = (ImageView) view.findViewById(16908296);
            this.ztwf = (ImageView) view.findViewById(16908291);
        }
    }

    public GDSuggestionsAdapter(Context context, SearchView searchView, SearchableInfo searchableInfo, WeakHashMap<String, Drawable.ConstantState> weakHashMap) {
        super(context, 17367053, (Cursor) null, true);
        this.mContext = context;
        this.mSearchView = searchView;
        this.mSearchable = searchableInfo;
        this.mProviderContext = getProviderContext(context, createActivityContext(context, searchableInfo.getSearchActivity()));
        this.mOutsideDrawablesCache = weakHashMap;
    }

    private Drawable checkIconCache(String str) {
        Drawable.ConstantState constantState = this.mOutsideDrawablesCache.get(str);
        if (constantState == null) {
            return null;
        }
        GDLog.DBGPRINTF(14, "GDSuggestionsAdapter: checkIconCache() Found icon in cache: " + str + "\n");
        return constantState.newDrawable();
    }

    private Context createActivityContext(Context context, ComponentName componentName) {
        try {
            return context.createPackageContext(componentName.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            GDLog.DBGPRINTF(12, "GDSuggestionsAdapter: createActivityContext()  Package not found " + componentName.getPackageName() + ". " + e.toString() + "\n");
            return null;
        } catch (SecurityException e2) {
            GDLog.DBGPRINTF(12, "GDSuggestionsAdapter: createActivityContext()  Can't make context for " + componentName.getPackageName() + ". " + e2.toString() + "\n");
            return null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private CharSequence formatUrl(Context context, CharSequence charSequence) {
        if (this.mUrlColor == null) {
            TypedValue typedValue = new TypedValue();
            context.getTheme().resolveAttribute(16842904, typedValue, true);
            this.mUrlColor = context.getColorStateList(typedValue.resourceId);
        }
        SpannableString spannableString = new SpannableString(charSequence);
        spannableString.setSpan(new TextAppearanceSpan(null, 0, 0, this.mUrlColor, null), 0, charSequence.length(), 33);
        return spannableString;
    }

    private Drawable getActivityIcon(ComponentName componentName) {
        PackageManager packageManager = this.mContext.getPackageManager();
        try {
            ActivityInfo activityInfo = packageManager.getActivityInfo(componentName, 128);
            int iconResource = activityInfo.getIconResource();
            if (iconResource == 0) {
                return null;
            }
            Drawable drawable = packageManager.getDrawable(componentName.getPackageName(), iconResource, activityInfo.applicationInfo);
            if (drawable != null) {
                return drawable;
            }
            GDLog.DBGPRINTF(14, "GDSuggestionsAdapter: getActivityIcon() Invalid icon resource " + iconResource + " for " + componentName.flattenToShortString() + "\n");
            return null;
        } catch (PackageManager.NameNotFoundException e) {
            GDLog.DBGPRINTF(12, "GDSuggestionsAdapter: getActivityIcon() Exception: " + e.toString() + "\n");
            return null;
        }
    }

    private Drawable getActivityIconWithCache(ComponentName componentName) {
        String flattenToShortString = componentName.flattenToShortString();
        Drawable.ConstantState constantState = null;
        if (this.mOutsideDrawablesCache.containsKey(flattenToShortString)) {
            Drawable.ConstantState constantState2 = this.mOutsideDrawablesCache.get(flattenToShortString);
            if (constantState2 != null) {
                return constantState2.newDrawable(this.mProviderContext.getResources());
            }
            return null;
        }
        Drawable activityIcon = getActivityIcon(componentName);
        if (activityIcon != null) {
            constantState = activityIcon.getConstantState();
        }
        this.mOutsideDrawablesCache.put(flattenToShortString, constantState);
        return activityIcon;
    }

    public static String getColumnString(Cursor cursor, String str) {
        return getStringOrNull(cursor, cursor.getColumnIndex(str));
    }

    private Drawable getDefaultIcon1(Cursor cursor) {
        Drawable activityIconWithCache = getActivityIconWithCache(this.mSearchable.getSearchActivity());
        return activityIconWithCache != null ? activityIconWithCache : this.mContext.getPackageManager().getDefaultActivityIcon();
    }

    private Drawable getDrawable(Uri uri) {
        try {
            if ("android.resource".equals(uri.getScheme())) {
                try {
                    return getDrawableFromResourceUri(uri);
                } catch (Resources.NotFoundException e) {
                    throw new FileNotFoundException("Resource does not exist: " + uri);
                }
            }
            InputStream openInputStream = this.mProviderContext.getContentResolver().openInputStream(uri);
            if (openInputStream != null) {
                Drawable createFromStream = Drawable.createFromStream(openInputStream, null);
                try {
                    openInputStream.close();
                } catch (IOException e2) {
                    GDLog.DBGPRINTF(12, "GDSuggestionsAdapter: getDrawable()  closing icon stream for " + uri + ". " + e2.toString() + "\n");
                }
                return createFromStream;
            }
            throw new FileNotFoundException("Failed to open " + uri);
        } catch (FileNotFoundException e3) {
            GDLog.DBGPRINTF(12, "GDSuggestionsAdapter: getDrawable() Icon not found: " + uri + ". " + e3.toString() + "\n");
            return null;
        }
        GDLog.DBGPRINTF(12, "GDSuggestionsAdapter: getDrawable() Icon not found: " + uri + ". " + e3.toString() + "\n");
        return null;
    }

    private Drawable getDrawableFromResourceValue(String str) {
        if (str == null || str.isEmpty() || "0".equals(str)) {
            return null;
        }
        try {
            int parseInt = Integer.parseInt(str);
            String str2 = "android.resource://" + this.mProviderContext.getPackageName() + "/" + parseInt;
            Drawable checkIconCache = checkIconCache(str2);
            if (checkIconCache != null) {
                return checkIconCache;
            }
            Drawable drawable = this.mProviderContext.getDrawable(parseInt);
            storeInIconCache(str2, drawable);
            return drawable;
        } catch (Resources.NotFoundException e) {
            GDLog.DBGPRINTF(12, "GDSuggestionsAdapter: getDrawableFromResourceValue() resource not found: " + str + ". " + e.toString() + "\n");
            return null;
        } catch (NumberFormatException e2) {
            Drawable checkIconCache2 = checkIconCache(str);
            if (checkIconCache2 != null) {
                return checkIconCache2;
            }
            Drawable drawable2 = getDrawable(Uri.parse(str));
            storeInIconCache(str, drawable2);
            return drawable2;
        }
    }

    private Drawable getIcon1(Cursor cursor) {
        int i = this.mIconName1Col;
        if (i == -1) {
            return null;
        }
        Drawable drawableFromResourceValue = getDrawableFromResourceValue(cursor.getString(i));
        return drawableFromResourceValue != null ? drawableFromResourceValue : getDefaultIcon1(cursor);
    }

    private Drawable getIcon2(Cursor cursor) {
        int i = this.mIconName2Col;
        if (i == -1) {
            return null;
        }
        return getDrawableFromResourceValue(cursor.getString(i));
    }

    private Context getProviderContext(Context context, Context context2) {
        String suggestPackage = this.mSearchable.getSuggestPackage();
        if (this.mSearchable.getSearchActivity().getPackageName().equals(suggestPackage)) {
            return context2;
        }
        if (suggestPackage != null) {
            try {
                return context.createPackageContext(suggestPackage, 0);
            } catch (PackageManager.NameNotFoundException e) {
                GDLog.DBGPRINTF(12, "GDSuggestionsAdapter: getProviderContext()  Exception: " + e.toString() + "\n");
            } catch (SecurityException e2) {
                GDLog.DBGPRINTF(12, "GDSuggestionsAdapter: getProviderContext() Exception: " + e2.toString() + "\n");
            }
        }
        return null;
    }

    private Cursor getSearchManagerSuggestions(SearchableInfo searchableInfo, String str, int i) {
        String suggestAuthority;
        String[] strArr;
        if (searchableInfo == null || (suggestAuthority = searchableInfo.getSuggestAuthority()) == null) {
            return null;
        }
        Uri.Builder fragment = new Uri.Builder().scheme("content").authority(suggestAuthority).query("").fragment("");
        String suggestPath = searchableInfo.getSuggestPath();
        if (suggestPath != null) {
            fragment.appendEncodedPath(suggestPath);
        }
        fragment.appendPath("search_suggest_query");
        String suggestSelection = searchableInfo.getSuggestSelection();
        if (suggestSelection != null) {
            strArr = new String[]{str};
        } else {
            fragment.appendPath(str);
            strArr = null;
        }
        if (i > 0) {
            fragment.appendQueryParameter("limit", String.valueOf(i));
        }
        return this.mProviderContext.getContentResolver().query(fragment.build(), null, suggestSelection, strArr, null);
    }

    private static String getStringOrNull(Cursor cursor, int i) {
        if (i == -1) {
            return null;
        }
        try {
            return cursor.getString(i);
        } catch (Exception e) {
            GDLog.DBGPRINTF(12, "GDSuggestionsAdapter: getStringOrNull() unexpected error retrieving valid column from cursor, did the remote process die?" + e.toString() + "\n");
            return null;
        }
    }

    private void setViewDrawable(ImageView imageView, Drawable drawable, int i) {
        imageView.setImageDrawable(drawable);
        if (drawable == null) {
            imageView.setVisibility(i);
            return;
        }
        imageView.setVisibility(0);
        drawable.setVisible(false, false);
        drawable.setVisible(true, false);
    }

    private void setViewText(TextView textView, CharSequence charSequence) {
        textView.setText(charSequence);
        if (TextUtils.isEmpty(charSequence)) {
            textView.setVisibility(8);
        } else {
            textView.setVisibility(0);
        }
    }

    private void storeInIconCache(String str, Drawable drawable) {
        if (drawable != null) {
            this.mOutsideDrawablesCache.put(str, drawable.getConstantState());
        }
    }

    private void updateSpinnerState(Cursor cursor) {
        Boolean bool = null;
        Bundle extras = cursor != null ? cursor.getExtras() : null;
        StringBuilder append = new StringBuilder().append("GDSuggestionsAdapter: updateSpinnerState() - extra = ");
        if (extras != null) {
            bool = Boolean.valueOf(extras.getBoolean("in_progress"));
        }
        GDLog.DBGPRINTF(14, append.append(bool).append("\n").toString());
        if (extras != null) {
            extras.getBoolean("in_progress");
        }
    }

    @Override // android.widget.CursorAdapter
    public void bindView(View view, Context context, Cursor cursor) {
        int i;
        CharSequence stringOrNull;
        hbfhc hbfhcVar = (hbfhc) view.getTag();
        int i2 = this.mFlagsCol;
        if (i2 == -1) {
            i = 0;
        } else {
            i = cursor.getInt(i2);
        }
        if (hbfhcVar.dbjc != null) {
            setViewText(hbfhcVar.dbjc, getStringOrNull(cursor, this.mText1Col));
        }
        if (hbfhcVar.qkduk != null) {
            String stringOrNull2 = getStringOrNull(cursor, this.mText2UrlCol);
            if (stringOrNull2 != null) {
                stringOrNull = formatUrl(context, stringOrNull2);
            } else {
                stringOrNull = getStringOrNull(cursor, this.mText2Col);
            }
            if (TextUtils.isEmpty(stringOrNull)) {
                TextView textView = hbfhcVar.dbjc;
                if (textView != null) {
                    textView.setSingleLine(false);
                    hbfhcVar.dbjc.setMaxLines(2);
                }
            } else {
                TextView textView2 = hbfhcVar.dbjc;
                if (textView2 != null) {
                    textView2.setSingleLine(true);
                    hbfhcVar.dbjc.setMaxLines(1);
                }
            }
            setViewText(hbfhcVar.qkduk, stringOrNull);
        }
        ImageView imageView = hbfhcVar.jwxax;
        if (imageView != null) {
            setViewDrawable(imageView, getIcon1(cursor), 4);
        }
        ImageView imageView2 = hbfhcVar.wxau;
        if (imageView2 != null) {
            setViewDrawable(imageView2, getIcon2(cursor), 8);
        }
        if (hbfhcVar.ztwf != null) {
            int i3 = this.mQueryRefinement;
            if (i3 != 2 && (i3 != 1 || (i & 1) == 0)) {
                hbfhcVar.ztwf.setVisibility(View.GONE);
                return;
            }
            hbfhcVar.ztwf.setVisibility(View.VISIBLE);
            hbfhcVar.ztwf.setTag(hbfhcVar.dbjc.getText());
            hbfhcVar.ztwf.setOnClickListener(this);
        }
    }

    @Override // android.widget.CursorAdapter
    public void changeCursor(Cursor cursor) {
        GDLog.DBGPRINTF(14, "GDSuggestionsAdapter: changeCursor(" + cursor + ")\n");
        if (this.mClosed) {
            GDLog.DBGPRINTF(14, "GDSuggestionsAdapter: changeCursor()  Tried to change cursor after adapter was closed.\n");
            if (cursor == null) {
                return;
            }
            cursor.close();
            return;
        }
        try {
            super.changeCursor(cursor);
            if (cursor == null) {
                return;
            }
            this.mText1Col = cursor.getColumnIndex("suggest_text_1");
            this.mText2Col = cursor.getColumnIndex("suggest_text_2");
            this.mText2UrlCol = cursor.getColumnIndex("suggest_text_2_url");
            this.mIconName1Col = cursor.getColumnIndex("suggest_icon_1");
            this.mIconName2Col = cursor.getColumnIndex("suggest_icon_2");
            this.mFlagsCol = cursor.getColumnIndex("suggest_flags");
        } catch (Exception e) {
            GDLog.DBGPRINTF(12, "GDSuggestionsAdapter: changeCursor() error changing cursor and caching columns. " + e.toString() + "\n");
        }
    }

    public void close() {
        GDLog.DBGPRINTF(14, "GDSuggestionsAdapter: close()\n");
        changeCursor(null);
        this.mClosed = true;
    }

    @Override // android.widget.CursorAdapter
    public CharSequence convertToString(Cursor cursor) {
        String columnString;
        String columnString2;
        if (cursor == null) {
            return null;
        }
        String columnString3 = getColumnString(cursor, "suggest_intent_query");
        if (columnString3 != null) {
            return columnString3;
        }
        if (this.mSearchable.shouldRewriteQueryFromData() && (columnString2 = getColumnString(cursor, "suggest_intent_data")) != null) {
            return columnString2;
        }
        if (this.mSearchable.shouldRewriteQueryFromText() && (columnString = getColumnString(cursor, "suggest_text_1")) != null) {
            return columnString;
        }
        return null;
    }

    Drawable getDrawableFromResourceUri(Uri uri) throws FileNotFoundException {
        int parseInt;
        String authority = uri.getAuthority();
        if (!TextUtils.isEmpty(authority)) {
            try {
                Resources resourcesForApplication = this.mProviderContext.getPackageManager().getResourcesForApplication(authority);
                List<String> pathSegments = uri.getPathSegments();
                if (pathSegments != null) {
                    int size = pathSegments.size();
                    if (size == 1) {
                        try {
                            parseInt = Integer.parseInt(pathSegments.get(0));
                        } catch (NumberFormatException e) {
                            throw new FileNotFoundException("Single path segment is not a resource ID: " + uri);
                        }
                    } else if (size == 2) {
                        parseInt = resourcesForApplication.getIdentifier(pathSegments.get(1), pathSegments.get(0), authority);
                    } else {
                        throw new FileNotFoundException("More than two path segments: " + uri);
                    }
                    if (parseInt != 0) {
                        return resourcesForApplication.getDrawable(parseInt);
                    }
                    throw new FileNotFoundException("No resource found for: " + uri);
                }
                throw new FileNotFoundException("No path: " + uri);
            } catch (PackageManager.NameNotFoundException e2) {
                throw new FileNotFoundException("No package found for authority: " + uri);
            }
        }
        throw new FileNotFoundException("No authority: " + uri);
    }

    @Override // android.widget.CursorAdapter, android.widget.BaseAdapter, android.widget.SpinnerAdapter
    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        try {
            return super.getDropDownView(i, view, viewGroup);
        } catch (RuntimeException e) {
            GDLog.DBGPRINTF(12, "GDSuggestionsAdapter: getDropDownView() Search suggestions cursor threw exception. " + e.toString() + "\n");
            return null;
        }
    }

    public int getQueryRefinement() {
        return this.mQueryRefinement;
    }

    @Override // android.widget.CursorAdapter, android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        try {
            return super.getView(i, view, viewGroup);
        } catch (RuntimeException e) {
            GDLog.DBGPRINTF(12, "GDSuggestionsAdapter: getView() Search suggestions cursor threw exception. " + e.toString() + "\n");
            return null;
        }
    }

    @Override // android.widget.CursorAdapter, android.widget.BaseAdapter, android.widget.Adapter
    public boolean hasStableIds() {
        return false;
    }

    @Override // android.widget.ResourceCursorAdapter, android.widget.CursorAdapter
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View newView = super.newView(context, cursor, viewGroup);
        newView.setTag(new hbfhc(this, newView));
        ImageView imageView = (ImageView) newView.findViewById(16908291);
        if (imageView != null) {
            imageView.setImageResource(this.mCommitIconResId);
        }
        return newView;
    }

    @Override // android.widget.BaseAdapter
    public void notifyDataSetChanged() {
        GDLog.DBGPRINTF(14, "GDSuggestionsAdapter: notifyDataSetChanged()\n");
        super.notifyDataSetChanged();
        updateSpinnerState(getCursor());
    }

    @Override // android.widget.BaseAdapter
    public void notifyDataSetInvalidated() {
        GDLog.DBGPRINTF(14, "GDSuggestionsAdapter: notifyDataSetInvalidated()\n");
        super.notifyDataSetInvalidated();
        updateSpinnerState(getCursor());
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        Object tag = view.getTag();
        if (tag instanceof CharSequence) {
            ((GDSearchView) this.mSearchView).onQueryRefine((CharSequence) tag);
        }
    }

    @Override // android.widget.CursorAdapter
    public Cursor runQueryOnBackgroundThread(CharSequence charSequence) {
        GDLog.DBGPRINTF(14, "GDSuggestionsAdapter: runQueryOnBackgroundThread(" + ((Object) charSequence) + ")\n");
        String charSequence2 = charSequence == null ? "" : charSequence.toString();
        if (this.mSearchView.getVisibility() == View.VISIBLE && this.mSearchView.getWindowVisibility() == View.VISIBLE) {
            try {
                Cursor searchManagerSuggestions = getSearchManagerSuggestions(this.mSearchable, charSequence2, 50);
                if (searchManagerSuggestions != null) {
                    return searchManagerSuggestions;
                }
            } catch (RuntimeException e) {
                GDLog.DBGPRINTF(12, "GDSuggestionsAdapter: runQueryOnBackgroundThread()  Search suggestions query threw an exception. " + e.toString() + "\n");
            }
        }
        return null;
    }

    public void setQueryRefinement(int i) {
        this.mQueryRefinement = i;
    }
}
