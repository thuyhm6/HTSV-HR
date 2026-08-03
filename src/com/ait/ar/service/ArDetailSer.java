package com.ait.ar.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ArDetailSer.java
 * @Description: implement Class ArDetailSerImp.java
 * @Create date: 2012-2-7 下午04:30:27
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface ArDetailSer {
	  @SuppressWarnings("unchecked")
	  public List getArDetailList(HttpServletRequest request,ModelMap modelMap) ;
	  
	  public int getArDetailListCnt(HttpServletRequest request,ModelMap modelMap);
	  
	  @SuppressWarnings("unchecked")
	  public List getAttendanceExceptionList(HttpServletRequest request,ModelMap modelMap) ;
	  	  
	  @SuppressWarnings("unchecked")
	  public List getItemList(HttpServletRequest request) ;
	  
	  public String getStartDateStr();
	  
	  public String getEndDateStr() ;
	  
	  @SuppressWarnings("unchecked")
	  public Map updateArDetailInfo(HttpServletRequest request);
	  
	  @SuppressWarnings("unchecked")
	  public Map deleteArDetailInfo(HttpServletRequest request);
	  
	  @SuppressWarnings("unchecked")
	  public Map addArDetailInfo(HttpServletRequest request);
	  
	  @SuppressWarnings("unchecked")
	  public List SearchArDetailExceptionInfo(HttpServletRequest request);
	  
	  @SuppressWarnings("unchecked")
	  public List SearchArDetailDeptExToMa(String string);
	  
	  @SuppressWarnings("unchecked")
	  public List SearchManagerInfo(HttpServletRequest request);
	  
	  public void SearchArDetailExceptionInfo();
	  
	  @SuppressWarnings("unchecked")
	  public List getArDetailEssList(HttpServletRequest request,ModelMap modelMap) ;
	  
	  public int getArDetailEssListCnt(HttpServletRequest request,ModelMap modelMap);
	  
	  @SuppressWarnings("unchecked")
	  public String getModifyYnBySupervisorId(String string);
	  
	  /**
		 * 取得人员考勤明细列表(get ArDetail List)
		 * 
		 * @param request
		 * @return List
		 * @throws
		 */
		@SuppressWarnings("unchecked")
		public List getArDetailListExcel(HttpServletRequest request, ModelMap modelMap);
			
		/**
		 * 获取明细导入信息
		 * 
		 * @Copyright: AIT (c)
		 * @Company: AIT
		 * @author weizhengchen@ait.net.cn
		 * @date 2014-7-03
		 * @version V1.0
		 */
		public List getArDetailTempList(HttpServletRequest request) ;
		
		/**
		 * 获取明细导入数量
		 * 
		 * @Copyright: AIT (c)
		 * @Company: AIT
		 * @author weizhengchen@ait.net.cn
		 * @date 2014-7-03
		 * @version V1.0
		 */
		public int getArDetailTempCnt(HttpServletRequest request, String errorFlag);
		
		/**
		 * 明细excel信息提交
		 * 
		 * @Copyright: AIT (c)
		 * @Company: AIT
		 * @author weizhengchen@ait.net.cn
		 * @date 2014-7-03
		 * @version V1.0
		 */
		public String submitImportExcelArDetailData(HttpServletRequest request);
		
		 public String countArDetailLengthInfo(HttpServletRequest request) ;

			/**
		 * 加班管理页面(search ot info list)
		 * 
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		public List getOtAffirmInfoListBatch(HttpServletRequest request) throws Exception;
		/**
		 * 倒休管理管理页面(search ot info list)
		 * 
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		public List viewArAdjustHolidayManagent(HttpServletRequest request) throws Exception;
		@SuppressWarnings("unchecked")
		public List viewArAdjustHolidayManagentNull(HttpServletRequest request) throws Exception;
		/**
		 * 加班管理页面SST(search ot info list)
		 * 
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		public List getOtAffirmInfoListBatchSST(HttpServletRequest request) throws Exception;
		/**
		 * 加班管理保存方法
		 * @param request
		 * @return
		 * @throws Exception
		 */

		public int delOvertimeApplyInBatch(HttpServletRequest request) throws Exception;
		/**
		 * 加班管理保存方法 New
		 * @param request
		 * @return
		 * @throws Exception
		 */

		public int saveArOvertimeManagent(HttpServletRequest request) throws Exception;
		/**
		 * 倒休管理保存方法
		 * @param request
		 * @return
		 * @throws Exception
		 */
		
		public int delOtAdjustApplyAffirmForm(HttpServletRequest request) throws Exception;
		/**
		 * SST加班管理保存方法
		 * @param request
		 * @return
		 * @throws Exception
		 */
		
		public int delLOvertimeApplyInBatchSST(HttpServletRequest request) throws Exception;

		/**
		 * 加班信息查询页面(get Leave Affirm Info List)
		 * 
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		public List getSearchApplyOtInfoList(HttpServletRequest request) throws Exception;
		/**
		 * myhome班车页面
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		public List getMyhomeCarInfoList(HttpServletRequest request) throws Exception;
		/**
		 * 倒休搜索(get Leave Affirm Info List)
		 * 
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		public List getSearchApplyAdjustInfoList(HttpServletRequest request) throws Exception;

		public List getApplyAttenanceManagentInfoList(HttpServletRequest request) throws Exception;
		public List getBatchLeaveAffirmMoreDayInfoList(HttpServletRequest request) throws Exception;

		public int delLeaveApplyInBatchForBatch(HttpServletRequest request) throws Exception;

		public Object getLeaveManagentForSearchInfoList(HttpServletRequest request) throws Exception;
		
		/**
		 * 加班信息查询(search ot info list)
		 * 
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		public List viewSearchOtInfo(HttpServletRequest request) throws Exception ;

		public void deleteOtAdjustForOnlyOne(LinkedHashMap deleteMap);

		public List viewAdjustRecords(HttpServletRequest request) throws Exception;

		@SuppressWarnings("unchecked")
		public List viewAbnormalDetailInfo(HttpServletRequest request);
		
		@SuppressWarnings("rawtypes")
		public String overTimeLimitImportDemo(HttpServletRequest request,List aliasNameList, List list, List mapList, List mapNameList,String flag)throws SQLException;
		
}
