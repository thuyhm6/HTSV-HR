package com.ait.ess.service.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ait.ar.dao.ArMonthCalculateDao;
import com.ait.ess.dao.AnnualadjustmentInfoDao;
import com.ait.ess.dao.ArMacRecordApplyDao;
import com.ait.ess.dao.InfoApplyDao;
import com.ait.ess.dao.InfoApplyLeaveDao;
import com.ait.ess.dao.PersonInfoDao;
import com.ait.ess.dao.ViewApplyDao;
import com.ait.ess.service.AnnualadjustmentInfoSer;
import com.ait.ess.service.InfoApplySer;
import com.ait.pa.dao.tempsale.PaTempSalesDAO;
import com.ait.sys.bean.AdminBean;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.AuthorityUtil;
import com.ait.web.util.CommonException;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

@Service
public class AnnualadjustmentInfoSerImpl  implements AnnualadjustmentInfoSer {

	Logger logger = Logger.getLogger(InfoApplySerImpl.class);
	
	@Autowired
	private InfoApplyDao infoApplyDao;

	@Autowired
	private InfoApplySer infoApplySer;
	@Autowired
	private AnnualadjustmentInfoDao annualadjustmentInfoDao;

	@Autowired
	private InfoApplyLeaveDao infoApplyLeaveDao;
	@Autowired
	private PaTempSalesDAO paTempSalesDAO;

	@Autowired
	private AuthorityUtil authorityUtil;
	
	
	
	/**
	 * 查询年假福利年假天数 
	 * 
	 * @param request
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List retrieveNianjiaFuli(HttpServletRequest request)
			throws Exception {
		List list =  new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		String personId = request.getParameter("PERSON_ID");
		if (personId == null || "".equals(personId)) {
			personId = admin.getAdminID();
		}

		String date = new SimpleDateFormat("yyyy").format(new Date());
		paramMap.put("VAC_ID", date);
		paramMap.put("PERSON_ID", personId);
		paramMap.put("CPNYID", admin.getCpnyId() );
		return this.annualadjustmentInfoDao.retrieveNianjiaFuli(paramMap);

	}
	
	
	/**
	 * 添加年休假申请信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int addAnnualadjustmentApply(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List batchOtApplyList = new ArrayList();
		LinkedHashMap paramMap = this.getLinkedMapByRequest(request);
		//获取页面参数、并剔除为空的参数
		String personId = paramMap.get("PERSON_ID")!=null?paramMap.get("PERSON_ID").toString():"";
		String applyTypeNo = paramMap.get("APPLY_TYPE_NO")!=null?paramMap.get("APPLY_TYPE_NO").toString():"";
		String applyTanShu = paramMap.get("FULINIANJIATIAOZHENGTIANSHU")!=null?paramMap.get("FULINIANJIATIAOZHENGTIANSHU").toString():"";
	
		String applyDate = paramMap.get("SHENQINGDAY")!=null?paramMap.get("SHENQINGDAY").toString():"";
		String APPLY_REMARK = paramMap.get("APPLY_REMARK")!=null?paramMap.get("APPLY_REMARK").toString():"";
		String FILE_URL =  StringUtil.checkNull(paramMap.get("fileUrl"));
		String FILE_NAME = StringUtil.checkNull(paramMap.get("fileName"));
		
		LinkedHashMap dateMap = new LinkedHashMap();
		dateMap = this.getLinkedMapByRequest(request, dateMap);
		dateMap.put("CPNY_ID", admin.getCpnyId());
		dateMap.put("PERSON_ID", personId);
		dateMap.put("APPLY_TYPE_NO", applyTypeNo);
		dateMap.put("applyTanShu", applyTanShu);
		dateMap.put("APPLY_DATE", applyDate);
		dateMap.put("APPLY_REMARK", APPLY_REMARK);
		dateMap.put("FILE_NAME", FILE_NAME);
		dateMap.put("FILE_URL", FILE_URL);

		//页面添加的决裁者列表
		List affirmList = new ArrayList();
		String[] affirmId = request.getParameterValues("AFFIRMOR_ID");
		if(affirmId == null || affirmId.length == 0){
			// 未给该员工设置决裁者时
			throw new CommonException("请先设置决裁者");// alert.ess.approval.no_approver
		}
		//添加决裁者
		int affirmLevel = 1;
		for(int i=0; i<affirmId.length; i++){
			LinkedHashMap affirmMap = new LinkedHashMap() ;
			if(affirmId[i] != null && !"".equals(affirmId[i])){
				affirmMap.put("AFFIRMOR_ID", affirmId[i]);
				affirmMap.put("AFFIRM_LEVEL", affirmLevel++ );
				affirmList.add(affirmMap);
			}
		}
		dateMap.put("affirmList", affirmList);
		
		LinkedHashMap otMap = this.preAddArMacRecordApply(dateMap, admin.getLanguage(),request);
		batchOtApplyList.add(otMap);
		this.annualadjustmentInfoDao.addAnnualadjustmentInBatch(batchOtApplyList);
		return 1;
	}
	
	
	public int delAnnApplyInBatch(HttpServletRequest request)
			throws Exception {
		try {
			// 批量封装加班申请数据并处理
			List data = this.encapsulationApplyNoListForBatch(request);
			annualadjustmentInfoDao.delAnnApplyInBatch(data);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
		}

	

	@SuppressWarnings("unchecked")
	private List encapsulationApplyNoListForBatch(HttpServletRequest request) {
		List list = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		try {
			String[] paramData = request.getParameterValues("c1");
			String applytype = request.getParameter("APPLY_TYPE");
			for (int i = 0; i < paramData.length; i++) {
				LinkedHashMap map = new LinkedHashMap();
				map.put("UPDATED_BY", admin.getPersonId());
				map.put("APPLY_NO", paramData[i]);
				map.put("APPLY_TYPE", applytype);
				
				list.add(map);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 查看年假调整List
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getAnnualadjustmentAffirmList(HttpServletRequest request,String batchFlag)throws Exception {
		List list = new ArrayList();
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request);
		// 页面提交数据
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("batchFlag", batchFlag);//是否批量申请的标示
		if(paramMap.get("FLAG") == null || !"1".equals(paramMap.get("FLAG").toString())){
			paramMap.put("KEY", admin.getEmpID());
		}
		//考勤担当权限
		int authority = authorityUtil.isArUser(admin.getPersonId());
		if(authority == 1){
			paramMap.put("authority", "ArUser");
		}
		if (UiUtil.getPageNum(request) > 0) {
			list = annualadjustmentInfoDao.getAnnualadjustmentAffirmList(paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = annualadjustmentInfoDao.getAnnualadjustmentAffirmList(paramMap);
		}
		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				LinkedHashMap returnMap = (LinkedHashMap)list.get(i);
				returnMap.put("APPLY_TYPE", "216691");
				List fileList = infoApplyLeaveDao.getEssFileList(returnMap);
				returnMap.put("fileList", fileList);
			}
		}
		return  list;
	}
	
	
	
	/**
	 * 批量删除申请(batch delete overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int delAnnuApplyInBatch(HttpServletRequest request)
			throws Exception {
		try {
			// 批量封装加班申请数据并处理
			this.annualadjustmentInfoDao.delAnnuApplyInBatch(this.encapsulationApplyNoListForBatch(request));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	
	/**
	 * 查看决裁年假调整List
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getAnnualadjustmentAffirmapplyList(HttpServletRequest request)throws Exception {
		List list = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request);
		Calendar year = Calendar.getInstance(); 
		if(paramMap.get("START_DATE") != null  && !"".equals(paramMap.get("START_DATE")) )
		  paramMap.put("VAC_ID", paramMap.get("START_DATE").toString().substring(0, 4));
		else
		  paramMap.put("VAC_ID", year.get(Calendar.YEAR));
		
		paramMap.put("JUECAI_ZHUANGTAI", StringUtil.checkNull(paramMap.get("JUECAI_ZHUANGTAI"),"-1"));
		if (UiUtil.getPageNum(request) > 0) {
			list = annualadjustmentInfoDao.getAnnualadjustmentAffirmapplyList(paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			list = annualadjustmentInfoDao.getAnnualadjustmentAffirmapplyList(paramMap);
		}
		return  list;
	}
	/**
	 * 查看年假调整List
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getAnnualadjustmentAffirmapplyListCnt(HttpServletRequest request)throws Exception {
		 
		// 页面提交数据
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request);
		Calendar year = Calendar.getInstance(); 
		if(paramMap.get("START_DATE") != null  && !"".equals(paramMap.get("START_DATE")) )
		  paramMap.put("VAC_ID", paramMap.get("START_DATE").toString().substring(0, 4));
		else
		  paramMap.put("VAC_ID", year.get(Calendar.YEAR));
		paramMap.put("JUECAI_ZHUANGTAI", StringUtil.checkNull(paramMap.get("JUECAI_ZHUANGTAI"),"-1"));
		return  annualadjustmentInfoDao.getAnnualadjustmentAffirmapplyListCnt(paramMap);
	}
	
	
	
	/**
	 * 查看年假调整List
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getAnnualadjustmentAffirmListCnt(HttpServletRequest request,String batchFlag)throws Exception {
		 
		// 页面提交数据
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request);
		paramMap.put("batchFlag", batchFlag);//是否批量申请的标示
		if(paramMap.get("FLAG") == null || !"1".equals(paramMap.get("FLAG").toString())){
			paramMap.put("KEY", admin.getEmpID());
		}
		//考勤担当权限
		int authority = authorityUtil.isArUser(admin.getPersonId());
		if(authority == 1){
			paramMap.put("authority", "ArUser");
		}
		return  annualadjustmentInfoDao.getAnnualadjustmentAffirmListCnt(paramMap);
	}
	
	
	/**
	 * 封装要插入的年休假调整申请数据
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private LinkedHashMap preAddArMacRecordApply(LinkedHashMap paramMap,String language,HttpServletRequest request) throws Exception {
		LinkedHashMap returnMap = new LinkedHashMap();
		List affirmerList = (List)paramMap.get("affirmList");

		LinkedHashMap affirmerMap = (LinkedHashMap) affirmerList.get(0);
		LinkedHashMap personMap = (LinkedHashMap) this.infoApplyDao.getPersonInfoByPersonId(paramMap);
		returnMap.put("personMap", personMap);
		paramMap.put("CURRENT_AFFIRM_ID", affirmerMap.get("AFFIRMOR_ID"));
		paramMap.put("ACTIVITY", "0");
		returnMap.put("PARAM_MAP", paramMap);
		returnMap.put("DISTINCT_LIST", affirmerList);
		
		return returnMap;
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
		//AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		//String cpnyId=admin.getCpnyId();
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
		
		//通过参数来查询决裁者
		String applyTypeNo = (String) paramMap.get("APPLY_TYPE_NO");
		String personId = (String)paramMap.get("PERSON_ID");
		String language = (String)paramMap.get("LANGUAGE");
		List affirmerList =  this.infoApplySer.getAffirmorListByString(applyTypeNo, personId, null, null,language);
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
	 * 取得审批人列表:1.先取特殊设置人员的决裁者;2.再取特殊设置部门的决裁者;3.最后按流程取决裁者 (get approver list:1
	 * get approver by special-person's-approver setup.2 get approver by
	 * special-department's-approver setup.3 get approver by approve-flow)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private List getAffirmorListByMap(LinkedHashMap paramMap) throws Exception {
		LinkedHashMap personMap = (LinkedHashMap) this.infoApplyDao.getPersonInfoByPersonId(paramMap);
		paramMap.put("CPNY_ID", personMap.get("CPNY_ID"));
		paramMap.put("DEPT_NO", personMap.get("DEPT_NO"));
		paramMap.put("DUTY_NO", personMap.get("DUTY_NO"));
		// 先取特殊设置人员的决裁者(get approver by special-person's-approver setup first)
		List<LinkedHashMap> spePersonList = this.infoApplyDao.getAffirmorListByPersonID(paramMap);
		if (spePersonList.size() == 0) {
			// 再取特殊设置部门的决裁者(get approver by special-department's-approver setup second)
			List<LinkedHashMap> speDeptList = this.infoApplyDao.getAffirmorListByDeptNo(paramMap);
			if (speDeptList.size() == 0) {
				// 最后按流程取决裁者 (get approver by approve-flow last)
				return this.infoApplyDao.getAffirmorListByNormal(paramMap);
			} else {
				return speDeptList;
			}
		} else {
			return spePersonList;
		}
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
		return paramMap;
	}
	
	/**
	 * 通过request请求封装查询条件(get search conditions from request)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private LinkedHashMap getLinkedMapByRequestForSearch(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("UPDATED_BY", admin.getPersonId());
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return paramMap;
	}
	
	/**
	 * 通过request请求封装查询条件(get conditions from request)
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private LinkedHashMap getLinkedMapByRequest(HttpServletRequest request,LinkedHashMap paramMap) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("UPDATED_BY", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return paramMap;
	}
	

	/**
		 * 批量添加考勤异常申请(batch add egression apply)
		 * 
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@Override
		public int addBatchCwaAbnormalApply(HttpServletRequest request)
				throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			// 页面参数
			int num = 1;
			String jsonString = request.getParameter("jsonData");
			List<LinkedHashMap<String, Object>> arDetailInfoList = ObjectBindUtil
					.getRequestJsonData(jsonString);
			Map paramMapo = ObjectBindUtil.getRequestParamData(request);
			/*if("HTSV".equals(paramMapo.get("interCpnyID"))||"SPC_DL".equals(paramMapo.get("interCpnyID"))||"HAE".equals(paramMapo.get("interCpnyID"))){
				int monthTime = infoApplyLeaveDao.arMonthTime(paramMapo);
				if(monthTime>=3){
					throw new Exception("本月申请超过3次,不能进行申请!");
				}
			}*/
			List batchOtApplyList = new ArrayList();
			boolean bool = false;
			for (int j = 0; j < arDetailInfoList.size(); j++) {
				LinkedHashMap paramMap = arDetailInfoList.get(j);
				paramMapo.put("PERSON_ID", paramMap.get("APPLY_PERSON_ID")); 
				//判断当前申请考勤日期是否在当前需计算考勤月里
				String flag = infoApplyLeaveDao.arValidLastMonth(paramMapo);
				if("OK".equals(flag) ){
					paramMap.put("interLanguage", admin.getLanguage());
					paramMap.put("PERSON_ID", paramMap.get("APPLY_PERSON_ID"));
					paramMap.put("adminID", paramMapo.get("adminID"));
					paramMap.put("adminIP", paramMapo.get("adminIP"));
					
					List affirmorList = this.infoApplySer.getAffirmorListByString("218197",paramMapo.get("PERSON_ID").toString(),"218197","0",admin.getLanguage());
					if(affirmorList.size()<=0){
						throw new Exception(paramMap.get("APPLY_EMP_ID") + " " + TipMessage.getTipMessage("ar.alert.message.viewApplyAttenanceManagentInfoList.noAffirmNoApply", request));//" 没有审批者,不能申请"
					}
					List affirmerList = new ArrayList();
					for(int i=0;i<affirmorList.size();i++){
						if(!"".equals(affirmorList.get(i))){
							LinkedHashMap map = (LinkedHashMap) affirmorList.get(i);
							LinkedHashMap affirmMap = new LinkedHashMap();
							affirmMap.put("AFFIRM_LEVEL", map.get("AFFIRM_LEVEL"));
							affirmMap.put("AFFIRMOR_ID", map.get("AFFIRMOR_ID"));
							affirmMap.put("PERSON_ID",paramMap.get("APPLY_PERSON_ID"));
							affirmerList.add(affirmMap);
						}
					}
					paramMap.put("affirmList", affirmerList);
					LinkedHashMap otMap = this.preAddArMacRecordApply(paramMap, admin.getLanguage(),request);
					batchOtApplyList.add(otMap);
					bool = true;
				}else{
					num = 2;
					throw new Exception(paramMap.get("APPLY_EMP_ID") + flag);
				}
			}
			
			if(bool){
				annualadjustmentInfoDao.addBatchCwaAbnormalApplyBatch(batchOtApplyList);
			}
			
			return num;
		}
		
		/**
		 * 批量添加个人考勤异常申请(add egression apply AnyApprover)
		 * 
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@Override
		public int addAbnormalApplyByAnyApprover(HttpServletRequest request) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			// 页面参数
			int num = 1;
			String jsonString = request.getParameter("jsonData");
			List<LinkedHashMap<String, Object>> arDetailInfoList = ObjectBindUtil
					.getRequestJsonData(jsonString);
			
			String affirmJsonString = request.getParameter("affirmJsonData");
			List<LinkedHashMap<String, Object>> affirmorList = ObjectBindUtil
					.getRequestJsonData(affirmJsonString);
			Map paramMapo = ObjectBindUtil.getRequestParamData(request);
			/*if("HTSV".equals(paramMapo.get("interCpnyID"))||"SPC_DL".equals(paramMapo.get("interCpnyID"))||"HAE".equals(paramMapo.get("interCpnyID"))){
				int monthTime = infoApplyLeaveDao.arMonthTime(paramMapo);
				if(monthTime>=3){
					throw new Exception("本月申请超过3次,不能进行申请!");
				}
			}*/
			List batchOtApplyList = new ArrayList();
			boolean bool = false;
			for (int j = 0; j < arDetailInfoList.size(); j++) {
				LinkedHashMap paramMap = arDetailInfoList.get(j);
				paramMapo.put("PERSON_ID", paramMap.get("APPLY_PERSON_ID")); 
				//判断当前申请考勤日期是否在当前需计算考勤月里
				String flag = infoApplyLeaveDao.arValidLastMonth(paramMapo);
				if("OK".equals(flag) ){
					paramMap.put("interLanguage", admin.getLanguage());
					paramMap.put("PERSON_ID", paramMap.get("APPLY_PERSON_ID"));
					paramMap.put("adminID", paramMapo.get("adminID"));
					paramMap.put("adminIP", paramMapo.get("adminIP"));
					
					//List affirmorList = this.infoApplySer.getAffirmorListByString("218197",paramMapo.get("PERSON_ID").toString(),"218197","0",admin.getLanguage());
					if(affirmorList.size()<=0){
						throw new Exception(paramMap.get("APPLY_EMP_ID") + " " + TipMessage.getTipMessage("ar.alert.message.viewApplyAttenanceManagentInfoList.noAffirmNoApply", request));//" 没有审批者,不能申请"
					}
					List affirmerList = new ArrayList();
					for(int i=0;i<affirmorList.size();i++){
						if(!"".equals(affirmorList.get(i))){
							LinkedHashMap map = (LinkedHashMap) affirmorList.get(i);
							LinkedHashMap affirmMap = new LinkedHashMap();
							affirmMap.put("AFFIRM_LEVEL", map.get("AFFIRM_LEVEL"));
							affirmMap.put("AFFIRMOR_ID", map.get("AFFIRMOR_ID"));
							affirmMap.put("PERSON_ID",paramMap.get("APPLY_PERSON_ID"));
							affirmerList.add(affirmMap);
						}
					}
					paramMap.put("affirmList", affirmerList);
					LinkedHashMap otMap = this.preAddArMacRecordApply(paramMap, admin.getLanguage(),request);
					batchOtApplyList.add(otMap);
					bool = true;
				}else{
					num = 2;
					throw new Exception(paramMap.get("APPLY_EMP_ID") + flag);
				}
			}
			
			if(bool){
				annualadjustmentInfoDao.addBatchCwaAbnormalApplyBatch(batchOtApplyList);
			}
			
			return num;
		}
		
		/**
		 * 批量删除申请(batch delete overtime apply)
		 * 
		 * @param request
		 * @return
		 * @throws Exception
		 */
		public int delCwaAbnormalApplyInfo(HttpServletRequest request)
				throws Exception {
			try {
				// 批量封装加班申请数据并处理
				this.annualadjustmentInfoDao.delCwaAbnormalApplyInfo(this.encapsulationApplyNoListForBatch(request));
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
			return 1;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public List getCwaAbnormalApplyInfoList(HttpServletRequest request,String batchFlag)throws Exception {
			List list = new ArrayList();
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			// 页面提交数据
			LinkedHashMap paramMap =  null;
			
			if(admin == null){
				paramMap = ObjectBindUtil.getRequestParamDataNoSession(request) ;
				paramMap.put("PERSON_ID",paramMap.get("personId"));
			}else {
				paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
				paramMap.put("LANGUAGE",  admin.getLanguage());
				paramMap.put("PERSON_ID", paramMap.get("personId") == null ? admin.getPersonId() : paramMap.get("personId"));
			}
			if(paramMap.get("FLAG") == null || !"1".equals(paramMap.get("FLAG").toString())){
				paramMap.put("KEY", admin.getEmpID());
			}
			//考勤担当权限
			int authority = authorityUtil.isArUser(admin.getPersonId());
			if(authority == 1){
				paramMap.put("authority", "ArUser");
			}
			paramMap.put("batchFlag", batchFlag);//是否批量申请的标示
			if (UiUtil.getPageNum(request) > 0) {
				list = annualadjustmentInfoDao.getCwaAbnormalApplyInfoList(paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
			} else {
				list = annualadjustmentInfoDao.getCwaAbnormalApplyInfoList(paramMap);
			}
			return  list;
		}
		
		
	
		
		/**
		 * 通过request请求封装查询条件(get search conditions from request)
		 * 
		 * @param request
		 * @return
		 */
		@SuppressWarnings("unchecked")
		private LinkedHashMap getLinkedMapByRequestForSearch(
				HttpServletRequest request, String flag) {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
					flag);
			paramMap.put("specialParam", admin.getSpecialParam());
			paramMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
			paramMap.put("userNo", admin.getUserNo());
			paramMap.put("deptNo", admin.getDeptNo());
			paramMap.put("ADMIN_ID", admin.getAdminID());
			paramMap.put("CREATED_BY", admin.getPersonId());
			paramMap.put("UPDATED_BY", admin.getPersonId());
			paramMap.put("CPNY_ID", admin.getCpnyId());
			return paramMap;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public int getCwaAbnormalApplyInfoListCnt(HttpServletRequest request,String batchFlag)throws Exception {
			 
			// 页面提交数据
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			// 页面提交数据
			LinkedHashMap paramMap = getLinkedMapByRequestForSearch(request);
			if(paramMap.get("FLAG") == null || !"1".equals(paramMap.get("FLAG").toString())){
				paramMap.put("KEY", admin.getEmpID());
			}
			//考勤担当权限
			int authority = authorityUtil.isArUser(admin.getPersonId());
			if(authority == 1){
				paramMap.put("authority", "ArUser");
			}
			return  annualadjustmentInfoDao.getCwaAbnormalApplyInfoListCnt(paramMap);
		}
		
		/**
		 * 休假申请决裁列表(leave apply affirm list)
		 * 
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		public List getviewCwaAbnormalAffirmList(HttpServletRequest request)
				throws Exception {
			List list = new ArrayList();
			// 封装查询条件
			LinkedHashMap paramMap = this.getLinkedMapByRequestForSearch(request);
			paramMap.put("APPLY_TYPE_NO", "218197");// kao qin yi chang
			if (UiUtil.getPageNum(request) > 0) {
				list = annualadjustmentInfoDao.getviewCwaAbnormalAffirmList(paramMap, UiUtil
						.getPageNum(request), UiUtil.getNumPerPage(request));
			} else {
				list = annualadjustmentInfoDao.getviewCwaAbnormalAffirmList(paramMap);
			}
//			return this.getAffirmerListForInfoApply(list, request);
			return list;
		}
		
		
		
		
		@SuppressWarnings("unchecked")
		@Override
		public int getviewCwaAbnormalAffirmListCnt(HttpServletRequest request)throws Exception {
			 
			// 页面提交数据
			LinkedHashMap paramMap = this.getLinkedMapByRequestForSearch(request);
			return  annualadjustmentInfoDao.getviewCwaAbnormalAffirmListCnt(paramMap);
		}
		

		@SuppressWarnings("unchecked")
		@Override
		public List getCwaAbnormalAffirmByApplyNOList(HttpServletRequest request) throws Exception {
			//Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
			List returnList = new ArrayList();
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			// 页面提交数据
			LinkedHashMap paramMap =  null;
			
			if(admin == null){
				paramMap = ObjectBindUtil.getRequestParamDataNoSession(request) ;
				paramMap.put("PERSON_ID",paramMap.get("personId"));
			}else {
				paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
				paramMap.put("LANGUAGE",  admin.getLanguage());
				paramMap.put("PERSON_ID", paramMap.get("personId") == null ? admin.getPersonId() : paramMap.get("personId"));
			}
			paramMap.put("APPLY_TYPE_NO", "218197");//考勤异常 
			//paramMap.put("menuNum", request.getParameter("menuNum"));
			paramMap.put("AR_SUPERVISIOR_YN","YES");
			paramMap.put("interLanguage", paramMap.get("LANGUAGE"));
                
			
			
			
			// 页面提交数据
			returnList = annualadjustmentInfoDao.getCwaAbnormalAffirmByApplyNOList(paramMap);
			
			return returnList;
		}
		
		
		
		@Override
		public List getCwaCheckList(HttpServletRequest request) throws Exception {
			List list = new ArrayList();
			// 封装查询条件
			LinkedHashMap paramMap = this.getLinkedMapByRequestForSearch(request);
			if (UiUtil.getPageNum(request) > 0) {
				list = annualadjustmentInfoDao.getCwaCheckList(paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
			} else {
				list = annualadjustmentInfoDao.getCwaCheckList(paramMap);
			}
			return list;
		}


		@Override
		public int getCwaCheckCnt(HttpServletRequest request) throws Exception {
			// 页面提交数据
			LinkedHashMap paramMap = this.getLinkedMapByRequestForSearch(request);
			return  annualadjustmentInfoDao.getCwaCheckListCnt(paramMap);
		}
		
	
		
		
		@Override
		public List getAnnuCheckList(HttpServletRequest request) throws Exception {
			List list = new ArrayList();
			// 封装查询条件
			LinkedHashMap paramMap = this.getLinkedMapByRequestForSearch(request);
			
			Calendar year = Calendar.getInstance(); 
			if(paramMap.get("START_DATE") != null  && !"".equals(paramMap.get("START_DATE")))
			  paramMap.put("VAC_ID", paramMap.get("START_DATE").toString().substring(0, 4));
			else
			  paramMap.put("VAC_ID", year.get(Calendar.YEAR));
			
			if (UiUtil.getPageNum(request) > 0) {
				list = annualadjustmentInfoDao.getAnnuCheckList(paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
			} else {
				list = annualadjustmentInfoDao.getAnnuCheckList(paramMap);
			}
			return list;
		}
		
		@Override
		public int getAnnuCheckListCnt(HttpServletRequest request) {
			// 页面提交数据
			LinkedHashMap paramMap = this.getLinkedMapByRequestForSearch(request);
			Calendar year = Calendar.getInstance(); 
			if(paramMap.get("START_DATE") != null  && !"".equals(paramMap.get("START_DATE")) )
			  paramMap.put("VAC_ID", paramMap.get("START_DATE").toString().substring(0, 4));
			else
			  paramMap.put("VAC_ID", year.get(Calendar.YEAR));
			return  annualadjustmentInfoDao.getAnnuCheckListCnt(paramMap);
		}
		
		@Override
		public List getAnnuapplyListByApplyno(HttpServletRequest request) throws Exception {
			List list = new ArrayList();
			// 封装查询条件
			//LinkedHashMap paramMap = this.getLinkedMapByRequestForSearch(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			// 页面提交数据
			LinkedHashMap paramMap =  null;
			
			if(admin == null){
				paramMap = ObjectBindUtil.getRequestParamDataNoSession(request) ;
				paramMap.put("PERSON_ID",paramMap.get("personId"));
			}else {
				paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
				paramMap.put("LANGUAGE",  admin.getLanguage());
				paramMap.put("PERSON_ID", paramMap.get("personId") == null ? admin.getPersonId() : paramMap.get("personId"));
			}
			paramMap.put("APPLY_TYPE_NO", "216691");//年假调整
			paramMap.put("menuNum", request.getParameter("menuNum"));
			paramMap.put("AR_SUPERVISIOR_YN","YES");
			paramMap.put("interLanguage", paramMap.get("LANGUAGE"));

			if (UiUtil.getPageNum(request) > 0) {
				list = annualadjustmentInfoDao.getAnnuapplyListByApplyno(paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
			} else {
				list = annualadjustmentInfoDao.getAnnuapplyListByApplyno(paramMap);
			}
			return list;
		}
		
		/**
		 * 根据加班申请NO决裁信息查询(search ot info list)
		 * 
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@Override
		public List getAffirmorByApplyNoList(HttpServletRequest request) throws Exception {
			Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
			List returnList = new ArrayList();
			// 页面提交数据annualadjustmentInfoDao
			returnList = annualadjustmentInfoDao.getAffirmorByApplyNoList(paramMap);
			
			return returnList;
		}
		/**
		 * 加班申请check信息查询(search ot info list)
		 * 
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@Override
		public List getCheckorByApplyNoList(HttpServletRequest request) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
			paramMap.put("ADMIN_ID", admin.getPersonId());
			List returnList = new ArrayList();
			// 页面提交数据annualadjustmentInfoDao
			returnList = annualadjustmentInfoDao.getCheckorByApplyNoList(paramMap);
			
			return returnList;
		}
		
		/**
		 * 获取年假调整导入信息
		 * 
		 * @Copyright: AIT (c)
		 * @Company: AIT
		 * @author weizhengchen@ait.net.cn
		 * @date 2014-7-03
		 * @version V1.0
		 */
		public List getEssArVacTempList(HttpServletRequest request) {
			List retrunList = new ArrayList();
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
					"seach_");
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("PERSON_ID", admin.getPersonId());
			if (UiUtil.getPageNum(request) > 0) {
				retrunList = annualadjustmentInfoDao.getEssArVacTempList(paramMap, UiUtil
						.getPageNum(request), UiUtil.getNumPerPage(request));
			} else {
				retrunList = annualadjustmentInfoDao.getEssArVacTempList(paramMap);
			}
			return retrunList;
		}
		
		/**
		 * 获取年假调整导入数量
		 * 
		 * @Copyright: AIT (c)
		 * @Company: AIT
		 * @author weizhengchen@ait.net.cn
		 * @date 2014-7-03
		 * @version V1.0
		 */
		public int getEssArVacTempCnt(HttpServletRequest request, String errorFlag){
			int retrunInt = 0;
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
					"seach_");
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("PERSON_ID", admin.getPersonId());
			if("E".equals(errorFlag)){
				retrunInt = annualadjustmentInfoDao.getEssArVacTempErrorCnt(paramMap);
			}else{
				retrunInt = annualadjustmentInfoDao.getEssArVacTempCnt(paramMap);
			}

			return retrunInt;
		}
		
		/**
		 * 年假调整批量申请excel信息提交
		 * 
		 * @Copyright: AIT (c)
		 * @Company: AIT
		 * @author weizhengchen@ait.net.cn
		 * @date 2014-7-03
		 * @version V1.0
		 */
		public String submitImportExcelEssArVacEmpData(HttpServletRequest request){

			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
					"seach_");
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("PERSON_ID", admin.getPersonId());
			paramMap.put("CPNY_ID", admin.getCpnyId());
			paramMap.put("PR_NAME", "PKG_VAC_EXCEL_IMP.PR_IMPORT_VAC_DATA");
			try {
				return this.paTempSalesDAO.importFromExcel(paramMap);
			} catch (Exception e) {
				e.printStackTrace();
				return e.getMessage();
			}
		}
		

		/**
		 * 批量删除漏刷卡申请
		 * 
		 * @param request
		 * @return
		 * @throws Exception
		 */
		public int delArVacApplyInBatchForBatch(HttpServletRequest request)
				throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			LinkedHashMap personMap = new LinkedHashMap();
			personMap.put("APPLY_PERSON", admin.getPersonId());
			String op_flag = request.getParameter("OP_FLAG");
			/*try {
				List list = this.encapsulationApplyNoListForArVac(request);
				if("0".equals(op_flag)){//删除
					this.ArVacRecordApplyDao.delArVacRecordApplyInfo(list);
				}else{//提交
					this.ArVacRecordApplyDao.submitArVacApplyInBatch(list);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}*/
			return 1;
		}
		

		/**
		 * 年假调整批量申请详细信息查询(search ot info list)
		 * 
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		public List getArVacBatchAffirmInfoList(HttpServletRequest request) throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			List returnList = new ArrayList();
			// 页面提交数据
			LinkedHashMap paramMap = new LinkedHashMap();
			paramMap.put("APPLY_NO",  request.getParameter("APPLY_NO"));
			if(admin == null){
				paramMap = ObjectBindUtil.getRequestParamDataNoSession(request);
				paramMap.put("LANGUAGE",  request.getParameter("LANGUAGE"));
			}else {
				paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
				paramMap.put("LANGUAGE",  admin.getLanguage());
			}
			if (UiUtil.getPageNum(request) > 0) {
				returnList = annualadjustmentInfoDao.getEssArVacList(paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
			} else {
				returnList = annualadjustmentInfoDao.getEssArVacList(paramMap);
			}
			
			if(returnList != null && returnList.size() > 0){
				for(int i=0;i<returnList.size();i++){
					LinkedHashMap returnMap = (LinkedHashMap)returnList.get(i);
					returnMap.put("APPLY_TYPE", "216691");
					returnMap.put("APPLY_NO", returnMap.get("APPLY_NO"));
					List fileList = infoApplyLeaveDao.getEssFileList(returnMap);
					returnMap.put("fileList", fileList);
				}
			}
			
			return returnList;
		}
		
		/**
		 * 年假调整批量申请详细信息数量
		 * 
		 * @Copyright: AIT (c)
		 * @Company: AIT
		 * @author weizhengchen@ait.net.cn
		 * @date 2014-7-03
		 * @version V1.0
		 */
		public int getArVacBatchAffirmInfoCnt(HttpServletRequest request){
			int retrunInt = 0;
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			List returnList = new ArrayList();
			// 页面提交数据
			LinkedHashMap paramMap = new LinkedHashMap();

			if(admin == null){
				paramMap = ObjectBindUtil.getRequestParamDataNoSession(request);
				paramMap.put("LANGUAGE",  request.getParameter("LANGUAGE"));
			}else {
				paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
				paramMap.put("LANGUAGE",  admin.getLanguage());
			}
			try {
				retrunInt = annualadjustmentInfoDao.getEssArVacCnt(paramMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return retrunInt;
		}
		

		/**
		 * 批量删除年假调整申请
		 * 
		 * @param request
		 * @return
		 * @throws Exception
		 */
		public int delAracApplyInBatchForBatch(HttpServletRequest request)
				throws Exception {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			LinkedHashMap personMap = new LinkedHashMap();
			personMap.put("APPLY_PERSON", admin.getPersonId());
			String op_flag = request.getParameter("OP_FLAG");
			try {
				List list = this.encapsulationApplyNoListForArVac(request);
				if("0".equals(op_flag)){//删除
					this.annualadjustmentInfoDao.delArVacRecordApplyInfo(list);
				}else{//提交
					this.annualadjustmentInfoDao.submitArVacApplyInBatch(list);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
			return 1;
		}
		
		private List encapsulationApplyNoListForArVac(HttpServletRequest request) {
			List list = new ArrayList();
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			try {
				String[] paramData = request.getParameterValues("BATCH_VAC");
				for (int i = 0; i < paramData.length; i++) {
					LinkedHashMap map = new LinkedHashMap();
					map.put("UPDATED_BY", admin.getPersonId());
					map.put("APPLY_NO", paramData[i]);
					map.put("APPLY_TYPE", "216691");
					
					list.add(map);
				}
				return list;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
}
