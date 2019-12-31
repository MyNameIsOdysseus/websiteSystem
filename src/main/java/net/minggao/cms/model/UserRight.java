package net.minggao.cms.model;

import java.util.Date;

public class UserRight {
    private Long rightId;

    private Long chanId;

    private Integer rightScope;

    private Long userId;

    private Long siteId;

    private Long createUser;

    private Date createTime;

    public Long getRightId() {
        return rightId;
    }

    public void setRightId(Long rightId) {
        this.rightId = rightId;
    }

    public Long getChanId() {
        return chanId;
    }

    public void setChanId(Long chanId) {
        this.chanId = chanId;
    }

    public Integer getRightScope() {
        return rightScope;
    }

    public void setRightScope(Integer rightScope) {
        this.rightScope = rightScope;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
}