package com.good.gd.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.text.Html;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.core.view.GravityCompat;
import com.good.gd.client.GDCustomizedUI;
import com.good.gd.ndkproxy.ui.BBDUIHelper;
import com.good.gd.ndkproxy.ui.BBDUILocalizationHelper;
import com.good.gd.ndkproxy.ui.CoreUI;
import com.good.gd.ndkproxy.ui.data.base.BlockBaseUI;
import com.good.gd.ndkproxy.ui.event.BBDUIEventManager;
import com.good.gd.ndkproxy.ui.event.BBDUIMessageType;
import com.good.gd.resources.R;
import com.good.gd.ui.base_ui.GDView;
import com.good.gd.utils.GDLocalizer;
import com.good.gd.utils.MessageFactory;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class GDBlockView extends GDView {
    private Runnable _closingTask;
    private final GDCustomizedUI customizedUI;
    protected final TextView messageView;
    protected final TextView okButton;
    protected final ProgressBar spinner;
    protected final TextView titleView;
    protected final BlockBaseUI uiData;
    protected final Button unlockButton;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class URLSpanNoUnderline extends URLSpan {
        private final Context dbjc;

        public URLSpanNoUnderline(Context context, String str) {
            super(str);
            this.dbjc = context;
        }

        @Override // android.text.style.URLSpan, android.text.style.ClickableSpan
        public void onClick(View view) {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(getURL()));
            intent.putExtra("com.android.browser.application_id", this.dbjc.getPackageName());
            this.dbjc.startActivity(intent);
        }

        @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
        public void updateDrawState(TextPaint textPaint) {
            super.updateDrawState(textPaint);
            textPaint.setColor(textPaint.linkColor);
            textPaint.setUnderlineText(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class ehnkx implements DialogInterface.OnClickListener {
        ehnkx() {
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            GDBlockView.this.sendMessageToService(MessageFactory.newMessage(1011));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class fdyxd implements DialogInterface.OnClickListener {
        fdyxd() {
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            GDBlockView.this.sendMessageToService(MessageFactory.newMessage(MessageFactory.MSG_CLIENT_WIPE_INCORRECT_AUTHDEL_PWD));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class hbfhc implements OnClickListener {
        hbfhc() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            GDBlockView.this.okButton.setVisibility(8);
            CoreUI.closeAuthDelegationBlockedUI();
            BBDUIHelper.ok(GDBlockView.this.uiData.getCoreHandle());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class yfdke implements OnClickListener {
        yfdke() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            BBDUIEventManager.sendMessage(GDBlockView.this.uiData, BBDUIMessageType.MSG_UI_REMOTE_UNLOCK);
            GDBlockView.this.unlockButton.setVisibility(8);
        }
    }

    public GDBlockView(Context context, ViewInteractor viewInteractor, BlockBaseUI blockBaseUI, ViewCustomizer viewCustomizer) {
        super(context, viewInteractor, viewCustomizer);
        inflateLayout(R.layout.bbd_block_view, this);
        this.uiData = blockBaseUI;
        this.customizedUI = viewCustomizer.getGDCustomizedUI();
        Button button = (Button) findViewById(R.id.gd_unlock_button);
        this.unlockButton = button;
        checkFieldNotNull(button, "bbd_block_view", "gd_unlock_button");
        button.setText(GDLocalizer.getLocalizedString("Unlock"));
        button.setVisibility(0);
        TextView textView = (TextView) findViewById(R.id.gd_ok_button);
        this.okButton = textView;
        checkFieldNotNull(textView, "bbd_block_view", "gd_ok_button");
        if (blockBaseUI.getInterAppLockReason() == 0) {
            textView.setText(GDLocalizer.getLocalizedString("Try again"));
        } else {
            textView.setText(BBDUILocalizationHelper.getLocalizedOK());
        }
        TextView textView2 = (TextView) findViewById(R.id.COM_GOOD_GD_BLOCK_VIEW_TITLE_VIEW);
        this.titleView = textView2;
        checkFieldNotNull(textView2, "bbd_block_view", "COM_GOOD_GD_BLOCK_VIEW_TITLE_VIEW");
        TextView textView3 = (TextView) findViewById(R.id.COM_GOOD_GD_BLOCK_VIEW_MESSAGE_VIEW);
        this.messageView = textView3;
        checkFieldNotNull(textView3, "bbd_block_view", "COM_GOOD_GD_BLOCK_VIEW_MESSAGE_VIEW");
        textView3.setMovementMethod(LinkMovementMethod.getInstance());
        textView3.setFocusable(false);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.COM_GOOD_GD_BLOCK_VIEW_PROGRESSBAR);
        this.spinner = progressBar;
        checkFieldNotNull(progressBar, "bbd_block_view", "COM_GOOD_GD_BLOCK_VIEW_PROGRESSBAR");
        enableBottomLine();
        enableBottomButton(blockBaseUI);
        enableHelpButton(blockBaseUI);
        applyUICustomization();
        setTopHeaderShadow();
    }

    private void showWipeOrResetDialog() {
        showPopupDialog2(GDLocalizer.getLocalizedString("Authentication failed"), GDLocalizer.getLocalizedString("Auth delegate authentication failed"), GDLocalizer.getLocalizedString("Wipe"), new fdyxd(), GDLocalizer.getLocalizedString("Unlock"), new ehnkx());
    }

    private void updateButtonsVisibility() {
        if (!this.uiData.isWiped() && !this.uiData.isUpdating()) {
            boolean isOfferRemoteUnlock = this.uiData.isOfferRemoteUnlock();
            if (this.uiData.getInterAppLockReason() == 0 && this.uiData.getBlockReason() != 13) {
                this.okButton.setVisibility(0);
                this.okButton.setText(GDLocalizer.getLocalizedString("Try again"));
                this.okButton.setOnClickListener(new hbfhc());
            } else {
                this.okButton.setVisibility(8);
            }
            if (!isOfferRemoteUnlock) {
                this.unlockButton.setVisibility(8);
                return;
            }
            this.unlockButton.setVisibility(0);
            this.unlockButton.setOnClickListener(new yfdke());
            return;
        }
        this.unlockButton.setVisibility(8);
        this.okButton.setVisibility(8);
    }

    private void updateLearnMore() {
        if (this.uiData.isGettingAccessKey()) {
            setBottomLabelVisibility(8);
        }
    }

    private void updateSpinner() {
        this.spinner.setVisibility(this.uiData.isUpdating() ? 0 : 8);
    }

    private void updateUI() {
        updateSpinner();
        updateButtonsVisibility();
        updateGravity();
        updateLearnMore();
    }

    @Override // com.good.gd.ui.base_ui.GDView
    public void activityFinished() {
        if (this.uiData.isWiped()) {
            sendMessageToService(MessageFactory.newMessage(1013));
        }
        if (this._closingTask != null) {
            new Handler().post(this._closingTask);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.good.gd.ui.base_ui.GDView
    public void applyUICustomization() {
        super.applyUICustomization();
        if (!this.customizedUI.isUICustomized() || this.customizedUI.getCustomUIColor() == null) {
            return;
        }
        this.messageView.setLinkTextColor(this.customizedUI.getCustomUIColor().intValue());
        this.unlockButton.setBackgroundColor(this.customizedUI.getCustomUIColor().intValue());
        this.okButton.setTextColor(this.customizedUI.getCustomUIColor().intValue());
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.titleView.setText(this.uiData.getTitleText());
        setMessage(this.uiData.getMessageText());
        updateUI();
    }

    @Override // com.good.gd.ui.base_ui.GDView
    public void onBackPressed() {
        moveTaskToBack();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setMessage(String str) {
        if (this.uiData.getBlockReason() != 26) {
            this.messageView.setText(Html.fromHtml(str.replace("\n", "<br>")));
            Linkify.addLinks(this.messageView, Pattern.compile("((https?|ftp)(:\\/\\/[-_.!~*\\'()a-zA-Z0-9;\\/?:\\@&=+\\$,%#]+))"), "");
            this.messageView.setLinkTextColor(getResources().getColor(R.color.bbd_link_color));
            setTextWithoutUnderlinesInLinks(this.messageView);
            return;
        }
        this.messageView.setText(str);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setTextWithoutUnderlinesInLinks(TextView textView) {
        URLSpan[] uRLSpanArr;
        Spannable spannable = (Spannable) textView.getText();
        if (spannable == null) {
            return;
        }
        for (URLSpan uRLSpan : (URLSpan[]) spannable.getSpans(0, spannable.length(), URLSpan.class)) {
            int spanStart = spannable.getSpanStart(uRLSpan);
            int spanEnd = spannable.getSpanEnd(uRLSpan);
            spannable.removeSpan(uRLSpan);
            spannable.setSpan(new URLSpanNoUnderline(getContext(), uRLSpan.getURL()), spanStart, spanEnd, 0);
        }
    }

    @Override // com.good.gd.ui.base_ui.GDView
    public void stateWasUpdated() {
        this.titleView.setText(this.uiData.getTitleText());
        setMessage(this.uiData.getMessageText());
        updateUI();
        if (this.uiData.dialogWaiting() == BlockBaseUI.GDDialogType.UI_DIALOG_DEVICE_WIPE_OR_RESET) {
            showWipeOrResetDialog();
            this.uiData.dialogAcknowledged();
        }
    }

    protected void updateGravity() {
        boolean z = false;
        int i = 1;
        boolean z2 = this.unlockButton.getVisibility() == 0;
        if (this.okButton.getVisibility() == 0) {
            z = true;
        }
        if (z2 || z) {
            i = GravityCompat.START;
        }
        this.titleView.setGravity(i);
        this.messageView.setGravity(i);
    }
}
