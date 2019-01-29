package com.hfc.spidernest.utils.decoder.douban;

import com.hfc.spidernest.entity.douban.Member;
import com.hfc.spidernest.utils.Constant;
import com.hfc.spidernest.utils.decoder.HtmlDecoder;
import com.hfc.spidernest.utils.exception.NotSuitableClassException;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            Element intro = doc.selectFirst("#content .aside #intro_display");
            // 简介
            String strIntro = intro.text();
            if (StringUtils.isNotBlank(strIntro)) {
                member.setIntroToUtf8(strIntro.trim());
            }
            Element basicInfo = doc.selectFirst("#content .aside .basic-info");
            // 头像url
            String avatarUrl = basicInfo.getElementsByTag("img").first().attr("src");
            member.setAvatarUrl(avatarUrl);
            // 长居地
            String position = basicInfo.getElementsByTag("a").first().text();
            position = position == null ? "" : position.trim();
            member.setPlace(position);
            // id 和加入时间
            String idAndTime = basicInfo.getElementsByClass("pl").first().text();
            Matcher matcher = Constant.ID_AND_DATE_REGEX.matcher(idAndTime);
            if (matcher.find()) {
                String id = matcher.group(1);
                String strDate = matcher.group(2).trim();
                member.setUserId(id);
                member.setJoinDate(LocalDate.parse(strDate, Constant.DEFAULT_DATE_FORMATTER));
            }

            Element aside = doc.selectFirst("#content .aside");
            // 关注数
            String follow = aside.selectFirst("#friend .pl").getElementsByTag("a").text();
            Matcher followMatcher = Constant.NUMBER_REGEX.matcher(follow);
            if (followMatcher.find()) {
                member.setFollow(Integer.parseInt(followMatcher.group(0)));
            }
            // 粉丝数
            String follower = aside.selectFirst(".rev-link").getElementsByTag("a").text();
            Matcher followerMatcher = Constant.NUMBER_REGEX.matcher(follower);
            if (followerMatcher.find()) {
                member.setFollower(Integer.parseInt(followerMatcher.group(0)));
            }
            // 常去的小组数
            String groups = aside.getElementById("group").getElementsByTag("h2").text();
            Matcher groupdMatcher = Constant.NUMBER_REGEX.matcher(groups);
            if (groupdMatcher.find()) {
                member.setFollowGroup(Integer.parseInt(groupdMatcher.group(0)));
            }
            // 页面url，使用id拼接
            if (StringUtils.isNotBlank(member.getUserId())) {
                member.setUserUrl("https://www.douban.com/people/" + member.getUserId() + "/");
            }

            Element article = doc.selectFirst("#content .article");
            Element info = article.selectFirst(".info");
            // 昵称
            String nickname = info.getElementsByTag("h1").text();
            if (StringUtils.isNotBlank(nickname)) {
                nickname = nickname.trim();
                nickname = nickname.substring(0, nickname.indexOf(" "));
                member.setNicknameToUtf8(nickname.trim());
            }
            // 个性签名
            String signature = info.getElementById("display").text();
            if (StringUtils.isNotBlank(signature)) {
                member.setSignatureToUtf8(signature.trim());
            }

            Element movieNode = article.selectFirst("#movie .pl");
            if (null != movieNode) {
                Elements movieInfo = movieNode.getElementsByTag("a");
                for (Element e : movieInfo) {
                    int status;
                    if ((status = hobbyStatusJudge(Constant.DOING_STATUS, e)) != -1) {
                        member.setWatching(status);
                    } else if ((status = hobbyStatusJudge(Constant.WISH_STATUS, e)) != -1) {
                        member.setWishWatch(status);
                    } else if ((status = hobbyStatusJudge(Constant.DONE_STATUS, e)) != -1) {
                        member.setWatched(status);
                    }
                }
            }

            Element musicNode = article.selectFirst("#music .pl");
            if (null != musicNode) {
                Elements musicInfo = movieNode.getElementsByTag("a");
                for (Element e : musicInfo) {
                    int status;
                    if ((status = hobbyStatusJudge(Constant.WISH_STATUS, e)) != -1) {
                        member.setWishListen(status);
                    } else if ((status = hobbyStatusJudge(Constant.DONE_STATUS, e)) != -1) {
                        member.setListened(status);
                    }
                }
            }

            Element bookNode = article.selectFirst("#book .pl");
            if (null != bookNode) {
                Elements bookInfo = bookNode.getElementsByTag("a");
                for (Element e : bookInfo) {
                    int status;
                    if ((status = hobbyStatusJudge(Constant.DOING_STATUS, e)) != -1) {
                        member.setReading(status);
                    } else if ((status = hobbyStatusJudge(Constant.WISH_STATUS, e)) != -1) {
                        member.setWishRead(status);
                    } else if ((status = hobbyStatusJudge(Constant.DONE_STATUS, e)) != -1) {
                        member.setReaded(status);
                    }
                }
            }
            return member;
        } else {
            throw new NotSuitableClassException(Document.class, object);
        }
    }

    private Integer hobbyStatusJudge (Pattern pattern, Element e) {
        Matcher hobbyStatus = pattern.matcher(e.text());
        if (hobbyStatus.find()) {
            return Integer.parseInt(hobbyStatus.group(1));
        } else {
            return -1;
        }
    }
}
