package net.minggao.cms.dao;

import net.minggao.cms.model.Process;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProcessMapper {
    int deleteByPrimaryKey(Long processId);

    int insert(Process record);

    int insertSelective(Process record);

    Process selectByPrimaryKey(Long processId);

    int updateByPrimaryKeySelective(Process record);

    int updateByPrimaryKey(Process record);

    List selectAll(Process process);

    Process selectByName(@Param(value ="name") String name);
}