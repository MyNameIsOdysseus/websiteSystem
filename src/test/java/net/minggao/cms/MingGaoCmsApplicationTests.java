package net.minggao.cms;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //(classes = {MingGaoCmsApplication.class})// 指定启动类
public class MingGaoCmsApplicationTests {
	private Log log = (Log) LogFactory.getLog(MingGaoCmsApplicationTests.class);
//	private ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
	@Resource(name="JdbcTemplate")
	private JdbcTemplate dbutil;
	
	
	@Test
	public void contextLoads() {
//		init();
//		ApplicationHelper.setApplicationCntxt(applicationContext);
		Map ll = dbutil.queryForMap("select count(1) CNT from ts_user_m", null);
		log.info("ll : "+ll);
	}
	
//	private void init() {
//		ApplicationContext app = SpringApplication.run(MingGaoCmsApplicationTests.class, new String[] {}); 
//		ApplicationHelper.setApplicationContext(applicationContext); 
//	}

}
