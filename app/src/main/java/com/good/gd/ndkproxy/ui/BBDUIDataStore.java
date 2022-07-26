package com.good.gd.ndkproxy.ui;

import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.ui.data.base.BBDUIObject;
import java.util.Map;
import java.util.TreeMap;

/* loaded from: classes.dex */
public class BBDUIDataStore {
    private static BBDUIDataStore sInstance;
    private static Map<Long, BBDUIObject> uiMap = new TreeMap();

    private BBDUIDataStore() {
    }

    public static synchronized BBDUIDataStore getInstance() {
        BBDUIDataStore bBDUIDataStore;
        synchronized (BBDUIDataStore.class) {
            if (sInstance == null) {
                sInstance = new BBDUIDataStore();
            }
            bBDUIDataStore = sInstance;
        }
        return bBDUIDataStore;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void addUIData(BBDUIObject bBDUIObject) {
        GDLog.DBGPRINTF(14, "BBDUIDataStore add: " + bBDUIObject + " " + bBDUIObject.getCoreHandle() + "\n");
        uiMap.put(Long.valueOf(bBDUIObject.getCoreHandle()), bBDUIObject);
    }

    public synchronized BBDUIObject getUIData(long j) {
        GDLog.DBGPRINTF(14, "BBDUIDataStore get: " + j + "\n");
        return uiMap.get(Long.valueOf(j));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void removeUIData(long j) {
        if (uiMap.containsKey(Long.valueOf(j))) {
            GDLog.DBGPRINTF(14, "BBDUIDataStore remove: " + j + "\n");
            uiMap.remove(Long.valueOf(j));
        } else {
            GDLog.DBGPRINTF(13, "BBDUIDataStore no such ui handle: " + j + "\n");
        }
    }

    public synchronized BBDUIObject getUIData(BBUIType bBUIType) {
        GDLog.DBGPRINTF(14, "BBDUIDataStore get: " + bBUIType + "\n");
        for (Map.Entry<Long, BBDUIObject> entry : uiMap.entrySet()) {
            BBDUIObject value = entry.getValue();
            if (value != null && value.getBBDUIType() == bBUIType) {
                return value;
            }
        }
        return null;
    }
}
