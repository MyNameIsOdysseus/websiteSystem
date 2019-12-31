package net.minggao.cms.service.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import net.minggao.cms.config.ReflectUtil;
import net.minggao.cms.config.SimpleUtil;
import net.minggao.cms.dao.ChanMapper;
import net.minggao.cms.dao.UserMapper;
import net.minggao.cms.dao.UserRightMapper;
import net.minggao.cms.model.Chan;
import net.minggao.cms.model.User;
import net.minggao.cms.model.UserRight;
import net.minggao.cms.service.UserService;
import net.minggao.core.common.util.MD5Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static net.minggao.cms.config.SimpleUtil.getPageSize;

/**
 * @Author robin
 * @Description: TODO
 * @Param:
 * @Return:
 * @Create: 2018/6/4 22:18
 */

@Service("UserService")
@Transactional
public class UserServiceImpl implements UserService {

    public static int maxnum;

    @Resource
    UserMapper userMapper;

    @Resource
    UserRightMapper userRightMapper;

    @Resource
    ChanMapper chanMapper;

    @Override
    public String delmessage(Long userid) {
        int x= userMapper.deleteByPrimaryKey(userid);
        if(x>0){
            return "删除成功";
        }else{
            return "删除失败";
        }
    }

    @Override
    public List updateAllPeople() {
        return userMapper.selectAll(new User());
    }

    @Override
    public int setUserMessage(User user) {
        user.setSiteId(0l);
        User ue=null;
        if(user.getUserId() == null){
             ue= userMapper.selectUser(user.getUsername());
        }
        if(ue !=null && !ue.getUserId().equals(user.getUserId())){
            return 3;
        }

        if(user.getPassword()!=null){
            user.setPassword(MD5Util.getMd5(user.getPassword()));
        }

        if(user.getUpUserid()==null){
            user.setUpUserid(0l);
        }
        JSONArray list = JSON.parseArray(user.getChanList());
        JSONArray type =JSON.parseArray(user.getChantype());
        if(userMapper.selectByPrimaryKey(user.getUserId()) != null){
            userMapper.updateByPrimaryKeySelective(user);
            UserRight userdemo = new UserRight();
            List  list1 =  userRightMapper.selectByUser(user.getUserId());
            for(int x=0;x<list1.size();x++){
                UserRight u = (UserRight) list1.get(x);
                int temp=0;
                for(int y=0;y<list.size();y++){
                    Boolean mm= u.getChanId()==Long.valueOf((String)list.get(y));
                    if(mm){
                        temp=1;
                    }
                }
                if(temp==0){
                    userRightMapper.deleteByPrimaryKey(u.getRightId());
                }
            }

            for(int a=0;a<list.size();a++){
                UserRight u=userRightMapper.selectBynum(user.getUserId(),Long.valueOf((String)list.get(a)));
                if(userRightMapper.selectBynum(user.getUserId(),Long.valueOf((String)list.get(a)))!=null){
                    u.setRightScope((Integer)type.get(a));
                    userRightMapper.updateByPrimaryKeySelective(u);
                }else{
                    userdemo.setUserId(user.getUserId());
                    userdemo.setChanId(Long.valueOf((String)list.get(a)));
                    userdemo.setRightScope((Integer)type.get(a));
                    userdemo.setRightId(SimpleUtil.getuuid());
                    userRightMapper.insert(userdemo);
                }
            }
            return 1;
        }
        user.setUserId(SimpleUtil.getuuid());
        user.setUserStatus(0);
        userMapper.insert(user);
        UserRight userRight = new UserRight();
        for(int a=0;a<list.size();a++){
            userRight.setUserId(user.getUserId());
            userRight.setChanId(Long.valueOf((String)list.get(a)));
            userRight.setRightScope((Integer)type.get(a));
            userRight.setRightId(SimpleUtil.getuuid());
            userRightMapper.insert(userRight);
        }
        return 0;
    }

    @Override
    public List getUserList(int currentpage, int pageSize, User user) {
        List list = userMapper.selectAll(user);
        maxnum=list.size();
        return getPageSize(currentpage,pageSize,list);
    }

    @Override
    public int getMaxNum() {
        return maxnum;
    }

    @Override
    public User getSingleMessage(String userid) {
         User user = userMapper.selectByPrimaryKey(Long.valueOf(userid));
         List list = userRightMapper.selectByUser(Long.valueOf(userid));
         String chanlist="[";
         String typelist="[";
         for(int a=0;a<list.size();a++){
             if(a==list.size()-1){
                 UserRight userRight = (UserRight) list.get(a);
                 chanlist+=userRight.getChanId()+"]";
                 typelist+=userRight.getRightScope()+"]";
             }else{
                 UserRight userRight = (UserRight) list.get(a);
                 chanlist+=userRight.getChanId()+",";
                 typelist+=userRight.getRightScope()+",";
             }
         }
         user.setChanList(chanlist);
         user.setChantype(typelist);
         user=(User) ReflectUtil.reflect(user);
         return user;
    }

    @Override
    public User getUserCheck(String username) {
        User user =userMapper.selectUser(username);
        user=(User) ReflectUtil.reflect(user);
        return user;
    }

    @Override
    public String StartOrStop(User user) {
        int a=userMapper.updateByPrimaryKeySelective(user);
        if(a>0){
            return "操作成功";
        }else{
            return "操作失败";
        }
    }

    @Override
    public Object changePassword(User user) {
        user.setPassword(MD5Util.getMd5(user.getPassword()));
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public Object[] getUserRight(String userid) {


        List chanList = chanMapper.getChanOrder();
        int length=0;
        for(int a=0;a<chanList.size();a++){
            List list= userRightMapper.selectByZu(Long.valueOf(userid),((Chan)chanList.get(a)).getChanLevelCode());
            if(list.size()>0){
                length++;
            }
        }

        List<Chan>[] getChan = new List[length];
        int temp=0;
        for(int a=0;a<chanList.size();a++){
            List list= userRightMapper.selectByZu(Long.valueOf(userid),((Chan)chanList.get(a)).getChanLevelCode());
            if(list.size()>0){
                List<Chan> list1 = new ArrayList();
                for(int b=0;b<list.size();b++){
                        String xx=((UserRight)list.get(b)).getChanId().toString();
                        Chan chan = chanMapper.selectByChanLevelCode(xx);
                        list1.add(chan);
                }
                getChan[temp]=list1;
                temp++;
            }
        }

        return getChan;

    }

    @Override
    public int updateUserMessage(User user) {
        userMapper.updateByPrimaryKeySelective(user);
        return 1;
    }
}
