package com.good.gd.widget;

import android.app.PendingIntent;
import android.app.SearchableInfo;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.autofill.AutofillValue;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import com.good.gd.adapter.SuggestionAdapterApi;
import com.good.gd.database.sqlite.SQLiteDatabase;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.utils.ReflectionUtils;
import com.good.gd.widget.impl.AdapterAndroidQ;
import com.good.gd.widget.impl.AdapterBelowAndroidQ;
import com.good.gd.widget.impl.SearchViewBelowAndroidP;
import com.good.gd.widget.impl.SearchViewPrivateApi;
import com.google.android.gms.actions.SearchIntents;
import java.util.WeakHashMap;

/* loaded from: classes.dex */
public class GDSearchView extends SearchView {
    private static final int DROP_DOWN_ITEM_ICON_WIDTH = 32;
    private static final int DROP_DOWN_TEXT_PADDING_LEFT = 8;
    private static final String IME_OPTION_NO_MICROPHONE = "nm";
    private static final int NO_VALUE = -1;
    private static final String TAG = GDSearchView.class.getSimpleName();
    private final GDSearchAutoCompleteTextView gdSearchAutoCompleteTextView;
    private Bundle mAppSearchData;
    private int mCollapsedImeOptions;
    private boolean mExpandedInActionView;
    private boolean mIconifiedByDefault;
    private CharSequence mOldQueryText;
    private OnCloseListener mOnCloseListener;
    private OnQueryTextListener mOnQueryChangeListener;
    private OnClickListener mOnSearchClickListener;
    private OnSuggestionListener mOnSuggestionListener;
    private SearchableInfo mSearchable;
    private boolean mSubmitButtonEnabled;
    private SuggestionAdapterApi mSuggestionAdapterPrivateApi;
    private CursorAdapter mSuggestionsAdapter;
    private CharSequence mUserQuery;
    private Intent mVoiceAppSearchIntent;
    private boolean mVoiceButtonEnabled;
    private Intent mVoiceWebSearchIntent;
    private SearchViewPrivateApi searchViewPrivateApi;
    private int gdPendingIMEOptions = -1;
    private int gdPendingInputType = -1;
    private final WeakHashMap<String, Drawable.ConstantState> mOutsideDrawablesCache = new WeakHashMap<>();
    private final Runnable updateDrawableStateRunnable = new hbfhc();
    private final AdapterView.OnItemSelectedListener onItemSelectedListener = new yfdke();
    private final OnClickListener mOnClickListener = new fdyxd();
    private final TextView.OnEditorActionListener onEditorActionListener = new ehnkx();
    private final AdapterView.OnItemClickListener onItemClickListener = new pmoiy();
    private final TextWatcher textWatcher = new mjbm();
    private final OnKeyListener textKeyListener = new aqdzk();

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class GDSearchAutoCompleteTextView extends GDAutoCompleteTextView {
        private int dbjc;
        private GDSearchView qkduk;

        /* synthetic */ GDSearchAutoCompleteTextView(Context context, AttributeSet attributeSet, int i, hbfhc hbfhcVar) {
            this(context, attributeSet, i);
        }

        static /* synthetic */ void qkduk(GDSearchAutoCompleteTextView gDSearchAutoCompleteTextView) {
            if (gDSearchAutoCompleteTextView != null) {
                try {
                    ReflectionUtils.invokeVoidMethod(AutoCompleteTextView.class, gDSearchAutoCompleteTextView, "doBeforeTextChanged", null, new Object[0]);
                    return;
                } catch (Exception e) {
                    GDSearchView.logMethodAccessException("doBeforeTextChanged()");
                    return;
                }
            }
            throw null;
        }

        @Override // android.widget.AutoCompleteTextView
        public boolean enoughToFilter() {
            return this.dbjc <= 0 || super.enoughToFilter();
        }

        @Override // android.widget.AutoCompleteTextView, android.widget.TextView, android.view.View
        protected void onFocusChanged(boolean z, int i, Rect rect) {
            super.onFocusChanged(z, i, rect);
            this.qkduk.onTextFocusChanged();
        }

        @Override // android.widget.AutoCompleteTextView, android.widget.TextView, android.view.View
        public boolean onKeyPreIme(int i, KeyEvent keyEvent) {
            if (i == 4) {
                if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                    KeyEvent.DispatcherState keyDispatcherState = getKeyDispatcherState();
                    if (keyDispatcherState != null) {
                        keyDispatcherState.startTracking(keyEvent, this);
                    }
                    return true;
                } else if (keyEvent.getAction() == 1) {
                    KeyEvent.DispatcherState keyDispatcherState2 = getKeyDispatcherState();
                    if (keyDispatcherState2 != null) {
                        keyDispatcherState2.handleUpEvent(keyEvent);
                    }
                    if (keyEvent.isTracking() && !keyEvent.isCanceled()) {
                        this.qkduk.clearFocus();
                        this.qkduk.setImeVisibility(false);
                        return true;
                    }
                }
            }
            return super.onKeyPreIme(i, keyEvent);
        }

        @Override // android.widget.AutoCompleteTextView, android.widget.TextView, android.view.View
        public void onWindowFocusChanged(boolean z) {
            super.onWindowFocusChanged(z);
            if (!z || !this.qkduk.hasFocus() || getVisibility() != 0) {
                return;
            }
            boolean z2 = false;
            ((InputMethodManager) getContext().getSystemService("input_method")).showSoftInput(this, 0);
            if (getContext().getResources().getConfiguration().orientation == 2) {
                z2 = true;
            }
            if (!z2) {
                return;
            }
            dbjc(true);
        }

        @Override // android.widget.AutoCompleteTextView
        public void performCompletion() {
        }

        @Override // android.widget.AutoCompleteTextView
        protected void replaceText(CharSequence charSequence) {
        }

        @Override // android.widget.AutoCompleteTextView
        public void setThreshold(int i) {
            super.setThreshold(i);
            this.dbjc = i;
        }

        /* synthetic */ GDSearchAutoCompleteTextView(Context context, AttributeSet attributeSet, hbfhc hbfhcVar) {
            this(context, attributeSet);
        }

        /* synthetic */ GDSearchAutoCompleteTextView(Context context, hbfhc hbfhcVar) {
            this(context);
        }

        static /* synthetic */ boolean dbjc(GDSearchAutoCompleteTextView gDSearchAutoCompleteTextView) {
            return TextUtils.getTrimmedLength(gDSearchAutoCompleteTextView.getText()) == 0;
        }

        private GDSearchAutoCompleteTextView(Context context) {
            super(context);
            this.dbjc = getThreshold();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dbjc(boolean z) {
            try {
                ReflectionUtils.invokeVoidMethod(AutoCompleteTextView.class, this, "ensureImeVisible", new Class[]{Boolean.TYPE}, Boolean.valueOf(z));
            } catch (Exception e) {
                GDSearchView.logMethodAccessException("ensureImeVisible(..)");
            }
        }

        private GDSearchAutoCompleteTextView(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.dbjc = getThreshold();
        }

        private GDSearchAutoCompleteTextView(Context context, AttributeSet attributeSet, int i) {
            super(context, attributeSet, i);
            this.dbjc = getThreshold();
        }

        public GDSearchAutoCompleteTextView(Context context, AttributeSet attributeSet, int i, int i2) {
            super(context, attributeSet, i, i2);
            this.dbjc = getThreshold();
        }

        static /* synthetic */ void dbjc(GDSearchAutoCompleteTextView gDSearchAutoCompleteTextView, int i) {
            if (gDSearchAutoCompleteTextView != null) {
                try {
                    ReflectionUtils.invokeVoidMethod(AutoCompleteTextView.class, gDSearchAutoCompleteTextView, "setDropDownAnimationStyle", new Class[]{Integer.class}, Integer.valueOf(i));
                    return;
                } catch (Exception e) {
                    GDSearchView.logMethodAccessException("setDropDownAnimationStyle(..)");
                    return;
                }
            }
            throw null;
        }
    }

    /* loaded from: classes.dex */
    class aqdzk implements OnKeyListener {
        aqdzk() {
        }

        @Override // android.view.View.OnKeyListener
        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            Object findActionKey;
            if (GDSearchView.this.getSearchable() == null) {
                return false;
            }
            if (!GDSearchView.this.gdSearchAutoCompleteTextView.isPopupShowing() || GDSearchView.this.gdSearchAutoCompleteTextView.getListSelection() == -1) {
                if (!GDSearchAutoCompleteTextView.dbjc(GDSearchView.this.gdSearchAutoCompleteTextView) && keyEvent.hasNoModifiers()) {
                    if (keyEvent.getAction() == 1 && (i == 66 || i == 160)) {
                        view.cancelLongPress();
                        GDSearchView gDSearchView = GDSearchView.this;
                        gDSearchView.launchQuerySearch(0, null, gDSearchView.gdSearchAutoCompleteTextView.getText().toString());
                        return true;
                    } else if (keyEvent.getAction() == 0 && (findActionKey = GDSearchView.this.mSuggestionAdapterPrivateApi.findActionKey(GDSearchView.this.getSearchable(), i)) != null && GDSearchView.this.mSuggestionAdapterPrivateApi.getQueryActionMsg(findActionKey) != null) {
                        GDSearchView gDSearchView2 = GDSearchView.this;
                        gDSearchView2.launchQuerySearch(i, gDSearchView2.mSuggestionAdapterPrivateApi.getQueryActionMsg(findActionKey), GDSearchView.this.gdSearchAutoCompleteTextView.getText().toString());
                        return true;
                    }
                }
                return false;
            }
            return GDSearchView.this.onSuggestionsKey(view, i, keyEvent);
        }
    }

    /* loaded from: classes.dex */
    class ehnkx implements TextView.OnEditorActionListener {
        ehnkx() {
        }

        @Override // android.widget.TextView.OnEditorActionListener
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            GDSearchView.this.onSubmitQuery();
            return true;
        }
    }

    /* loaded from: classes.dex */
    class fdyxd implements OnClickListener {
        fdyxd() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (view == GDSearchView.this.getSearchButtonField()) {
                GDSearchView.this.onSearchClicked();
            } else if (view == GDSearchView.this.getCloseButton()) {
                GDSearchView.this.onCloseClicked();
            } else if (view == GDSearchView.this.getSubmitButton()) {
                GDSearchView.this.onSubmitQuery();
            } else if (view == GDSearchView.this.getVoiceButton()) {
                GDSearchView.this.onVoiceClicked();
            } else if (view != GDSearchView.this.gdSearchAutoCompleteTextView) {
            } else {
                GDSearchView.this.forceSuggestionQuery();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class hbfhc implements Runnable {
        hbfhc() {
        }

        @Override // java.lang.Runnable
        public void run() {
            GDSearchView.this.updateFocusedState();
        }
    }

    /* loaded from: classes.dex */
    class mjbm implements TextWatcher {
        mjbm() {
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
        }

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            GDSearchView.this.onTextChanged(charSequence);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class orlrx implements OnLayoutChangeListener {
        orlrx() {
        }

        @Override // android.view.View.OnLayoutChangeListener
        public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            GDSearchView.this.adjustDropDownSizeAndPosition();
        }
    }

    /* loaded from: classes.dex */
    class pmoiy implements AdapterView.OnItemClickListener {
        pmoiy() {
        }

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            GDSearchView.this.onItemClicked(i, 0, null);
        }
    }

    /* loaded from: classes.dex */
    class yfdke implements AdapterView.OnItemSelectedListener {
        yfdke() {
        }

        @Override // android.widget.AdapterView.OnItemSelectedListener
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
            GDSearchView.this.onItemSelected(i);
        }

        @Override // android.widget.AdapterView.OnItemSelectedListener
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    public GDSearchView(Context context) {
        super(context);
        this.gdSearchAutoCompleteTextView = new GDSearchAutoCompleteTextView(context, (hbfhc) null);
        GDViewSetup();
    }

    private void GDViewSetup() {
        this.mSuggestionAdapterPrivateApi = createSuggestionAdapterIml();
        this.gdSearchAutoCompleteTextView.qkduk = this;
        Intent intent = new Intent("android.speech.action.WEB_SEARCH");
        this.mVoiceWebSearchIntent = intent;
        intent.addFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
        this.mVoiceWebSearchIntent.putExtra("android.speech.extra.LANGUAGE_MODEL", "web_search");
        Intent intent2 = new Intent("android.speech.action.RECOGNIZE_SPEECH");
        this.mVoiceAppSearchIntent = intent2;
        intent2.addFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
        replaceAutoCompleteTextView();
        checkAndSetXMLValues();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void adjustDropDownSizeAndPosition() {
        int i;
        View dropDownAnchor = getDropDownAnchor();
        if (dropDownAnchor.getWidth() > 1) {
            int i2 = 0;
            int paddingLeft = getSearchPlate() != null ? getSearchPlate().getPaddingLeft() : 0;
            Rect rect = new Rect();
            boolean isLayoutRtl = isLayoutRtl();
            if (getIconifiedByDefaultField()) {
                i2 = dpToPixels(32) + dpToPixels(8);
            }
            this.gdSearchAutoCompleteTextView.getDropDownBackground().getPadding(rect);
            if (isLayoutRtl) {
                i = -rect.left;
            } else {
                i = paddingLeft - (rect.left + i2);
            }
            this.gdSearchAutoCompleteTextView.setDropDownHorizontalOffset(i);
            this.gdSearchAutoCompleteTextView.setDropDownWidth((((dropDownAnchor.getWidth() + rect.left) + rect.right) + i2) - paddingLeft);
        }
    }

    private void checkAndSetXMLValues() {
        int i = this.gdPendingIMEOptions;
        if (i != -1) {
            setImeOptions(i);
        }
        int i2 = this.gdPendingInputType;
        if (i2 != -1) {
            setInputType(i2);
        }
    }

    private SuggestionAdapterApi createSuggestionAdapterIml() {
        if (Build.VERSION.SDK_INT > 28) {
            return new AdapterAndroidQ();
        }
        return new AdapterBelowAndroidQ();
    }

    private void dismissSuggestions() {
        this.gdSearchAutoCompleteTextView.dismissDropDown();
    }

    private int dpToPixels(int i) {
        return (int) (i / getContext().getResources().getDisplayMetrics().density);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void forceSuggestionQuery() {
        GDSearchAutoCompleteTextView.qkduk(this.gdSearchAutoCompleteTextView);
        GDSearchAutoCompleteTextView gDSearchAutoCompleteTextView = this.gdSearchAutoCompleteTextView;
        if (gDSearchAutoCompleteTextView != null) {
            try {
                ReflectionUtils.invokeVoidMethod(AutoCompleteTextView.class, gDSearchAutoCompleteTextView, "doAfterTextChanged", null, new Object[0]);
                return;
            } catch (Exception e) {
                logMethodAccessException("doAfterTextChanged()");
                return;
            }
        }
        throw null;
    }

    private String getActionKeyMessage(Cursor cursor, Object obj) {
        String str;
        String suggestActionMsgColumn = this.mSuggestionAdapterPrivateApi.getSuggestActionMsgColumn(obj);
        if (suggestActionMsgColumn == null) {
            str = null;
        } else {
            str = this.mSuggestionAdapterPrivateApi.getColumnString(cursor, suggestActionMsgColumn);
        }
        return str == null ? this.mSuggestionAdapterPrivateApi.getSuggestActionMsg(obj) : str;
    }

    private boolean getClearingFocus() {
        try {
            return ((Boolean) ReflectionUtils.getFieldValue(SearchView.class, this, "mClearingFocus")).booleanValue();
        } catch (Exception e) {
            logFieldAccessException("mClearingFocus");
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ImageView getCloseButton() {
        return getSearchViewPrivateApi().getCloseButton();
    }

    private int getCollapsedImeOption() {
        return getSearchViewPrivateApi().getCollapsedImeOption();
    }

    private CharSequence getDecoratedHint(CharSequence charSequence) {
        if (!getIconifiedByDefaultField()) {
            return charSequence;
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("   ");
        spannableStringBuilder.append(charSequence);
        int textSize = (int) (this.gdSearchAutoCompleteTextView.getTextSize() * 1.25d);
        Drawable searchHintIcon = getSearchHintIcon();
        if (searchHintIcon != null) {
            searchHintIcon.setBounds(0, 0, textSize, textSize);
            spannableStringBuilder.setSpan(new ImageSpan(searchHintIcon), 1, 2, 33);
        }
        return spannableStringBuilder;
    }

    private View getDropDownAnchor() {
        return findViewById(this.gdSearchAutoCompleteTextView.getDropDownAnchor());
    }

    private boolean getExpandedInActionView() {
        return getSearchViewPrivateApi().getExpandedInActionView();
    }

    private boolean getIconifiedByDefaultField() {
        return isIconfiedByDefault();
    }

    private CharSequence getOldQueryText() {
        return getSearchViewPrivateApi().getOldQueryText();
    }

    private OnCloseListener getOnCloseListener() {
        return this.mOnCloseListener;
    }

    private OnQueryTextListener getOnQueryTextListener() {
        return this.mOnQueryChangeListener;
    }

    private OnClickListener getOnSearchClickListener() {
        return this.mOnSearchClickListener;
    }

    private OnSuggestionListener getOnSuggestionListener() {
        return getSearchViewPrivateApi().getOnSuggestionListener();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AutoCompleteTextView getOriginalAutoCompleteTextView() {
        try {
            return (AutoCompleteTextView) ReflectionUtils.getFieldValue(SearchView.class, this, "mSearchSrcTextView");
        } catch (Exception e) {
            logFieldAccessException("mSearchSrcTextView");
            return null;
        }
    }

    private WeakHashMap<String, Drawable.ConstantState> getOutsideDrawablesCache() {
        return getSearchViewPrivateApi().getOutsideDrawablesCache();
    }

    private CursorAdapter getPrivateSuggestionsAdapter() {
        return getSearchViewPrivateApi().getPrivateSuggestionsAdapter();
    }

    private Runnable getReleaseCursorRunnable() {
        return getSearchViewPrivateApi().getReleaseCursorRunnable();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public View getSearchButtonField() {
        return getSearchViewPrivateApi().getSearchButtonField();
    }

    private View getSearchEditFrame() {
        return getSearchViewPrivateApi().getSearchEditFrame();
    }

    private Drawable getSearchHintIcon() {
        return getSearchHintIconDrawable();
    }

    private Drawable getSearchHintIconDrawable() {
        try {
            return (Drawable) ReflectionUtils.getFieldValue(SearchView.class, this, "mSearchHintIcon");
        } catch (Exception e) {
            logFieldAccessException("mSearchHintIcon");
            return null;
        }
    }

    private View getSearchPlate() {
        try {
            return (View) ReflectionUtils.getFieldValue(SearchView.class, this, "mSearchPlate");
        } catch (Exception e) {
            logFieldAccessException("mSearchPlate");
            return null;
        }
    }

    private SearchViewPrivateApi getSearchViewPrivateApi() {
        if (this.searchViewPrivateApi == null) {
            this.searchViewPrivateApi = ReflectionUtils.canUseReflectionInAndroidPorLater() ? new SearchViewBelowAndroidP(this) : new gioey(this, null);
        }
        return this.searchViewPrivateApi;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public SearchableInfo getSearchable() {
        return getSearchViewPrivateApi().getSearchable();
    }

    private View getSubmitArea() {
        return getSearchViewPrivateApi().getSubmitArea();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public View getSubmitButton() {
        return getSearchViewPrivateApi().getSubmitButton();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public View getViewFieldByIdName(String str) {
        try {
            return findViewById(Resources.getSystem().getIdentifier(str, "id", "android"));
        } catch (Exception e) {
            GDLog.DBGPRINTF(13, "getViewFieldByIdName " + str);
            return null;
        }
    }

    private Intent getVoiceAppSearchIntent() {
        return getSearchViewPrivateApi().getVoiceAppSearchIntent();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public View getVoiceButton() {
        return getSearchViewPrivateApi().getVoiceButton();
    }

    private Intent getVoiceWebSearchIntent() {
        return getSearchViewPrivateApi().getVoiceWebSearchIntent();
    }

    private boolean hasVoiceSearch() {
        SearchableInfo searchable = getSearchable();
        if (searchable == null || !searchable.getVoiceSearchEnabled()) {
            return false;
        }
        Intent intent = null;
        if (searchable.getVoiceSearchLaunchWebSearch()) {
            intent = getVoiceWebSearchIntent();
        } else if (this.mSearchable.getVoiceSearchLaunchRecognizer()) {
            intent = getVoiceAppSearchIntent();
        }
        return (intent == null || getContext().getPackageManager().resolveActivity(intent, 65536) == null) ? false : true;
    }

    private void initGdSearchAutoCompleteView(AutoCompleteTextView autoCompleteTextView, GDSearchAutoCompleteTextView gDSearchAutoCompleteTextView) {
        gDSearchAutoCompleteTextView.setId(autoCompleteTextView.getId());
        gDSearchAutoCompleteTextView.setOnFocusChangeListener(autoCompleteTextView.getOnFocusChangeListener());
        gDSearchAutoCompleteTextView.setLayoutParams(autoCompleteTextView.getLayoutParams());
        gDSearchAutoCompleteTextView.setPadding(autoCompleteTextView.getPaddingLeft(), autoCompleteTextView.getPaddingTop(), autoCompleteTextView.getPaddingRight(), autoCompleteTextView.getPaddingBottom());
        gDSearchAutoCompleteTextView.setSingleLine(true);
        gDSearchAutoCompleteTextView.setEllipsize(autoCompleteTextView.getEllipsize());
        gDSearchAutoCompleteTextView.setInputType(autoCompleteTextView.getInputType());
        gDSearchAutoCompleteTextView.setImeOptions(autoCompleteTextView.getImeOptions());
        gDSearchAutoCompleteTextView.setDropDownHeight(autoCompleteTextView.getDropDownHeight());
        gDSearchAutoCompleteTextView.setDropDownAnchor(autoCompleteTextView.getDropDownAnchor());
        gDSearchAutoCompleteTextView.setDropDownVerticalOffset(autoCompleteTextView.getDropDownVerticalOffset());
        gDSearchAutoCompleteTextView.setDropDownHorizontalOffset(autoCompleteTextView.getDropDownHorizontalOffset());
        gDSearchAutoCompleteTextView.setContentDescription(autoCompleteTextView.getContentDescription());
        gDSearchAutoCompleteTextView.setBackgroundColor(getResources().getColor(17170445));
        setIconifiedByDefault(true);
        if (getSearchButtonField() != null) {
            getSearchButtonField().setOnClickListener(this.mOnClickListener);
        }
        if (getCloseButton() != null) {
            getCloseButton().setOnClickListener(this.mOnClickListener);
        }
        if (getSubmitButton() != null) {
            getSubmitButton().setOnClickListener(this.mOnClickListener);
        }
        if (getVoiceButton() != null) {
            getVoiceButton().setOnClickListener(this.mOnClickListener);
        }
        this.gdSearchAutoCompleteTextView.setOnClickListener(this.mOnClickListener);
        this.gdSearchAutoCompleteTextView.setOnKeyListener(this.textKeyListener);
        this.gdSearchAutoCompleteTextView.setOnItemClickListener(this.onItemClickListener);
        this.gdSearchAutoCompleteTextView.addTextChangedListener(this.textWatcher);
        this.gdSearchAutoCompleteTextView.setOnEditorActionListener(this.onEditorActionListener);
        this.gdSearchAutoCompleteTextView.setOnItemSelectedListener(this.onItemSelectedListener);
        View dropDownAnchor = getDropDownAnchor();
        if (dropDownAnchor != null) {
            dropDownAnchor.addOnLayoutChangeListener(new orlrx());
        }
    }

    private boolean isLayoutRtl() {
        return getLayoutDirection() == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isSubmitAreaEnabled() {
        return (this.mSubmitButtonEnabled || this.mVoiceButtonEnabled) && !isIconified();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void launchQuerySearch(int i, String str, String str2) {
        getSearchViewPrivateApi().launchQuerySearch(i, str, str2);
    }

    private void launchSuggestion(int i, int i2, String str) {
        getSearchViewPrivateApi().launchSuggestion(i, i2, str);
    }

    public static void logClassAccessException(String str) {
        GDLog.DBGPRINTF(16, TAG + ": Can't access " + str + " class. Make sure that class exists ");
    }

    public static void logClassInstantiationException(String str) {
        GDLog.DBGPRINTF(16, TAG + ": Can't access " + str + " class. Make sure that class exists and constructor name and parameters are valid.");
    }

    public static void logFieldAccessException(String str) {
        GDLog.DBGPRINTF(16, TAG + ": Can't access " + str + " field. Make sure that field exists and given type and actual field types are the same");
    }

    public static void logMethodAccessException(String str) {
        GDLog.DBGPRINTF(16, TAG + ": Can't access " + str + " method. Make sure that method name exists and parameter and return types are valid.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onCloseClicked() {
        if (TextUtils.isEmpty(this.gdSearchAutoCompleteTextView.getText())) {
            if (!getIconifiedByDefaultField()) {
                return;
            }
            if (getOnCloseListener() != null && getOnCloseListener().onClose()) {
                return;
            }
            clearFocus();
            updateViewsVisibility(true);
            return;
        }
        this.gdSearchAutoCompleteTextView.setText("");
        this.gdSearchAutoCompleteTextView.requestFocus();
        setImeVisibility(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean onItemClicked(int i, int i2, String str) {
        OnSuggestionListener onSuggestionListener = getOnSuggestionListener();
        if (onSuggestionListener == null || !onSuggestionListener.onSuggestionClick(i)) {
            launchSuggestion(i, 0, null);
            setImeVisibility(false);
            dismissSuggestions();
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean onItemSelected(int i) {
        if (getOnSuggestionListener() == null || !getOnSuggestionListener().onSuggestionSelect(i)) {
            rewriteQueryFromSuggestion(i);
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSearchClicked() {
        updateViewsVisibility(false);
        this.gdSearchAutoCompleteTextView.requestFocus();
        setImeVisibility(true);
        if (getOnSearchClickListener() != null) {
            getOnSearchClickListener().onClick(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSubmitQuery() {
        Editable text = this.gdSearchAutoCompleteTextView.getText();
        if (text == null || TextUtils.getTrimmedLength(text) <= 0) {
            return;
        }
        if (getOnQueryTextListener() != null && getOnQueryTextListener().onQueryTextSubmit(text.toString())) {
            return;
        }
        if (getSearchable() != null) {
            launchQuerySearch(0, null, text.toString());
            setImeVisibility(false);
        }
        dismissSuggestions();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean onSuggestionsKey(View view, int i, KeyEvent keyEvent) {
        Object findActionKey;
        int listSelection;
        Cursor cursor;
        String actionKeyMessage;
        if (getSearchable() != null && getSuggestionsAdapter() != null && keyEvent.getAction() == 0 && keyEvent.hasNoModifiers()) {
            if (i == 66 || i == 160 || i == 84 || i == 61) {
                return onItemClicked(this.gdSearchAutoCompleteTextView.getListSelection(), 0, null);
            }
            if (i != 21 && i != 22) {
                if ((i != 19 || this.gdSearchAutoCompleteTextView.getListSelection() != 0) && (findActionKey = this.mSuggestionAdapterPrivateApi.findActionKey(getSearchable(), i)) != null && ((this.mSuggestionAdapterPrivateApi.getSuggestActionMsg(findActionKey) != null || this.mSuggestionAdapterPrivateApi.getSuggestActionMsgColumn(findActionKey) != null) && (listSelection = this.gdSearchAutoCompleteTextView.getListSelection()) != -1 && (cursor = getPrivateSuggestionsAdapter().getCursor()) != null && cursor.moveToPosition(listSelection) && (actionKeyMessage = getActionKeyMessage(cursor, findActionKey)) != null && actionKeyMessage.length() > 0)) {
                    return onItemClicked(listSelection, i, actionKeyMessage);
                }
            } else {
                this.gdSearchAutoCompleteTextView.setSelection(i == 21 ? 0 : this.gdSearchAutoCompleteTextView.length());
                this.gdSearchAutoCompleteTextView.setListSelection(0);
                this.gdSearchAutoCompleteTextView.clearListSelection();
                this.gdSearchAutoCompleteTextView.dbjc(true);
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTextChanged(CharSequence charSequence) {
        Editable text = this.gdSearchAutoCompleteTextView.getText();
        setUserQueryField(text);
        boolean z = !TextUtils.isEmpty(text);
        updateSubmitButton(z);
        updateVoiceButton(!z);
        updateCloseButton();
        updateSubmitArea();
        if (getOnQueryTextListener() != null && !TextUtils.equals(charSequence, getOldQueryText())) {
            getOnQueryTextListener().onQueryTextChange(charSequence.toString());
        }
        setOldQueryText(charSequence.toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTextFocusChanged() {
        updateViewsVisibility(isIconified());
        postUpdateFocusedState();
        if (this.gdSearchAutoCompleteTextView.hasFocus()) {
            forceSuggestionQuery();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onVoiceClicked() {
        getSearchViewPrivateApi().onVoiceClicked();
    }

    private void postUpdateFocusedState() {
        post(this.updateDrawableStateRunnable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releaseCursor() {
        this.mSuggestionAdapterPrivateApi.releaseCursor(this.mSuggestionsAdapter);
    }

    private void replaceAutoCompleteTextView() {
        AutoCompleteTextView originalAutoCompleteTextView = getOriginalAutoCompleteTextView();
        if (originalAutoCompleteTextView == null) {
            return;
        }
        originalAutoCompleteTextView.setVisibility(8);
        ViewGroup viewGroup = (ViewGroup) originalAutoCompleteTextView.getParent();
        int indexOfChild = viewGroup.indexOfChild(originalAutoCompleteTextView);
        viewGroup.removeView(originalAutoCompleteTextView);
        removeView(originalAutoCompleteTextView);
        initGdSearchAutoCompleteView(originalAutoCompleteTextView, this.gdSearchAutoCompleteTextView);
        viewGroup.addView(this.gdSearchAutoCompleteTextView, indexOfChild);
        updateViewsVisibility(getIconifiedByDefaultField());
        updateQueryHint();
    }

    private void rewriteQueryFromSuggestion(int i) {
        Editable text = this.gdSearchAutoCompleteTextView.getText();
        Cursor cursor = getSuggestionsAdapter().getCursor();
        if (cursor == null) {
            return;
        }
        if (cursor.moveToPosition(i)) {
            CharSequence convertToString = getSuggestionsAdapter().convertToString(cursor);
            if (convertToString != null) {
                setQuery(convertToString);
                return;
            } else {
                setQuery(text);
                return;
            }
        }
        setQuery(text);
    }

    private void setClearingFocus(boolean z) {
        try {
            ReflectionUtils.setFieldValue(SearchView.class, this, "mClearingFocus", Boolean.valueOf(z));
        } catch (Exception e) {
            logFieldAccessException("mClearingFocus");
        }
    }

    private void setCollapsedImeOption(int i) {
        getSearchViewPrivateApi().setCollapsedImeOption(i);
    }

    private void setExpandedInActionView(boolean z) {
        getSearchViewPrivateApi().setExpandedInActionView(z);
    }

    private void setIconifiedByDefaultField(boolean z) {
        getSearchViewPrivateApi().setIconifiedByDefaultField(z);
    }

    private void setIconifiedField(boolean z) {
        getSearchViewPrivateApi().setIconifiedField(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setImeVisibility(boolean z) {
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService("input_method");
        if (inputMethodManager == null) {
            return;
        }
        if (z) {
            inputMethodManager.showSoftInput(this.gdSearchAutoCompleteTextView, 0);
        } else {
            inputMethodManager.hideSoftInputFromWindow(getWindowToken(), 0);
        }
    }

    private void setOldQueryText(CharSequence charSequence) {
        getSearchViewPrivateApi().setOldQueryText(charSequence);
    }

    private void setPrivateSuggestionsAdapter(CursorAdapter cursorAdapter) {
        getSearchViewPrivateApi().setPrivateSuggestionsAdapter(cursorAdapter);
    }

    private void setSubmitButtonState(boolean z) {
        getSearchViewPrivateApi().setSubmitButtonState(z);
    }

    private void setUserQueryField(CharSequence charSequence) {
        getSearchViewPrivateApi().setUserQueryField(charSequence);
    }

    private void setVoiceButtonEnabled(boolean z) {
        getSearchViewPrivateApi().setVoiceButtonEnabled(z);
    }

    private void updateCloseButton() {
        ImageView closeButton = getCloseButton();
        if (closeButton == null) {
            return;
        }
        boolean z = true;
        boolean z2 = !TextUtils.isEmpty(this.gdSearchAutoCompleteTextView.getText());
        int i = 0;
        if (!z2 && (!getIconifiedByDefaultField() || getExpandedInActionView())) {
            z = false;
        }
        if (!z) {
            i = 8;
        }
        closeButton.setVisibility(i);
        closeButton.getDrawable().setState(z2 ? SearchView.ENABLED_STATE_SET : SearchView.EMPTY_STATE_SET);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateFocusedState() {
        boolean hasFocus = this.gdSearchAutoCompleteTextView.hasFocus();
        View searchPlate = getSearchPlate();
        if (searchPlate != null) {
            searchPlate.getBackground().setState(hasFocus ? SearchView.FOCUSED_STATE_SET : SearchView.EMPTY_STATE_SET);
        }
        View submitArea = getSubmitArea();
        if (submitArea != null) {
            submitArea.getBackground().setState(hasFocus ? SearchView.FOCUSED_STATE_SET : SearchView.EMPTY_STATE_SET);
        }
        invalidate();
    }

    private void updateQueryHint() {
        if (this.gdSearchAutoCompleteTextView == null) {
            return;
        }
        if (getQueryHint() != null) {
            this.gdSearchAutoCompleteTextView.setHint(getDecoratedHint(getQueryHint()));
        } else if (getSearchable() != null) {
            String str = null;
            int hintId = getSearchable().getHintId();
            if (hintId != 0) {
                str = getContext().getString(hintId);
            }
            if (str == null) {
                return;
            }
            this.gdSearchAutoCompleteTextView.setHint(getDecoratedHint(str));
        } else {
            this.gdSearchAutoCompleteTextView.setHint(getDecoratedHint(""));
        }
    }

    private void updateSearchAutoComplete() {
        int queryRefirementRefineByEntry;
        if (getSearchable() == null) {
            return;
        }
        GDSearchAutoCompleteTextView.dbjc(this.gdSearchAutoCompleteTextView, 0);
        this.gdSearchAutoCompleteTextView.setThreshold(getSearchable().getSuggestThreshold());
        this.gdSearchAutoCompleteTextView.setImeOptions(getSearchable().getImeOptions());
        int inputType = getSearchable().getInputType();
        if ((inputType & 15) == 1) {
            inputType &= -65537;
            if (getSearchable().getSuggestAuthority() != null) {
                inputType = inputType | 65536 | 524288;
            }
        }
        this.gdSearchAutoCompleteTextView.setInputType(inputType);
        if (getSuggestionsAdapter() != null) {
            getSuggestionsAdapter().changeCursor(null);
        }
        if (getSearchable().getSuggestAuthority() == null) {
            return;
        }
        CursorAdapter createSuggestionsAdapter = this.mSuggestionAdapterPrivateApi.createSuggestionsAdapter(getSearchable(), this.mOutsideDrawablesCache, getContext(), this);
        setPrivateSuggestionsAdapter(createSuggestionsAdapter);
        this.gdSearchAutoCompleteTextView.setAdapter(createSuggestionsAdapter);
        SuggestionAdapterApi suggestionAdapterApi = this.mSuggestionAdapterPrivateApi;
        if (isQueryRefinementEnabled()) {
            queryRefirementRefineByEntry = this.mSuggestionAdapterPrivateApi.getQueryRefirementRefineAll(createSuggestionsAdapter);
        } else {
            queryRefirementRefineByEntry = this.mSuggestionAdapterPrivateApi.getQueryRefirementRefineByEntry(createSuggestionsAdapter);
        }
        suggestionAdapterApi.setQueryRefinement(createSuggestionsAdapter, queryRefirementRefineByEntry);
    }

    private void updateSubmitArea() {
        getSearchViewPrivateApi().updateSubmitArea();
    }

    private void updateSubmitButton(boolean z) {
        getSearchViewPrivateApi().updateSubmitButton(z);
    }

    private void updateViewsVisibility(boolean z) {
        if (this.gdSearchAutoCompleteTextView == null) {
            return;
        }
        setIconifiedField(z);
        int i = 0;
        int i2 = z ? 0 : 8;
        boolean z2 = !TextUtils.isEmpty(this.gdSearchAutoCompleteTextView.getText());
        View searchButtonField = getSearchButtonField();
        if (searchButtonField != null) {
            searchButtonField.setVisibility(i2);
        }
        updateSubmitButton(z2);
        View searchEditFrame = getSearchEditFrame();
        if (searchEditFrame != null) {
            if (z) {
                i = 8;
            }
            searchEditFrame.setVisibility(i);
        }
        getSearchViewPrivateApi().hideMagnifierIcon();
        updateCloseButton();
        updateVoiceButton(!z2);
        updateSubmitArea();
    }

    private void updateVoiceButton(boolean z) {
        getSearchViewPrivateApi().updateVoiceButton(z);
    }

    @Override // android.view.View
    public final void autofill(SparseArray<AutofillValue> sparseArray) {
    }

    @Override // android.view.View
    public final void autofill(AutofillValue autofillValue) {
    }

    @Override // android.widget.SearchView, android.view.ViewGroup, android.view.View
    public void clearFocus() {
        setClearingFocus(true);
        setImeVisibility(false);
        super.clearFocus();
        this.gdSearchAutoCompleteTextView.clearFocus();
        setClearingFocus(false);
    }

    @Override // android.view.View
    public final int getAutofillType() {
        return 0;
    }

    @Override // android.view.View
    public final AutofillValue getAutofillValue() {
        return null;
    }

    @Override // android.widget.SearchView
    public int getImeOptions() {
        GDSearchAutoCompleteTextView gDSearchAutoCompleteTextView = this.gdSearchAutoCompleteTextView;
        if (gDSearchAutoCompleteTextView != null) {
            return gDSearchAutoCompleteTextView.getImeOptions();
        }
        return 0;
    }

    @Override // android.view.View
    public final int getImportantForAutofill() {
        return 8;
    }

    @Override // android.widget.SearchView
    public int getInputType() {
        GDSearchAutoCompleteTextView gDSearchAutoCompleteTextView = this.gdSearchAutoCompleteTextView;
        if (gDSearchAutoCompleteTextView != null) {
            return gDSearchAutoCompleteTextView.getInputType();
        }
        return 0;
    }

    @Override // android.widget.SearchView
    public CharSequence getQuery() {
        return this.gdSearchAutoCompleteTextView.getText();
    }

    @Override // android.widget.SearchView
    public boolean isIconified() {
        return getSearchViewPrivateApi().isIconified();
    }

    @Override // android.widget.SearchView, android.view.CollapsibleActionView
    public void onActionViewCollapsed() {
        setQuery("", false);
        clearFocus();
        updateViewsVisibility(true);
        this.gdSearchAutoCompleteTextView.setImeOptions(getCollapsedImeOption());
        setExpandedInActionView(false);
    }

    @Override // android.widget.SearchView, android.view.CollapsibleActionView
    public void onActionViewExpanded() {
        if (getExpandedInActionView()) {
            return;
        }
        setExpandedInActionView(true);
        setCollapsedImeOption(this.gdSearchAutoCompleteTextView.getImeOptions());
        this.gdSearchAutoCompleteTextView.setImeOptions(getCollapsedImeOption() | 33554432);
        this.gdSearchAutoCompleteTextView.setText("");
        setIconified(false);
    }

    @Override // android.view.View
    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        return GDDLPKeyboardControl.getInstance().configureInputConnection(super.onCreateInputConnection(editorInfo), getContext());
    }

    @Override // android.widget.SearchView, android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        removeCallbacks(this.updateDrawableStateRunnable);
        Runnable releaseCursorRunnable = getReleaseCursorRunnable();
        if (releaseCursorRunnable != null) {
            post(releaseCursorRunnable);
        }
        super.onDetachedFromWindow();
    }

    @Override // android.widget.SearchView, android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (getSearchable() == null) {
            return false;
        }
        Object findActionKey = this.mSuggestionAdapterPrivateApi.findActionKey(getSearchable(), i);
        if (findActionKey != null && this.mSuggestionAdapterPrivateApi.getQueryActionMsg(findActionKey) != null) {
            launchQuerySearch(i, this.mSuggestionAdapterPrivateApi.getQueryActionMsg(findActionKey), this.gdSearchAutoCompleteTextView.getText().toString());
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    public void onQueryRefine(CharSequence charSequence) {
        setQuery(charSequence);
    }

    @Override // android.widget.LinearLayout, android.view.View
    public void onRtlPropertiesChanged(int i) {
        this.gdSearchAutoCompleteTextView.setLayoutDirection(i);
    }

    @Override // android.widget.SearchView, android.view.View
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        postUpdateFocusedState();
    }

    @Override // android.widget.SearchView, android.view.ViewGroup, android.view.View
    public boolean requestFocus(int i, Rect rect) {
        if (!getClearingFocus() && isFocusable()) {
            if (!isIconified()) {
                boolean requestFocus = this.gdSearchAutoCompleteTextView.requestFocus(i, rect);
                if (requestFocus) {
                    updateViewsVisibility(false);
                }
                return requestFocus;
            }
            return super.requestFocus(i, rect);
        }
        return false;
    }

    public void setAppSearchData(Bundle bundle) {
        this.mAppSearchData = bundle;
    }

    @Override // android.widget.SearchView
    public void setIconified(boolean z) {
        if (z) {
            onCloseClicked();
        } else {
            onSearchClicked();
        }
    }

    @Override // android.widget.SearchView
    public void setIconifiedByDefault(boolean z) {
        if (getIconifiedByDefaultField() == z) {
            return;
        }
        setIconifiedByDefaultField(z);
        updateViewsVisibility(z);
        updateQueryHint();
    }

    @Override // android.widget.SearchView
    public void setImeOptions(int i) {
        GDSearchAutoCompleteTextView gDSearchAutoCompleteTextView = this.gdSearchAutoCompleteTextView;
        if (gDSearchAutoCompleteTextView != null) {
            gDSearchAutoCompleteTextView.setImeOptions(i);
        } else {
            this.gdPendingIMEOptions = i;
        }
    }

    @Override // android.widget.SearchView
    public void setInputType(int i) {
        GDSearchAutoCompleteTextView gDSearchAutoCompleteTextView = this.gdSearchAutoCompleteTextView;
        if (gDSearchAutoCompleteTextView != null) {
            gDSearchAutoCompleteTextView.setInputType(i);
        } else {
            this.gdPendingInputType = i;
        }
    }

    @Override // android.widget.SearchView
    public void setOnCloseListener(OnCloseListener onCloseListener) {
        this.mOnCloseListener = onCloseListener;
        super.setOnCloseListener(onCloseListener);
    }

    @Override // android.widget.SearchView
    public void setOnQueryTextListener(OnQueryTextListener onQueryTextListener) {
        this.mOnQueryChangeListener = onQueryTextListener;
        super.setOnQueryTextListener(onQueryTextListener);
    }

    @Override // android.widget.SearchView
    public void setOnSearchClickListener(OnClickListener onClickListener) {
        this.mOnSearchClickListener = onClickListener;
        super.setOnSearchClickListener(onClickListener);
    }

    @Override // android.widget.SearchView
    public void setOnSuggestionListener(OnSuggestionListener onSuggestionListener) {
        this.mOnSuggestionListener = onSuggestionListener;
        super.setOnSuggestionListener(onSuggestionListener);
    }

    @Override // android.widget.SearchView
    public void setQuery(CharSequence charSequence, boolean z) {
        this.gdSearchAutoCompleteTextView.setText(charSequence);
        if (charSequence != null) {
            GDSearchAutoCompleteTextView gDSearchAutoCompleteTextView = this.gdSearchAutoCompleteTextView;
            gDSearchAutoCompleteTextView.setSelection(gDSearchAutoCompleteTextView.length());
            setUserQueryField(charSequence);
        }
        if (!z || TextUtils.isEmpty(charSequence)) {
            return;
        }
        onSubmitQuery();
    }

    @Override // android.widget.SearchView
    public void setQueryHint(CharSequence charSequence) {
        super.setQueryHint(charSequence);
        updateQueryHint();
    }

    @Override // android.widget.SearchView
    public void setSearchableInfo(SearchableInfo searchableInfo) {
        this.mSearchable = searchableInfo;
        super.setSearchableInfo(searchableInfo);
        if (searchableInfo != null) {
            updateSearchAutoComplete();
            updateQueryHint();
        }
        if (GDDLPKeyboardControl.getInstance().isDLPKeyboardOn()) {
            GDDLPKeyboardControl.getInstance().configureDLPKeyboardOptions(this.gdSearchAutoCompleteTextView);
        } else if (hasVoiceSearch() && GDDLPKeyboardControl.getInstance().isDLPIncognitoModeOn()) {
            setVoiceButtonEnabled(false);
        } else {
            setVoiceButtonEnabled(hasVoiceSearch());
        }
        updateViewsVisibility(isIconified());
    }

    @Override // android.widget.SearchView
    public void setSubmitButtonEnabled(boolean z) {
        setSubmitButtonState(z);
        updateViewsVisibility(isIconified());
    }

    @Override // android.widget.SearchView
    public void setSuggestionsAdapter(CursorAdapter cursorAdapter) {
        setPrivateSuggestionsAdapter(cursorAdapter);
        this.gdSearchAutoCompleteTextView.setAdapter(cursorAdapter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class gioey implements SearchViewPrivateApi {
        private Runnable dbjc;
        private View jwxax;
        private boolean liflu;
        private ImageView lqox;
        private View qkduk;
        private View wxau;
        private ImageView ztwf;

        /* loaded from: classes.dex */
        class hbfhc implements Runnable {
            hbfhc() {
            }

            @Override // java.lang.Runnable
            public void run() {
                GDSearchView.this.releaseCursor();
            }
        }

        private gioey() {
            this.dbjc = new hbfhc();
        }

        private Intent dbjc(String str, Uri uri, String str2, String str3, int i, String str4) {
            Intent intent = new Intent(str);
            intent.addFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
            if (uri != null) {
                intent.setData(uri);
            }
            intent.putExtra("user_query", GDSearchView.this.mUserQuery);
            if (str3 != null) {
                intent.putExtra(SearchIntents.EXTRA_QUERY, str3);
            }
            if (str2 != null) {
                intent.putExtra("intent_extra_data_key", str2);
            }
            if (GDSearchView.this.mAppSearchData != null) {
                intent.putExtra("app_data", GDSearchView.this.mAppSearchData);
            }
            if (i != 0) {
                intent.putExtra("action_key", i);
                intent.putExtra("action_msg", str4);
            }
            intent.setComponent(GDSearchView.this.mSearchable.getSearchActivity());
            return intent;
        }

        @Override // com.good.gd.widget.impl.SearchViewPrivateApi
        public ImageView getCloseButton() {
            try {
                if (this.ztwf == null) {
                    this.ztwf = (ImageView) GDSearchView.this.getViewFieldByIdName("search_close_btn");
                }
                return this.ztwf;
            } catch (Exception e) {
                GDSearchView.logFieldAccessException("search_close_btn");
                return null;
            }
        }

        @Override // com.good.gd.widget.impl.SearchViewPrivateApi
        public int getCollapsedImeOption() {
            return GDSearchView.this.mCollapsedImeOptions;
        }

        @Override // com.good.gd.widget.impl.SearchViewPrivateApi
        public boolean getExpandedInActionView() {
            return GDSearchView.this.mExpandedInActionView;
        }

        @Override // com.good.gd.widget.impl.SearchViewPrivateApi
        public CharSequence getOldQueryText() {
            return GDSearchView.this.mOldQueryText;
        }

        @Override // com.good.gd.widget.impl.SearchViewPrivateApi
        public OnSuggestionListener getOnSuggestionListener() {
            return GDSearchView.this.mOnSuggestionListener;
        }

        @Override // com.good.gd.widget.impl.SearchViewPrivateApi
        public WeakHashMap<String, Drawable.ConstantState> getOutsideDrawablesCache() {
            return GDSearchView.this.mOutsideDrawablesCache;
        }

        @Override // com.good.gd.widget.impl.SearchViewPrivateApi
        public CursorAdapter getPrivateSuggestionsAdapter() {
            return GDSearchView.this.mSuggestionsAdapter;
        }

        @Override // com.good.gd.widget.impl.SearchViewPrivateApi
        public Runnable getReleaseCursorRunnable() {
            return this.dbjc;
        }

        @Override // com.good.gd.widget.impl.SearchViewPrivateApi
        public View getSearchButtonField() {
            try {
                if (this.jwxax == null) {
                    this.jwxax = GDSearchView.this.getViewFieldByIdName("search_button");
                }
                return this.jwxax;
            } catch (Exception e) {
                GDSearchView.logFieldAccessException("search_button");
                return null;
            }
        }

        @Override // com.good.gd.widget.impl.SearchViewPrivateApi
        public View getSearchEditFrame() {
            try {
                return GDSearchView.this.getViewFieldByIdName("search_edit_frame");
            } catch (Exception e) {
                GDSearchView.logFieldAccessException("search_edit_frame");
                return null;
            }
        }

        @Override // com.good.gd.widget.impl.SearchViewPrivateApi
        public SearchableInfo getSearchable() {
            return GDSearchView.this.mSearchable;
        }

        @Override // com.good.gd.widget.impl.SearchViewPrivateApi
        public View getSubmitArea() {
            try {
                if (this.qkduk == null) {
                    this.qkduk = GDSearchView.this.getViewFieldByIdName("submit_area");
                }
                return this.qkduk;
            } catch (Exception e) {
                GDSearchView.logFieldAccessException("submit_area");
                return null;
            }
        }

        @Override // com.good.gd.widget.impl.SearchViewPrivateApi
        public View getSubmitButton() {
            try {
                if (this.wxau == null) {
                    this.wxau = GDSearchView.this.getViewFieldByIdName("search_go_btn");
                }
                return this.wxau;
            } catch (Exception e) {
                GDSearchView.logFieldAccessException("search_go_btn");
                return null;
            }
        }

        @Override // com.good.gd.widget.impl.SearchViewPrivateApi
        public Intent getVoiceAppSearchIntent() {
            return GDSearchView.this.mVoiceAppSearchIntent;
        }

        @Override // com.good.gd.widget.impl.SearchViewPrivateApi
        public View getVoiceButton() {
            try {
                if (this.lqox == null) {
                    this.lqox = (ImageView) GDSearchView.this.getViewFieldByIdName("search_voice_btn");
                }
                return this.lqox;
            } catch (Exception e) {
                GDSearchView.logFieldAccessException("search_voice_btn");
                return null;
            }
        }

        @Override // com.good.gd.widget.impl.SearchViewPrivateApi
        public Intent getVoiceWebSearchIntent() {
            return GDSearchView.this.mVoiceWebSearchIntent;
        }

        @Override // com.good.gd.widget.impl.SearchViewPrivateApi
        public void hideMagnifierIcon() {
            ImageView imageView = (ImageView) GDSearchView.this.getViewFieldByIdName("search_mag_icon");
            if (imageView != null) {
                imageView.setVisibility((imageView.getDrawable() == null || GDSearchView.this.mIconifiedByDefault) ? 8 : 0);
            }
        }

        @Override // com.good.gd.widget.impl.SearchViewPrivateApi
        public boolean isIconified() {
            return this.liflu;
        }

        @Override // com.good.gd.widget.impl.SearchViewPrivateApi
        public void launchQuerySearch(int i, String str, String str2) {
            GDSearchView.this.getContext().startActivity(dbjc("android.intent.action.SEARCH", null, null, str2, i, str));
        }

        @Override // com.good.gd.widget.impl.SearchViewPrivateApi
        public void launchSuggestion(int i, int i2, String str) {
            int i3;
            Uri parse;
            String columnString;
            Cursor cursor = GDSearchView.this.mSuggestionsAdapter.getCursor();
            if (cursor == null || !cursor.moveToPosition(i)) {
                return;
            }
            Intent intent = null;
            try {
                String columnString2 = GDSearchView.this.mSuggestionAdapterPrivateApi.getColumnString(cursor, "suggest_intent_action");
                if (columnString2 == null) {
                    columnString2 = GDSearchView.this.mSearchable.getSuggestIntentAction();
                }
                if (columnString2 == null) {
                    columnString2 = "android.intent.action.SEARCH";
                }
                String str2 = columnString2;
                String columnString3 = GDSearchView.this.mSuggestionAdapterPrivateApi.getColumnString(cursor, "suggest_intent_data");
                if (columnString3 == null) {
                    columnString3 = GDSearchView.this.mSearchable.getSuggestIntentData();
                }
                if (columnString3 != null && (columnString = GDSearchView.this.mSuggestionAdapterPrivateApi.getColumnString(cursor, "suggest_intent_data_id")) != null) {
                    columnString3 = columnString3 + "/" + Uri.encode(columnString);
                }
                if (columnString3 == null) {
                    parse = null;
                } else {
                    parse = Uri.parse(columnString3);
                }
                intent = dbjc(str2, parse, GDSearchView.this.mSuggestionAdapterPrivateApi.getColumnString(cursor, "suggest_intent_extra_data"), GDSearchView.this.mSuggestionAdapterPrivateApi.getColumnString(cursor, "suggest_intent_query"), i2, str);
            } catch (RuntimeException e) {
                try {
                    i3 = cursor.getPosition();
                } catch (RuntimeException e2) {
                    i3 = -1;
                }
                GDLog.DBGPRINTF(13, "Search suggestions cursor at row " + i3 + " returned exception.", e);
            }
            if (intent == null) {
                return;
            }
            try {
                GDSearchView.this.getContext().startActivity(intent);
            } catch (RuntimeException e3) {
                GDLog.DBGPRINTF(12, "Failed launch activity: " + intent, e3);
            }
        }

        @Override // com.good.gd.widget.impl.SearchViewPrivateApi
        public void onVoiceClicked() {
            if (GDSearchView.this.mSearchable == null) {
                return;
            }
            SearchableInfo searchableInfo = GDSearchView.this.mSearchable;
            try {
                if (searchableInfo.getVoiceSearchLaunchWebSearch()) {
                    Intent intent = new Intent(GDSearchView.this.mVoiceWebSearchIntent);
                    ComponentName searchActivity = searchableInfo.getSearchActivity();
                    intent.putExtra("calling_package", searchActivity == null ? null : searchActivity.flattenToShortString());
                    GDSearchView.this.getContext().startActivity(intent);
                } else if (!searchableInfo.getVoiceSearchLaunchRecognizer()) {
                } else {
                    GDSearchView.this.getContext().startActivity(dbjc(GDSearchView.this.mVoiceAppSearchIntent, searchableInfo));
                }
            } catch (ActivityNotFoundException e) {
                GDLog.DBGPRINTF(13, "GDSearchView Could not find voice search activity");
            }
        }

        @Override // com.good.gd.widget.impl.SearchViewPrivateApi
        public void setCollapsedImeOption(int i) {
            GDSearchView.this.mCollapsedImeOptions = i;
        }

        @Override // com.good.gd.widget.impl.SearchViewPrivateApi
        public void setExpandedInActionView(boolean z) {
            GDSearchView.this.mExpandedInActionView = z;
        }

        @Override // com.good.gd.widget.impl.SearchViewPrivateApi
        public void setIconifiedByDefaultField(boolean z) {
            GDSearchView.this.mIconifiedByDefault = z;
            GDSearchView.super.setIconifiedByDefault(z);
        }

        @Override // com.good.gd.widget.impl.SearchViewPrivateApi
        public void setIconifiedField(boolean z) {
            this.liflu = z;
        }

        @Override // com.good.gd.widget.impl.SearchViewPrivateApi
        public void setOldQueryText(CharSequence charSequence) {
            GDSearchView.this.mOldQueryText = charSequence;
        }

        @Override // com.good.gd.widget.impl.SearchViewPrivateApi
        public void setPrivateSuggestionsAdapter(CursorAdapter cursorAdapter) {
            GDSearchView.this.mSuggestionsAdapter = cursorAdapter;
            GDSearchView gDSearchView = GDSearchView.this;
            GDSearchView.super.setSuggestionsAdapter(gDSearchView.mSuggestionsAdapter);
            AutoCompleteTextView originalAutoCompleteTextView = GDSearchView.this.getOriginalAutoCompleteTextView();
            if (originalAutoCompleteTextView != null) {
                originalAutoCompleteTextView.setAdapter(null);
            }
        }

        @Override // com.good.gd.widget.impl.SearchViewPrivateApi
        public void setSubmitButtonState(boolean z) {
            GDSearchView.this.mSubmitButtonEnabled = z;
        }

        @Override // com.good.gd.widget.impl.SearchViewPrivateApi
        public void setUserQueryField(CharSequence charSequence) {
            GDSearchView.this.mUserQuery = charSequence;
        }

        @Override // com.good.gd.widget.impl.SearchViewPrivateApi
        public void setVoiceButtonEnabled(boolean z) {
            GDSearchView.this.mVoiceButtonEnabled = z;
        }

        @Override // com.good.gd.widget.impl.SearchViewPrivateApi
        public void updateSubmitArea() {
            View submitButton = getSubmitButton();
            View voiceButton = getVoiceButton();
            int i = (!GDSearchView.this.isSubmitAreaEnabled() || ((submitButton == null || submitButton.getVisibility() != 0) && (voiceButton == null || voiceButton.getVisibility() != 0))) ? 8 : 0;
            View submitArea = getSubmitArea();
            if (submitArea != null) {
                submitArea.setVisibility(i);
            }
        }

        @Override // com.good.gd.widget.impl.SearchViewPrivateApi
        public void updateSubmitButton(boolean z) {
            try {
                int i = (!GDSearchView.this.mSubmitButtonEnabled || !GDSearchView.this.isSubmitAreaEnabled() || !GDSearchView.this.hasFocus() || (!z && GDSearchView.this.mVoiceButtonEnabled)) ? 8 : 0;
                View submitButton = getSubmitButton();
                if (submitButton == null) {
                    return;
                }
                submitButton.setVisibility(i);
            } catch (Exception e) {
                GDLog.DBGPRINTF(16, GDSearchView.TAG + " updateSubmitButton " + e);
            }
        }

        @Override // com.good.gd.widget.impl.SearchViewPrivateApi
        public void updateVoiceButton(boolean z) {
            int i = 8;
            if (GDSearchView.this.mVoiceButtonEnabled && !this.liflu && z) {
                View submitButton = getSubmitButton();
                if (submitButton != null) {
                    submitButton.setVisibility(8);
                }
                i = 0;
            }
            View voiceButton = getVoiceButton();
            if (voiceButton != null) {
                voiceButton.setVisibility(i);
            }
        }

        /* synthetic */ gioey(GDSearchView gDSearchView, hbfhc hbfhcVar) {
            this();
        }

        private Intent dbjc(Intent intent, SearchableInfo searchableInfo) {
            String str;
            String str2;
            String str3;
            ComponentName searchActivity = searchableInfo.getSearchActivity();
            Intent intent2 = new Intent("android.intent.action.SEARCH");
            intent2.setComponent(searchActivity);
            PendingIntent activity = PendingIntent.getActivity(GDSearchView.this.getContext(), 0, intent2, 1140850688);
            Bundle bundle = new Bundle();
            if (GDSearchView.this.mAppSearchData != null) {
                bundle.putParcelable("app_data", GDSearchView.this.mAppSearchData);
            }
            Intent intent3 = new Intent(intent);
            int i = 1;
            Resources resources = GDSearchView.this.getResources();
            if (searchableInfo.getVoiceLanguageModeId() == 0) {
                str = "free_form";
            } else {
                str = resources.getString(searchableInfo.getVoiceLanguageModeId());
            }
            String str4 = null;
            if (searchableInfo.getVoicePromptTextId() == 0) {
                str2 = null;
            } else {
                str2 = resources.getString(searchableInfo.getVoicePromptTextId());
            }
            if (searchableInfo.getVoiceLanguageId() == 0) {
                str3 = null;
            } else {
                str3 = resources.getString(searchableInfo.getVoiceLanguageId());
            }
            if (searchableInfo.getVoiceMaxResults() != 0) {
                i = searchableInfo.getVoiceMaxResults();
            }
            intent3.putExtra("android.speech.extra.LANGUAGE_MODEL", str);
            intent3.putExtra("android.speech.extra.PROMPT", str2);
            intent3.putExtra("android.speech.extra.LANGUAGE", str3);
            intent3.putExtra("android.speech.extra.MAX_RESULTS", i);
            if (searchActivity != null) {
                str4 = searchActivity.flattenToShortString();
            }
            intent3.putExtra("calling_package", str4);
            intent3.putExtra("android.speech.extra.RESULTS_PENDINGINTENT", activity);
            intent3.putExtra("android.speech.extra.RESULTS_PENDINGINTENT_BUNDLE", bundle);
            return intent3;
        }
    }

    private void setQuery(CharSequence charSequence) {
        this.gdSearchAutoCompleteTextView.setText(charSequence, true);
        this.gdSearchAutoCompleteTextView.setSelection(TextUtils.isEmpty(charSequence) ? 0 : charSequence.length());
    }

    public GDSearchView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.gdSearchAutoCompleteTextView = new GDSearchAutoCompleteTextView(context, attributeSet, (hbfhc) null);
        GDViewSetup();
    }

    public GDSearchView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.gdSearchAutoCompleteTextView = new GDSearchAutoCompleteTextView(context, attributeSet, i, (hbfhc) null);
        GDViewSetup();
    }

    public GDSearchView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.gdSearchAutoCompleteTextView = new GDSearchAutoCompleteTextView(context, attributeSet, i, i2);
        GDViewSetup();
    }
}
