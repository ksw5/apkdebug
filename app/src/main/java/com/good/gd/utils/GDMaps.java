package com.good.gd.utils;

import com.good.gd.utils.gdarraymapimpl.GDArrayMap;
import java.util.HashMap;

/* loaded from: classes.dex */
public class GDMaps {
    public static <K, V> GDArrayMap<K, V> newArrayMap() {
        return new GDArrayMap<>();
    }

    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<>();
    }
}
