package net.minggao.cms.service;

import java.util.List;

import net.minggao.cms.model.Template;

public interface TemplateService {
	
	List getPageList(int currentpage,int pagesize,Template template);
	
	int getMaxNum();

    String getStop(Long updateid);

    String getStart(Long updateid);

    String del(Long updateid);
    
    int saveTemplate(Template template);
    
    Template lookMessage(Long updateid);
    
    String ajaxRepeat(String templateCode);

    List getList();

}
