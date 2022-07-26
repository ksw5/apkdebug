package com.good.gd.ndkproxy.push;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import com.good.gd.ApplicationContext;
import com.good.gd.GDLocalBroadcastManager;
import com.good.gd.apache.http.protocol.HTTP;
import com.good.gd.containerstate.ContainerState;
import com.good.gd.error.GDInitializationError;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.push.PushChannel;
import com.good.gd.push.PushChannelEventType;
import com.good.gd.push.PushChannelListener;
import com.good.gd.push.PushChannelState;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

/* loaded from: classes.dex */
public class PushChannelImpl implements IPushChannel {
    private static final String GD_PUSH_CHANNEL_AUTHORITY = "com.blackberry.blackberrydynamics";
    private static final String GD_PUSH_CHANNEL_ERROR_EXTRA = "gd_push_channel_error_extra";
    private static final String GD_PUSH_CHANNEL_EVENTTYPE_EXTRA = "gd_push_channel_state_extra";
    private static final String GD_PUSH_CHANNEL_HOST_EXTRA = "gd_push_channel_host_extra";
    private static final String GD_PUSH_CHANNEL_MESSAGE_EXTRA = "gd_push_channel_message_extra";
    private static final String GD_PUSH_CHANNEL_PINGFAIL_EXTRA = "gd_push_channel_pingfail_extra";
    private static final String GD_PUSH_CHANNEL_SCHEME = "pushchannel";
    private static final String GD_PUSH_CHANNEL_TOKEN_EXTRA = "gd_push_channel_token_extra";
    private long _handle;
    private ApplicationContext applicationContext;
    private ContainerState containerState;
    private Uri mDataPath;
    private PushChannelListener _listener = null;
    private PushChannelState mState = PushChannelState.None;

    public PushChannelImpl(ContainerState containerState, ApplicationContext applicationContext) {
        this.containerState = containerState;
        this.applicationContext = applicationContext;
        containerState.checkAuthorized();
        long NDK_init = NDK_init();
        if (NDK_pushHandleValid(NDK_init)) {
            this._handle = NDK_init;
            String uuid = UUID.randomUUID().toString();
            this.mDataPath = new Uri.Builder().scheme(GD_PUSH_CHANNEL_SCHEME).authority(GD_PUSH_CHANNEL_AUTHORITY).path(uuid).build();
            GDLog.DBGPRINTF(16, "PushChannelImpl(): created id = " + uuid + "\n");
            return;
        }
        GDLog.DBGPRINTF(12, "PushChannelImpl(): failed error=" + NDK_init + "\n");
        throw new RuntimeException("PushChannelImpl creation failed: " + NDK_init);
    }

    private native void NDK_connect(long j);

    private native void NDK_disconnect(long j);

    private native void NDK_finish(long j);

    private native long NDK_init();

    private native long NDK_initWithChannelIdentifier(String str);

    private native boolean NDK_pushHandleValid(long j);

    private Intent getActionIntent() {
        Intent intent = new Intent();
        intent.setAction(PushChannel.GD_PUSH_CHANNEL_EVENT_ACTION);
        intent.setData(this.mDataPath);
        return intent;
    }

    public static int getErrorCode(Intent intent, int i) {
        return intent.getIntExtra(GD_PUSH_CHANNEL_ERROR_EXTRA, i);
    }

    public static PushChannelEventType getEventType(Intent intent) {
        return PushChannelEventType.get(intent.getIntExtra(GD_PUSH_CHANNEL_EVENTTYPE_EXTRA, PushChannelEventType.None.getCode()));
    }

    public static String getMessageBundle(Intent intent) {
        return intent.getStringExtra(GD_PUSH_CHANNEL_MESSAGE_EXTRA);
    }

    public static int getPingFail(Intent intent, int i) {
        return intent.getIntExtra(GD_PUSH_CHANNEL_PINGFAIL_EXTRA, i);
    }

    public static String getPushChannelHost(Intent intent) {
        return intent.getStringExtra(GD_PUSH_CHANNEL_HOST_EXTRA);
    }

    public static String getToken(Intent intent) {
        return intent.getStringExtra(GD_PUSH_CHANNEL_TOKEN_EXTRA);
    }

    private void postMessage(final String str) {
        this.applicationContext.getClientHandler().post(new Runnable() { // from class: com.good.gd.ndkproxy.push.PushChannelImpl.5
            @Override // java.lang.Runnable
            public void run() {
                if (PushChannelImpl.this.containerState.isAuthorized()) {
                    if (PushChannelImpl.this._listener != null) {
                        PushChannelImpl.this._listener.onChannelMessage(str);
                    }
                    PushChannelImpl.this.sendMessageBroadcast(str);
                }
            }
        });
    }

    private void postPingFail(final int i) {
        this.applicationContext.getClientHandler().post(new Runnable() { // from class: com.good.gd.ndkproxy.push.PushChannelImpl.4
            @Override // java.lang.Runnable
            public void run() {
                if (PushChannelImpl.this.containerState.isAuthorized()) {
                    if (PushChannelImpl.this._listener != null) {
                        PushChannelImpl.this._listener.onChannelPingFail(i);
                    }
                    PushChannelImpl.this.sendPingFailBroadcast(i);
                }
            }
        });
    }

    private void postStateClose(final String str) {
        this.applicationContext.getClientHandler().post(new Runnable() { // from class: com.good.gd.ndkproxy.push.PushChannelImpl.2
            @Override // java.lang.Runnable
            public void run() {
                PushChannelImpl.this.mState = PushChannelState.Closed;
                if (PushChannelImpl.this.containerState.isAuthorized()) {
                    if (PushChannelImpl.this._listener != null) {
                        PushChannelImpl.this._listener.onChannelClose(str);
                    }
                    PushChannelImpl.this.sendCloseBroadcast(str);
                }
            }
        });
    }

    private void postStateError(final int i) {
        this.applicationContext.getClientHandler().post(new Runnable() { // from class: com.good.gd.ndkproxy.push.PushChannelImpl.3
            @Override // java.lang.Runnable
            public void run() {
                PushChannelImpl.this.mState = PushChannelState.Error;
                if (PushChannelImpl.this.containerState.isAuthorized()) {
                    if (PushChannelImpl.this._listener != null) {
                        PushChannelImpl.this._listener.onChannelError(i);
                    }
                    PushChannelImpl.this.sendErrorBroadcast(i);
                }
            }
        });
    }

    private void postStateOpen(final String str, final String str2) {
        this.applicationContext.getClientHandler().post(new Runnable() { // from class: com.good.gd.ndkproxy.push.PushChannelImpl.1
            @Override // java.lang.Runnable
            public void run() {
                PushChannelImpl.this.mState = PushChannelState.Open;
                if (PushChannelImpl.this.containerState.isAuthorized()) {
                    if (PushChannelImpl.this._listener != null) {
                        PushChannelImpl.this._listener.onChannelOpen(str);
                    }
                    PushChannelImpl.this.sendOpenBroadcast(str, str2);
                }
            }
        });
    }

    private void sendBroadcast(Intent intent) {
        GDLocalBroadcastManager.getInstance().sendBroadcastSync(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendCloseBroadcast(String str) {
        Intent actionIntent = getActionIntent();
        actionIntent.putExtra(GD_PUSH_CHANNEL_EVENTTYPE_EXTRA, PushChannelEventType.Close.getCode());
        actionIntent.putExtra(GD_PUSH_CHANNEL_TOKEN_EXTRA, str);
        sendBroadcast(actionIntent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendErrorBroadcast(int i) {
        Intent actionIntent = getActionIntent();
        actionIntent.putExtra(GD_PUSH_CHANNEL_EVENTTYPE_EXTRA, PushChannelEventType.Error.getCode());
        actionIntent.putExtra(GD_PUSH_CHANNEL_ERROR_EXTRA, i);
        sendBroadcast(actionIntent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendMessageBroadcast(String str) {
        Intent actionIntent = getActionIntent();
        actionIntent.putExtra(GD_PUSH_CHANNEL_EVENTTYPE_EXTRA, PushChannelEventType.Message.getCode());
        actionIntent.putExtra(GD_PUSH_CHANNEL_MESSAGE_EXTRA, str);
        sendBroadcast(actionIntent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendOpenBroadcast(String str, String str2) {
        Intent actionIntent = getActionIntent();
        actionIntent.putExtra(GD_PUSH_CHANNEL_EVENTTYPE_EXTRA, PushChannelEventType.Open.getCode());
        actionIntent.putExtra(GD_PUSH_CHANNEL_TOKEN_EXTRA, str);
        if (str2.length() > 0) {
            actionIntent.putExtra(GD_PUSH_CHANNEL_HOST_EXTRA, str2);
        }
        sendBroadcast(actionIntent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendPingFailBroadcast(int i) {
        Intent actionIntent = getActionIntent();
        actionIntent.putExtra(GD_PUSH_CHANNEL_EVENTTYPE_EXTRA, PushChannelEventType.PingFail.getCode());
        actionIntent.putExtra(GD_PUSH_CHANNEL_PINGFAIL_EXTRA, i);
        sendBroadcast(actionIntent);
    }

    @Override // com.good.gd.ndkproxy.push.IPushChannel
    public void connect() {
        this.containerState.checkAuthorized();
        synchronized (this) {
            NDK_connect(this._handle);
        }
    }

    @Override // com.good.gd.ndkproxy.push.IPushChannel
    public void disconnect() {
        this.containerState.checkAuthorized();
        synchronized (this) {
            NDK_disconnect(this._handle);
        }
    }

    public void finalize() {
        NDK_finish(this._handle);
        this._handle = 0L;
    }

    @Override // com.good.gd.ndkproxy.push.IPushChannel
    public String getDataAuthority() {
        return GD_PUSH_CHANNEL_AUTHORITY;
    }

    @Override // com.good.gd.ndkproxy.push.IPushChannel
    public String getDataPath() {
        return this.mDataPath.getPath();
    }

    @Override // com.good.gd.ndkproxy.push.IPushChannel
    public String getDataScheme() {
        return GD_PUSH_CHANNEL_SCHEME;
    }

    @Override // com.good.gd.ndkproxy.push.IPushChannel
    public PushChannelState getState() {
        return this.mState;
    }

    @Override // com.good.gd.ndkproxy.push.IPushChannel
    public void onChannelClose(String str) {
        GDLog.DBGPRINTF(16, "PushChannelImpl(): onChannelClose\n");
        postStateClose(str);
    }

    @Override // com.good.gd.ndkproxy.push.IPushChannel
    public void onChannelError(int i) {
        GDLog.DBGPRINTF(16, "PushChannelImpl(): onChannelError " + i + "\n");
        postStateError(i);
    }

    @Override // com.good.gd.ndkproxy.push.IPushChannel
    public void onChannelMessage(String str) {
        GDLog.DBGPRINTF(16, "PushChannelImpl(): onChannelMessage length = " + str.length() + "\n");
        postMessage(str);
    }

    @Override // com.good.gd.ndkproxy.push.IPushChannel
    public void onChannelOpen(String str, String str2) {
        GDLog.DBGPRINTF(16, "PushChannelImpl(): onChannelOpen\n");
        postStateOpen(str, str2);
    }

    @Override // com.good.gd.ndkproxy.push.IPushChannel
    public void onChannelPingFail(int i) {
        GDLog.DBGPRINTF(16, "PushChannelImpl(): onChannelPingFail " + i + "\n");
        postPingFail(i);
    }

    @Override // com.good.gd.ndkproxy.push.IPushChannel
    public IntentFilter prepareIntentFilter() {
        IntentFilter intentFilter = new IntentFilter(PushChannel.GD_PUSH_CHANNEL_EVENT_ACTION);
        intentFilter.addDataScheme(getDataScheme());
        intentFilter.addDataAuthority(getDataAuthority(), null);
        intentFilter.addDataPath(getDataPath(), 0);
        return intentFilter;
    }

    @Override // com.good.gd.ndkproxy.push.IPushChannel
    @Deprecated
    public void setListener(PushChannelListener pushChannelListener) {
        this.containerState.checkAuthorized();
        this._listener = pushChannelListener;
    }

    @Override // com.good.gd.ndkproxy.push.IPushChannel
    public void onChannelMessage(byte[] bArr) {
        try {
            onChannelMessage(new String(bArr, HTTP.UTF_8));
        } catch (UnsupportedEncodingException e) {
            GDLog.DBGPRINTF(16, "onChannelMessage: " + e + "\n");
        }
    }

    public PushChannelImpl(String str, ContainerState containerState, ApplicationContext applicationContext) {
        this.containerState = containerState;
        this.applicationContext = applicationContext;
        if (str != null && !str.isEmpty()) {
            if (containerState.isGDIdAllowed(str)) {
                containerState.checkAuthorized();
                long NDK_initWithChannelIdentifier = NDK_initWithChannelIdentifier(str);
                if (NDK_pushHandleValid(NDK_initWithChannelIdentifier)) {
                    this._handle = NDK_initWithChannelIdentifier;
                    String uuid = UUID.randomUUID().toString();
                    this.mDataPath = new Uri.Builder().scheme(GD_PUSH_CHANNEL_SCHEME).authority(GD_PUSH_CHANNEL_AUTHORITY).path(uuid).build();
                    GDLog.DBGPRINTF(16, "PushChannelImpl(s): created id = " + uuid + "\n");
                    return;
                }
                GDLog.DBGPRINTF(12, "PushChannelImpl(s): failed error=" + NDK_initWithChannelIdentifier + "\n");
                throw new RuntimeException("PushChannelImpl creation failed: " + NDK_initWithChannelIdentifier);
            }
            throw new GDInitializationError("PushChannelImpl creation failed as : PushChannelIdentifier invalid. It may contain only lowercase English letters, digits, dot, and dash.");
        }
        GDLog.DBGPRINTF(12, "PushChannelImpl(s) : Trying to create push channel with empty identifier\n");
        throw new RuntimeException("PushChannelImpl creation failed as : PushChannelIdentifier not set. Provide valid PushChannelIdentifier.");
    }
}
