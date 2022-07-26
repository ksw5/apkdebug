package com.good.gd.widget;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStructure;
import android.view.autofill.AutofillValue;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.good.gd.content_Impl.GDClipboardUtils;
import com.good.gd.ndkproxy.GDLog;

/* loaded from: classes.dex */
public class GDWebView extends WebView {
    private ActionMode.Callback mActionModeCallback = new hbfhc(this);
    private GDClipboardUtils mGDClipboardUtils;

    /* loaded from: classes.dex */
    class hbfhc implements ActionMode.Callback {
        hbfhc(GDWebView gDWebView) {
        }

        @Override // android.view.ActionMode.Callback
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return true;
        }

        @Override // android.view.ActionMode.Callback
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            return true;
        }

        @Override // android.view.ActionMode.Callback
        public void onDestroyActionMode(ActionMode actionMode) {
        }

        @Override // android.view.ActionMode.Callback
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class yfdke implements OnLongClickListener {
        yfdke(GDWebView gDWebView) {
        }

        @Override // android.view.View.OnLongClickListener
        public boolean onLongClick(View view) {
            return true;
        }
    }

    public GDWebView(Context context) {
        super(context);
        commonInit(context);
    }

    private void commonInit(Context context) {
        if (Build.VERSION.SDK_INT >= 26) {
            GDLog.DBGPRINTF(16, "GDWebView: Diagnostic info - start\n");
            PackageInfo currentWebViewPackage = WebView.getCurrentWebViewPackage();
            GDLog.DBGPRINTF(16, "GDWebView: WebView package info =", currentWebViewPackage.packageName, " ", currentWebViewPackage.versionName);
            getAutofillType();
            getAutofillValue();
            getImportantForAutofill();
            getRendererPriorityWaivedWhenNotVisible();
            getRendererRequestedPriority();
            GDLog.DBGPRINTF(16, "GDWebView: Diagnostic info - end\n");
        }
        this.mGDClipboardUtils = GDClipboardUtils.getInstance(context);
    }

    private void disableLongPress() {
        setOnLongClickListener(new yfdke(this));
        setLongClickable(false);
    }

    private boolean isCopyPasteAllowed() {
        GDClipboardUtils gDClipboardUtils = this.mGDClipboardUtils;
        if (gDClipboardUtils != null) {
            return gDClipboardUtils.isPasteFromNonGDAppEnabled() && (this.mGDClipboardUtils.isGDSecureClipBoardEnabled() ^ true);
        }
        return false;
    }

    private static boolean isCopyPasteEvent(KeyEvent keyEvent) {
        return keyEvent.isCtrlPressed() && (keyEvent.getKeyCode() == 31 || keyEvent.getKeyCode() == 50 || keyEvent.getKeyCode() == 52);
    }

    private void setupLongPressState() {
        GDClipboardUtils gDClipboardUtils = this.mGDClipboardUtils;
        if (gDClipboardUtils != null && !gDClipboardUtils.isGDSecureClipBoardEnabled()) {
            if (isLongClickable()) {
                return;
            }
            setOnLongClickListener(null);
            setLongClickable(true);
            return;
        }
        disableLongPress();
    }

    @Override // android.view.View
    public final void autofill(AutofillValue autofillValue) {
        GDLog.DBGPRINTF(16, "GDWebView: autofill() - returned value: " + autofillValue.toString());
    }

    @Override // android.webkit.WebView, android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (!isCopyPasteEvent(keyEvent) || isCopyPasteAllowed()) {
            return super.dispatchKeyEvent(keyEvent);
        }
        return true;
    }

    @Override // android.view.View
    public final int getAutofillType() {
        GDLog.DBGPRINTF(16, "GDWebView: getAutofillType()\n");
        return 0;
    }

    @Override // android.view.View
    public final AutofillValue getAutofillValue() {
        GDLog.DBGPRINTF(16, "GDWebView: getAutofillValue() - returned value NULL\n");
        return null;
    }

    @Override // android.view.View
    public final int getImportantForAutofill() {
        GDLog.DBGPRINTF(16, "GDWebView: autofill() - returned value: IMPORTANT_FOR_AUTOFILL_NO_EXCLUDE_DESCENDANTS");
        return 8;
    }

    @Override // android.webkit.WebView
    public boolean getRendererPriorityWaivedWhenNotVisible() {
        boolean rendererPriorityWaivedWhenNotVisible = super.getRendererPriorityWaivedWhenNotVisible();
        GDLog.DBGPRINTF(16, "GDWebView: getRendererPriorityWaivedWhenNotVisible - returned value: " + rendererPriorityWaivedWhenNotVisible);
        return rendererPriorityWaivedWhenNotVisible;
    }

    @Override // android.webkit.WebView
    public int getRendererRequestedPriority() {
        int rendererRequestedPriority = super.getRendererRequestedPriority();
        GDLog.DBGPRINTF(16, "GDWebView: getRendererRequestedPriority - returned value: " + rendererRequestedPriority);
        return rendererRequestedPriority;
    }

    @Override // android.webkit.WebView
    public WebChromeClient getWebChromeClient() {
        WebChromeClient webChromeClient = super.getWebChromeClient();
        GDLog.DBGPRINTF(16, "GDWebView: getWebChromeClient - returned value: " + webChromeClient.toString());
        return webChromeClient;
    }

    @Override // android.webkit.WebView
    public WebViewClient getWebViewClient() {
        GDLog.DBGPRINTF(16, "GDWebView: getWebViewClient - returned value: " + super.getWebViewClient().toString());
        return super.getWebViewClient();
    }

    @Override // android.webkit.WebView, android.view.View
    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        return GDDLPKeyboardControl.getInstance().configureInputConnection(super.onCreateInputConnection(editorInfo), getContext());
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        setupLongPressState();
    }

    @Override // android.webkit.WebView, android.view.View
    public void onProvideAutofillVirtualStructure(ViewStructure viewStructure, int i) {
        GDLog.DBGPRINTF(16, "GDWebView: onProvideAutofillVirtualStructure()\n");
        super.onProvideAutofillVirtualStructure(viewStructure, i);
    }

    @Override // android.webkit.WebView, android.view.View
    protected void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        if (i == 0) {
            setupLongPressState();
        }
    }

    @Override // android.webkit.WebView, android.view.View
    public boolean performLongClick() {
        setupLongPressState();
        return super.performLongClick();
    }

    @Override // android.webkit.WebView
    public void setRendererPriorityPolicy(int i, boolean z) {
        GDLog.DBGPRINTF(16, "GDWebView: setRendererPriorityPolicy()\n", "rendererRequestedPriority = " + i, "waivedWhenNotVisible = " + z);
        super.setRendererPriorityPolicy(i, z);
    }

    @Override // android.view.View
    public ActionMode startActionMode(ActionMode.Callback callback) {
        if (isCopyPasteAllowed()) {
            return super.startActionMode(callback);
        }
        return super.startActionMode(this.mActionModeCallback);
    }

    @Override // android.webkit.WebView, android.view.View
    public final void autofill(SparseArray<AutofillValue> sparseArray) {
        GDLog.DBGPRINTF(16, "GDWebView: autofill()\n");
    }

    @Override // android.view.View
    public ActionMode startActionMode(ActionMode.Callback callback, int i) {
        if (isCopyPasteAllowed()) {
            return super.startActionMode(callback, i);
        }
        return super.startActionMode(this.mActionModeCallback, i);
    }

    public GDWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        commonInit(context);
    }

    public GDWebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        commonInit(context);
    }

    public GDWebView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        commonInit(context);
    }

    public GDWebView(Context context, AttributeSet attributeSet, int i, boolean z) {
        super(context, attributeSet, i, z);
        commonInit(context);
    }
}
