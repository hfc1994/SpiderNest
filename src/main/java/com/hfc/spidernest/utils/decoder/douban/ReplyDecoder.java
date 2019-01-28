package com.hfc.spidernest.utils.decoder.douban;

import com.hfc.spidernest.entity.douban.Reply;
import com.hfc.spidernest.utils.Constant;
import com.hfc.spidernest.utils.decoder.HtmlDecoder;
import com.hfc.spidernest.utils.exception.NotSuitableClassException;
import com.hfc.spidernest.utils.httpclients.ExtractUtil;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * Created by user-hfc on 2019/1/14.
 */
public class ReplyDecoder implements HtmlDecoder<Reply> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReplyDecoder.class.getName());

    /**
     * 存储相关回复下,回复id和对应的赞数
     * 使用ThreadLocal可以保持线程安全
     */
    private ThreadLocal<Map<String, Integer>> likesDataTL = new ThreadLocal<>();

    /**
     * 解析全部的回复节点
     * @param object 包含全部回复节点的父节点
     * @return 解析后的全部回复（未包含主题帖url）
     * @throws NotSuitableClassException 传入的类型不合适，需要的是Document
     */
    @Override
    public List<Reply> decodeAllNode(Object object) throws NotSuitableClassException{
        if (object instanceof Document) {
            List<Reply> replyList = new ArrayList<>(8);

            Document doc = (Document) object;
            // 主题帖的内容
            Reply topicContent = decodeTopicContent(doc);
            replyList.add(topicContent);
            // 楼主的id
            String lzId = topicContent.getReplierId();
            Element commentBody = doc.getElementById("comments");
            Elements comments = commentBody.children();
            for (Element comment : comments) {
                Reply reply = this.decodeNode(comment);
                if (null != reply) {
                    if (lzId.equals(reply.getReplierId())) {
                        reply.setTopicer(true);
                    } else {
                        reply.setTopicer(false);
                    }
                    replyList.add(reply);
                }
            }
            return replyList;
        } else {
            throw new NotSuitableClassException(Document.class, object);
        }
    }

    /**
     * 解析单个回复节点，指定主题帖下面的1楼由于结构不一样不会被解析
     * @param object 单个回复节点
     * @return 单条回复（未包含主题帖url和是否是楼主）
     * @throws NotSuitableClassException 传入的类型不合适，需要是Element
     */
    @Override
    public Reply decodeNode(Object object) throws NotSuitableClassException {
        Reply reply = null;
        if (object instanceof Element) {
            Element comment = (Element) object;
            reply = new Reply();

            // 回复的id
            String replyId = comment.attr("id");
            reply.setReplyId(replyId);

            Element replyDoc = comment.select(".reply-doc").first();
            Element head4 = replyDoc.getElementsByTag("h4").first();
            Element replier = head4.children().first();
            // 回复者的id
            String replierId = ExtractUtil.extractUserId(replier.attr("href"));
            reply.setReplierId(replierId);
            // 回复者昵称
            reply.setReplierNameToUtf8(replier.text());
            // 回复时间
            String replyTime = head4.selectFirst(".pubtime").text();
            reply.setReplyTime(LocalDateTime.parse(replyTime, Constant.DEFAULT_DATE_TIME_FORMATTER));
            // 回复来源，1为app，0为web
            Elements vias = replyDoc.getElementsByClass("via");
            reply.setReplySrc(vias.size() != 0);
            // 回复内容
            String content = replyDoc.selectFirst("p").text();
            reply.setReplyTextToUtf8(content);
            // 引用内容
            Elements replyQuoteNode = replyDoc.select(".reply-quote");
            if (replyQuoteNode.size() != 0) {
                String quoteText = replyQuoteNode.select(".all").first().text();
                Element quoteAuthor = replyQuoteNode.select(".pubdate a").first();
                String quoteAuthorName = quoteAuthor.text();
                String quoteAuthorId = ExtractUtil.extractUserId(quoteAuthor.attr("href"));
                reply.setQuoteTextToUtf8(quoteText + "---" + quoteAuthorName);
                reply.setQuoteUserid(quoteAuthorId);
            }

            // 回复的点赞数是页内的js执行之后才会显示的
            // 使用extractLikesToMap来弥补这个问题
            if (null != this.likesDataTL.get()) {
                Integer likes = this.likesDataTL.get().get(reply.getReplyId());
                likes = likes == null ? 0 : likes;
                reply.setLikes(likes);
            } else {
                LOGGER.error("please pay attention to [extractLikesToMap] method");
                reply.setLikes(0);
            }

        } else {
            throw new NotSuitableClassException(Element.class, object);
        }
        return reply;
    }

    /**
     * 解析主题帖的内容
     * @param doc 整个网页
     * @return 主题帖的内容
     */
    public Reply decodeTopicContent(Document doc) {
        Reply reply = new Reply();
        Element firstReply = doc.selectFirst(".topic-content .topic-doc");
        Element author = firstReply.selectFirst(".from a");
        String authorName = author.text();
        // 回复者昵称
        reply.setReplierNameToUtf8(authorName);
        String authorId = ExtractUtil.extractUserId(author.attr("href"));
        // 回复者id
        reply.setReplierId(authorId);
        String replyTime = firstReply.selectFirst(".color-green").text();
        // 回复时间
        reply.setReplyTime(LocalDateTime.parse(replyTime, Constant.DEFAULT_DATE_TIME_FORMATTER));
        Element richtext = firstReply.selectFirst("#link-report .topic-richtext");
        // 回复的内容
        reply.setReplyText(buildReplyContent(richtext));
        Elements vias = firstReply.select("#link-report_group .via");
        // 回复来源
        reply.setReplySrc(vias.size() != 0);

        // "0"表示是1楼
        reply.setQuoteUserid("0");
        // 是楼主
        reply.setTopicer(true);
        // 点赞数
        // 登录之后才会有具体点赞数，否则只有一个“赞”字
        // @todo---登录后获取赞数
        Element like = doc.selectFirst(".topic-content .action-react .react-btn");
        String strLike = like.text();
        Matcher likeMatcher = Constant.REPLY_LIKE_REGEX.matcher(strLike);
        if (likeMatcher.find()) {
            reply.setLikes(Integer.parseInt(likeMatcher.group(3)));
        } else {
            reply.setLikes(0);
        }
        return reply;
    }

    /**
     * 提取主题帖的具体内容
     * 主题帖的内容是一个富文本，不清楚会有哪些情况，所以此处仅做简单处理
     * @param richtext 主题帖内容的富文本节点
     * @return 主题帖里提取到的内容
     */
    private String buildReplyContent(Element richtext) {
        StringBuilder sb = new StringBuilder();
        Elements contentList = richtext.children();
        for (Element content : contentList) {
            if ("p".equalsIgnoreCase(content.tagName())) {
                if (StringUtils.isNotBlank(content.text())) {
                    sb.append(content.text().trim());
                }
            } else if ("h2".equalsIgnoreCase(content.tagName())) {
                if (StringUtils.isNotBlank(content.text())) {
                    sb.append("\n").append(content.text().trim()).append("\n");
                }
            } else if ("div".equalsIgnoreCase(content.tagName())) {
                if (content.classNames().contains("image-container")) {
                    Elements imgs = content.getElementsByTag("img");
                    for (Element img : imgs) {
                        sb.append("<img>").append(img.attr("src")).append("</img>");
                    }
                } else if (StringUtils.isNotBlank(content.text())) {
                    sb.append(content.text().trim());
                }
            }
        }

        return sb.toString();
    }

    /**
     * 回复的点赞数是由html里面的一段js执行更改html内容生成的
     * 因此需要识别到这段js，然后提取json数据
     * @param html 整个html文本
     */
    public void extractLikesToMap(String html) {
        Map<String, Integer> likesData = this.likesDataTL.get();
        // 每次调用相当于是解析新的页面了
        if (null == likesData) {
            likesData = new HashMap<>(8);
            this.likesDataTL.set(likesData);
        } else {
            likesData.clear();
        }

        Matcher jsonMatcher = Constant.LIKES_JSON_REGEX.matcher(html);
        if (jsonMatcher.find()) {
            String strJson = jsonMatcher.group(2);
            Matcher detailMatcher = Constant.LIKES_REGEX.matcher(strJson);
            String replyId = null;
            Integer likes = null;
            while (detailMatcher.find()) {
                replyId = detailMatcher.group(1);
                likes = Integer.parseInt(detailMatcher.group(3));
                likesData.put(replyId, likes);
            }
            LOGGER.info("the count of reply is " + likesData.size());
        } else {
            LOGGER.info("no json data found");
        }
    }
}
