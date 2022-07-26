package com.good.gd.dlp_util;

import android.content.Context;
import android.content.res.Configuration;
import com.good.gd.content_Impl.GDClipboardManagerImpl;
import com.good.gt.util.OSCheckUtils;

/* loaded from: classes.dex */
public class GDCustomROMDLPUtil {
    public static void clearClipBoardIfRequired() {
        if (GDClipboardManagerImpl.getInstance().isPasteFromNonGDAppEnabled() || !OSCheckUtils.getInstance().isMiuiROM()) {
            return;
        }
        GDClipboardManagerImpl.getInstance().emptyClipBoard();
    }

    public static boolean isInDeskTopMode(Context context) {
        Configuration configuration = context.getResources().getConfiguration();
        try {
            Class<?> cls = configuration.getClass();
            return cls.getField("SEM_DESKTOP_MODE_ENABLED").getInt(cls) == cls.getField("semDesktopModeEnabled").getInt(configuration);
        } catch (IllegalAccessException e) {
            return false;
        } catch (IllegalArgumentException e2) {
            return false;
        } catch (NoSuchFieldException e3) {
            return false;
        }
    }

    public static boolean shouldExplicitlyBlockAllDlpActions() {
        return GDClipboardManagerImpl.getInstance().isGDSecureClipBoardEnabled() && OSCheckUtils.getInstance().isMiuiROM();
    }
}
