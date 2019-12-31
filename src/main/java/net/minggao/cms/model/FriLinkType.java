package net.minggao.cms.model;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class FriLinkType {
    private Long frlinkId;

    @NotNull(message = "链接类型不能为空")
    @Length(max = 100,message = "链接类型的输入长度不能超过100")
    private String linkType;

    private Long siteId;

    private Long createUser;

    private Date createTime;

    private Integer linkCode;

    public Long getFrlinkId() {
        return frlinkId;
    }

    public void setFrlinkId(Long frlinkId) {
        this.frlinkId = frlinkId;
    }

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType == null ? null : linkType.trim();
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

    public Integer getLinkCode() {
        return linkCode;
    }

    public void setLinkCode(Integer linkCode) {
        this.linkCode = linkCode;
    }
}