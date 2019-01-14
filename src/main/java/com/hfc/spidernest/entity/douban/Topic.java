package com.hfc.spidernest.entity.douban;

import com.hfc.spidernest.utils.StringUtil;

import java.time.LocalDateTime;

/**
 * 豆瓣小组的主题bean
 *
 * 对可能出现emoji表情的字段增加了进行utf-8编码的getter/setter
 * 普通的getter/setter用于读写数据库时被mybatis调用
 */
public class Topic {
    private Integer id;

    private String title;

    private String url;

    private String authorId;

    private String authorName;

    private Integer replyCount;

    private LocalDateTime modifyTime;

    private LocalDateTime createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getTitleFromUtf8() {
        return StringUtil.decodeFromUtf8(title);
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public void setTitleToUtf8(String title) {
        this.title = title == null ? null : StringUtil.encodeToUtf8(title.trim());
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId == null ? null : authorId.trim();
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getAuthorNameFromUtf8() {
        return StringUtil.decodeFromUtf8(authorName);
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName == null ? null : authorName.trim();
    }

    public void setAuthorNameToUtf8(String authorName) {
        this.authorName = authorName == null ? null : StringUtil.encodeToUtf8(authorName.trim());
    }

    public Integer getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }

    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "在" + getModifyTime() + "的时候，有人顶了[" + getAuthorNameFromUtf8() + "]（id=" + getAuthorId()
                + "）的帖子，这个帖子是《" + getTitleFromUtf8() + "》（" + getUrl() + "），回复总数是" + getReplyCount();
    }
}