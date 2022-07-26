package com.good.gd.file;

import com.good.gd.ndkproxy.file.GDFileSystemImpl;
import com.good.gd.utils.ErrorUtils;
import com.good.gd.utils.GDSDKState;
import java.io.FileNotFoundException;

/* loaded from: classes.dex */
public class GDFileSystem {
    public static final int MODE_APPEND = 32768;
    public static final int MODE_PRIVATE = 0;

    private GDFileSystem() {
    }

    public static String getAbsoluteEncryptedPath(String str) {
        return GDFileSystemImpl.getAbsoluteEncryptedPath(str);
    }

    public static FileInputStream openFileInput(String str) throws FileNotFoundException {
        if (!GDSDKState.getInstance().isWiped()) {
            return GDFileSystemImpl.openFileInput(str);
        }
        throw new FileNotFoundException(ErrorUtils.CONTAINER_WIPED_MSG);
    }

    public static FileOutputStream openFileOutput(String str, int i) throws FileNotFoundException {
        if (!GDSDKState.getInstance().isWiped()) {
            return GDFileSystemImpl.openFileOutput(str, i);
        }
        throw new FileNotFoundException(ErrorUtils.CONTAINER_WIPED_MSG);
    }
}
