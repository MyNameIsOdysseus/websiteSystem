package net.minggao.cms.service.serviceImpl;

import net.minggao.cms.config.ReflectUtil;
import net.minggao.cms.config.SimpleUtil;
import net.minggao.cms.dao.ProcessActiMapper;
import net.minggao.cms.dao.ProcessMapper;
import net.minggao.cms.model.Process;
import net.minggao.cms.service.ProcessService;
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

@Service("/ProcessService")
public class ProcessServiceImpl implements ProcessService {

    public static int maxnum;


    @Resource
    ProcessMapper processMapper;

    @Resource
    ProcessActiMapper processActiMapper;


    @Override
    public List getProcessList(int currentpage, int pagesize,Process process) {
        List list = processMapper.selectAll(process);
        maxnum=list.size();
        return getPageSize(currentpage,pagesize,list);
    }

    @Override
    public int getMaxNum() {
        //int maxnum= processMapper.selectAll(new Process()).size();
        return maxnum;
    }

    @Override
    public int setProcessMessage(Process process) {

        if(processMapper.selectByName(process.getProcessName())!=null && !processMapper.selectByName(process.getProcessName()).getProcessId().equals(process.getProcessId())){
            return 3;
        }

        if(processMapper.selectByPrimaryKey(process.getProcessId())!=null){
            processMapper.updateByPrimaryKeySelective(process);
            return 1;
        }
        process.setProcessId(SimpleUtil.getuuid());
        processMapper.insert(process);
        return 0;
    }

    @Override
    public Process getProcessSingle(long id) {
        Process process = processMapper.selectByPrimaryKey(id);
        process=(Process) ReflectUtil.reflect(process);
        return process;
    }

    @Override
    public int deleteMessage(long id) {

        processActiMapper.deleteByProcessid(id);

        return processMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List getAllMessage() {
        return processMapper.selectAll(new Process());
    }

}
