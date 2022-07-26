package com.good.gd.database.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.os.OperationCanceledException;
import android.os.Parcel;
import android.text.TextUtils;
import com.good.gd.database.SQLException;
import com.good.gd.ndkproxy.GDLog;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.Collator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes.dex */
public class DatabaseUtils {
    public static final int STATEMENT_ABORT = 6;
    public static final int STATEMENT_ATTACH = 3;
    public static final int STATEMENT_BEGIN = 4;
    public static final int STATEMENT_COMMIT = 5;
    public static final int STATEMENT_DDL = 8;
    public static final int STATEMENT_OTHER = 99;
    public static final int STATEMENT_PRAGMA = 7;
    public static final int STATEMENT_SELECT = 1;
    public static final int STATEMENT_UNPREPARED = 9;
    public static final int STATEMENT_UPDATE = 2;
    private static final String TAG = "DatabaseUtils";
    private static Collator mColl;

    @Deprecated
    /* loaded from: classes.dex */
    public static class InsertHelper {
        public static final int TABLE_INFO_PRAGMA_COLUMNNAME_INDEX = 1;
        public static final int TABLE_INFO_PRAGMA_DEFAULT_INDEX = 4;
        private HashMap<String, Integer> mColumns;
        private final SQLiteDatabase mDb;
        private final String mTableName;
        private String mInsertSQL = null;
        private SQLiteStatement mInsertStatement = null;
        private SQLiteStatement mReplaceStatement = null;
        private SQLiteStatement mPreparedStatement = null;

        public InsertHelper(SQLiteDatabase sQLiteDatabase, String str) {
            this.mDb = sQLiteDatabase;
            this.mTableName = str;
        }

        private void buildSQL() throws SQLException {
            StringBuilder sb = new StringBuilder(128);
            sb.append("INSERT INTO ");
            sb.append(this.mTableName);
            sb.append(" (");
            StringBuilder sb2 = new StringBuilder(128);
            sb2.append("VALUES (");
            Cursor cursor = null;
            try {
                cursor = this.mDb.rawQuery("PRAGMA table_info(" + this.mTableName + ")", null);
                this.mColumns = new HashMap<>(cursor.getCount());
                int i = 1;
                while (cursor.moveToNext()) {
                    String string = cursor.getString(1);
                    String string2 = cursor.getString(4);
                    this.mColumns.put(string, Integer.valueOf(i));
                    sb.append("'");
                    sb.append(string);
                    sb.append("'");
                    if (string2 == null) {
                        sb2.append("?");
                    } else {
                        sb2.append("COALESCE(?, ");
                        sb2.append(string2);
                        sb2.append(")");
                    }
                    String str = ", ";
                    sb.append(i == cursor.getCount() ? ") " : str);
                    if (i == cursor.getCount()) {
                        str = ");";
                    }
                    sb2.append(str);
                    i++;
                }
                cursor.close();
                sb.append((CharSequence) sb2);
                this.mInsertSQL = sb.toString();
            } catch (Throwable th) {
                if (cursor != null) {
                    cursor.close();
                }
                throw th;
            }
        }

        private SQLiteStatement getStatement(boolean z) throws SQLException {
            if (z) {
                if (this.mReplaceStatement == null) {
                    if (this.mInsertSQL == null) {
                        buildSQL();
                    }
                    this.mReplaceStatement = this.mDb.compileStatement("INSERT OR REPLACE" + this.mInsertSQL.substring(6));
                }
                return this.mReplaceStatement;
            }
            if (this.mInsertStatement == null) {
                if (this.mInsertSQL == null) {
                    buildSQL();
                }
                this.mInsertStatement = this.mDb.compileStatement(this.mInsertSQL);
            }
            return this.mInsertStatement;
        }

        private long insertInternal(ContentValues contentValues, boolean z) {
            this.mDb.beginTransactionNonExclusive();
            try {
                try {
                    SQLiteStatement statement = getStatement(z);
                    statement.clearBindings();
                    for (Map.Entry<String, Object> entry : contentValues.valueSet()) {
                        DatabaseUtils.bindObjectToProgram(statement, getColumnIndex(entry.getKey()), entry.getValue());
                    }
                    long executeInsert = statement.executeInsert();
                    this.mDb.setTransactionSuccessful();
                    return executeInsert;
                } catch (SQLException e) {
                    GDLog.DBGPRINTF(12, "Error inserting " + contentValues + " into table  " + this.mTableName, e);
                    this.mDb.endTransaction();
                    return -1L;
                }
            } finally {
                this.mDb.endTransaction();
            }
        }

        public void bind(int i, double d) {
            this.mPreparedStatement.bindDouble(i, d);
        }

        public void bindNull(int i) {
            this.mPreparedStatement.bindNull(i);
        }

        public void close() {
            SQLiteStatement sQLiteStatement = this.mInsertStatement;
            if (sQLiteStatement != null) {
                sQLiteStatement.close();
                this.mInsertStatement = null;
            }
            SQLiteStatement sQLiteStatement2 = this.mReplaceStatement;
            if (sQLiteStatement2 != null) {
                sQLiteStatement2.close();
                this.mReplaceStatement = null;
            }
            this.mInsertSQL = null;
            this.mColumns = null;
        }

        public long execute() {
            SQLiteStatement sQLiteStatement = this.mPreparedStatement;
            if (sQLiteStatement != null) {
                try {
                    try {
                        return sQLiteStatement.executeInsert();
                    } catch (SQLException e) {
                        GDLog.DBGPRINTF(12, "Error executing InsertHelper with table " + this.mTableName, e);
                        this.mPreparedStatement = null;
                        return -1L;
                    }
                } finally {
                    this.mPreparedStatement = null;
                }
            }
            throw new IllegalStateException("you must prepare this inserter before calling execute");
        }

        public int getColumnIndex(String str) {
            getStatement(false);
            Integer num = this.mColumns.get(str);
            if (num != null) {
                return num.intValue();
            }
            throw new IllegalArgumentException("column '" + str + "' is invalid");
        }

        public long insert(ContentValues contentValues) {
            return insertInternal(contentValues, false);
        }

        public void prepareForInsert() {
            SQLiteStatement statement = getStatement(false);
            this.mPreparedStatement = statement;
            statement.clearBindings();
        }

        public void prepareForReplace() {
            SQLiteStatement statement = getStatement(true);
            this.mPreparedStatement = statement;
            statement.clearBindings();
        }

        public long replace(ContentValues contentValues) {
            return insertInternal(contentValues, true);
        }

        public void bind(int i, float f) {
            this.mPreparedStatement.bindDouble(i, f);
        }

        public void bind(int i, long j) {
            this.mPreparedStatement.bindLong(i, j);
        }

        public void bind(int i, int i2) {
            this.mPreparedStatement.bindLong(i, i2);
        }

        public void bind(int i, boolean z) {
            this.mPreparedStatement.bindLong(i, z ? 1L : 0L);
        }

        public void bind(int i, byte[] bArr) {
            if (bArr == null) {
                this.mPreparedStatement.bindNull(i);
            } else {
                this.mPreparedStatement.bindBlob(i, bArr);
            }
        }

        public void bind(int i, String str) {
            if (str == null) {
                this.mPreparedStatement.bindNull(i);
            } else {
                this.mPreparedStatement.bindString(i, str);
            }
        }
    }

    public static void appendEscapedSQLString(StringBuilder sb, String str) {
        sb.append('\'');
        if (str.indexOf(39) != -1) {
            int length = str.length();
            for (int i = 0; i < length; i++) {
                char charAt = str.charAt(i);
                if (charAt == '\'') {
                    sb.append('\'');
                }
                sb.append(charAt);
            }
        } else {
            sb.append(str);
        }
        sb.append('\'');
    }

    public static String[] appendSelectionArgs(String[] strArr, String[] strArr2) {
        if (strArr == null || strArr.length == 0) {
            return strArr2;
        }
        String[] strArr3 = new String[strArr.length + strArr2.length];
        System.arraycopy(strArr, 0, strArr3, 0, strArr.length);
        System.arraycopy(strArr2, 0, strArr3, strArr.length, strArr2.length);
        return strArr3;
    }

    public static final void appendValueToSql(StringBuilder sb, Object obj) {
        if (obj == null) {
            sb.append("NULL");
        } else if (obj instanceof Boolean) {
            if (((Boolean) obj).booleanValue()) {
                sb.append('1');
            } else {
                sb.append('0');
            }
        } else {
            appendEscapedSQLString(sb, obj.toString());
        }
    }

    public static void bindObjectToProgram(SQLiteProgram sQLiteProgram, int i, Object obj) {
        if (obj == null) {
            sQLiteProgram.bindNull(i);
        } else if (!(obj instanceof Double) && !(obj instanceof Float)) {
            if (obj instanceof Number) {
                sQLiteProgram.bindLong(i, ((Number) obj).longValue());
            } else if (obj instanceof Boolean) {
                if (((Boolean) obj).booleanValue()) {
                    sQLiteProgram.bindLong(i, 1L);
                } else {
                    sQLiteProgram.bindLong(i, 0L);
                }
            } else if (obj instanceof byte[]) {
                sQLiteProgram.bindBlob(i, (byte[]) obj);
            } else {
                sQLiteProgram.bindString(i, obj.toString());
            }
        } else {
            sQLiteProgram.bindDouble(i, ((Number) obj).doubleValue());
        }
    }

    public static String concatenateWhere(String str, String str2) {
        return TextUtils.isEmpty(str) ? str2 : TextUtils.isEmpty(str2) ? str : "(" + str + ") AND (" + str2 + ")";
    }

    public static void createDbFromSqlStatements(Context context, String str, int i, String str2) {
    }

    public static void cursorDoubleToContentValues(Cursor cursor, String str, ContentValues contentValues, String str2) {
        int columnIndex = cursor.getColumnIndex(str);
        if (!cursor.isNull(columnIndex)) {
            contentValues.put(str2, Double.valueOf(cursor.getDouble(columnIndex)));
        } else {
            contentValues.put(str2, (Double) null);
        }
    }

    public static void cursorDoubleToContentValuesIfPresent(Cursor cursor, ContentValues contentValues, String str) {
        int columnIndex = cursor.getColumnIndex(str);
        if (columnIndex == -1 || cursor.isNull(columnIndex)) {
            return;
        }
        contentValues.put(str, Double.valueOf(cursor.getDouble(columnIndex)));
    }

    public static void cursorDoubleToCursorValues(Cursor cursor, String str, ContentValues contentValues) {
        cursorDoubleToContentValues(cursor, str, contentValues, str);
    }

    public static void cursorFillWindow(Cursor cursor, int i, CursorWindow cursorWindow) {
        boolean putNull;
        if (i < 0 || i >= cursor.getCount()) {
            return;
        }
        int position = cursor.getPosition();
        int columnCount = cursor.getColumnCount();
        cursorWindow.clear();
        cursorWindow.setStartPosition(i);
        cursorWindow.setNumColumns(columnCount);
        if (cursor.moveToPosition(i)) {
            while (cursorWindow.allocRow()) {
                int i2 = 0;
                while (true) {
                    if (i2 >= columnCount) {
                        break;
                    }
                    int type = cursor.getType(i2);
                    if (type == 0) {
                        putNull = cursorWindow.putNull(i, i2);
                    } else if (type == 1) {
                        putNull = cursorWindow.putLong(cursor.getLong(i2), i, i2);
                    } else if (type == 2) {
                        putNull = cursorWindow.putDouble(cursor.getDouble(i2), i, i2);
                    } else if (type != 4) {
                        String string = cursor.getString(i2);
                        if (string != null) {
                            putNull = cursorWindow.putString(string, i, i2);
                        } else {
                            putNull = cursorWindow.putNull(i, i2);
                        }
                    } else {
                        byte[] blob = cursor.getBlob(i2);
                        if (blob != null) {
                            putNull = cursorWindow.putBlob(blob, i, i2);
                        } else {
                            putNull = cursorWindow.putNull(i, i2);
                        }
                    }
                    if (!putNull) {
                        cursorWindow.freeLastRow();
                        break;
                    }
                    i2++;
                }
                i++;
                if (!cursor.moveToNext()) {
                    break;
                }
            }
        }
        cursor.moveToPosition(position);
    }

    public static void cursorFloatToContentValuesIfPresent(Cursor cursor, ContentValues contentValues, String str) {
        int columnIndex = cursor.getColumnIndex(str);
        if (columnIndex == -1 || cursor.isNull(columnIndex)) {
            return;
        }
        contentValues.put(str, Float.valueOf(cursor.getFloat(columnIndex)));
    }

    public static void cursorIntToContentValues(Cursor cursor, String str, ContentValues contentValues) {
        cursorIntToContentValues(cursor, str, contentValues, str);
    }

    public static void cursorIntToContentValuesIfPresent(Cursor cursor, ContentValues contentValues, String str) {
        int columnIndex = cursor.getColumnIndex(str);
        if (columnIndex == -1 || cursor.isNull(columnIndex)) {
            return;
        }
        contentValues.put(str, Integer.valueOf(cursor.getInt(columnIndex)));
    }

    public static void cursorLongToContentValues(Cursor cursor, String str, ContentValues contentValues) {
        cursorLongToContentValues(cursor, str, contentValues, str);
    }

    public static void cursorLongToContentValuesIfPresent(Cursor cursor, ContentValues contentValues, String str) {
        int columnIndex = cursor.getColumnIndex(str);
        if (columnIndex == -1 || cursor.isNull(columnIndex)) {
            return;
        }
        contentValues.put(str, Long.valueOf(cursor.getLong(columnIndex)));
    }

    public static int cursorPickFillWindowStartPosition(int i, int i2) {
        return Math.max(i - (i2 / 3), 0);
    }

    public static void cursorRowToContentValues(Cursor cursor, ContentValues contentValues) {
        AbstractWindowedCursor abstractWindowedCursor = cursor instanceof AbstractWindowedCursor ? (AbstractWindowedCursor) cursor : null;
        String[] columnNames = cursor.getColumnNames();
        int length = columnNames.length;
        for (int i = 0; i < length; i++) {
            if (abstractWindowedCursor != null && abstractWindowedCursor.isBlob(i)) {
                contentValues.put(columnNames[i], cursor.getBlob(i));
            } else {
                contentValues.put(columnNames[i], cursor.getString(i));
            }
        }
    }

    public static void cursorShortToContentValuesIfPresent(Cursor cursor, ContentValues contentValues, String str) {
        int columnIndex = cursor.getColumnIndex(str);
        if (columnIndex == -1 || cursor.isNull(columnIndex)) {
            return;
        }
        contentValues.put(str, Short.valueOf(cursor.getShort(columnIndex)));
    }

    public static void cursorStringToContentValues(Cursor cursor, String str, ContentValues contentValues) {
        cursorStringToContentValues(cursor, str, contentValues, str);
    }

    public static void cursorStringToContentValuesIfPresent(Cursor cursor, ContentValues contentValues, String str) {
        int columnIndex = cursor.getColumnIndex(str);
        if (columnIndex == -1 || cursor.isNull(columnIndex)) {
            return;
        }
        contentValues.put(str, cursor.getString(columnIndex));
    }

    public static void cursorStringToInsertHelper(Cursor cursor, String str, InsertHelper insertHelper, int i) {
        insertHelper.bind(i, cursor.getString(cursor.getColumnIndexOrThrow(str)));
    }

    public static void dumpCurrentRow(Cursor cursor) {
        dumpCurrentRow(cursor, System.out);
    }

    public static String dumpCurrentRowToString(Cursor cursor) {
        StringBuilder sb = new StringBuilder();
        dumpCurrentRow(cursor, sb);
        return sb.toString();
    }

    public static void dumpCursor(Cursor cursor) {
        dumpCursor(cursor, System.out);
    }

    public static String dumpCursorToString(Cursor cursor) {
        StringBuilder sb = new StringBuilder();
        dumpCursor(cursor, sb);
        return sb.toString();
    }

    public static int findRowIdColumnIndex(String[] strArr) {
        int length = strArr.length;
        for (int i = 0; i < length; i++) {
            if (strArr[i].equals("_id")) {
                return i;
            }
        }
        return -1;
    }

    public static String getCollationKey(String str) {
        byte[] collationKeyInBytes = getCollationKeyInBytes(str);
        try {
            return new String(collationKeyInBytes, 0, getKeyLen(collationKeyInBytes), "ISO8859_1");
        } catch (Exception e) {
            return "";
        }
    }

    private static byte[] getCollationKeyInBytes(String str) {
        if (mColl == null) {
            Collator collator = Collator.getInstance();
            mColl = collator;
            collator.setStrength(0);
        }
        return mColl.getCollationKey(str).toByteArray();
    }

    private static int getKeyLen(byte[] bArr) {
        if (bArr[bArr.length - 1] != 0) {
            return bArr.length;
        }
        return bArr.length - 1;
    }

    public static int getSqlStatementType(String str) {
        String trim = str.trim();
        if (trim.length() < 3) {
            return 99;
        }
        String upperCase = trim.substring(0, 3).toUpperCase(Locale.US);
        if (upperCase.equals("SEL")) {
            return 1;
        }
        if (upperCase.equals("INS") || upperCase.equals("UPD") || upperCase.equals("REP") || upperCase.equals("DEL")) {
            return 2;
        }
        if (upperCase.equals("ATT")) {
            return 3;
        }
        if (upperCase.equals("COM") || upperCase.equals("END")) {
            return 5;
        }
        if (upperCase.equals("ROL")) {
            return 6;
        }
        if (upperCase.equals("BEG")) {
            return 4;
        }
        if (upperCase.equals("PRA")) {
            return 7;
        }
        if (upperCase.equals("CRE") || upperCase.equals("DRO") || upperCase.equals("ALT")) {
            return 8;
        }
        return (upperCase.equals("ANA") || upperCase.equals("DET")) ? 9 : 99;
    }

    public static int getTypeOfObject(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof byte[]) {
            return 4;
        }
        if ((obj instanceof Float) || (obj instanceof Double)) {
            return 2;
        }
        return ((obj instanceof Long) || (obj instanceof Integer) || (obj instanceof Short) || (obj instanceof Byte)) ? 1 : 3;
    }

    public static long longForQuery(SQLiteDatabase sQLiteDatabase, String str, String[] strArr) {
        SQLiteStatement compileStatement = sQLiteDatabase.compileStatement(str);
        try {
            return longForQuery(compileStatement, strArr);
        } finally {
            compileStatement.close();
        }
    }

    public static boolean queryIsEmpty(SQLiteDatabase sQLiteDatabase, String str) {
        return longForQuery(sQLiteDatabase, new StringBuilder().append("select exists(select 1 from ").append(str).append(")").toString(), null) == 0;
    }

    public static long queryNumEntries(SQLiteDatabase sQLiteDatabase, String str) {
        return queryNumEntries(sQLiteDatabase, str, null, null);
    }

    public static String sqlEscapeString(String str) {
        StringBuilder sb = new StringBuilder();
        appendEscapedSQLString(sb, str);
        return sb.toString();
    }

    public static String stringForQuery(SQLiteDatabase sQLiteDatabase, String str, String[] strArr) {
        SQLiteStatement compileStatement = sQLiteDatabase.compileStatement(str);
        try {
            return stringForQuery(compileStatement, strArr);
        } finally {
            compileStatement.close();
        }
    }

    public static final void writeExceptionToParcel(Parcel parcel, Exception exc) {
        boolean z = false;
        int i = 1;
        if (!(exc instanceof FileNotFoundException)) {
            if (exc instanceof IllegalArgumentException) {
                z = true;
                i = 2;
            } else if (exc instanceof UnsupportedOperationException) {
                z = true;
                i = 3;
            } else if (exc instanceof SQLiteAbortException) {
                z = true;
                i = 4;
            } else if (exc instanceof SQLiteConstraintException) {
                z = true;
                i = 5;
            } else if (exc instanceof SQLiteDatabaseCorruptException) {
                z = true;
                i = 6;
            } else if (exc instanceof SQLiteFullException) {
                z = true;
                i = 7;
            } else if (exc instanceof SQLiteDiskIOException) {
                z = true;
                i = 8;
            } else if (exc instanceof SQLiteException) {
                z = true;
                i = 9;
            } else if (exc instanceof OperationApplicationException) {
                z = true;
                i = 10;
            } else if (!(exc instanceof OperationCanceledException)) {
                parcel.writeException(exc);
                GDLog.DBGPRINTF(12, "Writing exception to parcel", exc);
                return;
            } else {
                i = 11;
            }
        }
        parcel.writeInt(i);
        parcel.writeString(exc.getMessage());
        if (z) {
            GDLog.DBGPRINTF(12, "Writing exception to parcel", exc);
        }
    }

    public static void cursorIntToContentValues(Cursor cursor, String str, ContentValues contentValues, String str2) {
        int columnIndex = cursor.getColumnIndex(str);
        if (!cursor.isNull(columnIndex)) {
            contentValues.put(str2, Integer.valueOf(cursor.getInt(columnIndex)));
        } else {
            contentValues.put(str2, (Integer) null);
        }
    }

    public static void cursorLongToContentValues(Cursor cursor, String str, ContentValues contentValues, String str2) {
        int columnIndex = cursor.getColumnIndex(str);
        if (!cursor.isNull(columnIndex)) {
            contentValues.put(str2, Long.valueOf(cursor.getLong(columnIndex)));
        } else {
            contentValues.put(str2, (Long) null);
        }
    }

    public static void cursorStringToContentValues(Cursor cursor, String str, ContentValues contentValues, String str2) {
        contentValues.put(str2, cursor.getString(cursor.getColumnIndexOrThrow(str)));
    }

    public static void dumpCurrentRow(Cursor cursor, PrintStream printStream) {
        String str;
        String[] columnNames = cursor.getColumnNames();
        printStream.println("" + cursor.getPosition() + " {");
        int length = columnNames.length;
        for (int i = 0; i < length; i++) {
            try {
                str = cursor.getString(i);
            } catch (SQLiteException e) {
                str = "<unprintable>";
            }
            printStream.println("   " + columnNames[i] + '=' + str);
        }
        printStream.println("}");
    }

    public static void dumpCursor(Cursor cursor, PrintStream printStream) {
        printStream.println(">>>>> Dumping cursor " + cursor);
        if (cursor != null) {
            int position = cursor.getPosition();
            cursor.moveToPosition(-1);
            while (cursor.moveToNext()) {
                dumpCurrentRow(cursor, printStream);
            }
            cursor.moveToPosition(position);
        }
        printStream.println("<<<<<");
    }

    public static long queryNumEntries(SQLiteDatabase sQLiteDatabase, String str, String str2) {
        return queryNumEntries(sQLiteDatabase, str, str2, null);
    }

    public static long queryNumEntries(SQLiteDatabase sQLiteDatabase, String str, String str2, String[] strArr) {
        return longForQuery(sQLiteDatabase, "select count(*) from " + str + (!TextUtils.isEmpty(str2) ? " where " + str2 : ""), strArr);
    }

    public static long longForQuery(SQLiteStatement sQLiteStatement, String[] strArr) {
        sQLiteStatement.bindAllArgsAsStrings(strArr);
        return sQLiteStatement.simpleQueryForLong();
    }

    public static String stringForQuery(SQLiteStatement sQLiteStatement, String[] strArr) {
        sQLiteStatement.bindAllArgsAsStrings(strArr);
        return sQLiteStatement.simpleQueryForString();
    }

    public static void dumpCursor(Cursor cursor, StringBuilder sb) {
        sb.append(">>>>> Dumping cursor " + cursor + "\n");
        if (cursor != null) {
            int position = cursor.getPosition();
            cursor.moveToPosition(-1);
            while (cursor.moveToNext()) {
                dumpCurrentRow(cursor, sb);
            }
            cursor.moveToPosition(position);
        }
        sb.append("<<<<<\n");
    }

    public static void dumpCurrentRow(Cursor cursor, StringBuilder sb) {
        String str;
        String[] columnNames = cursor.getColumnNames();
        sb.append("" + cursor.getPosition() + " {\n");
        int length = columnNames.length;
        for (int i = 0; i < length; i++) {
            try {
                str = cursor.getString(i);
            } catch (SQLiteException e) {
                str = "<unprintable>";
            }
            sb.append("   " + columnNames[i] + '=' + str + "\n");
        }
        sb.append("}\n");
    }
}
