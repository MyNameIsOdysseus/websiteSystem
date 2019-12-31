package net.minggao.cms.service;

import net.minggao.cms.model.Article;

import java.util.List;

public interface ArticleService {
    int setMessage(Article article);

    List artilcelist(int currentpage, int pageSize, String ImgName, String TitleName, String ChanName, String chanid,String userid,String username);

    int getMaxnum();

    Article getMessageByName(Long articleid);

    String del(Long updateid);

    String setPubmessage(String numvalue,String numtype,String articleid);

    List getWaitAuditMessage (int currentpage, int pageSize, String ImgName, String TitleName, String ChanName, String username);

    int getAuditMaxnum();

    int getlookMaxnum();

    Article getAuditMessageOld(String auditid);

    Article getAuditMessage(String auditid);

    int delMessage(String auditid);

    String changeMessageAudit(Article article);

    String getAuditoldList(String auditid);

    List getoldAuditMessage (int currentpage, int pageSize, String ImgName, String TitleName, String ChanName, String username);
}
