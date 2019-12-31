package net.minggao.cms.service.serviceImpl;

import net.minggao.cms.config.ReflectUtil;
import net.minggao.cms.config.SimpleUtil;
import net.minggao.cms.dao.ArticleMapper;
import net.minggao.cms.dao.ChanMapper;
import net.minggao.cms.model.Article;
import net.minggao.cms.model.Chan;
import net.minggao.cms.service.chanService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static net.minggao.cms.config.SimpleUtil.getPageSize;

/**
 * @Author robin
 * @Description: TODO
 * @Param:
 * @Return:
 * @Create: 2018/6/4 22:18
 */

@Service("chanService")
public class chanServiceImpl implements chanService {

    public static int maxnum;


    @Resource
    ChanMapper chanMapper;

    @Resource
    ArticleMapper articleMapper;

    @Override
    public int setChanMessage(Chan chan) {


        Chan chan11=chanMapper.selectByPrimaryKey(chan.getChanId());
        boolean gf=false;
        if(chan11 != null){
            if(!chan11.getBelongChan().equals(chan.getBelongChan())){
                gf=true;
            }
            if(!chan11.getChanType().equals("1") && chan.getChanType().equals("1")) {
                List list = articleMapper.selectByChanId(chan.getChanId());
                if(list.size()>1){
                    return 9;
                }
            }
        }

        if(chan.getChanId()==null){
            chan.setChanId(SimpleUtil.getuuid());
        }
        //用于栏目名称的唯一性的校验  现予以注释
        /*if(chanMapper.selectByName(chan.getChanName())!=null && !chanMapper.selectByName(chan.getChanName()).getChanId().equals(chan.getChanId())){
            return 3;
        }*/

        List list = chanMapper.selectByBelong(chan.getChanId());
        if(list.size()>0 && !chan11.getBelongChan().equals(chan.getBelongChan())){
            return 8;
        }

        if(chan11==null ||(chan11 != null && gf)){
            if(chan.getBelongChan()==0){
                chan.setBelongChan(0L);
                String top= null;
                try {
                    top = chanMapper.selectAll();
                }catch (Exception e) {
                    e.printStackTrace();
                }
                if(top != null){
                    top=top.substring(0,4);
                    chan.setChanLevel(1);
                    chan.setChanLevelCode((Integer.parseInt(top)+1)+"");
                    chan.setChanCode(chan.getChanId()+"");
                }else{
                    chan.setChanLevel(1);
                    chan.setChanLevelCode("1001");
                    chan.setChanCode(chan.getChanId()+"");
                }

            }else{
                Chan testchan = chanMapper.selectByPrimaryKey(chan.getBelongChan());
                String top=chanMapper.selectMaxValue(Long.valueOf(testchan.getChanLevelCode()),testchan.getChanLevel()+1);

                String top2=chanMapper.selectMaxValue(Long.valueOf(testchan.getChanLevelCode()),testchan.getChanLevel());
                //boolean xxx=top.equals(testchan.getChanLevelCode());
                if(top != null){
                    chan.setChanLevelCode((Long.valueOf(top)+1)+"");
                    chan.setChanLevel(chan.getChanLevelCode().length()/4);
                }else{
                    chan.setChanLevelCode(top2+"1001");
                    chan.setChanLevel(chan.getChanLevelCode().length()/4);
                    if(chan.getChanLevelCode().length()/4>4){
                        return 4;
                    }
//                chan.setChanLevelCode(Long.parseLong(top.substring(0,4))+1+"");
//                chan.setChanLevel(testchan.getChanLevelCode().length()/4);
                }
                chan.setChanCode(chan.getChanId()+"");
            }
        }

        if(chanMapper.selectByPrimaryKey(chan.getChanId())!=null){
            if(!chan11.getChanType().equals("1") && chan.getChanType().equals("1")){
                chanMapper.updateByPrimaryKeySelective(chan);
                Chan chan12=chanMapper.selectByPrimaryKey(chan.getChanId());
                chan12.setProcessId(null);
                chanMapper.updateByPrimaryKey(chan12);
                return 1;
            }else {
                chanMapper.updateByPrimaryKeySelective(chan);
                return 1;
            }
        }else{
            chanMapper.insert(chan);
            return 0;
        }
    }

    @Override
    public List getPageMessage(int currentpage, int pagesize,Chan chan) {
        List list=null;
        if(chan.getChanName() !=null || chan.getChanType() != null || chan.getChanStatus() != null){
            list  = chanMapper.getChanList(chan);
        }else{
            List list1 = chanMapper.getChanOrder1();  //一级栏目
            List list2 = chanMapper.getChanOrder2();
            List list3 = chanMapper.getChanOrder3();
            List list4 = chanMapper.getChanOrder4();
            int  length =list1.size()+list2.size()+list3.size()+list4.size();
            list = new ArrayList(length);

            if(list1.size()>0){
                for(int a=0;a<list1.size();a++){
                    list.add(list1.get(a));
                    if(list2.size()>0){
                        for(int b=0;b<list2.size();b++){
                            Boolean m=((Chan)list1.get(a)).getChanLevelCode().equals(((Chan)list2.get(b)).getChanLevelCode().substring(0,((Chan)list1.get(a)).getChanLevelCode().length())) ;
                            if(m){
                                list.add(list2.get(b));
                                if(list3.size()>0){
                                    for(int c=0;c<list3.size();c++){
                                        Boolean n=((Chan)list2.get(b)).getChanLevelCode().equals(((Chan)list3.get(c)).getChanLevelCode().substring(0,((Chan)list2.get(b)).getChanLevelCode().length())) ;
                                        if(n){
                                            list.add(list3.get(c));
                                            if(list4.size()>0){
                                                for(int d=0;d<list4.size();d++){
                                                    Boolean x=((Chan)list3.get(c)).getChanLevelCode().equals(((Chan)list4.get(d)).getChanLevelCode().substring(0,((Chan)list3.get(c)).getChanLevelCode().length())) ;
                                                    if(x){
                                                        list.add(list4.get(d));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        maxnum=list.size();
        return getPageSize(currentpage,pagesize,list);
    }

    @Override
    public int getMaxNum() {
        //List list= chanMapper.getMaxNum();
        return maxnum;
    }

    @Override
    public String getStop(Long updateid) {
        Chan chan = new Chan();
        chan.setChanId(updateid);
        chan.setChanStatus(1);
        int x= chanMapper.updateByPrimaryKeySelective(chan);
        if(x>0){
            return "禁用成功";
        }else{
            return "禁用失败";
        }

    }

    @Override
    public String getStart(Long updateid) {
        Chan chan = new Chan();
        chan.setChanId(updateid);
        chan.setChanStatus(0);
        int x= chanMapper.updateByPrimaryKeySelective(chan);
        if(x>0){
            return "启用成功";
        }else{
            return "启用失败";
        }

    }

    @Override
    public String del(Long updateid) {
        Chan chan = chanMapper.selectByPrimaryKey(updateid);
        List list = chanMapper.selectByBelong(chan.getChanId());
        if(list.size()>0){
            return "该栏目下存在子栏目禁止删除";
        }



        List list1 = articleMapper.selectByChanId(chan.getChanId());
        if(list1.size()>0){
            Iterator<Article> it = list1.iterator();
            while(it.hasNext()){
                Article article = it.next();
                articleMapper.deleteByPrimaryKey(article.getArticleId());
            }
        }

        int x= chanMapper.deleteByPrimaryKey(updateid);
        if(x>0){
            return "删除成功";
        }else{
            return "删除失败";
        }

    }

    @Override
    public Chan lookMessage(Long updateid) {
        Chan shan= chanMapper.selectByPrimaryKey(updateid);
        shan=(Chan) ReflectUtil.reflect(shan);
        return shan;
    }

    @Override
    public List getAllMessage() {
        return chanMapper.getMaxNum();
    }

    @Override
    public Object[] getChanOrder() {
        List list=chanMapper.getChanOrder();
        List<Chan>[] getChan = new List[list.size()];
        for(int a=0;a<list.size();a++){
           Chan chan= (Chan) list.get(a);
            List list1= null;
            list1=chanMapper.getListOrder(chan.getChanLevelCode());
//            List list2= new ArrayList();
//            for(int b=0;b<list1.size();b++){
//                Chan chan2= (Chan) list1.get(b);
//                chan2=(Chan) ReflectUtil.reflect(chan2);
//                list2.add(chan2);
//            }
            getChan[a]= list1;
        }
        return getChan;
    }

    @Override
    public Chan getChanMessage(String chanName) {
        Chan shan= chanMapper.selectByName(chanName);
        shan=(Chan) ReflectUtil.reflect(shan);
        return  shan;
    }

    @Override
    public Chan getChanMessageById(String chanId) {
        Chan shan = chanMapper.selectByChanLevelCode(chanId);
        shan = (Chan) ReflectUtil.reflect(shan);
        return shan;
    }
}
