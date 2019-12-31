package net.minggao.cms.dao;

import net.minggao.cms.model.FriLinkType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FriLinkTypeMapper {
    int deleteByPrimaryKey(Long frlinkId);

    int insert(FriLinkType record);

    int insertSelective(FriLinkType record);

    FriLinkType selectByPrimaryKey(Long frlinkId);

    FriLinkType selectByName(@Param(value = "name") String name);

    int updateByPrimaryKeySelective(FriLinkType record);

    int updateByPrimaryKey(FriLinkType record);

    List selectAll(FriLinkType friLinkType);
}