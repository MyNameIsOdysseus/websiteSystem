package net.minggao.cms.dao;

import net.minggao.cms.model.Allattach;

public interface AllattachMapper {

    int insert(Allattach record);

    int insertSelective(Allattach record);

    int deleteByPrimaryKey(String filename);

    Allattach selectByid(String fileid);

}