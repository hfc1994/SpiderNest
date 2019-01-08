package com.hfc.spidernest.utils.httpclients;

import org.apache.http.Header;
import org.apache.http.impl.client.CloseableHttpClient;

import java.util.List;

/**
 * Created by user-hfc on 2019/1/8.
 */
public interface Clients {

    // 获取HttpClint实例
    CloseableHttpClient getClient();

    // 构建请求头
    List<Header> buildHeaders();
}
