package net.minggao.cms.controller;

import net.minggao.cms.model.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author robin
 * @Description: TODO
 * @Param:
 * @Return:
 * @Create: 2018/6/4 22:18
 */
@Import(ConfigController.class)
public class LoginInterceptor implements HandlerInterceptor {


    private String serverHost;


    public LoginInterceptor(String serverHost) {
        this.serverHost=serverHost;
    }



    /**
     * 在请求被处理之前调用
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 检查每个到来的请求对应的session域中是否有登录标识
        User user = (User)request.getSession().getAttribute("UserSession");
        if (null == user ) {
            String type = request.getHeader("X-Requested-With");// XMLHttpRequest
            if (StringUtils.equals("XMLHttpRequest", type)) {
                response.setHeader("SESSIONSTATUS", "TIMEOUT");
                response.setHeader("CONTEXTPATH", serverHost+"/login");
            }else{
                request.getRequestDispatcher( "/login" ).forward(request,response);
            }
            return false;
        }
        String userName = user.getUsername();
        response.setHeader("SESSIONSTATUS", "OK");
        System.out.println("当前用户已登录，登录的用户名为： " + userName);
        return true;
    }

    /**
     * 在请求被处理后，视图渲染之前调用
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 在整个请求结束后调用
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    public String  go(){
        return "redirect:/cms/login";
    }

}
