package net.minggao.cms.model;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class FriendlyLink {
    private Long linkId;

    @NotNull(message = "链接地址不能为空")
    private String linkaddress;

    private Long siteId;

    private Long createUser;

    private Date createTime;

    private Long frlinkId;

    @NotNull(message = "链接名称不能为空")
    private String linkname;

    public Long getLinkId() {
        return linkId;
    }

    public void setLinkId(Long linkId) {
        this.linkId = linkId;
    }

    public String getLinkaddress() {
        return linkaddress;
    }

    public void setLinkaddress(String linkaddress) {
        this.linkaddress = linkaddress == null ? null : linkaddress.trim();
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

    public Long getFrlinkId() {
        return frlinkId;
    }

    public void setFrlinkId(Long frlinkId) {
        this.frlinkId = frlinkId;
    }

    public String getLinkname() {
        return linkname;
    }

    public void setLinkname(String linkname) {
        this.linkname = linkname == null ? null : linkname.trim();
    }
}