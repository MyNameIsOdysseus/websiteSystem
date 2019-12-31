package net.minggao.cms.controller;

import net.minggao.cms.config.ReflectUtil;
import net.minggao.cms.config.SimpleUtil;
import net.minggao.cms.model.Article;
import net.minggao.cms.service.ArticleService;
import net.minggao.core.framework.validate.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author robin
 * @Description: TODO
 * @Param:
 * @Return:
 * @Create: 2018/6/4 22:18
 */
@Controller
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @RequestMapping("/setMessage")
    @ResponseBody
    public Object setChanMessage(Article article) {
        if(article.getNewssubhead() == ""){
            article.setNewssubhead(null);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(article.getPublishdate() != null) {
            String sDate = simpleDateFormat.format(article.getPublishdate());
            Date date = null;

            try {
                date = simpleDateFormat.parse(sDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            article.setPublishdate(date);
        }
        Map map = ValidatorUtil.validate(article);
        if(map !=null){
            if(map.size()!=0){
                return map;
            }else{
                return null;
            }
        }else{
            int x= articleService.setMessage(article);
            if(x==0||x==1){
                return x;
            }else{
                return x;
            }
        }
    }

    @RequestMapping("/getArticleMessage")
    @ResponseBody
    public Map getArticleMessage(int currentpage,String pageSize,String ImgName,String TitleName,String ChanName,String chanid,String userid,String username){
        List artilcelist= articleService.artilcelist(currentpage,Integer.parseInt(pageSize),ImgName,TitleName,ChanName,chanid,userid,username);
        if(artilcelist.size()==1){
            Article a = (Article) artilcelist.get(0);
            a=(Article) ReflectUtil.reflect(a);
            //a.setArticleContentId(a.getArticleContentId().replaceAll("<p>",""));
            //a.setArticleContentId(a.getArticleContentId().replaceAll("</p>",""));
            artilcelist = new ArrayList();
            artilcelist.add(a);
        }
        int maxnum=articleService.getMaxnum();
        Map map = new HashMap();
        map.put("article",artilcelist);
        map.put("maxnum",maxnum);
        return map;


    }

    @RequestMapping("/getMessageByName")
    @ResponseBody
    public Article getMessageByName(String updateid){
        Article artilce= articleService.getMessageByName(Long.valueOf(updateid));
        return artilce;
    }


    @RequestMapping("/del")
    @ResponseBody
    public String del(String updateid){
        return articleService.del(Long.valueOf(updateid));
    }


    @RequestMapping("/setPubmessage")
    @ResponseBody
    public String setPubmessage(String numvalue,String numtype,String articleid){
        return articleService.setPubmessage(numvalue,numtype,articleid);
    }


    @RequestMapping("/getWaitAuditMessage")
    @ResponseBody
    public Map getWaitAuditMessage(int currentpage,String pageSize,String ImgName,String TitleName,String ChanName,String username){
        List waitArticle = articleService.getWaitAuditMessage(currentpage,Integer.parseInt(pageSize),ImgName,TitleName,ChanName,username);
        int maxnum=articleService.getAuditMaxnum();
        Map map = new HashMap();
        map.put("auditLsit",waitArticle);
        map.put("maxnum",maxnum);
        return map;
    }

    @RequestMapping("/getoldAuditMessage")
    @ResponseBody
    public Map getoldAuditMessage(int currentpage,String pageSize,String ImgName,String TitleName,String ChanName,String username){
        List waitArticle = articleService.getoldAuditMessage(currentpage,Integer.parseInt(pageSize),ImgName,TitleName,ChanName,username);
        int maxnum=articleService.getlookMaxnum();
        Map map = new HashMap();
        map.put("oldAuditLsit",waitArticle);
        map.put("maxnum",maxnum);
        return map;
    }

    @RequestMapping("/updatePage")
    public ModelAndView updatePage(String updateid){
        if(!SimpleUtil.isInteger(updateid)){
            return null;
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("updateid",updateid);
        modelAndView.setViewName("MG_auditDetailPage");
        return modelAndView;
    }

    @RequestMapping("/MG_auditOnlyLook")
    public ModelAndView MG_auditOnlyLook(String updateid){
        if(!SimpleUtil.isInteger(updateid)){
            return null;
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("updateid",updateid);
        modelAndView.setViewName("MG_auditOnlyLook");
        return modelAndView;
    }


    @RequestMapping("/getAuditMessage")
    @ResponseBody
    public Article getAuditMessage(String auditid){
        Article article = articleService.getAuditMessage(auditid);
        return article;
    }

    @RequestMapping("/delMessage")
    @ResponseBody
    public int delMessage(String auditid){
        int  a = articleService.delMessage(auditid);
        return a;
    }

    @RequestMapping("/changeMessageAudit")
    @ResponseBody
    public String changeMessageAudit(Article article){
        //article=(Article) ReflectUtil.reflect(article);
        // articleService.changeMessageAudit(article);
        return  articleService.changeMessageAudit(article);
    }

    @RequestMapping("/getAuditMessageOld")
    @ResponseBody
    public Article getAuditMessageOld(String auditid){
        Article article = articleService.getAuditMessageOld(auditid);
        return article;
    }

    @RequestMapping("/getAuditoldList")
    @ResponseBody
    public String getAuditoldList(String auditid){
        String textname = articleService.getAuditoldList(auditid);
        return textname;
    }


}
