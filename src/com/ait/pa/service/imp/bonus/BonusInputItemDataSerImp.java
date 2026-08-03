package com.ait.pa.service.imp.bonus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.pa.dao.BonusInputItemDataDao;
import com.ait.pa.dao.BonusInputItemParamDao;
import com.ait.pa.dao.PaBasicItemDao;
import com.ait.pa.service.bonus.BonusInputItemDataSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

@Service
public class BonusInputItemDataSerImp implements BonusInputItemDataSer {

	Logger logger = Logger.getLogger(BonusInputItemSerImp.class);

	@Autowired
	private BonusInputItemParamDao bonusInputItemParamDao;

	@Autowired
	private BonusInputItemDataDao bonusInputItemDataDao;
	
	@Autowired
	private PaBasicItemDao paBasicItemDao;

	@SuppressWarnings("unchecked")
	public Object getBonusInputItemInfo(HttpServletRequest request) {
		Object returnObj = new Object();

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");

		returnObj = bonusInputItemParamDao
				.getPaBonusInputItemParamInfo(paramMap);

		return returnObj;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getPaBonusInputItemDataCnt(HttpServletRequest request) {

		int retrunInt = 0;

		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");

		// 取得数据项目信息
		LinkedHashMap bonusInputItemInfo = (LinkedHashMap) this
				.getBonusInputItemInfo(request);

		// 取得该项目参数的DISTINCT_FIELD
		String distinctField = ObjectUtils.toString(bonusInputItemInfo
				.get("DISTINCT_FIELD"));
		String distinctField2 = ObjectUtils.toString(bonusInputItemInfo
				.get("DISTINCT_FIELD_2ND"));
		paramMap.put("DISTINCT_FIELD", distinctField);
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PA_ADMIN_ID", admin.getAdminID());
		Calendar c = Calendar.getInstance();
		String year=String.valueOf(c.get(Calendar.YEAR));
		String month=String.valueOf(c.get(Calendar.MONTH)+1);
		String bonusYear=request.getParameter("seach_bonusInputYear") == null ? year : request.getParameter("seach_bonusInputYear");
		String bonusMonth=request.getParameter("seach_bonusInputMonth") == null ? month : request.getParameter("seach_bonusInputMonth");
		if(bonusYear != null && bonusMonth != null && !("").equals(bonusYear) && !("").equals(bonusMonth)){
			paramMap.put("PA_MONTH", bonusYear+bonusMonth);
		}
		/*if(bonusYear == null && bonusMonth == null){
			paramMap.put("PA_MONTH", new SimpleDateFormat("yyyyMM").format(new Date()));
		}*/
		// 判断DISTINCT_FIELD是否是EMPID 两者返回的列表不同
		if (distinctField.equals("PERSON_ID")) {
			retrunInt = bonusInputItemDataDao
					.getBonusInputItemDataListDistinctFieldIsEmpidCnt(paramMap);
		}else if(distinctField.equals("CPNY_ID") && !distinctField2.equals("DEPTNO")){
			retrunInt = bonusInputItemDataDao
					.getBonusInputItemDataListDistinctFieldIsCpnyIdCnt(paramMap);
		}else if(!distinctField.equals("CPNY_ID") && distinctField2.equals("DEPTNO")){
			retrunInt = bonusInputItemDataDao
					.getBonusInputItemDataListDistinctFieldIsDeptNoCnt(paramMap);
		}//只针对与C01权限
		else if((distinctField.equals("WORK_AREA")||distinctField.equals("SOCIAL_SECURITY_AREA")) && admin.getCpnyId().equals("C01")){
			paramMap.put("C01_ADMINID", admin.getPersonId());
			retrunInt = bonusInputItemDataDao
					.getBonusInputItemDataListDistinctFieldIsNotEmpidCnt(paramMap);
		}else {
			retrunInt = bonusInputItemDataDao
					.getBonusInputItemDataListDistinctFieldIsNotEmpidCnt(paramMap);
		}

		return retrunInt;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getPaBonusInputItemDataList(HttpServletRequest request) {

		List retrunList = new ArrayList();

		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");

		// 取得数据项目信息
		LinkedHashMap bonusInputItemInfo = (LinkedHashMap) this.getBonusInputItemInfo(request);

		// 取得该项目参数的DISTINCT_FIELD
		String distinctField = ObjectUtils.toString(bonusInputItemInfo
				.get("DISTINCT_FIELD"));
		paramMap.put("DISTINCT_FIELD", distinctField);
		String distinctField2 = ObjectUtils.toString(bonusInputItemInfo
				.get("DISTINCT_FIELD_2ND"));
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PA_ADMIN_ID", admin.getPersonId());
		Calendar c = Calendar.getInstance();
		String year=String.valueOf(c.get(Calendar.YEAR));
		String month=String.valueOf(c.get(Calendar.MONTH)+1);
		String bonusYear=request.getParameter("seach_bonusInputYear") == null ? year : request.getParameter("seach_bonusInputYear");
		String bonusMonth=request.getParameter("seach_bonusInputMonth") == null ? month : request.getParameter("seach_bonusInputMonth");
		if(bonusYear != null && bonusMonth != null &&!("").equals(bonusYear) &&!("").equals(bonusMonth)){
			paramMap.put("PA_MONTH", bonusYear+bonusMonth);
		}
		//默认修改当前月份注销 2013-10-22 与年月份<请选择>冲突
		/*if(bonusYear == null && bonusMonth == null){
			paramMap.put("PA_MONTH", new SimpleDateFormat("yyyyMM").format(new Date()));
		}*/
		// 是否分页
		if (UiUtil.getPageNum(request) > 0) {
			// 判断DISTINCT_FIELD是否是EMPID 两者返回的列表不同
			if (distinctField.equals("PERSON_ID")) {
				retrunList = bonusInputItemDataDao
						.getBonusInputItemDataListDistinctFieldIsEmpid(
								paramMap, UiUtil.getPageNum(request), UiUtil
										.getNumPerPage(request));
			}else if(distinctField.equals("CPNY_ID") && !distinctField2.equals("DEPTNO")){
				retrunList = bonusInputItemDataDao
						.getBonusInputItemDataListDistinctFieldIsCpnyId(
								paramMap, UiUtil.getPageNum(request), UiUtil
								.getNumPerPage(request));
			}else if(!distinctField.equals("CPNY_ID") && distinctField2.equals("DEPTNO")){
				retrunList = bonusInputItemDataDao
						.getBonusInputItemDataListDistinctFieldIsDeptNo(
								paramMap, UiUtil.getPageNum(request), UiUtil
								.getNumPerPage(request));
			}//只针对与C01权限
			else if((distinctField.equals("WORK_AREA")||distinctField.equals("SOCIAL_SECURITY_AREA")) && admin.getCpnyId().equals("C01")){
				paramMap.put("C01_ADMINID", admin.getPersonId());
				retrunList = bonusInputItemDataDao
				.getBonusInputItemDataListDistinctFieldIsNotEmpid(
						paramMap, UiUtil.getPageNum(request), UiUtil
								.getNumPerPage(request));
			}else {
				retrunList = bonusInputItemDataDao
						.getBonusInputItemDataListDistinctFieldIsNotEmpid(
								paramMap, UiUtil.getPageNum(request), UiUtil
										.getNumPerPage(request));
			}
		} else {
			// 判断DISTINCT_FIELD是否是EMPID 两者返回的列表不同
			if (distinctField.equals("PERSON_ID")) {
				retrunList = bonusInputItemDataDao
						.getBonusInputItemDataListDistinctFieldIsEmpid(paramMap);
			}else if(distinctField.equals("CPNY_ID") && !distinctField2.equals("DEPTNO")){
				retrunList = bonusInputItemDataDao
						.getBonusInputItemDataListDistinctFieldIsCpnyId(paramMap);
			}else if(!distinctField.equals("CPNY_ID") && distinctField2.equals("DEPTNO")){
				retrunList = bonusInputItemDataDao
						.getBonusInputItemDataListDistinctFieldIsDeptNo(paramMap);
			}//只针对与C01权限
			else if((distinctField.equals("WORK_AREA")||distinctField.equals("SOCIAL_SECURITY_AREA")) && admin.getCpnyId().equals("C01")){
				paramMap.put("C01_ADMINID", admin.getPersonId());
				retrunList = bonusInputItemDataDao
						.getBonusInputItemDataListDistinctFieldIsNotEmpid(paramMap);
			}else {
				retrunList = bonusInputItemDataDao
						.getBonusInputItemDataListDistinctFieldIsNotEmpid(paramMap);
			}
		}
		return retrunList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int addPaBonusInputItemDataInfo(HttpServletRequest request) {
		// TODO Auto-generated method stub

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CREATED_BY", admin.getAdminID());

//		this.bonusInputItemDataDao.addPaBonusInputItemInfo(paramMap);

		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int checkAddPaBonusInputItemDataInfo(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		return this.bonusInputItemDataDao
				.checkAddPaBonusInputItemDataInfo(paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int checkBonusInputItemDataInfo(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		return this.bonusInputItemDataDao.checkBonusInputItemDataInfo(paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int checkBonusInputItemDataInfoType(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		return this.bonusInputItemDataDao.checkBonusInputItemDataInfoType(paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int deletePaBonusInputItemDataInfo(HttpServletRequest request) {

		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil
				.getRequestParamData(request);
		try {
			this.bonusInputItemDataDao.deleteBonusInputItemDataInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int deletePaBonusInputItemDataInfoType(HttpServletRequest request) {

		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil
				.getRequestParamData(request);
		try {
			this.bonusInputItemDataDao.deleteBonusInputItemDataInfoType(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * 批量删除输入项目数据
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int deletePaBonusInputItemDataBatchInfo(HttpServletRequest request) {

		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil
				.getRequestParamData(request);
		try {
				this.bonusInputItemDataDao
						.deleteBonusInputItemDataBatchInfo(paramMap);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 批量删除输入项目数据
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int deletePaBonusInputItemDataBatchInfoType(HttpServletRequest request) {

		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil
				.getRequestParamData(request);
		try {
				this.bonusInputItemDataDao
						.deleteBonusInputItemDataBatchInfoType(paramMap);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getPaBonusInputItemDataInfo(HttpServletRequest request) {
		// TODO Auto-generated method stub
		Object returnObj = new Object();

		// 页面提交数据
		@SuppressWarnings("unused")
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		returnObj = null;// bonusInputItemDataDao.getPaBonusInputItemInfo(paramMap);

		return returnObj;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int updatePaBonusInputItemDataInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		LinkedHashMap bonusInputItemInfo = (LinkedHashMap) this
		.getBonusInputItemInfo(request);

		// 取得该项目参数的DISTINCT_FIELD
		String distinctField = ObjectUtils.toString(bonusInputItemInfo
		.get("DISTINCT_FIELD"));
		String[] paramData = request.getParameterValues("c1");
		for (int i = 0; i < paramData.length; i++) {
			
			paramMap.put("PARAM_DATA_NO", paramData[i]);
			paramMap.put("START_MONTH", request.getParameter("START_MONTH_"+paramData[i]));
			paramMap.put("END_MONTH", request.getParameter("END_MONTH_"+paramData[i]));
			paramMap.put("RETURN_VALUE", request.getParameter("RETURN_VALUE_"+paramData[i]));
			paramMap.put("REMARK", request.getParameter("REMARK_"+paramData[i]));
			// 检测下数据库里是否存在这条记录

			paramMap.put("UPDATED_BY", admin.getAdminID());
			if(distinctField.equals("PERSON_ID")){
				this.bonusInputItemDataDao.updateBonusInputItemDataInfo(paramMap);
			}else{
				this.bonusInputItemDataDao.updateBonusInputItemDataInfoOther(paramMap);
			}
			
		}

		return 1;
	}

	@SuppressWarnings("unchecked")
	public List getAddBonusInputItemDataList(HttpServletRequest request) {
		List retrunList = new ArrayList();

		this.createAddBonusInputItemDataInfo(request);

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		// 取得数据项目信息
		LinkedHashMap bonusInputItemInfo = (LinkedHashMap) this
				.getBonusInputItemInfo(request);

		if (UiUtil.getPageNum(request) > 0) {
			if (ObjectUtils.toString(bonusInputItemInfo.get("DISTINCT_FIELD"))
					.equals("EMPID")) {
				retrunList = bonusInputItemDataDao
						.getAddBonusInputItemDataListDistinctFieldIsEmpid(paramMap);
			} else {
				retrunList = bonusInputItemDataDao
						.getAddBonusInputItemDataListDistinctFieldIsNotEmpid(paramMap);
			}
		} else {
			if (ObjectUtils.toString(bonusInputItemInfo.get("DISTINCT_FIELD"))
					.equals("EMPID")) {
				retrunList = bonusInputItemDataDao
						.getAddBonusInputItemDataListDistinctFieldIsEmpid(paramMap);
			} else {
				retrunList = bonusInputItemDataDao
						.getAddBonusInputItemDataListDistinctFieldIsNotEmpid(paramMap);
			}
		}
		return retrunList;
	}
	
	/**
	 * 获取保险输入项目参数列表（get Insurance Input Item Param List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getBnParamDataList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
				
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		LinkedHashMap bnInputItemParamInfo = (LinkedHashMap)this.bonusInputItemParamDao.getPaBonusInputItemParamInfo(paramMap);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		String distinctField1 = ObjectUtils.toString(bnInputItemParamInfo
				.get("DISTINCT_FIELD"));
		String distinctField2 = ObjectUtils.toString(bnInputItemParamInfo
				.get("DISTINCT_FIELD_2ND"));
		paramMap.put("distinctField1", distinctField1);
		paramMap.put("distinctField2", distinctField2);
		if("CPNY_ID".equals(distinctField1)){	
			retrunList = bonusInputItemDataDao.getBnParamDataIsCpnyIdList(paramMap);
		}else if("DEPTNO".equals(distinctField2)){
			retrunList = bonusInputItemDataDao.getBnParamDataList(paramMap);
		}else{
			retrunList = bonusInputItemDataDao.getBnParamDataList(paramMap);
		}
		return retrunList ;
	}
	
	/**
	 * 获取保险输入项目参数列表（get Insurance Input Item Param List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getBnParamDataTwoList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
				
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		LinkedHashMap bnInputItemParamInfo = (LinkedHashMap)this.bonusInputItemParamDao.getPaBonusInputItemParamInfo(paramMap);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		String distinctField2 = ObjectUtils.toString(bnInputItemParamInfo
				.get("DISTINCT_FIELD_2ND"));
		paramMap.put("distinctField2", distinctField2);
		if("DEPTNO".equals(distinctField2)){
			retrunList = bonusInputItemDataDao.getBnParamDataIsDeptNoList(paramMap);
		}else{
			retrunList = bonusInputItemDataDao.getBnParamDataTwoList(paramMap);
		}
		
		
		
		
		return retrunList ;
	}

	@SuppressWarnings("unchecked")
	public int createAddBonusInputItemDataInfo(HttpServletRequest request) {

		// 附加信息
		LinkedHashMap<String, Object> paramMap = ObjectBindUtil
				.getRequestParamData(request,"seach_");

		this.bonusInputItemDataDao.createAddBonusInputItemDataInfo(paramMap);

		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getPaBonusInputItemParamInfo(HttpServletRequest request) {
		// TODO Auto-generated method stub
		Object returnObj = new Object();

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");

		returnObj = this.bonusInputItemParamDao.getPaBonusInputItemParamInfo(paramMap);

		return returnObj;
	}
	
	
	/**
	 * 添加保险输入项目数据信息（add Insurance Input Item Data Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int addBonusInputItemDataInfo(HttpServletRequest request) {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		
		int result = 0;
		String person_id = paramMap.get("dwz.person.personId")!=null ? paramMap.get("dwz.person.personId").toString() : "";
		paramMap.put("CREATED_BY", admin.getAdminID());
		paramMap.put("UPDATED_BY", admin.getAdminID());
		paramMap.put("TABLE_NAME", "BN_PARAM_DATA");
		paramMap.put("PERSON_ID", person_id);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("START_MONTH_STR", paramMap.get("START_MONTH"));
		if(this.paBasicItemDao.checkAddPaBasicItemDataInfoMonth(paramMap) != 0){
			result = 2;
		}else{
			if(this.paBasicItemDao.checkAddPaBasicItemDataInfo(paramMap) == 0){
				this.bonusInputItemDataDao.addBonusInputItemDataInfo(paramMap);
				result = 0;
			}else{			
				this.bonusInputItemDataDao.updateBonusInputItemDataInfoMonth(paramMap);
				this.bonusInputItemDataDao.addBonusInputItemDataInfo(paramMap);
				result = 0;
			}
		}
		return result ;
	}
	
	/**
	 * 添加保险输入项目数据信息（add Insurance Input Item Data Info）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int addBonusInputItemOtherDataInfo(HttpServletRequest request) {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		
		int result = 0;
		paramMap.put("CREATED_BY", admin.getAdminID());
		paramMap.put("UPDATED_BY", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("TABLE_NAME", "BN_PARAM_DATA_OTHER");
		paramMap.put("START_MONTH_STR", paramMap.get("START_MONTH"));
		if(this.paBasicItemDao.checkAddPaBasicItemDataOtherInfoMonth(paramMap) != 0){
			result = 2;
		}else{
			if(this.paBasicItemDao.checkAddPaBasicItemDataOtherInfo(paramMap) == 0){
				this.bonusInputItemDataDao.addBonusInputItemOtherDataInfo(paramMap);
				result = 0;
			}else{
				this.bonusInputItemDataDao.updateBonusInputItemDataOtherInfoMonth(paramMap);
				this.bonusInputItemDataDao.addBonusInputItemOtherDataInfo(paramMap);
				result = 0;
				}
		}
		return result ;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getBonusInputItemDataPersonList(HttpServletRequest request) {
		List retrunList = new ArrayList();

		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}

		// 是否分页
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = bonusInputItemDataDao.getBonusInputItemDataPersonList(
					paramMap, UiUtil.getPageNum(request), UiUtil
							.getNumPerPage(request));

		} else {
			retrunList = bonusInputItemDataDao
					.getBonusInputItemDataPersonList(paramMap);
		}
		return retrunList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getBonusInputItemDataPersonCnt(HttpServletRequest request) {

		int retrunInt = 0;

		// 页面提交数据
		Map<String,Object> paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}

		retrunInt = bonusInputItemDataDao
				.getBonusInputItemDataPersonCnt(paramMap);

		return retrunInt;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getAddBonusPersonalInputList(HttpServletRequest request) {
		List retrunList = new ArrayList();

		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");

		// 是否分页
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = bonusInputItemDataDao.getAddBonusPersonalInputList(
					paramMap, UiUtil.getPageNum(request), UiUtil
							.getNumPerPage(request));

		} else {
			retrunList = bonusInputItemDataDao
					.getAddBonusPersonalInputList(paramMap);
		}
		return retrunList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getAddBonusPersonalInputListCnt(HttpServletRequest request) {

		int retrunInt = 0;

		// 页面提交数据
		Map<String,Object> paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");

		retrunInt = bonusInputItemDataDao
				.getAddBonusPersonalInputListCnt(paramMap);

		return retrunInt;
	}
	@SuppressWarnings("unchecked")
	@Override
	public int updateBonusInputItemDataPersonInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		String[] paramData = request.getParameterValues("c1");

		for (int i = 0; i < paramData.length; i++) {
			
			paramMap.put("PARAM_NO", paramData[i]);
			paramMap.put("START_MONTH", request.getParameter("START_MONTH_"+paramData[i]));
			paramMap.put("END_MONTH", request.getParameter("END_MONTH_"+paramData[i]));
			paramMap.put("RETURN_VALUE", request.getParameter("RETURN_VALUE_"+paramData[i]));
			paramMap.put("PARAM_DATA_NO", request.getParameter("PARAM_DATA_NO_"+paramData[i]));
			paramMap.put("CPNY_ID", request.getParameter("CPNY_ID_"+paramData[i]));
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID_"+paramData[i]));
			// 检测下数据库里是否存在这条记录
			int count = this.bonusInputItemDataDao
					.checkUpdateBonusInputItemDataPersonInfo(paramMap);
			// 如果存在记录则修改 没有记录则添加
			if (count == 1) {
				paramMap.put("UPDATED_BY", admin.getAdminID());
				this.bonusInputItemDataDao.updateBonusInputItemDataPersonInfo(paramMap);
			}else{
				paramMap.put("CREATED_BY", admin.getAdminID());
				this.bonusInputItemDataDao.addBonusInputItemDataPersonInfo(paramMap);
			}
		}

		return 1;
	}
}
