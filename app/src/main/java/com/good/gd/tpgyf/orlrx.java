package com.good.gd.tpgyf;

import com.good.gd.internal.MgmtContainerFile;
import com.good.gd.internal.MgmtContainerFileInputStream;
import com.good.gd.internal.MgmtContainerFileOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes.dex */
public class orlrx implements com.good.gd.ovnkx.ehnkx {
    @Override // com.good.gd.ovnkx.ehnkx
    public File createFile(String str) {
        return new MgmtContainerFile(str);
    }

    @Override // com.good.gd.ovnkx.ehnkx
    public OutputStream dbjc(String str) throws FileNotFoundException {
        return new MgmtContainerFileOutputStream(str);
    }

    @Override // com.good.gd.ovnkx.ehnkx
    public InputStream qkduk(String str) throws FileNotFoundException {
        return new MgmtContainerFileInputStream(str);
    }

    @Override // com.good.gd.ovnkx.ehnkx
    public File createFile(File file, String str) {
        return new MgmtContainerFile(file, str);
    }

    @Override // com.good.gd.ovnkx.ehnkx
    public InputStream dbjc(File file) throws FileNotFoundException {
        return new MgmtContainerFileInputStream(file);
    }

    @Override // com.good.gd.ovnkx.ehnkx
    public OutputStream qkduk(File file) throws FileNotFoundException {
        return new MgmtContainerFileOutputStream(file);
    }

    @Override // com.good.gd.ovnkx.ehnkx
    public OutputStream dbjc(File file, boolean z) throws FileNotFoundException {
        return new MgmtContainerFileOutputStream(file, z);
    }

    @Override // com.good.gd.ovnkx.ehnkx
    public String dbjc() {
        return File.separator;
    }
}
