package net.minggao.cms.service.serviceImpl;

import net.minggao.cms.config.ReflectUtil;
import net.minggao.cms.config.SimpleUtil;
import net.minggao.cms.dao.ProcessActiMapper;
import net.minggao.cms.model.ProcessActi;
import net.minggao.cms.service.ProcessAtService;
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
@Service("/ProcessAtService")
public class ProcessAtServiceImpl implements ProcessAtService {
    @Resource
    ProcessActiMapper processActiMapper;

    @Override
    public List getProcessAtList(String setid) {
        return processActiMapper.selectList(Long.valueOf(setid));
    }

    @Override
    public int setProcessAtiMessage(ProcessActi processActi) {
        if(processActiMapper.selectByPrimaryKey(processActi.getProcessactiId())!=null){
            processActiMapper.updateByPrimaryKeySelective(processActi);
            return 1;
        }
        processActi.setProcessactiId(SimpleUtil.getuuid());
        processActiMapper.insert(processActi);
        return 0;
    }

    @Override
    public ProcessActi getSingleMessage(Long id) {
        ProcessActi processActi = processActiMapper.selectByPrimaryKey(id);
        processActi=(ProcessActi) ReflectUtil.reflect(processActi);
        return processActi;
    }

    @Override
    public int delSingleMessage(Long id) {
        int a=processActiMapper.deleteByPrimaryKey(id);
        if(a==1){
            return a;
        }else{
            return 0;
        }
    }

}
