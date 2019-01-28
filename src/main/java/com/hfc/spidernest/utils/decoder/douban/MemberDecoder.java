package com.hfc.spidernest.utils.decoder.douban;

import com.hfc.spidernest.entity.douban.Member;
import com.hfc.spidernest.utils.decoder.HtmlDecoder;
import com.hfc.spidernest.utils.exception.NotSuitableClassException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user-hfc on 2019/1/21.
 */
public class MemberDecoder implements HtmlDecoder<Member> {

    /**
     * 解析成员页面
     * @param object 成员页面节点
     * @return 单个成员对象
     * @throws NotSuitableClassException 传入的类型不合适，需要的是Document
     */
    @Override
    public List<Member> decodeAllNode(Object object) throws NotSuitableClassException {
        if (object instanceof Document) {
            List<Member> members = new ArrayList<>(1);
            members.add(decodeNode(object));
            return members;
        } else {
            throw new NotSuitableClassException(Document.class, object);
        }

    }

    /**
     * 解析成员页面
     * @param object 成员页面节点
     * @return 单个成员对象
     * @throws NotSuitableClassException 传入的类型不合适，需要的是Document
     */
    @Override
    public Member decodeNode(Object object) throws NotSuitableClassException {
        if (object instanceof  Document) {
            Member member = new Member();

            Document doc = (Document) object;
            Element intro = doc.selectFirst("#content .aside #edit_intro");
            // 简介
            String strIntro = intro.text();
            System.out.println("intro --- " + strIntro);
            Element basicInfo = doc.selectFirst("#content .aside .basic-info");
            // 头像url
            String avatarUrl = basicInfo.getElementsByTag("img").first().attr("src");
            // 长居地
            String position = basicInfo.getElementsByTag("a").first().text();
            position = position == null ? "" : position.trim();
            // id 和加入时间
            String idAndTime = basicInfo.getElementsByClass("pl").first().text();
            System.out.println("idAndTime --- " + idAndTime);

            Element aside = doc.selectFirst("#content .aside");
            // 关注数
            String follow = aside.selectFirst("#friend .pl").getElementsByTag("a").text();
            // 粉丝数
            String follower = aside.selectFirst(".rev-link").getElementsByTag("a").text();
            // 常去的小组数
            String groups = aside.getElementById("group").getElementsByTag("h2").text();
            // 页面url，使用id拼接


            Element article = doc.selectFirst("#content .article");
            Element info = article.selectFirst(".info");
            // 昵称
            String nickname = info.getElementsByTag("h1").text();
            // 个性签名
            String signature = info.getElementById("display").text();
            Elements movieInfo = article.selectFirst("#movie .pl").getElementsByTag("a");
            String watching = movieInfo.get(0).text();
            String wantToWatch = movieInfo.get(1).text();
            String watched = movieInfo.get(2).text();

            Elements musicInfo = article.selectFirst("#music .pl").getElementsByTag("a");
            String wangToListen = musicInfo.get(0).text();
            String listened = musicInfo.get(1).text();

            Elements bookInfo = article.selectFirst("#book .pl").getElementsByTag("a");
            String reading = bookInfo.get(0).text();
            String wangToRead = bookInfo.get(1).text();
            String readed = bookInfo.get(2).text();

            System.out.println();

        } else {
            throw new NotSuitableClassException(Document.class, object);
        }
        return null;
    }
}
