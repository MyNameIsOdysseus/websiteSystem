package net.minggao.cms.service;

import net.minggao.cms.model.Contact;

import java.util.List;

public interface ContactService {

    List getContactList(int currentpage, int pagesize, Contact contact);

    int getMaxNum();

    int deleteMessage(Long contactId);

    Contact getSingleMessage(Long contactId);
}
