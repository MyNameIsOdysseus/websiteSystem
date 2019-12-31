package net.minggao.cms.controller;

import net.minggao.cms.model.ProcessActi;
import net.minggao.cms.service.ProcessAtService;
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
@RequestMapping("/ProcessAt")
public class ProcessAtController {

    @Autowired
    ProcessAtService processAtService;

    @RequestMapping("/getProcessAtList")
    @ResponseBody
    public List getProcessAtList(String setid){
        return processAtService.getProcessAtList(setid);
    }

    @RequestMapping("/setProcessAtiMessage")
    @ResponseBody
    public Object setProcessAtiMessage(ProcessActi processActi){
        Map map = ValidatorUtil.validate(processActi);
        if(map !=null){
            if(map.size()!=0){
                return map;
            }else{
                return null;
            }
        }else{
            int x= processAtService.setProcessAtiMessage(processActi);
            if(x==0||x==1){
                return x;
            }else{
                return x;
            }
        }
    }

    @RequestMapping("/getSingleMessage")
    @ResponseBody
    public ProcessActi getSingleMessage(String id){
        return processAtService.getSingleMessage(Long.valueOf(id));
    }

    @RequestMapping("/delSingleMessage")
    @ResponseBody
    public int delSingleMessage(String id){
        return processAtService.delSingleMessage(Long.valueOf(id));
    }

}
