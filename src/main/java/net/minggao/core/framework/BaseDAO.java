package net.minggao.core.framework;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * BaseDAO，数据库操作基础类，
 * 1、参数为hql或hsql的是hibernate的查询和更新语句
 * 2、参数为sql的时jdbc的查询和更新语句
 * @author mac
 *
 */
public interface BaseDAO {
	/**
	 * 获取当前数据库类型
	 * @return
	 */
	public String getDataBaseType();
	/**
	 * 
	 * hibernate查询单个记录PO 
	 * @param c      PO类
	 * @param id     记录Id
	 * @return      
	 * @author        liumc
	 * @Date          2017-1-12 下午4:31:44
	 */
    public Object getObject(Class c, Serializable id);
    
    /**
     * 
     * Hibernate查询所有数据 
     * @param c      PO类
     * @return      List<PO>
     * @author        liumc
     * @Date          2017-1-12 下午4:32:20
     */
    public List<Object> getAllList(Class c);
    
    /**
     * 
     * hibernate查询总的记录条数 
     * @param c     PO类
     * @return      
     * @author        liumc
     * @Date          2017-1-12 下午4:32:50
     */
    public Long getTotalCount(Class c);
    
    /**
     * 
     * hibernate保存对象 
     * @param po     PO对象
     * @return      
     * @author        liumc
     * @Date          2017-1-12 下午4:33:12
     */
    public Serializable save(Object po);
    
    /**
     * 
     * 清除hibernate缓存对象 
     * @param hibernatePO      
     * @author        liumc
     * @Date          2017-10-17 下午6:09:31
     */
    public void evict(Object hibernatePO);
    
    /**
     * 
     * hibernate更新pO对象 
     * @param po      PO对象
     * @author        liumc
     * @Date          2017-1-12 下午4:33:29
     */
    public boolean update(Object po);
    
    /**
     * 
     * hibernate删除PO对象 
     * @param po      PO对象
     * @author        liumc
     * @Date          2017-1-12 下午4:33:45
     */
    public boolean delete(Object po);
    
    /**
     * 
     * hibernate根据PO的Id删除对象 
     * @param c       PO类
     * @param id      对象Id
     * @author        liumc
     * @Date          2017-1-12 下午4:34:02
     */
    public boolean delete(Class c, Long id);
    
    /**
     * 
     * Hibernate批量删除PO对象 
     * @param c       PO类
     * @param ids     对象id数组=》String[]
     * @author        liumc
     * @Date          2017-1-12 下午4:34:25
     */
    public boolean delete(Class c, String[] ids);
    
//    /**
//     * 
//     * hibernate分页查询 
//     * @param hql            hibernate查询语句
//     * @param firstResult    起始记录数
//     * @param maxResults     一页查询最大记录数
//     * @return List<Object[]/PO>
//     * @author        liumc
//     * @Date          2017-1-12 下午4:34:56
//     * @deprecated
//     */
//    public List queryByPage(String hql,int firstResult,int maxResults);
    
    /**
     * 
     * hibernate分页查询 
     * @param hql              hibernate查询语句
     * @param namedParams      命名参数数组，String[]{"xxx",,,,...,"xxx"}
     * @param namedParaValues  命名对应数值值,Object[]{obj1,obj2,obj3,...,objx}
     * @param firstResult      其实记录数，从0开始
     * @param maxResults       一页查询最大记录数
     * @return      
     * @author        liumc
     * @Date          2017-1-12 下午4:35:56
     */
    public List queryByPage(String hql,String[] namedParams,Object[] namedParaValues,int firstResult,int maxResults);
    /**
     * 
     * hibernate分页查询 
     * @param hql              hibernate查询语句
     * @param namedParaValues  命名对应数值值,Object[]{obj1,obj2,obj3,...,objx}
     * @param firstResult      其实记录数，从0开始
     * @param maxResults       一页查询最大记录数
     * @return      
     * @author        liumc
     * @Date          2017-1-12 下午4:35:56
     */
    public List queryByPage(String hql,Object[] namedParaValues,int firstResult,int maxResults);
    
//    /**
//     * 
//     * hibernate查询 
//     * @param hql    hibernat查询sql
//     * @return      
//     * @author        liumc
//     * @Date          2017-1-12 下午4:37:42
//     * @deprecated
//     */
//    public List query(String hql);
    
    /**
     * 
     * hibernate查询 
     * @param hql             hibernate查询sql
     * @param namedParams     命名参数数组，String[]{"xxx",,,,...,"xxx"}
     * @param namedParaValues 命名对应数值值,Object[]{obj1,obj2,obj3,...,objx}
     * @return      
     * @author        liumc
     * @Date          2017-1-12 下午4:37:58
     */
    public List query(String hql,String[] namedParams,Object[] namedParaValues);
    /**
     * 
     * hibernate查询 
     * @param hql             hibernate查询sql
     * @param namedParaValues 命名对应数值值,Object[]{obj1,obj2,obj3,...,objx}
     * @return      
     * @author        liumc
     * @Date          2017-1-12 下午4:37:58
     */
    public List query(String hql,Object[] namedParaValues);
    
    /**
     * 
     * hibernate查询总的记录数 
     * @param hsql         hibernate查询sql
     * @param paramNames   命名参数数组，String[]{"xxx",,,,...,"xxx"}
     * @param paramValues  命名对应数值值,Object[]{obj1,obj2,obj3,...,objx}
     * @return      
     * @author        liumc
     * @Date          2017-1-12 下午4:38:44
     */
    public long queryCount(String hsql,String[] paramNames,Object[] paramValues);
    /**
     * 
     * hibernate查询总的记录数 
     * @param hsql         hibernate查询sql
     * @param paramValues  命名对应数值值,Object[]{obj1,obj2,obj3,...,objx}
     * @return      
     * @author        liumc
     * @Date          2017-1-12 下午4:38:44
     */
    public long queryCount(String hsql,Object[] paramValues);
    
//    /**
//     * 
//     * hibernate查询总的记录数 
//     * @param hsql    hibernate查询sql
//     * @return      
//     * @author        liumc
//     * @Date          2017-1-12 下午4:39:15
//     * @deprecated
//     */
//	public long queryCount(String hsql);
	/**
     * 
     * hibernate查询总的记录数 
     * @param hsql    hibernate查询sql
     * @param paramNames   命名参数数组，String[]{"xxx",,,,...,"xxx"}
     * @param paramValues  命名对应数值值,Object[]{obj1,obj2,obj3,...,objx}
     * @return      
     * @author        liumc
     * @Date          2017-1-12 下午4:39:15
     */
	public long getRecordsCount(String hsql,String[] paramNames,Object[] paramValues);
	/**
     * 
     * hibernate查询总的记录数 
     * @param hsql    hibernate查询sql
     * @param paramValues  命名对应数值值,Object[]{obj1,obj2,obj3,...,objx}
     * @return      
     * @author        liumc
     * @Date          2017-1-12 下午4:39:15
     */
    public long getRecordsCount(String hsql,Object[] paramValues);
    
//    /**
//     * 
//     * hibernate查询总的记录数 
//     * @param hsql    hibernate查询sql
//     * @return      
//     * @author        liumc
//     * @Date          2017-1-12 下午4:39:15
//     * @deprecated
//     */
//	public long getRecordsCount(String hsql);
	
	/**
	 * 
	 * hibernate的语句的更新语句，示例：
	 * hsql = " update net.minggao.xxxx.model.User user set user.name = :name where user.id = :id";   "update net.minggao.xxxx.model.User user  set user.name = ? where user.id = ? "
	 * hsql = " delete net.minggao.xxxx.model.User user  where user.id = :id "; " delete net.minggao.xxxx.model.User user  where user.id = ?  "
	 * hsql = " insert into net.minggao.xxxx.model.EntityNames(propertiesName,propertiesName,,,) select u.id,u.name from net.minggao.xxxx.model.user as u where u.domainId = :domain"; " insert into net.minggao.xxxx.model.EntityNames(propertiesName,propertiesName,,,) select u.id,u.name from net.minggao.xxxx.model.user as u where u.domainId = ? ";
	 * 注意 insert语句，不支持insert into tableName(,,) values(xxxx)形式的hsql语句，使用的时候请注意
	 * hsql不能用于存储过程
	 *   这里的po类必须使用全路径名，如：net.minggao.demo.model.User u 
	 * @param hsql
	 * @param paramNameds
	 * @param paraValues      
	 * @author        liumc
	 * @Date          2017-1-12 下午2:58:59
	 */
	public void executeUpdate(String hsql,String[] paramNameds,Object[] paraValues);
	
	/**
	 * 
	 * hibernate的语句的更新语句，示例：
	 * hsql = " update net.minggao.xxxx.model.User user set user.name = :name where user.id = :id";   "update net.minggao.xxxx.model.User user  set user.name = ? where user.id = ? "
	 * hsql = " delete net.minggao.xxxx.model.User user  where user.id = :id "; " delete net.minggao.xxxx.model.User user  where user.id = ?  "
	 * hsql = " insert into net.minggao.xxxx.model.EntityNames(propertiesName,propertiesName,,,) select u.id,u.name from net.minggao.xxxx.model.user as u where u.domainId = :domain"; " insert into net.minggao.xxxx.model.EntityNames(propertiesName,propertiesName,,,) select u.id,u.name from net.minggao.xxxx.model.user as u where u.domainId = ? ";
	 * 注意 insert语句，不支持insert into tableName(,,) values(xxxx)形式的hsql语句，使用的时候请注意
	 * 这里的po类必须使用全路径名，如：net.minggao.demo.model.User u 
	 * hsql不能用于存储过程
	 *   
	 * @param hsql
	 * @param paramNameds
	 * @param paraValues      
	 * @author        liumc
	 * @Date          2017-1-12 下午2:58:59
	 */
	public void executeUpdate(String hsql,Object[] paraValues);
	
	/**
	 * 
	 * 执行无返回值的存储过程 
	 * @param queryString
	 * @param params
	 * @throws Exception      
	 * @author        liumc
	 * @Date          2017-1-12 下午3:30:37
	 */
	public void executeVoidProcedureSQL(final String queryString,final Object[] params) throws Exception;
	
	
	/*---- 下面都是jdbc处理方法 ----------------------------------------------------------------------------------------------------------------*/
	
	/**
	 *
	 * 执行sql查询
	 * @param sql          带命名参数的sql，例如：select * from TableName where columName2=:v1 and columName3 = :v2;
	 * @param paramsNamed  没有命名参数，传入 null,String[]
	 * @param paramsValue  没有命名参数，传入 null,Object[]
	 * @return    
	 * @author        liumc
	 * @Date          2017-1-12 下午4:40:17
	 */
	public List<Map<String, Object>> queryJdbcForMapList(String sql,String[] paramsNamed,Object[] paramsValue);
	
	/**
	 * 
	 * 执行sql查询
	 * @param sql          带命名参数的sql，例如：select * from TableName where columName2=? and columName3 = ?;
	 * @param paramsValue  没有命名参数，传入 null,Object[]
	 * @return  
	 * @author        liumc
	 * @Date          2017-1-12 下午4:40:42
	 */
	public List<Map<String, Object>> queryJdbcForMapList(String sql,Object[] paramsValue);
	/**
	 * 
	 * 执行sql查询
	 * @param sql          带命名参数的sql，例如：select * from TableName where columName2=? and columName3 = ?;
	 * @param paramsValue  没有命名参数，传入 null,Object[]
	 * @return  
	 * @author        liumc
	 * @Date          2017-1-12 下午4:40:42
	 */
	public java.util.List queryForMapList2(String sql,Object[] params);
	
	/**
	 * 
	* 查询sql，返回long类型值，失败返回-1
	 * @param sql          带命名参数的sql，如：select count(1) from TableName where columName2=:v1 and columName3 = :v2;
	 * @param paramsNamed  没有命名参数，传入 null,String[]{"v1","v2"}
	 * @param paramsValue  没有命名参数，传入 null,Object[]
	 * @return 执行异常返回-1  
	 * @author        liumc
	 * @Date          2017-1-12 下午4:41:06
	 */
	public long queryJdbcForLong(String sql,String[] paramsNamed,Object[] paramsValue);
	
	/**
	 * 
	  * 查询sql，返回long类型值，失败返回-1
	 * @param sql          带命名参数的sql，如：select count(1) from TableName where columName2=? and columName3 = ?;
	 * @param paramsValue  没有命名参数，传入 null,Object[]
	 * @return 执行异常返回-1
	 * @author        liumc
	 * @Date          2017-1-12 下午4:41:53
	 */
	public long queryJdbcForLong(String sql,Object[] paramsValue);
	
	/**
	 * 
	  * 执行sql查询
	 * @param sql          带命名参数的sql，例如：select * from TableName where columName2=:v1 and columName3 = :v2;
	 * @param paramsNamed  没有命名参数，传入 null,String[]{"v1","v2"}
	 * @param paramsValue  没有命名参数，传入 null,Object[]
	 * @return 执行异常返回null
	 * @author        liumc
	 * @Date          2017-1-12 下午4:42:09
	 */
	public Map queryJdbcForMap(String sql,String[] paramsNamed,Object[] paramsValue);
	
	/**
	 * 
	 * 执行sql查询
	 * @param sql          带命名参数的sql，例如：select * from TableName where columName2=? and columName3 = ?;
	 * @param paramsValue  没有命名参数，传入 null,Object[]
	 * @return 执行异常返回null
	 * @author        liumc
	 * @Date          2017-1-12 下午4:45:37
	 */
	public Map queryJdbcForMap(String sql,Object[] paramsValue);
	
	/**
	 * 
	  * 执行sql查询
	 * @param sql          带命名参数的sql，例如：select * from TableName where columName2=:v1 and columName3 = :v2;
	 * @param paramsNamed  没有命名参数，传入 null
	 * @param paramsValue  没有命名参数，传入 null
	 * @return 执行异常返回null
	 * @author        liumc
	 * @Date          2017-1-12 下午4:46:05
	 */
	public String queryJdbcForString(String sql,String[] paramsNamed,Object[] paramsValue);
	
	/**
	 * 
	 * 执行sql查询
	 * @param sql          带命名参数的sql，例如：select * from TableName where columName2=? and columName3 = ?;
	 * @param paramsValue  没有命名参数，传入 null
	 * @return 执行异常返回null
	 * @author        liumc
	 * @Date          2017-1-12 下午4:47:02
	 */
	public String queryJdbcForString(String sql,Object[] paramsValue);
	
	/**
	 * 
	 * 执行更新、删除、插入 sql
	 * @param sql         带命名参数的sql，例如：delete  from TableName where columName2=:v1 and columName3 = :v2;
	 * @param paramsNamed 没有命名参数，传入 null
	 * @param paramsValue 没有命名参数，传入 null
	 * @return 返回影响记录的跳数（同PreparedStatement.exuecuteUpdate(sql)的值） 执行异常返回null
	 * @author        liumc
	 * @Date          2017-1-12 下午4:47:31
	 */
	public int executeJdbcSQL(String sql,String[] paramsNamed,Object[] paramsValue);
	
	/**
	 * 
	 * 执行更新、删除、插入 sql
	 * @param sql         带命名参数的sql，例如：delete  from TableName where columName2=? and columName3 = ?;
	 * @param paramsValue 没有命名参数，传入 null
	 * @return 返回影响记录的跳数（同PreparedStatement.exuecuteUpdate(sql)的值） 执行异常返回null
	 * @author        liumc
	 * @Date          2017-1-12 下午4:47:51
	 */
	public int executeJdbcSQL(String sql,Object[] paramsValue);
	
//	/**
//	 * 
//	 * 执行更新、删除、插入 sql
//	 * @deprecated
//	 * @param sql
//	 * @return      
//	 * @author        liumc
//	 * @Date          2017-5-18 下午5:44:15
//	 */
//	public int executeJdbcSQL(String sql);
	
	/**
	 * 
	 * 批量执行更新语句[使用声明性事务处理，在这里就不用单独针对connection设置事务了]
	 * @param sql         带命名参数的List<sql>，例如：delete  from TableName where columName2=:v1 and columName3 = :v2;
	 * @param paramsNamed 没有命名参数，传入 null List<String[]>
	 * @param paramsValue 没有命名参数，传入 null List<Object[]>
	 * @return      
	 * @author        liumc
	 * @Date          2017-1-12 下午4:48:07
	 */
	public boolean executeJdbcSQLBatch(List<String> sql,List<String[]> paramsNamed,List<Object[]> paramsValue);
	
	/**
	 * 
	  * 批量执行更新语句[使用声明性事务处理，在这里就不用单独针对connection设置事务了]
	 * @param sql         带命名参数的List<sql>，例如：delete  from TableName where columName2=? and columName3 = ?;
	 * @param paramsValue 没有命名参数，传入 null List<Object[]>
	 * @return      
	 * @author        liumc
	 * @Date          2017-1-12 下午4:48:27
	 */
	public boolean executeJdbcSQLBatch(List<String> sql,List<Object[]> paramsValue);
	
	
	/**
	 * 
	  * 批量执行更新语句[使用声明性事务处理，在这里就不用单独针对connection设置事务了]
	 * @param sql         带命名参数的List<sql>，例如：delete  from TableName where columName2=:xxx and columName3 = :xxx;
	 * @param  namedArr   new String[]{"xxx","xxx",...,"xxx"}
	 * @param valueList 没有命名参数，传入 null List<Object[]>
	 * @return      
	 * @author        liumc
	 * @Date          2017-1-12 下午4:48:27
	 */
	public boolean executeJdbcSQLBatch(String sql,String[] namedArr,List<Object[]> valueList);
	
	/**
     * 通过存储过程查询(单结果集)
     * @param sql 查询jdbc sql-->存储过程对应的sql
     * @param params 参数
     * @param columnNum 返回的列数
     * @return List<Map<String,Object>>
     */
    public List<Map<String, Object>> queryByProcedureSingle(final String sql,final Object[] params);
    
    /**
     * 通过存储过程查询(多结果集)
     * @param sql 查询sql
     * @param params 参数
     * @param columnNum 返回的列数
     * @return
     */
    public List<List<Map<String, Object>>> queryByProcedureMulti(final String sql,final Object[] params);
    
	/**
	 * 获取oracle中自动增长的值
	 * @param type 类型，sys或flowProc
	 * @return
	 */
	public Long getAutoIncrValue(String type);
	/**
	 * 获取自动增长的seq值
	 * @param domainId
	 * @return
	 */
	public Long getSEQValue(Long domainId);
	/**
	 * 获取自定义表的id
	 * @param domainId
	 * @return
	 */
	public Long getSEQTable(Long domainId);
	/**
	 * 获取自定义表的编码
	 * @param domainId
	 * @return
	 */
	public Long getSEQTableCode(Long domainId);
	/**
	 * 获取自定义字段的id
	 * @param domainId
	 * @return
	 */
	public Long getSEQField(Long domainId);
	/**
	 * 获取自定义字段的编码
	 * @param domainId
	 * @return
	 */
	public Long getSEQFieldCode(Long domainId);
	/**
	 * 获取记录的id
	 * @param domainId
	 * @return
	 */
	public Long getSEQRecord(Long domainId);
	/**
	 * 获取自定义数据表的最大id
	 * @param tableName         自定义数据表名
	 * @param primaryKeyField   主键字段名
	 * @return
	 */
	public Long getMaxId(String tableName,String primaryKeyField);
	
	/**
	 * 获取自定义表单id
	 * @param domainId
	 * @return
	 */
	public Long getSEQForm(Long domainId);
	
	
	/**
	  * <p>查询当前记录，排列位置,返回前后位置标识和所排列记录主键id</p>
	  * @param po 持久化对象
	  * @param idField 持久化对象主键字段名称
	  * @param parentIdField 父级字段名称
	  * @param sortField 排序字段名称
	  * @param id	主键字段值
	  * @param otherCondition 附加条件
	  * @return String[0]: 0-排前,1-排后;String[1]:所排列记录主键id
	  */
	 public String[] getSortLocation(String po,String idField,String parentIdField,String sortField,Long id,String otherCondition);
	 
	 /**
	  * 获取修改页面的字段排序前后位置值
	  * @param po
	  * @param idField
	  * @param sortField
	  * @param id
	  * @param otherCondition
	  * @return
	  */
	 public String[] getSortLocation(String po,String idField,String sortField, Long id, String otherCondition);
	 
	 /**
	  * 
	  * <p>获取对象层级节点的idString串及orderCode排序码
	  * 注：新增保存时，保存(save)之后调用此方法，然后再更新(update)对象
	  *   修改保存时，保存(save)之前调用此方法，然后再更新(update)对象
	  * </p>
	  * @param po 					持久化对象
	  * @param idField				ID主键字段
	  * @param parentIdField		当前记录父级字段名称
	  * @param idStringField		当前记录父级idString字段名称
	  * @param nameStringField		当前记录父级nameStringField字段名称,可选字段，如没有null
	  * @param leveField			当前记录层级字段名称，字段类型数字型，可选字段，如没有null
	  * @param sortField			排序字段名称
	  * @param id       			当前记录主键ID值
	  * @param parentId             所择的上级节点
	  * @param name					当前记录名称值
	  * @param currentOrderId  	    当前选择的排序列表记录的主键id值
	  * @param sortLocation 		当前选择的排列位置0-排前,1-排后
	  * @return String[0]=idString,String[1]=nameString,String[2]=orderCode,String[3]=level
	  */
	 public String[] getSortCodeAndIdString(String po,String idField,String parentIdField,String idStringField,String nameStringField,
			 String levelField,String sortField, Long id,String parentId,String naem,String currentOrderId,String sortLocation);
	 
	 /**
	  * 获取用户选择字段排序前后的排序码值(保存或更新前调用)
	  * @param po
	  * @param otherCondition
	  * @param idField
	  * @param sortField
	  * @param currentOrderId
	  * @param sortLocation
	  * @return
	  */
	 public int getSortCode(String po,String otherCondition,String idField,String sortField,String currentOrderId,String sortLocation);
	 
	 /**
		 * 批量更新
		 * @param sql (如:insert into demo(name,value)values(?,?))
		 * @param cols (操作列数)
		 * @param dataList(为数组:new String[]{"string","张三"}或为Object对象)
		 * @return
		 */
		public int saveOrUpdateAll(final String sql,int cols,List dataList);
		/**
		 * 批量更新
		 * @param sql (如:insert into demo(name,value)values(?,?))
		 * @param dataList(为数组:new Object[]{"nameValue","valueValue"})
		 * @return
		 */
		public int saveOrUpdateAll(final String sql, final List dataList);
		
//		/**
//		 * 执行jdbc查询，查询结果返回的是Object[]数组
//		 * @deprecated
//		 * @param sql
//		 * @return      
//		 * @author        liumc
//		 * @Date          2017-11-29 上午11:08:16
//		 */
//		public java.util.List querytForArray(String sql);
		/**
		 * 执行jdbc查询（占位符），查询结果返回的是Object[]数组
		 * @param sql
		 * @param params
		 * @return
		 */
		public java.util.List querytForArray(String sql,Object[] params);
}
