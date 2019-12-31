package net.minggao.core.framework;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationHelper implements ApplicationContextAware {
//public class ApplicationHelper  {
	private static Log log = LogFactory.getLog(ApplicationHelper.class);

	    private static ApplicationContext applicationContext = null;


	    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	        if(applicationContext == null) {
	        	applicationContext = applicationContext;
	        }
	        log.info("---------------------------------------------------------------------");

	        log.info("---------------------------------------------------------------------");

	        log.info("---------------me.shijunjie.util.SpringUtil------------------------------------------------------");

	        log.info("========ApplicationContext配置成功,在普通类可以通过调用SpringUtils.getAppContext()获取applicationContext对象,applicationContext="+ApplicationHelper.applicationContext+"========");

	        log.info("---------------------------------------------------------------------");
	    }
	    
	    public static void setApplicationCntxt(ApplicationContext appContext) {
	    	if(applicationContext == null) {
	        	applicationContext = appContext;
	        }
	    }

	    //获取applicationContext
	    public static ApplicationContext getApplicationContext() {
	    	log.info("applicationContext : "+applicationContext);
	        return applicationContext;
	    }

	    //通过name获取 Bean.
	    public static Object getBean(String name){
	        return getApplicationContext().getBean(name);
	    }

	    //通过class获取Bean.
	    public static <T> T getBean(Class<T> clazz){
	        return getApplicationContext().getBean(clazz);
	    }

	    //通过name,以及Clazz返回指定的Bean
	    public static <T> T getBean(String name,Class<T> clazz){
	        return getApplicationContext().getBean(name, clazz);
	    }
}
