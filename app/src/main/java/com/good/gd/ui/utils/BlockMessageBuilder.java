package com.good.gd.ui.utils;

import android.os.Build;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.ui.data.BlockFacade;
import com.good.gd.utils.GDLocalizer;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/* loaded from: classes.dex */
public class BlockMessageBuilder {
    private ApplicationInfoProvider applicationInfo;
    private BlockFacade blockFacade;
    private List<String> keys = new LinkedList();
    private Locale locale = GDLocalizer.getSpecifiedLocale();
    private Object[] params;
    private String resultMessage;

    public BlockMessageBuilder(BlockFacade blockFacade, ApplicationInfoProvider applicationInfoProvider) {
        this.blockFacade = blockFacade;
        this.applicationInfo = applicationInfoProvider;
    }

    private String buildResult() {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (String str : this.keys) {
            String localizedString = GDLocalizer.getLocalizedString(str);
            if (localizedString.contains("%@") && this.params != null) {
                String[] split = localizedString.split("%@");
                StringBuilder sb2 = new StringBuilder(split[0]);
                for (int i2 = 1; i2 < split.length; i2++) {
                    Object[] objArr = this.params;
                    if (i < objArr.length) {
                        sb2.append(objArr[i]);
                        i++;
                    }
                    sb2.append(split[i2]);
                }
                sb.append((CharSequence) sb2);
            } else {
                sb.append(localizedString);
            }
        }
        if (this.params != null) {
            return String.format(sb.toString(), this.params).trim();
        }
        return sb.toString().trim();
    }

    public BlockMessageBuilder addMessageKey(String str) {
        this.keys.add(str);
        return this;
    }

    public String build() {
        Locale specifiedLocale = GDLocalizer.getSpecifiedLocale();
        if (this.resultMessage == null || !specifiedLocale.equals(this.locale)) {
            this.resultMessage = buildResult();
            this.locale = specifiedLocale;
        }
        return this.resultMessage;
    }

    public BlockMessageBuilder setLocale(Locale locale) {
        this.locale = locale;
        return this;
    }

    public BlockMessageBuilder setParams(Object... objArr) {
        this.params = objArr;
        return this;
    }

    public BlockMessageBuilder setBlockMessage(int i, String str, boolean z) {
        String localizedString;
        String str2 = "Application access blocked";
        if (i != 24) {
            if (i != 26) {
                if (i != 31) {
                    switch (i) {
                        case 0:
                            addMessageKey("Wipe/Block key changed");
                            break;
                        case 1:
                            addMessageKey("Wipe/Block App Denied").setParams(this.applicationInfo.getApplicationId(), this.applicationInfo.getApplicationVersion());
                            break;
                        case 2:
                            addMessageKey("Wipe/Block NEM Mode");
                            break;
                        case 3:
                            addMessageKey("Wipe/Block Incompliant lib version").setParams(this.applicationInfo.getClientVersion());
                            break;
                        case 4:
                            addMessageKey("Wipe/Block OS Incompliant device").setParams(Build.MODEL + " Android " + Build.VERSION.RELEASE);
                            break;
                        case 5:
                            addMessageKey("Wipe/Block Incompliant device").setParams(Build.MODEL + " Android " + Build.VERSION.RELEASE);
                            break;
                        case 6:
                            addMessageKey("Wipe/Block Rooted");
                            break;
                        case 7:
                            int connComplianceTimebomb = this.blockFacade.getConnComplianceTimebomb();
                            if (connComplianceTimebomb > 0 && connComplianceTimebomb < 24) {
                                localizedString = GDLocalizer.getLocalizedString("Wipe/Block Connectivity in hours");
                            } else if (connComplianceTimebomb > 24) {
                                localizedString = GDLocalizer.getLocalizedString("Wipe/Block Connectivity in days");
                                connComplianceTimebomb /= 24;
                            } else {
                                GDLog.DBGPRINTF(13, "GDBlockStage.setBlockMessage: Invalid connection compliance value");
                                localizedString = GDLocalizer.getLocalizedString("Wipe/Block Connectivity in days");
                            }
                            addMessageKey(localizedString).setParams(Integer.valueOf(connComplianceTimebomb));
                            break;
                        case 8:
                            addMessageKey(GDLocalizer.getLocalizedString("Wipe/Block Max password attemnts exceeded")).setParams(Integer.valueOf(this.blockFacade.getMaxPwdAttempts()));
                            break;
                        case 9:
                            addMessageKey("Wipe reason - FIPS cannot be enabled in this app");
                            break;
                        case 10:
                            addMessageKey("Wipe reason - FIPS is not available for 64bit arch");
                            break;
                        case 11:
                            addMessageKey("Wipe reason - FIPS failed to activate");
                            break;
                        case 12:
                            addMessageKey("Remote lock wipe reason unlock description");
                            break;
                        case 13:
                            addMessageKey("Wipe/Block MDM required");
                            break;
                        default:
                            switch (i) {
                                case 15:
                                    addMessageKey("Unauthorized version.");
                                    break;
                                case 16:
                                    addMessageKey(z ? "The version of [App Name] has been wiped" : "The version of [App Name] is blocked").setParams(this.applicationInfo.getClientBundleVersion(), this.applicationInfo.getApplicationName());
                                    break;
                                case 17:
                                case 18:
                                    break;
                                default:
                                    if (z) {
                                        str2 = "Your device has been wiped!";
                                    }
                                    addMessageKey(str2);
                                    break;
                            }
                    }
                } else {
                    addMessageKey(str);
                }
            } else if (str != null && str.length() > 0) {
                if (str.split("\r?\n").length > 1) {
                    addMessageKey("MTD APK Scanning: Block UI: Message with multiple itemsMTD APK Scanning: Block UI: Uninstall Message with multiple items").setParams(str);
                } else {
                    addMessageKey("MTD APK Scanning: Block UI: Message with one itemMTD APK Scanning: Block UI: Uninstall Message with one item").setParams(str);
                }
            }
            return this;
        }
        if (str != null && str.length() > 0) {
            addMessageKey(str);
        } else {
            addMessageKey(str2);
        }
        return this;
    }
}
