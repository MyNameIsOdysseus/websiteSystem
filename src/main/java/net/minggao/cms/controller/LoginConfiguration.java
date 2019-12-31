package net.minggao.cms.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author robin
 * @Description: TODO
 * @Param:
 * @Return:
 * @Create: 2018/6/4 22:18
 */
@Configuration
@Import(ConfigController.class)
public class LoginConfiguration implements WebMvcConfigurer {

    @Value("${server.servlet.context-path}")//定义默认值
    private String demourl;

    //实例化LoginInterceptor对象，并将其交给Spring容器
    @Bean
    public LoginInterceptor getLoginInterceptor(){
        return new LoginInterceptor(demourl);//有参构造方法进行属性赋值
    }



    @Override
    public void addInterceptors(InterceptorRegistry registry) {
            // 注册拦截器
            LoginInterceptor loginInterceptor = new LoginInterceptor(demourl);
            InterceptorRegistration loginRegistry = registry.addInterceptor(loginInterceptor);
            // 拦截路径
            loginRegistry.addPathPatterns("/**");
            // 排除路径
            loginRegistry.excludePathPatterns("/");
            loginRegistry.excludePathPatterns("/cms/login");
            loginRegistry.excludePathPatterns("/cms");
            loginRegistry.excludePathPatterns("/login");
            loginRegistry.excludePathPatterns("/postlogin");
            loginRegistry.excludePathPatterns("/loginout");
            // 排除资源请求
            loginRegistry.excludePathPatterns( "/**/*.css",
                    "/**/*.js", "/**/*.png", "/**/*.jpg",
                    "/**/*.jpeg", "/**/*.gif","/**/*.mp4",
                    "/**/*.bmp","/**/*.avi","/**/*.wmv",
                    "/**/*.txt","/**/*.xlsx","/**/*.xls",
                    "/**/*.doc","/**/*.docx","/**/*.ppt",
                    "/**/*.pptx","/**/*.pdf","/**/*.html"
        );
        
    }



}
