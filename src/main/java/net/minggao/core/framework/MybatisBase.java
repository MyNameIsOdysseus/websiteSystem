package net.minggao.core.framework;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public abstract class MybatisBase {
	@Resource
	private SqlSessionFactory sqlSessionFactory;
	
	protected SqlSession openSession(){
		return this.sqlSessionFactory.openSession();
	}
	
}
