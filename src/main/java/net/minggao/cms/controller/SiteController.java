package net.minggao.cms.controller;


import net.minggao.cms.config.FileDBUtil;
import net.minggao.cms.model.Allattach;
import net.minggao.cms.model.Site;
import net.minggao.cms.service.siteService;
import net.minggao.core.framework.BaseController;
import net.minggao.core.framework.validate.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * discript:站点信息控制器类  主要是进行站点信息的管理控制跳转
 * author:robin
 * time:19年3月13日
 */

@Controller
public class SiteController extends BaseController {


    @Autowired
    siteService siteService;


	@RequestMapping("/index")
	public String test1(HttpServletRequest req){
        Site site = null;
        try {
            site = siteService.getSiteMessageSingle();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(site != null){
            req.setAttribute("saveName",""+site.getSiteLogo());
            req.setAttribute("media",""+site.getTrailer());
            req.setAttribute("trai",""+site.getTrailerCover());
            req.setAttribute("moblie",""+site.getMobiletCover());
        }else{
            req.setAttribute("saveName","1");
            req.setAttribute("media","1");
            req.setAttribute("trai","1");
            req.setAttribute("moblie","1");
        }
        return "MG_essential_information";
    }


    @RequestMapping("/login")
    public String login(HttpServletRequest req){
        return "MG_login";
    }

    @RequestMapping("/demo")
    public String test(Model model){
        return "redirect:/MG_essential_information";
    }

    @RequestMapping("/")
    public String index(Model model, HttpServletResponse response) {
        return "MG_login";
    }


    /**
     * 获取站点信息的控制器方法(适用于目前的单站点的状态)
     * 入参 无
     * 出参 站点信息类
     * @return
     */
    @RequestMapping("/getSiteMessageSingle")
    @ResponseBody
    public Site getSiteMessageSingle(){
	    return siteService.getSiteMessageSingle();
    }

    /**
     * 对站点信息进行新增或编辑
     * author:robin
     * @return
     */
    @RequestMapping("/setmessage")
    @ResponseBody
    public Object setmessage(Site site){

        Allattach a= siteService.checkppt(site.getSiteLogo());
        if(a==null){
            site.setSiteLogo(null);
        }

        String filePath="";
        try {
            filePath = new File("").getCanonicalPath();
            //System.out.println("文件的路径----->:"+filePath);
            if(filePath.indexOf("minggao")>0){
                filePath = filePath.substring(0,filePath.length()-3);
                filePath = filePath+"webapps"+File.separator+"cms"+File.separator+"WEB-INF"+File.separator+"_mgSiteFiles";
                //System.out.println("进入服务器路径----->"+filePath);
            }else{
                filePath = filePath+File.separator+"src"+File.separator+"main"+File.separator+"webapp"+File.separator+"WEB-INF"+File.separator+"_mgSiteFiles";
                //System.out.println("进入本地路径------>"+filePath);
            }
            if(site.getIssynchro()==0){
                //System.out.println("开始复制文件");
                //System.out.println("这里的文件路径"+filePath);
               // System.out.println("这里的获取的路径"+site.getSiteCatalog());
                FileDBUtil.copyDir(filePath,site.getSiteCatalog());
            }
        } catch (IOException e) {
            //System.out.println("操作报错了");
            e.printStackTrace();
        }
        Map s = ValidatorUtil.validate(site);
        if(s != null){
            if(s.size() !=0){
                return s;
            }else{
                return null;
            }
        }else{
            int x=siteService.setSiteMessage(site);
            if(x==0||x==1){
                return x;
            }else{
                return x;
            }
        }
    }

}
