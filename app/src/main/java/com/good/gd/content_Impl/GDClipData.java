package com.good.gd.content_Impl;

import android.content.ClipData;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.Base64;
import com.good.gd.ndkproxy.GDClipboardCryptoUtil;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.richtext.ParcelableUtil;
import com.good.gd.richtext.RichTextUtils;
import com.good.gd.richtext.SpanInfo;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class GDClipData extends ClipData {
    public static final String DEFAULT_CLIP_LABEL = "standart/default plain text";
    private static final int ENCRYPTED_DATA_ITEM_INDEX = 1;
    private static final int EXPECTED_MIME_ITEMS_COUNT = 3;
    private static final String GD_CLIPBOARD_MIME_TYPE = "gd/clipboard";
    static final String GD_ENCRYPTED_CLIP_LABEL = "GD encrypted clipboard data";
    private static final int RICHTEXT_INFO_ITEM_INDEX = 2;
    public static final String SPANS_KEY = "spans";

    private GDClipData(String str, String[] strArr, Item item) {
        super(str, strArr, item);
    }

    private static GDClipData createClipData(String str, CharSequence charSequence, CharSequence charSequence2) {
        Item item = new Item((CharSequence) null);
        String createRichTextData = createRichTextData(charSequence2);
        return new GDClipData(str, createRichTextData != null ? new String[]{GD_CLIPBOARD_MIME_TYPE, charSequence.toString(), createRichTextData} : new String[]{GD_CLIPBOARD_MIME_TYPE, charSequence.toString()}, item);
    }

    private static String createRichTextData(CharSequence charSequence) {
        try {
            if (charSequence instanceof Spannable) {
                ArrayList<? extends Parcelable> arrayList = new ArrayList<>(RichTextUtils.extractSpans((Spannable) charSequence));
                Bundle bundle = new Bundle();
                bundle.setClassLoader(SpanInfo.class.getClassLoader());
                bundle.putParcelableArrayList(SPANS_KEY, arrayList);
                return Base64.encodeToString(ParcelableUtil.marshall(bundle), 0);
            }
            GDLog.DBGPRINTF(16, "GDClipData createRichTextData text is not a Spannable");
            return null;
        } catch (Exception e) {
            GDLog.DBGPRINTF(12, "GDClipData createRichTextData", e);
            return null;
        }
    }

    private static CharSequence extractRichText(ClipData clipData, String str) {
        try {
            Bundle readBundle = ParcelableUtil.unmarshall(Base64.decode(getGDRichMetaData(clipData), 0)).readBundle(SpanInfo.class.getClassLoader());
            readBundle.setClassLoader(SpanInfo.class.getClassLoader());
            ArrayList parcelableArrayList = readBundle.getParcelableArrayList(SPANS_KEY);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str);
            RichTextUtils.applySpans(parcelableArrayList, spannableStringBuilder);
            return spannableStringBuilder;
        } catch (Exception e) {
            GDLog.DBGPRINTF(12, "extractRichText", e);
            return null;
        }
    }

    private static String getGDRichMetaData(ClipData clipData) {
        if (isGdClipData(clipData)) {
            if (clipData.getDescription().getMimeTypeCount() < 3) {
                return null;
            }
            return clipData.getDescription().getMimeType(2);
        }
        throw new IllegalArgumentException("Given ClipData instance is not GDClipData");
    }

    private static String getGDTextData(ClipData clipData) {
        if (isGdClipData(clipData)) {
            return clipData.getDescription().getMimeType(1);
        }
        throw new IllegalArgumentException("Given ClipData instance is not GDClipData");
    }

    static boolean isGdClipData(ClipData clipData) {
        CharSequence label = clipData.getDescription().getLabel();
        return label != null && GD_ENCRYPTED_CLIP_LABEL.equals(label.toString());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static ClipData newDecryptedTextData(ClipData clipData) {
        String decryptString = GDClipboardCryptoUtil.decryptString(getGDTextData(clipData));
        CharSequence extractRichText = extractRichText(clipData, decryptString);
        if (extractRichText != null) {
            return newDefaultClipData(extractRichText);
        }
        return newDefaultClipData(decryptString);
    }

    public static ClipData newDefaultClipData(CharSequence charSequence) {
        return ClipData.newPlainText(DEFAULT_CLIP_LABEL, charSequence);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static GDClipData newEncryptedTextData(CharSequence charSequence) {
        return createClipData(GD_ENCRYPTED_CLIP_LABEL, GDClipboardCryptoUtil.encryptString(charSequence.toString()), charSequence);
    }
}
