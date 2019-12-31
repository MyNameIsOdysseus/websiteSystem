package net.minggao.cms.controller;

import net.minggao.cms.model.User;
import net.minggao.cms.service.UserService;
import net.minggao.core.common.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author robin
 * @Description: TODO
 * @Param:
 * @Return:
 * @Create: 2018/6/4 22:18
 *
 */
@WebServlet("/postlogin")
public class LoginServlet extends HttpServlet {

    public void init(ServletConfig config) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    @Autowired
    UserService userService;

    @Value("${server.servlet.context-path}")
    private String demourl;

    @Override
    public void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {

        //int a=userService.getMaxNum();
        String username= httpServletRequest.getParameter("username");
        String password =httpServletRequest.getParameter("password");
        User user= null;
        if(username.equals("") || password.equals("")){
            if(username.equals("")){
                httpServletRequest.setAttribute("errormessage", "用户名不能为空");
                httpServletRequest.getRequestDispatcher("/login").forward(httpServletRequest, httpServletResponse);
                return;
            }else{
                httpServletRequest.setAttribute("errormessage", "密码不能为空");
                httpServletRequest.getRequestDispatcher("/login").forward(httpServletRequest, httpServletResponse);
                return;
            }
        }
        try {
            user = userService.getUserCheck(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(user ==null){
            httpServletRequest.setAttribute("errormessage", "账号或密码有误");
            httpServletRequest.getRequestDispatcher("/login").forward(httpServletRequest, httpServletResponse);
            return;
        }else if(user != null && user.getUserStatus()==1){
            httpServletRequest.setAttribute("errormessage", "账户信息已被禁用，请更换账号或联系管理员解冻");
            httpServletRequest.getRequestDispatcher("/login").forward(httpServletRequest, httpServletResponse);
            return;
        } else{
            String xxx=MD5Util.getMd5(password);
            System.out.println("这是加密之后的密码"+xxx);
            String yyy=user.getPassword();
            System.out.println("这是数据库中的密码"+yyy);
            Boolean type=MD5Util.getMd5(password).equals(user.getPassword());
            //System.out.println("这是密码进行对比之后的输出"+type);
            if(type){

                HttpSession oldSession = httpServletRequest.getSession();
                Enumeration<String> attributeNames = oldSession.getAttributeNames();
                Map<String, Object> attributeMap = new HashMap<String, Object>();
                while(attributeNames != null && attributeNames.hasMoreElements()){
                    String attributeName = attributeNames.nextElement();
                    attributeMap.put(attributeName, oldSession.getAttribute(attributeName));
                }
                oldSession.invalidate();
                HttpSession newSession = httpServletRequest.getSession(true);


                //httpServletRequest.getSession().setAttribute("UserSession", user);
                newSession.setAttribute("UserSession", user);

                user.setSiteId(0l);
                userService.updateUserMessage(user);
                if(user.getUsername().equals("admin")){
                    httpServletResponse.sendRedirect(demourl+"/index");
                }else{
                    httpServletResponse.sendRedirect(demourl+"/page/MG_changePassword");
                }
                return;
            }else{
                if(username.equals("admin")){
                    httpServletRequest.setAttribute("errormessage", "账号或密码有误");
                    httpServletRequest.getRequestDispatcher("/login").forward(httpServletRequest, httpServletResponse);
                    return;
                }else{
                    user.setSiteId(user.getSiteId()+1);
                    if(user.getSiteId()<5){
                        httpServletRequest.setAttribute("errormessage", "该账号已经输入错误"+user.getSiteId()+"次，还剩"+(5-user.getSiteId())+"次就将被禁用");
                        userService.updateUserMessage(user);
                    }else{
                        httpServletRequest.setAttribute("errormessage", "该账号已经输入错误"+user.getSiteId()+"次，已经被禁用");
                        user.setUserStatus(1);
                        user.setSiteId(0l);
                        userService.updateUserMessage(user);
                    }
                    httpServletRequest.getRequestDispatcher("/login").forward(httpServletRequest, httpServletResponse);
                    return;
                }
            }
        }
    }

    private void createNewSession(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession oldSession = request.getSession();
        Enumeration<String> attributeNames = oldSession.getAttributeNames();
        Map<String, Object> attributeMap = new HashMap<String, Object>();
        while(attributeNames != null && attributeNames.hasMoreElements()){
            String attributeName = attributeNames.nextElement();
            attributeMap.put(attributeName, oldSession.getAttribute(attributeName));
        }
        oldSession.invalidate();
        HttpSession newSession = request.getSession(true);
        for (String key : attributeMap.keySet()) {
            newSession.setAttribute(key, attributeMap.get(key));
        }
    }

}
