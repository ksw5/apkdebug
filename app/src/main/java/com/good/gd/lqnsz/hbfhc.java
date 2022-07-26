package com.good.gd.lqnsz;

import java.util.HashMap;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class hbfhc {
    private String dbjc;
    private yfdke jwxax;
    private C0015hbfhc qkduk;
    private JSONObject wxau;

    /* renamed from: com.good.gd.lqnsz.hbfhc$hbfhc  reason: collision with other inner class name */
    /* loaded from: classes.dex */
    public class C0015hbfhc {
        private JSONObject dbjc;
        private JSONObject jwxax;
        private JSONObject qkduk;
        private yfdke wxau;
        private C0016hbfhc ztwf;

        /* renamed from: com.good.gd.lqnsz.hbfhc$hbfhc$hbfhc  reason: collision with other inner class name */
        /* loaded from: classes.dex */
        public class C0016hbfhc {
            String dbjc;

            public C0016hbfhc(C0015hbfhc c0015hbfhc) {
            }

            public String dbjc() {
                return this.dbjc;
            }
        }

        /* renamed from: com.good.gd.lqnsz.hbfhc$hbfhc$yfdke */
        /* loaded from: classes.dex */
        public class yfdke {
            String dbjc;

            public yfdke(C0015hbfhc c0015hbfhc) {
            }

            public String dbjc() {
                return this.dbjc;
            }
        }

        public C0015hbfhc(hbfhc hbfhcVar) {
        }

        public void dbjc(JSONObject jSONObject) {
            this.jwxax = jSONObject;
        }

        public yfdke jwxax() {
            return this.wxau;
        }

        public void qkduk(JSONObject jSONObject) {
            this.qkduk = jSONObject;
        }

        public JSONObject wxau() {
            return this.qkduk;
        }

        public JSONObject ztwf() {
            return this.dbjc;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public void dbjc(yfdke yfdkeVar) {
            this.wxau = yfdkeVar;
        }

        public void jwxax(JSONObject jSONObject) {
            this.dbjc = jSONObject;
        }

        public JSONObject qkduk() {
            return this.jwxax;
        }

        public C0016hbfhc dbjc() {
            return this.ztwf;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public void dbjc(C0016hbfhc c0016hbfhc) {
            this.ztwf = c0016hbfhc;
        }
    }

    /* loaded from: classes.dex */
    public class yfdke {
        private HashMap<String, C0017hbfhc> dbjc = new HashMap<>();

        /* renamed from: com.good.gd.lqnsz.hbfhc$yfdke$hbfhc  reason: collision with other inner class name */
        /* loaded from: classes.dex */
        public class C0017hbfhc {
            private String dbjc;
            private JSONObject qkduk;

            public C0017hbfhc(yfdke yfdkeVar) {
            }

            public void dbjc(String str) {
                this.dbjc = str;
            }

            public String qkduk() {
                return this.dbjc;
            }

            public JSONObject dbjc() {
                return this.qkduk;
            }

            public void dbjc(JSONObject jSONObject) {
                this.qkduk = jSONObject;
            }
        }

        public yfdke(hbfhc hbfhcVar) {
        }

        public HashMap<String, C0017hbfhc> dbjc() {
            return this.dbjc;
        }

        public void dbjc(HashMap<String, C0017hbfhc> hashMap) {
            this.dbjc = hashMap;
        }
    }

    public void dbjc(String str) {
        this.dbjc = str;
    }

    public String jwxax() {
        return this.dbjc;
    }

    public JSONObject qkduk() {
        return this.wxau;
    }

    public yfdke wxau() {
        return this.jwxax;
    }

    public C0015hbfhc dbjc() {
        return this.qkduk;
    }

    public void dbjc(C0015hbfhc c0015hbfhc) {
        this.qkduk = c0015hbfhc;
    }

    public void dbjc(yfdke yfdkeVar) {
        this.jwxax = yfdkeVar;
    }

    public void dbjc(JSONObject jSONObject) {
        this.wxau = jSONObject;
    }
}
