package com.hfc.spidernest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by user-hfc on 2019/1/7.
 */
public class HttpClientTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientTest.class.getName());

    private static String doubanUrl = "http://www.pm25.com/";

    public static void main(String... args) throws IOException, InterruptedException {
//        HttpClientUtil client = new HttpClientUtil();
//        HttpResponseEntity entity = client.doGet(doubanUrl);
//
//        System.out.println(entity.getResponse().getProtocolVersion());
//        Header[] headers = entity.getResponse().getAllHeaders();
//        for (int i=0; i< headers.length; i++) {
//            System.out.println(headers[i]);
//        }
//
//        String content = EntityUtils.toString(entity.getResponse().getEntity(), "utf-8");
//        System.out.println(content);

//        StringBuilder sb = new StringBuilder();
//        BufferedReader br = null;
//        br = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
//        String line = null;
//        while ((line = br.readLine()) != null) {
//            sb.append(line);
//        }
//
//        System.out.println(sb.toString());
//
//        TimeUnit.SECONDS.sleep(10);
//        inputStream.close();
//        br.close();
    }
}
