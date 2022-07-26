package com.good.gd.ndkproxy;

/* loaded from: classes.dex */
public enum PMState {
    EPM_STATUS_PROV_INIT,
    NOC_EPM_STATUS_WAIT_FOR_NOC_CONNECTION,
    NOC_EPM_STATUS_PROV_CHECK_FOR_NEM,
    NOC_EPM_STATUS_PROV_ENTERPRISE_NOC,
    NOC_EPM_STATUS_PROV_START_PUSH_CHANNEL,
    NOC_EPM_STATUS_PROV_NEGOTIATE_REQUEST,
    EPM_STATUS_GET_ACTIVATION_INFO,
    EPM_STATUS_GET_SERVER_CAPABILITIES,
    EPM_STATUS_GET_ACCESS_TOKEN,
    EPM_STATUS_PERFORM_KEY_EXCHANGE,
    EPM_STATUS_PRE_PROV_REQUEST,
    EPM_STATUS_PROV_DATA_REQUEST,
    EPM_STATUS_PROV_COMPLETE,
    EPM_STATUS_PROV_RESET_COMPLETE,
    EPM_STATUS_CTP_PERMISSIONS,
    EPM_STATUS_CTP_PERMISSIONS_COMPLETE,
    EPM_STATUS_POLICY_DOWNLOAD,
    EPM_STATUS_POLICY_DOWNLOADED,
    EPM_STATUS_POLICY_SET_COMPLETE,
    NOC_EPM_STATUS_UPGRADE_NOC_REQUEST,
    NOC_EPM_STATUS_TAM_ACTIVATION,
    INVALID_VALUE;

    public static PMState convertFromIntegerValue(int i) {
        switch (i) {
            case 0:
                return EPM_STATUS_PROV_INIT;
            case 1:
                return NOC_EPM_STATUS_WAIT_FOR_NOC_CONNECTION;
            case 2:
                return NOC_EPM_STATUS_PROV_CHECK_FOR_NEM;
            case 3:
                return NOC_EPM_STATUS_PROV_ENTERPRISE_NOC;
            case 4:
                return NOC_EPM_STATUS_PROV_START_PUSH_CHANNEL;
            case 5:
                return NOC_EPM_STATUS_PROV_NEGOTIATE_REQUEST;
            case 6:
                return EPM_STATUS_GET_ACTIVATION_INFO;
            case 7:
                return EPM_STATUS_GET_SERVER_CAPABILITIES;
            case 8:
                return EPM_STATUS_GET_ACCESS_TOKEN;
            case 9:
                return EPM_STATUS_PERFORM_KEY_EXCHANGE;
            case 10:
                return EPM_STATUS_PRE_PROV_REQUEST;
            case 11:
                return EPM_STATUS_PROV_DATA_REQUEST;
            case 12:
                return EPM_STATUS_PROV_COMPLETE;
            case 13:
                return EPM_STATUS_PROV_RESET_COMPLETE;
            case 14:
                return EPM_STATUS_CTP_PERMISSIONS;
            case 15:
                return EPM_STATUS_CTP_PERMISSIONS_COMPLETE;
            case 16:
                return EPM_STATUS_POLICY_DOWNLOAD;
            case 17:
                return EPM_STATUS_POLICY_DOWNLOADED;
            case 18:
                return EPM_STATUS_POLICY_SET_COMPLETE;
            case 19:
                return NOC_EPM_STATUS_UPGRADE_NOC_REQUEST;
            case 20:
                return NOC_EPM_STATUS_TAM_ACTIVATION;
            default:
                return INVALID_VALUE;
        }
    }
}
