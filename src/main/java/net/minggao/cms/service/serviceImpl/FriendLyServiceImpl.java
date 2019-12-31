package net.minggao.cms.service.serviceImpl;

import net.minggao.cms.config.ReflectUtil;
import net.minggao.cms.config.SimpleUtil;
import net.minggao.cms.dao.FriendlyLinkMapper;
import net.minggao.cms.model.FriendlyLink;
import net.minggao.cms.service.FriendlyLinkService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author robin
 * @Description: TODO
 * @Param:
 * @Return:
 * @Create: 2018/6/4 22:18
 */
@Service("/FriendlyLinkService")
public class FriendLyServiceImpl implements FriendlyLinkService {
    @Resource
    FriendlyLinkMapper friendlyLinkMapper;


    @Override
    public int setFriLinkMessage(FriendlyLink friendlyLink) {
        if(friendlyLinkMapper.selectByName(friendlyLink.getLinkname())!=null && !friendlyLinkMapper.selectByName(friendlyLink.getLinkname()).getLinkId().equals(friendlyLink.getLinkId()) ){
            return 3;
        }

        if(friendlyLinkMapper.selectByPrimaryKey(friendlyLink.getLinkId())!=null){
            friendlyLinkMapper.updateByPrimaryKeySelective(friendlyLink);
            return 1;
        }
        friendlyLink.setLinkId(SimpleUtil.getuuid());
        friendlyLinkMapper.insert(friendlyLink);
        return 0;
    }

    @Override
    public List getFrilinkList(String frlinkId) {
        return friendlyLinkMapper.getFrilinkList(Long.valueOf(frlinkId));
    }

    @Override
    public FriendlyLink getSingleMessage(String linkId) {
        FriendlyLink friendlyLink = friendlyLinkMapper.selectByPrimaryKey(Long.valueOf(linkId));
        friendlyLink=(FriendlyLink) ReflectUtil.reflect(friendlyLink);
        return friendlyLink;
    }

    @Override
    public int delSingleMessage(String linkId) {
        int a=friendlyLinkMapper.deleteByPrimaryKey(Long.valueOf(linkId));
        if(a==1){
            return a;
        }else{
            return 0;
        }
    }
}
