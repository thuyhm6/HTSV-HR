package com.ait.web.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ait.web.util.limit.LimitSqlExecutor;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.engine.execution.SqlExecutor;
import com.ibatis.sqlmap.engine.impl.ExtendedSqlMapClient;

@Component
public class SqlMapClientSupport extends SqlMapClientDaoSupport{
	@Autowired  
	private SqlMapClient sqlMapClient;
	@Autowired
	private SqlExecutor sqlExecutor;
	@PostConstruct        
	public void initSqlMapClient(){         
		super.setSqlMapClient(sqlMapClient);    
	}
	
	public void setEnableLimit(boolean enableLimit){
		if(sqlExecutor instanceof LimitSqlExecutor){
			((LimitSqlExecutor)sqlExecutor).setEnableLimit(enableLimit);
		}
	}
	/**
	 * 初始化重载带数据库方言的sqlExecutor类
	 **/
	public void initialize() throws Exception{
		if(sqlExecutor!=null){
			this.sqlMapClient=getSqlMapClientTemplate().getSqlMapClient();
			if(sqlMapClient instanceof ExtendedSqlMapClient){
				ReflectUtil.setFieldValue(((ExtendedSqlMapClient) sqlMapClient).getDelegate(),
                        "sqlExecutor", SqlExecutor.class, sqlExecutor);
			}
		}
	}
	
	/**
	 * 无条件的查询,返回单个Object对象
	 * @param statementName 查询语句xml引用
	 * @return 
	 * @throws SQLException
	 */
	public Object queryForObject(String statementName) throws SQLException {
		Object result = this.queryForObject(statementName, null) ;
		return result;
	}
	
	/**
	 * 带条件的查询,返回单个Object对象
	 * @param statementName 查询语句xml引用
	 * @param parameterObject 查询条件参宿
	 * @return 
	 * @throws SQLException
	 */
	public Object queryForObject(String statementName, Object parameterObject) throws SQLException {
		Object result = new Object() ;
		if(parameterObject != null){
			result = this.getSqlMapClientTemplate().queryForObject(statementName,
				parameterObject);
		}
		else{
			result = this.getSqlMapClientTemplate().queryForObject(statementName);
		}
		return result;
	}
	
	/**
	 * 无条件的查询,返回List对象
	 * @param statementName 查询语句xml引用
	 * @return 
	 * @throws SQLException
	 */
	public List queryForList(String statementName)
	throws SQLException {
		List result = this.queryForList(statementName, null) ;
		return result;
	}
	
	/**
	 * 带条件的查询,返回List对象
	 * @param statementName 查询语句xml引用
	 * @param parameterObject 查询条件参宿
	 * @return 
	 * @throws SQLException
	 */
	public List queryForList(String statementName, Object parameterObject)
	throws SQLException {
		
		List result = new ArrayList() ;
		
		if(parameterObject != null){
			result = this.getSqlMapClientTemplate().queryForList(statementName,
				parameterObject);
		}
		else{
			result = this.getSqlMapClientTemplate().queryForList(statementName);
		}
		
		return result;
	}
	
	/**
	 * 无条件,带分页条件的查询
	 * @param statementName 查询语句xml引用
	 * @param skipResults 当前页
	 * @param maxResults 每页容量
	 * @return 
	 * @throws SQLException
	 */
	public List queryForList(String statementName,int skipResults,int maxResults )
	throws SQLException {

		List result = this.queryForList(statementName, null, skipResults, maxResults) ;
		
		return result;
	}
	
	/**
	 * 带条件,带分页条件的查询
	 * @param statementName 查询语句xml引用
	 * @param parameterObject 查询条件参数
	 * @param skipResults 当前页
	 * @param maxResults 每页容量
	 * @return 
	 * @throws SQLException
	 */
	public List queryForList(String statementName, Object parameterObject,int skipResults,int maxResults)
	throws SQLException {
		
		List result = new ArrayList() ;
		
		if(parameterObject != null){
			result = this.getSqlMapClientTemplate().queryForList(statementName, parameterObject, skipResults, maxResults);
		}
		else{
			result = this.getSqlMapClientTemplate().queryForList(statementName, skipResults, maxResults);
		}
		 
		return result;
	}
	
	/**
	 * 无参数的插入
	 * @param statementName 查询语句xml引用
	 * @return 
	 * @throws SQLException
	 */	
	public Object insert(String statementName)
	throws SQLException {

		Object result = this.insert(statementName, null) ;
		
		return result;
	}	
	
	/**
	 * 有参数的插入
	 * @param statementName 查询语句xml引用
	 * @param parameterObject 查询条件参数
	 * @return 
	 * @throws SQLException
	 */
	public Object insert(String statementName, Object parameterObject) throws SQLException {
		
		Object result = new Object() ;
		
		if(parameterObject != null){
			result = this.sqlMapClient.insert(statementName, parameterObject) ;
		}
		else{
			result = this.sqlMapClient.insert(statementName) ;
		}
			
		return result;
	}
	
	/**
	 * 无参数的更新
	 * @param statementName 查询语句xml引用
	 * @return 
	 * @throws SQLException
	 */	
	public Object update(String statementName) throws SQLException {

		Object result = this.update(statementName, null) ;
		return result;
	}	
	
	/**
	 * 有参数的更新,由外部程序控制事务
	 * @param statementName 查询语句xml引用
	 * @param parameterObject 查询条件参数
	 * @return 
	 * @throws SQLException
	 */
	public Object update(String statementName, Object parameterObject) throws SQLException {
		
		Object result = new Object() ;
		
		if(parameterObject != null){
			result = this.sqlMapClient.update(statementName, parameterObject) ;
		}
		else{
			result = this.sqlMapClient.update(statementName) ;
		}
		
		return result;
	}
	
	/**
	 * 无参数的删除
	 * @param statementName 查询语句xml引用
	 * @return 
	 * @throws SQLException
	 */	
	public Object delete(String statementName)
	throws SQLException {

		Object result = this.delete(statementName, null) ;
		return result;
	}	
	
	/**
	 * 有参数的删除,由外部程序控制事务
	 * @param statementName 查询语句xml引用
	 * @param parameterObject 查询条件参数
	 * @return 
	 * @throws SQLException
	 */
	public Object delete(String statementName, Object parameterObject) throws SQLException {
		
		Object result = new Object() ;
		
		if(parameterObject != null){
			result = this.sqlMapClient.delete(statementName, parameterObject) ;
		}
		else{
			result = this.sqlMapClient.delete(statementName) ;
		}
		
		return result;
	}
	
	/**
	 * 批量插入,外部控制数据
	 * @param statementName 查询语句xml引用
	 * @param parameterObject 查询条件参数
	 * @return 
	 * @throws SQLException
	 */
	public Object insertForList(String statementName, List list) throws SQLException {

		this.sqlMapClient.startBatch();
		
		int batch = 0;
		for (Object object : list) {					
			sqlMapClient.insert(statementName, object); 
			batch++;
		    //每1000条批量提交一次。 
		    if(batch==1000){ 
		    	sqlMapClient.executeBatch(); 
		    	sqlMapClient.startBatch();
			    batch = 0; 
		    } 
		}
		
		this.sqlMapClient.executeBatch();
			
		return null;
	}
	
	/**
	 * 批量更新,外部控制数据
	 * @param statementName 查询语句xml引用
	 * @param parameterObject 查询条件参数
	 * @param sqlMapClient 数据库客户端
	 * @return 
	 * @throws SQLException
	 */
	public Object updateForList(String statementName, List list) throws SQLException {
		
		this.sqlMapClient.startBatch();
		
		int batch = 0;
		for (Object object : list) {					
			sqlMapClient.update(statementName, object); 
			batch++;
		    //每1000条批量提交一次。 
		    if(batch==1000){ 
		    	sqlMapClient.executeBatch(); 
		    	sqlMapClient.startBatch();
			    batch = 0; 
		    } 
		}
		
		this.sqlMapClient.executeBatch();
		
		return null;
	}
	
	/**
	 * 批量删除,外部控制数据
	 * @param statementName 查询语句xml引用
	 * @param parameterObject 查询条件参数
	 * @param sqlMapClient 数据库客户端
	 * @return 
	 * @throws SQLException
	 */
	public Object deleteForList(String statementName, List list) throws SQLException {
		
		this.sqlMapClient.startBatch();
		
		int batch = 0;
		for (Object object : list) {					
			sqlMapClient.delete(statementName, object); 
			batch++;
		    //每1000条批量提交一次。 
		    if(batch==1000){ 
		    	sqlMapClient.executeBatch(); 
		    	sqlMapClient.startBatch();
			    batch = 0; 
		    } 
		}
		
		this.sqlMapClient.executeBatch();	
		
		return null ;
	}

	public SqlExecutor getSqlExecutor() {
		return sqlExecutor;
	}

	public void setSqlExecutor(SqlExecutor sqlExecutor) {
		this.sqlExecutor = sqlExecutor;
	}
	
	/**
	 * start current transation
	 * 
	 * @throws SQLException
	 */
	public void startTransaction() throws SQLException {

		sqlMapClient.startTransaction();
	}

	/**
	 * commit current transation
	 * 
	 * @throws SQLException
	 */
	public void commitTransation() throws SQLException {

		sqlMapClient.commitTransaction();
	}

	/**
	 * end current transation
	 * 
	 * @throws SQLException
	 */
	public void endTransation() throws SQLException {

		sqlMapClient.endTransaction();
	}
	
	public Connection getCurrentConnection() throws SQLException{
		return sqlMapClient.getCurrentConnection();
	}
	
	public Connection getConnection(){
		try {
			return this.getSqlMapClientTemplate().getDataSource().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	

	
}
