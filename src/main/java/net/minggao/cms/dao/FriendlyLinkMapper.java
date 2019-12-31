package net.minggao.cms.dao;

import net.minggao.cms.model.FriendlyLink;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FriendlyLinkMapper {
    int deleteByPrimaryKey(Long linkId);

    int insert(FriendlyLink record);

    int insertSelective(FriendlyLink record);

    FriendlyLink selectByPrimaryKey(Long linkId);

    FriendlyLink selectByName(@Param(value ="name") String name);

    int updateByPrimaryKeySelective(FriendlyLink record);

    int updateByPrimaryKey(FriendlyLink record);

    List getFrilinkList(Long frlinkId);
}