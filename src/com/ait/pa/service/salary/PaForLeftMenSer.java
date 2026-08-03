package com.ait.pa.service.salary;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;

public interface PaForLeftMenSer {

	@SuppressWarnings("unchecked")
	public List getPaAllItemList(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public Map submitAddPaForleftMen(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List getPaForLeftMenDetailList(HttpServletRequest request);

	public int getPaForLeftMenDetailListCnt(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List getPaForLeftMenApplyList(HttpServletRequest request);

	public int getPaForLeftMenApplyListCnt(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List getPaForLetMenTempList(HttpServletRequest request);

	public int getPaForLetMenTempListCnt(HttpServletRequest request);

	public int getPaForLetMenTempErrorCnt(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public String getPaForLeftMenModleInfo(HttpServletRequest request,
			List aliasNameList, List list, List mapList, List mapNameList)
			throws SQLException;

	@SuppressWarnings("unchecked")
	public String getPaForLeftMenInfoList(HttpServletRequest request,
			List aliasNameList, List list, List mapList, List mapNameList)
			throws SQLException;

	public int savePaForLeftMenInfo(HttpServletRequest request)
			throws Exception;

	/**
	 * 删除临时表中所有导入的离职员工薪资补发信息申请(delete pa info for emp of left apply information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int cancelPaForLeftApplyImport(HttpServletRequest request)
			throws Exception;

	public int delPaForLeftMenInfo(HttpServletRequest request);

	public int updatePaForLeftMenInfo(HttpServletRequest request);

	public List getApplyFeeList(HttpServletRequest request) throws Exception;

	@SuppressWarnings("unchecked")
	public List getPaForLeftMenAffirmList(HttpServletRequest request);

	public int getPaForLeftMenAffirmListCnt(HttpServletRequest request);

	/**
	 * 根据离职员工薪资补发申请NO决裁信息查询(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPaForLeftAffirmorByApplyNoList(HttpServletRequest request)
			throws Exception;

	/**
	 * 离职员工薪资补发申请check信息查询(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPaForLeftCheckorByApplyNoList(HttpServletRequest request)
			throws Exception;

	/**
	 * 通过/否决离职员工薪资补发申请(pass and reject pa info for emp of left apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int approvePaForLeftApply(HttpServletRequest request)
			throws Exception;

	/**
	 * 通过/否决离职员工薪资补发申请(pass and reject pa info for emp of left apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int approvePaForLeftApply_ep(HttpServletRequest request)
			throws Exception;

	/**
	 * 批量通过/否决离职员工薪资补发申请(batch pass and reject pa info for emp of left apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int approvePaForLeftApplyInBatch(HttpServletRequest request)
			throws Exception;

	/**
	 * 批量删除离职员工薪资补发申请(batch delete pa info for emp of left apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int delPaForLeftApplyInBatch(HttpServletRequest request)
			throws Exception;

	/**
	 * 删除未审核离职员工薪资补发信息申请(delete pa info for emp of left apply information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public boolean delPaForLeftApply(HttpServletRequest request)
			throws Exception;

	/**
	 * 添加checkor(add checkor)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int addApplyCheckList(HttpServletRequest request) throws Exception;

	/**
	 * 离职员工薪资补发申请check列表(pa for emp of left apply check list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPaForLeftCheckList(HttpServletRequest request)
			throws Exception;

	/**
	 * 离职员工薪资补发申请check总数(pa for emp of left apply check list count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int getPaForLeftCheckListCnt(HttpServletRequest request)
			throws Exception;

}
