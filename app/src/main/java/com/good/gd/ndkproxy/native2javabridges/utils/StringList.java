package com.good.gd.ndkproxy.native2javabridges.utils;

import java.util.ArrayList;

/* loaded from: classes.dex */
public class StringList extends ArrayList<String> {
    public void addString(String str) {
        super.add(str);
    }

    public int getSize() {
        return super.size();
    }

    public String getString(int i) {
        return (String) super.get(i);
    }
}
