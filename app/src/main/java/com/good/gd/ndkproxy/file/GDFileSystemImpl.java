package com.good.gd.ndkproxy.file;

import com.good.gd.file.FileInputStream;
import com.good.gd.file.FileOutputStream;
import com.good.gd.ndkproxy.NativeExecutionHandler;
import java.io.FileNotFoundException;

/* loaded from: classes.dex */
public final class GDFileSystemImpl {
    private static native String[] NDK_backupList(String[] strArr);

    private static native String NDK_getAbsoluteEncryptedPath(String str);

    public static String[] backupList(String... strArr) {
        return NDK_backupList(strArr);
    }

    public static String getAbsoluteEncryptedPath(String str) {
        String NDK_getAbsoluteEncryptedPath;
        synchronized (NativeExecutionHandler.nativeLockApi) {
            NDK_getAbsoluteEncryptedPath = NDK_getAbsoluteEncryptedPath(str);
        }
        return NDK_getAbsoluteEncryptedPath;
    }

    public static FileInputStream openFileInput(String str) throws FileNotFoundException {
        return new FileInputStream(str);
    }

    public static FileOutputStream openFileOutput(String str, int i) throws FileNotFoundException {
        return new FileOutputStream(str, i == 32768);
    }
}
