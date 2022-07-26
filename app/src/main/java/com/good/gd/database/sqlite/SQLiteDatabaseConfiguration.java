package com.good.gd.database.sqlite;

import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public final class SQLiteDatabaseConfiguration {
    private static final Pattern EMAIL_IN_DB_PATTERN = Pattern.compile("[\\w\\.\\-]+@[\\w\\.\\-]+");
    public static final String MEMORY_DB_PATH = ":memory:";
    public boolean foreignKeyConstraintsEnabled;
    public final String label;
    public Locale locale;
    public int maxSqlCacheSize;
    public int openFlags;
    public final String path;
    public final ArrayList<SQLiteCustomFunction> customFunctions = new ArrayList<>();
    public int lookasideSlotSize = -1;
    public int lookasideSlotCount = -1;
    public long idleConnectionTimeoutMs = Long.MAX_VALUE;

    public SQLiteDatabaseConfiguration(String str, int i) {
        if (str != null) {
            this.path = str;
            this.label = stripPathForLogs(str);
            this.openFlags = i;
            this.maxSqlCacheSize = 25;
            this.locale = Locale.getDefault();
            return;
        }
        throw new IllegalArgumentException("path must not be null.");
    }

    private static String stripPathForLogs(String str) {
        return str.indexOf(64) == -1 ? str : EMAIL_IN_DB_PATTERN.matcher(str).replaceAll("XX@YY");
    }

    public boolean isInMemoryDb() {
        return this.path.equalsIgnoreCase(MEMORY_DB_PATH);
    }

    boolean isLookasideConfigSet() {
        return this.lookasideSlotCount >= 0 && this.lookasideSlotSize >= 0;
    }

    public void updateParametersFrom(SQLiteDatabaseConfiguration sQLiteDatabaseConfiguration) {
        if (sQLiteDatabaseConfiguration != null) {
            if (this.path.equals(sQLiteDatabaseConfiguration.path)) {
                this.openFlags = sQLiteDatabaseConfiguration.openFlags;
                this.maxSqlCacheSize = sQLiteDatabaseConfiguration.maxSqlCacheSize;
                this.locale = sQLiteDatabaseConfiguration.locale;
                this.foreignKeyConstraintsEnabled = sQLiteDatabaseConfiguration.foreignKeyConstraintsEnabled;
                this.customFunctions.clear();
                this.customFunctions.addAll(sQLiteDatabaseConfiguration.customFunctions);
                this.lookasideSlotSize = sQLiteDatabaseConfiguration.lookasideSlotSize;
                this.lookasideSlotCount = sQLiteDatabaseConfiguration.lookasideSlotCount;
                this.idleConnectionTimeoutMs = sQLiteDatabaseConfiguration.idleConnectionTimeoutMs;
                return;
            }
            throw new IllegalArgumentException("other configuration must refer to the same database.");
        }
        throw new IllegalArgumentException("other must not be null.");
    }

    public SQLiteDatabaseConfiguration(SQLiteDatabaseConfiguration sQLiteDatabaseConfiguration) {
        if (sQLiteDatabaseConfiguration != null) {
            this.path = sQLiteDatabaseConfiguration.path;
            this.label = sQLiteDatabaseConfiguration.label;
            updateParametersFrom(sQLiteDatabaseConfiguration);
            return;
        }
        throw new IllegalArgumentException("other must not be null.");
    }
}
