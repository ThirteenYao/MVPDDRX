package com.thirteenyao.rrdd.base.net.convertor;


import com.thirteenyao.rrdd.base.util.LogUtils;

import java.io.EOFException;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by zhao on 2016/7/5.
 */
public class HttpLogInterceptor implements Interceptor {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        long startNs = System.nanoTime();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            LogUtils.e("<-- HTTP FAILED: " + e);
            throw e;
        }
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        String url = response.request().url().toString();

        RequestBody requestBody = request.body();
        Buffer buffer = new Buffer();
        requestBody.writeTo(buffer);

        Charset charset = UTF8;
        MediaType contentType = requestBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(UTF8);
        }

        if (isPlaintext(buffer)) {
            String urlPath = url + "?" + buffer.readString(charset);
            try {
                url = URLDecoder.decode(urlPath, "UTF-8");
            } catch (Exception e) {
                LogUtils.e("URL解码失败，可能是图片链接");
            }

            LogUtils.e(url);

//            LogUtils.e("<-- code:" + response.code()
//                    + " message:" + response.message()
//                    + " 请求方式:" + request.method()
//                    + " 请求大小:" + requestBody.contentLength() + "-byte body"
//                    + " (响应时间:" + tookMs + "ms" + ')');

        } else {
            LogUtils.e("<-- code:" + response.code()
                    + " message:" + response.message()
                    + " 请求方式:" + request.method()
                    + " 请求大小:" + requestBody.contentLength() + "-byte body omitted"
                    + " (响应时间:" + tookMs + "ms" + ')');
        }
        LogUtils.e("<-- " + response.code() + ' ' + response.message() + ' '+ " (" + tookMs + "ms)" +" body:" + response.body());
        return response;
    }

    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     */
    static boolean isPlaintext(Buffer buffer) throws EOFException {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

}

