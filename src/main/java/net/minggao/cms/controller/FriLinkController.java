package net.minggao.cms.controller;

import net.minggao.cms.model.FriendlyLink;
import net.minggao.cms.service.FriendlyLinkService;
import net.minggao.core.framework.validate.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
@RequestMapping("/FriLink")
public class FriLinkController {

    @Autowired
    FriendlyLinkService friendlyLinkService;

    @RequestMapping("/getFrilinkList")
    @ResponseBody
    public List getFrilinkList(String frlinkId){
        return friendlyLinkService.getFrilinkList(frlinkId);
    }



    @RequestMapping("/getSingleMessage")
    @ResponseBody
    public FriendlyLink getSingleMessage(String linkId){
        return friendlyLinkService.getSingleMessage(linkId);
    }


    @RequestMapping("/delSingleMessage")
    @ResponseBody
    public int delSingleMessage(String linkId){
        return friendlyLinkService.delSingleMessage(linkId);
    }

    @RequestMapping("/setFriLinkMessage")
    @ResponseBody
    public Object setFriLinkMessage(FriendlyLink friendlyLink){
        if(friendlyLink.getLinkname().equals("")){
            friendlyLink.setLinkname(null);
        }
        Map map = ValidatorUtil.validate(friendlyLink);
        if(map !=null){
            if(map.size()!=0){
                return map;
            }else{
                return null;
            }
        }else{
            int x= friendlyLinkService.setFriLinkMessage(friendlyLink);
            if(x==0||x==1){
                return x;
            }else{
                return x;
            }
        }
    }
}
