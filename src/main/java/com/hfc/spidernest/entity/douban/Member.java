package com.hfc.spidernest.entity.douban;

import java.util.Date;

public class Member {
    private Integer id;

    private String userId;

    private String nickname;

    private String signature;

    private Date joinDate;

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

    private Date createTime;

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

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature == null ? null : signature.trim();
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro == null ? null : intro.trim();
    }
}