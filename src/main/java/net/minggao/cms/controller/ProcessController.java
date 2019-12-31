package net.minggao.cms.controller;

import net.minggao.cms.config.SimpleUtil;
import net.minggao.cms.model.Process;
import net.minggao.cms.service.ProcessService;
import net.minggao.core.framework.validate.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

;

/**
 * @Author robin
 * @Description: TODO
 * @Param:
 * @Return:
 * @Create: 2018/6/4 22:18
 */
@RequestMapping("/Process")
@Controller
public class ProcessController {

    @Autowired
    ProcessService processService;

    @RequestMapping("/getProcessList")
    @ResponseBody
    public Map getProcessList(int currentpage,String pageSize,String ImgName){
        Process process = new Process();
        if(ImgName != ""){
            process.setProcessName(ImgName);
        }
        List list = processService.getProcessList(currentpage, Integer.parseInt(pageSize),process);
        int maxnum = processService.getMaxNum();
        Map map = new HashMap();
        map.put("processList",list);
        map.put("maxnum",maxnum);
        return map;
    }

    @RequestMapping("/setProcessMessage")
    @ResponseBody
    public Object setProcessMessage(Process process){
        Map map = ValidatorUtil.validate(process);
        if(map !=null){
            if(map.size()!=0){
                return map;
            }else{
                return null;
            }
        }else{
            int x= processService.setProcessMessage(process);
            if(x==0||x==1){
                return x;
            }else{
                return x;
            }
        }
    }

    @RequestMapping("/getProcessSingle")
    @ResponseBody
    public Process getProcessSingle(String processId){
        return processService.getProcessSingle(Long.valueOf(processId));
    }

    @RequestMapping("/deleteMessage")
    @ResponseBody
    public int deleteMessage(String processId){
        return processService.deleteMessage(Long.valueOf(processId));
    }

    @RequestMapping("/updatePage")
    public ModelAndView updatePage(String updateid){
        if(!SimpleUtil.isInteger(updateid)){
            return null;
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("updateid",updateid);
        modelAndView.setViewName("MG_processSet");
        return modelAndView;
    }

    @RequestMapping("/getAllMessage")
    @ResponseBody
    public List getAllMessage(){
        return processService.getAllMessage();
    }

}
