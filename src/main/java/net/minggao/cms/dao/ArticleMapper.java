package net.minggao.cms.dao;

import net.minggao.cms.model.Article;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ArticleMapper {
    int deleteByPrimaryKey(Long articleId);

    int insert(Article record);

    List getMaxnum();

    int insertSelective(Article record);

    Article selectByPrimaryKey(Long articleId);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKeyWithBLOBs(Article record);

    int updateByPrimaryKey(Article record);

    List selectByChanId(@Param("chanid") Long chanid);

    List selectByName(@Param("username") Long username);

    Article selectByArticleName(@Param("newname") String newname,@Param("chanid") Long chanid);

    List getList(@Param("ImgName")  String ImgName, @Param("TitleName") Date TitleName, @Param("ChanName") Date ChanName, @Param("chanid") String chanid);

    List getLsitByI(@Param("ImgName")  String ImgName, @Param("TitleName") Date TitleName, @Param("ChanName") Date ChanName, @Param("chanid") String chanid,@Param("userid") String userid);

    List selectByAll(@Param("ImgName")  String ImgName, @Param("TitleName") Date TitleName, @Param("ChanName") Date ChanName, @Param("articlelist") List articlelist);
}