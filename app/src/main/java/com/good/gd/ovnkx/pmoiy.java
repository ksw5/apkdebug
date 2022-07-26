package com.good.gd.ovnkx;

import java.io.File;
import java.util.Comparator;

/* loaded from: classes.dex */
final class pmoiy implements Comparator<File> {
    @Override // java.util.Comparator
    public int compare(File file, File file2) {
        return (int) (file.lastModified() - file2.lastModified());
    }
}
