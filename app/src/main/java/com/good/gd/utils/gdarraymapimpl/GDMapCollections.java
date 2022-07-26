package com.good.gd.utils.gdarraymapimpl;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public abstract class GDMapCollections<K, V> {
    yfdke dbjc;
    pmoiy jwxax;
    fdyxd qkduk;

    /* loaded from: classes.dex */
    final class ehnkx implements Iterator<Map.Entry<K, V>>, Map.Entry<K, V> {
        int dbjc;
        boolean jwxax = false;
        int qkduk = -1;

        ehnkx() {
            this.dbjc = GDMapCollections.this.dbjc() - 1;
        }

        @Override // java.util.Map.Entry
        public final boolean equals(Object obj) {
            if (this.jwxax) {
                if (!(obj instanceof Map.Entry)) {
                    return false;
                }
                Map.Entry entry = (Map.Entry) obj;
                return Objects.equal(entry.getKey(), GDMapCollections.this.dbjc(this.qkduk, 0)) && Objects.equal(entry.getValue(), GDMapCollections.this.dbjc(this.qkduk, 1));
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            if (this.jwxax) {
                return (K) GDMapCollections.this.dbjc(this.qkduk, 0);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            if (this.jwxax) {
                return (V) GDMapCollections.this.dbjc(this.qkduk, 1);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.qkduk < this.dbjc;
        }

        @Override // java.util.Map.Entry
        public final int hashCode() {
            if (this.jwxax) {
                int i = 0;
                Object dbjc = GDMapCollections.this.dbjc(this.qkduk, 0);
                Object dbjc2 = GDMapCollections.this.dbjc(this.qkduk, 1);
                int hashCode = dbjc == null ? 0 : dbjc.hashCode();
                if (dbjc2 != null) {
                    i = dbjc2.hashCode();
                }
                return hashCode ^ i;
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        @Override // java.util.Iterator
        public Object next() {
            this.qkduk++;
            this.jwxax = true;
            return this;
        }

        @Override // java.util.Iterator
        public void remove() {
            if (this.jwxax) {
                GDArrayMap.this.removeAt(this.qkduk);
                this.qkduk--;
                this.dbjc--;
                this.jwxax = false;
                return;
            }
            throw new IllegalStateException();
        }

        @Override // java.util.Map.Entry
        public V setValue(V v) {
            if (this.jwxax) {
                GDMapCollections gDMapCollections = GDMapCollections.this;
                return (V) GDArrayMap.this.setValueAt(this.qkduk, v);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        public final String toString() {
            return getKey() + "=" + getValue();
        }
    }

    /* loaded from: classes.dex */
    final class fdyxd implements Set<K> {
        /* JADX INFO: Access modifiers changed from: package-private */
        public fdyxd() {
        }

        @Override // java.util.Set, java.util.Collection
        public boolean add(K k) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean addAll(Collection<? extends K> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public void clear() {
            GDArrayMap.this.clear();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean contains(Object obj) {
            return GDArrayMap.this.indexOfKey(obj) >= 0;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean containsAll(Collection<?> collection) {
            GDArrayMap gDArrayMap = GDArrayMap.this;
            Iterator<?> it = collection.iterator();
            while (it.hasNext()) {
                if (!gDArrayMap.containsKey(it.next())) {
                    return false;
                }
            }
            return true;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean equals(Object obj) {
            return GDMapCollections.dbjc(this, obj);
        }

        @Override // java.util.Set, java.util.Collection
        public int hashCode() {
            int i = 0;
            for (int i2 = GDArrayMap.this.mSize - 1; i2 >= 0; i2--) {
                Object dbjc = GDMapCollections.this.dbjc(i2, 0);
                i += dbjc == null ? 0 : dbjc.hashCode();
            }
            return i;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean isEmpty() {
            return GDArrayMap.this.mSize == 0;
        }

        @Override // java.util.Set, java.util.Collection, java.lang.Iterable
        public Iterator<K> iterator() {
            return new hbfhc(0);
        }

        @Override // java.util.Set, java.util.Collection
        public boolean remove(Object obj) {
            int indexOfKey = GDArrayMap.this.indexOfKey(obj);
            if (indexOfKey >= 0) {
                GDArrayMap.this.removeAt(indexOfKey);
                return true;
            }
            return false;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            return GDMapCollections.dbjc(GDArrayMap.this, collection);
        }

        @Override // java.util.Set, java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            return GDMapCollections.qkduk(GDArrayMap.this, collection);
        }

        @Override // java.util.Set, java.util.Collection
        public int size() {
            return GDArrayMap.this.mSize;
        }

        @Override // java.util.Set, java.util.Collection
        public Object[] toArray() {
            return GDMapCollections.this.dbjc(0);
        }

        @Override // java.util.Set, java.util.Collection
        public <T> T[] toArray(T[] tArr) {
            return (T[]) GDMapCollections.this.dbjc(tArr, 0);
        }
    }

    /* loaded from: classes.dex */
    final class hbfhc<T> implements Iterator<T> {
        final int dbjc;
        int jwxax;
        int qkduk;
        boolean wxau = false;

        hbfhc(int i) {
            this.dbjc = i;
            this.qkduk = GDMapCollections.this.dbjc();
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.jwxax < this.qkduk;
        }

        @Override // java.util.Iterator
        public T next() {
            T t = (T) GDMapCollections.this.dbjc(this.jwxax, this.dbjc);
            this.jwxax++;
            this.wxau = true;
            return t;
        }

        @Override // java.util.Iterator
        public void remove() {
            if (this.wxau) {
                int i = this.jwxax - 1;
                this.jwxax = i;
                this.qkduk--;
                this.wxau = false;
                GDArrayMap.this.removeAt(i);
                return;
            }
            throw new IllegalStateException();
        }
    }

    /* loaded from: classes.dex */
    final class pmoiy implements Collection<V> {
        /* JADX INFO: Access modifiers changed from: package-private */
        public pmoiy() {
        }

        @Override // java.util.Collection
        public boolean add(V v) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Collection
        public boolean addAll(Collection<? extends V> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Collection
        public void clear() {
            GDArrayMap.this.clear();
        }

        @Override // java.util.Collection
        public boolean contains(Object obj) {
            return GDArrayMap.this.indexOfValue(obj) >= 0;
        }

        @Override // java.util.Collection
        public boolean containsAll(Collection<?> collection) {
            Iterator<?> it = collection.iterator();
            while (it.hasNext()) {
                if (!contains(it.next())) {
                    return false;
                }
            }
            return true;
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return GDArrayMap.this.mSize == 0;
        }

        @Override // java.util.Collection, java.lang.Iterable
        public Iterator<V> iterator() {
            return new hbfhc(1);
        }

        @Override // java.util.Collection
        public boolean remove(Object obj) {
            int indexOfValue = GDArrayMap.this.indexOfValue(obj);
            if (indexOfValue >= 0) {
                GDArrayMap.this.removeAt(indexOfValue);
                return true;
            }
            return false;
        }

        @Override // java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            int i = GDArrayMap.this.mSize;
            int i2 = 0;
            boolean z = false;
            while (i2 < i) {
                if (collection.contains(GDMapCollections.this.dbjc(i2, 1))) {
                    GDArrayMap.this.removeAt(i2);
                    i2--;
                    i--;
                    z = true;
                }
                i2++;
            }
            return z;
        }

        @Override // java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            int i = GDArrayMap.this.mSize;
            int i2 = 0;
            boolean z = false;
            while (i2 < i) {
                if (!collection.contains(GDMapCollections.this.dbjc(i2, 1))) {
                    GDArrayMap.this.removeAt(i2);
                    i2--;
                    i--;
                    z = true;
                }
                i2++;
            }
            return z;
        }

        @Override // java.util.Collection
        public int size() {
            return GDArrayMap.this.mSize;
        }

        @Override // java.util.Collection
        public Object[] toArray() {
            return GDMapCollections.this.dbjc(1);
        }

        @Override // java.util.Collection
        public <T> T[] toArray(T[] tArr) {
            return (T[]) GDMapCollections.this.dbjc(tArr, 1);
        }
    }

    /* loaded from: classes.dex */
    final class yfdke implements Set<Map.Entry<K, V>> {
        /* JADX INFO: Access modifiers changed from: package-private */
        public yfdke() {
        }

        @Override // java.util.Set, java.util.Collection
        public boolean add(Object obj) {
            Map.Entry entry = (Map.Entry) obj;
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean addAll(Collection<? extends Map.Entry<K, V>> collection) {
            int i = GDArrayMap.this.mSize;
            for (Map.Entry<K, V> entry : collection) {
                GDArrayMap.this.put(entry.getKey(), entry.getValue());
            }
            return i != GDArrayMap.this.mSize;
        }

        @Override // java.util.Set, java.util.Collection
        public void clear() {
            GDArrayMap.this.clear();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean contains(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            GDMapCollections gDMapCollections = GDMapCollections.this;
            int indexOfKey = GDArrayMap.this.indexOfKey(entry.getKey());
            if (indexOfKey >= 0) {
                return Objects.equal(GDMapCollections.this.dbjc(indexOfKey, 1), entry.getValue());
            }
            return false;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean containsAll(Collection<?> collection) {
            Iterator<?> it = collection.iterator();
            while (it.hasNext()) {
                if (!contains(it.next())) {
                    return false;
                }
            }
            return true;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean equals(Object obj) {
            return GDMapCollections.dbjc(this, obj);
        }

        @Override // java.util.Set, java.util.Collection
        public int hashCode() {
            int i = 0;
            for (int i2 = GDArrayMap.this.mSize - 1; i2 >= 0; i2--) {
                Object dbjc = GDMapCollections.this.dbjc(i2, 0);
                Object dbjc2 = GDMapCollections.this.dbjc(i2, 1);
                i += (dbjc == null ? 0 : dbjc.hashCode()) ^ (dbjc2 == null ? 0 : dbjc2.hashCode());
            }
            return i;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean isEmpty() {
            return GDArrayMap.this.mSize == 0;
        }

        @Override // java.util.Set, java.util.Collection, java.lang.Iterable
        public Iterator<Map.Entry<K, V>> iterator() {
            return new ehnkx();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean remove(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public int size() {
            return GDArrayMap.this.mSize;
        }

        @Override // java.util.Set, java.util.Collection
        public Object[] toArray() {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public <T> T[] toArray(T[] tArr) {
            throw new UnsupportedOperationException();
        }
    }

    public static <K, V> boolean dbjc(Map<K, V> map, Collection<?> collection) {
        int size = map.size();
        Iterator<?> it = collection.iterator();
        while (it.hasNext()) {
            map.remove(it.next());
        }
        return size != map.size();
    }

    public static <K, V> boolean qkduk(Map<K, V> map, Collection<?> collection) {
        int size = map.size();
        Iterator<K> it = map.keySet().iterator();
        while (it.hasNext()) {
            if (!collection.contains(it.next())) {
                it.remove();
            }
        }
        return size != map.size();
    }

    protected abstract int dbjc();

    protected abstract Object dbjc(int i, int i2);

    public Object[] dbjc(int i) {
        int i2 = GDArrayMap.this.mSize;
        Object[] objArr = new Object[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            objArr[i3] = dbjc(i3, i);
        }
        return objArr;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> T[] dbjc(T[] tArr, int i) {
        int i2 = GDArrayMap.this.mSize;
        if (tArr.length < i2) {
            tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), i2));
        }
        for (int i3 = 0; i3 < i2; i3++) {
            tArr[i3] = dbjc(i3, i);
        }
        if (tArr.length > i2) {
            tArr[i2] = null;
        }
        return tArr;
    }

    public static <T> boolean dbjc(Set<T> set, Object obj) {
        if (set == obj) {
            return true;
        }
        if (obj instanceof Set) {
            Set set2 = (Set) obj;
            try {
                if (set.size() == set2.size()) {
                    if (set.containsAll(set2)) {
                        return true;
                    }
                }
                return false;
            } catch (ClassCastException e) {
                return false;
            } catch (NullPointerException e2) {
            }
        }
        return false;
    }
}
