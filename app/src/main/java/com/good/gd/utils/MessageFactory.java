package com.good.gd.utils;

import android.os.Message;

/* loaded from: classes.dex */
public class MessageFactory {
    public static final int MSG_ACTIVITY_PAUSED = 1094;
    public static final int MSG_ACTIVITY_RESUMED = 1095;
    public static final int MSG_CLIENT_ACTIVATION_REQUEST = 1025;
    public static final int MSG_CLIENT_ACTIVITY_PAUSED = 1015;
    public static final int MSG_CLIENT_ACTIVITY_RESUMED = 1016;
    public static final int MSG_CLIENT_AUTHORIZE_REQUEST = 1001;
    public static final int MSG_CLIENT_AUTH_DELEGATE_FAILED_REQUEST = 1011;
    public static final int MSG_CLIENT_CANCEL_STARTUP = 1022;
    public static final int MSG_CLIENT_CERTIFICATE_SIGNING_REQUEST = 1069;
    public static final int MSG_CLIENT_EXECUTE_LOCAL_BLOCK = 1081;
    public static final int MSG_CLIENT_EXECUTE_LOCAL_UNBLOCK = 1082;
    public static final int MSG_CLIENT_EXECUTE_WIPE = 1080;
    public static final int MSG_CLIENT_IDLE_TIMER_EXCEEDED = 1030;
    public static final int MSG_CLIENT_INTERNAL_ACTIVITY_PAUSED = 1017;
    public static final int MSG_CLIENT_INTERNAL_ACTIVITY_RESUMED = 1018;
    public static final int MSG_CLIENT_OPEN_CHANGE_PASSWORD_UI = 1012;
    public static final int MSG_CLIENT_OPEN_LOG_UPLOAD_UI = 1066;
    public static final int MSG_CLIENT_READY_FOR_PROGRESS_UPDATES = 1008;
    public static final int MSG_CLIENT_RETRY_PAIRING = 1021;
    public static final int MSG_CLIENT_START_PROVISIONING = 1004;
    public static final int MSG_CLIENT_TEMP_UNLOCK_REQUEST = 1026;
    public static final int MSG_CLIENT_UI_HOST_UPDATE_EVENT = 1072;
    public static final int MSG_CLIENT_UI_MSG = 1070;
    public static final int MSG_CLIENT_UI_SESSION_INIT = 1002;
    public static final int MSG_CLIENT_UI_UPDATE_EVENT = 1071;
    public static final int MSG_CLIENT_USER_ACTIVITY = 1014;
    public static final int MSG_CLIENT_WIPE_ACKNOWLEDGED = 1013;
    public static final int MSG_CLIENT_WIPE_INCORRECT_AUTHDEL_PWD = 1023;
    public static final int MSG_ENTERING_BACKGROUND = 1090;
    public static final int MSG_ENTERING_FOREGROUND = 1091;
    public static final int MSG_FINGERPRINT_OPEN_SETTINGS_UI = 1065;
    public static final int MSG_FOCUS_GAINED = 1093;
    public static final int MSG_FOCUS_LOST = 1092;
    public static final int MSG_SAFE_WIFI_OPEN_SETUP_UI = 1067;
    public static final int MSG_SERVICE_APP_EVENT_CALLBACK = 1031;

    public static String idToString(int i) {
        if (i != 1001) {
            if (i == 1002) {
                return "MSG_CLIENT_UI_SESSION_INIT";
            }
            if (i == 1004) {
                return "MSG_CLIENT_START_PROVISIONING";
            }
            if (i == 1025) {
                return "MSG_CLIENT_ACTIVATION_REQUEST";
            }
            if (i == 1065) {
                return "MSG_FINGERPRINT_OPEN_SETTINGS_UI";
            }
            if (i == 1066) {
                return "MSG_CLIENT_OPEN_LOG_UPLOAD_UI";
            }
            switch (i) {
                case 1011:
                    return "MSG_CLIENT_AUTH_DELEGATE_FAILED_REQUEST";
                case 1012:
                    return "MSG_CLIENT_OPEN_CHANGE_PASSWORD_UI";
                case 1013:
                    return "MSG_CLIENT_WIPE_ACKNOWLEDGED";
                case 1014:
                    return "MSG_CLIENT_USER_ACTIVITY";
                case 1015:
                    return "MSG_CLIENT_ACTIVITY_PAUSED";
                case 1016:
                    return "MSG_CLIENT_ACTIVITY_RESUMED";
                case 1017:
                    return "MSG_CLIENT_INTERNAL_ACTIVITY_PAUSED";
                case 1018:
                    return "MSG_CLIENT_INTERNAL_ACTIVITY_RESUMED";
                default:
                    switch (i) {
                        case 1021:
                            return "MSG_CLIENT_RETRY_PAIRING";
                        case MSG_CLIENT_CANCEL_STARTUP /* 1022 */:
                            return "MSG_CLIENT_CANCEL_STARTUP";
                        case MSG_CLIENT_WIPE_INCORRECT_AUTHDEL_PWD /* 1023 */:
                            return "MSG_CLIENT_WIPE_INCORRECT_AUTHDEL_PWD";
                        default:
                            switch (i) {
                                case MSG_CLIENT_CERTIFICATE_SIGNING_REQUEST /* 1069 */:
                                    return "MSG_CLIENT_CERTIFICATE_SIGNING_REQUEST";
                                case MSG_CLIENT_UI_MSG /* 1070 */:
                                    return "MSG_CLIENT_UI_MSG";
                                case MSG_CLIENT_UI_UPDATE_EVENT /* 1071 */:
                                    return "MSG_CLIENT_UI_UPDATE_EVENT";
                                case MSG_CLIENT_UI_HOST_UPDATE_EVENT /* 1072 */:
                                    return "MSG_CLIENT_UI_HOST_UPDATE_EVENT";
                                default:
                                    switch (i) {
                                        case MSG_ENTERING_BACKGROUND /* 1090 */:
                                            return "MSG_ENTERING_BACKGROUND";
                                        case MSG_ENTERING_FOREGROUND /* 1091 */:
                                            return "MSG_ENTERING_FOREGROUND";
                                        case MSG_FOCUS_LOST /* 1092 */:
                                            return "MSG_FOCUS_LOST";
                                        case MSG_FOCUS_GAINED /* 1093 */:
                                            return "MSG_FOCUS_GAINED";
                                        case MSG_ACTIVITY_PAUSED /* 1094 */:
                                            return "MSG_ACTIVITY_PAUSED";
                                        case MSG_ACTIVITY_RESUMED /* 1095 */:
                                            return "MSG_ACTIVITY_RESUMED";
                                        default:
                                            return "Unknown message id " + i;
                                    }
                            }
                    }
            }
        }
        return "MSG_CLIENT_AUTHORIZE_REQUEST";
    }

    public static Message newMessage(int i) {
        return Message.obtain(null, i, 0, 0, null);
    }

    public static String showMessage(Message message) {
        StringBuilder append = new StringBuilder().append(idToString(message.what)).append("(");
        Object obj = message.obj;
        if (obj == null) {
            obj = "";
        }
        return append.append(obj).append(")").toString();
    }

    public static Message newMessage(int i, Object obj) {
        return Message.obtain(null, i, 0, 0, obj);
    }
}
