package com.hfc.spidernest;

import com.hfc.spidernest.entity.douban.Member;
import com.hfc.spidernest.entity.douban.Reply;
import com.hfc.spidernest.entity.douban.Topic;
import com.hfc.spidernest.utils.HttpClientUtil;
import com.hfc.spidernest.utils.decoder.douban.MemberDecoder;
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
//        patternTest();
//        doubanTopicSpider();
//        doubanReplySpider();
        doubanMemberSpider();
//        decodeHtml();
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
            Document doc = Jsoup.parse(document);

            List<Topic> list = decoder.decodeAllNode(doc);

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
//        String url = "https://www.douban.com/group/topic/131734270/?start=0";   // 有长文
        String document = HttpClientUtil.doGet(client, url);

        ReplyDecoder replyDecoder = new ReplyDecoder();
        if (null != document) {
            replyDecoder.extractLikesToMap(document);
            Document doc = Jsoup.parse(document);

            List<Reply> list = replyDecoder.decodeAllNode(doc);

            for (Reply t : list) {
                System.out.println("------");
                System.out.println(t);
            }
        }
    }

    // 豆瓣成员页面的爬虫测试
    public static void doubanMemberSpider() {
        DoubanClient doubanClient = new DoubanClient();
        CloseableHttpClient client = doubanClient.getClient();

        String url = "https://www.douban.com/people/qijiuzhiyue/";
        String document = HttpClientUtil.doGet(client, url);

        MemberDecoder memberDecoder = new MemberDecoder();
        if (null != document) {
            Document doc = Jsoup.parse(document);

            Member member = memberDecoder.decodeNode(doc);

            System.out.println(member);
        }
    }

    public static void decodeHtml() {
        String tmp = htmlContent;
        Pattern jsonPattern = Pattern.compile("(commentsVotes = ')([\\s\\S]*?)(',)");
        Pattern detailPattern = Pattern.compile("\"c([0-9]+)(\":)([0-9]+),");
        Matcher jsonMatcher = jsonPattern.matcher(tmp);
        System.out.println("---测试结果---");
        if (jsonMatcher.find()) {
            String strJson = jsonMatcher.group(2);
            System.out.println(strJson);
            Matcher detailMatcher = detailPattern.matcher(strJson);
            while (detailMatcher.find()) {
                String id = detailMatcher.group(1);
                String likes = detailMatcher.group(3);
                System.out.println("---[ " + id + " ] 获得 [ " + likes + " ]个赞");
            }
        } else {
            System.out.println("---没找到---");
        }
        System.out.println("---over---");


    }

    private static String htmlContent = "Do(function() {\n" +
            "  // comment fav num\n" +
            "  var commentsVotes = '{\"c1783899179\":7,\"c1783900258\":1,\"c1783918998\":4,\"c1783892904\":1,\"c1783899812\":1,\"c1784047711\":1,\"c1784025398\":1,\"c1783889542\":1}',\n" +
            "      votes = $.parseJSON(commentsVotes),\n" +
            "      voteId;\n" +
            "  for (vote in votes) {\n" +
            "      voteId = vote.slice(1);\n" +
            "      $('li[data-cid=\"' + voteId + '\"]').find('.comment-vote').append(' ('+ votes[vote] +')');\n" +
            "  }\n" +
            "}";
}
