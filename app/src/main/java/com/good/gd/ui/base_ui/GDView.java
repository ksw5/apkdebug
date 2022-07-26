package com.good.gd.ui.base_ui;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.good.gd.client.GDCustomizedUI;
import com.good.gd.enterprise.NocServerListProvider;
import com.good.gd.error.GDInitializationError;
import com.good.gd.ndkproxy.ui.BBDUIHelper;
import com.good.gd.ndkproxy.ui.data.base.BaseUI;
import com.good.gd.ndkproxy.ui.event.BBDUIMessageType;
import com.good.gd.resources.R;
import com.good.gd.ui.ViewCustomizer;
import com.good.gd.ui.ViewInteractor;
import com.good.gd.ui.dialogs.GDDialogState;
import com.good.gd.utils.EnterpriseModeChecker;
import com.good.gd.utils.GDLocalizer;
import com.good.gt.ndkproxy.util.GTLog;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/* loaded from: classes.dex */
public class GDView extends RelativeLayout {
    protected static final int DEFAULT_ACTIVATION_PROGRESS = 10;
    private static final int DEFAULT_HELP_BTN_TOUCHABLE_PADDING = 20;
    private static final int DEFAULT_KEYBOARD_HEIGHT = 100;
    private final GDCustomizedUI customizedUI;
    private final EnterpriseModeChecker enterpriseModeChecker;
    private boolean hasTextContainers;
    private final NocServerListProvider nocServerListProvider;
    protected ViewInteractor viewInteractor;
    protected final OnFocusChangeListener fieldFocusListener = new hbfhc();
    private boolean headerSmallHeightUpdated = false;
    private boolean headerBigHeightUpdated = false;
    private boolean footerHeightUpdated = false;
    public GDViewDelegateAdapter _delegate = new GDViewDelegateAdapter();

    /* loaded from: classes.dex */
    public static class GDViewDelegateAdapter {
        private GDView$GDViewDelegate$State currentState = GDView$GDViewDelegate$State.Unknown;

        /* loaded from: classes.dex */
        class ehnkx implements Runnable {
            ehnkx() {
            }

            @Override // java.lang.Runnable
            public void run() {
                GDViewDelegateAdapter.this.onActivityPause();
            }
        }

        /* loaded from: classes.dex */
        class fdyxd implements Runnable {
            fdyxd() {
            }

            @Override // java.lang.Runnable
            public void run() {
                GDViewDelegateAdapter.this.onActivityResume();
            }
        }

        /* loaded from: classes.dex */
        class hbfhc implements Runnable {
            final /* synthetic */ Bundle dbjc;

            hbfhc(Bundle bundle) {
                this.dbjc = bundle;
            }

            @Override // java.lang.Runnable
            public void run() {
                GDViewDelegateAdapter.this.onActivityCreate(this.dbjc);
            }
        }

        /* loaded from: classes.dex */
        class mjbm implements Runnable {
            mjbm() {
            }

            @Override // java.lang.Runnable
            public void run() {
                GDViewDelegateAdapter.this.onActivityDestroy();
            }
        }

        /* loaded from: classes.dex */
        class pmoiy implements Runnable {
            pmoiy() {
            }

            @Override // java.lang.Runnable
            public void run() {
                GDViewDelegateAdapter.this.onActivityStop();
            }
        }

        /* loaded from: classes.dex */
        class yfdke implements Runnable {
            yfdke() {
            }

            @Override // java.lang.Runnable
            public void run() {
                GDViewDelegateAdapter.this.onActivityStart();
            }
        }

        private void checkStateAndExecute(GDView$GDViewDelegate$State gDView$GDViewDelegate$State, Runnable runnable) {
            if (this.currentState == gDView$GDViewDelegate$State) {
                return;
            }
            this.currentState = gDView$GDViewDelegate$State;
            runnable.run();
        }

        public void initInputUIData() {
        }

        public void onActionNext() {
        }

        public void onActivityCreate(Bundle bundle) {
        }

        public void onActivityCreateSafe(Bundle bundle) {
            checkStateAndExecute(GDView$GDViewDelegate$State.Created, new hbfhc(bundle));
        }

        public void onActivityDestroy() {
        }

        public void onActivityDestroySafe() {
            checkStateAndExecute(GDView$GDViewDelegate$State.Destroyed, new mjbm());
        }

        public void onActivityPause() {
        }

        public void onActivityPauseSafe() {
            checkStateAndExecute(GDView$GDViewDelegate$State.Paused, new ehnkx());
        }

        public void onActivityResume() {
        }

        public void onActivityResumeSafe() {
            checkStateAndExecute(GDView$GDViewDelegate$State.Resumed, new fdyxd());
        }

        public void onActivityStart() {
        }

        public void onActivityStartSafe() {
            checkStateAndExecute(GDView$GDViewDelegate$State.Started, new yfdke());
        }

        public void onActivityStop() {
        }

        public void onActivityStopSafe() {
            checkStateAndExecute(GDView$GDViewDelegate$State.Stopped, new pmoiy());
        }

        public void onSaveInstanceState(Bundle bundle) {
        }
    }

    /* loaded from: classes.dex */
    class aqdzk implements Runnable {
        final /* synthetic */ ViewGroup dbjc;
        final /* synthetic */ int jwxax;
        final /* synthetic */ View qkduk;

        aqdzk(GDView gDView, ViewGroup viewGroup, View view, int i) {
            this.dbjc = viewGroup;
            this.qkduk = view;
            this.jwxax = i;
        }

        @Override // java.lang.Runnable
        public void run() {
            int width = this.dbjc.getWidth();
            ViewGroup.LayoutParams layoutParams = this.qkduk.getLayoutParams();
            layoutParams.width = (width / 10) * this.jwxax;
            this.qkduk.setLayoutParams(layoutParams);
            this.qkduk.invalidate();
        }
    }

    /* loaded from: classes.dex */
    class ehnkx implements Runnable {
        final /* synthetic */ View dbjc;

        ehnkx(View view) {
            this.dbjc = view;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.dbjc.requestFocus();
            ((InputMethodManager) GDView.this.getContext().getSystemService("input_method")).showSoftInput(this.dbjc, 0);
        }
    }

    /* loaded from: classes.dex */
    class fdyxd implements TextView.OnEditorActionListener {
        final /* synthetic */ int dbjc;

        fdyxd(int i) {
            this.dbjc = i;
        }

        @Override // android.widget.TextView.OnEditorActionListener
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i == this.dbjc) {
                GDView.this._delegate.onActionNext();
                return true;
            }
            return true;
        }
    }

    /* loaded from: classes.dex */
    class gioey implements ViewTreeObserver.OnGlobalLayoutListener {
        gioey() {
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            GDView gDView = GDView.this;
            gDView.setBottomMarginVisibility(!gDView.isKeyboardVisible());
        }
    }

    /* loaded from: classes.dex */
    class hbfhc implements OnFocusChangeListener {
        hbfhc() {
        }

        @Override // android.view.View.OnFocusChangeListener
        public void onFocusChange(View view, boolean z) {
            GDView.this.setEditTextBackground(view, z);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class mjbm implements Runnable {
        final /* synthetic */ ImageView dbjc;
        final /* synthetic */ View jwxax;
        final /* synthetic */ int qkduk;

        mjbm(GDView gDView, ImageView imageView, int i, View view) {
            this.dbjc = imageView;
            this.qkduk = i;
            this.jwxax = view;
        }

        @Override // java.lang.Runnable
        public void run() {
            Rect rect = new Rect();
            this.dbjc.getHitRect(rect);
            int i = rect.top;
            int i2 = this.qkduk;
            rect.top = i - i2;
            rect.left -= i2;
            rect.bottom += i2;
            rect.right += i2;
            this.jwxax.setTouchDelegate(new TouchDelegate(rect, this.dbjc));
        }
    }

    /* loaded from: classes.dex */
    class orlrx implements ViewTreeObserver.OnGlobalLayoutListener {
        orlrx() {
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            GDView gDView = GDView.this;
            gDView.setHeaderMargin(gDView.isKeyboardVisible());
        }
    }

    /* loaded from: classes.dex */
    class pmoiy implements Runnable {
        final /* synthetic */ View dbjc;

        pmoiy(View view) {
            this.dbjc = view;
        }

        @Override // java.lang.Runnable
        public void run() {
            ((InputMethodManager) GDView.this.getContext().getSystemService("input_method")).hideSoftInputFromWindow(this.dbjc.getWindowToken(), 0);
        }
    }

    /* loaded from: classes.dex */
    class yfdke implements TextView.OnEditorActionListener {
        yfdke() {
        }

        @Override // android.widget.TextView.OnEditorActionListener
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i == 5) {
                GDView.this._delegate.onActionNext();
                return true;
            }
            return true;
        }
    }

    public GDView(Context context, ViewInteractor viewInteractor, ViewCustomizer viewCustomizer) {
        super(context);
        this.viewInteractor = null;
        this.viewInteractor = viewInteractor;
        this.customizedUI = viewCustomizer.getGDCustomizedUI();
        this.enterpriseModeChecker = viewCustomizer.getEnterpriseModeChecker();
        this.nocServerListProvider = viewCustomizer.getNocServerListProvider();
    }

    private void addNocOverrideOverlay() {
        TextView textView;
        String selectedNocServer = this.nocServerListProvider.getSelectedNocServer();
        if (!TextUtils.isEmpty(selectedNocServer) && (textView = (TextView) findViewById(R.id.gd_selected_noc_label)) != null) {
            textView.setText(selectedNocServer);
            textView.setVisibility(0);
        }
    }

    private Drawable getCustomFocusedEditTextBackground() {
        if (this.customizedUI.isUICustomized()) {
            Bitmap decodeResource = BitmapFactory.decodeResource(getResources(), R.drawable.gd_edit_text_color_background);
            byte[] ninePatchChunk = decodeResource.getNinePatchChunk();
            int pixel = decodeResource.getPixel(0, decodeResource.getHeight() - 1);
            Paint paint = new Paint();
            paint.setColorFilter(new LightingColorFilter(pixel, this.customizedUI.getCustomUIColor().intValue()));
            Bitmap copy = decodeResource.copy(Bitmap.Config.ARGB_8888, true);
            new Canvas(copy).drawBitmap(copy, 0.0f, 0.0f, paint);
            return new NinePatchDrawable(getResources(), copy, ninePatchChunk, vzw.dbjc(ninePatchChunk).dbjc, null);
        }
        throw new Error("This method should be called only in case of UI customization");
    }

    private int getLighterColor(int i) {
        return Color.argb(75, Color.red(i), Color.green(i), Color.blue(i));
    }

    private float getMaxTextWidthPixels(Button button) {
        return getResources().getDimensionPixelSize(R.dimen.gd_button_width) - (button.getPaddingLeft() + button.getPaddingRight());
    }

    private float getTextWidth(Button button) {
        String charSequence = button.getText().toString();
        Rect rect = new Rect();
        button.getPaint().getTextBounds(charSequence, 0, charSequence.length(), rect);
        return rect.width();
    }

    private boolean isDarkModeEnabled() {
        return (getContext().getResources().getConfiguration().uiMode & 48) == 32;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setBottomMarginVisibility(boolean z) {
        int i = z ? 0 : 8;
        View findViewById = findViewById(R.id.gd_bottom_margin);
        if (findViewById != null) {
            findViewById.setVisibility(i);
            if (!z || !isInMultiWindowMode() || this.footerHeightUpdated) {
                return;
            }
            this.footerHeightUpdated = true;
            findViewById.setLayoutParams(new LinearLayout.LayoutParams(-1, ((LinearLayout.LayoutParams) findViewById.getLayoutParams()).height / 2));
        }
    }

    private void setCursorPointerColor(EditText editText, int i) {
        if (Build.VERSION.SDK_INT >= 29) {
            for (Drawable drawable : Arrays.asList(editText.getTextSelectHandle(), editText.getTextSelectHandleLeft(), editText.getTextSelectHandleRight())) {
                if (drawable != null) {
                    setImageColor(drawable, i);
                }
            }
            return;
        }
        for (String str : Arrays.asList("mTextSelectHandleRes", "mTextSelectHandleLeftRes", "mTextSelectHandleRightRes")) {
            try {
                Field declaredField = TextView.class.getDeclaredField(str);
                declaredField.setAccessible(true);
                Drawable drawable2 = getContext().getDrawable(declaredField.getInt(editText));
                if (drawable2 != null) {
                    setImageColor(drawable2, i);
                }
            } catch (Exception e) {
                GTLog.DBGPRINTF(12, "GDView: setCursorPointerColor: exception " + e.toString() + "\n");
            }
        }
    }

    private void setDarkModeSettings() {
        Drawable drawable;
        if (isDarkModeEnabled()) {
            ImageView imageView = (ImageView) findViewById(R.id.gd_app_logo);
            if (imageView != null && (drawable = imageView.getDrawable()) != null) {
                setImageColor(drawable, -1);
                imageView.setImageDrawable(drawable);
            }
            View findViewById = findViewById(R.id.gd_progress_line);
            if (findViewById == null) {
                return;
            }
            LayoutParams layoutParams = (LayoutParams) findViewById.getLayoutParams();
            layoutParams.removeRule(3);
            layoutParams.addRule(12, -1);
            layoutParams.setMargins(0, 0, 0, 0);
            findViewById.setLayoutParams(layoutParams);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setHeaderMargin(boolean z) {
        if (getResources().getConfiguration().orientation != 1 && isTablet()) {
            View findViewById = findViewById(R.id.COM_GOOD_GD_SMALL_MARGIN);
            View findViewById2 = findViewById(R.id.COM_GOOD_GD_BIG_MARGIN);
            boolean isInMultiWindowMode = isInMultiWindowMode();
            if (!z) {
                findViewById.setVisibility(8);
                if (isInMultiWindowMode && !this.headerBigHeightUpdated) {
                    this.headerBigHeightUpdated = true;
                    findViewById2.setLayoutParams(new LinearLayout.LayoutParams(-1, ((LinearLayout.LayoutParams) findViewById2.getLayoutParams()).height / 2));
                }
                findViewById2.setVisibility(0);
                return;
            }
            findViewById2.setVisibility(8);
            if (isInMultiWindowMode && this.headerSmallHeightUpdated) {
                this.headerSmallHeightUpdated = true;
                findViewById.setLayoutParams(new LinearLayout.LayoutParams(-1, ((LinearLayout.LayoutParams) findViewById.getLayoutParams()).height / 2));
            }
            findViewById.setVisibility(0);
        }
    }

    public static void setImageColor(Drawable drawable, int i) {
        if (Build.VERSION.SDK_INT >= 29) {
            drawable.setColorFilter(new BlendModeColorFilter(i, BlendMode.SRC_ATOP));
        } else {
            drawable.setColorFilter(i, PorterDuff.Mode.SRC_ATOP);
        }
    }

    public void activityFinished() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void adjustHeaderPositioning() {
        ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        if (viewTreeObserver == null) {
            return;
        }
        viewTreeObserver.addOnGlobalLayoutListener(new orlrx());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void applySimulatedOrDebugMode() {
        TextView textView;
        if (!this.enterpriseModeChecker.enterpriseSimulationModeEnabled() || (textView = (TextView) findViewById(R.id.gd_simulation_label)) == null) {
            return;
        }
        textView.setText(GDLocalizer.getLocalizedString("[Simulated]"));
        textView.setVisibility(0);
        if (!this.customizedUI.isUICustomized()) {
            return;
        }
        textView.setTextColor(this.customizedUI.getCustomUIColor().intValue());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void applyUICustomization() {
        addNocOverrideOverlay();
        setDarkModeSettings();
        if (!this.customizedUI.isUICustomized()) {
            return;
        }
        ImageView imageView = (ImageView) findViewById(R.id.gd_app_logo);
        if (imageView != null) {
            imageView.setImageDrawable(this.customizedUI.getSmallApplicationLogo());
        }
        TextView textView = (TextView) findViewById(R.id.gd_bottom_line_action_label);
        if (textView != null) {
            textView.setTextColor(this.customizedUI.getCustomUIColor().intValue());
        }
        ImageView imageView2 = (ImageView) findViewById(R.id.gd_secure_logo);
        if (imageView2 != null) {
            imageView2.setVisibility(0);
        }
        View findViewById = findViewById(R.id.gd_progress_line);
        if (findViewById != null) {
            findViewById.setBackgroundColor(this.customizedUI.getCustomUIColor().intValue());
        }
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.gd_spinner);
        if (progressBar != null) {
            progressBar.setIndeterminateDrawable(getCustomizedDrawable(R.drawable.gd_progress_bar));
        }
        ImageView imageView3 = (ImageView) findViewById(R.id.gd_help);
        if (imageView3 == null) {
            return;
        }
        imageView3.setImageDrawable(getCustomizedDrawable(R.drawable.help));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void checkFieldNotNull(View view, String str, String str2) {
        if (view != null) {
            return;
        }
        throw new GDInitializationError("layout/" + str + ": " + str2 + " missing or wrong type");
    }

    public void clearSensitiveData() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void customizeEditTextField(EditText editText, int i) {
        editText.setHighlightColor(getLighterColor(i));
        setCursorPointerColor(editText, i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void disableBottomButton(BaseUI baseUI) {
        if (baseUI.hasBottomButton()) {
            setBottomLabelTextAndAction(baseUI.getLocalizedBottomButton(), null);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void enableBottomButton(BaseUI baseUI) {
        if (baseUI.hasBottomButton()) {
            setBottomLabelTextAndAction(baseUI.getLocalizedBottomButton(), BBDUIHelper.getClickListenerForMessageType(baseUI, BBDUIMessageType.MSG_UI_BOTTOM_BUTTON));
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void enableBottomLine() {
        ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        if (viewTreeObserver == null) {
            return;
        }
        viewTreeObserver.addOnGlobalLayoutListener(new gioey());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void enableHelpButton(BaseUI baseUI) {
        if (baseUI.hasHelpButton()) {
            setupHelpButton(BBDUIHelper.getClickListenerForMessageType(baseUI, BBDUIMessageType.MSG_UI_HELP));
        }
    }

    protected void enableImageButton(ImageButton imageButton, boolean z) {
        imageButton.setEnabled(z);
        if (z) {
            imageButton.setImageAlpha(255);
        } else {
            imageButton.setImageAlpha(128);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Drawable getCustomizedDrawable(int i) {
        Drawable drawable = getResources().getDrawable(i, getContext().getTheme());
        if (this.customizedUI.isUICustomized() && drawable != null && this.customizedUI.getCustomUIColor() != null) {
            setImageColor(drawable, this.customizedUI.getCustomUIColor().intValue());
        }
        return drawable;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public float getFloatValueFromResource(int i) {
        TypedValue typedValue = new TypedValue();
        getResources().getValue(i, typedValue, true);
        return typedValue.getFloat();
    }

    public boolean hasTextContainers() {
        return this.hasTextContainers;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void inflateLayout(int i, ViewGroup viewGroup) {
        LayoutInflater layoutInflater;
        Context context = getContext();
        if (context != null) {
            layoutInflater = LayoutInflater.from(context);
        } else {
            layoutInflater = this.viewInteractor.getLayoutInflater();
        }
        layoutInflater.inflate(i, viewGroup);
        applySimulatedOrDebugMode();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isInMultiWindowMode() {
        Activity activity = (Activity) getContext();
        if (activity == null) {
            return false;
        }
        return activity.isInMultiWindowMode();
    }

    protected boolean isKeyboardVisible() {
        Activity activity = (Activity) getContext();
        if (activity != null) {
            return activity.getWindow().getDecorView().getHeight() - getHeight() >= 100;
        }
        throw new Error("this case is impossible");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isTablet() {
        return (getResources().getConfiguration().screenLayout & 15) >= 3;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void moveTaskToBack() {
        this.viewInteractor.moveTaskToBack();
    }

    public void onActivityResult(int i, int i2, Intent intent) {
    }

    public void onBackPressed() {
    }

    public void onPermissions(int i, String[] strArr, int[] iArr) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void rearrangeButton(Button button) {
        if (getTextWidth(button) > getMaxTextWidthPixels(button)) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) button.getLayoutParams();
            layoutParams.width = -2;
            button.setLayoutParams(layoutParams);
        }
    }

    public void requestHideKeyboard() {
    }

    public void requestHideKeyboard(View view) {
        post(new pmoiy(view));
    }

    public void requestShowKeyboard() {
    }

    public void requestShowKeyboard(View view) {
        post(new ehnkx(view));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void sendMessageToService(Message message) {
        this.viewInteractor.sendMessage(message);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setBottomLabelTextAndAction(String str, OnClickListener onClickListener) {
        TextView textView = (TextView) findViewById(R.id.gd_bottom_line_action_label);
        textView.setVisibility(0);
        textView.setText(str);
        if (onClickListener != null) {
            textView.setOnClickListener(onClickListener);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setBottomLabelVisibility(int i) {
        findViewById(R.id.gd_bottom_line_action_label).setVisibility(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setBottomLineBackground(boolean z) {
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.bbd_common_header_layout);
        if (viewGroup != null) {
            if (z) {
                viewGroup.setBackgroundColor(getResources().getColor(R.color.bbd_background_color));
            } else {
                viewGroup.setVisibility(8);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setButtonEnabled(Button button, boolean z) {
        if (z) {
            button.setAlpha(getFloatValueFromResource(R.dimen.gd_text_opaque));
        } else {
            button.setAlpha(getFloatValueFromResource(R.dimen.gd_button_opaque));
        }
        button.setEnabled(z);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setEditTextBackground(View view, boolean z) {
        if (!z) {
            view.setBackgroundResource(R.drawable.gd_edit_text_gray_background);
        } else if (!this.customizedUI.isUICustomized()) {
            view.setBackgroundResource(R.drawable.gd_edit_text_red_background);
        } else {
            view.setBackground(getCustomFocusedEditTextBackground());
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setHasTextContainers(boolean z) {
        this.hasTextContainers = z;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setLastEditTextForAction(EditText editText, int i) {
        editText.setOnEditorActionListener(new fdyxd(i));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setLastEditTextForActionNext(EditText editText) {
        editText.setOnEditorActionListener(new yfdke());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setProgressLine(int i) {
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.bbd_base_header);
        View findViewById = findViewById(R.id.gd_top_header_shadow);
        View findViewById2 = findViewById(R.id.gd_progress_line);
        if (findViewById2 != null) {
            findViewById2.setVisibility(0);
            if (!isDarkModeEnabled() && findViewById != null) {
                findViewById.setVisibility(0);
            }
            findViewById2.post(new aqdzk(this, viewGroup, findViewById2, i));
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setTopHeaderShadow() {
        View findViewById = findViewById(R.id.gd_top_header_shadow);
        if (findViewById == null || isDarkModeEnabled()) {
            return;
        }
        findViewById.setVisibility(0);
    }

    protected void setupHelpButton(OnClickListener onClickListener) {
        ImageView imageView = (ImageView) findViewById(R.id.gd_help);
        View view = (View) imageView.getParent();
        view.post(new mjbm(this, imageView, (int) TypedValue.applyDimension(1, 20.0f, Resources.getSystem().getDisplayMetrics()), view));
        imageView.setVisibility(0);
        if (onClickListener != null) {
            imageView.setOnClickListener(onClickListener);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void showPopupDialog(String str, String str2, String str3, DialogInterface.OnClickListener onClickListener) {
        GDDialogState.getInstance().showPopupDialog(this.viewInteractor, str, str2, str3, onClickListener);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void showPopupDialog2(String str, String str2, String str3, DialogInterface.OnClickListener onClickListener, String str4, DialogInterface.OnClickListener onClickListener2) {
        GDDialogState.getInstance().showPopupDialog2(this.viewInteractor, str, str2, str3, onClickListener, str4, onClickListener2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void showPopupDialog4(String str, String str2, boolean z) {
        GDDialogState.getInstance().showPopupDialog4(this.viewInteractor, str, str2, z);
    }

    public void stateWasUpdated() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class vzw {
        public final Rect dbjc = new Rect();

        private vzw() {
        }

        private static void dbjc(int[] iArr, ByteBuffer byteBuffer) {
            int length = iArr.length;
            for (int i = 0; i < length; i++) {
                iArr[i] = byteBuffer.getInt();
            }
        }

        private static void dbjc(int i) {
            if (i == 0 || (i & 1) != 0) {
                throw new RuntimeException("invalid nine-patch: " + i);
            }
        }

        static /* synthetic */ vzw dbjc(byte[] bArr) {
            ByteBuffer order = ByteBuffer.wrap(bArr).order(ByteOrder.nativeOrder());
            if (order.get() == 0) {
                return null;
            }
            vzw vzwVar = new vzw();
            int i = order.get();
            int[] iArr = new int[i];
            int i2 = order.get();
            dbjc(i);
            dbjc(i2);
            order.getInt();
            order.getInt();
            vzwVar.dbjc.left = order.getInt();
            vzwVar.dbjc.right = order.getInt();
            vzwVar.dbjc.top = order.getInt();
            vzwVar.dbjc.bottom = order.getInt();
            order.getInt();
            dbjc(iArr, order);
            dbjc(new int[i2], order);
            dbjc(new int[order.get()], order);
            return vzwVar;
        }
    }
}
