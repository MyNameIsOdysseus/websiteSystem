package net.minggao.cms.service;

import net.minggao.cms.model.Ppt;

import java.util.List;

public interface pptService {
    int setPptMessage(Ppt ppt);

    List<Ppt> getPptList(int currpage,int pagesize,Ppt ppt);

    int getMaxnum();

    int deletePpt(Long pptid);

    Ppt getSingleMessage(Long pptid);
}
