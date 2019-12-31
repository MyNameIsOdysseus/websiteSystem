package net.minggao.cms.service.serviceImpl;

import net.minggao.cms.config.ReflectUtil;
import net.minggao.cms.config.SimpleUtil;
import net.minggao.cms.dao.AllattachMapper;
import net.minggao.cms.dao.PptMapper;
import net.minggao.cms.model.Ppt;
import net.minggao.cms.service.pptService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Service("pptService")
@Transactional
public class pptServiceImpl implements pptService {

    public static int maxnum;

    @Resource
    PptMapper pptMapper;

    @Resource
    AllattachMapper allattachMapper;

    @Override
    public int setPptMessage(Ppt ppt) {
        if(pptMapper.selectByPrimaryKey(ppt.getPptId()) != null){
            pptMapper.updateByPrimaryKey(ppt);
            //pptMapper.updateByPrimaryKeySelective(ppt);
            return 1;
        }
        ppt.setPptId(SimpleUtil.getuuid());
        pptMapper.insert(ppt);
        return 0;
    }

    @Override
    public List<Ppt> getPptList(int currpage,int pagesize,Ppt ppt) {
        List<Ppt> ppts = pptMapper.selectPptList(ppt);
        maxnum=ppts.size();
        return  getPageSize(currpage,pagesize,ppts);
    }

    @Override
    public int getMaxnum() {
        return maxnum;
    }

    @Override
    public int deletePpt(Long pptid) {
        Ppt ppt = pptMapper.selectByPrimaryKey(pptid);
        allattachMapper.deleteByPrimaryKey(ppt.getPptsavename());
        return pptMapper.deleteByPrimaryKey(pptid);
    }

    @Override
    public Ppt getSingleMessage(Long pptid) {
        Ppt ppt=null;
        try {
            ppt =pptMapper.selectByPrimaryKey(pptid);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        ppt=(Ppt) ReflectUtil.reflect(ppt);

        return ppt;
    }



}
