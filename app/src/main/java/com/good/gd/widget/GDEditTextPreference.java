package com.good.gd.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.DialogPreference;
import android.preference.Preference;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.EditText;

/* loaded from: classes.dex */
public class GDEditTextPreference extends DialogPreference {
    private Context mContext;
    private GDEditText mEditText;
    private String mText;

    public GDEditTextPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        GDViewSetup(context, attributeSet);
    }

    private void GDViewSetup(Context context, AttributeSet attributeSet) {
        GDEditText gDEditText = new GDEditText(context, attributeSet);
        this.mEditText = gDEditText;
        this.mContext = context;
        gDEditText.setEnabled(true);
    }

    private void onAddEditTextToDialogView(View view, EditText editText) {
        ViewGroup viewGroup = (ViewGroup) view.findViewById(Resources.getSystem().getIdentifier("edittext_container", "id", "android"));
        if (viewGroup != null) {
            viewGroup.addView(editText, -1, -2);
        }
    }

    public EditText getEditText() {
        return this.mEditText;
    }

    public String getText() {
        return this.mText;
    }

    @Override // android.preference.DialogPreference
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);
        GDEditText gDEditText = this.mEditText;
        gDEditText.setText(getText());
        ViewParent parent = gDEditText.getParent();
        if (parent != view) {
            if (parent != null) {
                ((ViewGroup) parent).removeView(gDEditText);
            }
            onAddEditTextToDialogView(view, gDEditText);
        }
    }

    @Override // android.preference.DialogPreference
    protected void onDialogClosed(boolean z) {
        super.onDialogClosed(z);
        if (z) {
            String obj = this.mEditText.getText().toString();
            if (!callChangeListener(obj)) {
                return;
            }
            setText(obj);
        }
    }

    @Override // android.preference.Preference
    protected Object onGetDefaultValue(TypedArray typedArray, int i) {
        return typedArray.getString(i);
    }

    @Override // android.preference.DialogPreference, android.preference.Preference
    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable != null && parcelable.getClass().equals(SavedState.class)) {
            SavedState savedState = (SavedState) parcelable;
            super.onRestoreInstanceState(savedState.getSuperState());
            setText(savedState.dbjc);
            return;
        }
        super.onRestoreInstanceState(parcelable);
    }

    @Override // android.preference.DialogPreference, android.preference.Preference
    protected Parcelable onSaveInstanceState() {
        Parcelable onSaveInstanceState = super.onSaveInstanceState();
        if (isPersistent()) {
            return onSaveInstanceState;
        }
        SavedState savedState = new SavedState(onSaveInstanceState);
        savedState.dbjc = getText();
        return savedState;
    }

    @Override // android.preference.Preference
    protected void onSetInitialValue(boolean z, Object obj) {
        setText(z ? getPersistedString(this.mText) : (String) obj);
    }

    public void setText(String str) {
        boolean shouldDisableDependents = shouldDisableDependents();
        this.mText = str;
        persistString(str);
        boolean shouldDisableDependents2 = shouldDisableDependents();
        if (shouldDisableDependents2 != shouldDisableDependents) {
            notifyDependencyChange(shouldDisableDependents2);
        }
    }

    @Override // android.preference.Preference
    public boolean shouldDisableDependents() {
        return TextUtils.isEmpty(this.mText) || super.shouldDisableDependents();
    }

    /* loaded from: classes.dex */
    private static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new hbfhc();
        String dbjc;

        /* loaded from: classes.dex */
        static class hbfhc implements Creator<SavedState> {
            hbfhc() {
            }

            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        }

        public SavedState(Parcel parcel) {
            super(parcel);
            this.dbjc = parcel.readString();
        }

        @Override // android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeString(this.dbjc);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }

    public GDEditTextPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842898);
    }

    public GDEditTextPreference(Context context) {
        this(context, null);
    }

    public GDEditTextPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        GDViewSetup(context, attributeSet);
    }
}
