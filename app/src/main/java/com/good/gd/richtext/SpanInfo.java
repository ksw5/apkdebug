package com.good.gd.richtext;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class SpanInfo implements Parcelable {
    public static final Creator<SpanInfo> CREATOR = new hbfhc();
    public final int end;
    public final int flags;
    public final Object span;
    public final int start;

    /* loaded from: classes.dex */
    static class hbfhc implements Creator<SpanInfo> {
        hbfhc() {
        }

        @Override // android.os.Parcelable.Creator
        public SpanInfo createFromParcel(Parcel parcel) {
            return new SpanInfo(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public SpanInfo[] newArray(int i) {
            return new SpanInfo[i];
        }
    }

    protected SpanInfo() {
        this(0, 0, 0, null);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.start);
        parcel.writeInt(this.end);
        parcel.writeInt(this.flags);
        RichTextUtils.writeToParcel(this.span, parcel, i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public SpanInfo(int i, int i2, int i3, Object obj) {
        this.start = i;
        this.end = i2;
        this.flags = i3;
        this.span = obj;
    }

    protected SpanInfo(Parcel parcel) {
        this.start = parcel.readInt();
        this.end = parcel.readInt();
        this.flags = parcel.readInt();
        this.span = RichTextUtils.createFromParcel(parcel);
    }
}
