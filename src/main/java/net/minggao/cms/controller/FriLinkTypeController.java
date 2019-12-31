package net.minggao.cms.controller;

import net.minggao.cms.config.SimpleUtil;
import net.minggao.cms.model.FriLinkType;
import net.minggao.cms.service.FriLinkTypeService;
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
@RequestMapping("/FriLinkType")
public class FriLinkTypeController {

    @Autowired
    FriLinkTypeService friLinkTypeService;

    @RequestMapping("/getFriLinkTypeList")
    @ResponseBody
    public Map getFriLinkTypeList(int currentpage,String pageSize,String ImgName){
        FriLinkType friLinkType = new FriLinkType ();
        if(ImgName != ""){
            friLinkType.setLinkType(ImgName);
        }
        List list = friLinkTypeService.getFriLinkTypeList(currentpage, Integer.parseInt(pageSize),friLinkType);
        int maxnum = friLinkTypeService.getMaxNum();
        Map map = new HashMap();
        map.put("FriLinkTypeList",list);
        map.put("maxnum",maxnum);
        return map;
    }

    @RequestMapping("/setFriTypeMessage")
    @ResponseBody
    public Object setFritypeMessage(FriLinkType friLinkType){
        if(friLinkType.getLinkType().equals("")){
            friLinkType.setLinkType(null);
        }
        Map map = ValidatorUtil.validate(friLinkType);
        if(map !=null){
            if(map.size()!=0){
                return map;
            }else{
                return null;
            }
        }else{
            int x= friLinkTypeService.setFritypeMessage(friLinkType);
            if(x==0||x==1){
                return x;
            }else{
                return x;
            }
        }
    }

    @RequestMapping("/getFriLinkTypeSingle")
    @ResponseBody
    public FriLinkType getFriLinkTypeSingle(String frlinkId){
        return friLinkTypeService.getFriLinkTypeSingle(Long.valueOf(frlinkId));
    }

    @RequestMapping("/deleteMessage")
    @ResponseBody
    public int deleteMessage(String frlinkId){
        return friLinkTypeService.deleteMessage(Long.valueOf(frlinkId));
    }

    @RequestMapping("/updatePage")
    public ModelAndView updatePage(String updateid){
        if(!SimpleUtil.isInteger(updateid)){
            return null;
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("updateid",updateid);
        modelAndView.setViewName("MG_friendlySet");
        return modelAndView;
    }

}
