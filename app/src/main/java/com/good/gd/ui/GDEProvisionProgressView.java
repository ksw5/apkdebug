package com.good.gd.ui;

import android.animation.Animator;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.good.gd.client.GDCustomizedUI;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.ui.data.base.ProgressBaseUI;
import com.good.gd.ndkproxy.ui.event.BBDUIUpdateEvent;
import com.good.gd.resources.R;
import com.good.gd.ui.base_ui.GDView;
import com.good.gd.utils.GDLocalizer;
import com.good.gd.utils.MessageFactory;
import com.good.gd.widget.GDTextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public final class GDEProvisionProgressView extends GDView {
    private static final int INITIAL_PROGRESS_LINE_POSITION = 5;
    private static final String PROGRESS_POSITION = "progress_position";
    private static final int PROVISION_STEPS_COUNT = 3;
    private static final LocalizeHelper localizedHelper = new LocalizeHelper();
    private List<ImageView> animationArrows = new ArrayList();
    private ArrowAnimationHelper arrowAnimationHelper;
    private final GDCustomizedUI customizedUI;
    private final LinearLayout progressLayout;
    private ProgressBaseUI uiData;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class ArrowAnimationHelper implements Animator.AnimatorListener {
        private List<ImageView> jwxax;
        private boolean qkduk;
        private float dbjc = 0.1f;
        private Interpolator wxau = new LinearInterpolator();

        public ArrowAnimationHelper(List<ImageView> list) {
            this.jwxax = list;
        }

        public void dbjc() {
            float size = 1.0f / this.jwxax.size();
            this.dbjc = size;
            long j = 0;
            for (int i = 0; i < this.jwxax.size(); i++) {
                ViewPropertyAnimator interpolator = this.jwxax.get(i).animate().alphaBy(this.dbjc).setDuration(200L).setStartDelay(j).setInterpolator(this.wxau);
                this.dbjc += size;
                j += 200;
                if (i == this.jwxax.size() - 1) {
                    interpolator.setListener(this);
                    this.qkduk = true;
                }
                interpolator.start();
            }
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            if (this.qkduk) {
                for (int i = 0; i < this.jwxax.size(); i++) {
                    ViewPropertyAnimator interpolator = this.jwxax.get(i).animate().alpha(0.2f).setDuration(200L).setInterpolator(this.wxau);
                    if (i == this.jwxax.size() - 1) {
                        interpolator.setListener(this);
                        this.qkduk = false;
                    }
                    interpolator.start();
                }
                return;
            }
            dbjc();
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class LocalizeHelper {
        private List<String> dbjc = Collections.unmodifiableList(Arrays.asList(GDLocalizer.getLocalizedString("NOC Provisioning"), GDLocalizer.getLocalizedString("Negotiating Request"), GDLocalizer.getLocalizedString("Downloading Policies")));
        private List<String> qkduk = Collections.unmodifiableList(Arrays.asList("NOC Provisioning", "Negotiating Request", "Downloading Policies"));

        public boolean dbjc(String str) {
            return this.qkduk.contains(str);
        }

        public int qkduk(String str) {
            return this.qkduk.indexOf(str);
        }

        public String dbjc(int i) {
            return this.dbjc.get(i);
        }
    }

    /* loaded from: classes.dex */
    private class yfdke extends GDViewDelegateAdapter {
        private yfdke() {
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActivityCreate(Bundle bundle) {
            if (bundle != null) {
                GDEProvisionProgressView.this.moveToNextStep(bundle.getInt(GDEProvisionProgressView.PROGRESS_POSITION, 0));
            }
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onSaveInstanceState(Bundle bundle) {
            bundle.putInt(GDEProvisionProgressView.PROGRESS_POSITION, GDEProvisionProgressView.this.getCurrentProgressPosition());
        }
    }

    public GDEProvisionProgressView(Context context, ViewInteractor viewInteractor, ProgressBaseUI progressBaseUI, ViewCustomizer viewCustomizer) {
        super(context, viewInteractor, viewCustomizer);
        this.uiData = progressBaseUI;
        this.customizedUI = viewCustomizer.getGDCustomizedUI();
        this._delegate = new yfdke();
        inflateLayout(R.layout.gde_provision_progress_view, this);
        GDTextView gDTextView = (GDTextView) findViewById(R.id.gd_activation_title);
        checkFieldNotNull(gDTextView, "gde_provision_progress_view", "gd_activation_title");
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.gd_act_progress_step_layout);
        this.progressLayout = linearLayout;
        checkFieldNotNull(linearLayout, "gde_provision_progress_view", "gd_act_progress_step_layout");
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.anim_arrows_layout);
        if (linearLayout2 != null) {
            startActivationAnimation(linearLayout2);
        }
        if (this.uiData.isMigrationFlow()) {
            gDTextView.setText(GDLocalizer.getLocalizedString("Migration is in progress"));
        } else {
            gDTextView.setText(GDLocalizer.getLocalizedString("Activating"));
        }
        setInitialProgress(true);
        setProgressLine(6);
        enableBottomLine();
        enableBottomButton(this.uiData);
        applyUICustomization();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getCurrentProgressPosition() {
        for (int i = 0; i < 3; i++) {
            if (((LinearLayout) this.progressLayout.getChildAt(i)).findViewById(R.id.gd_spinner).getVisibility() == 0) {
                return i;
            }
        }
        return 3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void moveToNextStep(int i) {
        for (int i2 = 0; i2 < i; i2++) {
            updateProgressView((LinearLayout) this.progressLayout.getChildAt(i2), true);
        }
        if (i < 3) {
            updateProgressView((LinearLayout) this.progressLayout.getChildAt(i), false);
        }
        setProgressLine(i + 5 + 1);
    }

    private void resetProgressView() {
        setProgressLine(6);
        setInitialProgress(false);
    }

    private void setInitialProgress(boolean z) {
        LinearLayout linearLayout;
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService("layout_inflater");
        for (int i = 0; i < 3; i++) {
            if (z) {
                linearLayout = (LinearLayout) layoutInflater.inflate(R.layout.gd_provisioning_step_view, (ViewGroup) null);
                setProgressStepCustomization(linearLayout);
            } else {
                linearLayout = (LinearLayout) this.progressLayout.getChildAt(i);
            }
            TextView textView = (TextView) linearLayout.findViewById(R.id.gd_step_textview);
            textView.setText(localizedHelper.dbjc(i));
            textView.setTypeface(textView.getTypeface(), 0);
            textView.setAlpha(getFloatValueFromResource(R.dimen.gd_text_transparent_progress_step));
            linearLayout.findViewById(R.id.gd_step_check_icon).setVisibility(8);
            if (i == 0) {
                textView.setAlpha(getFloatValueFromResource(R.dimen.gd_text_opaque));
                linearLayout.findViewById(R.id.gd_spinner).setVisibility(0);
            } else {
                linearLayout.findViewById(R.id.gd_spinner).setVisibility(4);
            }
            if (z) {
                this.progressLayout.addView(linearLayout);
            }
        }
    }

    private void setProgressStepCustomization(LinearLayout linearLayout) {
        if (!this.customizedUI.isUICustomized() || this.customizedUI.getCustomUIColor() == null) {
            return;
        }
        ((ImageView) linearLayout.findViewById(R.id.gd_step_check_icon)).setImageDrawable(getCustomizedDrawable(R.drawable.bbd_progress_check));
        ((ProgressBar) linearLayout.findViewById(R.id.gd_spinner)).setIndeterminateDrawable(getCustomizedDrawable(R.drawable.gd_progress_bar));
    }

    private void startActivationAnimation(LinearLayout linearLayout) {
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            this.animationArrows.add((ImageView) linearLayout.getChildAt(i));
        }
        ArrowAnimationHelper arrowAnimationHelper = new ArrowAnimationHelper(this.animationArrows);
        this.arrowAnimationHelper = arrowAnimationHelper;
        arrowAnimationHelper.dbjc();
    }

    private void updateProgressView(LinearLayout linearLayout, boolean z) {
        if (linearLayout != null) {
            TextView textView = (TextView) linearLayout.findViewById(R.id.gd_step_textview);
            textView.setAlpha(getFloatValueFromResource(R.dimen.gd_text_opaque));
            if (!z) {
                textView.setTypeface(textView.getTypeface(), 0);
                linearLayout.findViewById(R.id.gd_spinner).setVisibility(0);
                linearLayout.findViewById(R.id.gd_step_check_icon).setVisibility(8);
                return;
            }
            textView.setTypeface(textView.getTypeface(), 1);
            linearLayout.findViewById(R.id.gd_spinner).setVisibility(8);
            linearLayout.findViewById(R.id.gd_step_check_icon).setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.good.gd.ui.base_ui.GDView
    public void applyUICustomization() {
        super.applyUICustomization();
        if (!this.customizedUI.isUICustomized() || this.customizedUI.getCustomUIColor() == null) {
            return;
        }
        ((GDTextView) findViewById(R.id.gd_activation_title)).setTextColor(this.customizedUI.getCustomUIColor().intValue());
        for (ImageView imageView : this.animationArrows) {
            imageView.setImageDrawable(getCustomizedDrawable(R.drawable.gd_anim_arrow));
        }
        ImageView imageView2 = (ImageView) findViewById(R.id.gd_shield_small);
        if (imageView2 != null) {
            imageView2.setImageDrawable(getCustomizedDrawable(R.drawable.gd_amin_shield_small));
        }
        ImageView imageView3 = (ImageView) findViewById(R.id.gd_shield_big);
        if (imageView3 == null) {
            return;
        }
        imageView3.setImageDrawable(getCustomizedDrawable(R.drawable.gd_anim_shield_big));
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        sendMessageToService(MessageFactory.newMessage(1008));
    }

    @Override // com.good.gd.ui.base_ui.GDView
    public void stateWasUpdated() {
        BBDUIUpdateEvent updateData = this.uiData.getUpdateData();
        GDLog.DBGPRINTF(16, "GDEProvisionProgressView.stateWasUpdated: " + updateData + " event: " + updateData.getText() + "\n");
        if (updateData.isSuccessful()) {
            if (updateData.getText().equals("NOC Activation")) {
                resetProgressView();
            } else if (updateData.getText().equals("Policy Download Complete")) {
                moveToNextStep(3);
            } else {
                LocalizeHelper localizeHelper = localizedHelper;
                if (!localizeHelper.dbjc(updateData.getText())) {
                    return;
                }
                moveToNextStep(localizeHelper.qkduk(updateData.getText()));
            }
        }
    }
}
