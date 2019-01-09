package com.hfc.spidernest;

import com.hfc.spidernest.utils.HttpClientUtil;
import com.hfc.spidernest.utils.httpclients.DoubanClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


/**
 * Created by user-hfc on 2019/1/8.
 */
public class DailyTest {

    public static void main(String... args) {
        DoubanClient doubanClient = new DoubanClient();
        CloseableHttpClient client = doubanClient.getClient();

        String url = "https://www.douban.com/group/hangzhou/discussion?start=0";
        String document = HttpClientUtil.doGet(client, url);

//        System.out.println(document);
        if (null != document) {
            Document doc = Jsoup.parse(document, "utf-8");
            // @todo DOM操作
            // @todo 生成实体类
        }
    }
}
