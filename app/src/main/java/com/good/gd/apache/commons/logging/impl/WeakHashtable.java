package com.good.gd.apache.commons.logging.impl;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public final class WeakHashtable extends Hashtable {
    private static final int MAX_CHANGES_BEFORE_PURGE = 100;
    private static final int PARTIAL_PURGE_COUNT = 10;
    private ReferenceQueue queue = new ReferenceQueue();
    private int changeCount = 0;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class ehnkx extends WeakReference {
        private final fdyxd dbjc;

        /* synthetic */ ehnkx(Object obj, ReferenceQueue referenceQueue, fdyxd fdyxdVar, hbfhc hbfhcVar) {
            this(obj, referenceQueue, fdyxdVar);
        }

        private ehnkx(Object obj, ReferenceQueue referenceQueue, fdyxd fdyxdVar) {
            super(obj, referenceQueue);
            this.dbjc = fdyxdVar;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class fdyxd {
        private final WeakReference dbjc;
        private final int qkduk;

        /* synthetic */ fdyxd(Object obj, hbfhc hbfhcVar) {
            this(obj);
        }

        static /* synthetic */ Object dbjc(fdyxd fdyxdVar) {
            return fdyxdVar.dbjc.get();
        }

        public boolean equals(Object obj) {
            if (obj instanceof fdyxd) {
                fdyxd fdyxdVar = (fdyxd) obj;
                Object obj2 = this.dbjc.get();
                Object obj3 = fdyxdVar.dbjc.get();
                if (obj2 != null) {
                    return obj2.equals(obj3);
                }
                boolean z = obj3 == null;
                if (!z) {
                    return z;
                }
                return this.qkduk == fdyxdVar.qkduk;
            }
            return false;
        }

        public int hashCode() {
            return this.qkduk;
        }

        /* synthetic */ fdyxd(Object obj, ReferenceQueue referenceQueue, hbfhc hbfhcVar) {
            this(obj, referenceQueue);
        }

        private fdyxd(Object obj) {
            this.dbjc = new WeakReference(obj);
            this.qkduk = obj.hashCode();
        }

        private fdyxd(Object obj, ReferenceQueue referenceQueue) {
            this.dbjc = new ehnkx(obj, referenceQueue, this, null);
            this.qkduk = obj.hashCode();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class hbfhc implements Enumeration {
        final /* synthetic */ Enumeration dbjc;

        hbfhc(WeakHashtable weakHashtable, Enumeration enumeration) {
            this.dbjc = enumeration;
        }

        @Override // java.util.Enumeration
        public boolean hasMoreElements() {
            return this.dbjc.hasMoreElements();
        }

        @Override // java.util.Enumeration
        public Object nextElement() {
            return fdyxd.dbjc((fdyxd) this.dbjc.nextElement());
        }
    }

    /* loaded from: classes.dex */
    private static final class yfdke implements Entry {
        private final Object dbjc;
        private final Object qkduk;

        /* synthetic */ yfdke(Object obj, Object obj2, hbfhc hbfhcVar) {
            this(obj, obj2);
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object obj) {
            if (obj == null || !(obj instanceof Map.Entry)) {
                return false;
            }
            Entry entry = (Entry) obj;
            Object obj2 = this.dbjc;
            if (obj2 == null) {
                if (entry.getKey() != null) {
                    return false;
                }
            } else if (!obj2.equals(entry.getKey())) {
                return false;
            }
            Object obj3 = this.qkduk;
            if (obj3 == null) {
                if (entry.getValue() != null) {
                    return false;
                }
            } else if (!obj3.equals(entry.getValue())) {
                return false;
            }
            return true;
        }

        @Override // java.util.Map.Entry
        public Object getKey() {
            return this.dbjc;
        }

        @Override // java.util.Map.Entry
        public Object getValue() {
            return this.qkduk;
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            Object obj = this.dbjc;
            int i = 0;
            int hashCode = obj == null ? 0 : obj.hashCode();
            Object obj2 = this.qkduk;
            if (obj2 != null) {
                i = obj2.hashCode();
            }
            return hashCode ^ i;
        }

        @Override // java.util.Map.Entry
        public Object setValue(Object obj) {
            throw new UnsupportedOperationException("Entry.setValue is not supported.");
        }

        private yfdke(Object obj, Object obj2) {
            this.dbjc = obj;
            this.qkduk = obj2;
        }
    }

    private void purge() {
        synchronized (this.queue) {
            while (true) {
                ehnkx ehnkxVar = (ehnkx) this.queue.poll();
                if (ehnkxVar != null) {
                    super.remove(ehnkxVar.dbjc);
                }
            }
        }
    }

    private void purgeOne() {
        synchronized (this.queue) {
            ehnkx ehnkxVar = (ehnkx) this.queue.poll();
            if (ehnkxVar != null) {
                super.remove(ehnkxVar.dbjc);
            }
        }
    }

    @Override // java.util.Hashtable, java.util.Map
    public boolean containsKey(Object obj) {
        return super.containsKey(new fdyxd(obj, (hbfhc) null));
    }

    @Override // java.util.Hashtable, java.util.Dictionary
    public Enumeration elements() {
        purge();
        return super.elements();
    }

    @Override // java.util.Hashtable, java.util.Map
    public Set entrySet() {
        purge();
        Set<Entry> entrySet = super.entrySet();
        HashSet hashSet = new HashSet();
        for (Entry entry : entrySet) {
            Object dbjc = fdyxd.dbjc((fdyxd) entry.getKey());
            Object value = entry.getValue();
            if (dbjc != null) {
                hashSet.add(new yfdke(dbjc, value, null));
            }
        }
        return hashSet;
    }

    @Override // java.util.Hashtable, java.util.Dictionary, java.util.Map
    public Object get(Object obj) {
        return super.get(new fdyxd(obj, (hbfhc) null));
    }

    @Override // java.util.Hashtable, java.util.Dictionary, java.util.Map
    public boolean isEmpty() {
        purge();
        return super.isEmpty();
    }

    @Override // java.util.Hashtable, java.util.Map
    public Set keySet() {
        purge();
        Set<fdyxd> keySet = super.keySet();
        HashSet hashSet = new HashSet();
        for (fdyxd fdyxdVar : keySet) {
            Object dbjc = fdyxd.dbjc(fdyxdVar);
            if (dbjc != null) {
                hashSet.add(dbjc);
            }
        }
        return hashSet;
    }

    @Override // java.util.Hashtable, java.util.Dictionary
    public Enumeration keys() {
        purge();
        return new hbfhc(this, super.keys());
    }

    @Override // java.util.Hashtable, java.util.Dictionary, java.util.Map
    public Object put(Object obj, Object obj2) {
        if (obj != null) {
            if (obj2 != null) {
                int i = this.changeCount;
                int i2 = i + 1;
                this.changeCount = i2;
                if (i > 100) {
                    purge();
                    this.changeCount = 0;
                } else if (i2 % 10 == 0) {
                    purgeOne();
                }
                return super.put(new fdyxd(obj, this.queue, null), obj2);
            }
            throw new NullPointerException("Null values are not allowed");
        }
        throw new NullPointerException("Null keys are not allowed");
    }

    @Override // java.util.Hashtable, java.util.Map
    public void putAll(Map map) {
        if (map != null) {
            for (Entry entry : map.entrySet()) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override // java.util.Hashtable
    protected void rehash() {
        purge();
        super.rehash();
    }

    @Override // java.util.Hashtable, java.util.Dictionary, java.util.Map
    public Object remove(Object obj) {
        int i = this.changeCount;
        int i2 = i + 1;
        this.changeCount = i2;
        if (i > 100) {
            purge();
            this.changeCount = 0;
        } else if (i2 % 10 == 0) {
            purgeOne();
        }
        return super.remove(new fdyxd(obj, (hbfhc) null));
    }

    @Override // java.util.Hashtable, java.util.Dictionary, java.util.Map
    public int size() {
        purge();
        return super.size();
    }

    @Override // java.util.Hashtable
    public String toString() {
        purge();
        return super.toString();
    }

    @Override // java.util.Hashtable, java.util.Map
    public Collection values() {
        purge();
        return super.values();
    }
}
