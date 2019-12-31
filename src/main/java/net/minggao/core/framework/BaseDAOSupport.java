package net.minggao.core.framework;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * <p>spring 4支持hibernate3.6以后的版本，同时支持hibernat5</p>
 * <p>本类使用的是spring管理的事务，针对hibernate session不需要进行打开和关闭操作！切记切记！</p>
 * <p>[本类主要用作本地测试类，在本地测试的时候需要修改为BaseDAOSupport.java进行测试]</p>
 * <p>hibernate3.6以后中已经废除 session.connection方法，要执行jdbc的操作，必须在doWork中法内部进行处理 [开发本地部署和测试用]</p>
 * <p>所有的查询都是使用的当前的 currentSession,只能在hibernate4.1中使用，hibernate4.2.21有bug，总是报session is closed</p>
 * <p>而且在不同的session中有可能出现相同的对象，所以在删除和修改之前，必须先merge后执行更新或删除。</p>
 * <p>hibernate4以后包括hibernate4，已经不支持使用HibernateDAOSupport和HibernateTemplate使用。</p>
 * 
 * <p>用法：</p>
 * <p>1、daoImpl类继承本类</p>
 * <p>2、使用
 * @autowried
 * BaseDAO baseDao;
 * 直接使用baseDao的方法进行数据持久层处理即可。
 * </p0>
 * @author         liumc
 * @version        V1.0  
 * @Date           2017-1-12 下午3:29:04
 */
@SuppressWarnings("all") 
@Repository(value="BaseDAO")
public class BaseDAOSupport implements BaseDAO{
	public static String databaseType = null;
	private Log log = LogFactory.getLog(BaseDAOSupport.class);
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	@Resource(name="sessionFactory")
	protected SessionFactory sessionFactory;

	/**
	 * 获取数据库类型：sqlserver，mysql，oracle  etc
	 * @return
	 */
	public String getDataBaseType() {
		if(null == databaseType) {
			String rslt = "sqlserver";
			Connection conn = null;
			String drivername ="";
			try {
				conn = jdbcTemplate.getDataSource().getConnection();
				DatabaseMetaData dbmd = conn.getMetaData();
				drivername = dbmd.getDriverName();
				log.info("drivername : "+drivername);
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				if(null != conn) this.close(conn);
			}
			if("mysql".equalsIgnoreCase(drivername)) rslt = "mysql";
			else if("sqlserver".equalsIgnoreCase(drivername) || "sql server".equalsIgnoreCase(drivername)) rslt = "sqlserver";
			else if("oracle".equalsIgnoreCase(drivername)) rslt = "oracle";
			databaseType = rslt;
			return rslt;
		}else {
			return databaseType;
		}
	}
	/**
     * gerCurrentSession 会自动关闭session，使用的是当前的session事务
     * 
     * @return
     */
    private Session getCurSession() {
    	Session session = null;
    	session = sessionFactory.getCurrentSession();
        return session;
    }

	public Object getObject(Class c, Serializable id) {
		Session session = getCurSession();
        Object rslt= session.get(c, id);
        return rslt;
	}

	public List<Object> getAllList(Class c) {
		String hql = " from " + c.getName();
        Session session = getCurSession();
        List<Object> rslt= session.createQuery(hql).list();
        return rslt;
	}

	public Long getTotalCount(Class c) {
        String hql = "select count(*) from " + c.getName();
		Session session = this.getCurSession();
        Long count = (Long) session.createQuery(hql).uniqueResult();
        return count != null ? count.longValue() : 0;
        
	}

    public void evict(Object hibernatePO){
    	Session session = null;
        try {
        	session = this.getCurSession();
            session.evict(hibernatePO);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        }
    }
	
    /**
     * 20180426 add transaction 解决保存后获取对应的id为<delayed:0>的错误
     */
	public Serializable save(Object po) {
		Serializable rslt = null;
        Session session = null;
        try {
        	session = this.getCurSession();
            rslt = session.save(session.merge(po));
            session.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        	return rslt;
        }
	}
	
 	protected Serializable saveObject(Object po){
		return this.save(po);
	}

	public boolean update(Object po) {
		boolean rslt = false;
		 Session session = null;
	        try {
	        	session = this.getCurSession();
	        	session.update(session.merge(po));
	            session.flush();
	            rslt = true;
	        } catch (Exception e) {
	            e.printStackTrace();
	            rslt = false;
	        }finally{
	        	return rslt;
	        }
	}
	
	protected boolean updateObject(Object po){
		return this.update(po);
	}
	
	public boolean delete(Object po) {
		boolean rslt = false;
		Session session = null;
        try {
        	session = this.getCurSession();
        	session.delete(session.merge(po));
            session.flush();
            rslt = true;
        } catch (Exception e) {
            e.printStackTrace();
            rslt = false;
        }finally{
        	return rslt;
        }
	}
	protected boolean removeObject(Object po){
		return this.delete(po);
	}
	protected boolean removeObject(Class clazz,Serializable id){
		return this.delete(this.getObject(clazz, id));
	}

	public boolean delete(Class c, Long id) {
		boolean rslt = false;
		Session session = null;
        try {
        	session = this.getCurSession();
        	Object po = session.get(c, id);
        	session.delete(session.merge(po));
            session.flush();
            rslt = true;
        } catch (Exception e) {
            e.printStackTrace();
            rslt = false;
        }finally{
            
        	return rslt;
        }
	}

	public boolean delete(Class c, String[] ids) {
		boolean rslt = false;
		Session session = null;
        try {
        	session = this.getCurSession();
        	for (String id : ids) {
                Object obj = session.get(c, Long.valueOf(id));
                if (obj != null) {
                	session.delete(session.merge(obj));
                }
            }
            session.flush();
            rslt = true;
        } catch (Exception e) {
            e.printStackTrace();
            rslt = false;
        }finally{
        	ids = null;
        	return rslt;
        }
	}

	public List queryByPage(String hql, int firstResult, int maxResults) {
		Session session = this.getCurSession();
		Query query = session.createQuery(hql);
    	query.setFirstResult(firstResult);
    	query.setMaxResults(maxResults);
    	List rslt = query.list();
    	return rslt;
	}

    public List queryByPage(String hql,Object[] namedParaValues,int firstResult,int maxResults){
		Session session = this.getCurSession();
		Query query = session.createQuery(hql);
    	query.setFirstResult(firstResult);
    	query.setMaxResults(maxResults);
    	if((namedParaValues!=null) || (namedParaValues!=null)){
    		int arrsize = namedParaValues.length;
    		for(int i=0;i<arrsize;i++){
    			query.setParameter(i, namedParaValues[i]);
    		}
    	}
    	List rslt = query.list();
    	namedParaValues = null;
    	return rslt;
    }
    
	public List queryByPage(String hql, String[] namedParams,Object[] namedParaValues, int firstResult, int maxResults) {
		Session session = this.getCurSession();
		Query query = session.createQuery(hql);
    	query.setFirstResult(firstResult);
    	query.setMaxResults(maxResults);
    	if((namedParams!=null) || (namedParaValues!=null)){
    		int arrsize = namedParaValues.length;
    		for(int i=0;i<arrsize;i++){
    			query.setParameter(namedParams[i], namedParaValues[i]);
    		}
    	}
    	List rslt = query.list();
    	namedParams = null;
    	namedParaValues = null;
    	return rslt;
	}

	/**
	 * @deprecated
	 */
	public List query(String hql) {
		Session session = this.getCurSession();
		Query query = session.createQuery(hql);
    	List rslt = query.list();
    	return rslt;
	}

	public List query(String hql,Object[] namedParaValues){
		Session session = this.getCurSession();
		Query query = session.createQuery(hql);
    	if((namedParaValues!=null) || (namedParaValues!=null)){
    		int arrsize = namedParaValues.length;
    		for(int i=0;i<arrsize;i++){
    			query.setParameter(i, namedParaValues[i]); //?
    		}
    	}
    	List rslt = query.list();
    	namedParaValues = null;
    	return rslt;
	}
	
	public List query(String hql, String[] namedParams, Object[] namedParaValues ) {
		Session session = this.getCurSession();
		Query query = session.createQuery(hql);
    	if((namedParams!=null) || (namedParaValues!=null)){
    		int arrsize = namedParaValues.length;
    		for(int i=0;i<arrsize;i++){
    			query.setParameter(namedParams[i], namedParaValues[i]);
    		}
    	}
    	List rslt = query.list();
    	namedParams = null;
    	namedParaValues = null;
    	return rslt;
	}

	public long queryCount(String hsql, Object[] paramValues) {
		long rslt = 0;
		String sql2 = "select count(*) ";
		sql2 += hsql.substring(hsql.toLowerCase().indexOf(" from "),hsql.length());
		if(sql2.toLowerCase().indexOf(" order by")>-1){
			sql2  = sql2.substring(0,sql2.toLowerCase().indexOf(" order by"));
		}

		Session session = this.getCurSession();
		Query query = session.createQuery(sql2);
    	if((paramValues!=null) || (paramValues!=null)){
    		int arrsize = paramValues.length;
    		for(int i=0;i<arrsize;i++){
    			query.setParameter(i, paramValues[i]);
    		}
    	}
    	
		List list = query.list();
		if((list!=null) && (list.size()==1)){
			rslt = Long.valueOf(String.valueOf(list.get(0))).longValue();
		}
		paramValues = null;
		return rslt;
	}
	
	public long queryCount(String hsql, String[] paramNames,Object[] paramValues) {
		long rslt = 0;
		String sql2 = "select count(*) ";
		sql2 += hsql.substring(hsql.toLowerCase().indexOf(" from "),hsql.length());
		if(sql2.toLowerCase().indexOf(" order by")>-1){
			sql2  = sql2.substring(0,sql2.toLowerCase().indexOf(" order by"));
		}

		Session session = this.getCurSession();
		Query query = session.createQuery(sql2);
    	if((paramNames!=null) || (paramNames!=null)){
    		int arrsize = paramValues.length;
    		for(int i=0;i<arrsize;i++){
    			query.setParameter(paramNames[i], paramValues[i]);
    		}
    	}
    	
		List list = query.list();
		if((list!=null) && (list.size()==1)){
			rslt = Long.valueOf(String.valueOf(list.get(0))).longValue();
		}
		paramNames = null;
		paramValues = null;
		return rslt;
	}

	public long queryCount(String hsql) {
		long rslt = 0;
		String sql2 = "select count(*) ";
		sql2 += hsql.substring(hsql.toLowerCase().indexOf(" from "),hsql.length());
		if(sql2.toLowerCase().indexOf(" order by")>-1){
			sql2  = sql2.substring(0,sql2.toLowerCase().indexOf(" order by"));
		}

		Session session = this.getCurSession();
		Query query = session.createQuery(sql2);
    	    	
		List list = query.list();
		if((list!=null) && (list.size()==1)){
			rslt = Long.valueOf(String.valueOf(list.get(0))).longValue();
		}
		list = null;
		return rslt;
	}

	public void executeUpdate(String hsql, String[] paramNameds,Object[] paraValues) {
		Session session = this.getCurSession();
		Query query = session.createQuery(hsql);
		int size = (null==paramNameds)?0:paramNameds.length;
		for(int i=0;i<size;i++){
			query.setParameter(paramNameds[i], paraValues[i]);
		}
		int exeRslt = query.executeUpdate();
		paramNameds = null;
		paraValues = null;
        
		
	}

	public void executeUpdate(String hsql, Object[] paraValues) {
		Session session = this.getCurSession();
		Query query = session.createQuery(hsql);
		int size = (null==paraValues)?0:paraValues.length;
		for(int i=0;i<size;i++){
			query.setParameter(i, paraValues[i]);
		}
		int exeRslt = query.executeUpdate();
		paraValues = null;
	}

	public List<Map<String, Object>> queryJdbcForMapList(final String sql, final String[] paramsNamed,final Object[] paramsValue) {
		final List<Map<String, Object>> rslt = new ArrayList<Map<String, Object>>(); 
		
		String tmpsql = sql;
		for(String ss:paramsNamed) {
			tmpsql = tmpsql.replaceAll(":"+ss, "?");
		}
		final String tsql2 = tmpsql;
		Session session = this.getCurSession();
		session.doWork(new Work(){

			public void execute(Connection conn) throws SQLException {
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				try{
					pstmt = conn.prepareStatement(tsql2);
					if(paramsValue!=null){
						for(int i=0;i<paramsValue.length;i++){
							pstmt.setObject(i+1, paramsValue[i]);
						}
					}
					rs = pstmt.executeQuery();
					ResultSetMetaData rsmd = rs.getMetaData();
					int cols = rsmd.getColumnCount();
					String columnName = null;
					while(rs.next()){
						Map<String ,Object> map = new HashMap<String,Object>();
						for(int k=0;k<rsmd.getColumnCount();k++){
							columnName = rsmd.getColumnName(k+1); //属性名
							int columnType = rsmd.getColumnType(k+1); //属性类型
							if(columnType == java.sql.Types.DATE ||
							   columnType == java.sql.Types.TIMESTAMP
							){ //日期类型
								if(rs.getTimestamp(k+1)==null) map.put(columnName, null);
								else map.put(columnName, new java.util.Date(rs.getTimestamp(k+1).getTime()));
							}else if(columnType == java.sql.Types.INTEGER){
								map.put(columnName, new Integer(rs.getInt(k+1)));
							}else if(columnType == java.sql.Types.CLOB){
								map.put(columnName, rs.getString(k+1));
							}else if(columnType == java.sql.Types.BLOB){
								map.put(columnName, rs.getBytes(k+1));
							}else {
								map.put(columnName, rs.getObject(k+1));
							}
						}
						rslt.add(map);
					}
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					close(rs);
					close(pstmt);
					//conn有hibernate自己控制是否关闭，这里不需要关闭
				}
			}
			
		});
		return rslt;
	}

	public List<Map<String, Object>> queryJdbcForMapList(final String sql, final Object[] paramsValue) {
		return this.jdbcTemplate.queryForList(sql, paramsValue);
	}

	public long queryJdbcForLong(final String sql, final String[] paramsNamed,final Object[] paramsValue) {
		Long rslt = null;
		String tmpsql = sql;
		if(!StringUtils.isEmpty(sql) && (paramsNamed!=null)) {
			for(String s:paramsNamed) {
				tmpsql = tmpsql.replaceAll(":"+s, "?");
			}
		}
		final String exeSQL = tmpsql;
		Session session = this.getCurSession();
		rslt = session.doReturningWork(new ReturningWork<Long>() {
			public Long execute(Connection conn) throws SQLException {
				Long queryRslt = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				try{
					pstmt = conn.prepareStatement(exeSQL);
					if(paramsValue!=null){
						int i=0;
						for(Object  obj:paramsValue){
							pstmt.setObject(i+1, paramsValue[i]);
							i++;
						}
					}
					rs = pstmt.executeQuery();
					
					while(rs.next()){
						queryRslt = rs.getLong(1);
						break;
					}
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					close(rs);
					close(pstmt);
					//conn有hibernate自己控制是否关闭，这里不需要关闭
				}
				return queryRslt;
			}
		});
		return rslt;
	}

	public long queryJdbcForLong(final String sql, final Object[] paramsValue) {
		Long rslt = null;
		final String exeSQL = sql;
		Session session = this.getCurSession();
		rslt = session.doReturningWork(new ReturningWork<Long>() {
			public Long execute(Connection conn) throws SQLException {
				Long queryRslt = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				try{
					pstmt = conn.prepareStatement(exeSQL);
					if(paramsValue!=null){
						int i=0;
						for(Object  obj:paramsValue){
							pstmt.setObject(i+1, paramsValue[i]);
							i++;
						}
					}
					rs = pstmt.executeQuery();
					
					while(rs.next()){
						queryRslt = rs.getLong(1);
						break;
					}
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					close(rs);
					close(pstmt);
					//conn有hibernate自己控制是否关闭，这里不需要关闭
				}
				return queryRslt;
			}
		});
		return rslt;
	}

	public Map queryJdbcForMap(final String sql,final String[] paramsNamed,final Object[] paramsValue) {
		Map rslt = new HashMap();
		String tmpsql = sql;
		if(!StringUtils.isEmpty(sql) && (paramsNamed!=null)) {
			for(String s:paramsNamed) {
				tmpsql = tmpsql.replaceAll(":"+s, "?");
			}
		}
		final String exeSQL = tmpsql;
		Session session = this.getCurSession();
		rslt = session.doReturningWork(new ReturningWork<Map>() {
			public Map execute(Connection conn) throws SQLException {
				Map queryRslt = new HashMap();
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				try{
					pstmt = conn.prepareStatement(exeSQL);
					if(paramsValue!=null){
						int i=0;
						for(Object  obj:paramsValue){
							pstmt.setObject(i+1, paramsValue[i]);
							i++;
						}
					}
					rs = pstmt.executeQuery();
					
					while(rs.next()){
						ResultSetMetaData rsmd = rs.getMetaData();
						int colscnt = rsmd.getColumnCount();
						String columnName = "";
						for(int k=0;k<colscnt;k++) {
							columnName = rsmd.getColumnName(k+1);
							queryRslt.put(columnName, rs.getObject(k+1));
						}
						break;
					}
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					close(rs);
					close(pstmt);
					//conn有hibernate自己控制是否关闭，这里不需要关闭
				}
				return queryRslt;
			}
		});
		return rslt;
	}

	public Map queryJdbcForMap(final String sql, final Object[] paramsValue) {
		Map rslt = new HashMap();
		final String exeSQL = sql;
		Session session = this.getCurSession();
		rslt = session.doReturningWork(new ReturningWork<Map>() {
			public Map execute(Connection conn) throws SQLException {
				Map queryRslt = new HashMap();
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				try{
					pstmt = conn.prepareStatement(exeSQL);
					if(paramsValue!=null){
						int i=0;
						for(Object  obj:paramsValue){
							pstmt.setObject(i+1, paramsValue[i]);
							i++;
						}
					}
					rs = pstmt.executeQuery();
					
					while(rs.next()){
						ResultSetMetaData rsmd = rs.getMetaData();
						int colscnt = rsmd.getColumnCount();
						String columnName = "";
						for(int k=0;k<colscnt;k++) {
							columnName = rsmd.getColumnName(k+1);
							queryRslt.put(columnName, rs.getObject(k+1));
						}
						break;
					}
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					close(rs);
					close(pstmt);
					//conn有hibernate自己控制是否关闭，这里不需要关闭
				}
				return queryRslt;
			}
		});
		return rslt;
	}

	public String queryJdbcForString(final String sql,final String[] paramsNamed,final Object[] paramsValue) {
		String rslt = null;
		String tmpsql = sql;
		if(!StringUtils.isEmpty(sql) && (paramsNamed!=null)) {
			for(String s:paramsNamed) {
				tmpsql = tmpsql.replaceAll(":"+s, "?");
			}
		}
		final String exeSQL = tmpsql;
		Session session = this.getCurSession();
		rslt = session.doReturningWork(new ReturningWork<String>() {
			public String execute(Connection conn) throws SQLException {
				String queryRslt = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				try{
					pstmt = conn.prepareStatement(exeSQL);
					if(paramsValue!=null){
						int i=0;
						for(Object  obj:paramsValue){
							pstmt.setObject(i+1, paramsValue[i]);
							i++;
						}
					}
					rs = pstmt.executeQuery();
					
					while(rs.next()){
						queryRslt = rs.getString(1);
						break;
					}
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					close(rs);
					close(pstmt);
					//conn有hibernate自己控制是否关闭，这里不需要关闭
				}
				return queryRslt;
			}
		});
		return rslt;
	}

	public String queryJdbcForString(final String sql,final Object[] paramsValue) {
		String rslt = null;
		final String exeSQL = sql;
		Session session = this.getCurSession();
		rslt = session.doReturningWork(new ReturningWork<String>() {
			public String execute(Connection conn) throws SQLException {
				String queryRslt = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				try{
					pstmt = conn.prepareStatement(exeSQL);
					if(paramsValue!=null){
						int i=0;
						for(Object  obj:paramsValue){
							pstmt.setObject(i+1, paramsValue[i]);
							i++;
						}
					}
					rs = pstmt.executeQuery();
					
					while(rs.next()){
						queryRslt = rs.getString(1);
						break;
					}
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					close(rs);
					close(pstmt);
					//conn有hibernate自己控制是否关闭，这里不需要关闭
				}
				return queryRslt;
			}
		});
		return rslt;
	}

	public int executeJdbcSQL(final String sql,final String[] paramsNamed,final Object[] paramsValue) {
		Integer rslt = null;
		String tmpsql = sql;
		if(!StringUtils.isEmpty(sql) && (paramsNamed!=null)) {
			for(String s:paramsNamed) {
				tmpsql = tmpsql.replaceAll(":"+s, "?");
			}
		}
		final String exeSQL = tmpsql;
		Session session = this.getCurSession();
		rslt = session.doReturningWork(new ReturningWork<Integer>() {
			public Integer execute(Connection conn) throws SQLException {
				Integer queryRslt = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				try{
					pstmt = conn.prepareStatement(exeSQL);
					if(paramsValue!=null){
						int i=0;
						for(Object  obj:paramsValue){
							pstmt.setObject(i+1, paramsValue[i]);
							i++;
						}
					}
					queryRslt = pstmt.executeUpdate();
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					close(rs);
					close(pstmt);
					//conn有hibernate自己控制是否关闭，这里不需要关闭
				}
				return queryRslt;
			}
		});
		return rslt;
	}

	public int executeJdbcSQL(final String tmpsql,final Object[] paramsValue) {
		Integer rslt = null;
		final String exeSQL = tmpsql;
		Session session = this.getCurSession();
		rslt = session.doReturningWork(new ReturningWork<Integer>() {
			public Integer execute(Connection conn) throws SQLException {
				Integer queryRslt = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				try{
					pstmt = conn.prepareStatement(exeSQL);
					if(paramsValue!=null){
						int i=0;
						for(Object  obj:paramsValue){
							pstmt.setObject(i+1, paramsValue[i]);
							i++;
						}
					}
					queryRslt = pstmt.executeUpdate();
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					close(rs);
					close(pstmt);
					//conn有hibernate自己控制是否关闭，这里不需要关闭
				}
				return queryRslt;
			}
		});
		return rslt;
	}
	
	public int executeJdbcSQL(final String tmpsql) {
		Integer rslt = null;
		Session session = this.getCurSession();
		rslt = session.doReturningWork(new ReturningWork<Integer>() {
			public Integer execute(Connection conn) throws SQLException {
				Integer queryRslt = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				try{
					pstmt = conn.prepareStatement(tmpsql);
					queryRslt = pstmt.executeUpdate();
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					close(rs);
					close(pstmt);
					//conn有hibernate自己控制是否关闭，这里不需要关闭
				}
				return queryRslt;
			}
		});
		return rslt;
	}

	public boolean executeJdbcSQLBatch(final List<String> sql,final List<String[]> paramsNamed,final List<Object[]> paramsValue) {
		boolean rslt = false;
		Session session = this.getCurSession();
		rslt = session.doReturningWork(new ReturningWork<Boolean>() {
			public Boolean execute(Connection conn) throws SQLException {
				Boolean queryRslt = false;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				try{
					conn.setAutoCommit(false);
					int i=0;
					for(String sql2:sql) {
						if(!StringUtils.isEmpty(sql2) && (paramsNamed.get(i)!=null)) {
							for(String s:paramsNamed.get(i)) {
								sql2 = sql2.replaceAll(":"+s, "?");
							}
						}
						log.info("sql2 : "+sql2);
						pstmt = conn.prepareStatement(sql2);
						Object[] tmp = paramsValue.get(i);
						if(tmp!=null){
							int j=0;
							for(Object  obj:tmp){
								pstmt.setObject(j+1, obj);
								j++;
							}
						}
						pstmt.executeUpdate();
						i++;
					}
					conn.commit();
					queryRslt = true;
				}catch(Exception e){
					conn.rollback();
					e.printStackTrace();
					queryRslt = false;
				}finally{
					conn.setAutoCommit(true);
					close(rs);
					close(pstmt);
					//conn有hibernate自己控制是否关闭，这里不需要关闭
				}
				return queryRslt;
			}
		});
		return rslt;
	}
	
	public boolean executeJdbcSQLBatch(final String sql,final String[] namedArr,final List<Object[]> valueList){
		boolean rslt = false;
		Session session = this.getCurSession();
		rslt = session.doReturningWork(new ReturningWork<Boolean>() {
			public Boolean execute(Connection conn) throws SQLException {
				Boolean queryRslt = false;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				try{
					conn.setAutoCommit(false);
					int i=0;
					String tmpsql = sql;
					for(String ss:namedArr) {
						tmpsql = tmpsql.replaceAll(":"+ss, "?");
					}
					for(Object[] valueArr:valueList) {
						pstmt = conn.prepareStatement(tmpsql);
						int j=0;
						for(Object  obj:valueArr){
							pstmt.setObject(j+1, obj);
							j++;
						}
						pstmt.executeUpdate();
					}
					conn.commit();
					queryRslt = true;
				}catch(Exception e){
					e.printStackTrace();
					queryRslt = false;
					conn.rollback();
				}finally{
					conn.setAutoCommit(true);
					close(rs);
					close(pstmt);
					//conn有hibernate自己控制是否关闭，这里不需要关闭
				}
				return queryRslt;
			}
		});
		return rslt;
	}

	
	public boolean executeJdbcSQLBatch(final List<String> sql,final List<Object[]> paramsValue) {
		boolean rslt = false;
		Session session = this.getCurSession();
		rslt = session.doReturningWork(new ReturningWork<Boolean>() {
			public Boolean execute(Connection conn) throws SQLException {
				Boolean queryRslt = false;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				try{
					conn.setAutoCommit(false);
					int i=0;
					for(String sql2:sql) {
						pstmt = conn.prepareStatement(sql2);
						Object[] tmp = paramsValue.get(i);
						if(tmp!=null){
							int j=0;
							for(Object  obj:tmp){
								pstmt.setObject(j+1, obj);
								j++;
							}
						}
						pstmt.executeUpdate();
						i++;
					}
					conn.commit();
					queryRslt = true;
				}catch(Exception e){
					e.printStackTrace();
					queryRslt = false;
					conn.rollback();
				}finally{
					conn.setAutoCommit(true);
					close(rs);
					close(pstmt);
					//conn有hibernate自己控制是否关闭，这里不需要关闭
				}
				return queryRslt;
			}
		});
		return rslt;
	}
	
	
	/**
     * 通过存储过程查询(单结果集)
     * @param sql 查询jdbc sql-->存储过程对应的sql
     * @param params 参数
     * @param columnNum 返回的列数
     * @return List<Map<String,Object>>
     */
    public List<Map<String, Object>> queryByProcedureSingle(final String sql,final Object[] params){
        final List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        try {
            Session session = this.getCurSession();
            session.doWork(new Work(){
              public void execute(Connection conn) throws SQLException {
                  CallableStatement cs=null;
                  ResultSet rs=null;
                  cs = conn.prepareCall(sql);
                    for(int i=1;i<=params.length;i++){
                        cs.setObject(i, params[i-1]);//设置参数
                    }
                    rs = cs.executeQuery();
                    ResultSetMetaData metaData = rs.getMetaData();
                    int colCount=metaData.getColumnCount();
                    while(rs.next()){
                        Map<String, Object> map = new HashMap<String, Object>();
                        for(int i=1;i<=colCount;i++){
                            String colName=metaData.getColumnName(i);
                            map.put(colName, rs.getObject(colName));
                        }
                        result.add(map);
                    }
                    close( cs, rs);
              }
           });
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    /**
     * 通过存储过程查询(多结果集)
     * @param sql 查询sql
     * @param params 参数
     * @param columnNum 返回的列数
     * @return
     */
    public List<List<Map<String, Object>>> queryByProcedureMulti(final String sql,final Object[] params){
        final List<List<Map<String, Object>>> result = new ArrayList<List<Map<String, Object>>>();
        try {
            Session session = this.getCurSession();
            session.doWork(new Work(){
                public void execute(Connection conn) throws SQLException {
                    CallableStatement cs=null;
                    ResultSet rs=null;
                    cs = conn.prepareCall(sql);
                    for(int i=1;i<=params.length;i++){
                        cs.setObject(i, params[i-1]);
                    }
                    boolean hadResults = cs.execute();
                    ResultSetMetaData metaData = null;
                    while(hadResults){//遍历结果集
                        List<Map<String, Object>> rsList = new ArrayList<Map<String, Object>>();//用于装该结果集的内容
                        rs = cs.getResultSet();//获取当前结果集
                        metaData=rs.getMetaData();
                        int colCount=metaData.getColumnCount();//获取当前结果集的列数
                        while(rs.next()){
                            Map<String, Object> map = new HashMap<String, Object>();
                            for(int i=1;i<=colCount;i++){
                                String colName=metaData.getColumnName(i);
                                map.put(colName, rs.getObject(colName));
                            }
                            rsList.add(map);
                        }
                        result.add(rsList);
                        close(null, rs);//遍历完一个结果集，将其关闭
                        hadResults=cs.getMoreResults();//移到下一个结果集
                    }
                    close(cs, rs);
                }
            });
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
	 * 通过SQL执行无返回结果的存储过程(仅限于存储过程)
	 * @param queryString
	 * @param params
	 */
	 public void executeVoidProcedureSQL(final String queryString,final Object[] params) throws Exception{
	     Session session = this.getCurSession();
	     session.doWork(new Work(){

			public void execute(Connection conn) throws SQLException {
				ResultSet rs = null;
				CallableStatement call = conn.prepareCall("{" + queryString + "}");
				if (null != params) {
	                 for (int i = 0; i <params.length; i++) {
	                     call.setObject(i+1, params[i]);
	                 }
	             }
	             rs = call.executeQuery();
	             call.close();
	             rs.close();
			}});
	     }
	
	private void close(CallableStatement cs,ResultSet rs){
        try {
            if(cs!=null){
                cs.close();
            }
            if(rs!=null){
                rs.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	/**
	 * 
	 * @param stmt
	 */
	private void close(Statement stmt){
		try {
			if(null!=stmt) stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param pstmt
	 */
	private void close(PreparedStatement pstmt){
		try {
			if(null!=pstmt) pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void close(CallableStatement cstmt){
		try {
			if(null!=cstmt) cstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param rs
	 */
	private void close(ResultSet rs){
		try {
			if(null!=rs) rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void close(Connection conn){
		try {
			if(null!=conn) conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Long getAutoIncrValue(String type) {
			Long result = null;
			String databaseType = this.getDataBaseType();
			if(null != databaseType && databaseType.indexOf("oracle") !=-1){
				if("sys".equalsIgnoreCase(type)){
					String sql = "select HIBERNATE_SEQUENCE.nextval CID from dual";
					Map map = this.queryJdbcForMap(sql, null);;		
					result = Long.valueOf(map.get("CID")+"");
				}else if("flowProc".equalsIgnoreCase(type)){
					String sql = "select SEQ_TFLOW_PROC.nextval CID from dual";
					Map map = this.queryJdbcForMap(sql, null);;		
					result = Long.valueOf(map.get("CID")+"");
				}
			}
			return result;
	}
	
	/**
	 * 
	 * 根据类型和domainId获取对应的序列 
	 * @param type [通用序列 value],[表名序列 table]，[字段序列 field]，[表编码序列 tablecode],[字段编码序列 fieldcode],[自定义表序列 record]
	 * @return      
	 * @author        liumc
	 * @Date          2017-3-24 上午11:06:44
	 */
	private synchronized long getSEQByType(final String type,final Long domainId){
		long rslt = (Long)this.jdbcTemplate.execute(   
		         new CallableStatementCreator() {   
		             public CallableStatement createCallableStatement(Connection con) throws SQLException {   
		                String storedProc = "{call GETTABLEID_BYTYPE(?,?,?)}";// 调用的sql   
		                CallableStatement cs = con.prepareCall(storedProc);   
		                cs.setLong(1, domainId);// 设置输入参数的值   
		                cs.setString(2, type.toLowerCase()); //类型
		                cs.registerOutParameter(3,java.sql.Types.BIGINT);// 注册输出参数的类型   
		                return cs;   
		             }   
		          }, new CallableStatementCallback() {   
		              public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {   
		                cs.execute();   
		                return cs.getLong(3);// 获取输出参数的值   
		          }   
		       });   
		return rslt;
	}

	public Long getSEQValue(Long domainId) {
		return this.getSEQByType("value", domainId);
	}

	public Long getSEQTable(Long domainId) {
		return this.getSEQByType("table", domainId);
	}

	public Long getSEQTableCode(Long domainId) {
		return this.getSEQByType("tablecode", domainId);
	}

	public Long getSEQField(Long domainId) {
		return this.getSEQByType("field", domainId);
	}

	public Long getSEQFieldCode(Long domainId) {
		return this.getSEQByType("fieldcode", domainId);
	}

	public Long getSEQRecord(Long domainId) {
		return this.getSEQByType("record", domainId);
	}
	
	public Long getSEQForm(Long domainId) {
		return this.getSEQByType("seqform", domainId);
	}

	public Long getMaxId(String tableName, String primaryKeyField) {
		Long result = new Long(1000);
		String sql = "SELECT MAX(" + primaryKeyField + ") as KEYFIELD from " + tableName;		
		Map m = this.queryJdbcForMap(sql, null);
		if((null != m) && (null!=m.get("KEYFIELD"))){
			result = new Long(Long.valueOf(m.get("KEYFIELD")+"").longValue() + 1);
		}
		return result;
	}

	public String[] getSortLocation(String po, String idField,
			String parentIdField, String sortField, Long id,
			String otherCondition) {
		String[] result = {"", ""};
		//查询此记录的排序码和父级记录的主键id
		String hqlSelectQuery = "select " + sortField + "," + parentIdField + " from " + po + " where " + idField + "=" + id;
		otherCondition = otherCondition == null?"":otherCondition.trim();
		String where = "";
		if(!"".equals(otherCondition)){
			String _otherCondition = otherCondition.toLowerCase();
			if(_otherCondition.startsWith("and") || _otherCondition.startsWith("or")){
				where += " " + _otherCondition;
			}else{
				where += " and (" + otherCondition + ")" ;
			}
		}
		List list = this.query(hqlSelectQuery);
		if(list.size() > 0){
			Object[] data = (Object[]) list.get(0);
			String parentSortCode = data[0].toString();
			String parentId = data[1].toString();
			
			if("".equals(parentSortCode))return result;
			//查询同一层级节点是否有排在前面的记录
			hqlSelectQuery = "select " + idField + " from " + po + " where " + parentIdField + "=" + parentId 
							+ " and " + sortField + " < " + parentSortCode;
			hqlSelectQuery += where;
			hqlSelectQuery += " order by " + sortField + " desc";
			
			List _list = this.queryByPage(hqlSelectQuery, 0, 1);
			if(null != _list && _list.size() > 0){//有排前面的
				result[0] = "1";
               result[1] = _list.iterator().next().toString();
			}else{//没有排前面的
				hqlSelectQuery = "select " + idField + " from " + po + " where " + parentIdField + "=" + parentId 
								+ " and " + sortField + " > " + parentSortCode;
				hqlSelectQuery += where;
				hqlSelectQuery += " order by " + sortField + " asc";
				_list = this.queryByPage(hqlSelectQuery, 0, 1);
				if(null != _list && _list.size() > 0){
					result[0] = "0";
	                result[1] = _list.iterator().next().toString();
				}else{//相同层次的
					hqlSelectQuery = "select " + idField + " from " + po + " where " + parentIdField + "=" + parentId 
					+ " and " + idField + " <> " + id;
					hqlSelectQuery += where;
					hqlSelectQuery += " order by " + parentIdField + " asc";
					_list = this.queryByPage(hqlSelectQuery, 0, 1);
					if(_list.size()>0){
						
		                result[1] = _list.iterator().next().toString();
		                if(Long.parseLong(result[1])>id.longValue()){
		                	result[0] = "0";
		                }else{
		                	result[0] = "1";
		                }
					}
				}
			}
		}

		if(null!=list) list.clear();
		list = null;
		//相同层次的调整
		if("".equals(result[0]) && "".equals(result[1])){
			
		}
		return result;
	}

	public String[] getSortLocation(String po, String idField,
			String sortField, Long id, String otherCondition) {
		this.log.info("--start - getSortLocation----------------------------------------");
		String[] result = {"", ""};
		if(id == null || id.longValue() == 0)return result;
		otherCondition = otherCondition == null?"":otherCondition.trim();
		String where = "";
		if(!"".equals(otherCondition)){
			String _otherCondition = otherCondition.toLowerCase();
			if(_otherCondition.startsWith("and") || _otherCondition.startsWith("or")){
				where += " " + _otherCondition;
			}else{
				where += " and (" + otherCondition + ")" ;
			}
		}
		
		//获取当前排序码值
		String hqlSelectQuery = "select " + sortField + " from " + po + " where " + idField + "=" + id + where;
		List list = this.query(hqlSelectQuery);
		if(list.size() > 0){
			Object data = (Object) list.get(0);
			String sortCode = data == null?"":data.toString();
			if("".equals(sortCode)) return result;
//			查询同一层级节点是否有排在前面的记录
			hqlSelectQuery = "select " + idField + " from " + po + " where " + sortField + " < " + sortCode + where;
			
			hqlSelectQuery += " order by " + sortField + " desc";
			
			List _list = this.queryByPage(hqlSelectQuery, 0, 1);
			if(null != _list && _list.size() > 0){//有排前面的
				result[0] = "1";
               result[1] = _list.iterator().next().toString();
			}else{//没有排前面的
				hqlSelectQuery = "select " + idField + " from " + po + " where "  + sortField + " > " + sortCode + where;
				
				hqlSelectQuery += " order by " + sortField + " asc";
				_list = this.queryByPage(hqlSelectQuery, 0, 1);
				if(null != _list && _list.size() > 0){
					result[0] = "0";
	                result[1] = _list.iterator().next().toString();
				}
			}
		}

		if(null!=list) list.clear();
		list = null;
		this.log.info("--end - getSortLocation----------------------------------------");
		return result;
	}

	/**
	 * 
	 * 获取根组织下最大的组织排序码，没有就返回空字符串 
	 * @return      
	 * @author        liumc
	 * @Date          2016-9-5 上午10:25:53
	 */
	private String getMaxRootOrgCode(String po,String sortField,String parentIdField){
		String rslt = "500000000";
		String hql = "select max("+sortField+") from "+po+" where "+parentIdField+" = 0 ";
		List list = this.query(hql);
		if((null != list) && (list.size()>0)){
			rslt = String.valueOf(list.get(0));
		}else{
			rslt = null;
		}
		return rslt;
	}
	
	/**
	 * 
	 * 获取指定位置的排序码 
	 * @return      
	 * @author        liumc
	 * @Date          2016-9-18 下午5:20:37
	 */
	private int getLocationOrgCode(String currentOrderId,String sortField,String parentIdField,String po,String idField,String sortLocation ,String parentId){
		this.log.info("---start getLocationOrgCode----");
		this.log.info("currentOrderId = "+currentOrderId);
		this.log.info("sortField = "+sortField);
		this.log.info("parentIdField = "+parentIdField);
		this.log.info("po = "+po);
		this.log.info("idField = "+idField);
		this.log.info("sortLocation = "+sortLocation);
		this.log.info("parentId ==11= "+parentId);
		if(StringUtils.isEmpty(parentId)) parentId = "0";
		this.log.info("parentId ==22= "+parentId);
		//计算排序码值
		int orderCode = 500000000;		
		
		if(null == currentOrderId ||  "-1".equals(currentOrderId) || "".equals(currentOrderId)){//没有选择排列位置
			orderCode=500000000;//默认排序值
		}else{//有选择排列位置
			
			//查询所选择的排序记录的排序码和父级字段值
			String hqlSelectQuery = "select " + sortField + "," + parentIdField + " from " + po + " where " + idField + "=" + currentOrderId;
			List _list = this.query(hqlSelectQuery);
			if(null != _list && _list.size() > 0){
				Object[] data = (Object[])_list.get(0);
				String _orderSortCode = data[0] == null?"500000000":data[0].toString();
				String _orderParentId = data[1] == null?"0":data[1].toString();
				
				if(null != sortLocation && "0".equals(sortLocation) || "up".equals(sortLocation)){//排前
					//相同层级节点，小于所选排序记录的最大排序码值
					hqlSelectQuery = "select max(" + sortField + ") from " + po + " where " + parentIdField + "=" + parentId
											+ " and " + sortField + "<" + _orderSortCode;
					_list = this.query(hqlSelectQuery);
					String maxSortField = _list.get(0) == null?"":_list.get(0).toString();
					if ("".equals(maxSortField)) {
//						不存在这样的组织，则新增的组织为父组织下的第一个组织
	                    orderCode=Integer.parseInt(_orderSortCode)-5000;
					}else{
	                    int intValue=Integer.parseInt(maxSortField);
	                    if(intValue==0) intValue=100000;
	                    orderCode = (intValue + (Integer.parseInt(_orderSortCode))) / 2;
	                }
				}else{//排后
					//相同层级节点，大于所选记录的最小排序码值
					hqlSelectQuery = "select min(" + sortField + ") from " + po + " where " + parentIdField + "=" + parentId
					+ " and " + sortField + ">" + _orderSortCode;
					_list = this.query(hqlSelectQuery);
					String minSortField = _list.get(0) == null?"":_list.get(0).toString();
					if ("".equals(minSortField)) {
	                    orderCode=Integer.parseInt(_orderSortCode)+5000;
	                }else{
	                    orderCode=(Integer.parseInt(minSortField)+(Integer.parseInt(_orderSortCode)))/2;
	                }
				}
			}

			if(null!=_list) _list.clear();
			_list = null;
			
		}
		this.log.info("---end getLocationOrgCode orderCode : "+orderCode+"----");
		return orderCode;
	}
	
	public String[] getSortCodeAndIdString(String po, String idField,
			String parentIdField, String idStringField, String nameStringField,
			String levelField, String sortField, Long id, String parentId,
			String name, String currentOrderId, String sortLocation) {
		log.info("-start--getSortCodeAndIdString -- ");
		log.info("parentId = "+parentId);
		//查询当前记录的parentId,parentIdString,nameString,level
		String[] result = new String[4];		
		if(null == parentId || "".equals(parentId) || "0".equals(parentId)){//没有选择上级节点
			//如果为空，从数据库中获取最大的值ordercode+5000   20160905
			String maxRootSort = this.getMaxRootOrgCode(po, sortField,parentIdField);//20161121
			if(StringUtils.isEmpty(maxRootSort)){//没有跟组织记录
				result[0] = "_500000000$" + id + "$";
				result[1] = name;
				result[2] = 500000000 + "";
				result[3] = 0 + "";
			}else{//有跟组织记录
				//要判断排序和currentOrderId ,如果排序是在什么之后
				String maxSort = "";//Integer.valueOf(maxRootSort)+500+"";
				//int orderCode = this.getLocationOrgCode(currentOrderId, sortField, parentIdField, po, parentIdField, sortLocation, "");//20160918
				//int orderCode = this.getLocationOrgCode(currentOrderId, sortField, parentIdField, po, idField, sortLocation, "");//20160926
				int orderCode = this.getLocationOrgCode(currentOrderId, sortField, parentIdField, po, idField, sortLocation, parentId);//20160926
				//int orderCode = Integer.valueOf(maxRootSort)+5000;
				maxSort = ""+orderCode;
				result[0] = "_"+maxSort+"$" + id + "$";
				result[1] = name;
				result[2] = maxSort + "";
				result[3] = 0 + "";
			}
			return result;
		}
		log.info("-start-11111111-getSortCodeAndIdString -- ");
		
		//结果值
		//初始化
		String idString = "";
		String nameString = "";
		String parentIdString = "";
		String parentNameString = "";
		int level = -1;
		
		String hqlSelectQuery = "";
		List list = new ArrayList();
		
		//int orderCode = this.getLocationOrgCode(currentOrderId, sortField, parentIdField, po, parentIdField, sortLocation, parentId);//20160918
		int orderCode = this.getLocationOrgCode(currentOrderId, sortField, parentIdField, po, idField, sortLocation, parentId);//20160926
	
		if(!"0".equals(parentId)){//当前记录为子节点
			//查询父级节点的idString,nameString,level
			hqlSelectQuery = " select " + idStringField + "," + idField;
			if(null != nameStringField && !"".equals(nameStringField)){
				hqlSelectQuery += "," + nameStringField;
			}
			if(null != levelField && !"".equals(levelField)){
				hqlSelectQuery += "," + levelField;
			}
			hqlSelectQuery += " from " + po;
			hqlSelectQuery += " where " + idField + "=" + parentId;
			list = this.query(hqlSelectQuery);
			if(null != list && list.size()>0){
				Object[] data = (Object[])list.get(0);
				parentIdString = data[0].toString();
				if(data.length > 3){
					parentNameString = data[2].toString();
					level = Integer.parseInt(data[3] == null?"-1":data[3].toString());
				}else if(data.length >2){
					if(null != nameStringField && !"".equals(nameStringField)){
						parentNameString = data[2] == null?"":data[2].toString();
					}else{
						level = Integer.parseInt(data[2] == null?"-1":data[2].toString());
					}
				}
				if(!"".equals(parentNameString)){
					nameString += parentNameString + ".";
				}
				
			}
			nameString += name;
		}else{//根节点
			nameString = name;
		}
		idString = parentIdString + "_" + orderCode + "$" + id + "$";
		level = ++level;	
		
		result[0] = idString;
		result[1] = nameString;
		result[2] = orderCode + "";
		result[3] = level + "";
		log.info("-end-11111111-getSortCodeAndIdString -- ");
		return result;
	}

	public int getSortCode(String po, String otherCondition, String idField,
			String sortField, String currentOrderId, String sortLocation) {
		otherCondition = otherCondition == null?"":otherCondition.trim();
		String where = "";
		if(!"".equals(otherCondition)){
			String _otherCondition = otherCondition.toLowerCase();
			if(_otherCondition.startsWith("and") || _otherCondition.startsWith("or")){
				where += " " + _otherCondition;
			}else{
				where += " and (" + otherCondition + ")" ;
			}
		}
		
		int orderCode = 500000000;
		if(null == currentOrderId ||  "-1".equals(currentOrderId) || "".equals(currentOrderId)){//没有选择排列位置
			orderCode = 500000000;
		}else{//获取所选排序字段的排序码值
			
			String hqlSelectQuery = "select " + sortField + "," + idField + " from " + po + " where " + idField + "=" + currentOrderId + where;
			List list = this.query(hqlSelectQuery);
			if(null !=list && list.size()>0){
				Object[] data = (Object[])list.get(0);
				String _orderSortCode = data[0] == null?"500000000":data[0].toString();
				//根据所选的排列位置情况调整排序码
				List _list = new ArrayList();
				if(null != sortLocation && "0".equals(sortLocation) || "up".equals(sortLocation)){//排前
					//相同层级节点，小于所选排序记录的最大排序码值
					hqlSelectQuery = "select max(" + sortField + ") from " + po + " where " + sortField + "<" + _orderSortCode + where;
					_list = this.query(hqlSelectQuery);
					String maxSortField = (_list.get(0) == null || "".equals(_list.get(0).toString()))?"0":_list.get(0).toString();				
					if ("0".equals(maxSortField)) {
						//不存在这样的组织，则新增的组织为父组织下的第一个组织
	                    orderCode=Integer.parseInt(_orderSortCode)-5000;
					}else{
	                    int intValue=Integer.parseInt(maxSortField);
	                    if(intValue==0) intValue=500000000; //1000000(20160929)
	                    orderCode = (intValue + (Integer.parseInt(_orderSortCode))) / 2;
	                }
				}else{//排后
					//相同层级节点，大于所选记录的最小排序码值
					hqlSelectQuery = "select min(" + sortField + ") from " + po + " where " + sortField + ">" + _orderSortCode + where;
					_list = this.query(hqlSelectQuery);
					String minSortField = (_list.get(0) == null || "".equals(_list.get(0).toString()))?"0":_list.get(0).toString();
					
					if ("0".equals(minSortField) || _list.size()==1) {
	                    orderCode=Integer.parseInt(_orderSortCode)+5000;
	                }else{
	                    orderCode=(Integer.parseInt(minSortField)+(Integer.parseInt(_orderSortCode)))/2;
	                }
				}
			}
			if(null!=list) list.clear();
			list = null;
		}
		
		return orderCode;
	}

	public List getObjectsByValueBean(String hql,Object obj){
		List rslt = new ArrayList();
		Session session = this.getCurSession();
		Query query = session.createQuery(hql);
		query.setProperties(obj);
		rslt = query.list();
		return rslt;
	}
	
	public List getObjectsByValueBean(String hql,Object obj,int firstResult,int maxResults){
		List rslt = new ArrayList();
		Session session = this.getCurSession();
		Query query = session.createQuery(hql);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		
		query.setProperties(obj);
		rslt = query.list();
		return rslt;
	}

	public long getRecordsCount(String hsql, String[] paramNames,
			Object[] paramValues) {
		return this.queryCount(hsql, paramNames, paramValues);
	}

	public long getRecordsCount(String hsql, Object[] paramValues) {
		return this.queryCount(hsql, paramValues);
	}

	public long getRecordsCount(String hsql) {
		return this.queryCount(hsql);
	}

	public int saveOrUpdateAll(String sql,final int cols,final List dataList){
		final int count = dataList.size();

		this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
			public int getBatchSize() {
				return count;
			}
			public void setValues(PreparedStatement ps, int idx) throws SQLException {				
				Object obj = (Object)dataList.get(idx);
				if(obj.getClass().isArray()){
					Object[] data = (Object[])obj;
					if(null != data[0] && "String".equalsIgnoreCase(data[0].toString())){
						ps.setString(1, data[1] == null?"":data[1].toString());
					}else if(null != data[0] && "Long".equalsIgnoreCase(data[0].toString()) && data[1] !=null){
						ps.setLong(1, Long.parseLong(data[1].toString()));
					}else if(null != data[0] && "Integer".equalsIgnoreCase(data[0].toString()) && data[1] !=null){
						ps.setInt(1, Integer.parseInt(data[1].toString()));
					}else{
                       ps.setObject(1, data[1]);
					}
				}else if(obj instanceof List){					
					List list = (List)obj;
					for(int k=0;k<list.size();k++){
						Object paraObj = (Object)list.get(k);						
						if(paraObj.getClass().isArray()){
							Object[] para = (Object[])paraObj;							
							if(null != para[0] && "String".equalsIgnoreCase(para[0].toString())){
								ps.setString(k+1, (String)para[1]);
							}else if(null != para[0] && "Long".equalsIgnoreCase(para[0].toString()) && para[1] !=null){
								ps.setLong(k+1, ((Long)para[1]).longValue());
							}else if(null != para[0] && "Integer".equalsIgnoreCase(para[0].toString()) && para[1] !=null){
								ps.setInt(k+1, ((Integer)para[1]).intValue());
							}else{
		                       ps.setObject(k+1, para[1]);
							}
						}else{
							ps.setObject(k+1, paraObj);
						}
					}
					if(null!=list) list.clear();
					list = null;
				}else{					
					ps.setObject(1, obj);
				}

			}

		});
		return count;
	}
	
	public int saveOrUpdateAll(final String sql,final List dataList){
		final int count = dataList.size();

		this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
			public int getBatchSize() {
				return count;
			}
			public void setValues(PreparedStatement ps, int idx) throws SQLException {				
				Object obj = (Object)dataList.get(idx);
				if(obj.getClass().isArray()){
					Object[] data = (Object[])obj;
					for(int i=0;i<data.length;i++){
                       ps.setObject(i+1, data[i]);
					}
				}
			}
		});
		return count;
	}

	public java.util.List querytForArray(String sql){
		List result = this.queryJdbcForListArray(sql, null);
		return result;
	}
	
	public java.util.List querytForArray(String sql,Object[] params){
		List list = this.queryJdbcForListArray(sql,params);
		return list;
	}
	
	private List<Object[]> queryJdbcForListArray(final String sql, final Object[] paramsValue) {
		final List<Object[]> rslt = new ArrayList<Object[]>(); 
		
		Session session = this.getCurSession();
		session.doWork(new Work(){

			public void execute(Connection conn) throws SQLException {
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				try{
					pstmt = conn.prepareStatement(sql);
					if(paramsValue!=null){
						for(int i=0;i<paramsValue.length;i++){
							pstmt.setObject(i+1, paramsValue[i]);
						}
					}
					rs = pstmt.executeQuery();
					ResultSetMetaData rsmd = rs.getMetaData();
					int cols = rsmd.getColumnCount();
					while(rs.next()){
						Object[] tmparr = new Object[cols];
						for(int k=0;k<rsmd.getColumnCount();k++){
							String columnName = rsmd.getColumnName(k+1); //属性名
							int columnType = rsmd.getColumnType(k+1); //属性类型
							if(columnType == java.sql.Types.DATE ||
							   columnType == java.sql.Types.TIMESTAMP
							){ //日期类型
								if(rs.getTimestamp(k+1)==null) tmparr[k] = null;
								else tmparr[k] =  new java.util.Date(rs.getTimestamp(k+1).getTime());
							}else if(columnType == java.sql.Types.INTEGER){
								tmparr[k] =  new Integer(rs.getInt(k+1));
							}else if(columnType == java.sql.Types.CLOB){
								tmparr[k] = rs.getString(k+1);
							}else if(columnType == java.sql.Types.BLOB){
								tmparr[k] = rs.getBytes(k+1);
							}else {
								tmparr[k] = rs.getObject(k+1);
							}
						}
						rslt.add(tmparr);
					}
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					close(rs);
					close(pstmt);
					//conn有hibernate自己控制是否关闭，这里不需要关闭
				}
			}
			
		});
		return rslt;
	}
	
	private java.util.List queryForMapList2(String sql){
		return this.jdbcTemplate.queryForList(sql);
	}
	public java.util.List queryForMapList2(String sql,Object[] params){
		return this.jdbcTemplate.queryForList(sql,params);
	} 

}
