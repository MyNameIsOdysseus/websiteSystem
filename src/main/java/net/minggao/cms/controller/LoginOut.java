package net.minggao.cms.controller;

import net.minggao.cms.model.User;
import net.minggao.cms.service.UserService;
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

/**
 * @Author robin
 * @Description: TODO
 * @Param:
 * @Return:
 * @Create: 2018/6/4 22:18
 */

@WebServlet("/logout")
public class LoginOut extends HttpServlet {

    public void init(ServletConfig config) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    @Autowired
    UserService userService;

    @Value("${server.servlet.context-path}")
    private String demourl;

    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        //System.out.println("1111111111111111");
        //System.out.println("url地址"+demourl);
        if(session != null){
            User user = (User)session.getAttribute("UserSession");
            req.getSession().removeAttribute("UserSession");
            req.getSession().invalidate();
        }
        boolean xx=demourl.equals("/cms");
        //System.out.println("22222222222222222");
        boolean xy = demourl == "/cms";
        //System.out.println("33333333333333333");
        if(demourl.equals("/cms") || demourl == "/cms"){
            resp.sendRedirect("/cms/login");
        }else{
            resp.sendRedirect("/cms_mysql/login");
        }
        return;
    }
}
