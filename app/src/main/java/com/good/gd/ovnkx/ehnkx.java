package com.good.gd.ovnkx;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes.dex */
public interface ehnkx {
    File createFile(File file, String str);

    File createFile(String str);

    InputStream dbjc(File file) throws FileNotFoundException;

    OutputStream dbjc(File file, boolean z) throws FileNotFoundException;

    OutputStream dbjc(String str) throws FileNotFoundException;

    String dbjc();

    InputStream qkduk(String str) throws FileNotFoundException;

    OutputStream qkduk(File file) throws FileNotFoundException;
}
