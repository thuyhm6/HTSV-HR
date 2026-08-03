package com.ait.ess.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.ess.dao.TempEmpDao;
import com.ait.ess.service.TempEmpSer;
import com.ait.pa.dao.tempsale.PaTempSalesDAO;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.CommonException;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

@Service
public class TempEmpSerImpl implements TempEmpSer {
	Logger logger = Logger.getLogger(TempEmpSerImpl.class);

	@Autowired
	private TempEmpDao tempEmpDao;
	@Autowired
	private PaTempSalesDAO paTempSalesDAO;

	/**
	 * 查询信息
	 */
	@SuppressWarnings("unchecked")
	public List viewTempEmpList(HttpServletRequest request,String target) {
		Map param = ObjectBindUtil.getRequestParamData(request,"seach_");
		/*String firstFlag = request.getParameter("firstFlag");
		if (firstFlag ==null || "".equals(firstFlag)) {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd"); 
			Calendar c = Calendar.getInstance();    
			if((request.getParameter("seach_START_DATE")==""||request.getParameter("seach_START_DATE")==null )&& (request.getParameter("seach_END_DATE")==""||request.getParameter("seach_END_DATE")==null)){
				//获取当前年第一天：
				c.add(Calendar.MONTH, -1);
				c.set(Calendar.DAY_OF_MONTH,1);
				String first = format.format(c.getTime());
				param.put("START_DATE",first);
				//获取当前月最后一天：
				c = Calendar.getInstance();  
				c.add(Calendar.MONTH, 0);
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  
				String last = format.format(c.getTime());
				param.put("END_DATE",last);
			}
		}*/
		String empType = StringUtil.checkNull(request.getAttribute("EMP_TYPE"));
		if(!"".equals(empType)){
			param.put("EMP_TYPE", empType);
		}
		String arMonth = StringUtil.checkNull(request.getAttribute("AR_MONTH"));
		if(!"".equals(arMonth)){
			param.put("AR_MONTH", arMonth);
		}
		return tempEmpDao.viewTempEmpList(param,target);
	}
	
	@SuppressWarnings("unchecked")
	public List viewTempEmpListWithparam(HttpServletRequest request,String target, String params) {
		Map param = ObjectBindUtil.getRequestParamData(request,"seach_");
		param.put("DEPTNO_Multi", params);
		return tempEmpDao.viewTempEmpList(param,target);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int addFixOtInfo(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMapo = ObjectBindUtil.getRequestParamData(request);
		try {
			this.tempEmpDao.addFixOtInfo(paramMapo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 查询信息
	 */
	@SuppressWarnings("unchecked")
	public List viewTempEmpList(Map param,String target) {
		return tempEmpDao.viewTempEmpList(param,target);
	}

	/**
	 * 查询信息数量
	 */
	@SuppressWarnings("unchecked")
	public int viewTempEmpCnt(HttpServletRequest request,String target) {
		Map param = ObjectBindUtil.getRequestParamData(request,"seach_");
		String arMonth = StringUtil.checkNull(request.getAttribute("AR_MONTH"));
		if(!"".equals(arMonth)){
			param.put("AR_MONTH", arMonth);
		}
		return tempEmpDao.viewTempEmpCnt(param,target);
	}

	/**
	 * 查询信息数量
	 */
	@SuppressWarnings("unchecked")
	public int viewTempEmpCnt(Map param,String target) {
		return tempEmpDao.viewTempEmpCnt(param,target);
	}
	
	/**
	 * 新增信息
	 */
	@SuppressWarnings("unchecked")
	public int addTempEmp(HttpServletRequest request,String target) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		try {
			String arMonth = StringUtil.checkNull(request.getAttribute("AR_MONTH"));
			if(!"".equals(arMonth)){
				paramMap.put("AR_MONTH", arMonth);
			}
			this.tempEmpDao.addTempEmp(paramMap,target);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 新增信息
	 */
	@SuppressWarnings("unchecked")
	public int addHrTempEmp(HttpServletRequest request,String target) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		try {
			this.tempEmpDao.addHrTempEmp(paramMap,target);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 新增信息
	 */
	@SuppressWarnings("unchecked")
	public int addActivityInfo(HttpServletRequest request,String target) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		try {
			this.tempEmpDao.addActivityInfo(paramMap,target);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 新增信息json
	 */
	@SuppressWarnings("unchecked")
	public int addTempEmpByJson(HttpServletRequest request,String target) {
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			this.tempEmpDao.addTempEmpByJson(dataList,target);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 新增信息json
	 */
	@SuppressWarnings("unchecked")
	public int addTempEmpByJsonPro(HttpServletRequest request,String target) {
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			this.tempEmpDao.addTempEmpByJsonPro(dataList,target);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 批量删除考勤
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int deleteFixOtInfo(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		try {
			this.tempEmpDao.deleteFixOtInfo(this.encapsulatioFixOtInfoForBatch(request,"c1"));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	

	/**
	 * 批量删除考勤
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int deleteNullFixOtInfo(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		try {
			Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
			this.tempEmpDao.deleteNullFixOtInfo(paramMap);
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
	private List encapsulatioFixOtInfoForBatch(HttpServletRequest request,String type) {
		List list = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		try {
			String[] paramData = request.getParameterValues(type);
			for (int i = 0; i < paramData.length; i++) {
				LinkedHashMap map = ObjectBindUtil.getRequestParamData(request);
				map.put("UPDATED_BY", admin.getPersonId());
				map.put("UPDATED_IP", admin.getAdminIP());
				map.put("SEQ", paramData[i]);
				map.put("CPNY_ID", admin.getCpnyId());
				list.add(map);
			}
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 新增信息json
	 */
	@SuppressWarnings("unchecked")
	public String addEnsInfoProcedure(HttpServletRequest request,String target) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		String message = "OK";
		try {
			String arMonth = StringUtil.checkNull(request.getAttribute("AR_MONTH"));
			if(!"".equals(arMonth)){
				paramMap.put("AR_MONTH", arMonth);
			}
			message = this.tempEmpDao.addEnsInfoProcedure(paramMap,target);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}

	/**
	 * 新增概要信息
	 */
	@SuppressWarnings("unchecked")
	public int addTempEmpResumeInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		try {
			this.tempEmpDao.addTempEmpResumeInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 新增信息
	 */
	@SuppressWarnings("unchecked")
	public int addRegPersonalTarget(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		try {
			List list = new ArrayList();
			String[] itemName = request.getParameterValues("ITEM_NAME");
			String[] itemContent = request.getParameterValues("ITEM_CONTENT");
			String[] itemScore = request.getParameterValues("ITEM_SCORE");
			String[] startDate = request.getParameterValues("START_DATE");
			String[] endDate = request.getParameterValues("END_DATE");
			if(itemName != null && itemName.length > 0){
				for(int i=0;i<itemName.length; i++){
					Map map = new LinkedHashMap();
					map.put("ITEM_NAME", itemName[i]);
					map.put("ITEM_CONTENT", itemContent[i]);
					map.put("ITEM_SCORE", itemScore[i]);
					map.put("START_DATE", startDate == null ? "" : startDate[i]);
					map.put("END_DATE", endDate == null ? "" : endDate[i]);
					map.put("RESUME_SEQ", paramMap.get("RESUME_SEQ"));
					map.put("EVS_OBJECT_SEQ", paramMap.get("EVS_OBJECT_SEQ"));
					map.put("adminIP", paramMap.get("adminIP"));
					map.put("adminID", paramMap.get("adminID"));
					list.add(map);
				}
			}
			this.tempEmpDao.addSSTTempEmpItem(list,paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 新增自我评价信息
	 */
	@SuppressWarnings("unchecked")
	public int addTempEmpBySelf(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			this.tempEmpDao.addTempEmpBySelf(dataList,paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getEmpListForFix(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		
		String EmpOffice = request.getParameter("seach_EmpOffice")==null?"15119":request.getParameter("seach_EmpOffice");
		
		paramMap.put("EmpOffice", EmpOffice);
		
		retrunList = this.tempEmpDao.getEmpListForFix(paramMap , UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		
		return retrunList;
	}
	
	/**
	 * 获得人员列表(view emp List)
	 * @param request
	 * @return int
	 * @throws 
	 */
	@Override
	public int getEmpListForFixCnt(HttpServletRequest request) {
		// TODO Auto-generated method stub
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		
		String EmpOffice = request.getParameter("seach_EmpOffice")==null?"15119":request.getParameter("seach_EmpOffice");
		
		paramMap.put("EmpOffice", EmpOffice);
		
		return this.tempEmpDao.getEmpListForFixCnt(paramMap) ;
	}

	/**
	 * TSTO一次考评
	 */
	@SuppressWarnings("unchecked")
	public int addTempEmpDetailInfoTSTO(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			this.tempEmpDao.addTempEmpDetailInfoTSTO(dataList,paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 新增自我评价信息
	 */
	@SuppressWarnings("unchecked")
	public int addTempEmpBySelfTSTO(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		try {
			String[] ITEM_CONTENT = request.getParameterValues("ITEM_CONTENT");
			String[] ITEM_SCORE = request.getParameterValues("ITEM_SCORE");
			String[] EVS_SCORE = request.getParameterValues("EVS_SCORE");
			List dataList = new ArrayList();
			if(ITEM_CONTENT != null && ITEM_CONTENT.length > 0){
				for(int i=0;i < ITEM_CONTENT.length;i++ ){
					Map map = new LinkedHashMap();
					map.put("ITEM_CONTENT", ITEM_CONTENT[i]);
					map.put("ITEM_SCORE", ITEM_SCORE[i]);
					map.put("EVS_SCORE", EVS_SCORE[i]);
					map.put("EVS_OBJECT_SEQ", paramMap.get("EVS_OBJECT_SEQ"));
					map.put("RESUME_SEQ", paramMap.get("RESUME_SEQ"));
					map.put("adminID", paramMap.get("adminID"));
					map.put("adminIP", paramMap.get("adminIP"));
					map.put("interCpnyID", paramMap.get("interCpnyID"));
					dataList.add(map);
				}
			}
			this.tempEmpDao.addTempEmpBySelfTSTO(dataList,paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 保存力量自我评价信息
	 */
	@SuppressWarnings("unchecked")
	public int addTempEmpBySelfTSTOAbility(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			this.tempEmpDao.addTempEmpBySelfTSTOAbility(dataList,paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 保存评价人
	 */
	@SuppressWarnings("unchecked")
	public int saveTempEmpObjectInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			this.tempEmpDao.saveTempEmpObjectInfo(dataList,paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 力量1次考核
	 */
	@SuppressWarnings("unchecked")
	public int addTempEmpDetailInfoTSTOAbility(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			this.tempEmpDao.addTempEmpDetailInfoTSTOAbility(dataList,paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 保存力量自我评价信息
	 */
	@SuppressWarnings("unchecked")
	public int addTempEmpBySelfSSTAbility(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			this.tempEmpDao.addTempEmpBySelfTSTOAbility(dataList,paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 目标确认
	 */
	@SuppressWarnings("unchecked")
	public int modifyObjectActivityForAffirm(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			this.tempEmpDao.modifyObjectActivityForAffirm(dataList,paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 新增考核对象
	 */
	@SuppressWarnings("unchecked")
	public String addTempEmpObject(HttpServletRequest request) {
		Map param = ObjectBindUtil.getRequestParamData(request,"seach_");
		String result = "OK";
		try {
			result = tempEmpDao.addTempEmpObject(param);
		} catch (Exception e) {
			e.printStackTrace();
			return "ERROR";
		}
		return result;
	}
	
	/**
	 * 删除对象
	 */
	@SuppressWarnings("unchecked")
	public int deleteTempEmpByJson(HttpServletRequest request) {
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			this.tempEmpDao.deleteTempEmpByJson(dataList);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 保存评价人
	 */
	@SuppressWarnings("unchecked")
	public int saveTempEmpObjectConfirmInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			this.tempEmpDao.saveTempEmpObjectConfirmInfo(dataList,paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 新增信息
	 */
	@SuppressWarnings("unchecked")
	public int addRegPersonalTargetProbation(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		try {
			List list = new ArrayList();
			String[] itemName = request.getParameterValues("ITEM_NAME");
			String[] itemContent = request.getParameterValues("ITEM_CONTENT");
			String[] itemScore = request.getParameterValues("ITEM_SCORE");
			if(itemName != null && itemName.length > 0){
				for(int i=0;i<itemName.length; i++){
					Map map = new LinkedHashMap();
					map.put("ITEM_NAME", itemName[i]);
					map.put("ITEM_CONTENT", itemContent[i]);
					map.put("ITEM_SCORE", itemScore[i]);
					map.put("RESUME_SEQ", paramMap.get("RESUME_SEQ"));
					map.put("EVS_OBJECT_SEQ", paramMap.get("EVS_OBJECT_SEQ"));
					map.put("adminIP", paramMap.get("adminIP"));
					map.put("adminID", paramMap.get("adminID"));
					list.add(map);
				}
			}
			
			//封装考核者
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			List affirmList = new ArrayList();
			String[] affirmIdStart = request.getParameterValues("AFFIRMOR_ID");
			String[] approvTypeIndex = request.getParameterValues("approvTypeIndex");
			if(affirmIdStart == null || affirmIdStart.length == 0){
				// 未给该员工设置决裁者时
				throw new CommonException("请先设置考核者");// alert.ess.approval.no_approver
			}
			List<String> affirmId=new ArrayList();
			List<String> approvTypeList=new ArrayList();
			for (int i = 0;i< affirmIdStart.length;i++) {
				String strings = affirmIdStart[i];
		    	if(!strings.equals("")){
		    		if(admin.getAdminID().equals(strings)){
		    			throw new CommonException("不能把自己设置为考核者");// alert.ess.approval.no_approver
		    		}
		    		affirmId.add(strings);
		    		approvTypeList.add(request.getParameter("approvType" + approvTypeIndex[i]));
		    	}
		    }
			if(affirmId == null || affirmId.size()==0){
				throw new CommonException("请先设置决裁者");// alert.ess.approval.no_approver
			}
			//添加决裁者 
			for(int i=0; i<affirmId.size(); i++){
				LinkedHashMap affirmMap = new LinkedHashMap() ;
				affirmMap.put("AFFIRMOR_ID", affirmId.get(i));
				affirmMap.put("AFFIRM_LEVEL", i+1);
				affirmMap.put("AFFIRM_TYPE", approvTypeList.get(i));
				affirmList.add(affirmMap);
			}
			paramMap.put("affirmList", affirmList);
			
			this.tempEmpDao.addSSTTempEmpItemProbation(list,paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 保存试用期考核信息
	 */
	@SuppressWarnings("unchecked")
	public int addProbationTempEmpAffirmInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			this.tempEmpDao.addProbationTempEmpAffirmInfo(dataList,paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 试用期考核结果保存
	 */
	@SuppressWarnings("unchecked")
	public int saveProbationResult(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			this.tempEmpDao.saveProbationResult(dataList,paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 明细excel信息提交
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String valImportExcelData(HttpServletRequest request){

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PR_NAME", "PKG_TEMP_EMP_PROCESS.PR_VALID_SHIFT_DATA");
		try {
			return this.paTempSalesDAO.importFromExcel(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	/**
	 * 明细excel信息提交
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String submitImportExcelData(HttpServletRequest request){

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PR_NAME", "PKG_TEMP_EMP_PROCESS.PR_IMPORT_SHIFT_DATA");
		try {
			return this.paTempSalesDAO.importFromExcel(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	/**
	 * 排班信息添加
	 */
	@SuppressWarnings("unchecked")
	public int addShopShiftByJson(HttpServletRequest request,String target) {
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			this.tempEmpDao.addShopShiftByJson(dataList,target);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public int addMonthDetaiByJson(HttpServletRequest request,String target) {
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			this.tempEmpDao.addMonthDetaiByJson(dataList,target);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 员工调店
	 */
	@SuppressWarnings("unchecked")
	public int addChangeShopInfo(HttpServletRequest request,String target) {
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			this.tempEmpDao.addChangeShopInfo(dataList,target);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 删除员工调店
	 */
	@SuppressWarnings("unchecked")
	public int deleteChangeShopInfo(HttpServletRequest request,String target) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		try {
			this.tempEmpDao.deleteChangeShopInfo(paramMap,target);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List viewFactoryShiftExcelList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		int days = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
		Calendar calendar = Calendar.getInstance();  
	    try {
			calendar.setTime(sdf.parse(paramMap.get("DDATE_STR").toString()));
		    days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH); 
			paramMap.put("DAY",days);
			paramMap.put("MONTH", paramMap.get("DDATE_STR").toString().substring(0, 7));
			returnList = tempEmpDao.viewFactoryShiftExcelList(paramMap) ;
	    } catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return returnList ;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List viewShopShiftExcelList(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		int days = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
		Calendar calendar = Calendar.getInstance();  
	    try {
			calendar.setTime(sdf.parse(paramMap.get("DDATE_STR").toString()));
		    days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH); 
			paramMap.put("DAY",days);
			paramMap.put("MONTH", paramMap.get("DDATE_STR").toString().substring(0, 7));
			returnList = tempEmpDao.viewShopShiftExcelList(paramMap) ;
	    } catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List viewPaNotImport(HttpServletRequest request) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		int days = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
		Calendar calendar = Calendar.getInstance();  
	 
			returnList = tempEmpDao.viewPaNotImport(paramMap) ;
	
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List viewAllShiftExcelList(HttpServletRequest request,String postFamily) {
		List returnList = new ArrayList() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		int days = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
		Calendar calendar = Calendar.getInstance();  
	    try {
			calendar.setTime(sdf.parse(paramMap.get("DDATE_STR").toString()));
		    days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH); 
			paramMap.put("DAY",days);
			paramMap.put("MONTH", paramMap.get("DDATE_STR").toString().substring(0, 7));
			paramMap.put("postFamily", postFamily);
			returnList = tempEmpDao.viewAllShiftExcelList(paramMap) ;
	    } catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return returnList ;
	}
	
	/**
	 * 查询月明细信息
	 * 2018/1/22 lipeng
	 */
	@SuppressWarnings("unchecked")
	public List getMonthDetailList(HttpServletRequest request) {
		Map param = ObjectBindUtil.getRequestParamData(request,"seach_");
		/*String firstFlag = request.getParameter("firstFlag");
		if (firstFlag ==null || "".equals(firstFlag)) {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd"); 
			Calendar c = Calendar.getInstance();    
			if((request.getParameter("seach_START_DATE")==""||request.getParameter("seach_START_DATE")==null )&& (request.getParameter("seach_END_DATE")==""||request.getParameter("seach_END_DATE")==null)){
				//获取当前年第一天：
				c.add(Calendar.MONTH, -1);
				c.set(Calendar.DAY_OF_MONTH,1);
				String first = format.format(c.getTime());
				param.put("START_DATE",first);
				//获取当前月最后一天：
				c = Calendar.getInstance();  
				c.add(Calendar.MONTH, 0);
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  
				String last = format.format(c.getTime());
				param.put("END_DATE",last);
			}
		}*/
		String empType = StringUtil.checkNull(request.getAttribute("EMP_TYPE"));
		if(!"".equals(empType)){
			param.put("EMP_TYPE", empType);
		}
		String arMonth = StringUtil.checkNull(request.getAttribute("AR_MONTH"));
		if(!"".equals(arMonth)){
			param.put("AR_MONTH", arMonth);
		}
		return tempEmpDao.getMonthDetailList(param);
	}
	
	/**
	 * 查询信息数量
	 */
	@SuppressWarnings("unchecked")
	public int getMonthDetailListCnt(HttpServletRequest request) {
		Map param = ObjectBindUtil.getRequestParamData(request,"seach_");
		String arMonth = StringUtil.checkNull(request.getAttribute("AR_MONTH"));
		if(!"".equals(arMonth)){
			param.put("AR_MONTH", arMonth);
		}
		return tempEmpDao.getMonthDetailListCnt(param);
	}
}
