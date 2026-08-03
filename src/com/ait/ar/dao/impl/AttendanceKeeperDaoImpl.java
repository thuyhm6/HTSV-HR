package com.ait.ar.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.ar.dao.AttendanceKeeperDao;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: AttendanceKeeperDaoImpl.java
 * @Description:
 * @Create date: 2012-1-14 下午01:47:40
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Repository
public class AttendanceKeeperDaoImpl extends SqlMapClientSupport implements AttendanceKeeperDao {
	
	/**
	 * 取得考勤员信息(get AttendanceKeeper)
	 * @param Object
	 * @return Object
	 */
	@SuppressWarnings("unchecked")
	public Object getAttendanceKeeper(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap() ;

		List returnList = this.getAttendanceKeeperList(obj) ;
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		
		return returnObj ;
	}
	
	/**
	 * 取得所有考勤员列表(get AttendanceKeeper List)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getAttendanceKeeperList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getAttendanceKeeperList(obj, -1, -1) ; 
		
		return returnList ;
	}
	
	/**
	 * 取得所有考勤员列表(get AttendanceKeeper List)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getAttendanceKeeperList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ar.attendanceKeeper.getAttendanceKeeperList", obj, currentPage, pageSize) ;
			}
			else{
				returnList = this.queryForList("ar.attendanceKeeper.getAttendanceKeeperList", obj) ;
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}

	/**
	 * 查看考勤员数量(get AttendanceKeeper count)
	 * @param Object
	 * @return int
	 * @throws
	 */
	@Override
	public int getAttendanceKeeperCnt(Object object) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.attendanceKeeper.getAttendanceKeeperCnt", object)), Integer.class) ;
			
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
	public int addAttendanceKeeperInfo(Object obj) {
		int result = 1;
		List postNos0001 = new ArrayList() ;
		try {
			this.startTransaction() ;
			//先删除原有数据
			this.delete("ar.attendanceKeeper.deleteAttendanceKeeperInfo", obj) ;
			this.insert("ar.attendanceKeeper.addAttendanceKeeperInfo", obj) ;
			Map paramMap = (Map)obj ;
			List deptTree = (List)paramMap.get("deptTree") ; 
			
			this.insertForList("ar.attendanceKeeper.addAttendanceKeeperDeptInfo", deptTree) ;
			/*String [] postNos = (String [])paramMap.get("postNos");//人员类型数组
			int leg = postNos.length;
			if(paramMap.get("postNos")!=null&&leg>0){
			  for(int i = 0; i < leg;i++){
				 //每个人员类型组对象
				String postNos01=postNos[i];
				paramMap.put("postNos01",postNos01);
				postNos0001 = this.queryForList("ar.cycle.getEmpTypeCode01", obj) ;
				//获取人员类型组下的人员类型
				for (Iterator iterator = postNos0001.iterator(); iterator.hasNext();) {
					Object object  =  iterator.next();
					//获取人员类型的code值
					String emptype=object.toString().substring(15, 21);
					Map tempP=new LinkedHashMap();
					tempP.put("emptypecode", emptype);
					tempP.put("PERSON_ID", paramMap.get("PERSON_ID"));
					tempP.put("CREATED_BY", paramMap.get("CREATED_BY"));
					this.insert("ar.attendanceKeeper.addAttendanceKeeperEmpTypeInfo",tempP);
				}
			  }
			}*/
			  
			//this.insert("ar.attendanceKeeper.addAttendanceKeeperEmpTypeInfo", obj);
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
	 * 更新考勤员信息(update AttendanceKeeper Info)
	 * @param Object
	 * @return int
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int updateAttendanceKeeperInfo(Object obj) {
		int result = 1;
		List postNos0001 = new ArrayList();
		try {
			this.startTransaction() ;
			this.insert("ar.attendanceKeeper.updateAttendanceKeeperInfo", obj);
			this.delete("ar.attendanceKeeper.deleteAttendanceKeeperDeptInfo", obj);
			Map paramMap = (Map)obj ;
			List deptTree = (List)paramMap.get("deptTree") ; 
				 
			this.insertForList("ar.attendanceKeeper.addAttendanceKeeperDeptInfo", deptTree) ;
			
			//this.delete("ar.attendanceKeeper.deleteAttendanceKeeperEmpTypeInfo", obj);
			/*String [] postNos = (String [])paramMap.get("postNos");//接收前台的人员类型组的数组，存放所有选中的人员类型组
			int leg = postNos.length;
			//遍历取出每一个人员类型组
            if(paramMap.get("postNos")!=null&&leg>0){
            	for (int i = 0; i < postNos.length; i++) {
					String postNos01 = postNos[i];//具体的人员类型组
					paramMap.put("postNos01",postNos01);
					//根据人员类型、CPNY_ID获取当前人员类型组下的人员类型
					postNos0001 = this.queryForList("ar.cycle.getEmpTypeCode01", obj) ;//是一个list
					//遍历取出每个人员类型
					for (Iterator iterator = postNos0001.iterator(); iterator.hasNext();) {
						Object object = iterator.next();
						//此处可设断点查看{EMP_TYPE_CODE="123455}形式存在，我们需要的时数字故截取
						String emptype = object.toString().substring(15,21);
						Map tempP=new LinkedHashMap();
						tempP.put("emptypecode", emptype);
						tempP.put("PERSON_ID", paramMap.get("PERSON_ID"));
						tempP.put("UPDATE_BY", paramMap.get("UPDATE_BY"));
						this.insert("ar.attendanceKeeper.addAttendanceKeeperEmpTypeInfo",tempP);
					}
				}
				
			}*/
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
	@SuppressWarnings("unchecked")
	public int deleteAttendanceKeeperInfo(Object obj) {
		int result = 1;
		try {
			this.startTransaction() ;
			
			this.delete("ar.attendanceKeeper.deleteAttendanceKeeperDeptInfo", obj);//删除考勤员
			
			this.delete("ar.attendanceKeeper.deleteAttendanceKeeperInfo", obj) ;//删除考勤员能看到的部门
			
			//this.delete("ar.attendanceKeeper.deleteAttendanceKeeperEmpTypeInfo", obj);//删除考勤员对应的人员类型
			
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
	 * 取得考勤员的部门信息(get AttendanceKeeperDept List)
	 * @param Object
	 * @return List
	 */
	@Override
	public List getAttendanceKeeperDeptList(Object object) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.attendanceKeeper.getAttendanceKeeperDeptList", object) ;
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
	@Override
	public int addAttendanceKeeperDeptInfo(List list) {
		
		try {
			this.startTransaction() ;
			
			this.delete("ar.attendanceKeeper.deleteAttendanceKeeperDeptInfo", list.get(0)) ;
			
			this.insertForList("ar.attendanceKeeper.addAttendanceKeeperDeptInfo", list) ;
			
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
	@SuppressWarnings("rawtypes")
	@Override
	public List getAttendanceDeptList(LinkedHashMap paramMap) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.attendanceKeeper.getAttendanceDeptList", paramMap) ;
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
	@Override
	public List getMaxDeptList(LinkedHashMap paramMap) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.attendanceKeeper.getAttendanceKeeperMaxDeptList", paramMap) ;
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
	@Override
	public List getPersonListView(Map paramMap, int currentPage, int pageSize) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.attendanceKeeper.getPersonListView", paramMap, currentPage, pageSize);
		} catch (Exception e) {
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
	@Override
	public List viewKeeperListOtApplyCheck(Map paramMap, int currentPage, int pageSize) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.attendanceKeeper.viewKeeperListOtApplyCheck", paramMap, currentPage, pageSize);
		} catch (Exception e) {
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
	@Override
	public List getPersonListView2(Map paramMap, int currentPage, int pageSize) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.attendanceKeeper.getPersonListView2", paramMap, currentPage, pageSize);
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
	@Override
	public int getPersonListCnt(Map paramMap) {
		// TODO Auto-generated method stub
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.attendanceKeeper.getPersonListCnt", paramMap)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
			return returnInt;
		}
		
		return returnInt ;
	}

	/**
	 * 获得人员列表(get PersonList Cnt)
	 * @param Map
	 * @return int
	 * @throws 
	 */
	@Override
	public int getPersonListOtApplyCheckCnt(Map paramMap) {
		// TODO Auto-generated method stub
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.attendanceKeeper.getPersonListOtApplyCheckCnt", paramMap)), Integer.class) ;
			
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
	@Override
	public List getEmpCalendarList(Map paramMap, int currentPage, int pageSize) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.attendanceKeeper.getEmpCalendarList", paramMap, currentPage, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return returnList ;
	}
	/**
	 * 部门考勤获得人员列表(get EmpCalendar List)
	 * @param Map
	 * @param int
	 * @param int
	 * @return List
	 * @throws 
	 */
	@Override
	public List getEmpCalendar2List(Map paramMap, int currentPage, int pageSize) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.attendanceKeeper.getEmpCalendar2List", paramMap, currentPage, pageSize);
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
	@Override
	public int getEmpCalendarCnt(Map paramMap) {
		// TODO Auto-generated method stub
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.attendanceKeeper.getEmpCalendarCnt", paramMap)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
			return returnInt;
		}
		
		return returnInt ;
	}
	/**
	 * 内务考勤申请获得人员列表(get PersonList Cnt)
	 * @param Map
	 * @return int
	 * @throws 
	 */
	@Override
	public int getEmpCalendar2Cnt(Map paramMap) {
		// TODO Auto-generated method stub
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.attendanceKeeper.getEmpCalendar2Cnt", paramMap)), Integer.class) ;
			
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
			returnList = this.queryForList("ar.attendanceKeeper.getDeptTree", paramMap);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 职位列表（get Position List）
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPositionList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			
				returnList = this.queryForList("ar.attendanceKeeper.getPositionList", obj);
						
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	/**
	 * 取得所有考勤员列表(get AttendanceKeeper List)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getDepartmentManageList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("ar.attendanceKeeper.getDepartmentManageList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	/**
	 * 修改部门最终管理信息
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addDepartManagerInfo(Object obj) {
		int result = 1;
		try {
			this.startTransaction() ;
			this.updateForList("ar.attendanceKeeper.addDepartManagerInfo", (List)obj) ;
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
	 * 修改部门最终管理期间统一信息
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addDepartManagerUnifyInfo(Object obj) {
		int result = 1;
		try {
			this.startTransaction() ;
			this.update("ar.attendanceKeeper.addDepartManagerUnifyInfo", obj) ;
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
}
