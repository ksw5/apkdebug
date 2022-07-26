package com.good.gd;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class GDVersion implements Comparable<GDVersion> {
    List<Integer> _version;

    public GDVersion(String str) {
        if (isValidVersion(str)) {
            this._version = new ArrayList();
            for (String str2 : str.split("\\.")) {
                this._version.add(Integer.valueOf(str2));
            }
            return;
        }
        throw new IllegalArgumentException("Version is invalid: " + str);
    }

    private static native boolean isValidVersion(String str);

    public boolean isEqualToVersion(GDVersion gDVersion) {
        if (gDVersion != null && numberOfVersionParts() == gDVersion.numberOfVersionParts()) {
            for (int i = 0; i < numberOfVersionParts(); i++) {
                if (versionPartAtPosition(i) != gDVersion.versionPartAtPosition(i)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean isGreaterThanVersion(GDVersion gDVersion) {
        return gDVersion != null && !isEqualToVersion(gDVersion) && isGreaterThanVersionInternal(gDVersion);
    }

    boolean isGreaterThanVersionInternal(GDVersion gDVersion) {
        int numberOfVersionParts = numberOfVersionParts();
        int numberOfVersionParts2 = gDVersion.numberOfVersionParts();
        for (int i = 0; i < numberOfVersionParts && i < numberOfVersionParts2; i++) {
            int versionPartAtPosition = versionPartAtPosition(i);
            int versionPartAtPosition2 = gDVersion.versionPartAtPosition(i);
            if (versionPartAtPosition > versionPartAtPosition2) {
                return true;
            }
            if (versionPartAtPosition != versionPartAtPosition2) {
                return false;
            }
        }
        return numberOfVersionParts > numberOfVersionParts2;
    }

    public boolean isLessThanVersion(GDVersion gDVersion) {
        return gDVersion != null && !isEqualToVersion(gDVersion) && !isGreaterThanVersionInternal(gDVersion);
    }

    public int numberOfVersionParts() {
        return this._version.size();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        int numberOfVersionParts = numberOfVersionParts();
        for (int i = 0; i < numberOfVersionParts; i++) {
            sb.append(versionPartAtPosition(i));
            if (i != numberOfVersionParts - 1) {
                sb.append(".");
            }
        }
        return sb.toString();
    }

    public int versionPartAtPosition(int i) {
        return this._version.get(i).intValue();
    }

    @Override // java.lang.Comparable
    public int compareTo(GDVersion gDVersion) {
        if (gDVersion == null) {
            return -1;
        }
        if (isEqualToVersion(gDVersion)) {
            return 0;
        }
        return isGreaterThanVersion(gDVersion) ? 1 : -1;
    }

    public GDVersion(List<Integer> list) {
        this._version = list;
        String gDVersion = toString();
        if (isValidVersion(gDVersion)) {
            return;
        }
        throw new IllegalArgumentException("Version is invalid: " + gDVersion);
    }
}
