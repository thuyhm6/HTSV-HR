package com.ait.sys.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.sys.dao.SyPageStructureDao;
import com.ait.web.util.SqlMapClientSupport;
@Repository
public class SyPageStructureDaoImpl  extends SqlMapClientSupport implements SyPageStructureDao {
   
	
	@SuppressWarnings("unchecked")
	public List getIsCanBeBuildPage(Object obj) {
		List returnList = new ArrayList() ;
		 try{
		 returnList = this.queryForList("sys.syPageStructure.getIsCanBeBuildPage");
	 
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getPageStructureInfoList(Object object) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("sys.syPageStructure.getPageStructureInfoList", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
		 
	}

	public int addPageStructure(Object object) {
		// TODO Auto-generated method stub
		int result=0;
		try {
			 this.insert("sys.syPageStructure.addPageStructure", object);
			 result=1;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public int deletePageStructure(List list) {
		// TODO Auto-generated method stub
		int result=0;
		
		try {
		   	this.startTransaction();
			 this.deleteForList("sys.syPageStructure.deletePageStructure", list);
			 this.deleteForList("sys.syPageStructure.deletePageStructureInfo", list);
			 result=1;
			 this.commitTransation();
		} catch (SQLException e) {			
			e.printStackTrace();
			 
		}finally {
			try {
				this.endTransation() ;
			} catch (Exception e) {
				e.printStackTrace();
			}
		 }
		return result;
	}

	@SuppressWarnings("unchecked")
	public List getAddPageStructureDetail(Object object) {
		// TODO Auto-generated method stub
		 List returnList = new ArrayList() ;
			
			returnList = this.getAddPageStructureDetail(object, -1, -1) ;
			
			return returnList ;
	}
	@SuppressWarnings("unchecked")
	public List getAddPageStructureDetail(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("sys.syPageStructure.getAddPageStructureDetail", obj, currentPage, pageSize);
			}
			else{
				  returnList = this.queryForList("sys.syPageStructure.getAddPageStructureDetail", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	public int getAddPageStructureDetailCnt(Object object) {
		// TODO Auto-generated method stub
		int i=0;
		try {
		 
			i = NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.syPageStructure.getAddPageStructureDetailCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return i ;
	}

	public int AddPageStructureDetailInfo(Object object) {
		// TODO Auto-generated method stub
		 
		int result=0;
		try {
			 this.insert("sys.syPageStructure.AddPageStructureDetailInfo", object);
			 result=1;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List getUpdatePageStructureDetail(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("sys.syPageStructure.getUpdatePageStructureDetail", obj, currentPage, pageSize);
			}
			else{
				  returnList = this.queryForList("sys.syPageStructure.getUpdatePageStructureDetail", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	public int getUpdatePageStructureDetailCnt(Object object) {
		// TODO Auto-generated method stub
		int i=0;
		try {
		 
			i = NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.syPageStructure.getUpdatePageStructureDetailCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return i ;
	}
	@SuppressWarnings("unchecked")
	public List getUpdatePageStructureDetail(Object object) {
		// TODO Auto-generated method stub
		 List returnList = new ArrayList() ;
			
			returnList = this.getAddPageStructureDetail(object, -1, -1) ;
			
			return returnList ;
	}

	public int deletePageStructureDetail(Object object) {
		// TODO Auto-generated method stub
      int result=0;
		
		try {
		    
			 this.delete("sys.syPageStructure.deletePageStructureDetail", object);
			 result=1;
			 
		} catch (SQLException e) {			
			e.printStackTrace();
	     }
		return result;
	}
	
	public int updatePageStructureDetailInfo(Object object) {
		// TODO Auto-generated method stub
      int result=0;
		
		try {
		    
			 this.update("sys.syPageStructure.updatePageStructureDetailInfo", object);
			 result=1;
			 
		} catch (SQLException e) {			
			e.printStackTrace();
	     }
		return result;
	}
} 
