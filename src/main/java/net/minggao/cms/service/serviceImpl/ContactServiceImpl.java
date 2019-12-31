package net.minggao.cms.service.serviceImpl;

import net.minggao.cms.dao.ContactMapper;
import net.minggao.cms.model.Contact;
import net.minggao.cms.service.ContactService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static net.minggao.cms.config.SimpleUtil.getPageSize;

/**
 * @Author robin
 * @Description: TODO
 * @Param:
 * @Return:
 * @Create: 2018/6/4 22:18
 */
@Service("/ContactService")
public class ContactServiceImpl implements ContactService {

    public static int maxnum;

    @Resource
    ContactMapper contactMapper;

    @Override
    public List getContactList(int currentpage, int pagesize, Contact contact) {
        List list = contactMapper.selectAll(contact);
        maxnum=list.size();
        return getPageSize(currentpage,pagesize,list);
    }


    @Override
    public int getMaxNum() {
        return maxnum;
    }

    @Override
    public int deleteMessage(Long contactId) {
        return contactMapper.deleteByPrimaryKey(contactId);
    }

    @Override
    public Contact getSingleMessage(Long contactId) {
        return contactMapper.selectByPrimaryKey(contactId);
    }
}
