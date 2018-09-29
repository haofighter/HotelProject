package com.net;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * 扩展OkHttp的请求体，实现上传时的进度提示
 *
 */
public final class FileResponseBody extends ResponseBody {

    /**
     * 实际请求体
     */
    private ResponseBody mResponseBody;
    /**
     * 下载回调接口
     */
    private ProgressListener mCallback;
    /**
     * 下载回调接口
     */
    private FileSubscribe fileSubscribe;
    /**
     * BufferedSource
     */
    private BufferedSource mBufferedSource;

    public FileResponseBody(ResponseBody body, ProgressListener callback) {
        mCallback=callback;
        mResponseBody=body;
    }
    public FileResponseBody(ResponseBody body, FileSubscribe callback) {
        fileSubscribe=callback;
        mResponseBody=body;
    }

    @Override
    public BufferedSource source() {
        if (mBufferedSource == null) {
            mBufferedSource = Okio.buffer(source(mResponseBody.source()));
        }
        return mBufferedSource;
    }
    @Override
    public long contentLength() {
        return mResponseBody.contentLength();
    }
    @Override
    public MediaType contentType() {
        return mResponseBody.contentType();
    }
    /**
     * 回调进度接口
     * @param source
     * @return Source
     */
    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;
            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                if (mCallback!=null)
                mCallback.onProgress(totalBytesRead, mResponseBody.contentLength(), bytesRead == -1);
                if (fileSubscribe!=null){
                    fileSubscribe.onLoading(mResponseBody.contentLength(),totalBytesRead);
                }
                return bytesRead;
            }
        };
    }
}