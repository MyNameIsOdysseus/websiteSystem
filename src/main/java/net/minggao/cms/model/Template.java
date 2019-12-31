package net.minggao.cms.model;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

public class Template implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long templateId;
	@NotEmpty(message = "模板编码不能为空")
	@Length(max = 100,message = "模板编码长度不能超过100个")
	private String templateCode;
	private Integer templateType;
	@NotEmpty(message = "模板名称不能为空")
	@Length(max=100,message = "模板名称不能超过100个")
	private String templateName;
	@NotEmpty(message = "模板内容不能为空")
	private String templateContent;
	private Long siteId;
	private Long createUser;
	private Date createTime;
	
	public Long getTemplateId() {
		return templateId;
	}
	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}
	public String getTemplateCode() {
		return templateCode;
	}
	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}
	public Integer getTemplateType() {
		return templateType;
	}
	public void setTemplateType(Integer templateType) {
		this.templateType = templateType;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getTemplateContent() {
		return templateContent;
	}
	public void setTemplateContent(String templateContent) {
		this.templateContent = templateContent;
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
