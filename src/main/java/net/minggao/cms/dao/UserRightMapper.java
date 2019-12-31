package net.minggao.cms.dao;

import net.minggao.cms.model.UserRight;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserRightMapper {
    int deleteByPrimaryKey(Long rightId);

    int insert(UserRight record);

    int insertSelective(UserRight record);

    UserRight selectByPrimaryKey(Long rightId);

    int updateByPrimaryKeySelective(UserRight record);

    int updateByPrimaryKey(UserRight record);

    UserRight selectBynum(@Param(value="userid") Long userid,@Param(value="chanid") long chanid);

    List selectAll();

    List selectByUser(@Param(value="userid") Long userid);

    List selectByZu(@Param(value="userid") Long userid,@Param(value="chanlevel") String chanlevel);

}