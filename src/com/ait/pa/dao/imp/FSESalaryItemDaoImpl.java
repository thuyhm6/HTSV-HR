package com.ait.pa.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.pa.dao.FSESalaryItemDao;
import com.ait.sys.dao.SyLanguageDao;
import com.ait.web.util.SqlMapClientSupport;
@Repository
public class FSESalaryItemDaoImpl extends SqlMapClientSupport implements FSESalaryItemDao{
	/**
	 * 分页查看法人、  职种（人员类型组）、和人员类型的对应关系   页面只显示职种和人员类型的对应关系
	 * @param paramMap
	 * @param pageNum
	 * @param numPerPage
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getFSESalaryList(Map paramMap, int pageNum, int numPerPage) {
		List returnList = new ArrayList() ;
		try {
			if(pageNum > -1 && numPerPage > -1){
				returnList = this.queryForList("pa.fsesalarycode.getFSESalaryList", paramMap, pageNum, numPerPage);
			}
			else{
				returnList = this.queryForList("pa.fsesalarycode.getFSESalaryList", paramMap);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}

	/**
	 * 同上  只是不分页
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getFSESalaryList(Map paramMap) {
        List returnList = new ArrayList() ;
		returnList = this.getFSESalaryList(paramMap, -1, -1) ;
		return returnList ;
	}

	/**
	 * 查找上面信息的总条数
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getFSESalaryCnt(Map paramMap) {
        int returnInt = 0 ;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.fsesalarycode.getFSESalaryCnt", paramMap)), Integer.class) ;	
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}

	/**
	 * 获取人员类型和人员类型组导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getFSESalaryTempList(Object object){
		return this.getFSESalaryTempList(object, 1, 10);
	}
	
	/**
	 * 获取人员类型和人员类型组导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getFSESalaryTempList(Object object, int currentPage, int pageSize){
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.salarycode.getFSESalaryTempList", object, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("pa.salarycode.getFSESalaryTempList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 获取人员类型和人员类型组导入信息数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getFSESalaryTempCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.salarycode.getFSESalaryTempCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 获取出错的人员类型和人员类型组导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getFSESalaryTempErrorCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.salarycode.getFSESalaryTempErrorCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 获取人员类型导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getFSESalaryImportTempList(Object object, int currentPage, int pageSize){
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.fsesalarycode.getFSESalaryImportTempList", object, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("pa.fsesalarycode.getFSESalaryImportTempList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 获取人员类型导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getFSESalaryImportTempList(Object object){
		List returnList = new ArrayList() ;
		try {
		   returnList = this.queryForList("pa.fsesalarycode.getFSESalaryImportTempList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 获取人员类型导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getFSESalaryImportTempCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.fsesalarycode.getFSESalaryImportTempCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 获取出错的人员类型导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getFSESalaryImportTempErrorCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.fsesalarycode.getFSESalaryImportTempErrorCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 数据导入(验证通过后--从临时表导入到正式表)
	 * @param 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String importFSESalaryFromExcel(Object object)  throws Exception{
		String returnString = "" ;		
		try {
			LinkedHashMap paramMap = (LinkedHashMap)object;
			paramMap.put("message", "") ;
			this.insert("pa.fsesalarycode.importFSESalaryFromExcel", paramMap) ;			
			returnString = ObjectUtils.toString(paramMap.get("message")) ;
			System.out.println(returnString);
		} catch (SQLException e) {	
			returnString = e.getMessage() ;			
			throw e;
		}
		return returnString ;
	}
}
