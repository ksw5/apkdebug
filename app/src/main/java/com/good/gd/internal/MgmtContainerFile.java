package com.good.gd.internal;

import com.good.gd.ndkproxy.file.FileImpl;
import com.good.gd.utils.ErrorUtils;
import com.good.gd.utils.GDSDKState;
import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URI;

/* loaded from: classes.dex */
public final class MgmtContainerFile extends File {
    private FileImpl<MgmtContainerFile> _impl;
    private static final String[] EMPTY_STRING_ARRAY = new String[0];
    private static final File[] EMPTY_FILE_ARRAY = new File[0];

    public MgmtContainerFile(String str, String str2) {
        this(str + "/" + str2);
    }

    public static File createTempFile(String str, String str2) throws IOException {
        if (!GDSDKState.getInstance().isWiped()) {
            return FileImpl.createTempFile(str, str2, new MgmtContainerFileFactory());
        }
        throw new IOException(ErrorUtils.CONTAINER_WIPED_MSG);
    }

    public static File[] listRoots() {
        if (GDSDKState.getInstance().isWiped()) {
            return EMPTY_FILE_ARRAY;
        }
        return FileImpl.listRoots(new MgmtContainerFileFactory());
    }

    @Override // java.io.File
    public boolean canExecute() {
        if (this._impl == null || GDSDKState.getInstance().isWiped()) {
            return false;
        }
        return this._impl.canExecute();
    }

    @Override // java.io.File
    public boolean canRead() {
        if (this._impl == null || GDSDKState.getInstance().isWiped()) {
            return false;
        }
        return this._impl.canRead();
    }

    @Override // java.io.File
    public boolean canWrite() {
        if (this._impl == null || GDSDKState.getInstance().isWiped()) {
            return false;
        }
        return this._impl.canWrite();
    }

    @Override // java.io.File
    public boolean createNewFile() throws IOException {
        if (this._impl != null && !GDSDKState.getInstance().isWiped()) {
            return this._impl.createNewFile();
        }
        throw new IOException(ErrorUtils.CONTAINER_WIPED_MSG);
    }

    @Override // java.io.File
    public boolean delete() {
        if (this._impl == null || GDSDKState.getInstance().isWiped()) {
            return false;
        }
        return this._impl.delete();
    }

    @Override // java.io.File
    public void deleteOnExit() {
        if (this._impl == null || GDSDKState.getInstance().isWiped()) {
            return;
        }
        this._impl.deleteOnExit();
    }

    @Override // java.io.File
    public boolean equals(Object obj) {
        if (this._impl == null || GDSDKState.getInstance().isWiped() || !(obj instanceof MgmtContainerFile)) {
            return false;
        }
        return this._impl.equals(obj);
    }

    @Override // java.io.File
    public boolean exists() {
        if (this._impl == null || GDSDKState.getInstance().isWiped()) {
            return false;
        }
        return this._impl.exists();
    }

    @Override // java.io.File
    public File getAbsoluteFile() {
        if (this._impl == null || GDSDKState.getInstance().isWiped()) {
            return null;
        }
        return this._impl.getAbsoluteFile();
    }

    @Override // java.io.File
    public String getAbsolutePath() {
        if (this._impl == null || GDSDKState.getInstance().isWiped()) {
            return null;
        }
        return this._impl.getAbsolutePath();
    }

    @Override // java.io.File
    public File getCanonicalFile() throws IOException {
        if (!GDSDKState.getInstance().isWiped()) {
            return this._impl.getCanonicalFile();
        }
        throw new IOException(ErrorUtils.CONTAINER_WIPED_MSG);
    }

    @Override // java.io.File
    public String getCanonicalPath() throws IOException {
        if (!GDSDKState.getInstance().isWiped()) {
            return this._impl.getCanonicalPath();
        }
        throw new IOException(ErrorUtils.CONTAINER_WIPED_MSG);
    }

    @Override // java.io.File
    public long getFreeSpace() {
        if (this._impl == null || GDSDKState.getInstance().isWiped()) {
            return 0L;
        }
        return this._impl.getFreeSpace();
    }

    @Override // java.io.File
    public String getName() {
        return (this._impl == null || GDSDKState.getInstance().isWiped()) ? "" : this._impl.getName();
    }

    @Override // java.io.File
    public String getParent() {
        return (this._impl == null || GDSDKState.getInstance().isWiped()) ? "" : this._impl.getParent();
    }

    @Override // java.io.File
    public File getParentFile() {
        if (this._impl == null || GDSDKState.getInstance().isWiped()) {
            return null;
        }
        return this._impl.getParentFile();
    }

    @Override // java.io.File
    public String getPath() {
        return (this._impl == null || GDSDKState.getInstance().isWiped()) ? "" : this._impl.getPath();
    }

    @Override // java.io.File
    public long getTotalSpace() {
        if (this._impl == null || GDSDKState.getInstance().isWiped()) {
            return 0L;
        }
        return this._impl.getTotalSpace();
    }

    @Override // java.io.File
    public long getUsableSpace() {
        if (this._impl == null || GDSDKState.getInstance().isWiped()) {
            return 0L;
        }
        return this._impl.getUsableSpace();
    }

    @Override // java.io.File
    public int hashCode() {
        if (this._impl == null || GDSDKState.getInstance().isWiped()) {
            return 0;
        }
        return this._impl.hashCode();
    }

    @Override // java.io.File
    public boolean isAbsolute() {
        if (this._impl == null || GDSDKState.getInstance().isWiped()) {
            return false;
        }
        return this._impl.isAbsolute();
    }

    @Override // java.io.File
    public boolean isDirectory() {
        if (this._impl == null || GDSDKState.getInstance().isWiped()) {
            return false;
        }
        return this._impl.isDirectory();
    }

    @Override // java.io.File
    public boolean isFile() {
        if (this._impl == null || GDSDKState.getInstance().isWiped()) {
            return false;
        }
        return this._impl.isFile();
    }

    @Override // java.io.File
    public boolean isHidden() {
        if (this._impl == null || GDSDKState.getInstance().isWiped()) {
            return false;
        }
        return this._impl.isHidden();
    }

    @Override // java.io.File
    public long lastModified() {
        if (this._impl == null || GDSDKState.getInstance().isWiped()) {
            return 0L;
        }
        return this._impl.lastModified();
    }

    @Override // java.io.File
    public long length() {
        if (this._impl == null || GDSDKState.getInstance().isWiped()) {
            return 0L;
        }
        return this._impl.length();
    }

    @Override // java.io.File
    public String[] list() {
        if (this._impl != null && !GDSDKState.getInstance().isWiped()) {
            return this._impl.list();
        }
        return EMPTY_STRING_ARRAY;
    }

    @Override // java.io.File
    public File[] listFiles() {
        if (this._impl != null && !GDSDKState.getInstance().isWiped()) {
            return this._impl.listFiles();
        }
        return EMPTY_FILE_ARRAY;
    }

    @Override // java.io.File
    public boolean mkdir() {
        if (this._impl == null || GDSDKState.getInstance().isWiped()) {
            return false;
        }
        return this._impl.mkdir();
    }

    @Override // java.io.File
    public boolean mkdirs() {
        if (this._impl == null || GDSDKState.getInstance().isWiped()) {
            return false;
        }
        return this._impl.mkdirs();
    }

    @Override // java.io.File
    public boolean renameTo(File file) {
        if (this._impl == null || GDSDKState.getInstance().isWiped()) {
            return false;
        }
        return this._impl.renameTo(file);
    }

    @Override // java.io.File
    public boolean setExecutable(boolean z) {
        if (this._impl == null || GDSDKState.getInstance().isWiped()) {
            return false;
        }
        return this._impl.setExecutable(z);
    }

    @Override // java.io.File
    public boolean setLastModified(long j) {
        if (this._impl == null || GDSDKState.getInstance().isWiped()) {
            return false;
        }
        return this._impl.setLastModified(j);
    }

    @Override // java.io.File
    public boolean setReadOnly() {
        if (this._impl == null || GDSDKState.getInstance().isWiped()) {
            return false;
        }
        return this._impl.setReadOnly();
    }

    @Override // java.io.File
    public boolean setReadable(boolean z) {
        if (this._impl == null || GDSDKState.getInstance().isWiped()) {
            return false;
        }
        return this._impl.setReadable(z);
    }

    @Override // java.io.File
    public boolean setWritable(boolean z) {
        if (this._impl == null || GDSDKState.getInstance().isWiped()) {
            return false;
        }
        return this._impl.setWritable(z);
    }

    @Override // java.io.File
    public String toString() {
        if (this._impl == null || GDSDKState.getInstance().isWiped()) {
            return null;
        }
        return this._impl.toString();
    }

    @Override // java.io.File
    public URI toURI() {
        if (this._impl == null || GDSDKState.getInstance().isWiped()) {
            return null;
        }
        return this._impl.toURI();
    }

    public MgmtContainerFile(File file, String str) {
        this(file.getPath() + "/" + str);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.lang.Comparable
    public int compareTo(File file) {
        if (this._impl == null || GDSDKState.getInstance().isWiped()) {
            return 0;
        }
        return this._impl.compareTo(file);
    }

    public MgmtContainerFile(URI uri) {
        this(uri.getPath());
    }

    public MgmtContainerFile(String str) {
        super(str);
        this._impl = null;
        if (GDSDKState.getInstance().isWiped()) {
            return;
        }
        this._impl = new FileImpl<>(str, 4, new MgmtContainerFileFactory());
    }

    @Override // java.io.File
    public boolean setExecutable(boolean z, boolean z2) {
        if (this._impl == null || GDSDKState.getInstance().isWiped()) {
            return false;
        }
        return this._impl.setExecutable(z, z2);
    }

    @Override // java.io.File
    public boolean setReadable(boolean z, boolean z2) {
        if (this._impl == null || GDSDKState.getInstance().isWiped()) {
            return false;
        }
        return this._impl.setReadable(z, z2);
    }

    @Override // java.io.File
    public boolean setWritable(boolean z, boolean z2) {
        if (this._impl == null || GDSDKState.getInstance().isWiped()) {
            return false;
        }
        return this._impl.setWritable(z, z2);
    }

    public static File createTempFile(String str, String str2, File file) throws IOException {
        if (!GDSDKState.getInstance().isWiped()) {
            return FileImpl.createTempFile(str, str2, file, new MgmtContainerFileFactory());
        }
        throw new IOException(ErrorUtils.CONTAINER_WIPED_MSG);
    }

    @Override // java.io.File
    public String[] list(FilenameFilter filenameFilter) {
        if (this._impl != null && !GDSDKState.getInstance().isWiped()) {
            return this._impl.list(filenameFilter, this);
        }
        return EMPTY_STRING_ARRAY;
    }

    @Override // java.io.File
    public File[] listFiles(FileFilter fileFilter) {
        if (this._impl != null && !GDSDKState.getInstance().isWiped()) {
            return this._impl.listFiles(fileFilter);
        }
        return EMPTY_FILE_ARRAY;
    }

    @Override // java.io.File
    public File[] listFiles(FilenameFilter filenameFilter) {
        if (this._impl != null && !GDSDKState.getInstance().isWiped()) {
            return this._impl.listFiles(filenameFilter, this);
        }
        return EMPTY_FILE_ARRAY;
    }
}
