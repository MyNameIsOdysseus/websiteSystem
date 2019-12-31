package net.minggao.cms.service;

import net.minggao.cms.model.FriendlyLink;

import java.util.List;

public interface FriendlyLinkService {
    int setFriLinkMessage(FriendlyLink friendlyLink);

    List getFrilinkList(String frlinkId);

    FriendlyLink getSingleMessage(String linkId);

    int delSingleMessage(String linkId);
}
