package net.minggao.cms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author robin
 * @Description: TODO
 * @Param:
 * @Return:
 * @Create: 2018/6/4 22:18
 */
@RequestMapping("/page")
@Controller
public class PageController {
    @RequestMapping("{pageName}")
    public ModelAndView toPage(@PathVariable("pageName") String pageName){
        ModelAndView mv = new ModelAndView(pageName);
        return mv ;
    }
}
