package net.minggao.cms.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class Article {
    private Long articleId;

    private String upsubhead;

    private String downsubhead;
    @NotNull(message = "新闻标题不能为空")
    private String newssubhead;

    private Boolean stylestatus;

    public Boolean getStylestatus() {
        return stylestatus;
    }

    public void setStylestatus(Boolean stylestatus) {
        this.stylestatus = stylestatus;
    }

    @NotNull(message = "发布时间不能为空")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publishdate;

    private String informationsource;

    private String simpleintroduce;

    private String keywords;

    private String image;

    private Integer isopenlink;

    private String linkaddress;

    private Integer isstick;

    private Integer isrecommend;

    private Integer refusecopy;

    @NotNull(message = "排序码不能为空")
    @Min(0)
    @Max(9999)
    private Integer sortedcode;

    private Long belongChan;

    private Long siteId;

    private Long createUser;

    private Date createTime;

    private Integer auditStatus;

    private String author;

    private String issuer;

    private String articleContentId;

    private String isMutiy;

    public String getIsMutiy() {
        return isMutiy;
    }

    public void setIsMutiy(String isMutiy) {
        this.isMutiy = isMutiy;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getUpsubhead() {
        return upsubhead;
    }

    public void setUpsubhead(String upsubhead) {
        this.upsubhead = upsubhead == null ? null : upsubhead.trim();
    }

    public String getDownsubhead() {
        return downsubhead;
    }

    public void setDownsubhead(String downsubhead) {
        this.downsubhead = downsubhead == null ? null : downsubhead.trim();
    }

    public String getNewssubhead() {
        return newssubhead;
    }

    public void setNewssubhead(String newssubhead) {
        this.newssubhead = newssubhead == null ? null : newssubhead.trim();
    }

    public Date getPublishdate() {
        return publishdate;
    }

    public void setPublishdate(Date publishdate) {
        this.publishdate = publishdate;
    }

    public String getInformationsource() {
        return informationsource;
    }

    public void setInformationsource(String informationsource) {
        this.informationsource = informationsource == null ? null : informationsource.trim();
    }

    public String getSimpleintroduce() {
        return simpleintroduce;
    }

    public void setSimpleintroduce(String simpleintroduce) {
        this.simpleintroduce = simpleintroduce == null ? null : simpleintroduce.trim();
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords == null ? null : keywords.trim();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public Integer getIsopenlink() {
        return isopenlink;
    }

    public void setIsopenlink(Integer isopenlink) {
        this.isopenlink = isopenlink;
    }

    public String getLinkaddress() {
        return linkaddress;
    }

    public void setLinkaddress(String linkaddress) {
        this.linkaddress = linkaddress == null ? null : linkaddress.trim();
    }

    public Integer getIsstick() {
        return isstick;
    }

    public void setIsstick(Integer isstick) {
        this.isstick = isstick;
    }

    public Integer getIsrecommend() {
        return isrecommend;
    }

    public void setIsrecommend(Integer isrecommend) {
        this.isrecommend = isrecommend;
    }

    public Integer getRefusecopy() {
        return refusecopy;
    }

    public void setRefusecopy(Integer refusecopy) {
        this.refusecopy = refusecopy;
    }

    public Integer getSortedcode() {
        return sortedcode;
    }

    public void setSortedcode(Integer sortedcode) {
        this.sortedcode = sortedcode;
    }

    public Long getBelongChan() {
        return belongChan;
    }

    public void setBelongChan(Long belongChan) {
        this.belongChan = belongChan;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer == null ? null : issuer.trim();
    }

    public String getArticleContentId() {
        return articleContentId;
    }

    public void setArticleContentId(String articleContentId) {
        this.articleContentId = articleContentId == null ? null : articleContentId.trim();
    }
}