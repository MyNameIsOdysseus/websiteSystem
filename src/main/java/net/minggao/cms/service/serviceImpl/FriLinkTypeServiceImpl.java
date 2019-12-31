package net.minggao.cms.service.serviceImpl;

import net.minggao.cms.config.ReflectUtil;
import net.minggao.cms.config.SimpleUtil;
import net.minggao.cms.dao.FriLinkTypeMapper;
import net.minggao.cms.model.FriLinkType;
import net.minggao.cms.service.FriLinkTypeService;
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
@Service("/FriLinkTypeService")
public class FriLinkTypeServiceImpl implements FriLinkTypeService {

    public static int maxnum;

    @Resource
    FriLinkTypeMapper friLinkTypeMapper;

    @Override
    public int setFritypeMessage(FriLinkType friLinkType) {
        if(friLinkTypeMapper.selectByName(friLinkType.getLinkType())!=null && !friLinkTypeMapper.selectByName(friLinkType.getLinkType()).getFrlinkId().equals(friLinkType.getFrlinkId())){
            return 3;
        }

        if(friLinkTypeMapper.selectByPrimaryKey(friLinkType.getFrlinkId())!=null){
            friLinkTypeMapper.updateByPrimaryKeySelective(friLinkType);
            return 1;
        }
        friLinkType.setFrlinkId(SimpleUtil.getuuid());
        friLinkTypeMapper.insert(friLinkType);
        return 0;
    }

    @Override
    public List getFriLinkTypeList(int currentpage, int pagesize, FriLinkType friLinkType) {
        List list = friLinkTypeMapper.selectAll(friLinkType);
        maxnum=list.size();
        return getPageSize(currentpage,pagesize,list);
    }

    @Override
    public int getMaxNum() {
        // friLinkTypeMapper.selectAll(new FriLinkType()).size();
        return maxnum;
    }

    @Override
    public FriLinkType getFriLinkTypeSingle(Long frlinkId) {
        FriLinkType friLinkType =  friLinkTypeMapper.selectByPrimaryKey(frlinkId);
        friLinkType=(FriLinkType) ReflectUtil.reflect(friLinkType);
        return friLinkType;
    }

    @Override
    public int deleteMessage(Long frlinkId) {
        return friLinkTypeMapper.deleteByPrimaryKey(frlinkId);
    }
}
