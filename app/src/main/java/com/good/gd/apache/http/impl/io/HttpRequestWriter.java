package com.good.gd.apache.http.impl.io;

import com.good.gd.apache.http.HttpMessage;
import com.good.gd.apache.http.HttpRequest;
import com.good.gd.apache.http.io.SessionOutputBuffer;
import com.good.gd.apache.http.message.LineFormatter;
import com.good.gd.apache.http.params.HttpParams;
import java.io.IOException;

/* loaded from: classes.dex */
public class HttpRequestWriter extends AbstractMessageWriter {
    public HttpRequestWriter(SessionOutputBuffer sessionOutputBuffer, LineFormatter lineFormatter, HttpParams httpParams) {
        super(sessionOutputBuffer, lineFormatter, httpParams);
    }

    @Override // com.good.gd.apache.http.impl.io.AbstractMessageWriter
    protected void writeHeadLine(HttpMessage httpMessage) throws IOException {
        this.sessionBuffer.writeLine(this.lineFormatter.formatRequestLine(this.lineBuf, ((HttpRequest) httpMessage).getRequestLine()));
    }
}
