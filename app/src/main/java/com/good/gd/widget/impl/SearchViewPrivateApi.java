package com.good.gd.widget.impl;

import android.app.SearchableInfo;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.SearchView;
import java.util.WeakHashMap;

/* loaded from: classes.dex */
public interface SearchViewPrivateApi {
    ImageView getCloseButton();

    int getCollapsedImeOption();

    boolean getExpandedInActionView();

    CharSequence getOldQueryText();

    SearchView.OnSuggestionListener getOnSuggestionListener();

    WeakHashMap<String, Drawable.ConstantState> getOutsideDrawablesCache();

    CursorAdapter getPrivateSuggestionsAdapter();

    Runnable getReleaseCursorRunnable();

    View getSearchButtonField();

    View getSearchEditFrame();

    SearchableInfo getSearchable();

    View getSubmitArea();

    View getSubmitButton();

    Intent getVoiceAppSearchIntent();

    View getVoiceButton();

    Intent getVoiceWebSearchIntent();

    void hideMagnifierIcon();

    boolean isIconified();

    void launchQuerySearch(int i, String str, String str2);

    void launchSuggestion(int i, int i2, String str);

    void onVoiceClicked();

    void setCollapsedImeOption(int i);

    void setExpandedInActionView(boolean z);

    void setIconifiedByDefaultField(boolean z);

    void setIconifiedField(boolean z);

    void setOldQueryText(CharSequence charSequence);

    void setPrivateSuggestionsAdapter(CursorAdapter cursorAdapter);

    void setSubmitButtonState(boolean z);

    void setUserQueryField(CharSequence charSequence);

    void setVoiceButtonEnabled(boolean z);

    void updateSubmitArea();

    void updateSubmitButton(boolean z);

    void updateVoiceButton(boolean z);
}
