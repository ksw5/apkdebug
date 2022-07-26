package com.good.gd.database.sqlite;

import android.database.CharArrayBuffer;
import com.good.gd.database.StaleDataException;

/* loaded from: classes.dex */
public abstract class AbstractWindowedCursor extends AbstractCursor {
    protected CursorWindow mWindow;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.good.gd.database.sqlite.AbstractCursor
    public void checkPosition() {
        super.checkPosition();
        if (this.mWindow != null) {
            return;
        }
        throw new StaleDataException("Attempting to access a closed CursorWindow.Most probable cause: cursor is deactivated prior to calling this method.");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void clearOrCreateWindow(String str) {
        CursorWindow cursorWindow = this.mWindow;
        if (cursorWindow == null) {
            this.mWindow = new CursorWindow(str);
        } else {
            cursorWindow.clear();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void closeWindow() {
        CursorWindow cursorWindow = this.mWindow;
        if (cursorWindow != null) {
            cursorWindow.close();
            this.mWindow = null;
        }
    }

    @Override // com.good.gd.database.sqlite.AbstractCursor, android.database.Cursor
    public void copyStringToBuffer(int i, CharArrayBuffer charArrayBuffer) {
        checkPosition();
        this.mWindow.copyStringToBuffer(this.mPos, i, charArrayBuffer);
    }

    @Override // com.good.gd.database.sqlite.AbstractCursor, android.database.Cursor
    public byte[] getBlob(int i) {
        checkPosition();
        return this.mWindow.getBlob(this.mPos, i);
    }

    @Override // com.good.gd.database.sqlite.AbstractCursor, android.database.Cursor
    public double getDouble(int i) {
        checkPosition();
        return this.mWindow.getDouble(this.mPos, i);
    }

    @Override // com.good.gd.database.sqlite.AbstractCursor, android.database.Cursor
    public float getFloat(int i) {
        checkPosition();
        return this.mWindow.getFloat(this.mPos, i);
    }

    @Override // com.good.gd.database.sqlite.AbstractCursor, android.database.Cursor
    public int getInt(int i) {
        checkPosition();
        return this.mWindow.getInt(this.mPos, i);
    }

    @Override // com.good.gd.database.sqlite.AbstractCursor, android.database.Cursor
    public long getLong(int i) {
        checkPosition();
        return this.mWindow.getLong(this.mPos, i);
    }

    @Override // com.good.gd.database.sqlite.AbstractCursor, android.database.Cursor
    public short getShort(int i) {
        checkPosition();
        return this.mWindow.getShort(this.mPos, i);
    }

    @Override // com.good.gd.database.sqlite.AbstractCursor, android.database.Cursor
    public String getString(int i) {
        checkPosition();
        return this.mWindow.getString(this.mPos, i);
    }

    @Override // com.good.gd.database.sqlite.AbstractCursor, android.database.Cursor
    public int getType(int i) {
        checkPosition();
        return this.mWindow.getType(this.mPos, i);
    }

    @Override // com.good.gd.database.sqlite.AbstractCursor, com.good.gd.database.sqlite.CrossProcessCursor
    public CursorWindow getWindow() {
        return this.mWindow;
    }

    public boolean hasWindow() {
        return this.mWindow != null;
    }

    @Deprecated
    public boolean isBlob(int i) {
        return getType(i) == 4;
    }

    @Deprecated
    public boolean isFloat(int i) {
        return getType(i) == 2;
    }

    @Deprecated
    public boolean isLong(int i) {
        return getType(i) == 1;
    }

    @Override // com.good.gd.database.sqlite.AbstractCursor, android.database.Cursor
    public boolean isNull(int i) {
        checkPosition();
        return this.mWindow.getType(this.mPos, i) == 0;
    }

    @Deprecated
    public boolean isString(int i) {
        return getType(i) == 3;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.good.gd.database.sqlite.AbstractCursor
    public void onDeactivateOrClose() {
        super.onDeactivateOrClose();
        closeWindow();
    }

    public void setWindow(CursorWindow cursorWindow) {
        if (cursorWindow != this.mWindow) {
            closeWindow();
            this.mWindow = cursorWindow;
        }
    }
}
