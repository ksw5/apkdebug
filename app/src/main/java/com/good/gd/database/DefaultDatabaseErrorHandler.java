package com.good.gd.database;

import android.util.Pair;
import com.good.gd.database.sqlite.DatabaseErrorHandler;
import com.good.gd.database.sqlite.SQLiteDatabase;
import com.good.gd.database.sqlite.SQLiteDatabaseConfiguration;
import com.good.gd.database.sqlite.SQLiteException;
import com.good.gd.file.File;
import com.good.gd.ndkproxy.GDLog;
import java.util.List;

/* loaded from: classes.dex */
public final class DefaultDatabaseErrorHandler implements DatabaseErrorHandler {
    private static final String TAG = "DefaultDatabaseErrorHandler";
    private String fileName;

    private void deleteDatabaseFile() {
        if (this.fileName.equalsIgnoreCase(SQLiteDatabaseConfiguration.MEMORY_DB_PATH) || this.fileName.trim().length() == 0) {
            return;
        }
        try {
            File file = new File(this.fileName);
            if (!file.exists()) {
                return;
            }
            file.delete();
            GDLog.DBGPRINTF(13, "deleted the database file\n");
        } catch (Exception e) {
            GDLog.DBGPRINTF(12, "delete failed: " + e.getMessage());
        }
    }

    @Override // com.good.gd.database.sqlite.DatabaseErrorHandler
    public void onCorruption(SQLiteDatabase sQLiteDatabase) {
        GDLog.DBGPRINTF(12, "Corruption reported by sqlite on database\n");
        if (!sQLiteDatabase.isOpen()) {
            this.fileName = sQLiteDatabase.getPath();
            deleteDatabaseFile();
            return;
        }
        List<Pair<String, String>> list = null;
        try {
            try {
                list = sQLiteDatabase.getAttachedDbs();
            } finally {
                if (list != null) {
                    for (Pair<String, String> next : list) {
                        this.fileName = (String) next.second;
                        deleteDatabaseFile();
                    }
                } else {
                    this.fileName = sQLiteDatabase.getPath();
                    deleteDatabaseFile();
                }
            }
        } catch (SQLiteException e) {
        }
        try {
            sQLiteDatabase.close();
        } catch (SQLiteException e2) {
        }
    }
}
