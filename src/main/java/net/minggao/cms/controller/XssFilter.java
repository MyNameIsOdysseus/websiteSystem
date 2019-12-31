package net.minggao.cms.controller;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author robin
 * @Description: TODO
 * @Param:
 * @Return:
 * @Create: 2018/6/4 22:18
 */
@WebFilter(filterName="xssFilter",urlPatterns="/*")
public class XssFilter implements Filter {

    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList( "/pluUploader","/TemplateController/ajaxRepeat","/TemplateController/upda","/TemplateController/lookMessage","/TemplateController/saveTemplate")));


    FilterConfig filterConfig = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //System.out.println("-----------过滤器执行初始化-----------");
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //System.out.println("-----------过滤器开始操作-----------");
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String path = request.getServletPath();
        String[] exclusionsUrls = {".js",".gif",".jpg",".png",".css",".ico"};
        boolean type= ALLOWED_PATHS.contains(path);
        if(type){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }else{
            for (String str : exclusionsUrls) {
                if (path.contains(str)) {
                    filterChain.doFilter(servletRequest,servletResponse);
                    return;
                }
            }
            filterChain.doFilter(new XssHttpServletRequestWrapper(request),servletResponse);
        }
    }

    @Override
    public void destroy() {
        //System.out.println("-----------过滤器开始销毁-----------");
        this.filterConfig = null;
    }
}
