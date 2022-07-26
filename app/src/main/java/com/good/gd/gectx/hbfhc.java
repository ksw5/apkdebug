package com.good.gd.gectx;

import com.good.gd.ui.utils.sis.SISProxy;
import com.good.gd.ui.utils.sis.TelemetryManagerConstants;
import com.good.gd.wxh.ehnkx;
import com.good.gd.wxh.fdyxd;
import com.good.gd.wxh.yfdke;

/* loaded from: classes.dex */
public class hbfhc implements com.good.gd.alu.hbfhc {
    private static com.good.gd.wwiqd.hbfhc dbjc;
    private static com.good.gd.qvfxc.hbfhc qkduk;

    static {
        fdyxd fdyxdVar = new fdyxd();
        SISProxy.setLocationDialogListener(fdyxdVar);
        SISProxy.setUXActionListener(fdyxdVar);
        SISProxy.setLocationInfoProvider(new ehnkx());
        SISProxy.setActivityStatusListener(new yfdke());
        SISProxy.setLogger(new com.good.gd.wxh.hbfhc());
        SISProxy.setTouchListenersProvider(new com.good.gd.xfftf.hbfhc());
        TelemetryManagerConstants.PRIMER_WAITING_FOR_USER_ACTION = -1;
        TelemetryManagerConstants.PRIMER_AGREED = 0;
        TelemetryManagerConstants.PRIMER_MAY_BE_LATER = 1;
        TelemetryManagerConstants.PRIMER_DISAGREED_WITH_NEVER_ASK_AGAIN = 2;
    }

    public void dbjc(com.good.gd.wwiqd.hbfhc hbfhcVar) {
        dbjc = hbfhcVar;
    }

    public com.good.gd.wwiqd.hbfhc qkduk() {
        return dbjc;
    }

    public void dbjc(com.good.gd.qvfxc.hbfhc hbfhcVar) {
        qkduk = hbfhcVar;
    }

    public com.good.gd.qvfxc.hbfhc dbjc() {
        return qkduk;
    }
}
