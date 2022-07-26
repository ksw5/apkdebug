package com.good.gd.utils;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.res.XmlResourceParser;
import com.good.gd.apache.http.cookie.ClientCookie;
import com.good.gd.context.GDContext;
import com.good.gd.error.GDInitializationError;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.utils.ReflectionUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class GDBackupSchemeValidator {
    private static final String backupComparisonString = "app_data/.DContainer/.gdstartupdata";
    private static final String backupComparisonString2 = "app_data/.DContainer/.gdstartupdata2";

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public enum Result {
        SUCCESS("Successfully processed"),
        FLAG_ALLOW_BACKUP_MISSING("FLAG_ALLOW_BACKUP is set but the Manifest file is missing attribute fullBackupContent."),
        BAD_EXCLUDE_LIST("\nAutoBackup xml file is missing the GD startup files from its exclude list, exclude gdstartupdata and gdstartupdata2 files from being backed up.\n"),
        GDSTARTUPDATA_NOT_EXCLUDED("AutoBackup xml file doesn't exclude gdstartupdata, this file should be excluded from backup."),
        GDSTARTUPDATA2_NOT_EXCLUDED("AutoBackup xml file doesn't exclude gdstartupdata2, this file should be excluded from backup."),
        GDSTARTUPDATA_INCLUDED("AutoBackup xml file gdstartupdata, added to the include backup list.This file shouldn't be backed up \n"),
        GDSTARTUPDATA2_INCLUDED("AutoBackup xml file gdstartupdata2, added to the include backup list.This file shouldn't be backed up \n");
        
        private String dbjc;

        Result(String str) {
            this.dbjc = str;
        }

        String dbjc() {
            return this.dbjc;
        }
    }

    public static void checkAndroidBackupConfiguration(PackageInfo packageInfo) {
        if (!isBackupAllowed(packageInfo)) {
            return;
        }
        if (!ReflectionUtils.canUseReflectionInAndroidPorLater()) {
            GDLog.DBGPRINTF(13, "Backup is allowed but backup scheme cannot be verified because of reflection limitations\n");
            return;
        }
        Result processXMLScheme = processXMLScheme(getFullBackupContentValue(packageInfo));
        if (processXMLScheme != Result.SUCCESS) {
            throw new GDInitializationError(processXMLScheme.dbjc());
        }
    }

    private static boolean checkValueInList(String str, List<String> list) {
        Iterator<String> it = list.iterator();
        boolean z = false;
        while (it.hasNext() && !(z = it.next().toLowerCase(Locale.ENGLISH).equals(str.toLowerCase(Locale.ENGLISH)))) {
        }
        return z;
    }

    private static int getFullBackupContentValue(PackageInfo packageInfo) {
        try {
            return ((Integer) ReflectionUtils.getFieldValue(ApplicationInfo.class, packageInfo.applicationInfo, "fullBackupContent")).intValue();
        } catch (ReflectionUtils.FieldAccessException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static boolean isBackupAllowed(PackageInfo packageInfo) {
        return (packageInfo.applicationInfo.flags & 32768) == 32768;
    }

    private static Result processStringAfterXmlParsing(boolean z, boolean z2, ArrayList<String> arrayList, ArrayList<String> arrayList2) {
        if (arrayList.size() < 2) {
            return Result.BAD_EXCLUDE_LIST;
        }
        if (z) {
            if (!checkValueInList(backupComparisonString, arrayList)) {
                return Result.GDSTARTUPDATA_NOT_EXCLUDED;
            }
            if (!checkValueInList(backupComparisonString2, arrayList)) {
                return Result.GDSTARTUPDATA2_NOT_EXCLUDED;
            }
        }
        if (z2 && arrayList2.size() > 0) {
            if (checkValueInList(backupComparisonString, arrayList2)) {
                return Result.GDSTARTUPDATA_INCLUDED;
            }
            if (checkValueInList(backupComparisonString2, arrayList2)) {
                return Result.GDSTARTUPDATA2_INCLUDED;
            }
        }
        return Result.SUCCESS;
    }

    private static Result processXMLScheme(int i) {
        int i2;
        boolean z;
        boolean z2;
        boolean z3;
        if (i == 0) {
            return Result.FLAG_ALLOW_BACKUP_MISSING;
        }
        XmlResourceParser xml = GDContext.getInstance().getApplicationContext().getResources().getXml(i);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        try {
            i2 = xml.getEventType();
            z = false;
            z2 = false;
            z3 = false;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            i2 = 0;
            z = false;
            z2 = false;
            z3 = false;
        }
        while (i2 != 1) {
            String name = xml.getName();
            if (i2 != 2) {
                if (i2 != 3) {
                    if (i2 == 4 && z3) {
                        if (z) {
                            arrayList.add(xml.getText());
                        } else if (z2) {
                            arrayList2.add(xml.getText());
                        }
                    }
                } else if (name != null) {
                    if (name.equalsIgnoreCase("exclude")) {
                        z3 = false;
                        z = true;
                    } else if (name.equalsIgnoreCase("include")) {
                        z3 = false;
                        z2 = true;
                    }
                }
            } else if (name != null) {
                if (name.equalsIgnoreCase("exclude")) {
                    if (xml.getAttributeCount() == 2) {
                        arrayList.add(xml.getAttributeValue(null, ClientCookie.PATH_ATTR));
                        z = true;
                    } else {
                        z = true;
                        z3 = true;
                    }
                }
                if (name.equalsIgnoreCase("include")) {
                    if (xml.getAttributeCount() == 2) {
                        arrayList2.add(xml.getAttributeValue(null, ClientCookie.PATH_ATTR));
                        z2 = true;
                    } else {
                        z2 = true;
                        z3 = true;
                    }
                }
            }
            try {
                i2 = xml.next();
            } catch (IOException | XmlPullParserException e2) {
                e2.printStackTrace();
            }
        }
        return processStringAfterXmlParsing(z, z2, arrayList, arrayList2);
    }
}
