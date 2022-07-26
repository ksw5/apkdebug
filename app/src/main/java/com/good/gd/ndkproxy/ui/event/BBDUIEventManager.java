package com.good.gd.ndkproxy.ui.event;

import android.os.Handler;
import android.os.Message;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.ui.BBDUIManager;
import com.good.gd.ndkproxy.ui.data.base.BBDUIObject;
import com.good.gd.utils.MessageFactory;

/* loaded from: classes.dex */
public class BBDUIEventManager {
    private static BBDUIEventManager sInstance;
    private Handler mObserver = null;

    public static BBDUIEventManager getInstance() {
        if (sInstance == null) {
            sInstance = new BBDUIEventManager();
        }
        return sInstance;
    }

    public static void processEvent(Message message) {
        BBDUIObjectMsg bBDUIObjectMsg = (BBDUIObjectMsg) message.obj;
        BBDUIObject target = bBDUIObjectMsg.getTarget();
        if (target != null) {
            BBDUIManager.getInstance().updateUI(target, bBDUIObjectMsg.getUpdateEvent());
        } else {
            GDLog.DBGPRINTF(13, "BBDUIEventManager.processEvent: no ui to send event to\n");
        }
    }

    public static void processHostEvent(Message message) {
        BBDUIManager.getInstance().updateHost(((BBDUIObjectMsg) message.obj).getUpdateEvent());
    }

    public static void processMessage(Message message) {
        BBDUIObjectMsg bBDUIObjectMsg = (BBDUIObjectMsg) message.obj;
        BBDUIObject target = bBDUIObjectMsg.getTarget();
        if (target != null) {
            target.onMessage(bBDUIObjectMsg.getType(), bBDUIObjectMsg.getData());
        }
    }

    public static void sendHostUpdateEvent(BBDUIUpdateEvent bBDUIUpdateEvent) {
        Message obtain = Message.obtain(null, MessageFactory.MSG_CLIENT_UI_HOST_UPDATE_EVENT, 0, 0, new BBDUIObjectMsg(bBDUIUpdateEvent));
        GDLog.DBGPRINTF(14, "BBDUIEventManager.sendHostUpdateEvent: event = " + bBDUIUpdateEvent + "\n");
        getInstance().mObserver.sendMessage(obtain);
    }

    public static void sendMessage(BBDUIObject bBDUIObject, BBDUIMessageType bBDUIMessageType, Object obj) {
        Message obtain = Message.obtain(null, MessageFactory.MSG_CLIENT_UI_MSG, 0, 0, new BBDUIObjectMsg(bBDUIObject, bBDUIMessageType, obj));
        GDLog.DBGPRINTF(14, "BBDUIEventManager.sendMessage: target = " + bBDUIObject + " type = " + bBDUIMessageType + " data = " + obj + "\n");
        getInstance().mObserver.sendMessage(obtain);
    }

    public static void sendUpdateEvent(BBDUIUpdateEvent bBDUIUpdateEvent, BBDUIObject bBDUIObject) {
        Message obtain = Message.obtain(null, MessageFactory.MSG_CLIENT_UI_UPDATE_EVENT, 0, 0, new BBDUIObjectMsg(bBDUIObject, bBDUIUpdateEvent));
        GDLog.DBGPRINTF(14, "BBDUIEventManager.sendUpdateEvent: event = " + bBDUIUpdateEvent + " data " + bBDUIObject + "\n");
        getInstance().mObserver.sendMessage(obtain);
    }

    public void setEventsObserver(Handler handler) {
        this.mObserver = handler;
    }

    public static void sendMessage(BBDUIObject bBDUIObject, BBDUIMessageType bBDUIMessageType) {
        sendMessage(bBDUIObject, bBDUIMessageType, null);
    }
}
