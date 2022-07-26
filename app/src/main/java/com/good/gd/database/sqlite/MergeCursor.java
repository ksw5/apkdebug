package com.good.gd.database.sqlite;

import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;

/* loaded from: classes.dex */
public class MergeCursor extends AbstractCursor {
    private Cursor mCursor;
    private Cursor[] mCursors;
    private DataSetObserver mObserver = new DataSetObserver() { // from class: com.good.gd.database.sqlite.MergeCursor.1
        @Override // android.database.DataSetObserver
        public void onChanged() {
            MergeCursor.this.mPos = -1;
        }

        @Override // android.database.DataSetObserver
        public void onInvalidated() {
            MergeCursor.this.mPos = -1;
        }
    };

    public MergeCursor(Cursor[] cursorArr) {
        this.mCursors = cursorArr;
        int i = 0;
        this.mCursor = cursorArr[0];
        while (true) {
            Cursor[] cursorArr2 = this.mCursors;
            if (i < cursorArr2.length) {
                if (cursorArr2[i] != null) {
                    cursorArr2[i].registerDataSetObserver(this.mObserver);
                }
                i++;
            } else {
                return;
            }
        }
    }

    @Override // com.good.gd.database.sqlite.AbstractCursor, android.database.Cursor, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        int length = this.mCursors.length;
        for (int i = 0; i < length; i++) {
            Cursor[] cursorArr = this.mCursors;
            if (cursorArr[i] != null) {
                cursorArr[i].close();
            }
        }
        super.close();
    }

    @Override // com.good.gd.database.sqlite.AbstractCursor, android.database.Cursor
    public void deactivate() {
        int length = this.mCursors.length;
        for (int i = 0; i < length; i++) {
            Cursor[] cursorArr = this.mCursors;
            if (cursorArr[i] != null) {
                cursorArr[i].deactivate();
            }
        }
        super.deactivate();
    }

    @Override // com.good.gd.database.sqlite.AbstractCursor, android.database.Cursor
    public byte[] getBlob(int i) {
        return this.mCursor.getBlob(i);
    }

    @Override // com.good.gd.database.sqlite.AbstractCursor, android.database.Cursor
    public String[] getColumnNames() {
        Cursor cursor = this.mCursor;
        if (cursor != null) {
            return cursor.getColumnNames();
        }
        return new String[0];
    }

    @Override // com.good.gd.database.sqlite.AbstractCursor, android.database.Cursor
    public int getCount() {
        int length = this.mCursors.length;
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            Cursor[] cursorArr = this.mCursors;
            if (cursorArr[i2] != null) {
                i += cursorArr[i2].getCount();
            }
        }
        return i;
    }

    @Override // com.good.gd.database.sqlite.AbstractCursor, android.database.Cursor
    public double getDouble(int i) {
        return this.mCursor.getDouble(i);
    }

    @Override // com.good.gd.database.sqlite.AbstractCursor, android.database.Cursor
    public float getFloat(int i) {
        return this.mCursor.getFloat(i);
    }

    @Override // com.good.gd.database.sqlite.AbstractCursor, android.database.Cursor
    public int getInt(int i) {
        return this.mCursor.getInt(i);
    }

    @Override // com.good.gd.database.sqlite.AbstractCursor, android.database.Cursor
    public long getLong(int i) {
        return this.mCursor.getLong(i);
    }

    @Override // com.good.gd.database.sqlite.AbstractCursor, android.database.Cursor
    public short getShort(int i) {
        return this.mCursor.getShort(i);
    }

    @Override // com.good.gd.database.sqlite.AbstractCursor, android.database.Cursor
    public String getString(int i) {
        return this.mCursor.getString(i);
    }

    @Override // com.good.gd.database.sqlite.AbstractCursor, android.database.Cursor
    public int getType(int i) {
        return this.mCursor.getType(i);
    }

    @Override // com.good.gd.database.sqlite.AbstractCursor, android.database.Cursor
    public boolean isNull(int i) {
        return this.mCursor.isNull(i);
    }

    @Override // com.good.gd.database.sqlite.AbstractCursor, com.good.gd.database.sqlite.CrossProcessCursor
    public boolean onMove(int i, int i2) {
        this.mCursor = null;
        int length = this.mCursors.length;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            if (i3 >= length) {
                break;
            }
            Cursor[] cursorArr = this.mCursors;
            if (cursorArr[i3] != null) {
                if (i2 < cursorArr[i3].getCount() + i4) {
                    this.mCursor = this.mCursors[i3];
                    break;
                }
                i4 += this.mCursors[i3].getCount();
            }
            i3++;
        }
        Cursor cursor = this.mCursor;
        if (cursor != null) {
            return cursor.moveToPosition(i2 - i4);
        }
        return false;
    }

    @Override // com.good.gd.database.sqlite.AbstractCursor, android.database.Cursor
    public void registerContentObserver(ContentObserver contentObserver) {
        int length = this.mCursors.length;
        for (int i = 0; i < length; i++) {
            Cursor[] cursorArr = this.mCursors;
            if (cursorArr[i] != null) {
                cursorArr[i].registerContentObserver(contentObserver);
            }
        }
    }

    @Override // com.good.gd.database.sqlite.AbstractCursor, android.database.Cursor
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
        int length = this.mCursors.length;
        for (int i = 0; i < length; i++) {
            Cursor[] cursorArr = this.mCursors;
            if (cursorArr[i] != null) {
                cursorArr[i].registerDataSetObserver(dataSetObserver);
            }
        }
    }

    @Override // com.good.gd.database.sqlite.AbstractCursor, android.database.Cursor
    public boolean requery() {
        int length = this.mCursors.length;
        for (int i = 0; i < length; i++) {
            Cursor[] cursorArr = this.mCursors;
            if (cursorArr[i] != null && !cursorArr[i].requery()) {
                return false;
            }
        }
        return true;
    }

    @Override // com.good.gd.database.sqlite.AbstractCursor, android.database.Cursor
    public void unregisterContentObserver(ContentObserver contentObserver) {
        int length = this.mCursors.length;
        for (int i = 0; i < length; i++) {
            Cursor[] cursorArr = this.mCursors;
            if (cursorArr[i] != null) {
                cursorArr[i].unregisterContentObserver(contentObserver);
            }
        }
    }

    @Override // com.good.gd.database.sqlite.AbstractCursor, android.database.Cursor
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
        int length = this.mCursors.length;
        for (int i = 0; i < length; i++) {
            Cursor[] cursorArr = this.mCursors;
            if (cursorArr[i] != null) {
                cursorArr[i].unregisterDataSetObserver(dataSetObserver);
            }
        }
    }
}
