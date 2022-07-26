package com.good.gd.ndkproxy.icc;

import android.content.Intent;
import android.os.SystemClock;
import com.good.gd.ndkproxy.GDClipboardCryptoUtil;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ui_control.UniversalActivityController;
import com.good.gt.context.GTBaseContext;
import com.good.gt.icc.AppControl;
import com.good.gt.icc.AuthDelegationServer;
import com.good.gt.icc.AuthDelegationServerEventsListener;
import com.good.gt.icc.FrontParams;
import com.good.gt.icc.GTInteger;
import com.good.gt.icc.ICCControllerFactory;
import com.good.gt.icc.ListenerAlreadySetException;
import com.good.gt.util.ByteArrayBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes.dex */
public final class AuthDelegationProvider implements AuthDelegationServerEventsListener, AppControl {
    private static AuthDelegationProvider _instance;
    private Set<String> _authDelegatees;
    private final AuthDelegationServer _authDelegationServer;

    public AuthDelegationProvider() {
        AuthDelegationServer authDelegationServer = ICCControllerFactory.getICCController(this).getAuthDelegationServer();
        this._authDelegationServer = authDelegationServer;
        try {
            authDelegationServer.setAuthDelegationServerEventsListener(this);
        } catch (ListenerAlreadySetException e) {
            GDLog.DBGPRINTF(12, e.toString());
        }
    }

    private native byte[] authDelegationRequest(String str);

    private native String[] getAuthDelegateeList(int i);

    public static synchronized AuthDelegationProvider getInstance() {
        AuthDelegationProvider authDelegationProvider;
        synchronized (AuthDelegationProvider.class) {
            if (_instance == null) {
                _instance = new AuthDelegationProvider();
            }
            authDelegationProvider = _instance;
        }
        return authDelegationProvider;
    }

    private native void ndkInit();

    private native void saveAuthDelegateeList(String[] strArr, int i);

    @Override // com.good.gt.icc.AppControl
    public void becomeForeground(FrontParams frontParams) {
        UniversalActivityController.getInstance().handleICCFrontRequest(frontParams);
    }

    public void initialize() throws Exception {
        try {
            ndkInit();
        } catch (Exception e) {
            throw new Exception("AuthDelegationProvider: Cannot initialize Native peer", e);
        }
    }

    @Override // com.good.gt.icc.AuthDelegationServerEventsListener
    public boolean onReceiveAuthRequest(String str, ByteArrayBuffer byteArrayBuffer, GTInteger gTInteger, GTInteger gTInteger2, StringBuilder sb) {
        GDLog.DBGPRINTF(16, "AuthDelegationProvider::onReceiveAuthRequest(application=" + str + ")\n");
        if (str == null) {
            return false;
        }
        gTInteger.setValue(GDIccManager.liflu().qkduk());
        if (gTInteger2 != null) {
            gTInteger2.setValue(GDIccManager.liflu().jwxax());
        }
        if (sb != null) {
            sb.append(GDIccManager.liflu().wxau());
        }
        byte[] authDelegationRequest = authDelegationRequest(str);
        if (authDelegationRequest == null) {
            return false;
        }
        if (this._authDelegatees == null) {
            GDLog.DBGPRINTF(16, "onReceiveAuthRequest::populateAuthDelegateeList \n");
            populateAuthDelegateeList();
        }
        Set<String> set = this._authDelegatees;
        if (set != null && set.add(str)) {
            Set<String> set2 = this._authDelegatees;
            saveAuthDelegateeList((String[]) set2.toArray(new String[set2.size()]), (int) SystemClock.elapsedRealtime());
        }
        byteArrayBuffer.append(authDelegationRequest, 0, authDelegationRequest.length);
        return true;
    }

    public void populateAuthDelegateeList() {
        GDLog.DBGPRINTF(16, "AuthDelegationProvider::populateAuthDelegateeList IN\n");
        String[] authDelegateeList = getAuthDelegateeList((int) SystemClock.elapsedRealtime());
        if (authDelegateeList == null || authDelegateeList.length <= 0) {
            GDLog.DBGPRINTF(16, "AuthDelegationProvider::populateAuthDelegateeList new list\n");
            this._authDelegatees = Collections.synchronizedSet(new HashSet());
            return;
        }
        this._authDelegatees = Collections.synchronizedSet(new HashSet(Arrays.asList(authDelegateeList)));
    }

    public void sendComplianceActionBroadCast() {
        GDLog.DBGPRINTF(16, "AuthDelegationProvider::sendComplianceActionBroadCast IN\n");
        Set<String> set = this._authDelegatees;
        if (set == null || set.size() <= 0) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction("com.blackberry.bbd.intent.action.ACTION_COMPLIANCE_BROADCAST");
        int qkduk = GDIccManager.liflu().qkduk();
        intent.putExtra("extra_info", GDClipboardCryptoUtil.encryptString(qkduk + ":" + SystemClock.elapsedRealtime()));
        for (String str : this._authDelegatees) {
            GDLog.DBGPRINTF(16, "AuthDelegationProvider::sendComplianceActionBroadCast to " + str + "\n");
            intent.setPackage(str);
            GTBaseContext.getInstance().getApplicationContext().sendBroadcast(intent);
        }
    }
}
