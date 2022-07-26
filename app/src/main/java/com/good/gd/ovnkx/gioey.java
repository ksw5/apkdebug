package com.good.gd.ovnkx;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class gioey {
    private final aqdzk dbjc;
    private JSONObject lqox;
    private boolean wxau;
    private boolean ztwf;
    private long qkduk = -1;
    private long jwxax = -1;
    private final Class liflu = gioey.class;

    public gioey() {
        com.good.gd.kloes.hbfhc.wxau(gioey.class, "Task Scheduler Storage.");
        aqdzk aqdzkVar = new aqdzk(mjbm.qkduk(), "task_scheduler");
        this.dbjc = aqdzkVar;
        try {
            aqdzkVar.dbjc();
            tlske();
        } catch (IOException e) {
            com.good.gd.kloes.hbfhc.wxau(this.liflu, "Failed to create network configuration file: " + e.getLocalizedMessage());
        }
    }

    private void tlske() {
        try {
            JSONObject jSONObject = new JSONObject(this.dbjc.liflu());
            this.lqox = jSONObject;
            this.qkduk = jSONObject.optLong("next_get_request_in", -1L);
            this.jwxax = this.lqox.optLong("next_post_request_in", -1L);
            com.good.gd.kloes.hbfhc.wxau(this.liflu, String.format(Locale.getDefault(), "Load Task Scheduler Configuration get[%d] post[%d]", Long.valueOf(this.qkduk), Long.valueOf(this.jwxax)));
        } catch (Exception e) {
            com.good.gd.kloes.hbfhc.qkduk(this.liflu, "Load Task Scheduler Configuration. Local file doesn't exist");
            com.good.gd.kloes.hbfhc.wxau(this.liflu, "Trying to load task scheduler configuration from older filesystem.");
            try {
                if (com.blackberry.bis.core.yfdke.iulf() != null) {
                    aqdzk aqdzkVar = new aqdzk("task_scheduler_storage");
                    if (aqdzkVar.jwxax()) {
                        JSONObject jSONObject2 = new JSONObject(aqdzkVar.liflu());
                        this.lqox = jSONObject2;
                        this.qkduk = jSONObject2.optLong("next_get_request_in", -1L);
                        this.jwxax = this.lqox.optLong("next_post_request_in", -1L);
                        com.good.gd.kloes.hbfhc.wxau(this.liflu, String.format(Locale.getDefault(), "Load Task Scheduler Configuration get[%d] post[%d]", Long.valueOf(this.qkduk), Long.valueOf(this.jwxax)));
                    } else {
                        com.good.gd.kloes.hbfhc.qkduk(this.liflu, "Load Old Task Scheduler Configuration. Old file doesn't exist");
                        dbjc();
                    }
                } else {
                    throw null;
                }
            } catch (Exception e2) {
                com.good.gd.kloes.hbfhc.qkduk(this.liflu, "Exception while loading Old Task Scheduler Configuration." + e2.getLocalizedMessage());
                dbjc();
            }
        }
    }

    public void dbjc(long j, TimeUnit timeUnit) {
        com.good.gd.kloes.hbfhc.wxau(this.liflu, "Set Next Get Request.");
        this.ztwf = true;
        if (j <= 0) {
            return;
        }
        this.qkduk = System.currentTimeMillis() + timeUnit.toMillis(j);
        if (this.lqox == null) {
            this.lqox = new JSONObject();
        }
        try {
            this.lqox.put("next_get_request_in", this.qkduk);
            if (true == this.dbjc.dbjc(this.lqox.toString())) {
                return;
            }
            throw new IOException("Can't save content to file");
        } catch (Exception e) {
            com.good.gd.kloes.hbfhc.wxau(this.liflu, "Failed to set next GET request delay: " + e.getLocalizedMessage());
        }
    }

    public void jcpqe() {
        com.good.gd.kloes.hbfhc.wxau(this.liflu, "resetUploadReqScheduledFlag");
        this.wxau = false;
    }

    public long jwxax() {
        long j = this.qkduk;
        if (j == -1) {
            return j;
        }
        long currentTimeMillis = j - System.currentTimeMillis();
        if (currentTimeMillis >= 0) {
            return currentTimeMillis;
        }
        return 0L;
    }

    public void liflu() {
        com.good.gd.kloes.hbfhc.wxau(this.liflu, "resetGetConfigReqScheduledFlag");
        this.ztwf = false;
    }

    public boolean lqox() {
        return this.wxau;
    }

    public void qkduk(long j, TimeUnit timeUnit) {
        com.good.gd.kloes.hbfhc.wxau(this.liflu, "Set Next Post Request.");
        this.wxau = true;
        if (j <= 0) {
            return;
        }
        this.jwxax = System.currentTimeMillis() + timeUnit.toMillis(j);
        if (this.lqox == null) {
            this.lqox = new JSONObject();
        }
        try {
            this.lqox.put("next_post_request_in", this.jwxax);
            if (true == this.dbjc.dbjc(this.lqox.toString())) {
                return;
            }
            throw new IOException("can't save content to file");
        } catch (Exception e) {
            com.good.gd.kloes.hbfhc.wxau(this.liflu, "Failed to set next POST request delay: " + e.getLocalizedMessage());
        }
    }

    public long wxau() {
        long j = this.jwxax;
        if (j == -1) {
            return j;
        }
        long currentTimeMillis = j - System.currentTimeMillis();
        if (currentTimeMillis >= 0) {
            return currentTimeMillis;
        }
        return 0L;
    }

    public boolean ztwf() {
        return this.ztwf;
    }

    public boolean qkduk() {
        return this.qkduk > -1 || this.jwxax > -1;
    }

    public void dbjc() {
        com.good.gd.kloes.hbfhc.wxau(this.liflu, "Cleaning up Task Scheduler Storage.");
        this.qkduk = -1L;
        this.jwxax = -1L;
        liflu();
        jcpqe();
        this.dbjc.qkduk();
        if (this.lqox == null) {
            this.lqox = new JSONObject();
        }
        try {
            this.lqox.put("next_get_request_in", -1);
            this.lqox.put("next_post_request_in", -1);
        } catch (JSONException e) {
            com.good.gd.kloes.hbfhc.qkduk(this.liflu, "Task Scheduler Storage not cleaned up!");
        }
    }
}
