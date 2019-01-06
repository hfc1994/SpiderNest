package com.hfc.spidernest.utils;

import com.hfc.spidernest.bean.HttpResponseEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by user-hfc on 2019/1/6.
 */
public class HttpClientPool {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientPool.class.getName());

    // @todo 测试这个方法
    public HttpResponseEntity doGet(String url) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpGet);
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
}
