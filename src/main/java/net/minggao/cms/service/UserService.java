package net.minggao.cms.service;

import net.minggao.cms.model.User;

import java.util.List;

public interface UserService {
    int setUserMessage(User user);

    List getUserList(int currentpage, int pageSize, User user);

    int getMaxNum();

    User getSingleMessage(String userid);

    String StartOrStop(User user);

    String delmessage(Long userid);

    List updateAllPeople();

    User getUserCheck(String username);

    Object changePassword(User user);

    Object[] getUserRight(String  userid);

    int updateUserMessage(User user);
}
