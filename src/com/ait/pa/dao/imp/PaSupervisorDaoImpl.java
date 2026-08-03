package com.ait.pa.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;
import com.ait.pa.dao.PaSupervisorDao;
import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PaSupervisorDaoImpl.java
 * @Description:
 * @Create date: 2012-1-14 下午01:47:40
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Repository
public class PaSupervisorDaoImpl extends SqlMapClientSupport implements PaSupervisorDao {
	
	/**
	 * 取得考勤员信息(get PaSupervisor)
	 * @param Object
	 * @return Object
	 */
	@SuppressWarnings("unchecked")
	public Object getPaSupervisor(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap() ;

		List returnList = this.getPaSupervisorList(obj) ;
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		
		return returnObj ;
	}
	
	/**
	 * 取得所有考勤员列表(get PaSupervisor List)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getPaSupervisorList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getPaSupervisorList(obj, -1, -1) ; 
		
		return returnList ;
	}
	
	/**
	 * 取得所有考勤员列表(get PaSupervisor List)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getPaSupervisorList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.supervisor.getPaSupervisorList", obj, currentPage, pageSize) ;
			}
			else{
				returnList = this.queryForList("pa.supervisor.getPaSupervisorList", obj) ;
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}

	/**
	 * 查看考勤员数量(get PaSupervisor count)
	 * @param Object
	 * @return int
	 * @throws
	 */
	@Override
	public int getPaSupervisorCnt(Object object) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.supervisor.getPaSupervisorCnt", object)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	/**
	 * 插入考勤员信息
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addPaSupervisorInfo(Object obj) {
		int result = 1;
		List postNos0001=new ArrayList();
		try {
			this.startTransaction() ;
			this.insert("pa.supervisor.addPaSupervisorInfo", obj) ;
			Map paramMap = (Map)obj ;
			List deptTree = (List)paramMap.get("deptTree") ; 
			
			this.insertForList("pa.supervisor.addPaSupervisorDeptInfo", deptTree) ;
//			String []  postNos = (String [])paramMap.get("postNos");
//			int leg =postNos.length;
//			if(paramMap.get("postNos")!=null&&leg>0){
//				for (int i = 0; i < postNos.length; i++) {
//					String postNos01 = postNos[i];
//					paramMap.put("postNos01", postNos01);
//					postNos0001 = this.queryForList("ar.cycle.getEmpTypeCode01", obj);
//					for (Iterator iterator = postNos0001.iterator(); iterator.hasNext();) {
//						Object object = iterator.next();
//						String emptype = object.toString().substring(15,21);
//						Map tempP=new LinkedHashMap();
//						tempP.put("emptypecode", emptype);
//						tempP.put("PERSON_ID", paramMap.get("PERSON_ID"));
//						tempP.put("CREATED_BY", paramMap.get("CREATED_BY"));
//						this.insert("pa.supervisor.addPaSupervisorEmpTypeCodeInfo",tempP);
//					}
//				}
//			}
			
			this.commitTransation() ;
		} catch (SQLException e) {			
			e.printStackTrace();
			result = 0;
		}
		finally {
			try {
				this.endTransation();
			} catch (Exception e) {
				e.printStackTrace();
				result = 0;
			}
		 }
		return result;
	}
	
	/**
	 * 更新考勤员信息(update PaSupervisor Info)
	 * @param Object
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	public int updatePaSupervisorInfo(Object obj) {
		int result = 1;
		List postNos0001 = new ArrayList();
		try {
			this.startTransaction() ;
			this.insert("pa.supervisor.updatePaSupervisorInfo", obj);
			this.delete("pa.supervisor.deletePaSupervisorDeptInfo", obj);
			Map paramMap = (Map)obj ;
			List deptTree = (List)paramMap.get("deptTree") ; 
				 
			this.insertForList("pa.supervisor.addPaSupervisorDeptInfo", deptTree) ;
			
			this.delete("pa.supervisor.deletePaSupervisorEmpTypeCodeInfo", obj);
//			String []  postNos = (String [])paramMap.get("postNos");
//			int leg =postNos.length;
//			if(paramMap.get("postNos")!=null&&leg>0){
//				for (int i = 0; i < postNos.length; i++) {
//					String postNos01 = postNos[i];
//					paramMap.put("postNos01", postNos01);
//					postNos0001 = this.queryForList("ar.cycle.getEmpTypeCode01", obj);
//					for (Iterator iterator = postNos0001.iterator(); iterator.hasNext();) {
//						Object object = iterator.next();
//						String emptype = object.toString().substring(15,21);
//						Map tempP=new LinkedHashMap();
//						tempP.put("emptypecode", emptype);
//						tempP.put("PERSON_ID", paramMap.get("PERSON_ID"));
//						tempP.put("CREATED_BY", paramMap.get("CREATED_BY"));
//						this.insert("pa.supervisor.addPaSupervisorEmpTypeCodeInfo",tempP);
//					}
//				}
//			}
			this.commitTransation() ;
		} catch (SQLException e) {			
			e.printStackTrace();
			result = 0;
		}
		finally {
			try {
				this.endTransation();
			} catch (Exception e) {
				e.printStackTrace();
				result = 0;
			}
		 }
		return result;
	}
	
	/**
	 * 删除考勤员信息
	 * @param Object
	 * @return
	 */
	public int deletePaSupervisorInfo(Object obj) {
		int result = 1;
		try {
			this.startTransaction() ;
			
			this.delete("pa.supervisor.deletePaSupervisorDeptInfo", obj);
			
			this.delete("pa.supervisor.deletePaSupervisorInfo", obj) ;
			
			this.commitTransation() ;
		} catch (SQLException e) {
			e.printStackTrace();
			result = 0;
		}
		finally {
			try {
				this.endTransation() ;
			} catch (Exception e) {
				e.printStackTrace();
				result = 0;
			}
		 }
		
		return result;
	}

	/**
	 * 取得考勤员的部门信息(get PaSupervisorDept List)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaSupervisorDeptList(Object object) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("pa.supervisor.getPaSupervisorDeptList", object) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}

	/**
	 * 插入考勤员的部门信息
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int addPaSupervisorDeptInfo(List list) {
		
		try {
			this.startTransaction() ;
			
			this.delete("pa.supervisor.deletePaSupervisorDeptInfo", list.get(0)) ;
			
			this.insertForList("pa.supervisor.addPaSupervisorDeptInfo", list) ;
			
			this.commitTransation() ;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				this.endTransation() ;
			} catch (Exception e) {
				e.printStackTrace();
			}
		 }
		return 0;
	}

	/**
	 * 取子部门信息(get AttendanceDept List)
	 * @param paramMap
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getAttendanceDeptList(LinkedHashMap paramMap) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("pa.supervisor.getAttendanceDeptList", paramMap) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}

	/**
	 * 取最高部门(get MaxDept List)
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getMaxDeptList(LinkedHashMap paramMap) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("pa.supervisor.getPaSupervisorMaxDeptList", paramMap) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}

	/**
	 * 获得人员列表(get PersonList View)
	 * @param Map
	 * @param int
	 * @param int
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPersonListView(Map paramMap, int currentPage, int pageSize) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("pa.supervisor.getPersonListView", paramMap, currentPage, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return returnList ;
	}

	/**
	 * 获得人员列表(get PersonList Cnt)
	 * @param Map
	 * @return int
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getPersonListCnt(Map paramMap) {
		// TODO Auto-generated method stub
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.supervisor.getPersonListCnt", paramMap)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
			return returnInt;
		}
		
		return returnInt ;
	}
	
	/**
	 * 获得人员列表(get EmpCalendar List)
	 * @param Map
	 * @param int
	 * @param int
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getEmpCalendarList(Map paramMap, int currentPage, int pageSize) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("pa.supervisor.getEmpCalendarList", paramMap, currentPage, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return returnList ;
	}

	/**
	 * 获得人员列表(get PersonList Cnt)
	 * @param Map
	 * @return int
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getEmpCalendarCnt(Map paramMap) {
		// TODO Auto-generated method stub
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.supervisor.getEmpCalendarCnt", paramMap)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
			return returnInt;
		}
		
		return returnInt ;
	}
	
	/**
	 * 部门树(get Dept Tree)
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getDeptTree(Map paramMap) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("pa.supervisor.getDeptTree", paramMap);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 部门树(get Dept Tree)
	 * @param request
	 * @return List
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List getDeptTreeResumeNo(Map paramMap) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("pa.supervisor.getDeptTreeResumeNo", paramMap);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
}
