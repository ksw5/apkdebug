package com.good.gd.database.sqlite;

import java.util.ArrayList;

/* loaded from: classes.dex */
public class MatrixCursor extends AbstractCursor {
    private final int columnCount;
    private final String[] columnNames;
    private Object[] data;
    private int rowCount;

    public MatrixCursor(String[] strArr, int i) {
        this.rowCount = 0;
        this.columnNames = strArr;
        int length = strArr.length;
        this.columnCount = length;
        this.data = new Object[length * (i < 1 ? 1 : i)];
    }

    private void ensureCapacity(int i) {
        Object[] objArr = this.data;
        if (i > objArr.length) {
            int length = objArr.length * 2;
            if (length >= i) {
                i = length;
            }
            Object[] objArr2 = new Object[i];
            this.data = objArr2;
            System.arraycopy(objArr, 0, objArr2, 0, objArr.length);
        }
    }

    private Object get(int i) {
        int i2;
        if (i >= 0 && i < (i2 = this.columnCount)) {
            int i3 = this.mPos;
            if (i3 >= 0) {
                if (i3 < this.rowCount) {
                    return this.data[(i3 * i2) + i];
                }
                throw new CursorIndexOutOfBoundsException("After last row.");
            }
            throw new CursorIndexOutOfBoundsException("Before first row.");
        }
        throw new CursorIndexOutOfBoundsException("Requested column: " + i + ", # of columns: " + this.columnCount);
    }

    public void addRow(Object[] objArr) {
        int length = objArr.length;
        int i = this.columnCount;
        if (length == i) {
            int i2 = this.rowCount;
            this.rowCount = i2 + 1;
            int i3 = i2 * i;
            ensureCapacity(i + i3);
            System.arraycopy(objArr, 0, this.data, i3, this.columnCount);
            return;
        }
        throw new IllegalArgumentException("columnNames.length = " + this.columnCount + ", columnValues.length = " + objArr.length);
    }

    @Override // com.good.gd.database.sqlite.AbstractCursor, android.database.Cursor
    public byte[] getBlob(int i) {
        return (byte[]) get(i);
    }

    @Override // com.good.gd.database.sqlite.AbstractCursor, android.database.Cursor
    public String[] getColumnNames() {
        return this.columnNames;
    }

    @Override // com.good.gd.database.sqlite.AbstractCursor, android.database.Cursor
    public int getCount() {
        return this.rowCount;
    }

    @Override // com.good.gd.database.sqlite.AbstractCursor, android.database.Cursor
    public double getDouble(int i) {
        Object obj = get(i);
        if (obj == null) {
            return 0.0d;
        }
        return obj instanceof Number ? ((Number) obj).doubleValue() : Double.parseDouble(obj.toString());
    }

    @Override // com.good.gd.database.sqlite.AbstractCursor, android.database.Cursor
    public float getFloat(int i) {
        Object obj = get(i);
        if (obj == null) {
            return 0.0f;
        }
        return obj instanceof Number ? ((Number) obj).floatValue() : Float.parseFloat(obj.toString());
    }

    @Override // com.good.gd.database.sqlite.AbstractCursor, android.database.Cursor
    public int getInt(int i) {
        Object obj = get(i);
        if (obj == null) {
            return 0;
        }
        return obj instanceof Number ? ((Number) obj).intValue() : Integer.parseInt(obj.toString());
    }

    @Override // com.good.gd.database.sqlite.AbstractCursor, android.database.Cursor
    public long getLong(int i) {
        Object obj = get(i);
        if (obj == null) {
            return 0L;
        }
        return obj instanceof Number ? ((Number) obj).longValue() : Long.parseLong(obj.toString());
    }

    @Override // com.good.gd.database.sqlite.AbstractCursor, android.database.Cursor
    public short getShort(int i) {
        Object obj = get(i);
        if (obj == null) {
            return (short) 0;
        }
        return obj instanceof Number ? ((Number) obj).shortValue() : Short.parseShort(obj.toString());
    }

    @Override // com.good.gd.database.sqlite.AbstractCursor, android.database.Cursor
    public String getString(int i) {
        Object obj = get(i);
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }

    @Override // com.good.gd.database.sqlite.AbstractCursor, android.database.Cursor
    public int getType(int i) {
        return DatabaseUtils.getTypeOfObject(get(i));
    }

    @Override // com.good.gd.database.sqlite.AbstractCursor, android.database.Cursor
    public boolean isNull(int i) {
        return get(i) == null;
    }

    public RowBuilder newRow() {
        int i = this.rowCount;
        int i2 = i + 1;
        this.rowCount = i2;
        ensureCapacity(i2 * this.columnCount);
        return new RowBuilder(i);
    }

    /* loaded from: classes.dex */
    public class RowBuilder {
        private final int endIndex;
        private int index;
        private final int row;

        RowBuilder(int i) {
            this.row = i;
            int i2 = i * MatrixCursor.this.columnCount;
            this.index = i2;
            this.endIndex = i2 + MatrixCursor.this.columnCount;
        }

        public RowBuilder add(Object obj) {
            if (this.index != this.endIndex) {
                Object[] objArr = MatrixCursor.this.data;
                int i = this.index;
                this.index = i + 1;
                objArr[i] = obj;
                return this;
            }
            throw new CursorIndexOutOfBoundsException("No more columns left.");
        }

        public RowBuilder add(String str, Object obj) {
            for (int i = 0; i < MatrixCursor.this.columnNames.length; i++) {
                if (str.equals(MatrixCursor.this.columnNames[i])) {
                    MatrixCursor.this.data[(this.row * MatrixCursor.this.columnCount) + i] = obj;
                }
            }
            return this;
        }
    }

    public void addRow(Iterable<?> iterable) {
        int i = this.rowCount;
        int i2 = this.columnCount;
        int i3 = i * i2;
        int i4 = i2 + i3;
        ensureCapacity(i4);
        if (iterable instanceof ArrayList) {
            addRow((ArrayList) iterable, i3);
            return;
        }
        Object[] objArr = this.data;
        for (Object obj : iterable) {
            if (i3 != i4) {
                objArr[i3] = obj;
                i3++;
            } else {
                throw new IllegalArgumentException("columnValues.size() > columnNames.length");
            }
        }
        if (i3 == i4) {
            this.rowCount++;
            return;
        }
        throw new IllegalArgumentException("columnValues.size() < columnNames.length");
    }

    public MatrixCursor(String[] strArr) {
        this(strArr, 16);
    }

    private void addRow(ArrayList<?> arrayList, int i) {
        int size = arrayList.size();
        if (size == this.columnCount) {
            this.rowCount++;
            Object[] objArr = this.data;
            for (int i2 = 0; i2 < size; i2++) {
                objArr[i + i2] = arrayList.get(i2);
            }
            return;
        }
        throw new IllegalArgumentException("columnNames.length = " + this.columnCount + ", columnValues.size() = " + size);
    }
}
