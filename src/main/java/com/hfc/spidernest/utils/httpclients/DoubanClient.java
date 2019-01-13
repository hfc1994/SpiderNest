package com.hfc.spidernest.utils.httpclients;

import com.hfc.spidernest.utils.Constant;
import org.apache.http.Header;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by user-hfc on 2019/1/8.
 *
 * 豆瓣的HttpClient生成器
 */
public class DoubanClient implements Clients {

    private List<Header> headers = null;
    private Random random = new Random(Constant.RANDOM_SEED);

    @Override
    public CloseableHttpClient getClient() {
        return HttpClientBuilder.create()
                .setDefaultRequestConfig(Constant.DEFAULT_HTTP_CONFIG)
                .setDefaultHeaders(this.buildHeaders())
                .build();
    }

    @Override
    public List<Header> buildHeaders() {
        if (headers == null ) {
            headers = new ArrayList<>(6);
            headers.add(new BasicHeader("Accept-Encoding", "gzip, deflate, br"));
            headers.add(new BasicHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8"));
            headers.add(new BasicHeader("Connection", "keep-alive"));
            headers.add(new BasicHeader("Upgrade-Insecure-Requests", "1"));
            headers.add(new BasicHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8"));
            headers.add(new BasicHeader("User-Agent", Constant.USER_AGENT_ARRAY[random.nextInt(Constant.USER_AGENT_ARRAY.length)]));
        }

        // 每次都是用新的用户代理头
        headers.set(5, new BasicHeader("User-Agent", Constant.USER_AGENT_ARRAY[random.nextInt(Constant.USER_AGENT_ARRAY.length)]));
        return headers;
    }
}
