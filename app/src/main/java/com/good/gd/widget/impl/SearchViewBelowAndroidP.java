package com.good.gd.widget.impl;

import android.app.SearchableInfo;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.SearchView;
import com.good.gd.utils.ReflectionUtils;
import com.good.gd.widget.GDSearchView;
import java.util.WeakHashMap;

/* loaded from: classes.dex */
public class SearchViewBelowAndroidP implements SearchViewPrivateApi {
    private final SearchView searchView;

    public SearchViewBelowAndroidP(SearchView searchView) {
        this.searchView = searchView;
    }

    @Override // com.good.gd.widget.impl.SearchViewPrivateApi
    public ImageView getCloseButton() {
        try {
            return (ImageView) ReflectionUtils.getFieldValue(SearchView.class, this.searchView, "mCloseButton");
        } catch (Exception e) {
            GDSearchView.logFieldAccessException("mCloseButton");
            return null;
        }
    }

    @Override // com.good.gd.widget.impl.SearchViewPrivateApi
    public int getCollapsedImeOption() {
        try {
            return ((Integer) ReflectionUtils.getFieldValue(SearchView.class, this.searchView, "mCollapsedImeOptions")).intValue();
        } catch (Exception e) {
            GDSearchView.logFieldAccessException("mCollapsedImeOptions");
            return 0;
        }
    }

    @Override // com.good.gd.widget.impl.SearchViewPrivateApi
    public boolean getExpandedInActionView() {
        try {
            return ((Boolean) ReflectionUtils.getFieldValue(SearchView.class, this.searchView, "mExpandedInActionView")).booleanValue();
        } catch (Exception e) {
            GDSearchView.logFieldAccessException("mExpandedInActionView");
            return false;
        }
    }

    @Override // com.good.gd.widget.impl.SearchViewPrivateApi
    public CharSequence getOldQueryText() {
        try {
            return (CharSequence) ReflectionUtils.getFieldValue(SearchView.class, this.searchView, "mOldQueryText");
        } catch (Exception e) {
            GDSearchView.logFieldAccessException("mOldQueryText");
            return null;
        }
    }

    @Override // com.good.gd.widget.impl.SearchViewPrivateApi
    public SearchView.OnSuggestionListener getOnSuggestionListener() {
        try {
            return (SearchView.OnSuggestionListener) ReflectionUtils.getFieldValue(SearchView.class, this.searchView, "mOnSuggestionListener");
        } catch (Exception e) {
            GDSearchView.logFieldAccessException("mOnSuggestionListener");
            return null;
        }
    }

    @Override // com.good.gd.widget.impl.SearchViewPrivateApi
    public WeakHashMap<String, Drawable.ConstantState> getOutsideDrawablesCache() {
        try {
            return (WeakHashMap) ReflectionUtils.getFieldValue(SearchView.class, this.searchView, "mOutsideDrawablesCache");
        } catch (Exception e) {
            GDSearchView.logFieldAccessException("mOutsideDrawablesCache");
            return null;
        }
    }

    @Override // com.good.gd.widget.impl.SearchViewPrivateApi
    public CursorAdapter getPrivateSuggestionsAdapter() {
        try {
            return (CursorAdapter) ReflectionUtils.getFieldValue(SearchView.class, this.searchView, "mSuggestionsAdapter");
        } catch (Exception e) {
            GDSearchView.logFieldAccessException("mSuggestionsAdapter");
            return null;
        }
    }

    @Override // com.good.gd.widget.impl.SearchViewPrivateApi
    public Runnable getReleaseCursorRunnable() {
        try {
            return (Runnable) ReflectionUtils.getFieldValue(SearchView.class, this.searchView, "mReleaseCursorRunnable");
        } catch (Exception e) {
            GDSearchView.logFieldAccessException("mReleaseCursorRunnable");
            return null;
        }
    }

    @Override // com.good.gd.widget.impl.SearchViewPrivateApi
    public View getSearchButtonField() {
        try {
            return (View) ReflectionUtils.getFieldValue(SearchView.class, this.searchView, "mSearchButton");
        } catch (Exception e) {
            GDSearchView.logFieldAccessException("mSearchButton");
            return null;
        }
    }

    @Override // com.good.gd.widget.impl.SearchViewPrivateApi
    public View getSearchEditFrame() {
        try {
            return (View) ReflectionUtils.getFieldValue(SearchView.class, this.searchView, "mSearchEditFrame");
        } catch (Exception e) {
            GDSearchView.logFieldAccessException("mSearchEditFrame");
            return null;
        }
    }

    @Override // com.good.gd.widget.impl.SearchViewPrivateApi
    public SearchableInfo getSearchable() {
        try {
            return (SearchableInfo) ReflectionUtils.getFieldValue(SearchView.class, this.searchView, "mSearchable");
        } catch (Exception e) {
            GDSearchView.logFieldAccessException("mSearchable");
            return null;
        }
    }

    @Override // com.good.gd.widget.impl.SearchViewPrivateApi
    public View getSubmitArea() {
        try {
            return (View) ReflectionUtils.getFieldValue(SearchView.class, this.searchView, "mSubmitArea");
        } catch (Exception e) {
            GDSearchView.logFieldAccessException("mSubmitArea");
            return null;
        }
    }

    @Override // com.good.gd.widget.impl.SearchViewPrivateApi
    public View getSubmitButton() {
        try {
            return (View) ReflectionUtils.getFieldValue(SearchView.class, this.searchView, "mGoButton");
        } catch (Exception e) {
            GDSearchView.logFieldAccessException("mGoButton");
            return null;
        }
    }

    @Override // com.good.gd.widget.impl.SearchViewPrivateApi
    public Intent getVoiceAppSearchIntent() {
        try {
            return (Intent) ReflectionUtils.getFieldValue(SearchView.class, this.searchView, "mVoiceAppSearchIntent");
        } catch (Exception e) {
            GDSearchView.logFieldAccessException("mVoiceAppSearchIntent");
            return null;
        }
    }

    @Override // com.good.gd.widget.impl.SearchViewPrivateApi
    public View getVoiceButton() {
        try {
            return (View) ReflectionUtils.getFieldValue(SearchView.class, this.searchView, "mVoiceButton");
        } catch (Exception e) {
            GDSearchView.logFieldAccessException("mVoiceButton");
            return null;
        }
    }

    @Override // com.good.gd.widget.impl.SearchViewPrivateApi
    public Intent getVoiceWebSearchIntent() {
        try {
            return (Intent) ReflectionUtils.getFieldValue(SearchView.class, this.searchView, "mVoiceWebSearchIntent");
        } catch (Exception e) {
            GDSearchView.logFieldAccessException("mVoiceWebSearchIntent");
            return null;
        }
    }

    @Override // com.good.gd.widget.impl.SearchViewPrivateApi
    public void hideMagnifierIcon() {
    }

    @Override // com.good.gd.widget.impl.SearchViewPrivateApi
    public boolean isIconified() {
        try {
            return ((Boolean) ReflectionUtils.getFieldValue(SearchView.class, this.searchView, "mIconified")).booleanValue();
        } catch (Exception e) {
            GDSearchView.logFieldAccessException("mIconified");
            return false;
        }
    }

    @Override // com.good.gd.widget.impl.SearchViewPrivateApi
    public void launchQuerySearch(int i, String str, String str2) {
        try {
            ReflectionUtils.invokeVoidMethod(SearchView.class, this.searchView, "launchQuerySearch", new Class[]{Integer.class, String.class, String.class}, Integer.valueOf(i), str, str2);
        } catch (Exception e) {
            GDSearchView.logMethodAccessException("launchQuerySearch(..)");
        }
    }

    @Override // com.good.gd.widget.impl.SearchViewPrivateApi
    public void launchSuggestion(int i, int i2, String str) {
        try {
            ReflectionUtils.invokeVoidMethod(SearchView.class, this.searchView, "launchSuggestion", new Class[]{Integer.class, Integer.class, String.class}, Integer.valueOf(i), Integer.valueOf(i2), str);
        } catch (Exception e) {
            GDSearchView.logMethodAccessException("launchSuggestion(..)");
        }
    }

    @Override // com.good.gd.widget.impl.SearchViewPrivateApi
    public void onVoiceClicked() {
        try {
            ReflectionUtils.invokeVoidMethod(SearchView.class, this.searchView, "onVoiceClicked", null, new Object[0]);
        } catch (Exception e) {
            GDSearchView.logMethodAccessException("onVoiceClicked()");
        }
    }

    @Override // com.good.gd.widget.impl.SearchViewPrivateApi
    public void setCollapsedImeOption(int i) {
        try {
            ReflectionUtils.setFieldValue(SearchView.class, this.searchView, "mCollapsedImeOptions", Integer.valueOf(i));
        } catch (Exception e) {
            GDSearchView.logFieldAccessException("mCollapsedImeOptions");
        }
    }

    @Override // com.good.gd.widget.impl.SearchViewPrivateApi
    public void setExpandedInActionView(boolean z) {
        try {
            ReflectionUtils.setFieldValue(SearchView.class, this.searchView, "mExpandedInActionView", Boolean.valueOf(z));
        } catch (Exception e) {
            GDSearchView.logFieldAccessException("mExpandedInActionView");
        }
    }

    @Override // com.good.gd.widget.impl.SearchViewPrivateApi
    public void setIconifiedByDefaultField(boolean z) {
        try {
            ReflectionUtils.setFieldValue(SearchView.class, this.searchView, "mIconifiedByDefault", Boolean.valueOf(z));
        } catch (Exception e) {
            GDSearchView.logFieldAccessException("mIconifiedByDefault");
        }
    }

    @Override // com.good.gd.widget.impl.SearchViewPrivateApi
    public void setIconifiedField(boolean z) {
        try {
            ReflectionUtils.setFieldValue(SearchView.class, this.searchView, "mIconified", Boolean.valueOf(z));
        } catch (Exception e) {
            GDSearchView.logFieldAccessException("mIconified");
        }
    }

    @Override // com.good.gd.widget.impl.SearchViewPrivateApi
    public void setOldQueryText(CharSequence charSequence) {
        try {
            ReflectionUtils.setFieldValue(SearchView.class, this.searchView, "mOldQueryText", charSequence);
        } catch (Exception e) {
            GDSearchView.logFieldAccessException("mOldQueryText");
        }
    }

    @Override // com.good.gd.widget.impl.SearchViewPrivateApi
    public void setPrivateSuggestionsAdapter(CursorAdapter cursorAdapter) {
        try {
            ReflectionUtils.setFieldValue(SearchView.class, this.searchView, "mSuggestionsAdapter", cursorAdapter);
        } catch (Exception e) {
            GDSearchView.logFieldAccessException("mSuggestionsAdapter");
        }
    }

    @Override // com.good.gd.widget.impl.SearchViewPrivateApi
    public void setSubmitButtonState(boolean z) {
        try {
            ReflectionUtils.setFieldValue(SearchView.class, this.searchView, "mSubmitButtonEnabled", Boolean.valueOf(z));
        } catch (Exception e) {
            GDSearchView.logFieldAccessException("mSubmitButtonEnabled");
        }
    }

    @Override // com.good.gd.widget.impl.SearchViewPrivateApi
    public void setUserQueryField(CharSequence charSequence) {
        try {
            ReflectionUtils.setFieldValue(SearchView.class, this.searchView, "mUserQuery", charSequence);
        } catch (Exception e) {
            GDSearchView.logFieldAccessException("mUserQuery");
        }
    }

    @Override // com.good.gd.widget.impl.SearchViewPrivateApi
    public void setVoiceButtonEnabled(boolean z) {
        try {
            ReflectionUtils.setFieldValue(SearchView.class, this.searchView, "mVoiceButtonEnabled", Boolean.valueOf(z));
        } catch (Exception e) {
            GDSearchView.logFieldAccessException("mVoiceButtonEnabled");
        }
    }

    @Override // com.good.gd.widget.impl.SearchViewPrivateApi
    public void updateSubmitArea() {
        try {
            ReflectionUtils.invokeVoidMethod(SearchView.class, this.searchView, "updateSubmitArea", null, new Object[0]);
        } catch (Exception e) {
            GDSearchView.logMethodAccessException("updateSubmitArea");
        }
    }

    @Override // com.good.gd.widget.impl.SearchViewPrivateApi
    public void updateSubmitButton(boolean z) {
        try {
            ReflectionUtils.invokeVoidMethod(SearchView.class, this.searchView, "updateSubmitButton", new Class[]{Boolean.TYPE}, Boolean.valueOf(z));
        } catch (Exception e) {
            GDSearchView.logMethodAccessException("updateSubmitButton(..)");
        }
    }

    @Override // com.good.gd.widget.impl.SearchViewPrivateApi
    public void updateVoiceButton(boolean z) {
        try {
            ReflectionUtils.invokeVoidMethod(SearchView.class, this.searchView, "updateVoiceButton", new Class[]{Boolean.TYPE}, Boolean.valueOf(z));
        } catch (Exception e) {
            GDSearchView.logMethodAccessException("updateVoiceButton");
        }
    }
}
