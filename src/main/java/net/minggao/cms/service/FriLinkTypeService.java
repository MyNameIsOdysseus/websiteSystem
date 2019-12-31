package net.minggao.cms.service;

import net.minggao.cms.model.FriLinkType;

import java.util.List;

public interface FriLinkTypeService {
    int setFritypeMessage(FriLinkType friLinkType);

    List getFriLinkTypeList(int currentpage, int pagesize, FriLinkType friLinkType);

    int getMaxNum();

    FriLinkType getFriLinkTypeSingle(Long frlinkId);

    int deleteMessage(Long frlinkId);
}
