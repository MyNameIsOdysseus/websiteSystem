package net.minggao.cms.dao;

import net.minggao.cms.model.Chan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChanMapper {
    int deleteByPrimaryKey(Long chanId);

    int insert(Chan record);

    int insertSelective(Chan record);

    Chan selectByPrimaryKey(Long chanId);

    String selectAll();

    String selectMaxValue(@Param(value="param") Long va,@Param(value="num") int num);

    Chan selectByName(@Param(value="chanName") String channame);

    int updateByPrimaryKeySelective(Chan record);

    int updateByPrimaryKeyWithBLOBs(Chan record);

    int updateByPrimaryKey(Chan record);

    List getChanList(Chan chan);

    List  getMaxNum();

    List getChanOrder();

    List getChanOrder1();

    List getChanOrder2();

    List getChanOrder3();

    List getChanOrder4();

    List selectBytemp(@Param(value="param") Long va);

    List selectBytemp2(@Param(value="param") Long va);

    List getListOrder(@Param(value="param") String va);

    List selectByBelong(@Param(value="param") Long va);

    Chan selectByChanLevelCode(@Param(value="levelCode") String levelCode);


}