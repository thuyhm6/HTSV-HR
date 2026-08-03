package com.ait.disc.dao.impl;

import java.awt.Dimension;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.disc.dao.RetrieveSqlMasterDao;
import com.ait.web.util.SqlMapClientSupport;
import com.ibatis.sqlmap.engine.impl.SqlMapClientImpl;



@Repository
public class RetrieveSqlMasterListDaoImpl extends SqlMapClientSupport implements RetrieveSqlMasterDao {

	/* 
	* Title: getRetrivevSqlMasterList
	* Description:查询自动下载excel全部列表
	* @author 孙鹏  
	* @date 2014年10月22日 上午9:20:34  
	* @param obj
	* @return
	* @throws Exception 
	* @see com.ait.disc.dao.RetrieveSqlMasterDao#getRetrivevSqlMasterList(java.lang.Object) 
	*/
	
	@Override
	public List getRetrieveSqlMasterList(Object obj) throws SQLException {
		List returnList = new ArrayList();
		returnList = this.getRetrieveSqlMasterList(obj, -1, -1);
		return returnList;
	}
	
	
	@Override
	public List getRetrieveSqlMasterList(Object obj,int pageNum, int numPerPage) {
		List returnList = new ArrayList() ;
		try {
			if(pageNum > -1 && numPerPage > -1){
				returnList = this.queryForList("disc.autoexcel.RetrieveSqlMasterList", obj, pageNum, numPerPage);
			}
			else{
				returnList = this.queryForList("disc.autoexcel.RetrieveSqlMasterList", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}

	/* 
	* Title: getRetrieveSqlMasterListCnt
	* Description:查询自动下载参数的数量
	* @author 孙鹏  
	* @date 2014年10月22日 上午11:14:58  
	* @param obj
	* @return 
	* @see com.ait.disc.dao.RetrieveSqlMasterDao#getRetrieveSqlMasterListCnt(java.lang.Object) 
	*/
	@Override
	public int getRetrieveSqlMasterListCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(
						ObjectUtils.toString(this.queryForObject("disc.autoexcel.RetrieveSqlMasterCnt", obj)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}


	/* 
	* Title: insertSqlMaster
	* Description:sqlmaster，报表的插入
	* @author 孙鹏  
	* @date 2014年10月22日 下午4:45:35  
	* @param request
	* @return
	* @throws Exception 
	* @see com.ait.disc.dao.RetrieveSqlMasterDao#insertSqlMaster(javax.servlet.http.HttpServletRequest) 
	*/
	@Override
	public int insertSqlMaster(Object object) throws Exception {
		int returnint=0;
		try {
			returnint=(Integer) this.insert("disc.autoexcel.CreateSqlMasterDetail", object);
		} catch (SQLException e) {
			e.printStackTrace();
			return returnint;
		}catch (Exception e) {
			logger.error(e.toString());
			return returnint;
		}
		return returnint;
	}


	/* 
	* Title: getSqlMaster
	* Description:获取sql报表一行数据
	* @author 孙鹏  
	* @date 2014年10月23日 上午10:08:20  
	* @param object
	* @return
	* @throws Exception 
	* @see com.ait.disc.dao.RetrieveSqlMasterDao#getSqlMaster(java.lang.Object) 
	*/
	@Override
	public List getSqlMaster(Object object) throws Exception {
		return this.queryForList("disc.autoexcel.RetrieveSqlDetailList", object);
//		List returnList = this.getRetrieveSqlMasterList(object) ;
//		if(returnList.size() > 0){
//			returnObj = (LinkedHashMap)returnList.get(0) ;
//		}
	}


	/* 
	* Title: updateSqlMaster
	* Description:更新一个sql报表数据
	* @author 孙鹏  
	* @date 2014年10月23日 上午11:14:53  
	* @param object
	* @return
	* @throws Exception 
	* @see com.ait.disc.dao.RetrieveSqlMasterDao#updateSqlMaster(java.lang.Object) 
	*/
	@Override
	public int updateSqlMaster(Object object) throws Exception {
		try {
			this.insert("disc.autoexcel.UpdateSqlMasterDetail",object);
			
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
			return 0;
		}
		return 1;
	}


	/* 
	* Title: updateSqlParam
	* Description:更新一行sql报表的参数
	* @author 孙鹏  
	* @date 2014年10月23日 上午11:11:53  
	* @param object
	* @return
	* @throws Exception 
	* @see com.ait.disc.dao.RetrieveSqlMasterDao#updateSqlParam(java.lang.Object) 
	*/
	@Override
	public int updateSqlParam(Object object) throws Exception {
		try {
			this.update("disc.autoexcel.UpdateSqlParam",object);
			
		} catch (Exception e) {
			logger.error(e.toString());
			return 0;
		}
		return 1;
	}


	/* 
	* Title: getSqlParamList
	* Description:获取某个报表的所有参数列表
	* @author 孙鹏  
	* @date 2014年10月23日 下午2:13:11  
	* @param object
	* @return
	* @throws Exception 
	* @see com.ait.disc.dao.RetrieveSqlMasterDao#getSqlParamList(java.lang.Object) 
	*/
	@Override
	public List getSqlParamList(Object object) throws Exception {
		List returnList = new ArrayList() ;
		returnList=  this.queryForList("disc.autoexcel.RetrieveSqlParamList", object);
		return returnList ;
	}


	/* 
	* Title: insertSqlParam
	* Description:插入一行参数
	* @author 孙鹏  
	* @date 2014年10月23日 上午11:11:22  
	* @param object
	* @return
	* @throws Exception 
	* @see com.ait.disc.dao.RetrieveSqlMasterDao#insertSqlParam(java.lang.Object) 
	*/
	@Override
	public int insertSqlParam(Object object) throws Exception {
		try {
			//is.insuranceSystemCalculate.getPaBenStandardSerious
			this.insert("disc.autoexcel.CreateSqlParam",object);
			
		} catch (Exception e) {
			logger.error(e.toString());
			return 0;
		}
		return 1;
	}


	/* 
	* Title: updateSqlParamUseN
	* Description:将参数表此sql下的参数行临时更新为不可用
	* @author 孙鹏  
	* @date 2014年10月23日 上午11:02:55  
	* @param object
	* @return
	* @throws Exception 
	* @see com.ait.disc.dao.RetrieveSqlMasterDao#updateSqlParamUseN(java.lang.Object) 
	*/
	@Override
	public int updateSqlParamUseN(Object object) throws Exception {
		try {
			//is.insuranceSystemCalculate.getPaBenStandardSerious
			this.update("disc.autoexcel.UpdateSqlParamUseN",object);
			
		} catch (Exception e) {
			logger.error(e.toString());
			return 0;
		}
		return 1;
	}


	/* 
	* Title: getSqlParam
	* Description:查询sql报表的一个参数
	* @author 孙鹏  
	* @date 2014年10月23日 上午11:08:07  
	* @param object
	* @return
	* @throws Exception 
	* @see com.ait.disc.dao.RetrieveSqlMasterDao#getSqlParam(java.lang.Object) 
	*/
	@Override
	public Map getSqlParam(Object object) throws Exception {
		LinkedHashMap returnObj = new LinkedHashMap() ;
		returnObj=  (LinkedHashMap) this.queryForObject("disc.autoexcel.RetrieveSqlParamDetail", object);
		return returnObj ;
	}


	@Override
	public void deleteSqlMaster(Object object) throws Exception {
		this.delete("disc.autoexcel.deleteSqlMasterSql", object);
		
	}


	@Override
	public void deleteSqlParam(Object object) throws Exception {
		this.delete("disc.autoexcel.deleteSqlParamSql", object);
		
	}


	/* 
	* Title: querySql
	* Description:执行手写的sql语句
	* @author 孙鹏  
	* @date 2014年10月28日 上午9:51:48  
	* @param object
	* @return
	* @throws Exception 
	* @see com.ait.disc.dao.RetrieveSqlMasterDao#querySql(java.lang.Object) 
	*/
	@Override
	public List querySql(Object object) throws Exception {
		List<LinkedHashMap> returnObj = new ArrayList() ;
		returnObj=  this.queryForList("disc.autoexcel.queryForSql", object);
		return returnObj ;
	}

}
