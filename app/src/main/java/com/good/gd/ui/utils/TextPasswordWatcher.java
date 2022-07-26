package com.good.gd.ui.utils;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import com.good.gd.resources.R;
import com.good.gd.ui.base_ui.GDView;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes.dex */
public class TextPasswordWatcher implements TextWatcher, View.OnClickListener {
    private Integer brandingColor;
    private TextPasswordWatcherCallback callbackConfirmPassword;
    private TextPasswordWatcherCallback callbackOldPassword;
    private TextPasswordWatcherCallback callbackPassword;
    private EditText confirmPasswordField;
    private ImageView eye;
    private boolean isPasswordEyeStateRestored;
    private EditText passwordField;
    private boolean passwordHidden;
    private Resources resources;
    private TextEnteredListener textEnteredListener;
    private EditText oldPasswordField = null;
    private Set<EditText> allTextFields = new HashSet();
    private Set<TextPasswordWatcherCallback> allTextPasswordWatcherCallbacks = new HashSet();

    /* loaded from: classes.dex */
    public interface TextEnteredListener {
        void onTextEntered();
    }

    /* loaded from: classes.dex */
    public interface TextPasswordWatcherCallback {
        void disableGuard();

        void enableGuard();
    }

    public TextPasswordWatcher(EditText editText, ImageView imageView, Resources resources) {
        if (editText != null && imageView != null) {
            this.passwordField = editText;
            this.eye = imageView;
            this.resources = resources;
            imageView.setOnClickListener(this);
            this.passwordHidden = true;
            return;
        }
        throw new NullPointerException("EditText or ImageView can't be null");
    }

    private void disableGuard() {
        for (TextPasswordWatcherCallback textPasswordWatcherCallback : getAllTextPasswordWatcherCallbacks()) {
            if (textPasswordWatcherCallback != null) {
                textPasswordWatcherCallback.disableGuard();
            }
        }
    }

    private void enableGuard() {
        for (TextPasswordWatcherCallback textPasswordWatcherCallback : getAllTextPasswordWatcherCallbacks()) {
            if (textPasswordWatcherCallback != null) {
                textPasswordWatcherCallback.enableGuard();
            }
        }
    }

    private Set<EditText> getAllTextFields() {
        this.allTextFields.add(this.passwordField);
        this.allTextFields.add(this.confirmPasswordField);
        this.allTextFields.add(this.oldPasswordField);
        return this.allTextFields;
    }

    private Set<TextPasswordWatcherCallback> getAllTextPasswordWatcherCallbacks() {
        this.allTextPasswordWatcherCallbacks.add(this.callbackPassword);
        this.allTextPasswordWatcherCallbacks.add(this.callbackConfirmPassword);
        this.allTextPasswordWatcherCallbacks.add(this.callbackOldPassword);
        return this.allTextPasswordWatcherCallbacks;
    }

    private float getFloatValue(int i) {
        TypedValue typedValue = new TypedValue();
        this.resources.getValue(i, typedValue, true);
        return typedValue.getFloat();
    }

    private boolean isEmptyInputField() {
        for (EditText editText : getAllTextFields()) {
            if (editText != null && !TextUtils.isEmpty(editText.getText().toString())) {
                return false;
            }
        }
        return true;
    }

    private void performTransformation(int i, TransformationMethod transformationMethod) {
        disableGuard();
        setTransformation(this.passwordField, transformationMethod);
        setTransformation(this.confirmPasswordField, transformationMethod);
        setTransformation(this.oldPasswordField, transformationMethod);
        enableGuard();
        setImageViewCustomizedDrawable(i);
        this.passwordHidden = !this.passwordHidden;
    }

    private void setImageViewCustomizedDrawable(int i) {
        Integer num;
        Drawable drawable = this.resources.getDrawable(i, null);
        if (drawable != null && (num = this.brandingColor) != null) {
            GDView.setImageColor(drawable, num.intValue());
        }
        this.eye.setImageDrawable(drawable);
    }

    private void setInitialPasswordState(boolean z) {
        boolean isEmptyInputField = isEmptyInputField();
        if (z) {
            performTransformation(isEmptyInputField ? R.drawable.gd_eye_show_inactive : R.drawable.gd_eye_show_active, PasswordTransformationMethod.getInstance());
        } else {
            performTransformation(isEmptyInputField ? R.drawable.gd_eye_show_inactive : R.drawable.gd_eye_hide_password, HideReturnsTransformationMethod.getInstance());
        }
        this.passwordHidden = z;
    }

    private void setTransformation(EditText editText, TransformationMethod transformationMethod) {
        if (editText == null) {
            return;
        }
        int selectionEnd = editText.getSelectionEnd();
        editText.setTransformationMethod(transformationMethod);
        if (selectionEnd == -1) {
            return;
        }
        editText.setSelection(selectionEnd);
    }

    @Override // android.text.TextWatcher
    public void afterTextChanged(Editable editable) {
        boolean z = true;
        for (EditText editText : getAllTextFields()) {
            if (editText != null) {
                if (TextUtils.isEmpty(editText.getText())) {
                    editText.setAlpha(getFloatValue(R.dimen.gd_text_transparent));
                } else {
                    z = false;
                    editText.setAlpha(getFloatValue(R.dimen.gd_text_opaque));
                }
            }
        }
        if (z) {
            setImageViewCustomizedDrawable(R.drawable.gd_eye_show_inactive);
        } else if (this.passwordHidden) {
            setImageViewCustomizedDrawable(R.drawable.gd_eye_show_active);
        } else {
            setImageViewCustomizedDrawable(R.drawable.gd_eye_hide_password);
        }
        TextEnteredListener textEnteredListener = this.textEnteredListener;
        if (textEnteredListener != null) {
            textEnteredListener.onTextEntered();
        }
    }

    @Override // android.text.TextWatcher
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public boolean getPasswordState() {
        return this.passwordHidden;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (isEmptyInputField()) {
            return;
        }
        if (this.passwordHidden) {
            performTransformation(R.drawable.gd_eye_hide_password, HideReturnsTransformationMethod.getInstance());
        } else {
            performTransformation(R.drawable.gd_eye_show_active, PasswordTransformationMethod.getInstance());
        }
    }

    @Override // android.text.TextWatcher
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public void resetRestoredPasswordEyeState() {
        this.isPasswordEyeStateRestored = false;
    }

    public void restorePasswordEyeState(boolean z) {
        setInitialPasswordState(z);
        this.isPasswordEyeStateRestored = true;
    }

    public void restorePasswordEyeStateAfterBackgroundTransition() {
        if (this.isPasswordEyeStateRestored) {
            return;
        }
        setInitialPasswordState(true);
    }

    public void setBrandingColor(Integer num) {
        this.brandingColor = num;
    }

    public void setCallbackConfirmPassword(TextPasswordWatcherCallback textPasswordWatcherCallback) {
        this.callbackConfirmPassword = textPasswordWatcherCallback;
    }

    public void setCallbackOldPassword(TextPasswordWatcherCallback textPasswordWatcherCallback) {
        this.callbackOldPassword = textPasswordWatcherCallback;
    }

    public void setCallbackPassword(TextPasswordWatcherCallback textPasswordWatcherCallback) {
        this.callbackPassword = textPasswordWatcherCallback;
    }

    public void setOldPasswordField(EditText editText) {
        this.oldPasswordField = editText;
    }

    public void setTextEnteredListener(TextEnteredListener textEnteredListener) {
        this.textEnteredListener = textEnteredListener;
    }

    public TextPasswordWatcher(EditText editText, EditText editText2, ImageView imageView, Resources resources) {
        if (editText != null && editText2 != null && imageView != null) {
            this.passwordField = editText;
            this.confirmPasswordField = editText2;
            this.eye = imageView;
            this.resources = resources;
            imageView.setOnClickListener(this);
            this.passwordHidden = true;
            return;
        }
        throw new NullPointerException("EditText or ImageView can't be null");
    }
}
