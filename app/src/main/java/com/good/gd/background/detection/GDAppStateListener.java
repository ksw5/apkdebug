package com.good.gd.background.detection;

import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.utils.MessageFactory;

/* loaded from: classes.dex */
public class GDAppStateListener implements AppStateListener {
    private static final String TAG = "GDAppStateListener";
    private Messenger _serviceMessenger;

    public GDAppStateListener(Messenger messenger) {
        this._serviceMessenger = messenger;
    }

    private boolean sendMessageOptional(Message message) {
        Messenger messenger = this._serviceMessenger;
        if (messenger != null) {
            try {
                messenger.send(message);
                return true;
            } catch (RemoteException e) {
                throw new RuntimeException("BBDAppBackgroundDetector.sendMessageToServer: ", e);
            }
        }
        return false;
    }

    @Override // com.good.gd.background.detection.ActivityResumedAndPausedListener
    public void onActivityPaused() {
        sendMessageOptional(MessageFactory.newMessage(MessageFactory.MSG_ACTIVITY_PAUSED));
    }

    @Override // com.good.gd.background.detection.ActivityResumedAndPausedListener
    public void onActivityResumed() {
        sendMessageOptional(MessageFactory.newMessage(MessageFactory.MSG_ACTIVITY_RESUMED));
    }

    @Override // com.good.gd.background.detection.AppForegroundChangedListener
    public void onBackgroundEntering() {
        if (sendMessageOptional(MessageFactory.newMessage(MessageFactory.MSG_ENTERING_BACKGROUND))) {
            GDLog.DBGPRINTF(16, TAG, "onBackgroundEntering sent\n");
        }
    }

    @Override // com.good.gd.background.detection.AppFocusChangedListener
    public void onFocusLost() {
        GDLog.DBGPRINTF(16, TAG, "onFocusLost\n");
        sendMessageOptional(MessageFactory.newMessage(MessageFactory.MSG_FOCUS_LOST));
    }

    @Override // com.good.gd.background.detection.AppFocusChangedListener
    public void onFocused() {
        GDLog.DBGPRINTF(16, TAG, "onFocused\n");
        sendMessageOptional(MessageFactory.newMessage(MessageFactory.MSG_FOCUS_GAINED));
    }

    @Override // com.good.gd.background.detection.AppForegroundChangedListener
    public void onForegroundEntering() {
        if (sendMessageOptional(MessageFactory.newMessage(MessageFactory.MSG_ENTERING_FOREGROUND))) {
            GDLog.DBGPRINTF(16, TAG, "onForegroundEntering sent\n");
        }
    }
}
