package com.hfc.spidernest.utils.decoder.douban;

import com.hfc.spidernest.entity.douban.Topic;
import com.hfc.spidernest.utils.Constant;
import com.hfc.spidernest.utils.StringUtil;
import com.hfc.spidernest.utils.decoder.HtmlDecoder;
import com.hfc.spidernest.utils.exception.NotSuitableClassException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by user-hfc on 2019/1/12.
 */
public class TopicDecoder implements HtmlDecoder<Topic> {

    private static Logger LOGGER = LoggerFactory.getLogger(TopicDecoder.class.getName());

    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private Pattern timePattern = Pattern.compile("^[0-9]{2}-[0-9]{2}\\s[0-9]{2}:[0-9]{2}$");
    private Pattern datePattern = Pattern.compile("^[0-9]{4}-[0-9]{2}-[0-9]{2}$");
    // 用户唯一ID可能是数字也可能是字符串
    private Pattern userIdPattern = Pattern.compile("^(.*)(people/)([^/]*)(/)?$");

    @Override
    public List<Topic> decode(Object object) throws NotSuitableClassException{
        if (object instanceof Document) {
            List<Topic> topicList = new ArrayList<>(8);

            Document doc = (Document) object;
            Element tbody = doc.select(".article .olt tbody").get(0);
            Elements trs = tbody.children();
            for (Element tr : trs) {
                if (tr.classNames().contains("th")) {
                    continue;
                }

                Elements tds = tr.children();
                Topic topic = new Topic();
                for (Element td : tds) {
                    // 没有class，返回的是""，居然不是null...
                    String strClass = td.attr("class");
                    switch (strClass) {
                        case "":
                            if (td.attributes().size() == 1) {
                                Matcher m = userIdPattern.matcher(td.child(0).attr("href"));
                                if (m.find()) {
                                    topic.setAuthorId(m.group(3));
                                }
                                // @todo 把编解码emoji的工作放到实体类里面，新增特殊的方法
                                topic.setAuthorName(StringUtil.encodeToUtf8(td.child(0).text()));
                            } else {
                                topic.setReplyCount("".equals(td.text()) ? 0 : Integer.parseInt(td.text()));
                            }
                            break;
                        case "title":
                            topic.setUrl(td.child(0).attr("href"));
                            topic.setTitle(StringUtil.encodeToUtf8(td.child(0).text()));
                            break;
                        case "time":
                            // 今年的最后回应格式为01-13 16:19,去年以前的格式为2018-12-31
                            // 为了统一格式，去年以前的最后会被追加" 23:59:59"
                            String lastReplyTime = td.text();
                            if (timePattern.matcher(lastReplyTime).matches()) {
                                lastReplyTime = "2019-" + lastReplyTime + ":00";
                            } else if (datePattern.matcher(lastReplyTime).matches()) {
                                lastReplyTime = lastReplyTime + " 23:59:59";
                            } else {
                                lastReplyTime = Constant.DEFAULT_DATE_TIME;
                            }
                            topic.setModifyTime(LocalDateTime.parse(lastReplyTime, dtf));
                            break;
                        default:
                    }
                }
                topicList.add(topic);
            }
            return topicList;
        } else {
            throw new NotSuitableClassException(Document.class, object);
        }
    }
}