package com.good.gd.pki;

import android.content.Intent;
import android.util.SparseArray;
import com.good.gd.ndkproxy.pki.CredentialsProfileImpl;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class CredentialsProfile {
    public static final String GD_CREDENTIAL_PROFILE_STATE_CHANGE_ACTION = "com.good.gd.pki.CREDENTIAL_PROFILE_EVENT";
    protected String id;
    protected String name;
    protected String providerSettings;
    protected Boolean required;
    protected State state;
    protected String type;

    /* loaded from: classes.dex */
    public enum State {
        GDCredentialsProfileStateNone(0),
        GDCredentialsProfileStateImportDue(1),
        GDCredentialsProfileStateImportNow(2),
        GDCredentialsProfileStateImported(3),
        GDCredentialsProfileStateModified(4),
        GDCredentialsProfileStateRenewalDue(5),
        GDCredentialsProfileStateDeleted(6);
        
        private static final SparseArray<State> intCodeToEnum = new SparseArray<>();
        private int _state;

        static {
            Iterator it = EnumSet.allOf(State.class).iterator();
            while (it.hasNext()) {
                State state = (State) it.next();
                intCodeToEnum.put(state.getCode(), state);
            }
        }

        State(int i) {
            this._state = i;
        }

        public static State get(int i) {
            return intCodeToEnum.get(i);
        }

        public int getCode() {
            return this._state;
        }
    }

    public static Map<String, CredentialsProfile> getMap() {
        return CredentialsProfileImpl.getInstance().getMap();
    }

    public List<Credential> getCredentials() {
        return CredentialsProfileImpl.getInstance().getCredentials(getId());
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getProviderSettings() {
        return this.providerSettings;
    }

    public Boolean getRequired() {
        return this.required;
    }

    public State getState() {
        return this.state;
    }

    public String getType() {
        return this.type;
    }

    public static String getId(Intent intent) {
        return CredentialsProfileImpl.getId(intent);
    }

    public static String getName(Intent intent) {
        return CredentialsProfileImpl.getName(intent);
    }

    public static String getProviderSettings(Intent intent) {
        return CredentialsProfileImpl.getProviderSettings(intent);
    }

    public static boolean getRequired(Intent intent) {
        return CredentialsProfileImpl.getRequired(intent);
    }

    public static State getState(Intent intent) {
        return CredentialsProfileImpl.getProfileState(intent);
    }
}
