package com.hfc.spidernest.entity.douban;

import java.time.LocalDateTime;

public class Reply {
    private Integer id;

    private String topicUrl;

    private String replierId;

    private String replierName;

    private Boolean replySrc;

    private Boolean topicer;

    private Integer likes;

    private LocalDateTime replyTime;

    private LocalDateTime createTime;

    private String replyText;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTopicUrl() {
        return topicUrl;
    }

    public void setTopicUrl(String topicUrl) {
        this.topicUrl = topicUrl == null ? null : topicUrl.trim();
    }

    public String getReplierId() {
        return replierId;
    }

    public void setReplierId(String replierId) {
        this.replierId = replierId == null ? null : replierId.trim();
    }

    public String getReplierName() {
        return replierName;
    }

    public void setReplierName(String replierName) {
        this.replierName = replierName == null ? null : replierName.trim();
    }

    public Boolean getReplySrc() {
        return replySrc;
    }

    public void setReplySrc(Boolean replySrc) {
        this.replySrc = replySrc;
    }

    public Boolean getTopicer() {
        return topicer;
    }

    public void setTopicer(Boolean topicer) {
        this.topicer = topicer;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public LocalDateTime getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(LocalDateTime replyTime) {
        this.replyTime = replyTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getReplyText() {
        return replyText;
    }

    public void setReplyText(String replyText) {
        this.replyText = replyText == null ? null : replyText.trim();
    }
}