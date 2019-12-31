package net.minggao.cms.dao;

import net.minggao.cms.model.Site;

public interface SiteMapper {
    int deleteByPrimaryKey(Long siteId);

    int delete();

    int insert(Site record);

    int insertSelective(Site record);

    Site selectAll();

    Site selectByPrimaryKey(Long siteId);

    int updateByPrimaryKeySelective(Site record);

    int updateByPrimaryKey(Site record);
}