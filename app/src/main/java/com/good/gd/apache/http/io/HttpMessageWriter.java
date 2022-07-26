package com.good.gd.apache.http.io;

import com.good.gd.apache.http.HttpException;
import com.good.gd.apache.http.HttpMessage;
import java.io.IOException;

/* loaded from: classes.dex */
public interface HttpMessageWriter {
    void write(HttpMessage httpMessage) throws IOException, HttpException;
}
