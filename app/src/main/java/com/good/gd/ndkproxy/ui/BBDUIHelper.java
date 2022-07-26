package com.good.gd.ndkproxy.ui;

import android.view.View;
import com.good.gd.ndkproxy.ui.data.base.BBDUIObject;
import com.good.gd.ndkproxy.ui.event.BBDUIEventManager;
import com.good.gd.ndkproxy.ui.event.BBDUIMessageType;
import com.good.gd.utils.GDLocalizer;

/* loaded from: classes.dex */
public final class BBDUIHelper {

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class hbfhc implements View.OnClickListener {
        final /* synthetic */ BBDUIObject dbjc;
        final /* synthetic */ BBDUIMessageType qkduk;

        hbfhc(BBDUIObject bBDUIObject, BBDUIMessageType bBDUIMessageType) {
            this.dbjc = bBDUIObject;
            this.qkduk = bBDUIMessageType;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            BBDUIEventManager.sendMessage(this.dbjc, this.qkduk);
        }
    }

    private static native void _bottomButton(long j);

    private static native boolean _canBeClosed(long j);

    private static native void _cancel(long j);

    private static native void _clearSensitiveData(long j);

    private static native void _dialogAction(long j);

    private static native String _getActivationEmail(long j);

    private static native String _getActivationPassword(long j);

    private static native String _getBcpUrl(long j);

    private static native String _getEmail(long j);

    private static native String _getLocalizableBottomButtonKey(long j);

    private static native String _getLocalizableCancelButtonKey(long j);

    private static native String _getLocalizableMessageKey(long j);

    private static native String _getLocalizableOkButtonKey(long j);

    private static native String _getLocalizableTitleKey(long j);

    private static native String _getLocalizedCoreMessage(long j);

    private static native String _getLocalizedCoreTitle(long j);

    private static native String _getPassword(long j);

    private static native String _getUIName(long j);

    private static native int _getUIPriority(long j);

    private static native boolean _hasBCPURLField(long j);

    private static native boolean _hasBottomButton(long j);

    private static native boolean _hasCancelButton(long j);

    private static native boolean _hasHelpButton(long j);

    private static native boolean _hasOkButton(long j);

    private static native boolean _hasQRCodeOkButton(long j);

    private static native void _help(long j);

    private static native boolean _isRemoteLockFlow(long j);

    private static native void _ok(long j);

    public static void bottomButton(long j) {
        if (j != 0) {
            _bottomButton(j);
        }
    }

    public static boolean canBeClosed(long j) {
        if (j != 0) {
            return _canBeClosed(j);
        }
        return false;
    }

    public static void cancel(long j) {
        if (j != 0) {
            _cancel(j);
        }
    }

    public static void clearSensitiveData(long j) {
        if (j != 0) {
            _clearSensitiveData(j);
        }
    }

    public static void dialogAction(long j) {
        if (j != 0) {
            _dialogAction(j);
        }
    }

    public static View.OnClickListener getClickListenerForMessageType(BBDUIObject bBDUIObject, BBDUIMessageType bBDUIMessageType) {
        return new hbfhc(bBDUIObject, bBDUIMessageType);
    }

    public static String getEnteredActivationEmail(long j) {
        return j != 0 ? _getActivationEmail(j) : "";
    }

    public static String getEnteredActivationPassword(long j) {
        return j != 0 ? _getActivationPassword(j) : "";
    }

    public static String getEnteredBcpUrl(long j) {
        return j != 0 ? _getBcpUrl(j) : "";
    }

    public static String getEnteredEmail(long j) {
        return j != 0 ? _getEmail(j) : "";
    }

    public static String getEnteredPassword(long j) {
        return j != 0 ? _getPassword(j) : "";
    }

    public static String getLocalizableBottomButton(long j) {
        return j != 0 ? _getLocalizableBottomButtonKey(j) : "";
    }

    public static String getLocalizableMessage(long j) {
        return j != 0 ? _getLocalizableMessageKey(j) : "";
    }

    public static String getLocalizableTitle(long j) {
        return j != 0 ? _getLocalizableTitleKey(j) : "";
    }

    public static String getLocalizedBottomButton(long j) {
        return j != 0 ? GDLocalizer.getLocalizedString(_getLocalizableBottomButtonKey(j)) : "";
    }

    public static String getLocalizedCancelButton(long j) {
        return j != 0 ? GDLocalizer.getLocalizedString(_getLocalizableCancelButtonKey(j)) : "";
    }

    public static String getLocalizedCoreMessage(long j) {
        return j != 0 ? _getLocalizedCoreMessage(j) : "";
    }

    public static String getLocalizedCoreTitle(long j) {
        return j != 0 ? _getLocalizedCoreTitle(j) : "";
    }

    public static String getLocalizedMessage(long j) {
        if (j != 0) {
            String _getLocalizableMessageKey = _getLocalizableMessageKey(j);
            return !_getLocalizableMessageKey.isEmpty() ? GDLocalizer.getLocalizedString(_getLocalizableMessageKey) : "";
        }
        return "";
    }

    public static String getLocalizedOkButton(long j) {
        return j != 0 ? GDLocalizer.getLocalizedString(_getLocalizableOkButtonKey(j)) : "";
    }

    public static String getLocalizedTitle(long j) {
        return j != 0 ? GDLocalizer.getLocalizedString(_getLocalizableTitleKey(j)) : "";
    }

    public static String getUIName(long j) {
        if (j != 0) {
            return _getUIName(j);
        }
        throw new IllegalStateException("BBDUIHelper.getUIName: BBDUI subclass was not found: " + toHex(j));
    }

    public static int getUIPriority(long j) {
        if (j != 0) {
            return _getUIPriority(j);
        }
        throw new IllegalStateException("BBDUIHelper.getUIPriority: BBDUI subclass was not found: " + toHex(j));
    }

    public static boolean hasBCPURLField(long j) {
        if (j != 0) {
            return _hasBCPURLField(j);
        }
        return false;
    }

    public static boolean hasBottomButton(long j) {
        if (j != 0) {
            return _hasBottomButton(j);
        }
        return false;
    }

    public static boolean hasCancelButton(long j) {
        if (j != 0) {
            return _hasCancelButton(j);
        }
        return false;
    }

    public static boolean hasHelpButton(long j) {
        if (j != 0) {
            return _hasHelpButton(j);
        }
        return false;
    }

    public static boolean hasOkButton(long j) {
        if (j != 0) {
            return _hasOkButton(j);
        }
        return false;
    }

    public static boolean hasQRCodeOkButton(long j) {
        if (j != 0) {
            return _hasQRCodeOkButton(j);
        }
        return false;
    }

    public static void help(long j) {
        if (j != 0) {
            _help(j);
        }
    }

    public static boolean isBypassAllowed(long j) {
        if (j != 0) {
            return isBypassAllowedImpl(j);
        }
        return true;
    }

    private static native boolean isBypassAllowedImpl(long j);

    public static boolean isRemoteLockFlow(long j) {
        if (j != 0) {
            return _isRemoteLockFlow(j);
        }
        return false;
    }

    public static void ok(long j) {
        if (j != 0) {
            _ok(j);
        }
    }

    public static String toHex(long j) {
        if (j < 0) {
            return "-" + Long.toHexString(-j);
        }
        return Long.toHexString(j);
    }
}
