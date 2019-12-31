package net.minggao.cms.service;

import net.minggao.cms.model.Chan;

import java.util.List;

public interface chanService {
    int setChanMessage(Chan chan);

    List getPageMessage(int currentpage,int pagesize,Chan chan);

    int getMaxNum();

    String getStop(Long updateid);

    String getStart(Long updateid);

    String del(Long updateid);

    Chan lookMessage(Long updateid);

    List getAllMessage();

    Object[] getChanOrder();

    Chan getChanMessage(String chanName);

    Chan getChanMessageById(String chanId);

}
