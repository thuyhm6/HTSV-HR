package com.ait.ess.service;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface DimissionApplySer {
	
	public String getDimissionInfoSeq(HttpServletRequest request) throws Exception;
	/**
	 * 离职申请检查该员工是否已经申请
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int checkAddDimissionInfo(HttpServletRequest request);
	
	/**
	 * 离职申请
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int addDimissionInfoApply(HttpServletRequest request) throws Exception;
	
	/**
	 * 取得审批人列表:1.先取特殊设置人员的决裁者;2.再取特殊设置部门的决裁者;3.最后按流程取决裁者 (get approver list:1
	 * get approver by special-person's-approver setup.2 get approver by
	 * special-department's-approver setup.3 get approver by approve-flow)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List getAffirmorList(HttpServletRequest request) throws Exception;
	
	  /**
		 * 查找离职申请数量
		 * @param parameterObject
		 * @return
		 * @throws Exception
		 */
		public int getDimissionCnt(HttpServletRequest request)throws SQLException;
		
		/**
		 * 查找离职申请的List 
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return
		 * @throws Exception
		 */ 
		@SuppressWarnings("unchecked")
		public List getDimissionList(HttpServletRequest request);
		
		/**
		 * 删除还没有开始审批的申请信息
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return
		 * @throws Exception
		 */ 
		@SuppressWarnings("unchecked")
		public int deleteDimissionInfo(HttpServletRequest request);
		
		/**
		 * 检查要删除的申请信息是否开始审批  如果开始则不能删除
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return
		 * @throws Exception
		 */ 
		@SuppressWarnings("unchecked")
		public int checkDimissionInfo(HttpServletRequest request);
		
}
