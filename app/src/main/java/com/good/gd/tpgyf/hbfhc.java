package com.good.gd.tpgyf;

import com.good.gd.file.FileInputStream;
import com.good.gd.file.FileOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes.dex */
public class hbfhc implements com.good.gd.ovnkx.ehnkx {
    @Override // com.good.gd.ovnkx.ehnkx
    public File createFile(String str) {
        return new com.good.gd.file.File(str);
    }

    @Override // com.good.gd.ovnkx.ehnkx
    public OutputStream dbjc(String str) throws FileNotFoundException {
        return new FileOutputStream(str);
    }

    @Override // com.good.gd.ovnkx.ehnkx
    public OutputStream qkduk(File file) throws FileNotFoundException {
        return new FileOutputStream((com.good.gd.file.File) file);
    }

    @Override // com.good.gd.ovnkx.ehnkx
    public File createFile(File file, String str) {
        return new com.good.gd.file.File(file, str);
    }

    @Override // com.good.gd.ovnkx.ehnkx
    public OutputStream dbjc(File file, boolean z) throws FileNotFoundException {
        return new FileOutputStream((com.good.gd.file.File) file, z);
    }

    @Override // com.good.gd.ovnkx.ehnkx
    public InputStream qkduk(String str) throws FileNotFoundException {
        return new FileInputStream(str);
    }

    @Override // com.good.gd.ovnkx.ehnkx
    public InputStream dbjc(File file) throws FileNotFoundException {
        return new FileInputStream(file);
    }

    @Override // com.good.gd.ovnkx.ehnkx
    public String dbjc() {
        return File.separator;
    }
}
