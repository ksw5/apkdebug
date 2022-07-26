package com.good.gd.ovnkx;

import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class yfdke {
    private final aqdzk dbjc;
    private final Class jwxax;
    protected JSONObject qkduk;

    public yfdke() {
        Class<?> cls = getClass();
        this.jwxax = cls;
        com.good.gd.kloes.hbfhc.wxau(cls, "Creating storage settings for client");
        aqdzk aqdzkVar = new aqdzk(mjbm.qkduk(), "network_intervals");
        this.dbjc = aqdzkVar;
        try {
            aqdzkVar.dbjc();
            jwxax();
        } catch (IOException e) {
            com.good.gd.kloes.hbfhc.qkduk(this.jwxax, "IOException in creating client settings storage file. \t" + e.getLocalizedMessage());
        }
    }

    public void dbjc(com.good.gd.zwn.pmoiy pmoiyVar) {
        com.good.gd.kloes.hbfhc.wxau(this.jwxax, "Saving Upload Server Configuration in a File.");
        if (this.qkduk == null) {
            this.qkduk = new JSONObject();
        }
        try {
            this.qkduk.put("configuration_client_interval", pmoiyVar.dbjc);
            this.qkduk.put("configuration_client_window", pmoiyVar.qkduk);
            this.qkduk.put("configuration_user_correlation", pmoiyVar.jwxax());
            this.qkduk.put("configuration_upload_interval", pmoiyVar.wxau);
            this.qkduk.put("configuration_upload_window", pmoiyVar.ztwf);
            this.qkduk.put("configuration_client_val", pmoiyVar.wxau());
            this.qkduk.put("configuration_upload_val", pmoiyVar.qkduk());
            com.good.gd.kloes.ehnkx.jwxax(this.jwxax, "Saving configuration: " + this.qkduk);
            if (true == this.dbjc.dbjc(this.qkduk.toString())) {
                return;
            }
            throw new IOException("can't save content to file");
        } catch (Exception e) {
            com.good.gd.kloes.hbfhc.qkduk(this.jwxax, "Exception while saving the client config settings. \t" + e.getLocalizedMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void jwxax() {
        com.good.gd.kloes.hbfhc.wxau(this.jwxax, "Load Upload Server Configuration File.");
        try {
            this.qkduk = new JSONObject(this.dbjc.liflu());
            com.good.gd.kloes.hbfhc.wxau(this.jwxax, "Upload Server Configuration File Data Loaded.");
            com.good.gd.kloes.ehnkx.jwxax(this.jwxax, "Server Configuration File Data: " + this.qkduk);
        } catch (Exception e) {
            com.good.gd.kloes.hbfhc.qkduk(this.jwxax, "Upload Server Configuration File Data not Found.");
            com.good.gd.kloes.hbfhc.wxau(this.jwxax, "Trying to load upload server configuration from older filesystem.");
            try {
                if (com.blackberry.bis.core.yfdke.iulf() != null) {
                    aqdzk aqdzkVar = new aqdzk("persistent_storage");
                    if (aqdzkVar.jwxax()) {
                        this.qkduk = new JSONObject(aqdzkVar.liflu());
                        return;
                    }
                    com.good.gd.kloes.hbfhc.qkduk(this.jwxax, "Load Upload Server Configuration. Old file doesn't exist");
                    dbjc();
                    return;
                }
                throw null;
            } catch (Exception e2) {
                com.good.gd.kloes.hbfhc.qkduk(this.jwxax, "Exception while loading old Upload Server Configuration File." + e2.getLocalizedMessage());
                dbjc();
            }
        }
    }

    public com.good.gd.zwn.pmoiy qkduk() {
        com.good.gd.kloes.hbfhc.wxau(this.jwxax, "Get Network Configuration.");
        if (this.qkduk == null) {
            this.qkduk = new JSONObject();
        }
        return new com.good.gd.zwn.pmoiy(this.qkduk.optInt("configuration_client_interval", -1), this.qkduk.optInt("configuration_client_window", -1), this.qkduk.optBoolean("configuration_user_correlation", false), this.qkduk.optInt("configuration_upload_interval", -1), this.qkduk.optInt("configuration_upload_window", -1), this.qkduk.optInt("configuration_client_val", -1), this.qkduk.optInt("configuration_upload_val", -1));
    }

    public void dbjc() {
        com.good.gd.kloes.hbfhc.wxau(this.jwxax, "Clean up Client Settings Storage.");
        this.dbjc.qkduk();
        if (this.qkduk == null) {
            this.qkduk = new JSONObject();
        }
        try {
            this.qkduk.put("configuration_client_interval", -1);
            this.qkduk.put("configuration_client_window", -1);
            this.qkduk.put("configuration_user_correlation", false);
            this.qkduk.put("configuration_upload_interval", -1);
            this.qkduk.put("configuration_upload_window", -1);
            this.qkduk.put("configuration_client_val", -1);
            this.qkduk.put("configuration_upload_val", -1);
            this.qkduk.put("configuration_upload_val", -1);
        } catch (JSONException e) {
            com.good.gd.kloes.hbfhc.qkduk(this.jwxax, "Json not cleaned up! \t" + e.getLocalizedMessage());
        }
    }
}
