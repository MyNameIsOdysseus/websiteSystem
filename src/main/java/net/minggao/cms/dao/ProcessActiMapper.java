package net.minggao.cms.dao;

import net.minggao.cms.model.ProcessActi;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProcessActiMapper {
    int deleteByPrimaryKey(Long processactiId);

    int deleteByProcessid(@Param("processId") Long processid);

    int insert(ProcessActi record);

    int insertSelective(ProcessActi record);

    ProcessActi selectByPrimaryKey(Long processactiId);

    int updateByPrimaryKeySelective(ProcessActi record);

    int updateByPrimaryKey(ProcessActi record);

    List selectList(Long setid);

    List selectByProcessId(@Param("processId") Long processid);
}