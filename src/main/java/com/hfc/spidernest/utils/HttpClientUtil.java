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

    // try-with-resource语法
    public static String doGet(CloseableHttpClient client, String url) {
        HttpGet httpGet = new HttpGet(url);
        try (CloseableHttpResponse response = client.execute(httpGet)) {
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            LOGGER.error("httpGet执行失败：" + e.getMessage(), e);
            return null;
        }
    }

    // try-with-resource语法
    public static String doPost(CloseableHttpClient client, String url) {
        HttpPost httpPost = new HttpPost(url);
        try (CloseableHttpResponse response = client.execute(httpPost)) {
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            LOGGER.error("httpPost执行失败：" + e.getMessage(), e);
            return null;
        }
    }
}
