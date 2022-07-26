package com.good.gd.apache.http.impl.conn.tsccm;

import com.good.gd.apache.http.conn.ClientConnectionManager;
import com.good.gd.apache.http.impl.conn.AbstractPoolEntry;
import com.good.gd.apache.http.impl.conn.AbstractPooledConnAdapter;

/* loaded from: classes.dex */
public class BasicPooledConnAdapter extends AbstractPooledConnAdapter {
    /* JADX INFO: Access modifiers changed from: protected */
    public BasicPooledConnAdapter(ThreadSafeClientConnManager threadSafeClientConnManager, AbstractPoolEntry abstractPoolEntry) {
        super(threadSafeClientConnManager, abstractPoolEntry);
        markReusable();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.good.gd.apache.http.impl.conn.AbstractPooledConnAdapter, com.good.gd.apache.http.impl.conn.AbstractClientConnAdapter
    public void detach() {
        super.detach();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.good.gd.apache.http.impl.conn.AbstractClientConnAdapter
    public ClientConnectionManager getManager() {
        return super.getManager();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public AbstractPoolEntry getPoolEntry() {
        return this.poolEntry;
    }
}
