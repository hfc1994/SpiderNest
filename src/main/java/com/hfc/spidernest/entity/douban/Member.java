package com.hfc.spidernest.entity.douban;

import com.hfc.spidernest.utils.StringUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 豆瓣小组的用户bean
 *
 * 对可能出现emoji表情的字段增加了进行utf-8编码的getter/setter
 * 普通的getter/setter用于读写数据库时被mybatis调用
 */
public class Member {
    private Integer id;

    private String userId;

    private String nickname;

    private String signature;

    private LocalDate joinDate;

    private String place;

    private Integer follow;

    private Integer follower;

    private Integer followGroup;

    private String userUrl;

    private String avatarUrl;

    private Integer watching;

    private Integer wishWatch;

    private Integer watched;

    private Integer wishListen;

    private Integer listened;

    private Integer reading;

    private Integer wishRead;

    private Integer readed;

    private LocalDateTime createTime;

    private String intro;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public String getNicknameFromUtf8() {
        return StringUtil.decodeFromUtf8(nickname);
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public void setNicknameToUtf8(String nickname) {
        this.nickname = nickname == null ? null : StringUtil.encodeToUtf8(nickname.trim());
    }

    public String getSignature() {
        return signature;
    }

    public String getSignatureFromUtf8() {
        return StringUtil.decodeFromUtf8(signature);
    }

    public void setSignature(String signature) {
        this.signature = signature == null ? null : signature.trim();
    }

    public void setSignatureToUtf8(String signature) {
        this.signature = signature == null ? null : StringUtil.encodeToUtf8(signature.trim());
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place == null ? null : place.trim();
    }

    public Integer getFollow() {
        return follow;
    }

    public void setFollow(Integer follow) {
        this.follow = follow;
    }

    public Integer getFollower() {
        return follower;
    }

    public void setFollower(Integer follower) {
        this.follower = follower;
    }

    public Integer getFollowGroup() {
        return followGroup;
    }

    public void setFollowGroup(Integer followGroup) {
        this.followGroup = followGroup;
    }

    public String getUserUrl() {
        return userUrl;
    }

    public void setUserUrl(String userUrl) {
        this.userUrl = userUrl == null ? null : userUrl.trim();
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl == null ? null : avatarUrl.trim();
    }

    public Integer getWatching() {
        return watching;
    }

    public void setWatching(Integer watching) {
        this.watching = watching;
    }

    public Integer getWishWatch() {
        return wishWatch;
    }

    public void setWishWatch(Integer wishWatch) {
        this.wishWatch = wishWatch;
    }

    public Integer getWatched() {
        return watched;
    }

    public void setWatched(Integer watched) {
        this.watched = watched;
    }

    public Integer getWishListen() {
        return wishListen;
    }

    public void setWishListen(Integer wishListen) {
        this.wishListen = wishListen;
    }

    public Integer getListened() {
        return listened;
    }

    public void setListened(Integer listened) {
        this.listened = listened;
    }

    public Integer getReading() {
        return reading;
    }

    public void setReading(Integer reading) {
        this.reading = reading;
    }

    public Integer getWishRead() {
        return wishRead;
    }

    public void setWishRead(Integer wishRead) {
        this.wishRead = wishRead;
    }

    public Integer getReaded() {
        return readed;
    }

    public void setReaded(Integer readed) {
        this.readed = readed;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getIntro() {
        return intro;
    }

    public String getIntroFromUtf8() {
        return StringUtil.decodeFromUtf8(intro);
    }

    public void setIntro(String intro) {
        this.intro = intro == null ? null : intro.trim();
    }

    public void setIntroToUtf8(String intro) {
        this.intro = intro == null ? null : StringUtil.encodeToUtf8(intro.trim());
    }

    @Override
    public String toString() {
        return getNickname() + "（" + getUserId() + "）关注了" + getFollow() + "个人，被" + getFollower() + "个人"
                + "看过" + getWatched() + "部影视，想看" + getWishWatch() + "部影视，正在看" + getWatching() + "部影视";
    }
}