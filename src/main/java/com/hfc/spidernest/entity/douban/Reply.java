package com.hfc.spidernest.entity.douban;

import com.hfc.spidernest.utils.StringUtil;

import java.time.LocalDateTime;

public class Reply {
    private Integer id;

    private String topicId;

    private String replierId;

    private String replierName;

    private Boolean replySrc;

    private String quoteUserid; // 被引用的回复者昵称

    private Boolean topicer;    // 是否是楼主

    private Integer likes;

    private LocalDateTime replyTime;

    private LocalDateTime createTime;

    private String replyText;

    private String quoteText;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId == null ? null : topicId.trim();
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

    public String getReplierNameFromUtf8() {
        return StringUtil.decodeFromUtf8(replierName);
    }

    public void setReplierName(String replierName) {
        this.replierName = replierName == null ? null : replierName.trim();
    }

    public void setReplierNameToUtf8(String replierName) {
        this.replierName = replierName == null ? null : StringUtil.encodeToUtf8(replierName.trim());
    }

    public Boolean getReplySrc() {
        return replySrc;
    }

    public void setReplySrc(Boolean replySrc) {
        this.replySrc = replySrc;
    }

    public String getQuoteUserid() {
        return quoteUserid;
    }

    public void setQuoteUserid(String quoteUserid) {
        this.quoteUserid = quoteUserid == null ? null : quoteUserid.trim();
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

    public String getReplyTextFromUtf8() {
        return StringUtil.decodeFromUtf8(replyText);
    }

    public void setReplyText(String replyText) {
        this.replyText = replyText == null ? null : replyText.trim();
    }

    public void setReplyTextToUtf8(String replyText) {
        this.replyText = replyText == null ? null : StringUtil.encodeToUtf8(replyText.trim());
    }

    public String getQuoteText() {
        return quoteText;
    }

    public String getQuoteTextFromUtf8() {
        return StringUtil.decodeFromUtf8(quoteText);
    }

    public void setQuoteText(String quoteText) {
        this.quoteText = quoteText == null ? null : quoteText.trim();
    }

    public void setQuoteTextToUtf8(String quoteText) {
        this.quoteText = quoteText == null ? null : StringUtil.encodeToUtf8(quoteText.trim());
    }

    @Override
    public String toString() {
        return "在" + getReplyTime() + "的时候，[" + getReplierNameFromUtf8() + "]（" + getReplierId() + "）"
                + "回复了一句“" + getReplyTextFromUtf8() + "”，收获点赞数" + getLikes();
    }
}