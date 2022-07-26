package com.good.gd.internal;

import java.io.File;

/* loaded from: classes.dex */
public interface IFileFactory<GDFileType extends File> {
    File createFile(File file, String str);

    File createFile(String str);

    File createFile(String str, String str2);

    /* renamed from: createFileArray */
    File[] mo293createFileArray(int i);
}
