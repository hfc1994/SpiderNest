package com.hfc.spidernest.utils;

import com.hfc.spidernest.bean.HttpResponseEntity;
import org.apache.http.Header;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user-hfc on 2019/1/6.
 */
public class HttpClientUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class.getName());

    private List<Header> headers = null;

    private RequestConfig config = RequestConfig.custom()
            .setConnectTimeout(15000)
            .setSocketTimeout(15000)
            .build();

    public HttpResponseEntity doGet(String url) {
        CloseableHttpClient client= HttpClientBuilder.create()
                .setDefaultRequestConfig(config)
                .setDefaultHeaders(this.getHeaders())
                .build();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpGet);
            return HttpResponseEntity.getRespEntity(response);
        } catch (IOException e) {
            LOGGER.error("httpGet执行失败：" + e.getMessage(), e);
            return HttpResponseEntity.getRespEntity(0, null);
        } finally {
            if (null != response) {
                try {
                    response.close();
                } catch (IOException ioe) {
                    LOGGER.error("httpclient关闭异常：" + ioe.getMessage(), ioe);
                }
            }
        }
    }

    public List<Header> getHeaders() {
        if (headers != null && headers.size() > 0) {
            return headers;
        } else {
            headers = new ArrayList<>();
            headers.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.92 Safari/537.36"));
            headers.add(new BasicHeader("Accept-Encoding", "gzip, deflate"));
            headers.add(new BasicHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8"));
            headers.add(new BasicHeader("Connection", "keep-alive"));
//            headers.add(new BasicHeader("Cache-Control", "max-age=0"));
            headers.add(new BasicHeader("Upgrade-Insecure-Requests", "1"));
            headers.add(new BasicHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8"));
        }

        return headers;
    }
}
