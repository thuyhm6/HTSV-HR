package com.ait.hrm.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.hrm.dao.InformationRetrievalDao;
import com.ait.sys.dao.SyLanguageDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class InformationRetrievalDaoImpl extends SqlMapClientSupport implements
		InformationRetrievalDao {
   //  hrm.informationRetrieval
	@Autowired
	private SyLanguageDao syLanguageDao;
	
	@SuppressWarnings("unchecked")
	public List getEmpRetrieveShowList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getEmpRetrieveShowList(obj, -1, -1) ; 
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getCustomerTableList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			 
				returnList = this.queryForList("hrm.informationRetrieval.getCustomerTableList", obj);
			}
		 catch (SQLException e) {			
			e.printStackTrace();
		}
	    return returnList ;
	}
	
	
	@SuppressWarnings("unchecked")
	public List getCodeParamList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			 
				returnList = this.queryForList("hrm.informationRetrieval.getCodeParamList", obj);
			}
		 catch (SQLException e) {			
			e.printStackTrace();
		}
	    return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getPostGradeForCheckBoxList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			 
				returnList = this.queryForList("hrm.informationRetrieval.getPostGradeForCheckBoxList", obj);
			}
		 catch (SQLException e) {			
			e.printStackTrace();
		}
	    return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getPostForCheckBoxList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			 
				returnList = this.queryForList("hrm.informationRetrieval.getPostForCheckBoxList", obj);
			}
		 catch (SQLException e) {			
			e.printStackTrace();
		}
	    return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getDutyForCheckBoxList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			 
				returnList = this.queryForList("hrm.informationRetrieval.getDutyForCheckBoxList", obj);
			}
		 catch (SQLException e) {			
			e.printStackTrace();
		}
	    return returnList ;
	}
	@SuppressWarnings("unchecked")
	public List getPositionForCheckBoxList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			 
				returnList = this.queryForList("hrm.informationRetrieval.getPositionForCheckBoxList", obj);
			}
		 catch (SQLException e) {			
			e.printStackTrace();
		}
	    return returnList ;
	}
	
	
	@SuppressWarnings("unchecked")
	public LinkedHashMap getCustomerTableListByNO(Object obj) {
		LinkedHashMap map=new LinkedHashMap();
		try {
			 
			map =(LinkedHashMap) this.queryForObject("hrm.informationRetrieval.getCustomerTableListByNO", obj);
			}
		 catch (SQLException e) {			
			e.printStackTrace();
		}
	    return map ;
	}
	
	
	@SuppressWarnings("unchecked")
	public List getEmpRetrieveShowList(Object object, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
			try {
				if(currentPage > -1 && pageSize > -1){
					returnList = this.queryForList("hrm.informationRetrieval.getEmpRetrieveShowList", object, currentPage, pageSize);
				}
				else{
					returnList = this.queryForList("hrm.informationRetrieval.getEmpRetrieveShowList", object);
				}
			} catch (SQLException e) {			
				e.printStackTrace();
			}
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	public int getEmpRetrieveShowCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("hrm.informationRetrieval.getEmpRetrieveShowCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public  List getInfoFieldByTableNameList(Object obj) {
		List returnList = new ArrayList() ;
			try {
				 
					returnList = this.queryForList("hrm.informationRetrieval.getInfoFieldByTableNameList",obj );
				}
				 
			  catch (SQLException e) {			
				e.printStackTrace();
			}
		return returnList ;
	}
	
	
	 
	@SuppressWarnings("unchecked")
	@Override
	public void saveEmpRetrieveInfo(HttpServletRequest request,
			 Map paramMap) throws Exception {
		try {
			  LinkedHashMap pMap=new LinkedHashMap();
			  pMap = (LinkedHashMap)this.syLanguageDao.saveSyGlobalName(paramMap);
	          paramMap.put("RETRIEVE_TABLE_NO", pMap.get("NO"));
			this.insert("hrm.informationRetrieval.saveEmpRetrieveInfo", paramMap);
		}
		 
		  catch (SQLException e) {			
			e.printStackTrace();
		}
		 
	}
	
	
	@SuppressWarnings("unchecked")
	public void deleteCustTable(HttpServletRequest request,
			 Map paramMap) throws Exception {
		try {
			     LinkedHashMap map=new LinkedHashMap();
			   /////////查询 langge no
			    map=(LinkedHashMap)this.queryForObject("hrm.informationRetrieval.getCustTableSyGlobalNoById",paramMap);
			    this.syLanguageDao.deleteSyGlobalName(map);
	            this.delete("hrm.informationRetrieval.deleteCustTable",paramMap);
		}
		 
		  catch (SQLException e) {			
			e.printStackTrace();
		}
		 
	}

	
	@SuppressWarnings("unchecked")
	public List getEmpIdRetrieveList(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("hrm.informationRetrieval.getEmpIdRetrieveList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("hrm.informationRetrieval.getEmpIdRetrieveList", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}

	
	@SuppressWarnings("unchecked")
	public List getEmpIdRetrieveList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("hrm.informationRetrieval.getEmpIdRetrieveList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}

	
	@Override
	public int getEmpIdListCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("hrm.empinfo.getEmpIdListCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	public List viewStructureDept(Object obj) {
		List returnList = new ArrayList() ;
		try {
			 
				returnList = this.queryForList("hrm.informationRetrieval.viewStructureDept", obj);
			}
		 catch (SQLException e) {			
			e.printStackTrace();
		}
	    return returnList ;
	}
	
	
}
