package com.good.gd.ui.biometric;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.good.gd.resources.R;
import com.good.gd.utils.GDLocalizer;
import java.lang.ref.WeakReference;
import java.util.Locale;

/* loaded from: classes.dex */
public class BBDDialogView {
    private WeakReference<Activity> activityWeakReference;
    private int bbdLightGrey;
    private int colorAlertButtonText;
    private int colorBackgroundDefault;
    private int colorBackgroundError;
    private int colorBackgroundOK;
    private int colorStatusDefault;
    private int colorStatusError;
    private int colorStatusOK;
    private View dialogView;
    private TextView fingerprintText;
    private TextView fingerprintTitle;
    private ImageView icon;
    private FrameLayout iconBackground;
    private int iconCheckWhite;
    private int iconDefault;
    private RelativeLayout iconFrame;
    private int iconPriorityHighWhite;
    private TextView iconText;
    private Button positiveButton;

    public BBDDialogView(Activity activity) {
        this.activityWeakReference = new WeakReference<>(activity);
    }

    private Activity getActivityOrNull() {
        WeakReference<Activity> weakReference = this.activityWeakReference;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    public void disableCancelButton() {
        this.positiveButton.setEnabled(false);
        this.positiveButton.setTextColor(this.bbdLightGrey);
    }

    public void enableCancelButton() {
        Button button = this.positiveButton;
        if (button != null) {
            button.setEnabled(true);
            this.positiveButton.setTextColor(this.colorAlertButtonText);
        }
    }

    public View getDialogView() {
        return this.dialogView;
    }

    public boolean initView(DialogArgs dialogArgs) {
        Activity activityOrNull = getActivityOrNull();
        if (activityOrNull == null) {
            return false;
        }
        this.dialogView = activityOrNull.getLayoutInflater().inflate(R.layout.bbd_fingerprint_dialog_container, (ViewGroup) null, false);
        this.colorAlertButtonText = activityOrNull.getResources().getColor(R.color.bbd_fingerprint_alert_button_text_color);
        this.bbdLightGrey = activityOrNull.getResources().getColor(R.color.bbd_light_grey);
        this.colorStatusError = activityOrNull.getResources().getColor(R.color.bbd_fingerprint_status_error);
        this.colorStatusOK = activityOrNull.getResources().getColor(R.color.bbd_fingerprint_status_ok);
        this.colorStatusDefault = activityOrNull.getResources().getColor(R.color.bbd_fingerprint_status_default);
        this.colorBackgroundError = R.drawable.gd_fingerprint_background_error;
        this.colorBackgroundOK = R.drawable.gd_fingerprint_background_ok;
        this.colorBackgroundDefault = R.drawable.gd_fingerprint_background_default;
        this.iconPriorityHighWhite = R.drawable.gd_priority_high_white;
        this.iconCheckWhite = R.drawable.gd_check_white;
        this.iconDefault = R.drawable.gd_fingerprint_white;
        updateViewForArgs(dialogArgs);
        return true;
    }

    public void setStatus(String str, String str2, int i, int i2, String str3, int i3) {
        if (str2 != null) {
            this.fingerprintText.setText(str2);
        }
        if (str != null) {
            this.fingerprintTitle.setText(str);
        }
        if (str3 != null) {
            this.iconFrame.setVisibility(0);
            this.iconBackground.setBackgroundResource(i);
            this.icon.setImageResource(i2);
            this.iconText.setTextColor(i3);
            this.iconText.setText(str3);
            return;
        }
        this.iconFrame.setVisibility(8);
    }

    public void showAuthenticationSuccess(String str) {
        setStatus(null, null, this.colorBackgroundOK, this.iconCheckWhite, str, this.colorStatusOK);
    }

    public void showError(String str, String str2) {
        setStatus(str, str2, this.colorBackgroundError, this.iconPriorityHighWhite, null, this.colorStatusError);
    }

    public void showErrorStatus(String str) {
        setStatus(null, null, this.colorBackgroundError, this.iconPriorityHighWhite, str, this.colorStatusError);
    }

    public void showListening() {
        setStatus(null, null, this.colorBackgroundDefault, this.iconDefault, GDLocalizer.getLocalizedString(GDAbstractBiometricHelper.DIALOG_TEXT_FINGERPRINT_TOUCH_SENSOR), this.colorStatusDefault);
        this.iconText.setText(GDLocalizer.getLocalizedString(GDAbstractBiometricHelper.DIALOG_TEXT_FINGERPRINT_TOUCH_SENSOR));
    }

    public void updateViewForArgs(DialogArgs dialogArgs) {
        TextView textView = (TextView) this.dialogView.findViewById(R.id.fingerprint_title);
        this.fingerprintTitle = textView;
        textView.setText(GDLocalizer.getLocalizedString(dialogArgs.getTitleKey()));
        TextView textView2 = (TextView) this.dialogView.findViewById(R.id.fingerprint_text);
        this.fingerprintText = textView2;
        textView2.setText(GDLocalizer.getLocalizedString(dialogArgs.getTextKey()));
        this.iconFrame = (RelativeLayout) this.dialogView.findViewById(R.id.fingerprint_icon_frame);
        this.iconBackground = (FrameLayout) this.dialogView.findViewById(R.id.fingerprint_icon_background);
        this.icon = (ImageView) this.dialogView.findViewById(R.id.fingerprint_icon);
        TextView textView3 = (TextView) this.dialogView.findViewById(R.id.fingerprint_icon_text);
        this.iconText = textView3;
        textView3.setText(GDLocalizer.getLocalizedString(GDAbstractBiometricHelper.DIALOG_TEXT_FINGERPRINT_TOUCH_SENSOR));
        if (dialogArgs.getStatus() == null) {
            this.iconFrame.setVisibility(8);
        }
        Button button = (Button) this.dialogView.findViewById(16908313);
        this.positiveButton = (Button) this.dialogView.findViewById(16908314);
        String localizedString = GDLocalizer.getLocalizedString(dialogArgs.getPositiveButtonTextKey());
        String localizedString2 = GDLocalizer.getLocalizedString(dialogArgs.getNegativeButtonTextKey());
        if (localizedString != null) {
            button.setText(localizedString2.toUpperCase(Locale.getDefault()));
            button.setOnClickListener(dialogArgs.getNegativeButtonCallback());
            this.positiveButton.setText(localizedString.toUpperCase(Locale.getDefault()));
            this.positiveButton.setOnClickListener(dialogArgs.getPositiveButtonCallback());
            return;
        }
        button.setVisibility(4);
        this.positiveButton.setText(localizedString2.toUpperCase(Locale.getDefault()));
        this.positiveButton.setOnClickListener(dialogArgs.getNegativeButtonCallback());
    }
}
