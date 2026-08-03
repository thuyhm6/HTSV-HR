package com.ait.pa.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;
import com.ait.pa.dao.PaCityCoefficientDao;
import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PaCityCoefficientDaoImpl.java
 * @Description:
 * @Create date: 2012-3-16 下午03:34:42
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Repository
public class PaCityCoefficientDaoImpl extends SqlMapClientSupport implements PaCityCoefficientDao {
	/**
	 * 取得所有人员账户信息列表
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaCityCoefficientList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getPaCityCoefficientList(obj, -1, -1) ;
		
		return returnList ;
	}
	
	/**
	 * 取得所有人员账户信息列表
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaCityCoefficientList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.cityCoefficient.getPaCityCoefficientList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("pa.cityCoefficient.getPaCityCoefficientList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 取得所有人员账户信息总数
	 * @param List
	 * @return
	 */
	public int getPaCityCoefficientCnt(Object obj) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.cityCoefficient.paCityCoefficientCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	public int checkAddCityCoefficientInfo(Object obj) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.cityCoefficient.checkAddCityCoefficientInfo", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	public int addCityCoefficientInfo(Object obj) {
		
		try {
			
			this.insert("pa.cityCoefficient.addCityCoefficientInfo", obj);
			
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object paCityCoefficientInfo(Object obj) {
		
		LinkedHashMap returnObj = new LinkedHashMap() ;
		
		List returnList = this.getPaCityCoefficientList(obj) ;
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		
		return returnObj ;
		
	}
	
	public int updateCityCoefficientInfo(Object obj) {
		
		try {
			
			this.insert("pa.cityCoefficient.updateCityCoefficientInfo", obj);
			
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	public int deleteCityCoefficientInfo(Object obj) {
		
		try {
			
			this.insert("pa.cityCoefficient.deleteCityCoefficientInfo", obj);
			
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
}
