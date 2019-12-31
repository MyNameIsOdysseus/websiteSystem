package net.minggao.cms.controller;

import net.minggao.cms.config.SimpleUtil;
import net.minggao.cms.model.User;
import net.minggao.cms.service.UserService;
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
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;


    @RequestMapping("/setUserMessage")
    @ResponseBody
    public Object setUserMessage(User user){
        if(user.getUsername() == "admin" || user.getUsername() == "ADMIN"){
            return 6;
        }

        Map map = ValidatorUtil.validate(user);
        if(user.getPassword().equals("") || user.getPassword()==""){
            user.setPassword(null);
        }
        if(map !=null && user.getUserId()==null){
            if(map.size()!=0){
                return map;
            }else{
                return null;
            }
        }else{
            int x= userService.setUserMessage(user);
            if(x==0||x==1){
                return x;
           }else{
                return x;
            }
        }
    }


    @RequestMapping("/getUserList")
    @ResponseBody
    public Map getUserList(int currentpage,String pageSize,String ImgName){
        User user = new User ();
        if(ImgName != ""){
            user.setUsername(ImgName);
        }
        List list = userService.getUserList(currentpage, Integer.parseInt(pageSize),user);
        int maxnum = userService.getMaxNum();
        Map map = new HashMap();
        map.put("UserList",list);
        map.put("maxnum",maxnum);
        return map;
    }


    @RequestMapping("/updateuser")
    public ModelAndView updateuser(String updateid){
        if(!SimpleUtil.isInteger(updateid)){
            return null;
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("updateid",updateid);
        modelAndView.setViewName("MG_user");
        return modelAndView;
    }

    @RequestMapping("/getSingleMessage")
    @ResponseBody
    public User getSingleMessage(String userid){
        return userService.getSingleMessage(userid);
    }

    @RequestMapping("/StartOrStop")
    @ResponseBody
    public String StartOrStop(User user){
        return userService.StartOrStop(user);
    }

    @RequestMapping("/delmessage")
    @ResponseBody
    public String delmessage(String userid){
        return userService.delmessage(Long.valueOf(userid));
    }

    @RequestMapping("/updateAllPeople")
    @ResponseBody
    public List updateAllPeople(){
        return userService.updateAllPeople();
    }

    @RequestMapping("/changePassword")
    @ResponseBody
    public Object changePassword(User user){
        return userService.changePassword(user);
    }


    @RequestMapping("/getUserRight")
    @ResponseBody
    public Object[] getUserRight(String  userid){
        return userService.getUserRight(userid);
    }


}
