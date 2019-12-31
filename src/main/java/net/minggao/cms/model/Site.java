package net.minggao.cms.model;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 网站信息的实体类 用于存放网站的主体信息
 */
public class Site {
    private Long siteId;

    @NotBlank(message = "网站名称不能为空")
    @Length(max =50,message = "网站的名称不能超过50字")
    private String siteName;

    @NotBlank(message = "网站的关键词不能为空")
    @Length(max =200,message = "网站的关键词不能超过200字")
    private String siteKeyword;

    //@NotBlank(message = "网站描述不能为空")
    @Length(max=2000,message = "网站描述的字数不能超过两千字")
    private String siteDescribe;

    @NotBlank(message = "网站地址不能为空")
    @Length(max=100,message = "网站地址信息不能超过一百字")
    private String address;

    @NotBlank(message = "网站电话不能为空")
    @Length(max=50,message = "电话信息长度不能超过50个")
    private String telephone;

    @NotBlank(message = "网站邮箱不能为空")
    @Length(max=50,message = "网站邮箱长度不能超过50个")
    private String email;

    @NotBlank(message = "版权信息不能为空")
    @Length(max=50,message = "版权信息长度不能超过50个")
    private String copMessage;

    @NotBlank(message = "网站logo不能为空")
    private String siteLogo;

    //@NotBlank(message = "宣传片标题不能为空")
    @Length(max=100,message = "宣传片标题长度不能超过50个")
    private String trailerTitle;

    //@NotBlank(message = "宣传片内容不能为空")
    private String trailer;

    //@NotBlank(message = "宣传图片不能为空")
    private String trailerCover;

    //@NotBlank(message = "手机宣传图片不能为空")
    private String mobiletCover;

    @NotBlank(message = "站点目录不能为空")
    private String siteCatalog;

    private Integer siteStatus;

    private String createUser;

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    private Date createTime;

    private Long issynchro;

    public Long getIssynchro() {
        return issynchro;
    }

    public void setIssynchro(Long issynchro) {
        this.issynchro = issynchro;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName == null ? null : siteName.trim();
    }

    public String getSiteKeyword() {
        return siteKeyword;
    }

    public void setSiteKeyword(String siteKeyword) {
        this.siteKeyword = siteKeyword == null ? null : siteKeyword.trim();
    }

    public String getSiteDescribe() {
        return siteDescribe;
    }

    public void setSiteDescribe(String siteDescribe) {
        this.siteDescribe = siteDescribe == null ? null : siteDescribe.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getCopMessage() {
        return copMessage;
    }

    public void setCopMessage(String copMessage) {
        this.copMessage = copMessage == null ? null : copMessage.trim();
    }

    public String getSiteLogo() {
        return siteLogo;
    }

    public void setSiteLogo(String siteLogo) {
        this.siteLogo = siteLogo == null ? null : siteLogo.trim();
    }

    public String getTrailerTitle() {
        return trailerTitle;
    }

    public void setTrailerTitle(String trailerTitle) {
        this.trailerTitle = trailerTitle == null ? null : trailerTitle.trim();
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer == null ? null : trailer.trim();
    }

    public String getTrailerCover() {
        return trailerCover;
    }

    public void setTrailerCover(String trailerCover) {
        this.trailerCover = trailerCover == null ? null : trailerCover.trim();
    }

    public String getMobiletCover() {
        return mobiletCover;
    }

    public void setMobiletCover(String mobiletCover) {
        this.mobiletCover = mobiletCover == null ? null : mobiletCover.trim();
    }

    public String getSiteCatalog() {
        return siteCatalog;
    }

    public void setSiteCatalog(String siteCatalog) {
        this.siteCatalog = siteCatalog == null ? null : siteCatalog.trim();
    }

    public Integer getSiteStatus() {
        return siteStatus;
    }

    public void setSiteStatus(Integer siteStatus) {
        this.siteStatus = siteStatus;
    }



    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}