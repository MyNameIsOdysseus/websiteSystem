package net.minggao.cms.model;

import java.util.Date;

public class ArticleAudit {
    private Long auditid;

    private Long recordId;

    private String audituser;

    private Integer auditresult;

    private Long auditActid;

    private Long siteId;

    private Long createUser;

    private Date createTime;

    private String auditname;

    private Date publisdate;

    private Integer sorted;

    private String channame;

    public Long getAuditid() {
        return auditid;
    }

    public void setAuditid(Long auditid) {
        this.auditid = auditid;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public String getAudituser() {
        return audituser;
    }

    public void setAudituser(String audituser) {
        this.audituser = audituser == null ? null : audituser.trim();
    }

    public Integer getAuditresult() {
        return auditresult;
    }

    public void setAuditresult(Integer auditresult) {
        this.auditresult = auditresult;
    }

    public Long getAuditActid() {
        return auditActid;
    }

    public void setAuditActid(Long auditActid) {
        this.auditActid = auditActid;
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

    public String getAuditname() {
        return auditname;
    }

    public void setAuditname(String auditname) {
        this.auditname = auditname == null ? null : auditname.trim();
    }

    public Date getPublisdate() {
        return publisdate;
    }

    public void setPublisdate(Date publisdate) {
        this.publisdate = publisdate;
    }

    public Integer getSorted() {
        return sorted;
    }

    public void setSorted(Integer sorted) {
        this.sorted = sorted;
    }

    public String getChanname() {
        return channame;
    }

    public void setChanname(String channame) {
        this.channame = channame == null ? null : channame.trim();
    }
}