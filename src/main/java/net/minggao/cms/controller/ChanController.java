package net.minggao.cms.controller;

import net.minggao.cms.config.SimpleUtil;
import net.minggao.cms.model.Chan;
import net.minggao.cms.service.chanService;
import net.minggao.core.framework.validate.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
@RequestMapping("/chan")
public class ChanController {

    @Autowired
    chanService chanService;

    @RequestMapping("/setChanMessage")
    @ResponseBody
    public Object setChanMessage(Chan chan){
        if(chan.getChanStatus() == null){
            chan.setChanStatus(0);
        }
        Map map = ValidatorUtil.validate(chan);
        if(map !=null){
            if(map.size()!=0){
                return map;
            }else{
                return null;
            }
        }else{
            int x= chanService.setChanMessage(chan);
            if(x==0||x==1){
                return x;
            }else{
               return x;
            }
        }
    }

    @RequestMapping("/getPageMessage")
    @ResponseBody
    public Map  getPageMessage(int currentpage,String pageSize,String ImgName,String TitleName,String ChanName){
        Chan chan = new Chan();
        if(ImgName != ""){
            chan.setChanName(ImgName);
        }
        if(!TitleName.equals("0")){
            chan.setChanType(TitleName);
        }
        if(!ChanName.equals("-1")){
            chan.setChanStatus(Integer.parseInt(ChanName));
        }

        List list= chanService.getPageMessage(currentpage,Integer.parseInt(pageSize),chan);
        int maxnum = chanService.getMaxNum();
        Map map = new HashMap();
        map.put("chanlist",list);
        map.put("maxnum",maxnum);
        return map;
    }


    @RequestMapping("/getStop")
    @ResponseBody
    public String getStop(String updateid){
        return chanService.getStop(Long.parseLong(updateid));
    }

    @RequestMapping("/getStart")
    @ResponseBody
    public String getStart(String updateid){
        return chanService.getStart(Long.parseLong(updateid));
    }

    @RequestMapping("/del")
    @ResponseBody
    public String del(String updateid){
        return chanService.del(Long.parseLong(updateid));
    }

    @RequestMapping("/upda")
    public ModelAndView upda(String updateid){
        if(!SimpleUtil.isInteger(updateid)){
            return null;
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("updateid",updateid);
        modelAndView.setViewName("MG_column");
        return modelAndView;
    }

    @RequestMapping("/lookMessage")
    @ResponseBody
    public Chan lookMessage(String updateid){
        return chanService.lookMessage(Long.parseLong(updateid));
    }

    @RequestMapping("/getAllMessage")
    @ResponseBody
    public List getAllMessage(){
        return chanService.getAllMessage();
    }

    @RequestMapping("/getChanOrder")
    @ResponseBody
    public Object[] getChanOrder(){
        return chanService.getChanOrder();
    }

    @RequestMapping("/getChanMessage")
    @ResponseBody
    public Chan getChanMessage(String chanName){
        return chanService.getChanMessage(chanName);
    }

    @RequestMapping("/getChanMessageById")
    @ResponseBody
    public Chan getChanMessageById(String chanId){
        return chanService.getChanMessageById(chanId);
    }



}
