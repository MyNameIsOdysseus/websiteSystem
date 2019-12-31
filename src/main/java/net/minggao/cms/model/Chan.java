package net.minggao.cms.model;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class Chan {
    private Long chanId;

    @NotNull(message = "栏目名称不能为空")
    @NotBlank(message = "栏目名称不能为空")
    @Length(max = 100,message = "栏目的名称不能超过100字")
    private String chanName;

    private Long belongChan;

    @NotNull(message = "栏目类型不能为空")
    private String chanType;

    private String chanImage;

    private String chanBanner;

    private Integer openlink;

    private String linkAddress;
    @NotNull(message = "打开方式必须选择一个")
    private Integer openType;
    @NotNull(message = "排序码不能为空")
    @Min(0)
    @Max(9999)
    private Integer sortCode;

    @NotNull(message = "栏目模板不能为空")
    private Long chanTemplate;

    @NotNull(message = "信息模板不能为空")
    private Long infoTemplate;

    private Long processId;

    private String chanLevelCode;

    private Integer chanLevel;


    private String chanCode;

    private Long siteId;

    private Long createUser;

    private Date createTime;

    private Integer chanStatus;

    private String chanTemplatename;

    private String infoTemplatename;

    private String belongChanname;

    @NotNull(message = "栏目描述不能为空")
    private String chenDisc;

    public Long getChanId() {
        return chanId;
    }

    public void setChanId(Long chanId) {
        this.chanId = chanId;
    }

    public String getChanName() {
        return chanName;
    }

    public void setChanName(String chanName) {
        this.chanName = chanName == null ? null : chanName.trim();
    }

    public Long getBelongChan() {
        return belongChan;
    }

    public void setBelongChan(Long belongChan) {
        this.belongChan = belongChan;
    }

    public String getChanType() {
        return chanType;
    }

    public void setChanType(String chanType) {
        this.chanType = chanType == null ? null : chanType.trim();
    }

    public String getChanImage() {
        return chanImage;
    }

    public void setChanImage(String chanImage) {
        this.chanImage = chanImage == null ? null : chanImage.trim();
    }

    public String getChanBanner() {
        return chanBanner;
    }

    public void setChanBanner(String chanBanner) {
        this.chanBanner = chanBanner == null ? null : chanBanner.trim();
    }

    public Integer getOpenlink() {
        return openlink;
    }

    public void setOpenlink(Integer openlink) {
        this.openlink = openlink;
    }

    public String getLinkAddress() {
        return linkAddress;
    }

    public void setLinkAddress(String linkAddress) {
        this.linkAddress = linkAddress == null ? null : linkAddress.trim();
    }

    public Integer getOpenType() {
        return openType;
    }

    public void setOpenType(Integer openType) {
        this.openType = openType;
    }

    public Integer getSortCode() {
        return sortCode;
    }

    public void setSortCode(Integer sortCode) {
        this.sortCode = sortCode;
    }

    public Long getChanTemplate() {
        return chanTemplate;
    }

    public void setChanTemplate(Long chanTemplate) {
        this.chanTemplate = chanTemplate;
    }

    public Long getInfoTemplate() {
        return infoTemplate;
    }

    public void setInfoTemplate(Long infoTemplate) {
        this.infoTemplate = infoTemplate;
    }

    public Long getProcessId() {
        return processId;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public String getChanLevelCode() {
        return chanLevelCode;
    }

    public void setChanLevelCode(String chanLevelCode) {
        this.chanLevelCode = chanLevelCode == null ? null : chanLevelCode.trim();
    }

    public Integer getChanLevel() {
        return chanLevel;
    }

    public void setChanLevel(Integer chanLevel) {
        this.chanLevel = chanLevel;
    }

    public String getChanCode() {
        return chanCode;
    }

    public void setChanCode(String chanCode) {
        this.chanCode = chanCode == null ? null : chanCode.trim();
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

    public Integer getChanStatus() {
        return chanStatus;
    }

    public void setChanStatus(Integer chanStatus) {
        this.chanStatus = chanStatus;
    }

    public String getChanTemplatename() {
        return chanTemplatename;
    }

    public void setChanTemplatename(String chanTemplatename) {
        this.chanTemplatename = chanTemplatename == null ? null : chanTemplatename.trim();
    }

    public String getInfoTemplatename() {
        return infoTemplatename;
    }

    public void setInfoTemplatename(String infoTemplatename) {
        this.infoTemplatename = infoTemplatename == null ? null : infoTemplatename.trim();
    }

    public String getBelongChanname() {
        return belongChanname;
    }

    public void setBelongChanname(String belongChanname) {
        this.belongChanname = belongChanname == null ? null : belongChanname.trim();
    }

    public String getChenDisc() {
        return chenDisc;
    }

    public void setChenDisc(String chenDisc) {
        this.chenDisc = chenDisc == null ? null : chenDisc.trim();
    }
}