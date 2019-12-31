package net.minggao.cms.dao;

import net.minggao.cms.model.Ppt;

import java.util.List;

public interface PptMapper {
    int deleteByPrimaryKey(Long pptId);

    int insert(Ppt record);

    int insertSelective(Ppt record);

    Ppt selectByPrimaryKey(Long pptId);

    List<Ppt> selectPptList(Ppt ppt);

    List<Ppt> getMaxnum();

    int updateByPrimaryKeySelective(Ppt record);

    int updateByPrimaryKey(Ppt record);
}