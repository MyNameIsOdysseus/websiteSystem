package net.minggao.cms.service.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import net.minggao.cms.config.ReflectUtil;
import net.minggao.cms.config.SimpleUtil;
import net.minggao.cms.dao.*;
import net.minggao.cms.model.*;
import net.minggao.cms.service.ArticleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

import static net.minggao.cms.config.SimpleUtil.getPageSize;

/**
 * @Author robin
 * @Description: TODO
 * @Param:
 * @Return:
 * @Create: 2018/6/4 22:18
 */
@Service("/ArticleService")
@Transactional
public class ArticleServiceImpl implements ArticleService {

    public static int maxnum;

    public static int auditmaxnum;

    public static int lookmaxnum;

    @Resource
    ArticleMapper articleMapper;

    @Resource
    ContentMapper contentMapper;

    @Resource
    ChanMapper chanMapper;

    @Resource
    ArticleAuditMapper articleAuditMapper;

    @Resource
    ProcessActiMapper processActiMapper;

    @Resource
    UserMapper userMapper;

    @Resource
    UserRightMapper userRightMapper;

    @Resource
    ArticleRecordMapper articleRecordMapper;

    @Override
    public int setMessage(Article article) {

        long xxxxx=0;

        if(articleMapper.selectByArticleName(article.getNewssubhead(),article.getBelongChan())!=null && !articleMapper.selectByArticleName(article.getNewssubhead(),article.getBelongChan()).getArticleId().equals(article.getArticleId())){
            return 3;
        }

        boolean mk=article.getArticleId() != null;
        if(mk){
            article.setCreateUser(articleMapper.selectByPrimaryKey(article.getArticleId()).getCreateUser());
            article.setAuditStatus(articleMapper.selectByPrimaryKey(article.getArticleId()).getAuditStatus());
            int m=articleMapper.updateByPrimaryKeySelective(article);
            List list = articleRecordMapper.selectByArticleIdList(article.getArticleId());

            for(int vb=0;vb<list.size();vb++){
                ArticleAudit  articleAudit = articleAuditMapper.selectByRecordId(((ArticleRecord)list.get(vb)).getRecordId());
                if(articleAudit != null){
                    articleAudit.setAuditname(article.getNewssubhead());
                    articleAuditMapper.updateByPrimaryKeySelective(articleAudit);
                }
            }

            String responseMessage = null;
            try {
                responseMessage = SimpleUtil.testToUpdateConnection(article);
            } catch (Exception e) {
                //System.out.println("全文检索服务异常");
                e.printStackTrace();
            }
            //System.out.println("全文检索修改接口调用详情----------->:"+responseMessage);
            return 1;
        }
        article.setArticleId(SimpleUtil.getuuid());
        Content content = new Content();
        content.setContentId(SimpleUtil.getuuid());
        content.setArticleId(article.getArticleId());
        content.setContent(article.getArticleContentId());
        boolean x=article.getIsMutiy().equals("1");

        String responseMessage = null;
        try {
            responseMessage = SimpleUtil.testToConnection(article);
        } catch (Exception e) {
           // System.out.println("全文检索服务异常");
            e.printStackTrace();
        }
       // System.out.println("全文检索新增接口调用详情----------->:"+responseMessage);

        /**
         * 开始进行流程审核数据渲染
         * */
        Chan chan=chanMapper.selectByPrimaryKey(article.getBelongChan());
        List processActilist = processActiMapper.selectByProcessId(chan.getProcessId());
        if(processActilist.size()>0){
            article.setAuditStatus(1);
        }else{
            article.setAuditStatus(0);
        }

        ArticleRecord articleRecord = new ArticleRecord();
        for(int a=0;a<processActilist.size();a++){
            ProcessActi processActi = (ProcessActi)processActilist.get(a);
            if(processActi.getProcessactiUser()==0 && a==0){
                User user = userMapper.selectByPrimaryKey(article.getCreateUser());
                //User user1 =userMapper.selectUser(article.getAuthor());
                if(user==null){
                    return 8;
                }
                if(user.getUpUserid()==0){
                    articleRecordMapper.deleteByArticleId(article.getArticleId());
                    articleAuditMapper.deleteByPrimaryKey(xxxxx);
                    return 2;   //无领导
                }else{
                    articleRecord.setHandler(user.getUpUsername());
                    articleRecord.setHandleId(user.getUpUserid());
                }
            }else if(processActi.getProcessactiUser()==0 && a>0) {
                processActi = (ProcessActi)processActilist.get(a-1);
                if(processActi.getProcessactiUser()==0){
                    User user =userMapper.selectByPrimaryKey(articleRecord.getHandleId());
                    if(user.getUpUserid()==0){
                        articleRecordMapper.deleteByArticleId(article.getArticleId());
                        articleAuditMapper.deleteByPrimaryKey(xxxxx);
                        return 2;   //无领导
                    }else{
                        articleRecord.setHandler(user.getUpUsername());
                        articleRecord.setHandleId(user.getUpUserid());
                    }
                }else{
                    User user =userMapper.selectByPrimaryKey(processActi.getProcessactiUser());
                    if(user.getUpUserid()==0){
                        articleRecordMapper.deleteByArticleId(article.getArticleId());
                        articleAuditMapper.deleteByPrimaryKey(xxxxx);
                        return 2;   //无领导
                    }else{
                        articleRecord.setHandler(user.getUpUsername());
                        articleRecord.setHandleId(user.getUpUserid());
                    }
                }
            }else{
                articleRecord.setHandler(processActi.getProcessactiUsername());
                articleRecord.setHandleId(processActi.getProcessactiUser());
            }
            articleRecord.setRecordId(SimpleUtil.getuuid());
            articleRecord.setAuditSorted(a+1);
            articleRecord.setArticleId(article.getArticleId());
            articleRecordMapper.insert(articleRecord);
            if(a==0){
                ArticleAudit articleAudit = new ArticleAudit();
                articleAudit.setAuditid(SimpleUtil.getuuid());
                xxxxx=articleAudit.getAuditid();
                articleAudit.setRecordId(articleRecord.getRecordId());
                articleAudit.setAudituser(articleRecord.getHandler());
                articleAudit.setAuditresult(1);
                articleAudit.setAuditActid(1L);
                articleAudit.setAuditname(article.getNewssubhead());
                articleAudit.setPublisdate(article.getPublishdate());
                articleAudit.setSorted(article.getSortedcode());
                articleAudit.setChanname(chan.getChanName());
                articleAuditMapper.insert(articleAudit);
            }
        }
        /**
         * 流程审核数据渲染结束
         * */

        if(x){
            List list = articleMapper.selectByChanId(article.getBelongChan());
            if(list.size()>0){
                for(int a=0;a<list.size();a++){
                    Article ar = (Article)list.get(a);
                    articleMapper.deleteByPrimaryKey(ar.getArticleId());
                }
            }
        }
        article.setCreateTime(new Date());

        if(articleMapper.selectByArticleName(article.getNewssubhead(),article.getBelongChan())!=null && !articleMapper.selectByArticleName(article.getNewssubhead(),article.getBelongChan()).getArticleId().equals(article.getArticleId())){
            return 3;
        }

        articleMapper.insert(article);
        contentMapper.insert(content);
        return 0;
    }

    @Override
    public List artilcelist(int currentpage, int pageSize, String ImgName, String TitleName, String ChanName, String chanid,String userid,String username) {
        Date star = SimpleUtil.strToDate(TitleName);
        Date end  = SimpleUtil.strToDate(ChanName);
        List list =null;
        Chan chan = chanMapper.selectByPrimaryKey(Long.valueOf(chanid));
        Boolean type=false;
        if(!username.equals("admin")){
            UserRight userRight = userRightMapper.selectBynum(Long.valueOf(userid),Long.valueOf(chan.getChanLevelCode()));
            type = userRight.getRightScope()==1;
        }
        if(username.equals("admin") || type ){
            list= articleMapper.getList(ImgName,star,end,chanid);
            if(list.size()>0){
                Iterator<Article> it = list.iterator();
                while(it.hasNext()){
                    Article article = it.next();
                    article.setStylestatus(true);
                }
            }
        }else{
            list= articleMapper.getLsitByI(ImgName,star,end,chanid,userid);
            if(list.size()>0){
                Iterator<Article> it = list.iterator();
                while(it.hasNext()){
                    Article article = it.next();
                    articleMapper.deleteByPrimaryKey(article.getArticleId());
                    article.setStylestatus(type);
                }
            }
        }
        maxnum=list.size();
        return getPageSize(currentpage,pageSize,list);
    }

    @Override
    public int getMaxnum() {
        //int a=articleMapper.getMaxnum().size();
        return maxnum;
    }

    @Override
    public Article getMessageByName(Long articleid) {
        Article article = articleMapper.selectByPrimaryKey(articleid);
        article=(Article) ReflectUtil.reflect(article);
        return article;
    }

    @Override
    public String del(Long updateid) {
        int x= articleMapper.deleteByPrimaryKey(updateid);

        String responseMessage = SimpleUtil.testToDeleteConnection(updateid.toString());
        //System.out.println("全文检索删除接口调用详情----------->:"+responseMessage);

        if(x>0){
            return "删除成功";
        }else{
            return "删除失败";
        }
    }

    @Override
    public String setPubmessage(String numvalue, String numtype, String articleid) {
        JSONArray list = JSON.parseArray(numvalue);
        JSONArray type =JSON.parseArray(numtype);

        int isdel=1;
        int isupdatetime=1;
        int isreadzero=1;

        if(list.size()==0){
            return "您没有勾选推送栏目，推送失败！";
        }

        if(type.size()>0){
            for(int a=0;a<type.size();a++){
                boolean x= type.get(a).toString().equals("pubdel");
                if(x){
                    isdel=0;
                }
                x=type.get(a).toString().equals("pubnow");
                if(x){
                    isupdatetime=0;
                }
                x=type.get(a).toString().equals("pubzero");
                if(x){
                    isreadzero=0;
                }
            }
        }

        Article article = articleMapper.selectByPrimaryKey(Long.valueOf(articleid));
        if(isupdatetime==0){
            article.setPublishdate(new Date());
        }
        if(isreadzero==0){
            article.setSiteId(0l);
        }
        for(int a=0;a<list.size();a++){
            if(articleMapper.selectByArticleName(article.getNewssubhead(),Long.valueOf(list.get(a).toString()))!=null){
                Chan chan = chanMapper.selectByPrimaryKey(Long.valueOf(list.get(a).toString()));
                return "在栏目名称为："+chan.getChanName()+"下已经存在该信息。推送失败！";
            }
        }
        for(int a=0;a<list.size();a++){
            article.setBelongChan(Long.valueOf(list.get(a).toString()));
            article.setArticleId(SimpleUtil.getuuid());
            articleMapper.insert(article);
        }
        if(isdel==0){
            articleMapper.deleteByPrimaryKey(Long.valueOf(articleid));
        }
        return "推送成功";
    }

    @Override
    public List getWaitAuditMessage(int currentpage, int pageSize, String ImgName, String TitleName, String ChanName, String username) {

        Date star = SimpleUtil.strToDate(TitleName);
        Date end  = SimpleUtil.strToDate(ChanName);
        List list = articleAuditMapper.getList(ImgName,star,end,username);
//                articleMapper.getList(ImgName,star,end,username);;/;/
        auditmaxnum=list.size();
        return getPageSize(currentpage,pageSize,list);
    }

    @Override
    public int getAuditMaxnum() {
        return auditmaxnum;
    }

    @Override
    public Article getAuditMessage(String auditid) {
        ArticleAudit articleAudit = articleAuditMapper.selectByPrimaryKey(Long.valueOf(auditid));
        ArticleRecord articleRecord = articleRecordMapper.selectByPrimaryKey(articleAudit.getRecordId());
        int maxnum = articleRecordMapper.selectByArticleId(articleRecord.getArticleId());
        Article article = articleMapper.selectByPrimaryKey(articleRecord.getArticleId());
        if(maxnum==articleAudit.getAuditActid()){
            article.setCreateUser(0L);
        }else{
            article.setCreateUser(1L);
        }
        article=(Article) ReflectUtil.reflect(article);
        return article;
    }

    @Override
    public int delMessage(String auditid) {

        ArticleAudit articleAudit = articleAuditMapper.selectByPrimaryKey(Long.valueOf(auditid));
        ArticleRecord articleRecord = articleRecordMapper.selectByPrimaryKey(articleAudit.getRecordId());
        try {
            articleRecordMapper.deleteByArticleId(articleRecord.getArticleId());
            articleMapper.deleteByPrimaryKey(articleRecord.getArticleId());
            articleAuditMapper.deleteByPrimaryKey(Long.valueOf(auditid));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 1;
        }

        return 0;
    }

    @Override
    public String changeMessageAudit(Article article) {

        if(article.getArticleId() != null){
            articleMapper.updateByPrimaryKeySelective(article);
        }
        List list = articleRecordMapper.selectByArticleIdList(article.getArticleId());
        ArticleAudit articleAudit = articleAuditMapper.selectByTitleName(article.getNewssubhead());
        for(int a=0;a<list.size();a++){
            ArticleRecord articleRecord = (ArticleRecord)list.get(a);
            if(articleAudit.getAuditActid()==Long.valueOf(articleRecord.getAuditSorted()) && a<list.size()){
                articleRecord.setHandleresult("审核通过");
                articleRecord.setHandletime(new Date());
                articleRecordMapper.updateByPrimaryKey(articleRecord);
                if(a+1<=list.size()-1) {
                    articleRecord = (ArticleRecord) list.get(a + 1);
                    articleAudit.setAudituser(articleRecord.getHandler());
                    articleAudit.setAuditActid(Long.valueOf(articleRecord.getAuditSorted()));
                    articleAudit.setAuditresult(1);
                    articleAuditMapper.updateByPrimaryKeySelective(articleAudit);
                    return "审核成功";
                }else{
                    articleRecord.setHandleresult("审核通过");
                    articleRecord.setHandletime(new Date());
                    articleRecordMapper.updateByPrimaryKey(articleRecord);
                    articleAuditMapper.deleteByPrimaryKey(articleAudit.getAuditid());
                    article.setAuditStatus(0);
                    articleMapper.updateByPrimaryKeySelective(article);
                    return "操作成功";
                }
            }else if(articleAudit.getAuditActid()!=Long.valueOf(articleRecord.getAuditSorted()) && a<list.size()){
                continue;
            }else{
                articleRecord.setHandleresult("审核通过");
                articleRecord.setHandletime(new Date());
                articleRecordMapper.updateByPrimaryKeySelective(articleRecord);
                articleAuditMapper.deleteByPrimaryKey(articleAudit.getAuditid());
                article.setAuditStatus(0);
                //articleMapper.updateByPrimaryKey(article);
                articleMapper.updateByPrimaryKeySelective(article);
                return "操作成功";
            }
        }
        return null;
    }

    @Override
    public List getoldAuditMessage(int currentpage, int pageSize, String ImgName, String TitleName, String ChanName, String username) {
        Date star = SimpleUtil.strToDate(TitleName);
        Date end  = SimpleUtil.strToDate(ChanName);
        User user =userMapper.selectUser(username);
        List recordlist = articleRecordMapper.selectByName(username);
        List article = articleMapper.selectByName(user.getUserId());
        List<Long> longList = new ArrayList<>();
        if(recordlist.size()>0){
            for(int a=0;a<recordlist.size();a++){
                ArticleRecord articleRecord = (ArticleRecord)recordlist.get(a);
                longList.add(articleRecord.getArticleId());
            }
        }
        if(article.size()>0){
            for(int a=0;a<article.size();a++){
                Article article1 = (Article)article.get(a);
                longList.add(article1.getArticleId());
            }
        }
        HashSet set = new HashSet(longList);
        longList.clear();
        longList.addAll(set);
        if(longList.size()>0){
            article = articleMapper.selectByAll(ImgName,star,end,longList);
            lookmaxnum=article.size();
            return getPageSize(currentpage,pageSize,article);
        }else{
            longList=null;
            return null;
        }

    }

    @Override
    public int getlookMaxnum() {
        return lookmaxnum;
    }

    @Override
    public Article getAuditMessageOld(String auditid) {
        Article article = articleMapper.selectByPrimaryKey(Long.valueOf(auditid));
        article=(Article) ReflectUtil.reflect(article);
        return article;
    }

    @Override
    public String getAuditoldList(String auditid) {
        Article article =articleMapper.selectByPrimaryKey(Long.valueOf(auditid));
        List list =articleRecordMapper.selectByArticleIdList(Long.valueOf(auditid));
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        User user = userMapper.selectByPrimaryKey(article.getCreateUser());
        //String name = article.getAuthor()+" "+ sDateFormat.format(article.getPublishdate())+" 起草";
        String name = user.getUsername()+" "+ sDateFormat.format(article.getCreateTime())+" 起草";
        for(int a=0;a<list.size();a++){
            ArticleRecord articleRecord = (ArticleRecord) list.get(a);
            if(articleRecord.getHandleresult()!="" && articleRecord.getHandleresult()!= null){
                name+=","+articleRecord.getHandler()+" "+sDateFormat.format(articleRecord.getHandletime())+" 审核";
            }
        }
        return name;
    }
}
