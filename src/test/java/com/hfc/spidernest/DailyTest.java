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
        doubanReplySpider();
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
        Pattern ulPattern = Pattern.compile("(<ul class=\"topic-reply\" id=\"comments\">)([\\s\\S]*?)(</ul>)");
        Pattern liPattern = Pattern.compile("(<li class=\"clearfix comment-item\" id=\")([0-9]*)([\\s\\S]*?)(</li>)");
        Pattern likePattern = Pattern.compile("([\\u8d5e])(.*)([0-9]+)(\\))");
        Matcher ulMatcher = ulPattern.matcher(tmp);
        System.out.println("---测试结果---");
        if (ulMatcher.find()) {
            String lis = ulMatcher.group(2);
            Matcher liMatcher = liPattern.matcher(lis);
            while (liMatcher.find()) {
                String li = liMatcher.group(0);
                String replyId = liMatcher.group(2);
                Matcher likeMatcher = likePattern.matcher(li);
                if (likeMatcher.find()) {
                    String likes = likeMatcher.group(3);
                    System.out.println("--- [" + replyId + "] 的赞数是 = [" + likes + "] ---");
                } else {
                    System.out.println("---赞数没找到---");
                }
                System.out.println("********************************");
            }
        } else {
            System.out.println("---没找到---");
        }
        System.out.println("---over---");


    }

    private static String htmlContent = "cxzczxczx<ul class=\"topic-reply\" id=\"comments\">\n" +
            "zxczxcxz<li class=\"clearfix comment-item\" id=\"1783889542\" data-cid=\"1783889542\">\n" +
            "    <div class=\"user-face\">\n" +
            "        <a href=\"https://www.douban.com/people/177875673/\"><img class=\"pil\" src=\"https://img3.doubanio.com/icon/u177875673-3.jpg\" alt=\"角球\"></a>\n" +
            "    </div>\n" +
            "    <div class=\"reply-doc content\" style=\"padding-left:0px;\">\n" +
            "        <div class=\"bg-img-green\">\n" +
            "          <h4>\n" +
            "              <a href=\"https://www.douban.com/people/177875673/\" class=\"\">角球</a>\n" +
            "              <span class=\"pubtime\">2019-01-15 10:49:15</span>\n" +
            "          </h4>\n" +
            "        </div>\n" +
            "        <p class=\"\">被催婚</p>\n" +
            "        <div class=\"operation_div\" id=\"177875673\">\n" +
            "            <a href=\"https://www.douban.com/group/topic/131344244/?cid=1783889542#last\" class=\"lnk-reply\">回应</a>\n" +
            "            <a rel=\"nofollow\" href=\"javascript:void(0);\" class=\"comment-vote lnk-fav\">赞 (1)</a>\n" +
            "            <div class=\"operation-more\" style=\"display: none;\">\n" +
            "                <a rel=\"nofollow\" href=\"javascript:void(0);\" data-cid=\"1783889542\" class=\"lnk-delete-comment\" title=\"真的要删除角球的发言?\">删除</a>\n" +
            "            <div class=\"comment-report\" style=\"visibility: hidden;\"><a rel=\"nofollow\" href=\"javascript:void(0)\">举报</a></div></div>\n" +
            "        </div>\n" +
            "        <!-- via  -->\n" +
            "         <span class=\"via\">来自 <a href=\"/doubanapp/app?channel=from_group_topic\" target=\"_blank\" title=\"豆瓣App\">豆瓣App</a></span>\n" +
            "    </div>\n" +
            "</li>\n" +
            "cvcxvcxv<li class=\"clearfix comment-item\" id=\"1783889943\" data-cid=\"1783889943\">\n" +
            "    <div class=\"user-face\">\n" +
            "        <a href=\"https://www.douban.com/people/1127666/\"><img class=\"pil\" src=\"https://img1.doubanio.com/icon/u1127666-37.jpg\" alt=\"机器喵\"></a>\n" +
            "    </div>\n" +
            "    <div class=\"reply-doc content\" style=\"padding-left:0px;\">\n" +
            "        <div class=\"bg-img-green\">\n" +
            "          <h4>\n" +
            "              <a href=\"https://www.douban.com/people/1127666/\" class=\"\">机器喵</a>\n" +
            "              <span class=\"pubtime\">2019-01-15 10:49:49</span>\n" +
            "          </h4>\n" +
            "        </div>\n" +
            "        <p class=\"\">不懂什么题</p>\n" +
            "        <div class=\"operation_div\" id=\"1127666\">\n" +
            "            <a href=\"https://www.douban.com/group/topic/131344244/?cid=1783889943#last\" class=\"lnk-reply\">回应</a>\n" +
            "            <a rel=\"nofollow\" href=\"javascript:void(0);\" class=\"comment-vote lnk-fav\">赞</a>\n" +
            "            <div class=\"operation-more\">\n" +
            "                <a rel=\"nofollow\" href=\"javascript:void(0);\" data-cid=\"1783889943\" class=\"lnk-delete-comment\" title=\"真的要删除机器喵的发言?\">删除</a>\n" +
            "            <div class=\"comment-report\"><a rel=\"nofollow\" href=\"javascript:void(0)\">举报</a></div></div>\n" +
            "        </div>\n" +
            "        <!-- via  -->\n" +
            "    </div>\n" +
            "</li>\n" +
            "gfhgfhgf<li class=\"clearfix comment-item\" id=\"1783890127\" data-cid=\"1783890127\">\n" +
            "    <div class=\"user-face\">\n" +
            "        <a href=\"https://www.douban.com/people/179550320/\"><img class=\"pil\" src=\"https://img3.doubanio.com/icon/u179550320-13.jpg\" alt=\"一一\"></a>\n" +
            "    </div>\n" +
            "    <div class=\"reply-doc content\" style=\"padding-left:0px;\">\n" +
            "        <div class=\"bg-img-green\">\n" +
            "          <h4>\n" +
            "              <a href=\"https://www.douban.com/people/179550320/\" class=\"\">一一</a>\n" +
            "              <span class=\"pubtime\">2019-01-15 10:50:04</span>\n" +
            "          </h4>\n" +
            "        </div>\n" +
            "        <div class=\"reply-quote\">\n" +
            "            <span class=\"short\">不懂什么题</span>\n" +
            "            <span class=\"all\">不懂什么题</span>\n" +
            "        <a href=\"#\" class=\"toggle-reply\">\n" +
            "        </a><span class=\"pubdate\"><a href=\"https://www.douban.com/people/1127666/\">机器喵</a></span></div>\n" +
            "        <p class=\"\">你还小，不需要懂</p>\n" +
            "        <div class=\"operation_div\" id=\"179550320\">\n" +
            "            <a href=\"https://www.douban.com/group/topic/131344244/?cid=1783890127#last\" class=\"lnk-reply\">回应</a>\n" +
            "            <a rel=\"nofollow\" href=\"javascript:void(0);\" class=\"comment-vote lnk-fav\">赞</a>\n" +
            "            <div class=\"operation-more\">\n" +
            "                <a rel=\"nofollow\" href=\"javascript:void(0);\" data-cid=\"1783890127\" class=\"lnk-delete-comment\" title=\"真的要删除一一的发言?\">删除</a>\n" +
            "            <div class=\"comment-report\"><a rel=\"nofollow\" href=\"javascript:void(0)\">举报</a></div></div>\n" +
            "        </div>\n" +
            "        <!-- via  -->\n" +
            "         <span class=\"via\">来自 <a href=\"/doubanapp/app?channel=from_group_topic\" target=\"_blank\" title=\"豆瓣App\">豆瓣App</a></span>\n" +
            "    </div>\n" +
            "</li>\n" +
            "</ul><ul><li></li></ul>";
}
