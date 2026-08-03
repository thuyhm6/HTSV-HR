package com.ait.ess.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

public interface AnnualadjustmentInfoSer {
	
	/**
	 * 查询年假福利年假天数 
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	public List retrieveNianjiaFuli(HttpServletRequest request)
			throws Exception;
	
	public int getCwaCheckCnt(HttpServletRequest request) throws Exception ;
	/**
	 * 添加年休假申请信息
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	public int addAnnualadjustmentApply(HttpServletRequest request)
			throws Exception;
	

	
	/**
	 * 查看年假调整List
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	public List getAnnualadjustmentAffirmList(HttpServletRequest request,String batchFlag)
			throws Exception;
	
	/**
	 * 删除年假调整申请
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int delAnnuApplyInBatch(HttpServletRequest request)throws Exception;
	
	/**
	 * 添加年休假申请信息
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	public int delAnnApplyInBatch(HttpServletRequest request)
			throws Exception;
	
	/**
	 * 查看决裁年假调整List
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	public List getAnnualadjustmentAffirmapplyList(HttpServletRequest request)
			throws Exception;
	
	
	/**
	 * 查看决裁年假调整List
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	public int getAnnualadjustmentAffirmapplyListCnt(HttpServletRequest request)
			throws Exception;
	/**
	 * 查看年假调整List
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	public int getAnnualadjustmentAffirmListCnt(HttpServletRequest request,String batchFlag)
			throws Exception;
	
	
	
	/**
	 * 批量添加考勤异常申请(batch add egression apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int addBatchCwaAbnormalApply(HttpServletRequest request)
			throws Exception;
	
	/**
	 * 批量个人添加考勤异常申请(add egression apply AnyApprover)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int addAbnormalApplyByAnyApprover(HttpServletRequest request) throws Exception;


	/**
	 * 删除年假调整申请
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int delCwaAbnormalApplyInfo(HttpServletRequest request)throws Exception;
 
	public List getCwaAbnormalApplyInfoList(HttpServletRequest request,String batchFlag)
			throws Exception;
	
	
	public int getCwaAbnormalApplyInfoListCnt(HttpServletRequest request,String batchFlag)
	throws Exception;
	
	/**
	 * kaoqinyichang 决裁列表(affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getviewCwaAbnormalAffirmList(HttpServletRequest request)
			throws Exception;
	
	
	/**
	 * kaoqinyichang 决裁列表(affirm list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getviewCwaAbnormalAffirmListCnt(HttpServletRequest request)
			throws Exception;
	
	
	@SuppressWarnings("unchecked")
	public List getCwaAbnormalAffirmByApplyNOList(HttpServletRequest request)
			throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getCwaCheckList(HttpServletRequest request)
			throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getAnnuCheckList(HttpServletRequest request)
			throws Exception;
	
	
	@SuppressWarnings("unchecked")
	public int getAnnuCheckListCnt(HttpServletRequest request)
			throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getAnnuapplyListByApplyno(HttpServletRequest request)
			throws Exception;
	/**
	 * 根据加班申请NO决裁信息查询(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getAffirmorByApplyNoList(HttpServletRequest request) throws Exception;
	
	/**
	 * 加班申请check信息查询(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getCheckorByApplyNoList(HttpServletRequest request) throws Exception;
	
	/**
	 * 获取年假调整导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getEssArVacTempList(HttpServletRequest request) ;
	
	/**
	 * 获取年假调整导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getEssArVacTempCnt(HttpServletRequest request, String errorFlag);
	
	/**
	 * 年假调整批量申请excel信息提交
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String submitImportExcelEssArVacEmpData(HttpServletRequest request);
	

	/**
	 * 年假调整批量申请详细信息查询(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getArVacBatchAffirmInfoList(HttpServletRequest request) throws Exception ;
	
	/**
	 * 年假调整批量申请详细信息数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getArVacBatchAffirmInfoCnt(HttpServletRequest request);
	
	/**
	 * 批量删除年假调整申请
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int delAracApplyInBatchForBatch(HttpServletRequest request)
			throws Exception;
}
