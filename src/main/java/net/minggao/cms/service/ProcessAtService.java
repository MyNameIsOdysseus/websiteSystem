package net.minggao.cms.service;

import net.minggao.cms.model.ProcessActi;

import java.util.List;

public interface ProcessAtService {
    List getProcessAtList(String setid);

    int setProcessAtiMessage(ProcessActi processActi);

    ProcessActi getSingleMessage(Long id);

    int delSingleMessage(Long id);

}
