package com.liompei.youquanhelper.network.download;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by Liompei
 * Time 2017/10/11 20:56
 * 1137694912@qq.com
 * remark:自定义精度的body
 */

public class DownloadResponseBody extends ResponseBody {

    private ResponseBody responseBody;
    private DownloadProgressListener progressListener;
    private BufferedSource bufferedSource;

    public DownloadResponseBody(ResponseBody responseBody, DownloadProgressListener progressListener) {
        this.responseBody = responseBody;
        this.progressListener = progressListener;
    }


    //类型
    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    //长度
    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }


    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(getSource(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source getSource(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                // read() returns the number of bytes read, or -1 if this source is exhausted.
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                if (null != progressListener) {
                    progressListener.update(totalBytesRead, responseBody.contentLength(), bytesRead == -1);
                }
                return bytesRead;
            }
        };
    }

}
