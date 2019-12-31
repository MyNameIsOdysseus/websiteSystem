package net.minggao.cms.model;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class User {
    private Long userId;

    @NotNull(message = "用户名不能为空")
    @Length(min = 2, max =40,message = "用户名的长度必须大于2，且不能超过40字符")
    private String username;
    @Length(min = 8,max =20,message = "密码长度不能小于8，并且不能超过20")
    @NotNull(message = "密码不能为空")
    private String password;

    private Long upUserid;

    private Long siteId;

    private Long createUser;

    private Date createTime;

    private String upUsername;

    private Integer userStatus;

    private String  chanList;

    private String chantype;

    public String getChantype() {
        return chantype;
    }

    public void setChantype(String chantype) {
        this.chantype = chantype;
    }

    public String getChanList() {
        return chanList;
    }

    public void setChanList(String chanList) {
        this.chanList = chanList;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Long getUpUserid() {
        return upUserid;
    }

    public void setUpUserid(Long upUserid) {
        this.upUserid = upUserid;
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

    public String getUpUsername() {
        return upUsername;
    }

    public void setUpUsername(String upUsername) {
        this.upUsername = upUsername == null ? null : upUsername.trim();
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }
}