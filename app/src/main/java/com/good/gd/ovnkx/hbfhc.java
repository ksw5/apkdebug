package com.good.gd.ovnkx;

import java.io.IOException;
import java.util.UUID;

/* loaded from: classes.dex */
public class hbfhc {
    private static hbfhc jwxax;
    private static final Object qkduk = hbfhc.class;
    private final aqdzk dbjc;

    private hbfhc() {
        aqdzk aqdzkVar = new aqdzk(mjbm.wxau(), "app_identifier");
        this.dbjc = aqdzkVar;
        try {
            aqdzkVar.dbjc();
        } catch (IOException e) {
            com.good.gd.kloes.hbfhc.wxau(qkduk, "Unable to create App Instance ID file: " + e.getLocalizedMessage());
        }
    }

    public static hbfhc qkduk() {
        if (jwxax == null) {
            jwxax = new hbfhc();
        }
        return jwxax;
    }

    public String dbjc() {
        String str;
        String liflu = this.dbjc.liflu();
        if (com.good.gd.whhmi.yfdke.qkduk(liflu)) {
            if (com.blackberry.bis.core.yfdke.iulf() != null) {
                aqdzk aqdzkVar = new aqdzk("app_instance_id");
                if (!aqdzkVar.jwxax()) {
                    str = null;
                } else {
                    str = aqdzkVar.liflu();
                }
                if (com.good.gd.whhmi.yfdke.qkduk(str)) {
                    Object obj = qkduk;
                    com.good.gd.kloes.hbfhc.wxau(obj, "Unable to find App Instance ID from old file.");
                    String liflu2 = this.dbjc.liflu();
                    if (true != com.good.gd.whhmi.yfdke.qkduk(liflu2)) {
                        com.good.gd.kloes.hbfhc.wxau(obj, "App instance ID found at newer file after migration.");
                        return liflu2;
                    }
                    com.good.gd.kloes.hbfhc.wxau(obj, "Storing the App Instance ID freshly into new file.");
                    String uuid = UUID.randomUUID().toString();
                    this.dbjc.dbjc(uuid);
                    return uuid;
                }
                com.good.gd.kloes.hbfhc.wxau(qkduk, "App instance ID found at older file.");
                return str;
            }
            throw null;
        }
        return liflu;
    }
}
