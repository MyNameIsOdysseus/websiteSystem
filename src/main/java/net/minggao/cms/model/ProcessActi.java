package net.minggao.cms.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class ProcessActi {
    private Long processactiId;

    @NotNull(message = "步骤序号不能为空")
    private Integer processactiNum;

    @NotNull(message = "参与人不能为空")
    private Long processactiUser;

    private Long siteId;

    private Long createUser;

    private Date createTime;

    private Long processId;

    private String processactiUsername;

    public Long getProcessactiId() {
        return processactiId;
    }

    public void setProcessactiId(Long processactiId) {
        this.processactiId = processactiId;
    }

    public Integer getProcessactiNum() {
        return processactiNum;
    }

    public void setProcessactiNum(Integer processactiNum) {
        this.processactiNum = processactiNum;
    }

    public Long getProcessactiUser() {
        return processactiUser;
    }

    public void setProcessactiUser(Long processactiUser) {
        this.processactiUser = processactiUser;
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

    public Long getProcessId() {
        return processId;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public String getProcessactiUsername() {
        return processactiUsername;
    }

    public void setProcessactiUsername(String processactiUsername) {
        this.processactiUsername = processactiUsername == null ? null : processactiUsername.trim();
    }
}