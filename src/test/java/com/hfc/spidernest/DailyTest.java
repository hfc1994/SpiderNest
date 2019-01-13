package com.hfc.spidernest;

import com.hfc.spidernest.entity.douban.Topic;
import com.hfc.spidernest.utils.HttpClientUtil;
import com.hfc.spidernest.utils.exception.NotSuitableClassException;
import com.hfc.spidernest.utils.httpclients.DoubanClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by user-hfc on 2019/1/8.
 */
public class DailyTest {

    public static void main(String... args) {
//        try {
//            normalTest();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            System.out.println("-------");
//            e.printStackTrace();
//        }
        patternTest();
//        doubanTopicSpider();
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

//        System.out.println(document);
        if (null != document) {
            Document doc = Jsoup.parse(document, "utf-8");

            Element tbody = doc.select(".article .olt tbody").get(0);
            Elements elements = tbody.children();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            for (Element e1 : elements) {
                if (e1.classNames().contains("th")) {
                    continue;
                }
                Elements content = e1.children();
                Topic topic = new Topic();
                for (Element e2 : content) {
                    // 没有class，返回的是""，居然不是null...
                    String strClass = e2.attr("class");

                    switch (strClass) {
                        case "":
                            if (e2.attributes().size() == 1) {
                                topic.setAuthorId(e2.child(0).attr("href"));
                                topic.setAuthorName(e2.child(0).text());
                            } else {
                                topic.setReplyCount("".equals(e2.text()) ? 0 : Integer.parseInt(e2.text()));
                            }
                            break;
                        case "title":
                            topic.setUrl(e2.child(0).attr("href"));
                            topic.setTitle(e2.child(0).text());
                            break;
                        case "time":
                            String lastReplyTime = e2.text();
                            if (lastReplyTime.indexOf("-") == 2) {
                                lastReplyTime = "2019-" + lastReplyTime + ":00";
                                LocalDateTime ldt = LocalDateTime.from(dtf.parse(lastReplyTime));
                                topic.setModifyTime(ldt);
                            }
                            break;
                    }
                }
                StringBuilder sb = new StringBuilder("在");
                sb.append(topic.getModifyTime()).append("的时候，有人给").append(topic.getAuthorName())
                        .append("(").append(topic.getAuthorId()).append(")顶了一下贴，帖子叫")
                        .append(topic.getTitle()).append("(").append(topic.getUrl()).append("),")
                        .append("此时的总回复数是").append(topic.getReplyCount()).append("。");
                System.out.println("-----------");
                System.out.println(sb.toString());
                System.out.println("-----------");
            }
            System.out.println("帖子总数是" + elements.size());
        }
    }
}
