package com.hfc.spidernest.utils;

import org.apache.http.client.config.RequestConfig;

import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

/**
 * Created by user-hfc on 2019/1/8.
 */
public class Constant {

    public final static RequestConfig DEFAULT_HTTP_CONFIG = RequestConfig.custom()
            .setConnectTimeout(5000)
            .setSocketTimeout(5000)
            .build();

    // 随机数种子
    public final static long RANDOM_SEED = 56871274128L;

    // 当需要的时间获取不到时所使用的默认时间
    public final static String DEFAULT_DATE_TIME = "1970-01-01 00:00:00";

    // 默认的时间格式
    public final static DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // 时间格式的正则表达式，对应的格式类似于12-12 12:12
    public final static Pattern TIME_REGEX = Pattern.compile("^[0-9]{2}-[0-9]{2}\\s[0-9]{2}:[0-9]{2}$");
    // 日期格式的正则表达式，对应的格式类似于2018-12-12
    public final static Pattern DATE_REGEX = Pattern.compile("^[0-9]{4}-[0-9]{2}-[0-9]{2}$");
    // 用户id的正则表达式，用户唯一ID可能是数字也可能是字符串，类似https://www.douban.com/people/666/
    public final static Pattern USER_URL_REGEX = Pattern.compile("(.*)(people/)([^/]*)(/)?");
    // 主题帖的正则表达式，类似https://www.douban.com/group/topic/666/
    public final static Pattern TOPIC_URL_REGEX = Pattern.compile("(.*)(topic/)([^/]*)(/)?");
    // 在主题帖的html内获取ul和li内容的正则表达式
    public final static Pattern REPLY_UL_REGEX = Pattern.compile("(<ul class=\"topic-reply\" id=\"comments\">)([\\s\\S]*?)(</ul>)");
    public final static Pattern REPLY_LI_REGEX = Pattern.compile("(<li class=\"clearfix comment-item\" id=\")([0-9]*)([\\s\\S]*?)(</li>)");
    // \u8d5e是“赞”的Unicode
    public final static Pattern REPLY_LIKE_REGEX = Pattern.compile("([\\u8d5e])(.*)([0-9]+)(\\))");

    // 用户代理头
    public final static String[] USER_AGENT_ARRAY = new String[] {
            "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.92 Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.110 Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2623.110 Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2623.110 Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2623.110 Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2623.110 Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2623.110 Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2623.110 Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2623.110 Safari/537.36",
            "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:50.0) Gecko/20100101 Firefox/50.0",
            "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.115 Safari/537.36",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.75 Safari/537.36",
            "Mozilla/5.0 (Windows NT 6.3; rv:36.0) Gecko/20100101 Firefox/36.04",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:52.0) Gecko/20100101 Firefox/52.0",
            "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; Trident/4.0; InfoPath.2; SV1; .NET CLR 2.0.50727; WOW64)",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2227.0 Safari/537.36",
            "Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko",
            "Mozilla/4.0 (compatible; MSIE 6.0b; Windows NT 5.1)",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:24.0) Gecko/20100101 Firefox/24.0",
            "Mozilla/5.0 (X11; Linux i686; rv:40.0) Gecko/20100101 Firefox/40.0",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.157 Safari/537.36",
            "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0)",
            "Opera/9.80 (X11; Linux i686; U; ru) Presto/2.8.131 Version/11.11",
            "Mozilla/5.0 (iPad; CPU OS 6_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10A5355d Safari/8536.25",
            "Mozilla/5.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; GTB7.4; InfoPath.2; SV1; .NET CLR 3.3.69573; WOW64; en-US)",
            "Mozilla/5.0 (X11; Ubuntu; Linux i686; rv:15.0) Gecko/20100101 Firefox/15.0.1"
    };
}
