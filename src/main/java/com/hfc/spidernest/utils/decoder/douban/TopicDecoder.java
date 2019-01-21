package com.hfc.spidernest.utils.decoder.douban;

import com.hfc.spidernest.entity.douban.Topic;
import com.hfc.spidernest.utils.Constant;
import com.hfc.spidernest.utils.decoder.HtmlDecoder;
import com.hfc.spidernest.utils.exception.NotSuitableClassException;
import com.hfc.spidernest.utils.httpclients.ExtractUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user-hfc on 2019/1/12.
 */
public class TopicDecoder implements HtmlDecoder<Topic> {

    /**
     * 解析全部的主题帖节点
     * @param object 包含全部主题帖节点的父节点
     * @return 解析后的全部主题帖
     * @throws NotSuitableClassException 传入的类型不合适，需要的是Document
     */
    @Override
    public List<Topic> decodeAllNode(Object object) throws NotSuitableClassException {
        if (object instanceof Document) {
            List<Topic> topicList = new ArrayList<>(8);

            Document doc = (Document) object;
            Element tbody = doc.select(".article .olt tbody").get(0);
            Elements trs = tbody.children();
            for (Element tr : trs) {
                if (tr.classNames().contains("th")) {
                    continue;
                }

                Topic topic = this.decodeNode(tr);
                if (null != topic) {
                    topicList.add(topic);
                }
            }
            return topicList;
        } else {
            throw new NotSuitableClassException(Document.class, object);
        }
    }

    /**
     * 解析单个主题帖节点
     * @param object 单个主题帖节点
     * @return 单个主题帖
     * @throws NotSuitableClassException 传入的类型不合适，需要的是Element
     */
    @Override
    public Topic decodeNode(Object object) throws NotSuitableClassException {
        Topic topic = null;
        if (object instanceof Element) {
            Element tr = (Element) object;
            Elements tds = tr.children();
            topic = new Topic();
            for (Element td : tds) {
                // 没有class，返回的是""，居然不是null...
                String strClass = td.attr("class");
                switch (strClass) {
                    case "":
                        if (td.attributes().size() == 1) {
                            String userId = ExtractUtil.extractUserId(td.child(0).attr("href"));
                            topic.setAuthorId(userId);
                            topic.setAuthorNameToUtf8(td.child(0).text());
                        } else {
                            topic.setReplyCount("".equals(td.text()) ? 0 : Integer.parseInt(td.text()));
                        }
                        break;
                    case "title":
                        topic.setUrl(td.child(0).attr("href"));
                        topic.setTitleToUtf8(td.child(0).text());
                        break;
                    case "time":
                        // 今年的最后回应格式为01-13 16:19,去年以前的格式为2018-12-31
                        // 为了统一格式，去年以前的最后会被追加" 23:59:59"
                        String lastReplyTime = td.text();
                        if (Constant.TIME_REGEX.matcher(lastReplyTime).matches()) {
                            lastReplyTime = "2019-" + lastReplyTime + ":00";
                        } else if (Constant.DATE_REGEX.matcher(lastReplyTime).matches()) {
                            lastReplyTime = lastReplyTime + " 23:59:59";
                        } else {
                            lastReplyTime = Constant.DEFAULT_DATE_TIME;
                        }
                        topic.setModifyTime(LocalDateTime.parse(lastReplyTime, Constant.DEFAULT_DATE_TIME_FORMATTER));
                        break;
                    default:
                }
            }
        } else {
            throw new NotSuitableClassException(Element.class, object);
        }
        return topic;
    }
}
