package net.minggao.cms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


@SpringBootApplication
@ServletComponentScan   //启动器启动时，扫描本目录以及子目录带有的webservlet注解的
@MapperScan("net.minggao.cms.dao")
public class MingGaoCmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MingGaoCmsApplication.class, args);
		
//		ApplicationContext app = SpringApplication.run(MingGaoCmsApplication.class, args); 
//		ApplicationHelper.setApplicationCntxt(app); 
		//SpringApplication.run(WebApplication.class, args);
		
		
	}



}
