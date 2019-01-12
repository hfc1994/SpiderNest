package com.hfc.spidernest.utils.decoder.douban;

import com.hfc.spidernest.entity.douban.Topic;
import com.hfc.spidernest.utils.decoder.HtmlDecoder;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by user-hfc on 2019/1/12.
 */
public class TopicDecoder implements HtmlDecoder<Topic> {

    private static Logger LOGGER = LoggerFactory.getLogger(TopicDecoder.class.getName());

    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public List<Topic> decode(Object object) {


        if (object instanceof Document) {
            List<Topic> topicList = new ArrayList<>(8);

            Document doc = (Document) object;
            Element tbody = doc.select(".article .olt tbody").get(0);
            Elements elements = tbody.children();
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
                                // @todo 使用LocalDateTime获取时间，最后再转Date
//                                try {
//
//                                    topic.setModifyTime(d);
//                                } catch (ParseException e) {
//                                    e.printStackTrace();
//                                }
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

            return null;
        } else {
            return null;
        }
    }
}
