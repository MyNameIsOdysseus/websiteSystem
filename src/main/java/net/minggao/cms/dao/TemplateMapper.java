package net.minggao.cms.dao;

import net.minggao.cms.model.Template;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TemplateMapper {
	
	List getTemplateList(Template template);
	
	Template selectByPrimaryKey(Long templateId);

	List  selectAll();
	
	int updateByPrimaryKeySelective(Template template);
	
	int insert(Template template);
	
	int deleteByPrimaryKey(Long templateId);
	
	String ajaxRepeat(@Param(value="templateCode") String templateCode);

	Template selectByname(@Param(value="templateName") String templateName);

}
