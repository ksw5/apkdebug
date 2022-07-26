package com.good.gd.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public final class JSONUtils {
    private JSONUtils() {
    }

    @Deprecated
    public static boolean addObjectsToMap(Map<String, Object> map, JSONObject jSONObject) throws JSONException {
        map.putAll(toMap(jSONObject));
        return true;
    }

    @Deprecated
    public static boolean addObjectsToVector(Vector<Object> vector, JSONArray jSONArray) throws JSONException {
        vector.addAll(toVector(jSONArray));
        return true;
    }

    private static Object fromJson(Object obj) throws JSONException {
        if (obj == JSONObject.NULL) {
            return null;
        }
        if (obj instanceof JSONObject) {
            return toMap((JSONObject) obj);
        }
        return obj instanceof JSONArray ? toVector((JSONArray) obj) : obj;
    }

    public static Map<String, Object> toMap(JSONObject jSONObject) throws JSONException {
        HashMap hashMap = new HashMap();
        Iterator<String> keys = jSONObject.keys();
        while (keys.hasNext()) {
            String next = keys.next();
            hashMap.put(next, fromJson(jSONObject.get(next)));
        }
        return hashMap;
    }

    public static Vector<Object> toVector(JSONArray jSONArray) throws JSONException {
        Vector<Object> vector = new Vector<>();
        for (int i = 0; i < jSONArray.length(); i++) {
            vector.add(fromJson(jSONArray.get(i)));
        }
        return vector;
    }
}
