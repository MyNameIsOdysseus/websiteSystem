package net.minggao.cms.service.serviceImpl;

import net.minggao.cms.config.ReflectUtil;
import net.minggao.cms.config.SimpleUtil;
import net.minggao.cms.dao.AllattachMapper;
import net.minggao.cms.dao.SiteMapper;
import net.minggao.cms.model.Allattach;
import net.minggao.cms.model.Site;
import net.minggao.cms.service.siteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service("siteService")
@Transactional
public class siteServiceImpl implements siteService {

    @Resource
    SiteMapper siteMapper;

    @Resource
    AllattachMapper allattachMapper;

    @Override
    public int setSiteMessage(Site site) {
        if(siteMapper.selectByPrimaryKey(site.getSiteId()) != null){
            siteMapper.updateByPrimaryKeySelective(site);
            return 1;//执行更新的操作
        }
        site.setSiteId(SimpleUtil.getuuid());
        site.setSiteStatus(1);
        //site.setCreateUser(1111l);
        site.setCreateTime(new Date());
        siteMapper.delete();
        siteMapper.insert(site);
        return 0; //执行插入的操作
    }

    @Override
    public Site getSiteMessageSingle() {
        Site site = siteMapper.selectAll();
        if(site != null){
            site=(Site) ReflectUtil.reflect(site);
        }
        return site;
    }

    @Override
    public Allattach checkppt(String fileid) {
        return allattachMapper.selectByid(fileid);
    }
}
