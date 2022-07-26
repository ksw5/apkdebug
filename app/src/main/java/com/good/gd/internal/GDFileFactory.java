package com.good.gd.internal;

import com.good.gd.file.File;
import java.net.URI;

/* loaded from: classes.dex */
public class GDFileFactory implements IFileFactory<File> {
    @Override // com.good.gd.internal.IFileFactory
    public java.io.File createFile(String str, String str2) {
        return new File(str, str2);
    }

    @Override // com.good.gd.internal.IFileFactory
    public java.io.File createFile(java.io.File file, String str) {
        return new File(file, str);
    }

    @Override // com.good.gd.internal.IFileFactory
    /* renamed from: createFileArray */
    public File[] mo293createFileArray(int i) {
        return new File[i];
    }

    public java.io.File createFile(URI uri) {
        return new File(uri);
    }

    @Override // com.good.gd.internal.IFileFactory
    public java.io.File createFile(String str) {
        return new File(str);
    }
}
