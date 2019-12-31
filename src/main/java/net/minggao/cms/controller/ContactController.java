package net.minggao.cms.controller;

import net.minggao.cms.model.Contact;
import net.minggao.cms.service.ContactService;
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
@RequestMapping("/Contact")
@Controller
public class ContactController {

    @Autowired
    ContactService contactService;

    @RequestMapping("/getContactList")
    @ResponseBody
    public Map getContactList(int currentpage,String pageSize,String ImgName){
        Contact contact = new Contact();
        if(ImgName != ""){
            contact.setName(ImgName);
        }
        List list = contactService.getContactList(currentpage,Integer.parseInt(pageSize),contact);
        int maxnum = contactService.getMaxNum();
        Map map = new HashMap();
        map.put("contactList",list);
        map.put("maxnum",maxnum);
        return map;
    }

    @RequestMapping("/deleteContact")
    @ResponseBody
    public int deleteContact(String contactId){
        return contactService.deleteMessage(Long.valueOf(contactId));
    }

    @RequestMapping("/getSingleMessage")
    @ResponseBody
    public Contact getSingleMessage(String contactId){
        return contactService.getSingleMessage(Long.valueOf(contactId));
    }

}
