package net.minggao.core.common.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import net.minggao.core.framework.ApplicationHelper;

@Component
public class DBUtils {
	private Log log = LogFactory.getLog(DBUtils.class);
	@Resource(name="DataSource")
	private DataSource dataSource = null;
	private final String DEFAULT_JNDINAME = "jdbc-mgsite";
	private static DBUtils instance = null;
	public static DBUtils getInstance(){
		if(instance == null) instance = new DBUtils();
		return instance;
	}
	
	/**根据在服务器中间件中配置的数据源的jndiName获取数据源
	 * 此方法仅供获取第三方数据源，用jdbc整合第三方程序时调用(慎用)
	 * 在访问第三方系统数据库的时候，可以使用带jndiName参数的方法，避免自己写jdbc程序来实现，万不得已的情况下，才能用此方法获取数据源进行jdbc程序的编写
	 * @param jndiName 
	 * @deprecated
	 * @return
	 */
	public DataSource getDataSource(String jndiName){
		DataSource ds = null;
		if(jndiName.equals(DEFAULT_JNDINAME)){
			ds = this.getDefaultDataSource();
		}else{
			try{
				Context ctx = new InitialContext();
				ds = (DataSource)ctx.lookup(jndiName);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return ds;
	}

	@Bean
	public DataSource getDefaultDataSource(){
		DataSource ds = (DataSource)ApplicationHelper.getBean("DataSource");
		this.log.info(" return ds : "+ds);
		return ds;
	}
	
	/**
	 * 通过数据源获取数据库的连接
	 * @param jndiName
	 * @return
	 */
	private Connection getConnection(String jndiName){
		DataSource ds = this.getDataSource(jndiName);
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	private Connection getConnection(){
		DataSource ds = this.getDefaultDataSource();
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	/**
	 * 关闭数据库连接
	 * @deprecated  //不推荐使用
	 * @param conn
	 */
	private void close(Connection conn){
		try {
			if(null!=conn) conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @deprecated  //不推荐使用
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
	 * @deprecated  //不推荐使用
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
	 * @deprecated  //不推荐使用
	 * @param rs
	 */
	private void close(ResultSet rs){
		try {
			if(null!=rs) rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 执行sql查询
	 * @param sql          带命名参数的sql，例如：select * from TableName where columName2=:v1 and columName3 = :v2;
	 * @param paramsNamed  没有命名参数，传入 null,String[]
	 * @param paramsValue  没有命名参数，传入 null,Object[]
	 * @return
	 */
	public List<Map> queryForMapList(String sql,String[] paramsNamed,Object[] paramsValue){
		return this.queryForMapList(sql, paramsNamed, paramsValue, DEFAULT_JNDINAME);
	}
	public List<Map> queryForMapList(String sql,Object[] paramsNamed,Object[] paramsValue){
		return this.queryForMapList(sql, paramsNamed, paramsValue, DEFAULT_JNDINAME);
	}
	public List<Map> queryForMapList2(String sql,Object[] paramsValue){
		return this.queryForMapList2(sql, paramsValue, DEFAULT_JNDINAME);
	}
	/**
	 * @param sql    查询sql
	 * @return        List<Object[]> 
	 * @author        liumc
	 * @Date          2017-4-26 上午9:07:26
	 * @deprecated
	 */
	public List<Object[]> queryForObjArrList(String sql){
		return this.queryForObjArrList(sql, DEFAULT_JNDINAME);
	}
	public List<Object[]> queryForObjArrList(String sql,Object[] paraValues){
		return this.queryForObjArrList(sql,paraValues, DEFAULT_JNDINAME);
	}
	/**
	 * @param sql       查询sql
	 * @param  jndiName 数据源名称
	 * @return        List<Object[]> 
	 * @author        liumc
	 * @Date          2017-4-26 上午9:07:26
	 * @deprecated
	 */
	public List<Object[]> queryForObjArrList(String sql,String jndiName){
		List<Object[]> rslt = new ArrayList<Object[]>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			if(DEFAULT_JNDINAME.equals(jndiName)){
				conn = this.getConnection();
			}else{
				conn = getConnection(jndiName);
			}
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			while(rs.next()){
				int columnCount = rsmd.getColumnCount();
				Object[] tup = new Object[columnCount];
				for(int k=0;k<columnCount;k++){
					String columnName = rsmd.getColumnName(k+1); //属性名
					int columnType = rsmd.getColumnType(k+1); //属性类型
					if(columnType == java.sql.Types.DATE ||
					   columnType == java.sql.Types.TIMESTAMP
					){ //日期类型
						if(rs.getTimestamp(k+1)==null) tup[k] =  null;
						else tup[k] =  new java.util.Date(rs.getTimestamp(k+1).getTime());
					}else if(columnType == java.sql.Types.INTEGER){
						tup[k]= new Integer(rs.getInt(k+1));
					}else if(columnType == java.sql.Types.CLOB){
						tup[k]=  rs.getString(k+1);
					}else if(columnType == java.sql.Types.BLOB){
						tup[k]= rs.getBytes(k+1);
					}else {
						tup[k]= rs.getObject(k+1);
					}
//					map.put(columnName, rs.getObject(columnName));
				}
				rslt.add(tup);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.close(rs);
			this.close(pstmt);
			this.close(conn);
		}
		return rslt;
	}
	public List<Object[]> queryForObjArrList(String sql,Object[] paraValues,String jndiName){
		List<Object[]> rslt = new ArrayList<Object[]>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			if(DEFAULT_JNDINAME.equals(jndiName)){
				conn = this.getConnection();
			}else{
				conn = getConnection(jndiName);
			}
			pstmt = conn.prepareStatement(sql);
			int paraSize = 0;
			if(null !=paraValues) paraSize = paraValues.length;
			for(int i=0;i<paraSize;i++){
				pstmt.setObject(i+1, paraValues[i]);
			}
			
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			while(rs.next()){
				int columnCount = rsmd.getColumnCount();
				Object[] tup = new Object[columnCount];
				for(int k=0;k<columnCount;k++){
					String columnName = rsmd.getColumnName(k+1); //属性名
					int columnType = rsmd.getColumnType(k+1); //属性类型
					if(columnType == java.sql.Types.DATE ||
					   columnType == java.sql.Types.TIMESTAMP
					){ //日期类型
						if(rs.getTimestamp(k+1)==null) tup[k] =  null;
						else tup[k] =  new java.util.Date(rs.getTimestamp(k+1).getTime());
					}else if(columnType == java.sql.Types.INTEGER){
						tup[k]= new Integer(rs.getInt(k+1));
					}else if(columnType == java.sql.Types.CLOB){
						tup[k]=  rs.getString(k+1);
					}else if(columnType == java.sql.Types.BLOB){
						tup[k]= rs.getBytes(k+1);
					}else {
						tup[k]= rs.getObject(k+1);
					}
//					map.put(columnName, rs.getObject(columnName));
				}
				rslt.add(tup);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.close(rs);
			this.close(pstmt);
			this.close(conn);
		}
		return rslt;
	}
	
	/**
	 * 执行sql查询
	 * @param sql          带命名参数的sql，例如：select * from TableName where columName2=:v1 and columName3 = :v2;
	 * @param paramsNamed  没有命名参数，传入 null,String[]
	 * @param paramsValue  没有命名参数，传入 null,Object[]
	 * @param dsJndiName   DataSource的jndiName
	 * @return
	 */
	public List<Map> queryForMapList(String tmpsql,String[] paramsNamed,Object[] paramsValue,String jndiName){
		String sql = tmpsql;
		List<Map> rslt = new ArrayList<Map>();
		if(paramsNamed!=null){
			for(int i=0;i<paramsNamed.length;i++){
				sql = sql.replaceAll(":"+paramsNamed[i], "?");
			}
		}
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			if(DEFAULT_JNDINAME.equals(jndiName)){
				conn = this.getConnection();
			}else{
				conn = getConnection(jndiName);
			}
			pstmt = conn.prepareStatement(sql);
			if(paramsNamed!=null){
				for(int i=0;i<paramsNamed.length;i++){
					pstmt.setObject(i+1, paramsValue[i]);
				}
			}
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			while(rs.next()){
				Map map = new LinkedHashMap();
				for(int k=0;k<rsmd.getColumnCount();k++){
					String columnName = rsmd.getColumnName(k+1); //属性名
					int columnType = rsmd.getColumnType(k+1); //属性类型
					if(columnType == java.sql.Types.DATE ||
					   columnType == java.sql.Types.TIMESTAMP
					){ //日期类型
						if(rs.getTimestamp(k+1)==null) map.put(columnName, null);
						else map.put(columnName,  new java.util.Date(rs.getTimestamp(k+1).getTime()));
					}else if(columnType == java.sql.Types.INTEGER){
								map.put(columnName,   new Integer(rs.getInt(k+1)));
					}else if(columnType == java.sql.Types.CLOB){
								map.put(columnName,  rs.getString(k+1));
					}else if(columnType == java.sql.Types.BLOB){
								map.put(columnName,  rs.getBytes(k+1));
					}else {
								map.put(columnName,  rs.getObject(k+1));
					}
//					map.put(columnName, rs.getObject(columnName));
				}
				rslt.add(map);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.close(rs);
			this.close(pstmt);
			this.close(conn);
		}
		return rslt;
	}
	
	public List<Map> queryForMapList(String tmpsql,Object[] paramsNamed,Object[] paramsValue,String jndiName){
		String sql = tmpsql;
		List<Map> rslt = new ArrayList<Map>();
		if(paramsNamed!=null){
			for(int i=0;i<paramsNamed.length;i++){
				sql = sql.replaceAll(":"+paramsNamed[i], "?");
			}
		}
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			if(DEFAULT_JNDINAME.equals(jndiName)){
				conn = this.getConnection();
			}else{
				conn = getConnection(jndiName);
			}
			pstmt = conn.prepareStatement(sql);
			if(paramsNamed!=null){
				for(int i=0;i<paramsNamed.length;i++){
					pstmt.setObject(i+1, paramsValue[i]);
				}
			}
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			while(rs.next()){
				Map map = new LinkedHashMap();
				for(int k=0;k<rsmd.getColumnCount();k++){
					String columnName = rsmd.getColumnName(k+1); //属性名
					int columnType = rsmd.getColumnType(k+1); //属性类型
					if(columnType == java.sql.Types.DATE ||
					   columnType == java.sql.Types.TIMESTAMP
					){ //日期类型
						if(rs.getTimestamp(k+1)==null) map.put(columnName, null);
						else map.put(columnName,  new java.util.Date(rs.getTimestamp(k+1).getTime()));
					}else if(columnType == java.sql.Types.INTEGER){
								map.put(columnName,   new Integer(rs.getInt(k+1)));
					}else if(columnType == java.sql.Types.CLOB){
								map.put(columnName,  rs.getString(k+1));
					}else if(columnType == java.sql.Types.BLOB){
								map.put(columnName,  rs.getBytes(k+1));
					}else {
								map.put(columnName,  rs.getObject(k+1));
					}
//					map.put(columnName, rs.getObject(columnName));
				}
				rslt.add(map);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.close(rs);
			this.close(pstmt);
			this.close(conn);
		}
		return rslt;
	}
	public List<Map> queryForMapList2(String sql,Object[] paramsValue,String jndiName){
		List<Map> rslt = new ArrayList<Map>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			if(DEFAULT_JNDINAME.equals(jndiName)){
				conn = this.getConnection();
			}else{
				conn = getConnection(jndiName);
			}
			pstmt = conn.prepareStatement(sql);
			if(paramsValue!=null){
				for(int i=0;i<paramsValue.length;i++){
					pstmt.setObject(i+1, paramsValue[i]);
				}
			}
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			while(rs.next()){
				Map map = new LinkedHashMap();
				for(int k=0;k<rsmd.getColumnCount();k++){
					String columnName = rsmd.getColumnName(k+1); //属性名
					int columnType = rsmd.getColumnType(k+1); //属性类型
					if(columnType == java.sql.Types.DATE ||
					   columnType == java.sql.Types.TIMESTAMP
					){ //日期类型
						if(rs.getTimestamp(k+1)==null) map.put(columnName, null);
						else map.put(columnName,  new java.util.Date(rs.getTimestamp(k+1).getTime()));
					}else if(columnType == java.sql.Types.INTEGER){
								map.put(columnName,   new Integer(rs.getInt(k+1)));
					}else if(columnType == java.sql.Types.CLOB){
								map.put(columnName,  rs.getString(k+1));
					}else if(columnType == java.sql.Types.BLOB){
								map.put(columnName,  rs.getBytes(k+1));
					}else {
								map.put(columnName,  rs.getObject(k+1));
					}
//					map.put(columnName, rs.getObject(columnName));
				}
				rslt.add(map);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.close(rs);
			this.close(pstmt);
			this.close(conn);
		}
		return rslt;
	}
	
	/**
	 * 查询sql，返回long类型值，失败返回-1
	 * @param sql          带命名参数的sql，如：select count(1) from TableName where columName2=:v1 and columName3 = :v2;
	 * @param paramsNamed  没有命名参数，传入 null,String[]
	 * @param paramsValue  没有命名参数，传入 null,Object[]
	 * @return 执行异常返回-1
	 */
	public long queryForLong(String sql,String[] paramsNamed,Object[] paramsValue){
		return this.queryForLong(sql, paramsNamed, paramsValue, DEFAULT_JNDINAME);
	}
	public long queryForLong2(String sql,Object[] paramsValue){
		return this.queryForLong2(sql, paramsValue, DEFAULT_JNDINAME);
	}
	
	/**
	 * 查询sql，返回long类型值，失败返回-1
	 * @param sql          带命名参数的sql，如：select count(1) from TableName where columName2=:v1 and columName3 = :v2;
	 * @param paramsNamed  没有命名参数，传入 null,String[]{"v1","v2"}
	 * @param paramsValue  没有命名参数，传入 null,Object[]
	 * @param jndiName   DataSource的jndiName
	 * @return 执行异常返回-1
	 */
	public long queryForLong(String tmpsql,String[] paramsNamed,Object[] paramsValue,String jndiName){
		String sql = tmpsql;
		long rslt = -1;
		if(paramsNamed!=null){
			for(int i=0;i<paramsNamed.length;i++){
				sql = sql.replaceAll(":"+paramsNamed[i], "?");
			}
		}
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			if(DEFAULT_JNDINAME.equals(jndiName)){
				conn = this.getConnection();
			}else{
				conn = getConnection(jndiName);
			}
			pstmt = conn.prepareStatement(sql);
			if(paramsNamed!=null){
				for(int i=0;i<paramsNamed.length;i++){
					pstmt.setObject(i+1, paramsValue[i]);
				}
			}
			rs = pstmt.executeQuery();
			while(rs.next()){
				rslt = rs.getLong(1);
				break;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.close(rs);
			this.close(pstmt);
			this.close(conn);
		}
		return rslt;
	}
	public long queryForLong2(String sql,Object[] paramsValue,String jndiName){
		long rslt = -1;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			if(DEFAULT_JNDINAME.equals(jndiName)){
				conn = this.getConnection();
			}else{
				conn = getConnection(jndiName);
			}
			pstmt = conn.prepareStatement(sql);
			if(paramsValue!=null){
				for(int i=0;i<paramsValue.length;i++){
					pstmt.setObject(i+1, paramsValue[i]);
				}
			}
			rs = pstmt.executeQuery();
			while(rs.next()){
				rslt = rs.getLong(1);
				break;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.close(rs);
			this.close(pstmt);
			this.close(conn);
		}
		return rslt;
	}
	
	/**
	 * 执行sql查询
	 * @param sql          带命名参数的sql，例如：select * from TableName where columName2=:v1 and columName3 = :v2;
	 * @param paramsNamed  没有命名参数，传入 null,String[]{"v1","v2"}
	 * @param paramsValue  没有命名参数，传入 null,Object[]
	 * @return 执行异常返回null
	 */
	public Map queryForMap(String sql,String[] paramsNamed,Object[] paramsValue){
		return this.queryForMap(sql, paramsNamed, paramsValue, DEFAULT_JNDINAME);
	}
	public Map queryForMap2(String sql,Object[] paramsValue){
		return this.queryForMap2(sql, paramsValue, DEFAULT_JNDINAME);
	}
	
	/**
	 * 执行sql查询
	 * @param sql          带命名参数的sql，例如：select * from TableName where columName2=:v1 and columName3 = :v2;
	 * @param paramsNamed  没有命名参数，传入 null,String[]{"v1","v2"}
	 * @param paramsValue  没有命名参数，传入 null,Object[]
	 * @param jndiName   DataSource的jndiName
	 * @return 执行异常返回null
	 */
	public Map queryForMap(String tmpsql,String[] paramsNamed,Object[] paramsValue,String jndiName){
		String sql = tmpsql;
		Map rslt = null;
		if(paramsNamed!=null){
			for(int i=0;i<paramsNamed.length;i++){
				sql = sql.replaceAll(":"+paramsNamed[i], "?");
			}
		}
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			if(DEFAULT_JNDINAME.equals(jndiName)){
				conn = this.getConnection();
			}else{
				conn = getConnection(jndiName);
			}
			pstmt = conn.prepareStatement(sql);
			if(paramsNamed!=null){
				for(int i=0;i<paramsNamed.length;i++){
					pstmt.setObject(i+1, paramsValue[i]);
				}
			}
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			while(rs.next()){
				rslt = new HashMap();
				for(int k=0;k<rsmd.getColumnCount();k++){
					String columnName = rsmd.getColumnName(k+1);
					int columnType = rsmd.getColumnType(k+1); //属性类型
					if(columnType == java.sql.Types.DATE ||
					   columnType == java.sql.Types.TIMESTAMP
					){ //日期类型
						if(rs.getTimestamp(k+1)==null) rslt.put(columnName, null);
						else rslt.put(columnName,  new java.util.Date(rs.getTimestamp(k+1).getTime()));
					}else if(columnType == java.sql.Types.INTEGER){
						rslt.put(columnName,   new Integer(rs.getInt(k+1)));
					}else if(columnType == java.sql.Types.CLOB){
						rslt.put(columnName,  rs.getString(k+1));
					}else if(columnType == java.sql.Types.BLOB){
						rslt.put(columnName,  rs.getBytes(k+1));
					}else {
						rslt.put(columnName,  rs.getObject(k+1));
					}
				}
				break;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.close(rs);
			this.close(pstmt);
			this.close(conn);
		}
		return rslt;
	}
	public Map queryForMap2(String sql,Object[] paramsValue,String jndiName){
		Map rslt = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			if(DEFAULT_JNDINAME.equals(jndiName)){
				conn = this.getConnection();
			}else{
				conn = getConnection(jndiName);
			}
			pstmt = conn.prepareStatement(sql);
			if(paramsValue!=null){
				for(int i=0;i<paramsValue.length;i++){
					pstmt.setObject(i+1, paramsValue[i]);
				}
			}
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			while(rs.next()){
				rslt = new HashMap();
				for(int k=0;k<rsmd.getColumnCount();k++){
					String columnName = rsmd.getColumnName(k+1);
					int columnType = rsmd.getColumnType(k+1); //属性类型
					if(columnType == java.sql.Types.DATE ||
					   columnType == java.sql.Types.TIMESTAMP
					){ //日期类型
						if(rs.getTimestamp(k+1)==null) rslt.put(columnName, null);
						else rslt.put(columnName,  new java.util.Date(rs.getTimestamp(k+1).getTime()));
					}else if(columnType == java.sql.Types.INTEGER){
						rslt.put(columnName,   new Integer(rs.getInt(k+1)));
					}else if(columnType == java.sql.Types.CLOB){
						rslt.put(columnName,  rs.getString(k+1));
					}else if(columnType == java.sql.Types.BLOB){
						rslt.put(columnName,  rs.getBytes(k+1));
					}else {
						rslt.put(columnName,  rs.getObject(k+1));
					}
				}
				break;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.close(rs);
			this.close(pstmt);
			this.close(conn);
		}
		return rslt;
	}
	
	
	/**
	 * 执行sql查询
	 * @param sql          带命名参数的sql，例如：select * from TableName where columName2=:v1 and columName3 = :v2;
	 * @param paramsNamed  没有命名参数，传入 null,String[]{"v1","v2"}
	 * @param paramsValue  没有命名参数，传入 null,Object[]
	 * @return 执行异常返回null
	 */
	public String queryForString(String sql,String[] paramsNamed,Object[] paramsValue){
		return this.queryForString(sql, paramsNamed, paramsValue, DEFAULT_JNDINAME);
	}
	public String queryForString2(String sql,Object[] paramsValue){
		return this.queryForString2(sql, paramsValue, DEFAULT_JNDINAME);
	}
	/**
	 * 执行sql查询
	 * @param sql          带命名参数的sql，例如：select * from TableName where columName2=:v1 and columName3 = :v2;
	 * @param paramsNamed  没有命名参数，传入 null
	 * @param paramsValue  没有命名参数，传入 null
	 * @param jndiName   DataSource的jndiName
	 * @return 执行异常返回null
	 */
	public String queryForString(String tmpsql,String[] paramsNamed,Object[] paramsValue,String jndiName){
		String sql = tmpsql;
		String rslt = null;
		if(paramsNamed!=null){
			for(int i=0;i<paramsNamed.length;i++){
				sql = sql.replaceAll(":"+paramsNamed[i], "?");
			}
		}
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			if(DEFAULT_JNDINAME.equals(jndiName)){
				conn = this.getConnection();
			}else{
				conn = getConnection(jndiName);
			}
			pstmt = conn.prepareStatement(sql);
			if(paramsNamed!=null){
				for(int i=0;i<paramsNamed.length;i++){
					pstmt.setObject(i+1, paramsValue[i]);
				}
			}
			rs = pstmt.executeQuery();
			while(rs.next()){
				rslt = rs.getString(1);
				break;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.close(rs);
			this.close(pstmt);
			this.close(conn);
		}
		return rslt;
	}
	public String queryForString2(String sql,Object[] paramsValue,String jndiName){
		String rslt = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			if(DEFAULT_JNDINAME.equals(jndiName)){
				conn = this.getConnection();
			}else{
				conn = getConnection(jndiName);
			}
			pstmt = conn.prepareStatement(sql);
			if(paramsValue!=null){
				for(int i=0;i<paramsValue.length;i++){
					pstmt.setObject(i+1, paramsValue[i]);
				}
			}
			rs = pstmt.executeQuery();
			while(rs.next()){
				rslt = rs.getString(1);
				break;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.close(rs);
			this.close(pstmt);
			this.close(conn);
		}
		return rslt;
	}
	
	/**
	 * 执行更新、删除、插入 sql
	 * @param sql         带命名参数的sql，例如：delete  from TableName where columName2=:v1 and columName3 = :v2;
	 * @param paramsNamed 没有命名参数，传入 null
	 * @param paramsValue 没有命名参数，传入 null
	 * @return 返回影响记录的跳数（同PreparedStatement.exuecuteUpdate(sql)的值） 执行异常返回null
	 */
	public int executeSQL(String sql,String[] paramsNamed,Object[] paramsValue){
		return this.executeSQL(sql, paramsNamed, paramsValue, DEFAULT_JNDINAME);
	}
	public int executeSQL(String sql,Object[] paramsValue){
		return this.executeSQL(sql, paramsValue, DEFAULT_JNDINAME);
	}
	public int executeSQL2(String sql,Object[] paramsValue){
		return this.executeSQL2(sql, paramsValue, DEFAULT_JNDINAME);
	}
	public int executeSQL2(String sql){
		return this.executeSQL2(sql, DEFAULT_JNDINAME);
	}
	/**
	 * 执行更新、删除、插入 sql
	 * @param sql         带命名参数的sql，例如：delete  from TableName where columName2=:v1 and columName3 = :v2;
	 * @param paramsNamed 没有命名参数，传入 null
	 * @param paramsValue 没有命名参数，传入 null
	 * @param jndiName 数据源名
	 * @return 返回影响记录的跳数（同PreparedStatement.exuecuteUpdate(sql)的值） 执行异常返回null
	 */
	public int executeSQL(String tmpsql,String[] paramsNamed,Object[] paramsValue,String jndiName){
		String sql = tmpsql;
		int rslt = 0;
		if(paramsNamed!=null){
			for(int i=0;i<paramsNamed.length;i++){
				sql = sql.replaceAll(":"+paramsNamed[i], "?");
			}
		}
		Connection conn = null;
		PreparedStatement pstmt = null;
		try{
			if(DEFAULT_JNDINAME.equals(jndiName)){
				conn = this.getConnection();
			}else{
				conn = getConnection(jndiName);
			}
			pstmt = conn.prepareStatement(sql);
			if(paramsNamed!=null){
				for(int i=0;i<paramsNamed.length;i++){
					pstmt.setObject(i+1, paramsValue[i]);
				}
			}
			rslt = pstmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.close(pstmt);
			this.close(conn);
		}
		return rslt;
	}
	public int executeSQL(String sql,Object[] paramsValue,String jndiName){
		int rslt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try{
			if(DEFAULT_JNDINAME.equals(jndiName)){
				conn = this.getConnection();
			}else{
				conn = getConnection(jndiName);
			}
			pstmt = conn.prepareStatement(sql);
			if(paramsValue!=null){
				for(int i=0;i<paramsValue.length;i++){
					pstmt.setObject(i+1, paramsValue[i]);
				}
			}
			rslt = pstmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.close(pstmt);
			this.close(conn);
		}
		return rslt;
	}
	public int executeSQL2(String sql,Object[] paramsValue,String jndiName){
		int rslt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try{
			if(DEFAULT_JNDINAME.equals(jndiName)){
				conn = this.getConnection();
			}else{
				conn = getConnection(jndiName);
			}
			pstmt = conn.prepareStatement(sql);
			if(paramsValue!=null){
				for(int i=0;i<paramsValue.length;i++){
					pstmt.setObject(i+1, paramsValue[i]);
				}
			}
			rslt = pstmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.close(pstmt);
			this.close(conn);
		}
		return rslt;
	}
	public int executeSQL2(String sql,String jndiName){
		int rslt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try{
			if(DEFAULT_JNDINAME.equals(jndiName)){
				conn = this.getConnection();
			}else{
				conn = getConnection(jndiName);
			}
			pstmt = conn.prepareStatement(sql);
			rslt = pstmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.close(pstmt);
			this.close(conn);
		}
		return rslt;
	}
	
	/**
	 * 批量执行更新语句
	 * @param sql         带命名参数的sql，例如：delete  from TableName where columName2=:v1 and columName3 = :v2;
	 * @param paramsNamed 没有命名参数，传入 null
	 * @param paramsValue 没有命名参数，传入 null
	 */
	public boolean executeSQLBatch(List<String> sql,List<String[]> paramsNamed,List<Object[]> paramsValue){
		return this.executeSQLBatch(sql, paramsNamed, paramsValue, DEFAULT_JNDINAME);
	}
	public boolean executeSQLBatch2(List<String> sql,List<Object[]> paramsValue){
		return this.executeSQLBatch2(sql, paramsValue, DEFAULT_JNDINAME);
	}
	/**
	 * 批量执行更新语句
	 * @param sql         带命名参数的sql，例如：delete  from TableName where columName2=:v1 and columName3 = :v2;
	 * @param paramsNamed 没有命名参数，传入 null
	 * @param paramsValue 没有命名参数，传入 null
	 * @param jndiName 数据源名
	 */
	public boolean executeSQLBatch(List<String> sql,List<String[]> paramsNamed,List<Object[]> paramsValue,String jndiName){
		boolean rslt = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try{
			if(DEFAULT_JNDINAME.equals(jndiName)){
				conn = this.getConnection();
			}else{
				conn = getConnection(jndiName);
			}
			conn.setAutoCommit(false);
			int sqlSize = 0;
			if(null!=sql) sqlSize = sql.size();
			for(int i=0;i<sqlSize;i++){
				String s = sql.get(i);
				
				String[] pn = null;
				if((null != paramsNamed) &&(paramsNamed.size()>i)) pn = paramsNamed.get(i);
				if(pn!=null){
					for(int k=0;k<pn.length;k++){
						s = s.replaceAll(":"+pn[k], "?");
					}
				}
				
				pstmt = conn.prepareStatement(s);
				
				Object[] pv = null;
				if((null != paramsValue) &&(paramsValue.size()>i)){
					pv = paramsValue.get(i);
				}
				
				if(pv!=null){
					for(int j=0;j<pn.length;j++){
						pstmt.setObject(j+1, pv[j]);
					}
				}
				
				pstmt.executeUpdate();
				this.close(pstmt);
			}
			conn.commit();
			rslt = true;
		}catch(Exception e){
			e.printStackTrace();
			try{
				conn.rollback();
			}catch(Exception ee){
				ee.printStackTrace();
			}
		}finally{
			try{
				conn.setAutoCommit(true);
			}catch(Exception e){
				e.printStackTrace();
			}
			this.close(pstmt);
			this.close(conn);
		}
		return rslt;
	}
	public boolean executeSQLBatch2(List<String> sql,List<Object[]> paramsValue,String jndiName){
		boolean rslt = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try{
			if(DEFAULT_JNDINAME.equals(jndiName)){
				conn = this.getConnection();
			}else{
				conn = getConnection(jndiName);
			}
			conn.setAutoCommit(false);
			int sqlSize = 0;
			if(null!=sql) sqlSize = sql.size();
			for(int i=0;i<sqlSize;i++){
				String s = sql.get(i);

				pstmt = conn.prepareStatement(s);
				
				Object[] pv = null;
				if((null != paramsValue) &&(paramsValue.size()>i)){
					pv = paramsValue.get(i);
				}
				
				if(pv!=null){
					for(int j=0;j<pv.length;j++){
						pstmt.setObject(j+1, pv[j]);
					}
				}
				
				pstmt.executeUpdate();
				this.close(pstmt);
			}
			conn.commit();
			rslt = true;
		}catch(Exception e){
			e.printStackTrace();
			try{
				conn.rollback();
			}catch(Exception ee){
				ee.printStackTrace();
			}
		}finally{
			try{
				conn.setAutoCommit(true);
			}catch(Exception e){
				e.printStackTrace();
			}
			this.close(pstmt);
			this.close(conn);
		}
		return rslt;
	}
	
	/**
	 * 
	 * 批量插入数据 
	 * @param sql               带命名符的sql语句
	 * @param paramsNamed       命名数组
	 * @param paramsValueList   插入的数据值List
	 * @param jndiName
	 * @return      
	 * @author        liumc
	 * @Date          2016-7-22 下午4:59:24
	 */
	public boolean executeSQLBatch(String tmpsql,String[] paramsNamed,List<Object[]> paramsValueList,String jndiName){
		boolean rslt = false;
		String sql = tmpsql;
		int paramsNamedLen = paramsNamed.length;
		if(paramsNamed!=null){
			for(int i=0;i<paramsNamedLen;i++){
				sql = sql.replaceAll(":"+paramsNamed[i], "?");
			}
		}
		Connection conn = null;
		PreparedStatement pstmt = null;
		try{
			if(DEFAULT_JNDINAME.equals(jndiName)){
				conn = this.getConnection();
			}else{
				conn = getConnection(jndiName);
			}
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			int paramsValueListSize = 0;
			if(null != paramsValueList) paramsValueListSize = paramsValueList.size();
			
			for(int k=0;k<paramsValueListSize;k++){
				Object[] paramsValue = (Object[])paramsValueList.get(k);
				if(paramsNamed!=null){
					for(int i=0;i<paramsNamedLen;i++){
						pstmt.setObject(i+1, paramsValue[i]);
					}
				}
				pstmt.addBatch();
			}
			
			pstmt.executeBatch();
			conn.commit();
			rslt = true;
			paramsValueList.clear();
			paramsValueList = null;
			paramsNamed = null;
			sql = null;
		}catch(Exception e){
			e.printStackTrace();
			try{
				conn.rollback();
			}catch(Exception ee){
				ee.printStackTrace();
			}
		}finally{
			try{
				conn.setAutoCommit(true);
			}catch(Exception e){
				e.printStackTrace();
			}
			this.close(pstmt);
			this.close(conn);
		}
		return rslt;
	}
	public boolean executeSQLBatch(String sql,String[] paramsNamed,List<Object[]> paramsValueList){
		boolean rslt =  false;
		rslt = this.executeSQLBatch(sql, paramsNamed, paramsValueList, DEFAULT_JNDINAME);
		return rslt;
	}
	
	/**
	 * 获取自定义数据表主键id的值，
	 * 保持跟IJdbcDAO中getSEQRecord(Long domainId)方法逻辑一致
	 * @return      
	 * @author        liumc
	 * @Date          2016-7-1 上午9:28:23
	 */
	public long getCusmTableId(Long domainId){
		/*
		long rslt = -1;
		String DBType = SystemCommon.getDatabaseType();
		//ts_seq表中查询对应的字段的值
		String uptSQL = "update TS_SEQ set SEQ_RECORD = SEQ_RECORD+1   where DOMAIN_ID=" + domainId;
		String querySQL = "select SEQ_RECORD from TS_SEQ  where DOMAIN_ID=" + domainId;
		int cnt = this.executeSQL(uptSQL, null, null);
		if(cnt<1) {
			uptSQL = "insert into TS_SEQ(SEQ_VALUE,SEQ_TABLE,SEQ_FIELD,SEQ_TABLECODE,SEQ_FIELDCODE,SEQ_RECORD,DOMAIN_ID)values(100,100,100,100,100,100," + domainId + ")" ;
			this.executeSQL(uptSQL, null, null);
		}
		rslt = this.queryForLong(querySQL, null, null);
		return rslt;
		*/
		return this.getSystemKeyId(domainId);
	}
	
	/**
	 * 
	 * 获得系统ID 
	 * oracle中使用hibernate_sequence.nextval,mysql或mssql使用存储过程 
	 * @return      
	 * @author        liumc
	 * @Date          2016-7-4 下午5:56:54
	 */
	public long getSystemKeyId(Long domainId){
		long rslt = -1;
		String str="{call GETTABLEID_BYTYPE(?,?,?)}";
		String type = "value";
		java.sql.Connection conn = null;
		java.sql.CallableStatement callstmt = null;
		try{
			//conn = this.getConnection(DEFAULT_JNDINAME);
			conn = this.getConnection(); //调用系统默认的数据源
			callstmt = conn.prepareCall(str);
			callstmt.setLong(1, domainId);// 设置输入参数的值   
			callstmt.setString(2, type.toLowerCase()); //类型
			callstmt.registerOutParameter(3,java.sql.Types.BIGINT);// 注册输出参数的类型   
			callstmt.execute();
			rslt = callstmt.getLong(3);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.close(callstmt);
			this.close(conn);
		}
		return rslt;
	}
}
