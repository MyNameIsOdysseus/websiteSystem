package net.minggao.cms.dao;

import net.minggao.cms.model.Contact;

import java.util.List;

public interface ContactMapper {
    int deleteByPrimaryKey(Long contactId);

    int insert(Contact record);

    int insertSelective(Contact record);

    Contact selectByPrimaryKey(Long contactId);

    int updateByPrimaryKeySelective(Contact record);

    int updateByPrimaryKeyWithBLOBs(Contact record);

    int updateByPrimaryKey(Contact record);

    List selectAll(Contact record);
}