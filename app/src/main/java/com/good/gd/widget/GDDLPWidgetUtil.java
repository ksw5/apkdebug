package com.good.gd.widget;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.SuggestionSpan;
import android.view.ActionMode;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.EditText;
import android.widget.TextView;
import com.good.gd.apache.http.protocol.HTTP;
import com.good.gd.content.ClipboardManager;
import com.good.gd.content_Impl.GDClipData;
import com.good.gd.content_Impl.GDClipboardManagerImpl;
import com.good.gd.content_Impl.GDClipboardUtils;
import com.good.gd.dlp_util.ActionModeControl;
import com.good.gd.dlp_util.ActionModeHolder;
import com.good.gd.dlp_util.GDCustomROMDLPUtil;
import com.good.gd.dlp_util.TransformedText;
import com.good.gd.error.GDNotAuthorizedError;
import com.good.gd.ndkproxy.GDLog;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class GDDLPWidgetUtil {
    private aqdzk jcpqe;
    private final Context jwxax;
    private final GDClipboardUtils liflu;
    private final ClipboardManager lqox;
    private final ActionMode.Callback odlf;
    private final TextView qkduk;
    private final Set<EditAction> tlske;
    private final ActionModeControl yrp;
    private View.OnLongClickListener ztwf;
    private final String dbjc = new String("com.good.launcher/launcher-data");
    private boolean jsgtu = false;
    private boolean wuird = false;
    final TransformedText mvf = new TransformedText();
    private View.OnLongClickListener wxau = wxau();

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public interface aqdzk {
        void dbjc(View.OnLongClickListener onLongClickListener);
    }

    /* loaded from: classes.dex */
    private static class ehnkx {
        TextView dbjc;
        int jwxax;
        int qkduk;

        ehnkx(TextView textView, int i, int i2) {
            this.dbjc = textView;
            this.qkduk = i;
            this.jwxax = i2;
        }
    }

    /* loaded from: classes.dex */
    private class fdyxd {
        private int dbjc;
        private int qkduk;

        fdyxd(GDDLPWidgetUtil gDDLPWidgetUtil) {
            this.dbjc = 0;
            this.qkduk = 0;
            int selectionStart = gDDLPWidgetUtil.qkduk.getSelectionStart();
            int selectionEnd = gDDLPWidgetUtil.qkduk.getSelectionEnd();
            this.dbjc = Math.max(0, Math.min(selectionStart, selectionEnd));
            this.qkduk = Math.max(0, Math.max(selectionStart, selectionEnd));
        }

        int dbjc() {
            return this.qkduk;
        }

        int qkduk() {
            return this.dbjc;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class hbfhc implements View.OnLongClickListener {
        hbfhc() {
        }

        @Override // android.view.View.OnLongClickListener
        public boolean onLongClick(View view) {
            GDLog.DBGPRINTF(14, "GDDLPWidgetUtil onLongClick\n");
            if (GDDLPWidgetUtil.this.qkduk.isFocused()) {
                GDLog.DBGPRINTF(16, "GDDLPWidgetUtil viewIsFocussed\n");
                fdyxd fdyxdVar = new fdyxd(GDDLPWidgetUtil.this);
                if (fdyxdVar.qkduk() != fdyxdVar.dbjc()) {
                    ClipData newDefaultClipData = GDClipData.newDefaultClipData(GDDLPWidgetUtil.this.jwxax(fdyxdVar.qkduk(), fdyxdVar.dbjc()));
                    GDLog.DBGPRINTF(16, "GDDLPWidgetUtil selectionStart = " + fdyxdVar.qkduk() + "selectionEnd = " + fdyxdVar.dbjc() + "\n");
                    try {
                        GDDLPWidgetUtil.this.lqox.startDragAndDrop(newDefaultClipData, view, null, new ehnkx(GDDLPWidgetUtil.this.qkduk, GDDLPWidgetUtil.this.qkduk.getSelectionStart(), GDDLPWidgetUtil.this.qkduk.getSelectionEnd()));
                    } catch (GDNotAuthorizedError e) {
                        GDLog.DBGPRINTF(12, "GDDLPWidgetUtil OnLongClickListener.onLongClick", e.getMessage());
                        return true;
                    }
                }
            }
            if (GDDLPWidgetUtil.this.ztwf != null) {
                return GDDLPWidgetUtil.this.ztwf.onLongClick(view);
            }
            if (!GDCustomROMDLPUtil.shouldExplicitlyBlockAllDlpActions()) {
                return false;
            }
            view.startActionMode(GDDLPWidgetUtil.this.odlf);
            return true;
        }
    }

    /* loaded from: classes.dex */
    enum orlrx {
        RETURN_FALSE,
        RETURN_TRUE,
        RETURN_SUPER
    }

    /* loaded from: classes.dex */
    private class pmoiy implements ActionMode.Callback {
        private pmoiy() {
        }

        @Override // android.view.ActionMode.Callback
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return GDDLPWidgetUtil.this.jwxax(menuItem.getItemId());
        }

        @Override // android.view.ActionMode.Callback
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            return !GDCustomROMDLPUtil.shouldExplicitlyBlockAllDlpActions();
        }

        @Override // android.view.ActionMode.Callback
        public void onDestroyActionMode(ActionMode actionMode) {
        }

        @Override // android.view.ActionMode.Callback
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            if (GDDLPWidgetUtil.this.liflu.isGDSecureClipBoardEnabled()) {
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < menu.size(); i++) {
                    MenuItem item = menu.getItem(i);
                    if (item.getItemId() != 16908320 && item.getItemId() != 16908322 && item.getItemId() != 16908321 && item.getItemId() != 16908319) {
                        arrayList.add(Integer.valueOf(item.getItemId()));
                    }
                }
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    menu.removeItem(((Integer) it.next()).intValue());
                }
                return true;
            }
            return false;
        }

        /* synthetic */ pmoiy(GDDLPWidgetUtil gDDLPWidgetUtil, hbfhc hbfhcVar) {
            this();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class yfdke extends View.AccessibilityDelegate {
        yfdke(GDDLPWidgetUtil gDDLPWidgetUtil) {
        }

        @Override // android.view.View.AccessibilityDelegate
        public void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            super.onPopulateAccessibilityEvent(view, accessibilityEvent);
            accessibilityEvent.getText().clear();
        }

        @Override // android.view.View.AccessibilityDelegate
        public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
            if (i == 2097152) {
                GDLog.DBGPRINTF(13, "GDDLPWidgetUtil action = ACTION_SET_TEXT ignored\n");
                return false;
            }
            return super.performAccessibilityAction(view, i, bundle);
        }
    }

    public GDDLPWidgetUtil(TextView textView, aqdzk aqdzkVar) {
        this.qkduk = textView;
        this.jcpqe = aqdzkVar;
        Context context = textView.getContext();
        this.jwxax = context;
        this.lqox = ClipboardManager.getInstance(context);
        this.liflu = GDClipboardUtils.getInstance(context);
        HashSet hashSet = new HashSet();
        this.tlske = hashSet;
        hashSet.addAll(Arrays.asList(EditAction.values()));
        qkduk();
        textView.addTextChangedListener(new mjbm(this, null));
        dbjc(textView);
        if (GDCustomROMDLPUtil.shouldExplicitlyBlockAllDlpActions()) {
            textView.setTextIsSelectable(false);
        }
        ActionModeHolder actionModeHolder = new ActionModeHolder(new pmoiy(this, null));
        this.odlf = actionModeHolder;
        this.yrp = actionModeHolder;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public CharSequence jwxax(int i, int i2) {
        Spannable spannableString;
        CharSequence subSequence = jwxax().subSequence(i, i2);
        if (subSequence instanceof Spanned) {
            if (subSequence instanceof Spannable) {
                spannableString = (Spannable) subSequence;
            } else {
                spannableString = new SpannableString(subSequence);
                subSequence = spannableString;
            }
            for (SuggestionSpan suggestionSpan : (SuggestionSpan[]) spannableString.getSpans(0, subSequence.length(), SuggestionSpan.class)) {
                spannableString.removeSpan(suggestionSpan);
            }
        }
        return subSequence;
    }

    private View.OnLongClickListener wxau() {
        return new hbfhc();
    }

    public void qkduk(int i) {
        if (i == 0) {
            GDDLPKeyboardControl.getInstance().configureDLPKeyboardOptions(this.qkduk);
            qkduk();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class mjbm implements TextWatcher {
        String dbjc;
        boolean qkduk;

        /* loaded from: classes.dex */
        class hbfhc extends AsyncTask<Void, Void, Void> {
            hbfhc() {
            }

            @Override // android.os.AsyncTask
            protected Void doInBackground(Void[] voidArr) {
                GDDLPWidgetUtil.this.qkduk.post(new com.good.gd.widget.hbfhc(this));
                return null;
            }
        }

        private mjbm() {
            this.dbjc = null;
            this.qkduk = false;
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            if (!GDDLPWidgetUtil.this.liflu.isPasteFromNonGDAppEnabled() && this.qkduk && !GDDLPWidgetUtil.this.jsgtu && this.dbjc != null) {
                new hbfhc().execute(new Void[0]);
            }
            if (GDDLPWidgetUtil.this.jsgtu) {
                GDDLPWidgetUtil.this.jsgtu = false;
            }
            if (this.qkduk) {
                this.qkduk = false;
            }
        }

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            StackTraceElement[] stackTrace;
            if (GDDLPWidgetUtil.this.liflu.isPasteFromNonGDAppEnabled() || GDDLPWidgetUtil.this.jsgtu) {
                return;
            }
            boolean z = true;
            if (i3 - i2 <= 1) {
                return;
            }
            for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
                if (stackTraceElement.getMethodName().equals("pasteClipBoardData") || stackTraceElement.getMethodName().equals("pasteCipBoardData")) {
                    GDLog.DBGPRINTF(14, "GDDLPWidgetUtil isSamsungPaste\n");
                    break;
                }
            }
            z = false;
            this.qkduk = z;
            if (!z) {
                return;
            }
            this.dbjc = GDDLPWidgetUtil.this.qkduk.getText().toString();
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        /* synthetic */ mjbm(GDDLPWidgetUtil gDDLPWidgetUtil, hbfhc hbfhcVar) {
            this();
        }
    }

    public void dbjc(int i, int i2) {
        if (i > 0) {
            GDClipboardManagerImpl.getInstance().reportUserActivity();
        }
        if (GDCustomROMDLPUtil.shouldExplicitlyBlockAllDlpActions()) {
            if (i != i2) {
                TextView textView = this.qkduk;
                if (textView instanceof EditText) {
                    ((EditText) textView).setSelection(i2);
                }
            }
            this.yrp.stopActionMode();
        }
    }

    private void qkduk() {
        aqdzk aqdzkVar;
        TextView textView = this.qkduk;
        if (!(!(textView instanceof GDTextView) || textView.isTextSelectable()) || (aqdzkVar = this.jcpqe) == null) {
            return;
        }
        aqdzkVar.dbjc(this.wxau);
    }

    private void qkduk(int i, int i2) {
        Editable qkduk = qkduk(this.qkduk);
        if (qkduk != null) {
            qkduk.delete(i, i2);
        }
    }

    private CharSequence jwxax() {
        return this.mvf.getTransformed(this.qkduk);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean jwxax(int i) {
        int i2;
        String charSequence;
        int length = this.qkduk.getText().length();
        if (!this.qkduk.isFocused()) {
            i2 = 0;
        } else {
            int selectionStart = this.qkduk.getSelectionStart();
            int selectionEnd = this.qkduk.getSelectionEnd();
            i2 = Math.max(0, Math.min(selectionStart, selectionEnd));
            length = Math.max(0, Math.max(selectionStart, selectionEnd));
        }
        if (i == 16908337) {
            if (!this.tlske.contains(EditAction.PASTE_AS_PLAIN_TEXT)) {
                return false;
            }
            dbjc(i2, length, false);
            return true;
        } else if (i != 16908341) {
            switch (i) {
                case 16908319:
                    if (!this.tlske.contains(EditAction.SELECT_ALL)) {
                        return false;
                    }
                    CharSequence text = this.qkduk.getText();
                    Selection.setSelection((Spannable) text, 0, text.length());
                    return true;
                case 16908320:
                    if (!this.tlske.contains(EditAction.CUT)) {
                        return false;
                    }
                    if (dbjc(jwxax(i2, length))) {
                        qkduk(i2, length);
                    }
                    this.yrp.stopActionMode();
                    return true;
                case 16908321:
                    if (!this.tlske.contains(EditAction.COPY)) {
                        return false;
                    }
                    dbjc(jwxax(i2, length));
                    this.yrp.stopActionMode();
                    return true;
                case 16908322:
                    if (!this.tlske.contains(EditAction.PASTE)) {
                        return false;
                    }
                    dbjc(i2, length, true);
                    return true;
                default:
                    return false;
            }
        } else {
            if (!this.liflu.isGDSecureClipBoardEnabled() && (charSequence = jwxax(i2, length).toString()) != null && !charSequence.isEmpty()) {
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType(HTTP.PLAIN_TEXT_TYPE);
                intent.removeExtra("android.intent.extra.TEXT");
                intent.putExtra("android.intent.extra.TEXT", charSequence);
                this.qkduk.getContext().startActivity(Intent.createChooser(intent, null));
                this.yrp.stopActionMode();
            }
            return true;
        }
    }

    private Editable qkduk(TextView textView) {
        if (!textView.onCheckIsTextEditor() || !(textView.getText() instanceof Editable)) {
            return null;
        }
        return (Editable) textView.getText();
    }

    public boolean dbjc(int i) {
        return jwxax(i);
    }

    public void dbjc(EditAction editAction, boolean z) {
        if (z) {
            this.tlske.add(editAction);
        } else {
            this.tlske.remove(editAction);
        }
    }

    public orlrx dbjc(DragEvent dragEvent) {
        int offsetForPosition;
        int action = dragEvent.getAction();
        if (action != 1) {
            if (action != 2) {
                if (action != 3) {
                    return orlrx.RETURN_SUPER;
                }
                try {
                    ClipData clipData = this.lqox.getClipData(dragEvent);
                    if (clipData != null) {
                        ehnkx ehnkxVar = null;
                        Object localState = dragEvent.getLocalState();
                        if (localState != null && (localState instanceof ehnkx)) {
                            ehnkxVar = (ehnkx) localState;
                        }
                        if (ehnkxVar != null) {
                            TextView textView = ehnkxVar.dbjc;
                            TextView textView2 = this.qkduk;
                            if (textView == textView2 && (offsetForPosition = textView2.getOffsetForPosition(dragEvent.getX(), dragEvent.getY())) >= ehnkxVar.qkduk && offsetForPosition < ehnkxVar.jwxax) {
                                return orlrx.RETURN_TRUE;
                            }
                        }
                        fdyxd fdyxdVar = new fdyxd(this);
                        dbjc(fdyxdVar.qkduk(), fdyxdVar.dbjc(), clipData, false, true);
                        return orlrx.RETURN_TRUE;
                    }
                } catch (GDNotAuthorizedError e) {
                    GDLog.DBGPRINTF(12, "GDDLPWidgetUtil onDragEvent ACTION_DROP", e.getMessage());
                    return orlrx.RETURN_TRUE;
                }
            }
            return orlrx.RETURN_TRUE;
        }
        ClipDescription clipDescription = dragEvent.getClipDescription();
        if (clipDescription != null && clipDescription.hasMimeType(this.dbjc)) {
            return orlrx.RETURN_FALSE;
        }
        return orlrx.RETURN_TRUE;
    }

    public orlrx dbjc(MotionEvent motionEvent) {
        if (GDCustomROMDLPUtil.isInDeskTopMode(this.jwxax)) {
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked == 0) {
                this.wuird = true;
                return orlrx.RETURN_SUPER;
            } else if (actionMasked == 2) {
                if (this.wuird) {
                    this.wuird = false;
                    GDLog.DBGPRINTF(16, "GDDLPWidgetUtil ACTION_MOVE = PROCESSING \n");
                    fdyxd fdyxdVar = new fdyxd(this);
                    if (fdyxdVar.qkduk() != fdyxdVar.dbjc()) {
                        ClipData newDefaultClipData = GDClipData.newDefaultClipData(jwxax(fdyxdVar.qkduk(), fdyxdVar.dbjc()));
                        GDLog.DBGPRINTF(16, "GDDLPWidgetUtil selectionStart = " + fdyxdVar.qkduk() + "selectionEnd = " + fdyxdVar.dbjc() + "\n");
                        try {
                            this.lqox.startDragAndDrop(newDefaultClipData, this.qkduk, null, new ehnkx(this.qkduk, this.qkduk.getSelectionStart(), this.qkduk.getSelectionEnd()));
                        } catch (GDNotAuthorizedError e) {
                            GDLog.DBGPRINTF(12, "GDDLPWidgetUtil onTouchEvent", e.getMessage());
                            return orlrx.RETURN_TRUE;
                        }
                    }
                } else {
                    GDLog.DBGPRINTF(16, "GDDLPWidgetUtil ACTION_MOVE = NOT PROCESSING \n");
                }
                return orlrx.RETURN_TRUE;
            }
        }
        return orlrx.RETURN_SUPER;
    }

    public void dbjc(View.OnLongClickListener onLongClickListener) {
        this.ztwf = onLongClickListener;
    }

    private boolean dbjc(CharSequence charSequence) {
        try {
            this.lqox.setPrimaryClip(GDClipData.newDefaultClipData(charSequence));
            return true;
        } catch (GDNotAuthorizedError e) {
            GDLog.DBGPRINTF(12, "GDDLPWidgetUtil copy", e.getMessage());
            return false;
        } catch (IllegalArgumentException e2) {
            GDLog.DBGPRINTF(12, "GDDLPWidgetUtil copy", e2.getMessage());
            return false;
        }
    }

    private void dbjc(int i, int i2, ClipData clipData, boolean z, boolean z2) {
        CharSequence coerceToText;
        if (clipData != null) {
            this.jsgtu = true;
            boolean z3 = false;
            for (int i3 = 0; i3 < clipData.getItemCount(); i3++) {
                if (z2) {
                    coerceToText = clipData.getItemAt(i3).coerceToStyledText(this.qkduk.getContext());
                } else {
                    coerceToText = clipData.getItemAt(i3).coerceToText(this.qkduk.getContext());
                    if (coerceToText instanceof Spanned) {
                        coerceToText = coerceToText.toString();
                    }
                }
                if (coerceToText != null) {
                    if (!z3) {
                        if (coerceToText.length() > 0) {
                            if (i > 0) {
                                int i4 = i - 1;
                                char charAt = jwxax().charAt(i4);
                                char charAt2 = coerceToText.charAt(0);
                                if (Character.isSpaceChar(charAt) && Character.isSpaceChar(charAt2)) {
                                    int length = this.qkduk.getText().length();
                                    qkduk(i4, i);
                                    int length2 = this.qkduk.getText().length() - length;
                                    i += length2;
                                    i2 += length2;
                                } else if (!Character.isSpaceChar(charAt) && charAt != '\n' && !Character.isSpaceChar(charAt2) && charAt2 != '\n') {
                                    int length3 = this.qkduk.getText().length();
                                    Editable qkduk = qkduk(this.qkduk);
                                    if (qkduk != null) {
                                        qkduk.replace(i, i, " ");
                                    }
                                    int length4 = this.qkduk.getText().length() - length3;
                                    i += length4;
                                    i2 += length4;
                                }
                            }
                            if (i2 < this.qkduk.getText().length()) {
                                char charAt3 = coerceToText.charAt(coerceToText.length() - 1);
                                char charAt4 = jwxax().charAt(i2);
                                if (Character.isSpaceChar(charAt3) && Character.isSpaceChar(charAt4)) {
                                    qkduk(i2, i2 + 1);
                                } else if (!Character.isSpaceChar(charAt3) && charAt3 != '\n' && !Character.isSpaceChar(charAt4) && charAt4 != '\n') {
                                    dbjc(i2, i2, " ");
                                }
                            }
                        }
                        long j = (i << 32) | i2;
                        i = (int) (j >>> 32);
                        i2 = (int) (j & 4294967295L);
                        CharSequence text = this.qkduk.getText();
                        if (!(text instanceof Spannable)) {
                            this.qkduk.setText(text, TextView.BufferType.SPANNABLE);
                        }
                        Selection.setSelection((Spannable) this.qkduk.getText(), i2);
                        dbjc(i, i2, coerceToText);
                        z3 = true;
                    } else {
                        int selectionEnd = this.qkduk.getSelectionEnd();
                        Editable qkduk2 = qkduk(this.qkduk);
                        if (qkduk2 != null) {
                            qkduk2.insert(selectionEnd, "\n");
                        }
                        int selectionEnd2 = this.qkduk.getSelectionEnd();
                        Editable qkduk3 = qkduk(this.qkduk);
                        if (qkduk3 != null) {
                            qkduk3.insert(selectionEnd2, coerceToText);
                        }
                    }
                }
            }
            if (!z) {
                return;
            }
            this.yrp.stopActionMode();
        }
    }

    private void dbjc(int i, int i2, boolean z) {
        try {
            dbjc(i, i2, this.lqox.getPrimaryClip(), true, z);
        } catch (GDNotAuthorizedError e) {
            GDLog.DBGPRINTF(12, "GDDLPWidgetUtil paste", e.getMessage());
            this.yrp.stopActionMode();
        }
    }

    private void dbjc(int i, int i2, CharSequence charSequence) {
        Editable qkduk = qkduk(this.qkduk);
        if (qkduk != null) {
            qkduk.replace(i, i2, charSequence);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void dbjc() {
        this.qkduk.setCustomSelectionActionModeCallback(this.odlf);
    }

    private void dbjc(TextView textView) {
        if (textView != null) {
            int inputType = textView.getInputType() & 4095;
            boolean z = false;
            if (!(inputType == 18 || inputType == 129 || inputType == 145 || inputType == 225)) {
                return;
            }
            if ((textView.getContext().getApplicationContext().getApplicationInfo().flags & 2) != 0) {
                z = true;
            }
            if (z) {
                return;
            }
            textView.setAccessibilityDelegate(new yfdke(this));
        }
    }
}
