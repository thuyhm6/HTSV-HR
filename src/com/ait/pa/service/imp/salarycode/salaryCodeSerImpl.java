package com.ait.pa.service.imp.salarycode;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.affirm.service.AffirmInfoToLGEPSer;
import com.ait.pa.dao.salaryCodeDao;
import com.ait.pa.service.imp.wagebase.PaBasicItemSerImp;
import com.ait.pa.service.salarycode.salaryCodeSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.AttendItemMappingDao;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;
import com.businessobjects.crystalreports.viewer.core.r;
@Service
public class salaryCodeSerImpl implements salaryCodeSer{
	
    Logger logger = Logger.getLogger(salaryCodeSerImpl.class);
	
	@Autowired
	private salaryCodeDao salaryCodeDao;
	

	//离职申请类型代码
	private static String APPLY_TYPE_NO = "1101";
		
	@Autowired
	private AffirmInfoToLGEPSer affirmInfoToLGEPSer;
		
	@Autowired
	private AttendItemMappingDao attendItemMappingDao;

	@Override
	public List getSalaryCodeList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("PROJECT_TYPE", request.getParameter("PROJECT_TYPE"));
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				salaryCodeDao.getSalaryCodeList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = salaryCodeDao.getSalaryCodeList(paramMap) ;
		}
		
		return retrunList ;
	}
	
	/**
	 * 需要决裁的(决裁查看)
	 */
	public List getAffirmSalaryCodeList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String cpny_id = admin.getCpnyId();
		if(cpny_id != null && !"".equals(cpny_id)){
			paramMap.put("CPNY_ID", cpny_id);
		}
		paramMap.put("PROJECT_TYPE", request.getParameter("PROJECT_TYPE"));
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				salaryCodeDao.getAffirmSalaryCodeList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = salaryCodeDao.getAffirmSalaryCodeList(paramMap) ;
		}
		
		return retrunList ;
	}
	
	/**
	 * 需要决裁的(决裁页面)
	 */
	public List getAffirmSalaryList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = new LinkedHashMap();
		String cpny_id = request.getParameter("cpny");
		// 页面提交数据
		String personId = request.getParameter("personId");
		if(personId == null || "".equals(personId)){
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
			if(cpny_id != null && !"".equals(cpny_id)){
				paramMap.put("CPNY_ID", cpny_id);
			}else{
				paramMap.put("CPNY_ID", admin.getCpnyId());
			}
			paramMap.put("PROJECT_TYPE", request.getParameter("PROJECT_TYPE"));
			paramMap.put("ITEM_NO", request.getParameter("ITEM_NO"));
		}else{
			paramMap.put("CPNY_ID", request.getParameter("CPNY_ID"));
			paramMap.put("interLanguage", request.getParameter("LANGUAGE"));
			paramMap.put("ITEM_NO", request.getParameter("APPLY_NO"));
		}
		
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				salaryCodeDao.getAffirmSalaryList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = salaryCodeDao.getAffirmSalaryList(paramMap) ;
		}
		
		return retrunList ;
	}


	@Override
	public int getSalaryCodeCnt(HttpServletRequest request) {
		int retrunInt = 0 ;
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		retrunInt = salaryCodeDao.getSalaryCodeCnt(paramMap) ;
		
		return retrunInt ;
	}
	/**
	 *需要决裁的（决裁查看）
	 */
	@Override
	public int getAffirmSalaryCodeCnt(HttpServletRequest request) {
		int retrunInt = 0 ;
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String cpny_id = admin.getCpnyId();
		if(cpny_id != null && !"".equals(cpny_id)){
			paramMap.put("CPNY_ID", cpny_id);
		}
		retrunInt = salaryCodeDao.getAffirmSalaryCodeCnt(paramMap) ;
		
		return retrunInt ;
	}
	
	/**
	 * 需要决裁的（决裁查看）决裁情况查看
	 * @param request
	 * @return
	 */
	public List getAffirmSalary(HttpServletRequest request){
		List retrunList = new ArrayList() ;
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String cpny_id = admin.getCpnyId();
		if(cpny_id != null && !"".equals(cpny_id)){
			paramMap.put("CPNY_ID", cpny_id);
		}
		String item_no = request.getParameter("NO");
		paramMap.put("ITEM_NO", item_no);
		retrunList = salaryCodeDao.getAffirmSalary(paramMap) ;
		return retrunList ;
	}
	
	/**
	 *需要决裁的（决裁页面）
	 */
	@Override
	public int getAffirmSalaryCnt(HttpServletRequest request) {
		int retrunInt = 0 ;
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String cpny_id = admin.getCpnyId();
		if(cpny_id != null && !"".equals(cpny_id)){
			paramMap.put("CPNY_ID", cpny_id);
		}
		retrunInt = salaryCodeDao.getAffirmSalaryCnt(paramMap) ;
		
		return retrunInt ;
	}

	@Override
	public Object getSalaryCodeInfo(HttpServletRequest request) {
		Object returnObj = new Object() ;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		paramMap.remove("sortname") ;
//		if(paramMap.get("PROJECT_TYPE")=="1" || "1".equals(paramMap.get("PROJECT_TYPE"))){
//			paramMap.put("PROJECT_TYPE", "基础项目");
//		}else if(paramMap.get("PROJECT_TYPE")=="2" || "2".equals(paramMap.get("PROJECT_TYPE"))){
//			paramMap.put("PROJECT_TYPE", "输入项目");
//		}else if(paramMap.get("PROJECT_TYPE")=="3" || "3".equals(paramMap.get("PROJECT_TYPE"))){
//			paramMap.put("PROJECT_TYPE", "计算项目");
//		}
		returnObj = salaryCodeDao.getSalaryCodeInfo(paramMap) ;
		return returnObj ;
	}

	/**
	 * 决裁通过
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int updateAffirmSalaryInfo(HttpServletRequest request){
		String personId = request.getParameter("personId");
		LinkedHashMap<String, Object> paramMap = new LinkedHashMap<String, Object>();
		if(personId == null || "".equals(personId)){
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			paramMap = ObjectBindUtil.getRequestParamData(request) ;
			paramMap.put("UPDATED_BY", admin.getAdminID()) ;
				// 页面提交数据
		}else{
			paramMap.put("UPDATED_BY",personId) ;
			paramMap.put("interCpnyID",request.getParameter("CPNY_ID")) ;
			paramMap.put("CPNY_ID",request.getParameter("CPNY_ID")) ;
			paramMap.put("AFFIRM_DESCR",request.getParameter("AFFIRM_DESCR")) ;
		}
		String AFFIRM_ITEM_NO = request.getParameter("ITEM_NO");
		String flag = request.getParameter("flag");
		
		if(flag.equals("1")){
			paramMap.put("ACTIVITY", 1);
		}else{
			paramMap.put("ACTIVITY", 2);
		}
		if(AFFIRM_ITEM_NO !=null && !"".equals(AFFIRM_ITEM_NO)){
			paramMap.put("ITEM_NO", AFFIRM_ITEM_NO);
		}
		  try {
			  this.salaryCodeDao.updateAffirmSalaryInfo(paramMap) ;
			//审批发送LGEP
			  paramMap.put("FLAG", "1");
			  paramMap.put("APPLY_NO", AFFIRM_ITEM_NO);
			  List list = new ArrayList();
				list = this.attendItemMappingDao.findAfirmorByRelation();
				LinkedHashMap affirmor = new LinkedHashMap();
				if(list != null && list.size()>0){
					affirmor = (LinkedHashMap) list.get(0);
				}
				paramMap.put("AFFIRM_FLAG",paramMap.get("ACTIVITY"));
				paramMap.put("CURRENT_AFFIRM_ID",affirmor.get("PERSON_ID"));
			  sendToLGEP(paramMap);
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
			return 1;
	}
	
	/**
	 * 审批后发送LGEP
	 * @param eventId
	 */
	private void sendToLGEP(LinkedHashMap paramMap){
			LinkedHashMap lgepMap = new LinkedHashMap();
			lgepMap.put("APPLY_TYPE", APPLY_TYPE_NO);
			lgepMap.put("APPLY_NO", paramMap.get("APPLY_NO"));
			if("0".equals(paramMap.get("FLAG").toString())){
				lgepMap.put("AFFIRM_LEVEL", paramMap.get("AFFIRM_LEVEL"));
				lgepMap.put("AFFIRM_FLAG", 1);
			}else{//决裁完成\
				lgepMap.put("FINISH", "FINISH");
				lgepMap.put("AFFIRM_FLAG", paramMap.get("AFFIRM_FLAG"));
				lgepMap.put("AFFIRM_LEVEL", "1");
			}
			lgepMap.put("AFFIRM_EMPID", paramMap.get("CURRENT_AFFIRM_ID"));
			lgepMap.put("CREATED_BY", paramMap.get("CURRENT_AFFIRM_ID"));
			lgepMap.put("AAI_URL", "http://{serverIp}/LGEP/affirm/viewAffirmSalaryItemInfo?LGEP=LGEP&LANGUAGE=zh&personId=123" + "&APPLY_NO=" + paramMap.get("APPLY_NO")+ "&CPNY_ID="
					+ paramMap.get("CPNY_ID"));
			lgepMap.put("AAF_URL", "http://{serverIp}/LGEP/affirm/viewAffirmSalaryItemInfo?LGEP=LGEP&LANGUAGE=zh&personId=123" + "&APPLY_NO=" + paramMap.get("APPLY_NO")+ "&CPNY_ID="
					+ paramMap.get("CPNY_ID"));
			lgepMap.put("ABY_URL", "http://{serverIp}/LGEP/affirm/viewAffirmSalaryItemInfo?LGEP=LGEP&LANGUAGE=zh&personId=" + paramMap.get("CURRENT_AFFIRM_ID") + "&APPLY_NO=" + paramMap.get("APPLY_NO")+ "&CPNY_ID="
					+ paramMap.get("CPNY_ID"));
			lgepMap.put("PRE_AFFIRM_EMPID", paramMap.get("CURRENT_AFFIRM_ID"));
			lgepMap.put("CURRENT_AFFIRM_ID", paramMap.get("CURRENT_AFFIRM_ID"));
			this.affirmInfoToLGEPSer.affirm(lgepMap);
	}

	
	/**
	 * 没有决裁的申请记录可以被删除
	 */
	public int deleteAffirmSalaryInfo(HttpServletRequest request){
		// 附加信息
				LinkedHashMap<String, Object> paramMap = ObjectBindUtil.getRequestParamData(request) ;
				AdminBean admin = SessionUtil.getLoginUserFromSession(request);
				try{
					String CPNY_ID = request.getParameter("CPNY_ID");
					if(CPNY_ID==null || "".equals(CPNY_ID)){
						CPNY_ID = admin.getCpnyId();
					}
					paramMap.put("CPNY_ID", CPNY_ID);
					this.salaryCodeDao.deleteAffirmSalaryInfo(paramMap) ;
					String ITEM_NO = paramMap.get("NO").toString();
					this.deleteSendToLGEP(ITEM_NO);
				}
				catch(Exception e){
					e.printStackTrace() ;
					return 0;
				}
				
				return 1;
	}
	
	/**
	 * 删除发送LGEP
	 * @param eventId
	 */
	private void deleteSendToLGEP(String ITEM_NO){
		LinkedHashMap lgepMap = new LinkedHashMap();
			lgepMap.put("APPLY_NO",ITEM_NO);
			lgepMap.put("APPLY_TYPE", APPLY_TYPE_NO);
			this.affirmInfoToLGEPSer.deleteAffirm(lgepMap);
	}
	
	/**
	 * 查找所有工资项目参数（基本项目参数，输入项目参数，计算项目参数）的信息列表  
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getSalaryCodeMappingList(HttpServletRequest request){
		List retrunList = new ArrayList() ;
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PROJECT_TYPE", request.getParameter("PROJECT_TYPE"));
		String item_no = request.getParameter("ITEM_NO");
		if(paramMap.get("CPNY") == null || "".equals(paramMap.get("CPNY"))){
			paramMap.put("CPNY", admin.getCpnyId());
		}
		if(item_no != null && !"".equals(item_no)){
			paramMap.put("ITEM_NO", item_no);
		}
		String cpny_id = request.getParameter("CPNY_ID");
		if(cpny_id != null && !"".equals(cpny_id)){
			paramMap.put("CPNY_ID", cpny_id);
		}
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				salaryCodeDao.getSalaryCodeMappingList(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}
		else{
			retrunList = salaryCodeDao.getSalaryCodeMappingList(paramMap) ;
		}
		return retrunList ;
	}
	
	/**
	 * 查找所有工资项目参数(基本项目参数，输入项目参数，计算项目参数)的信息的数量
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getSalaryCodeMappingCnt(HttpServletRequest request){
		int retrunInt = 0 ;
		// 页面提交数据
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String item_no = request.getParameter("ITEM_NO");
		if(paramMap.get("CPNY") == null || "".equals(paramMap.get("CPNY"))){
			paramMap.put("CPNY", admin.getCpnyId());
		}
		if(item_no != null && !"".equals(item_no)){
			paramMap.put("ITEM_NO", item_no);
		}
		String cpny_id = request.getParameter("CPNY_ID");
		if(cpny_id != null && !"".equals(cpny_id)){
			paramMap.put("CPNY_ID", cpny_id);
		}
		retrunInt = salaryCodeDao.getSalaryCodeMappingCnt(paramMap) ;
		
		return retrunInt ;
	}
	

	/**
	 * 工资财务代码管理
	 */
	public List getPaItemList(HttpServletRequest request) {
		List list = new ArrayList();
		LinkedHashMap param = ObjectBindUtil.getRequestParamData(request,"seach_");
		list = salaryCodeDao.getPaItemList(param);
		return list;
	}
	
	public List viewPaItemList(HttpServletRequest request) {
		List list = new ArrayList();
		LinkedHashMap param = ObjectBindUtil.getRequestParamData(request,"seach_");
		list = salaryCodeDao.viewPaItemList(param);
		return list;
	}

	@SuppressWarnings("unchecked")
	public int addPaItemInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		int flag = 1;
		try {
			flag = this.salaryCodeDao.addPaItemInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return flag;
	}
	

	@SuppressWarnings("unchecked")
	public int deletePaItemInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			this.salaryCodeDao.deletePaItemInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	

	/**
	 * 工资财务代码公式管理
	 */
	public List getPaItemFormulaList(HttpServletRequest request) {
		List list = new ArrayList();
		LinkedHashMap param = ObjectBindUtil.getRequestParamData(request,"seach_");
		list = salaryCodeDao.getPaItemFormulaList(param);
		return list;
	}
	
	public List viewPaItemFormulaList(HttpServletRequest request) {
		List list = new ArrayList();
		LinkedHashMap param = ObjectBindUtil.getRequestParamData(request,"seach_");
		list = salaryCodeDao.viewPaItemFormulaList(param);
		return list;
	}

	@SuppressWarnings("unchecked")
	public int addPaItemFormulaInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		int flag = 1;
		try {
			flag = this.salaryCodeDao.addPaItemFormulaInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return flag;
	}
	

	@SuppressWarnings("unchecked")
	public int deletePaItemFormulaInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			this.salaryCodeDao.deletePaItemFormulaInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
}
