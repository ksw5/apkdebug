package com.good.gd.utils.gdarraymapimpl;

import android.util.Log;
import com.good.gd.utils.gdarraymapimpl.GDMapCollections;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public final class GDArrayMap<K, V> implements Map<K, V> {
    private static final int BASE_SIZE = 4;
    private static final int CACHE_SIZE = 10;
    private static final boolean DEBUG = false;
    public static final GDArrayMap EMPTY = new GDArrayMap(true);
    static final int[] EMPTY_IMMUTABLE_INTS = new int[0];
    private static final String TAG = "GDArrayMap";
    static Object[] mBaseCache;
    static int mBaseCacheSize;
    static Object[] mTwiceBaseCache;
    static int mTwiceBaseCacheSize;
    Object[] mArray;
    GDMapCollections<K, V> mCollections;
    int[] mHashes;
    int mSize;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class hbfhc extends GDMapCollections<K, V> {
        hbfhc() {
        }

        @Override // com.good.gd.utils.gdarraymapimpl.GDMapCollections
        protected int dbjc() {
            return GDArrayMap.this.mSize;
        }

        @Override // com.good.gd.utils.gdarraymapimpl.GDMapCollections
        protected Object dbjc(int i, int i2) {
            return GDArrayMap.this.mArray[(i << 1) + i2];
        }
    }

    public GDArrayMap() {
        this.mHashes = EmptyArray.INT;
        this.mArray = EmptyArray.OBJECT;
        this.mSize = 0;
    }

    private void allocArrays(int i) {
        if (this.mHashes != EMPTY_IMMUTABLE_INTS) {
            if (i == 8) {
                synchronized (GDArrayMap.class) {
                    Object[] objArr = mTwiceBaseCache;
                    if (objArr != null) {
                        this.mArray = objArr;
                        mTwiceBaseCache = (Object[]) objArr[0];
                        this.mHashes = (int[]) objArr[1];
                        objArr[1] = null;
                        objArr[0] = null;
                        mTwiceBaseCacheSize--;
                        return;
                    }
                }
            } else if (i == 4) {
                synchronized (GDArrayMap.class) {
                    Object[] objArr2 = mBaseCache;
                    if (objArr2 != null) {
                        this.mArray = objArr2;
                        mBaseCache = (Object[]) objArr2[0];
                        this.mHashes = (int[]) objArr2[1];
                        objArr2[1] = null;
                        objArr2[0] = null;
                        mBaseCacheSize--;
                        return;
                    }
                }
            }
            this.mHashes = new int[i];
            this.mArray = new Object[i << 1];
            return;
        }
        throw new UnsupportedOperationException("GDArrayMap is immutable");
    }

    private static void freeArrays(int[] iArr, Object[] objArr, int i) {
        if (iArr.length == 8) {
            synchronized (GDArrayMap.class) {
                if (mTwiceBaseCacheSize < 10) {
                    objArr[0] = mTwiceBaseCache;
                    objArr[1] = iArr;
                    for (int i2 = (i << 1) - 1; i2 >= 2; i2--) {
                        objArr[i2] = null;
                    }
                    mTwiceBaseCache = objArr;
                    mTwiceBaseCacheSize++;
                }
            }
        } else if (iArr.length == 4) {
            synchronized (GDArrayMap.class) {
                if (mBaseCacheSize < 10) {
                    objArr[0] = mBaseCache;
                    objArr[1] = iArr;
                    for (int i3 = (i << 1) - 1; i3 >= 2; i3--) {
                        objArr[i3] = null;
                    }
                    mBaseCache = objArr;
                    mBaseCacheSize++;
                }
            }
        }
    }

    private GDMapCollections<K, V> getCollection() {
        if (this.mCollections == null) {
            this.mCollections = new hbfhc();
        }
        return this.mCollections;
    }

    public void append(K k, V v) {
        int i;
        int i2 = this.mSize;
        int hashCode = k == null ? 0 : k.hashCode();
        int[] iArr = this.mHashes;
        if (i2 < iArr.length) {
            if (i2 > 0) {
                if (iArr[i2 - 1] > hashCode) {
                    RuntimeException runtimeException = new RuntimeException("here");
                    runtimeException.fillInStackTrace();
                    Log.w(TAG, "New hash " + hashCode + " is before end of array hash " + this.mHashes[i] + " at index " + i2 + " key " + k, runtimeException);
                    put(k, v);
                    return;
                }
            }
            this.mSize = i2 + 1;
            iArr[i2] = hashCode;
            int i3 = i2 << 1;
            Object[] objArr = this.mArray;
            objArr[i3] = k;
            objArr[i3 + 1] = v;
            return;
        }
        throw new IllegalStateException("Array is full");
    }

    @Override // java.util.Map
    public void clear() {
        int i = this.mSize;
        if (i > 0) {
            freeArrays(this.mHashes, this.mArray, i);
            this.mHashes = EmptyArray.INT;
            this.mArray = EmptyArray.OBJECT;
            this.mSize = 0;
        }
    }

    public boolean containsAll(Collection<?> collection) {
        Iterator<?> it = collection.iterator();
        while (it.hasNext()) {
            if (!containsKey(it.next())) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.Map
    public boolean containsKey(Object obj) {
        return indexOfKey(obj) >= 0;
    }

    @Override // java.util.Map
    public boolean containsValue(Object obj) {
        return indexOfValue(obj) >= 0;
    }

    public void ensureCapacity(int i) {
        int[] iArr = this.mHashes;
        if (iArr.length < i) {
            Object[] objArr = this.mArray;
            allocArrays(i);
            int i2 = this.mSize;
            if (i2 > 0) {
                System.arraycopy(iArr, 0, this.mHashes, 0, i2);
                System.arraycopy(objArr, 0, this.mArray, 0, this.mSize << 1);
            }
            freeArrays(iArr, objArr, this.mSize);
        }
    }

    @Override // java.util.Map
    public Set<Entry<K, V>> entrySet() {
        GDMapCollections<K, V> collection = getCollection();
        if (collection.dbjc == null) {
            collection.dbjc = new GDMapCollections.yfdke();
        }
        return collection.dbjc;
    }

    @Override // java.util.Map
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Map) {
            Map map = (Map) obj;
            if (size() != map.size()) {
                return false;
            }
            for (int i = 0; i < this.mSize; i++) {
                try {
                    K keyAt = keyAt(i);
                    V valueAt = valueAt(i);
                    Object obj2 = map.get(keyAt);
                    if (valueAt == null) {
                        if (obj2 != null || !map.containsKey(keyAt)) {
                            return false;
                        }
                    } else if (!valueAt.equals(obj2)) {
                        return false;
                    }
                } catch (ClassCastException e) {
                    return false;
                } catch (NullPointerException e2) {
                }
            }
            return true;
        }
        return false;
    }

    public void erase() {
        int i = this.mSize;
        if (i > 0) {
            int i2 = i << 1;
            Object[] objArr = this.mArray;
            for (int i3 = 0; i3 < i2; i3++) {
                objArr[i3] = null;
            }
            this.mSize = 0;
        }
    }

    @Override // java.util.Map
    public V get(Object obj) {
        int indexOfKey = indexOfKey(obj);
        if (indexOfKey >= 0) {
            return (V) this.mArray[(indexOfKey << 1) + 1];
        }
        return null;
    }

    @Override // java.util.Map
    public int hashCode() {
        int[] iArr = this.mHashes;
        Object[] objArr = this.mArray;
        int i = this.mSize;
        int i2 = 1;
        int i3 = 0;
        int i4 = 0;
        while (i3 < i) {
            Object obj = objArr[i2];
            i4 += (obj == null ? 0 : obj.hashCode()) ^ iArr[i3];
            i3++;
            i2 += 2;
        }
        return i4;
    }

    int indexOf(Object obj, int i) {
        int i2 = this.mSize;
        if (i2 == 0) {
            return -1;
        }
        int dbjc = com.good.gd.utils.gdarraymapimpl.hbfhc.dbjc(this.mHashes, i2, i);
        if (dbjc < 0 || obj.equals(this.mArray[dbjc << 1])) {
            return dbjc;
        }
        int i3 = dbjc + 1;
        while (i3 < i2 && this.mHashes[i3] == i) {
            if (obj.equals(this.mArray[i3 << 1])) {
                return i3;
            }
            i3++;
        }
        for (int i4 = dbjc - 1; i4 >= 0 && this.mHashes[i4] == i; i4--) {
            if (obj.equals(this.mArray[i4 << 1])) {
                return i4;
            }
        }
        return ~i3;
    }

    public int indexOfKey(Object obj) {
        return obj == null ? indexOfNull() : indexOf(obj, obj.hashCode());
    }

    int indexOfNull() {
        int i = this.mSize;
        if (i == 0) {
            return -1;
        }
        int dbjc = com.good.gd.utils.gdarraymapimpl.hbfhc.dbjc(this.mHashes, i, 0);
        if (dbjc < 0 || this.mArray[dbjc << 1] == null) {
            return dbjc;
        }
        int i2 = dbjc + 1;
        while (i2 < i && this.mHashes[i2] == 0) {
            if (this.mArray[i2 << 1] == null) {
                return i2;
            }
            i2++;
        }
        for (int i3 = dbjc - 1; i3 >= 0 && this.mHashes[i3] == 0; i3--) {
            if (this.mArray[i3 << 1] == null) {
                return i3;
            }
        }
        return ~i2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int indexOfValue(Object obj) {
        int i = this.mSize * 2;
        Object[] objArr = this.mArray;
        if (obj == null) {
            for (int i2 = 1; i2 < i; i2 += 2) {
                if (objArr[i2] == null) {
                    return i2 >> 1;
                }
            }
            return -1;
        }
        for (int i3 = 1; i3 < i; i3 += 2) {
            if (obj.equals(objArr[i3])) {
                return i3 >> 1;
            }
        }
        return -1;
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return this.mSize <= 0;
    }

    public K keyAt(int i) {
        return (K) this.mArray[i << 1];
    }

    @Override // java.util.Map
    public Set<K> keySet() {
        GDMapCollections<K, V> collection = getCollection();
        if (collection.qkduk == null) {
            collection.qkduk = new GDMapCollections.fdyxd();
        }
        return collection.qkduk;
    }

    @Override // java.util.Map
    public V put(K k, V v) {
        int i;
        int indexOf;
        if (k == null) {
            indexOf = indexOfNull();
            i = 0;
        } else {
            int hashCode = k.hashCode();
            i = hashCode;
            indexOf = indexOf(k, hashCode);
        }
        if (indexOf >= 0) {
            int i2 = (indexOf << 1) + 1;
            Object[] objArr = this.mArray;
            V v2 = (V) objArr[i2];
            objArr[i2] = v;
            return v2;
        }
        int i3 = ~indexOf;
        int i4 = this.mSize;
        int[] iArr = this.mHashes;
        if (i4 >= iArr.length) {
            int i5 = 4;
            if (i4 >= 8) {
                i5 = (i4 >> 1) + i4;
            } else if (i4 >= 4) {
                i5 = 8;
            }
            Object[] objArr2 = this.mArray;
            allocArrays(i5);
            int[] iArr2 = this.mHashes;
            if (iArr2.length > 0) {
                System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
                System.arraycopy(objArr2, 0, this.mArray, 0, objArr2.length);
            }
            freeArrays(iArr, objArr2, this.mSize);
        }
        int i6 = this.mSize;
        if (i3 < i6) {
            int[] iArr3 = this.mHashes;
            int i7 = i3 + 1;
            System.arraycopy(iArr3, i3, iArr3, i7, i6 - i3);
            Object[] objArr3 = this.mArray;
            System.arraycopy(objArr3, i3 << 1, objArr3, i7 << 1, (this.mSize - i3) << 1);
        }
        this.mHashes[i3] = i;
        Object[] objArr4 = this.mArray;
        int i8 = i3 << 1;
        objArr4[i8] = k;
        objArr4[i8 + 1] = v;
        this.mSize++;
        return null;
    }

    public void putAll(GDArrayMap<? extends K, ? extends V> gDArrayMap) {
        int i = gDArrayMap.mSize;
        ensureCapacity(this.mSize + i);
        if (this.mSize != 0) {
            for (int i2 = 0; i2 < i; i2++) {
                put(gDArrayMap.keyAt(i2), gDArrayMap.valueAt(i2));
            }
        } else if (i > 0) {
            System.arraycopy(gDArrayMap.mHashes, 0, this.mHashes, 0, i);
            System.arraycopy(gDArrayMap.mArray, 0, this.mArray, 0, i << 1);
            this.mSize = i;
        }
    }

    @Override // java.util.Map
    public V remove(Object obj) {
        int indexOfKey = indexOfKey(obj);
        if (indexOfKey >= 0) {
            return removeAt(indexOfKey);
        }
        return null;
    }

    public boolean removeAll(Collection<?> collection) {
        return GDMapCollections.dbjc(this, collection);
    }

    public V removeAt(int i) {
        Object[] objArr = this.mArray;
        int i2 = i << 1;
        V v = (V) objArr[i2 + 1];
        int i3 = this.mSize;
        if (i3 <= 1) {
            freeArrays(this.mHashes, objArr, i3);
            this.mHashes = EmptyArray.INT;
            this.mArray = EmptyArray.OBJECT;
            this.mSize = 0;
        } else {
            int[] iArr = this.mHashes;
            int i4 = 8;
            if (iArr.length > 8 && i3 < iArr.length / 3) {
                if (i3 > 8) {
                    i4 = i3 + (i3 >> 1);
                }
                allocArrays(i4);
                this.mSize--;
                if (i > 0) {
                    System.arraycopy(iArr, 0, this.mHashes, 0, i);
                    System.arraycopy(objArr, 0, this.mArray, 0, i2);
                }
                int i5 = this.mSize;
                if (i < i5) {
                    int i6 = i + 1;
                    System.arraycopy(iArr, i6, this.mHashes, i, i5 - i);
                    System.arraycopy(objArr, i6 << 1, this.mArray, i2, (this.mSize - i) << 1);
                }
            } else {
                int i7 = i3 - 1;
                this.mSize = i7;
                if (i < i7) {
                    int i8 = i + 1;
                    System.arraycopy(iArr, i8, iArr, i, i7 - i);
                    Object[] objArr2 = this.mArray;
                    System.arraycopy(objArr2, i8 << 1, objArr2, i2, (this.mSize - i) << 1);
                }
                Object[] objArr3 = this.mArray;
                int i9 = this.mSize << 1;
                objArr3[i9] = null;
                objArr3[i9 + 1] = null;
            }
        }
        return v;
    }

    public boolean retainAll(Collection<?> collection) {
        return GDMapCollections.qkduk(this, collection);
    }

    public V setValueAt(int i, V v) {
        int i2 = (i << 1) + 1;
        Object[] objArr = this.mArray;
        V v2 = (V) objArr[i2];
        objArr[i2] = v;
        return v2;
    }

    @Override // java.util.Map
    public int size() {
        return this.mSize;
    }

    public String toString() {
        if (isEmpty()) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.mSize * 28);
        sb.append('{');
        for (int i = 0; i < this.mSize; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            K keyAt = keyAt(i);
            if (keyAt != this) {
                sb.append(keyAt);
            } else {
                sb.append("(this Map)");
            }
            sb.append('=');
            V valueAt = valueAt(i);
            if (valueAt != this) {
                sb.append(valueAt);
            } else {
                sb.append("(this Map)");
            }
        }
        sb.append('}');
        return sb.toString();
    }

    public void validate() {
        int i = this.mSize;
        if (i <= 1) {
            return;
        }
        int i2 = 0;
        int i3 = this.mHashes[0];
        for (int i4 = 1; i4 < i; i4++) {
            int i5 = this.mHashes[i4];
            if (i5 != i3) {
                i2 = i4;
                i3 = i5;
            } else {
                Object obj = this.mArray[i4 << 1];
                for (int i6 = i4 - 1; i6 >= i2; i6--) {
                    Object obj2 = this.mArray[i6 << 1];
                    if (obj != obj2) {
                        if (obj != null && obj2 != null && obj.equals(obj2)) {
                            throw new IllegalArgumentException("Duplicate key in GDArrayMap: " + obj);
                        }
                    } else {
                        throw new IllegalArgumentException("Duplicate key in GDArrayMap: " + obj);
                    }
                }
                continue;
            }
        }
    }

    public V valueAt(int i) {
        return (V) this.mArray[(i << 1) + 1];
    }

    @Override // java.util.Map
    public Collection<V> values() {
        GDMapCollections<K, V> collection = getCollection();
        if (collection.jwxax == null) {
            collection.jwxax = new GDMapCollections.pmoiy();
        }
        return collection.jwxax;
    }

    public GDArrayMap(int i) {
        if (i == 0) {
            this.mHashes = EmptyArray.INT;
            this.mArray = EmptyArray.OBJECT;
        } else {
            allocArrays(i);
        }
        this.mSize = 0;
    }

    @Override // java.util.Map
    public void putAll(Map<? extends K, ? extends V> map) {
        ensureCapacity(this.mSize + map.size());
        for (Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    private GDArrayMap(boolean z) {
        this.mHashes = EmptyArray.INT;
        this.mArray = EmptyArray.OBJECT;
        this.mSize = 0;
    }

    public GDArrayMap(GDArrayMap gDArrayMap) {
        this();
        if (gDArrayMap != null) {
            putAll(gDArrayMap);
        }
    }
}
