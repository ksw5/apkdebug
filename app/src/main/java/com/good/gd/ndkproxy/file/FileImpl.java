package com.good.gd.ndkproxy.file;

import android.os.Environment;
import android.os.StatFs;
import com.good.gd.error.GDError;
import com.good.gd.internal.IFileFactory;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.NativeExecutionHandler;
import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.SecureRandom;
import java.util.ArrayList;

/* loaded from: classes.dex */
public final class FileImpl<GDFileType extends File> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final char separatorChar = '/';
    private static final SecureRandom tempFileRandom = new SecureRandom();
    private final int m_containerType;
    private final IFileFactory<GDFileType> m_fileFactory;
    private String m_path;

    public FileImpl(String str, IFileFactory<GDFileType> iFileFactory) {
        this(str, 2, iFileFactory);
    }

    private native boolean NDK_createNewFile(String str, int i);

    private native boolean NDK_delete(String str, int i);

    private native boolean NDK_exists(String str, int i);

    private native boolean NDK_isDirectory(String str, int i);

    private native boolean NDK_isFile(String str, int i);

    private native long NDK_lastModified(String str, int i);

    private native long NDK_length(String str, int i);

    private native String[] NDK_list(String str, int i);

    private native boolean NDK_mkdir(String str, int i);

    private native boolean NDK_mkdirs(String str, int i);

    private native boolean NDK_renameTo(String str, String str2, int i);

    private native boolean NDK_setLastModified(String str, long j, int i);

    public static File createTempFile(String str, String str2, IFileFactory<?> iFileFactory) throws IOException {
        return createTempFile(str, str2, null, iFileFactory);
    }

    private void finish() {
        GDLog.DBGPRINTF(16, "FileImpl::finish() IN\n");
        try {
            synchronized (NativeExecutionHandler.nativeLockApi) {
                finishNative();
            }
        } catch (Exception e) {
            GDLog.DBGPRINTF(12, "FileImpl::finish(): ", e);
        }
        GDLog.DBGPRINTF(16, "FileImpl::finish(): peer deinitialized\n");
    }

    private native void finishNative();

    private void init() {
        try {
            synchronized (NativeExecutionHandler.nativeLockApi) {
                ndkInit();
            }
        } catch (GDError e) {
            GDLog.DBGPRINTF(12, "FileImpl::init(): Cannot initialize C++ peer (authorize not called)", e);
            throw e;
        } catch (Throwable th) {
            GDLog.DBGPRINTF(12, "FileImpl::init(): Cannot initialize C++ peer", th);
        }
    }

    public static File[] listRoots(IFileFactory<?> iFileFactory) {
        File[] mo293createFileArray = iFileFactory.mo293createFileArray(1);
        mo293createFileArray[0] = iFileFactory.createFile("/");
        return mo293createFileArray;
    }

    private native void ndkInit();

    public boolean canExecute() {
        return false;
    }

    public boolean canRead() {
        return exists();
    }

    public boolean canWrite() {
        return exists();
    }

    public int compareTo(File file) {
        return this.m_path.compareTo(file.getPath());
    }

    public boolean createNewFile() throws IOException {
        boolean z;
        synchronized (NativeExecutionHandler.nativeLockApi) {
            if (exists()) {
                z = false;
            } else {
                z = NDK_createNewFile(this.m_path, this.m_containerType);
            }
        }
        return z;
    }

    public boolean delete() {
        boolean NDK_delete;
        synchronized (NativeExecutionHandler.nativeLockApi) {
            NDK_delete = NDK_delete(this.m_path, this.m_containerType);
        }
        return NDK_delete;
    }

    public void deleteOnExit() {
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof File)) {
            return false;
        }
        return this.m_path.equals(((File) obj).getPath());
    }

    public boolean exists() {
        boolean NDK_exists;
        synchronized (NativeExecutionHandler.nativeLockApi) {
            NDK_exists = NDK_exists(this.m_path, this.m_containerType);
        }
        return NDK_exists;
    }

    public File getAbsoluteFile() {
        return this.m_fileFactory.createFile(getAbsolutePath());
    }

    public String getAbsolutePath() {
        if (this.m_path.length() > 0 && this.m_path.charAt(0) == '/') {
            return this.m_path;
        }
        return '/' + this.m_path;
    }

    public File getCanonicalFile() throws IOException {
        return this.m_fileFactory.createFile(getCanonicalPath());
    }

    public String getCanonicalPath() throws IOException {
        return this.m_path;
    }

    public long getFreeSpace() {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        return statFs.getAvailableBlocksLong() * statFs.getBlockSizeLong();
    }

    public String getName() {
        int lastIndexOf = this.m_path.lastIndexOf(47);
        return lastIndexOf < 0 ? this.m_path : this.m_path.substring(lastIndexOf + 1);
    }

    public String getParent() {
        int length = this.m_path.length();
        int i = 2;
        int i2 = (length <= 2 || this.m_path.charAt(1) != ':') ? 0 : 2;
        int lastIndexOf = this.m_path.lastIndexOf(47);
        if (lastIndexOf != -1 || i2 <= 0) {
            i = lastIndexOf;
        }
        if (i == -1 || this.m_path.charAt(length - 1) == '/') {
            return null;
        }
        if (this.m_path.indexOf(47) == i && this.m_path.charAt(i2) == '/') {
            return this.m_path.substring(0, i + 1);
        }
        return this.m_path.substring(0, i);
    }

    public File getParentFile() {
        String parent = getParent();
        if (parent == null) {
            return null;
        }
        return this.m_fileFactory.createFile(parent);
    }

    public String getPath() {
        return this.m_path;
    }

    public long getTotalSpace() {
        return new StatFs(Environment.getDataDirectory().getPath()).getTotalBytes();
    }

    public long getUsableSpace() {
        return getFreeSpace();
    }

    public int hashCode() {
        return getPath().hashCode() ^ 1234321;
    }

    public boolean isAbsolute() {
        return this.m_path.length() > 0 && this.m_path.charAt(0) == '/';
    }

    public boolean isDirectory() {
        boolean NDK_isDirectory;
        synchronized (NativeExecutionHandler.nativeLockApi) {
            NDK_isDirectory = NDK_isDirectory(this.m_path, this.m_containerType);
        }
        return NDK_isDirectory;
    }

    public boolean isFile() {
        boolean NDK_isFile;
        synchronized (NativeExecutionHandler.nativeLockApi) {
            NDK_isFile = NDK_isFile(this.m_path, this.m_containerType);
        }
        return NDK_isFile;
    }

    public boolean isHidden() {
        return false;
    }

    public long lastModified() {
        long NDK_lastModified;
        synchronized (NativeExecutionHandler.nativeLockApi) {
            NDK_lastModified = NDK_lastModified(this.m_path, this.m_containerType) * 1000;
        }
        return NDK_lastModified;
    }

    public long length() {
        long NDK_length;
        synchronized (NativeExecutionHandler.nativeLockApi) {
            NDK_length = NDK_length(this.m_path, this.m_containerType);
        }
        return NDK_length;
    }

    public String[] list() {
        String[] NDK_list;
        synchronized (NativeExecutionHandler.nativeLockApi) {
            NDK_list = NDK_list(this.m_path, this.m_containerType);
        }
        return NDK_list;
    }

    public File[] listFiles() {
        String[] list = list();
        File[] mo293createFileArray = this.m_fileFactory.mo293createFileArray(list.length);
        for (int i = 0; i < list.length; i++) {
            mo293createFileArray[i] = this.m_fileFactory.createFile(this.m_path, list[i]);
        }
        return mo293createFileArray;
    }

    public boolean mkdir() {
        boolean NDK_mkdir;
        synchronized (NativeExecutionHandler.nativeLockApi) {
            NDK_mkdir = NDK_mkdir(this.m_path, this.m_containerType);
        }
        return NDK_mkdir;
    }

    public boolean mkdirs() {
        boolean NDK_mkdirs;
        synchronized (NativeExecutionHandler.nativeLockApi) {
            NDK_mkdirs = NDK_mkdirs(this.m_path, this.m_containerType);
        }
        return NDK_mkdirs;
    }

    public boolean renameTo(File file) {
        boolean NDK_renameTo;
        synchronized (NativeExecutionHandler.nativeLockApi) {
            NDK_renameTo = NDK_renameTo(this.m_path, file.getPath(), this.m_containerType);
        }
        return NDK_renameTo;
    }

    public boolean setExecutable(boolean z) {
        return setExecutable(z, false);
    }

    public boolean setLastModified(long j) {
        boolean NDK_setLastModified;
        synchronized (NativeExecutionHandler.nativeLockApi) {
            NDK_setLastModified = NDK_setLastModified(this.m_path, j, this.m_containerType);
        }
        return NDK_setLastModified;
    }

    public boolean setReadOnly() {
        return false;
    }

    public boolean setReadable(boolean z) {
        return setReadable(z, false);
    }

    public boolean setWritable(boolean z) {
        return setWritable(z, false);
    }

    public String toString() {
        return this.m_path;
    }

    public URI toURI() {
        String absolutePath = getAbsolutePath();
        try {
            if (!absolutePath.startsWith("/")) {
                return new URI("file", null, "/" + absolutePath, null, null);
            }
            if (absolutePath.startsWith("//")) {
                return new URI("file", "", absolutePath, null);
            }
            return new URI("file", null, absolutePath, null, null);
        } catch (URISyntaxException e) {
            return null;
        }
    }

    public FileImpl(String str, int i, IFileFactory<GDFileType> iFileFactory) {
        this.m_path = null;
        if (str == null) {
            throw new AssertionError();
        }
        this.m_containerType = i;
        this.m_fileFactory = iFileFactory;
        this.m_path = str;
        init();
    }

    public static File createTempFile(String str, String str2, File file, IFileFactory<?> iFileFactory) throws IOException {
        File createFile;
        if (str.length() >= 3) {
            if (str2 == null) {
                str2 = ".tmp";
            }
            if (file == null) {
                file = iFileFactory.createFile("/tmp_a220d0b393b55f6f068fa35a7a1b5999");
                file.mkdir();
            }
            int i = 0;
            do {
                createFile = iFileFactory.createFile(file, str + tempFileRandom.nextInt() + str2);
                i++;
                if (createFile.createNewFile()) {
                    break;
                }
            } while (i < 100);
            return createFile;
        }
        throw new IllegalArgumentException("prefix must be at least 3 characters");
    }

    public boolean setExecutable(boolean z, boolean z2) {
        return exists() && !z;
    }

    public boolean setReadable(boolean z, boolean z2) {
        return exists() && z;
    }

    public boolean setWritable(boolean z, boolean z2) {
        return exists() && z;
    }

    public String[] list(FilenameFilter filenameFilter, File file) {
        String[] list = list();
        ArrayList arrayList = new ArrayList(list.length);
        for (String str : list) {
            if (filenameFilter.accept(file, str)) {
                arrayList.add(str);
            }
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public File[] listFiles(FileFilter fileFilter) {
        String[] list = list();
        ArrayList arrayList = new ArrayList(list.length);
        for (String str : list) {
            File createFile = this.m_fileFactory.createFile(this.m_path, str);
            if (fileFilter.accept(createFile)) {
                arrayList.add(createFile);
            }
        }
        return (File[]) arrayList.toArray(this.m_fileFactory.mo293createFileArray(arrayList.size()));
    }

    public File[] listFiles(FilenameFilter filenameFilter, File file) {
        String[] list = list();
        ArrayList arrayList = new ArrayList(list.length);
        for (String str : list) {
            if (filenameFilter.accept(file, str)) {
                arrayList.add(this.m_fileFactory.createFile(this.m_path, str));
            }
        }
        return (File[]) arrayList.toArray(this.m_fileFactory.mo293createFileArray(arrayList.size()));
    }
}
