package net.minggao.cms.dao;

import net.minggao.cms.model.ArticleRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleRecordMapper {
    int deleteByPrimaryKey(Long recordId);

    int insert(ArticleRecord record);

    int insertSelective(ArticleRecord record);

    ArticleRecord selectByPrimaryKey(Long recordId);

    int updateByPrimaryKeySelective(ArticleRecord record);

    int updateByPrimaryKey(ArticleRecord record);

    int deleteByArticleId(@Param(value = "articleId") Long articleId);

    int selectByArticleId(@Param(value = "articleId") Long articleId);

    List selectByArticleIdList(@Param(value = "articleId") Long articleId);

    List selectByName(@Param(value="username") String username);

}