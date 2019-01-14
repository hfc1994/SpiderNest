package com.hfc.spidernest.entity.douban;

import com.hfc.spidernest.utils.StringUtil;

import java.time.LocalDateTime;

/**
 * 豆瓣小组的主题里面的单条回复bean
 *
 * 对可能出现emoji表情的字段增加了进行utf-8编码的getter/setter
 * 普通的getter/setter用于读写数据库时被mybatis调用
 */
public class Reply {
    private Integer id;

    private String topicUrl;

    private String replierId;

    private String replierName;

    private Boolean replySrc;

    private Boolean topicer;    // 是否是楼主

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

    @Override
    public String toString() {
        return "在" + getReplyTime() + "的时候，[" + getReplierNameFromUtf8() + "]（" + getReplierId() + "）"
                + "回复了一句“" + getReplyTextFromUtf8() + "”，收获点赞数" + getLikes();
    }
}