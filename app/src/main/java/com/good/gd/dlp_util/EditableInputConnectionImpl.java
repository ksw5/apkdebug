package com.good.gd.dlp_util;

import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.CorrectionInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputContentInfo;

/* loaded from: classes.dex */
public abstract class EditableInputConnectionImpl implements InputConnection {
    private InputConnection defaultInputConnectionImpl;

    public EditableInputConnectionImpl(InputConnection inputConnection) {
        this.defaultInputConnectionImpl = inputConnection;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean beginBatchEdit() {
        return this.defaultInputConnectionImpl.beginBatchEdit();
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean clearMetaKeyStates(int i) {
        return this.defaultInputConnectionImpl.clearMetaKeyStates(i);
    }

    @Override // android.view.inputmethod.InputConnection
    public void closeConnection() {
        this.defaultInputConnectionImpl.closeConnection();
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean commitCompletion(CompletionInfo completionInfo) {
        return this.defaultInputConnectionImpl.commitCompletion(completionInfo);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean commitContent(InputContentInfo inputContentInfo, int i, Bundle bundle) {
        return this.defaultInputConnectionImpl.commitContent(inputContentInfo, i, bundle);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean commitCorrection(CorrectionInfo correctionInfo) {
        return this.defaultInputConnectionImpl.commitCorrection(correctionInfo);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean commitText(CharSequence charSequence, int i) {
        return this.defaultInputConnectionImpl.commitText(charSequence, i);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean deleteSurroundingText(int i, int i2) {
        return this.defaultInputConnectionImpl.deleteSurroundingText(i, i2);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean deleteSurroundingTextInCodePoints(int i, int i2) {
        return this.defaultInputConnectionImpl.deleteSurroundingTextInCodePoints(i, i2);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean endBatchEdit() {
        return this.defaultInputConnectionImpl.endBatchEdit();
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean finishComposingText() {
        return this.defaultInputConnectionImpl.finishComposingText();
    }

    @Override // android.view.inputmethod.InputConnection
    public int getCursorCapsMode(int i) {
        return this.defaultInputConnectionImpl.getCursorCapsMode(i);
    }

    @Override // android.view.inputmethod.InputConnection
    public ExtractedText getExtractedText(ExtractedTextRequest extractedTextRequest, int i) {
        return this.defaultInputConnectionImpl.getExtractedText(extractedTextRequest, i);
    }

    @Override // android.view.inputmethod.InputConnection
    public Handler getHandler() {
        return this.defaultInputConnectionImpl.getHandler();
    }

    @Override // android.view.inputmethod.InputConnection
    public CharSequence getSelectedText(int i) {
        return this.defaultInputConnectionImpl.getSelectedText(i);
    }

    @Override // android.view.inputmethod.InputConnection
    public CharSequence getTextAfterCursor(int i, int i2) {
        return this.defaultInputConnectionImpl.getTextAfterCursor(i, i2);
    }

    @Override // android.view.inputmethod.InputConnection
    public CharSequence getTextBeforeCursor(int i, int i2) {
        return this.defaultInputConnectionImpl.getTextBeforeCursor(i, i2);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean performContextMenuAction(int i) {
        return this.defaultInputConnectionImpl.performContextMenuAction(i);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean performEditorAction(int i) {
        return this.defaultInputConnectionImpl.performEditorAction(i);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean performPrivateCommand(String str, Bundle bundle) {
        return this.defaultInputConnectionImpl.performPrivateCommand(str, bundle);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean reportFullscreenMode(boolean z) {
        return this.defaultInputConnectionImpl.reportFullscreenMode(z);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean requestCursorUpdates(int i) {
        return this.defaultInputConnectionImpl.requestCursorUpdates(i);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean sendKeyEvent(KeyEvent keyEvent) {
        return this.defaultInputConnectionImpl.sendKeyEvent(keyEvent);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean setComposingRegion(int i, int i2) {
        return this.defaultInputConnectionImpl.setComposingRegion(i, i2);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean setComposingText(CharSequence charSequence, int i) {
        return this.defaultInputConnectionImpl.setComposingText(charSequence, i);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean setSelection(int i, int i2) {
        return this.defaultInputConnectionImpl.setSelection(i, i2);
    }
}
