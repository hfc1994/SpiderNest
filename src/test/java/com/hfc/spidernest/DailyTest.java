package com.hfc.spidernest;

import com.hfc.spidernest.entity.douban.Reply;
import com.hfc.spidernest.entity.douban.Topic;
import com.hfc.spidernest.utils.HttpClientUtil;
import com.hfc.spidernest.utils.decoder.douban.ReplyDecoder;
import com.hfc.spidernest.utils.decoder.douban.TopicDecoder;
import com.hfc.spidernest.utils.exception.NotSuitableClassException;
import com.hfc.spidernest.utils.httpclients.DoubanClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by user-hfc on 2019/1/8.
 */
public class DailyTest {

    public static void main(String... args) {
//        String url = "https://www.douban.com/people/174837097/";
//        Pattern userIdPattern = Pattern.compile("^(.*)(people/)([^/]*)(/)?$");
//        Matcher m = userIdPattern.matcher(url);
//        System.out.println(m.find());
//        System.out.println(m.group(3));

//        try {
//            normalTest();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            System.out.println("-------");
//            e.printStackTrace();
//        }
//        patternTest();
//        doubanTopicSpider();
        doubanReplySpider();
    }

    public static void normalTest() throws Exception {
        String str = "www";
        throw new NotSuitableClassException(Integer.class, str);
    }

    public static void patternTest() {
        // 01-13 16:48
//        String regex1 = "^[0-9]{2}-[0-9]{2}\\s[0-9]{2}:[0-9]{2}$";
//        Pattern p1 = Pattern.compile(regex1);
//        String date1 = "01-13 16:48";
//        boolean ret1 = p1.matcher(date1).matches();
//        System.out.println("时间匹配结果是---" + ret1);
//        // 2019-01-13
//        String regex2 = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$";
//        Pattern p2 = Pattern.compile(regex2);
//        String date2 = "2019-01-13";
//        boolean ret2 = p2.matcher(date2).matches();
//        System.out.println("日期匹配结果是---" + ret2);
        // https://www.douban.com/people/99999999/
        String regex3 = "^([^0-9]+/)([0-9]+)(/*)";
        Pattern p3 = Pattern.compile(regex3);
        String url = "https://www.douban.com/people/99999999/";
        Matcher matcher = p3.matcher(url);
        if (matcher.find()) {
            System.out.println("url匹配结果是---" + matcher.group(2));
        } else {
            System.out.println("url匹配异常");
        }
    }

    // 豆瓣话题页面的爬虫测试
    public static void doubanTopicSpider() {
        DoubanClient doubanClient = new DoubanClient();
        CloseableHttpClient client = doubanClient.getClient();

        String url = "https://www.douban.com/group/hangzhou/discussion?start=0";
        String document = HttpClientUtil.doGet(client, url);

        TopicDecoder decoder = new TopicDecoder();
        if (null != document) {
            Document doc = Jsoup.parse(document, "utf-8");

            List<Topic> list = decoder.decode(doc);

            for (Topic t : list) {
                System.out.println("------");
                System.out.println(t);
            }
        }
    }

    // 豆瓣话题页面的爬虫测试
    public static void doubanReplySpider() {
        DoubanClient doubanClient = new DoubanClient();
        CloseableHttpClient client = doubanClient.getClient();

        String url = "https://www.douban.com/group/topic/131344244/?start=0";
        String document = HttpClientUtil.doGet(client, url);

        ReplyDecoder replyDecoder = new ReplyDecoder();
        if (null != document) {
            Document doc = Jsoup.parse(document, "utf-8");

            List<Reply> list = replyDecoder.decode(doc);

            for (Reply t : list) {
                System.out.println("------");
                System.out.println(t);
            }
        }
    }
}
