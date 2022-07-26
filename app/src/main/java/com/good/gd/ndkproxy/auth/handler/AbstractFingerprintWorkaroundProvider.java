package com.good.gd.ndkproxy.auth.handler;

import android.os.Build;
import android.util.Log;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationHandler;
import com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationManager;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes.dex */
public abstract class AbstractFingerprintWorkaroundProvider extends AbstractFingerprintAuthenticationHandler implements GDFingerprintAuthenticationHandler {
    private static final String ALIAS_SEPARATOR = ";";
    private static final String KEY_ALIAS_WORKAROUND_PREFIX = "GD_UK_";
    private static final int MAX_KEY_ALIAS_COUNT = 5;
    private boolean keyStoreWorkaroundNeeded;
    private List<String> usedAliasesList;
    private boolean workaroundCanBeUsed;

    /* JADX INFO: Access modifiers changed from: protected */
    public AbstractFingerprintWorkaroundProvider() throws Exception {
        this.workaroundCanBeUsed = Build.VERSION.SDK_INT >= 26;
        this.keyStoreWorkaroundNeeded = false;
        ArrayList arrayList = new ArrayList();
        this.usedAliasesList = arrayList;
        Collections.addAll(arrayList, GDFingerprintAuthenticationManager.getKeyStoreAlias().split(ALIAS_SEPARATOR));
        this.usedAliasesList.remove("");
        if (this.usedAliasesList.size() > 0) {
            List<String> list = this.usedAliasesList;
            if (list.get(list.size() - 1).length() <= 0) {
                return;
            }
            List<String> list2 = this.usedAliasesList;
            AbstractFingerprintAuthenticationHandler.KEY_ALIAS = list2.get(list2.size() - 1);
            GDLog.DBGPRINTF(14, "Fingerprint: ", String.valueOf(this.usedAliasesList.size() - 1), "\n");
        }
    }

    private boolean createKeyStoreKeyWorkaround() throws GeneralSecurityException {
        if (this.usedAliasesList.size() == 5) {
            return false;
        }
        String str = KEY_ALIAS_WORKAROUND_PREFIX + this.usedAliasesList.size();
        this.usedAliasesList.add(str);
        saveAliases(this.usedAliasesList);
        try {
            AbstractFingerprintAuthenticationHandler.KEY_ALIAS = str;
            createKeyStoreKey();
            return true;
        } catch (RuntimeException e) {
            printUnexpectedException(e);
            throw new GeneralSecurityException(e);
        }
    }

    private String prepareAliasesForCore(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String str : list) {
            sb.append(str);
            sb.append(ALIAS_SEPARATOR);
        }
        return sb.toString();
    }

    private void removeWorkaroundKeysIfPossible() {
        if (!this.workaroundCanBeUsed) {
            return;
        }
        if (hasKeyStoreKey("UK")) {
            deleteKeyStoreKey("UK");
        }
        boolean z = !hasKeyStoreKey("UK");
        if (this.usedAliasesList.size() == 0) {
            return;
        }
        LinkedList linkedList = new LinkedList();
        for (String str : this.usedAliasesList) {
            deleteKeyStoreKey(str);
            linkedList.add(str);
        }
        this.usedAliasesList.removeAll(linkedList);
        if (this.usedAliasesList.size() > 0) {
            GDLog.DBGPRINTF(12, "Fingerprint was not deleted\n");
            if (z) {
                this.usedAliasesList.add("UK");
            }
        } else {
            AbstractFingerprintAuthenticationHandler.KEY_ALIAS = "UK";
        }
        if (z) {
            AbstractFingerprintAuthenticationHandler.KEY_ALIAS = "UK";
        }
        saveAliases(this.usedAliasesList);
    }

    private void saveAliases(List<String> list) {
        GDFingerprintAuthenticationManager.setKeyStoreAlias(prepareAliasesForCore(list));
    }

    protected void checkWorkaroundForDelete() {
        if (!this.workaroundCanBeUsed) {
            return;
        }
        if (hasKeyStoreKey()) {
            try {
                GDLog.DBGPRINTF(12, "Fingerprint was not deleted\n");
                createKeyStoreKey();
                super.deleteKeyStoreKey();
            } catch (RuntimeException | GeneralSecurityException e) {
                printUnexpectedException(e);
            }
            if (!hasKeyStoreKey()) {
                return;
            }
            GDLog.DBGPRINTF(12, "Failed to delete fingerprint twice\n");
            this.keyStoreWorkaroundNeeded = true;
            return;
        }
        removeWorkaroundKeysIfPossible();
    }

    @Override // com.good.gd.ndkproxy.auth.handler.AbstractFingerprintAuthenticationHandler, com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationHandler
    public void deleteKeyStoreKey() {
        super.deleteKeyStoreKey();
        if (!this.workaroundCanBeUsed) {
            return;
        }
        checkWorkaroundForDelete();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.good.gd.ndkproxy.auth.handler.AbstractFingerprintAuthenticationHandler
    public void ensureKeyStoreKey() throws GeneralSecurityException {
        try {
            super.ensureKeyStoreKey();
            if (!this.workaroundCanBeUsed || !this.keyStoreWorkaroundNeeded) {
                return;
            }
            this.keyStoreWorkaroundNeeded = false;
            GDLog.DBGPRINTF(16, "Using workaround for Fingerprint\n");
            if (createKeyStoreKeyWorkaround()) {
                return;
            }
            GDLog.DBGPRINTF(12, "Fingerprint should be reset by the user\n");
        } catch (RuntimeException e) {
            printUnexpectedException(e);
            throw new GeneralSecurityException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean ensureKeystoreFaceUnlockSafe() throws GeneralSecurityException {
        try {
            ensureKeyStoreKey();
            return true;
        } catch (InvalidAlgorithmParameterException e) {
            if ((e.getCause() instanceof IllegalStateException) && Build.VERSION.SDK_INT >= 29 && Build.MANUFACTURER.equalsIgnoreCase("samsung")) {
                GDLog.DBGPRINTF(13, "FingerprintAuthHandler ensureKeystoreFaceUnlockSafe - FAILED - Exception = " + Log.getStackTraceString(e) + "\n");
                return false;
            }
            throw e;
        }
    }
}
