package net.minggao.cms.controller;

import net.minggao.cms.config.ReflectUtil;
import net.minggao.cms.config.SimpleUtil;
import net.minggao.cms.model.Template;
import net.minggao.cms.service.TemplateService;
import net.minggao.core.framework.validate.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/TemplateController")
public class TemplateController {
	
	@Autowired
	private TemplateService templateService;
	
	@RequestMapping("/getPageList")
    @ResponseBody
    public Map getPageList(int currentpage,String pageSize,String templateName){
        
		Template template = new Template();
		if(!templateName.equals("")) {
			template.setTemplateName(templateName);
		}
        List list= templateService.getPageList(currentpage,Integer.parseInt(pageSize),template);
        int maxnum = templateService.getMaxNum();
        Map map = new HashMap();
        map.put("templatelist",list);
        map.put("maxnum",maxnum);
        return map;
    }
	
	@RequestMapping("/saveTemplate")
    @ResponseBody
    public Object setChanMessage(Template template){

        template=(Template) ReflectUtil.reflect(template);

        Map map = ValidatorUtil.validate(template);
        if(map !=null){
            if(map.size()!=0){
                return map;
            }else{
                return null;
            }
        }else{
        	int x= templateService.saveTemplate(template);
        	if(x==0||x==1){
                return x;
            }else {
            	return x;
            }
        }
    }
	
	@RequestMapping("/del")
    @ResponseBody
    public String del(String updateid){
        return templateService.del(Long.parseLong(updateid));
    }

    @RequestMapping("/upda")
    public ModelAndView upda(String updateid){
        if(!SimpleUtil.isInteger(updateid)){
            return null;
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("updateid",updateid);
        modelAndView.setViewName("MG_template");
        return modelAndView;
    }

    @RequestMapping("/getList")
    @ResponseBody
    public List getList(){
        return templateService.getList();
    }





    @RequestMapping("/lookMessage")
    @ResponseBody
    public Template lookMessage(String updateid){
        return templateService.lookMessage(Long.parseLong(updateid));
    }
    
    @RequestMapping("/ajaxRepeat")
    @ResponseBody
    public String ajaxRepeat(String templateCode){
        return templateService.ajaxRepeat(templateCode);
    }

}
