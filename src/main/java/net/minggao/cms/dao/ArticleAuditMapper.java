package net.minggao.cms.dao;

import net.minggao.cms.model.ArticleAudit;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ArticleAuditMapper {
    int deleteByPrimaryKey(Long auditid);

    int insert(ArticleAudit record);

    int insertSelective(ArticleAudit record);

    ArticleAudit selectByPrimaryKey(Long auditid);

    ArticleAudit selectByRecordId (@Param("newname")Long newname);

    int updateByPrimaryKeySelective(ArticleAudit record);

    int updateByPrimaryKey(ArticleAudit record);

    ArticleAudit selectByTitleName(@Param("newname")String newname);

    List getList(@Param("ImgName")  String ImgName, @Param("TitleName") Date TitleName, @Param("ChanName") Date ChanName, @Param("username") String username);

}