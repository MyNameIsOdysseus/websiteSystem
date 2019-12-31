package net.minggao.cms.service;

import net.minggao.cms.model.Process;

import java.util.List;

public interface ProcessService {

    List getProcessList(int currentpage, int pagesize,Process process);

    int getMaxNum();

    int setProcessMessage(Process process);

    Process getProcessSingle(long id);

    int deleteMessage(long id);

    List getAllMessage();

}
