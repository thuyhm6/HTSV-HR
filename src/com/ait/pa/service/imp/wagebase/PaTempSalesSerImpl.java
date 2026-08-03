package com.ait.pa.service.imp.wagebase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.affirm.service.AffirmInfoToLGEPSer;
import com.ait.ess.dao.InfoApplyDao;
import com.ait.ess.dao.InfoApplyLeaveDao;
import com.ait.ess.service.InfoApplySer;
import com.ait.pa.dao.tempsale.PaTempSalesDAO;
import com.ait.pa.service.tempsale.PaTempSalesSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.messages.Messages;
import com.ait.web.util.DateUtil;
import com.ait.web.util.MailManager;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

/**
 * 临促工资管理
 * @author Administrator
 */
@Service
@SuppressWarnings({"unchecked","unused"})
public class PaTempSalesSerImpl implements PaTempSalesSer {

	@Autowired
	PaTempSalesDAO paTempSalesDAO;

	@Autowired
	private InfoApplyDao infoApplyDao;
	
	@Autowired
	private AffirmInfoToLGEPSer affirmInfoToLGEPSer;
	
	@Autowired
	private InfoApplySer  infoApplySer ;
	
	@Autowired
	private MailManager mailManager;
	
	@Autowired
	MailManager mailManger;
	
	@Autowired
	private InfoApplyLeaveDao infoApplyLeaveDao;
	
	//临促项目代码
	private static String APPLY_TYPE_NO = "218064";
	
	//临促邀请名称
	private static String APPLY_TYPE_NAME = "临促决裁邀请";

	//临促邀请名称
	private static String APPLY_TITLE_SUFFIX = "决裁邀请";
	
	@Override
	public int getTempSalesCnt(HttpServletRequest request,String accuralFlag) {
		int retrunInt = 0;

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("LANGUAGE", admin.getLanguage());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("ACCRUAL_FLAG", accuralFlag);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}

		retrunInt = paTempSalesDAO.getPaTempSalesCnt(paramMap);

		return retrunInt;
	}

	public int getTempSalesAffirmCnt(HttpServletRequest request,String accuralFlag){
		int retrunInt = 0;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("LANGUAGE", admin.getLanguage());
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("APPLY_TYPE_NO", APPLY_TYPE_NO);
		paramMap.put("ACCRUAL_FLAG", accuralFlag);

		retrunInt = paTempSalesDAO.getTempSalesAffirmCnt(paramMap);

		return retrunInt;
	}
	
	@Override
	public List getTempSalesList(HttpServletRequest request,String accuralFlag) {
		List retrunList = new ArrayList();

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("LANGUAGE", admin.getLanguage());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("ACCRUAL_FLAG", accuralFlag);
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = paTempSalesDAO.getPaTempSalesList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = paTempSalesDAO.getPaTempSalesList(paramMap);
		}
		
		if(retrunList != null && retrunList.size() > 0){
			for(int i=0;i<retrunList.size();i++){
				LinkedHashMap returnMap = (LinkedHashMap)retrunList.get(i);
				returnMap.put("APPLY_NO", returnMap.get("EVENT_ID"));
				returnMap.put("APPLY_TYPE",APPLY_TYPE_NO);
				List fileList = infoApplyLeaveDao.getEssFileList(returnMap);
				returnMap.put("fileList", fileList);
			}
		}
		return retrunList;
	}
	
	/**
	 * 获取需要裁决的临促信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	public List getTempSalesAffirmList(HttpServletRequest request,String accuralFlag) {
		List retrunList = new ArrayList();

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("LANGUAGE", admin.getLanguage());
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("APPLY_TYPE_NO", APPLY_TYPE_NO);
		paramMap.put("ACCRUAL_FLAG", accuralFlag);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = paTempSalesDAO.getTempSalesAffirmList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = paTempSalesDAO.getTempSalesAffirmList(paramMap);
		}
		return retrunList;
	}
	
	@Override
	public int getTempSalesEmpInfoCnt(HttpServletRequest request) {
		int retrunInt = 0;

		// 页面提交数据
		LinkedHashMap paramMap = new LinkedHashMap();
		if(request.getParameter("LANGUAGE") == null || "".equals(request.getParameter("LANGUAGE"))){
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("LANGUAGE", admin.getLanguage());
		}else{
			paramMap.put("LANGUAGE", request.getParameter("LANGUAGE"));
		}
		paramMap.put("EVENT_ID", request.getParameter("EVENT_ID"));
		
		retrunInt = paTempSalesDAO.getPaTempSalesEmpInfoCnt(paramMap);

		return retrunInt;
	}

	@Override
	public List getTempSalesEmpInfoList(HttpServletRequest request) {
		List retrunList = new ArrayList();

		// 页面提交数据
		LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("EVENT_ID", request.getParameter("EVENT_ID"));
		if(request.getParameter("LANGUAGE") == null || "".equals(request.getParameter("LANGUAGE"))){
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("LANGUAGE", admin.getLanguage());
		}else{
			paramMap.put("LANGUAGE", request.getParameter("LANGUAGE"));
		}
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = paTempSalesDAO.getPaTempSalesEmpInfoList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = paTempSalesDAO.getPaTempSalesEmpInfoList(paramMap);
		}
		return retrunList;
	}
	
	/**
	 * 获取临促工资编号
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	@Override
	public String getPaTempSalesSeq(HttpServletRequest request) {
		try {
			return this.paTempSalesDAO.getPaTempSalesSeq();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 添加临促工工资
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	@Override
	public int addPaTempSales(HttpServletRequest request) {
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String[] affirmId = request.getParameterValues("AFFIRMOR_ID");
		paramMap.put("CPNY_ID", admin.getCpnyId()); 
		paramMap.put("PERSON_ID", admin.getAdminID());
		paramMap.put("PERSON_ID_FILE", admin.getPersonId());
		paramMap.put("EVENT_NAME", paramMap.get("START_DATE").toString().replace("-", ""));
		int count = Integer.parseInt(request.getParameter("count"));
		try {
			//已修改，添加的时候不添加人员信息
			/*for (int i = 0; i < count; i++) {//先循环保存临促人员信息
				if(request.getParameter("EMP_NAME" + i) != null){
					paramMap.put("EMP_NAME", request.getParameter("EMP_NAME" + i));
					paramMap.put("IDCARD_NO", request.getParameter("IDCARD_NO"+ i));
					paramMap.put("CELLPHONE", request.getParameter("CELLPHONE" + i));
					paramMap.put("BIRTH_DATE", request.getParameter("BIRTH_DATE" + i));
					paramMap.put("DEPT_NAME", request.getParameter("DEPT_NAME" + i));
					paramMap.put("EVS_GRADE", request.getParameter("EVS_GRADE"+ i));
					paramMap.put("WORK_DAYS", request.getParameter("WORK_DAYS" + i));
					paramMap.put("EVENT_SALARY", request.getParameter("EVENT_SALARY" + i));
					paramMap.put("BLACK_LIST_YN", request.getParameter("BLACK_LIST_YN" + i));
					paramMap.put("BANK_NO", request.getParameter("BANK_NO" + i));
					this.paTempSalesDAO.insertPaTempSalesEmpInfo(paramMap);
				}
			}*/
			paramMap.put("APPLY_TYPE_NO", APPLY_TYPE_NO);
			List affirmList = this.getAffirmorList(request);
			if(affirmList == null || affirmList.size() ==0){
				return -1;//无审批者，请先设置支社长。
			}else{
				//添加决裁者
				for(int i=0; i<affirmList.size(); i++){
					LinkedHashMap affirmMap = (LinkedHashMap)affirmList.get(i);
					paramMap.put("AFFIRMOR_ID", affirmMap.get("AFFIRMOR_ID"));
					paramMap.put("AFFIRM_LEVEL", affirmMap.get("AFFIRM_LEVEL"));
					paTempSalesDAO.insertAffirmor(paramMap);
				}
				LinkedHashMap affirmMap = (LinkedHashMap)affirmList.get(0);
				paramMap.put("AFFIRMOR_ID", affirmMap.get("AFFIRMOR_ID"));
				paramMap.put("AFFIRM_LEVEL", affirmMap.get("AFFIRM_LEVEL"));
				paramMap.put("CURRENT_AFFIRM_ID",affirmMap.get("AFFIRMOR_ID"));
			}
			String FILE_URL =  StringUtil.checkNull(paramMap.get("fileUrl"));
			String FILE_NAME = StringUtil.checkNull(paramMap.get("fileName"));
			paramMap.put("FILE_NAME", FILE_NAME);
			paramMap.put("FILE_URL", FILE_URL);
			paramMap.put("PAY_DATE", paramMap.get("paYear").toString() +  paramMap.get("paMonth").toString());
			this.paTempSalesDAO.insertPaTempSales(paramMap);//保存成功后保存临促信息
			if("1".equals(paramMap.get("FLAG").toString())){//提交状态发送LGEP
				this.sendToLGEP(new String[]{paramMap.get("EVENT_ID").toString()});
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 添加临促工人员信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int addPaTempSalesEmpInfo(HttpServletRequest request){
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getAdminID());
		int count = Integer.parseInt(request.getParameter("count")) + 1;
		try {
			for (int i = 0; i < count; i++) {//先循环保存临促人员信息
				if(request.getParameter("EMP_NAME" + i) != null){
					paramMap.put("EMP_NAME", request.getParameter("EMP_NAME" + i));
					paramMap.put("IDCARD_NO", request.getParameter("IDCARD_NO"+ i));
					paramMap.put("BANK_NO", request.getParameter("BANK_NO"+ i));
					paramMap.put("CELLPHONE", request.getParameter("CELLPHONE" + i));
					paramMap.put("BIRTH_DATE", request.getParameter("BIRTH_DATE" + i));
					paramMap.put("DEPT_NAME", request.getParameter("DEPT_NAME" + i));
					paramMap.put("EVS_GRADE", request.getParameter("EVS_GRADE"+ i));
					paramMap.put("WORK_DAYS", request.getParameter("WORK_DAYS" + i));
					paramMap.put("EVENT_SALARY", request.getParameter("EVENT_SALARY" + i));
					paramMap.put("BLACK_LIST_YN", request.getParameter("BLACK_LIST_YN" + i));
					this.paTempSalesDAO.insertPaTempSalesEmpInfo(paramMap);
				}
			}
			this.paTempSalesDAO.updateEventInfoByEmoInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	/**
	 * 取支社长
	 * get approver by special-person's-approver setup.2 get approver by
	 * special-department's-approver setup.3 get approver by approve-flow)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getAffirmorList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 封装查询条件
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		Object obj = paramMap.get("DEPTNO");
		if(obj == null){
			return null;
		}
		return paTempSalesDAO.getAffirmorInfo(paramMap);
	}
	
	public List getApplyFeeList(HttpServletRequest request) throws Exception {
		// 封装查询条件
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		if(paramMap==null || paramMap.get("APPLY_TYPE_NO")==null || "".equals(paramMap.get("APPLY_TYPE_NO").toString())){
			paramMap.put("APPLY_TYPE_NO", "217886");
		}
		return this.infoApplySer.getAffirmorListByString("218295", paramMap.get("PERSON_ID").toString(), "", "", paramMap.get("LANGUAGE").toString());
	}

	/**
	 * 取得审批人列表:1.先取特殊设置人员的决裁者;2.再取特殊设置部门的决裁者;3.最后按流程取决裁者 (get approver list:1
	 * get approver by special-person's-approver setup.2 get approver by
	 * special-department's-approver setup.3 get approver by approve-flow)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	private List getAffirmorListByMap(LinkedHashMap paramMap) throws Exception {
		
		return infoApplySer.getAffirmorListByString(APPLY_TYPE_NO, paramMap.get("PERSON_ID").toString(), "", "", paramMap.get("LANGUAGE").toString());
	}
	
	/**
	 * 通过request请求封装查询条件(get conditions from request)
	 * 
	 * @param request
	 * @return
	 */
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
	
	/**
	 * 获取决裁情况
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getAffirmorListByEventId(HttpServletRequest request) {
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("EVENT_ID", request.getParameter("EVENT_ID"));
		if(request.getParameter("LANGUAGE") == null || "".equals(request.getParameter("LANGUAGE"))){
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("LANGUAGE", admin.getLanguage());
		}else{
			paramMap.put("LANGUAGE", request.getParameter("LANGUAGE"));
		}
		paramMap.put("APPLY_TYPE_NO", APPLY_TYPE_NO);
		retrunList = paTempSalesDAO.getAffirmorList(paramMap);
		return retrunList;
	}
	
	/**
	 * 获取check信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getCheckListByEventId(HttpServletRequest request){
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("EVENT_ID", request.getParameter("EVENT_ID"));
		if(request.getParameter("LANGUAGE") == null || "".equals(request.getParameter("LANGUAGE"))){
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("LANGUAGE", admin.getLanguage());
		}else{
			paramMap.put("LANGUAGE", request.getParameter("LANGUAGE"));
		}
		paramMap.put("APPLY_TYPE_NO", APPLY_TYPE_NO);
		retrunList = paTempSalesDAO.getCheckList(paramMap);
		return retrunList;
	}

	/**
	 * 获取决裁情况
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getAffirmorListByEventId(String eventId) {
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("EVENT_ID", eventId);
		paramMap.put("LANGUAGE",  "zh");
		paramMap.put("APPLY_TYPE_NO", APPLY_TYPE_NO);
		retrunList = paTempSalesDAO.getAffirmorList(paramMap);
		return retrunList;
	}
	

	/**
	 * 获取check信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getCheckListByEventId(String eventId){
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("EVENT_ID", eventId);
		paramMap.put("LANGUAGE",  "zh");
		paramMap.put("APPLY_TYPE_NO", APPLY_TYPE_NO);
		retrunList = paTempSalesDAO.getCheckList(paramMap);
		return retrunList;
	}
	/**
	 * 批量提交未提交的临促工资项目
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int submitTempSalary(HttpServletRequest request) {
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String[] EVENT_ID = request.getParameterValues("pa0711Check");
		if(EVENT_ID == null || EVENT_ID.length == 0){
			EVENT_ID = request.getParameterValues("pa0701Check");
		}
		String EVENT_ID_STR = "";
		for(int i=0;i<EVENT_ID.length;i++){
			if(i == 0){
				EVENT_ID_STR += EVENT_ID[i];
			}else{
				EVENT_ID_STR += "," + EVENT_ID[i];
			}
		}
		paramMap.put("EVENT_ID_STR", EVENT_ID_STR);
		try {
			if(EVENT_ID.length > 0){
				if("1".equals(paramMap.get("FLAG"))){//提交
					paTempSalesDAO.submitTempSalary(paramMap);
					this.sendToLGEP(EVENT_ID);
				}else{//删除
					paTempSalesDAO.deleteTempSalary(paramMap);
					this.deleteSendToLGEP(EVENT_ID_STR);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	/**
	 * 根据eventid获取临促信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-07
	 * @version V1.0
	 */
	public LinkedHashMap getTempSalesEmpInfoByEventId(HttpServletRequest request){
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamDataNoSession(request);
		LinkedHashMap object = new LinkedHashMap();
		if(paramMap != null && paramMap.get("LANGUAGE") == null){
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("LANGUAGE", admin.getLanguage());
		}
		object = paTempSalesDAO.getTempSalesEmpInfoByEventId(paramMap);
		if(object!=null){
			LinkedHashMap returnMap = (LinkedHashMap)object;
			returnMap.put("APPLY_NO", returnMap.get("EVENT_ID"));
			returnMap.put("APPLY_TYPE",APPLY_TYPE_NO);
			List fileList = infoApplyLeaveDao.getEssFileList(returnMap);
			returnMap.put("fileList", fileList);
		}
		return object;
	}
	
	/**
	 * 根据编号获取对应临促人员信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public LinkedHashMap getTempSalesEmpInfoByInfoNo(HttpServletRequest request){
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		return paTempSalesDAO.getTempSalesEmpInfoByInfoNo(paramMap);
	}
	/**
	 * 修改临促工工资
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int updatePaTempSales(HttpServletRequest request){
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String[] affirmId = request.getParameterValues("AFFIRMOR_ID");
		paramMap.put("CPNY_ID", admin.getCpnyId()); 
		paramMap.put("PERSON_ID", admin.getAdminID());
		paramMap.put("PERSON_ID_FILE", admin.getPersonId());
		paramMap.put("EVENT_NAME", paramMap.get("START_DATE").toString().replace("-", ""));
		try {
			paramMap.put("APPLY_TYPE_NO", APPLY_TYPE_NO);
			//删除旧的决裁者
			//paTempSalesDAO.deleteAffirmor(paramMap);
			//添加决裁者

			List affirmList = this.getAffirmorListByEventId(request);
			if(affirmList == null){
				return -1;//无审批者，请先设置支社长。
			}else{
				LinkedHashMap affirmMap = (LinkedHashMap)affirmList.get(0);
				paramMap.put("AFFIRMOR_ID", affirmMap.get("AFFIRMOR_ID"));
				paramMap.put("AFFIRM_LEVEL", affirmMap.get("AFFIRM_LEVEL"));
				paramMap.put("CURRENT_AFFIRM_ID",affirmMap.get("AFFIRMOR_ID"));
			}
			paramMap.put("PAY_DATE", paramMap.get("paYear").toString() +  paramMap.get("paMonth").toString());
			//保存临促信息
			String FILE_URL =  StringUtil.checkNull(paramMap.get("fileUrl"));
			String FILE_NAME = StringUtil.checkNull(paramMap.get("fileName"));
			paramMap.put("FILE_NAME", FILE_NAME);
			paramMap.put("FILE_URL", FILE_URL);
			this.paTempSalesDAO.updatePaTempSales(paramMap);
			if("1".equals(paramMap.get("FLAG").toString())){//提交状态发送LGEP
				this.sendToLGEP(new String[]{paramMap.get("EVENT_ID").toString()});
			} 
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 修改临促工人员信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int updatePaTempSalesEmpInfo(HttpServletRequest request){
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getAdminID());
		try {
			//保存临促信息
			this.paTempSalesDAO.updatePaTempSalesEmpInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 删除临促工人员信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int deletePaTempSalesEmpInfo(HttpServletRequest request){
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			//保存临促信息
			this.paTempSalesDAO.deletePaTempSalesEmpInfo(paramMap);
			this.paTempSalesDAO.updateEventInfoByEmoInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 组装临促模版信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String getTemplateInfo(HttpServletRequest request, List aliasNameList, List list,List mapList,List mapNameList) throws SQLException{

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String codeSql = "SELECT distinct NVL(U.CONTENT, ' ') CONTENT,'' CONTENT1 FROM SY_CODE T, SY_GLOBAL_NAME U WHERE T.CODE_NO = U.NO(+) AND U.LANGUAGE(+) = 'zh' AND T.PARENT_CODE_NO = ";
		//模版类型1:临促信息 2： 临促人员信息
		String type = request.getParameter("type");
		//模版名称
		String name = "";
		if("1".equals(type)){//临促信息模版
			aliasNameList.add("EVENT部门CODE");
			aliasNameList.add("开始日期");
			aliasNameList.add("结束日期");
			aliasNameList.add("工资支付月（YYYYMM）");
			aliasNameList.add("EVENT内容");
			aliasNameList.add("备注");
			LinkedHashMap map = new LinkedHashMap();
			map.put("CELL0", "12758");
			map.put("CELL1", "2014/07/07");
			map.put("CELL2", "2014/07/08");
			map.put("CELL3", "201407");
			map.put("CELL4", "商品促销");
			map.put("CELL5", "文本");
			list.add(map);
			
			mapNameList.add("支社参考");
			mapList.add(" SELECT HR.ACC_ORG_CODE  CONTENT,SY.CONTENT CONTENT1 FROM HR_DEPARTMENT HR, HR_DEPARTMENT_NAME SY WHERE HR.DEPT_TYPE='branch' AND HR.DEPTNO = SY.DEPTNO AND SY.LANGUAGE = 'zh' AND HR.CPNY_ID = '" + admin.getCpnyId() + "' AND HR.DEPTNO IN ( SELECT A.DEPTNO FROM SY_USER_DEPT A WHERE A.USER_NO = " + admin.getUserNo() + " )");
			name = "tempSalesInfo";
		}else if("3".equals(type)){//临促预提信息模版
			aliasNameList.add("支社");
			aliasNameList.add("产品类型");
			aliasNameList.add("工资支付月（YYYYMM）");
			aliasNameList.add("总金额");
			LinkedHashMap map = new LinkedHashMap();
			map.put("CELL1", "21004");
			map.put("CELL2", "彩电(LTV)");
			map.put("CELL3", "201407");
			map.put("CELL4", "2000");
			list.add(map);
			
			mapNameList.add("支社参考");
			mapNameList.add("产品类型参考");
			mapList.add(" SELECT HR.ACC_ORG_CODE CONTENT,SY.CONTENT CONTENT1 FROM HR_DEPARTMENT HR, HR_DEPARTMENT_NAME SY WHERE HR.DEPT_TYPE='branch' AND HR.DEPTNO = SY.DEPTNO AND SY.LANGUAGE = 'zh' AND HR.CPNY_ID = '" + admin.getCpnyId() + "' AND HR.DEPTNO IN ( SELECT A.DEPTNO FROM SY_USER_DEPT A WHERE A.USER_NO = " + admin.getUserNo() + " )");
			mapList.add("SELECT NVL(T.DESCRIPTION, ' ') CONTENT,NVL(U.CONTENT, ' ') CONTENT1 FROM SY_CODE T, SY_GLOBAL_NAME U,SY_CODE_PARAM SY WHERE T.CODE_NO = SY.CODE_NO AND SY.CPNY_ID = '" + admin.getCpnyId() + "' AND T.ACTIVITY = 1 AND T.CODE_NO = U.NO(+) AND U.LANGUAGE(+) = 'zh' AND T.PARENT_CODE_NO = 211424");
			name = "tempSalesAccrualInfo";
		}else{//临促人员信息模版
			aliasNameList.add("姓名");
			aliasNameList.add("门店");
			aliasNameList.add("身份证号");
			aliasNameList.add("银行帐号");
			aliasNameList.add("开户行");
			aliasNameList.add("产品类型");
			aliasNameList.add("联系方式");
			aliasNameList.add("评价等级");
			aliasNameList.add("工作天数");
			aliasNameList.add("EVENT工资");
			LinkedHashMap map = new LinkedHashMap();
			map.put("CELL0", "魏某某");
			map.put("CELL1", "02483117-S");
			map.put("CELL2", "411328198805065019");
			map.put("CELL3", "6222111237373737");
			map.put("CELL4", "建行");
			map.put("CELL5", "彩电(LTV)");
			map.put("CELL6", "1858585885");
			map.put("CELL7", "S");
			map.put("CELL8", "10");
			map.put("CELL9", "1000");
			list.add(map);

			mapNameList.add("产品参考");
			mapNameList.add("评价等级参考");
			mapNameList.add("门店参考");
			mapList.add("SELECT NVL(T.DESCRIPTION, ' ') CONTENT,NVL(U.CONTENT, ' ') CONTENT1 FROM SY_CODE T, SY_GLOBAL_NAME U,SY_CODE_PARAM SY WHERE T.CODE_NO = SY.CODE_NO AND SY.CPNY_ID = '" + admin.getCpnyId() + "' AND T.ACTIVITY = 1 AND T.CODE_NO = U.NO(+) AND U.LANGUAGE(+) = 'zh' AND T.PARENT_CODE_NO = 211424");
			mapList.add("SELECT NVL(T.DESCRIPTION, ' ') CONTENT1,NVL(U.CONTENT, ' ') CONTENT FROM SY_CODE T, SY_GLOBAL_NAME U,SY_CODE_PARAM SY WHERE T.CODE_NO = SY.CODE_NO AND SY.CPNY_ID = '" + admin.getCpnyId() + "' AND T.ACTIVITY = 1 AND T.CODE_NO = U.NO(+) AND U.LANGUAGE(+) = 'zh' AND T.PARENT_CODE_NO = 123195");
			mapList.add(" SELECT SHOP_CD CONTENT,SHOP_NAME CONTENT1 FROM spms_shop_grade_chrs WHERE yyyy=to_char(sysdate,'yyyy') and  COM_CD = '" + admin.getCpnyId() + "' AND BRANCH_CD IN ( SELECT A.DEPTNO FROM SY_USER_DEPT A WHERE A.USER_NO = " + admin.getUserNo() + " )");
			name = "tempSalesEmpInfo";
		}
		return name;
	}

	/**
	 * 组装临促模版信息,导出带错误提示的数据
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String getTemplateInfoByExcelData(HttpServletRequest request, List aliasNameList, List list,List mapList,List mapNameList) throws SQLException{

		LinkedHashMap paramMap = new LinkedHashMap();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("interCpnyID", admin.getCpnyId());
		
		String codeSql = "SELECT distinct NVL(U.CONTENT, ' ') CONTENT,' ' CONTENT1 FROM SY_CODE T, SY_GLOBAL_NAME U WHERE T.CODE_NO = U.NO(+) AND U.LANGUAGE(+) = 'zh' AND T.PARENT_CODE_NO = ";
		//模版类型1:临促信息 2： 临促人员信息
		String type = request.getParameter("type");
		//模版名称
		String name = "";
		if("1".equals(type)){//临促信息模版
			aliasNameList.add("EVENT部门CODE");
			aliasNameList.add("开始日期");
			aliasNameList.add("结束日期");
			aliasNameList.add("工资支付月（YYYYMM）");
			aliasNameList.add("EVENT内容");
			aliasNameList.add("备注");
			aliasNameList.add("验证结果");
			List paTempSalesTempList = this.paTempSalesDAO.getTempSalesTempList(paramMap,-1,-1);
			for(int i=0;i<paTempSalesTempList.size();i++){
				LinkedHashMap map = new LinkedHashMap();
				LinkedHashMap map1 = (LinkedHashMap)paTempSalesTempList.get(i);
				map.put("CELL0", map1.get("EVENT_DEPTNO") == null ? "" : map1.get("EVENT_DEPTNO"));
				map.put("CELL2", map1.get("START_DATE") == null ? "" : map1.get("START_DATE"));
				map.put("CELL3", map1.get("END_DATE") == null ? "" : map1.get("END_DATE"));
				map.put("CELL4", map1.get("PAY_DATE") == null ? "" : map1.get("PAY_DATE"));
				map.put("CELL5", map1.get("EVENT_CONTENT") == null ? "" : map1.get("EVENT_CONTENT"));
				map.put("CELL6", map1.get("REMARK") == null ? "" : map1.get("REMARK"));
				map.put("CELL7", map1.get("UPLOAD_ERROR_MSG") == null ? "" : map1.get("UPLOAD_ERROR_MSG"));
				list.add(map);
			}
			mapNameList.add("支社参考");
			mapList.add(" SELECT HR.ACC_ORG_CODE  CONTENT,SY.CONTENT CONTENT1 FROM HR_DEPARTMENT HR, HR_DEPARTMENT_NAME SY WHERE HR.DEPT_TYPE='branch' AND HR.DEPTNO = SY.DEPTNO AND SY.LANGUAGE = 'zh' AND HR.CPNY_ID = '" + admin.getCpnyId() + "' AND HR.DEPTNO IN ( SELECT A.DEPTNO FROM SY_USER_DEPT A WHERE A.USER_NO = " + admin.getUserNo() + " )");
			name = "tempSalesInfo";
		}else if("3".equals(type)){//临促预提信息
			aliasNameList.add("支社");
			aliasNameList.add("产品类型");
			aliasNameList.add("工资支付月（YYYYMM）");
			aliasNameList.add("总金额");
			aliasNameList.add("验证结果");
			List paTempSalesTempList = this.paTempSalesDAO.getTempSalesTempList(paramMap,-1,-1);
			for(int i=0;i<paTempSalesTempList.size();i++){
				LinkedHashMap map = new LinkedHashMap();
				LinkedHashMap map1 = (LinkedHashMap)paTempSalesTempList.get(i);
				map.put("CELL0", map1.get("EVENT_DEPTNO") == null ? "" : map1.get("EVENT_DEPTNO"));
				map.put("CELL1", map1.get("PRODUCT_CODE") == null ? "" : map1.get("PRODUCT_CODE"));
				map.put("CELL2", map1.get("PAY_DATE") == null ? "" : map1.get("PAY_DATE"));
				map.put("CELL3", map1.get("EVENT_CONTENT") == null ? "" : map1.get("EVENT_CONTENT"));
				map.put("CELL4", map1.get("UPLOAD_ERROR_MSG") == null ? "" : map1.get("UPLOAD_ERROR_MSG"));
				list.add(map);
			}
			
			mapNameList.add("支社参考");
			mapNameList.add("产品类型参考");
			mapList.add(" SELECT HR.ACC_ORG_CODE CONTENT,SY.CONTENT CONTENT1 FROM HR_DEPARTMENT HR, HR_DEPARTMENT_NAME SY WHERE HR.DEPT_TYPE='branch' AND HR.DEPTNO = SY.DEPTNO AND SY.LANGUAGE = 'zh' AND HR.CPNY_ID = '" + admin.getCpnyId() + "' AND HR.DEPTNO IN ( SELECT A.DEPTNO FROM SY_USER_DEPT A WHERE A.USER_NO = " + admin.getUserNo() + " )");
			mapList.add("SELECT NVL(T.DESCRIPTION, ' ') CONTENT,NVL(U.CONTENT, ' ') CONTENT1 FROM SY_CODE T, SY_GLOBAL_NAME U,SY_CODE_PARAM SY WHERE T.CODE_NO = SY.CODE_NO AND SY.CPNY_ID = '" + admin.getCpnyId() + "' AND T.ACTIVITY = 1 AND  T.CODE_NO = U.NO(+) AND U.LANGUAGE(+) = 'zh' AND T.PARENT_CODE_NO = 211424");
			name = "tempSalesAccrualInfo";
		}else{//临促人员信息模版
			aliasNameList.add("姓名");
			aliasNameList.add("门店");
			aliasNameList.add("身份证号");
			aliasNameList.add("银行帐号");
			aliasNameList.add("开户行");
			aliasNameList.add("产品类型");
			aliasNameList.add("联系方式");
			aliasNameList.add("评价等级");
			aliasNameList.add("工作天数");
			aliasNameList.add("EVENT工资");
			aliasNameList.add("验证结果");
			List paTempSalesEmpTempList = this.paTempSalesDAO.getTempSalesEmpTempList(paramMap,-1,-1);
			for(int i=0;i<paTempSalesEmpTempList.size();i++){
				LinkedHashMap map = new LinkedHashMap();
				LinkedHashMap map1 = (LinkedHashMap)paTempSalesEmpTempList.get(i);
				map.put("CELL0", map1.get("EMP_NAME") == null ? "" :map1.get("EMP_NAME"));
				map.put("CELL1", map1.get("EVENT_STORE_CODE") == null ? "" :map1.get("EVENT_STORE_CODE"));
				map.put("CELL2", map1.get("IDCARD_NO") == null ? "" :map1.get("IDCARD_NO"));
				map.put("CELL3", map1.get("BANK_NO") == null ? "" :map1.get("BANK_NO"));
				map.put("CELL4", map1.get("BANK_NAME") == null ? "" :map1.get("BANK_NAME"));
				map.put("CELL5", map1.get("PROD_TP") == null ? "" :map1.get("PROD_TP"));
				map.put("CELL6", map1.get("CELLPHONE") == null ? "" :map1.get("CELLPHONE"));
				map.put("CELL7", map1.get("EVS_GRADE") == null ? "" :map1.get("EVS_GRADE"));
				map.put("CELL8", map1.get("WORK_DAYS") == null ? "" :map1.get("WORK_DAYS"));
				map.put("CELL9", map1.get("EVENT_SALARY") == null ? "" :map1.get("EVENT_SALARY"));
				map.put("CELL10", map1.get("UPLOAD_ERROR_MSG") == null ? "" : map1.get("UPLOAD_ERROR_MSG"));
				list.add(map);
			}
			mapNameList.add("产品参考");
			mapNameList.add("评价等级参考");
			mapNameList.add("门店参考");
			mapList.add("SELECT NVL(T.DESCRIPTION, ' ') CONTENT,NVL(U.CONTENT, ' ') CONTENT1 FROM SY_CODE T, SY_GLOBAL_NAME U,SY_CODE_PARAM SY WHERE T.CODE_NO = SY.CODE_NO AND SY.CPNY_ID = '" + admin.getCpnyId() + "' AND T.ACTIVITY = 1 AND T.CODE_NO = U.NO(+) AND U.LANGUAGE(+) = 'zh' AND T.PARENT_CODE_NO = 211424");
			mapList.add("SELECT NVL(T.DESCRIPTION, ' ') CONTENT1,NVL(U.CONTENT, ' ') CONTENT FROM SY_CODE T, SY_GLOBAL_NAME U,SY_CODE_PARAM SY WHERE T.CODE_NO = SY.CODE_NO AND SY.CPNY_ID = '" + admin.getCpnyId() + "' AND T.ACTIVITY = 1 AND T.CODE_NO = U.NO(+) AND U.LANGUAGE(+) = 'zh' AND T.PARENT_CODE_NO = 123195");
			mapList.add(" SELECT SHOP_CD CONTENT,SHOP_NAME CONTENT1 FROM spms_shop_grade_chrs WHERE yyyy=to_char(sysdate,'yyyy') and  COM_CD = '" + admin.getCpnyId() + "' AND BRANCH_CD IN ( SELECT A.DEPTNO FROM SY_USER_DEPT A WHERE A.USER_NO = " + admin.getUserNo() + " )");
			name = "tempSalesEmpInfo";
		}
		return name;
	}
	
	@Override
	public List getExcelMessage(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		return paTempSalesDAO.getExcelMessage(paramMap);
	}
	/**
	 * 费用申请
	 */
	@Override
	public int saveWageApplication(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		paramMap.put("TITLE", request.getParameter("TITLE"));
		String fileName = (String) (paramMap.get("oldAppName")==null?"":paramMap.get("oldAppName"));
		String fileUrl  = paramMap.get("newAppName")==null?"":"/resources/uploadFile/wageApp/"+paramMap.get("newAppName");
		paramMap.put("FILENAME", fileName);
		paramMap.put("FILEURL", fileUrl);
		int max = paTempSalesDAO.saveWageApplication(paramMap);
		
		paramMap.put("APPLY_TYPE_NO", "217886");
		paramMap.put("EVENT_ID", max);
		String[] affirmId = request.getParameterValues("AFFIRMOR_ID");
		for(int i=0; i<affirmId.length; i++){
			paramMap.put("AFFIRMOR_ID", affirmId[i]);
			paramMap.put("AFFIRM_LEVEL", i+1);
			try {
				paTempSalesDAO.insertAffirmor(paramMap);
				if(i==0){
					LinkedHashMap lgepMap = new LinkedHashMap();
					lgepMap.put("EVENT_ID", max);
					lgepMap.put("APPLY_TYPE", 217886);
					lgepMap.put("APPLY_NO", max);
					lgepMap.put("APPLY_TYPE_NAME", "费用申请");
					lgepMap.put("APPLY_TITLE", paramMap.get("TITLE")+"决裁邀请");
					lgepMap.put("APPLY_EMPID", admin.getPersonId());
					lgepMap.put("ARI_URL", "http://{serverIp}/LGEP/affirm/proveApplicationList?APPLY_NO="+max+"&PERSON_ID=123");
					lgepMap.put("ABY_URL", "http://{serverIp}/LGEP/affirm/proveApplicationList?APPLY_NO="+max+"&PERSON_ID="+affirmId[i]);
					lgepMap.put("AFFIRM_LEVEL", "1");
					lgepMap.put("PRE_AFFIRM_EMPID", affirmId[i]);
					lgepMap.put("CURRENT_AFFIRM_ID", affirmId[i]);
					this.affirmInfoToLGEPSer.crateAffirm(lgepMap);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return max;
	}
	
	@Override
	public int deleteWageApplicationDetail(HttpServletRequest request){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return this.paTempSalesDAO.deleteWageApplicationDetail(paramMap);
	}

	/**
	 * 添加Check人
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int addCheckPaTempSales(HttpServletRequest request){
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getAdminID());
		paramMap.put("CHECKOR_ID", paramMap.get("dwz.person.personId"));
		try {
			this.paTempSalesDAO.addCheckPaTempSales(paramMap);
			//Check人发送LGEP
			this.sendToLGEPCreateCheck(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 决裁临促工资项目
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int affirmPaTempSales(HttpServletRequest request){

		AdminBean admin = null;
		LinkedHashMap paramMap = null;
		if(request.getParameter("personId") == null || "".equals(request.getParameter("personId"))){
			admin = SessionUtil.getLoginUserFromSession(request) ;
			paramMap = ObjectBindUtil.getRequestParamData(request) ;
		}else{
			paramMap = ObjectBindUtil.getRequestParamDataNoSession(request);
		}
		String[] affirmId = request.getParameterValues("AFFIRMOR_ID");
		paramMap.put("FLAG", paramMap.get("FLAG"));
		paramMap.put("UPDATED_BY", admin == null ? request.getParameter("personId") :admin.getAdminID());
		paramMap.put("PERSON_ID", admin == null ? request.getParameter("personId") : admin.getAdminID());
		paramMap.put("CURRENT_AFFIRM_ID",  "");
		paramMap.put("APPLY_TYPE_NO", APPLY_TYPE_NO);
		int dept_level = Integer.parseInt(paramMap.get("dept_level").toString());
		paramMap.put("dept_level", dept_level + 1);
		try {
			//删除旧的决裁者
			paTempSalesDAO.deleteAffirmor(paramMap);
			//添加决裁者
			for(int i=1; i<affirmId.length; i++){
				paramMap.put("AFFIRMOR_ID", affirmId[i]);
				paramMap.put("AFFIRM_LEVEL", i+dept_level);
				paTempSalesDAO.insertAffirmor(paramMap);
			}
			//决裁
			this.paTempSalesDAO.updateEssAffirm(paramMap);
			if(!"2".equals(paramMap.get("FLAG"))){//否决直接修改标志位,通过继续下面操作
				//获取决裁者列表
				paramMap.put("ACTIVITY", "0");
				List<LinkedHashMap> affirmList = this.paTempSalesDAO.getAffirmorList(paramMap);
				if (affirmList != null && affirmList.size() > 0) {
					for (LinkedHashMap parmers : affirmList) { 
						if("0".equals(parmers.get("AFFIRM_FLAG").toString())){
							paramMap.put("CURRENT_AFFIRM_ID",  parmers.get("AFFIRMOR_ID"));
							paramMap.put("AFFIRM_LEVEL",  parmers.get("AFFIRM_LEVEL"));
							paramMap.put("FLAG", "0");
							break;
						}
						paramMap.put("AFFIRM_LEVEL",  affirmList.size());
					}
				}
			}
			this.sendToLGEPCheckBatch(paramMap);
			//check信息修改为已check
			this.paTempSalesDAO.updateCheckFlagByEssAffirmNo(paramMap);
			this.paTempSalesDAO.affirmPaTempSales(paramMap);
			//发送LGEP
			this.sendToLGEP(paramMap);
			if("CONFIRM_N".equals(paramMap.get("accrualFlag").toString())){
				this.paTempSalesDAO.updateTempSalesProgress(paramMap);
			}else if("CONFIRM_Y".equals(paramMap.get("accrualFlag").toString())){
				this.paTempSalesDAO.updateTempSalesProgress(paramMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * check临促工资项目
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int checkPaTempSales(HttpServletRequest request){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamDataNoSession(request) ;
		if(admin == null){
			paramMap.put("UPDATED_BY", paramMap.get("PERSON_ID"));
		}else{
			paramMap.put("UPDATED_BY", admin.getAdminID());
			paramMap.put("PERSON_ID", admin.getAdminID());
		}
		try {
			this.paTempSalesDAO.updateEssCheck(paramMap);
			this.sendToLGEPCheck(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * check信息列表
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getTempSalesCheckList(HttpServletRequest request,String accuralFlag){
		List retrunList = new ArrayList();

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("LANGUAGE", admin.getLanguage());
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("APPLY_TYPE_NO", APPLY_TYPE_NO);
		paramMap.put("ACCRUAL_FLAG", accuralFlag);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = paTempSalesDAO.getTempSalesCheckList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = paTempSalesDAO.getTempSalesCheckList(paramMap);
		}
		return retrunList;
	}
	
	/**
	 * check信息列表
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getTempSalesCheckCnt(HttpServletRequest request,String accuralFlag) {
		int retrunInt = 0;


		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("LANGUAGE", admin.getLanguage());
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("APPLY_TYPE_NO", APPLY_TYPE_NO);
		paramMap.put("ACCRUAL_FLAG", accuralFlag);

		retrunInt = paTempSalesDAO.getPaTempSalesCheckCnt(paramMap);

		return retrunInt;
	}
	/**
	 * 获取临促导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getTempSalesTempList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = paTempSalesDAO.getTempSalesTempList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = paTempSalesDAO.getTempSalesTempList(paramMap);
		}
		return retrunList;
	}
	
	/**
	 * 获取临促导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getTempSalesTempCnt(HttpServletRequest request, String errorFlag){
		int retrunInt = 0;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if("E".equals(errorFlag)){
			retrunInt = paTempSalesDAO.getTempSalesTempErrorCnt(paramMap);
		}else{
			retrunInt = paTempSalesDAO.getTempSalesTempCnt(paramMap);
		}

		return retrunInt;
	}
	
	/**
	 * 获取临促人员导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getTempSalesTempEmpList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = paTempSalesDAO.getTempSalesEmpTempList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = paTempSalesDAO.getTempSalesEmpTempList(paramMap);
		}
		return retrunList;
	}
	
	/**
	 * 获取临促人员导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getTempSalesEmpTempCnt(HttpServletRequest request, String errorFlag){
		int retrunInt = 0;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if("E".equals(errorFlag)){
			retrunInt = paTempSalesDAO.getTempSalesEmpTempErrorCnt(paramMap);
		}else{
			retrunInt = paTempSalesDAO.getTempSalesEmpTempCnt(paramMap);
		}

		return retrunInt;
	}
	
	/**
	 * 临促工资excel信息提交
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String submitImportExcelTempSalesData(HttpServletRequest request){

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		if(request.getParameter("accrual") == null){
			paramMap.put("PR_NAME", "pkg_tempsales_excel.pr_import_temp_sales_data");
		}else{
			paramMap.put("PR_NAME", "pkg_tempsales_excel.pr_import_temp_sales_acc_data");
		}
		try {
			return this.paTempSalesDAO.importFromExcel(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	/**
	 * 派遣津贴标准提交功能
	 */
	public String submitImportExcelTempPqdJtbzData(HttpServletRequest request){

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());//创建者
		paramMap.put("CREATED_BY", admin.getPersonId());//创建者
		paramMap.put("CPNY_ID", admin.getCpnyId());//法人区分
		String result = "Error";
		try {
			result = this.paTempSalesDAO.submitImportExcelTempPqdJtbzData(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return result;
	}
	
	public String submitImportExcelTempPqdGuanLiData(HttpServletRequest request){

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());//创建者
		paramMap.put("CREATED_BY", admin.getPersonId());//创建者
		paramMap.put("CPNY_ID", admin.getCpnyId());//法人区分
		String result = "Error";
		try {
			result = this.paTempSalesDAO.submitImportExcelTempPqdGuanLiData(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return result;
	}
	
	public String submitImportExcelTempZuiDiGongZiBiaoZhunFeiCuXiaoYuanData(HttpServletRequest request){

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());//创建者
		paramMap.put("CPNY_ID", admin.getCpnyId());//法人区分
		String result = "Error";
		try {
			result = this.paTempSalesDAO.submitImportExcelTempZuiDiGongZiBiaoZhunFeiCuXiaoYuanData(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return result;
	}
	
	public String submitImportExcelTempZuiDiGongZiBiaoZhunCuXiaoYuanData(HttpServletRequest request){

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());//创建者
		paramMap.put("CPNY_ID", admin.getCpnyId());//法人区分
		String result = "Error";
		try {
			result = this.paTempSalesDAO.submitImportExcelTempZuiDiGongZiBiaoZhunCuXiaoYuanData(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return result;
	}
	
	/**
	 * 临促工资人员excel信息提交
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String submitImportExcelTempSalesEmpData(HttpServletRequest request){

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PR_NAME", "pkg_tempsales_excel.pr_import_temp_sales_emp_data");
		try {
			return this.paTempSalesDAO.importFromExcel(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	/**
	 * 判断是否有促销人员为空的信息提交
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int checkTempSalesCount(HttpServletRequest request) {
		int retrunInt = 0;

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		String[] EVENT_ID = request.getParameterValues("pa0711Check");
		if(EVENT_ID == null || EVENT_ID.length == 0){
			EVENT_ID = request.getParameterValues("pa0701Check");
		}
		for(int i=0;i<EVENT_ID.length;i++){
			paramMap.put("EVENT_ID", EVENT_ID[i]);
			retrunInt = paTempSalesDAO.getPaTempSalesEmpInfoCnt(paramMap);
			if(retrunInt == 0){
				break;
			}
		}

		return retrunInt;
	}
	
	/**
	 * 获取门店信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getSpmsShopList(HttpServletRequest request){
		List retrunList = new ArrayList();

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("LANGUAGE", admin.getLanguage());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		retrunList = paTempSalesDAO.getSpmsShopList(paramMap);
		return retrunList;
	}
	
	/**
	 * 提交后发送LGEP
	 * @param eventId
	 */
	private void sendToLGEP(String[] eventId){
		for(int i=0;i<eventId.length;i++){
			LinkedHashMap paramMap = new LinkedHashMap();
			paramMap.put("EVENT_ID", eventId[i]);
			if (this.paTempSalesDAO.isTsm(paramMap) != 0){
				try {
					this.paTempSalesDAO.updateTsmStatus(paramMap);
					this.paTempSalesDAO.updateTsmStatusAffirm(paramMap);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				LinkedHashMap lgepMap = paTempSalesDAO.getTempSalesEmpInfoByEventId(paramMap);
				lgepMap.put("APPLY_TYPE", APPLY_TYPE_NO);
				lgepMap.put("APPLY_NO", eventId[i]);
				lgepMap.put("APPLY_TYPE_NAME", APPLY_TYPE_NAME);
				lgepMap.put("APPLY_TITLE", lgepMap.get("EVENT_NAME") + APPLY_TITLE_SUFFIX);
				lgepMap.put("APPLY_EMPID", lgepMap.get("CREATED_BY"));
				lgepMap.put("ARI_URL", "http://{serverIp}/LGEP/affirm/viewTempSaleAffirm?LGEP=LGEP&LANGUAGE=zh&personId=123&EVENT_ID=" + lgepMap.get("EVENT_ID") + "&affirmOrCheck=1");
				lgepMap.put("ABY_URL", "http://{serverIp}/LGEP/affirm/viewTempSaleAffirm?LGEP=LGEP&LANGUAGE=zh&personId=" + lgepMap.get("CURRENT_AFFIRM_ID") + "&EVENT_ID=" + lgepMap.get("EVENT_ID") + "&affirmOrCheck=1");
				lgepMap.put("AFFIRM_LEVEL", "1");
				lgepMap.put("PRE_AFFIRM_EMPID", lgepMap.get("CURRENT_AFFIRM_ID"));
				this.affirmInfoToLGEPSer.crateAffirm(lgepMap);
			}
		}
	}
	
	/**
	 * 审批后发送LGEP
	 * @param eventId
	 */
	private void sendToLGEP(LinkedHashMap paramMap){
			LinkedHashMap lgepMap = paTempSalesDAO.getTempSalesEmpInfoByEventId(paramMap);
			lgepMap.put("APPLY_TYPE", APPLY_TYPE_NO);
			lgepMap.put("APPLY_NO", paramMap.get("EVENT_ID"));
			if("0".equals(paramMap.get("FLAG").toString())){
				lgepMap.put("AFFIRM_LEVEL", paramMap.get("AFFIRM_LEVEL"));
				lgepMap.put("AFFIRM_FLAG", 1);
			}else{//决裁完成
				lgepMap.put("FINISH", "FINISH");
				lgepMap.put("AFFIRM_FLAG", paramMap.get("FLAG"));
				lgepMap.put("AFFIRM_LEVEL", Integer.parseInt(paramMap.get("dept_level").toString()) - 1);
			}
			lgepMap.put("AFFIRM_EMPID", paramMap.get("PERSON_ID"));
			lgepMap.put("AAI_URL", "http://{serverIp}/LGEP/affirm/viewTempSaleAffirm?LGEP=LGEP&LANGUAGE=zh&personId=123&EVENT_ID=" + lgepMap.get("EVENT_ID") + "&affirmOrCheck=1");
			lgepMap.put("AAF_URL", "http://{serverIp}/LGEP/affirm/viewTempSaleAffirm?LGEP=LGEP&LANGUAGE=zh&personId=123&EVENT_ID=" + lgepMap.get("EVENT_ID") + "&affirmOrCheck=1");
			lgepMap.put("ABY_URL", "http://{serverIp}/LGEP/affirm/viewTempSaleAffirm?LGEP=LGEP&LANGUAGE=zh&personId=" + lgepMap.get("CURRENT_AFFIRM_ID") + "&EVENT_ID=" + lgepMap.get("EVENT_ID") + "&affirmOrCheck=1");
			lgepMap.put("PRE_AFFIRM_EMPID", paramMap.get("CURRENT_AFFIRM_ID"));
			this.affirmInfoToLGEPSer.affirm(lgepMap);
	}
	/**
	 * 审批后发送LGEP
	 * @param eventId/**
	 * CHECK：
	 *  APPLY_NO:申请的seq
	 *  APPLY_TYPE：申请类型（数字代码）
	 *	AFFIRM_LEVEL：ess_check_no
	 *	AFFIRM_FLAG：传1，表示通过
	 *	AFFIRM_EMPID：check人person_id
	 *	AAI_URL:check结果查看页面url
	 */
	private void sendToLGEPCheck(LinkedHashMap paramMap){
			LinkedHashMap lgepMap = new LinkedHashMap();
			lgepMap.put("APPLY_TYPE", APPLY_TYPE_NO);
			lgepMap.put("APPLY_NO", paramMap.get("EVENT_ID"));
			lgepMap.put("AFFIRM_FLAG", '1');
			lgepMap.put("AFFIRM_LEVEL", paramMap.get("ESS_CHECK_NO"));
			lgepMap.put("AFFIRM_EMPID", paramMap.get("PERSON_ID"));
			lgepMap.put("AAI_URL", "http://{serverIp}/LGEP/affirm/viewTempSaleAffirm?LGEP=LGEP&LANGUAGE=zh&personId=123&EVENT_ID=" + paramMap.get("EVENT_ID") + "&affirmOrCheck=2");
			this.affirmInfoToLGEPSer.check(lgepMap);
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
				lgepMap.put("APPLY_NO", paramMap.get("EVENT_ID"));
				lgepMap.put("AFFIRM_FLAG", '1');
				lgepMap.put("AFFIRM_LEVEL", lgepMap.get("ESS_CHECK_NO"));
				lgepMap.put("AFFIRM_EMPID", lgepMap.get("CHECKOR_ID"));
				lgepMap.put("AAI_URL", "http://{serverIp}/LGEP/affirm/viewTempSaleAffirm?LGEP=LGEP&LANGUAGE=zh&personId=123&EVENT_ID=" + paramMap.get("EVENT_ID") + "&affirmOrCheck=2");
				this.affirmInfoToLGEPSer.check(lgepMap);
			}
		}
	}
	
	/**
	 * 审批后发送LGEP
	 * @param eventId
	 * 创建check邀请：
	 *     APPLY_NO:申请的seq
	 *	   APPLY_TYPE：申请类型（数字代码）
	 *	   AFFIRM_LEVEL：ess_check_no
	 *	   PRE_AFFIRM_EMPID: check人person_id
	 *	   ABY_URL: check页面url
	 */
	private void sendToLGEPCreateCheck(LinkedHashMap paramMap){
			LinkedHashMap lgepMap = new LinkedHashMap();
			lgepMap.put("APPLY_TYPE", APPLY_TYPE_NO);
			lgepMap.put("APPLY_NO", paramMap.get("APPLY_NO"));
			lgepMap.put("AFFIRM_LEVEL", paramMap.get("ESS_CHECK_NO"));
			lgepMap.put("PRE_AFFIRM_EMPID", paramMap.get("CHECKOR_ID"));
			lgepMap.put("ABY_URL", "http://{serverIp}/LGEP/affirm/viewTempSaleAffirm?LGEP=LGEP&LANGUAGE=zh&personId=" + paramMap.get("CHECKOR_ID") + "&EVENT_ID=" + paramMap.get("APPLY_NO") + "&affirmOrCheck=2");
			this.affirmInfoToLGEPSer.crateCheck(lgepMap);
	}

	/**
	 * 删除发送LGEP
	 * @param eventId
	 */
	private void deleteSendToLGEP(String EVENT_ID_STR){
		LinkedHashMap lgepMap = new LinkedHashMap();
		String[] eventIds = EVENT_ID_STR.split(",");
		for(int i=0;i<eventIds.length;i++){
			lgepMap.put("APPLY_NO",eventIds[i]);
			lgepMap.put("APPLY_TYPE", APPLY_TYPE_NO);
			this.affirmInfoToLGEPSer.deleteAffirm(lgepMap);
		}
	}
	/**
	 * 根据部门id获取共同社编
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-07
	 * @version V1.0
	 */
	public LinkedHashMap getCommonEmpId(HttpServletRequest request){

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);

		LinkedHashMap map = paTempSalesDAO.getCommonEmpId(paramMap);
		return map;
	}
	

	public List getTempSalesEmpList(HttpServletRequest request) {
		List retrunList = new ArrayList();

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID",admin.getCpnyId());
		paramMap.put("USERNO",admin.getUserNo());
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("deptNo", admin.getDeptNo());
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = paTempSalesDAO.getPaTempSalesEmpList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = paTempSalesDAO.getPaTempSalesEmpList(paramMap);
		}
		return retrunList;
	}

	public int getTempSalesEmpCnt(HttpServletRequest request) {
		int retrunInt = 0;

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID",admin.getCpnyId());
		paramMap.put("USERNO",admin.getUserNo());
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("deptNo", admin.getDeptNo());
		retrunInt = paTempSalesDAO.getPaTempSalesEmpCnt(paramMap);

		return retrunInt;
	}
	
	/**
	 * 根据身份证号获取对应临促人员信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public LinkedHashMap getTempSalesEmpInfoByIdCard(HttpServletRequest request){
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		return paTempSalesDAO.getTempSalesEmpInfoByIdCard(paramMap);
	}
	

	/**
	 * 修改临促工人员信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int updatePaTempSalesEmp(HttpServletRequest request){
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
	
	public List getTempSalesSummaryList(HttpServletRequest request) {
		List retrunList = new ArrayList();

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		Object type = paramMap.get("TYPE");
		if(type == null || "".equals(type.toString())){
			paramMap.put("TYPE", "summary");
		}
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = paTempSalesDAO.getPaTempSalesSummaryList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = paTempSalesDAO.getPaTempSalesSummaryList(paramMap);
		}
		return retrunList;
	}

	public int getTempSalesSummaryCnt(HttpServletRequest request) {
		int retrunInt = 0;

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");

		Object type = paramMap.get("TYPE");
		if(type == null || "".equals(type.toString())){
			paramMap.put("TYPE", "summary");
		}
		
		retrunInt = paTempSalesDAO.getPaTempSalesSummaryCnt(paramMap);

		return retrunInt;
	}
	/**
	 * 临促汇总
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String viewTempSaleSummary(HttpServletRequest request){
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
		"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getAdminID());
		String resultSuccess = "";
		String resultFail = "";
		String result = "OK";
		String payAreaCd = request.getParameter("seach_PAY_AREA_CD");
		String payAreaNm = request.getParameter("seach_PAY_AREA_NM");
		try {
			if(payAreaCd == null || "".equals(payAreaCd) || payAreaNm == null || "".equals(payAreaNm) ){
				result = "请先选择要计算的大区。";
			}else{
				String[] payAreaCdArray = payAreaCd.split(",");
				String[] payAreaNmArray = payAreaNm.split(",");
				for (int i = 0;i<payAreaCdArray.length;i++){
					paramMap.put("PAY_AREA_CD",payAreaCdArray[i]);
					//保存临促信息
					String resultMsg  = this.paTempSalesDAO.viewTempSaleSummary(paramMap);
					if("OK".equals(resultMsg)){
						resultSuccess += payAreaNmArray[i] + " ";
					}else{
						if("1".equals(resultMsg)){
							resultFail += payAreaNmArray[i] + "已传送财务,不能再重新计算税金<br/>";
						}else if("2".equals(resultMsg)){
							resultFail += payAreaNmArray[i] + "无有效的申请数据,无法进行税金计算<br/>";
						}else{
							resultFail += payAreaNmArray[i] + "出现系统错误，无法计算，请与管理员联系<br/>";
						}
					}
				}
				if(resultSuccess.length() > 0 && resultFail.length() > 0){
					result = resultSuccess + "计算成功<br/>" + resultFail ;
				}
				if(resultSuccess.length() > 0 && resultFail.length() == 0){
					result = "OK";
				}
				if(resultSuccess.length() == 0 && resultFail.length() > 0){
					result = resultFail;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}
	

	public List getTempSalesAccuralInfoList(HttpServletRequest request) {
		List retrunList = new ArrayList();

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamDataNoSession(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if(admin != null){
			paramMap.put("LANGUAGE", admin.getLanguage());
		}
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = paTempSalesDAO.getTempSalesAccuralInfoList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = paTempSalesDAO.getTempSalesAccuralInfoList(paramMap);
		}
		return retrunList;
	}
	
	public int getTempSalesAccuralInfoCnt(HttpServletRequest request) {
		int retrunInt = 0;

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamDataNoSession(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if(admin != null){
			paramMap.put("LANGUAGE", admin.getLanguage());
		}
		retrunInt = paTempSalesDAO.getTempSalesAccuralInfoCnt(paramMap);
		return retrunInt;
	}
	/**
	 * 根据eventid获取临促信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-07
	 * @version V1.0
	 */
	public LinkedHashMap getTempSalesInfoByEventId(HttpServletRequest request){
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamDataNoSession(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		if(admin != null){
			paramMap.put("LANGUAGE", admin.getLanguage());
		}
		Map eventObj = paTempSalesDAO.getTempSalesInfoByEventId(paramMap);
		if(eventObj != null){
			LinkedHashMap returnMap = (LinkedHashMap)eventObj;
			returnMap.put("APPLY_NO", returnMap.get("EVENT_ID"));
			returnMap.put("APPLY_TYPE", "218064");
			List fileList = infoApplyLeaveDao.getEssFileList(returnMap);
			returnMap.put("fileList", fileList);
			eventObj.put("fileList", fileList);
		}
		return (LinkedHashMap) eventObj;
	}
	
	/**
	 * 获取支社列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List getBranchList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		paramMap.put("language", admin.getLanguage());
		paramMap.put("CPNY_ID",  admin.getCpnyId());
		paramMap.put("userNo", admin.getUserNo());// 判断部门权限用
		return paTempSalesDAO.getBranchList(paramMap);
	}


	/**
	 * 获取支社列表(人事权限)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List getBranchListHR(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("CPNY_ID",  admin.getCpnyId());
		paramMap.put("userNo", admin.getUserNo());// 判断部门权限用
		paramMap.put("deptNo", admin.getDeptNo());// 判断部门权限用
		paramMap.put("specialParam", admin.getSpecialParam());// 判断部门权限用
		return paTempSalesDAO.getBranchListHR(paramMap);
	}
	
	/**
	 * 临促工资发送财务
	 * @param request
	 * @return
	 */
	public List getTempSalesSendList(HttpServletRequest request,String accrualFlag) {
		List retrunList = new ArrayList();
		LinkedHashMap paramMap = null;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		if(admin == null){
			paramMap = ObjectBindUtil.getRequestParamDataNoSession(request);
			paramMap.put("interLanguage", paramMap.get("LANGUAGE"));
		}else{
			paramMap = ObjectBindUtil.getRequestParamData(request,
			"seach_");
		}
		paramMap.put("accrualFlag", accrualFlag);
		String year = request.getParameter("YEAR") == null ? request.getParameter("seach_YEAR") : request.getParameter("YEAR") ;
		String month = request.getParameter("MONTH") == null ? request.getParameter("seach_MONTH") : request.getParameter("MONTH") ;
		if(year != null){
			paramMap.put("PA_MONTH", year + month);
			paramMap.put("IS_SEND", "1");
			if (UiUtil.getPageNum(request) > 0) {
				retrunList = paTempSalesDAO.getPaTempSalesSendList(paramMap, UiUtil
						.getPageNum(request), UiUtil.getNumPerPage(request));
			} else {
				retrunList = paTempSalesDAO.getPaTempSalesSendList(paramMap);
			}
		}else{
			paramMap.put("PA_MONTH", request.getAttribute("PA_MONTH"));
			paramMap.put("IS_SEND", "0");
			retrunList = paTempSalesDAO.getPaTempSalesSendList(paramMap);
		}
		return retrunList;
	}

	/**
	 * 临促月别总计
	 * @param request
	 * @return
	 */
	public LinkedHashMap getTempSalesSum(HttpServletRequest request,String accrualFlag) {

		LinkedHashMap result = null;
		// 页面提交数据
		LinkedHashMap paramMap = null;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		if(admin == null){
			paramMap = ObjectBindUtil.getRequestParamDataNoSession(request);
			paramMap.put("interLanguage", paramMap.get("LANGUAGE"));
		}else{
			paramMap = ObjectBindUtil.getRequestParamData(request,
			"seach_");
		}
		paramMap.put("accrualFlag", accrualFlag);
		String year = request.getParameter("YEAR") == null ? request.getParameter("seach_YEAR") : request.getParameter("YEAR") ;
		String month = request.getParameter("MONTH") == null ? request.getParameter("seach_MONTH") : request.getParameter("MONTH") ;
		if(year != null){
			paramMap.put("PA_MONTH", year + month);
		}else{
			paramMap.put("PA_MONTH", request.getAttribute("PA_MONTH"));
		}
		List retrunList = paTempSalesDAO.getTempSalesSum(paramMap);
		if(retrunList != null && retrunList.size() > 0){
			result = (LinkedHashMap)retrunList.get(0);
		}
		return result;
	}
	/**
	 * 临促工资发送财务数量
	 * @param request
	 * @return
	 */
	public int getTempSalesSendCnt(HttpServletRequest request,String accrualFlag) {
		int retrunInt = 0;

		// 页面提交数据
		LinkedHashMap paramMap = null;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		if(admin == null){
			paramMap = ObjectBindUtil.getRequestParamDataNoSession(request);
			paramMap.put("interLanguage", paramMap.get("LANGUAGE"));
		}else{
			paramMap = ObjectBindUtil.getRequestParamData(request,
			"seach_");
		}

		String year = request.getParameter("YEAR") == null ? request.getParameter("seach_YEAR") : request.getParameter("YEAR") ;
		String month = request.getParameter("MONTH") == null ? request.getParameter("seach_MONTH") : request.getParameter("MONTH") ;
		if(year != null){
			paramMap.put("PA_MONTH", year + month);
		}else{
			paramMap.put("PA_MONTH", request.getAttribute("PA_MONTH"));
		}
		paramMap.put("accrualFlag", accrualFlag);
		
		retrunInt = paTempSalesDAO.getPaTempSalesSendCnt(paramMap);

		return retrunInt;
	}
	

	/**
	 * 临促传送财务
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String viewTempSaleSend(HttpServletRequest request){
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
		"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getAdminID());
		String resultSuccess = "";
		String resultFail = "";
		String result = "OK";
		String payAreaCd = request.getParameter("seach_PAY_AREA_CD");
		String payAreaNm = request.getParameter("seach_PAY_AREA_NM");
		try {
			if(payAreaCd == null || "".equals(payAreaCd) || payAreaNm == null || "".equals(payAreaNm) ){
				result = "请先选择要传送的大区。";
			}else{
				String[] payAreaCdArray = payAreaCd.split(",");
				String[] payAreaNmArray = payAreaNm.split(",");
				for (int i = 0;i<payAreaCdArray.length;i++){
					paramMap.put("PAY_AREA_CD",payAreaCdArray[i]);
					//保存临促信息
					String resultMsg  = this.paTempSalesDAO.viewTempSaleSend(paramMap);
					if("OK".equals(resultMsg)){
						resultSuccess += payAreaNmArray[i] + " ";
					}else{
						if("1".equals(resultMsg)){
							resultFail += payAreaNmArray[i] + "大区担当审批未通过,不能传送财务<br/>";
						}else if("2".equals(resultMsg)){
							resultFail += payAreaNmArray[i] + "还未进行税金计算，不能传送财务<br/>";
						}else if("3".equals(resultMsg)){
							resultFail += payAreaNmArray[i] + "已传送财务，不能重复传送<br/>";
						}else{
							resultFail += payAreaNmArray[i] + "出现系统错误，无法传送，请与管理员联系<br/>";
						}
					}
				}
				if(resultSuccess.length() > 0 && resultFail.length() > 0){
					result = resultSuccess + "传送成功<br/>" + resultFail;
				}
				if(resultSuccess.length() > 0 && resultFail.length() == 0){
					result = "OK";
				}
				if(resultSuccess.length() == 0 && resultFail.length() > 0){
					result = resultFail;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}
	

	/**
	 * 发送数据给大区担当确认
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String viewTempSaleConfirm(HttpServletRequest request){
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
		"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getAdminID());
		String resultSuccess = "";
		String resultFail = "";
		String result = "";
		String payAreaCd = request.getParameter("seach_PAY_AREA_CD");
		String payAreaNm = request.getParameter("seach_PAY_AREA_NM");
		try {
			if(payAreaCd == null || "".equals(payAreaCd) || payAreaNm == null || "".equals(payAreaNm) ){
				result = "请先选择要确认的大区。";
			}else{
				String[] payAreaCdArray = payAreaCd.split(",");
				String[] payAreaNmArray = payAreaNm.split(",");
				for (int i = 0;i<payAreaCdArray.length;i++){
					paramMap.put("PAY_AREA_CD",payAreaCdArray[i]);
					//保存临促信息
					String resultMsg  = this.paTempSalesDAO.viewTempSaleConfirm(paramMap);
					if("OK".equals(resultMsg)){
						resultSuccess += payAreaNmArray[i] + " ";
					}else{
						if("-1".equals(resultMsg)){
							resultFail += payAreaNmArray[i] + "大区担当不存在<br/>";
						}else if("1".equals(resultMsg)){
							resultFail += payAreaNmArray[i] + "已经发送给大区担当确认无需重复发送<br/>";
						}else if("2".equals(resultMsg)){
							resultFail += payAreaNmArray[i] + "已传送财务，不能再确认<br/>";
						}else if("3".equals(resultMsg)){
							resultFail += payAreaNmArray[i] + "还未进行税金计算，不能确认<br/>";
						}else if("4".equals(resultMsg)){
							resultFail += payAreaNmArray[i] + "无有效的申请数据，无法确认<br/>";
						}else{
							resultFail += payAreaNmArray[i] + "出现系统错误，无法确认，请与管理员联系<br/>";
						}
					}
				}
				if(resultSuccess.length() > 0 && resultFail.length() > 0){
					result = resultSuccess + "确认成功<br/>" + resultFail ;
				}
				if(resultSuccess.length() > 0 && resultFail.length() == 0){
					result = "OK";
				}
				if(resultSuccess.length() == 0 && resultFail.length() > 0){
					result = resultFail;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}
	/**
	 * 临促工资状态查看
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	public List viewTempSaleState(HttpServletRequest request) {
		List retrunList = new ArrayList();

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");

		retrunList = paTempSalesDAO.viewTempSaleState(paramMap);
		return retrunList;
	}
	

	/**
	 * 根据月份、大区、预提标示 获取临促汇总信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-07
	 * @version V1.0
	 */
	public LinkedHashMap getTempSalesSummaryInfo(HttpServletRequest request){
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamDataNoSession(request);
		LinkedHashMap object = new LinkedHashMap();
		if(paramMap != null && paramMap.get("LANGUAGE") == null){
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("LANGUAGE", admin.getLanguage());
		}
		object = paTempSalesDAO.getTempSalesSummary(paramMap);
		return object;
	}
}
