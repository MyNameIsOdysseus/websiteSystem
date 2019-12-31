package net.minggao.cms.controller;

import net.minggao.cms.model.Ppt;
import net.minggao.cms.service.pptService;
import net.minggao.core.framework.validate.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author robin
 * @Description: TODO
 * @Param:
 * @Return:
 * @Create: 2018/6/4 22:18
 */
@Controller
@RequestMapping("/ppt")
public class PptController {

    @Autowired
    pptService pptService;

    @RequestMapping("/setPptMessage")
    @ResponseBody
    public Object setPptMessage(Ppt ppt){
        if(ppt.getBelongChan()==1){
            ppt.setBelongChan(null);
        }
        Map s = ValidatorUtil.validate(ppt);
        if(s != null){
            if(s.size() !=0){
                return s;
            }else{
                return null;
            }
        }else{
            int x=pptService.setPptMessage(ppt);
            if(x==0||x==1){
                return x;
            }else{
                return x;
            }
        }
    }

    @RequestMapping("/getPptList")
    @ResponseBody
    public Map getPptList(int currentpage,String pageSize,String ImgName,String TitleName,String ChanName){
        Ppt ppt = new Ppt();
        if(ImgName != ""){
            ppt.setPptName(ImgName);
        }
        if(TitleName != ""){
            ppt.setTitle(TitleName);
        }
        if(ChanName != ""){
            ppt.setBelongChanName(ChanName);
        }
        List pptlist= pptService.getPptList(currentpage,Integer.parseInt(pageSize),ppt);
        int maxnum=pptService.getMaxnum();
        Map map = new HashMap();
        map.put("pptlist",pptlist);
        map.put("maxnum",maxnum);
        return map;
    }

    @RequestMapping("/deletePpt")
    @ResponseBody
    public int deletePpt(Long pptId){
        return pptService.deletePpt(pptId);
    }

    @RequestMapping("/getSingleMessage")
    @ResponseBody
    public Ppt getSingleMessage(Long pptid){
        return pptService.getSingleMessage(pptid);
    }

}
