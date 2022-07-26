package com.good.gd.ndkproxy.native2javabridges.utils;

import java.text.Collator;
import java.text.Normalizer;
import java.util.Locale;

/* loaded from: classes.dex */
public class StringCompareUtils {
    public static final int CompareOptionCaseInsensitive = 1;
    public static final int CompareOptionDiacriticInsensitive = 2;
    public static final int CompareOptionNone = 0;

    /* loaded from: classes.dex */
    public static class CaseDiacriticInsensitiveComparator extends StringComparator {
        private Collator collator;

        public CaseDiacriticInsensitiveComparator() {
            Collator collator = Collator.getInstance(Locale.ENGLISH);
            this.collator = collator;
            collator.setStrength(0);
        }

        @Override // com.good.gd.ndkproxy.native2javabridges.utils.StringCompareUtils.StringComparator
        public boolean equals(String str, String str2) {
            return this.collator.equals(str, str2);
        }
    }

    /* loaded from: classes.dex */
    public static class CaseInsensitiveComparator extends StringComparator {
        @Override // com.good.gd.ndkproxy.native2javabridges.utils.StringCompareUtils.StringComparator
        public boolean equals(String str, String str2) {
            return str.equalsIgnoreCase(str2);
        }
    }

    /* loaded from: classes.dex */
    public static class DiacriticInsensitiveComparator extends StringComparator {
        private Collator collator;

        public DiacriticInsensitiveComparator() {
            Collator collator = Collator.getInstance(Locale.ENGLISH);
            this.collator = collator;
            collator.setStrength(0);
        }

        @Override // com.good.gd.ndkproxy.native2javabridges.utils.StringCompareUtils.StringComparator
        public boolean equals(String str, String str2) {
            if (this.collator.equals(str, str2)) {
                return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "").equals(Normalizer.normalize(str2, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", ""));
            }
            return false;
        }
    }

    /* loaded from: classes.dex */
    public static class IdenticalComparator extends StringComparator {
        @Override // com.good.gd.ndkproxy.native2javabridges.utils.StringCompareUtils.StringComparator
        public boolean equals(String str, String str2) {
            return str.equals(str2);
        }
    }

    /* loaded from: classes.dex */
    public static abstract class StringComparator {
        public abstract boolean equals(String str, String str2);
    }

    public static StringComparator getStringComparator(int i) {
        if (3 == i) {
            return new CaseDiacriticInsensitiveComparator();
        }
        if (1 == i) {
            return new CaseInsensitiveComparator();
        }
        if (2 == i) {
            return new DiacriticInsensitiveComparator();
        }
        if (i == 0) {
            return new IdenticalComparator();
        }
        throw new UnsupportedOperationException("Unsupported compare type");
    }
}
