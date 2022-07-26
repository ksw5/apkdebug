package com.good.gd.kloes;

import android.content.Context;
import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.json.JSONObject;

/* loaded from: classes.dex */
class yfdke {
    private static boolean dbjc = false;
    private static boolean qkduk = false;

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.good.gd.kloes.yfdke$yfdke  reason: collision with other inner class name */
    /* loaded from: classes.dex */
    public static class AsyncTaskC0013yfdke extends AsyncTask<Context, Void, JSONObject> {
        private AsyncTaskC0013yfdke() {
        }

        @Override // android.os.AsyncTask
        protected JSONObject doInBackground(Context[] contextArr) {
            BufferedReader bufferedReader;
            Context[] contextArr2 = contextArr;
            StringBuilder sb = new StringBuilder();
            BufferedReader bufferedReader2 = null;
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(contextArr2[0].getAssets().open("settings.json")));
                while (true) {
                    try {
                        try {
                            String readLine = bufferedReader.readLine();
                            if (readLine == null) {
                                break;
                            }
                            sb.append(readLine);
                        } catch (Exception e) {
                            e = e;
                            hbfhc.dbjc(this, "ANALYTICS_LIB", "Failed to read settings file", e);
                            if (bufferedReader == null) {
                                return null;
                            }
                            try {
                                bufferedReader.close();
                                return null;
                            } catch (Exception e2) {
                                return null;
                            }
                        }
                    } catch (Throwable th) {
                        th = th;
                        bufferedReader2 = bufferedReader;
                        if (bufferedReader2 != null) {
                            try {
                                bufferedReader2.close();
                            } catch (Exception e3) {
                            }
                        }
                        throw th;
                    }
                }
                if (sb.length() != 0) {
                    JSONObject jSONObject = new JSONObject(sb.toString());
                    try {
                        bufferedReader.close();
                    } catch (Exception e4) {
                    }
                    return jSONObject;
                }
                throw new Exception("Empty settings file");
            } catch (Exception e5) {
                e = e5;
                bufferedReader = null;
            } catch (Throwable th2) {
                th = th2;
            }
        }

        @Override // android.os.AsyncTask
        protected void onPostExecute(JSONObject jSONObject) {
            JSONObject jSONObject2 = jSONObject;
            if (jSONObject2 == null) {
                return;
            }
            try {
                if (jSONObject2.has("GCSDebugLogging")) {
                    boolean unused = yfdke.dbjc = jSONObject2.getBoolean("GCSDebugLogging");
                } else if (jSONObject2.has("enableMultipleAccounts")) {
                    boolean unused2 = yfdke.qkduk = jSONObject2.getBoolean("enableMultipleAccounts");
                }
                hbfhc.dbjc(this, "ANALYTICS_LIB", "isDebugMode = " + yfdke.dbjc);
                hbfhc.dbjc(this, "ANALYTICS_LIB", "multipleAccountsEnabled = " + yfdke.qkduk);
            } catch (Exception e) {
                hbfhc.dbjc(this, "ANALYTICS_LIB", "Invalid JSON property", e);
            }
        }
    }
}
