package com.good.gd.apache.http.message;

import com.good.gd.apache.http.HeaderElement;
import com.good.gd.apache.http.NameValuePair;
import com.good.gd.apache.http.util.CharArrayBuffer;
import com.good.gd.apache.http.util.LangUtils;

/* loaded from: classes.dex */
public class BasicHeaderElement implements HeaderElement, Cloneable {
    private final String name;
    private final NameValuePair[] parameters;
    private final String value;

    public BasicHeaderElement(String str, String str2, NameValuePair[] nameValuePairArr) {
        if (str != null) {
            this.name = str;
            this.value = str2;
            if (nameValuePairArr != null) {
                this.parameters = nameValuePairArr;
                return;
            } else {
                this.parameters = new NameValuePair[0];
                return;
            }
        }
        throw new IllegalArgumentException("Name may not be null");
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof HeaderElement)) {
            return false;
        }
        BasicHeaderElement basicHeaderElement = (BasicHeaderElement) obj;
        return this.name.equals(basicHeaderElement.name) && LangUtils.equals(this.value, basicHeaderElement.value) && LangUtils.equals((Object[]) this.parameters, (Object[]) basicHeaderElement.parameters);
    }

    @Override // com.good.gd.apache.http.HeaderElement
    public String getName() {
        return this.name;
    }

    @Override // com.good.gd.apache.http.HeaderElement
    public NameValuePair getParameter(int i) {
        return this.parameters[i];
    }

    @Override // com.good.gd.apache.http.HeaderElement
    public NameValuePair getParameterByName(String str) {
        if (str != null) {
            int i = 0;
            while (true) {
                NameValuePair[] nameValuePairArr = this.parameters;
                if (i >= nameValuePairArr.length) {
                    return null;
                }
                NameValuePair nameValuePair = nameValuePairArr[i];
                if (nameValuePair.getName().equalsIgnoreCase(str)) {
                    return nameValuePair;
                }
                i++;
            }
        } else {
            throw new IllegalArgumentException("Name may not be null");
        }
    }

    @Override // com.good.gd.apache.http.HeaderElement
    public int getParameterCount() {
        return this.parameters.length;
    }

    @Override // com.good.gd.apache.http.HeaderElement
    public NameValuePair[] getParameters() {
        return (NameValuePair[]) this.parameters.clone();
    }

    @Override // com.good.gd.apache.http.HeaderElement
    public String getValue() {
        return this.value;
    }

    public int hashCode() {
        int hashCode = LangUtils.hashCode(LangUtils.hashCode(17, this.name), this.value);
        int i = 0;
        while (true) {
            NameValuePair[] nameValuePairArr = this.parameters;
            if (i < nameValuePairArr.length) {
                hashCode = LangUtils.hashCode(hashCode, nameValuePairArr[i]);
                i++;
            } else {
                return hashCode;
            }
        }
    }

    public String toString() {
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(64);
        charArrayBuffer.append(this.name);
        if (this.value != null) {
            charArrayBuffer.append("=");
            charArrayBuffer.append(this.value);
        }
        for (int i = 0; i < this.parameters.length; i++) {
            charArrayBuffer.append("; ");
            charArrayBuffer.append(this.parameters[i]);
        }
        return charArrayBuffer.toString();
    }

    public BasicHeaderElement(String str, String str2) {
        this(str, str2, null);
    }
}
