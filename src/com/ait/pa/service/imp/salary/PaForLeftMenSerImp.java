package com.ait.pa.service.imp.salary;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.affirm.service.AffirmInfoToLGEPSer;
import com.ait.ess.dao.AffirmApplyDao;
import com.ait.ess.dao.EditionAffirmDao;
import com.ait.ess.service.InfoApplySer;
import com.ait.pa.dao.PaForLeftMenDao;
import com.ait.pa.dao.tempsale.PaTempSalesDAO;
import com.ait.pa.service.excelUtil.ExcelUtilSer;
import com.ait.pa.service.salary.PaForLeftMenSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.messages.Messages;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright: LGE Company: LGE
 * 
 * @fileName: PaForLeftMenSerImp.java
 * @Description:
 * @Create date: 2014-9-6 下午16:54:03
 * @Create by: lufeng(lufeng@ait.net.cn)
 * @version 5.1
 */
@Service
public class PaForLeftMenSerImp implements PaForLeftMenSer {

	Logger logger = Logger.getLogger(PaForLeftMenSerImp.class);
	private static String APPLY_TYPE_NO = "224";
	
	@Autowired
	private EditionAffirmDao editionAffirmDao;
	
	@Autowired
	private PaForLeftMenDao paForLeftMenDao ;
	
	@Autowired
	private ExcelUtilSer excelUtilSer;
	
	@Autowired
	private PaTempSalesDAO paTempSalesDAO;
	
	@Autowired
	private AffirmInfoToLGEPSer affirmInfoToLGEPSer;
	
	@Autowired
	private InfoApplySer infoApplySer;
	
	@Autowired
	private AffirmApplyDao affirmApplyDao;
	 
	
	/**
	 * 离职人员工资补发页面 取得所有工资项目，added on 2014-07-01
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPaAllItemList(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		String table_name="PA_SUMMARY_"+paramMap.get("interCpnyID");
		paramMap.put("table_name", table_name);
		//this.paForLeftMenDao.getPaForLetMenList(paramMap,pageNum, numPerPage);
		
		return this.paForLeftMenDao.getPaAllItemList(paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public Map submitAddPaForleftMen(HttpServletRequest request) {
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		int vResult = -1;
		String date = "";
		String item = "";
		Map messMap = new LinkedHashMap();
		// 页面参数
		String jsonString = request.getParameter("jsonData");
		List<LinkedHashMap<String, Object>> paDetailInfoList = ObjectBindUtil.getRequestJsonData(jsonString);
		for (int i = 0; i < paDetailInfoList.size(); i++) {
			LinkedHashMap arDetailInfoMap = (LinkedHashMap) paDetailInfoList.get(i);
			arDetailInfoMap.put("interCpnyID", admin.getCpnyId());
			arDetailInfoMap.put("interLanguage", admin.getLanguage());
			arDetailInfoMap.put("paMonthZhifu",(String)arDetailInfoMap.get("paYearZhifu")+(String)arDetailInfoMap.get("paMonthZhifu"));
			arDetailInfoMap.put("paMonthFafang",(String)arDetailInfoMap.get("paYearFafang")+(String)arDetailInfoMap.get("paMonthFafang"));
		}
		try {
			this.paForLeftMenDao.submitAddPaForleftMen(paDetailInfoList);
			messMap.put("scode", "1");
		} catch (Exception e) {
			e.printStackTrace();
			messMap.put("scode", "0");
			return messMap;
		}
		return messMap;
	}
	
	/**
	 * 离职人员工资补发信息，update 2014-09-06
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPaForLeftMenDetailList(HttpServletRequest request) {
		// 页面提交数据
		String interLanguage = request.getParameter("LANGUAGE");
		String personId = request.getParameter("personId");
		String BATCH_NO = request.getParameter("BATCH_NO");
		Map paramMap = null;
		if(interLanguage == null || "".equals(interLanguage)){
			paramMap = ObjectBindUtil.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}else{
			paramMap = ObjectBindUtil.getRequestParamDataNoSession(request);
			paramMap.put("interLanguage", interLanguage);
		}
		paramMap.put("APPLY_TYPE_NO", APPLY_TYPE_NO);
		paramMap.put("PERSON_ID", personId);
		paramMap.put("BATCH_NO", BATCH_NO);
		List returnList = new ArrayList();
		if (UiUtil.getPageNum(request) > 0){
			returnList = 
				paForLeftMenDao.getPaForLeftMenDetailList(paramMap ,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}else{
			returnList = paForLeftMenDao.getPaForLeftMenDetailList(paramMap) ;
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int getPaForLeftMenDetailListCnt(HttpServletRequest request) {
		String interLanguage = request.getParameter("LANGUAGE");
		String personId = request.getParameter("personId");
		String BATCH_NO = request.getParameter("BATCH_NO");
		Map paramMap = null;
		if(interLanguage == null || "".equals(interLanguage)){
			paramMap = ObjectBindUtil.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}else{
			paramMap = ObjectBindUtil.getRequestParamDataNoSession(request);
			paramMap.put("interLanguage", interLanguage);
		}
		paramMap.put("APPLY_TYPE_NO", APPLY_TYPE_NO);
		paramMap.put("PERSON_ID", personId);
		paramMap.put("BATCH_NO", BATCH_NO);
		return paForLeftMenDao.getPaForLeftMenDetailListCnt(paramMap) ;
	}
	
	/**
	 * 离职人员工资补发申请信息
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPaForLeftMenApplyList(HttpServletRequest request) {
		// 页面提交数据
		String interLanguage = request.getParameter("LANGUAGE");
		String personId = request.getParameter("personId");
		Map paramMap = null;
		if(interLanguage == null || "".equals(interLanguage)){
			paramMap = ObjectBindUtil.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}else{
			paramMap = ObjectBindUtil.getRequestParamDataNoSession(request);
			paramMap.put("interLanguage", interLanguage);
		}
		paramMap.put("APPLY_TYPE_NO", APPLY_TYPE_NO);
		paramMap.put("PERSON_ID", personId);
		List returnList = new ArrayList();
		if (UiUtil.getPageNum(request) > 0){
			returnList = 
				paForLeftMenDao.getPaForLeftMenApplyList(paramMap ,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}else{
			returnList = paForLeftMenDao.getPaForLeftMenApplyList(paramMap) ;
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int getPaForLeftMenApplyListCnt(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("language", Messages.getLanguage(request));
		
		return paForLeftMenDao.getPaForLeftMenApplyListCnt(paramMap) ;
	}
	
	/**
	 * 离职人员工资补发信息，update 2014-09-06
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPaForLetMenTempList(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CREATED_BY", admin.getPersonId());
		List returnList = new ArrayList();
		//if (UiUtil.getPageNum(request) > 0){
			//returnList = 
				//paForLeftMenDao.getPaForLetMenTempList(paramMap ,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		//}else{
			returnList = paForLeftMenDao.getPaForLetMenTempList(paramMap) ;
		//}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int getPaForLetMenTempListCnt(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("language", Messages.getLanguage(request));
		
		return paForLeftMenDao.getPaForLetMenTempListCnt(paramMap) ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int getPaForLetMenTempErrorCnt(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CREATED_BY", admin.getPersonId());
		
		return paForLeftMenDao.getPaForLetMenTempErrorCnt(paramMap) ;
	}
	
	@SuppressWarnings("unchecked")
	public List getApplyFeeList(HttpServletRequest request) throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		String applyTypeNo = paramMap.get("APPLY_TYPE_NO")!=null?paramMap.get("APPLY_TYPE_NO").toString():"224";
		
		return this.infoApplySer.getAffirmorListByString(applyTypeNo, paramMap.get("PERSON_ID").toString(), "", "",paramMap.get("LANGUAGE").toString());
	}
	
	@SuppressWarnings("unchecked")
	public String getPaForLeftMenModleInfo(HttpServletRequest request,List aliasNameList, List list,List mapList, List mapNameList)throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String cpnyId = admin.getCpnyId()!=null?admin.getCpnyId().toString():"TSTO";
		String tableName = "PA_SUMMARY_"+cpnyId;
		String codeCsmcSql = " select 'PA' ITEM_NO,'薪资' ITEM_NAME from dual union all "
			               + " select 'IS' ITEM_NO,'保险' ITEM_NAME from dual ";
//		String codeDqmcSql = " SELECT ITEM_NO,ITEM_ID,ITEM_NAME,ITEM_TYPE FROM PA_IS_ITEM_V WHERE CPNY_ID = '"+cpnyId+"' AND ITEM_ID IN "
//			               + "(SELECT UT.COLUMN_NAME FROM USER_TAB_COLS UT WHERE UT.TABLE_NAME = '"+tableName+"') ";
		String name = "";
		aliasNameList.add("社号*");//社号
		aliasNameList.add("姓名");//姓名
		aliasNameList.add("补发月份[例：201401]*");//补发月份
		aliasNameList.add("项目类型*");//项目类型
//		aliasNameList.add("补发项目*");//补发项目
//		aliasNameList.add("金额*");//金额
		aliasNameList.add("发放月份[例：201401]*");//发放月份
		aliasNameList.add("备注[不超过100字]");//备注
		String sqlDetailInfo = " select '14000001' empid,'张三' name,'201312' pa_month_for,'薪资' item_type, "
			+ " '201401' pa_month,'不能为空' remark from dual ";
		LinkedHashMap sqlDetailmap = new LinkedHashMap();
		sqlDetailmap.put("sqlContent", sqlDetailInfo);
		List itemDetailList = this.excelUtilSer.getContentNoByFiled(sqlDetailmap);
		for (int i = 0; i < itemDetailList.size(); i++) {
			LinkedHashMap empInfo = new LinkedHashMap();
			empInfo = (LinkedHashMap) itemDetailList.get(i);
			list.add(empInfo);
		}
		//这些是sheet名字
		mapNameList.add("项目类型");
//		mapNameList.add("详细项目信息");
		mapList.add(codeCsmcSql);
//		mapList.add(codeDqmcSql);

		name = "paForLeftMenImportModle";
		return name;
	}
	
	@SuppressWarnings("unchecked")
	public String getPaForLeftMenInfoList(HttpServletRequest request,List aliasNameList, List list,List mapList, List mapNameList)throws SQLException {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String cpnyId = admin.getCpnyId()!=null?admin.getCpnyId().toString():"TSTO";
		String tableName = "PA_SUMMARY_"+cpnyId;
		String codeCsmcSql = " select 'PA' ITEM_NO,'薪资' ITEM_NAME from dual union all "
			               + " select 'IS' ITEM_NO,'保险' ITEM_NAME from dual ";
		String codeDqmcSql = " SELECT ITEM_NO,ITEM_ID,ITEM_NAME,ITEM_TYPE FROM PA_IS_ITEM_V WHERE CPNY_ID = '"+cpnyId+"' AND ITEM_ID IN "
			               + "(SELECT UT.COLUMN_NAME FROM USER_TAB_COLS UT WHERE UT.TABLE_NAME = '"+tableName+"') ";
		String name = "";
		aliasNameList.add("社号*");//社号
		aliasNameList.add("姓名");//
		aliasNameList.add("补发月份*");//
		aliasNameList.add("项目类型*");//项目类型
		aliasNameList.add("补发项目*");//
		aliasNameList.add("金额*");//
		aliasNameList.add("发放月份*");//
		aliasNameList.add("备注");//
		aliasNameList.add("正/异常");//
		aliasNameList.add("错误提示");//错误提示
		String sqlDetailInfo =" SELECT PA.EMPID,PA.LOCAL_NAME,PA.PA_MONTH,PA.ITEM_TYPE,"
								   + " PA.ITEM_NO,PA.ITEM_DATA,PA.PA_MONTH_FOR, "
							       + " PA.REMARK,PA.CHECK_FLAG,PA.CHECK_ERROR "
							       + " FROM PA_LEFTMEN_ADDING_TEMP PA "
							       + " WHERE PA.CPNY_ID = '"+admin.getCpnyId()+ "'"
							       + " AND PA.CREATED_BY = '"+admin.getPersonId()+"' ";
		LinkedHashMap sqlDetailmap = new LinkedHashMap();
		sqlDetailmap.put("sqlContent", sqlDetailInfo);
		List itemDetailList = this.excelUtilSer.getContentNoByFiled(sqlDetailmap);

		for (int i = 0; i < itemDetailList.size(); i++) {
			LinkedHashMap empInfo = new LinkedHashMap();
			empInfo = (LinkedHashMap) itemDetailList.get(i);
			list.add(empInfo);
		}
		//这些是sheet名字
		mapNameList.add("项目类型");
		mapNameList.add("详细项目信息");
		mapList.add(codeCsmcSql);
		mapList.add(codeDqmcSql);

		name = "viewPaForLeftMenInfoExcel";
		return name;
	}
	
	/**
	 * 添加离职人员工资补发申请(add pa info for emp of left apply)
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int savePaForLeftMenInfo(HttpServletRequest request) throws Exception {
		int checkFlag = 0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		
		paramMap.put("TITLE", request.getParameter("TITLE"));
		List paForLeftImportList = new ArrayList();
		List paForLeftDataList = new ArrayList();
		List affirmList = new ArrayList();
		paForLeftDataList = this.getPaForLetMenTempList(request);
		//数据库里的决裁者与页面添加、删除的决裁者进行重组
		affirmList = this.preAddAffirmList(paramMap, request);
		paramMap.put("affirmList", affirmList);
		//这里对所有导入离职人员工资补发临时表里的离职人员工资补发数据进行验证，并将验证结果存入离职人员工资补发信息临时表中
		checkFlag = checkImportPaForEmpLeftData(paForLeftDataList,paramMap,request);
		//只有当所有的派遣地验证全部通过之后才能进行派遣地信息插入
		if(checkFlag==0){
			//数据核对没有出现错误才可以进行下一步（更新person_id,item_type,item_no）
			for(int i=0;i<paForLeftDataList.size();i++){
				LinkedHashMap dataMap = (LinkedHashMap)paForLeftDataList.get(i);
				this.paForLeftMenDao.updatePaForLetMenDataToPersonId(dataMap);
				this.paForLeftMenDao.updatePaForLetMenDataToItemType(dataMap);
				this.paForLeftMenDao.updatePaForLetMenDataToItemNo(dataMap);
			}
			//name转换code之后再次获取信息
			paForLeftDataList = this.getPaForLetMenTempList(request);
			LinkedHashMap tempMap = new LinkedHashMap(); 
			for(int i=0;i<paForLeftDataList.size();i++){
				LinkedHashMap dataMap = new LinkedHashMap();
				tempMap = (LinkedHashMap)paForLeftDataList.get(i);
				dataMap.put("APPLY_NO", tempMap.get("APPLY_NO"));
				dataMap.put("PA_MONTH", tempMap.get("PA_MONTH"));
				dataMap.put("PERSON_ID", tempMap.get("PERSON_ID"));
				dataMap.put("CPNY_ID", admin.getCpnyId());
				dataMap.put("PA_MONTH_FOR", tempMap.get("PA_MONTH_FOR"));
				
				dataMap.put("ITEM_TYPE", tempMap.get("ITEM_TYPE"));
				dataMap.put("ITEM_NO", tempMap.get("ITEM_NO"));
				dataMap.put("ITEM_ID", tempMap.get("ITEM_ID"));
				dataMap.put("ITEM_DATA", tempMap.get("ITEM_DATA"));
				dataMap.put("REMARK", tempMap.get("REMARK"));
				dataMap.put("CREATED_BY", admin.getPersonId());
				
				paForLeftImportList.add(dataMap);
			}
			paramMap.put("paForLeftImportList", paForLeftImportList);
			//1.将申请信息保存至主表pa_leftmen_apply
			//2.将申请信息由导入临时表pa_leftmen_adding_temp转移至正式表表pa_leftmen_adding
			//3.删除导入临时表pa_leftmen_adding_temp
			//4.插入决裁信息ess_affirm//max---为申请批次号
			paramMap.put("PERSON_ID", admin.getPersonId());
			checkFlag = this.paForLeftMenDao.savePaForLetMen(paramMap);
		}
		return checkFlag;
	}
	
	/**
	 * 验证导入的离职人员工资补发数据(check pai qian di data info)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int checkImportPaForEmpLeftData(List paForLeftDataList,LinkedHashMap paramMap,HttpServletRequest request)throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CREATED_BY", admin.getPersonId());
		int result = 0;
		for (int k = 0; k <paForLeftDataList.size(); k++) {
			int checkFlag = 0;
			LinkedHashMap dataMap = new LinkedHashMap();
			dataMap = (LinkedHashMap)paForLeftDataList.get(k);
			
			List dataExistList = new ArrayList();
			int dataExistFlag = 0;
			List cpnyList = new ArrayList();
			int cpnyFlag = 0;
			List empList = new ArrayList();
			int empFlag = 0;
			List dataList = new ArrayList();
			List itemList = new ArrayList();
			int itemFlag = 0;
			List paMonthList = new ArrayList();
			int paMonthFlag = 0;
			
			String errorContent = "";
			//1.验证法人是否存在，是否正确
			dataMap.put("CHECK_TYPE", "CPNY");
			cpnyList = this.paForLeftMenDao.getPaForLeftCodeNoCheckList(dataMap);
			cpnyFlag = cpnyList!=null?cpnyList.size():0;
			if(cpnyFlag <= 0) {
				errorContent = "[该法人不存在!]";
				dataMap.put("ERROR_CONTENT", errorContent);
				checkFlag = this.paForLeftMenDao.updatePaForLetMenDataCheckResult(dataMap);
				result = result + 1;
			}
			
			//2.验证工号是否存在，是否正确
			dataMap.put("CHECK_TYPE", "EMP");
			empList = this.paForLeftMenDao.getPaForLeftCodeNoCheckList(dataMap);
			empFlag = empList!=null?empList.size():0;
			if(empFlag <= 0) {
				errorContent = "[该工号不存在!]";
				dataMap.put("ERROR_CONTENT", errorContent);
				checkFlag = this.paForLeftMenDao.updatePaForLetMenDataCheckResult(dataMap);
				result = result + 1;
			}
			
			//3.验证是否是离职人员
			dataMap.put("CHECK_TYPE", "EMP");
			dataMap.put("CHECK_CONDITION", "DATE_LEFT");
			empList = this.paForLeftMenDao.getPaForLeftCodeNoCheckList(dataMap);
			empFlag = empList!=null?empList.size():0;
			if(empFlag <= 0) {
				errorContent = "[非离职人员，不允许申请薪资补发!]";
				dataMap.put("ERROR_CONTENT", errorContent);
				checkFlag = this.paForLeftMenDao.updatePaForLetMenDataCheckResult(dataMap);
				result = result + 1;
			}
			
			//4.验证发放月份是否存在，是否正确
			dataMap.put("CHECK_TYPE", "MONTH");
			dataList = this.paForLeftMenDao.getPaForLeftCodeNoCheckList(dataMap);
			LinkedHashMap tempMap = (LinkedHashMap)dataList.get(0);
			String pa_month = tempMap.get("PA_MONTH")!=null?tempMap.get("PA_MONTH").toString():"";
			if(pa_month==null || "".equals(pa_month) || pa_month.length()!=6) {
				errorContent = "[该发放月份不准确!]";
				dataMap.put("ERROR_CONTENT", errorContent);
				checkFlag = this.paForLeftMenDao.updatePaForLetMenDataCheckResult(dataMap);
				result = result + 1;
			}
			
			//5.验证补发月份是否存在，是否正确
			String pa_month_for = tempMap.get("PA_MONTH_FOR")!=null?tempMap.get("PA_MONTH_FOR").toString():"";
			if(pa_month_for==null || "".equals(pa_month_for) || pa_month_for.length()!=6) {
				errorContent = "[该薪资补发月份不准确!]";
				dataMap.put("ERROR_CONTENT", errorContent);
				checkFlag = this.paForLeftMenDao.updatePaForLetMenDataCheckResult(dataMap);
				result = result + 1;
			}
			
			//6.验证项目类型是否一致，是否正确
			String item_type = tempMap.get("ITEM_TYPE")!=null?tempMap.get("ITEM_TYPE").toString():"";
			if(item_type==null || "".equals(item_type) || (!"薪资".equals(item_type) && !"保险".equals(item_type))){
				errorContent = "[项目类型有误，只能是薪资或保险!]";
				dataMap.put("ERROR_CONTENT", errorContent);
				checkFlag = this.paForLeftMenDao.updatePaForLetMenDataCheckResult(dataMap);
				result = result + 1;
			}
			String table_name = "PA_SUMMARY_"+admin.getCpnyId();
			//7.验证详细项目是否存在，是否正确
			dataMap.put("CHECK_TYPE", "ITEM");
			dataMap.put("TABLE_NAME", table_name);
//			itemList = this.paForLeftMenDao.getPaForLeftCodeNoCheckList(dataMap);
//			itemFlag = itemList!=null?itemList.size():0;
//			if(itemFlag <= 0) {
//				errorContent = "[该薪资、保险项目不存在!]";
//				dataMap.put("ERROR_CONTENT", errorContent);
//				checkFlag = this.paForLeftMenDao.updatePaForLetMenDataCheckResult(dataMap);
//				result = result + 1;
//			}
			
			//8.验证项目类型与详细项目是否一致，是否正确
			dataMap.put("CHECK_TYPE", "ITEM_ALON");
//			paMonthList = this.paForLeftMenDao.getPaForLeftCodeNoCheckList(dataMap);
//			paMonthFlag = paMonthList!=null?paMonthList.size():0;
//			if(paMonthFlag <= 0){
//				errorContent = "[项目类型与详细项目不一致!]";
//				dataMap.put("ERROR_CONTENT", errorContent);
//				checkFlag = this.paForLeftMenDao.updatePaForLetMenDataCheckResult(dataMap);
//				result = result + 1;
//			}
			
			//9.验证该离职人员工资补发信息在正式表中是否存在
			dataMap.put("CHECK_TYPE", "EXIST_NORMAL");
			dataExistList = this.paForLeftMenDao.getPaForLeftCodeNoCheckList(dataMap);
			dataExistFlag = dataExistList!=null?dataExistList.size():0;
			if(dataExistFlag >= 1) {
				errorContent = "[该离职人员工资补发信息已存在，无法导入!]";
				dataMap.put("ERROR_CONTENT", errorContent);
				checkFlag = this.paForLeftMenDao.updatePaForLetMenDataCheckResult(dataMap);
				result = result + 1;
			}
			
			//10.验证该离职人员工资补发信息在临时表中是否有重复
			dataMap.put("CHECK_TYPE", "EXIST_TEMP");
			dataExistList = this.paForLeftMenDao.getPaForLeftCodeNoCheckList(dataMap);
			dataExistFlag = dataExistList!=null?dataExistList.size():0;
			if(dataExistFlag >= 1) {
				errorContent = "[导入的离职人员工资补发信息重复!]";
				dataMap.put("ERROR_CONTENT", errorContent);
				checkFlag = this.paForLeftMenDao.updatePaForLetMenDataCheckResult(dataMap);
				result = result + 1;
			}
			
			//11.验证是否有决裁者
			List affirmList = (ArrayList)paramMap.get("affirmList");
			int dataFlag = affirmList!=null?affirmList.size():0;
			if(dataFlag <= 0) {
				errorContent = "[没有决裁者，请设置决裁者!]";
				dataMap.put("ERROR_CONTENT", errorContent);
				checkFlag = this.paForLeftMenDao.updatePaForLetMenDataCheckResult(dataMap);
				result = result + 1;
			}
			
			result = result + checkFlag;
		}
		return result;
	}
	
	/**
	 * 封装要插入的加班申请数据
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private List preAddAffirmList(LinkedHashMap paramMap,HttpServletRequest request) throws Exception {
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		String cpnyId=admin.getCpnyId();
		//String createBy=admin.getAdminID();
		//Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		List<LinkedHashMap> tempAffirmList = new ArrayList<LinkedHashMap>();
		List<LinkedHashMap> returnAffirmList = new ArrayList<LinkedHashMap>();
		List addAffirmList = new ArrayList();
		List personList=new ArrayList();
		Enumeration e = request.getParameterNames() ;
		//获取页面添加的决裁者的决裁等级（页面等级）
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement() ;
			if(key.indexOf("dwz.person.personId")>-1){
				String dwzName="dwz.person.personId";
				if(key.equals("dwz.person.personId")){
					personList.add(0);	
				}else{
					personList.add(Integer.parseInt(key.substring(dwzName.length())));
				}
			}
		}
		//对页面获取的决裁者信息进行排序（按照页面决裁等级排序）
		Collections.sort(personList);
		for(int i=0;i<personList.size();i++){
			LinkedHashMap affirmMap = new LinkedHashMap() ;
			String keyName="";
			String affirmLevel = personList.get(i).toString(); 
			if(affirmLevel.equals(0)){
				keyName="dwz.person.personId";
			}else{
				keyName="dwz.person.personId"+affirmLevel;
			}
			//LinkedHashMap personMap = (LinkedHashMap) this.infoApplyDao.getPersonInfoByPersonId(paramMap);
			//获取添加的决裁者的person_id
			String personId=paramMap.get(keyName).toString();
			affirmMap.put("AFFIRMOR_ID", personId);
			affirmMap.put("AFFIRM_LEVEL", affirmLevel);
			affirmMap.put("AFFIRM_COM_TYPE", "ADD_COM");
			
			addAffirmList.add(affirmMap);
		}
		//从数据库中根据决裁设置获取决裁者信息
		//paramMap.put("APPLY_TYPE_NO", "224");//费用申请
		String applyTypeNo = (String) paramMap.get("APPLY_TYPE_NO");
		String personId = admin.getPersonId();
		//String applyLength = (String)paramMap.get("otLength");
		//String applyTypeCode= (String) paramMap.get("APPLY_TYPE_CODE");
		//从数据库里查询系统设置的决裁者
		List affirmerList = this.infoApplySer.getAffirmorListByString(applyTypeNo, personId, "", "", admin.getLanguage());
		for(int m=0;m<affirmerList.size();m++){
			((LinkedHashMap)affirmerList.get(m)).put("AFFIRM_COM_TYPE", "DB_COM");
		}
		if(addAffirmList!=null && addAffirmList.size()>0){
			for(int i=0;i<addAffirmList.size();i++){
				LinkedHashMap addMap = (LinkedHashMap)addAffirmList.get(i) ;
				int addDeptLevel = Integer.parseInt(addMap.get("AFFIRM_LEVEL").toString());
				for(int j=0;j<affirmerList.size();j++){
					LinkedHashMap dbMap = (LinkedHashMap)affirmerList.get(j) ;
					int dbDeptLevel = Integer.parseInt(dbMap.get("AFFIRM_LEVEL").toString());
					if(dbDeptLevel >= addDeptLevel){
						((LinkedHashMap)affirmerList.get(j)).put("AFFIRM_LEVEL", dbDeptLevel+1);
					}
				}
				tempAffirmList = affirmerList;
				tempAffirmList.add(addMap);
			}
		}else{
			tempAffirmList = affirmerList;
		}
		int maxAffirmLevel = 0;
		for(int i=0;i<tempAffirmList.size();i++){
			LinkedHashMap affirmMap = (LinkedHashMap)tempAffirmList.get(i);
			int affirmLevel = Integer.parseInt(affirmMap.get("AFFIRM_LEVEL").toString());
			if(affirmLevel > maxAffirmLevel){
				maxAffirmLevel = affirmLevel;
			}
		}
		for(int m=0;m<maxAffirmLevel;m++){
			for(int n=0;n<tempAffirmList.size();n++){
				LinkedHashMap dataMap = (LinkedHashMap)tempAffirmList.get(n);
				int affirmLevel = Integer.parseInt(dataMap.get("AFFIRM_LEVEL").toString());
				if(affirmLevel==m+1){
					returnAffirmList.add(dataMap);
					break;
				}
			}
		}
		return returnAffirmList;
	}
	
	/**
	 * 通过request请求封装查询条件(get conditions from request)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private LinkedHashMap getLinkedMapByRequest(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("UPDATED_BY", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		if(paramMap.get("PERSON_ID")==null || "".equals(paramMap.get("PERSON_ID"))){
			paramMap.put("PERSON_ID", admin.getPersonId());
		}
		return paramMap;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int delPaForLeftMenInfo(HttpServletRequest request){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		return this.paForLeftMenDao.deletePaForLetMenDetail(paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public int updatePaForLeftMenInfo(HttpServletRequest request){
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getAdminID());
		try {
			//保存临促信息
			this.paTempSalesDAO.updatePaTempSalesEmp(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 通过request请求封装查询条件(get search conditions for request)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	private LinkedHashMap getMapByRequestForSearch(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("PA_SUPERVISIOR_INFO", admin.getPersonId());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return paramMap;
	}
	
	/**
	 * 离职人员工资补发--决裁信息
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPaForLeftMenAffirmList(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		List returnList = new ArrayList();
		if (UiUtil.getPageNum(request) > 0){
			returnList = 
				paForLeftMenDao.getPaForLeftMenAffirmList(paramMap ,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}else{
			returnList = paForLeftMenDao.getPaForLeftMenAffirmList(paramMap) ;
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int getPaForLeftMenAffirmListCnt(HttpServletRequest request) {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", Messages.getLanguage(request));
		
		return paForLeftMenDao.getPaForLeftMenAffirmListCnt(paramMap) ;
	}
	
	/**
	 * 根据离职员工薪资补发申请NO决裁信息查询(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaForLeftAffirmorByApplyNoList(HttpServletRequest request) throws Exception { 
		List returnList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = null;
		if(admin == null){
			paramMap = ObjectBindUtil.getRequestParamDataNoSession(request) ;
		}else {
			paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
			paramMap.put("LANGUAGE",  admin.getLanguage());
		}
		returnList = paForLeftMenDao.getPaForLeftAffirmorByApplyNoList(paramMap);
		
		return returnList;
	}
	
	/**
	 * 离职员工薪资补发申请check信息查询(search ot info list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaForLeftCheckorByApplyNoList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List returnList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = null;
		if(admin == null){
			paramMap = ObjectBindUtil.getRequestParamDataNoSession(request) ;
		}else {
			paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
			paramMap.put("LANGUAGE",  admin.getLanguage());
			paramMap.put("ADMIN_ID", admin.getPersonId());
		}
		// 页面提交数据
		returnList = paForLeftMenDao.getPaForLeftCheckorByApplyNoList(paramMap);
		
		return returnList;
	}
	
	/**
	 * 通过/否决离职员工薪资补发申请(pass and reject pa info for emp of left apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int approvePaForLeftApply(HttpServletRequest request)throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = null;
		if(admin == null){
			paramMap = ObjectBindUtil.getRequestParamDataNoSession(request) ;
			paramMap.put("interLanguage", paramMap.get("LANGUAGE"));
		}else {
			paramMap = ObjectBindUtil.getRequestParamData(request);
			paramMap.put("LANGUAGE",  admin.getLanguage());
		}
		//将页面添加的决裁者插入到已有的决裁者列表中，注意顺序
		int result = this.addNewAffirmorList(paramMap, request);
		//最新的决裁者插入之后再进行决裁
		if(result==1){
			// 封装加班申请数据并处理
			this.paForLeftMenDao.savePaForLeftApplyAffirm(this.encapsulationApplyAffirmMap(request, false));
		}
		return 1;
	}
	
	/**
	 * 决裁离职人员工资补发申请
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-13
	 * @version V1.0
	 */
	public int approvePaForLeftApply_ep(HttpServletRequest request){
		AdminBean admin = null;
		LinkedHashMap paramMap = new LinkedHashMap();
		if(request.getParameter("personId") == null || "".equals(request.getParameter("personId"))){
			admin = SessionUtil.getLoginUserFromSession(request) ;
			paramMap = ObjectBindUtil.getRequestParamDataNoSession(request);
		}else{
			paramMap = ObjectBindUtil.getRequestParamDataNoSession(request);
		}
		String[] affirmId = request.getParameterValues("AFFIRMOR_ID");
		if(paramMap.get("interLanguage")==null || "".equals(paramMap.get("interLanguage"))){
			paramMap.put("interLanguage", "zh");
		}
		paramMap.put("FLAG", paramMap.get("AFFIRM_FLAG"));
		paramMap.put("UPDATED_BY", admin == null ? request.getParameter("personId") :admin.getAdminID());
		paramMap.put("CREATED_BY", admin == null ? request.getParameter("personId") :admin.getAdminID());
		paramMap.put("PERSON_ID", admin == null ? request.getParameter("personId") : admin.getAdminID());
		paramMap.put("CURRENT_AFFIRM_ID",  "");
		paramMap.put("APPLY_TYPE_NO", APPLY_TYPE_NO);
		paramMap.put("APPLY_TYPE", APPLY_TYPE_NO);
		paramMap.put("APPLY_NO",request.getParameter("APPLY_NO"));
		paramMap.put("BATCH_NO",request.getParameter("APPLY_NO"));
		paramMap.put("ADMIN_ID", paramMap.get("PERSON_ID"));
		int dept_level = Integer.parseInt(request.getParameter("dept_level").toString());
		paramMap.put("dept_level", dept_level + 1);
		try {
			//删除旧的决裁者
			editionAffirmDao.deleteDimissionAffirmor(paramMap);
			//添加决裁者
			for(int i=1; i<affirmId.length; i++){
				paramMap.put("AFFIRMOR_ID", affirmId[i]);
				paramMap.put("AFFIRM_LEVEL", i+dept_level);
				editionAffirmDao.insertDimissionAffirmor(paramMap);
			}
			if(request.getParameter("personId")!= null && !"".equals(request.getParameter("personId"))){
				this.addNewAffirmorList(paramMap, request);
			}
			//决裁
			this.editionAffirmDao.updateDimissionEssAffirm(paramMap);
			if(!"2".equals(paramMap.get("AFFIRM_FLAG"))){//否决直接修改标志位,通过继续下面操作
				//获取决裁者列表
				paramMap.put("ACTIVITY", "0");
				List<LinkedHashMap> affirmList = this.editionAffirmDao.getDimissionAffirmor(paramMap);
				if (affirmList != null && affirmList.size() > 0) {
					for (LinkedHashMap parmers : affirmList) {
						if("0".equals(parmers.get("AFFIRM_FLAG").toString())){
							paramMap.put("CURRENT_AFFIRM_ID",  parmers.get("AFFIRMOR_ID"));
							paramMap.put("AFFIRM_LEVEL",  parmers.get("AFFIRM_LEVEL"));
							this.paForLeftMenDao.affirmPaForLeftInfoAffirm(paramMap);
							break;
						}
						paramMap.put("AFFIRM_LEVEL",  affirmList.size());
					}
				}
				List listAffirm = this.editionAffirmDao.getDimissionAffirmor(paramMap);
				paramMap.put("AFFIRM_FLAG1","1");
				List listAffirmor = this.editionAffirmDao.getDimissionAffirmor(paramMap);
				if(listAffirm.size()>0 && listAffirmor.size()>0 && listAffirm.size()==listAffirmor.size()){
					paramMap.put("FLAG", "1");
					//这就是通过后进行的操作
					this.paForLeftMenDao.savePaForLeftApplyAffirm_ep(paramMap);
				}else{
					paramMap.put("FLAG", "0");
				}
			}
			//check信息修改为已check
			sendToLGEPCheckBatch(paramMap);
			this.editionAffirmDao.updateCheckFlagByDimissionEssAffirmNo(paramMap);
			this.paForLeftMenDao.updateApplyPaForLeftByApplyNo(paramMap);
			//审批发送LGEP
			paramMap.put("AFFIRM_FLAG", paramMap.get("FLAG"));
			sendToLGEP(paramMap);

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	
	/**
	 * 审批后发送LGEP
	 * @param eventId/**
	 * check：
	 *  APPLY_NO:申请的seq
	 *  APPLY_TYPE：申请类型（数字代码）
	 *	AFFIRM_LEVEL：ess_check_no
	 *	AFFIRM_FLAG：传1，表示通过
	 *	AFFIRM_EMPID：check人person_id
	 *	AAI_URL:check结果查看页面url
	 */
	private void sendToLGEPCheckBatch(LinkedHashMap paramMap){
		List checkList = this.paTempSalesDAO.getCheckListToLgep(paramMap);
		if(checkList != null && checkList.size() > 0){
			for(int i=0;i<checkList.size();i++){
				LinkedHashMap lgepMap = (LinkedHashMap)checkList.get(i);
				lgepMap.put("APPLY_TYPE", APPLY_TYPE_NO);
				lgepMap.put("APPLY_NO", paramMap.get("BATCH_NO"));
				lgepMap.put("BATCH_NO", paramMap.get("BATCH_NO"));
				lgepMap.put("AFFIRM_FLAG", '1');
				lgepMap.put("AFFIRM_LEVEL", lgepMap.get("ESS_CHECK_NO"));
				lgepMap.put("AFFIRM_EMPID", lgepMap.get("CHECKOR_ID"));
				lgepMap.put("AAI_URL", "http://{serverIp}/LGEP/affirm/checkLeftMenApplyInfo?LGEP=LGEP&LANGUAGE=zh&personId=123&BATCH_NO=" + paramMap.get("APPLY_NO") + "&affirmOrCheck=2");
				this.affirmInfoToLGEPSer.check(lgepMap);
			}
		}
	}
	
	/**
	 * 审批后发送LGEP
	 * @param eventId
	 */
	private void sendToLGEP(LinkedHashMap paramMap){
			LinkedHashMap lgepMap = new LinkedHashMap();
			lgepMap.put("APPLY_TYPE", APPLY_TYPE_NO);
			lgepMap.put("APPLY_NO", paramMap.get("BATCH_NO"));
			lgepMap.put("BATCH_NO", paramMap.get("BATCH_NO"));
			if("0".equals(paramMap.get("FLAG").toString())){
				lgepMap.put("AFFIRM_LEVEL", paramMap.get("AFFIRM_LEVEL"));
				lgepMap.put("AFFIRM_FLAG", 1);
			}else{//决裁完成\
				lgepMap.put("FINISH", "FINISH");
				lgepMap.put("AFFIRM_FLAG", paramMap.get("AFFIRM_FLAG"));
				lgepMap.put("AFFIRM_LEVEL", Integer.parseInt(paramMap.get("dept_level").toString())-1);
			}
			lgepMap.put("AFFIRM_EMPID", paramMap.get("PERSON_ID"));
			lgepMap.put("AAI_URL", "http://{serverIp}/LGEP/affirm/viewFullPaForLeftApplyAffirmorList?LGEP=LGEP&LANGUAGE=zh&personId=123&BATCH_NO=" + paramMap.get("BATCH_NO"));
			lgepMap.put("AAF_URL", "http://{serverIp}/LGEP/affirm/viewFullPaForLeftApplyAffirmorList?LGEP=LGEP&LANGUAGE=zh&personId=123&BATCH_NO=" + paramMap.get("BATCH_NO"));
			lgepMap.put("ABY_URL", "http://{serverIp}/LGEP/affirm/viewFullPaForLeftApplyAffirmorList?LGEP=LGEP&LANGUAGE=zh&personId=" + paramMap.get("CURRENT_AFFIRM_ID") + "&BATCH_NO=" + paramMap.get("BATCH_NO"));
			lgepMap.put("CURRENT_AFFIRM_ID", paramMap.get("CURRENT_AFFIRM_ID"));
			lgepMap.put("CREATED_BY", paramMap.get("CURRENT_AFFIRM_ID"));
			this.affirmInfoToLGEPSer.affirm(lgepMap);
	}
	
	/**
	 * 封装要插入的加班申请数据
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private int addNewAffirmorList(LinkedHashMap paramMap,HttpServletRequest request) throws Exception {
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		int result = 1;
		List addAffirmList = new ArrayList();
		List personList=new ArrayList();
		Enumeration e = request.getParameterNames() ;
		//获取页面添加的决裁者的决裁等级（页面等级）
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement() ;
			if(key.indexOf("dwz.person.personId")>-1){
				String dwzName="dwz.person.personId";
				if(key.equals("dwz.person.personId")){
					personList.add(0);	
				}else{
					personList.add(Integer.parseInt(key.substring(dwzName.length())));
				}
			}
		}
		//对页面获取的决裁者信息进行排序（按照页面决裁等级排序）
		Collections.sort(personList);
		for(int i=0;i<personList.size();i++){
			LinkedHashMap affirmMap = new LinkedHashMap() ;
			String keyName="";
			String affirmLevel = personList.get(i).toString(); 
			if(affirmLevel.equals(0)){
				keyName="dwz.person.personId";
			}else{
				keyName="dwz.person.personId"+affirmLevel;
			}
			//LinkedHashMap personMap = (LinkedHashMap) this.infoApplyDao.getPersonInfoByPersonId(paramMap);
			//获取添加的决裁者的person_id
			String personId=paramMap.get(keyName).toString();
			affirmMap.put("AFFIRMOR_ID", personId);
			affirmMap.put("APPLY_NO", paramMap.get("APPLY_NO").toString());
			affirmMap.put("APPLY_TYPE", paramMap.get("APPLY_TYPE").toString());
			affirmMap.put("AFFIRM_LEVEL", affirmLevel);
			affirmMap.put("AFFIRM_COM_TYPE", "ADD_COM");
			affirmMap.put("CREATED_BY", personId);
			affirmMap.put("UPDATED_BY", personId);
			
			addAffirmList.add(affirmMap);
		}
		LinkedHashMap addAffirmorMap = new LinkedHashMap();
		int affirmorCnt = 0;
		for(int j=0;j<addAffirmList.size();j++){
			addAffirmorMap = (LinkedHashMap)addAffirmList.get(j);
			affirmorCnt = this.paForLeftMenDao.getAffirmorCntByApplyNo(addAffirmorMap);
			if(affirmorCnt==0){
				this.paForLeftMenDao.addNewApplyAffirmor(addAffirmorMap);
			}
		}
		
		return result;
	}
	
	/**
	 * 封装发令信息成map(encapsulation transaction from request to map)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private LinkedHashMap encapsulationApplyAffirmMap(
			HttpServletRequest request, boolean ifEntryTrans) {
		LinkedHashMap curAffirmMap = new LinkedHashMap();
		try {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			// 页面提交数据
			LinkedHashMap map =null;
			if(admin == null){
				map= ObjectBindUtil.getRequestParamDataNoSession(request);
				map.put("interLanguage", map.get("LANGUAGE"));
				map.put("ADMIN_ID", map.get("PERSON_ID"));
			}else{
				map = ObjectBindUtil.getRequestParamData(request);
			}
			String AFFIRMOR_ID = "";
			if(admin == null){
				String[] affirmId = request.getParameterValues("AFFIRMOR_ID");
				AFFIRMOR_ID = affirmId[0];
				map.put("CPNY_ID", request.getParameterValues("CPNY_ID"));
			}else{
				AFFIRMOR_ID = admin.getAdminID();
				map.put("CPNY_ID", admin.getCpnyId());
			}
			map.put("UPDATED_BY", AFFIRMOR_ID);
			map.put("CREATED_BY", AFFIRMOR_ID);
			
			// 当前决裁者
			map.put("CURRENT_AFFIRM_ID", AFFIRMOR_ID);
			String affirm_no = map.get("ESS_AFFIRM_NO").toString();
			curAffirmMap.put("ESS_AFFIRM_NO", map.get("ESS_AFFIRM_NO"));
			curAffirmMap = (LinkedHashMap) this.paForLeftMenDao.getEssAffirmInfoByAffirmNo(curAffirmMap);
			// 决裁级别
			String currentAffirmLevel = curAffirmMap.get("AFFIRM_LEVEL") != null ? curAffirmMap.get("AFFIRM_LEVEL").toString(): "0";
			// 决裁通过/否决标识
			String affirmFlag = map.get("AFFIRM_FLAG") != null ? map.get("AFFIRM_FLAG").toString() : "0";
			map.put("APPLY_NO", curAffirmMap.get("APPLY_NO"));
			curAffirmMap.put("APPLY_TYPE", map.get("APPLY_TYPE"));
			// 获得当前申请中最高的决裁等级(get Max Affirm Flag By ExpInsideNo)
			int maxAffirmLevel = this.paForLeftMenDao.getMaxAffirmLevelByApplyNo(curAffirmMap);
			// 如果不是决裁流程最后一步且通过的的时候,需要做个标识并修改当前发令中的CURRENT_AFFIRM_ID字段为下一步中的决裁者
			if (Integer.parseInt(currentAffirmLevel) < maxAffirmLevel) {
				if ("1".equals(affirmFlag)) {
					int nextAffirmLevel = Integer.parseInt(currentAffirmLevel) + 1;
					map.put("NEXT_AFFIRM_LEVEL", nextAffirmLevel);
					LinkedHashMap nextHrAffirmMap = (LinkedHashMap) this.paForLeftMenDao.getEssAffirmInfoByApplyNoAndLevel(map);
					String nextAffirmerId = nextHrAffirmMap.get("AFFIRMOR_ID") != null ? nextHrAffirmMap.get("AFFIRMOR_ID").toString(): AFFIRMOR_ID;
					map.put("NEXT_AFFIRM_ID", nextAffirmerId);
					map.put("ACTIVITY", "0");
					//用来标志申请信息正在：决裁中，为：4
					map.put("AFFIRM_READ_FLAG", "4");
				} else {
					map.put("NEXT_AFFIRM_ID", "");
					map.put("ACTIVITY", "2");
					//用来标志申请信息正在：已否决，为：2
					map.put("AFFIRM_READ_FLAG", "2");
				}
			}
			// 如果是决裁流程的最后一步且为通过时
			if (Integer.parseInt(currentAffirmLevel) == maxAffirmLevel) {
				if ("1".equals(affirmFlag)) {
					// 如果不需要人事确认,则最后一步决裁通过后直接生效
					map.put("FLAG", "1");
					map.put("ACTIVITY", "1");
					//用来标志申请信息正在：已通过，为：1
					map.put("AFFIRM_READ_FLAG", "1");
				} else {
					map.put("ACTIVITY", "2");
					//用来标志申请信息正在：已否决，为：2
					map.put("AFFIRM_READ_FLAG", "2");
				}
				map.put("NEXT_AFFIRM_ID", "");
			}
			map.put("currentAffirmLevel", currentAffirmLevel);
			//map.put("CPNY", admin.getCpnyId());
			map.put("CREATED_BY", AFFIRMOR_ID);
			map.put("UPDATED_BY", AFFIRMOR_ID);
			map.put("ADMIN_ID", AFFIRMOR_ID);
			map.put("navTabId", request.getParameter("navTabId"));
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 删除临时表中所有导入的离职员工薪资补发信息申请(delete pa info for emp of left apply information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int cancelPaForLeftApplyImport(HttpServletRequest request)throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("UPLOAD_BY", admin.getPersonId());
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		try {
			// 批量封装加班申请数据并处理
			this.paForLeftMenDao.cancelPaForLeftApplyImport(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 批量通过/否决离职员工薪资补发申请(batch pass and reject pa info for emp of left apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int approvePaForLeftApplyInBatch(HttpServletRequest request)
			throws Exception {
		try {
			// 批量封装加班申请数据并处理
			this.paForLeftMenDao.savePaForLeftApplyAffirmInBatch(this.encapsulationApplyAffirmListForBatch(request));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 批量封装信息申请信息成List(encapsulation information apply from request to List for
	 * batch)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List encapsulationApplyAffirmListForBatch(HttpServletRequest request) {
		List list = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap linkedMap = new LinkedHashMap();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			String affirmFlag = paramMap.get("AFFIRM_FLAG") != null ? paramMap.get("AFFIRM_FLAG").toString() : "";
			//属于离职员工薪资部分申请
			String affirmType = paramMap.get("AFFIRM_TYPE") != null ? paramMap.get("AFFIRM_TYPE").toString() : "";
			String[] paramData = request.getParameterValues("c1");
			for (int i = 0; i < paramData.length; i++) {
				LinkedHashMap map = new LinkedHashMap();
				map.put("UPDATED_BY", admin.getPersonId());
				map.put("CREATED_BY", admin.getPersonId());
				map.put("ADMIN_ID", admin.getPersonId());
				map.put("CPNY_ID", admin.getCpnyId());
				map.put("CURRENT_AFFIRM_ID", admin.getPersonId());

				map.put("ESS_AFFIRM_NO", paramData[i]);
				linkedMap.put("ESS_AFFIRM_NO", map.get("ESS_AFFIRM_NO"));
				linkedMap = (LinkedHashMap) this.paForLeftMenDao.getEssAffirmInfoByAffirmNo(linkedMap);
				map.put("AFFIRM_FLAG", affirmFlag);
				// 决裁级别
				String currentAffirmLevel = linkedMap.get("AFFIRM_LEVEL") != null ? linkedMap.get("AFFIRM_LEVEL").toString(): "0";
				map.put("APPLY_NO", linkedMap.get("APPLY_NO"));
				map.put("APPLY_TYPE", linkedMap.get("APPLY_TYPE"));
				// 获得当前申请中最高的决裁等级(get Max Affirm Flag By ExpInsideNo)
				int maxAffirmLevel = this.paForLeftMenDao.getMaxAffirmLevelByApplyNo(map);
				// 如果不是决裁流程最后一步且通过的的时候,需要做个标识并修改当前发令中的CURRENT_AFFIRM_ID字段为下一步中的决裁者
				if (Integer.parseInt(currentAffirmLevel) < maxAffirmLevel) {
					if ("1".equals(affirmFlag)) {
						int nextAffirmLevel = Integer.parseInt(currentAffirmLevel) + 1;
						map.put("NEXT_AFFIRM_LEVEL", nextAffirmLevel);
						LinkedHashMap nextHrAffirmMap = (LinkedHashMap) this.paForLeftMenDao.getEssAffirmInfoByApplyNoAndLevel(map);
						String nextAffirmerId=nextHrAffirmMap.get("AFFIRMOR_ID")!=null?nextHrAffirmMap.get("AFFIRMOR_ID").toString():admin.getPersonId();
						map.put("NEXT_AFFIRM_ID", nextAffirmerId);
						map.put("ACTIVITY", "0");
						//用来标志申请信息正在：决裁中，为：4
						map.put("AFFIRM_READ_FLAG", "4");
					} else {
						map.put("NEXT_AFFIRM_ID", "");
						map.put("ACTIVITY", "2");
						//用来标志申请信息正在：已否决，为：2
						map.put("AFFIRM_READ_FLAG", "2");
					}
				}
				// 查看是否需要人事确认.(4160为对应的CODE)
				//map.put("ESS_PARAM_NO", "4160");
				//Object obj = this.affirmApplyDao.getParamValueByCpnyIdAndParamNo(map);
				//String confirmFlag = obj != null ? obj.toString() : "0";
				// 如果是决裁流程的最后一步且为通过时
				if (currentAffirmLevel.equals(String.valueOf(maxAffirmLevel))) {
					if ("1".equals(affirmFlag)) {
						map.put("FLAG", "1");
						map.put("ACTIVITY", "1");
						//用来标志申请信息正在：已通过，为：1
						map.put("AFFIRM_READ_FLAG", "1");
					} else {
						map.put("ACTIVITY", "2");
						//用来标志申请信息正在：已否决，为：2
						map.put("AFFIRM_READ_FLAG", "2");
					}
					map.put("NEXT_AFFIRM_ID", "");
				}
				map.put("CPNY", admin.getCpnyId());
				map.put("CREATED_BY", admin.getAdminID());
				map.put("UPDATED_BY", admin.getAdminID());
				map.put("ADMIN_ID", admin.getAdminID());
				String navTabId=request.getParameter("navTabId")!=null?request.getParameter("navTabId"):"";
				map.put("navTabId", navTabId);
				list.add(map);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 批量删除离职员工薪资补发申请(batch delete pa info for emp of left apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int delPaForLeftApplyInBatch(HttpServletRequest request)
			throws Exception {
		try {
			this.paForLeftMenDao.delPaForLeftApplyInBatch(this.encapsulationApplyNoListForBatch(request));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 批量封装信息申请信息成List(encapsulation information apply from request to List for
	 * batch)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List encapsulationApplyNoListForBatch(HttpServletRequest request) {
		List list = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		try {
			String[] paramData = request.getParameterValues("c1");
			for (int i = 0; i < paramData.length; i++) {
				LinkedHashMap map = new LinkedHashMap();
				map.put("UPDATED_BY", admin.getPersonId());
				map.put("APPLY_NO", paramData[i]);
				map.put("APPLY_TYPE", "224");
				
				list.add(map);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 删除未审核离职员工薪资补发信息申请(delete pa info for emp of left apply information)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean delPaForLeftApply(HttpServletRequest request)throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		return this.paForLeftMenDao.delPaForLeftApply(paramMap);
	}
	
	/**
	 * 添加checkor(add checkor)
	 * 
	 * @param request
	 * @return
	 * @throws Exception 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int addApplyCheckList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("CREATED_BY", admin.getAdminID());
		paramMap.put("UPDATED_BY", admin.getAdminID());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		
		String essAffirmNo = request.getParameter("ESS_AFFIRM_NO")!=null?request.getParameter("ESS_AFFIRM_NO").toString():"";
		String personId = request.getParameter("dwz.person.personId")!=null?
				request.getParameter("dwz.person.personId").toString():admin.getPersonId();
		paramMap.put("ESS_AFFIRM_NO", essAffirmNo);
		paramMap.put("PERSON_ID", personId);
		//查看此checkor者是否已经存在
		LinkedHashMap linkedMap = new LinkedHashMap();
		linkedMap.put("ESS_AFFIRM_NO", essAffirmNo);
		linkedMap.put("CHECKOR_ID", personId);
		
		//查看此决裁人是否还有当前checkor人了
		linkedMap = (LinkedHashMap) this.paForLeftMenDao.getEssAffirmInfoByAffirmNo(linkedMap);
		String currentCheckFlag = linkedMap.get("CURRENT_CHECKOR_ID")!=null?linkedMap.get("CURRENT_CHECKOR_ID").toString():"";
		int cnt = 0;
		linkedMap.put("ESS_AFFIRM_NO", essAffirmNo);
		linkedMap.put("CHECKOR_ID", personId);
		cnt = this.paForLeftMenDao.getEssCheckCntByCheckorId(linkedMap);
		//如果此人之前没有加入为此条加班申请的checkor人，插入
		//在这个map里面添加申请类型和URL对应值
		//Map<String, String> ipMap = new HashMap<String, String>();
		//ipMap.put("paForLeft", "viewPaForLeftCheck");//离职员工薪资补发
		
		int checkno = this.affirmApplyDao.selectCheckNo();
		paramMap.put("smallpage", "smallpage");
		paramMap.put("ESS_CHECK_NO", checkno);
		//判断申请类型
		String applyType = "224";//离职员工薪资补发申请
		if(cnt==0){
			//插入新的checkor
			paramMap.put("CHECKOR_ID", paramMap.get("PERSON_ID")!=null?paramMap.get("PERSON_ID").toString():admin.getPersonId());
			int flag = this.affirmApplyDao.addApplyCheckListNew(paramMap);
			//其他人要用了这个方法下面有个参数PAGE_FLAG这个需从你的审批页面传过来，其他参数原来应该都是已经有的,原来是Apply_type下面已经改成了PAGE_FLAG
			if(flag == 1){//Check添加成功后，发送小页面
				LinkedHashMap lgepMap = new LinkedHashMap();
					lgepMap.put("APPLY_NO", linkedMap.get("APPLY_NO"));
					lgepMap.put("APPLY_TYPE", paramMap.get("APPLY_TYPE") == null ? applyType : paramMap.get("APPLY_TYPE"));
					lgepMap.put("PRE_AFFIRM_EMPID", linkedMap.get("CHECKOR_ID"));
					lgepMap.put("AFFIRM_LEVEL", checkno);
					lgepMap.put("ABY_URL", "http://{serverIp}/LGEP/affirm/viewPaForLeftCheck"+"?APPLY_TYPE_NO="+paramMap.get("APPLY_TYPE")
							+"&LGEP=LGEP&LANGUAGE=zh&personId="+personId+"&APPLY_NO="+linkedMap.get("APPLY_NO").toString());
				this.affirmInfoToLGEPSer.crateCheck(lgepMap);
			}
			//如果当前没有checkor，还需要更新ess_affirm中的current_checkor_id，如果已有current_checkor_id，则只插入新的checkor即可
			if(currentCheckFlag==null || "".equals(currentCheckFlag)){
				paramMap.put("NEXT_CHECKOR_ID", personId);
				this.affirmApplyDao.updateCurrentCheckor(paramMap);
			}
		//如果此人之前已经加入为此条加班申请的checkor人，不需要插入
		}
		return 1;
	}
	
	/**
	 * 离职员工薪资补发申请check列表(pa for emp of left apply check list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPaForLeftCheckList(HttpServletRequest request)throws Exception {
		List list = new ArrayList();
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("PERSON_ID", paramMap.get("ADMIN_ID"));
		if (UiUtil.getPageNum(request) > 0) {
			list = this.paForLeftMenDao.getPaForLeftCheckList(paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = paForLeftMenDao.getPaForLeftCheckList(paramMap);
		}
		return list;
	}
	
	/**
	 * 离职员工薪资补发申请check总数(pa for emp of left apply check list count)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getPaForLeftCheckListCnt(HttpServletRequest request)
			throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getMapByRequestForSearch(request);
		paramMap.put("PERSON_ID", paramMap.get("ADMIN_ID"));
		
		return this.paForLeftMenDao.getPaForLeftCheckListCnt(paramMap);
	}
}
