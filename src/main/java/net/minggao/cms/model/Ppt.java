package net.minggao.cms.model;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class Ppt {
    private Long pptId;

    @NotNull(message = "图片名称不能为空")
    @Length(max = 50,message = "上传的图片名称不能超过50长度")
    private String pptName;

    private String linkAddress;

    private String title;

    @NotNull(message = "排序码不能为空")
    @Min(0)
    @Max(9999)
    private Integer sorted;

    @NotNull(message = "所属栏目不能为空")
    private Long belongChan;

    private String belongChanName;

    public String getBelongChanName() {
        return belongChanName;
    }

    public void setBelongChanName(String belongChanName) {
        this.belongChanName = belongChanName;
    }

    private Long createUser;

    private Date createTime;

    private Long siteId;

    private String pptsavename;

    public Long getPptId() {
        return pptId;
    }

    public void setPptId(Long pptId) {
        this.pptId = pptId;
    }

    public String getPptName() {
        return pptName;
    }

    public void setPptName(String pptName) {
        this.pptName = pptName == null ? null : pptName.trim();
    }

    public String getLinkAddress() {
        return linkAddress;
    }

    public void setLinkAddress(String linkAddress) {
        this.linkAddress = linkAddress == null ? null : linkAddress.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getSorted() {
        return sorted;
    }

    public void setSorted(Integer sorted) {
        this.sorted = sorted;
    }

    public Long getBelongChan() {
        return belongChan;
    }

    public void setBelongChan(Long belongChan) {
        this.belongChan = belongChan;
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

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public String getPptsavename() {
        return pptsavename;
    }

    public void setPptsavename(String pptsavename) {
        this.pptsavename = pptsavename == null ? null : pptsavename.trim();
    }
}