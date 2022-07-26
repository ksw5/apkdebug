package com.good.gd;

import android.content.SharedPreferences;
import android.os.Looper;
import com.good.gd.file.File;
import com.good.gd.file.FileOutputStream;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.utils.GDMaps;
import com.good.gd.utils.GDQueuedWork;
import com.good.gd.utils.GDSDKState;
import com.good.gd.utils.XmlUtils;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.CountDownLatch;

/* loaded from: classes.dex */
public final class GDSharedPreferencesImpl implements SharedPreferences {
    private static final String TAG = "GDSharedPreferencesImpl";
    private static final Object mContent = new Object();
    private final File mBackupFile;
    private final File mFile;
    private boolean mLoaded;
    private int mDiskWritesInFlight = 0;
    private final Object mWritingToDiskLock = new Object();
    private final WeakHashMap<OnSharedPreferenceChangeListener, Object> mListeners = new WeakHashMap<>();
    private Map<String, Object> mMap = null;

    /* loaded from: classes.dex */
    public final class EditorImpl implements Editor {
        private final Map<String, Object> mModified = GDMaps.newHashMap();
        private boolean mClear = false;

        /* loaded from: classes.dex */
        class hbfhc implements Runnable {
            final /* synthetic */ MemoryCommitResult dbjc;

            hbfhc(EditorImpl editorImpl, MemoryCommitResult memoryCommitResult) {
                this.dbjc = memoryCommitResult;
            }

            @Override // java.lang.Runnable
            public void run() {
                try {
                    this.dbjc.ztwf.await();
                } catch (InterruptedException e) {
                }
            }
        }

        /* loaded from: classes.dex */
        class yfdke implements Runnable {
            final /* synthetic */ Runnable dbjc;

            yfdke(EditorImpl editorImpl, Runnable runnable) {
                this.dbjc = runnable;
            }

            @Override // java.lang.Runnable
            public void run() {
                this.dbjc.run();
                GDQueuedWork.remove(this.dbjc);
            }
        }

        public EditorImpl() {
        }

        /* JADX WARN: Removed duplicated region for block: B:39:0x00df A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:42:0x0082 A[SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private MemoryCommitResult commitToMemory() {
            /*
                r8 = this;
                com.good.gd.GDSharedPreferencesImpl$MemoryCommitResult r0 = new com.good.gd.GDSharedPreferencesImpl$MemoryCommitResult
                r1 = 0
                r0.<init>(r1)
                com.good.gd.GDSharedPreferencesImpl r1 = com.good.gd.GDSharedPreferencesImpl.this
                monitor-enter(r1)
                com.good.gd.GDSharedPreferencesImpl r2 = com.good.gd.GDSharedPreferencesImpl.this     // Catch: java.lang.Throwable -> Lf0
                int r2 = com.good.gd.GDSharedPreferencesImpl.access$300(r2)     // Catch: java.lang.Throwable -> Lf0
                if (r2 <= 0) goto L21
                com.good.gd.GDSharedPreferencesImpl r2 = com.good.gd.GDSharedPreferencesImpl.this     // Catch: java.lang.Throwable -> Lf0
                java.util.HashMap r3 = new java.util.HashMap     // Catch: java.lang.Throwable -> Lf0
                com.good.gd.GDSharedPreferencesImpl r4 = com.good.gd.GDSharedPreferencesImpl.this     // Catch: java.lang.Throwable -> Lf0
                java.util.Map r4 = com.good.gd.GDSharedPreferencesImpl.access$400(r4)     // Catch: java.lang.Throwable -> Lf0
                r3.<init>(r4)     // Catch: java.lang.Throwable -> Lf0
                com.good.gd.GDSharedPreferencesImpl.access$402(r2, r3)     // Catch: java.lang.Throwable -> Lf0
            L21:
                com.good.gd.GDSharedPreferencesImpl r2 = com.good.gd.GDSharedPreferencesImpl.this     // Catch: java.lang.Throwable -> Lf0
                java.util.Map r2 = com.good.gd.GDSharedPreferencesImpl.access$400(r2)     // Catch: java.lang.Throwable -> Lf0
                r0.wxau = r2     // Catch: java.lang.Throwable -> Lf0
                com.good.gd.GDSharedPreferencesImpl r2 = com.good.gd.GDSharedPreferencesImpl.this     // Catch: java.lang.Throwable -> Lf0
                com.good.gd.GDSharedPreferencesImpl.access$308(r2)     // Catch: java.lang.Throwable -> Lf0
                com.good.gd.GDSharedPreferencesImpl r2 = com.good.gd.GDSharedPreferencesImpl.this     // Catch: java.lang.Throwable -> Lf0
                java.util.WeakHashMap r2 = com.good.gd.GDSharedPreferencesImpl.access$500(r2)     // Catch: java.lang.Throwable -> Lf0
                int r2 = r2.size()     // Catch: java.lang.Throwable -> Lf0
                r3 = 0
                r4 = 1
                if (r2 <= 0) goto L3e
                r2 = r4
                goto L3f
            L3e:
                r2 = r3
            L3f:
                if (r2 == 0) goto L59
                java.util.ArrayList r5 = new java.util.ArrayList     // Catch: java.lang.Throwable -> Lf0
                r5.<init>()     // Catch: java.lang.Throwable -> Lf0
                r0.qkduk = r5     // Catch: java.lang.Throwable -> Lf0
                java.util.HashSet r5 = new java.util.HashSet     // Catch: java.lang.Throwable -> Lf0
                com.good.gd.GDSharedPreferencesImpl r6 = com.good.gd.GDSharedPreferencesImpl.this     // Catch: java.lang.Throwable -> Lf0
                java.util.WeakHashMap r6 = com.good.gd.GDSharedPreferencesImpl.access$500(r6)     // Catch: java.lang.Throwable -> Lf0
                java.util.Set r6 = r6.keySet()     // Catch: java.lang.Throwable -> Lf0
                r5.<init>(r6)     // Catch: java.lang.Throwable -> Lf0
                r0.jwxax = r5     // Catch: java.lang.Throwable -> Lf0
            L59:
                monitor-enter(r8)     // Catch: java.lang.Throwable -> Lf0
                boolean r5 = r8.mClear     // Catch: java.lang.Throwable -> Led
                if (r5 == 0) goto L78
                com.good.gd.GDSharedPreferencesImpl r5 = com.good.gd.GDSharedPreferencesImpl.this     // Catch: java.lang.Throwable -> Led
                java.util.Map r5 = com.good.gd.GDSharedPreferencesImpl.access$400(r5)     // Catch: java.lang.Throwable -> Led
                boolean r5 = r5.isEmpty()     // Catch: java.lang.Throwable -> Led
                if (r5 != 0) goto L75
                r0.dbjc = r4     // Catch: java.lang.Throwable -> Led
                com.good.gd.GDSharedPreferencesImpl r5 = com.good.gd.GDSharedPreferencesImpl.this     // Catch: java.lang.Throwable -> Led
                java.util.Map r5 = com.good.gd.GDSharedPreferencesImpl.access$400(r5)     // Catch: java.lang.Throwable -> Led
                r5.clear()     // Catch: java.lang.Throwable -> Led
            L75:
                r8.mClear = r3     // Catch: java.lang.Throwable -> Led
            L78:
                java.util.Map<java.lang.String, java.lang.Object> r3 = r8.mModified     // Catch: java.lang.Throwable -> Led
                java.util.Set r3 = r3.entrySet()     // Catch: java.lang.Throwable -> Led
                java.util.Iterator r3 = r3.iterator()     // Catch: java.lang.Throwable -> Led
            L82:
                boolean r5 = r3.hasNext()     // Catch: java.lang.Throwable -> Led
                if (r5 == 0) goto Le5
                java.lang.Object r5 = r3.next()     // Catch: java.lang.Throwable -> Led
                java.util.Map$Entry r5 = (java.util.Map.Entry) r5     // Catch: java.lang.Throwable -> Led
                java.lang.Object r6 = r5.getKey()     // Catch: java.lang.Throwable -> Led
                java.lang.String r6 = (java.lang.String) r6     // Catch: java.lang.Throwable -> Led
                java.lang.Object r5 = r5.getValue()     // Catch: java.lang.Throwable -> Led
                if (r5 != r8) goto Lb1
                com.good.gd.GDSharedPreferencesImpl r5 = com.good.gd.GDSharedPreferencesImpl.this     // Catch: java.lang.Throwable -> Led
                java.util.Map r5 = com.good.gd.GDSharedPreferencesImpl.access$400(r5)     // Catch: java.lang.Throwable -> Led
                boolean r5 = r5.containsKey(r6)     // Catch: java.lang.Throwable -> Led
                if (r5 != 0) goto La7
                goto L82
            La7:
                com.good.gd.GDSharedPreferencesImpl r5 = com.good.gd.GDSharedPreferencesImpl.this     // Catch: java.lang.Throwable -> Led
                java.util.Map r5 = com.good.gd.GDSharedPreferencesImpl.access$400(r5)     // Catch: java.lang.Throwable -> Led
                r5.remove(r6)     // Catch: java.lang.Throwable -> Led
                goto Ld9
            Lb1:
                com.good.gd.GDSharedPreferencesImpl r7 = com.good.gd.GDSharedPreferencesImpl.this     // Catch: java.lang.Throwable -> Led
                java.util.Map r7 = com.good.gd.GDSharedPreferencesImpl.access$400(r7)     // Catch: java.lang.Throwable -> Led
                boolean r7 = r7.containsKey(r6)     // Catch: java.lang.Throwable -> Led
                if (r7 == 0) goto Ld0
                com.good.gd.GDSharedPreferencesImpl r7 = com.good.gd.GDSharedPreferencesImpl.this     // Catch: java.lang.Throwable -> Led
                java.util.Map r7 = com.good.gd.GDSharedPreferencesImpl.access$400(r7)     // Catch: java.lang.Throwable -> Led
                java.lang.Object r7 = r7.get(r6)     // Catch: java.lang.Throwable -> Led
                if (r7 == 0) goto Ld0
                boolean r7 = r7.equals(r5)     // Catch: java.lang.Throwable -> Led
                if (r7 == 0) goto Ld0
                goto L82
            Ld0:
                com.good.gd.GDSharedPreferencesImpl r7 = com.good.gd.GDSharedPreferencesImpl.this     // Catch: java.lang.Throwable -> Led
                java.util.Map r7 = com.good.gd.GDSharedPreferencesImpl.access$400(r7)     // Catch: java.lang.Throwable -> Led
                r7.put(r6, r5)     // Catch: java.lang.Throwable -> Led
            Ld9:
                r0.dbjc = r4     // Catch: java.lang.Throwable -> Led
                if (r2 == 0) goto L82
                java.util.List<java.lang.String> r5 = r0.qkduk     // Catch: java.lang.Throwable -> Led
                r5.add(r6)     // Catch: java.lang.Throwable -> Led
                goto L82
            Le5:
                java.util.Map<java.lang.String, java.lang.Object> r2 = r8.mModified     // Catch: java.lang.Throwable -> Led
                r2.clear()     // Catch: java.lang.Throwable -> Led
                monitor-exit(r8)     // Catch: java.lang.Throwable -> Led
                monitor-exit(r1)     // Catch: java.lang.Throwable -> Lf0
                return r0
            Led:
                r0 = move-exception
                monitor-exit(r8)     // Catch: java.lang.Throwable -> Led
                throw r0     // Catch: java.lang.Throwable -> Lf0
            Lf0:
                r0 = move-exception
                monitor-exit(r1)     // Catch: java.lang.Throwable -> Lf0
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.good.gd.GDSharedPreferencesImpl.EditorImpl.commitToMemory():com.good.gd.GDSharedPreferencesImpl$MemoryCommitResult");
        }

        private void notifyListeners(MemoryCommitResult memoryCommitResult) {
            List<String> list;
            if (memoryCommitResult.jwxax == null || (list = memoryCommitResult.qkduk) == null || list.size() == 0 || Looper.myLooper() != Looper.getMainLooper()) {
                return;
            }
            for (int size = memoryCommitResult.qkduk.size() - 1; size >= 0; size--) {
                String str = memoryCommitResult.qkduk.get(size);
                for (OnSharedPreferenceChangeListener onSharedPreferenceChangeListener : memoryCommitResult.jwxax) {
                    if (onSharedPreferenceChangeListener != null) {
                        onSharedPreferenceChangeListener.onSharedPreferenceChanged(GDSharedPreferencesImpl.this, str);
                    }
                }
            }
        }

        @Override // android.content.SharedPreferences.Editor
        public void apply() {
            GDSDKState.getInstance().checkAuthorized();
            MemoryCommitResult commitToMemory = commitToMemory();
            hbfhc hbfhcVar = new hbfhc(this, commitToMemory);
            GDQueuedWork.add(hbfhcVar);
            GDSharedPreferencesImpl.this.enqueueDiskWrite(commitToMemory, new yfdke(this, hbfhcVar));
            notifyListeners(commitToMemory);
        }

        @Override // android.content.SharedPreferences.Editor
        public Editor clear() {
            synchronized (this) {
                this.mClear = true;
            }
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public boolean commit() {
            GDSDKState.getInstance().checkAuthorized();
            MemoryCommitResult commitToMemory = commitToMemory();
            GDSharedPreferencesImpl.this.enqueueDiskWrite(commitToMemory, null);
            try {
                commitToMemory.ztwf.await();
                notifyListeners(commitToMemory);
                return commitToMemory.lqox;
            } catch (InterruptedException e) {
                return false;
            }
        }

        @Override // android.content.SharedPreferences.Editor
        public Editor putBoolean(String str, boolean z) {
            GDSDKState.getInstance().checkAuthorized();
            synchronized (this) {
                this.mModified.put(str, Boolean.valueOf(z));
            }
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public Editor putFloat(String str, float f) {
            GDSDKState.getInstance().checkAuthorized();
            synchronized (this) {
                this.mModified.put(str, Float.valueOf(f));
            }
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public Editor putInt(String str, int i) {
            GDSDKState.getInstance().checkAuthorized();
            synchronized (this) {
                this.mModified.put(str, Integer.valueOf(i));
            }
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public Editor putLong(String str, long j) {
            GDSDKState.getInstance().checkAuthorized();
            synchronized (this) {
                this.mModified.put(str, Long.valueOf(j));
            }
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public Editor putString(String str, String str2) {
            GDSDKState.getInstance().checkAuthorized();
            synchronized (this) {
                this.mModified.put(str, str2);
            }
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public Editor putStringSet(String str, Set<String> set) {
            GDSDKState.getInstance().checkAuthorized();
            synchronized (this) {
                this.mModified.put(str, set == null ? null : new HashSet(set));
            }
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public Editor remove(String str) {
            GDSDKState.getInstance().checkAuthorized();
            synchronized (this) {
                this.mModified.put(str, this);
            }
            return this;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class hbfhc extends Thread {
        hbfhc(String str) {
            super(str);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            synchronized (GDSharedPreferencesImpl.this) {
                GDSharedPreferencesImpl.this.loadFromDiskLocked();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class yfdke implements Runnable {
        final /* synthetic */ MemoryCommitResult dbjc;
        final /* synthetic */ Runnable qkduk;

        yfdke(MemoryCommitResult memoryCommitResult, Runnable runnable) {
            this.dbjc = memoryCommitResult;
            this.qkduk = runnable;
        }

        @Override // java.lang.Runnable
        public void run() {
            synchronized (GDSharedPreferencesImpl.this.mWritingToDiskLock) {
                GDSharedPreferencesImpl.this.writeToFile(this.dbjc);
            }
            synchronized (GDSharedPreferencesImpl.this) {
                GDSharedPreferencesImpl.access$310(GDSharedPreferencesImpl.this);
            }
            Runnable runnable = this.qkduk;
            if (runnable != null) {
                runnable.run();
            }
        }
    }

    public GDSharedPreferencesImpl(File file, int i) {
        this.mLoaded = false;
        this.mFile = file;
        this.mBackupFile = makeBackupFile(file);
        this.mLoaded = false;
        startLoadFromDisk();
    }

    static /* synthetic */ int access$308(GDSharedPreferencesImpl gDSharedPreferencesImpl) {
        int i = gDSharedPreferencesImpl.mDiskWritesInFlight;
        gDSharedPreferencesImpl.mDiskWritesInFlight = i + 1;
        return i;
    }

    static /* synthetic */ int access$310(GDSharedPreferencesImpl gDSharedPreferencesImpl) {
        int i = gDSharedPreferencesImpl.mDiskWritesInFlight;
        gDSharedPreferencesImpl.mDiskWritesInFlight = i - 1;
        return i;
    }

    private void awaitLoadedLocked() {
        while (!this.mLoaded) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
    }

    private static FileOutputStream createFileOutputStream(File file) {
        try {
            return new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            if (!file.getParentFile().mkdir()) {
                GDLog.DBGPRINTF(12, "GDSharedPreferencesImplCouldn't create directory for SharedPreferences file " + file);
                return null;
            }
            try {
                return new FileOutputStream(file);
            } catch (FileNotFoundException e2) {
                GDLog.DBGPRINTF(12, "GDSharedPreferencesImplCouldn't create SharedPreferences file " + file, e2);
                return null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enqueueDiskWrite(MemoryCommitResult memoryCommitResult, Runnable runnable) {
        yfdke yfdkeVar = new yfdke(memoryCommitResult, runnable);
        boolean z = false;
        if (runnable == null) {
            synchronized (this) {
                if (this.mDiskWritesInFlight == 1) {
                    z = true;
                }
            }
            if (z) {
                yfdkeVar.run();
                return;
            }
        }
        GDQueuedWork.singleThreadExecutor().execute(yfdkeVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00b5  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00b8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void loadFromDiskLocked() {
        /*
            Method dump skipped, instructions count: 195
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.good.gd.GDSharedPreferencesImpl.loadFromDiskLocked():void");
    }

    private static File makeBackupFile(File file) {
        return new File(file.getPath() + ".bak");
    }

    private void startLoadFromDisk() {
        synchronized (this) {
            this.mLoaded = false;
        }
        new hbfhc("GDSharedPreferencesImpl-load").start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void writeToFile(MemoryCommitResult memoryCommitResult) {
        if (this.mFile.exists()) {
            if (!memoryCommitResult.dbjc) {
                memoryCommitResult.dbjc(true);
                return;
            } else if (!this.mBackupFile.exists()) {
                if (!this.mFile.renameTo(this.mBackupFile)) {
                    GDLog.DBGPRINTF(12, "GDSharedPreferencesImplCouldn't rename file " + this.mFile + " to backup file " + this.mBackupFile);
                    memoryCommitResult.dbjc(false);
                    return;
                }
            } else {
                this.mFile.delete();
            }
        }
        try {
            FileOutputStream createFileOutputStream = createFileOutputStream(this.mFile);
            if (createFileOutputStream == null) {
                memoryCommitResult.dbjc(false);
                return;
            }
            XmlUtils.writeMapXml(memoryCommitResult.wxau, createFileOutputStream);
            createFileOutputStream.close();
            this.mBackupFile.delete();
            memoryCommitResult.dbjc(true);
        } catch (Exception e) {
            GDLog.DBGPRINTF(12, "GDSharedPreferencesImplwriteToFile: Got exception:", e);
            if (this.mFile.exists() && !this.mFile.delete()) {
                GDLog.DBGPRINTF(12, "GDSharedPreferencesImplCouldn't clean up partially-written file " + this.mFile);
            }
            memoryCommitResult.dbjc(false);
        }
    }

    @Override // android.content.SharedPreferences
    public boolean contains(String str) {
        boolean containsKey;
        GDSDKState.getInstance().checkAuthorized();
        synchronized (this) {
            awaitLoadedLocked();
            containsKey = this.mMap.containsKey(str);
        }
        return containsKey;
    }

    @Override // android.content.SharedPreferences
    public Editor edit() {
        GDSDKState.getInstance().checkAuthorized();
        synchronized (this) {
            awaitLoadedLocked();
        }
        return new EditorImpl();
    }

    @Override // android.content.SharedPreferences
    public Map<String, ?> getAll() {
        HashMap hashMap;
        GDSDKState.getInstance().checkAuthorized();
        synchronized (this) {
            awaitLoadedLocked();
            hashMap = new HashMap(this.mMap);
        }
        return hashMap;
    }

    @Override // android.content.SharedPreferences
    public boolean getBoolean(String str, boolean z) {
        GDSDKState.getInstance().checkAuthorized();
        synchronized (this) {
            awaitLoadedLocked();
            Boolean bool = (Boolean) this.mMap.get(str);
            if (bool != null) {
                z = bool.booleanValue();
            }
        }
        return z;
    }

    @Override // android.content.SharedPreferences
    public float getFloat(String str, float f) {
        GDSDKState.getInstance().checkAuthorized();
        synchronized (this) {
            awaitLoadedLocked();
            Float f2 = (Float) this.mMap.get(str);
            if (f2 != null) {
                f = f2.floatValue();
            }
        }
        return f;
    }

    @Override // android.content.SharedPreferences
    public int getInt(String str, int i) {
        GDSDKState.getInstance().checkAuthorized();
        synchronized (this) {
            awaitLoadedLocked();
            Integer num = (Integer) this.mMap.get(str);
            if (num != null) {
                i = num.intValue();
            }
        }
        return i;
    }

    @Override // android.content.SharedPreferences
    public long getLong(String str, long j) {
        GDSDKState.getInstance().checkAuthorized();
        synchronized (this) {
            awaitLoadedLocked();
            Long l = (Long) this.mMap.get(str);
            if (l != null) {
                j = l.longValue();
            }
        }
        return j;
    }

    @Override // android.content.SharedPreferences
    public String getString(String str, String str2) {
        GDSDKState.getInstance().checkAuthorized();
        synchronized (this) {
            awaitLoadedLocked();
            String str3 = (String) this.mMap.get(str);
            if (str3 != null) {
                str2 = str3;
            }
        }
        return str2;
    }

    @Override // android.content.SharedPreferences
    public Set<String> getStringSet(String str, Set<String> set) {
        GDSDKState.getInstance().checkAuthorized();
        synchronized (this) {
            awaitLoadedLocked();
            Set<String> set2 = (Set) this.mMap.get(str);
            if (set2 != null) {
                set = set2;
            }
        }
        return set;
    }

    @Override // android.content.SharedPreferences
    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
        synchronized (this) {
            this.mListeners.put(onSharedPreferenceChangeListener, mContent);
        }
    }

    @Override // android.content.SharedPreferences
    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
        synchronized (this) {
            this.mListeners.remove(onSharedPreferenceChangeListener);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class MemoryCommitResult {
        public boolean dbjc;
        public Set<OnSharedPreferenceChangeListener> jwxax;
        public volatile boolean lqox;
        public List<String> qkduk;
        public Map<?, ?> wxau;
        public final CountDownLatch ztwf;

        private MemoryCommitResult() {
            this.ztwf = new CountDownLatch(1);
            this.lqox = false;
        }

        public void dbjc(boolean z) {
            this.lqox = z;
            this.ztwf.countDown();
        }

        /* synthetic */ MemoryCommitResult(hbfhc hbfhcVar) {
            this();
        }
    }
}
