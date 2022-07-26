package com.good.gd.internal;

import java.io.File;
import java.net.URI;

/* loaded from: classes.dex */
public class MgmtContainerFileFactory implements IFileFactory<MgmtContainerFile> {
    @Override // com.good.gd.internal.IFileFactory
    public File createFile(String str, String str2) {
        return new MgmtContainerFile(str, str2);
    }

    @Override // com.good.gd.internal.IFileFactory
    public File createFile(File file, String str) {
        return new MgmtContainerFile(file, str);
    }

    @Override // com.good.gd.internal.IFileFactory
    /* renamed from: createFileArray */
    public MgmtContainerFile[] mo293createFileArray(int i) {
        return new MgmtContainerFile[i];
    }

    public File createFile(URI uri) {
        return new MgmtContainerFile(uri);
    }

    @Override // com.good.gd.internal.IFileFactory
    public File createFile(String str) {
        return new MgmtContainerFile(str);
    }
}
