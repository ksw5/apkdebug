package com.journeyapps.barcodescanner;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;

/* loaded from: classes.dex */
public class DefaultDecoderFactory implements DecoderFactory {
    private String characterSet;
    private Collection<BarcodeFormat> decodeFormats;
    private Map<DecodeHintType, ?> hints;
    private int scanType;

    public DefaultDecoderFactory() {
    }

    public DefaultDecoderFactory(Collection<BarcodeFormat> decodeFormats) {
        this.decodeFormats = decodeFormats;
    }

    public DefaultDecoderFactory(Collection<BarcodeFormat> decodeFormats, Map<DecodeHintType, ?> hints, String characterSet, int scanType) {
        this.decodeFormats = decodeFormats;
        this.hints = hints;
        this.characterSet = characterSet;
        this.scanType = scanType;
    }

    @Override // com.journeyapps.barcodescanner.DecoderFactory
    public Decoder createDecoder(Map<DecodeHintType, ?> baseHints) {
        Map<DecodeHintType, Object> hints = new EnumMap<>(DecodeHintType.class);
        hints.putAll(baseHints);
        Map<? extends DecodeHintType, ? extends Object> map = this.hints;
        if (map != null) {
            hints.putAll(map);
        }
        if (this.decodeFormats != null) {
            hints.put(DecodeHintType.POSSIBLE_FORMATS, this.decodeFormats);
        }
        if (this.characterSet != null) {
            hints.put(DecodeHintType.CHARACTER_SET, this.characterSet);
        }
        MultiFormatReader reader = new MultiFormatReader();
        reader.setHints(hints);
        switch (this.scanType) {
            case 0:
                return new Decoder(reader);
            case 1:
                return new InvertedDecoder(reader);
            case 2:
                return new MixedDecoder(reader);
            default:
                return new Decoder(reader);
        }
    }
}
