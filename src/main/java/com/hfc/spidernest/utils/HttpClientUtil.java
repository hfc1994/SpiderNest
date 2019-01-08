package com.hfc.spidernest.utils;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by user-hfc on 2019/1/6.
 */
public class HttpClientUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class.getName());

    // @todo 试试是用try with resource的方式
    public String doGet(CloseableHttpClient client, String url) {
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpGet);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            LOGGER.error("httpGet执行失败：" + e.getMessage(), e);
            return null;
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

    public String doPost(CloseableHttpClient client, String url) {
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpPost);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            LOGGER.error("httpPost执行失败：" + e.getMessage(), e);
            return null;
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
