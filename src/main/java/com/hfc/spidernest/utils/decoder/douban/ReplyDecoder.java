package com.hfc.spidernest.utils.decoder.douban;

import com.hfc.spidernest.entity.douban.Reply;
import com.hfc.spidernest.utils.Constant;
import com.hfc.spidernest.utils.decoder.HtmlDecoder;
import com.hfc.spidernest.utils.exception.NotSuitableClassException;
import com.hfc.spidernest.utils.httpclients.ExtractUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user-hfc on 2019/1/14.
 */
public class ReplyDecoder implements HtmlDecoder<Reply> {

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
            Reply firstReply = decodeTopicFirstReply(doc);
            replyList.add(firstReply);
            // 楼主的id
            String lzId = firstReply.getReplierId();
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

            Element replyDoc = comment.select(".reply-doc").first();
            Element head4 = replyDoc.getElementsByTag("h4").first();
            Element replier = head4.children().first();
            // 回复者id
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

            // 怀疑是jsoup的解析bug，无法获取一个Node下面的多个TextNode，只能获取到第一个
//            Element likes = replyDoc.select(".operation_div .comment-vote").first();
//            String strLikes = likes.text();
        } else {
            throw new NotSuitableClassException(Element.class, object);
        }
        return reply;
    }

    /**
     * 解析主题帖中的1楼
     * @param doc 整个网页
     * @return 1楼
     */
    public Reply decodeTopicFirstReply(Document doc) {
        Reply reply = new Reply();
        Element firstReply = doc.selectFirst(".topic-content .topic-doc");
        Element author = firstReply.selectFirst(".from a");
        String authorName = author.text();
        reply.setReplierNameToUtf8(authorName);
        String authorId = ExtractUtil.extractUserId(author.attr("href"));
        reply.setReplierId(authorId);
        String replyTime = firstReply.selectFirst(".color-green").text();
        reply.setReplyTime(LocalDateTime.parse(replyTime, Constant.DEFAULT_DATE_TIME_FORMATTER));
        // @todo replyContent里面需要处理图片问题
        String replyContent = firstReply.selectFirst("#link-report .topic-richtext").text().trim();
        Elements vias = firstReply.select("#link-report_group .via");
        reply.setReplySrc(vias.size() != 0);

        // "0"表示是1楼
        reply.setQuoteUserid("0");
        // 是楼主
        reply.setTopicer(true);
        return reply;
    }
}
