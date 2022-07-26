package com.good.gd.ndkproxy.auth;

/* loaded from: classes.dex */
class hbfhc {
    private int dbjc;

    public hbfhc(int i) {
        this.dbjc = i;
    }

    public void dbjc(int i) {
        this.dbjc = (~i) & this.dbjc;
    }

    public boolean equals(Object obj) {
        return (obj instanceof hbfhc) && ((hbfhc) obj).dbjc == this.dbjc;
    }

    public boolean jwxax(int i) {
        return (this.dbjc & i) == i;
    }

    public void qkduk(int i) {
        this.dbjc = i | this.dbjc;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        boolean z = true;
        StringBuilder append = sb.append("UNLOCK_STATE_WARM_START_ALLOWED = ").append((this.dbjc & 1) != 0).append('\n').append("UNLOCK_STATE_WARM_START_DECLINED = ").append((this.dbjc & 2) != 0).append('\n').append("UNLOCK_STATE_WARM_START_ACTIVATED = ").append((this.dbjc & 4) != 0).append('\n').append("UNLOCK_STATE_COLD_START_ALLOWED = ").append((this.dbjc & 256) != 0).append('\n').append("UNLOCK_STATE_COLD_START_DECLINED = ").append((this.dbjc & 512) != 0).append('\n').append("UNLOCK_STATE_COLD_START_ACTIVATED = ");
        if ((this.dbjc & 1024) == 0) {
            z = false;
        }
        append.append(z).append('\n');
        return sb.toString();
    }

    public int dbjc() {
        return this.dbjc;
    }

    public hbfhc() {
        this.dbjc = 0;
    }
}
